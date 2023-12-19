package dev.manere.utils.logger;

import dev.manere.utils.library.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

/**
 * The AdventureLogger class provides utility methods for logging messages with various log levels
 * in Bukkit plugins using Kyori Adventure's component logger.
 */
public class AdventureLogger {
    private static final ComponentLogger LOGGER = Utils.plugin().getComponentLogger();

    /**
     * Logs a debug message with the provided Adventure component.
     *
     * @param component The Adventure component to be logged.
     */
    public void debug(Component component) {
        LOGGER.debug(component);
    }

    /**
     * Logs a warning message with the provided Adventure component.
     *
     * @param component The Adventure component to be logged.
     */
    public void warn(Component component) {
        LOGGER.warn(component);
    }

    /**
     * Logs an error message with the provided Adventure component.
     *
     * @param component The Adventure component to be logged.
     */
    public void error(Component component) {
        LOGGER.error(component);
    }

    /**
     * Logs an information message with the provided Adventure component.
     *
     * @param component The Adventure component to be logged.
     */
    public void info(Component component) {
        LOGGER.info(component);
    }

    /**
     * Logs a trace message with the provided Adventure component.
     *
     * @param component The Adventure component to be logged.
     */
    public void trace(Component component) {
        LOGGER.trace(component);
    }
}
