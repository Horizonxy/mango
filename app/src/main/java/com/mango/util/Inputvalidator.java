package com.mango.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 输入效验类
 */
public final class Inputvalidator {

	private static final String[] prefix = { "134", "135", "136", "137", "138",
			"139", "150", "151", "152", "157", "158", "159", "187", "188",
			"130", "131", "132", "155", "156", "185", "186", "180", "189",
			"133", "153", "182" };

	public static boolean isMobiePhoneNum(String num) {
		if (num == null || "".equals(num)) {
			return false;
		}
		if (isNumeric(num) && num.length() == 11) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isBetweenPwd(String pwd){
		if (pwd.length()>=6&&pwd.length()<=16) {
			return true;
		}
		return false;
	}

	/**
	 * 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，
	 * 三位数字顺序码和一位数字校验码。
	 * 
	 * 2、地址码(前六位数） 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。
	 * 
	 * 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 * 
	 * 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性。
	 * 
	 * 5、校验码（第十八位数） （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16
	 * ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6
	 * 3 7 9 10 5 8 4 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7
	 * 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
	 * 
	 * 所以我们就可以大致写一个函数来校验是否正确了。
	 * */

	/**
	 * ======================================================================
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：true 无效：false
	 * @throws ParseException
	 */
	public static boolean IDCardValidate(String IDStr) {
		try {
			String errorInfo = "";// 记录错误信息
			String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5",
					"4", "3", "2" }; // 18位身份证中最后一位校验码
			// 18位身份证中，各个数字的生成校验码时的权值
			String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3",
					"7", "9", "10", "5", "8", "4", "2" };
			// String[] Checker = {"1","9","8","7","6","5","4","3","2","1","1"};
			String Ai = "";

			// ================ 号码的长度 15位或18位 ================
			if (IDStr.length() != 15 && IDStr.length() != 18) {
				errorInfo = "号码长度应该为15位或18位。";
				LogUtils.i(errorInfo);
				return false;
			}
			// =======================(end)========================

			// ================ 数字 除最后以为都为数字 ================
			if (IDStr.length() == 18) {
				Ai = IDStr.substring(0, 17);
			} else if (IDStr.length() == 15) {
				Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
			}
			if (isNumeric(Ai) == false) {
				errorInfo = "15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
				LogUtils.i(errorInfo);
				return false;
			}
			// =======================(end)========================

			// ================ 出生年月是否有效 ================
			String strYear = Ai.substring(6, 10);// 年份
			String strMonth = Ai.substring(10, 12);// 月份
			String strDay = Ai.substring(12, 14);// 日子

			if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
				errorInfo = "生日无效。";
				LogUtils.i(errorInfo);
				return false;
			}

			GregorianCalendar gc = new GregorianCalendar();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				errorInfo = "生日不在有效范围。";
				LogUtils.i(errorInfo);
				return false;
			}
			if (Integer.parseInt(strMonth) > 12
					|| Integer.parseInt(strMonth) == 0) {
				errorInfo = "月份无效";
				LogUtils.i(errorInfo);
				return false;
			}
			if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
				errorInfo = "日期无效";
				LogUtils.i(errorInfo);
				return false;
			}
			// =====================(end)=====================

			// ================ 地区码时候有效 ================
			Map<String, String> h = GetAreaCode();
			if (h.get(Ai.substring(0, 2)) == null) {
				errorInfo = "地区编码错误。";
				LogUtils.i(errorInfo);
				return false;
			}
			// ==============================================

			// ================ 判断最后一位的值 ================
			int TotalmulAiWi = 0;
			for (int i = 0; i < 17; i++) {
				TotalmulAiWi = TotalmulAiWi
						+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
						* Integer.parseInt(Wi[i]);
			}
			int modValue = TotalmulAiWi % 11;
			String strVerifyCode = ValCodeArr[modValue];
			Ai = Ai + strVerifyCode;

			if (IDStr.length() == 18) {
				if (Ai.equals(IDStr) == false) {
					errorInfo = "身份证无效，最后一位字母错误";
					LogUtils.i(errorInfo);
					return false;
				}
			} else {
				LogUtils.i("所在地区:"
						+ h.get(Ai.substring(0, 2).toString()));
				LogUtils.i("新身份证号:" + Ai);
				return true;
			}
			// =====================(end)=====================
			LogUtils.i("所在地区:" + h.get(Ai.substring(0, 2).toString()));
			return true;
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ======================================================================
	 * 功能：设置地区编码
	 * 
	 * @return Map 对象
	 */
	private static Map<String, String> GetAreaCode() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("11", "北京");
		map.put("12", "天津");
		map.put("13", "河北");
		map.put("14", "山西");
		map.put("15", "内蒙古");
		map.put("21", "辽宁");
		map.put("22", "吉林");
		map.put("23", "黑龙江");
		map.put("31", "上海");
		map.put("32", "江苏");
		map.put("33", "浙江");
		map.put("34", "安徽");
		map.put("35", "福建");
		map.put("36", "江西");
		map.put("37", "山东");
		map.put("41", "河南");
		map.put("42", "湖北");
		map.put("43", "湖南");
		map.put("44", "广东");
		map.put("45", "广西");
		map.put("46", "海南");
		map.put("50", "重庆");
		map.put("51", "四川");
		map.put("52", "贵州");
		map.put("53", "云南");
		map.put("54", "西藏");
		map.put("61", "陕西");
		map.put("62", "甘肃");
		map.put("63", "青海");
		map.put("64", "宁夏");
		map.put("65", "新疆");
		map.put("71", "台湾");
		map.put("81", "香港");
		map.put("82", "澳门");
		map.put("91", "国外");
		return map;
	}

	/**
	 * 
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param strDate
	 * @return
	 */
	private static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]"
						+ "?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])"
						+ "|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])"
						+ "|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])"
						+ "|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])"
						+ "|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))"
						+ "|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])"
						+ "|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检验EditText只输入中文、英文、数字和下划线
	 * 
	 * @param value
	 * @return
	 */
	public static String checkEdittext(String value) {
		String vehicleNoStyle = "^[\u4E00-\u9FA5A-Za-z0-9_]+$";
		Pattern pattern = Pattern.compile(vehicleNoStyle);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			return "0";// 验证格式不正确返回0
		} else {
			return "1";// 验证格式正确返回1
		}
	}

	/**
	 * 校验手机号码
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkPhone(String value) {
		String vString = "^1[3|4|5|7|8][0-9]{9}$";
		Pattern pattern = Pattern.compile(vString);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 校验车牌号
	 * 
	 * @param value
	 * @return
	 */
	public static boolean checkCarPalte(String value) {
		String vString = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
		Pattern pattern = Pattern.compile(vString);
		Matcher matcher = pattern.matcher(value);
		if (!matcher.matches()) {
			return false;
		} else {
			return true;
		}
	}
}
