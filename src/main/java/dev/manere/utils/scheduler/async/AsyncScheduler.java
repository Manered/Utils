package dev.manere.utils.scheduler.async;

import dev.manere.utils.library.Utils;
import dev.manere.utils.scheduler.SchedulerBase;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An asynchronous scheduler wrapper for {@link SchedulerBase}.
 */
public class AsyncScheduler extends SchedulerBase {
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task) {
        scheduler().runTaskAsynchronously(Utils.plugin(), task);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable) {
        scheduler().runTaskAsynchronously(Utils.plugin(), runnable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task, int afterTicks) {
        scheduler().runTaskLaterAsynchronously(Utils.plugin(), task, afterTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable, int afterTicks) {
        scheduler().runTaskLaterAsynchronously(Utils.plugin(), runnable, afterTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Consumer<BukkitTask> task, int afterTicks, int everyTicks) {
        scheduler().runTaskTimerAsynchronously(Utils.plugin(), task, afterTicks, everyTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Runnable runnable, int afterTicks, int everyTicks) {
        scheduler().runTaskTimerAsynchronously(Utils.plugin(), runnable, afterTicks, everyTicks);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable Object execute(@NotNull Supplier<?> supplier) {
        try {
            return CompletableFuture.supplyAsync(supplier).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> @Nullable T supply(@NotNull Supplier<T> supplier) {
        try {
            return CompletableFuture.supplyAsync(supplier).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
