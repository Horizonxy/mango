package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberWalletPresenter;
import cn.com.mangopi.android.ui.viewlistener.MemeberWalletListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

public class MyAccountActivity extends BaseTitleBarActivity implements MemeberWalletListener {

    MemberWalletPresenter presenter;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_available)
    TextView tvAvailable;
    @Bind(R.id.tv_freezing)
    TextView tvFreezing;
    @Bind(R.id.layout_card)
    View layoutCard;
    @Bind(R.id.layout_cash)
    View layoutCash;
    @Bind(R.id.layout_detail)
    View layoutDetail;
    TextView tvCard;
    TextView tvCardRight;
    TextView tvCash;
    TextView tvDetail;
    List<MemberCardBean> memberCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        presenter = new MemberWalletPresenter(new MemberModel(), this);
        initView();
    }

    private void initView() {
        titleBar.setTitle(getString(R.string.my_account));
        tvCard = (TextView) layoutCard.findViewById(R.id.tv_left);
        tvCardRight = (TextView) layoutCard.findViewById(R.id.tv_right);
        tvCash = (TextView) layoutCash.findViewById(R.id.tv_left);
        tvDetail = (TextView) layoutDetail.findViewById(R.id.tv_left);
        tvCard.setText("我的银行卡");
        tvCash.setText("提现");
        tvDetail.setText("交易明细");
        ((ImageView) layoutCard.findViewById(R.id.iv_left)).setImageResource(R.drawable.iocn_card);
        ((ImageView) layoutCash.findViewById(R.id.iv_left)).setImageResource(R.drawable.iocn_tixian);
        ((ImageView) layoutDetail.findViewById(R.id.iv_left)).setImageResource(R.drawable.iocn_jiaoyi);

        presenter.getWallet();
        presenter.getCardList();
    }

    @OnClick(R.id.layout_card)
    void cardListClick(View v){
        if(memberCardList == null || memberCardList.size()  == 0) {
            ActivityBuilder.startAddBlankCardActivity(this);
        } else {
            ActivityBuilder.startCardListActivity(this, memberCardList);
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
    public void onWalletSuccess(MemberWalletBean memberWallet) {
        if(memberWallet.getTotal_amount() != null) {
            tvTotal.setText(memberWallet.getTotal_amount().toString());
        }
        if(memberWallet.getAvailable_amount() != null) {
            tvAvailable.setText(memberWallet.getAvailable_amount().toString());
        }
        if(memberWallet.getFreezing_amount() != null) {
            tvFreezing.setText(memberWallet.getFreezing_amount().toString());
        }

    }

    @Override
    public void onCardListSuccess(List<MemberCardBean> memberCardList) {
        this.memberCardList = memberCardList;
        if(memberCardList == null || memberCardList.size() == 0){
            tvCardRight.setText(getString(R.string.unbind_card));
        } else {
            tvCardRight.setText("");
        }
    }
}