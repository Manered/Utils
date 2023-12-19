package dev.manere.utils.returnable;

/**
 * An interface for a functional operation that takes two parameters and returns a value.
 * @param <R> the type of the return value
 * @param <A> the type of the first parameter
 * @param <B> the type of the second parameter
 */
public interface TripleReturnable<R, A, B> {
    /**
     * Computes a result based on the given parameters.
     * @param firstParam the first parameter
     * @param secondParam the second parameter
     * @return the result of the computation
     */
    R returnVal(A firstParam, B secondParam);
}
