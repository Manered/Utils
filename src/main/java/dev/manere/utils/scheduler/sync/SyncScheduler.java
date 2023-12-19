package dev.manere.utils.scheduler.sync;

import dev.manere.utils.library.Utils;
import dev.manere.utils.scheduler.SchedulerBase;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A synchronous scheduler wrapper for {@link SchedulerBase}.
 */
public class SyncScheduler extends SchedulerBase {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task) {
        scheduler().runTask(Utils.plugin(), task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable) {
        scheduler().runTask(Utils.plugin(), runnable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task, int afterTicks) {
        scheduler().runTaskLater(Utils.plugin(), task, afterTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable, int afterTicks) {
        scheduler().runTaskLater(Utils.plugin(), runnable, afterTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task, int afterTicks, int everyTicks) {
        scheduler().runTaskTimer(Utils.plugin(), task, afterTicks, everyTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable, int afterTicks, int everyTicks) {
        scheduler().runTaskTimer(Utils.plugin(), runnable, afterTicks, everyTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Object execute(@NotNull Supplier<?> supplier) {
        return supplier.get();
    }
}
