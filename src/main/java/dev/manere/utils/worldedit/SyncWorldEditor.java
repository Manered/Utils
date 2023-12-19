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
import java.util.Objects;

/**
 * Utility class for interacting with WorldEdit in a synchronous manner.
 */
public class SyncWorldEditor {
    /**
     * Copies a region specified by two corner locations.
     *
     * @param corner1 The first corner location.
     * @param corner2 The second corner location.
     * @param shouldCopyEntities Whether to copy entities along with blocks.
     * @return The copied clipboard.
     */
    public Clipboard copy(Location corner1, Location corner2, boolean shouldCopyEntities) {
        BlockVector3 bottom = BlockVector3.at(corner1.getBlockX(), corner1.getBlockY(), corner1.getBlockZ());
        BlockVector3 top = BlockVector3.at(corner2.getBlockX(), corner2.getBlockY(), corner2.getBlockZ());

        CuboidRegion region = new CuboidRegion(bottom, top);
        Clipboard clipboard = new BlockArrayClipboard(region);

        clipboard.setOrigin(BlockVector3.at(corner1.getX(), corner1.getY(), corner1.getZ()));

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(Objects.requireNonNull(corner1.getWorld()).getName()))) {
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());

            forwardExtentCopy.setCopyingEntities(shouldCopyEntities);

            Operations.complete(forwardExtentCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clipboard;
    }

    /**
     * Pastes a clipboard at a specified location.
     *
     * @param clipboard The clipboard to paste.
     * @param corner1 The corner location where the clipboard will be pasted.
     * @param ignoreAir Whether to ignore air blocks during paste.
     */
    public void paste(Clipboard clipboard, Location corner1, boolean ignoreAir) {
        if (clipboard == null) return;

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(Objects.requireNonNull(corner1.getWorld()).getName()))) {
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
     * Saves a clipboard to a file.
     *
     * @param clipboard The clipboard to save.
     * @param path The file path where the clipboard will be saved.
     */
    public void save(Clipboard clipboard, String path) {
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
     * Loads a clipboard from a file.
     *
     * @param path The file path from which to load the clipboard.
     * @return The loaded clipboard, or null if an error occurs.
     */
    public Clipboard load(String path) {
        File file = new File(path);

        if (!file.exists()) return null;

        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try (ClipboardReader reader = Objects.requireNonNull(format).getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return clipboard;
    }
}