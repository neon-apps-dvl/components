package neon.pixel.componentstest;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;
import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED;

import static neon.pixel.components.Components.getPx;
import static neon.pixel.components.Components.getPxF;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MediaControlsView extends CoordinatorLayout {
    private static final String TAG = "MediaControlsView";

    @LayoutRes
    private static final int LAYOUT = R.layout.layout_media_controls;

    private static final int CORNERS_DP = 24;

    private CoordinatorLayout mContainer;
    private CoordinatorLayout mView;
    private CoordinatorLayout mContent;
    private BottomSheetBehavior mBehavior;

    public MediaControlsView (@NonNull Context context) {
        super (context, null);
    }

    public MediaControlsView (@NonNull Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);

        LayoutInflater layoutInflater = LayoutInflater.from (context);
        layoutInflater.inflate (LAYOUT, this, true);

        mContainer = findViewById (R.id.container);
        mView = findViewById (R.id.view);
        mContent = findViewById (R.id.content);
        mBehavior = BottomSheetBehavior.from (mView);
        mBehavior.addBottomSheetCallback (mCallback);
    }

    @Override
    protected void onLayout (boolean changed, int l, int t, int r, int b) {
        super.onLayout (changed, l, t, r, b);

        if (! changed) return;
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged (w, h, oldw, oldh);

        mContainer.setOutlineProvider (new ViewOutlineProvider () {
            @Override
            public void getOutline (View view, Outline outline) {
                outline.setRoundRect (
                        view.getLeft (),
                        view.getTop (),
                        view.getRight (),
                        view.getBottom (),
                        getPxF (getContext (), CORNERS_DP)
                );
            }
        });

        Log.e (TAG, String.format ("onSizeChanged: w=%d h=%d oldw=%d oldh=%d", w, h, oldw, oldh));
    }

    private BottomSheetBehavior.BottomSheetCallback mCallback = new BottomSheetBehavior.BottomSheetCallback () {
        @Override
        public void onStateChanged (@NonNull View bottomSheet, int newState) {
            if (newState == STATE_COLLAPSED) Log.e (TAG, "collapsed");
            else if (newState == STATE_EXPANDED) Log.e (TAG, "expanded");
        }

        @Override
        public void onSlide (@NonNull View bottomSheet, float slideOffset) {

        }
    };
}
