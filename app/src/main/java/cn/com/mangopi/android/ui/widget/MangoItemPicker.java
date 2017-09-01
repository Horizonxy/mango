package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.com.mangopi.android.R;
import cn.com.mangopi.android.util.DisplayUtils;

public class MangoItemPicker extends LinearLayout {

    public static interface IPickerViewValueChangedListener {
        void onItemValueChanged(PickerView picker, int valueIndex);
    }

    private IPickerViewValueChangedListener mIPickerViewValueChangedListener;
    private ArrayList<PickerView> mPickerViewList;

    public MangoItemPicker(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    // override this methord to response the event,instead of using listener
    protected void onItemValueChanged(PickerView picker, int valueIndex) {
        if (mIPickerViewValueChangedListener != null) {
            mIPickerViewValueChangedListener.onItemValueChanged(picker, valueIndex);
        }
    }

    public void setmValueChangedListener(IPickerViewValueChangedListener listener) {
        mIPickerViewValueChangedListener = listener;
    }


    public PickerView addPicker() {
        if (mPickerViewList == null) {
            mPickerViewList = new ArrayList<PickerView>();
        }
        PickerView picker = new PickerView(getContext());

        mPickerViewList.add(picker);
        addView(picker);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.weight = mPickerViewList.size();
        for (PickerView view : mPickerViewList) {
            view.setLayoutParams(lp);
        }
        return picker;
    }

    public class PickerView extends LinearLayout implements OnClickListener/*, OnTouchListener*/ {
        private final ImageView mImageButtonPrev, mImageButtonNext;
        // private final TextView mTextViewPrev;
        // private final TextView mTextViewNext;
        // private final TextView mTextViewCurrent;
        private WheelView mWheelView;
        private boolean mEditable = false;
        private int mCurrentIndex = 0;
        private List<String> mDataList;

        public PickerView(Context context) {
            super(context);
            View v = View.inflate(context, R.layout.dialog_item_picker, null);
            addView(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mImageButtonPrev = (ImageView) v.findViewById(R.id.imageButtonPrev);
            mImageButtonPrev.setVisibility(View.GONE);
            // mTextViewPrev = (TextView) v.findViewById(R.id.textViewPrev);
            // mTextViewCurrent = (TextView)
            // v.findViewById(R.id.editTextCurrent);
            // mTextViewNext = (TextView) v.findViewById(R.id.textViewNext);
            mWheelView = (WheelView) v.findViewById(R.id.wheelview);
            mWheelView.setVisibleItems(3);
            mImageButtonNext = (ImageView) v.findViewById(R.id.imageButtonNext);
            mImageButtonNext.setVisibility(View.GONE);
            mImageButtonPrev.setOnClickListener(this);
            // mTextViewPrev.setOnClickListener(this);
            // mTextViewNext.setOnClickListener(this);
            mImageButtonNext.setOnClickListener(this);
            //setOnTouchListener(this);
            mWheelView.addChangingListener(new OnWheelChangedListener() {

                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    setCurrent(wheel.getCurrentItem());
                }
            });
        }

        public void setEditable(boolean edit) {
            mEditable = edit;
        }

        public void setVisibleItems(int visibleItems){
            mWheelView.setVisibleItems(visibleItems);
        }

        public boolean getEdittable() {
            return mEditable;
        }

        public void setData(List<String> list) {
            mDataList = list;
            setCurrent(mCurrentIndex);
            if (list != null && list.size() > 0) {
                String[] array = new String[list.size()];
                list.toArray(array);
                ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this.getContext(), array);
                adapter.setTextColor(getContext().getResources().getColor(R.color.color_333333));
                adapter.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_16));
                adapter.setDefaultTextColor(getContext().getResources().getColor(R.color.color_666666));
                adapter.setDefaultTextSize(getResources().getDimensionPixelSize(R.dimen.sp_16));
                mWheelView.setViewAdapter(adapter);
                mWheelView.setCurrentItem(mCurrentIndex);
            }
        }

        public List<String> getData() {
            return mDataList;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
            case R.id.imageButtonPrev:
                setCurrent(mCurrentIndex - 1);
                break;
            case R.id.imageButtonNext:
                setCurrent(mCurrentIndex + 1);
                break;
            }

        }

        public int getCurrent() {
            return mCurrentIndex;
        }

        public String getCurrentValue() {
            if (mDataList != null && mDataList.size() > mCurrentIndex && mCurrentIndex >= 0) {
                return mDataList.get(mCurrentIndex);
            }
            return null;
        }

        public void setCurrent(int index) {
            if (mDataList != null && mDataList.size() > 0) {
                index = index < 0 ? mDataList.size() - 1 : index;
                index = index >= mDataList.size() ? 0 : index;
//                if (mDataList.size() >= 3) {
                    // int prev = index > 0 ? (index - 1) : (mDataList.size() -
                    // 1);
                    // int next = index < (mDataList.size() - 1) ? (index + 1) :
                    // 0;
                    // mTextViewPrev.setText(mDataList.get(prev));
                    // mTextViewCurrent.setText(mDataList.get(index));
                    // mTextViewNext.setText(mDataList.get(next));
                    mWheelView.setCurrentItem(index);
//                } else {
                    // int prev = index > 0 ? (index - 1) : -1;
                    // int next = index < (mDataList.size() - 1) ? (index + 1) :
                    // -1;
                    // mTextViewPrev.setText(prev == -1 ? "" :
                    // mDataList.get(prev));
                    // mTextViewCurrent.setText(mDataList.get(index));
                    // mTextViewNext.setText(next == -1 ? "" :
                    // mDataList.get(next));
//                    mWheelView.setCurrentItem(index);
//                }

                mCurrentIndex = index;
                onItemValueChanged(this, index);
            } else {
                // mTextViewPrev.setText("");
                // mTextViewCurrent.setText("");
                // mTextViewNext.setText("");
            }
        }

        private final Point mTouchPoint = new Point();
        private final int step = DisplayUtils.dip2px(getContext(), 25);

//        @Override
//        public boolean onTouch(View arg0, MotionEvent arg1) {
//            int x = (int) arg1.getX();
//            int y = (int) arg1.getY();
//            if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
//                mTouchPoint.set(x, y);
//            }
//            if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
//                if (Math.abs(y - mTouchPoint.y) > Math.abs(x - mTouchPoint.x) && Math.abs(y - mTouchPoint.y) > step) {
//                    int direction = y > mTouchPoint.y ? -1 : 1;
//                    setCurrent(mCurrentIndex + direction);
//                    mTouchPoint.set(x, y);
//                }
//            }
//            return true;
//        }
    }

}
