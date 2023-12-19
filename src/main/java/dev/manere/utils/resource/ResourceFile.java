package dev.manere.utils.resource;

import dev.manere.utils.library.Utils;
import dev.manere.utils.resource.format.ResourceFormat;
import dev.manere.utils.resource.format.ResourceFormats;
import dev.manere.utils.resource.path.ResourcePath;
import dev.manere.utils.resource.yaml.ResourceConfiguration;
import dev.manere.utils.scheduler.Schedulers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Represents a resource file with a specified format, options, path, and name.
 */
public class ResourceFile {
    private ResourceFormat resourceFormat;
    private final ResourceOptions recourseOptions;
    private final ResourcePath resourcePath;
    private final String name;

    /**
     * Constructs a new ResourceFile with the specified name, format, options, and path.
     *
     * @param name   The name of the resource file.
     * @param format The format of the resource file.
     * @param options The options for the resource file.
     * @param path   The path where the resource file is located.
     */
    public ResourceFile(String name, ResourceFormat format, ResourceOptions options, ResourcePath path) {
        this.resourceFormat = format;
        this.recourseOptions = options;
        this.resourcePath = path;
        this.name = name;

        if (!this.resourceFormat.extension().contains(".")) {
            this.resourceFormat = ResourceFormat.format("." + this.resourceFormat.extension());
        }
    }

    /**
     * Creates and returns a new instance of ResourceFile using the provided parameters.
     *
     * @param name   The name of the resource file.
     * @param format The format of the resource file.
     * @param options The options for the resource file.
     * @param path   The path where the resource file is located.
     * @return A new ResourceFile instance.
     */
    public static ResourceFile resource(String name, ResourceFormat format, ResourceOptions options, ResourcePath path) {
        return new ResourceFile(name, format, options, path);
    }

    /**
     * Returns a new instance of {@link ResourceBuilder} for building resource files.
     *
     * @return A new ResourceBuilder instance.
     */
    public static ResourceBuilder builder() {
        return ResourceBuilder.builder();
    }

    /**
     * Returns a new instance of {@link ResourceConfiguration} for working with YAML configurations.
     *
     * @return A new ResourceConfiguration instance.
     */
    public ResourceConfiguration yaml() {
        return new ResourceConfiguration(this);
    }

    /**
     * Loads and returns the file configuration from the resource file asynchronously.
     *
     * @return The loaded FileConfiguration.
     */
    public FileConfiguration loadYml() {
        return (FileConfiguration) Schedulers.async().execute(() -> YamlConfiguration.loadConfiguration(file()));
    }

    /**
     * Reloads and returns the file configuration from the resource file asynchronously.
     *
     * @return The reloaded FileConfiguration.
     */
    public FileConfiguration reloadYml() {
        return loadYml();
    }

    /**
     * Asynchronously saves the file configuration to the resource file.
     */
    public void saveYml() {
        if (Objects.equals(format(), ResourceFormats.yaml()) || Objects.equals(format(), ResourceFormats.yml())) {
            Schedulers.async().execute(task -> {
                try {
                    file().mkdirs();
                    file().createNewFile();

                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file());
                    configuration.save(file());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            throw new UnsupportedOperationException("only yaml files are supported for saveOrLoad()");
        }
    }

    /**
     * Asynchronously saves or loads the resource file based on its existence and plugin resources.
     *
     * @return The current ResourceFile instance.
     */
    public ResourceFile saveOrLoadYml() {
        if (Objects.equals(format(), ResourceFormats.yaml()) || Objects.equals(format(), ResourceFormats.yml())) {
            Schedulers.async().execute(task -> {
                if (file().exists()) {
                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file());
                    return;
                }

                if (Utils.plugin().getResource(this.name + this.resourceFormat.extension()) != null) {
                    Utils.plugin().saveResource(this.resourcePath.path().replaceAll(Utils.plugin().getDataFolder().getPath(), "") + this.name + this.resourceFormat.extension(), false);
                }

                try {
                    if (!file().createNewFile()) {
                        Utils.plugin().getLogger().severe("Failed to create resource: " + file().getPath());
                        task.cancel();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file());

                configuration.options()
                        .copyDefaults(true)
                        .parseComments(true)
                        .pathSeparator('.');

                if (recourseOptions.header() != null) {
                    configuration.options().setHeader(recourseOptions.header());
                }

                if (recourseOptions.footer() != null) {
                    configuration.options().setFooter(recourseOptions.footer());
                }

                try {
                    configuration.save(file());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            throw new UnsupportedOperationException("only yaml files are supported for saveOrLoad()");
        }

        return this;
    }

    /**
     * Returns the file associated with the resource file.
     *
     * @return The file representing the resource file.
     */
    public File file() {
        return new File(new File(resourcePath.path()), name + resourceFormat.extension());
    }

    /**
     * Gets the format of the resource file.
     *
     * @return The format of the resource file.
     */
    public ResourceFormat format() {
        return resourceFormat;
    }

    /**
     * Gets the options for the resource file.
     *
     * @return The options for the resource file.
     */
    public ResourceOptions options() {
        return recourseOptions;
    }

    /**
     * Gets the path where the resource file is located.
     *
     * @return The path of the resource file.
     */
    public ResourcePath path() {
        return resourcePath;
    }

    /**
     * Gets the name of the resource file.
     *
     * @return The name of the resource file.
     */
    public String name() {
        return name;
    }
}
