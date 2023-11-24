package dev.manere.utils.command.args.misc;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GamemodeArgument implements CustomArgument<GameMode> {
    public static GamemodeArgument arg() {
        return new GamemodeArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable GameMode parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        switch (arg.toLowerCase()) {
            case "creative" -> {
                return GameMode.CREATIVE;
            }
            case "spectator" -> {
                return GameMode.SPECTATOR;
            }
            case "survival" -> {
                return GameMode.SURVIVAL;
            }
            case "adventure" -> {
                return GameMode.ADVENTURE;
            }
            default -> throw new ArgumentParseException("CustomArgument can only be creative|spectator|survival|adventure", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
