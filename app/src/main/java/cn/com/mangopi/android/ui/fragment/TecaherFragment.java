package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.Type;
import cn.com.mangopi.android.di.component.DaggerTeacherFragmentComponent;
import cn.com.mangopi.android.di.module.TeacherFragmentModule;
import cn.com.mangopi.android.model.bean.CourseBean;
import cn.com.mangopi.android.model.bean.CourseClassifyBean;
import cn.com.mangopi.android.presenter.TeacherPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.TeacherListener;
import cn.com.mangopi.android.ui.widget.GridView;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshBase;
import cn.com.mangopi.android.ui.widget.pulltorefresh.PullToRefreshListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.MangoUtils;

public class TecaherFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, TeacherListener {

    PullToRefreshListView listView;
    int pageNo = 1;
    List<CourseBean> listDatas = new ArrayList<CourseBean>();
    @Inject
    @Type("list")
    QuickAdapter listAdapter;
    List<CourseClassifyBean> gridDatas = new ArrayList<CourseClassifyBean>();
    GridView gvCategory;
    @Inject
    @Type("grid")
    QuickAdapter gridAdapter;
    @Inject
    TeacherPresenter presenter;
    boolean hasNext = true;
    TextView tvMyClass;
    ConvenientBanner courseBanner;
    List<CourseBean> bannerDatas;
    EditText etSearch;

    public TecaherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTeacherFragmentComponent.builder().teacherFragmentModule(new TeacherFragmentModule(this, listDatas, gridDatas)).build().inject(this);
    }

    @Override
    void findView(View root) {
        listView = (PullToRefreshListView) root.findViewById(R.id.listview);
        root.findViewById(R.id.tv_left).setOnClickListener(this);

        tvMyClass = (TextView) root.findViewById(R.id.tv_right);
        tvMyClass.setOnClickListener(this);

        root.findViewById(R.id.iv_tab_search).setOnClickListener(this);
        etSearch = (EditText) root.findViewById(R.id.et_search);
    }

    @Override
    void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_header_teacher, null, false);
        courseBanner = (ConvenientBanner) headerView.findViewById(R.id.course_banner);
        listView.getRefreshableView().addHeaderView(headerView);
        gvCategory = (GridView) headerView.findViewById(R.id.gv_category);
        gvCategory.setAdapter(gridAdapter);
        gvCategory.setOnItemClickListener(this);

        bannerDatas = new ArrayList<>();
        courseBanner.setPages(new CBViewHolderCreator<BannerHolderView>() {

            @Override
            public BannerHolderView createHolder() {
                return new BannerHolderView();
            }
        }, bannerDatas).setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setPageIndicator(new int[]{R.drawable.shape_indicator_normal, R.drawable.shape_indicator_selected})
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        CourseBean course = bannerDatas.get(position);
                        ActivityBuilder.startCourseDetailActivity(getActivity(), course.getId());
                    }
                });

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNo = 1;
                hasNext = true;
                initData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData();
            }
        });
        listView.setRefreshing(true);

        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
        if(!indentityList.contains(Constants.UserIndentity.TUTOR)){
            tvMyClass.setVisibility(View.GONE);
        } else {
            tvMyClass.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(courseBanner != null) {
            courseBanner.startTurning(3 * 1000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(courseBanner != null) {
            courseBanner.stopTurning();
        }
    }

    class BannerHolderView implements Holder<CourseBean> {

        private ImageView imageView;
        private TextView tvTitle;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_item_course_banner, courseBanner, false);
            imageView = (ImageView) view.findViewById(R.id.iv_logo);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, CourseBean data) {
            Application.application.getImageLoader().displayImage(MangoUtils.getCalculateScreenWidthSizeUrl(data.getTutor_logo_rsurl()), imageView, Application.application.getDefaultOptions());
            tvTitle.setText(data.getCourse_title());
        }
    }

    private void initData(){
        presenter.getCourseList(1);
        presenter.getCourseClassify();
        presenter.getCourseList(2);
    }

    private void loadData() {
        if(hasNext) {
            presenter.getCourseList(2);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_tecaher;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object item = parent.getAdapter().getItem(position);
        if(item instanceof CourseClassifyBean){
//            if(position == (gridDatas.size() - 1)){
//                ArrayList<CourseClassifyBean> classifyList = new ArrayList<>();
//                classifyList.addAll(gridDatas.subList(0, gridDatas.size() - 1));
//                ActivityBuilder.startTutorClassCategoryActivity(getActivity(), classifyList);
//            } else {
                CourseClassifyBean classify = (CourseClassifyBean) item;
                ActivityBuilder.startCourseListActivity(getActivity(), classify);
//            }
        } else if(item instanceof CourseBean){
            CourseBean course = (CourseBean) item;
            ActivityBuilder.startCourseDetailActivity(getActivity(), course.getId());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_right:
                ActivityBuilder.startMyClassesActivity(getActivity());
                break;
            case R.id.iv_tab_search:
                if(!TextUtils.isEmpty(etSearch.getText())) {
                    ActivityBuilder.startSearchActivity(getActivity(), etSearch.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            listView.onRefreshComplete();
        }
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onCourseListSuccess(int hotTypes, List<CourseBean> courseList) {
        if(hotTypes == 2) {
            if (pageNo == 1) {
                listDatas.clear();
            }

            listView.onRefreshComplete();
            if (hasNext = (courseList.size() >= Constants.PAGE_SIZE)) {
                pageNo++;
                listView.setMode(PullToRefreshBase.Mode.BOTH);
            } else {
                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }

            if(courseList != null) {
                listDatas.addAll(courseList);
            }
            listAdapter.notifyDataSetChanged();
        } else if(hotTypes == 1){
            bannerDatas.clear();
            if(courseList != null) {
                bannerDatas.addAll(courseList);
            }
            courseBanner.notifyDataSetChanged();
        }
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {
        gridDatas.clear();
//        if(classifyList != null && classifyList.size() > 7){
//            gridDatas.addAll(classifyList.subList(0, 7));
//        } else {
        if(classifyList != null) {
            gridDatas.addAll(classifyList);
        }
//        }
//        CourseClassifyBean classifyAll = new CourseClassifyBean();
//        classifyAll.setClassify_name("全部");
//        gridDatas.add(classifyAll);
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public Map<String, Object> getQueryMap(int hotTypes) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hot_types", hotTypes);
        map.put("lst_sessid", Application.application.getSessId());
        map.put("state", 50);
        if(hotTypes == 1){
            map.put("page_no", 1);
            map.put("page_size", 10);
        } else if(hotTypes == 2){
            map.put("page_no", pageNo);
            map.put("page_size", Constants.PAGE_SIZE);
        }
        return map;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
