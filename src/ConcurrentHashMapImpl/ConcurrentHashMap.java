package ConcurrentHashMapImpl;

import java.util.*;


public class ConcurrentHashMap<K, V> implements Map<K, V> {

    private Entry MAP_ARRAY[] = new Entry[16];

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public int size() {

        synchronized (MAP_ARRAY) {

            int sizeOfMap = 0;

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        sizeOfMap++;
                        entry = entry.next;
                    }
                }
            }
            return sizeOfMap;
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (MAP_ARRAY) {

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    return false;
                }
            }
            return true;
        }
    }


    @Override
    public boolean containsValue(Object value) {

        synchronized (MAP_ARRAY) {

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        if (entry.value.equals((V) value))
                            return true;
                        entry = entry.next;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public boolean containsKey(Object key) {

        synchronized (MAP_ARRAY) {
            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        if (entry.key.equals((K) key))
                            return true;
                        entry = entry.next;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public V get(Object key) {
        synchronized (MAP_ARRAY) {

            if (key == null) return null;

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        if (entry.key.equals((K) key))
                            return entry.value;
                        entry = entry.next;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public V put(Object key, Object value) {
        synchronized (MAP_ARRAY) {

            if (value == null) return null;

            int indexOfNewElement = hash((K) key);

            Entry<K, V> newEntry = new Entry<K, V>((K) key, (V) value, null);

            if (MAP_ARRAY[indexOfNewElement] == null) {
                MAP_ARRAY[indexOfNewElement] = newEntry;

                System.out.println("1 Index of this - " + indexOfNewElement);
            } else {
                Entry<K, V> current = MAP_ARRAY[indexOfNewElement];
                Entry<K, V> previous = MAP_ARRAY[indexOfNewElement];
                while (current != null) {
                    if (current.key.equals(newEntry.key)) {
                        current.value = newEntry.value;
                        return newEntry.value;
                    }
                    previous = current;
                    current = current.next;
                }
                previous.next = newEntry;
            }

            return newEntry.value;
        }
    }

    @Override
    public V remove(Object key) {

        synchronized (MAP_ARRAY) {
            if (key == null || !containsKey(key)) return null;

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> currentEntry = MAP_ARRAY[i];
                    Entry<K, V> previousEntry = MAP_ARRAY[i];
                    if (currentEntry.key.equals(key) && currentEntry.next == null) {
                        MAP_ARRAY[i] = null;
                        return currentEntry.value;
                    } else
                        while (currentEntry != null) {
                            if (currentEntry.key.equals(key)) {

                                previousEntry.next = currentEntry.next;
                                return currentEntry.value;
                            }
                            previousEntry = currentEntry;
                            currentEntry = currentEntry.next;

                        }
                }
            }

        }
        return null;
    }

    public void print() {
        synchronized (MAP_ARRAY) {

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        System.out.print("{" + entry.key + "=" + entry.value + "}" + " ");
                        entry = entry.next;
                    }
                }
            }
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % MAP_ARRAY.length;
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        synchronized (MAP_ARRAY) {

            if (m.size() == 0) return;

            Set<? extends Map.Entry<? extends K, ? extends V>> newSet = m.entrySet();
            for (Map.Entry<? extends K, ? extends V> entry : newSet) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void clear() {
        synchronized (MAP_ARRAY) {

            for (int i = 0; i < MAP_ARRAY.length; i++)
                MAP_ARRAY[i] = null;
        }

    }

    @Override
    public Set keySet() {
        synchronized (MAP_ARRAY) {
            Set<Entry<K, V>> kes = new HashSet<>();

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        kes.add((Entry<K, V>) entry.key);
                        entry = entry.next;
                    }
                }
            }

            return kes;
        }
    }

    @Override
    public Collection<V> values() {

        //not finished method!

        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        synchronized (MAP_ARRAY) {

            Set<Map.Entry<K, V>> es = new HashSet<>();

            for (int i = 0; i < MAP_ARRAY.length; i++) {
                if (MAP_ARRAY[i] != null) {
                    Entry<K, V> entry = MAP_ARRAY[i];
                    while (entry != null) {
                        es.add((Map.Entry<K, V>) entry);
                        entry = entry.next;
                    }
                }
            }
            return es;
        }
    }
}
