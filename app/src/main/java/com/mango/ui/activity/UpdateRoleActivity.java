package com.mango.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.mango.Application;
import com.mango.R;

import butterknife.Bind;

public class UpdateRoleActivity extends BaseTitleBarActivity {

    @Bind(R.id.tv_current_role)
    TextView tvCurrentRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_role);

        initView();
    }

    private void initView() {
        titleBar.setTitle(R.string.update_my_role);

        tvCurrentRole.setText(String.format(getString(R.string.my_current_role),
                Application.application.getMember().getUser_identity_label()));
    }
}
