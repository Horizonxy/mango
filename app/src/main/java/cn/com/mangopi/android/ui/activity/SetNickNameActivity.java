package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.di.component.DaggerSetNickNameActivityComponent;
import cn.com.mangopi.android.di.module.SetNickNameActivityModule;
import cn.com.mangopi.android.presenter.MemberPresenter;
import cn.com.mangopi.android.ui.viewlistener.SetNickNameListener;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import rx.functions.Action1;

public class SetNickNameActivity extends BaseTitleBarActivity implements SetNickNameListener {

    @Bind(R.id.btn_finish)
    Button btnFinish;
    @Bind(R.id.et_nick_name)
    EditText etNickName;
    @Inject
    MemberPresenter presenter;
    @Bind(R.id.iv_female)
    ImageView ivFemale;
    @Bind(R.id.iv_man)
    ImageView ivMan;
    int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);
        DaggerSetNickNameActivityComponent.builder().setNickNameActivityModule(new SetNickNameActivityModule(this)).build().inject(this);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.set_nickname_title);

        RxView.clicks(btnFinish).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.updateMember();
            }
        });

        RxTextView.textChanges(etNickName).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnFinish.setEnabled(etNickName.getText().length() > 0);
            }
        });
    }

    @OnClick(R.id.iv_female)
    void onClickFemale(View v){
        ivFemale.setImageResource(R.drawable.user_pic1_s);
        ivMan.setImageResource(R.drawable.user_pic2);
        gender = 0;
    }

    @OnClick(R.id.iv_man)
    void onClickMan(View v){
        ivFemale.setImageResource(R.drawable.user_pic1);
        ivMan.setImageResource(R.drawable.user_pic2_s);
        gender = 1;
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
    public void onSuccess() {
        ActivityBuilder.startMainActivity(this);
    }

    @Override
    public String getNickName() {
        return etNickName.getText().toString();
    }

    @Override
    public int getGender() {
        return gender;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
