package neon.pixel.components.android.theme;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import neon.pixel.components.android.color.AppColorProfile;

public class Theme {
    protected Class <? extends Enum> mProfile;
    protected int [] mColors;

    public Theme (Class <? extends Enum> profile) {
        mProfile = profile;
        mColors = new int[profile.getEnumConstants ().length];
    }

    public void setColor (Enum color, int value) {
        if (! mProfile.equals (color.getClass ())) throw new IllegalArgumentException ();

        List <Enum> profile = Arrays.asList (mProfile.getEnumConstants ());
        Enum <?> c = Enum.valueOf (mProfile, color.name ());
        int i = profile.indexOf (c);

        if (color.equals (AppColorProfile.PRIMARY_CONTAINER_LIGHT)) {
            Log.e ("AppTheme:DEBUG", "set color " + c.name () + " @" + i + ": " + value);
        }

        mColors [i] = value;
    }

    public void setColors (Enum [] colors, int[] values) {

    }

    public int getColor (Enum color) {
        if (! mProfile.equals (color.getClass ())) throw new IllegalArgumentException ();

        List <Enum> profile = Arrays.asList (mProfile.getEnumConstants ());
        int i = profile.indexOf (color);

        if (color.equals (AppColorProfile.PRIMARY_CONTAINER_LIGHT)) {
            Log.e ("AppTheme:DEBUG", "get color " + color.name () + " @" + i + ": " + mColors [i]);
        }

        return mColors [i];
    }

    public void getColors (Enum [] colors) {

    }

    public Class getProfile () {
        return mProfile;
    }
}
