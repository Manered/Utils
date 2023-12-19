package dev.manere.utils.command.args;

import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.command.impl.dispatcher.SuggestionDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

/**
 * Represents a command argument with a specified type and additional properties.
 *
 * @param <T> The type of the custom argument associated with this Argument.
 */
public class Argument<T extends CustomArgument<?>> {
    private BiFunction<CommandContext, ArgumentExType, Boolean> onError;
    private T type;
    private final String identifier;
    private SuggestionDispatcher suggestions;
    private String defaultVal;

    /**
     * Creates an Argument with the given identifier.
     *
     * @param identifier The identifier of the argument.
     */
    public Argument(@NotNull String identifier) {
        this.identifier = identifier;
    }

    /**
     * Creates an Argument with the given identifier and custom argument type.
     *
     * @param identifier The identifier of the argument.
     * @param type       The custom argument type associated with this Argument.
     */
    public Argument(@NotNull String identifier, @NotNull T type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * Creates a new Argument instance with the specified identifier.
     *
     * @param identifier The identifier of the argument.
     * @param <T>        The type of the custom argument associated with this Argument.
     * @return A new Argument instance.
     */
    public static @NotNull <T extends CustomArgument<?>> Argument<T> of(@NotNull String identifier) {
        return new Argument<>(identifier);
    }

    /**
     * Creates a new Argument instance with the specified identifier and custom argument type.
     *
     * @param identifier The identifier of the argument.
     * @param type       The custom argument type associated with this Argument.
     * @param <T>        The type of the custom argument associated with this Argument.
     * @return A new Argument instance.
     */
    public static @NotNull <T extends CustomArgument<?>> Argument<T> of(@NotNull String identifier, @NotNull T type) {
        return new Argument<>(identifier, type);
    }

    /**
     * Gets the error handling function associated with this Argument.
     *
     * @return The error handling function.
     */
    public BiFunction<CommandContext, ArgumentExType, Boolean> onError() {
        return onError;
    }

    /**
     * Sets the error handling function for this Argument.
     *
     * @param onError The error handling function.
     * @return This Argument instance.
     */
    public @NotNull Argument<T> onError(@NotNull BiFunction<CommandContext, ArgumentExType, Boolean> onError) {
        this.onError = onError;
        return this;
    }

    /**
     * Gets the suggestion dispatcher associated with this Argument.
     *
     * @return The suggestion dispatcher.
     */
    public @Nullable SuggestionDispatcher suggestions() {
        return suggestions;
    }

    /**
     * Sets the suggestion dispatcher for this Argument.
     *
     * @param suggestions The suggestion dispatcher.
     * @return This Argument instance.
     */
    public @NotNull Argument<T> suggestions(@NotNull SuggestionDispatcher suggestions) {
        this.suggestions = suggestions;
        return this;
    }

    /**
     * Gets the custom argument type associated with this Argument.
     *
     * @return The custom argument type.
     */
    public @NotNull T type() {
        return type;
    }

    /**
     * Sets the custom argument type for this Argument.
     *
     * @param type The custom argument type.
     * @return This Argument instance.
     */
    public @NotNull Argument<T> type(@NotNull T type) {
        this.type = type;
        return this;
    }

    /**
     * Gets the identifier of this Argument.
     *
     * @return The identifier.
     */
    public @NotNull String identifier() {
        return identifier;
    }

    /**
     * Sets the default value for this Argument.
     *
     * @param defaultVal The default value.
     * @return This Argument instance.
     */
    public @NotNull Argument<T> defaultVal(@NotNull String defaultVal) {
        this.defaultVal = defaultVal;
        return this;
    }

    /**
     * Gets the default value of this Argument.
     *
     * @return The default value.
     */
    public @Nullable String defaultVal() {
        return defaultVal;
    }
}
