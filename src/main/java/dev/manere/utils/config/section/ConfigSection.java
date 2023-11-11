package dev.manere.utils.config.section;

import dev.manere.utils.config.setter.ConfigSectionSetter;
import dev.manere.utils.library.Utils;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigSection {
    public final ConfigurationSection section;
    public final String sectionPath;

    public ConfigSection(String section) {
        if (Utils.plugin().getConfig().getConfigurationSection(section) == null) {
            Utils.plugin().getConfig().createSection(section);
        }

        this.section = Utils.plugin().getConfig().getConfigurationSection(section);
        this.sectionPath = section;
    }

    public static ConfigSection section(String section) {
        return new ConfigSection(section);
    }

    public ConfigSectionSetter set() {
        return new ConfigSectionSetter(this);
    }

    public Object get(String key) {
        return section.get(sectionPath + "." + key);
    }

    public void delete() {
        Utils.plugin().getConfig().set(sectionPath, null);
    }
}
