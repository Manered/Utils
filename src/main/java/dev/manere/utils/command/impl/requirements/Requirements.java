package dev.manere.utils.command.impl.requirements;

import dev.manere.utils.command.impl.dispatcher.CommandContext;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Utility class for defining common command requirements as predicates.
 */
public class Requirements {

    /**
     * Predicate that checks if the command sender is a player.
     *
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> playerOnly() {
        return ctx -> !ctx.senderInstanceOfPlayer();
    }

    /**
     * Predicate that checks if the command sender is a player and sends a custom message if not.
     *
     * @param message The custom message to send.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> playerOnly(@NotNull Component message) {
        return playerOnly(ctx -> ctx.sender().sendMessage(message));
    }

    /**
     * Predicate that checks if the command sender is a player and executes a custom action if not.
     *
     * @param executeIfNotMet The action to execute if the requirement is not met.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> playerOnly(@NotNull Consumer<CommandContext> executeIfNotMet) {
        return ctx -> {
            if (!ctx.senderInstanceOfPlayer()) {
                executeIfNotMet.accept(ctx);
                return true;
            }

            return false;
        };
    }

    /**
     * Predicate that checks if the command sender is a player and executes a custom action if not.
     *
     * @param executeIfNotMet The action to execute if the requirement is not met.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> playerOnly(@NotNull Runnable executeIfNotMet) {
        return playerOnly(ctx -> executeIfNotMet.run());
    }

    /**
     * Predicate that checks if the command has a specific number of arguments.
     *
     * @param required The required number of arguments.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> argSize(int required) {
        return ctx -> ctx.size() != required;
    }

    /**
     * Predicate that checks if the command has a specific number of arguments and sends a custom message if not.
     *
     * @param required The required number of arguments.
     * @param message  The custom message to send.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> argSize(int required, @NotNull Component message) {
        return argSize(required, ctx -> ctx.sender().sendMessage(message));
    }

    /**
     * Predicate that checks if the command has a specific number of arguments and executes a custom action if not.
     *
     * @param required         The required number of arguments.
     * @param executeIfNotMet The action to execute if the requirement is not met.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> argSize(int required, @NotNull Consumer<CommandContext> executeIfNotMet) {
        return ctx -> {
            if (ctx.size() != required) {
                executeIfNotMet.accept(ctx);
                return true;
            }

            return false;
        };
    }

    /**
     * Predicate that checks if the command has a specific number of arguments and executes a custom action if not.
     *
     * @param required         The required number of arguments.
     * @param executeIfNotMet The action to execute if the requirement is not met.
     * @return The predicate.
     */
    public static @NotNull Predicate<CommandContext> argSize(int required, @NotNull Runnable executeIfNotMet) {
        return argSize(required, ctx -> executeIfNotMet.run());
    }
}