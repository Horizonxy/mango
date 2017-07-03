package com.mango.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mango.R;
import com.mango.util.BusEvent;
import com.mcxiaoke.bus.Bus;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

public class SetNickNameActivity extends BaseTitleBarActivity {

    @Bind(R.id.btn_finish)
    Button btnFinish;
    @Bind(R.id.et_nick_name)
    EditText etNickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nick_name);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.set_nickname_title);

        RxView.clicks(btnFinish).throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startActivity(new Intent(SetNickNameActivity.this, MainActivity.class));
                BusEvent.ActivityFinishEvent event = new BusEvent.ActivityFinishEvent();
                event.setFinish(true);
                Bus.getDefault().post(event);
                finish();
            }
        });

        RxTextView.textChanges(etNickName).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                btnFinish.setEnabled(etNickName.getText().length() > 0);
            }
        });
    }
}
