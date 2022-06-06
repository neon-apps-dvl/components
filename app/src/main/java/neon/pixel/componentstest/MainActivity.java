package neon.pixel.componentstest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import neon.pixel.components.color.Argb;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        int i = 0;
        float f = 1.2f;
        double d = 2d;
        long l = 10l;
        char c = 'c';

        Array dataMap = new Array ();

        Array car = new Array ();
        car.put ("p1", "wheels");
        car.put ("p2", "engine");

        Array rx7 = new Array ();
        rx7.put ("engine", "rotary");
        rx7.put ("color", "pink");

        Array r34 = new Array ();
        r34.put ("name", "gtr");
        r34.put ("engine", "rb26dett");

        car.put ("r34", r34);

        car.put ("rx7", rx7);

        car.put ("carValue1", "1");
        car.put ("carValue2", "2");

        dataMap.put ("car", car);

        dataMap.put ("desc", "datamap value");

//        Log.e (TAG, "INDEX");
//        Log.e (TAG, "car: " + car);
//        Log.e (TAG, "rx7: " + rx7);
//        Log.e (TAG, "r34: " + r34);
//        Log.e (TAG, "");

        byte[] map = ArrayTools.toBytes (dataMap);

        Array dm = ArrayTools.from (map);
        String desc = (String) dm.get ("desc");

        String r34Engine = (String) dm.getArray ("car")
                .getArray ("r34")
                .get ("engine");

//        Log.e (TAG, "engine: " + r34Engine);

        int color = Color.argb (255, 255, 0, 0);

        Argb argb = Argb.from (color);

        argb.setAlpha (2);
        argb.setBlue (76);

        Log.e (TAG, "color int: " + color);
        Log.e (TAG, "argb int: " + argb.toInt ());
        Log.e (TAG, "argb a: " + argb.alpha ());
        Log.e (TAG, "argb r: " + argb.red ());
        Log.e (TAG, "argb g: " + argb.green ());
        Log.e (TAG, "argb b: " + argb.blue ());
    }
}