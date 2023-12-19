package dev.manere.utils.serializers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The ByteSerializer class provides static methods for serializing and deserializing
 * ItemStack objects to and from byte arrays.
 */
@SuppressWarnings("UnstableApiUsage") // Why Google. Why.
public class ByteSerializer {
    /**
     * Serializes a single ItemStack object into a byte array.
     *
     * @param item The ItemStack to be serialized.
     * @return A byte array representing the serialized ItemStack.
     */
    public byte[] serialize(ItemStack item) {
        return item.serializeAsBytes();
    }

    /**
     * Serializes a list of ItemStack objects into a byte array.
     *
     * @param items The list of ItemStack objects to be serialized.
     * @return A byte array representing the serialized list of ItemStack objects.
     */
    public byte[] serialize(List<ItemStack> items) {
        return serialize(items.toArray(new ItemStack[0]));
    }

    /**
     * Serializes an array of ItemStack objects into a byte array.
     *
     * @param items The array of ItemStack objects to be serialized.
     * @return A byte array representing the serialized array of ItemStack objects.
     */
    public byte[] serialize(ItemStack[] items) {
        ByteArrayDataOutput stream = ByteStreams.newDataOutput();

        for (ItemStack item : items) {
            byte[] data = item == null || item.getType() == Material.AIR ? new byte[]{} : item.serializeAsBytes();

            stream.writeInt(data.length);
            stream.write(data);
        }

        stream.writeInt(-1);
        return stream.toByteArray();
    }

    /**
     * Deserializes a byte array into an ItemStack object.
     *
     * @param bytes The byte array to be deserialized into an ItemStack.
     * @return The deserialized ItemStack object.
     */
    public ItemStack deserialize(byte[] bytes) {
        return ItemStack.deserializeBytes(bytes);
    }

    /**
     * Deserializes a byte array into an array of ItemStack objects.
     *
     * @param bytes The byte array to be deserialized into an array of ItemStack objects.
     * @return An array of deserialized ItemStack objects.
     */
    public ItemStack[] deserializeArray(byte[] bytes) {
        List<ItemStack> items = new ArrayList<>();
        ByteArrayDataInput inputStream = ByteStreams.newDataInput(bytes);
        int length = inputStream.readInt();

        while (length != -1) {
            if (length == 0) {
                items.add(null);
            } else {
                byte[] data = new byte[length];

                inputStream.readFully(data, 0, data.length);
                items.add(ItemStack.deserializeBytes(data));
            }

            length = inputStream.readInt();
        }

        return items.toArray(new ItemStack[0]);
    }

    /**
     * Deserializes a byte array into a list of ItemStack objects.
     *
     * @param bytes The byte array to be deserialized into a list of ItemStack objects.
     * @return A list of deserialized ItemStack objects.
     */
    public List<ItemStack> deserializeList(byte[] bytes) {
        return Arrays.asList(deserializeArray(bytes));
    }
}
