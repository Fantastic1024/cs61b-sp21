package deque;

public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;
    private int maxSize;

    public ArrayDeque() {
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        maxSize = 8;
        items = (T[]) new Object[maxSize];
    }

    private void resizeEnlarge() {
        int firstIndex = (nextFirst + 1) % maxSize;
        int firstSize = maxSize - firstIndex;
        int lastSize = maxSize - firstSize;
        maxSize *= 2;
        T[] newArray = (T[]) new Object[maxSize];
        System.arraycopy(items, firstIndex, newArray, 0, firstSize);
        System.arraycopy(items, 0, newArray, firstSize, lastSize);
        items = newArray;
        nextFirst = maxSize - 1;
        nextLast = size;
    }

    private boolean beforeRatioCheck25() {
        double useRatio = (double) (size - 1) / maxSize;
        return useRatio < 0.25;
    }

    private void resizeShrink() {
        int firstIndex = (nextFirst + 1) % maxSize;
        int lastIndex = (((nextLast - 1) % maxSize) + maxSize) % maxSize;
        int firstSize = maxSize - firstIndex;
        int lastSize = 0;
        maxSize /= 2;
        T[] newArray = (T[]) new Object[maxSize];
        if (lastIndex > firstIndex) {
            firstSize = (lastIndex - firstIndex + 1);
        } else {
            lastSize = lastIndex + 1;
            System.arraycopy(items, 0, newArray, firstSize, lastSize);
        }
        System.arraycopy(items, firstIndex, newArray, 0, firstSize);
        items = newArray;
        nextFirst = maxSize - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        if (maxSize == size) {
            resizeEnlarge();
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = (((nextFirst - 1) % maxSize) + maxSize) % maxSize;
    }

    public void addLast(T item) {
        if (maxSize == size) {
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
            System.out.print(items[(firstIndex + i) % maxSize] + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size >= 16 && beforeRatioCheck25()) {
            resizeShrink();
        }
        T firstItem = items[(nextFirst + 1) % maxSize];
        nextFirst = (nextFirst + 1) % maxSize;
        size -= 1;

        return firstItem;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size >= 16 && beforeRatioCheck25()) {
            resizeShrink();
        }
        T lastItem = items[(((nextLast - 1) % maxSize) + maxSize) % maxSize];
        nextLast = (((nextLast - 1) % maxSize) + maxSize) % maxSize;
        size -= 1;

        return lastItem;
    }

    public T get(int index) {
        if (size == 0 || index > size - 1) {
            return null;
        }

        return items[(((nextFirst + 1) % maxSize) + index) % maxSize];
    }
}
