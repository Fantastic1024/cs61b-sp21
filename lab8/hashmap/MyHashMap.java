package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
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
    private int itemSize;
    private int collectionSize;
    private HashSet<K> hashSet;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        loadFactor = 0.75;
        collectionSize = 16;
        buckets = createTable(collectionSize);
        hashSet = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
        hashSet = new HashSet<>();
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        loadFactor = maxLoad;
        hashSet = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
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
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    private int getHashcodeIndex(K key) {
        return Math.floorMod(key.hashCode(), collectionSize);
    }

    private int getHashcodeIndex(K key, int cSize) {
        return Math.floorMod(key.hashCode(), cSize);
    }

    @Override
    public void clear() {
        buckets = createTable(collectionSize);
        itemSize = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int hashCodeIndex = getHashcodeIndex(key);
        return buckets[hashCodeIndex] != null;
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            int hashCodeIndex = getHashcodeIndex(key);
            Collection<Node> bucket = buckets[hashCodeIndex];
            for (Node i : bucket) {
                if (key.equals(i.key)) {
                    return i.value;
                }
            }
        }

        return null;
    }

    @Override
    public int size() {
        return itemSize;
    }

    @Override
    public void put(K key, V value) {
        int hashCodeIndex = getHashcodeIndex(key);
        Collection<Node> bucket = buckets[hashCodeIndex];
        if (containsKey(key)) {
            for (Node i : bucket) {
                if (key.equals(i.key)) {
                    i.value = value;
                    return;
                }
            }
        }

        if (loadCheck()) {
            resizeCol();
            hashCodeIndex = getHashcodeIndex(key);
            bucket = buckets[hashCodeIndex];
        }

        if (bucket == null) {
            bucket = createBucket();
        }

        bucket.add(createNode(key, value));
        buckets[hashCodeIndex] = bucket;
        hashSet.add(key);
        itemSize += 1;
        loadFactor = (double) itemSize / collectionSize;
    }

    private void resizeCol() {
        int enlargedSize = 2 * collectionSize;
        Collection<Node>[] newBuckets = createTable(enlargedSize);
        for (K key : hashSet) {
            int hashCodeIndex = getHashcodeIndex(key, enlargedSize);
            Collection<Node> newBucket = newBuckets[hashCodeIndex];
            if (newBucket == null) {
                newBucket = createBucket();
            }
            newBucket.add(createNode(key, get(key)));
            newBuckets[hashCodeIndex] = newBucket;
        }
        collectionSize = enlargedSize;
        buckets = newBuckets;
        loadFactor = (double) itemSize / collectionSize;
    }

    private boolean loadCheck() {
        double nextLoadFactor = (double) (itemSize + 1) / collectionSize;
        return nextLoadFactor > 0.75;
    }

    @Override
    public Set<K> keySet() {
        return hashSet;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return hashSet.iterator();
    }
}
