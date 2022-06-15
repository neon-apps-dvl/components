package neon.pixel.components.arrays;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ArrayTools {
    private static final int TYPE_INT = 1;
    private static final int TYPE_LONG = 2;
    private static final int TYPE_FLOAT = 3;
    private static final int TYPE_DOUBLE = 4;
    private static final int TYPE_CHAR = 5;
    private static final int TYPE_STRING = 6;
    private static final int TYPE_BITMAP = 7;

    private static final int SIZE_INT = 4;
    private static final int SIZE_LONG = 8;
    private static final int SIZE_FLOAT = 4;
    private static final int SIZE_DOUBLE = 8;
    private static final int SIZE_CHAR = 16;

    private static final int CONFIG_ARGB_8888 = 0;
    private static final int CONFIG_ALPHA_8 = 1;
    private static final int CONFIG_RGBA_F16 = 2;
    private static final int CONFIG_HARDWARE = 3;

    private static final String TAG = "DataMap";

    public ArrayTools () { // TODO rename class to ArrayTools

    }

    public static byte[] toBytes (Array array) {
        Stack <Object> stack = new Stack <> ();
        Set <Object> set = new HashSet <> ();

        int bufferSize = 0;
        List <byte[]> byteBuffer = new ArrayList <> ();

        Array p = null;

        stack.push (array);

        while (!stack.isEmpty ()) {
            Object o = stack.pop ();

            if (!set.contains (o)) {
                set.add (o);

                if (o instanceof Array) {
                    Object key = null;

                    if (p != null) {
                        for (Object k : p.mMap.keySet ()) {
                            if (p.mMap.get (k).equals (o)) {
                                key = k;
                            }
                        }
                    }

                    int depth = 0;

                    for (Object value : ((Array) o).mMap.values ()) {

                        if (value instanceof Array) {
                            depth++; // next [depth] arrays are subarrays of o
                        }
                    }

                    depth = ((Array) o).mMap.size ();

                    byte[] arrayHeader = createArrayHeader (key, depth);
                    byteBuffer.add (arrayHeader);

                    bufferSize += arrayHeader.length;

                    for (Object k : ((Array) o).mMap.keySet ()) {
                        Object v = ((Array) o).mMap.get (k);

                        if (v instanceof Array) {
                            p = (Array) o;
                            stack.push (v);
                        } else {
                            byte[] value = createValue (k, v);

                            byteBuffer.add (value);

                            bufferSize += value.length;
                        }
                    }
                }
            }
        }

        byte[] bytes = new byte[bufferSize];

        int i = 0;

        for (byte[] packet : byteBuffer) {
            for (byte b : packet) {
                bytes[i] = b;

                i++;
            }
        }

        return bytes;
    }

    public static Array from (byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap (bytes);
        Stack <Array> arrayStack = new Stack <> ();
        Stack <Integer> depthStack = new Stack <> ();
        Array a = new Array ();
        a.mKey = "main";

        byteBuffer.position (2);
        Integer d = byteBuffer.getInt ();

        while (byteBuffer.hasRemaining ()) {
            byte type = byteBuffer.get ();

            try {
                if (type == 0) {
                    // array

                    byte keyType = byteBuffer.get ();
                    Object key = nextValue (byteBuffer, keyType);

                    Integer depth = byteBuffer.getInt ();

                    Array array = new Array ();
                    array.mKey = key;
                    a.put (key, array);
                    arrayStack.push (a);
                    a = array;

                    d--;
                    depthStack.push (d);
                    d = new Integer (depth);
                } else if (type == 1) {
                    // value
                    int keyType = byteBuffer.get ();

                    Object key = nextValue (byteBuffer, keyType);

                    byte valueType = byteBuffer.get ();
                    Object value = nextValue (byteBuffer, valueType);

                    d--;
                    a.put (key, value);
                }

                if (d == 0) {
                    if (!arrayStack.isEmpty ()) {
                        a = arrayStack.pop ();
                        d = depthStack.pop ();
                    } else {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace ();
            }
        }

        return arrayStack.pop ();
    }

    private static Object nextValue (ByteBuffer byteBuffer, int type) {
        Object object = null;

        if (type == TYPE_INT) {
            object = byteBuffer.getInt ();
        } else if (type == TYPE_FLOAT) {
            object = byteBuffer.getFloat ();
        } else if (type == TYPE_LONG) {
            object = byteBuffer.getLong ();
        } else if (type == TYPE_DOUBLE) {
            object = byteBuffer.getDouble ();
        } else if (type == TYPE_CHAR) {
            object = byteBuffer.getChar ();
        } else if (type == TYPE_STRING) {
            int l = byteBuffer.getInt ();
            byte[] string = new byte[l];

            byteBuffer.get (string);

            object = new String (string);
        } else if (type == TYPE_BITMAP) {
            int l = byteBuffer.getInt ();
            int w = byteBuffer.getInt ();
            int h = byteBuffer.getInt ();
            int config = byteBuffer.getInt ();
            byte[] b = new byte[l];

            byteBuffer.get (b);

            Bitmap bitmap = Bitmap.createBitmap (w, h, getBitmapConfig (config));
            bitmap.copyPixelsFromBuffer (ByteBuffer.wrap (b));

            object = bitmap;
        } else {}

        return object;
    }

    private static Object fromBytes (byte[] o) {
        ByteBuffer b = ByteBuffer.wrap (o);
        int type = b.getInt ();

        Object object = null;

        if (type == TYPE_INT) {
            object = b.getInt ();
        } else if (type == TYPE_FLOAT) {
            object = b.getFloat ();
        } else if (type == TYPE_LONG) {
            object = b.getLong ();
        } else if (type == TYPE_DOUBLE) {
            object = b.getDouble ();
        } else if (type == TYPE_CHAR) {
        } else if (type == TYPE_STRING) {

        } else if (type == TYPE_BITMAP) {
        }

        return object;
    }

    private static byte[] createValue (Object key, Object value) {
        byte[] h = new byte[] {1};
        byte[] k = toBytes (key);
        byte[] v = toBytes (value);

        return ByteBuffer.allocate (h.length + k.length + v.length)
                .put (h)
                .put (k)
                .put (v)
                .array ();
    }

    private static byte[] createArrayHeader (Object key, int depth) {
        byte[] h = new byte[] {0};
        byte[] k = key != null ? toBytes (key) : new byte[1];

        return ByteBuffer.allocate (h.length + k.length + SIZE_INT)
                .put (h)
                .put (k)
                .putInt (depth)
                .array ();
    }

    private static byte[] toBytes (Object o) {
        Class c = o.getClass ();

        ByteBuffer b = null;

        if (c.equals (Integer.class)) {
            b = ByteBuffer.allocate (SIZE_INT + 1);
            b.put (new byte[] {1});
            b.putInt ((Integer) o);
        } else if (c.equals (Long.class)) {
            b = ByteBuffer.allocate (SIZE_LONG + 1);
            b.put (new byte[] {2});
            b.putFloat ((Float) o);
        } else if (c.equals (Float.class)) {
            b = ByteBuffer.allocate (SIZE_FLOAT + 1);
            b.put (new byte[] {3});
            b.putLong ((Long) o);
        } else if (c.equals (Double.class)) {
            b = ByteBuffer.allocate (SIZE_DOUBLE + 1);
            b.put (new byte[] {4});
            b.putDouble ((Double) o);
        } else if (c.equals (Character.class)) {
            b = ByteBuffer.allocate (SIZE_CHAR + 1);
            b.put (new byte[] {5});
            b.putChar ((Character) o);
        } else if (c.equals (String.class)) {
            byte[] string = ((String) o).getBytes ();
            b = ByteBuffer.allocate (string.length + SIZE_INT + 1);
            b.put (new byte[] {6});
            b.putInt (string.length);
            b.put (string);
        } else if (c.equals (Bitmap.class)) {
            Bitmap bitmap = (Bitmap) o;
            int l = bitmap.getByteCount ();
            b = ByteBuffer.allocate (l + 4 * SIZE_INT + 1);
            b.put (new byte[] {7});
            b.putInt (l);
            b.putInt (bitmap.getWidth ());
            b.putInt (bitmap.getHeight ());
            b.putInt (getBitmapConfig (bitmap.getConfig ()));
            ((Bitmap) o).copyPixelsToBuffer (b);
        } else {

        }

        return b.array ();
    }

    private static Bitmap.Config getBitmapConfig (int config) {
        switch (config) {
            case CONFIG_ARGB_8888:
                return Bitmap.Config.ARGB_8888;

            case CONFIG_ALPHA_8:
                return Bitmap.Config.ALPHA_8;

            case CONFIG_RGBA_F16:
                return Bitmap.Config.RGBA_F16;

            case CONFIG_HARDWARE:
                return Bitmap.Config.HARDWARE;
        }

        return Bitmap.Config.ARGB_8888;
    }

    private static int getBitmapConfig (Bitmap.Config config) {
        switch (config) {
            case ARGB_8888:
                return CONFIG_ARGB_8888;

            case ALPHA_8:
                return CONFIG_ALPHA_8;

            case RGBA_F16:
                return CONFIG_RGBA_F16;

            case HARDWARE:
                return CONFIG_HARDWARE;
        }

        return CONFIG_ARGB_8888;
    }
}

/*



TYPE KEY TYPE VALUE

LENGTH KEY ITEM_0 ITEM_1 ...

HOLDER_INDEX LENGTH [LINKED INDICES]
 */