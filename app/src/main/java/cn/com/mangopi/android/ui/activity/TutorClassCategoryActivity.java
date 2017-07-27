package cn.com.mangopi.android.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.MangoUtils;
import cn.com.mangopi.android.util.SystemStatusManager;

public class TutorClassCategoryActivity extends BaseActivity {

    @Bind(R.id.ib_left)
    ImageButton ibLeft;
    @Bind(R.id.lv_category)
    ListView lvCagegory;
    List<CourseClassifyBean> datas;
    QuickAdapter adapter;
    @Bind(R.id.tv_right)
    TextView tvMyClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemStatusManager.setTranslucentStatusColor(this, getResources().getColor(R.color.color_ffb900));
        setContentView(R.layout.activity_teacher_class_category);
        datas = (List<CourseClassifyBean>) getIntent().getSerializableExtra(Constants.BUNDLE_CLASSIFY_LIST);
        if (datas == null) {
            datas = new ArrayList<CourseClassifyBean>();
        }
        initView();
    }

    private void initView() {
        lvCagegory.setDivider(null);
        lvCagegory.addFooterView(getLayoutInflater().inflate(R.layout.layout_bottom_concern, lvCagegory, false));

        lvCagegory.setAdapter(adapter = new QuickAdapter<CourseClassifyBean>(this, R.layout.listview_item_calss_category, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, CourseClassifyBean item) {
                helper.setImageUrl(R.id.iv_logo, item.getLogo_rsurl())
                        .setText(R.id.tv_name, item.getClassify_name());
                if (helper.getPosition() == datas.size() - 1) {
                    helper.setVisible(R.id.v_line, false);
                } else {
                    helper.setVisible(R.id.v_line, true);
                }
                List<CourseClassifyBean> list = item.getDetails();
                if (list == null) {
                    list = new ArrayList<CourseClassifyBean>();
                }
                ((GridView) helper.getView(R.id.gv_item_category)).setAdapter(new QuickAdapter<CourseClassifyBean>(TutorClassCategoryActivity.this, R.layout.gridview_item_class_category_item, list) {
                    @Override
                    protected void convert(BaseAdapterHelper helper, CourseClassifyBean item) {
                        helper.setText(R.id.tv_name, item.getClassify_name());
                    }
                });
                ((GridView) helper.getView(R.id.gv_item_category)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        });

        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
        if (!indentityList.contains(Constants.UserIndentity.TUTOR)) {
            tvMyClass.setVisibility(View.GONE);
        } else {
            tvMyClass.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ib_left)
    void onBack(View v) {
        finish();
    }

    @OnClick(R.id.tv_right)
    void onMyCourse(View v) {
        ActivityBuilder.startMyClassesActivity(this);
    }

    @Override
    public int statusColorResId() {
        return R.color.color_ffb900;
    }
}
