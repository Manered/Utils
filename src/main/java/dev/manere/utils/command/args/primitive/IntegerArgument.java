package dev.manere.utils.command.args.primitive;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.impl.dispatcher.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IntegerArgument implements CustomArgument<Integer> {
    private final @Nullable Integer max;
    private final @Nullable Integer min;

    public IntegerArgument(int min, int max) {
        this.max = max;
        this.min = min;
    }

    public IntegerArgument() {
        this.min = null;
        this.max = null;
    }

    public static IntegerArgument arg() {
        return new IntegerArgument();
    }

    public static IntegerArgument arg(@Nullable Integer min, @Nullable Integer max) {
        return new IntegerArgument(min, max);
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
            int value = Integer.parseInt(arg);

            if (min != null && value < min) {
                throw new ArgumentParseException("Value must be greater than " + min, ArgumentExType.INVALID_RANGE);
            }

            if (max != null && value > max) {
                throw new ArgumentParseException("Value must be less than " + max, ArgumentExType.INVALID_RANGE);
            }

            return value;
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid Integer/Number provided.", ArgumentExType.INVALID_SYNTAX);
        }
    }
}
