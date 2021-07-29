package net.infinitygrid.mercury.command

enum class CommandResult {

    SUCCESS,
    NO_PERMISSION,
    INSUFFICIENT_PERMISSIONS,
    NOT_ENOUGH_ARGUMENTS,
    SYNTAX_EXCEPTION,
    INVALID_COMMAND_SENDER

}