package dev.manere.utils.worldedit;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * A utility class for working with WorldEdit and schematic files.
 * <p></p>
 * It is likely that setting {@code ignoreAir} in the {@code placeClipboard} method to {@code true} will not work.
 */
public class WorldEditUtils {

    /**
     * Copies a region from the specified corner locations into a clipboard.
     *
     * @param corner1          The first corner of the region.
     * @param corner2          The second corner of the region.
     * @param shouldCopyEntities Whether to copy entities as well.
     * @return The clipboard containing the copied region.
     */
    public static Clipboard copyClipboard(Location corner1, Location corner2, boolean shouldCopyEntities) {
        BlockVector3 bottom = BlockVector3.at(corner1.getBlockX(), corner1.getBlockY(), corner1.getBlockZ());
        BlockVector3 top = BlockVector3.at(corner2.getBlockX(), corner2.getBlockY(), corner2.getBlockZ());

        CuboidRegion region = new CuboidRegion(bottom, top);
        Clipboard clipboard = new BlockArrayClipboard(region);

        clipboard.setOrigin(BlockVector3.at(corner1.getX(), corner1.getY(), corner1.getZ()));

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(corner1.getWorld().getName()))) {
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());

            forwardExtentCopy.setCopyingEntities(shouldCopyEntities);

            Operations.complete(forwardExtentCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clipboard;
    }

    /**
     * Pastes the contents of a clipboard into the world at the specified location.
     *
     * @param clipboard   The clipboard to paste.
     * @param corner1     The corner of the region to paste into.
     * @param ignoreAir   Whether to ignore air blocks during the paste.
     */
    public static void placeClipboard(Clipboard clipboard, Location corner1, boolean ignoreAir) {
        if (clipboard == null) return;

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(corner1.getWorld().getName()))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(corner1.getX(), corner1.getY(), corner1.getZ()))
                    .ignoreAirBlocks(ignoreAir)
                    .build();

            Operations.complete(operation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a clipboard to a schematic file.
     *
     * @param clipboard The clipboard to save.
     * @param path      The path where the schematic file will be saved.
     */
    public static void saveSchematic(Clipboard clipboard, String path) {
        File file = new File(path);

        try {
            ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(file));

            writer.write(clipboard);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a clipboard from a schematic file.
     *
     * @param path The path to the schematic file.
     * @return The loaded clipboard, or null if an error occurs.
     */
    public static Clipboard loadSchematic(String path) {
        File file = new File(path);

        if (!file.exists()) return null;

        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return clipboard;
    }
}