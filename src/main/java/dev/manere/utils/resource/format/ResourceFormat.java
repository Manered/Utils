package dev.manere.utils.resource.format;

/**
 * Represents the format of a resource file, identified by its file extension.
 */
public class ResourceFormat {
    private String extension;

    /**
     * Constructs a ResourceFormat with the specified file extension.
     *
     * @param extension The file extension of the resource format.
     */
    public ResourceFormat(String extension) {
        this.extension = extension;

        if (!this.extension.contains(".")) {
            this.extension = "." + extension;
        }
    }

    /**
     * Constructs a ResourceFormat with a default file extension ".yml".
     */
    public ResourceFormat() {
        this.extension = ".yml";
    }

    /**
     * Creates a new ResourceFormat with the specified file extension.
     *
     * @param extension The file extension of the resource format.
     * @return A new ResourceFormat instance.
     */
    public static ResourceFormat format(String extension) {
        return new ResourceFormat(extension);
    }

    /**
     * Retrieves the file extension associated with this resource format.
     *
     * @return The file extension.
     */
    public String extension() {
        return extension;
    }
}
