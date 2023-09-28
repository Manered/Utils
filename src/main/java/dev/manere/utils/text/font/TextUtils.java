package dev.manere.utils.text.font;

import dev.manere.utils.text.color.ColorUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.util.StringUtil;

/**
 * Utility class for text manipulation and formatting.
 */
public class TextUtils {
    private final static int CENTER_PX = 154;

    /**
     * Centers the provided text by adding spaces to the beginning.
     *
     * @param text The text to be centered.
     * @return The centered text.
     */
    public static String centeredText(String text) {
        text = ColorUtils.color(text);

        int textPxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char character : text.toCharArray()){
            if (character == '§') {
                previousCode = true;
            } else {
                if (previousCode) {
                    previousCode = false;

                    isBold = character == 'l' || character == 'L';
                } else {
                    FontInfo fontInfo = FontInfo.getFontInfo(character);
                    textPxSize += isBold ? fontInfo.getBoldLength() : fontInfo.getLength();
                    textPxSize++;
                }
            }
        }

        int halvedTextSize = textPxSize / 2;
        int toCompensate = CENTER_PX - halvedTextSize;
        int spaceLength = FontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder builder = new StringBuilder();

        while (compensated < toCompensate) {
            builder.append(" ");
            compensated += spaceLength;
        }

        return builder + text;
    }
}
