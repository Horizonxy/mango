package cn.com.mangopi.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.mcxiaoke.bus.Bus;

import butterknife.Bind;
import cn.com.mangopi.android.Constants;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.widget.TitleBar;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.BusEvent;
import rx.functions.Action1;

public class InputMessageActivity extends BaseTitleBarActivity implements TitleBar.OnTitleBarClickListener{

    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_num)
    TextView tvNum;

    String title;
    String right;
    String type;
    int limitNum;
    String content;
    boolean must;
    /**
     * 1、数字
     * 2、字母
     * 3、字母+数字
     */
    int inputType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_message);

        Intent intent = getIntent();
        title = intent.getStringExtra(Constants.BUNDLE_TITLE);
        right = intent.getStringExtra(Constants.BUNDLE_RIGHT_TEXT);
        limitNum = intent.getIntExtra(Constants.BUNDLE_LIMIT_NUM, 0);
        type = intent.getStringExtra(Constants.BUNDLE_TYPE);
        content = intent.getStringExtra(Constants.BUNDLE_CONTENT);
        must = intent.getBooleanExtra(Constants.BUNDLE_MUST, true);
        inputType = intent.getIntExtra(Constants.BUNDLE_INPUT_TYPE, -1);

        initView();
    }

    private void initView() {
        titleBar.setOnTitleBarClickListener(this);
        titleBar.setTitle(title);
        titleBar.setRightText(right);
        titleBar.setRightColor(R.color.color_ffb900);

        InputFilter[] filters = {new InputFilter.LengthFilter(limitNum)};
        etContent.setFilters(filters);
        tvNum.setText(String.valueOf(limitNum));

        if(inputType > 0){
            if(inputType == 1){
                etContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            } else if(inputType == 2){
                etContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            } else if(inputType == 3){
                etContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            }
        }

        RxTextView.textChanges(etContent).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                tvNum.setText(String.valueOf(limitNum - charSequence.toString().length()));
            }
        });
        etContent.setText(content);
        etContent.setSelection(etContent.getText().toString().length());
    }

    @Override
    public void onTitleButtonClick(View view) {
        switch (view.getId()){
            case R.id.tv_right:
                if(must && TextUtils.isEmpty(etContent.getText().toString())){
                    AppUtils.showToast(this, "请输入内容");
                    return;
                }
                BusEvent.InputEvent event = new BusEvent.InputEvent();
                event.setType(type);
                event.setContent(etContent.getText().toString());
                Bus.getDefault().postSticky(event);
                finish();
                break;
        }
    }

}
