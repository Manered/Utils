package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanArgument implements CustomArgument<Boolean> {
    public static BooleanArgument arg() {
        return new BooleanArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Boolean parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(arg);
        } else {
            throw new ArgumentParseException("Invalid Boolean (true|false) provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
