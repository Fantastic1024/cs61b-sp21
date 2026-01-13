package deque;

public class LinkedListDeque<T> {
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
        for (int i = 0; i <= index; i++) {
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
        getReHelper(front.next, index - 1);
        return null; // ?
    }

    public T getRecursive(int index) {
        return getReHelper(sentinel.next, index);
    }
}
