package dev.manere.utils.command;

import dev.manere.utils.command.arguments.CommandArgument;
import dev.manere.utils.library.Utils;
import dev.manere.utils.registration.RegistrationUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A utility class for building and configuring Bukkit plugin commands.
 */
public class CommandBuilder {
    private String name;
    private final PluginCommand command;
    private String shortHelpDescription;
    private final List<CommandArgument<?>> arguments = new ArrayList<>();

    /**
     * Creates a new CommandBuilder for the specified command name.
     *
     * @param name The name of the command.
     * @throws IllegalArgumentException If the specified command name is not found.
     */
    public CommandBuilder(String name) {
        this.name = name;

        command = Utils.getPlugin().getCommand(name);
        if (command == null) throw new IllegalArgumentException("Command not found: " + name);
    }

    /**
     * Creates a new CommandBuilder instance.
     *
     * @return A new CommandBuilder instance.
     */
    public static CommandBuilder of(String name) {
        return new CommandBuilder(name);
    }

    /**
     * Sets the permission required to execute the command.
     *
     * @param permission The permission node.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setPermission(String permission) {
        command.setPermission(permission);
        return this;
    }

    /**
     * Retrieves the name of the command.
     *
     * @return The name of the command.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the command.
     *
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Retrieves the PluginCommand variable.
     *
     * @return The PluginCommand variable.
     */
    public PluginCommand getCommand() {
        return command;
    }

    /**
     * Sets the arguments for the command.
     *
     * @param arguments The command arguments.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setArguments(CommandArgument<?>... arguments) {
        this.arguments.addAll(Arrays.asList(arguments));
        return this;
    }

    /**
     *
     * Retrieves the list of command arguments.
     *
     * @return The list of command arguments.
     */
    public List<CommandArgument<?>> getArguments() {
        return arguments;
    }

    /**
     * Sets the usage message for the command.
     *
     * @param usage The usage message.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setUsage(String usage) {
        command.setUsage(usage);
        return this;
    }

    /**
     * Sets the description of the command.
     *
     * @param description The description of the command.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setDescription(String description) {
        command.setDescription(description);
        return this;
    }

    /**
     * Sets a short help description for the command.
     *
     * @param shortHelpDescription The short help description.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setShortHelpDescription(String shortHelpDescription) {
        if (!(Objects.equals(this.shortHelpDescription, shortHelpDescription)))
            this.shortHelpDescription = shortHelpDescription;

        return this;
    }

    /**
     * Sets the message sent to players without the required permission.
     *
     * @param permissionMessage The permission message.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setPermissionMessage(String permissionMessage) {
        command.setPermissionMessage(permissionMessage);
        return this;
    }

    /**
     * Sets the aliases for the command.
     *
     * @param aliases The command aliases.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setAliases(String... aliases) {
        command.setAliases(Arrays.asList(aliases));
        return this;
    }

    /**
     * Sets a TabCompleter for the command.
     *
     * @param tabCompleter The TabCompleter to be used.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setTabCompleter(TabCompleter tabCompleter) {
        command.setTabCompleter(tabCompleter);
        return this;
    }

    /**
     * Adds an alias to the command.
     *
     * @param alias The alias to add.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder addAlias(String alias) {
        command.getAliases().add(alias);
        return this;
    }

    /**
     * Sets the executor for the command.
     *
     * @param executor The CommandExecutor to be used.
     * @return This CommandBuilder for method chaining.
     */
    public CommandBuilder setExecutor(CommandExecutor executor) {
        command.setExecutor(executor);
        return this;
    }

    /**
     * Builds and configures the command.
     */
    public void build(boolean useCommandMap, boolean shouldTabComplete) {
        if (useCommandMap) RegistrationUtils.registerCommandMap(Utils.getPlugin(), this);

        else Utils.getPlugin().getCommand(command.getName()).setExecutor(command.getExecutor());

        if (shouldTabComplete) {
            Utils.getPlugin().getCommand(command.getName()).setTabCompleter(command.getTabCompleter());
        }
    }
}
