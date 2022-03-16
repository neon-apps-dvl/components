package neon.pixel.components.seekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import neon.pixel.components.R;

public class SeekBar extends CoordinatorLayout {
    private View background;
    private View progressBar;

    private int width;

    private boolean showPointer;
    private boolean respondToTouch;
    private boolean snapToTouch;
    private int defaultBackgroundColor;
    private int defaultColor;
    private boolean queueInteractionEvents = false;

    private final InteractionListener defaultInteractionListener = new InteractionListener () {
        @Override
        public void onProgressChanged (float newProgress, int flags) {

        }
    };
    private InteractionListener interactionListener = defaultInteractionListener;

    private int interactionListenerProgressChangedFlags = 0;

    private int backgroundColor;
    private int color;
    private float progress;

    private Drawable pointerDrawable;
    private View pointer;

    public SeekBar (Context context) {
        super (context);
    }

    public SeekBar (Context context, @Nullable AttributeSet rawAttrs) {
        super (context, rawAttrs);
        LayoutInflater inflater = LayoutInflater.from (context);
        inflater.inflate (R.layout.layout_seek_bar, this, true);

        setBackground (null);

        background = findViewById (R.id.background);
        progressBar = findViewById (R.id.progress_bar);
        pointer = findViewById (R.id.pointer);

        respondToTouch = rawAttrs.getAttributeBooleanValue (R.styleable.SeekBarAttrs_respondToTouch, false);
        snapToTouch = rawAttrs.getAttributeBooleanValue (R.styleable.SeekBarAttrs_snapToTouch, false);

        TypedArray attrs = context.getTheme ().obtainStyledAttributes (
                rawAttrs,
                R.styleable.SeekBarAttrs,
                0, 0);

        respondToTouch = attrs.getBoolean (R.styleable.SeekBarAttrs_respondToTouch, true);
        snapToTouch = attrs.getBoolean (R.styleable.SeekBarAttrs_snapToTouch, false);

        TypedValue tempHolder = new TypedValue ();

        context.getTheme ().resolveAttribute (com.google.android.material.R.attr.colorPrimaryContainer, tempHolder, true);
        defaultBackgroundColor = tempHolder.data;

        context.getTheme ().resolveAttribute (com.google.android.material.R.attr.colorPrimary, tempHolder, true);
        defaultColor = tempHolder.data;

        backgroundColor = attrs.getColor (R.styleable.SeekBarAttrs_backgroundColor, defaultBackgroundColor);
        color = attrs.getColor (R.styleable.SeekBarAttrs_color, defaultColor);

        pointerDrawable = context.getResources ().getDrawable (R.drawable.seek_bar_pointer_background, context.getTheme ()).mutate ();
        pointerDrawable.setTint (color);

        pointer.setBackground (pointerDrawable);

        background.setBackgroundColor (backgroundColor);
        progressBar.setBackgroundColor (color);

        if (respondToTouch) setOnTouchListener (onTouchListener);
    }

    public SeekBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);

        int minw = getPaddingLeft () + getPaddingRight () + getSuggestedMinimumWidth ();
        int w = resolveSizeAndState (minw, widthMeasureSpec, 1);

        int minh = (int) (48 * getResources ().getDisplayMetrics ().density);
        int h = resolveSizeAndState (minh, heightMeasureSpec, 0);

        width = w;

        setMeasuredDimension (w, h);
    }

    public void seekTo (@FloatRange (from = 0, to = 1) float position) {
        progress = position;

        int newWidth = (int) (position * width);
        int maxPointerPosition = (int) (width - 8 * getContext ().getResources ().getDisplayMetrics ().density);
        int pointerPosition = newWidth <= maxPointerPosition ? newWidth : maxPointerPosition;

        LayoutParams params = new LayoutParams (newWidth, -1);
        progressBar.setLayoutParams (params);
        pointer.setX (pointerPosition);
    }

    public boolean getShowPointer () {
        return showPointer;
    }

    public boolean getRespondToTouch () {
        return respondToTouch;
    }

    public boolean getSnapToTouch () {
        return snapToTouch;
    }

    public int getBackgroundColor () {
        return defaultBackgroundColor;
    }

    public int getColor () {
        return defaultColor;
    }

    public float getProgress () {
        return progress;
    }

    public void setShowPointer (boolean showPointer) {
        this.showPointer = showPointer;
    }

    public void setRespondToTouch (boolean respondToTouch) {
        this.respondToTouch = respondToTouch;
    }

    public void setSnapToTouch (boolean snapToTouch) {
        this.snapToTouch = snapToTouch;
    }

    public void setInteractionListener (InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    public void removeInteractionListener () {
        this.interactionListener = defaultInteractionListener;
    }

    @Override
    public void setBackgroundColor (int backgroundColor) {
        this.defaultBackgroundColor = backgroundColor;
    }

    public void setColor (int color) {
        this.defaultColor = color;
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
                interactionListenerProgressChangedFlags = InteractionListener.Flags.INTERACTION_START;

                startTouchX = rawTouchX;
                startX = width * progress;

                _ = snapToTouch ? 0 : startX - startTouchX;
            } else if (event.getAction () == MotionEvent.ACTION_UP) {
                interactionListenerProgressChangedFlags = InteractionListener.Flags.INTERACTION_EXIT;
            } else if (event.getAction () == MotionEvent.ACTION_MOVE) {

            }

            rawProgress = (rawTouchX + _) / width;

            progress = rawProgress >= 0 ? (rawProgress <= 1 ? rawProgress : 1) : 0;

            seekTo (progress);

            interactionListener.onProgressChanged (progress, interactionListenerProgressChangedFlags);

            return true;
        }
    };

    public interface InteractionListener {
        class Flags {
            public static int INTERACTION_START = 1;
            public static int INTERACTION_EXIT = 2;
        }

        void onProgressChanged (@FloatRange (from = 0, to = 1) float newProgress, int flags);
    }
}
