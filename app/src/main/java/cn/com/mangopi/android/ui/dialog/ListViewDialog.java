package cn.com.mangopi.android.ui.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;

public abstract class ListViewDialog<T> implements AdapterView.OnItemClickListener{

    List<T> dataList;
    Context context;
    TextView tvTitle;
    TextView tvOk;
    TextView tvCancel;
    ListView listView;
    String title;
    String ok = "确定";
    String cancel = "取消";
    OnListViewListener onListViewListener;
    T selectData;
    QuickAdapter<T> dataAdapteer;

    public ListViewDialog(String title,  List<T> dataList) {
        this.title = title;
        this.dataList = dataList;
    }

    public void show(Context context, T data) {
        this.context = context;
        if(data != null){
            this.selectData = data;
        } else if(data == null &&  dataList != null && dataList.size() > 0){
            this.selectData = dataList.get(0);
        }
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_listview_choose, null ,false);
        dialog.setContentView(view);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(dataAdapteer = new QuickAdapter<T>(context, R.layout.listview_item_text_in_popup, dataList) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                fiddData(helper, item);
                if(selectData == item){
                    helper.setTextColorRes(R.id.tv_text, R.color.color_ffb900);
                    ImageView ivRight = helper.getView(R.id.iv_right);
                    ivRight.setSelected(true);
                } else {
                    helper.setTextColorRes(R.id.tv_text, R.color.color_666666);
                    ImageView ivRight = helper.getView(R.id.iv_right);
                    ivRight.setSelected(false);
                }
            }
        });
        listView.setOnItemClickListener(this);
        tvOk = (TextView) view.findViewById(R.id.tv_ok);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvTitle.setText(title);

        tvOk.setText(ok);
        tvCancel.setText(cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onListViewListener != null){
                    onListViewListener.onItemClicked(ListViewDialog.this.selectData);
                }
                dialog.dismiss();
            }
        });
    }

    public abstract void fiddData(BaseAdapterHelper helper, T item);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectData = (T) parent.getAdapter().getItem(position);
        if(dataAdapteer != null){
            dataAdapteer.notifyDataSetChanged();
        }
    }

    public interface OnListViewListener<T>{
        void onItemClicked(T data);
    }

    public void setOnListViewListener(OnListViewListener onListViewListener) {
        this.onListViewListener = onListViewListener;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }
}
