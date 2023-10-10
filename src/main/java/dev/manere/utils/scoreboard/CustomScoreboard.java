package dev.manere.utils.scoreboard;

import dev.manere.utils.reflection.ReflectionUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Objects;

/**
 * A custom implementation of a scoreboard for a specific player.
 * This class extends the ScoreboardBase class, providing additional functionality.
 */
public class CustomScoreboard extends ScoreboardBase<String> {

    private static final MethodHandle MESSAGE_FROM_STRING;
    private static final Object EMPTY_MESSAGE;

    // (Static initialization block)
    // MESSAGE_FROM_STRING: Method handle for converting a String to a chat message
    // EMPTY_MESSAGE: An empty chat message object
    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            Class<?> craftChatMessageClass = ReflectionUtils.obcClass("util.CraftChatMessage");
            MESSAGE_FROM_STRING = lookup.unreflect(craftChatMessageClass.getMethod("fromString", String.class));
            EMPTY_MESSAGE = Array.get(MESSAGE_FROM_STRING.invoke(""), 0);
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    /**
     * Constructs a CustomScoreboard for a specific player.
     *
     * @param player The player for whom the scoreboard is being created.
     */
    public CustomScoreboard(Player player) {
        super(player);
    }

    /**
     * Sets the title of the scoreboard.
     *
     * @param title The title to be set. Must not be null.
     * @return The CustomScoreboard instance with the updated title.
     * @throws IllegalArgumentException If the title is longer than 32 characters (prior to Minecraft 1.13).
     */
    @Override
    public ScoreboardBase<String> setTitle(String title) {
        Objects.requireNonNull(title, "title");

        if (!VersionType.V1_13.isHigherOrEqual() && title.length() > 32) {
            throw new IllegalArgumentException("Title is longer than 32 chars");
        }

        super.setTitle(title);
        return this;
    }

    /**
     * Sets the lines of the scoreboard.
     *
     * @param lines The lines to be set. Must not be null.
     * @return The CustomScoreboard instance with the updated lines.
     * @throws IllegalArgumentException If any line is longer than 30 characters (prior to Minecraft 1.13).
     */
    @Override
    public ScoreboardBase<String> setLines(String... lines) {
        Objects.requireNonNull(lines, "lines");

        if (!VersionType.V1_13.isHigherOrEqual()) {
            int lineCount = 0;
            for (String s : lines) {
                if (s != null && s.length() > 30) {
                    throw new IllegalArgumentException("Line " + lineCount + " is longer than 30 chars");
                }

                lineCount++;
            }
        }

        super.setLines(lines);
        return this;
    }

    /**
     * Sends a change in a scoreboard line to the player.
     *
     * @param score The score corresponding to the line being changed.
     * @throws Throwable If an error occurs during the operation.
     */
    @Override
    protected void sendLineChange(int score) throws Throwable {
        int maxLength = hasLinesMaxLength() ? 16 : 1024;
        String line = getLineByScore(score);
        String prefix;
        String suffix = "";

        if (line == null || line.isEmpty()) {
            prefix = COLOR_CODES[score] + ChatColor.RESET;
        } else if (line.length() <= maxLength) {
            prefix = line;
        } else {
            // Prevent splitting color codes
            int index = line.charAt(maxLength - 1) == ChatColor.COLOR_CHAR
                    ? (maxLength - 1) : maxLength;
            prefix = line.substring(0, index);
            String suffixTmp = line.substring(index);
            ChatColor chatColor = null;

            if (suffixTmp.length() >= 2 && suffixTmp.charAt(0) == ChatColor.COLOR_CHAR) {
                chatColor = ChatColor.getByChar(suffixTmp.charAt(1));
            }

            String color = ChatColor.getLastColors(prefix);
            boolean addColor = chatColor == null || chatColor.isFormat();

            suffix = (addColor ? (color.isEmpty() ? ChatColor.RESET.toString() : color) : "") + suffixTmp;
        }

        if (prefix.length() > maxLength || suffix.length() > maxLength) {
            prefix = prefix.substring(0, maxLength);
            suffix = suffix.substring(0, maxLength);
        }

        sendTeamPacket(score, TeamMode.UPDATE, prefix, suffix);
    }

    /**
     * Converts a string to a Minecraft component object.
     *
     * @param line The string to be converted.
     * @return The corresponding Minecraft component object.
     * @throws Throwable If an error occurs during the operation.
     */
    @Override
    protected Object toMinecraftComponent(String line) throws Throwable {
        if (line == null || line.isEmpty()) {
            return EMPTY_MESSAGE;
        }

        return Array.get(MESSAGE_FROM_STRING.invoke(line), 0);
    }

    /**
     * Provides the representation of an empty line in the scoreboard.
     *
     * @return An empty string.
     */
    @Override
    protected String emptyLine() {
        return "";
    }

    /**
     * Checks if the server version supports maximum line length.
     *
     * @return True if the server version is not higher or equal to 1.13, false otherwise.
     */
    protected boolean hasLinesMaxLength() {
        return !VersionType.V1_13.isHigherOrEqual();
    }
}
