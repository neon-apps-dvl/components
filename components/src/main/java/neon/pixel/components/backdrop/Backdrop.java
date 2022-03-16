package neon.pixel.components.backdrop;

import static com.pixel.components.Components.getPx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.pixel.components.R;

public class Backdrop extends CoordinatorLayout {
    private static final String TAG = "Backdrop";

    @LayoutRes
    private static final int LAYOUT = R.layout.layout_backdrop;

    @IdRes
    private static final int BACK = R.id.back;

    @IdRes
    private static final int FRONT = R.id.front;

    private static final int NO_OFFSET_CORNERS_DP = 12;
    private static final int CORNERS_DP = 48;

    public static final int MATCH_SYSTEM = -1;

    private int mBackCorners;
    private int mNoOffsetCornersPx;
    private int mCornersPx;

    protected CoordinatorLayout mBackView;
    protected CoordinatorLayout mFrontView;

    private int mOffset = 0;

    private OnOffsetChangedListener mListener;

    public Backdrop (@NonNull Context context) {
        super (context);

        LayoutInflater layoutInflater = LayoutInflater.from (context);
        layoutInflater.inflate (LAYOUT, this, true);

        setBackground (null);

        mBackView = findViewById (BACK);
        mFrontView = findViewById (FRONT);

        mBackCorners = (int) getPx (context, NO_OFFSET_CORNERS_DP);
        mNoOffsetCornersPx = (int) getPx (context, NO_OFFSET_CORNERS_DP);
        mCornersPx = (int) getPx (context, CORNERS_DP);
    }

    public Backdrop (@NonNull Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);

        LayoutInflater layoutInflater = LayoutInflater.from (context);
        layoutInflater.inflate (LAYOUT, this, true);

        setBackground (null);

        mBackView = findViewById (BACK);
        mFrontView = findViewById (FRONT);

        mCornersPx = (int) getPx (context, CORNERS_DP);

        TypedArray a = context.getTheme ()
                .obtainStyledAttributes (attrs, R.styleable.Backdrop, 0, 0);

        int corners = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? MATCH_SYSTEM : (int) getPx (context, NO_OFFSET_CORNERS_DP);

        mBackCorners = a.getDimensionPixelSize (R.styleable.Backdrop_backCorners, corners);

        mNoOffsetCornersPx = (int) a.getDimensionPixelSize (R.styleable.Backdrop_frontCorners, (int) getPx (context, corners));
    }

    @Override
    protected void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged (w, h, oldw, oldh);

        mBackView.setOutlineProvider (new ViewOutlineProvider () {
            @Override
            public void getOutline (View view, Outline outline) {
                if (mBackCorners == MATCH_SYSTEM && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

                    int c0 = getRootWindowInsets ().getRoundedCorner (RoundedCorner.POSITION_TOP_LEFT).getRadius ();
                    int c1 = getRootWindowInsets ().getRoundedCorner (RoundedCorner.POSITION_TOP_RIGHT).getRadius ();

                    mBackCorners = c0 >= c1 ? c0 : c1;
                }

                outline.setRoundRect (view.getLeft (),
                        view.getTop (),
                        view.getRight (),
                        view.getBottom () + mBackCorners,
                        mBackCorners);

                mBackView.setClipToOutline (true);
            }
        });

        mFrontView.setOutlineProvider (new ViewOutlineProvider () {
            @Override
            public void getOutline (View view, Outline outline) {
                if (mNoOffsetCornersPx == MATCH_SYSTEM && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    int c0 = getRootWindowInsets ().getRoundedCorner (RoundedCorner.POSITION_TOP_LEFT).getRadius ();
                    int c1 = getRootWindowInsets ().getRoundedCorner (RoundedCorner.POSITION_TOP_RIGHT).getRadius ();

                    mNoOffsetCornersPx = c0 >= c1 ? c0 : c1;
                }

                float dCorners = mCornersPx - mNoOffsetCornersPx;
                float rawOffset = (float) mOffset / (float) dCorners;
                float o = rawOffset <= 1 ? rawOffset : 1;
                int c = (int) (mNoOffsetCornersPx + o * dCorners);

                outline.setRoundRect (view.getLeft (),
                        view.getTop (),
                        view.getRight (),
                        view.getBottom () + c,
                        c);

                mFrontView.setClipToOutline (true);
            }
        });

        if (mListener != null) mListener.onOffsetChanged (mOffset);
    }

    public int getCorners () {
        return mNoOffsetCornersPx;
    }

    public void setCorners (int corners) {
        mNoOffsetCornersPx = corners;
    }

    public void setBackView (View v) {
        mBackView.removeAllViews ();
        mBackView.addView (v);
    }

    public void setFrontView (View v) {
        mFrontView.removeAllViews ();
        mFrontView.addView (v);
    }

    public int getOffset () {
        return mOffset;
    }

    public void setOffset (int offset) {
        mOffset = offset;

        mFrontView.setY (mOffset);

        mFrontView.setOutlineProvider (new ViewOutlineProvider () {
            @Override
            public void getOutline (View view, Outline outline) {
                float dCorners = mCornersPx - mNoOffsetCornersPx;
                float rawOffset = (float) mOffset / (float) dCorners;
                float o = rawOffset <= 1 ? rawOffset : 1;
                int c = (int) (mNoOffsetCornersPx + o * dCorners);

                outline.setRoundRect (view.getLeft (),
                        view.getTop (),
                        view.getRight (),
                        view.getBottom () + c,
                        c);

                mFrontView.setClipToOutline (true);

            }
        });

        if (mListener != null) mListener.onOffsetChanged (mOffset);
    }

    public void setOnOffsetChangedListener (OnOffsetChangedListener l) {
        mListener = l;
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged (int newOffset);
    }
}
