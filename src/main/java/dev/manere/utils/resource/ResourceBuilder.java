package dev.manere.utils.resource;

import dev.manere.utils.resource.format.ResourceFormat;
import dev.manere.utils.resource.format.ResourceFormats;
import dev.manere.utils.resource.path.ResourcePath;
import dev.manere.utils.resource.path.ResourcePaths;

import java.util.function.Consumer;

/**
 * A builder class for creating instances of {@link ResourceFile}. Allows customization
 * of the resource file's format, options, name, and path before building the resource.
 */
public class ResourceBuilder {
    private ResourceFormat format;
    private final ResourceOptions options;
    private ResourcePath path;
    private String name;

    /**
     * Constructs a new ResourceBuilder with default settings.
     */
    public ResourceBuilder() {
        this.format = ResourceFormats.yaml();
        this.options = new ResourceOptions();
        this.name = null;
        this.path = ResourcePaths.plugin();
    }

    /**
     * Returns a new instance of ResourceBuilder.
     *
     * @return A new ResourceBuilder instance.
     */
    public static ResourceBuilder builder() {
        return new ResourceBuilder();
    }

    /**
     * Gets the current resource format.
     *
     * @return The current resource format.
     */
    public ResourceFormat format() {
        return format;
    }

    /**
     * Sets the resource format for the builder.
     *
     * @param format The desired resource format.
     * @return The current ResourceBuilder instance for method chaining.
     */
    public ResourceBuilder format(ResourceFormat format) {
        this.format = format;
        return this;
    }

    /**
     * Gets the current resource options.
     *
     * @return The current resource options.
     */
    public ResourceOptions options() {
        return options;
    }

    /**
     * Configures the resource options using the provided consumer.
     *
     * @param optionsConsumer A consumer for configuring resource options.
     * @return The current ResourceBuilder instance for method chaining.
     */
    public ResourceBuilder options(Consumer<ResourceOptions> optionsConsumer) {
        optionsConsumer.accept(this.options);
        return this;
    }

    /**
     * Gets the current resource name.
     *
     * @return The current resource name.
     */
    public String name() {
        return name;
    }

    /**
     * Sets the resource name for the builder.
     *
     * @param name The desired resource name.
     * @return The current ResourceBuilder instance for method chaining.
     */
    public ResourceBuilder name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the current resource path.
     *
     * @return The current resource path.
     */
    public ResourcePath path() {
        return path;
    }

    /**
     * Sets the resource path for the builder.
     *
     * @param path The desired resource path.
     * @return The current ResourceBuilder instance for method chaining.
     */
    public ResourceBuilder path(ResourcePath path) {
        this.path = path;
        return this;
    }

    /**
     * Builds and returns a new instance of {@link ResourceFile} based on the builder's configuration.
     *
     * @return A new ResourceFile instance.
     */
    public ResourceFile build() {
        return ResourceFile.resource(this.name, this.format, this.options, this.path);
    }
}
