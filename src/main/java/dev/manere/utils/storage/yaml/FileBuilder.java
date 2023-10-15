package dev.manere.utils.storage.yaml;

import dev.manere.utils.library.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * A utility class for managing files and their configurations.
 */
public class FileBuilder {
    public File file;
    public FileConfiguration config;
    public YamlConfiguration yamlConfig;
    public final String path;
    public final String name;

    /**
     * Constructs a FileBuilder with the specified path and name.
     *
     * @param path The path where the file is located.
     * @param name The name of the file.
     */
    public FileBuilder(String path, String name) {
        this.path = path;
        this.name = name;
    }

    /**
     * Constructs a FileBuilder with the specified name, using the default plugin data folder path.
     *
     * @param name The name of the file.
     */
    public FileBuilder(String name) {
        this.path = Utils.getPlugin().getDataFolder().getPath();
        this.name = name;
    }

    /**
     * Creates a new FileBuilder with the specified path and name.
     * If the file does not exist, it will be created.
     *
     * @param path The path where the file is located.
     * @param name The name of the file.
     * @return The created FileBuilder.
     */
    public static FileBuilder of(String path, String name) {
        FileBuilder fileBuilder = new FileBuilder(path, name);

        fileBuilder.file = new File(path, name);

        if (!fileBuilder.file.exists()) {
            try {
                fileBuilder.file.createNewFile();
            } catch (IOException e) {
                Utils.getPlugin().getLogger().log(Level.SEVERE, "An error has occurred while attempting to create a file.\n  Message: " + e.getMessage());
            }
        }

        fileBuilder.config = YamlConfiguration.loadConfiguration(fileBuilder.file);
        fileBuilder.config.options().parseComments(true);
        fileBuilder.yamlConfig = YamlConfiguration.loadConfiguration(fileBuilder.file);

        return fileBuilder;
    }

    /**
     * Creates a new FileBuilder with the specified name, using the default plugin data folder path.
     * If the file does not exist, it will be created.
     *
     * @param name The name of the file.
     * @return The created FileBuilder.
     */
    public static FileBuilder of(String name) {
        return of(Utils.getPlugin().getDataFolder().getPath(), name);
    }

    /**
     * Retrieves an existing FileBuilder from the specified path and name.
     *
     * @param path The path where the file is located.
     * @param name The name of the file.
     * @return The existing FileBuilder, or null if the file does not exist.
     */
    public static FileBuilder from(String path, String name) {
        FileBuilder fileBuilder = new FileBuilder(path, name);

        fileBuilder.file = new File(path, name);

        if (fileBuilder.fileExists()) {
            fileBuilder.config = YamlConfiguration.loadConfiguration(fileBuilder.file);
            fileBuilder.config.options().parseComments(true);

            fileBuilder.yamlConfig = YamlConfiguration.loadConfiguration(fileBuilder.file);
        } else {
            return null;
        }

        return fileBuilder;
    }

    /**
     * Retrieves an existing FileBuilder from the default plugin data folder.
     *
     * @param name The name of the file.
     * @return The existing FileBuilder, or null if the file does not exist.
     */
    public static FileBuilder from(String name) {
        return from(Utils.getPlugin().getDataFolder().getPath(), name);
    }

    /**
     * Sets a value in the configuration.
     *
     * @param path    The path where the value will be set.
     * @param value   The value to be set.
     * @param doAsync Whether to run the operation asynchronously.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder value(String path, Object value, boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                config.set(path, value);
                save(false);
            });
        } else {
            config.set(path, value);
            save(false);
        }

        return this;
    }

    /**
     * Sets a value in the configuration without asynchronous operation.
     *
     * @param path  The path where the value will be set.
     * @param value The value to be set.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder value(String path, Object value) {
        return value(path, value, false);
    }

    /**
     * Sets multiple values from a map in the configuration.
     *
     * @param values  The map containing the values.
     * @param doAsync Whether to run the operation asynchronously.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder fileToMap(Map<String, Object> values, boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                config = new YamlConfiguration();

                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    config.set(entry.getKey(), entry.getValue());
                }
            });
        } else {
            config = new YamlConfiguration();

            for (Map.Entry<String, Object> entry : values.entrySet()) {
                config.set(entry.getKey(), entry.getValue());
            }
        }

        return this;
    }

    /**
     * Sets multiple values from a map in the configuration without asynchronous operation.
     *
     * @param values The map containing the values.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder fileToMap(Map<String, Object> values) {
        return fileToMap(values, false);
    }

    /**
     * Retrieves a map of values from the file.
     *
     * @param doAsync  Whether to run the operation asynchronously.
     * @param callback A callback function to handle the map of values.
     */
    public void getMapFromFile(boolean doAsync, Consumer<Map<String, Object>> callback) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                Map<String, Object> map = new HashMap<>();

                for (String key : config.getKeys(true)) {
                    map.put(key, config.get(key));
                }

                callback.accept(map);
            });
        } else {
            Map<String, Object> map = new HashMap<>();

            for (String key : config.getKeys(true)) {
                map.put(key, config.get(key));
            }

            callback.accept(map);
        }
    }

    /**
     * Retrieves a map of values from the file without asynchronous operation.
     *
     * @param callback A callback function to handle the map of values.
     */
    public void getMapFromFile(Consumer<Map<String, Object>> callback) {
        getMapFromFile(false, callback);
    }

    /**
     * Retrieves a value from the configuration.
     *
     * @param path    The path of the value to retrieve.
     * @param doAsync Whether to run the operation asynchronously.
     * @return The retrieved value.
     */
    public Object getValue(String path, boolean doAsync) {
        if (doAsync) {
            reload();
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> yamlConfig.get(path));
        } else {
            reload();
            return yamlConfig.get(path);
        }

        return null;
    }

    /**
     * Retrieves a value from the configuration without asynchronous operation.
     *
     * @param path The path of the value to retrieve.
     * @return The retrieved value.
     */
    public Object getValue(String path) {
        return getValue(path, false);
    }

    /**
     * Saves the configuration to the file.
     *
     * @param doAsync Whether to run the operation asynchronously.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder save(boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                try {
                    config.save(file);
                } catch (IOException e) {
                    Utils.getPlugin().getLogger().log(Level.SEVERE, "An error has occurred while attempting to save a file.\n  Message: " + e.getMessage());
                }
            });
        } else {
            try {
                config.save(file);
            } catch (IOException e) {
                Utils.getPlugin().getLogger().log(Level.SEVERE, "An error has occurred while attempting to save a file.\n  Message: " + e.getMessage());
            }
        }

        return this;
    }

    /**
     * Saves the configuration to the file without asynchronous operation.
     *
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder save() {
        return save(false);
    }

    /**
     * Reloads the configuration from the file.
     *
     * @param doAsync Whether to run the operation asynchronously.
     */
    public void reload(boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                this.config = YamlConfiguration.loadConfiguration(file);
                this.yamlConfig = YamlConfiguration.loadConfiguration(file);
            });
        } else {
            this.config = YamlConfiguration.loadConfiguration(file);
            this.yamlConfig = YamlConfiguration.loadConfiguration(file);
        }

    }

    /**
     * Reloads the configuration from the file without asynchronous operation.
     */
    public void reload() {
        reload(false);
    }

    /**
     * Deletes the file associated with this FileBuilder.
     *
     * @param doAsync Whether to run the operation asynchronously.
     */
    public void delete(boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                if (file.exists()) {
                    file.delete();
                }
            });
        } else {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Deletes the file associated with this FileBuilder without asynchronous operation.
     */
    public void delete() {
        delete(false);
    }

    /**
     * Checks if the file associated with this FileBuilder exists.
     *
     * @return True if the file exists, otherwise false.
     */
    public boolean fileExists() {
        return file.exists();
    }

    /**
     * Copies the file to a new location.
     *
     * @param newPath The path where the file will be copied.
     * @param doAsync Whether to run the operation asynchronously.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder copy(String newPath, boolean doAsync) {
        if (doAsync) {
            Utils.getPlugin().getServer().getScheduler().runTaskAsynchronously(Utils.getPlugin(), () -> {
                File newFile = new File(newPath, name);

                try {
                    Files.copy(file.toPath(), newFile.toPath());
                } catch (IOException e) {
                    Utils.getPlugin().getLogger().log(Level.SEVERE, "An error has occurred while attempting to copy a file.\n  Message: " + e.getMessage());
                }
            });
        } else {
            File newFile = new File(newPath, name);

            try {
                Files.copy(file.toPath(), newFile.toPath());
            } catch (IOException e) {
                Utils.getPlugin().getLogger().log(Level.SEVERE, "An error has occurred while attempting to copy a file.\n  Message: " + e.getMessage());
            }
        }

        return this;
    }

    /**
     * Copies the file to a new location without asynchronous operation.
     *
     * @param newPath The path where the file will be copied.
     * @return This FileBuilder for method chaining.
     */
    public FileBuilder copy(String newPath) {
        return copy(newPath, false);
    }

    /**
     * Gets the associated file.
     *
     * @return The file.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the configuration of the file.
     *
     * @return The file configuration.
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Gets the YAML configuration of the file.
     *
     * @return The YAML file configuration.
     */
    public YamlConfiguration getYamlConfig() {
        return yamlConfig;
    }

    /**
     * Gets the path of the file.
     *
     * @return The file path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the name of the file.
     *
     * @return The file name.
     */
    public String getName() {
        return name;
    }
}
