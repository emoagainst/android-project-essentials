package com.quickstart.utils

import android.content.Context
import android.content.SharedPreferences
import com.quickstart.BuildConfig
import java.util.*
import kotlin.properties.Delegates

/*
 * Created at 28/10/14 17:06
 * @author altero
 */
interface OnChestChangeListener {
    fun onChestChange(key: String)
}

class Chest private constructor(val context: Context, val name: String) : SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {

        private var instance: Chest by Delegates.notNull()

        @JvmStatic
        fun init(context: Context) {
            init(context, "${BuildConfig.APPLICATION_ID}_${BuildConfig.BUILD_TYPE}")
        }
        @JvmStatic
        fun init(context: Context, name: String) {
            instance = newInstance(context.applicationContext, name)
        }
        @JvmStatic
        fun newInstance(context: Context, name: String): Chest = Chest(context.applicationContext, name)

        fun registerOnChestChangeListener(listener: OnChestChangeListener) {
            instance.registerOnChestChangeListener(listener)
        }

        fun unregisterOnChestChangeListener(listener: OnChestChangeListener) {
            instance.unregisterOnChestChangeListener(listener)
        }

        fun unregisterAllOnChestChangeListeners() {
            instance.listeners.clear()
        }

        fun getString(key: String, defValue: String? = null): String? = instance.getString(key, defValue)
        fun putString(key: String, value: String?) {
            instance.putString(key, value)
        }

        fun getStringSet(key: String, defValue: Set<String>? = null): Set<String>? = instance.getStringSet(key, defValue)
        fun putStringSet(key: String, value: Set<String>?) {
            instance.putStringSet(key, value)
        }

        fun getBoolean(key: String, defValue: Boolean): Boolean = instance.getBoolean(key, defValue)
        fun putBoolean(key: String, value: Boolean) {
            instance.putBoolean(key, value)
        }

        fun getInt(key: String, defValue: Int): Int = instance.getInt(key, defValue)
        fun putInt(key: String, value: Int) {
            instance.putInt(key, value)
        }

        fun getLong(key: String, defValue: Long): Long = instance.getLong(key, defValue)
        fun putLong(key: String, value: Long) {
            instance.putLong(key, value)
        }

        fun getFloat(key: String, defValue: Float): Float = instance.getFloat(key, defValue)
        fun putFloat(key: String, value: Float) {
            instance.putFloat(key, value)
        }

        fun getAll(): Map<String, Any?> = instance.getAll()

        fun remove(key: String) {
            instance.remove(key)
        }

        fun clear() {
            instance.clear()
        }
    }

    private val lock: Any by lazy { Any() }

    private val stub: Any by lazy { Any() }
    private val listeners: WeakHashMap<OnChestChangeListener, Any>
            by lazy { WeakHashMap<OnChestChangeListener, Any>() }

    private val preferences: SharedPreferences by lazy {
        val prefs = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        prefs.registerOnSharedPreferenceChangeListener(this)
        prefs
    }

    fun registerOnChestChangeListener(listener: OnChestChangeListener) {
        listeners.put(listener, stub)
    }

    fun unregisterOnChestChangeListener(listener: OnChestChangeListener) {
        listeners.remove(listener)
    }

    fun getString(key: String, defValue: String?): String? =
            synchronized(lock, { preferences.getString(key, defValue) })

    fun putString(key: String, value: String?) {
        synchronized(lock, { preferences.edit().putString(key, value).apply() })
    }

    fun getStringSet(key: String, defValue: Set<String>?): Set<String>? =
            synchronized(lock, { preferences.getStringSet(key, defValue) })

    fun putStringSet(key: String, value: Set<String>?) {
        synchronized(lock, { preferences.edit().putStringSet(key, value).apply() })
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean =
            synchronized(lock, { preferences.getBoolean(key, defValue) })

    fun putBoolean(key: String, value: Boolean) {
        synchronized(lock, { preferences.edit().putBoolean(key, value).apply() })
    }

    fun getInt(key: String, defValue: Int): Int =
            synchronized(lock, { preferences.getInt(key, defValue) })

    fun putInt(key: String, value: Int) {
        synchronized(lock, { preferences.edit().putInt(key, value).apply() })
    }

    fun getLong(key: String, defValue: Long): Long =
            synchronized(lock, { preferences.getLong(key, defValue) })

    fun putLong(key: String, value: Long) {
        synchronized(lock, { preferences.edit().putLong(key, value).apply() })
    }

    fun getFloat(key: String, defValue: Float): Float =
            synchronized(lock, { preferences.getFloat(key, defValue) })

    fun putFloat(key: String, value: Float) {
        synchronized(lock, { preferences.edit().putFloat(key, value).apply() })
    }

    fun getAll(): Map<String, Any?> = synchronized(lock, { preferences.all })

    fun remove(key: String) {
        synchronized(lock, { preferences.edit().remove(key).apply() })
    }

    fun clear() {
        synchronized(lock, { preferences.edit().clear().apply() })
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        listeners.keys.forEach { it -> it.onChestChange(key) }
    }
}

//public fun chestValue(key: String, defValue: String): ReadWriteProperty<Any, String?> = ChestEntry(key, defValue)
//public fun chestValue(key: String, defValue: Boolean): ReadWriteProperty<Any, Boolean?> = ChestEntry(key, defValue)
//public fun chestValue(key: String, defValue: Int): ReadWriteProperty<Any, Int?> = ChestEntry(key, defValue)
//public fun chestValue(key: String, defValue: Long): ReadWriteProperty<Any, Long?> = ChestEntry(key, defValue)
//public fun chestValue(key: String, defValue: Float): ReadWriteProperty<Any, Float?> = ChestEntry(key, defValue)
//
//private class ChestEntry<T : Any?>(val key: String, val defValue: T) : ReadWriteProperty<Any, T> {
//
//    private val TAG = makeLogTag(this.javaClass)
//
//    [suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_UNIT_OR_ANY")]
//    override fun get(thisRef: Any, desc: PropertyMetadata): T {
//
//        return when (defValue) {
//            is String -> Chest.getString(key, defValue)!!
//            is Boolean -> Chest.getBoolean(key, defValue)
//            is Int -> Chest.getInt(key, defValue)
//            is Long -> Chest.getLong(key, defValue)
//            is Float -> Chest.getFloat(key, defValue)
//            else -> throw IllegalArgumentException("Chest can't handle this type: ${defValue?.javaClass}")
//        } as T
//    }
//
//    override fun set(thisRef: Any, desc: PropertyMetadata, value: T) {
//
//        if (value == null)
//            Chest.remove(key)
//        else {
//            when (defValue) {
//                is String -> Chest.putString(key, value as String)
//                is Boolean -> Chest.putBoolean(key, value as Boolean)
//                is Int -> Chest.putInt(key, value as Int)
//                is Long -> Chest.putLong(key, value as Long)
//                is Float -> Chest.putFloat(key, value as Float)
//                else -> throw IllegalArgumentException("Chest can't handle this type: ${defValue?.javaClass}")
//            }
//        }
//    }
//}
