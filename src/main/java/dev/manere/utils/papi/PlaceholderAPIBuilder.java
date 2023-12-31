package dev.manere.utils.papi;

import dev.manere.utils.library.Utils;
import dev.manere.utils.prettify.ListPrettify;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class PlaceholderAPIBuilder {
    private final String prefix;
    private String author;
    private String version;
    private BiFunction<Player, String, String> playerResult;
    private BiFunction<OfflinePlayer, String, String> offlinePlayerResult;

    /**
     * Private constructor to create a PlaceholderAPIBuilder with the specified identifier.
     *
     * @param prefix The identifier for the PlaceholderAPI expansion.
     */
    /* I hate you paper (why would you deprecate JavaPlugin#getDescription()
       and then make the alternative (PluginMeta) "unstable" */
    @SuppressWarnings("UnstableApiUsage")
    public PlaceholderAPIBuilder(@NotNull String prefix) {
        this.prefix = prefix;
        this.version = Utils.plugin().getPluginMeta().getVersion();
        this.playerResult = null;
        this.offlinePlayerResult = null;
        this.author = ListPrettify.strings(Utils.plugin()
                .getPluginMeta()
                .getAuthors());
    }

    /**
     * Creates a new PlaceholderAPIBuilder with the specified identifier.
     *
     * @param identifier The identifier for the PlaceholderAPI expansion.
     * @return A new PlaceholderAPIBuilder instance.
     */
    public static @NotNull PlaceholderAPIBuilder of(@NotNull String identifier) {
        return new PlaceholderAPIBuilder(identifier);
    }

    /**
     * Sets the author of the PlaceholderAPI expansion.
     *
     * @param author The author of the PlaceholderAPI expansion.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public @NotNull PlaceholderAPIBuilder author(@NotNull String author) {
        this.author = author;
        return this;
    }

    /**
     * Sets the version of the PlaceholderAPI expansion.
     *
     * @param version The version of the PlaceholderAPI expansion.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public @NotNull PlaceholderAPIBuilder version(@NotNull String version) {
        this.version = version;
        return this;
    }

    /**
     * Sets the function to handle placeholder requests for online players.
     *
     * @param result The function to handle placeholder requests for online players.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public @NotNull PlaceholderAPIBuilder onlineRequest(@Nullable BiFunction<Player, String, String> result) {
        this.playerResult = result;
        return this;
    }

    /**
     * Sets the function to handle placeholder requests for offline players.
     *
     * @param result The function to handle placeholder requests for offline players.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public @NotNull PlaceholderAPIBuilder offlineRequest(@Nullable BiFunction<OfflinePlayer, String, String> result) {
        this.offlinePlayerResult = result;
        return this;
    }

    /**
     * Sets the function to handle placeholder requests for both online and offline players.
     *
     * @param onlineResult The function to handle placeholder requests for online players.
     * @param offlineResult The function to handle placeholder requests for offline players.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public @NotNull PlaceholderAPIBuilder request(
            @Nullable BiFunction<Player, String, String> onlineResult,
            @Nullable BiFunction<OfflinePlayer, String, String> offlineResult
    ) {
        this.playerResult = onlineResult;
        this.offlinePlayerResult = offlineResult;
        return this;
    }

    /**
     * Builds and registers the PlaceholderAPI expansion.
     *
     * @return The PlaceholderExpansion instance if registration is successful, otherwise null.
     */
    public @Nullable PlaceholderExpansion build() {
        PlaceholderExpansion expansion = new PlaceholderExpansion() {

            /**
             * {@inheritDoc}
             */
            @Override
            public @NotNull String getIdentifier() {
                return prefix;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public @NotNull String getAuthor() {
                return author;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public @NotNull String getVersion() {
                return version;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String onPlaceholderRequest(Player player, @NotNull String params) {
                if (playerResult != null) {
                    return playerResult.apply(player, params);
                }

                return null;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
                if (offlinePlayerResult != null) {
                    return offlinePlayerResult.apply(offlinePlayer, params);
                }

                return null;
            }
        };

        if (expansion.canRegister() && !expansion.isRegistered()) {
            expansion.register();
            return expansion;
        } else {
            return null;
        }
    }
}
