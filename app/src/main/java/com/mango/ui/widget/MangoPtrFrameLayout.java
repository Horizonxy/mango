package com.mango.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;

public class MangoPtrFrameLayout extends PtrFrameLayout {

    public MangoPtrFrameLayout(Context context) {
        super(context);
        initView();
    }

    public MangoPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MangoPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        MangoPtrUIHandler ptrHeader = new MangoPtrUIHandler(getContext());
        setHeaderView(ptrHeader);
        addPtrUIHandler(ptrHeader);

        ILoadMoreViewFactory loadMoreViewFactory = new MangoLoadMoreViewFooter();
        setFooterView(loadMoreViewFactory);
    }
}
