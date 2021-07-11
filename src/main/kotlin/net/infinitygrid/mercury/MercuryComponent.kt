package net.infinitygrid.mercury

import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

abstract class MercuryComponent(private val plugin: MercuryPluginLoader) {

    private val pluginManager = Bukkit.getPluginManager()
    private val listeners = mutableSetOf<Listener>()

    fun initiate() {
        onEnable()
    }

    fun unregisterListeners() {
        listeners.forEach { listener ->
            HandlerList.unregisterAll(listener)
        }
    }

    open fun onEnable() {}
    open fun onDisable() {}

    fun registerListener(vararg listeners: Listener) {
        this.listeners.addAll(listeners)
        listeners.forEach { listener ->
            pluginManager.registerEvents(listener, plugin)
        }
    }

    fun shutdown() {
        onDisable()
        unregisterListeners()
    }

}
