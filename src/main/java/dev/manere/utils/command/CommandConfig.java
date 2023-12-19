package dev.manere.utils.command;

import dev.manere.utils.command.impl.dispatcher.CommandDispatcher;
import dev.manere.utils.command.impl.dispatcher.SuggestionDispatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Class representing settings for a {@link Commander} command.
 */
public class CommandConfig {
    private CommandDispatcher executes;
    private SuggestionDispatcher completes;
    private CommandTypes type;

    /**
     * Creates settings with default executor and completer.
     */
    public CommandConfig() {
        this.type = CommandTypes.PLUGIN_YML;
        this.executes = context -> true;
        this.completes = context -> new ArrayList<>();
    }

    /**
     * Creates settings with the given executor and completer.
     *
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     */
    public CommandConfig(CommandDispatcher executes, SuggestionDispatcher completes) {
        this.type = CommandTypes.PLUGIN_YML;
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
    public CommandConfig(CommandTypes type, CommandDispatcher executes, SuggestionDispatcher completes) {
        this.type = type;
        this.executes = executes;
        this.completes = completes;
    }

    /**
     * Gets the default command settings.
     *
     * @return The default settings
     */
    public static @NotNull CommandConfig settings() {
        return new CommandConfig();
    }

    /**
     * Gets settings with the given executor and completer.
     *
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     * @return The settings
     */
    public static @NotNull CommandConfig settings(@Nullable CommandDispatcher executes, @Nullable SuggestionDispatcher completes) {
        return new CommandConfig(executes, completes);
    }

    /**
     * Gets settings with the given type, executor and completer.
     *
     * @param type The command type
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     * @return The settings
     */
    public static @NotNull CommandConfig settings(@NotNull CommandTypes type, @Nullable CommandDispatcher executes, @Nullable SuggestionDispatcher completes) {
        return new CommandConfig(type, executes, completes);
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
    public @NotNull CommandConfig executes(@Nullable CommandDispatcher executes) {
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
    public @NotNull CommandConfig completes(@Nullable SuggestionDispatcher completes) {
        this.completes = completes;
        return this;
    }

    /**
     * Gets the command type.
     *
     * @return The type
     */
    public @NotNull CommandTypes type() {
        return type;
    }

    /**
     * Sets the command type.
     *
     * @param type The new type
     * @return This settings instance
     */
    public @NotNull CommandConfig type(@NotNull CommandTypes type) {
        this.type = type;
        return this;
    }
}