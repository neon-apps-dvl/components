package neon.pixel.components.android.theme;

import static com.pixel.components.android.color.Color.TONE_CONTAINER_LIGHT;
import static com.pixel.components.android.color.Color.TONE_LIGHT;
import static com.pixel.components.android.color.Color.TONE_ON_CONTAINER_LIGHT;
import static com.pixel.components.android.color.Color.TONE_ON_LIGHT;

import com.pixel.components.android.color.AppColorProfile;
import com.pixel.components.color.Hct;

public class AppTheme extends Theme{
    private static final Class mProfile = AppColorProfile.class;

    protected AppTheme () {

    }

    public AppTheme from (int primary, int secondary, int tertiary, int surface) {
        AppTheme appTheme = new AppTheme ();

        appTheme.setAllPrimaryLight (primary);

        appTheme.setAllSecondaryLight (secondary);

        appTheme.setAllTertiaryLight (tertiary);

        return appTheme;
    }

    public void setSurfaceLight (int color) {
        setColor (AppColorProfile.SURFACE_LIGHT, color);
    }

    public void setAllPrimaryLight (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_LIGHT);
        setPrimaryLight (hct.toInt ());

        hct.setTone (TONE_ON_LIGHT);
        setOnPrimaryLight (hct.toInt ());

        hct.setTone (TONE_CONTAINER_LIGHT);
        setPrimaryContainerLight (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnPrimaryContainerLight (hct.toInt ());
    }

    public void setAllSecondaryLight (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_LIGHT);
        setSecondaryLight (hct.toInt ());

        hct.setTone (TONE_ON_LIGHT);
        setOnSecondaryLight (hct.toInt ());

        hct.setTone (TONE_CONTAINER_LIGHT);
        setSecondaryContainerLight (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnSecondaryContainerLight (hct.toInt ());
    }

    public void setAllTertiaryLight (int color) {
        Hct hct = Hct.fromInt (color);

        hct.setTone (TONE_LIGHT);
        setTertiaryLight (hct.toInt ());

        hct.setTone (TONE_ON_LIGHT);
        setOnTertiaryLight (hct.toInt ());

        hct.setTone (TONE_CONTAINER_LIGHT);
        setTertiaryContainerLight (hct.toInt ());

        hct.setTone (TONE_ON_CONTAINER_LIGHT);
        setOnTertiaryContainerLight (hct.toInt ());
    }

    // primary
    public void setPrimaryLight (int color) {
        setColor (AppColorProfile.PRIMARY_LIGHT, color);
    }

    public void setOnPrimaryLight (int color) {
        setColor (AppColorProfile.ON_PRIMARY_LIGHT, color);
    }

    public void setPrimaryContainerLight (int color) {
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
}
