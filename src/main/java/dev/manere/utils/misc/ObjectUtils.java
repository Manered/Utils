package dev.manere.utils.misc;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class for common object-related operations, including null checks and validation.
 */
public class ObjectUtils {
    /**
     * Ensures that the specified value is not null.
     *
     * @param value The value to check for null.
     * @param <T>   The type of the value.
     * @return The non-null value.
     * @throws NullPointerException if the value is null.
     */
    @CanIgnoreReturnValue
    public static <T> T nonNull(@Nullable T value) {
        if (value == null) throw new NullPointerException();
        return value;
    }

    /**
     * Ensures that the specified value is not null.
     *
     * @param value The value to check for null.
     * @param text  The exception message if the value is null.
     * @param <T>   The type of the value.
     * @return The non-null value.
     * @throws NullPointerException if the value is null.
     */
    @CanIgnoreReturnValue
    public static <T> T nonNull(@Nullable T value, @NotNull String text) {
        if (value == null) throw new NullPointerException(text);
        return value;
    }

    /**
     * Provides a default value if the specified value is null.
     *
     * @param value       The value to check for null.
     * @param defaultValue The default value to return if the original value is null.
     * @param <T>         The type of the value.
     * @return The original value if not null, otherwise the default value.
     */
    public static <T> T defaultIfNull(@Nullable T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }

    /**
     * Executes the specified action if the value is not null.
     *
     * @param value  The value to check for null.
     * @param action The action to perform on the non-null value.
     * @param <T>    The type of the value.
     */
    public static <T> void ifNonNull(@Nullable T value, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    /**
     * Ensures that the specified value is not null, throwing a custom exception if it is.
     *
     * @param value            The value to check for null.
     * @param exceptionSupplier A supplier providing the exception to be thrown if the value is null.
     * @param <T>              The type of the value.
     * @param <E>              The type of the exception.
     * @return The non-null value.
     * @throws E if the value is null.
     */
    @CanIgnoreReturnValue
    public static <T, E extends RuntimeException> T nonNull(@Nullable T value, Supplier<E> exceptionSupplier) {
        if (value == null) throw exceptionSupplier.get();
        return value;
    }

    /**
     * Ensures that the specified collection is not null and does not contain null elements.
     *
     * @param collection The collection to check for null and null elements.
     * @param <T>        The type of elements in the collection.
     * @return The non-null and non-contain-null-elements collection.
     * @throws NullPointerException if the collection is null or contains null elements.
     */
    @CanIgnoreReturnValue
    public static <T> Collection<T> nonNullElements(@Nullable Collection<T> collection) {
        if (collection == null || collection.contains(null)) throw new NullPointerException("Collection contains null elements");
        return collection;
    }

    /**
     * Ensures that the specified string is not null or empty.
     *
     * @param value     The string to check for null or empty.
     * @param fieldName The name of the field being checked.
     * @return The non-null and non-empty string.
     * @throws IllegalArgumentException if the string is null or empty.
     */
    @CanIgnoreReturnValue
    public static String nonEmpty(@Nullable String value, @NotNull String fieldName) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException(fieldName + " must not be empty");
        return value;
    }

    /**
     * Provides an empty collection if the specified collection is null.
     *
     * @param collection The collection to check for null.
     * @param <T>        The type of elements in the collection.
     * @return The original collection if not null, otherwise an empty collection.
     */
    public static <T> Collection<T> emptyIfNull(@Nullable Collection<T> collection) {
        return (collection != null) ? collection : Collections.emptyList();
    }

    /**
     * Checks the specified value against a predicate, throwing an exception if the check fails.
     *
     * @param value       The value to check.
     * @param predicate   The predicate to test the value against.
     * @param errorMessage The error message to include in the exception if the check fails.
     * @param <T>         The type of the value.
     * @return The original value if the check passes.
     * @throws IllegalArgumentException if the check fails.
     */
    @CanIgnoreReturnValue
    public static <T> T check(@Nullable T value, Predicate<T> predicate, String errorMessage) {
        if (!predicate.test(value)) throw new IllegalArgumentException(errorMessage);
        return value;
    }

    /**
     * Ensures that the specified integer is non-negative.
     *
     * @param value       The integer value to check.
     * @param errorMessage The error message to include in the exception if the check fails.
     * @return The original value if it is non-negative.
     * @throws IllegalArgumentException if the value is negative.
     */
    public static int requireNonNegative(int value, String errorMessage) {
        if (value < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }
}