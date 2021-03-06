package net.infinitygrid.mercury

import net.infinitygrid.mercury.command.MercuryCommand
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

abstract class MercuryComponent(private val plugin: MercuryPluginLoader) {

    private val pluginManager = Bukkit.getPluginManager()
    private val listeners = mutableSetOf<Listener>()
    private val commands = mutableSetOf<MercuryCommand>()

    fun initiate() {
        onEnable()
    }

    @Suppress("MemberVisibilityCanBePrivate")
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

    fun registerCommand(vararg mercuryCommands: MercuryCommand) {
        this.commands.addAll(mercuryCommands)
        plugin.registerCommand(*mercuryCommands)
    }

    fun shutdown() {
        onDisable()
        unregisterListeners()
    }

}
