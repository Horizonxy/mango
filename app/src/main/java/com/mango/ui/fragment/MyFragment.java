package com.mango.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mango.Application;
import com.mango.R;
import com.mango.di.component.DaggerMyFragmentComponent;
import com.mango.di.module.MyFragmentModule;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.activity.MyAccountActivity;
import com.mango.ui.activity.MyOrderListActivity;
import com.mango.ui.activity.SettingActivity;
import com.mango.ui.activity.UpdateRoleActivity;
import com.mango.ui.viewlistener.MyFragmentListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class MyFragment extends BaseFragment implements MyFragmentListener {

    @Bind(R.id.tv_my_name)
    TextView tvMyName;
    @Bind(R.id.tv_update_info)
    TextView tvUpdateInfo;
    @Bind(R.id.layout_role)
    View vRole;
    @Bind(R.id.layout_order_list)
    View vOrderList;
    @Bind(R.id.layout_works)
    View vWorks;
    @Bind(R.id.layout_classes)
    View vClasses;
    @Bind(R.id.layout_account)
    View vAccount;
    @Bind(R.id.layout_setting)
    View vSetting;

    TextView tvRole;
    TextView tvOrderList;
    TextView tvWorks;
    TextView tvClasses;
    TextView tvAccount;
    TextView tvSetting;
    @Inject
    MemberPresenter memberPresenter;

    public MyFragment() {
    }

    public static MyFragment newInstance(String param1, String param2) {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMyFragmentComponent.builder().myFragmentModule(new MyFragmentModule(this)).build().inject(this);
    }

    @Override
    void initView() {
        tvRole = (TextView) vRole.findViewById(R.id.tv_left);
        tvOrderList = (TextView) vOrderList.findViewById(R.id.tv_left);
        tvWorks = (TextView) vWorks.findViewById(R.id.tv_left);
        tvClasses = (TextView) vClasses.findViewById(R.id.tv_left);
        tvAccount = (TextView) vAccount.findViewById(R.id.tv_left);
        tvSetting = (TextView) vSetting.findViewById(R.id.tv_left);
        tvRole.setText(getString(R.string.my_role));
        tvOrderList.setText(getString(R.string.my_order_list));
        tvWorks.setText(getString(R.string.my_works));
        tvClasses.setText(getString(R.string.my_classes));
        tvAccount.setText(getString(R.string.my_account));
        tvSetting.setText(getString(R.string.setting));

        if(Application.application.getMember() != null) {
            memberPresenter.getMember();
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.layout_role)
    void updateRoleClick(View v){
        startActivity(new Intent(getActivity(), UpdateRoleActivity.class));
    }

    @OnClick(R.id.layout_order_list)
    void orderListClick(View v){
        startActivity(new Intent(getActivity(), MyOrderListActivity.class));
    }

    @OnClick(R.id.layout_works)
    void worksClick(View v){

    }

    @OnClick(R.id.layout_classes)
    void classesClick(View v){
        ActivityBuilder.startMyClassesActivity(getActivity());
    }

    @OnClick(R.id.layout_account)
    void accountClick(View v){
        startActivity(new Intent(getActivity(), MyAccountActivity.class));
    }

    @OnClick(R.id.layout_setting)
    void settingClick(View v){
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(getContext(), message);
    }

    @Override
    public Context currentContext() {
        return getContext();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public long getMemberId() {
        return Application.application.getMember().getId().longValue();
    }
}
