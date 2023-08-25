package dev.manere.utils.color;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    /**
     * Translates the color codes in a string to the appropriate format for display.
     *
     * @param message the message string containing color codes
     * @return the translated message string
     */
    public static String translate(String message) {
        final Pattern colorPattern = Pattern.compile("(#[A-Fa-f0-9]{6})|(&[0-9a-fk-or])");
        Matcher matcher = colorPattern.matcher(message);
        StringBuilder buffer = new StringBuilder(message.length() + 4 * 8);

        // Track the current active color
        String activeColor = null;

        while (matcher.find()) {
            String group = matcher.group();

            if (group.startsWith("#")) {
                String hexCode = group.substring(1);

                // Convert the hex color code to Minecraft formatting codes
                StringBuilder colorCode = new StringBuilder("§x");
                for (int i = 0; i < 6; i += 2) {
                    colorCode.append("§").append(hexCode.charAt(i)).append("§").append(hexCode.charAt(i + 1));
                }

                if (!colorCode.toString().equals(activeColor)) {
                    matcher.appendReplacement(buffer, colorCode.toString());
                    activeColor = colorCode.toString();
                }
            } else if (group.startsWith("&")) {
                // Use Minecraft color code as is
                String colorCode = group.substring(0, 2).replace('&', '§');
                if (!colorCode.equals(activeColor)) {
                    matcher.appendReplacement(buffer, colorCode);
                    activeColor = colorCode;
                }
            }
        }

        return matcher.appendTail(buffer).toString();
    }

    /**
     * Translates an array of strings containing color codes to the appropriate format for display.
     *
     * @param lore the array of lore strings containing color codes
     * @return the translated array of lore strings
     */
    public static String[] translate(String... lore) {
        if (lore == null) {
            return null;
        }

        String[] translatedLore = new String[lore.length];

        for (int i = 0; i < lore.length; i++) {
            if (lore[i] != null) {
                translatedLore[i] = translate(lore[i]);
            }
        }

        return translatedLore;
    }

    /**
     * Replaces color code placeholders in the input string with actual ChatColor values.
     * Also translates standard Minecraft color codes.
     *
     * @param message the message string with color code placeholders
     * @return the message string with actual color formatting
     */
    public static String color(String message) {
        // Replace <color code name> with the actual color code using ChatColor
        message = message.replaceAll("<black>", ChatColor.BLACK.toString());
        message = message.replaceAll("<dark_blue>", ChatColor.DARK_BLUE.toString());
        message = message.replaceAll("<dark_green>", ChatColor.DARK_GREEN.toString());
        message = message.replaceAll("<dark_aqua>", ChatColor.DARK_AQUA.toString());
        message = message.replaceAll("<dark_light_blue>", ChatColor.DARK_AQUA.toString());
        message = message.replaceAll("<dark_red>", ChatColor.DARK_RED.toString());
        message = message.replaceAll("<dark_purple>", ChatColor.DARK_PURPLE.toString());
        message = message.replaceAll("<dark_pink>", ChatColor.DARK_PURPLE.toString());
        message = message.replaceAll("<gold>", ChatColor.GOLD.toString());
        message = message.replaceAll("<dark_yellow>", ChatColor.GOLD.toString());
        message = message.replaceAll("<gray>", ChatColor.GRAY.toString());
        message = message.replaceAll("<grey>", ChatColor.GRAY.toString());
        message = message.replaceAll("<dark_gray>", ChatColor.DARK_GRAY.toString());
        message = message.replaceAll("<dark_grey>", ChatColor.DARK_GRAY.toString());
        message = message.replaceAll("<blue>", ChatColor.BLUE.toString());
        message = message.replaceAll("<green>", ChatColor.GREEN.toString());
        message = message.replaceAll("<lime>", ChatColor.GREEN.toString());
        message = message.replaceAll("<aqua>", ChatColor.AQUA.toString());
        message = message.replaceAll("<light_blue>", ChatColor.AQUA.toString());
        message = message.replaceAll("<red>", ChatColor.RED.toString());
        message = message.replaceAll("<pink>", ChatColor.LIGHT_PURPLE.toString());
        message = message.replaceAll("<purple>", ChatColor.LIGHT_PURPLE.toString());
        message = message.replaceAll("<light_purple>", ChatColor.LIGHT_PURPLE.toString());
        message = message.replaceAll("<light_pink>", ChatColor.LIGHT_PURPLE.toString());
        message = message.replaceAll("<magenta>", ChatColor.LIGHT_PURPLE.toString());
        message = message.replaceAll("<yellow>", ChatColor.YELLOW.toString());
        message = message.replaceAll("<light_yellow>", ChatColor.YELLOW.toString());
        message = message.replaceAll("<white>", ChatColor.WHITE.toString());

        // Replace <#hex> with the actual hex color code using ChatColor
        message = message.replaceAll("<#([A-Fa-f0-9]{6})>", ChatColor.COLOR_CHAR + "x$1");

        // Replace special formatting tags
        message = message.replaceAll("<bold>", ChatColor.BOLD.toString());
        message = message.replaceAll("<reset>", ChatColor.RESET.toString());
        message = message.replaceAll("<underline>", ChatColor.UNDERLINE.toString());
        message = message.replaceAll("<italic>", ChatColor.ITALIC.toString());
        message = message.replaceAll("<strikethrough>", ChatColor.STRIKETHROUGH.toString());
        message = message.replaceAll("<strike>", ChatColor.STRIKETHROUGH.toString());
        message = message.replaceAll("<obfuscated>", ChatColor.MAGIC.toString());
        message = message.replaceAll("<magic>", ChatColor.MAGIC.toString());
        message = message.replaceAll("<random>", ChatColor.MAGIC.toString());

        // Translate color codes
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }
}