package com.quickstart.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created at 24.11.16 13:03
 * @author Alexey_Ivanov
 */
open class Repo constructor (@PrimaryKey var id: Long, var name : String) : RealmObject() {
    constructor() : this (-1, "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Repo) return false

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Repo(id=$id, name='$name')"
    }
}
