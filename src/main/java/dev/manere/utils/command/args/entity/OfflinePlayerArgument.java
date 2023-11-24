package dev.manere.utils.command.args.entity;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.server.ServerUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OfflinePlayerArgument implements CustomArgument<OfflinePlayer> {
    public static OfflinePlayerArgument arg() {
        return new OfflinePlayerArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable OfflinePlayer parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        return ServerUtils.server().getOfflinePlayer(arg);
    }
}
