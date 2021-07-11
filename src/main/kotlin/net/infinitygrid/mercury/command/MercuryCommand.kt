package net.infinitygrid.mercury.command

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

abstract class MercuryCommand(name: String) : BukkitCommand(name) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        TODO("Not yet implemented")
    }

    override fun tabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): MutableList<String> {
        return super.tabComplete(sender, alias, args, location)
    }

}