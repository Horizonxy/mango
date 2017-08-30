package cn.com.mangopi.android.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.widget.RedPointView;
import cn.com.mangopi.android.util.DisplayUtils;

/**
 * @author 蒋先明
 * @date 2017/8/30
 */

public class ProjectDetailProgressAdapter extends QuickAdapter<ProjectDetailProgressAdapter.ProgressBean> {

    int width;
    int reachColor;
    int unReachColor;

    public ProjectDetailProgressAdapter(Context context, int layoutResId, List<ProgressBean> data) {
        super(context, layoutResId, data);
        width = (DisplayUtils.screenWidth(context) - DisplayUtils.dip2px(context, 15) * 2) / 4;
        reachColor = R.color.color_ffb900;
        unReachColor = R.color.color_efeff4;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, ProgressBean item) {
        helper.setText(R.id.tv_top, item.getTop()).setText(R.id.tv_bottom, item.getBottom());

        View convertView = helper.getView();
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
        if(params == null){
            params  = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.width = width;
        convertView.setLayoutParams(params);

        View vLeft = helper.getView(R.id.v_left);
        View vRight = helper.getView(R.id.v_right);
        RedPointView point = helper.getView(R.id.v_point);

        if(helper.getPosition() == 0){
            vLeft.setVisibility(View.INVISIBLE);
            vRight.setBackgroundResource(item.isReach() ? reachColor : unReachColor);
            point.setColor(item.isReach() ? reachColor : unReachColor);
        } else {
            if (item.isReach()) {
                vLeft.setBackgroundResource(reachColor);
                vRight.setBackgroundResource(reachColor);
                point.setColor(reachColor);
            } else {
                ProgressBean last = data.get(helper.getPosition() - 1);
                vLeft.setBackgroundResource(last.isReach() ? reachColor : unReachColor);
                vRight.setBackgroundResource(unReachColor);
                point.setColor(unReachColor);
            }

            if(helper.getPosition() == (data.size() - 1)){
                vRight.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static class ProgressBean implements Serializable {
        private String top;
        private String bottom;
        private boolean reach;

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getBottom() {
            return bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = bottom;
        }

        public boolean isReach() {
            return reach;
        }

        public void setReach(boolean reach) {
            this.reach = reach;
        }
    }
}
