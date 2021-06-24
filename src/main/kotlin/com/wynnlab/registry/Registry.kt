package com.wynnlab.registry

abstract class Registry<T> {
    abstract protected val entries: MutableList<T>

    fun register(entry: T) = entries.add(entry)
    fun unregister(entry: T) = entries.remove(entry)

    fun entries(): List<T> = entries
}