package cn.com.mangopi.android.ui.popupwindow;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.Application;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.DisplayUtils;

public class SharePopupWindow extends PopupWindow {

    private Activity aty;
    private List<ShareVo> list;
    private String url, content, title, imageUrl;
    private UMShareListener umShareListener;

    public SharePopupWindow(Activity aty, String url, String content, String title, String imageUrl, UMShareListener umShareListener) {
        super(aty);
        this.aty = aty;
        this.url = url;
        this.content = content;
        this.title = title;
        this.imageUrl = imageUrl;
        this.umShareListener = umShareListener;

        buildList();

        LinearLayout root = (LinearLayout) aty.getLayoutInflater().inflate(R.layout.layout_popupwindow_share, null);
        GridView gridView = (GridView) root.findViewById(R.id.gv_platform);
        gridView.setAdapter(new ShareAdapter());
        gridView.setOnItemClickListener(new ShareItemClickListener());
        TextView tvCancel = (TextView) root.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        setContentView(root);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.PopupWindowBottomInOut);
        setOutsideTouchable(true);
        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(aty.getResources().getColor(R.color.white));
        this.setBackgroundDrawable(dw);
    }

    private void buildList() {
        this.list = new ArrayList<ShareVo>();

        ShareVo wechat = new ShareVo();
        wechat.shareMedia = SHARE_MEDIA.WEIXIN;
        wechat.platform = "微信好友";
        wechat.iconId = R.drawable.icon_share_wx_frient;
        list.add(wechat);

        ShareVo wxMoments = new ShareVo();
        wxMoments.shareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
        wxMoments.platform = "朋友圈";
        wxMoments.iconId = R.drawable.icon_share_wx_moments;
        list.add(wxMoments);

        ShareVo qq = new ShareVo();
        qq.shareMedia = SHARE_MEDIA.QQ;
        qq.platform = "QQ";
        qq.iconId = R.drawable.icon_share_qq;
        list.add(qq);

        ShareVo qzone = new ShareVo();
        qzone.shareMedia = SHARE_MEDIA.QZONE;
        qzone.platform = "QQ空间";
        qzone.iconId = R.drawable.icon_share_qzone;
        list.add(qzone);
    }

    public void show() {
        showAtLocation(aty.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
        DisplayUtils.backgroundAlpha(aty, 0.8f);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        DisplayUtils.backgroundAlpha(aty, 1f);
    }

    class ShareVo {
        SHARE_MEDIA shareMedia;
        String platform;
        int iconId;
    }

    class ShareItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ShareVo shareVo = list.get(position);
            Logger.d("platform: "+shareVo.platform);

            ShareAction shareAction = new ShareAction(aty);
            if(content != null){
                shareAction.withText(content);
            }
            if(title != null){
                shareAction.withTitle(title);
            }
            if(url != null){
                Logger.e("share url: "+url);
                shareAction.withTargetUrl(url);
            }
            if (TextUtils.isEmpty(imageUrl)) {
                shareAction.withMedia(new UMImage(Application.application.getApplicationContext(), R.mipmap.logo));
            } else {
                shareAction.withMedia(new UMImage(Application.application.getApplicationContext(), imageUrl));
            }
            shareAction.setPlatform(shareVo.shareMedia).setCallback(umShareListener).share();

            dismiss();
        }
    }

    class ShareAdapter extends BaseAdapter {

        private Resources res = aty.getResources();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                holder = new Holder();
                convertView = aty.getLayoutInflater().inflate(R.layout.gridview_item_share_content, null);
                holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
                holder.tvPlatform = (TextView) convertView.findViewById(R.id.tv_platform);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            ShareVo shareVo = list.get(position);
            holder.ivIcon.setImageResource(shareVo.iconId);
            holder.tvPlatform.setText(shareVo.platform);
            return convertView;
        }

        class Holder {
            ImageView ivIcon;
            TextView tvPlatform;
        }
    }
}
