package dev.manere.utils.command.args.text;

import dev.manere.utils.command.args.custom.CustomListArgument;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TextListArgument extends CustomListArgument<Text> {
    public static TextListArgument arg() {
        return new TextListArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Text parse(@Nullable List<String> args) throws ArgumentParseException {
        if (args == null || args.isEmpty()) {
            throw new ArgumentParseException("CustomArgument list cannot be null or empty", ArgumentExType.ARG_IS_NULL);
        }

        return Text.text(String.join(" ", args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Text parse(@NotNull CommandContext ctx, int indexStart) throws ArgumentParseException {
        List<String> rawArgs = ctx.rawArgs();

        if (indexStart < 0 || indexStart >= rawArgs.size()) {
            throw new ArgumentParseException("Invalid indexStart value", ArgumentExType.INVALID_SYNTAX);
        }

        return parse(rawArgs.subList(indexStart, rawArgs.size()));
    }
}
