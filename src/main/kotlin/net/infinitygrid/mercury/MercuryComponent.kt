package net.infinitygrid.mercury

import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

public abstract class MercuryComponent(private val plugin: MercuryPluginLoader) {

    private val pluginManager = Bukkit.getPluginManager()
    private val listeners = mutableSetOf<Listener>()

    internal fun initiate() {
        onModuleEnable()
    }

    public fun unregisterListeners() {
        listeners.forEach { listener ->
            HandlerList.unregisterAll(listener)
        }
    }

    public open fun onModuleEnable() {}
    public open fun onModuleDisable() {}

    public fun registerListener(vararg listeners: Listener) {
        this.listeners.addAll(listeners)
        listeners.forEach { listener ->
            pluginManager.registerEvents(listener, plugin)
        }
    }

}
