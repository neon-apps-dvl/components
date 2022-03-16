package neon.pixel.components.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import neon.pixel.components.Components;
import neon.pixel.components.R;

public class ProgressBar extends CoordinatorLayout {
    ProgressBar containerLayout;
    CoordinatorLayout viewContainer;
    View background;
    GradientDrawable backgroundDrawable;
    View progressBar;

    int width;
    int height;

    int viewHeight;

    int orientation;

    boolean respondToTouch;
    boolean snapToTouch;
    int backgroundColor;
    int color;

    float progress;

    public ProgressBar (Context context) {
        super (context);
    }

    public ProgressBar (Context context, @Nullable AttributeSet rawAttrs) {
        super (context, rawAttrs);

        backgroundDrawable = (GradientDrawable) context.getDrawable (R.drawable.seek_bar_background).mutate ();

        TypedArray seekBarAttrs = context.getTheme().obtainStyledAttributes(
                rawAttrs,
                R.styleable.SeekBarAttrs,
                0, 0);

        TypedArray progressBarAttrs = context.getTheme().obtainStyledAttributes(
                rawAttrs,
                R.styleable.ProgressBarAttrs,
                0, 0);

//        orientation = progressBarAttrs.getInteger (R.styleable.ComponentAttrs_orientation, 0);
        viewHeight = (int) progressBarAttrs.getDimension (R.styleable.ProgressBarAttrs_viewHeight, 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        containerLayout = (ProgressBar) inflater.inflate (R.layout.layout_progress_bar, this, true);
        viewContainer = containerLayout.findViewById (R.id.view_container);
        background = containerLayout.findViewById (R.id.background);
        progressBar = containerLayout.findViewById (R.id.progress_bar);

        containerLayout.setBackground (backgroundDrawable);

        respondToTouch = seekBarAttrs.getBoolean (R.styleable.SeekBarAttrs_respondToTouch, false);
        snapToTouch = seekBarAttrs.getBoolean (R.styleable.SeekBarAttrs_snapToTouch, false);

        TypedValue tempHolder = new TypedValue ();

        context.getTheme ().resolveAttribute (com.google.android.material.R.attr.colorPrimaryContainer, tempHolder,  true);
        backgroundColor = tempHolder.data;

        context.getTheme ().resolveAttribute (com.google.android.material.R.attr.colorPrimary, tempHolder,  true);
        color = tempHolder.data;

        background.setBackgroundColor (backgroundColor);
        progressBar.setBackgroundColor (color);

        if (respondToTouch) setOnTouchListener (onTouchListener);
    }

    public ProgressBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);

        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        int minh = (int) (48 * getResources ().getDisplayMetrics ().density);
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        width = w;
        height = h;

        LayoutParams params = (LayoutParams) viewContainer.getLayoutParams ();
        params.height = viewHeight;
        viewContainer.setLayoutParams (params);

        backgroundDrawable.setCornerRadius (h >= Components.getPx (getContext (), 48) ? Components.getPx (getContext (), 24) : height / 2);
        viewContainer.setBackground (backgroundDrawable);

        setMeasuredDimension(w, h);
    }

    public void seekTo (@FloatRange (from = 0, to = 1) float position) {
        progress = position;

        int newWidth = (int) (position * width);

        LayoutParams params = new LayoutParams (newWidth, -1);
        progressBar.setLayoutParams (params);
    }

    public boolean getRespondToTouch () {
        return respondToTouch;
    }

    public boolean getSnapToTouch () {
        return snapToTouch;
    }

    public int getBackgroundColor () {
        return backgroundColor;
    }

    public int getColor () {
        return color;
    }

    public float getProgress () {
        return progress;
    }

    public void setRespondToTouch (boolean respondToTouch) {
        this.respondToTouch = respondToTouch;
    }

    public void setSnapToTouch (boolean snapToTouch) {
        this.snapToTouch = snapToTouch;
    }

    @Override
    public void setBackgroundColor (int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setColor (int color) {
        this.color = color;
    }

    public void setProgress (float progress) {
        this.progress = progress;
    }

    private OnTouchListener onTouchListener = new OnTouchListener () {
        float startTouchX;
        float startX;
        float _;
        float rawTouchX;
        float touchX;

        float rawProgress;
        float progress;

        @Override
        public boolean onTouch (View v, MotionEvent event) {
            rawTouchX = event.getX ();
            touchX = rawTouchX >= 0 ? (rawTouchX <= width ? rawTouchX : width) : 0;

            if (event.getAction () == MotionEvent.ACTION_DOWN) {
                startTouchX = rawTouchX;
                startX = width * progress;

                _ = snapToTouch ? 0 : startX - startTouchX;
            }
            else if (event.getAction () == MotionEvent.ACTION_UP) {

            }
            else if (event.getAction () == MotionEvent.ACTION_MOVE) {

            }

            rawProgress = (rawTouchX + _) / width;

            progress = rawProgress >= 0 ? (rawProgress <= 1 ? rawProgress : 1) : 0;

            seekTo (progress);

            return true;
        }
    };
}
