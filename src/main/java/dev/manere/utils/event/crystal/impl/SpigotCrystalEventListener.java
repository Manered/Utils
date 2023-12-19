package dev.manere.utils.event.crystal.impl;

import dev.manere.utils.event.crystal.PlayerCrystalDeathEvent;
import dev.manere.utils.library.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SpigotCrystalEventListener implements Listener {
    private Entity crystal;
    private Player victim;
    private Player killer;
    private EntityDamageByEntityEvent playerDamageEvent;

    private void reset() {
        this.crystal = null;
        this.victim = null;
        this.killer = null;
        this.playerDamageEvent = null;
    }

    @EventHandler
    public void onCrystalExplode(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.ENDER_CRYSTAL) return;

        if (event.getDamager().getType() != EntityType.PLAYER) return;

        this.reset();
        this.crystal = event.getEntity();
        this.killer = (Player) event.getDamager();
    }

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

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.crystal == null || this.killer == null || victim == null || playerDamageEvent == null) {
            return;
        }

        if (event.getEntity() != this.victim) {
            return;
        }

        PlayerCrystalDeathEvent calledEvent = new PlayerCrystalDeathEvent(
                this.killer, this.victim, this.crystal, this.playerDamageEvent, event
        );

        Utils.plugin().getServer().getPluginManager().callEvent(calledEvent);

        this.reset();
    }
}
