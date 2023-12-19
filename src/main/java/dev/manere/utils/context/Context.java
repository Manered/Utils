package dev.manere.utils.context;

import dev.manere.utils.command.impl.dispatcher.CommandContext;

/**
 * The {@code Context} interface represents a context associated with a specific type of source.
 * It provides a method to retrieve the source associated with the context.
 * <P></P>
 * A context should be a class to store multiple values and provide utility methods on them.
 * An example is {@link CommandContext} which is a context class for commands.
 *
 * @param <S> The type of the source associated with the context.
 */
public interface Context<S> {
    /**
     * Retrieves the source associated with this context.
     *
     * @return The source associated with this context.
     */
    S source();

    /**
     * The {@code Source} interface represents a wrapper for a specific type of source.
     * It provides a method to unwrap and obtain the original source object.
     *
     * @param <T> The type of the source object.
     */
    interface Source<T> {
        /**
         * Unwraps and obtains the original source object.
         *
         * @return The original source object.
         */
        T unwrap();
    }
}
