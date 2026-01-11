package deque;

public class DequeList<T> {
    public DequeList prev;
    public T item;
    public DequeList next;

    public DequeList(T x, DequeList p, DequeList n) {
        item = x;
        prev = p;
        next = n;
    }
}
