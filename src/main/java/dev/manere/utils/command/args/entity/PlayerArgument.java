package dev.manere.utils.command.args.entity;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.player.PlayerUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerArgument implements CustomArgument<Player> {
    public static PlayerArgument arg() {
        return new PlayerArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Player parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        Player player = PlayerUtils.player(arg);

        if (player == null) {
            throw new ArgumentParseException("Player doesn't exist.", ArgumentExType.RETURNS_NULL);
        }

        return player;
    }
}
