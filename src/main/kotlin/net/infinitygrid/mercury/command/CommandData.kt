package net.infinitygrid.mercury.command

import org.bukkit.command.CommandSender

data class CommandData(val sender: CommandSender, val label: String, val args: Array<out String>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CommandData

        if (sender != other.sender) return false
        if (label != other.label) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sender.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }

}
