package com.wynnlab.mobs

import com.wynnlab.mobs.spells.BaseMobSpell
import com.wynnlab.util.combineOr
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.inventory.ItemStack
import kotlin.reflect.KClass

abstract class BaseMob {
    abstract val name: TextComponent
    abstract val mobType: EntityType
    abstract val ai: AI
    abstract val stats: Stats
    abstract val elementalDamage: Elemental<IntRange>?
    abstract val elementalDefense: Elemental<Int>?
    abstract val ambientSound: Sound?
    abstract val hurtSound: Sound?
    abstract val deathSound: Sound?
    abstract val kbResistance: Double
    abstract val equipment: Equipment
    abstract val spells: List<(Entity, Player) -> BaseMobSpell>

    data class Stats(
        val level: Int,
        val health: Int,
        val regen: Int,
        val damage: IntRange,
        val attackSpeed: Double,
        val projectile: Projectile?,
        val speed: Double,
        val vision: Double,
        val defense: Double,
    )

    data class Equipment(
        val head: ItemStack? = null,
        val chest: ItemStack? = null,
        val legs: ItemStack? = null,
        val feet: ItemStack? = null,
        val mainHand: ItemStack? = null,
        val offHand: ItemStack? = null,
    )

    data class Sound(
        val bukkitSound: org.bukkit.Sound,
        val volume: Float,
        val pitch: Float
    )

    data class Projectile(
        val type: KClass<out org.bukkit.entity.Projectile>,
        val item: ItemStack?
    )

    data class Elemental<T>(
        val earth: T,
        val thunder: T,
        val water: T,
        val fire: T,
        val air: T
    )

    open class AI protected constructor(val flags: Int) {
        constructor(vararg ais: AI) : this(combineOr(ais))

        object CastSpells : AI(1 shl 0)
        object MeleeAttack : AI(1 shl 1)
        object RangedAttack : AI(1 shl 2)
    }

    companion object {
        val EVIL_NAME_COLOR = TextColor.color(0xd91a2d)
    }
}