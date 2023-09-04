package dev.manere.utils.command;

import dev.manere.utils.library.Utils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CommandBuilder {
    private final PluginCommand command;
    private String shortHelpDescription;

    public CommandBuilder(JavaPlugin plugin, String name) {
        command = plugin.getCommand(name);
        if (command == null) {
            throw new IllegalArgumentException("Command not found: " + name);
        }
    }

    public CommandBuilder setPermission(String permission) {
        command.setPermission(permission);
        return this;
    }

    public CommandBuilder setUsage(String usage) {
        command.setUsage(usage);
        return this;
    }

    public CommandBuilder setDescription(String description) {
        command.setDescription(description);
        return this;
    }

    public CommandBuilder setShortHelpDescription(String shortHelpDescription) {
        this.shortHelpDescription = shortHelpDescription;
        return this;
    }

    public CommandBuilder setPermissionMessage(String permissionMessage) {
        command.setPermissionMessage(permissionMessage);
        return this;
    }

    public CommandBuilder setAliases(String... aliases) {
        command.setAliases(java.util.Arrays.asList(aliases));
        return this;
    }

    public CommandBuilder setTabCompleter(TabCompleter tabCompleter) {
        command.setTabCompleter(tabCompleter);
        return this;
    }

    public CommandBuilder addAlias(String alias) {
        command.getAliases().add(alias);
        return this;
    }

    public CommandBuilder setExecutor(CommandExecutor executor) {
        command.setExecutor(executor);
        return this;
    }

    public CommandBuilder build() {
        if (!command.isRegistered()) {
            try {
                Field bukkitCommandMap = Utils.getPlugin().getServer().getClass().getDeclaredField("commandMap");

                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Utils.getPlugin().getServer());

                if (commandMap.getCommand(command.getName()) == null) {
                    commandMap.register(command.getName(), command);

                    if (shortHelpDescription != null) {
                        Utils.getPlugin().getServer().getHelpMap().getHelpTopic(command.getName()).amendTopic(shortHelpDescription, command.getDescription());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }
}
