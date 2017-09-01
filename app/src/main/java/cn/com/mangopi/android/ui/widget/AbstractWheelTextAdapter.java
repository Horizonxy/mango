/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cn.com.mangopi.android.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import cn.com.mangopi.android.R;


/**
 * Abstract wheel adapter provides common functionality for adapters.
 */
public abstract class AbstractWheelTextAdapter extends AbstractWheelAdapter {

    /** Text view resource. Used as a default view for adapter. */
    public static final int TEXT_VIEW_ITEM_RESOURCE = -1;

    /** No resource constant. */
    protected static final int NO_RESOURCE = 0;

    /** Default text color */
    public static final int DEFAULT_TEXT_COLOR = 0xFFB0B0B0;
    public static final int CHOOSE_TEXT_COLOR =  0xFF202020;

    /** Default text color */
    public static final int LABEL_COLOR = 0xFF700070;

    /** Default text size */
    public static final int DEFAULT_TEXT_SIZE = 24;
    public static final int CHOOSE_TEXT_SIZE = 24;

    // Text settings
    private int textColor = CHOOSE_TEXT_COLOR;
    private int textSize = CHOOSE_TEXT_SIZE;
    private int defaultTextColor = DEFAULT_TEXT_COLOR;
    private int defaultTextSize = DEFAULT_TEXT_SIZE;

    // Current context
    protected Context context;
    // Layout inflater
    protected LayoutInflater inflater;

    // Items resources
    protected int itemResourceId;
    protected int itemTextResourceId;

    // Empty items resources
    protected int emptyItemResourceId;

    protected HashMap<Integer, View> convertViews = new HashMap<Integer, View>();

    // 当前选中的item
	private int currentItem;

    /**
     * Constructor
     * @param context the current context
     */
    protected AbstractWheelTextAdapter(Context context) {
        this(context, TEXT_VIEW_ITEM_RESOURCE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param itemResource the resource ID for a layout file containing a TextView to use when instantiating items views
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource) {
        this(context, itemResource, NO_RESOURCE);
    }

    /**
     * Constructor
     * @param context the current context
     * @param itemResource the resource ID for a layout file containing a TextView to use when instantiating items views
     * @param itemTextResource the resource ID for a text view in the item layout
     */
    protected AbstractWheelTextAdapter(Context context, int itemResource, int itemTextResource) {
        this.context = context;
        itemResourceId = itemResource;
        itemTextResourceId = itemTextResource;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Gets text color
     * @return the text color
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * Sets text color
     * @param textColor the text color to set
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * Gets text size
     * @return the text size
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     * Sets text size
     * @param textSize the text size to set
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getDefaultTextColor() {
		return defaultTextColor;
	}

	public void setDefaultTextColor(int defaultTextColor) {
		this.defaultTextColor = defaultTextColor;
	}

	public int getDefaultTextSize() {
		return defaultTextSize;
	}

	public void setDefaultTextSize(int defaultTextSize) {
		this.defaultTextSize = defaultTextSize;
	}

	/**
     * Gets resource Id for items views
     * @return the item resource Id
     */
    public int getItemResource() {
        return itemResourceId;
    }

    /**
     * Sets resource Id for items views
     * @param itemResourceId the resource Id to set
     */
    public void setItemResource(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    /**
     * Gets resource Id for text view in item layout
     * @return the item text resource Id
     */
    public int getItemTextResource() {
        return itemTextResourceId;
    }

    /**
     * Sets resource Id for text view in item layout
     * @param itemTextResourceId the item text resource Id to set
     */
    public void setItemTextResource(int itemTextResourceId) {
        this.itemTextResourceId = itemTextResourceId;
    }

    /**
     * Gets resource Id for empty items views
     * @return the empty item resource Id
     */
    public int getEmptyItemResource() {
        return emptyItemResourceId;
    }

    /**
     * Sets resource Id for empty items views
     * @param emptyItemResourceId the empty item resource Id to set
     */
    public void setEmptyItemResource(int emptyItemResourceId) {
        this.emptyItemResourceId = emptyItemResourceId;
    }


    /**
     * Returns text for specified item
     * @param index the item index
     * @return the text of specified items
     */
    protected abstract CharSequence getItemText(int index);

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (index >= 0 && index < getItemsCount()) {
        	convertView = convertViews.get(index);
            if (convertView == null) {
                convertView = getView(itemResourceId, parent);
                convertViews.put(index, convertView);
            }
            TextView textView = getTextView(convertView, itemTextResourceId);
            if (textView != null) {
                CharSequence text = getItemText(index);
                if (text == null) {
                    text = "";
                }
                textView.setText(text);

                if (itemResourceId == TEXT_VIEW_ITEM_RESOURCE) {
                    configureTextView(textView, index);
                }
            }
            return convertView;
        }
    	return null;
    }

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getView(emptyItemResourceId, parent);
        }
        if (emptyItemResourceId == TEXT_VIEW_ITEM_RESOURCE && convertView instanceof TextView) {
            configureTextView((TextView)convertView, -1);
        }

        return convertView;
	}

    /**
     * Configures text view. Is called for the TEXT_VIEW_ITEM_RESOURCE views.
     * @param view the text view to be configured
     */
    protected void configureTextView(TextView view, int index) {
		if (index == currentItem) {
			view.setTextColor(textColor);
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		} else {
			view.setTextColor(defaultTextColor);
			view.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize);
		}
        view.setGravity(Gravity.CENTER);
        int padding_top_px = view.getResources().getDimensionPixelSize(R.dimen.message_dialog_picker_item_padding_top);
        int padding_bottom_px = view.getResources().getDimensionPixelSize(R.dimen.message_dialog_picker_item_padding_bottom);
        view.setPadding(0, padding_top_px, 0, padding_bottom_px);
        view.setLines(1);
        view.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
    }

    /**
     * Loads a text view from view
     * @param view the text view or layout containing it
     * @param textResource the text resource Id in layout
     * @return the loaded text view
     */
    private TextView getTextView(View view, int textResource) {
    	TextView text = null;
    	try {
            if (textResource == NO_RESOURCE && view instanceof TextView) {
                text = (TextView) view;
            } else if (textResource != NO_RESOURCE) {
                text = (TextView) view.findViewById(textResource);
            }
        } catch (ClassCastException e) {
            Log.e("AbstractWheelAdapter", "You must supply a resource ID for a TextView");
            throw new IllegalStateException(
                    "AbstractWheelAdapter requires the resource ID to be a TextView", e);
        }

        return text;
    }

    /**
     * Loads view from resources
     * @param resource the resource Id
     * @return the loaded view or null if resource is not set
     */
    private View getView(int resource, ViewGroup parent) {
        switch (resource) {
        case NO_RESOURCE:
            return null;
        case TEXT_VIEW_ITEM_RESOURCE:
            return new TextView(context);
        default:
            return inflater.inflate(resource, parent, false);
        }
    }

	/**
	 * 获取到某一个具体的TextView，方便对其样式进行修改
	 *
	 * @param index
	 *            位置
	 * @return
	 */
	public TextView getTextViewByIndex(int index) {
		View convertView = convertViews.get(index);
		return getTextView(convertView, itemTextResourceId);
	}

	/**
	 * 设置当前选中条目
	 *
	 * @param currentItem
	 */
	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}
}
