package com.quickstart.api

import android.content.Context
import chest.Chest
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.net.URISyntaxException
import java.util.*
import kotlin.properties.Delegates

/**
 * Created at 24.11.16 13:47
 * @author Alexey_Ivanov
 */

class CookieJar (): okhttp3.CookieJar {

    private val STORE_NAME = "AndroidCookieStoreChest"

    private val GSON = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    private var chest: Chest by Delegates.notNull()
    private val cookieMap: Hashtable<HttpUrl, MutableList<Cookie>> by lazy {
        Hashtable<HttpUrl, MutableList<Cookie>>()
    }

    @Synchronized fun init(context: Context): CookieJar {
        chest = Chest.newInstance(context, STORE_NAME)

        // we save cookies as map URL -> Set<String>,
        // where URL is a String and Set contains encoded serialized HttpCookies
        @Suppress("UNCHECKED_CAST")
        val prefsMap: Map<String, Set<String>> = chest.getAll() as Map<String, Set<String>>

        prefsMap.forEach { entry: Map.Entry<String, Set<String>> ->
            try {
                var hostUrl = HttpUrl.parse(entry.key)

                if (!hostUrl.isHttps) {
                    // API v1
                    hostUrl = hostUrl.newBuilder().scheme("https").build()
                }

                val cookiesSet = entry.value

                val restoredCookies = ArrayList<Cookie>(cookiesSet.size)
                val cookieStrings = ArrayList<String>(cookiesSet.size)

                cookiesSet.forEach { cookieString ->
                    // decoding cookie from String and deserializing it
                    val decodedCookie = decodeCookie(hostUrl, cookieString)
                    if (decodedCookie != null && !decodedCookie.isExpired()) {

                        restoredCookies.add(decodedCookie)
                        cookieStrings.add(cookieString)

                    } else {
                    }
                }

                cookieMap.put(hostUrl, restoredCookies)
                chest.putStringSet(hostUrl.toString(), cookieStrings.toSet())
            } catch (e: URISyntaxException) {
                // if error occurs and we can't convert String to URL,
                // then just delete the whole set
                chest.remove(entry.key)
            }
        }

        return this
    }


    override fun loadForRequest(url: HttpUrl?): MutableList<Cookie>? {
        if (url == null) {
            return ArrayList()
        }
        val host = getHost(url)
        val cookies = cookieMap[host] ?: ArrayList()
        return cookies
    }

    override fun saveFromResponse(url: HttpUrl?, listOfCookies: MutableList<Cookie>?) {
        if (url == null) {
            return
        }
        if (listOfCookies == null) {
            return
        }
        val host = getHost(url)
        val cookiesToStore = mergeCookies(cookieMap[host] ?: ArrayList(), listOfCookies)
        cookieMap[host] = cookiesToStore
        chest.putStringSet(host.toString(), cookiesToStore.map { it.toString() }.toSet())
    }

    private fun mergeCookies(existingCookies: MutableList<Cookie>, newCookies: MutableList<Cookie>): MutableList<Cookie> {
        existingCookies.removeAll { existing -> newCookies.any { it.name() == existing.name() } } // remove obsolete cookies
        existingCookies.addAll(newCookies.distinct())
        return existingCookies
    }

    private fun getHost(url: HttpUrl) = HttpUrl.Builder().host(url.host()).scheme(url.scheme()).build()

    fun Cookie.isExpired(): Boolean {
        if (persistent()) {
            return expiresAt() < System.currentTimeMillis()
        } else {
            return false
        }
    }


    @Synchronized
    fun removeAll(): Boolean {

        val result = !cookieMap.isEmpty

        cookieMap.clear()
        chest.clear()

        return result
    }

    private fun decodeCookie(hostUrl: HttpUrl, cookieString: String?): Cookie? {
        return cookieString?.let{Cookie.parse(hostUrl, it)}
    }
}
