package dev.manere.utils.worldedit;

import com.fastasyncworldedit.core.FaweAPI;
import com.fastasyncworldedit.core.util.TaskManager;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Location;

/**
 * Utility class for interacting with WorldEdit in an asynchronous manner using {@link TaskManager#async(Runnable runnable)}.
 */
public class AsyncWorldEditor extends SyncWorldEditor {

    /**
     * This method is run asynchronously using {@link TaskManager#async(Runnable runnable)}
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void paste(Clipboard clipboard, Location corner1, boolean ignoreAir) {
        FaweAPI.getTaskManager().async(() -> super.paste(clipboard, corner1, ignoreAir));
    }

    /**
     * This method is run asynchronously using {@link TaskManager#async(Runnable runnable)}
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void save(Clipboard clipboard, String path) {
        FaweAPI.getTaskManager().async(() -> super.save(clipboard, path));
    }
}
