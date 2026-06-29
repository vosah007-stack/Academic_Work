import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>, Iterable<T>{
    int nextFirst;
    int nextLast;
    T[] AList;
    int size;
    public ArrayDeque61B()
    {
        size = 0;
        AList = (T[]) new Object[8];
        nextFirst = AList.length/2;
        nextLast = nextFirst + 1;
    }
    @Override
    public void addFirst(T x) {
        if (size == AList.length)
        {
            resizeUp();
        }
        AList[nextFirst] = x;
        size ++;
        nextFirst --;
        if(nextFirst < 0)
        {
            nextFirst = AList.length - 1;
        }
    }

    @Override
    public void addLast(T x) {
        if (size == AList.length)
        {
            resizeUp();
        }
        AList[nextLast] = x;
        size ++;
        nextLast ++;
        if (nextLast >= AList.length)
        {
            nextLast = 0;
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size <= 0)
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
        if (size == 0)
        {
            return null;
        }
        if (nextFirst == AList.length - 1)
        {
            return AList[0];
        }
        return AList[nextFirst + 1];
    }

    @Override
    public T getLast() {
        if (size == 0)
        {
            return null;
        }
        if (nextLast == 0)
        {
            return AList[AList.length - 1];
        }
        return AList[nextLast - 1];
    }

    @Override
    public T removeFirst() {
        if(AList.length >= 16 && size <= AList.length/4)
        {
            resizeDown();
        }
        T returnItem;
        if (size == 0)
        {
            return null;
        }
        if (nextFirst == AList.length - 1)
        {
            returnItem = AList[0];
            AList[0] = null;
            nextFirst = 0;
            size --;
            return returnItem;
        }
        returnItem = AList[nextFirst + 1];
        AList[nextFirst + 1] = null;
        nextFirst ++;
        size --;
        return returnItem;
    }

    @Override
    public T removeLast() {
        if(AList.length >= 16 && size <= AList.length/4)
        {
            resizeDown();
        }
        T returnItem;
        if (size == 0)
        {
            return null;
        }
        if (nextLast == 0)
        {
            returnItem = AList[AList.length - 1];
            AList[AList.length - 1] = null;
            size--;
            nextLast = AList.length - 1;
            return returnItem;
        }
        returnItem = AList[nextLast - 1];
        AList[nextLast - 1] = null;
        size --;
        nextLast --;
        return returnItem;
    }

    @Override
    public T get(int index) {
        int adjIndex;
        if (index >= size || index < 0)
        {
            return null;
        }
        if (nextFirst == AList.length - 1)
        {
            return AList[index];
        }
        adjIndex = index + nextFirst + 1;
        return AList[adjIndex - (adjIndex / AList.length) * AList.length];

    }

    public void resizeUp()
    {
        T[] arrayCopy = (T[]) new Object[size * 2];
        for (int i = arrayCopy.length/4; i < arrayCopy.length/4 + size; i++)
        {
            arrayCopy[i] = get(i - arrayCopy.length/4);
        }
        AList = arrayCopy;
        nextFirst = arrayCopy.length/4 - 1;
        nextLast = arrayCopy.length/4 + size;
    }
    public void resizeDown()
    {
        T[] arrayCopy = (T[]) new Object[AList.length / 2];
        for (int i = arrayCopy.length/4; i < arrayCopy.length/4 + size; i++)
        {
            arrayCopy[i] = get(i - arrayCopy.length/4);
        }
        AList = arrayCopy;
        nextFirst = arrayCopy.length/4 - 1;
        nextLast = arrayCopy.length/4 + size;
    }

    @Override
    public Iterator<T> iterator() {
        return new AListIterator();
    }

    private class AListIterator implements Iterator<T>
    {
        private int wizPos;
        public AListIterator()
        {
            wizPos = 0;
        }
        public boolean hasNext()
        {
            return wizPos < size;
        }
        public T next()
        {
            T returnItem = get(wizPos);
            wizPos ++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ArrayDeque61B<?> uddaAList)
        {
            if (this.size == uddaAList.size)
            {
                for (int i = 0; i < this.size; i++)
                {
                    if (uddaAList.get(i) != get(i))
                    {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString()
    {
        String returnMes = "[";
        for (int i = 0; i < size; i ++)
        {
            returnMes = returnMes + get(i) + ", ";
        }
        returnMes = "]";
        return returnMes;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

}
