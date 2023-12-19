package dev.manere.utils.worldedit;

/**
 * Used to retrieve Async and Sync WorldEditor easily instead of doing:
 * <p>
 * <code><p>
 * new AsyncWorldEditor();
 * <p>
 * new SyncWorldEditor();
 * </code>
 */
public class WorldEditor {
    /**
     * Gets an instance of AsyncWorldEditor.
     * @return An instance of AsyncWorldEditor.
     */
    public static AsyncWorldEditor async() {
        return new AsyncWorldEditor();
    }

    /**
     * Gets an instance of SyncWorldEditor.
     * @return An instance of SyncWorldEditor.
     */
    public static SyncWorldEditor sync() {
        return new SyncWorldEditor();
    }

    /**
     * Gets an instance of AsyncWorldEditor (alias for async() method).
     * @return An instance of AsyncWorldEditor.
     */
    public static AsyncWorldEditor asynchronous() {
        return async();
    }

    /**
     * Gets an instance of SyncWorldEditor (alias for sync() method).
     * @return An instance of SyncWorldEditor.
     */
    public static SyncWorldEditor synchronous() {
        return sync();
    }
}
