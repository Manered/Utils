package dev.manere.utils.serializers;

/**
 * Provides easy access to all serializers/deserializers in one class.
 */
public class Serializers {

    /**
     * Provides an instance of Base64Serializer for serializing and deserializing ItemStacks and ItemBuilders using Base64 encoding.
     *
     * @return An instance of Base64Serializer.
     */
    public static Base64Serializer base64() {
        return new Base64Serializer();
    }
}
