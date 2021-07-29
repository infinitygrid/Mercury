package net.infinitygrid.mercury

import com.mojang.brigadier.tree.LiteralCommandNode
import me.lucko.commodore.Commodore
import me.lucko.commodore.CommodoreProvider
import me.lucko.commodore.file.CommodoreFileFormat
import net.infinitygrid.mercury.command.MercuryCommand
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.io.IOException
import java.util.logging.Level

open class MercuryPluginLoader : JavaPlugin() {

    private val pluginManager = Bukkit.getPluginManager()
    private val commandMap = Bukkit.getCommandMap()
    private val initUnixTimeMs = System.currentTimeMillis()
    private val componentSet = mutableSetOf<MercuryComponent>()
    var commodore: Commodore? = null

    final override fun onLoad() {
        onPluginLoad()
    }

    final override fun onEnable() {
        onPluginEnable()
        logger.log(Level.INFO, "Plugin has been enabled! (${System.currentTimeMillis() - initUnixTimeMs}ms)")
        object : BukkitRunnable() {
            override fun run() {
                afterPluginEnable()
            }
        }
    }

    final override fun onDisable() {
        componentSet.forEach { it.shutdown() }
        onPluginDisable()
        logger.log(Level.INFO, "Plugin has been disabled.")
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

    fun registerCommand(vararg mercuryCommands: MercuryCommand) {
        mercuryCommands.forEach { mercuryCommand ->
            commandMap.register(mercuryCommand.name, mercuryCommand)
            if (CommodoreProvider.isSupported()) commodore = CommodoreProvider.getCommodore(this)
            mercuryCommand.resourceFileName?.let { resourceFile ->
                try {
                    commodore!!.register(
                        mercuryCommand,
                        CommodoreFileFormat.parse<LiteralCommandNode<MercuryCommand>>(this.getResource(resourceFile))
                    )
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
        }
    }

    fun registerComponent(vararg components: MercuryComponent) {
        componentSet.addAll(components)
        components.forEach { it.initiate() }
    }

}