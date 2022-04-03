package neon.pixel.components.android.dynamictheme;

import neon.pixel.components.android.theme.Theme;

public interface OnThemeChangedListener {
    void onThemeChangedListenerAdded (int id);

    void onThemeChangedListenerRemoved (int id);

    void onThemeChanged (int id, Theme theme);
}
