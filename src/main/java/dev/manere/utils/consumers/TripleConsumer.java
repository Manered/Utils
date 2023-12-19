package dev.manere.utils.consumers;

/**
 * The {@code TripleConsumer} interface represents an entity capable of executing an operation
 * that involves three parameters of different types.
 *
 * @param <A> The type of the first parameter.
 * @param <B> The type of the second parameter.
 * @param <C> The type of the third parameter.
 */
public interface TripleConsumer<A, B, C> {
    /**
     * Executes an operation with the given parameters.
     *
     * @param firstParam  The first parameter of type A.
     * @param secondParam The second parameter of type B.
     * @param thirdParam  The third parameter of type C.
     */
    void execute(A firstParam, B secondParam, C thirdParam);
}
