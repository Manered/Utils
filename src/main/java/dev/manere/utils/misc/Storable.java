package dev.manere.utils.misc;

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
    String serialize();

    /**
     * Deserializes the object from a string representation.
     *
     * @param serialized the object to be deserialized.
     * @return The deserialized object.
     */
    T deserialize(String serialized);
}
