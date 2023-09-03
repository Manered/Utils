package dev.manere.utils.storage.yaml;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for managing custom configuration files using Bukkit's FileConfiguration and YamlConfiguration.
 */
public class CustomFileUtils {

    private final File file;
    private FileConfiguration customFile;
    private final Plugin plugin;

    /**
     * Constructs a CustomFileUtils instance, initializing the custom configuration file.
     *
     * @param plugin   The plugin instance that owns this configuration file.
     * @param fileName The name of the configuration file.
     */
    public CustomFileUtils(Plugin plugin, String fileName) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Retrieves the underlying FileConfiguration instance.
     *
     * @return The FileConfiguration instance representing the custom configuration file.
     */
    public FileConfiguration getConfig() {
        return customFile;
    }

    /**
     * Asynchronously saves the current state of the configuration to the file.
     */
    public void saveConfigAsync() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this::saveConfig);
    }

    /**
     * Synchronously saves the current state of the configuration to the file.
     */
    public void saveConfig() {
        synchronized (this) {
            try {
                customFile.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asynchronously reloads the configuration from the file.
     */
    public void reloadConfigAsync() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this::reloadConfig);
    }

    /**
     * Synchronously reloads the configuration from the file.
     */
    public void reloadConfig() {
        synchronized (this) {
            customFile = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        }
    }

    /**
     * Asynchronously sets a value in the configuration.
     *
     * @param path  The path to the value.
     * @param value The value to set.
     */
    public void setValueAsync(String path, Object value) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> setValue(path, value));
    }

    /**
     * Synchronously sets a value in the configuration.
     *
     * @param path  The path to the value.
     * @param value The value to set.
     */
    public void setValue(String path, Object value) {
        synchronized (this) {
            customFile.set(path, value);
        }
    }

    /**
     * Synchronously retrieves a value from the configuration.
     *
     * @param path The path to the value.
     * @return The value associated with the given path.
     */
    public Object getValue(String path) {
        synchronized (this) {
            return customFile.get(path);
        }
    }

    /**
     * Converts the entire configuration into a map.
     *
     * @return A map representation of the configuration.
     */
    public Map<String, Object> getConfigAsMap() {
        synchronized (this) {
            Map<String, Object> dataMap = new HashMap<>();
            for (String key : customFile.getKeys(true)) {
                dataMap.put(key, customFile.get(key));
            }
            return dataMap;
        }
    }

    /**
     * Sets the configuration values from a provided map.
     *
     * @param dataMap The map containing data to populate the configuration.
     */
    public void setConfigFromMap(Map<String, Object> dataMap) {
        synchronized (this) {
            customFile = new org.bukkit.configuration.file.YamlConfiguration();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                customFile.set(entry.getKey(), entry.getValue());
            }
        }
    }
}
