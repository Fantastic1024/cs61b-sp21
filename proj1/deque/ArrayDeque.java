package deque;

public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;
    private int maxSize;

    public ArrayDeque() {
        items = (T[]) new Object[maxSize];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        maxSize = 8;
    }

    private void resizeEnlarge() {
        int firstIndex = (nextFirst + 1) % maxSize;
        int firstSize = maxSize - firstIndex;
//        int lastIndex = (((nextLast - 1) % maxSize) + maxSize) % maxSize;
        int lastSize = maxSize - firstSize;
        maxSize *= 2;
        T[] newArray = (T[]) new Object[maxSize];
        System.arraycopy(items, firstIndex, newArray, 0, firstSize);
        System.arraycopy(items, 0, newArray, firstSize, lastSize);
        items = newArray;
        nextFirst = maxSize - 1;
        nextLast = newArray.length;
    }

    private boolean beforeRatioCheck25() {
        double useRatio = (double) (size - 1) / maxSize;
        return useRatio < 0.25;
    }

    private void resizeShrink() {
        int firstIndex = (nextFirst + 1) % maxSize;
        int lastIndex = (((nextLast - 1) % maxSize) + maxSize) % maxSize;
        int firstSize;
        int lastSize = 0;
        maxSize /= 2;
        T[] newArray = (T[]) new Object[maxSize];
        if (lastIndex > firstIndex) {
            firstSize = (lastIndex - firstIndex + 1);
        } else {
            firstSize = maxSize - firstIndex + 1;
            lastSize = lastIndex + 1;
            System.arraycopy(items, 0, newArray, firstSize, lastSize);
        }
        System.arraycopy(items, firstIndex, newArray, 0, firstSize);
        items = newArray;
        nextFirst = maxSize - 1;
        nextLast = newArray.length;


    }

    public void addFirst(T item) {
        if (size == items.length){
            resizeEnlarge();
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = (((nextFirst - 1) % maxSize) + maxSize) % maxSize;
    }

    public void addLast(T item) {
        if (size == items.length){
            resizeEnlarge();
        }
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % maxSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int firstIndex = (nextFirst + 1) % maxSize;
        for (int i = 0; i < size; i++) {
            System.out.print(items[(firstIndex + i) % maxSize]);
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (beforeRatioCheck25()) {
            resizeShrink();
        }
        T firstItem = items[nextFirst + 1];
        nextFirst += 1;
        size -= 1;

        return firstItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (beforeRatioCheck25()) {
            resizeShrink();
        }
        T lastItem = items[nextLast - 1];
        nextFirst -= 1;
        size -= 1;

        return lastItem;
    }

    public T get(int index) {
        if (size == 0) {
            return null;
        }

        return items[((nextFirst + 1) % maxSize) + index];
    }
}
