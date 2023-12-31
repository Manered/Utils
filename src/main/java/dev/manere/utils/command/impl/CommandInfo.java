package dev.manere.utils.command.impl;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Represents metadata information for a command.
 */
public class CommandInfo {
    final @NotNull Commands builder;
    private @Nullable String namespace;
    private @Nullable String description;
    private @Nullable String permission;
    private @Nullable Component permissionMessage;
    private @Nullable String usage;
    private @Nullable List<String> aliases;

    /**
     * Constructs a new CommandInfo associated with the given Commands builder.
     *
     * @param builder The Commands builder to associate with this command information.
     */
    public CommandInfo(@NotNull Commands builder) {
        this.builder = builder;
    }

    /**
     * Gets the namespace of the command.
     *
     * @return The namespace of the command.
     */
    public String namespace() {
        return namespace;
    }

    /**
     * Sets the namespace of the command.
     *
     * @param namespace The namespace to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Gets the description of the command.
     *
     * @return The description of the command.
     */
    public String description() {
        return description;
    }

    /**
     * Sets the description of the command.
     *
     * @param description The description to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Gets the permission required to execute the command.
     *
     * @return The permission required to execute the command.
     */
    public String permission() {
        return permission;
    }

    /**
     * Sets the permission required to execute the command.
     *
     * @param permission The permission to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo permission(String permission) {
        this.permission = permission;
        return this;
    }

    /**
     * Sets the permission required to execute the command with a custom prefix and suffix.
     *
     * @param prefix The prefix to use.
     * @param suffix The suffix to use.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo permission(String prefix, String suffix) {
        this.permission = prefix + "." + suffix;
        return this;
    }

    /**
     * Gets the message sent to a user lacking the required permission to execute the command.
     *
     * @return The permission message.
     */
    public Component permissionMessage() {
        return permissionMessage;
    }

    /**
     * Sets the message sent to a user lacking the required permission to execute the command.
     *
     * @param permissionMessage The permission message to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo permissionMessage(Component permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    /**
     * Gets the usage syntax of the command.
     *
     * @return The usage syntax of the command.
     */
    public String usage() {
        return usage;
    }

    /**
     * Sets the usage syntax of the command.
     *
     * @param usage The usage syntax to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo usage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Gets the aliases (alternative names) for the command.
     *
     * @return The list of aliases.
     */
    public List<String> aliases() {
        return aliases;
    }

    /**
     * Sets the aliases (alternative names) for the command.
     *
     * @param aliases The list of aliases to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo aliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * Sets the aliases (alternative names) for the command using varargs.
     *
     * @param aliases The aliases to set.
     * @return This CommandInfo instance for method chaining.
     */
    public CommandInfo aliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }
}
