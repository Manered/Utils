package dev.manere.utils.persistence;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A utility class for saving and loading a HashMap to/from a YAML file using Bukkit's YamlConfiguration.
 */
public class HashMap2YamlUtils {

    private final File configFile;
    private final YamlConfiguration config;

    /**
     * Constructs a new HashMap2YamlUtils instance.
     *
     * @param plugin   the JavaPlugin instance owning the configuration file
     * @param fileName the name of the YAML configuration file
     */
    public HashMap2YamlUtils(JavaPlugin plugin, String fileName) {
        this.configFile = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Saves the contents of the HashMap to the YAML file.
     *
     * @param hashMap the HashMap to be saved
     */
    public void saveHashMapToFile(HashMap<?, ?> hashMap) {
        for (Map.Entry<?, ?> entry : hashMap.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a HashMap from the YAML file.
     *
     * @return the loaded HashMap
     */
    public HashMap<?, ?> loadHashMapFromFile() {
        HashMap<Object, Object> hashMap = new HashMap<>();

        for (String key : config.getKeys(false)) {
            Object value = config.get(key);
            hashMap.put(key, value);
        }

        return hashMap;
    }

    /**
     * Returns the File object representing the configuration file.
     *
     * @return the configuration File object
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * Returns the YamlConfiguration object representing the YAML file.
     *
     * @return the YamlConfiguration object
     */
    public YamlConfiguration getConfig() {
        return config;
    }
}
