package dev.manere.utils.event.crystal;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The {@code PlayerCrystalDeathEvent} class represents an event that occurs when a player is killed by another player using an end crystal.
 * This event extends the Bukkit {@link Event} class and provides information about the involved players, the end crystal used, and the damage dealt.
 */
public class PlayerCrystalDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player killer;
    private final Player victim;
    private final Entity crystal;
    private final EntityDamageByEntityEvent playerDamageEvent;
    private final PlayerDeathEvent playerDeathEvent;

    /**
     * Constructs a new {@code PlayerCrystalDeathEvent}.
     *
     * @param killer            The player who initiated the kill.
     * @param victim            The player who was killed.
     * @param crystal           The entity representing the crystal used in the kill.
     * @param playerDamageEvent The {@link EntityDamageByEntityEvent} associated with the kill.
     * @param playerDeathEvent  The {@link PlayerDeathEvent} associated with the kill.
     */
    public PlayerCrystalDeathEvent(
            Player killer,
            Player victim,
            Entity crystal,
            EntityDamageByEntityEvent playerDamageEvent,
            PlayerDeathEvent playerDeathEvent
    ) {
        this.killer = killer;
        this.victim = victim;
        this.crystal = crystal;
        this.playerDeathEvent = playerDeathEvent;
        this.playerDamageEvent = playerDamageEvent;
    }

    /**
     * Returns the event handlers for this event type.
     *
     * @return The event handlers.
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Returns the event handlers for this event type.
     *
     * @return The event handlers.
     */
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Returns the event handlers for this event type.
     *
     * @return The event handlers.
     */
    public @NotNull HandlerList handlers() {
        return getHandlers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getEventName() {
        return "PlayerCrystalDeathEvent";
    }

    public @NotNull String eventName() {
        return getEventName();
    }

    /**
     * Sets the death message for the player's death.
     *
     * @param deathMessage The death message to set.
     */
    public void deathMessage(Component deathMessage) {
        this.playerDeathEvent.deathMessage(deathMessage);
    }

    /**
     * Gets the death message for the player's death.
     *
     * @return The death message.
     */
    public Component deathMessage() {
        return this.playerDeathEvent.deathMessage();
    }

    /**
     * Gets the player who initiated the kill.
     *
     * @return The killer player.
     */
    public Player killer() {
        return killer;
    }

    /**
     * Gets the player who was killed.
     *
     * @return The victim player.
     */
    public Player victim() {
        return victim;
    }

    /**
     * Gets the entity representing the crystal used in the kill.
     *
     * @return The crystal entity.
     */
    public Entity crystal() {
        return crystal;
    }

    /**
     * Checks if the player's inventory should be kept after death.
     *
     * @return {@code true} if the inventory should be kept, otherwise {@code false}.
     */
    public boolean keepInventory() {
        return this.playerDeathEvent.getKeepInventory();
    }

    /**
     * Sets whether the player's inventory should be kept after death.
     *
     * @param keepInventory {@code true} to keep the inventory, otherwise {@code false}.
     */
    public void keepInventory(boolean keepInventory) {
        this.playerDeathEvent.setKeepInventory(keepInventory);
    }

    /**
     * Checks if the player's experience levels should be kept after death.
     *
     * @return {@code true} if the experience levels should be kept, otherwise {@code false}.
     */
    public boolean keepLevel() {
        return this.playerDeathEvent.getKeepLevel();
    }

    /**
     * Sets whether the player's experience levels should be kept after death.
     *
     * @param keepLevel {@code true} to keep the experience levels, otherwise {@code false}.
     */
    public void keepLevel(boolean keepLevel) {
        this.playerDeathEvent.setKeepLevel(keepLevel);
    }

    /**
     * Gets the new experience amount for the player after death.
     *
     * @return The new experience amount.
     */
    public int newExp() {
        return this.playerDeathEvent.getNewExp();
    }

    /**
     * Sets the new experience amount for the player after death.
     *
     * @param exp The new experience amount.
     */
    public void newExp(int exp) {
        this.playerDeathEvent.setNewExp(exp);
    }

    /**
     * Gets the new level for the player after death.
     *
     * @return The new level.
     */
    public int newLevel() {
        return this.playerDeathEvent.getNewLevel();
    }

    /**
     * Sets the new level for the player after death.
     *
     * @param level The new level.
     */
    public void newLevel(int level) {
        this.playerDeathEvent.setNewLevel(level);
    }

    /**
     * Gets the new total experience amount for the player after death.
     *
     * @return The new total experience amount.
     */
    public int newTotalExp() {
        return this.playerDeathEvent.getNewTotalExp();
    }

    /**
     * Sets the new total experience amount for the player after death.
     *
     * @param totalExp The new total experience amount.
     */
    public void newTotalExp(int totalExp) {
        this.playerDeathEvent.setNewTotalExp(totalExp);
    }

    /**
     * Gets the dropped experience amount upon player's death.
     *
     * @return The dropped experience amount.
     */
    public int droppedExp() {
        return this.playerDeathEvent.getDroppedExp();
    }

    /**
     * Sets the dropped experience amount upon player's death.
     *
     * @param exp The dropped experience amount.
     */
    public void droppedExp(int exp) {
        this.playerDeathEvent.setDroppedExp(exp);
    }

    /**
     * Gets the list of items dropped upon player's death.
     *
     * @return The list of dropped items.
     */
    public List<ItemStack> drops() {
        return this.playerDeathEvent.getDrops();
    }

    /**
     * Gets the initial damage dealt to the player by the other entity.
     *
     * @return The initial damage.
     */
    public double damage() {
        return this.playerDamageEvent.getDamage();
    }

    /**
     * Gets the final damage dealt to the player by the other entity.
     *
     * @return The final damage.
     */
    public final double finalDamage() {
        return this.playerDamageEvent.getFinalDamage();
    }

    /**
     * Checks if the death was a result of suicide.
     *
     * @return {@code true} if it was suicide, otherwise {@code false}.
     */
    public boolean isSuicide() {
        return this.killer == this.victim;
    }
}
