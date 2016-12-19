package com.quickstart.utils

/**
 * Created at 19.12.16 16:29
 * @author Alexey_Ivanov
 */
inline fun <E> MutableCollection<E>.addIfNotExists(element:E){
    if (!this.contains(element)){
        this.add(element)
    }
}
