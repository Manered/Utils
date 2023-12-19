package dev.manere.utils.resource.path;

import dev.manere.utils.library.Utils;

public class ResourcePath {
    private final String path;

    /**
     * Constructs a ResourcePath with the specified path.
     *
     * @param path The path to the resource.
     */
    public ResourcePath(String path) {
        this.path = path;
    }

    /**
     * Constructs a ResourcePath with the default path derived from the plugin's data folder.
     */
    public ResourcePath() {
        this.path = Utils.plugin().getDataFolder().getPath();
    }

    /**
     * Creates a new ResourcePath with the specified path.
     *
     * @param path The path to the resource.
     * @return A new ResourcePath instance.
     */
    public static ResourcePath path(String path) {
        return new ResourcePath(path);
    }

    /**
     * Retrieves the path associated with this resource.
     *
     * @return The resource path.
     */
    public String path() {
        return path;
    }
}
