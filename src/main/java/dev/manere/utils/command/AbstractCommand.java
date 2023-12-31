package dev.manere.utils.command;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.command.impl.dispatcher.CommandDispatcher;
import dev.manere.utils.command.impl.dispatcher.SuggestionDispatcher;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

/**
 * An alternative to {@link CommandExecutor}, {@link TabExecutor}, the old (now non-existent) Commander.
 * Uses {@link Commands}
 *
 * @see Commands
 * @see CommandExecutor
 * @see TabExecutor
 */
public abstract class AbstractCommand implements CommandDispatcher, SuggestionDispatcher {
    /**
     * Retrieves the instance of a class that extends AbstractCommand.
     * Requires for the instance to have a constructor without any parameters.
     *
     * @param clazz The class that extends AbstractCommand
     * @return The instance of the class
     * @param <T> The instance that extends AbstractCommand
     */
    public static <T extends AbstractCommand> @NotNull T instance(@NotNull Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | NoSuchMethodException
                 | InvocationTargetException
                 | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the instance of a class that extends AbstractCommand
     * and then runs {@code command().build().register()} on it.
     * @param clazz The class that extends AbstractCommand
     * @return The instance of the class
     * @param <T> The instance that extends AbstractCommand
     */
    @CanIgnoreReturnValue
    public static <T extends AbstractCommand> @NotNull T register(@NotNull Class<T> clazz) {
        instance(clazz).command().build().register();
        return instance(clazz);
    }

    /**
     * The command information.
     * @return The command information.
     */
    public abstract @NotNull Commands command();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract @NotNull CommandResult run(@NotNull CommandContext context);

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Suggestions suggest(@NotNull CommandContext context) {
        return null;
    }
}
