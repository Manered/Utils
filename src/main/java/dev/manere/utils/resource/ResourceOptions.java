package dev.manere.utils.resource;

import java.util.Arrays;
import java.util.List;

/**
 * Represents options for configuring a resource file, including header, footer, and defaults.
 */
public class ResourceOptions {
    private List<String> header;
    private List<String> footer;
    private boolean defaults;

    /**
     * Constructs a new ResourceOptions with the specified header, footer, and defaults.
     *
     * @param header   The header lines for the resource file.
     * @param footer   The footer lines for the resource file.
     * @param defaults Whether to use default options.
     */
    public ResourceOptions(List<String> header, List<String> footer, boolean defaults) {
        this.header = header;
        this.footer = footer;
        this.defaults = defaults;
    }

    /**
     * Constructs a new ResourceOptions with default settings.
     */
    public ResourceOptions() {
        this.header = null;
        this.footer = null;
        this.defaults = false;
    }

    /**
     * Gets the header lines for the resource file.
     *
     * @return The header lines.
     */
    public List<String> header() {
        return header;
    }

    /**
     * Gets the footer lines for the resource file.
     *
     * @return The footer lines.
     */
    public List<String> footer() {
        return footer;
    }

    /**
     * Checks whether default options should be used.
     *
     * @return True if default options should be used, false otherwise.
     */
    public boolean defaults() {
        return defaults;
    }

    /**
     * Sets the header lines for the resource file.
     *
     * @param header The header lines to set.
     * @return The current ResourceOptions instance for method chaining.
     */
    public ResourceOptions header(List<String> header) {
        this.header = header;
        return this;
    }

    /**
     * Sets the footer lines for the resource file.
     *
     * @param footer The footer lines to set.
     * @return The current ResourceOptions instance for method chaining.
     */
    public ResourceOptions footer(List<String> footer) {
        this.footer = footer;
        return this;
    }

    /**
     * Sets the header lines for the resource file using varargs.
     *
     * @param header The header lines to set.
     * @return The current ResourceOptions instance for method chaining.
     */
    public ResourceOptions header(String... header) {
        return header(Arrays.asList(header));
    }

    /**
     * Sets the footer lines for the resource file using varargs.
     *
     * @param footer The footer lines to set.
     * @return The current ResourceOptions instance for method chaining.
     */
    public ResourceOptions footer(String... footer) {
        return footer(Arrays.asList(footer));
    }

    /**
     * Sets whether default options should be used.
     *
     * @param defaults True to use default options, false otherwise.
     * @return The current ResourceOptions instance for method chaining.
     */
    public ResourceOptions defaults(boolean defaults) {
        this.defaults = defaults;
        return this;
    }
}
