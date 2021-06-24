package com.wynnlab.registry

import com.wynnlab.mobs.BaseMob

object MobRegistry : Registry<BaseMob>() {
    override val entries = mutableListOf<BaseMob>()
}