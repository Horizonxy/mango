package cn.com.mangopi.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.jakewharton.rxbinding.view.RxView;
import java.util.List;
import java.util.concurrent.TimeUnit;
import cn.com.mangopi.android.R;
import cn.com.mangopi.android.ui.activity.PictureDetailActivity;
import cn.com.mangopi.android.ui.adapter.quickadapter.BaseAdapterHelper;
import cn.com.mangopi.android.ui.adapter.quickadapter.QuickAdapter;
import cn.com.mangopi.android.util.ActivityBuilder;
import cn.com.mangopi.android.util.AppUtils;
import cn.com.mangopi.android.util.FileUtils;
import cn.com.mangopi.android.util.MangoUtils;
import cn.com.mangopi.android.util.SmallPicInfo;
import rx.functions.Action1;
import rx.functions.Func1;

public class ProjectActorPhotoAdapter extends QuickAdapter<String> {

    int width;

    public ProjectActorPhotoAdapter(Context context, int layoutResId, List<String> data, int itemWidth) {
        super(context, layoutResId, data);
        this.width = itemWidth;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, String item) {
        ImageView ivItem = helper.getView(R.id.iv_item);
        AbsListView.LayoutParams ivParams = (AbsListView.LayoutParams) ivItem.getLayoutParams();
        ivParams.width = ivParams.height = width;
        ivItem.setLayoutParams(ivParams);
        String suffix = FileUtils.getMIMEType(item);
        if(FileUtils.FILE_TYPE_IMAGE.equals(suffix)) {
            helper.setImageUrl(R.id.iv_item, item);
            RxView.clicks(ivItem)
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .map(new Func1<Void, SmallPicInfo>() {
                        @Override
                        public SmallPicInfo call(Void aVoid) {
                            return MangoUtils.getSmallPicInfo(ivItem, item);
                        }
                    })
                    .filter(new Func1<SmallPicInfo, Boolean>() {
                        @Override
                        public Boolean call(SmallPicInfo smallPicInfo) {
                            return smallPicInfo != null;
                        }
                    })
                    .subscribe(new Action1<SmallPicInfo>() {
                        @Override
                        public void call(SmallPicInfo smallPicInfo) {
                            PictureDetailActivity.bmp = ((BitmapDrawable)ivItem.getDrawable()).getBitmap();
                            ActivityBuilder.startPictureDetailActivity((Activity) context, smallPicInfo);
                        }
                    });
        } else {
            ivItem.setImageResource(AppUtils.getResIdBySuffix(item));
            ivItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityBuilder.startSystemBrowser((Activity) context, Uri.parse(item));
                }
            });
        }
    }
}
