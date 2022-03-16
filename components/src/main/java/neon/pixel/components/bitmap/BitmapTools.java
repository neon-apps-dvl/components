package neon.pixel.components.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import java.io.File;
import java.net.URL;

public class BitmapTools {
    public static Bitmap from (Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap (drawable.getIntrinsicWidth (), drawable.getIntrinsicHeight (), Bitmap.Config.ARGB_8888);
        Canvas wrapper = new Canvas (bitmap);
        drawable.setBounds (0, 0, wrapper.getWidth (), wrapper.getHeight ());

        drawable.draw (wrapper);

        return bitmap;
    }

    public static Bitmap from (Resources resources, @DrawableRes int drawable) {
        Bitmap bitmap = BitmapFactory.decodeResource (resources, drawable);

        return bitmap;
    }

    public static Bitmap from (Bitmap source, int x, int y, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap (source, x, y, w, h);

        return bitmap;
    }

    public static Bitmap scale (Bitmap source, int w, int h) {
        return Bitmap.createScaledBitmap (source, w, h, true);
    }

    public static Bitmap scale (Bitmap source, float scaleX, float scaleY) {
        int destW = (int) (source.getWidth () * scaleX);
        int destH = (int) (source.getHeight () * scaleY);

        return scale (source, destW, destH);
    }

    public static Bitmap from (String url) {
        try {
            URL bitmapUrl = new URL (url);
            return BitmapFactory.decodeStream (bitmapUrl.openConnection ().getInputStream ());
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static void from (String url, BitmapDownloadCallback callback) {
        try {
            callback.onDownload (from (url));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static Bitmap from (File bitmap) {
        try {
            return BitmapFactory.decodeFile (String.valueOf (bitmap));
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static void from (File bitmap, BitmapDownloadCallback callback) {
        try {
            callback.onDownload (from (bitmap));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
