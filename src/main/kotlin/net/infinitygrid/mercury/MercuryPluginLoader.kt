package net.infinitygrid.mercury

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

abstract class MercuryPluginLoader : JavaPlugin() {

    private val pluginManager = Bukkit.getPluginManager()

    final override fun onLoad() {
        onPluginLoad()
    }

    final override fun onEnable() {
        onPluginEnable()
        object : BukkitRunnable() {
            override fun run() {
                afterPluginEnable()
            }
        }
    }

    final override fun onDisable() {
        onPluginDisable()
    }

    open fun onPluginLoad() {}
    open fun onPluginEnable() {}
    open fun afterPluginEnable() {}
    open fun onPluginDisable() {}

    fun registerListener(vararg listeners: Listener) {
        listeners.forEach {
            pluginManager.registerEvents(it, this)
        }
    }

}
