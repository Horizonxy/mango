package cn.com.mangopi.android.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.presenter.WorkProjectCommentPresenter;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.dialog.ListViewDialog;
import cn.com.mangopi.android.ui.viewlistener.WorkProjectCommentListener;
import cn.com.mangopi.android.util.AppUtils;
import rx.functions.Action1;

public class WorkProjectCommentActivity extends BaseTitleBarActivity implements WorkProjectCommentListener {

    @Bind(R.id.tv_tutor_comment)
    TextView tvToturComment;
    @Bind(R.id.layout_score)
    LinearLayout layoutScore;
    @Bind(R.id.tv_score)
    TextView tvScore;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_content_count)
    TextView tvContentCount;
    @Bind(R.id.btn_submit_comment)
    Button btnSubmitComment;
    List<String> scores = new ArrayList<String>();
    long actorId;
    WorkProjectCommentPresenter commentPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_project_comment);

        initView();
        commentPresenter = new WorkProjectCommentPresenter(this);
    }

    private void initView() {
        titleBar.setTitle(R.string.work_project_comment);

        RxTextView.textChanges(etContent).subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                tvContentCount.setText(String.format(getString(R.string.input_num), String.valueOf(100 - charSequence.toString().length())));
            }
        });

        int[] scoreArr = getResources().getIntArray(R.array.score);
        for (int i = 0; scoreArr != null && i < scoreArr.length; i++){
            scores.add(String.valueOf(scoreArr[i]));
        }
        String initScore = scores.get(scores.size() - 1);
        tvScore.setText(initScore);
        tvScore.setTag(initScore);
    }

    @OnClick(R.id.layout_score)
    void layoutScoreClicked(View v){
        ListViewDialog<String> scoreListDialog = new ListViewDialog<String>("选择评分", scores) {
            @Override
            public void fiddData(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_text, item);
            }
        };
        scoreListDialog.setOnListViewListener(new ListViewDialog.OnListViewListener<String>() {
            @Override
            public void onItemClicked(String data) {
                tvScore.setText(data);
                tvScore.setTag(data);
            }
        });
        scoreListDialog.show(this, (String) tvScore.getTag());
    }

    @OnClick(R.id.btn_submit_comment)
    void submitCommentClicked(View v){
        if(TextUtils.isEmpty(etContent.getText())){
            AppUtils.showToast(this, "请输入点评内容");
            return;
        }
        commentPresenter.actorComment();
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
    public long getActorId() {
        return actorId;
    }

    @Override
    public int getScore() {
        return Integer.parseInt((String) tvScore.getText());
    }

    @Override
    public String getComment() {
        return etContent.getText().toString();
    }

    @Override
    public void onCommentSuccess() {
        finish();
    }

    @Override
    protected void onDestroy() {
        if(commentPresenter != null){
            commentPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
