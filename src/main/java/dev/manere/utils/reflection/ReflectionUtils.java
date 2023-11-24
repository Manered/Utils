package dev.manere.utils.reflection;

import org.bukkit.Bukkit;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A utility class providing methods for reflection-related tasks.
 */
public class ReflectionUtils {

    public static final String NM_PACKAGE = "net.minecraft";
    public static final String CRAFTBUKKIT_PACKAGE = "org.bukkit.craftbukkit";
    public static final String NMS_PACKAGE = NM_PACKAGE + ".server";
    public static final String VERSION = Bukkit.getServer().getClass().getPackage().getName().substring(CRAFTBUKKIT_PACKAGE.length() + 1);
    private static final MethodType VOID_METHOD_TYPE = MethodType.methodType(void.class);
    private static final boolean NMS_REPACKAGED = optionalClass(NM_PACKAGE + ".network.protocol.Packet").isPresent();
    private static volatile Object theUnsafe;

    /**
     * Checks if the net.minecraft server package has been repackaged.
     *
     * @return True if the package has been repackaged, otherwise false.
     */
    public static boolean isRepackaged() {
        return NMS_REPACKAGED;
    }

    /**
     * Generates the fully-qualified class name for a net.minecraft server class.
     *
     * @param post1_17package The optional sub-package introduced after 1.17.
     * @param className       The name of the class.
     * @return The fully-qualified class name.
     */
    public static String nmsClassName(String post1_17package, String className) {
        if (NMS_REPACKAGED) {
            String classPackage = post1_17package == null ? NM_PACKAGE : NM_PACKAGE + '.' + post1_17package;
            return classPackage + '.' + className;
        }

        return NMS_PACKAGE + '.' + VERSION + '.' + className;
    }

    /**
     * Gets the Class object for a net.minecraft server class.
     *
     * @param post1_17package The optional sub-package introduced after 1.17.
     * @param className       The name of the class.
     * @return The Class object.
     * @throws ClassNotFoundException if the class cannot be found.
     */
    public static Class<?> nmsClass(String post1_17package, String className) throws ClassNotFoundException {
        return Class.forName(nmsClassName(post1_17package, className));
    }

    /**
     * Gets an optional Class object for a net.minecraft server class.
     *
     * @param post1_17package The optional sub-package introduced after 1.17.
     * @param className       The name of the class.
     * @return An Optional containing the Class object if found, or empty if not.
     */
    public static Optional<Class<?>> nmsOptionalClass(String post1_17package, String className) {
        return optionalClass(nmsClassName(post1_17package, className));
    }

    /**
     * Generates the fully-qualified class name for an org.bukkit.craftbukkit class.
     *
     * @param className The name of the class.
     * @return The fully-qualified class name.
     */
    public static String obcClassName(String className) {
        return CRAFTBUKKIT_PACKAGE + '.' + VERSION + '.' + className;
    }

    /**
     * Gets the Class object for an org.bukkit.craftbukkit class.
     *
     * @param className The name of the class.
     * @return The Class object.
     * @throws ClassNotFoundException if the class cannot be found.
     */
    public static Class<?> obcClass(String className) throws ClassNotFoundException {
        return Class.forName(obcClassName(className));
    }

    /**
     * Gets an optional Class object for an org.bukkit.craftbukkit class.
     *
     * @param className The name of the class.
     * @return An Optional containing the Class object if found, or empty if not.
     */
    public static Optional<Class<?>> obcOptionalClass(String className) {
        return optionalClass(obcClassName(className));
    }

    /**
     * Gets an optional Class object for a specified class name.
     *
     * @param className The name of the class.
     * @return An Optional containing the Class object if found, or empty if not.
     */
    public static Optional<Class<?>> optionalClass(String className) {
        try {
            return Optional.of(Class.forName(className));
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves an enum constant by its name from a specified enum class.
     *
     * @param enumClass The enum class.
     * @param enumName  The name of the enum constant.
     * @return The enum constant.
     */
    public static Object enumValueOf(Class<?> enumClass, String enumName) {
        return Enum.valueOf(enumClass.asSubclass(Enum.class), enumName);
    }

    /**
     * Retrieves an enum constant by its name from a specified enum class,
     * with a fallback ordinal value if the constant is not found.
     *
     * @param enumClass        The enum class.
     * @param enumName         The name of the enum constant.
     * @param fallbackOrdinal  The fallback ordinal value.
     * @return The enum constant or the fallback value if not found.
     */
    public static Object enumValueOf(Class<?> enumClass, String enumName, int fallbackOrdinal) {
        try {
            return enumValueOf(enumClass, enumName);
        } catch (IllegalArgumentException e) {
            Object[] constants = enumClass.getEnumConstants();

            if (constants.length > fallbackOrdinal) {
                return constants[fallbackOrdinal];
            }

            throw e;
        }
    }

    /**
     * Finds an inner class within a parent class based on a provided predicate.
     *
     * @param parentClass    The parent class.
     * @param classPredicate The predicate for matching inner classes.
     * @return The inner class that matches the predicate.
     * @throws ClassNotFoundException if no matching class is found.
     */
    public static Class<?> innerClass(Class<?> parentClass, Predicate<Class<?>> classPredicate) throws ClassNotFoundException {
        for (Class<?> innerClass : parentClass.getDeclaredClasses()) {
            if (classPredicate.test(innerClass)) {
                return innerClass;
            }
        }

        throw new ClassNotFoundException("No class in " + parentClass.getCanonicalName() + " matches the predicate.");
    }

    /**
     * Finds a constructor method for creating instances of a packet class.
     *
     * @param packetClass The packet class.
     * @param lookup      The MethodHandles.Lookup instance.
     * @return The PacketConstructor for the packet class.
     * @throws Exception if the constructor cannot be found.
     */
    public static PacketConstructor findPacketConstructor(Class<?> packetClass, MethodHandles.Lookup lookup) throws Exception {
        try {
            MethodHandle constructor = lookup.findConstructor(packetClass, VOID_METHOD_TYPE);
            return constructor::invoke;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // try below with Unsafe
        }

        if (theUnsafe == null) {
            synchronized (ReflectionUtils.class) {
                if (theUnsafe == null) {
                    Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                    Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
                    theUnsafeField.setAccessible(true);
                    theUnsafe = theUnsafeField.get(null);
                }
            }
        }

        MethodType allocateMethodType = MethodType.methodType(Object.class, Class.class);
        MethodHandle allocateMethod = lookup.findVirtual(theUnsafe.getClass(), "allocateInstance", allocateMethodType);
        return () -> allocateMethod.invoke(theUnsafe, packetClass);
    }

    /**
     * A functional interface representing a constructor method for packets.
     */
    @FunctionalInterface
    public interface PacketConstructor {

        /**
         * Invokes the constructor to create a packet instance.
         *
         * @return The created packet.
         * @throws Throwable if an error occurs during construction.
         */
        Object invoke() throws Throwable;
    }
}
