package dev.manere.utils.command.args.text;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextArgument implements CustomArgument<Text> {
    public static TextArgument arg() {
        return new TextArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Text parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        return Text.text(arg);
    }
}
