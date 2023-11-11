package dev.manere.utils.location;

import dev.manere.utils.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Utility class for building {@link Location} objects.
 */
public class LocationBuilder {
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private World world;

    /**
     * Creates a new LocationBuilder with default values.
     * X, Y, Z will be 0, world will be "world", pitch and yaw will be 0.
     */
    public LocationBuilder() {
        this.x = 0.0;
        this.y = 64.0;
        this.z = 0.0;
        this.world = Worlds.world("world");
        this.pitch = 0.0f;
        this.yaw = 0.0f;
    }

    /**
     * Creates a new LocationBuilder with the given X, Y, Z coordinates and world.
     * Pitch and yaw will be 0.
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     * @param world The world
     */
    public LocationBuilder(double x, double y, double z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    /**
     * Creates a new LocationBuilder with the given X, Y, Z coordinates, world, pitch and yaw.
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     * @param world The world
     * @param pitch The pitch
     * @param yaw The yaw
     */
    public LocationBuilder(double x, double y, double z, World world, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    /**
     * Returns a new LocationBuilder instance with default values.
     *
     * @return a new LocationBuilder
     */
    public static LocationBuilder location() {
        return new LocationBuilder();
    }

    /**
     * Returns a new LocationBuilder instance with the given X, Y, Z and world.
     * Pitch and yaw will be 0.
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     * @param world The world
     * @return a new LocationBuilder
     */
    public static LocationBuilder location(double x, double y, double z, World world) {
        return new LocationBuilder(x, y, z, world);
    }

    /**
     * Returns a new LocationBuilder instance with the given X, Y, Z, world, pitch and yaw.
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     * @param world The world
     * @param pitch The pitch
     * @param yaw The yaw
     * @return a new LocationBuilder
     */
    public static LocationBuilder location(double x, double y, double z, World world, float pitch, float yaw) {
        return new LocationBuilder(x, y, z, world, pitch, yaw);
    }

    /**
     * Sets the X coordinate.
     *
     * @param x The X coordinate
     * @return this builder
     */
    public LocationBuilder x(double x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the Y coordinate.
     *
     * @param y The Y coordinate
     * @return this builder
     */
    public LocationBuilder y(double y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the Z coordinate.
     *
     * @param z The Z coordinate
     * @return this builder
     */
    public LocationBuilder z(double z) {
        this.z = z;
        return this;
    }

    /**
     * Sets the world.
     *
     * @param world The world
     * @return this builder
     */
    public LocationBuilder world(World world) {
        this.world = world;
        return this;
    }

    /**
     * Sets the pitch.
     *
     * @param pitch The pitch
     * @return this builder
     */
    public LocationBuilder pitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    /**
     * Sets the yaw.
     *
     * @param yaw The yaw
     * @return this builder
     */
    public LocationBuilder yaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    /**
     * Builds and returns the Location with the configured values.
     *
     * @return The built Location
     */
    public Location build() {
        return new Location(world, x, y, z, yaw, pitch);
    }
}