package dev.manere.utils.command.impl.dispatcher;

import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface RawSuggestionDispatcher extends SuggestionDispatcher {
    /**
     * Generates a list of command suggestions based on the provided command context.
     *
     * @param context The context of the command for which suggestions are being generated.
     * @return A list of suggested command options, or null if no suggestions are available.
     */
    @Nullable List<String> suggestRaw(@NotNull CommandContext context);

    @Override
    default @Nullable Suggestions suggest(@NotNull CommandContext context) {
        List<String> raw = suggestRaw(context);
        return raw == null ? Suggestions.empty() : Suggestions.wrap(raw);
    }
}
