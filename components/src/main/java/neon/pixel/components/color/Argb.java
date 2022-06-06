package neon.pixel.components.color;

import android.graphics.ColorSpace;

import neon.pixel.components.R;

public class Argb {
    float alpha;
    float red;
    float green;
    float blue;

    private Argb (float a, float r, float g, float b) {
        alpha = a;
        red = r;
        green = g;
        blue = b;
    }

    public static Argb from (float alpha, float red, float green, float blue) {
        return new Argb (alpha, red, green, blue);
    }

    public static Argb from (int argb) {
        float alpha = argb >> 24 & 0xff;
        float red = argb >> 16 & 0xff;
        float green = argb >> 8 & 0xff;
        float blue = argb & 0xff;

        return new Argb (alpha, red, green, blue);
    }

    public int toInt () {
        return (int) alpha << 24
                | (int) red << 16
                | (int) green << 8
                | (int) blue;
    }

    public float alpha () {
        return alpha;
    }

    public float red () {
        return red;
    }

    public float green () {
        return green;
    }

    public float blue () {
        return blue;
    }

    public void setAlpha (float alpha) {
        this.alpha = alpha;
    }

    public void setRed (float red) {
        this.red = red;
    }

    public void setGreen (float green) {
        this.green = green;
    }

    public void setBlue (float blue) {
        this.blue = blue;
    }
}
