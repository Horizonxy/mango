package com.mango.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Application;
import com.mango.R;
import com.mango.di.component.DaggerMyFragmentComponent;
import com.mango.di.module.MyFragmentModule;
import com.mango.model.bean.MemberBean;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.activity.MyAccountActivity;
import com.mango.ui.activity.MyOrderListActivity;
import com.mango.ui.activity.ProfileInfoActivity;
import com.mango.ui.activity.SettingActivity;
import com.mango.ui.activity.UpdateRoleActivity;
import com.mango.ui.viewlistener.MyFragmentListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import javax.inject.Inject;

public class MyFragment extends BaseFragment implements MyFragmentListener,View.OnClickListener {

    TextView tvNickName;
    TextView tvUpdateInfo;
    View vRole;
    View vOrderList;
    View vWorks;
    View vClasses;
    View vAccount;
    View vSetting;

    TextView tvRole;
    TextView tvOrderList;
    TextView tvWorks;
    TextView tvClasses;
    TextView tvAccount;
    TextView tvSetting;
    @Inject
    MemberPresenter memberPresenter;
    @Inject
    MemberBean member;
    TextView tvCollectionCount;
    TextView tvMsgCount;
    TextView tvTrendCount;
    ImageView ivAvatar;

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
    void findView(View root) {
        tvNickName = (TextView) root.findViewById(R.id.tv_nick_name);
        tvUpdateInfo = (TextView) root.findViewById(R.id.tv_update_info);
        vRole = root.findViewById(R.id.layout_role);
        vOrderList = root.findViewById(R.id.layout_order_list);
        vWorks = root.findViewById(R.id.layout_works);
        vClasses = root.findViewById(R.id.layout_classes);
        vAccount = root.findViewById(R.id.layout_account);
        vSetting = root.findViewById(R.id.layout_setting);
        tvCollectionCount = (TextView) root.findViewById(R.id.tv_collection_count);
        tvMsgCount = (TextView) root.findViewById(R.id.tv_msg_count);
        tvTrendCount = (TextView) root.findViewById(R.id.tv_trend_count);
        ivAvatar = (ImageView) root.findViewById(R.id.iv_avatar);
        vRole.setOnClickListener(this);
        vOrderList.setOnClickListener(this);
        vWorks.setOnClickListener(this);
        vClasses.setOnClickListener(this);
        vAccount.setOnClickListener(this);
        vSetting.setOnClickListener(this);
        tvUpdateInfo.setOnClickListener(this);
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

        setMemberView();

        if(Application.application.getMember() != null) {
            memberPresenter.getMember();
        }
    }

    private void setMemberView(){
        if(member == null){
            return;
        }
        Application.application.getImageLoader().displayImage(member.getAvatar_rsurl(), ivAvatar, Application.application.getDefaultOptions());
        tvNickName.setText(member.getNick_name());
        tvCollectionCount.setText(String.valueOf(member.getFav_count()));
        tvMsgCount.setText(String.valueOf(member.getMessage_count()));
        tvTrendCount.setText(String.valueOf(member.getTrend_count()));
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_my;
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
        return Application.application.getMember().getId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_role:
                startActivity(new Intent(getActivity(), UpdateRoleActivity.class));
                break;
            case R.id.layout_order_list:
                startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                break;
            case R.id.layout_works:
                break;
            case R.id.layout_classes:
                ActivityBuilder.startMyClassesActivity(getActivity());
                break;
            case R.id.layout_account:
                startActivity(new Intent(getActivity(), MyAccountActivity.class));
                break;
            case R.id.layout_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.tv_update_info:
                startActivity(new Intent(getActivity(), ProfileInfoActivity.class));
                break;
        }
    }
}
