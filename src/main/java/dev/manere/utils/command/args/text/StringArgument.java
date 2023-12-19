package dev.manere.utils.command.args.text;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringArgument implements CustomArgument<String> {
    public static StringArgument arg() {
        return new StringArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable String parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        return arg;
    }
}
