package com.mango.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.mango.BuildConfig;

import butterknife.ButterKnife;

/**
 * @author 蒋先明
 * @date 2017/6/30
 */

public abstract class BaseFragment extends Fragment {

    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root == null){
            root = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, root);
            initView();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    abstract void initView();

    abstract int getLayoutId();

    @Override
    public void onPause() {
        super.onPause();
        if (!BuildConfig.DEBUG) {
            String pageName=this.getClass().getSimpleName();
            StatService.onPageEnd(getActivity(),pageName);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!BuildConfig.DEBUG) {
            String pageName=this.getClass().getSimpleName();
            StatService.onPageStart(getActivity(),pageName);
        }
    }
}
