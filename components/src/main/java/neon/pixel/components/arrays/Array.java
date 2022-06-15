package neon.pixel.components.arrays;

import java.util.LinkedHashMap;

public class Array {
    @Deprecated
    public Object mKey;
    public LinkedHashMap <Object, Object> mMap;

    public Array () {
        mMap = new LinkedHashMap <> ();
    }

    public Object get (Object key) {
        return mMap.get (key);
    }

    public Array getArray (Object key) {
        Object array;

        if ((array = mMap.get (key)) instanceof Array) {
            return (Array) array;
        }

        return null;
    }

    public void put (Object key, Object item) {
        mMap.put (key, item);
    }

    public void remove (Object key) {
        mMap.remove (key);
    }
}
