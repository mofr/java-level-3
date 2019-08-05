import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList <T> fruits;

    public Box () {
        this.fruits = new ArrayList<>();
    }

    public float getWeight() {
        float weight = 0;
        for(int i = 0; i < fruits.size(); i++) {
           weight += fruits.get(i).getWeight();
        }
        return weight;
    }

    public boolean compare(Box box) {
        return getWeight() == box.getWeight();
    }

    public void pourIn (Box <T> box) {
        for (int i = 0; i < fruits.size(); i++) {
            box.addFruit(fruits.get(i));
        }
        clear();
    }

    public void addFruit(T fruit) {
        fruits.add(fruit);
    }

    public void clear () {
        fruits.clear();
    }

    public T getFruit(int index) {
        return fruits.get(index);
    }

    public int getSize() {
        return fruits.size();
    }
}
