package dev.manere.utils.command.args.entity;

import dev.manere.utils.command.args.exception.ArgumentParseException;
import dev.manere.utils.command.args.exception.ArgumentExType;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.builder.dispatcher.CommandContext;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityTypeArgument implements CustomArgument<EntityType> {
    public static EntityTypeArgument arg() {
        return new EntityTypeArgument();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    @Override
    public @Nullable EntityType parse(@NotNull CommandContext ctx, @Nullable String arg) throws ArgumentParseException {
        if (arg == null) {
            throw new ArgumentParseException("CustomArgument cannot be null", ArgumentExType.ARG_IS_NULL);
        }

        EntityType type = EntityType.fromName(arg.toLowerCase());

        if (type == null) {
            throw new ArgumentParseException("EntityType is null", ArgumentExType.INVALID_SYNTAX);
        }

        return type;
    }
}
