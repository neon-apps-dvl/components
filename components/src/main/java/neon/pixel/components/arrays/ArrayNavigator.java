package neon.pixel.components.arrays;

public class ArrayNavigator {
    private Array mArray;

    private ArrayNavigator (Array array) {
        mArray = array;
    }

    public static ArrayNavigator wrap (Array array) {
        return new ArrayNavigator (array);
    }

    public void getArray (Object key) {
    }
}
