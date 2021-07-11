package net.infinitygrid.mercury.command

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

abstract class MercuryCommand(name: String) : BukkitCommand(name) {

    var requireAnyPermission: MutableList<String>? = null
    val requirePermissions: MutableList<String>? = null

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        requireAnyPermission?.let {
            if (!hasAnyPermission(sender, it)) return true
        }
        requirePermissions?.let {
            if (!hasEveryPermission(sender, it)) return true
        }
        return executeCommand(sender, commandLabel, args)
    }

    abstract fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean

    private fun hasEveryPermission(commandSender: CommandSender, nodeList: List<String>): Boolean {
        nodeList.forEach { permissionNode ->
            if (!commandSender.hasPermission(permissionNode)) return false
        }
        return true
    }

    private fun hasAnyPermission(commandSender: CommandSender, nodeList: List<String>): Boolean {
        nodeList.forEach { permissionNode ->
            if (commandSender.hasPermission(permissionNode)) return true
        }
        return false
    }

    final override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): MutableList<String> {
        return super.tabComplete(sender, alias, args, location)
    }

}