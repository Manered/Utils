package dev.manere.utils.command.builder;

import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.library.Utils;
import dev.manere.utils.registration.Registrar;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

/**
 * Class related to finally registering a CommandBuilder.
 * @param commandBuilder The command builder to handle.
 */
public record CommandBuilderHandler(@NotNull CommandBuilder commandBuilder) {

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
    public void register(@NotNull JavaPlugin plugin) {
        switch (commandBuilder.type()) {
            case PLUGIN_YML -> {
                if (plugin.getCommand(commandBuilder.name()) == null) throw new NullPointerException(
                        "The plugin.yml file is missing a definition for the command you are trying to register." +
                                " Add the command definition to your plugin.yml to fix this."
                );
                if (commandBuilder.executes() == null) throw new NullPointerException(
                        "The command you are trying to register does not have a CommandContext/CommandExecutor set."
                );

                plugin.getCommand(commandBuilder.name()).setExecutor((sender, command, label, rawArgs) -> {
                    CommandContext ctx = CommandContext.context(sender, command, label, rawArgs);
                    ctx.args().clear();

                    for (Argument<?> arg : commandBuilder.args()) ctx.args().add(arg);
                    for (Predicate<CommandContext> filter : commandBuilder.requirements()) if (filter.test(ctx)) return true;

                    return commandBuilder.executes().run(ctx);
                });

                if (commandBuilder.completes() != null) {
                    plugin.getCommand(commandBuilder.name()).setTabCompleter((sender, command, label, rawArgs) -> {
                        CommandContext ctx = CommandContext.context(sender, command, label, rawArgs);

                        for (Argument<?> arg : commandBuilder.args()) {
                            if (arg.suggestions() == null || ctx.argPos(arg.identifier()) == -1 || ctx.args().size() != ctx.argPos(arg.identifier())) continue;
                            if (arg.suggestions().suggest(ctx) == null) return List.of();

                            return arg.suggestions().suggest(ctx);
                        }

                        if (commandBuilder.completes().suggest(ctx) == null) return List.of();
                        return commandBuilder.completes().suggest(ctx);
                    });
                }
            }

            case COMMAND_MAP -> Registrar.commandMap(this);
        }
    }
}
