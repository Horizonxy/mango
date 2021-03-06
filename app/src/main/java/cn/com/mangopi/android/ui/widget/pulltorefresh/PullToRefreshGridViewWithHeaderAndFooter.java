package cn.com.mangopi.android.ui.widget.pulltorefresh;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import cn.com.mangopi.android.ui.widget.GridViewWithHeaderAndFooter;
import cn.com.mangopi.android.R;


public class PullToRefreshGridViewWithHeaderAndFooter extends PullToRefreshAdapterViewBase<GridViewWithHeaderAndFooter> {

    public PullToRefreshGridViewWithHeaderAndFooter(Context context) {
        super(context);
    }

    public PullToRefreshGridViewWithHeaderAndFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshGridViewWithHeaderAndFooter(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshGridViewWithHeaderAndFooter(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    @Override
    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected final GridViewWithHeaderAndFooter createRefreshableView(Context context, AttributeSet attrs) {
        final GridViewWithHeaderAndFooter gv;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            gv = new InternalGridViewSDK9(context, attrs);
        } else {
            gv = new InternalGridView(context, attrs);
        }

        // Use Generated ID (from res/values/ids.xml)
        gv.setId(R.id.gridview);
        return gv;
    }

    class InternalGridView extends GridViewWithHeaderAndFooter implements EmptyViewMethodAccessor {

        public InternalGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshGridViewWithHeaderAndFooter.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }
    }

    @TargetApi(9)
    final class InternalGridViewSDK9 extends PullToRefreshGridViewWithHeaderAndFooter.InternalGridView {

        public InternalGridViewSDK9(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

            final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                    scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

            // Does all of the hard work...
            OverscrollHelper.overScrollBy(PullToRefreshGridViewWithHeaderAndFooter.this, deltaX, scrollX, deltaY, scrollY, isTouchEvent);

            return returnValue;
        }
    }
}
