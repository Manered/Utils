package dev.manere.utils.command.builder.dispatcher;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandContext {
    private final CommandSender sender;
    private final Command command;
    private final String name;
    private final List<String> args;

    /**
     * Constructs a new CommandContext with the specified sender, command, name, and arguments.
     *
     * @param sender  The CommandSender who executed the command.
     * @param command The Command object associated with this context.
     * @param name    The name of the command.
     * @param args    A list of arguments provided with the command.
     */
    public CommandContext(CommandSender sender, Command command, String name, String[] args) {
        this.sender = sender;
        this.command = command;
        this.name = name;
        this.args = Arrays.stream(args).toList();
    }

    /**
     * Constructs a new CommandContext with the specified sender, command, name, and arguments.
     *
     * @param sender  The CommandSender who executed the command.
     * @param command The Command object associated with this context.
     * @param name    The name of the command.
     * @param args    An array of arguments provided with the command.
     */
    public CommandContext(CommandSender sender, Command command,  String name, List<String> args) {
        this.sender = sender;
        this.command = command;
        this.name = name;
        this.args = args;
    }

    /**
     * Returns the CommandSender who executed the command.
     *
     * @return The CommandSender associated with this context.
     */
    public CommandSender sender() {
        return sender;
    }

    /**
     * Returns the Player who executed the command. Assumes the sender is a Player.
     *
     * @return The Player associated with this context.
     */
    public Player player() {
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
     * Returns the Command object associated with this context.
     *
     * @return The Command object.
     */
    public Command command() {
        return command;
    }

    /**
     * Returns the name of the command.
     *
     * @return The name of the command.
     */
    public String name() {
        return name;
    }

    /**
     * Returns the alias of the command (which is the same as the name in this context).
     *
     * @return The alias of the command.
     */
    public String alias() {
        return name();
    }

    /**
     * Returns the name of the command (which is the same as the alias in this context).
     *
     * @return The name of the command that was executed.
     */
    public String commandRan() {
        return alias();
    }

    /**
     * Returns a list of arguments provided with the command.
     *
     * @return A list of arguments.
     */
    public List<String> args() {
        return args;
    }

    /**
     * Returns the argument at the specified index.
     *
     * @param index The index of the argument.
     * @return The argument at the specified index.
     */
    public String argAt(int index) {
        return args.get(index);
    }
}
