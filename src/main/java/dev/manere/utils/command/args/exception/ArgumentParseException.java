package dev.manere.utils.command.args.exception;

/**
 * Exception thrown when there is an issue parsing command arguments.
 */
public class ArgumentParseException extends RuntimeException {
    private final ArgumentExType type;

    /**
     * Constructs an ArgumentParseException with a specified error message and exception type.
     *
     * @param message The error message associated with the exception.
     * @param type The type of argument parsing exception.
     */
    public ArgumentParseException(String message, ArgumentExType type) {
        super(message);
        this.type = type;
    }

    /**
     * Retrieves the type of argument parsing exception.
     *
     * @return The type of argument parsing exception.
     */
    public ArgumentExType type() {
        return this.type;
    }
}
