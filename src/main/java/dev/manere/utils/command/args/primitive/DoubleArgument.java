package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DoubleArgument implements CustomArgument<Double> {
    public static DoubleArgument arg() {
        return new DoubleArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Double parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        try {
            return Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid Double/Number provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
