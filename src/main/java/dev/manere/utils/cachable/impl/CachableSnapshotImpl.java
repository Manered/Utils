package dev.manere.utils.cachable.impl;

import dev.manere.utils.cachable.CachableSnapshot;
import dev.manere.utils.model.Tuple;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CachableSnapshotImpl<K, V> implements CachableSnapshot<K, V> {
    /**
     * Internal {@link CachableImpl}
     */
    private final CachableImpl<K, V> cachable;

    CachableSnapshotImpl(CachableImpl<K, V> cachable) {
        this.cachable = cachable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<K, V> asMap() {
        return cachable.cache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Tuple<K, V>> asList() {
        List<Tuple<K, V>> list = new ArrayList<>();

        for (K key : cachable.cache.keySet()) {
            for (V val : cachable.cache.values()) {
                list.add(Tuple.tuple(key, val));
            }
        }

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Tuple<K, V>> asCollection() {
        return asList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tuple<K, V>[] asTupleArray() {
        @SuppressWarnings("unchecked")
        Tuple<K, V>[] tuples = (Tuple<K, V>[]) Array.newInstance(Tuple.class, asList().size());

        int i = 0;
        for (Tuple<K, V> tuple : asList()) {
            tuples[i++] = tuple;
        }

        return tuples;
    }
}
