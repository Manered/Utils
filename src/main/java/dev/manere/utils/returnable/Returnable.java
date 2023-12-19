package dev.manere.utils.returnable;

/**
 * An interface for a functional operation that takes no parameters and returns a value.
 * @param <R> the type of the return value
 */
public interface Returnable<R> {
    /**
     * Computes a result without any parameters.
     * @return the result of the computation
     */
    R returnVal();
}
