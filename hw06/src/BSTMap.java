import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{

    public class BSTNode
    {
        BSTNode left;
        BSTNode right;
        K key;
        V value;
        private BSTNode(K k, V v, BSTNode l, BSTNode r)
        {
            key = k;
            value = v;
            left = l;
            right = r;
            size ++;
        }
    }
    BSTNode root;
    int size = 0;
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */

    @Override
    public void put(K key, V value) {
        BSTNode tempNode = root;
        while(tempNode != null) {
            if (tempNode.key.equals(key)) {
                tempNode.value = value;
                return;
            } else if(key.compareTo(tempNode.key) > 0) {
                if (tempNode.right == null) {
                    tempNode.right = new BSTNode(key, value, null, null);
                    return;
                }
                tempNode = tempNode.right;
            } else {
                if (tempNode.left == null) {
                    tempNode.left = new BSTNode(key, value, null, null);
                    return;
                }
                tempNode = tempNode.left;
            }
        }
        root = new BSTNode(key, value, null, null);
    }



    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        BSTNode tempNode = root;
        while(tempNode != null) {
            if (tempNode.key.equals(key)) {
                return tempNode.value;
            } else if(key.compareTo(tempNode.key) > 0) {
                if (tempNode.right == null) {
                    return null;
                }
                tempNode = tempNode.right;
            } else {
                if (tempNode.left == null) {
                    return null;
                }
                tempNode = tempNode.left;
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        BSTNode tempNode = root;
        while(tempNode != null) {
            if (tempNode.key.equals(key)) {
                return true;
            } else if(key.compareTo(tempNode.key) > 0) {
                if (tempNode.right == null) {
                    return false;
                }
                tempNode = tempNode.right;
            } else {
                if (tempNode.left == null) {
                    return false;
                }
                tempNode = tempNode.left;
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
