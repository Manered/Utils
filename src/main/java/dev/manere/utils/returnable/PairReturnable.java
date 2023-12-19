package dev.manere.utils.returnable;

/**
 * An interface for a functional operation that takes one parameter and returns a value.
 * @param <R> the type of the return value
 * @param <A> the type of the first parameter
 */
public interface PairReturnable<R, A> {
    /**
     * Computes a result based on the given parameter.
     * @param firstParam the first parameter
     * @return the result of the computation
     */
    R returnVal(A firstParam);
}
