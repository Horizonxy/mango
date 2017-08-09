package cn.com.mangopi.android.ui.widget;

import android.view.View;
import android.widget.TextView;

import com.chanven.lib.cptr.loadmore.ILoadMoreViewFactory;
import cn.com.mangopi.android.R;

public class MangoLoadMoreViewFooter implements ILoadMoreViewFactory {

    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    class LoadMoreHelper implements ILoadMoreView {

        protected View footerView;
        protected TextView tvLoadMore;
        protected GifMovieView gifProgress;

        protected View.OnClickListener onClickRefreshListener;


        @Override
        public void init(FootViewAdder footViewHolder, View.OnClickListener onClickLoadMoreListener) {
            footerView = footViewHolder.addFootView(R.layout.layout_ptr_loadmore_footer);
            tvLoadMore = (TextView) footerView.findViewById(R.id.tv_loadmore);
            gifProgress = (GifMovieView) footerView.findViewById(R.id.gif_progress);
            gifProgress.setMovieResource(R.raw.loadinggif);
            this.onClickRefreshListener = onClickLoadMoreListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            tvLoadMore.setVisibility(View.VISIBLE);
            tvLoadMore.setText("点击加载更多");
            gifProgress.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void showNomore() {
            tvLoadMore.setVisibility(View.VISIBLE);
            tvLoadMore.setText("已经到最后");
            gifProgress.setVisibility(View.GONE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void showLoading() {
            tvLoadMore.setVisibility(View.VISIBLE);
            tvLoadMore.setText("正在加载中...");
            gifProgress.setVisibility(View.VISIBLE);
            footerView.setOnClickListener(null);
        }

        @Override
        public void showFail(Exception e) {
            tvLoadMore.setVisibility(View.VISIBLE);
            tvLoadMore.setText("加载失败，点击重新");
            gifProgress.setVisibility(View.GONE);
            footerView.setOnClickListener(onClickRefreshListener);
        }

        @Override
        public void setFooterVisibility(boolean isVisible) {
            footerView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }
}
