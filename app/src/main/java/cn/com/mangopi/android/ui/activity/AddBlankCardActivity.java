package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.model.data.MemberModel;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.AddBlankCardListener;
import cn.com.mangopi.android.ui.widget.AddSpaceTextWatcher;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;

public class AddBlankCardActivity extends BaseTitleBarActivity implements AddBlankCardListener {

    @Bind(R.id.et_blank_name)
    EditText etBlankName;
    @Bind(R.id.et_card_no)
    EditText etCardNo;
    MemberPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blank_card);

        initView();
        presenter = new MemberPresenter(new MemberModel(), this);
    }

    private void initView() {
        titleBar.setTitle(R.string.add_blank_card);

        new AddSpaceTextWatcher(etCardNo,48).setSpaceType(AddSpaceTextWatcher.SpaceType.bankCardNumberType);
    }

    @OnClick(R.id.btn_add)
    void addCardClick(View v){
        if(TextUtils.isEmpty(etBlankName.getText())){
            AppUtils.showToast(this, "请输入银行名称");
            return;
        }
        if(TextUtils.isEmpty(etCardNo.getText().toString().replaceAll(" ", ""))){
            AppUtils.showToast(this, "请输入银行卡号");
            return;
        }
        presenter.addBlankCard();
    }

    @Override
    public void onSuccess() {
        ActivityBuilder.startCardListActivity(this, null);
        finish();
    }

    @Override
    public String getBlankName() {
        return etBlankName.getText().toString();
    }

    @Override
    public String getCardNo() {
        return etCardNo.getText().toString().replaceAll(" ", "");
    }

    @Override
    public void onFailure(String message) {
        AppUtils.showToast(this, message);
    }

    @Override
    public Context currentContext() {
        return this;
    }
}
