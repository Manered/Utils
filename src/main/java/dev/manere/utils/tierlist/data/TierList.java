package dev.manere.utils.tierlist.data;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Represents tier list data obtained from an external API.
 */
public record TierList(List<List<List<JsonPrimitive>>> rankings, Map<String, ShortPlayerInfo> players) {
    /**
     * Represents a summary of player information for tier lists.
     */
    public record ShortPlayerInfo(String name, String region, int points) {

    }

    private static final String ENDPOINT = "https://mctiers.com/api/tier/%s?count=32767";

    /**
     * Retrieves tier list information asynchronously from the specified endpoint.
     *
     * @param client The HttpClient instance to use for the request.
     * @param mode   The mode or category for which to retrieve tier list data.
     * @return A CompletableFuture that resolves to the retrieved TierList.
     */
    public static CompletableFuture<TierList> tierList(HttpClient client, String mode) {
        URI formattedEndpoint = URI.create(ENDPOINT.formatted(mode));
        final HttpRequest request = HttpRequest.newBuilder(formattedEndpoint).GET().build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(s -> new Gson().fromJson(s, TierList.class));
    }

    /**
     * Retrieves a map of player UUIDs and their corresponding tiers.
     *
     * @return A map where UUIDs are keys and their respective tiers are values.
     */
    public Map<UUID, String> tiers() {
        HashMap<UUID, String> map = new HashMap<>();

        for (int tier = 0; tier < rankings.size(); tier++) {
            List<List<JsonPrimitive>> tierList = rankings.get(tier);
            for (List<JsonPrimitive> player : tierList) {
                UUID uuid = uuid(player.get(0).getAsString());
                boolean high = player.get(1).getAsByte() == 0;
                String tierString = "%cT%d".formatted(high ? 'H' : 'L', tier + 1);
                map.put(uuid, tierString);
            }
        }

        return map;
    }

    /**
     * Converts a hexadecimal string representation to a UUID.
     *
     * @param s The hexadecimal string to convert.
     * @return The UUID representation of the input string.
     */
    private static UUID uuid(String s) {
        long mostSig = Long.parseUnsignedLong(s.substring(0, 16), 16);
        long leastSig = Long.parseUnsignedLong(s.substring(16), 16);
        return new UUID(mostSig, leastSig);
    }
}
