package dev.manere.utils.command;

import dev.manere.utils.command.annotation.AutoRegister;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.dispatcher.CommandDispatcher;
import dev.manere.utils.command.impl.dispatcher.SuggestionDispatcher;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Bukkit-like wrapper for a command, uses {@link Commands}.
 * @see Commands
 * @see AutoRegister
 */
public abstract class Commander implements CommandDispatcher, SuggestionDispatcher {
    private CommandConfig settings;
    private String name;
    private final List<String> aliases = new ArrayList<>();
    private String description;
    private String permission;
    private String usage;

    /**
     * Constructs a new Commander instance.
     * @param name The name of the command to use for registration.
     */
    public Commander(String name) {
        this.settings = CommandConfig.settings();
        this.name = name;
    }

    /**
     * Gets the settings for this command.
     *
     * @return The command settings
     */
    public @NotNull CommandConfig settings() {
        return settings;
    }

    /**
     * Gets the name of this command.
     *
     * @return The command name
     */
    public @NotNull String name() {
        return name;
    }

    /**
     * Sets the settings for this command.
     *
     * @param settings The new settings
     * @return This commander instance
     */
    public @NotNull Commander settings(@NotNull CommandConfig settings) {
        this.settings = settings;
        return this;
    }

    /**
     * Sets the name for this command.
     *
     * @param name The new name
     * @return This commander instance
     */
    public @NotNull Commander name(@NotNull String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the description for this command.
     *
     * @param description The description
     * @return This commander instance
     */
    public @NotNull Commander description(@NotNull String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the required permission for this command.
     *
     * @param permission The permission
     * @return This commander instance
     */
    public @NotNull Commander permission(@NotNull String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Sets the usage info for this command.
     *
     * @param usage The usage info
     * @return This commander instance
     */
    public @NotNull Commander usage(@NotNull String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Gets the aliases for this command.
     *
     * @return The aliases
     */
    public @NotNull List<String> aliases() {
        return aliases;
    }

    /**
     * Gets the description for this command.
     *
     * @return The description
     */
    public @NotNull String description() {
        return description;
    }

    /**
     * Gets the required permission for this command.
     *
     * @return The permission
     */
    public @NotNull String permission() {
        return permission;
    }

    /**
     * Gets the usage info for this command.
     *
     * @return The usage info
     */
    public @NotNull String usage() {
        return usage;
    }
}
