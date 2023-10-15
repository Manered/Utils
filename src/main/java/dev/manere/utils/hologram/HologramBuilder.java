package dev.manere.utils.hologram;

import dev.manere.utils.location.LocationUtils;
import dev.manere.utils.text.color.ColorUtils;
import dev.manere.utils.world.WorldUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A utility class for creating holograms in Minecraft Bukkit/Spigot plugins.
 * Holograms are created as ArmorStand entities with custom text labels that can be displayed
 * at a specified location in the game world.
 */
public class HologramBuilder {
    private List<Component> lines;
    private Location location;
    private double lineHeight;
    private boolean visible;
    private boolean deleted;

    /**
     * Constructs a new HologramBuilder with default settings.
     */
    public HologramBuilder() {
        this.deleted = false;
        this.visible = true;
        this.lineHeight = 0.25;

        World world = WorldUtils.world("world");

        double y = world
                .getHighestBlockAt(0, 0)
                .getLocation()
                .clone()
                .add(0.0, 4.0, 0.0)
                .getY();

        this.location = new Location(world, 0.0, y, 0.0);
        this.lines = new ArrayList<>();
    }

    /**
     * Constructs a new HologramBuilder with a specified location.
     *
     * @param location The location where the hologram will be created.
     */
    public HologramBuilder(Location location) {
        this.deleted = false;
        this.visible = true;
        this.lineHeight = 0.25;
        this.location = location;
        this.lines = new ArrayList<>();
    }

    /**
     * Creates a new instance of HologramBuilder with default settings.
     *
     * @return A new instance of HologramBuilder.
     */
    public static HologramBuilder of() {
        return new HologramBuilder();
    }

    /**
     * Creates a new instance of HologramBuilder with a specified location.
     *
     * @param location The location where the hologram will be created.
     * @return A new instance of HologramBuilder.
     */
    public static HologramBuilder of(Location location) {
        return new HologramBuilder(location);
    }

    /**
     * Sets the lines of text for the hologram.
     *
     * @param lines The lines of text to be displayed in the hologram.
     * @return This HologramBuilder instance.
     */
    public HologramBuilder setLines(Component... lines) {
        this.lines = Arrays.asList(lines);
        return this;
    }

    /**
     * Sets the lines of text for the hologram.
     *
     * @param lines The list of lines to be displayed in the hologram.
     * @return This HologramBuilder instance.
     */
    public HologramBuilder setLines(List<Component> lines) {
        this.lines = lines;
        return this;
    }

    /**
     * Sets the location where the hologram will be created.
     *
     * @param location The location where the hologram will be created.
     * @return This HologramBuilder instance.
     */
    public HologramBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    /**
     * Sets the height between lines of the hologram.
     *
     * @param lineHeight The height between lines.
     * @return This HologramBuilder instance.
     */
    public HologramBuilder setLineHeight(double lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    /**
     * Sets the visibility of the hologram.
     *
     * @param visible true to make the hologram visible, false to make it invisible.
     * @return This HologramBuilder instance.
     */
    public HologramBuilder setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    /**
     * Deletes the hologram.
     *
     * @return This HologramBuilder instance.
     */
    public HologramBuilder delete() {
        Objects.requireNonNull(location, "Location for hologram cannot be null");
        Objects.requireNonNull(lines, "Lines of hologram cannot be null");
        Objects.requireNonNull(location.getWorld(), "World of hologram's location cannot be null");

        ArmorStand toDelete = null;

        for (ArmorStand stand : location.getWorld().getEntitiesByClass(ArmorStand.class)) {
            if (stand.getLocation() == location) {
                if (stand.getArrowCooldown() == 1) {
                    toDelete = stand;
                    break;
                }
            }
        }

        Objects.requireNonNull(toDelete, "No hologram found at <location>. Seems to be a NPE"
                .replaceAll("<location>", LocationUtils.serializeLocation(location)));

        toDelete.remove();
        this.deleted = true;

        return this;
    }

    /**
     * Builds and displays the hologram in the world.
     *
     * @return This HologramBuilder instance.
     */
    public HologramBuilder build() {
        Objects.requireNonNull(location, "Location for hologram cannot be null");
        Objects.requireNonNull(lines, "Lines of hologram cannot be null");

        if (deleted) {
            throw new NullPointerException("This hologram has previously been deleted therefore it was not found.");
        }

        for (Component text : lines) {
            Objects.requireNonNull(location.getWorld(), "World of hologram's location cannot be null");

            ArmorStand stand = (ArmorStand) location
                    .getWorld()
                    .spawnEntity(location, EntityType.ARMOR_STAND);

            stand.setGravity(false);
            stand.setInvisible(true);
            stand.setInvulnerable(true);
            stand.setCustomNameVisible(true);
            stand.customName(text);
            stand.setSilent(true);
            stand.setAI(false);
            stand.setArrowCooldown(1);
            stand.setBasePlate(false);
            stand.setCanPickupItems(false);
            stand.setCollidable(false);
            stand.setPersistent(true);

            location = location.subtract(0, lineHeight, 0);
        }

        return this;
    }
}
