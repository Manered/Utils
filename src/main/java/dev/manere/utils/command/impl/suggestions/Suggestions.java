package dev.manere.utils.command.impl.suggestions;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.elements.impl.ElementsImpl;
import dev.manere.utils.server.Servers;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents Suggestions of a command completion.
 */
public class Suggestions {
    private final ElementsImpl<Suggestion> internal;

    private Suggestions() {
        this.internal = Elements.of(Suggestion.class);
    }

    private Suggestions(@NotNull ElementsImpl<Suggestion> suggestions) {
        this.internal = suggestions;
    }

    public static @NotNull Suggestions wrap(@Nullable List<String> toWrap) {
        if (toWrap == null) {
            toWrap = new ArrayList<>();
        }

        List<Suggestion> suggestions = new ArrayList<>();

        for (String string : toWrap) {
            suggestions.add(Suggestion.text(string));
        }

        return Suggestions.of(suggestions);
    }

    /**
     * Constructs an empty Suggestions object.
     * @return An empty Suggestions object.
     */
    public static @NotNull Suggestions empty() {
        return new Suggestions();
    }

    /**
     * Constructs an empty Suggestions object.
     * @return An empty Suggestions object.
     */
    public static @NotNull Suggestions of() {
        return Suggestions.empty();
    }

    /**
     * Constructs a Suggestions object with the given suggestion.
     * @param suggestion The suggestion to add.
     * @return A Suggestions object containing the suggestion.
     */
    public static @NotNull Suggestions of(@NotNull Suggestion suggestion) {
        return new Suggestions(Elements.of(List.of(suggestion)));
    }

    /**
     * Constructs a Suggestions object with the given raw text suggestion.
     * @param rawSuggestion The raw text suggestion to add.
     * @return A Suggestions object containing the suggestion.
     */
    public static @NotNull Suggestions of(@NotNull String rawSuggestion) {
        return new Suggestions(Elements.of(List.of(Suggestion.text(rawSuggestion))));
    }

    /**
     * Constructs a Suggestions object with the given list of suggestions.
     * @param suggestions The list of suggestions to add.
     * @return A Suggestions object containing the suggestions.
     */
    public static @NotNull Suggestions of(@NotNull List<Suggestion> suggestions) {
        return new Suggestions(Elements.of(suggestions));
    }

    /**
     * Constructs a Suggestions object with the given collection of suggestions.
     * @param suggestions The collection of suggestions to add.
     * @return A Suggestions object containing the suggestions.
     */
    public static @NotNull Suggestions of(@NotNull Collection<Suggestion> suggestions) {
        return new Suggestions(Elements.of(suggestions));
    }

    /**
     * Constructs a Suggestions object with the given array of suggestions.
     * @param suggestions The array of suggestions to add.
     * @return A Suggestions object containing the suggestions.
     */
    public static @NotNull Suggestions of(@NotNull Suggestion... suggestions) {
        return new Suggestions(Elements.of(suggestions));
    }

    /**
     * Constructs a Suggestions object with the given array of raw text suggestions.
     * @param rawSuggestions The array of raw text suggestions to add.
     * @return A Suggestions object containing the suggestions.
     */
    public static @NotNull Suggestions of(@NotNull String... rawSuggestions) {
        List<Suggestion> suggestions = new ArrayList<>();

        for (String string : rawSuggestions) {
            suggestions.add(Suggestion.text(string));
        }

        return Suggestions.of(suggestions);
    }

    /**
     * Constructs a Suggestions object with online player names.
     * @return A Suggestions object containing online player names.
     */
    public static @NotNull Suggestions players() {
        List<Suggestion> suggestionsList = new ArrayList<>();

        for (String name : Servers.onlineNames()) {
            suggestionsList.add(Suggestion.text(name));
        }

        return Suggestions.of(suggestionsList);
    }

    /**
     * Constructs a Suggestions object with online player names except the given player.
     * @param player The player name to exclude.
     * @return A Suggestions object containing online player names except the given player.
     */
    public static @NotNull Suggestions playersWithout(@NotNull Player player) {
        return Suggestions.playersWithout(player.getName());
    }

    /**
     * Constructs a Suggestions object with online player names except the given player name.
     * @param player The player name to exclude.
     * @return A Suggestions object containing online player names except the given player name.
     */
    public static @NotNull Suggestions playersWithout(@NotNull String player) {
        List<Suggestion> suggestionsList = new ArrayList<>();

        for (String name : Servers.onlineNames()) {
            suggestionsList.add(Suggestion.text(name));
        }

        suggestionsList.remove(Suggestion.text(player));

        return Suggestions.of(suggestionsList);
    }

    /**
     * Constructs a Suggestions object with online player names that have the given permission.
     * @param permission The permission to filter by.
     * @return A Suggestions object containing online player names with the permission.
     */
    public static @NotNull Suggestions playersWithPermission(@NotNull String permission) {
        List<Suggestion> suggestionsList = new ArrayList<>();

        for (Player player : Servers.online()) {
            if (player.hasPermission(permission)) {
                suggestionsList.add(Suggestion.text(player.getName()));
            }
        }

        return Suggestions.of(suggestionsList);
    }

    /**
     * Constructs a Suggestions object with number suggestions up to the given max.
     * @param max The maximum number to include.
     * @return A Suggestions object containing number suggestions.
     */
    public static @NotNull Suggestions numbers(int max) {
        List<Integer> integerList = new ArrayList<>();
        List<Suggestion> suggestionsList = new ArrayList<>();

        for (int number = 0; number <= max; number++) {
            integerList.add(number);
        }

        for (int number : integerList) {
            suggestionsList.add(Suggestion.number(number));
        }

        return Suggestions.of(suggestionsList);
    }

    /**
     * Constructs a Suggestions object with number suggestions between the given min and max.
     * @param min The minimum number to include.
     * @param max The maximum number to include.
     * @return A Suggestions object containing number suggestions between min and max.
     */
    public static @NotNull Suggestions numbers(int min, int max) {
        List<Integer> integerList = new ArrayList<>();
        List<Suggestion> suggestionsList = new ArrayList<>();

        for (int number = min; number <= max; number++) {
            integerList.add(number);
        }

        for (int number : integerList) {
            suggestionsList.add(Suggestion.number(number));
        }

        return Suggestions.of(suggestionsList);
    }

    /**
     * Adds the given suggestions to this Suggestions object.
     * @param suggestions The suggestions to add.
     * @return This Suggestions object.
     */
    @CanIgnoreReturnValue
    public @NotNull Suggestions add(@NotNull Suggestion... suggestions) {
        for (Suggestion suggestion : suggestions) {
            add(suggestion);
        }

        return this;
    }

    /**
     * Adds the given collection of suggestions to this Suggestions object.
     * @param suggestions The collection of suggestions to add.
     * @return This Suggestions object.
     */
    @CanIgnoreReturnValue
    public @NotNull Suggestions add(@NotNull Collection<Suggestion> suggestions) {
        return add(suggestions.toArray(new Suggestion[0]));
    }

    /**
     * Adds the given suggestion to this Suggestions object.
     * @param suggestion The suggestion to add.
     * @return This Suggestions object.
     */
    @CanIgnoreReturnValue
    public @NotNull Suggestions add(@NotNull Suggestion suggestion) {
        this.internal.element(suggestion);
        return this;
    }

    /**
     * Returns the suggestions as a list.
     *
     * @return The list of suggestions.
     */
    public @NotNull List<Suggestion> asList() {
        List<Suggestion> suggestions = new ArrayList<>();

        for (Suggestion suggestion : this.internal) {
            suggestions.add(suggestion);
        }

        return suggestions;
    }

    /**
     * Returns the suggestions as a list of raw text strings.
     *
     * @return The list of raw text suggestions.
     */
    public @NotNull List<String> unwrap() {
        List<Suggestion> suggestions = asList();
        List<String> rawList = new ArrayList<>();

        for (Suggestion suggestion : suggestions) {
            rawList.add(suggestion.text());
        }

        return rawList;
    }
}