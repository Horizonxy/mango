package com.mango.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mango.R;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.widget.GridView;
import com.mango.ui.widget.TitleBar;
import com.mango.util.ActivityBuilder;
import com.mango.util.SystemStatusManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class TutorClassCategoryActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener {

    @Bind(R.id.lv_category)
    ListView lvCagegory;
    List datas = new ArrayList();
    QuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusColor(this, getResources().getColor(R.color.color_ffb900));
        setContentView(R.layout.activity_teacher_class_category);

        initView();
    }

    private void initView() {
        titleBar.setBarBackGroundColor(R.color.color_ffb900);
        titleBar.setTitle(R.string.teacher);
        titleBar.setRightText(R.string.my_classes);

        lvCagegory.addFooterView(getLayoutInflater().inflate(R.layout.layout_bottom_concern, lvCagegory, false));
        datas.add("");
        datas.add("");
        lvCagegory.setAdapter(adapter = new QuickAdapter<String>(this, R.layout.listview_item_calss_category, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                List itemDatas = new ArrayList();
                for (int i = 0; i < 7; i++){
                    itemDatas.add("");
                }
                ((GridView)helper.getView(R.id.gv_item_category)).setAdapter(new QuickAdapter<String>(TutorClassCategoryActivity.this, R.layout.gridview_item_class_category_item, itemDatas) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, String item) {

                    }
                });
                ((GridView)helper.getView(R.id.gv_item_category)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        });
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                ActivityBuilder.startMyClassesActivity(this);
                break;
        }
    }
}
