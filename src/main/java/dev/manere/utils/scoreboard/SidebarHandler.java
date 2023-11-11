package dev.manere.utils.scoreboard;

import dev.manere.utils.reflection.ReflectionUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * Lightweight packet-based scoreboard API for Bukkit plugins.
 * Basically the same as FastBoard. It's literally using the code of it, it's just made a tiny bit prettier.
 */
public abstract class SidebarHandler<T> {
    private static final Map<Class<?>, Field[]> PACKETS = new HashMap<>(8);

    /**
     * Please ignore the fact that ChatColor is deprecated. It's required.
     */
    @SuppressWarnings("deprecation")
    protected static final String[] COLOR_CODES = Arrays.stream(ChatColor.values())
            .map(Object::toString)
            .toArray(String[]::new);
    private static final VersionType VERSION_TYPE;
    private static final Class<?> CHAT_COMPONENT_CLASS;
    private static final Class<?> CHAT_FORMAT_ENUM;
    private static final Object RESET_FORMATTING;
    private static final MethodHandle PLAYER_CONNECTION;
    private static final MethodHandle SEND_PACKET;
    private static final MethodHandle PLAYER_GET_HANDLE;
    private static final ReflectionUtils.PacketConstructor PACKET_SB_OBJ;
    private static final ReflectionUtils.PacketConstructor PACKET_SB_DISPLAY_OBJ;
    private static final ReflectionUtils.PacketConstructor PACKET_SB_SCORE;
    private static final ReflectionUtils.PacketConstructor PACKET_SB_TEAM;
    private static final ReflectionUtils.PacketConstructor PACKET_SB_SERIALIZABLE_TEAM;
    private static final Class<?> DISPLAY_SLOT_TYPE;
    private static final Class<?> ENUM_SB_HEALTH_DISPLAY;
    private static final Class<?> ENUM_SB_ACTION;
    private static final Object SIDEBAR_DISPLAY_SLOT;
    private static final Object ENUM_SB_HEALTH_DISPLAY_INTEGER;
    private static final Object ENUM_SB_ACTION_CHANGE;
    private static final Object ENUM_SB_ACTION_REMOVE;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();

            if (ReflectionUtils.isRepackaged()) {
                VERSION_TYPE = VersionType.V1_17;
            } else if (ReflectionUtils.nmsOptionalClass(null, "ScoreboardServer$Action").isPresent()) {
                VERSION_TYPE = VersionType.V1_13;
            } else if (ReflectionUtils.nmsOptionalClass(null, "IScoreboardCriteria$EnumScoreboardHealthDisplay").isPresent()) {
                VERSION_TYPE = VersionType.V1_8;
            } else {
                VERSION_TYPE = VersionType.V1_7;
            }

            String gameProtocolPackage = "network.protocol.game";
            Class<?> craftPlayerClass = ReflectionUtils.obcClass("entity.CraftPlayer");
            Class<?> entityPlayerClass = ReflectionUtils.nmsClass("server.level", "EntityPlayer");
            Class<?> playerConnectionClass = ReflectionUtils.nmsClass("server.network", "PlayerConnection");
            Class<?> packetClass = ReflectionUtils.nmsClass("network.protocol", "Packet");
            Class<?> packetSbObjClass = ReflectionUtils.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardObjective");
            Class<?> packetSbDisplayObjClass = ReflectionUtils.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardDisplayObjective");
            Class<?> packetSbScoreClass = ReflectionUtils.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardScore");
            Class<?> packetSbTeamClass = ReflectionUtils.nmsClass(gameProtocolPackage, "PacketPlayOutScoreboardTeam");

            Class<?> sbTeamClass = VersionType.V1_17.isHigherOrEqual()
                    ? ReflectionUtils.innerClass(packetSbTeamClass, innerClass -> !innerClass.isEnum()) : null;

            Field playerConnectionField = Arrays.stream(entityPlayerClass.getFields())
                    .filter(field -> field.getType().isAssignableFrom(playerConnectionClass))
                    .findFirst().orElseThrow(NoSuchFieldException::new);

            Method sendPacketMethod = Stream.concat(
                    Arrays.stream(playerConnectionClass.getSuperclass().getMethods()), Arrays.stream(playerConnectionClass.getMethods()))
                    .filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == packetClass)
                    .findFirst().orElseThrow(NoSuchMethodException::new);

            Optional<Class<?>> displaySlotEnum = ReflectionUtils.nmsOptionalClass("world.scores", "DisplaySlot");
            CHAT_COMPONENT_CLASS = ReflectionUtils.nmsClass("network.chat", "IChatBaseComponent");
            CHAT_FORMAT_ENUM = ReflectionUtils.nmsClass(null, "EnumChatFormat");
            DISPLAY_SLOT_TYPE = displaySlotEnum.orElse(int.class);
            RESET_FORMATTING = ReflectionUtils.enumValueOf(CHAT_FORMAT_ENUM, "RESET", 21);
            SIDEBAR_DISPLAY_SLOT = displaySlotEnum.isPresent() ? ReflectionUtils.enumValueOf(DISPLAY_SLOT_TYPE, "SIDEBAR", 1) : 1;
            PLAYER_GET_HANDLE = lookup.findVirtual(craftPlayerClass, "getHandle", MethodType.methodType(entityPlayerClass));
            PLAYER_CONNECTION = lookup.unreflectGetter(playerConnectionField);
            SEND_PACKET = lookup.unreflect(sendPacketMethod);
            PACKET_SB_OBJ = ReflectionUtils.findPacketConstructor(packetSbObjClass, lookup);
            PACKET_SB_DISPLAY_OBJ = ReflectionUtils.findPacketConstructor(packetSbDisplayObjClass, lookup);
            PACKET_SB_SCORE = ReflectionUtils.findPacketConstructor(packetSbScoreClass, lookup);
            PACKET_SB_TEAM = ReflectionUtils.findPacketConstructor(packetSbTeamClass, lookup);
            PACKET_SB_SERIALIZABLE_TEAM = sbTeamClass == null ? null : ReflectionUtils.findPacketConstructor(sbTeamClass, lookup);

            for (Class<?> clazz : Arrays.asList(packetSbObjClass, packetSbDisplayObjClass, packetSbScoreClass, packetSbTeamClass, sbTeamClass)) {
                if (clazz == null) {
                    continue;
                }

                Field[] fields = Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> !Modifier.isStatic(field.getModifiers()))
                        .toArray(Field[]::new);

                for (Field field : fields) {
                    field.setAccessible(true);
                }

                PACKETS.put(clazz, fields);
            }

            if (VersionType.V1_8.isHigherOrEqual()) {
                String enumSbActionClass = VersionType.V1_13.isHigherOrEqual()
                        ? "ScoreboardServer$Action"
                        : "PacketPlayOutScoreboardScore$EnumScoreboardAction";

                ENUM_SB_HEALTH_DISPLAY = ReflectionUtils.nmsClass("world.scores.criteria", "IScoreboardCriteria$EnumScoreboardHealthDisplay");
                ENUM_SB_ACTION = ReflectionUtils.nmsClass("server", enumSbActionClass);
                ENUM_SB_HEALTH_DISPLAY_INTEGER = ReflectionUtils.enumValueOf(ENUM_SB_HEALTH_DISPLAY, "INTEGER", 0);
                ENUM_SB_ACTION_CHANGE = ReflectionUtils.enumValueOf(ENUM_SB_ACTION, "CHANGE", 0);
                ENUM_SB_ACTION_REMOVE = ReflectionUtils.enumValueOf(ENUM_SB_ACTION, "REMOVE", 1);
            } else {
                ENUM_SB_HEALTH_DISPLAY = null;
                ENUM_SB_ACTION = null;
                ENUM_SB_HEALTH_DISPLAY_INTEGER = null;
                ENUM_SB_ACTION_CHANGE = null;
                ENUM_SB_ACTION_REMOVE = null;
            }
        } catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }

    private final Player player;
    private final String uniqueId;

    private final List<T> lines = new ArrayList<>();
    private T title = emptyLine();

    private boolean deleted = false;

    /**
     * Creates a new scoreboard handler for the given player.
     *
     * @param player the player that owns this scoreboard
     */
    protected SidebarHandler(Player player) {
        this.player = Objects.requireNonNull(player, "player");
        this.uniqueId = "sidebar-" + Integer.toHexString(ThreadLocalRandom.current().nextInt());

        try {
            sendObjectivePacket(ObjectiveMode.CREATE);
            sendDisplayObjectivePacket();
        } catch (Throwable t) {
            throw new RuntimeException("Unable to create scoreboard", t);
        }
    }

    /**
     * Gets the current title displayed at the top of the scoreboard.
     *
     * @return the title text
     */
    public T title() {
        return this.title;
    }

    /**
     * Updates the title displayed at the top of the scoreboard.
     *
     * @param title the new title text
     */
    public void updateTitle(T title) {
        if (this.title.equals(Objects.requireNonNull(title, "title"))) {
            return;
        }

        this.title = title;

        try {
            sendObjectivePacket(ObjectiveMode.UPDATE);
        } catch (Throwable t) {
            throw new RuntimeException("Unable to update scoreboard title", t);
        }
    }

    /**
     * Gets an unmodifiable list of the lines currently displayed in the scoreboard.
     *
     * @return an unmodifiable list of lines
     */
    public List<T> lines() {
        return new ArrayList<>(this.lines);
    }

    /**
     * Gets the line at the specified position in the scoreboard.
     *
     * @param line the line number
     * @return the text of the line
     * @throws IllegalArgumentException if line is out of bounds
     */
    public T line(int line) {
        checkLineNumber(line, true, false);

        return this.lines.get(line);
    }

    /**
     * Updates the text of the line at the specified position.
     *
     * @param line the line number
     * @param text the new text for the line
     * @throws IllegalArgumentException if line is out of bounds
     */
    public synchronized void updateLine(int line, T text) {
        checkLineNumber(line, false, true);

        try {
            if (line < size()) {
                this.lines.set(line, text);

                sendLineChange(scoreByLine(line));
                return;
            }

            List<T> newLines = new ArrayList<>(this.lines);

            if (line > size()) {
                for (int i = size(); i < line; i++) {
                    newLines.add(emptyLine());
                }
            }

            newLines.add(text);

            updateLines(newLines);
        } catch (Throwable t) {
            throw new RuntimeException("Unable to update scoreboard lines", t);
        }
    }

    /**
     * Removes the line at the specified position from the scoreboard.
     *
     * @param line the line number
     * @throws IllegalArgumentException if line is out of bounds
     */
    public synchronized void removeLine(int line) {
        checkLineNumber(line, false, false);

        if (line >= size()) {
            return;
        }

        List<T> newLines = new ArrayList<>(this.lines);
        newLines.remove(line);
        updateLines(newLines);
    }

    /**
     * Updates all lines in the scoreboard to the given texts.
     *
     * @param lines the new contents of the scoreboard
     */
    @SafeVarargs
    public final void updateLines(T... lines) {
        updateLines(Arrays.asList(lines));
    }

    /**
     * Updates all lines in the scoreboard to the given texts.
     *
     * @param lines the new contents of the scoreboard
     */
    public synchronized void updateLines(Collection<T> lines) {
        Objects.requireNonNull(lines, "lines");
        checkLineNumber(lines.size(), false, true);

        List<T> oldLines = new ArrayList<>(this.lines);
        this.lines.clear();
        this.lines.addAll(lines);

        int linesSize = this.lines.size();

        try {
            if (oldLines.size() != linesSize) {
                List<T> oldLinesCopy = new ArrayList<>(oldLines);

                if (oldLines.size() > linesSize) {
                    for (int i = oldLinesCopy.size(); i > linesSize; i--) {
                        sendTeamPacket(i - 1, TeamMode.REMOVE);
                        sendScorePacket(i - 1, ScoreboardAction.REMOVE);

                        oldLines.remove(0);
                    }
                } else {
                    for (int i = oldLinesCopy.size(); i < linesSize; i++) {
                        sendScorePacket(i, ScoreboardAction.CHANGE);
                        sendTeamPacket(i, TeamMode.CREATE, null, null);
                    }
                }
            }

            for (int i = 0; i < linesSize; i++) {
                if (!Objects.equals(lineByScore(oldLines, i), lineByScore(i))) {
                    sendLineChange(i);
                }
            }
        } catch (Throwable t) {
            throw new RuntimeException("Unable to update scoreboard lines", t);
        }
    }

    /**
     * Gets the player associated with this scoreboard.
     *
     * @return the owning player
     */
    public Player player() {
        return this.player;
    }

    /**
     * Gets the unique ID of this scoreboard.
     *
     * @return the unique ID
     */
    public String uniqueId() {
        return this.uniqueId;
    }

    /**
     * Checks if this scoreboard is deleted.
     *
     * @return true if deleted, false otherwise
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Gets the number of lines currently in this scoreboard.
     *
     * @return the number of lines
     */
    public int size() {
        return this.lines.size();
    }

    /**
     * Deletes this scoreboard, removing it from the client.
     */
    public void delete() {
        try {
            for (int i = 0; i < this.lines.size(); i++) {
                sendTeamPacket(i, TeamMode.REMOVE);
            }

            sendObjectivePacket(ObjectiveMode.REMOVE);
        } catch (Throwable t) {
            throw new RuntimeException("Unable to delete scoreboard", t);
        }

        this.deleted = true;
    }

    /**
     * Sends a line change.
     *
     * @param score line of the score to change.
     */
    protected abstract void sendLineChange(int score) throws Throwable;

    /**
     * Converts {@code T} to a Minecraft component.
     *
     * @param value the value to convert.
     * @return the Minecraft component.
     */
    protected abstract Object toMinecraftComponent(T value) throws Throwable;

    /**
     * Serializes a line.
     *
     * @param value the value to serialize.
     * @return a String.
     */
    protected abstract String serializeLine(T value);

    /**
     * Returns an empty line.
     *
     * @return an empty line.
     */
    protected abstract T emptyLine();

    private void checkLineNumber(int line, boolean checkInRange, boolean checkMax) {
        if (line < 0) {
            throw new IllegalArgumentException("Line number must be positive");
        }

        if (checkInRange && line >= this.lines.size()) {
            throw new IllegalArgumentException("Line number must be under " + this.lines.size());
        }

        if (checkMax && line >= COLOR_CODES.length - 1) {
            throw new IllegalArgumentException("Line number is too high: " + line);
        }
    }

    protected int scoreByLine(int line) {
        return this.lines.size() - line - 1;
    }

    protected T lineByScore(int score) {
        return lineByScore(this.lines, score);
    }

    protected T lineByScore(List<T> lines, int score) {
        return score < lines.size() ? lines.get(lines.size() - score - 1) : null;
    }

    protected void sendObjectivePacket(ObjectiveMode mode) throws Throwable {
        Object packet = PACKET_SB_OBJ.invoke();

        field(packet, String.class, this.uniqueId);
        field(packet, int.class, mode.ordinal());

        if (mode != ObjectiveMode.REMOVE) {
            componentField(packet, this.title, 1);

            if (VersionType.V1_8.isHigherOrEqual()) {
                field(packet, ENUM_SB_HEALTH_DISPLAY, ENUM_SB_HEALTH_DISPLAY_INTEGER);
            }
        } else if (VERSION_TYPE == VersionType.V1_7) {
            field(packet, String.class, "", 1);
        }

        sendPacket(packet);
    }

    protected void sendDisplayObjectivePacket() throws Throwable {
        Object packet = PACKET_SB_DISPLAY_OBJ.invoke();

        field(packet, DISPLAY_SLOT_TYPE, SIDEBAR_DISPLAY_SLOT); // Position
        field(packet, String.class, this.uniqueId); // Score Name

        sendPacket(packet);
    }

    protected void sendScorePacket(int score, ScoreboardAction action) throws Throwable {
        Object packet = PACKET_SB_SCORE.invoke();

        field(packet, String.class, COLOR_CODES[score], 0); // Player Name

        if (VersionType.V1_8.isHigherOrEqual()) {
            Object enumAction = action == ScoreboardAction.REMOVE
                    ? ENUM_SB_ACTION_REMOVE : ENUM_SB_ACTION_CHANGE;
            field(packet, ENUM_SB_ACTION, enumAction);
        } else {
            field(packet, int.class, action.ordinal(), 1); // Action
        }

        if (action == ScoreboardAction.CHANGE) {
            field(packet, String.class, this.uniqueId, 1); // Objective Name
            field(packet, int.class, score); // Score
        }

        sendPacket(packet);
    }

    protected void sendTeamPacket(int score, TeamMode mode) throws Throwable {
        sendTeamPacket(score, mode, null, null);
    }

    protected void sendTeamPacket(int score, TeamMode mode, T prefix, T suffix) throws Throwable {
        if (mode == TeamMode.ADD_PLAYERS || mode == TeamMode.REMOVE_PLAYERS) {
            throw new UnsupportedOperationException();
        }

        Object packet = PACKET_SB_TEAM.invoke();

        field(packet, String.class, this.uniqueId + ':' + score); // Team name
        field(packet, int.class, mode.ordinal(), VERSION_TYPE == VersionType.V1_8 ? 1 : 0); // Update mode

        if (mode == TeamMode.REMOVE) {
            sendPacket(packet);
            return;
        }

        if (VersionType.V1_17.isHigherOrEqual()) {
            Object team = PACKET_SB_SERIALIZABLE_TEAM.invoke();
            // Since the packet is initialized with null values, we need to change more things.
            componentField(team, null, 0); // Display name
            field(team, CHAT_FORMAT_ENUM, RESET_FORMATTING); // Color
            componentField(team, prefix, 1); // Prefix
            componentField(team, suffix, 2); // Suffix
            field(team, String.class, "always", 0); // Visibility
            field(team, String.class, "always", 1); // Collisions
            field(packet, Optional.class, Optional.of(team));
        } else {
            componentField(packet, prefix, 2); // Prefix
            componentField(packet, suffix, 3); // Suffix
            field(packet, String.class, "always", 4); // Visibility for 1.8+
            field(packet, String.class, "always", 5); // Collisions for 1.9+
        }

        if (mode == TeamMode.CREATE) {
            field(packet, Collection.class, Collections.singletonList(COLOR_CODES[score])); // Players in the team
        }

        sendPacket(packet);
    }

    private void sendPacket(Object packet) throws Throwable {
        if (this.deleted) {
            throw new IllegalStateException("This FastBoard is deleted");
        }

        if (this.player.isOnline()) {
            Object entityPlayer = PLAYER_GET_HANDLE.invoke(this.player);
            Object playerConnection = PLAYER_CONNECTION.invoke(entityPlayer);
            SEND_PACKET.invoke(playerConnection, packet);
        }
    }

    private void field(Object object, Class<?> fieldType, Object value) throws ReflectiveOperationException {
        field(object, fieldType, value, 0);
    }

    private void field(Object packet, Class<?> fieldType, Object value, int count) throws ReflectiveOperationException {
        int i = 0;

        for (Field field : PACKETS.get(packet.getClass())) {
            if (field.getType() == fieldType && count == i++) {
                field.set(packet, value);
            }
        }
    }

    private void componentField(Object packet, T value, int count) throws Throwable {
        if (!VersionType.V1_13.isHigherOrEqual()) {
            String line = value != null ? serializeLine(value) : "";
            field(packet, String.class, line, count);
            return;
        }

        int i = 0;
        for (Field field : PACKETS.get(packet.getClass())) {
            if ((field.getType() == String.class || field.getType() == CHAT_COMPONENT_CLASS) && count == i++) {
                field.set(packet, toMinecraftComponent(value));
            }
        }
    }

    public enum ObjectiveMode {
        CREATE, REMOVE, UPDATE
    }

    public enum TeamMode {
        CREATE, REMOVE, UPDATE, ADD_PLAYERS, REMOVE_PLAYERS
    }

    public enum ScoreboardAction {
        CHANGE, REMOVE
    }

    enum VersionType {
        V1_7, V1_8, V1_13, V1_17;

        public boolean isHigherOrEqual() {
            return VERSION_TYPE.ordinal() >= ordinal();
        }
    }
}