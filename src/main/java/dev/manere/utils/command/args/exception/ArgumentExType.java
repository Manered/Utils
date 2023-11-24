package dev.manere.utils.command.args.exception;

/**
 * Enum representing different types of argument parsing exceptions.
 */
public enum ArgumentExType {
    /**
     * Indicates that the argument is null (not provided).
     */
    ARG_IS_NULL,

    /**
     * Indicates that the parsed command would return null.
     */
    RETURNS_NULL,

    /**
     * Indicates that the syntax of the command is invalid. (Incorrect Usage)
     */
    INVALID_SYNTAX
}
