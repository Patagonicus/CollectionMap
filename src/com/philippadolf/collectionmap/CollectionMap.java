package com.philippadolf.collectionmap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;


public abstract class CollectionMap<K, V> implements ConcurrentMap<K, Collection<V>> {

	interface MapFactory<K, V> {
		public Map<K, Collection<V>> createContentsMap();
	}

	interface CollectionFactory<V> {
		public Collection<V> createCollection();
	}

	// just for the ease of use.
	public interface CollectionMapHelper<K, V> extends CollectionFactory<V>, MapFactory<K, V> {

	}

	private final Map<K, Collection<V>> contents;

	private final CollectionFactory<V> collectionFactory;

	public CollectionMap(MapFactory<K, V> mapFactory, CollectionFactory<V> collectionFactory) {
		if (mapFactory == null) { throw new IllegalArgumentException(new NullPointerException(
				"constructor argument mapFactory")); }
		if (collectionFactory == null) { throw new IllegalArgumentException(new NullPointerException(
				"constructor argument collectionFactory")); }
		this.collectionFactory = collectionFactory;
		contents = mapFactory.createContentsMap();
	}

	public CollectionMap(CollectionMapHelper<K, V> helper) {
		if (helper == null) { throw new IllegalArgumentException(
				new NullPointerException("constructor argument helper")); }
		this.collectionFactory = helper;
		contents = helper.createContentsMap();
	}

	public void clear() {
		synchronized (contents) {
			contents.clear();
		}
	}

	public boolean containsKey(final Object key) {
		synchronized (contents) {
			return contents.containsKey(key);
		}
	}

	public boolean containsValue(final Object value) {
		synchronized (value) {
			return contents.containsValue(value);
		}
	}

	public boolean containsInValue(final V value) {
		synchronized (contents) {
			for (final Collection<V> collection : contents.values()) {
				if (collection.contains(value)) { return true; }
			}
		}
		return false;
	}

	public Set<Map.Entry<K, Collection<V>>> entrySet() {
		return contents.entrySet();
	}

	public Collection<V> get(final Object key) {
		synchronized (contents) {
			return contents.get(key);
		}
	}

	public boolean isEmptry() {
		synchronized (contents) {
			return contents.isEmpty();
		}
	}

	public Set<K> keySet() {
		synchronized (contents) {
			return contents.keySet();
		}
	}

	public Collection<V> put(final K key, final Collection<V> value) {
		synchronized (contents) {
			return contents.put(key, value);
		}
	}

	public boolean add(final K key, final V value) {
		synchronized (contents) {
			Collection<V> collection = contents.get(key);
			if (collection == null) {
				collection = collectionFactory.createCollection();
				contents.put(key, collection);
			}
			return collection.add(value);
		}
	}

	public void putAll(final Map<? extends K, ? extends Collection<V>> m) {
		synchronized (contents) {
			contents.putAll(m);
		}
	}

	public void addAll(final Map<? extends K, ? extends Collection<V>> m) {
		synchronized (contents) {
			for (final Map.Entry<? extends K, ? extends Collection<V>> entry : m.entrySet()) {
				addAll(entry.getKey(), entry.getValue());
			}
		}
	}

	public boolean addAll(final K key, final Collection<? extends V> values) {
		synchronized (contents) {
			Collection<V> collection = contents.get(key);
			if (collection == null) {
				collection = collectionFactory.createCollection();
				contents.put(key, collection);
			}
			return collection.addAll(values);
		}
	}

	public Collection<V> remove(final Object key) {
		synchronized (contents) {
			return contents.remove(key);
		}
	}

	public boolean remove(final Object key, final Object value) {
		synchronized (contents) {
			final Collection<V> collection = contents.get(key);
			if (collection == null) { return false; }
			return collection.remove(value);
		}
	}

	public int size() {
		synchronized (contents) {
			return contents.size();
		}
	}

	public Collection<Collection<V>> values() {
		synchronized (contents) {
			return contents.values();
		}
	}
}
