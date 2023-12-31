package dev.manere.utils.command.impl;

import dev.manere.utils.command.CommandResult;
import org.jetbrains.annotations.NotNull;

/**
 * Helps with parsing a CommandResult as a boolean.
 */
public class CommandResultWrapper {
    /**
     * Returns a boolean from a CommandResult.
     * @param result The CommandResult to convert.
     * @return true or false depending on the result.
     */
    public static boolean wrap(@NotNull CommandResult result) {
        if (result.ordinal() > 1) {
            throw new IllegalStateException("Unexpected value: " + result.ordinal());
        }

        return switch (result.ordinal()) {
            case 0 -> true;
            case 1 -> false;
            default -> throw new IllegalStateException("Unexpected value: " + result.ordinal());
        };
    }

    /**
     * Returns a CommandResult from a boolean.
     * @param result The boolean to convert.
     * @return the CommandResult depending on the result.
     */
    public static @NotNull CommandResult unwrap(boolean result) {
        return result ? CommandResult.done() : CommandResult.fail();
    }
}
