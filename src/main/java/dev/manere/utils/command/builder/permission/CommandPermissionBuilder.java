package dev.manere.utils.command.builder.permission;

import dev.manere.utils.command.builder.CommandBuilder;
import net.kyori.adventure.text.Component;

/**
 * A builder class for setting custom permissions for a command associated with a CommandBuilder.
 */
public class CommandPermissionBuilder {
    private final CommandBuilder commandBuilder;
    private CommandPermission type;
    private String custom;
    private Component message;

    /**
     * Constructs a new CommandPermissionBuilder associated with the specified CommandBuilder.
     *
     * @param commandBuilder The CommandBuilder associated with this CommandPermissionBuilder.
     */
    public CommandPermissionBuilder(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    /**
     * Sets the type of the command permission.
     *
     * @param type The type of the command permission.
     * @return This CommandPermissionBuilder instance for method chaining.
     */
    public CommandPermissionBuilder type(CommandPermission type) {
        this.type = type;
        return this;
    }

    /**
     * Sets the custom message associated with the command permission.
     *
     * @param message The custom message for the command permission.
     * @return This CommandPermissionBuilder instance for method chaining.
     */
    public CommandPermissionBuilder message(Component message) {
        this.message = message;
        return this;
    }

    /**
     * Sets a custom permission string for the command.
     *
     * @param custom The custom permission string.
     * @return This CommandPermissionBuilder instance for method chaining.
     */
    public CommandPermissionBuilder custom(String custom) {
        this.custom = custom;
        return this;
    }

    /**
     * Gets the associated CommandBuilder.
     *
     * @return The CommandBuilder associated with this CommandPermissionBuilder.
     */
    public CommandBuilder commandBuilder() {
        return commandBuilder;
    }

    /**
     * Gets the type of the command permission.
     *
     * @return The type of the command permission.
     */
    public CommandPermission type() {
        return type;
    }

    /**
     * Gets the custom permission string.
     *
     * @return The custom permission string.
     */
    public String custom() {
        return custom;
    }

    /**
     * Gets the custom message for the command permission.
     *
     * @return The custom message for the command permission.
     */
    public Component message() {
        return message;
    }

    /**
     * Sets a custom permission string with a specified prefix and suffix.
     *
     * @param prefix The prefix for the custom permission string.
     * @param suffix The suffix for the custom permission string.
     * @return This CommandPermissionBuilder instance for method chaining.
     */
    public CommandPermissionBuilder custom(String prefix, String suffix) {
        this.custom = prefix + "." + suffix;
        return this;
    }

    /**
     * Builds and associates the command permission with the CommandBuilder.
     *
     * @return The CommandBuilder with the associated command permission.
     */
    public CommandBuilder build() {
        commandBuilder.permission(this);
        return commandBuilder;
    }
}
