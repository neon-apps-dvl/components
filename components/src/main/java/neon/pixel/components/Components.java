package neon.pixel.components;

import android.content.Context;

public class Components {
    public static int getPx (Context context, int dp) {
        return (int) (dp * context.getResources ().getDisplayMetrics ().density);
    }

    public static int getDp (Context context, int px) {
        return (int) (px / context.getResources ().getDisplayMetrics ().density);
    }

    public static float getPxF (Context context, int dp) {
        return dp * context.getResources ().getDisplayMetrics ().density;
    }

    public static float getDpF (Context context, int px) {
        return px / context.getResources ().getDisplayMetrics ().density;
    }
}
