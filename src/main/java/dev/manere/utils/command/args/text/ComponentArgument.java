package dev.manere.utils.command.args.text;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComponentArgument implements CustomArgument<Component> {
    public static ComponentArgument arg() {
        return new ComponentArgument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Component parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        return TextStyle.style(arg);
    }
}
