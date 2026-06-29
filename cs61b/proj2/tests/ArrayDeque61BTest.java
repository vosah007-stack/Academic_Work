import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    // Test both addFirst and addLast
    void testAddFirstAndAddLast()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        test.addFirst(1);
        assertThat(test.getFirst()).isEqualTo(1);
        test.removeFirst();
        test.addLast(2);
        assertThat(test.getLast()).isEqualTo(2);
        test.addFirst(1);
        assertThat(test.toList()).containsExactly(1,2);
        test.addLast(3);
        assertThat(test.toList()).containsExactly(1,2,3);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.addFirst(1);
        assertThat(test.toList()).containsExactly(1);
        test.addFirst(1);
        test.addFirst(1);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.addLast(2);
        assertThat(test.toList()).containsExactly(2);
        test.addLast(3);
        test.addFirst(1);
        assertThat(test.getLast()).isEqualTo(3);
        test.addFirst(0);
        assertThat(test.getFirst()).isEqualTo(0);
        test.addFirst(-1);
        test.addFirst(-2);
        test.addFirst(-3);
        test.addFirst(-4);
        test.addFirst(-5);
        assertThat(test.toList()).containsExactly(-5,-4,-3,-2,-1,0,1,2,3);
        test = new ArrayDeque61B<>();
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        test.addLast(1);
        assertThat(test.toList()).containsExactly(1,1,1,1,1,1,1,1,1);


    }
    @Test
    //test getFirst and getLast
    void testGetFirstAndGetLast()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        assertThat(test.getFirst()).isNull();
        assertThat(test.getLast()).isNull();
        test.addFirst(99);
        assertThat(test.getFirst()).isEqualTo(99);
        assertThat(test.getLast()).isEqualTo(99);
        test.addLast(33);
        assertThat(test.getFirst()).isEqualTo(99);
        assertThat(test.getLast()).isEqualTo(33);
        test.addLast(2);
        test.addFirst(10);
        assertThat(test.getFirst()).isEqualTo(10);
        assertThat(test.getLast()).isEqualTo(2);
    }
    @Test
    //test get
    void testGet()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        assertThat(test.get(2)).isNull();
        test.addFirst(1);
        test.addLast(4);
        assertThat(test.get(1)).isEqualTo(4);
        test.addFirst(2);
        test.addFirst(3);
        test.addFirst(5);
        test.addFirst(6);
        test.addFirst(7);
        assertThat(test.get(3)).isEqualTo(3);
        assertThat(test.get(300)).isNull();
        assertThat(test.get(-300)).isNull();
    }
    @Test
    //tests isEmpty and size
    void testIsEmptyAndSize() {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        assertThat(test.size()).isEqualTo(0);
        assertThat(test.isEmpty()).isEqualTo(true);
        test.addFirst(1);
        assertThat(test.size()).isEqualTo(1);
        assertThat(test.isEmpty()).isEqualTo(false);
        test.addLast(2);
        assertThat(test.size()).isEqualTo(2);
        test.removeLast();
        test.removeLast();
        assertThat(test.size()).isEqualTo(0);
        test.removeLast();
        assertThat(test.size()).isEqualTo(0);

    }
    @Test
    //tests toList
    void testToList()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        assertThat(test.toList()).isEqualTo(new ArrayList<>());
        test.addFirst(1);
        test.addLast(2);
        test.addLast(3);
        assertThat(test.toList()).containsExactly(1,2,3).inOrder();
    }
    @Test
    //tests removeFirst and removeLast
    void testRemoveFirstAndLast()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        assertThat(test.removeFirst()).isNull();
        assertThat(test.removeLast()).isNull();
        test.addFirst(1);
        test.addLast(2);
        test.removeLast();
        assertThat(test.toList()).containsExactly(1).inOrder();
        assertThat(test.removeLast()).isEqualTo(1);
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(2);
        test.removeLast();
        assertThat(test.removeLast()).isEqualTo(2);
        assertThat(test.removeFirst()).isEqualTo(1);
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(2);
        test.removeFirst();
        assertThat(test.removeFirst()).isEqualTo(2);
        test.addFirst(1);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(10);
        test.removeFirst();
        assertThat(test.toList()).containsExactly(2,2,2,2,1).inOrder();
        test.addLast(15);
        test.addLast(15);
        test.addLast(15);
        test.removeLast();
        assertThat(test.toList()).containsExactly(2,2,2,2,1,15,15).inOrder();
        test.addLast(15);
        test.addLast(15);
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        test.removeLast();
        assertThat(test.toList()).containsExactly(2,2,2);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(2);
        test.addFirst(2);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        assertThat(test.toList()).containsExactly(2,2,2);


    }
    @Test
    //tests resize methods
    public void testResize()
    {
        Deque61B<Integer> test = new ArrayDeque61B<>();
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.addFirst(1);
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        test.removeFirst();
        assertThat(test.toList()).containsExactly(1,1,1).inOrder();
    }

}
