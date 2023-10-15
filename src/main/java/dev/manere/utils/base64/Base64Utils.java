package dev.manere.utils.base64;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Utilities for serializing and deserializing ItemStacks to Base64 strings.
 */
public class Base64Utils {

    /**
     * Serializes a map of kit contents (slot ID to ItemStack) to a Base64 string.
     *
     * @param contents The map of kit contents to serialize
     * @return A Base64 string representing the serialized kit contents
     */
    public static String serializeItemStacks(Map<Integer, ItemStack> contents) {
        ItemStack[] items = contents.values().toArray(new ItemStack[0]);

        List<String> serializedItems = Arrays.stream(items)
                .map(Base64Utils::serialize)
                .collect(Collectors.toList());

        return String.join(";", serializedItems);
    }

    /**
     * Deserializes a Base64 string into a map of kit contents (slot ID to ItemStack).
     *
     * @param data The Base64 string to deserialize
     * @return The deserialized map of kit contents
     */
    public static Map<Integer, ItemStack> deserializeItemStacks(String data) {
        Map<Integer, ItemStack> kitContents = new HashMap<>();
        String[] serializedItems = data.split(";");

        IntStream.range(0, serializedItems.length)
                .forEach(i -> {
                    ItemStack item = deserializeItemStack(serializedItems[i]);
                    kitContents.put(i, item);
                });

        return kitContents;
    }

    /**
     * Serializes an ItemStack to a Base64 string.
     *
     * @param itemStack The ItemStack to serialize
     * @return The Base64 string representing the serialized ItemStack
     */
    public static String serialize(ItemStack itemStack) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream bukkitObjectOutputStream;

        try {
            bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.close();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deserializes a Base64 string into an ItemStack.
     *
     * @param data The Base64 string to deserialize
     * @return The deserialized ItemStack
     */
    public static ItemStack deserializeItemStack(String data) {
        byte[] bytes = Base64.getDecoder().decode(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            ItemStack itemStack = (ItemStack) bukkitObjectInputStream.readObject();
            bukkitObjectInputStream.close();

            return itemStack;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
