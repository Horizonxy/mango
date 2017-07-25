package com.mango.di.module;

import android.support.v4.app.Fragment;

import com.mango.R;
import com.mango.di.FragmentScope;
import com.mango.model.bean.OrderBean;
import com.mango.model.data.OrderModel;
import com.mango.presenter.OrderPresenter;
import com.mango.ui.adapter.quickadapter.BaseAdapterHelper;
import com.mango.ui.adapter.quickadapter.QuickAdapter;
import com.mango.ui.viewlistener.BaseViewListener;
import com.mango.util.DateUtils;

import java.util.List;

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

    public MyOrderListFragmentModule(Fragment fragment, List datas, int relation) {
        this.fragment = fragment;
        this.datas = datas;
        this.relation = relation;
    }

    @FragmentScope
    @Provides
    public QuickAdapter provideQuickAdapter(){
        return new QuickAdapter<OrderBean>(fragment.getContext(), R.layout.listview_item_order_list, datas) {
            @Override
            protected void convert(BaseAdapterHelper helper, OrderBean item) {
                ;
                helper.setText(R.id.tv_order_no, "单号："+item.getOrder_no())
                        .setText(R.id.tv_order_time, DateUtils.dateToString(item.getOrder_time(), DateUtils.TIME_PATTERN_YMDHM))
                        .setText(R.id.tv_pay_state_label, item.getPay_state_label())
                        .setText(R.id.tv_order_count, "x "+item.getOrder_count())
                        .setText(R.id.tv_course_name, item.getOrder_name());
                if(relation == 1) {
                    helper.setText(R.id.tv_member_name, item.getTutor_name());
                    helper.setVisible(R.id.btn_act, false);
                    helper.setVisible(R.id.btn_pay, true);
                    helper.setVisible(R.id.btn_cancle, true);
                } else if(relation == 2){
                    helper.setText(R.id.tv_member_name, item.getMember_name());
                    helper.setVisible(R.id.btn_act, true);
                    helper.setVisible(R.id.btn_pay, false);
                    helper.setVisible(R.id.btn_cancle, false);
                }
                if(item.getSale_price() != null) {
                    helper.setText(R.id.tv_sale_price, fragment.getString(R.string.rmb) + item.getSale_price().toString());
                }
            }
        };
    }

    @FragmentScope
    @Provides
    public OrderPresenter provideOrderPresenter(){
        return new OrderPresenter(new OrderModel(), (BaseViewListener) fragment);
    }

}
