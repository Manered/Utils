package dev.manere.utils.annotation;

import dev.manere.utils.event.EventListener;
import dev.manere.utils.library.Utils;
import dev.manere.utils.logger.Loggers;
import dev.manere.utils.model.Tuple;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * Handles scanning, registering all classes that are annotated with {@link AutoRegister}
 * @see AutoRegister
 */
public class AutoRegisterHandler {
    /**
     * Scans the project using this library and then
     * attempts to register the class as an event listener or command.
     *
     * @see EventListener
     */
    public static void scanAndRegister() {
        List<Class<?>> classes = findValidClasses();

        for (Class<?> clazz : classes) {
            try {
                AutoRegister autoRegister = clazz.getAnnotation(AutoRegister.class);

                if (autoRegister != null || EventListener.class.isAssignableFrom(clazz)) {
                    try {
                        autoRegister(clazz);
                    } catch (NoClassDefFoundError | NoSuchFieldError ex) {
                        Loggers.server().warning("Failed to auto register " + clazz + " due to it requesting missing fields/classes: " + ex.getMessage());
                    } catch (Throwable t) {
                        Loggers.server().severe("Failed to auto register class " + clazz);
                    }
                }
            } catch (Throwable t) {
                if (t instanceof VerifyError) {
                    continue;
                }

                Loggers.server().severe("Failed to scan class '" + clazz + "'!");
            }
        }
    }

    private static @NotNull Tuple<FindInstance, Object> findInstance(@NotNull Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        Object instance = null;
        FindInstance mode = null;

        // Strictly limit the class to one no args constructor
        if (constructors.length == 1) {
            Constructor<?> constructor = constructors[0];

            if (constructor.getParameterCount() == 0) {
                int modifiers = constructor.getModifiers();

                // Case 1: Public constructor
                if (Modifier.isPublic(modifiers)) {
                    instance = instantiate(constructor);
                    mode = FindInstance.NEW_FROM_CONSTRUCTOR;
                }

                // Case 2: Singleton
                else if (Modifier.isPrivate(modifiers)) {
                    Field instanceField = null;

                    for (Field field : clazz.getDeclaredFields()) {
                        int fieldMods = field.getModifiers();

                        if (!field.getType().isAssignableFrom(clazz) || field.getType() == Object.class)
                            continue;

                        if (Modifier.isPrivate(fieldMods) && Modifier.isStatic(fieldMods) && (Modifier.isFinal(fieldMods) || Modifier.isVolatile(fieldMods)))
                            instanceField = field;
                    }

                    if (instanceField != null) {
                        instance = fieldContent(instanceField);
                        mode = FindInstance.SINGLETON;
                    }
                }
            }

        }

        return new Tuple<>(mode, instance);
    }

    private static void autoRegister(@NotNull Class<?> clazz) {
        JavaPlugin plugin = Utils.plugin();
        Tuple<FindInstance, Object> tuple = findInstance(clazz);

        Object instance = tuple.val();

        if (instance == null) {
            return;
        }

        if (EventListener.class.isAssignableFrom(clazz)) {
            EventListener<?> inst = (EventListener<?>) instance;

            plugin.getServer().getPluginManager().registerEvent(
                    inst.clazz(),
                    inst,
                    inst.priority(),
                    inst,
                    Utils.plugin(),
                    inst.ignoreCancelled()
            );
        } else {
            throw new UnsupportedOperationException("@AutoRegister cannot be used on " + clazz);
        }
    }

    private static @NotNull List<Class<?>> findValidClasses() {
        List<Class<?>> classes = new ArrayList<>();

        // Ignore anonymous inner classes
        Pattern anonymousClassPattern = Pattern.compile("\\w+\\$[0-9]$");

        File source = src();

        try (JarFile file = new JarFile(source)) {
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jar = entry.nextElement();
                String name = jar.getName().replace("/", ".");

                // Ignore files such as settings.yml
                if (!name.endsWith(".class"))
                    continue;

                String className = name.substring(0, name.length() - 6);
                Class<?> clazz;

                // Look up the Java class, silently ignore if failing
                try {
                    clazz = Utils.plugin().getClass().getClassLoader().loadClass(className);
                } catch (ClassFormatError | VerifyError | NoClassDefFoundError | ClassNotFoundException | IncompatibleClassChangeError error) {
                    continue;
                }

                // Ignore abstract or anonymous classes
                if (!Modifier.isAbstract(clazz.getModifiers()) && !anonymousClassPattern.matcher(className).find())
                    classes.add(clazz);
            }

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        return classes;
    }

    public static @NotNull File src() {
        try {
            JavaPlugin plugin = (JavaPlugin) Utils.plugin().getServer().getPluginManager().getPlugin(Utils.plugin().getName());
            Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
            getFileMethod.setAccessible(true);

            return (File) getFileMethod.invoke(plugin);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    enum FindInstance {
        NEW_FROM_CONSTRUCTOR,
        SINGLETON
    }

    private static @NotNull Object fieldContent(@NotNull Field field) {
        try {
            field.setAccessible(true);
            return field.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not get field " + field.getName() + " in instance " + field.getClass().getSimpleName());
        }
    }

    private static @NotNull <T> T instantiate(@NotNull Constructor<T> constructor, @NotNull Object... params) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(params);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Could not make new instance of " + constructor + " with params: " + Arrays.toString(params));
        }
    }
}
