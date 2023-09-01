package dev.manere.utils.worldedit;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.mask.ExistingBlockMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BaseBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldEditUtils {
    public static Clipboard copyClipboard(JavaPlugin plugin, Location corner1, Location corner2, boolean shouldCopyEntities) {
        BlockVector3 bottom = BlockVector3.at(corner1.getBlockX(), corner1.getBlockY(), corner1.getBlockZ());
        BlockVector3 top = BlockVector3.at(corner2.getBlockX(), corner2.getBlockY(), corner2.getBlockZ());
        CuboidRegion region = new CuboidRegion(bottom, top);
        Clipboard clipboard = new BlockArrayClipboard(region);

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(corner1.getWorld().getName()))) {
            ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(editSession, region, clipboard, region.getMinimumPoint());
            forwardExtentCopy.setCopyingEntities(shouldCopyEntities);
            Operations.complete(forwardExtentCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clipboard;
    }

    public static void placeClipboard(JavaPlugin plugin, Clipboard clipboard, Location location, boolean ignoreAir) {
        if (clipboard == null) return;

        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(location.getWorld().getName()))) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(location.getX(), location.getY(), location.getZ()))
                    .ignoreAirBlocks(ignoreAir)
                    .build();

            Operations.complete(operation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSchematic(JavaPlugin plugin, Clipboard clipboard, String path) {
        File file = new File(path);

        try {
            ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(file));
            writer.write(clipboard);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Clipboard loadSchematic(JavaPlugin plugin, String path) {
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

    public static void clearArea(Location min, Location max) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(FaweAPI.getWorld(min.getWorld().getName()));) {
            Region region = new CuboidRegion(BlockVector3.at(min.getX(), min.getY(), min.getZ()), BlockVector3.at(max.getX(), max.getY(), max.getZ()));
            Pattern pattern = new BaseBlock(BukkitAdapter.adapt(Material.AIR.createBlockData()));
            Mask mask = new ExistingBlockMask(editSession.getExtent());
            editSession.replaceBlocks(region, mask, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}