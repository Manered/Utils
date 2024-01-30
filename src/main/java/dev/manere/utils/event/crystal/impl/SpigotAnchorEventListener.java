package dev.manere.utils.event.crystal.impl;

import dev.manere.utils.event.crystal.PlayerAnchorDeathEvent;
import dev.manere.utils.library.Utils;
import dev.manere.utils.player.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.RespawnAnchor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpigotAnchorEventListener implements Listener {
    private static final Map<Location, UUID> DETONATOR_MAP = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock() == null || !event.hasBlock() || event.getMaterial() != Material.RESPAWN_ANCHOR)
                return;
            if (!(event.getClickedBlock().getBlockData() instanceof RespawnAnchor anchorBlock)) return;

            ItemStack itemHand = event.getItem() == null
                    ? new ItemStack(Material.AIR)
                    : event.getItem();

            if (!(anchorBlock.getCharges() <= 3)) return;
            if (itemHand.getType() == Material.GLOWSTONE && anchorBlock.getCharges() != 0) return;

            DETONATOR_MAP.put(event.getClickedBlock().getLocation(), event.getPlayer().getUniqueId());
        } catch (Exception e) {
            HandlerList.unregisterAll(this);
        }
    }

    @EventHandler
    public void onAnchorDamage(EntityDamageByBlockEvent event) {
        try {
            if (event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return;
            if (!(event.getEntity() instanceof Player victim) || event.getEntityType() != EntityType.PLAYER) return;
            if (victim.getHealth() > event.getDamage()) return;

            if (victim.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) return;
            if (victim.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) return;

            if (event.getDamagerBlockState() == null || event.getDamagerBlockState().getType() != Material.RESPAWN_ANCHOR)
                return;

            if (victim.getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING) return;
            if (victim.getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) return;

            UUID detonator = DETONATOR_MAP.get(new Location(
                    victim.getWorld(),
                    event.getDamagerBlockState().getX(),
                    event.getDamagerBlockState().getY(),
                    event.getDamagerBlockState().getZ())
            );

            if (detonator == null) return;

            Player playerDetonator = PlayerUtils.player(detonator);

            if (playerDetonator == null) return;

            PlayerAnchorDeathEvent calledEvent = new PlayerAnchorDeathEvent(
                    event.getDamagerBlockState(), victim, playerDetonator
            );

            Utils.plugin().getServer().getPluginManager().callEvent(calledEvent);

            DETONATOR_MAP.remove(event.getDamagerBlockState().getLocation());
        } catch (Exception e) {
            HandlerList.unregisterAll(this);
        }
    }
}