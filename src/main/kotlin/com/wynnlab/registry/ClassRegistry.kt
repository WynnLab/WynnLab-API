package com.wynnlab.registry

import com.wynnlab.classes.BaseClass

object ClassRegistry : Registry<BaseClass>() {
    override val entries = mutableListOf<BaseClass>()
}