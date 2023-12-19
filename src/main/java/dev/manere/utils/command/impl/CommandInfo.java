package dev.manere.utils.command.impl;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CommandInfo {
    final @NotNull Commands builder;
    private @Nullable String namespace;
    private @Nullable String description;
    private @Nullable String permission;
    private @Nullable Component permissionMessage;
    private @Nullable String usage;
    private @Nullable List<String> aliases;

    public CommandInfo(@NotNull Commands builder) {
        this.builder = builder;
    }

    public String namespace() {
        return namespace;
    }

    public CommandInfo namespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public String description() {
        return description;
    }

    public CommandInfo description(String description) {
        this.description = description;
        return this;
    }

    public String permission() {
        return permission;
    }

    public CommandInfo permission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandInfo permission(String prefix, String suffix) {
        this.permission = prefix + "." + suffix;
        return this;
    }

    public Component permissionMessage() {
        return permissionMessage;
    }

    public CommandInfo permissionMessage(Component permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }

    public String usage() {
        return usage;
    }

    public CommandInfo usage(String usage) {
        this.usage = usage;
        return this;
    }

    public List<String> aliases() {
        return aliases;
    }

    public CommandInfo aliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandInfo aliases(String... aliases) {
        this.aliases = Arrays.asList(aliases);
        return this;
    }
}
