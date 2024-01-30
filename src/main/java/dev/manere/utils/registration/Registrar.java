package dev.manere.utils.registration;

import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.impl.CommandResultWrapper;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.CommandsRegistrar;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import dev.manere.utils.library.Utils;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A utility class for registering various components in a Bukkit plugin.
 */
public class Registrar {
    /**
     * Registers a listener with the provided plugin.
     *
     * @param plugin   The JavaPlugin instance.
     * @param listener The listener to register.
     */
    public static void events(@NotNull JavaPlugin plugin, @NotNull Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    /**
     * Registers a command executor with the provided plugin.
     *
     * @param plugin      The JavaPlugin instance.
     * @param commandName The name of the command.
     * @param executor    The CommandExecutor to be associated with the command.
     * @throws NullPointerException if plugin or executor is null.
     */
    public static void command(@NotNull JavaPlugin plugin, @NotNull String commandName, @NotNull CommandExecutor executor) {
        Objects.requireNonNull(plugin, "Plugin cannot be null");
        Objects.requireNonNull(executor, "CommandExecutor cannot be null");

        Objects.requireNonNull(plugin.getCommand(commandName)).setExecutor(executor);
    }

    /**
     * Retrieves the CommandMap associated with the provided plugin.
     *
     * @param plugin The JavaPlugin instance.
     * @return The CommandMap object, or null if it couldn't be retrieved.
     */
    public static @NotNull CommandMap commandMap(@NotNull JavaPlugin plugin) {
        return plugin.getServer().getCommandMap();
    }

    /**
     * Registers a custom command with the CommandMap associated with the provided plugin.
     *
     * @param plugin      The JavaPlugin instance.
     * @param commandName The name of the command.
     * @param command     The Command object to register.
     */
    public static void commandMap(@NotNull JavaPlugin plugin, @NotNull String commandName, @NotNull Command command) {
        Objects.requireNonNull(commandMap(plugin)).register(plugin.getName(), command);
    }

    /**
     * Registers a listener with the plugin obtained from Utils.
     *
     * @param listener The listener to register.
     */
    public static void events(@NotNull Listener listener) {
        events(Utils.plugin(), listener);
    }

    /**
     * Registers a command executor with the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param executor The CommandExecutor to associate with the command.
     * @throws NullPointerException if executor is null.
     */
    public static void command(@NotNull String commandName, @NotNull CommandExecutor executor) {
        command(Utils.plugin(), commandName, executor);
    }

    /**
     * Retrieves the CommandMap associated with the plugin obtained from Utils.
     *
     * @return The CommandMap object, or null if unable to retrieve.
     */
    public static @NotNull CommandMap commandMap() {
        return commandMap(Utils.plugin());
    }

    /**
     * Registers a custom command with the CommandMap of the plugin obtained from Utils.
     *
     * @param commandName The name of the command.
     * @param command The Command object to register.
     */
    public static void commandMap(@NotNull String commandName, @NotNull Command command) {
        commandMap(Utils.plugin(), commandName, command);
    }

    @SuppressWarnings("DataFlowIssue")
    public static void commandMap(CommandsRegistrar handler) {
        Commands builder = handler.commandBuilder();

        Command command = new Command(builder.name(), builder.description(), builder.usage(), builder.aliases().aliases()) {

            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
                CommandContext ctx = CommandContext.context(sender, this, label, args);
                ctx.args().clear();

                for (Argument<?> arg : builder.args()) ctx.args().add(arg);
                for (Predicate<CommandContext> filter : builder.requirements()) if (filter.test(ctx)) return true;

                return CommandResultWrapper.unwrap(builder.executes().run(ctx));
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
                CommandContext ctx = CommandContext.context(sender, this, builder.name(), args);

                for (Argument<?> arg : builder.args()) {
                    if (arg.suggestions() == null || ctx.argPos(arg.identifier()) == -1 || ctx.args().size() != ctx.argPos(arg.identifier())) continue;
                    if (arg.suggestions().suggest(ctx) == null) return List.of();

                    return arg.suggestions().suggest(ctx).unwrap();
                }

                Suggestions suggestions = builder.completes().suggest(ctx);

                if (suggestions == null) return List.of();
                return suggestions.unwrap();
            }
        };

        command.setName(builder.bukkitCommand().getName());
        command.setAliases(builder.bukkitCommand().getAliases());
        command.setDescription(builder.bukkitCommand().getDescription());

        if (builder.bukkitCommand().getPermission() != null) {
            command.setPermission(builder.bukkitCommand().getPermission());
        }

        command.setUsage(builder.bukkitCommand().getUsage());

        if (builder.bukkitCommand().permissionMessage() != null) {
            command.permissionMessage(builder.bukkitCommand().permissionMessage());
        }

        if (CommodoreProvider.isSupported()) {
            if (handler.brigadier()) {
                handler.brigadierRegister(Utils.plugin());
            }
        }

        Registrar.commandMap().register(builder.bukkitCommand().getName(), builder.namespace(), command);
    }
}
