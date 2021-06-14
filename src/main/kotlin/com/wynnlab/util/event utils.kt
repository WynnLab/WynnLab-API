package com.wynnlab.util

import co.aikar.timings.TimedEventExecutor
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.IllegalPluginAccessException
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.RegisteredListener
import java.lang.reflect.Method

inline fun <reified E : Event> Listener.register(plugin: Plugin, method: String, priority: EventPriority = EventPriority.NORMAL, ignoreCancelled: Boolean = false) {
    getHandlerList(E::class.java).register(RegisteredListener(this, TimedEventExecutor(EventExecutor.create(this::class.java.getDeclaredMethod(method, E::class.java), E::class.java), plugin, null, E::class.java), priority, plugin, ignoreCancelled))
}

inline fun <reified E : Event> Listener.unregister() {
    getHandlerList(E::class.java).unregister(this)
}

fun getHandlerList(clazz: Class<out Event>): HandlerList {
    val method: Method = getRegistrationClass(clazz).getDeclaredMethod("getHandlerList")
    method.isAccessible = true
    return method.invoke(null) as HandlerList
}

private fun getRegistrationClass(clazz: Class<out Event>): Class<out Event> = try {
    clazz.getDeclaredMethod("getHandlerList")
    clazz
} catch (e: NoSuchMethodException) {
    if (clazz.superclass != null && clazz.superclass != Event::class.java
        && Event::class.java.isAssignableFrom(clazz.superclass)
    ) {
        getRegistrationClass(clazz.superclass.asSubclass(Event::class.java))
    } else {
        throw IllegalPluginAccessException("Unable to find handler list for event " + clazz.name + ". Static getHandlerList method required!")
    }
}