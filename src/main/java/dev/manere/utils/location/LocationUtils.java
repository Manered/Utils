package dev.manere.utils.location;

import org.bukkit.Location;

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
    public static boolean locIsBetween(Location a, Location b, Location locToCheck) {
        return isBetween(a.getBlockX(), b.getBlockX(), locToCheck.getBlockX())
                && isBetween(a.getBlockY(), b.getBlockY(), locToCheck.getBlockY())
                && isBetween(a.getBlockZ(), b.getBlockZ(), locToCheck.getBlockZ());
    }
}
