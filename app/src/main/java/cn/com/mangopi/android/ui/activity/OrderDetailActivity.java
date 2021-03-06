package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.mcxiaoke.bus.Bus;
import com.mcxiaoke.bus.annotation.BusReceiver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.OrderBean;
import cn.com.mangopi.android.model.bean.OrderDetailBean;
import cn.com.mangopi.android.model.data.OrderModel;
import cn.com.mangopi.android.presenter.OrderPresenter;
import cn.com.mangopi.android.ui.viewlistener.OrderDetailListener;
import cn.com.mangopi.android.ui.viewlistener.OrderListListener;
import cn.com.mangopi.android.ui.widget.Clickable;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import cn.com.mangopi.android.util.DateUtils;
import cn.com.mangopi.android.util.DisplayUtils;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.MangoUtils;
import cn.com.mangopi.android.util.SmallPicInfo;
import rx.functions.Action1;
import rx.functions.Func1;

public class OrderDetailActivity extends BaseTitleBarActivity implements OrderDetailListener, OrderListListener {

    long id;
    OrderPresenter presenter;
    @Bind(R.id.tv_order_no)
    TextView tvOrderNo;
    @Bind(R.id.tv_pay_state_label)
    TextView tvStateLabel;
    @Bind(R.id.tv_order_time)
    TextView tvOrderTime;
    @Bind(R.id.tv_order_count)
    TextView tvOrderCount;
    @Bind(R.id.tv_order_name)
    TextView tvOrderName;
    @Bind(R.id.tv_sale_price)
    TextView tvSalePrice;
    @Bind(R.id.tv_member_name)
    TextView tvMemberName;
    @Bind(R.id.tv_total_price)
    TextView tvTotalPrice;
    @Bind(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @Bind(R.id.tv_pay_price)
    TextView tvPayPrice;
    OrderDetailBean orderDetail;
    @Bind(R.id.btn_cancel)
    Button btnCancel;
    @Bind(R.id.btn_cancel1)
    Button btnCancel1;
    @Bind(R.id.btn_pay)
    Button btnPay;
    @Bind(R.id.layout_material)
    LinearLayout layoutMaterial;
    @Bind(R.id.grid_material)
    GridLayout gridMaterial;
    @Bind(R.id.line_material)
    View lineMaterial;
    int dp5;
    FrameLayout.LayoutParams rsItemLp;
    @Bind(R.id.layout_after_info)
    LinearLayout layoutAfterInfo;
    @Bind(R.id.tv_after_info)
    TextView tvAfterInfo;
    @Bind(R.id.line_after_info)
    View lineAfterInfo;
    @Bind(R.id.layout_content)
    LinearLayout layoutContent;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_reply)
    TextView tvTutorReply;
    @Bind(R.id.line_content)
    View lineContent;
//    @Bind(R.id.tv_like_tip)
//    TextView tvLikeTip;
//    @Bind(R.id.line_like_tip)
//    View lineLikeTip;
    int relation;
    @Bind(R.id.layout_make)
    LinearLayout laoutMake;
    @Bind(R.id.layout_make2)
    LinearLayout layoutMake2;
    @Bind(R.id.layout_received)
    LinearLayout layoutReceived;
    @Bind(R.id.btn_act)
    Button btnAct;
    @Bind(R.id.btn_un_act)
    Button btnUnAct;
    @Bind(R.id.btn_reply)
    Button btnReply;
    @Bind(R.id.btn_comment)
    Button btnComment;
//    @Bind(R.id.layout_reward)
//    LinearLayout layoutReward;
//    @Bind(R.id.btn_reward)
//    Button btnReward;

    @Bind(R.id.line_receive)
    View lineReceive;
    @Bind(R.id.tv_receive_price)
    TextView tvReceivePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Bus.getDefault().register(this);
        id = getIntent().getLongExtra(Constants.BUNDLE_ORDER_ID, 0);
        relation = getIntent().getIntExtra(Constants.BUNDLE_ORDER_RELATION, 1);
        initView();
        presenter = new OrderPresenter(new OrderModel(), this);
        presenter.getOrder();

        dp5 = (int) getResources().getDimension(R.dimen.dp_5);
        int width = (int) ((DisplayUtils.screenWidth(this) - getResources().getDimension(R.dimen.dp_15) * 2 - dp5 * 4) / 5);
        rsItemLp = new FrameLayout.LayoutParams(width, width);
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        Bus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        titleBar.setTitle(R.string.order_detail);
    }

    private void fillData(OrderDetailBean orderDetail){
        this.orderDetail = orderDetail;

        tvOrderNo.setText("单号："+orderDetail.getOrder_no());
        tvOrderTime.setText(DateUtils.dateToString(orderDetail.getOrder_time(), DateUtils.TIME_PATTERN_YMDHM));
        tvStateLabel.setText(orderDetail.getState_label());
        tvOrderName.setText(orderDetail.getOrder_name());
        tvOrderCount.setText("x "+orderDetail.getOrder_count());

        String afterInfo = orderDetail.getAfter_sale_info();
        if(TextUtils.isEmpty(afterInfo)){
            layoutAfterInfo.setVisibility(View.GONE);
            lineAfterInfo.setVisibility(View.GONE);
        } else {
            tvAfterInfo.setText(afterInfo);
            lineAfterInfo.setVisibility(View.VISIBLE);
            layoutAfterInfo.setVisibility(View.VISIBLE);
        }

        bindOrderCommentData(orderDetail);

        if(relation ==1){
            tvMemberName.setText(orderDetail.getTutor_name());
            bindMakeOrder(orderDetail);
        } else if(relation == 2){
            tvMemberName.setText(orderDetail.getMember_name());
            bindReceivedOrder(orderDetail);
        }

    }

    private void bindOrderCommentData(OrderDetailBean orderDetail){
        List<OrderDetailBean.Comment> commentList = orderDetail.getComments();
        if(commentList == null || commentList.size() == 0){//点评
            layoutContent.setVisibility(View.GONE);
            lineContent.setVisibility(View.GONE);
        } else {
            layoutContent.setVisibility(View.VISIBLE);
            lineContent.setVisibility(View.VISIBLE);
            OrderDetailBean.Comment comment = commentList.get(0);
            tvContent.setText(comment.getContent());
            if(TextUtils.isEmpty(comment.getReply())){
                tvTutorReply.setVisibility(View.GONE);
            } else {
                tvTutorReply.setVisibility(View.VISIBLE);
                tvTutorReply.setText(String.format(getString(R.string.tutor_reply), comment.getReply()));
            }
        }
    }

    private void bindReceivedOrder(OrderDetailBean orderDetail){
        laoutMake.setVisibility(View.GONE);
        layoutMake2.setVisibility(View.GONE);
        layoutReceived.setVisibility(View.VISIBLE);

        lineReceive.setVisibility(View.VISIBLE);
        tvReceivePrice.setVisibility(View.VISIBLE);

        if(orderDetail.getTotal_price() != null) {
            tvReceivePrice.setText(getString(R.string.rmb)+orderDetail.getTotal_price().toString());
        } else {
            tvReceivePrice.setText("");
        }

        if(TextUtils.isEmpty(orderDetail.getMember_mobile())){
            tvSalePrice.setText("");
        } else {
            String mobile = orderDetail.getMember_mobile();
            SpannableString spannableString = new SpannableString(mobile);
            spannableString.setSpan(new Clickable(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityBuilder.startPhoneNumber(OrderDetailActivity.this, mobile);
                }
            }), 0, mobile.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvSalePrice.setText(spannableString, TextView.BufferType.SPANNABLE);
            tvSalePrice.setMovementMethod(LinkMovementMethod.getInstance());
            tvSalePrice.setHighlightColor(getResources().getColor(android.R.color.transparent));
            tvSalePrice.setTextSize(14);
        }

        if(orderDetail.getState() != null){
            if(orderDetail.getState().intValue() == 2){//订单待付款
                btnUnAct.setVisibility(View.GONE);
                btnAct.setVisibility(View.GONE);
                btnCancel1.setVisibility(View.VISIBLE);
            } else if (orderDetail.getState().intValue() == 5){//订单已付款，已安排
                btnUnAct.setVisibility(View.VISIBLE);
                btnAct.setVisibility(View.GONE);
                btnCancel1.setVisibility(View.GONE);
            } else if (orderDetail.getState().intValue() == 4){//订单已付款，待安排
                btnAct.setVisibility(View.VISIBLE);
                btnUnAct.setVisibility(View.GONE);
                btnCancel1.setVisibility(View.GONE);
            } else {
                btnUnAct.setVisibility(View.GONE);
                btnAct.setVisibility(View.GONE);
                btnCancel1.setVisibility(View.GONE);
            }
        } else {
            btnCancel1.setVisibility(View.GONE);
            btnUnAct.setVisibility(View.GONE);
            btnAct.setVisibility(View.GONE);
        }
        if(orderDetail.getComments() != null && orderDetail.getComments().size() > 0 && TextUtils.isEmpty(orderDetail.getComments().get(0).getReply())){//回复点评
            btnReply.setVisibility(View.VISIBLE);
        } else {
            btnReply.setVisibility(View.GONE);
        }
    }

    private void bindMakeOrder(OrderDetailBean orderDetail){
        laoutMake.setVisibility(View.VISIBLE);
        layoutMake2.setVisibility(View.VISIBLE);
        layoutReceived.setVisibility(View.GONE);
        if(orderDetail.getMaterial_rsurls() == null || orderDetail.getMaterial_rsurls().size() == 0){
            layoutMaterial.setVisibility(View.GONE);
            lineMaterial.setVisibility(View.GONE);
        } else {
            layoutMaterial.setVisibility(View.VISIBLE);
            lineMaterial.setVisibility(View.VISIBLE);
            gridMaterial.removeAllViews();
            for (int i = 0; i < orderDetail.getMaterial_rsurls().size(); i++){
                String rsurl = orderDetail.getMaterial_rsurls().get(i);
                ImageView item = new ImageView(this);
                item.setScaleType(ImageView.ScaleType.CENTER_CROP);
                String suffix = FileUtils.getMIMEType(rsurl);
                if(FileUtils.FILE_TYPE_IMAGE.equals(suffix)) {
                    Application.application.getImageLoader().displayImage(rsurl, item, Application.application.getDefaultOptions());
                    RxView.clicks(item)
                            .throttleFirst(1, TimeUnit.SECONDS)
                            .map(new Func1<Void, SmallPicInfo>() {
                                @Override
                                public SmallPicInfo call(Void aVoid) {
                                    return MangoUtils.getSmallPicInfo(item, rsurl);
                                }
                            })
                            .filter(new Func1<SmallPicInfo, Boolean>() {
                                @Override
                                public Boolean call(SmallPicInfo smallPicInfo) {
                                    return smallPicInfo != null;
                                }
                            })
                            .subscribe(new Action1<SmallPicInfo>() {
                                @Override
                                public void call(SmallPicInfo smallPicInfo) {
                                    PictureDetailActivity.bmp = ((BitmapDrawable)item.getDrawable()).getBitmap();
                                    ActivityBuilder.startPictureDetailActivity(OrderDetailActivity.this, smallPicInfo);
                                }
                            });
                } else {
                    item.setImageResource(AppUtils.getResIdBySuffix(rsurl));
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            String url = "https://view.officeapps.live.com/op/view.aspx?src=" + AppUtils.replaceHttps(rsurl);
                            ActivityBuilder.startSystemBrowser(OrderDetailActivity.this, Uri.parse(rsurl));
                        }
                    });
                }
                GridLayout.LayoutParams gl = new GridLayout.LayoutParams(rsItemLp);
                if(i >= 5){
                    gl.topMargin = dp5;
                }
                if (i % 5 == 1) {
                    gl.leftMargin = dp5;
                } else if(i % 5 == 2) {
                    gl.leftMargin = dp5;
                    gl.rightMargin = dp5;
                } else if(i % 5 == 3){
                    gl.rightMargin = dp5;
                }

                gridMaterial.addView(item, i, gl);
            }
        }

        if(orderDetail.getSale_price() != null) {
            tvSalePrice.setText(getString(R.string.rmb)+orderDetail.getSale_price().toString());
            tvSalePrice.setTextSize(16);
        } else {
            tvSalePrice.setText("");
        }
        if(orderDetail.getTotal_price() != null) {
            tvTotalPrice.setText(getString(R.string.rmb)+orderDetail.getTotal_price().toString());
        } else {
            tvTotalPrice.setText("");
        }
        if(orderDetail.getDiscount_price() != null) {
            tvDiscountPrice.setText(getString(R.string.rmb)+orderDetail.getDiscount_price().toString());
        } else {
            tvDiscountPrice.setText("");
        }
        if(orderDetail.getPay_price() != null) {
            tvPayPrice.setText(getString(R.string.rmb)+orderDetail.getPay_price().toString());
        } else {
            tvPayPrice.setText("");
        }

        if(orderDetail.getState() != null){
            if(orderDetail.getState().intValue() == 2){//订单待付款
                btnCancel.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.VISIBLE);
//                layoutReward.setVisibility(View.GONE);
//                tvLikeTip.setVisibility(View.GONE);
//                lineLikeTip.setVisibility(View.GONE);
                btnComment.setVisibility(View.GONE);
            }  else if(orderDetail.getState().intValue() == 5 || orderDetail.getState().intValue() == 50){//订单已安排、交易完成
//                layoutReward.setVisibility(View.VISIBLE);
//                tvLikeTip.setVisibility(View.VISIBLE);
//                lineLikeTip.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                btnPay.setVisibility(View.GONE);

                if(orderDetail.getComments() == null || orderDetail.getComments().size() == 0){//点评
                    btnComment.setVisibility(View.VISIBLE);
                } else {
                    btnComment.setVisibility(View.GONE);
                }
            } else {
//                layoutReward.setVisibility(View.GONE);
//                tvLikeTip.setVisibility(View.GONE);
//                lineLikeTip.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                btnPay.setVisibility(View.GONE);
                btnComment.setVisibility(View.GONE);
            }
        } else {
            btnCancel.setVisibility(View.GONE);
            btnPay.setVisibility(View.GONE);
//            layoutReward.setVisibility(View.GONE);
//            tvLikeTip.setVisibility(View.GONE);
//            lineLikeTip.setVisibility(View.GONE);
            btnComment.setVisibility(View.GONE);
        }

        lineReceive.setVisibility(View.GONE);
        tvReceivePrice.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_pay)
    void onPay(View v){
        ActivityBuilder.startSelectPayActivity(this, converOrderBean(orderDetail));
    }

    @OnClick({R.id.btn_cancel, R.id.btn_cancel1})
    void onCancel(View v){
        presenter.cancelOrder(converOrderBean(orderDetail));
    }

    @OnClick(R.id.btn_act)
    void onAct(View v){
        ActivityBuilder.startOrderScheduleCalendarActivity(this, orderDetail.getCourse_id(), orderDetail.getId());
    }

    @OnClick(R.id.btn_un_act)
    void onUnAct(View v){
        presenter.cancelSchedule(converOrderBean(orderDetail));
    }

    @OnClick(R.id.btn_reply)
    void onReply(View v){
        ActivityBuilder.startInputMessageActivity(this, "回复", "确定", "reply_comment", 200, "");
    }

    @OnClick(R.id.btn_reward)
    void onReward(View v){

    }

    @OnClick(R.id.btn_comment)
    void onComment(View v){
        ActivityBuilder.startInputMessageActivity(this, "点评", "确定", "comment_order", 200, "");
    }

    @BusReceiver
    public void onInputEvent(BusEvent.InputEvent event) {
        String type = event.getType();
        if ("comment_order".equals(type) && !TextUtils.isEmpty(event.getContent())) {
            presenter.addCourseComment(event.getContent());
        } else if("reply_comment".equals(type) && !TextUtils.isEmpty(event.getContent())){
            presenter.replyCourseComment(event.getContent());
        }
    }

    @BusReceiver
    public void onPayOrderSuccessEventEvent(BusEvent.PayOrderSuccessEvent event) {
        if(event != null && event.getId() == id && orderDetail != null){
            orderDetail.setState(4);
            orderDetail.setState_label("订单已付款，待安排");
            fillData(orderDetail);
        }
    }

    @BusReceiver
    public void onOrderSeheduleEvent(BusEvent.OrderSeheduleEvent event) {
        if(event != null && event.getId() == id && orderDetail != null){
            orderDetail.setState(5);
            orderDetail.setState_label("订单已安排，待上课");
            fillData(orderDetail);
        }
    }

    @BusReceiver
    public void onCancelOrderSeheduleEvent(BusEvent.CancelOrderSeheduleEvent event){
        if(event != null && event.getId() == id && orderDetail != null){
            orderDetail.setState(4);
            orderDetail.setState_label("订单已付款，待安排");
            fillData(orderDetail);
        }
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }

    @Override
    public void onSuccess(OrderDetailBean orderDetail) {

        fillData(orderDetail);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getCourseId() {
        if(orderDetail != null){
            return orderDetail.getCourse_id();
        }
        return 0;
    }

    @Override
    public void onCommentSuccess(String content) {
        OrderDetailBean.Comment comment = new OrderDetailBean.Comment();
        comment.setContent(content);
        comment.setCourse_id(orderDetail.getCourse_id());
        comment.setMember_id(Application.application.getMember().getId());
        orderDetail.getComments().add(0, comment);
        bindOrderCommentData(orderDetail);
        btnComment.setVisibility(View.GONE);
    }

    @Override
    public long getCommentId() {
        if(orderDetail != null){
            List<OrderDetailBean.Comment> comments = orderDetail.getComments();
            if(comments != null && comments.size() > 0){
                return comments.get(0).getId();
            }
        }
        return 0;
    }

    @Override
    public void onReplySuccess(String reply) {
        List<OrderDetailBean.Comment> comments = orderDetail.getComments();
        if(comments != null && comments.size() > 0) {
            OrderDetailBean.Comment comment = comments.get(0);
            comment.setReply(reply);
            bindOrderCommentData(orderDetail);
            btnReply.setVisibility(View.GONE);
        }
    }

    private OrderBean converOrderBean(OrderDetailBean orderDetail){
        OrderBean order = new OrderBean();
        order.setSale_price(orderDetail.getSale_price());
        order.setPay_price(orderDetail.getPay_price());
        order.setTotal_price(orderDetail.getTotal_price());
        order.setDiscount_price(orderDetail.getDiscount_price());
        order.setId(orderDetail.getId());
        order.setOrder_count(orderDetail.getOrder_count());
        order.setMember_id(orderDetail.getMember_id());
        order.setMember_name(orderDetail.getMember_name());
        order.setCourse_id(orderDetail.getCourse_id());
        order.setOrder_name(orderDetail.getOrder_name());
        order.setOrder_no(orderDetail.getOrder_no());
        order.setPromotion_code(orderDetail.getPromotion_code());
        return order;
    }

    @Override
    public void onOrderListSuccess(List<OrderBean> orderList) {}

    @Override
    public Map<String, Object> getQueryMap() {
        return null;
    }

    @Override
    public void onCancelSuccess(OrderBean order) {
        orderDetail.setState(-1);
        orderDetail.setState_label("订单已取消");
        fillData(orderDetail);

        BusEvent.CancelOrderEvent event = new BusEvent.CancelOrderEvent();
        event.setId(order.getId());
        Bus.getDefault().postSticky(event);
    }

    @Override
    public void onCancelScheduleSuccess(OrderBean order) {
        order.setState(4);
        order.setState_label("订单已付款，待安排");
        fillData(orderDetail);

        BusEvent.CancelOrderSeheduleEvent event = new BusEvent.CancelOrderSeheduleEvent();
        event.setId(order.getId());
        Bus.getDefault().postSticky(event);
    }
}
