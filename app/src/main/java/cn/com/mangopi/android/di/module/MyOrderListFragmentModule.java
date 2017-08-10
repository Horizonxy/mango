package cn.com.mangopi.android.di.module;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.FragmentScope;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.BaseViewListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.DateUtils;
import dagger.Module;
import dagger.Provides;

/**
 * @author 蒋先明
 * @date 2017/6/29
 */
@Module
public class MyOrderListFragmentModule {

    Fragment fragment;
    List datas;
    /** 1: 我下的订单2：我收到的订单 */
    int relation;
    OnOrderStateListener onOrderStateListener;

    public MyOrderListFragmentModule(Fragment fragment, List datas, int relation, OnOrderStateListener onOrderStateListener) {
        this.fragment = fragment;
        this.datas = datas;
        this.relation = relation;
        this.onOrderStateListener = onOrderStateListener;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<OrderBean>(fragment.getContext(), R.layout.listview_item_order_list, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, OrderBean item) {
                helper.setText(R.id.tv_order_no, "单号："+item.getFiveLenOrderNo())
                        .setText(R.id.tv_order_time, DateUtils.dateToString(item.getOrder_time(), DateUtils.TIME_PATTERN_YMDHM))
                        .setText(R.id.tv_pay_state_label, item.getState_label())
                        .setText(R.id.tv_order_count, "x "+item.getOrder_count())
                        .setText(R.id.tv_course_name, item.getOrder_name());
                if(relation == 1) {
                    helper.setText(R.id.tv_member_name, item.getTutor_name());
                    helper.setVisible(R.id.btn_act, false);
                    if(item.getState() != null && item.getState().intValue() == 2) {
                        helper.setVisible(R.id.layout_action, true);
                        helper.setVisible(R.id.v_line, true);
                    } else {
                        helper.setVisible(R.id.layout_action, false);
                        helper.setVisible(R.id.v_line, false);
                    }
                } else if(relation == 2){
                    helper.setText(R.id.tv_member_name, item.getMember_name());
                    helper.setVisible(R.id.btn_act, true);
                    helper.setVisible(R.id.btn_pay, false);
                    helper.setVisible(R.id.btn_cancle, false);
                }
                if(item.getPay_price() != null) {
                    helper.setText(R.id.tv_sale_price, fragment.getString(R.string.rmb) + item.getPay_price().toString());
                }

                ItemClickListener clickListener = new ItemClickListener(item);
                helper.setOnClickListener(R.id.btn_pay, clickListener);
                helper.setOnClickListener(R.id.btn_cancle, clickListener);
            }
        };
    }

    @FragmentScope
    @Provides
    public OrderPresenter provideOrderPresenter(){
        return new OrderPresenter(new OrderModel(), (BaseViewListener) fragment);
    }

    class ItemClickListener implements View.OnClickListener{

        OrderBean order;

        public ItemClickListener(OrderBean order) {
            this.order = order;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_pay:
                    ActivityBuilder.startSelectPayActivity(fragment.getActivity(), order);
                    break;
                case R.id.btn_cancle:
                    if(onOrderStateListener != null){
                        onOrderStateListener.onCancel(order);
                    }
                    break;
            }
        }
    }

    public interface OnOrderStateListener {
        void onCancel(OrderBean order);
    }
}
