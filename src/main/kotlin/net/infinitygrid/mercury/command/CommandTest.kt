package net.infinitygrid.mercury.command

import org.bukkit.command.CommandSender

class CommandTest : MercuryCommand("test") {

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        sender.sendMessage("ping!")
        return true
    }

}
