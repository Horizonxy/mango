package com.mango.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mango.R;
import com.mango.di.component.DaggerSetNickNameActivityComponent;
import com.mango.di.module.SetNickNameActivityModule;
import com.mango.presenter.MemberPresenter;
import com.mango.ui.viewlistener.SetNickNameListener;
import com.mango.util.ActivityBuilder;
import com.mango.util.AppUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.functions.Action1;

public class SetNickNameActivity extends BaseTitleBarActivity implements SetNickNameListener {

    @Bind(R.id.btn_finish)
    Button btnFinish;
    @Bind(R.id.et_nick_name)
    EditText etNickName;
    @Inject
    MemberPresenter presenter;

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
        return 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null) {
            presenter.onDestroy();
        }
    }
}
