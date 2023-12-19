package dev.manere.utils.resource.format;

/**
 * Utility class for obtaining predefined ResourceFormats.
 */
public class ResourceFormats {
    /**
     * Retrieves a ResourceFormat instance for YAML files with the extension ".yml".
     *
     * @return A ResourceFormat instance for YAML files.
     */
    public static ResourceFormat yml() {
        return ResourceFormat.format(".yml");
    }

    /**
     * Retrieves a ResourceFormat instance for YAML files with the extension ".yml".
     *
     * @return A ResourceFormat instance for YAML files.
     */
    public static ResourceFormat yaml() {
        return ResourceFormat.format(".yml");
    }
}
