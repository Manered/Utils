package dev.manere.utils.command;

import dev.manere.utils.command.builder.dispatcher.CommandDispatcher;
import dev.manere.utils.command.builder.dispatcher.SuggestionDispatcher;
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
        this.completes = context -> null;
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
    public static CommandSettings settings() {
        return new CommandSettings();
    }

    /**
     * Gets settings with the given executor and completer.
     *
     * @param executes The execute dispatcher
     * @param completes The complete dispatcher
     * @return The settings
     */
    public static CommandSettings settings(CommandDispatcher executes, SuggestionDispatcher completes) {
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
    public static CommandSettings settings(CommandType type, CommandDispatcher executes, SuggestionDispatcher completes) {
        return new CommandSettings(type, executes, completes);
    }

    /**
     * Gets the execute dispatcher.
     *
     * @return The dispatcher
     */
    public CommandDispatcher executes() {
        return executes;
    }

    /**
     * Sets the execute dispatcher.
     *
     * @param executes The new dispatcher
     * @return This settings instance
     */
    public CommandSettings executes(CommandDispatcher executes) {
        this.executes = executes;
        return this;
    }

    /**
     * Gets the complete dispatcher.
     *
     * @return The dispatcher
     */
    public SuggestionDispatcher completes() {
        return completes;
    }

    /**
     * Sets the complete dispatcher.
     *
     * @param completes The new dispatcher
     * @return This settings instance  
     */
    public CommandSettings completes(SuggestionDispatcher completes) {
        this.completes = completes;
        return this;
    }

    /**
     * Gets the command type.
     *
     * @return The type
     */
    public CommandType type() {
        return type;
    }

    /**
     * Sets the command type.
     *
     * @param type The new type
     * @return This settings instance
     */
    public CommandSettings type(CommandType type) {
        this.type = type;
        return this;
    }

}