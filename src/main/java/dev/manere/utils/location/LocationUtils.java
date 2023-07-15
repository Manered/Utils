package dev.manere.utils.location;

import org.bukkit.Location;

public class LocationUtils {
    public static boolean isBetween(int a, int b, int numberToCheck) {
        return isBetween(a, b, numberToCheck, true);
    }

    public static boolean isBetween(int a, int b, int numberToCheck, boolean inclusive) {
        if (inclusive)
            return numberToCheck >= Math.min(a, b) && numberToCheck <= Math.max(a, b);
        else
            return numberToCheck > Math.min(a, b) && numberToCheck < Math.max(a, b);
    }

    public static boolean locIsBetween(Location a, Location b, Location locToCheck) {
        return isBetween(a.getBlockX(), b.getBlockX(), locToCheck.getBlockX())
                && isBetween(a.getBlockY(), b.getBlockY(), locToCheck.getBlockY())
                && isBetween(a.getBlockZ(), b.getBlockZ(), locToCheck.getBlockZ());
    }
}
