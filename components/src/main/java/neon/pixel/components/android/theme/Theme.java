package neon.pixel.components.android.theme;

import java.util.Arrays;
import java.util.List;

public class Theme {
    private Class <? extends Enum> mProfile;
    private int [] mColors;

    protected Theme () {

    }

    public static Theme from (Class <? extends Enum> profile) {
        Theme theme = new Theme ();
        theme.mProfile = profile;

        theme.mColors = new int[profile.getEnumConstants ().length];

        return theme;
    }

    public void setColor (Enum color, int value) {
        if (! mProfile.equals (color.getClass ())) throw new IllegalArgumentException ();

        List <Enum> profile = Arrays.asList (mProfile.getEnumConstants ());
        Enum <?> c = Enum.valueOf (mProfile, color.name ());
        int i = profile.indexOf (c);

        mColors [i] = value;
    }

    public void setColors (Enum [] colors, int[] values) {

    }

    public int getColor (Enum color) {
        if (! mProfile.equals (color.getClass ())) throw new IllegalArgumentException ();

        List <Enum> profile = Arrays.asList (mProfile.getEnumConstants ());
        int i = profile.indexOf (color);

        return mColors [i];
    }

    public void getColors (int colors) {

    }

    public Class getProfile () {
        return mProfile;
    }
}
