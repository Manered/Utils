package dev.manere.utils.fastasyncworldedit;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for asynchronous WorldEdit schematic operations.
 */
public class SchematicUtils {

    /**
     * Saves a schematic of the region asynchronously.
     *
     * @param plugin the JavaPlugin instance
     * @param name the name for the schematic file
     * @param corner1 one corner location of the region
     * @param corner2 the opposite corner location of the region
     */
    public static CompletableFuture<Void> saveSchematicAsync(JavaPlugin plugin, String name, Location corner1, Location corner2) {
        return CompletableFuture.runAsync(() -> saveSchematic(plugin, name, corner1, corner2));
    }

    /**
     * Saves a schematic of the region.
     *
     * @param plugin the JavaPlugin instance
     * @param name the name for the schematic file
     * @param corner1 one corner location of the region
     * @param corner2 the opposite corner location of the region
     */
    public static void saveSchematic(JavaPlugin plugin, String name, Location corner1, Location corner2) {
        World world = BukkitAdapter.adapt(corner1.getWorld());
        CuboidRegion region = new CuboidRegion(
                BukkitAdapter.adapt(corner1).toBlockPoint(),
                BukkitAdapter.adapt(corner2).toBlockPoint());

        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);
        clipboard.setOrigin(BukkitAdapter.adapt(corner1).toBlockPoint());

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                world, region, clipboard, region.getMinimumPoint()
        );

        Operations.complete(forwardExtentCopy);

        File schem = getSchematicFile(plugin, name);

        try (ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(Files.newOutputStream(schem.toPath()))) {
            if (!schem.exists()) schem.createNewFile();
            writer.write(clipboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the schematic file for the given name.
     *
     * @param plugin the JavaPlugin instance
     * @param name the name for the schematic file
     * @return the File instance
     */
    public static File getSchematicFile(JavaPlugin plugin, String name) {
        File dir = new File(Objects.requireNonNull(plugin.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit")).getDataFolder() + "/schematics");
        if (!dir.exists()) dir.mkdir();
        return new File(dir, name + ".schem");
    }

    /**
     * Pastes a schematic asynchronously at the given location.
     *
     * @param plugin the JavaPlugin instance
     * @param name the name of the schematic file
     * @param corner1 the location to paste the schematic
     */
    public static CompletableFuture<Void> pasteSchematicAsync(JavaPlugin plugin, String name, Location corner1) {
        return CompletableFuture.runAsync(() -> pasteSchematic(plugin, name, corner1));
    }

    /**
     * Pastes a schematic at the given location.
     *
     * @param plugin the JavaPlugin instance
     * @param name the name of the schematic file
     * @param corner1 the location to paste the schematic
     */
    public static void pasteSchematic(JavaPlugin plugin, String name, Location corner1) {
        File file = getSchematicFile(plugin, name);
        Clipboard clipboard;
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        if (format != null) {
            try (ClipboardReader reader = format.getReader(Files.newInputStream(file.toPath()))) {
                clipboard = reader.read();
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(BukkitAdapter.adapt(corner1.getWorld()))
                        .to(BukkitAdapter.adapt(corner1).toBlockPoint())
                        .build();
                Operations.complete(operation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}