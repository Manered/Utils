package dev.manere.utils.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code Storable} interface defines methods for serializing an object to a string and deserializing from a string.
 *
 * @param <T> The type of object to be serialized and deserialized.
 */
public interface Storable<T> {
    /**
     * Serializes the object to a string representation.
     *
     * @return A string representation of the serialized object.
     */
    @Nullable String repr();

    /**
     * Deserializes the object from a string representation.
     *
     * @param serialized the object to be deserialized.
     * @return The deserialized object.
     */
    @Nullable T ref(@NotNull String serialized);
}
