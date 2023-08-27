package dev.manere.utils.placeholders;

/**
 * A utility class for simple placeholder replacement.
 */
public class PlaceholderUtils {
    /**
     * Replaces indexed placeholders in the given text with the provided replacements.
     *
     * @param text         The text containing indexed placeholders.
     * @param replacements An array of replacement strings.
     * @return The text with indexed placeholders replaced by their respective replacements.
     */
    public static String fill(String text, String... replacements) {
        for (int i = 0; i < replacements.length; i++) {
            text = text.replace("{" + i + "}", replacements[i]);
        }
        return text;
    }
}