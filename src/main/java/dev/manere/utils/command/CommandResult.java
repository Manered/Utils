package dev.manere.utils.command;

/**
 * Represents the result of executing a command.
 */
public class CommandResult {
    private final int ordinal;
    private final String identifier;

    /**
     * Stops the execution of the command, does not print the usage of the command.
     */
    public static final CommandResult SUCCESS = CommandResult.result(0, "success");

    /**
     * Stops the execution of the command, prints the usage of the command.
     */
    public static final CommandResult FAILURE = CommandResult.result(1, "failure");

    private CommandResult(int ordinal, String identifier) {
        this.ordinal = ordinal;
        this.identifier = identifier;
    }

    public static CommandResult result(int ordinal, String identifier) {
        return new CommandResult(ordinal, identifier);
    }

    public static CommandResult success() {
        return CommandResult.SUCCESS;
    }

    public static CommandResult finish() {
        return success();
    }

    public static CommandResult done() {
        return finish();
    }

    public static CommandResult stop() {
        return done();
    }

    public static CommandResult failure() {
        return CommandResult.FAILURE;
    }

    public static CommandResult fail() {
        return failure();
    }

    public static CommandResult error() {
        return fail();
    }

    public static CommandResult usage() {
        return error();
    }

    public int ordinal() {
        return ordinal;
    }

    public String identifier() {
        return identifier;
    }
}
