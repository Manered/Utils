package dev.manere.utils.misc;

import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.Utils;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class for handling Minecraft versions and version-related operations.
 */
public class Versions {
    /**
     * Checks if the provided version matches the current Minecraft server version.
     *
     * @param version The version to compare.
     * @return True if the provided version matches the current server version, false otherwise.
     */
    public static boolean isVersion(@NotNull String version) {
        return version().equalsIgnoreCase(version);
    }

    /**
     * Checks if the current Minecraft server version is higher than the provided version.
     *
     * @param version The version to compare.
     * @return True if the current server version is higher, false otherwise.
     */
    public static boolean isHigherThanOrEqualTo(@NotNull String version) {
        return isVersion(version) || compareVersions(version(), version) > 0;
    }

    /**
     * Checks if the current Minecraft server version is lower than the provided version.
     *
     * @param version The version to compare.
     * @return True if the current server version is lower, false otherwise.
     */
    public static boolean isLowerThan(@NotNull String version) {
        return !isHigherThanOrEqualTo(version);
    }

    /**
     * Compares two version strings and returns an integer representing their relationship.
     *
     * @param versionOne The first version to compare.
     * @param versionTwo The second version to compare.
     * @return An integer less than 0 if versionOne is less than versionTwo,
     *         0 if they are equal, and greater than 0 if versionOne is greater than versionTwo.
     */
    private static int compareVersions(@NotNull String versionOne, @NotNull String versionTwo) {
        List<Integer> v1Parts = getVersionParts(versionOne);
        List<Integer> v2Parts = getVersionParts(versionTwo);

        int minLength = Math.min(v1Parts.size(), v2Parts.size());

        for (int i = 0; i < minLength; i++) {
            int partComparison = Integer.compare(v1Parts.get(i), v2Parts.get(i));
            if (partComparison != 0) {
                return partComparison;
            }
        }

        return Integer.compare(v1Parts.size(), v2Parts.size());
    }

    /**
     * Converts a version string into a list of integers representing its parts.
     *
     * @param version The version string to convert.
     * @return A list of integers representing the version parts.
     */
    private static @NotNull List<Integer> getVersionParts(@NotNull String version) {
        return Stream.of(version.split("\\."))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    /**
     * Gets the current Minecraft server version.
     *
     * @return The current server version.
     */
    public static @NotNull String version() {
        return Bukkit.getMinecraftVersion();
    }

    /**
     * Gets a list of known Minecraft versions in descending order.
     * This does not contain snapshots/betas/alphas.
     *
     * @return A list of Minecraft versions.
     */
    public static @NotNull List<String> minecraft() {
        return new ArrayList<>(List.of(
                "1.20.4", "1.20.3", "1.20.2", "1.20.1", "1.20",
                "1.19.4", "1.19.3", "1.19.2", "1.19.1", "1.19",
                "1.18.2", "1.18.1", "1.18",
                "1.17.1", "1.17",
                "1.16.5", "1.16.4", "1.16.3", "1.16.2", "1.16.1", "1.16",
                "1.15.2", "1.15.1", "1.15",
                "1.14.4", "1.14.3", "1.14.2", "1.14.1", "1.14",
                "1.13.2", "1.13.1", "1.13",
                "1.12.2", "1.12.1", "1.12",
                "1.11.2", "1.11.1", "1.11",
                "1.10.2", "1.10.1", "1.10",
                "1.9.4", "1.9.3", "1.9.2", "1.9.1", "1.9",
                "1.8.9", "1.8.8", "1.8.7", "1.8.6", "1.8.5", "1.8.4", "1.8.3", "1.8.2", "1.8.1", "1.8",
                "1.7.10", "1.7.9", "1.7.8", "1.7.7", "1.7.6", "1.7.5", "1.7.4", "1.7.3", "1.7.2",
                "1.6.4", "1.6.2", "1.6.1", "1.5.2", "1.5.1",
                "1.4.7", "1.4.6", "1.4.5", "1.4.4", "1.4.2",
                "1.3.2", "1.3.1",
                "1.2.5", "1.2.4", "1.2.3", "1.2.2", "1.2.1",
                "1.1",
                "1.0"
        ));
    }

    /**
     * Alias for the {@link Versions#minecraft()} method.
     *
     * @return A list of Minecraft versions.
     */
    public static @NotNull List<String> mc() {
        return minecraft();
    }

    /**
     * Automatically retrieves the dependency version from the plugin metadata and formats it.
     *
     * @return The formatted version string.
     */
    @SuppressWarnings("UnstableApiUsage")
    public static String auto() {
        return Utils.plugin().getPluginMeta().getVersion()
                .toLowerCase()
                .replaceAll("\\[", "")
                .replaceAll("]", "")
                .replaceAll("_", ".");
    }

    /**
     * Creates a formatted version string with additional properties.
     *
     * @param version    The base version.
     * @param properties The additional version properties.
     * @return The formatted version string.
     */
    public static String of(@NotNull String version, @NotNull Elements<VersionProperty> properties) {
        version = version.replaceAll("_", ".");
        StringBuilder builder = new StringBuilder(version);

        for (VersionProperty property : properties) {
            builder.append(property.value())
                    .append("-");
        }

        builder.deleteCharAt(builder.lastIndexOf("-"));
        return builder.toString();
    }

    /**
     * Creates a formatted version string with additional properties.
     *
     * @param version    The base version.
     * @param properties The additional version properties.
     * @return The formatted version string.
     */
    public static String of(@NotNull String version, @NotNull VersionProperty... properties) {
        return Versions.of(version, Elements.of(properties));
    }

    /**
     * Represents a version property with a key and a value.
     */
    public record VersionProperty(String key, String value) {
        /**
         * Creates a new VersionProperty instance.
         *
         * @param key   The key of the property.
         * @param value The value of the property.
         * @return The VersionProperty instance.
         */
        public static VersionProperty of(String key, String value) {
            return new VersionProperty(key, value.replaceAll(" ", "_").toUpperCase());
        }
    }
}
