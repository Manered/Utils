package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerArgument implements CustomArgument<Integer> {
    public static IntegerArgument arg() {
        return new IntegerArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Integer parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid Integer/Number provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
