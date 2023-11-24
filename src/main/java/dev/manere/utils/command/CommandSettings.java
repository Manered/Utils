package dev.manere.utils.command;

import dev.manere.utils.command.builder.dispatcher.CommandDispatcher;
import dev.manere.utils.command.builder.dispatcher.SuggestionDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Class representing settings for a {@link Commander} command.
 */
public class CommandSettings {
    private CommandDispatcher executes;
    private SuggestionDispatcher completes;
    private CommandType type;

    /**
     * Creates settings with default executor and completer.
     */
    public CommandSettings() {
        this.type = CommandType.PLUGIN_YML;
        this.executes = context -> true;
        this.completes = context -> List.of();
    }

    /**
     * Creates settings with the given executor and completer.
     *
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     */
    public CommandSettings(CommandDispatcher executes, SuggestionDispatcher completes) {
        this.type = CommandType.PLUGIN_YML;
        this.executes = executes;
        this.completes = completes;
    }

    /**
     * Creates settings with the given type, executor and completer.
     *
     * @param type The command type
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     */
    public CommandSettings(CommandType type, CommandDispatcher executes, SuggestionDispatcher completes) {
        this.type = type;
        this.executes = executes;
        this.completes = completes;
    }

    /**
     * Gets the default command settings.
     *
     * @return The default settings
     */
    public static @NotNull CommandSettings settings() {
        return new CommandSettings();
    }

    /**
     * Gets settings with the given executor and completer.
     *
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     * @return The settings
     */
    public static @NotNull CommandSettings settings(@Nullable CommandDispatcher executes, @Nullable SuggestionDispatcher completes) {
        return new CommandSettings(executes, completes);
    }

    /**
     * Gets settings with the given type, executor and completer.
     *
     * @param type The command type
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     * @return The settings
     */
    public static @NotNull CommandSettings settings(@NotNull CommandType type, @Nullable CommandDispatcher executes, @Nullable SuggestionDispatcher completes) {
        return new CommandSettings(type, executes, completes);
    }

    /**
     * Gets the execute dispatcher.
     *
     * @return The dispatcher
     */
    public @NotNull CommandDispatcher executes() {
        return executes;
    }

    /**
     * Sets the execute dispatcher.
     *
     * @param executes The new dispatcher
     * @return This settings instance
     */
    public @NotNull CommandSettings executes(@Nullable CommandDispatcher executes) {
        this.executes = executes;
        return this;
    }

    /**
     * Gets the complete dispatcher.
     *
     * @return The dispatcher
     */
    public @Nullable SuggestionDispatcher completes() {
        return completes;
    }

    /**
     * Sets the complete dispatcher.
     *
     * @param completes The new dispatcher
     * @return This settings instance  
     */
    public @NotNull CommandSettings completes(@Nullable SuggestionDispatcher completes) {
        this.completes = completes;
        return this;
    }

    /**
     * Gets the command type.
     *
     * @return The type
     */
    public @NotNull CommandType type() {
        return type;
    }

    /**
     * Sets the command type.
     *
     * @param type The new type
     * @return This settings instance
     */
    public @NotNull CommandSettings type(@NotNull CommandType type) {
        this.type = type;
        return this;
    }
}