package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private Node left;
        private Node right;
        private final K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        return getKey(key, root) != null;
    }

    @Override
    public V get(K key) {
        return get(key, root);
    }

    private V get(K key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = n.key.compareTo(key);
        if (cmp > 0) {
            return get(key, n.left);
        }
        if (cmp < 0) {
            return get(key, n.right);
        }
        return n.value;
    }

    private V getKey(K key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = n.key.compareTo(key);
        if (cmp > 0) {
            return get(key, n.left);
        }
        if (cmp < 0) {
            return get(key, n.right);
        }
        return (V) n;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node n) {
        if (n == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = n.key.compareTo(key);
        if (cmp == 0) {
            n.value = value;
        } else if (cmp > 0) {
            n.left = put(key, value, n.left);
        } else {
            n.right = put(key, value, n.right);
        }
        return n;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
//        return Set.of();
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
        throw new UnsupportedOperationException();
    }
}
