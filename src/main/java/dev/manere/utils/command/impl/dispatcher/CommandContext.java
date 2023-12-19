package dev.manere.utils.command.impl.dispatcher;

import dev.manere.utils.command.args.Argument;
import dev.manere.utils.command.args.custom.CustomArgument;
import dev.manere.utils.command.args.custom.CustomListArgument;
import dev.manere.utils.command.args.exception.ArgumentParseException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandContext {
    private final CommandSender sender;
    private final Command command;
    private final String name;
    private final List<String> rawArgs;
    private final List<Argument<?>> args;

    /**
     * Constructs a new CommandContext with the specified sender, command, name, and arguments.
     *
     * @param sender  The CommandSender who executed the command.
     * @param command The Command object associated with this context.
     * @param name    The name of the command.
     * @param rawArgs    A list of arguments provided with the command.
     */
    public CommandContext(@NotNull CommandSender sender, @NotNull Command command, @NotNull String name, @NotNull String[] rawArgs) {
        this.sender = sender;
        this.command = command;
        this.name = name;
        this.rawArgs = Arrays.stream(rawArgs).toList();
        this.args = new ArrayList<>();
    }

    /**
     * Constructs a new CommandContext with the specified sender, command, name, and arguments.
     *
     * @param sender  The CommandSender who executed the command.
     * @param command The Command object associated with this context.
     * @param name    The name of the command.
     * @param rawArgs    A list of arguments provided with the command.
     */
    public static @NotNull CommandContext context(@NotNull CommandSender sender, @NotNull Command command, @NotNull String name, @NotNull String[] rawArgs) {
        return new CommandContext(sender, command, name, rawArgs);
    }

    /**
     * Returns the CommandSender who executed the command.
     *
     * @return The CommandSender associated with this context.
     */
    public @NotNull CommandSender sender() {
        return sender;
    }

    /**
     * Returns the Player who executed the command. Assumes the sender is a Player.
     *
     * @return The Player associated with this context.
     */
    public @NotNull Player player() {
        return (Player) sender;
    }

    /**
     * Returns true if the CommandSender is an instanceof a Player, else false.
     *
     * @return if the CommandSender is an instanceof a Player.
     */
    public boolean senderInstanceOfPlayer() {
        return sender() instanceof Player;
    }

    /**
     * Returns true if the CommandSender is an instanceof a Player, else false.
     *
     * @return if the CommandSender is an instanceof a Player.
     */
    public boolean senderIsPlayer() {
        return senderInstanceOfPlayer();
    }

    /**
     * Returns the Command object associated with this context.
     *
     * @return The Command object.
     */
    public @NotNull Command command() {
        return command;
    }

    /**
     * Returns the Command object associated with this context.
     *
     * @return The Command object.
     */
    public @NotNull Command cmd() {
        return command;
    }

    /**
     * Returns the name of the command.
     *
     * @return The name of the command.
     */
    public @NotNull String name() {
        return name;
    }

    /**
     * Returns the alias of the command (which is the same as the name in this context).
     *
     * @return The alias of the command.
     */
    public @NotNull String alias() {
        return name();
    }

    /**
     * Returns the name of the command (which is the same as the alias in this context).
     *
     * @return The name of the command that was executed.
     */
    public @NotNull String commandRan() {
        return alias();
    }

    /**
     * Returns a list of arguments provided with the command.
     *
     * @return A list of arguments.
     */
    public @NotNull List<String> rawArgs() {
        return rawArgs;
    }

    /**
     * Returns the argument at the specified index.
     *
     * @param index The index of the argument.
     * @return The argument at the specified index.
     */
    public @Nullable String rawArgAt(int index) {
        return rawArgs.get(index);
    }

    /**
     * Returns the argument type at the specified index.
     *
     * @param index The index of the argument.
     * @return The argument type at the specified index.
     */
    public @Nullable CustomArgument<?> argTypeAt(int index) {
        return args.get(index).type();
    }

    /**
     * Returns the argument type of the identifier.
     *
     * @param identifier The identifier of the argument.
     * @return The argument type of the identifier.
     */
    public @Nullable CustomArgument<?> argTypeAt(@NotNull String identifier) {
        for (Argument<?> arg : args) {
            if (arg.identifier().equalsIgnoreCase(identifier)) {
                return args.get(args.indexOf(arg)).type();
            }
        }

        return null;
    }

    /**
     * Returns the argument of the identifier.
     *
     * @param identifier The identifier of the argument.
     * @return The argument at the specified index.
     */
    public @Nullable Object argAt(@NotNull String identifier) {
        for (Argument<?> arg : args) {
            if (arg.identifier().equalsIgnoreCase(identifier)) {
                return argAt(args.indexOf(arg));
            }
        }

        return null;
    }

    /**
     * Returns the argument at the specified index.
     *
     * @param index The index of the argument.
     * @return The argument at the specified index.
     */
    public @Nullable Object argAt(int index) {
        Argument<?> argument = args.get(index);

        if (argument == null || rawArgs == null || rawArgs.isEmpty()) return null;

        if (rawArgs.size() <= index || rawArgAt(index) == null) {
            try {
                if (argument.type() instanceof CustomListArgument<?> arg) return arg.parse(this, 0);
                if (argument.defaultVal() == null) return null;

                return argument.type().parse(this, argument.defaultVal());
            } catch (ArgumentParseException err) {
                if (argument.onError() != null) argument.onError().apply(this, err.type());
                else throw new RuntimeException(err.getMessage());
            }
        } else if (rawArgAt(index) != null) {
            try {
                if (argument.type() instanceof CustomListArgument<?> arg) return arg.parse(this, index);

                return argument.type().parse(this, rawArgAt(index));
            } catch (ArgumentParseException e) {
                if (argument.onError() != null) argument.onError().apply(this, e.type());
                else throw new RuntimeException(e.getMessage());
            }
        }

        return null;
    }

    /**
     * Returns the argument of the identifier with the specified type.
     *
     * @param identifier The identifier of the argument.
     * @param type       The type parameter for the generic method.
     * @param <T>        The type parameter for the generic method.
     * @return The argument of the specified type.
     */
    public <T> @Nullable T argAt(@NotNull String identifier, @NotNull Class<T> type) {
        for (Argument<?> arg : args) {
            if (arg.identifier().equalsIgnoreCase(identifier)) {
                return argAt(args.indexOf(arg), type);
            }
        }

        return null;
    }

    /**
     * Returns the argument at the specified index with the specified type.
     *
     * @param index The index of the argument.
     * @param type  The type parameter for the generic method.
     * @param <T>   The type parameter for the generic method.
     * @return The argument at the specified index with the specified type.
     */
    @SuppressWarnings("unchecked")
    public <T> @Nullable T argAt(int index, @NotNull Class<T> type) {
        Argument<?> argument = args.get(index);

        if (argument == null || rawArgs == null || rawArgs.isEmpty()) return null;

        if (rawArgs.size() <= index || rawArgAt(index) == null) {
            try {
                if (argument.type() instanceof CustomListArgument<?> arg) return (T) arg.parse(this, 0);
                if (argument.defaultVal() == null) return null;

                return type.cast(argument.type().parse(this, argument.defaultVal()));
            } catch (ArgumentParseException err) {
                if (argument.onError() != null) argument.onError().apply(this, err.type());
                else throw new RuntimeException(err.getMessage());
            }
        } else if (rawArgAt(index) != null) {
            try {
                if (argument.type() instanceof CustomListArgument<?> arg) return (T) arg.parse(this, index);

                return type.cast(argument.type().parse(this, rawArgAt(index)));
            } catch (ArgumentParseException e) {
                if (argument.onError() != null) argument.onError().apply(this, e.type());
                else throw new RuntimeException(e.getMessage());
            }
        }

        return null;
    }

    public int argPos(String identifier) {
        for (Argument<?> arg : args) {
            if (arg.identifier().equalsIgnoreCase(identifier)) {
                return args.indexOf(arg);
            }
        }

        return -1;
    }

    /**
     * Returns a list of arguments.
     *
     * @return A list of arguments.
     */
    public @NotNull List<Argument<?>> args() {
        return args;
    }

    /**
     * Returns the size of all arguments.
     *
     * @return The size of all arguments.
     */
    public int argSize() {
        return rawArgs().size();
    }

    /**
     * Returns the size of all arguments.
     *
     * @return The size of all arguments.
     */
    public int size() {
        return argSize();
    }
}
