package cn.com.mangopi.android.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<View> vList;

    public ViewPagerAdapter(List<View> vList) {
        this.vList = vList;
    }

    @Override
    public int getCount() {
        return vList == null ? 0 : vList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        view.addView(vList.get(position));
        return vList.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(vList.get(position));
    }
}
