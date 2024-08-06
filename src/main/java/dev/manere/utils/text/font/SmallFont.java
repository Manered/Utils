package dev.manere.utils.text.font;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for converting text to a small font representation using a character mapping.
 */
public class SmallFont {
    /**
     * A map that holds the character mapping for converting normal characters to small font characters.
     */
    private static final Map<Character, Character> smallFontMap = new HashMap<>();

    // Initializing the character mapping
    static {
        smallFontMap.put('a' , 'ᴀ');
        smallFontMap.put('b' , 'ʙ');
        smallFontMap.put('c' , 'ᴄ');
        smallFontMap.put('d' , 'ᴅ');
        smallFontMap.put('e' , 'ᴇ');
        smallFontMap.put('f' , 'ꜰ');
        smallFontMap.put('g' , 'ɢ');
        smallFontMap.put('h' , 'ʜ');
        smallFontMap.put('i' , 'ɪ');
        smallFontMap.put('j' , 'ᴊ');
        smallFontMap.put('k' , 'ᴋ');
        smallFontMap.put('l' , 'ʟ');
        smallFontMap.put('m' , 'ᴍ');
        smallFontMap.put('n' , 'ɴ');
        smallFontMap.put('o' , 'ᴏ');
        smallFontMap.put('p' , 'ᴘ');
        smallFontMap.put('q' , 'ꞯ');
        smallFontMap.put('r' , 'ʀ');
        smallFontMap.put('s' , 'ꜱ');
        smallFontMap.put('t' , 'ᴛ');
        smallFontMap.put('u' , 'ᴜ');
        smallFontMap.put('v' , 'ᴠ');
        smallFontMap.put('w' , 'ᴡ');
        smallFontMap.put('x' , 'x');
        smallFontMap.put('y' , 'ʏ');
        smallFontMap.put('z' , 'ᴢ');
        smallFontMap.put('{' , '{');
        smallFontMap.put('|' , '|');
        smallFontMap.put('}' , '}');
        smallFontMap.put('~' , '˜');
    }

    /**
     * Converts a given text to its small font representation.
     *
     * @param text The input text to be converted.
     * @return The converted text in small font representation.
     */
    public static String convert(String text) {
        text = text.toLowerCase();
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (smallFontMap.containsKey(character)) {
                result.append(smallFontMap.get(character));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}
