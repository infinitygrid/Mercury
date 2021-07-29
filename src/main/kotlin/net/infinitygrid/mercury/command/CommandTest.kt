package net.infinitygrid.mercury.command

import org.bukkit.Location
import org.bukkit.command.CommandSender

class CommandTest : MercuryCommand("test", "test.commodore") {

    override fun executeCommand(data: CommandData): CommandResult {
        data.sender.sendMessage("ping!")
        return CommandResult.SUCCESS
    }

    override fun onTabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location?
    ): MutableList<String> {
        return mutableListOf()
    }

}
