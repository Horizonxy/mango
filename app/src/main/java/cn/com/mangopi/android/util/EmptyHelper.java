package cn.com.mangopi.android.util;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.mangopi.android.R;

public class EmptyHelper {

    Context context;
    View vEmpty;
    TextView tvMessage;
    Button btnRefresh;
    ImageView ivEmpty;
    OnRefreshListener onRefreshListener;

    public EmptyHelper(Context context, View vEmpty, OnRefreshListener onRefreshListener) {
        this.context = context;
        this.vEmpty = vEmpty;
        this.onRefreshListener = onRefreshListener;
        tvMessage = (TextView) vEmpty.findViewById(R.id.tv_empty);
        btnRefresh = (Button) vEmpty.findViewById(R.id.btn_empty);
        ivEmpty = (ImageView) vEmpty.findViewById(R.id.iv_empty);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRefreshListener != null){
                    onRefreshListener.onRefresh();
                }
            }
        });
    }

    public void hideEmptyView(View contentView) {
        if(vEmpty.getVisibility() == View.GONE && contentView.getVisibility() == View.VISIBLE){
            return;
        }
        vEmpty.setVisibility(View.GONE);
        vEmpty.setEnabled(false);
        contentView.setVisibility(View.VISIBLE);
    }

    public void showEmptyView(View contentView) {
        if(!NetUtils.isNetworkConnected(context)){
            setMessage(R.string.noconnectionremind_for_draft);
        }
        if(vEmpty.getVisibility() == View.VISIBLE && contentView.getVisibility() == View.GONE){
            return;
        }
        vEmpty.setVisibility(View.VISIBLE);
        vEmpty.setEnabled(true);
        contentView.setVisibility(View.GONE);
    }

    public void setImageRes(int resId){
        ivEmpty.setImageResource(resId);
    }

    public void setMessage(int resId){
        tvMessage.setText(context.getResources().getString(resId));
    }

    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    public void showMessage(boolean show){
        tvMessage.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void showRefreshButton(boolean show){
        btnRefresh.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
