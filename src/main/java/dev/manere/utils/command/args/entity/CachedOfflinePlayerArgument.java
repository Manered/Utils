package dev.manere.utils.command.args.entity;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.server.ServerUtils;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CachedOfflinePlayerArgument implements CustomArgument<OfflinePlayer> {
    public static CachedOfflinePlayerArgument arg() {
        return new CachedOfflinePlayerArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable OfflinePlayer parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        OfflinePlayer player = ServerUtils.server().getOfflinePlayerIfCached(arg);

        if (player == null) {
            throw new ArgumentParseException("OfflinePlayer not cached or doesn't exist.", ArgumentExType.RETURNS_NULL);
        }

        return player;
    }
}
