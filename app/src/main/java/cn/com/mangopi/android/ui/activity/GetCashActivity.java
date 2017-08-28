package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.CourseTypeBean;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberWalletPresenter;
import cn.com.mangopi.android.presenter.WalletDrawPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.popupwindow.ListViewPopupWindow;
import cn.com.mangopi.android.ui.viewlistener.MemeberWalletListener;
import cn.com.mangopi.android.ui.viewlistener.WalletDrawListener;
import cn.com.mangopi.android.ui.widget.PointLengthFilter;
import cn.com.mangopi.android.util.AppUtils;

public class GetCashActivity extends BaseTitleBarActivity implements MemeberWalletListener, WalletDrawListener {

    @Bind(R.id.tv_card_no)
    TextView tvCardNo;
    @Bind(R.id.et_amount)
    EditText etAmount;
    @Bind(R.id.tv_can_cash_amount)
    TextView tvCanCashAmount;
    BigDecimal availableAmount;
    List<MemberCardBean> cardList = new ArrayList<MemberCardBean>();
    MemberCardBean selectCard;
    MemberWalletPresenter presenter;
    WalletDrawPresenter drawPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_cash);

        availableAmount = (BigDecimal) getIntent().getSerializableExtra(Constants.BUNDLE_AMOUNT);
        if(availableAmount == null){
            finish();
        }
        initView();
        MemberModel memberModel = new MemberModel();
        presenter = new MemberWalletPresenter(memberModel, this);
        drawPresenter = new WalletDrawPresenter(memberModel, this);
        presenter.getCardList();
    }

    private void initView() {
        titleBar.setTitle(R.string.get_cash);

        etAmount.setFilters(new InputFilter[] { new PointLengthFilter() });
        tvCanCashAmount.setText(String.format(getString(R.string.can_get_cash_amount), availableAmount.toString()));
    }

    @OnClick(R.id.btn_get_cash)
    void onGetCash(View v){
        MemberCardBean cardBean = (MemberCardBean) tvCardNo.getTag();
        if(cardBean == null){
            AppUtils.showToast(this, "请选择银行卡");
            return;
        }
        if(TextUtils.isEmpty(etAmount.getText())){
            AppUtils.showToast(this, "请输入提现金额");
            return;
        }
        drawPresenter.walletDraw();
    }

    @OnClick(R.id.tv_card_no)
    void onSelectCardClick(View v){
        if(cardList == null || cardList.size() == 0){
            presenter.getCardList();
        } else {
            showCardList(cardList);
        }
    }

    private void showCardList(List<MemberCardBean> cardList) {
        if(cardList.size() > 0) {
            ListViewPopupWindow<MemberCardBean> cardListPopupWindow = new ListViewPopupWindow<MemberCardBean>(this, cardList, (MemberCardBean) tvCardNo.getTag(), new ListViewPopupWindow.OnListViewListener<MemberCardBean>() {
                @Override
                public void onItemClick(MemberCardBean data) {
                    tvCardNo.setText(data.getBank_name());
                    tvCardNo.setTag(data);
                }
            }){

                @Override
                public void fiddData(BaseAdapterHelper helper, MemberCardBean item) {
                    helper.setText(R.id.tv_text, item.getBank_name());
                }
            };
            cardListPopupWindow.showAsDropDown(tvCardNo);
        } else {
            onFailure("请先绑定银行卡");
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
    public void onWalletSuccess(MemberWalletBean memberWallet) {}

    @Override
    public void onCardListSuccess(List<MemberCardBean> memberCardList) {
        cardList.clear();
        if(memberCardList != null && memberCardList.size() > 0){
            cardList.addAll(memberCardList);
        }

        if(cardList.size() > 0) {
            selectCard = cardList.get(0);

            tvCardNo.setTag(selectCard);
            tvCardNo.setText(selectCard.getBank_name());
        }
    }

    @Override
    public void onDrawSuccess() {
        AppUtils.showToast(this, "提现申请已提交，请等候系统确认");
        finish();
    }

    @Override
    public long getCardId() {
        MemberCardBean cardBean = (MemberCardBean) tvCardNo.getTag();
        return cardBean.getId();
    }

    @Override
    public BigDecimal getAmount() {
        return new BigDecimal(etAmount.getText().toString());
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        if(drawPresenter != null){
            drawPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
