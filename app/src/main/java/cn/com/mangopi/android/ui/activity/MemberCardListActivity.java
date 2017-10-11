package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.bean.MemberCardBean;
import cn.com.mangopi.android.model.bean.MemberWalletBean;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberWalletPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.ui.viewlistener.MemeberWalletListener;
import cn.com.mangopi.android.ui.widget.AddSpaceTextWatcher;
import cn.com.mangopi.android.ui.widget.ListView;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.DialogUtil;

public class MemberCardListActivity extends BaseTitleBarActivity implements MemeberWalletListener {

    List<MemberCardBean> cardList;
    MemberWalletPresenter presenter;
    @Bind(R.id.lv_card)
    ListView lvCard;
    QuickAdapter<MemberCardBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_card_list);

        cardList = (List<MemberCardBean>) getIntent().getSerializableExtra(Constants.BUNDLE_CARD_LIST);
        if(cardList == null){
            cardList = new ArrayList<MemberCardBean>();
        }
        initView();
        presenter = new MemberWalletPresenter(new MemberModel(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getCardList();
    }

    private void initView() {
        titleBar.setTitle(R.string.my_blank_card);

        lvCard.setAdapter(adapter = new QuickAdapter<MemberCardBean>(this, R.layout.listview_item_card_list, cardList) {
            @Override
            protected void convert(BaseAdapterHelper helper, MemberCardBean item) {
                EditText tvCardNo = helper.getView(R.id.tv_card_no);
                new AddSpaceTextWatcher(tvCardNo, 48).setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);
                tvCardNo.setText(item.getDealCardNo());
                helper.setText(R.id.tv_blank_name, item.getBank_name());

                helper.setVisible(R.id.v_line, helper.getPosition() < (data.size() - 1));
                helper.getView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        DialogUtil.createChoosseDialog(MemberCardListActivity.this, "是否删除", "****" + (item.getCard_no().length() > 4 ? item.getCard_no().substring(item.getCard_no().length()-4) : item.getCard_no()) + "银行卡", "确定", "取消", new DialogUtil.OnChooseDialogListener() {
                            @Override
                            public void onChoose() {
                                presenter.delCard(item);
                            }
                        });
                        return true;
                    }
                });
                helper.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityBuilder.startMemberCardStateActivity(MemberCardListActivity.this, item.getState(), item.getState_label());
                    }
                });
            }
        });
    }

    @OnClick(R.id.tv_add_card)
    void clickAddCard(View v){
        ActivityBuilder.startAddBlankCardActivity(this);
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
        if(memberCardList != null) {
            cardList.addAll(memberCardList);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelSuccess(MemberCardBean card) {
        cardList.remove(card);
        adapter.notifyDataSetChanged();

        DialogUtil.createAlertDialog(this, "银行卡"+"****" + (card.getCard_no().length() > 4 ? card.getCard_no().substring(card.getCard_no().length() - 4) : card.getCard_no())+"已删除成功", "我知道了");
    }

    @Override
    protected void onDestroy() {
        if(presenter != null){
            presenter.onDestroy();
        }
        super.onDestroy();
    }

}
