package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {

    @Test
    public void testEmptyMaxReturnsNull() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(Integer::compare);
        assertNull(d.max());
        assertNull(d.max(Integer::compare));
    }

    @Test
    public void testSingleItem() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(Integer::compare);
        d.addLast(42);
        assertEquals(Integer.valueOf(42), d.max());
        assertEquals(Integer.valueOf(42), d.max((a, b) -> Integer.compare(b, a))); // 反向也还是 42
    }

    @Test
    public void testMaxUsesDefaultComparator() {
        // 默认：自然升序 -> max 应该是数值最大
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(Integer::compare);
        d.addLast(3);
        d.addLast(7);
        d.addLast(5);
        assertEquals(Integer.valueOf(7), d.max());
    }

    @Test
    public void testMaxWithGivenComparatorOverridesDefault() {
        Comparator<Integer> natural = Integer::compare;
        Comparator<Integer> reverse = (a, b) -> Integer.compare(b, a);

        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(natural);
        d.addLast(3);
        d.addLast(7);
        d.addLast(5);

        // 默认规则：最大是 7
        assertEquals(Integer.valueOf(7), d.max());

        // 传入反向 comparator：按反向比较，“最大”会变成最小元素 3
        assertEquals(Integer.valueOf(3), d.max(reverse));
    }

    @Test
    public void testDuplicates() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(Integer::compare);
        d.addLast(10);
        d.addLast(10);
        d.addLast(9);
        assertEquals(Integer.valueOf(10), d.max());
    }

    @Test
    public void testStringMaxByLength() {
        Comparator<String> byLength = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        MaxArrayDeque<String> d = new MaxArrayDeque<>(byLength);

        d.addLast("a");
        d.addLast("abcd");
        d.addLast("xy");

        assertEquals("abcd", d.max()); // 默认：长度最大
    }

    @Test
    public void testStringMaxByLexicographicWithOverride() {
        Comparator<String> byLength = (s1, s2) -> Integer.compare(s1.length(), s2.length());
        Comparator<String> lexicographic = String::compareTo;

        MaxArrayDeque<String> d = new MaxArrayDeque<>(byLength);
        d.addLast("b");
        d.addLast("aa");
        d.addLast("c");

        // 默认按长度：最大是 "aa"
        assertEquals("aa", d.max());

        // 覆盖成字典序：最大是 "c"
        assertEquals("c", d.max(lexicographic));
    }

    // 可选：如果你的 spec/实现要求传入 null comparator 要抛异常，就启用此测试
    @Test(expected = IllegalArgumentException.class)
    public void testMaxWithNullComparatorThrows() {
        MaxArrayDeque<Integer> d = new MaxArrayDeque<>(Integer::compare);
        d.addLast(1);
        d.max(null);
    }
}

