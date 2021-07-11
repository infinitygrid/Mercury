package net.infinitygrid.mercury.command

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import java.io.InputStream

abstract class MercuryCommand(name: String, val commodoreFileInStream: InputStream? = null) : BukkitCommand(name) {

    var requiresAnyPermission: MutableSet<String> = mutableSetOf()
    val requiredPermissions: MutableSet<String> = mutableSetOf()

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (requiresAnyPermission.size > 0) if (!hasAnyPermission(sender, requiresAnyPermission)) return true
        if (requiredPermissions.size > 0) if (!hasEveryPermission(sender, requiredPermissions)) return true
        return executeCommand(sender, commandLabel, args)
    }

    abstract fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean

    private fun hasEveryPermission(commandSender: CommandSender, nodeList: Set<String>): Boolean {
        nodeList.forEach { permissionNode ->
            if (!commandSender.hasPermission(permissionNode)) return false
        }
        return true
    }

    private fun hasAnyPermission(commandSender: CommandSender, nodeList: Set<String>): Boolean {
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