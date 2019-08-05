import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayTest {
    @Test
    void testGetElement1() {
        Array<String> array = new Array<String>(new String[]{"s", "f", "k"});
        String element = array.getElement(0);

        assertEquals("s", element);
    }

    @Test
    void testGetElement2() {
        Array<Integer> array = new Array<Integer>(new Integer[]{1, 6, 0, 4});
        int element = array.getElement(2);

        assertEquals(0, element);
    }

    @Test
    void testSwap() {
        Array<Integer> array = new Array<Integer>(new Integer[] {4, 8, 12, 52});
        array.swap(1, 2);
        int elementX = array.getElement(1);
        int elementY = array.getElement(2);

        assertEquals(12, elementX);
        assertEquals(8, elementY);
    }
   @Test
    void testToArrayList() {
        Array<Integer> array = new Array<>(new Integer[] {1, 4, 3, 6, 8});
        array.toArrayList();




    }

}
