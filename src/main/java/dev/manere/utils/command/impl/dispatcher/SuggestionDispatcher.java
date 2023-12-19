package dev.manere.utils.command.impl.dispatcher;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * An interface for providing command suggestions based on the provided command context.
 */
public interface SuggestionDispatcher {
    /**
     * Generates a list of command suggestions based on the provided command context.
     *
     * @param context The context of the command for which suggestions are being generated.
     * @return A list of suggested command options, or null if no suggestions are available.
     */
    @Nullable List<String> suggest(@NotNull CommandContext context);
}
