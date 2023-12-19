package dev.manere.utils.event.crystal;

import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an event triggered when a player dies from a detonated Respawn Anchor.
 * This event provides information about the block of the Respawn Anchor,
 * the victim player, the attacking player (the player who detonated the anchor),
 * and the Respawn Anchor block itself (if the block is a Respawn Anchor).
 */
public class PlayerAnchorDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final @NotNull BlockState block;
    private final @NotNull Player victim;
    private final @NotNull Player attacker;
    private final @Nullable RespawnAnchor anchor;

    /**
     * Constructs a new instance of the PlayerAnchorDeathEvent.
     *
     * @param block    The block of the Respawn Anchor.
     * @param victim   The player who died near the Respawn Anchor.
     * @param attacker The player who detonated the anchor. Shouldn't be nullable, but can be.
     */
    public PlayerAnchorDeathEvent(@NotNull BlockState block, @NotNull Player victim, @NotNull Player attacker) {
        this.block = block;
        this.victim = victim;
        this.attacker = attacker;

        if (this.block instanceof RespawnAnchor respawnAnchor) {
            this.anchor = respawnAnchor;
        } else {
            this.anchor = null;
        }
    }

    /**
     * Gets the HandlerList for this event.
     *
     * @return The HandlerList for this event.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Gets the HandlerList for this event.
     *
     * @return The HandlerList for this event.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the HandlerList for this event.
     *
     * @return The HandlerList for this event.
     */
    public @NotNull HandlerList handlers() {
        return getHandlers();
    }

    /**
     * Gets the name of this event.
     *
     * @return The name of this event.
     */
    @Override
    public @NotNull String getEventName() {
        return "PlayerAnchorDeathEvent";
    }

    /**
     * Gets the name of this event.
     *
     * @return The name of this event.
     */
    public @NotNull String eventName() {
        return getEventName();
    }

    /**
     * Gets the block of the Respawn Anchor involved in this event.
     *
     * @return The Respawn Anchor block.
     */
    public @NotNull BlockState block() {
        return block;
    }

    /**
     * Gets the player who died near the Respawn Anchor.
     *
     * @return The victim player.
     */
    public @NotNull Player victim() {
        return victim;
    }

    /**
     * Gets the player who attacked, if any.
     *
     * @return The attacking player, or null if the death was not caused by a player.
     */
    public @NotNull Player attacker() {
        return attacker;
    }

    /**
     * Gets the Respawn Anchor block, if the block is a Respawn Anchor.
     *
     * @return The Respawn Anchor block, or null if the block is not a Respawn Anchor.
     */
    public @Nullable RespawnAnchor anchor() {
        return anchor;
    }
}
