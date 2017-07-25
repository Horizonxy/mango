package com.mango.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.di.Type;
import com.mango.di.component.DaggerTeacherFragmentComponent;
import com.mango.di.module.TeacherFragmentModule;
import com.mango.model.bean.CourseBean;
import com.mango.model.bean.CourseClassifyBean;
import com.mango.presenter.TeacherPresenter;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.TeacherListener;
import com.mango.ui.widget.GridView;
import com.mango.ui.widget.MangoPtrFrameLayout;
import com.mango.util.ActivityBuilder;
import com.mango.util.MangoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class TecaherFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener, TeacherListener {

    MangoPtrFrameLayout refreshLayout;
    ListView listView;
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

    public TecaherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerTeacherFragmentComponent.builder().teacherFragmentModule(new TeacherFragmentModule(this, listDatas, gridDatas)).build().inject(this);
    }

    @Override
    void findView(View root) {
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        listView = (ListView) root.findViewById(R.id.listview);
        root.findViewById(R.id.tv_left).setOnClickListener(this);

        tvMyClass = (TextView) root.findViewById(R.id.tv_right);
        tvMyClass.setOnClickListener(this);
    }

    @Override
    void initView() {
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_header_teacher, null, false);
        courseBanner = (ConvenientBanner) headerView.findViewById(R.id.course_banner);
        listView.addHeaderView(headerView);
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
                        Toast.makeText(getActivity(), "banner: " + position, Toast.LENGTH_SHORT).show();
                    }
                });

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                hasNext = true;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                loadData();
            }
        });

        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh(true);
            }
        }, 400);

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
            Application.application.getImageLoader().displayImage(data.getAvatar_rsurl(), imageView, Application.application.getDefaultOptions());
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
        } else {
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            refreshLayout.setNoMoreData();
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
            if(position == 7){
                ArrayList<CourseClassifyBean> classifyList = new ArrayList<>();
                classifyList.addAll(gridDatas.subList(0, gridDatas.size() - 1));
                ActivityBuilder.startTutorClassCategoryActivity(getActivity(), classifyList);
            } else {
                CourseClassifyBean classify = (CourseClassifyBean) item;
                ActivityBuilder.startCalssListActivity(getActivity(), classify);
            }
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
        }
    }

    @Override
    public void onFailure(String message) {
        if(TextUtils.isEmpty(message)) {
            if (pageNo == 1) {
                refreshLayout.refreshComplete();
            }
            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
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
                refreshLayout.refreshComplete();
            }

            refreshLayout.setLoadMoreEnable(true);
            refreshLayout.loadMoreComplete(true);
            if (hasNext = (listDatas.size() >= Constants.PAGE_SIZE)) {
                pageNo++;
            } else {
                refreshLayout.setNoMoreData();
            }

            listDatas.addAll(courseList);
            listAdapter.notifyDataSetChanged();
        } else if(hotTypes == 1){
            bannerDatas.clear();
            bannerDatas.addAll(courseList);
            courseBanner.notifyDataSetChanged();
        }
    }

    @Override
    public void onClassifySuccess(List<CourseClassifyBean> classifyList) {
        gridDatas.clear();
        if(classifyList != null && classifyList.size() > 7){
            gridDatas.addAll(classifyList.subList(0, 7));
        } else {
            gridDatas.addAll(classifyList);
        }
        CourseClassifyBean classifyAll = new CourseClassifyBean();
        classifyAll.setClassify_name("全部");
        gridDatas.add(classifyAll);
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
