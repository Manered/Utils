package dev.manere.utils.elements.impl;

import dev.manere.utils.cachable.Cachable;
import dev.manere.utils.consumers.PairConsumer;
import dev.manere.utils.elements.Elements;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class ElementsImpl<E> implements Elements<E>, Iterable<E> {
    private final Cachable<Integer, E> internal = Cachable.of();

    /**
     * {@inheritDoc}
     */
    @Override
    public void elements(@NotNull Collection<E> elements) {
        for (E element : elements) {
            internal.cache(internal.cached(), element);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SafeVarargs
    @Override
    public final void elements(@NotNull E... elements) {
        elements(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void element(@Nullable E element) {
        internal.cache(internal.cached(), element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void element(int at, @Nullable E element) {
        internal.cache(at, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @Nullable E element(int at) {
        return internal.val(at);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int at(@NotNull E element) {
        return internal.key(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void del(@NotNull E element) {
        internal.del(internal.key(element), element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void del(int at) {
        internal.del(at);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void del(int at, @NotNull E element) {
        internal.del(at, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delAll(@NotNull Collection<E> elements) {
        for (E element : elements) {
            internal.del(internal.cached(), element);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SafeVarargs
    @Override
    public final void delAll(@NotNull E... elements) {
        delAll(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean has(@NotNull E element) {
        return internal.hasVal(element);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public Iterator<E> iterator() {
        return internal.snapshot().asMap().values().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        for (E element : internal.snapshot().asMap().values()) {
            action.accept(element);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(@NotNull PairConsumer<Integer, E> elementConsumer) {
        internal.forEach(elementConsumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<E> elements() {
        return internal.snapshot().asMap().values();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public @NotNull E[] elementsArray() {
        return (E[]) elements().toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        internal.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return internal.cached();
    }

    /**
     * Deletes all null elements.
     */
    public void delAllNull() {
        this.forEach((at, what) -> {
            if (what == null) {
                this.del(at);
            }
        });
    }

    /**
     * Sorts the integer keys and returns a list of elements in sorted order.
     *
     * @return a list of elements sorted by their integer keys
     */
    public ElementsImpl<E> sortKeys() {
        List<E> sorted =  internal.snapshot().asMap().entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();

        return Elements.of(sorted);
    }
}
