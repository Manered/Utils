package dev.manere.utils.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract class that provides a foundation for creating custom command executors in Bukkit.
 */
public abstract class CommandBuilder implements CommandExecutor {

    /** The sender of the command. */
    protected CommandSender sender;

    /** The executed command. */
    protected Command command;

    /** The label of the executed command. */
    protected String label;

    /** The arguments provided with the command. */
    protected String[] args;

    /** Indicates whether the command sender is a player. */
    protected boolean isPlayer;

    /** If the sender is a player, this holds the player instance. */
    protected Player player;

    /**
     * Executes the command when it is triggered.
     *
     * @param sender The sender of the command.
     * @param command The command that was executed.
     * @param label The alias that was used to trigger the command.
     * @param args The arguments provided with the command.
     * @return {@code true} if the command was successfully executed, {@code false} otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;

        if (sender instanceof Player) {
            this.isPlayer = true;
            this.player = (Player) sender;
        }

        return onExecute();
    }

    /**
     * This method is called when the command is executed and should be overridden by subclasses
     * to define the behavior of the command.
     *
     * @return {@code true} if the command was successfully executed, {@code false} otherwise.
     */
    public abstract boolean onExecute();
}
