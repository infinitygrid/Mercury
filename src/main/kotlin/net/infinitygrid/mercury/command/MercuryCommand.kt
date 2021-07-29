package net.infinitygrid.mercury.command

import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.infinitygrid.mercury.Mercury
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

abstract class MercuryCommand(name: String, val resourceFileName: String? = null) : BukkitCommand(name) {

    public var requiresAnyPermission: MutableSet<String> = mutableSetOf()
    public var requiredPermissions: MutableSet<String> = mutableSetOf()
    public var minArgsSize = 0

    final override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        var commandResult: CommandResult? = null
        var executeCommand = false

        if (resourceFileName != null) {
            val syntaxException = thrownSyntaxException(sender, commandLabel, args)
            syntaxException?.let {
                sender.sendMessage(getSyntaxExceptionMessage(it))
                commandResult = CommandResult.SYNTAX_EXCEPTION
                return true
            }
        }

        if (!isArgumentSizeValid(args)) commandResult = CommandResult.NOT_ENOUGH_ARGUMENTS
        if (hasNoPermission(sender)) commandResult = CommandResult.NO_PERMISSION
        if (hasInsufficientPermissions(sender)) commandResult = CommandResult.INSUFFICIENT_PERMISSIONS

        if (commandResult == null) commandResult = executeCommand(CommandData(sender, label, args))

        return true
    }

    private fun thrownSyntaxException(commandSender: CommandSender, commandLabel: String, args: Array<out String>): CommandSyntaxException? {
        val command = commandToString(commandLabel, args)
        val result = Mercury.instance.commodore.dispatcher.parse(command, this)
        val optionalException = result.exceptions.entries.stream().findFirst()
        if (optionalException.isPresent) return optionalException.get().value
        return null
    }

    private fun getSyntaxExceptionMessage(exception: CommandSyntaxException): Component {
        val contextWithoutPointer = exception.context.substring(0, exception.context.length - 9)
        val input = exception.input
        val errorUnderlined = input.replace(contextWithoutPointer, "").split(" ")[0]
        return Component
            .text("${exception.type}", TextColor.color(0xFF5555))
            .append(Component.newline())
            .append(Component.text(contextWithoutPointer, TextColor.color(0xAAAAAA)))
            .append(Component.text(errorUnderlined, Style.style(TextDecoration.UNDERLINED)))
            .append(Component.text("<--[HERE]", Style.style(TextDecoration.ITALIC)))
    }

    private fun commandToString(commandLabel: String, args: Array<out String>): String {
        return "$commandLabel ${args.joinToString(" ")}"
    }

    private fun isArgumentSizeValid(args: Array<out String>): Boolean {
        return args.size >= minArgsSize
    }

    private fun hasNoPermission(sender: CommandSender): Boolean {
        return requiresAnyPermission.size > 0 && !hasAnyPermission(sender, requiresAnyPermission)
    }

    private fun hasInsufficientPermissions(sender: CommandSender): Boolean {
        return requiredPermissions.size > 0 && !hasEveryPermission(sender, requiredPermissions)
    }

    abstract fun executeCommand(data: CommandData): CommandResult

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
        return onTabComplete(sender, alias, args, location)
    }

    abstract fun onTabComplete(
        sender: CommandSender,
        alias: String,
        args: Array<out String>,
        location: Location? = null
    ): MutableList<String>

}