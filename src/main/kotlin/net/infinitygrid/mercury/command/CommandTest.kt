package net.infinitygrid.mercury.command

import net.infinitygrid.mercury.Mercury
import org.bukkit.Location
import org.bukkit.command.CommandSender

class CommandTest : MercuryCommand("test", Mercury.instance.getResource("test.commodore")) {

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        sender.sendMessage("ping!")
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): MutableList<String> {
        TODO("Not yet implemented")
    }

}
