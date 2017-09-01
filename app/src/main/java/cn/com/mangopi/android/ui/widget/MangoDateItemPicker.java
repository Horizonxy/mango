package cn.com.mangopi.android.ui.widget;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MangoDateItemPicker extends MangoItemPicker {
	private final PickerView mPickerViewYear;
	private final PickerView mPickerViewMonth;
	private final PickerView mPickerViewDay;
	private final List<String> listYear = new ArrayList<String>();
	private final List<String> listMonth = new ArrayList<String>();
	private final List<String> listDay = new ArrayList<String>();
	private boolean isCreate = false;

	public MangoDateItemPicker(Context context, int year, int month, int date) {
		super(context);
		Calendar calendar = Calendar.getInstance();
		int nowy = calendar.get(Calendar.YEAR);
		for (int i = 1940 ; i <= nowy; i++) {
			listYear.add(String.valueOf(i));
		}
		for (int i = 1; i <= 12; i++) {
			listMonth.add(String.valueOf(i));
		}
		int days = getMonthDays(year, month);
		for (int i = 1; i <= days; i++) {
			listDay.add(i + "");
		}
		mPickerViewYear = addPicker();
		mPickerViewMonth = addPicker();
		mPickerViewDay = addPicker();
		mPickerViewYear.setData(listYear);
		mPickerViewMonth.setData(listMonth);
		mPickerViewDay.setData(listDay);
		setYear(year + "");
		setMonth(month + "");
		setDay(date + "");
		isCreate = true;
	}

	@Override
	protected void onItemValueChanged(PickerView picker, int valueIndex) {
		if ((picker == mPickerViewYear || picker == mPickerViewMonth) && isCreate) {
			if (mPickerViewYear.getCurrentValue() != null && mPickerViewMonth.getCurrentValue() != null) {
				listDay.clear();
				int month = Integer.parseInt(mPickerViewMonth.getCurrentValue());
				int year = Integer.parseInt(mPickerViewYear.getCurrentValue());
				int days = getMonthDays(year, month);
				for (int i = 1; i <= days; i++) {
					listDay.add(i + "");
				}
				mPickerViewDay.setData(listDay);
			}
		}
	}

	private void setYear(String year) {
		if (listYear != null) {
			for (int i = 0; i < listYear.size(); i++) {
				if (listYear.get(i).equals(year)) {
					mPickerViewYear.setCurrent(i);
					break;
				}
			}
		}
	}

	private void setMonth(String month) {
		if (listMonth != null) {
			for (int i = 0; i < listMonth.size(); i++) {
				if (listMonth.get(i).equals(month)) {
					mPickerViewMonth.setCurrent(i);
					break;
				}
			}
		}
	}

	private int getMonthDays(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);

		return cal.getActualMaximum(Calendar.DATE);
	}

	private void setDay(String day) {
		if (listDay != null) {
			for (int i = 0; i < listDay.size(); i++) {
				if (listDay.get(i).equals(day)) {
					mPickerViewDay.setCurrent(i);
					break;
				}
			}
		}
	}

	public String getYear() {
		return mPickerViewYear == null ? null : mPickerViewYear.getCurrentValue();
	}

	public String getMonth() {
		return mPickerViewMonth == null ? null : mPickerViewMonth.getCurrentValue();
	}

	public String getDay() {
		return mPickerViewDay == null ? null : mPickerViewDay.getCurrentValue();
	}

	private String formedString(String value) {
		if (value != null) {
			if (value.length() < 2) {
				value = "0" + value;
			}
		}
		return value;
	}

	public String getDate() {
		StringBuilder sb = new StringBuilder();
		sb.append(getYear());
		sb.append("-");
		sb.append(formedString(getMonth()));
		sb.append("-");
		sb.append(formedString(getDay()));
		return sb.toString();
	}
}
