import java.util.ArrayList;
import java.util.List;

public class Array <T> {
    private T[] array;

    Array(T[] array) {
        this.array = array;
    }

    public void swap(int x, int y) {
            T elementX = array[x];
            array[x] = array[y];
            array[y] = elementX;
    }

    public T getElement(int x) {
        return array[x];
    }

    public List <T> toArrayList() {
        List<T> arrayList = new ArrayList<>(array.length);
        for(int i = 0; i < array.length; i++) {
            arrayList.add(array[i]);
        }
        return arrayList;
    }
}

