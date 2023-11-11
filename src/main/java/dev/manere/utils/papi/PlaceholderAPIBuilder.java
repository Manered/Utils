package dev.manere.utils.papi;

import dev.manere.utils.library.Utils;
import dev.manere.utils.prettify.ListPrettify;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class PlaceholderAPIBuilder {

    private final String identifier;
    private String author;
    private String version;
    private BiFunction<Player, String, String> playerResult;
    private BiFunction<OfflinePlayer, String, String> offlinePlayerResult;

    /**
     * Private constructor to create a PlaceholderAPIBuilder with the specified identifier.
     *
     * @param identifier The identifier for the PlaceholderAPI expansion.
     */
    /* I hate you paper (why would you deprecate JavaPlugin#getDescription() and then make the alternative (PluginMeta) "unstable" */
    @SuppressWarnings("UnstableApiUsage")
    public PlaceholderAPIBuilder(String identifier) {
        this.identifier = identifier;
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
    public static PlaceholderAPIBuilder of(String identifier) {
        return new PlaceholderAPIBuilder(identifier);
    }

    /**
     * Sets the author of the PlaceholderAPI expansion.
     *
     * @param author The author of the PlaceholderAPI expansion.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public PlaceholderAPIBuilder author(String author) {
        this.author = author;
        return this;
    }

    /**
     * Sets the version of the PlaceholderAPI expansion.
     *
     * @param version The version of the PlaceholderAPI expansion.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public PlaceholderAPIBuilder version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Sets the function to handle placeholder requests for online players.
     *
     * @param playerResult The function to handle placeholder requests for online players.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public PlaceholderAPIBuilder offlineResult(BiFunction<Player, String, String> playerResult) {
        this.playerResult = playerResult;
        return this;
    }

    /**
     * Sets the function to handle placeholder requests for offline players.
     *
     * @param offlinePlayerResult The function to handle placeholder requests for offline players.
     * @return The updated PlaceholderAPIBuilder instance.
     */
    public PlaceholderAPIBuilder onlineResult(BiFunction<OfflinePlayer, String, String> offlinePlayerResult) {
        this.offlinePlayerResult = offlinePlayerResult;
        return this;
    }

    /**
     * Builds and registers the PlaceholderAPI expansion.
     *
     * @return The PlaceholderExpansion instance if registration is successful, otherwise null.
     */
    public PlaceholderExpansion build() {
        PlaceholderExpansion expansion = new PlaceholderExpansion() {

            /**
             * {@inheritDoc}
             */
            @Override
            public @NotNull String getIdentifier() {
                return identifier;
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
