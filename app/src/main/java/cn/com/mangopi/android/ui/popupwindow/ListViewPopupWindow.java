package cn.com.mangopi.android.ui.popupwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.List;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.DisplayUtils;

public abstract class ListViewPopupWindow<T> extends BasePopupWindow {

    List<T> dataList;
    Context context;


    public ListViewPopupWindow(Context context, List<T> dataList, T dataT, OnListViewListener<T> onListViewListener) {
        super(context);
        this.dataList = dataList;
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow_list, null, false);
        setContentView(view);
        ListView lvClassify = (ListView) view.findViewById(R.id.listview);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

        lvClassify.setAdapter(new QuickAdapter<T>(context, R.layout.listview_item_text_in_popup, dataList) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                fiddData(helper, item);
                if(dataT == item){
                    helper.setTextColorRes(R.id.tv_text, R.color.color_ffb900);
                }
            }
        });
        lvClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               if(onListViewListener != null){
                   onListViewListener.onItemClick((T) parent.getAdapter().getItem(position));
               }
               dismiss();
            }
        });

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvClassify.getLayoutParams();
        if(params.height > DisplayUtils.screenHeight(context) / 2) {
            params.height = DisplayUtils.screenHeight(context) / 2;
            lvClassify.setLayoutParams(params);
        }
    }

    public abstract void fiddData(BaseAdapterHelper helper, T item);

    public interface OnListViewListener<T> {
        void onItemClick(T data);
    }
}
