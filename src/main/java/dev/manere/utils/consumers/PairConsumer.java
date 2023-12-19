package dev.manere.utils.consumers;

/**
 * The {@code PairConsumer} interface represents an entity capable of executing an operation
 * that involves two parameters of different types.
 *
 * @param <A> The type of the first parameter.
 * @param <B> The type of the second parameter.
 */
public interface PairConsumer<A, B> {
    /**
     * Executes an operation with the given parameters.
     *
     * @param firstParam  The first parameter of type A.
     * @param secondParam The second parameter of type B.
     */
    void execute(A firstParam, B secondParam);
}
