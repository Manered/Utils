package dev.manere.utils.misc;

/**
 * The {@code FluentBuilder} class is an abstract class representing a fluent builder pattern.
 * Classes extending this abstract class should implement the build method to create an object of a specified type.
 *
 * @param <R> The type of object that the builder constructs.
 */
public abstract class FluentBuilder<R> {
    /**
     * Constructs and returns an object of the specified type.
     *
     * @return The constructed object.
     */
    public abstract R build();
}
