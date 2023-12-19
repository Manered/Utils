package dev.manere.utils.pdc;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * A custom implementation of {@link PersistentDataType} for converting between byte arrays and UUIDs.
 * This implementation is used for data persistence in Paper/Spigot/Bukkit plugins.
 *
 * @see PersistentDataType
 */
public class UUIDDataType implements PersistentDataType<byte[], UUID> {
    /**
     * Creates a new instance of UUIDDataType.
     *
     * @return A new instance of UUIDDataType.
     */
    public static UUIDDataType of() {
        return new UUIDDataType();
    }

    /**
     * Gets the primitive type associated with this data type.
     *
     * @return The primitive type (byte array).
     */
    @Override
    public @NotNull Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    /**
     * Gets the complex type associated with this data type.
     *
     * @return The complex type (UUID).
     */
    @Override
    public @NotNull Class<UUID> getComplexType() {
        return UUID.class;
    }

    /**
     * Converts a UUID to a byte array.
     *
     * @param complex The UUID to convert.
     * @param context The PersistentDataAdapterContext.
     * @return The byte array representation of the UUID.
     */
    @Override
    public byte @NotNull [] toPrimitive(UUID complex, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);

        byteBuffer.putLong(complex.getMostSignificantBits());
        byteBuffer.putLong(complex.getLeastSignificantBits());

        return byteBuffer.array();
    }

    /**
     * Converts a byte array to a UUID.
     *
     * @param primitive The byte array to convert.
     * @param context   The PersistentDataAdapterContext.
     * @return The UUID representation of the byte array.
     */
    @Override
    public @NotNull UUID fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(primitive);

        long firstLong = byteBuffer.getLong();
        long secondLong = byteBuffer.getLong();

        return new UUID(firstLong, secondLong);
    }
}