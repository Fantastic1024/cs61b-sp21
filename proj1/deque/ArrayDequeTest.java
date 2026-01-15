package deque;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the ArrayDeque class.
 *
 * @author Josh Hug
 */

public class ArrayDequeTest {
    @Test
    public void testEmptySize() {
        ArrayDeque L = new ArrayDeque();
        assertEquals(0, L.size());
    }

    @Test
    public void testAddAndSize() {
        ArrayDeque L = new ArrayDeque<Integer>();
        L.addLast(99);
        L.addLast(99);
        assertEquals(2, L.size());
    }


//    @Test
//    public void testAddAndGetLast() {
//        ArrayDeque L = new ArrayDeque();
//        L.addLast(99);
//        assertEquals(99, L.getLast());
//        L.addLast(36);
//        assertEquals(36, L.getLast());
//    }


    @Test
    public void testGet() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        L.addLast(36);
        L.addLast(36);
        L.addLast(123);
        assertEquals(123, L.get(4));

//        assertEquals(99, L.get(0));
//        assertEquals(36, L.get(1));
//        assertEquals(null, L.get(2));
    }


    @Test
    public void testRemoveLast() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        assertEquals(99, L.get(0));
        L.removeLast();
        assertEquals(99, L.get(0));
        L.addLast(100);
        assertEquals(100, L.get(1));
        assertEquals(2, L.size());
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        assertEquals(36, L.get(1));
        L.removeFirst();
        assertEquals(36, L.get(0));
        L.addFirst(100);
        assertEquals(100, L.get(0));
        assertEquals(2, L.size());
    }

    @Test
    public void testPrint() {
        ArrayDeque L = new ArrayDeque();
        L.addLast(99);
        assertEquals(99, L.get(0));
        L.addLast(36);
        assertEquals(36, L.get(1));
        L.removeFirst();
        assertEquals(36, L.get(0));
        L.addFirst(100);
        assertEquals(100, L.get(0));
        L.printDeque();
        assertEquals(2, L.size());
    }

    /**
     * Tests insertion of a large number of items.
     */
    @Test
    public void testMegaInsert() {
        ArrayDeque L = new ArrayDeque();
        int N = 1000000;
        for (int i = 0; i < N; i += 1) {
            L.addLast(i);
        }

        for (int i = 0; i < N; i += 1) {
            L.addLast(L.get(i));
        }
    }

    @Test
    public void testRemoveRatioLess25() {
        ArrayDeque L = new ArrayDeque();
        int N = 100;
        for (int i = 0; i < N; i += 1) {
            L.addLast(i);
        }
        L.addFirst(123);
        L.removeFirst();
        L.removeLast();

      }

    @Test
    public void iteratorTestArray() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addLast(5);
        lld1.addLast(23);
        lld1.addLast(42);

        for (Object i : lld1) {
            System.out.println(i);
        }
    }

    @Test
    public void containTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addLast(5);
        lld1.addLast(23);
        lld1.addLast(42);

        assertEquals(true, lld1.contains(5));
        assertEquals(false, lld1.contains(6));
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addLast(5);
        lld1.addLast(23);
        lld1.addLast(42);

        ArrayDeque<Integer> lld2 = new ArrayDeque<Integer>();
        lld2.addLast(5);
        lld2.addLast(23);
        lld2.addLast(42);

        assertEquals(true, lld1.equals(lld2));
    }


    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("all", ArrayDequeTest.class);
    }
} 