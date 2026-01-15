package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable {
    int size;
    DequeList sentinel = new DequeList(null, null, null);

    public LinkedListDeque() {
        size = 0;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public void addFirst(T item) {
        size += 1;
        DequeList tempNext = sentinel.next;
        sentinel.next = new DequeList<T>(item, sentinel, sentinel.next);

        if (size == 1) {
            sentinel.prev = sentinel.next;
        } else {
            tempNext.prev = sentinel.next;
        }
    }

    public void addLast(T item) {
        size += 1;
        DequeList tempLast = sentinel.prev;
        sentinel.prev = new DequeList<T>(item, sentinel.prev, sentinel);
        tempLast.next = sentinel.prev;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T firstItem = (T) sentinel.next.item;
        DequeList secondL = sentinel.next.next;
        sentinel.next = secondL;
        secondL.prev = sentinel;

        return firstItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T lastItem = (T) sentinel.prev.item;
        DequeList secondToLast = sentinel.prev.prev;
        sentinel.prev = secondToLast;
        secondToLast.next = sentinel;

        return lastItem;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        DequeList front = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(front.item + " ");
            front = front.next;
        }
        System.out.println();
    }

    public T get(int index) {
        DequeList front = sentinel.next;
        for (int i = 0; i < index; i++) {
            if (front == sentinel) {
                return null;
            }
            front = front.next;
        }

        return (T) front.item;
    }

    private T getReHelper(DequeList front, int index) {
        if (front == sentinel) {
            return null;
        }
        if (index == 0) {
            return (T) front.item;
        }
        return getReHelper(front.next, index - 1);
    }

    public T getRecursive(int index) {
        return getReHelper(sentinel.next, index);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private int wizPos;

        public LinkedListDequeIterator() {
            wizPos = 0;
        }

        public boolean hasNext(){
            return wizPos < size;
        }

        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof LinkedListDeque<?>)) {
            return false;
        }
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (Object item : this) {
            if (!other.contains((T) item)) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(T item) {
        for (int i = 0; i < size; i += 1) {
            if (get(i).equals(item)) {
                return true;
            }
        }
        return false;
    }
}
