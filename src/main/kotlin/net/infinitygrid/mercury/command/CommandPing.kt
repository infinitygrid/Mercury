package net.infinitygrid.mercury.command

import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandPing : MercuryCommand("ping") {

    override fun executeCommand(data: CommandData): CommandResult {
        if (data.sender !is Player) return CommandResult.INVALID_COMMAND_SENDER
        val player: Player = data.sender
        player.sendMessage("Your ping currently is ${player.ping}ms!")
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