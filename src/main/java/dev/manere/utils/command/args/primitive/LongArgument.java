package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LongArgument implements CustomArgument<Long> {
    public static LongArgument arg() {
        return new LongArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Long parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        try {
            return Long.parseLong(arg);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid Long/Number provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
