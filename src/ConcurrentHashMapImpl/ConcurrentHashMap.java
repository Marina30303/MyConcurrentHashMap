package ConcurrentHashMapImpl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;


public class ConcurrentHashMap<K, V> implements Map{

    private Entry MAP_ARRAY[] = new Entry[16];

    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    @Override
    public int size() {

        int sizeOfMap = 0;

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                Entry<K, V> entry=MAP_ARRAY[i];
                while(entry!=null){
                    sizeOfMap++;
                    entry=entry.next;
                }
            }
        }
        return sizeOfMap;
    }

    @Override
    public boolean isEmpty() {

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                return false;
            }
        }
        return true;
    }



    @Override
    public boolean containsValue(Object value) {

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                Entry<K, V> entry=MAP_ARRAY[i];
                while(entry!=null){
                    if (entry.value.equals((V)value))
                        return true;
                    entry=entry.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) {

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                Entry<K, V> entry=MAP_ARRAY[i];
                while(entry!=null){
                    if (entry.key.equals((K)key))
                        return true;
                    entry=entry.next;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {

        if(key == null) return null;

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                Entry<K, V> entry=MAP_ARRAY[i];
                while(entry!=null){
                    if (entry.key.equals((K)key))
                        return entry.value;
                    entry=entry.next;
                }
            }
        }
        return null;
    }

    @Override
    public V put(Object key, Object value) {

        if(value == null) return null;

        int index=hash((K) key);

        Entry<K,V> newEntry = new Entry<K,V>((K) key, (V) value, null);

        if (MAP_ARRAY[index]==null)
            MAP_ARRAY[index]=newEntry;
        else {
            Entry<K, V> current = MAP_ARRAY[index];
            Entry<K, V> previous = MAP_ARRAY[index];
            while (current != null) {
                if(current.key.equals(newEntry.key))
                { current.value=newEntry.value; return newEntry.value;}
                previous= current;
                current = current.next;
            }
            previous.next = newEntry;
        }
        //checking hash
        // System.out.println("Index of element: " + index);
        return newEntry.value;
    }


    public void print(){

        for(int i=0;i<MAP_ARRAY.length;i++){
            if(MAP_ARRAY[i]!=null){
                Entry<K, V> entry=MAP_ARRAY[i];
                while(entry!=null){
                    System.out.print("{"+entry.key+"="+entry.value+"}" +" ");
                    entry=entry.next;
                }
            }
        }

    }

    private int hash(K key){
        return Math.abs(key.hashCode()) % MAP_ARRAY.length;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

        for(int i=0;i<MAP_ARRAY.length;i++)
            MAP_ARRAY[i]=null;

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
