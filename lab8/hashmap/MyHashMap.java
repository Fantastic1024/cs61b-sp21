package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

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
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        loadFactor = 0.75;
        collectionSize = 16;
        buckets = createTable(collectionSize);
    }

    public MyHashMap(int initialSize) {
        buckets = createTable(initialSize);
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
        this.loadFactor = maxLoad;
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

    @Override
    public void clear() {
        buckets = createTable(collectionSize);
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
            for (Node i : buckets[hashCodeIndex]) {
                if (i.key == key) {
                    return (V) i;
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
        if (containsKey(key)) {
            Node currentNode = (Node) get(key);
            currentNode.value = value;
            return;
        }

        if (loadCheck()) {
            resizeCol();
        }

        itemSize += 1;
        buckets[hashCodeIndex] = createBucket();
        buckets[hashCodeIndex].add(createNode(key, value));
    }

    private void resizeCol() {
        collectionSize *= 2;
    }

    private boolean loadCheck() {
        double nextLoadFactor = (double) (itemSize + 1) / collectionSize;
        return nextLoadFactor > 1.5;
    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public V remove(K key, V value) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        private int wizPosTable;
        private int wizPosNode;
        private int wizPosCount;
        private Collection<Node> wizPosBucketArray;

        private MyHashMapIterator() {
            wizPosTable = 0;
            wizPosNode = 0;
            Node[] wizPosBucketArray = (Node[]) buckets[wizPosTable].toArray();
        }

        @Override
        public boolean hasNext() {
            return wizPosCount < itemSize;
        }

        @Override
        public K next() {
            if (wizPosBucketArray.isEmpty()) {
                wizPosTable += 1;
            }
            K returnItem = (K) wizPosBucketArray[wizPosNode];
            wizPosNode += 1;
            wizPosCount += 1;
            return returnItem;
        }
    }
}
