package com.wynnlab.extensions

import com.wynnlab.WynnLabAPI
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

val Player.direction get() = eyeLocation.direction

fun Entity.hasScoreboardTag(tag: String) = tag in scoreboardTags

fun Player.canDamage(player: Player) = hasScoreboardTag("pvp") && player.hasScoreboardTag("pvp") &&
        data.get(wlKey("party"), PersistentDataType.STRING) != player.data.get(wlKey("party"), PersistentDataType.STRING)

fun Player.canHeal(player: Player) = !canDamage(player)

val Player.data get() = persistentDataContainer

fun wlKey(string: String) = NamespacedKey(Bukkit.getPluginManager().getPlugin("wynnlab") ?: WynnLabAPI, string)