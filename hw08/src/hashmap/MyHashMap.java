package hashmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Vaughan Osahon
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private double loadFactor;
    private int numItems;
    private int capacity;
    final double INITLOADFACTOR = 0.75;
    final int CAPACITY = 16;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        loadFactor = INITLOADFACTOR;
        numItems = 0;
        capacity = CAPACITY;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        loadFactor = INITLOADFACTOR;
        numItems = 0;
        capacity = initialCapacity;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        numItems = 0;
        capacity = initialCapacity;
        buckets = new Collection[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new ArrayList();
    }

    public void put(K key, V value) {
        int bucketNum = Math.floorMod(key.hashCode(), buckets.length);
        boolean isRepeated = false;
        for (Node n: buckets[bucketNum]) {
            if (n.key.equals(key)) {
                n.value = value;
                isRepeated = true;
            }
        }
        if (!isRepeated) {
            buckets[bucketNum].add(new Node(key, value));
            numItems++;
        }
        if ((double) numItems / buckets.length > loadFactor) {
            Collection<Node>[] tempBuckets = buckets;
            clear();
            capacity = capacity * 2;
            buckets = new Collection[capacity];
            for (int i = 0; i < capacity; i++) {
                buckets[i] = createBucket();
            }
            for (Collection<Node> bucket: tempBuckets) {
                for (Node n: bucket) {
                    put(n.key, n.value);
                }
            }
        }
    }
    public V get(K key) {
        int bucketNum = Math.floorMod(key.hashCode(), buckets.length);
        for (Node n: buckets[bucketNum]) {
            if (key.equals(n.key)) {
                return n.value;
            }
        }
        return null;
    }
    public boolean containsKey(K key) {
        int bucketNum = Math.floorMod(key.hashCode(), buckets.length);
        for (Node n: buckets[bucketNum]) {
            if (key.equals(n.key)) {
                return true;
            }
        }
        return false;
    }
    public int size() {
        return numItems;
    }
    public void clear() {
        buckets = new Collection[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = createBucket();
        }
        numItems = 0;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }


    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
