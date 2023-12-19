package dev.manere.utils.serializers;

import org.jetbrains.annotations.NotNull;

/**
 * Provides easy access to all serializers/deserializers in one class.
 */
public class Serializers {
    /**
     * Provides an instance of Base64Serializer
     * for serializing and deserializing ItemStacks and ItemBuilders using Base64 encoding.
     *
     * @return An instance of Base64Serializer.
     */
    public static @NotNull Base64Serializer base64() {
        return new Base64Serializer();
    }

    /**
     * Provides an instance of ByteSerializer
     * for serializing and deserializing ItemStacks and ItemBuilders using byte array encoding.
     *
     * @return An instance of ByteSerializer.
     */
    public static @NotNull ByteSerializer bytes() {
        return new ByteSerializer();
    }
}
