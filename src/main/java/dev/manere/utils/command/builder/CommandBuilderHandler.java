package dev.manere.utils.command.builder;

import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.library.Utils;
import dev.manere.utils.registration.Registrar;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Class related to finally registering a CommandBuilder.
 * @param commandBuilder The command builder to handle.
 */
public record CommandBuilderHandler(CommandBuilder commandBuilder) {

    /**
     * Attempts to register the command
     * with the plugin obtained by {@link Utils#plugin()}
     */
    public void register() {
        register(Utils.plugin());
    }

    /**
     * Attempts to register the command
     *
     * @param plugin the plugin that is used to register the command, required.
     */
    @SuppressWarnings("DataFlowIssue")
    public void register(JavaPlugin plugin) {
        switch (commandBuilder.type()) {
            case PLUGIN_YML -> {
                if (plugin.getCommand(commandBuilder.name()) == null) throw new NullPointerException("The plugin.yml file is missing a definition for the command you are trying to register. Add the command definition to your plugin.yml to fix this.");

                if (commandBuilder.executes() == null) throw new NullPointerException("The command you are trying to register does not have a CommandExecutor set.");

                plugin.getCommand(commandBuilder.name()).setExecutor((sender, command, label, args) -> commandBuilder.executes().run(new CommandContext(sender, command, label, args)));

                if (commandBuilder.completes() != null) {
                    plugin.getCommand(commandBuilder.name()).setTabCompleter((sender, command, label, args) -> {
                        if (commandBuilder.completes().suggest(new CommandContext(sender, command, label, args)) == null) {
                            return List.of();
                        } else {
                            return commandBuilder.completes().suggest(new CommandContext(sender, command, label, args));
                        }
                    });
                }

            }

            case COMMAND_MAP -> Registrar.commandMap(Utils.plugin(), commandBuilder.name(), commandBuilder.command());
        }
    }
}
