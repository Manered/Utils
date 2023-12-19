package dev.manere.utils.command.args.exception;

import dev.manere.utils.command.args.primitive.IntegerArgument;

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
    INVALID_SYNTAX,

    /**
     * Indicates that the argument provided is not within the expected integer range.
     * @see IntegerArgument
     */
    INVALID_RANGE
}
