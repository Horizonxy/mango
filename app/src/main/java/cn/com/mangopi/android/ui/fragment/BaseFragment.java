package cn.com.mangopi.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;

import cn.com.mangopi.android.BuildConfig;

public abstract class BaseFragment extends Fragment {

    View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(root == null){
            root = inflater.inflate(getLayoutId(), container, false);
            findView(root);
            initView();
        }
        return root;
    }

    abstract void findView(View root);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != root) {
            ((ViewGroup) root.getParent()).removeView(root);
        }
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
