package dev.manere.utils.event.crystal;

import dev.manere.utils.library.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * This class implements a Spigot event listener for various events related to player damage and death involving crystals.
 */
public class SpigotEventListener implements Listener {
    private Entity crystal;
    private Player victim;
    private Player killer;
    private EntityDamageByEntityEvent playerDamageEvent;

    /**
     * Resets temporary data related to the events.
     */
    private void reset() {
        this.crystal = null;
        this.victim = null;
        this.killer = null;
        this.playerDamageEvent = null;
    }

    /**
     * Listens for the crystal explosion event to determine the killer.
     *
     * @param event The EntityDamageByEntityEvent representing the crystal explosion event.
     */
    @EventHandler
    public void onCrystalExplode(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.ENDER_CRYSTAL) return;

        if (event.getDamager().getType() != EntityType.PLAYER) return;

        this.reset();
        this.crystal = event.getEntity();
        this.killer = (Player) event.getDamager();
    }

    /**
     * Listens for player damage events by crystals to determine the victim.
     *
     * @param event The EntityDamageByEntityEvent representing the player damage event.
     */
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;

        if (this.crystal == null) {
            this.reset();
            return;
        }

        if (event.getDamager() != this.crystal) {
            this.reset();
            return;
        }

        this.victim = (Player) event.getEntity();
        this.playerDamageEvent = event;
    }

    /**
     * Listens for player death events to create a custom event for a player death involving a crystal.
     *
     * @param event The PlayerDeathEvent representing the player's death.
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.crystal == null || this.killer == null || victim == null || playerDamageEvent == null) {
            return;
        }

        if (event.getEntity() != this.victim) {
            return;
        }

        PlayerDeathByPlayerWithCrystalEvent calledEvent = new PlayerDeathByPlayerWithCrystalEvent(
                this.killer, this.victim, this.crystal, this.playerDamageEvent, event
        );

        Utils.plugin().getServer().getPluginManager().callEvent(calledEvent);

        this.reset();
    }
}
