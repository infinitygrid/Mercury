package net.infinitygrid.mercury

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.util.logging.Level

public abstract class MercuryPluginLoader public constructor() : JavaPlugin() {

    private val pluginManager = Bukkit.getPluginManager()
    private val initUnixTimeMs = System.currentTimeMillis()
    private val componentSet = mutableSetOf<MercuryComponent>()

    final override fun onLoad() {
        onPluginLoad()
    }

    final override fun onEnable() {
        onPluginEnable()
        logger.log(Level.INFO, "Plugin has been enabled! (Took ${System.currentTimeMillis() - initUnixTimeMs}ms!)")
        object : BukkitRunnable() {
            override fun run() {
                afterPluginEnable()
            }
        }
    }

    final override fun onDisable() {
        componentSet.forEach { component ->
            component.shutdown()
        }
        onPluginDisable()
        logger.log(Level.INFO, "Plugin has been disabled.")
    }

    public open fun onPluginLoad() {}
    public open fun onPluginEnable() {}
    public open fun afterPluginEnable() {}
    public open fun onPluginDisable() {}

    public fun registerListener(vararg listeners: Listener) {
        listeners.forEach {
            pluginManager.registerEvents(it, this)
        }
    }

    public fun registerComponent(vararg components: MercuryComponent) {
        componentSet.addAll(components)
        components.forEach { component ->
            component.initiate()
        }
    }

}
