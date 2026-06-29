import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>
{
    int size;
    Node sentinel;
    public class Node
    {
        T item;
        Node prev;
        Node next;
        Node(T item, Node prev, Node next)
        {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    public LinkedListDeque61B()
    {
        size = 0;
        sentinel = new Node (null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

    }


    @Override
    public void addFirst(T x)
    {
        Node fNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = fNode;
        sentinel.next = fNode;
        size ++;
    }

    @Override
    public void addLast(T x)
    {
        Node lNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = lNode;
        sentinel.prev = lNode;
        size ++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node copyList = sentinel.next;
        while (copyList.item != null)
        {
            returnList.add((T) copyList.item);
            copyList = copyList.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (sentinel.next == sentinel)
        {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T getFirst() {
        if (sentinel.next == sentinel)
        {
            return null;
        }
        return (T) sentinel.next.item;
    }

    @Override
    public T getLast() {
        if (sentinel.prev == sentinel)
        {
            return null;
        }
        return (T) sentinel.prev.item;
    }

    @Override
    public T removeFirst() {
        if(sentinel.next != sentinel)
        {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if(sentinel.prev != sentinel)
        {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if(index >= 0 && index < size)
        {
            Node copyList = sentinel.next;
            for (int i = 0; i < size; i++)
            {
                if (index == i)
                {
                    return (T) copyList.item;
                }
                copyList = copyList.next;
            }
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        if (index >= 0 && index < size)
        {
            Node copyList = sentinel.next;
            return help(index, copyList);

        }
        return null;
    }
    public T help(int index, Node copyList)
    {
        if (index == 0)
        {
            return copyList.item;
        }
        return help(index - 1, copyList.next);
    }
    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
