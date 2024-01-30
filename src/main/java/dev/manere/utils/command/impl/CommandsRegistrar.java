package dev.manere.utils.command.impl;

import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.manere.utils.command.CommandTypes;
import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.library.Utils;
import dev.manere.utils.registration.Registrar;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class related to finally registering a Commands.
 */
public final class CommandsRegistrar {
    private final @NotNull Commands commandBuilder;
    private boolean brigadier;
    private String commodorePath;

    /**
     * @param commandBuilder The command builder to handle.
     */
    public CommandsRegistrar(@NotNull Commands commandBuilder) {
        this.commandBuilder = commandBuilder;
        this.commodorePath = commandBuilder.name() + ".commodore";
        this.brigadier = false;
    }

    /**
     * Attempts to register the command
     * with the plugin obtained by {@link Utils#plugin()}
     */
    public void register() {
        register(Utils.plugin());
    }

    /**
     * Enables Brigadier Tab Completions for the command.
     * @return This CommandsRegistrar instance for method chaining.
     */
    public @NotNull CommandsRegistrar enableBrigadier() {
        this.brigadier = true;
        return this;
    }

    /**
     * Sets the path of the command's commodore file.
     * This should return for example staffchat.commodore
     * since it's going to be searched for in your jar file.
     * @param commodorePath The path of the command's commodore file.
     * @return This CommandsRegistrar instance for method chaining.
     */
    public @NotNull CommandsRegistrar commodorePath(@NotNull String commodorePath) {
        this.commodorePath = commodorePath;
        return this;
    }

    /**
     * Attempts to register the command
     *
     * @param plugin the plugin that is used to register the command, required.
     */
    @SuppressWarnings("DataFlowIssue")
    public void register(@NotNull JavaPlugin plugin) {
        switch (commandBuilder.type().ordinal()) {
            case 0 -> {
                if (plugin.getCommand(commandBuilder.name()) == null) throw new NullPointerException(
                        "The plugin.yml file is missing a definition for the command" +
                                " you are trying to register." +
                                " Add the command definition to your plugin.yml to fix this."
                );
                if (commandBuilder.executes() == null) throw new NullPointerException(
                        "The command you are trying to register does not have" +
                                " a CommandContext/CommandExecutor set."
                );

                plugin.getCommand(commandBuilder.name()).setExecutor((s, c, l, rawA) -> {
                    CommandContext ctx = CommandContext.context(s, c, l, rawA);
                    ctx.args().clear();

                    for (Argument<?> arg : commandBuilder.args()) ctx.args().add(arg);

                    for (Predicate<CommandContext> filter : commandBuilder.requirements())
                        if (filter.test(ctx)) return true;

                    return CommandResultWrapper.unwrap(commandBuilder.executes().run(ctx));
                });

                if (commandBuilder.completes() != null) {
                    plugin.getCommand(commandBuilder.name()).setTabCompleter((s, c, l, rawA) -> {
                        CommandContext ctx = CommandContext.context(s, c, l, rawA);

                        for (Argument<?> arg : commandBuilder.args()) {
                            if (arg.suggestions() == null
                                    || ctx.argPos(arg.identifier()) == -1
                                    || ctx.args().size() != ctx.argPos(arg.identifier())
                            ) continue;
                            if (arg.suggestions().suggest(ctx) == null) return List.of();

                            return arg.suggestions().suggest(ctx).unwrap();
                        }

                        if (commandBuilder.completes().suggest(ctx) == null) return List.of();
                        return commandBuilder.completes().suggest(ctx).unwrap();
                    });
                } else {
                    plugin.getCommand(commandBuilder.name()).setTabCompleter(
                            (sender, command, label, args) -> new ArrayList<>()
                    );
                }

                if (CommodoreProvider.isSupported() && brigadier) brigadierRegister(plugin);
            }

            case 1 -> Registrar.commandMap(this);
        }
    }

    /**
     * This is meant to be called internally.
     * Please avoid calling this if you don't know what you're doing!
     */
    public void brigadierRegister(@NotNull JavaPlugin plugin) {
        try (InputStream inputStream = plugin.getResource(commodorePath)) {
            if (inputStream == null) {
                throw new NullPointerException("Brigadier command data missing from jar");
            }

            LiteralCommandNode<?> commandNode = CommodoreFileReader.INSTANCE.parse(inputStream);

            if (commandBuilder().type().ordinal() == CommandTypes.plugin().ordinal()) {
                PluginCommand pluginCommand = plugin.getCommand(commandBuilder().name());

                CommodoreProvider.getCommodore(plugin).register(pluginCommand, commandNode, player -> {
                    if (pluginCommand.getPermission() != null) {
                        return player.hasPermission(pluginCommand.getPermission());
                    }

                    return true;
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the command builder associated with this class.
     * @return the command builder associated with this class.
     */
    public @NotNull Commands commandBuilder() {
        return commandBuilder;
    }

    /**
     * Returns if the command is using brigadier tab completions.
     * @return true if the command is using brigadier tab completions, otherwise false.
     */
    public boolean brigadier() {
        return brigadier;
    }
}
