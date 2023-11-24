package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloatArgument implements CustomArgument<Float> {
    public static FloatArgument arg() {
        return new FloatArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Float parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        try {
            return Float.parseFloat(arg);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid Float/Number provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
