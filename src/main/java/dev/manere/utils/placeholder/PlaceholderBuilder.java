package dev.manere.utils.placeholder;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for building and replacing placeholders in a text.
 */
public class PlaceholderBuilder {
    private String text;
    private final Map<String, String> placeholders;

    /**
     * Constructs a new PlaceholderBuilder with an empty placeholder map.
     */
    public PlaceholderBuilder() {
        this.placeholders = new HashMap<>();
    }

    /**
     * Sets the base text to work with.
     *
     * @param text The base text containing placeholders to replace.
     * @return The current PlaceholderBuilder instance for method chaining.
     */
    public PlaceholderBuilder fromText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Adds a placeholder and its replacement to the map.
     *
     * @param placeholder The placeholder string to be replaced.
     * @param replacement The replacement string for the placeholder.
     * @return The current PlaceholderBuilder instance for method chaining.
     */
    public PlaceholderBuilder placeholder(String placeholder, String replacement) {
        placeholders.put(placeholder, replacement);
        return this;
    }

    /**
     * Builds the text by replacing placeholders with their respective replacements.
     *
     * @return The text with placeholders replaced by their corresponding replacements.
     */
    public String build() {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            text = text.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return text;
    }
}