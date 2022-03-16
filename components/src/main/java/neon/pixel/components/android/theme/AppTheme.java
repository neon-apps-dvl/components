package neon.pixel.components.android.theme;

import static neon.pixel.components.android.color.Color.TONE_CONTAINER_DARK;
import static neon.pixel.components.android.color.Color.TONE_CONTAINER_LIGHT;
import static neon.pixel.components.android.color.Color.TONE_DARK;
import static neon.pixel.components.android.color.Color.TONE_LIGHT;
import static neon.pixel.components.android.color.Color.TONE_ON_CONTAINER_DARK;
import static neon.pixel.components.android.color.Color.TONE_ON_CONTAINER_LIGHT;
import static neon.pixel.components.android.color.Color.TONE_ON_DARK;
import static neon.pixel.components.android.color.Color.TONE_ON_LIGHT;

import android.util.Log;

import neon.pixel.components.android.color.AppColorProfile;
import neon.pixel.components.color.Hct;

public class AppTheme extends Theme {
    private static final Class <? extends Enum> mProfile = AppColorProfile.class;
    private static final String TAG = "AppTheme:DEBUG";

    public AppTheme () {
        super (mProfile);
    }

    public AppTheme (Theme theme) {
        super (mProfile);

        if (! theme.getProfile ().equals (mProfile)) throw new IllegalArgumentException ();

        System.arraycopy (theme.mColors, 0, mColors, 0, theme.mColors.length);
    }

    public static AppTheme from (int primary, int secondary, int tertiary, int surface) {
        AppTheme appTheme = new AppTheme ();

        appTheme.setSurfaceLight (surface);

        appTheme.setAllPrimaryLight (primary);

        appTheme.setAllSecondaryLight (secondary);

        appTheme.setAllTertiaryLight (tertiary);

        //

        appTheme.setSurfaceDark (surface);

        appTheme.setAllPrimaryDark (primary);

        appTheme.setAllSecondaryDark (secondary);

        appTheme.setAllTertiaryDark (tertiary);

        return appTheme;
    }

    public void setAllPrimaryLight (int color) {
        Hct hct = Hct.fromInt (color);
        hct.setTone (TONE_LIGHT);
        setPrimaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_LIGHT);
        setOnPrimaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_CONTAINER_LIGHT);
        setPrimaryContainerLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnPrimaryContainerLight (hct.toInt ());
    }

    public void setAllSecondaryLight (int color) {
        Hct hct = Hct.fromInt (color);
        hct.setTone (TONE_LIGHT);
        setSecondaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_LIGHT);
        setOnSecondaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_CONTAINER_LIGHT);
        setSecondaryContainerLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnSecondaryContainerLight (hct.toInt ());
    }

    public void setAllTertiaryLight (int color) {
        Hct hct = Hct.fromInt (color);
        hct.setTone (TONE_LIGHT);
        setTertiaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_LIGHT);
        setOnTertiaryLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_CONTAINER_LIGHT);
        setTertiaryContainerLight (hct.toInt ());

        hct = Hct.fromInt (color);
        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnTertiaryContainerLight (hct.toInt ());
    }

    //

    public void setAllPrimaryDark (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_DARK);
        setPrimaryDark (hct.toInt ());

        hct.setTone (TONE_ON_DARK);
        setOnPrimaryDark (hct.toInt ());

        hct.setTone (TONE_CONTAINER_DARK);
        setPrimaryContainerDark (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_DARK);
        setOnPrimaryContainerDark (hct.toInt ());
    }

    public void setAllSecondaryDark (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_DARK);
        setSecondaryDark (hct.toInt ());

        hct.setTone (TONE_ON_DARK);
        setOnSecondaryDark (hct.toInt ());

        hct.setTone (TONE_CONTAINER_DARK);
        setSecondaryContainerDark (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_DARK);
        setOnSecondaryContainerDark (hct.toInt ());
    }

    public void setAllTertiaryDark (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_DARK);
        setTertiaryDark (hct.toInt ());

        hct.setTone (TONE_ON_DARK);
        setOnTertiaryDark (hct.toInt ());

        hct.setTone (TONE_CONTAINER_DARK);
        setTertiaryContainerDark (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_DARK);
        setOnTertiaryContainerDark (hct.toInt ());
    }

    //

    public void setSurfaceLight (int color) {
        setColor (AppColorProfile.SURFACE_LIGHT, color);
    }

    // primary
    public void setPrimaryLight (int color) {
        setColor (AppColorProfile.PRIMARY_LIGHT, color);
    }

    public void setOnPrimaryLight (int color) {
        setColor (AppColorProfile.ON_PRIMARY_LIGHT, color);
    }

    public void setPrimaryContainerLight (int color) {
        Log.e ("AppTheme:DEBUG", "setPrimaryContainerLight: " + color);

        setColor (AppColorProfile.PRIMARY_CONTAINER_LIGHT, color);
    }

    public void setOnPrimaryContainerLight (int color) {
        setColor (AppColorProfile.PRIMARY_ON_CONTAINER_LIGHT, color);
    }

    // secondary
    public void setSecondaryLight (int color) {
        setColor (AppColorProfile.SECONDARY_LIGHT, color);
    }

    public void setOnSecondaryLight (int color) {
        setColor (AppColorProfile.ON_SECONDARY_LIGHT, color);
    }

    public void setSecondaryContainerLight (int color) {
        setColor (AppColorProfile.SECONDARY_CONTAINER_LIGHT, color);
    }

    public void setOnSecondaryContainerLight (int color) {
        setColor (AppColorProfile.SECONDARY_ON_CONTAINER_LIGHT, color);
    }

    // tertiary
    public void setTertiaryLight (int color) {
        setColor (AppColorProfile.TERTIARY_LIGHT, color);
    }

    public void setOnTertiaryLight (int color) {
        setColor (AppColorProfile.ON_TERTIARY_LIGHT, color);
    }

    public void setTertiaryContainerLight (int color) {
        setColor (AppColorProfile.TERTIARY_CONTAINER_LIGHT, color);
    }

    public void setOnTertiaryContainerLight (int color) {
        setColor (AppColorProfile.TERTIARY_ON_CONTAINER_LIGHT, color);
    }


    public void setSurfaceDark (int color) {
        setColor (AppColorProfile.SURFACE_DARK, color);
    }

    // primary
    public void setPrimaryDark (int color) {
        setColor (AppColorProfile.PRIMARY_DARK, color);
    }

    public void setOnPrimaryDark (int color) {
        setColor (AppColorProfile.ON_PRIMARY_DARK, color);
    }

    public void setPrimaryContainerDark (int color) {
        setColor (AppColorProfile.PRIMARY_CONTAINER_DARK, color);
    }

    public void setOnPrimaryContainerDark (int color) {
        setColor (AppColorProfile.PRIMARY_ON_CONTAINER_DARK, color);
    }

    // secondary
    public void setSecondaryDark (int color) {
        setColor (AppColorProfile.SECONDARY_DARK, color);
    }

    public void setOnSecondaryDark (int color) {
        setColor (AppColorProfile.ON_SECONDARY_DARK, color);
    }

    public void setSecondaryContainerDark (int color) {
        setColor (AppColorProfile.SECONDARY_CONTAINER_DARK, color);
    }

    public void setOnSecondaryContainerDark (int color) {
        setColor (AppColorProfile.SECONDARY_ON_CONTAINER_DARK, color);
    }

    // tertiary
    public void setTertiaryDark (int color) {
        setColor (AppColorProfile.TERTIARY_DARK, color);
    }

    public void setOnTertiaryDark (int color) {
        setColor (AppColorProfile.ON_TERTIARY_DARK, color);
    }

    public void setTertiaryContainerDark (int color) {
        setColor (AppColorProfile.TERTIARY_CONTAINER_DARK, color);
    }

    public void setOnTertiaryContainerDark (int color) {
        setColor (AppColorProfile.TERTIARY_ON_CONTAINER_DARK, color);
    }

    //
    //

    // get

    public int getSurfaceLight () {
        return getColor (AppColorProfile.SURFACE_LIGHT);
    }

    // primary
    public int getPrimaryLight () {
        return getColor (AppColorProfile.PRIMARY_LIGHT);
    }

    public int getOnPrimaryLight () {
        return getColor (AppColorProfile.ON_PRIMARY_LIGHT);
    }

    public int getPrimaryContainerLight () {
        return getColor (AppColorProfile.PRIMARY_CONTAINER_LIGHT);
    }

    public int getOnPrimaryContainerLight () {
        return getColor (AppColorProfile.PRIMARY_ON_CONTAINER_LIGHT);
    }

    // secondary
    public int getSecondaryLight () {
        return getColor (AppColorProfile.SECONDARY_LIGHT);
    }

    public int getOnSecondaryLight () {
        return getColor (AppColorProfile.ON_SECONDARY_LIGHT);
    }

    public int getSecondaryContainerLight () {
        return getColor (AppColorProfile.SECONDARY_CONTAINER_LIGHT);
    }

    public int getOnSecondaryContainerLight () {
        return getColor (AppColorProfile.SECONDARY_ON_CONTAINER_LIGHT);
    }

    // tertiary
    public int getTertiaryLight () {
        return getColor (AppColorProfile.TERTIARY_LIGHT);
    }

    public int getOnTertiaryLight () {
        return getColor (AppColorProfile.ON_TERTIARY_LIGHT);
    }

    public int getTertiaryContainerLight () {
        return getColor (AppColorProfile.TERTIARY_CONTAINER_LIGHT);
    }

    public int getOnTertiaryContainerLight () {
        return getColor (AppColorProfile.TERTIARY_ON_CONTAINER_LIGHT);
    }


    public int getSurfaceDark () {
        return getColor (AppColorProfile.SURFACE_DARK);
    }

    // primary
    public int getPrimaryDark () {
        return getColor (AppColorProfile.PRIMARY_DARK);
    }

    public int getOnPrimaryDark () {
        return getColor (AppColorProfile.ON_PRIMARY_DARK);
    }

    public int getPrimaryContainerDark () {
        return getColor (AppColorProfile.PRIMARY_CONTAINER_DARK);
    }

    public int getOnPrimaryContainerDark () {
        return getColor (AppColorProfile.PRIMARY_ON_CONTAINER_DARK);
    }

    // secondary
    public int getSecondaryDark () {
        return getColor (AppColorProfile.SECONDARY_DARK);
    }

    public int getOnSecondaryDark () {
        return getColor (AppColorProfile.ON_SECONDARY_DARK);
    }

    public int getSecondaryContainerDark () {
        return getColor (AppColorProfile.SECONDARY_CONTAINER_DARK);
    }

    public int getOnSecondaryContainerDark () {
        return getColor (AppColorProfile.SECONDARY_ON_CONTAINER_DARK);
    }

    // tertiary
    public int getTertiaryDark () {
        return getColor (AppColorProfile.TERTIARY_DARK);
    }

    public int getOnTertiaryDark () {
        return getColor (AppColorProfile.ON_TERTIARY_DARK);
    }

    public int getTertiaryContainerDark () {
        return getColor (AppColorProfile.TERTIARY_CONTAINER_DARK);
    }

    public int getOnTertiaryContainerDark () {
        return getColor (AppColorProfile.TERTIARY_ON_CONTAINER_DARK);
    }
}
