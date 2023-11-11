package dev.manere.utils.text.color;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for handling formatting in text strings.
 */
public class TextStyle {

    /**
     * Replaces hexadecimal legacy tags in the input string with ChatColor values.
     * <p></p>
     * @param text the text string with hexadecimal legacy tags
     * @return the text string with ChatColor formatting
     */
    @SuppressWarnings("deprecation")
    public static String legacyHex(String text) {
        // Define a regular expression pattern to match <#HEX> tags
        String pattern = "<#([A-Fa-f0-9]{6})>";

        // Create a pattern object and a matcher to find matches
        Pattern colorPattern = Pattern.compile(pattern);
        Matcher matcher = colorPattern.matcher(text);

        // Use a StringBuffer to build the modified message
        StringBuilder modifiedMessage = new StringBuilder();

        // Iterate through the matches and replace them with ChatColor.of()
        while (matcher.find()) {
            String hexColor = matcher.group(1); // Extract the HEX color
            ChatColor color = ChatColor.of("#" + hexColor); // Convert to ChatColor
            matcher.appendReplacement(modifiedMessage, color.toString()); // Replace with ChatColor
        }

        // Append the remaining text to the modified message
        matcher.appendTail(modifiedMessage);

        return modifiedMessage.toString();
    }

    /**
     * Replaces color code placeholders in the input string with actual ChatColor values.
     * Also translates standard Minecraft color codes.
     *
     * @param text the text string with color code placeholders
     * @return the text string with actual color formatting
     */

    /* Can't do anything about this */
    @SuppressWarnings("deprecation")
    public static String legacy(String text) {
        // Replace <color code name> with the actual color code using ChatColor
        text = text.replaceAll("<black>", ChatColor.BLACK.toString());
        text = text.replaceAll("<dark_blue>", ChatColor.DARK_BLUE.toString());
        text = text.replaceAll("<dark_green>", ChatColor.DARK_GREEN.toString());
        text = text.replaceAll("<dark_aqua>", ChatColor.DARK_AQUA.toString());
        text = text.replaceAll("<dark_light_blue>", ChatColor.DARK_AQUA.toString());
        text = text.replaceAll("<dark_red>", ChatColor.DARK_RED.toString());
        text = text.replaceAll("<dark_purple>", ChatColor.DARK_PURPLE.toString());
        text = text.replaceAll("<dark_pink>", ChatColor.DARK_PURPLE.toString());
        text = text.replaceAll("<gold>", ChatColor.GOLD.toString());
        text = text.replaceAll("<dark_yellow>", ChatColor.GOLD.toString());
        text = text.replaceAll("<gray>", ChatColor.GRAY.toString());
        text = text.replaceAll("<grey>", ChatColor.GRAY.toString());
        text = text.replaceAll("<dark_gray>", ChatColor.DARK_GRAY.toString());
        text = text.replaceAll("<dark_grey>", ChatColor.DARK_GRAY.toString());
        text = text.replaceAll("<blue>", ChatColor.BLUE.toString());
        text = text.replaceAll("<green>", ChatColor.GREEN.toString());
        text = text.replaceAll("<lime>", ChatColor.GREEN.toString());
        text = text.replaceAll("<aqua>", ChatColor.AQUA.toString());
        text = text.replaceAll("<light_blue>", ChatColor.AQUA.toString());
        text = text.replaceAll("<red>", ChatColor.RED.toString());
        text = text.replaceAll("<pink>", ChatColor.LIGHT_PURPLE.toString());
        text = text.replaceAll("<purple>", ChatColor.LIGHT_PURPLE.toString());
        text = text.replaceAll("<light_purple>", ChatColor.LIGHT_PURPLE.toString());
        text = text.replaceAll("<light_pink>", ChatColor.LIGHT_PURPLE.toString());
        text = text.replaceAll("<magenta>", ChatColor.LIGHT_PURPLE.toString());
        text = text.replaceAll("<yellow>", ChatColor.YELLOW.toString());
        text = text.replaceAll("<light_yellow>", ChatColor.YELLOW.toString());
        text = text.replaceAll("<white>", ChatColor.WHITE.toString());

        // Replace special formatting tags
        text = text.replaceAll("<bold>", ChatColor.BOLD.toString());
        text = text.replaceAll("<reset>", ChatColor.RESET.toString());
        text = text.replaceAll("<underline>", ChatColor.UNDERLINE.toString());
        text = text.replaceAll("<italic>", ChatColor.ITALIC.toString());
        text = text.replaceAll("<strikethrough>", ChatColor.STRIKETHROUGH.toString());
        text = text.replaceAll("<strike>", ChatColor.STRIKETHROUGH.toString());
        text = text.replaceAll("<obfuscated>", ChatColor.MAGIC.toString());
        text = text.replaceAll("<magic>", ChatColor.MAGIC.toString());
        text = text.replaceAll("<random>", ChatColor.MAGIC.toString());
        text = text.replaceAll("<newline>", "\n");
        text = text.replaceAll("<nl>", "\n");

        // Translate color codes
        text = legacyTranslate(text);

        // Replace <#hex> with the actual hex color code using ChatColor
        text = legacyHex(text);

        return text;
    }

    /**
     * Converts a text string to a Component using MiniMessage.
     *
     * @param text the text to convert
     * @return the Component
     */
    public static Component component(String text) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        return miniMessage.deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    /**
     * Converts a text string to a Component using MiniMessage.
     *
     * @param text the text to convert
     * @return the Component
     */
    public static Component color(String text) {
        return component(text);
    }

    /**
     * Converts a Component to a text string using MiniMessage.
     *
     * @param component the Component to convert
     * @return the text string
     */
    public static String text(Component component) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        return miniMessage.serialize(component);
    }

    /**
     * Translates alternate color codes in a text string.
     *
     * @param text the text to translate
     * @return the translated text
     */
    @SuppressWarnings("deprecation")
    public static String legacyTranslate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Gets the ChatColor for a given legacy character code.
     *
     * @param input the character
     * @return the ChatColor
     */
    @SuppressWarnings("deprecation")
    public static ChatColor chatColor(String input) {
        char character;

        if (input.startsWith("&")) character = input.charAt(1);
        else character = input.charAt(0);

        return ChatColor.getByChar(character);
    }

    /**
     * Gets the string value of a ChatColor.
     *
     * @param color the ChatColor
     * @return the string
     */
    @SuppressWarnings("deprecation")
    public static String value(ChatColor color) {
        return color.toString();
    }

    /**
     * Gets a ChatColor from a hexadecimal string.
     *
     * @param hex the hexadecimal string
     * @return the ChatColor
     */
    @SuppressWarnings("deprecation")
    public static ChatColor hex(String hex) {
        if (!hex.startsWith("#")) return ChatColor.of("#" + hex);

        return ChatColor.of(hex);
    }
}