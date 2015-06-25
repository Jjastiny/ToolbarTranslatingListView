package isy.toolbartranslatinglistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;


/**
 * Created by justinyang on 6/24/15.
 */
public class CustomListViewForActionBar extends ListView {
    private CustomScrollListener onScrollListener = null;
    private View tempView;
    private int topViewPosition;
    private int bottomViewPosition;

    public CustomListViewForActionBar(Context context) {
        super(context);
    }

    public CustomListViewForActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListViewForActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollListener(CustomScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (tempView == null) {
            if (getChildCount() > 0) {
                tempView = getChildAt(getChildCount() / 2);
                topViewPosition = tempView.getTop();
                bottomViewPosition = getPositionForView(tempView);
            }
        } else {
            boolean childIsSafeToTrack = tempView.getParent() == this && getPositionForView(tempView) == bottomViewPosition;
            if (childIsSafeToTrack) {
                int top = tempView.getTop();
                if (onScrollListener != null) {
                    int deltaY = top - topViewPosition;
                    onScrollListener.onScrollChanged(deltaY);
                }
                topViewPosition = top;
            } else {
                tempView = null;
            }
        }
    }
}

interface CustomScrollListener {
    void onScrollChanged(int deltaY);
}