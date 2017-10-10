package cn.com.mangopi.android.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import javax.inject.Inject;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerMyFragmentComponent;
import cn.com.mangopi.android.di.module.MyFragmentModule;
import cn.com.mangopi.android.model.bean.MemberBean;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.activity.MemberCouponListActivity;
import cn.com.mangopi.android.ui.activity.MyAccountActivity;
import cn.com.mangopi.android.ui.activity.MyOrderListActivity;
import cn.com.mangopi.android.ui.activity.SettingActivity;
import cn.com.mangopi.android.ui.viewlistener.MemberDetailListener;
import cn.com.mangopi.android.ui.widget.MangoPtrFrameLayout;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.MangoUtils;

public class MyFragment extends BaseFragment implements MemberDetailListener {

    MangoPtrFrameLayout refreshLayout;
    TextView tvNickName;
    TextView tvUpdateInfo;
    View vRole;
    View vCoupon;
    View vOrderList;
    View vWorks;
    View vClasses;
    View vAccount;
    View vSetting;

    TextView tvRole;
    TextView tvCoupon;
    TextView tvOrderList;
    TextView tvWorks;
    TextView tvClasses;
    TextView tvAccount;
    TextView tvSetting;
    TextView tvOrderCount;
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
    DisplayImageOptions.Builder optionsBuilder;

    public MyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bus.getDefault().register(this);
        DaggerMyFragmentComponent.builder().myFragmentModule(new MyFragmentModule(this)).build().inject(this);

        optionsBuilder = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true);
    }

    @Override
    void findView(View root) {
        refreshLayout = (MangoPtrFrameLayout) root.findViewById(R.id.refresh_layout);
        refreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if(Application.application.getMember() != null) {
                    memberPresenter.getMember();
                }
            }
        });
        tvNickName = (TextView) root.findViewById(R.id.tv_nick_name);
        tvUpdateInfo = (TextView) root.findViewById(R.id.tv_update_info);
        vRole = root.findViewById(R.id.layout_role);
        vCoupon = root.findViewById(R.id.layout_coupon);
        vOrderList = root.findViewById(R.id.layout_order_list);
        vWorks = root.findViewById(R.id.layout_works);
        vClasses = root.findViewById(R.id.layout_classes);
        vAccount = root.findViewById(R.id.layout_account);
        vSetting = root.findViewById(R.id.layout_setting);
        tvCollectionCount = (TextView) root.findViewById(R.id.tv_collection_count);
        tvMsgCount = (TextView) root.findViewById(R.id.tv_msg_count);
        tvTrendCount = (TextView) root.findViewById(R.id.tv_trend_count);
        ivAvatar = (ImageView) root.findViewById(R.id.iv_avatar);
        tvOrderCount = (TextView) vOrderList.findViewById(R.id.tv_right);
        tvClassCount = (TextView) vClasses.findViewById(R.id.tv_right);
        tvProjectCount = (TextView) vWorks.findViewById(R.id.tv_right);
        tvUpdateRole = (TextView) vRole.findViewById(R.id.tv_right);

        View.OnClickListener clickListener = new CheckLoginClickListener(){

            @Override
            void onLoginClick(View v) {
                switch (v.getId()) {
                    case R.id.layout_role:
                        ActivityBuilder.startUpgradeRoleActivityy(getActivity());
                        break;
                    case R.id.layout_coupon:
                        startActivity(new Intent(getActivity(), MemberCouponListActivity.class));
                        break;
                    case R.id.layout_order_list:
                        startActivity(new Intent(getActivity(), MyOrderListActivity.class));
                        break;
                    case R.id.layout_works:
                        Constants.UserIndentity indentity = (Constants.UserIndentity) vWorks.getTag();
                        if(indentity != null) {
                            ActivityBuilder.startMemberWorksActivity(getActivity(), indentity);
                        }
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
                        ActivityBuilder.startProfileInfoActivity(getActivity());
                        break;
                    case R.id.layout_message:
                        ActivityBuilder.startMessageListActivity(getActivity());
                        break;
                    case R.id.layout_fav:
                        ActivityBuilder.startFavListActivity(getActivity());
                        break;
                    case R.id.layout_trend:
                        ActivityBuilder.startMemberTrendActivity(getActivity());
                        break;
                }
            }
        };
        vRole.setOnClickListener(clickListener);
        vCoupon.setOnClickListener(clickListener);
        vOrderList.setOnClickListener(clickListener);
        vWorks.setOnClickListener(clickListener);
        vClasses.setOnClickListener(clickListener);
        vAccount.setOnClickListener(clickListener);
        vSetting.setOnClickListener(clickListener);
        tvUpdateInfo.setOnClickListener(clickListener);
        root.findViewById(R.id.layout_message).setOnClickListener(clickListener);
        root.findViewById(R.id.layout_fav).setOnClickListener(clickListener);
        root.findViewById(R.id.layout_trend).setOnClickListener(clickListener);
    }

    @Override
    void initView() {
        tvRole = (TextView) vRole.findViewById(R.id.tv_left);
        tvCoupon = (TextView) vCoupon.findViewById(R.id.tv_left);
        tvOrderList = (TextView) vOrderList.findViewById(R.id.tv_left);
        tvWorks = (TextView) vWorks.findViewById(R.id.tv_left);
        tvClasses = (TextView) vClasses.findViewById(R.id.tv_left);
        tvAccount = (TextView) vAccount.findViewById(R.id.tv_left);
        tvSetting = (TextView) vSetting.findViewById(R.id.tv_left);
        tvRole.setText(getString(R.string.my_role));
        tvCoupon.setText(getString(R.string.my_coupon));
        tvOrderList.setText(getString(R.string.my_order_list));
        tvWorks.setText(getString(R.string.my_works));
        tvClasses.setText(getString(R.string.my_classes));
        tvAccount.setText(getString(R.string.my_account));
        tvSetting.setText(getString(R.string.setting));
        ((ImageView)vRole.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_id);
        ((ImageView)vCoupon.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_dingdan);
        ((ImageView)vOrderList.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_dingdan);
        ((ImageView)vWorks.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_gongzuobao);
        ((ImageView)vClasses.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_shouke);
        ((ImageView)vAccount.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_zhanghu);
        ((ImageView)vSetting.findViewById(R.id.iv_left)).setImageResource(R.drawable.icon_shezhi);

        setMemberView();
    }

    private void setMemberView(){
        List<Constants.UserIndentity> indentityList = MangoUtils.getIndentityList();
        if(!indentityList.contains(Constants.UserIndentity.TUTOR)){
            vClasses.setVisibility(View.GONE);
            vAccount.setVisibility(View.GONE);
        } else {
            vClasses.setVisibility(View.VISIBLE);
            vAccount.setVisibility(View.GONE);
        }
        if(indentityList.contains(Constants.UserIndentity.COMPANY)){
            vWorks.setVisibility(View.VISIBLE);
            vWorks.setTag(Constants.UserIndentity.COMPANY);
        } else if(indentityList.contains(Constants.UserIndentity.STUDENT)){
            vWorks.setVisibility(View.VISIBLE);
            vWorks.setTag(Constants.UserIndentity.STUDENT);
        } else {
            vWorks.setVisibility(View.GONE);
        }

        if(member == null){
            tvNickName.setText("");
            tvCollectionCount.setText("-");
            tvMsgCount.setText("-");
            tvTrendCount.setText("-");
            tvRole.setText(getString(R.string.my_role));
            tvOrderCount.setText("");
            tvClassCount.setText("");
            tvProjectCount.setText("");
            tvUpdateRole.setText("");
        } else {
            DisplayImageOptions options;
            if(member.getGender() == 1){
                options = optionsBuilder
                        .showImageOnFail(R.drawable.user_pic2)
                        .showImageForEmptyUri(R.drawable.user_pic2)
                        .showImageOnLoading(R.drawable.user_pic2)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
            } else {
                options = optionsBuilder
                        .showImageOnFail(R.drawable.user_pic1)
                        .showImageForEmptyUri(R.drawable.user_pic1)
                        .showImageOnLoading(R.drawable.user_pic1)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();
            }
            Application.application.getImageLoader().displayImage(member.getAvatar_rsurl(), ivAvatar, options);
            tvNickName.setText(member.getNick_name());
            tvCollectionCount.setText(String.valueOf(member.getFav_count()));
            tvMsgCount.setText(String.valueOf(member.getMessage_count()));
            tvTrendCount.setText(String.valueOf(member.getTrend_count()));
            tvRole.setText(String.format(getString(R.string.my_role), member.getUser_identity_label()));
            tvOrderCount.setText(String.valueOf(member.getOrder_count()));
            tvClassCount.setText(String.valueOf(member.getCourse_count()));
            tvProjectCount.setText(String.valueOf(member.getProject_count()));

            if (indentityList.contains(Constants.UserIndentity.STUDENT) ||
                    indentityList.contains(Constants.UserIndentity.TUTOR) ||
                    indentityList.contains(Constants.UserIndentity.COMMUNITY) ||
                    indentityList.contains(Constants.UserIndentity.COMPANY)) {
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
        refreshLayout.refreshComplete();
        this.member = member;
        Application.application.saveMember(member, Application.application.getSessId());
        setMemberView();
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
    public void onResume() {
        super.onResume();
        if(Application.application.getMember() != null) {
            memberPresenter.getMember();
        }
    }

    @BusReceiver
    public void OnRefreshMember(BusEvent.RefreshMemberEvent event){
        if(event != null){
            member = Application.application.getMember();
            setMemberView();
        }
    }

    @Override
    public void onDestroy() {
        Bus.getDefault().unregister(this);
        if(memberPresenter != null) {
            memberPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
