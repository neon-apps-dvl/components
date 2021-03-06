package neon.pixel.components.android.dynamictheme;

import android.util.Log;

import neon.pixel.components.android.theme.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Class for dynamic application-wide themes
 */
public class DynamicTheme {
    private static final String TAG = DynamicTheme.class.getName ();
    private static Map <Integer, Theme> sThemes;
    private static Map <Integer, List <OnThemeChangedListener>> sListeners;

    /**
     * Create a {@link Theme} instance
     * @param id The ID of this theme
     * @param themeProvider The {@code enum} used to provide the possible colors for this theme
     * @return An instance of {@link Theme}
     */
    public static Theme newInstance (int id, Class <? extends Enum> themeProvider) {
        if (sThemes == null) {
            sThemes = new HashMap <> ();
            sListeners = new HashMap <> ();
        }

        Theme theme = new Theme (themeProvider);

        sThemes.put (id, theme);

        return theme;
    }

    /**
     * Create a {@link Theme} instance
     * @param id The ID of this theme
     * @param themeProvider The {@code enum} used to provide the possible colors for this theme
     * @param l An {@link OnThemeChangedListener} used to notify changes to the theme
     * @return An instance of {@link Theme}
     */
    public static Theme newInstance (int id, Class <? extends Enum> themeProvider, OnThemeChangedListener l) {
        addOnThemeChangedListener (id, l);

        return newInstance (id, themeProvider);
    }

    /**
     * Attach an {@link OnThemeChangedListener} to the {@link DynamicTheme} provider
     * to listen for changes to a {@link Theme}
     * @param id The id of the {@link Theme}
     * @param l The {@link OnThemeChangedListener} to add
     */
    public static void addOnThemeChangedListener (int id, OnThemeChangedListener l) {
        List <OnThemeChangedListener> listeners;

        if ((listeners = sListeners.get (id)) == null) {
            listeners = new ArrayList <> ();
        }

        listeners.add (l);
        sListeners.put (id, listeners);

        l.onThemeChangedListenerAdded (id);
    }

    /**
     * Detach the {@link OnThemeChangedListener} from the {@link DynamicTheme} provider
     * listening for changes to a {@link Theme}
     * @param id The id of the {@link Theme}
     * @param l The {@link OnThemeChangedListener} to remove
     */
    public static void removeOnThemeChangedListener (int id, OnThemeChangedListener l) {
        List <OnThemeChangedListener> listeners;

        if ((listeners = sListeners.get (id)) != null) {
            listeners.remove (l);
        }

        l.onThemeChangedListenerRemoved (id);
    }

    /**
     * Notify the {@link DynamicTheme} provider of a change to a {@link Theme}
     * @param id The id of the {@link Theme}
     */
    public static void notifyThemeChanged (int id) {
        Theme theme = getTheme (id);

        List <OnThemeChangedListener> listeners = sListeners.get (id);

        if (listeners != null) {
            for (OnThemeChangedListener listener : listeners) {
                Executor e = Executors.newSingleThreadExecutor ();
                e.execute (() -> listener.onThemeChanged (id, theme));
            }
        }
    }

    /**
     * Notify the {@link DynamicTheme} provider of a change to a {@link Theme}
     * @param theme The {@link Theme}
     */
    public static void notifyThemeChanged (Theme theme) {
        Integer id = getId (theme);

        if (id == null) throw new NullPointerException ();

        List <OnThemeChangedListener> listeners = sListeners.get (id);

        if (listeners != null) {
            for (OnThemeChangedListener listener : listeners) {
                Executor e = Executors.newSingleThreadExecutor ();
                e.execute (() -> listener.onThemeChanged (id, theme));
            }
        }
    }

    public static Theme getTheme (int id) {
        return sThemes.get (id);
    }

    private static Integer getId (Theme theme) {
        for (Map.Entry entry : sThemes.entrySet ()) {
            if (entry.getValue ().equals (theme)) return (int) entry.getKey ();
        }

        return null;
    }
}
