package net.infinitygrid.mercury.command

import me.lucko.commodore.file.CommodoreFileFormat
import net.infinitygrid.mercury.Mercury
import org.bukkit.Location
import org.bukkit.command.CommandSender

class CommandTest : MercuryCommand("test", CommodoreFileFormat.parse(Mercury.instance.getResource("test.commodore"))) {

    override fun executeCommand(sender: CommandSender, commandLabel: String, args: Array<out String>): CommandResult {
        sender.sendMessage("ping!")
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
