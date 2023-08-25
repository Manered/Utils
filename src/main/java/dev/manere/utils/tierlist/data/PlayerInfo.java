package dev.manere.utils.tierlist.data;

import com.google.gson.Gson;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Represents player information obtained from an external API for tier list data.
 */
public record PlayerInfo(
        String uuid, String name, Map<String, Ranking> rankings, String region, int points, int overall, List<Badge> badges
) {

    /**
     * Represents a player's ranking details.
     */
    public record Ranking(int tier, int pos, @Nullable Integer peak_tier, @Nullable Integer peak_pos, long attained, boolean retired) {
    }

    /**
     * Represents a badge earned by a player.
     */
    public record Badge(String title, String desc) {
    }

    private static final String ENDPOINT = "https://mctiers.com/api/profile/%s";

    /**
     * Retrieves player information asynchronously from the specified endpoint.
     *
     * @param client The HttpClient instance to use for the request.
     * @param uuid   The UUID of the player.
     * @return A CompletableFuture that resolves to the retrieved PlayerInfo.
     */
    public static CompletableFuture<PlayerInfo> get(HttpClient client, UUID uuid) {
        URI formattedEndpoint = URI.create(ENDPOINT.formatted(uuidStr(uuid)));
        final HttpRequest request = HttpRequest.newBuilder(formattedEndpoint).GET().build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> new Gson().fromJson(s, PlayerInfo.class));
    }

    /**
     * Converts a UUID to its hexadecimal representation as a String.
     *
     * @param uuid The UUID to convert.
     * @return The hexadecimal representation of the UUID.
     */
    private static String uuidStr(UUID uuid) {
        return Long.toUnsignedString(uuid.getMostSignificantBits(), 16) + Long.toUnsignedString(uuid.getLeastSignificantBits(), 16);
    }
}
