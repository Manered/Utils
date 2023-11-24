package dev.manere.utils.location;

import dev.manere.utils.library.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for performing operations and checks related to locations and coordinates.
 */
public class LocationUtils {

    /**
     * Checks if a given integer value is between two other integer values.
     *
     * @param a             The lower bound of the range.
     * @param b             The upper bound of the range.
     * @param numberToCheck The number to check.
     * @return True if the numberToCheck is between a and b, inclusive.
     */
    public static boolean isBetween(int a, int b, int numberToCheck) {
        return isBetween(a, b, numberToCheck, true);
    }

    /**
     * Checks if a given integer value is between two other integer values.
     *
     * @param a             The lower bound of the range.
     * @param b             The upper bound of the range.
     * @param numberToCheck The number to check.
     * @param inclusive     If true, the range includes the boundaries.
     * @return True if the numberToCheck is between a and b according to inclusiveness.
     */
    public static boolean isBetween(int a, int b, int numberToCheck, boolean inclusive) {
        if (inclusive)
            return numberToCheck >= Math.min(a, b) && numberToCheck <= Math.max(a, b);
        else
            return numberToCheck > Math.min(a, b) && numberToCheck < Math.max(a, b);
    }

    /**
     * Checks if a given Location is within the bounding box defined by two other Locations.
     *
     * @param a            The first corner of the bounding box.
     * @param b            The second corner of the bounding box.
     * @param locToCheck   The Location to check.
     * @return True if locToCheck is within the bounding box defined by a and b.
     */
    public static boolean locIsBetween(@NotNull Location a, @NotNull Location b, @NotNull Location locToCheck) {
        return isBetween(a.getBlockX(), b.getBlockX(), locToCheck.getBlockX())
                && isBetween(a.getBlockY(), b.getBlockY(), locToCheck.getBlockY())
                && isBetween(a.getBlockZ(), b.getBlockZ(), locToCheck.getBlockZ());
    }

    /**
     * Retrieves a list of players within a certain radius of a specified location.
     *
     * @param center The center location.
     * @param radius The radius in which to search for players.
     * @return A list of players within the specified radius of the center location.
     */
    public static @NotNull List<Player> playersInRadius(@NotNull Location center, double radius) {
        List<Player> nearbyPlayers = new ArrayList<>();

        for (Player player : Utils.plugin().getServer().getOnlinePlayers()) {
            Location playerLocation = player.getLocation();
            if (Objects.equals(center.getWorld(), playerLocation.getWorld()) &&
                    center.distance(playerLocation) <= radius) {
                nearbyPlayers.add(player);
            }
        }

        return nearbyPlayers;
    }

    /**
     * Retrieves a list of players within a certain radius of a target player.
     *
     * @param targetPlayer The target player around whom to search for other players.
     * @param radius       The radius in which to search for players.
     * @return A list of players within the specified radius of the target player.
     */
    public static @NotNull List<Player> playersNearPlayer(@NotNull Player targetPlayer, double radius) {
        List<Player> nearbyPlayers = new ArrayList<>();
        Location targetLocation = targetPlayer.getLocation();

        for (Player player : Utils.plugin().getServer().getOnlinePlayers()) {
            if (!player.equals(targetPlayer)) {
                Location playerLocation = player.getLocation();
                if (Objects.equals(targetLocation.getWorld(), playerLocation.getWorld()) &&
                        targetLocation.distance(playerLocation) <= radius) {
                    nearbyPlayers.add(player);
                }
            }
        }

        return nearbyPlayers;
    }

    /**
     * Calculates the distance between two locations.
     *
     * @param loc1 The first location.
     * @param loc2 The second location.
     * @return The distance between the two locations.
     */
    public static double distance(@NotNull Location loc1, @NotNull Location loc2) {
        return loc1.distance(loc2);
    }

    /**
     * Serializes a location into a string representation.
     *
     * @param location The location to serialize.
     * @return The serialized location string.
     */
    public static @NotNull String serialize(@NotNull Location location) {
        return Objects.requireNonNull(location.getWorld()).getName() + "," +
                location.getX() + "," +
                location.getY() + "," +
                location.getZ() + "," +
                location.getPitch() + "," +
                location.getYaw();
    }

    /**
     * Deserializes a location from a string representation.
     *
     * @param serialized The serialized location string.
     * @return The deserialized Location object, or null if deserialization fails.
     */
    public static @Nullable Location deserialize(@NotNull String serialized) {
        String[] parts = serialized.split(",");

        if (parts.length == 6) {
            World world = Bukkit.getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float pitch = Float.parseFloat(parts[4]);
            float yaw = Float.parseFloat(parts[5]);
            return new Location(world, x, y, z, yaw, pitch);
        }

        return null;
    }
}
