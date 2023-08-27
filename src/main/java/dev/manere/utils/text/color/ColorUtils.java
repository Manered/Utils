package dev.manere.utils.text.color;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    /**
     * Translates the color codes in a string to the appropriate format for display.
     *
     * @param text the text string containing color codes
     * @return the translated text string
     */
    public static String colorOld(String text) {
        final Pattern colorPattern = Pattern.compile("(#[A-Fa-f0-9]{6})|(&[0-9a-fk-or])");
        Matcher matcher = colorPattern.matcher(text);
        StringBuilder buffer = new StringBuilder(text.length() + 4 * 8);

        // Track the current active color
        String activeColor = null;

        while (matcher.find()) {
            String group = matcher.group();

            if (group.startsWith("#")) {
                String hexCode = group.substring(2);

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

    public static String colorHex(String text) {
        final Pattern colorPattern = Pattern.compile("(<#[A-Fa-f0-9]{6}>)|(&[0-9a-fk-or])");
        Matcher matcher = colorPattern.matcher(text);
        StringBuilder buffer = new StringBuilder(text.length() + 4 * 8);

        // Track the current active color
        String activeColor = null;

        while (matcher.find()) {
            String group = matcher.group();

            if (group.startsWith("<#")) {
                String hexCode = group.substring(2, group.length() - 1);

                // Convert the hex color code to Minecraft formatting codes
                StringBuilder colorCode = new StringBuilder("§x");
                for (int i = 0; i < 6; i += 2) {
                    colorCode.append("§").append(hexCode.charAt(i)).append("§").append(hexCode.charAt(i + 1));
                }

                if (!colorCode.toString().equals(activeColor)) {
                    matcher.appendReplacement(buffer, colorCode.toString());
                    activeColor = colorCode.toString();
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
    public static String[] colorOld(String... lore) {
        if (lore == null) {
            return null;
        }

        String[] translatedLore = new String[lore.length];

        for (int i = 0; i < lore.length; i++) {
            if (lore[i] != null) {
                translatedLore[i] = colorOld(lore[i]);
            }
        }

        return translatedLore;
    }

    /**
     * Replaces color code placeholders in the input string with actual ChatColor values.
     * Also translates standard Minecraft color codes.
     *
     * @param text the text string with color code placeholders
     * @return the text string with actual color formatting
     */
    public static String color(String text) {
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

        // Replace <#hex> with the actual hex color code using ChatColor
        text = colorHex(text);

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

        // Translate color codes
        text = ChatColor.translateAlternateColorCodes('&', text);

        return text;
    }
}