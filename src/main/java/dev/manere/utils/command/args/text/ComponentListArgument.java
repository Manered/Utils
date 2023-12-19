package dev.manere.utils.command.args.text;

import dev.manere.utils.command.args.custom.CustomListArgument;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComponentListArgument extends CustomListArgument<Component> {
    public static ComponentListArgument arg() {
        return new ComponentListArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Component parse(@Nullable List<String> args) throws ArgumentParseException {
        if (args == null || args.isEmpty()) {
            throw new ArgumentParseException("CustomArgument list cannot be null or empty", ArgumentExType.ARG_IS_NULL);
        }

        return TextStyle.style(String.join(" ", args));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Component parse(@NotNull CommandContext ctx, int indexStart) throws ArgumentParseException {
        List<String> rawArgs = ctx.rawArgs();

        if (indexStart < 0 || indexStart >= rawArgs.size()) {
            throw new ArgumentParseException("Invalid indexStart value", ArgumentExType.INVALID_SYNTAX);
        }

        return parse(rawArgs.subList(indexStart, rawArgs.size()));
    }
}
