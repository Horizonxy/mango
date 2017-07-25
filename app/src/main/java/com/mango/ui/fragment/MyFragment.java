package com.mango.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.Application;
import com.mango.Constants;
import com.mango.R;
import com.mango.di.component.DaggerMyFragmentComponent;
import com.mango.di.module.MyFragmentModule;
import com.mango.model.bean.MemberBean;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.activity.MyAccountActivity;
import com.mango.ui.activity.MyOrderListActivity;
import com.mango.ui.activity.ProfileInfoActivity;
import com.mango.ui.activity.SettingActivity;
import com.mango.ui.activity.UpgradeRoleActivity;
import com.mango.ui.viewlistener.MyFragmentListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;
import com.mango.util.MangoUtils;

import java.util.List;

import javax.inject.Inject;

public class MyFragment extends BaseFragment implements MyFragmentListener{

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
    TextView tvClassCount;
    TextView tvProjectCount;
    TextView tvUpdateRole;
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
        tvClassCount = (TextView) vClasses.findViewById(R.id.tv_right);
        tvProjectCount = (TextView) vWorks.findViewById(R.id.tv_right);
        tvUpdateRole = (TextView) vRole.findViewById(R.id.tv_right);

        View.OnClickListener clickListener = new CheckLoginClickListener(){

            @Override
            void onLoginClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_role:
                        startActivity(new Intent(getActivity(), UpgradeRoleActivity.class));
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
        };
        vRole.setOnClickListener(clickListener);
        vOrderList.setOnClickListener(clickListener);
        vWorks.setOnClickListener(clickListener);
        vClasses.setOnClickListener(clickListener);
        vAccount.setOnClickListener(clickListener);
        vSetting.setOnClickListener(clickListener);
        tvUpdateInfo.setOnClickListener(clickListener);
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
        ((ImageView)vRole.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_id);
        ((ImageView)vOrderList.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_dingdan);
        ((ImageView)vWorks.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_gongzuobao);
        ((ImageView)vClasses.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_shouke);
        ((ImageView)vAccount.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_zhanghu);
        ((ImageView)vSetting.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_shezhi);


        setMemberView();

        if(Application.application.getMember() != null) {
            memberPresenter.getMember();
        }
    }

    private void setMemberView(){
        if(member == null){
            tvNickName.setText("");
            tvCollectionCount.setText("-");
            tvMsgCount.setText("-");
            tvTrendCount.setText("-");
            tvRole.setText(getString(R.string.my_role));
            tvClassCount.setText("");
            tvProjectCount.setText("");
            tvUpdateRole.setText("");
        } else {
            Application.application.getImageLoader().displayImage(member.getAvatar_rsurl(), ivAvatar, Application.application.getDefaultOptions());
            tvNickName.setText(member.getNick_name());
            tvCollectionCount.setText(String.valueOf(member.getFav_count()));
            tvMsgCount.setText(String.valueOf(member.getMessage_count()));
            tvTrendCount.setText(String.valueOf(member.getTrend_count()));
            tvRole.setText(String.format(getString(R.string.my_role), member.getUser_identity_label()));
            tvClassCount.setText(String.valueOf(member.getCourse_count()));
            tvProjectCount.setText(String.valueOf(member.getProject_count()));

            List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
            if((indentityList.contains(Constants.UserIndentity.STUDENT) && indentityList.contains(Constants.UserIndentity.TUTOR) && indentityList.contains(Constants.UserIndentity.COMMUNITY))
                    || (indentityList.contains(Constants.UserIndentity.TUTOR) && indentityList.contains(Constants.UserIndentity.STUDENT) && indentityList.contains(Constants.UserIndentity.COMPANY) && indentityList.contains(Constants.UserIndentity.COMMUNITY))
                    || (indentityList.contains(Constants.UserIndentity.COMPANY) &&indentityList.contains(Constants.UserIndentity.TUTOR))
                    || (indentityList.contains(Constants.UserIndentity.COMMUNITY) && indentityList.contains(Constants.UserIndentity.STUDENT) && indentityList.contains(Constants.UserIndentity.TUTOR))){
                tvUpdateRole.setText(getString(R.string.click_to_eye));
            } else {
                tvUpdateRole.setText(getString(R.string.click_to_update));
            }
        }
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
    public void onSuccess(MemberBean member) {
        this.member = member;
        setMemberView();
        Application.application.saveMember(member, Application.application.getSessId());
    }

    @Override
    public long getMemberId() {
        return Application.application.getMember().getId();
    }

    abstract class CheckLoginClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(Application.application.getMember() == null){

            } else {
                onLoginClick(v);
            }
        }

        abstract void onLoginClick(View v);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(memberPresenter != null) {
            memberPresenter.onDestroy();
        }
    }
}
