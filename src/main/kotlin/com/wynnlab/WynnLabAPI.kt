package com.wynnlab

import org.bukkit.plugin.java.JavaPlugin

class WynnLabAPIPlugin : JavaPlugin() {
    override fun onEnable() {
        WynnLabAPI_ = this
    }
}

private lateinit var WynnLabAPI_: WynnLabAPIPlugin

val WynnLabAPI get() = WynnLabAPI_