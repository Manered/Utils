package dev.manere.utils.cachable.impl;

import dev.manere.utils.cachable.Cachable;
import dev.manere.utils.cachable.CachableSnapshot;
import dev.manere.utils.consumers.PairConsumer;
import dev.manere.utils.model.Tuple;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachableImpl<K, V> implements Cachable<K, V> {
    /**
     * Internal {@link ConcurrentHashMap}.
     */
    final Map<K, V> cache = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public V val(K key) {
        return cache.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public K key(V val) {
        for (Map.Entry<K, V> entry : cache.entrySet()) {
            if (entry.getValue().equals(val)) {
                return entry.getKey();
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasKey(K key) {
        return cache.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasVal(V val) {
        return cache.containsValue(val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cache(K key, V val) {
        cache.put(key, val);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void del(K key, V val) {
        if (cache.get(key).equals(val)) {
            cache.remove(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void del(K key) {
        cache.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cacheAll(Collection<Tuple<K, V>> entries) {
        for (Tuple<K, V> entry : entries) {
            cache(entry.key(), entry.val());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cacheAll(Map<K, V> entries) {
        for (Map.Entry<K, V> entry : entries.entrySet()) {
            K key = entry.getKey();
            V val = entry.getValue();

            cache(key, val);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delAll(Collection<K> keys) {
        for (K key : keys) {
            cache.remove(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(PairConsumer<K, V> forEach) {
        cache.forEach(forEach::execute);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int cached() {
        return cache.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        cache.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull CachableSnapshot<K, V> snapshot() {
        return new CachableSnapshotImpl<>(this);
    }
}
