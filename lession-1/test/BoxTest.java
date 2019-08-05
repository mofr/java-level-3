import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BoxTest {
    @Test
    void testAddFruit() {
        Box <Orange> box = new Box<>();
        Orange orange = new Orange();
        Orange orange1 = new Orange();
        box.addFruit(orange);
        box.addFruit(orange1);

        assertEquals(orange, box.getFruit(0));
        assertEquals(orange1, box.getFruit(1));
    }

    @Test
    void testClear() {
        Box <Apple> box = new Box<>();
        Apple apple = new Apple();
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        box.addFruit(apple);
        box.addFruit(apple1);
        box.clear();
        box.addFruit(apple2);

        assertEquals(apple2, box.getFruit(0));
    }

    @Test
    void testGetWeight() {
        Box <Orange> box = new Box<>();
        Box <Apple> box1 = new Box<>();
        Orange orange = new Orange();
        Orange orange1 = new Orange();
        Apple apple = new Apple();
        Apple apple1 = new Apple();
        box.addFruit(orange);
        box.addFruit(orange1);
        box1.addFruit(apple);
        box1.addFruit(apple1);

        assertEquals(3.0f, box.getWeight());
        assertEquals(2.0f, box1.getWeight());
    }

    @Test
    void testCompare() {
        Box <Orange> box = new Box<>();
        Box <Apple> box1 = new Box<>();
        Box <Apple> box2 = new Box<>();
        Orange orange = new Orange();
        Orange orange1 = new Orange();
        Apple apple = new Apple();
        Apple apple1 = new Apple();
        box.addFruit(orange);
        box.addFruit(orange1);
        box1.addFruit(apple);
        box1.addFruit(apple1);
        box2.addFruit(apple);
        box2.addFruit(apple1);

        assertEquals(false, box.compare(box1));
        assertEquals(true, box1.compare(box2));
    }

    @Test
    void testPourIn() {
        Box <Orange> box = new Box<>();
        Box <Orange> box1 = new Box<>();
        Orange orange = new Orange();
        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        box.addFruit(orange);
        box.addFruit(orange1);
        box.addFruit(orange2);
        box.pourIn(box1);

        assertEquals(orange, box1.getFruit(0));
        assertEquals(orange1, box1.getFruit(1));
        assertEquals(orange2, box1.getFruit(2));
    }

    @Test
    void testGetFruit() {
        Box <Apple> box = new Box<>();
        Apple apple = new Apple();
        Apple apple1 = new Apple();
        box.addFruit(apple);
        box.addFruit(apple1);

        assertEquals(apple, box.getFruit(0));
        assertEquals(apple1, box.getFruit(1));
    }

    @Test
    void testGetSize() {
        Box <Orange> box = new Box<>();
        Orange orange = new Orange();
        Orange orange1 = new Orange();
        box.addFruit(orange);
        box.addFruit(orange1);

        assertEquals(2, box.getSize());
    }
}
