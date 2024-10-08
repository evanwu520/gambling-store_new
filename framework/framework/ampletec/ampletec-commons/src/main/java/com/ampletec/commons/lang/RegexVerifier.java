package com.ampletec.commons.lang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexVerifier {

	public static boolean isEmail(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match(regex, str);
	}

	public static boolean isIPAddress(String str) {
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num
				+ "$";
		return match(regex, str);
	}

	public static boolean isURL(String str) {
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}
	
	public static boolean isImage(String imageUrl) {
		String reg = ".+(.JPEG|.jpeg|.JPG|.jpg)$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(imageUrl);
		return matcher.find();
	}

	public static boolean isTelephone(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		String result;
		String regex = "^[0-9]+?[0-9-()]*[0-9()]+?$|^[0-9]+?$|^$";
		String regex1 = "\\-{2,}";
		String regex2 = "\\({2,}";
		String regex3 = "\\){2,}";
		result = str.replaceAll(regex1, " ");
		result = result.replaceAll(regex2, " ");
		result = result.replaceAll(regex3, " ");
		// String regex = "^\\d{1,18}$";
		return match(regex, str) && str.length() == result.length();
	}

	/**
	 * 验证是否合法手机号
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		boolean flag = m.matches();
		System.out.println("----------------------------->>>StringUtils is mobileno=" + flag);
		return flag;
	}

	public static String transactSQLInjection(String str) {
		String ret = str.replaceAll(".*([';]+|(--)+).*", "");
		return ret.replaceAll(" ", "");
	}

	public static boolean isEnglishAndNumber(String value) {
		return value.matches("^[a-zA-z]+[A-Za-z0-9_]{5,11}$");
	}

	public static boolean rightIdentity(String value, int min, int max) {
		return value.matches("^[a-zA-z]+[A-Za-z0-9_]{"+min+","+max+"}$");
	}

	public static boolean isPassword(String str) {
		String regex = "[A-Za-z]+[0-9]";
		return match(regex, str);
	}

	public static boolean isPasswLength(String str) {
		String regex = "^.{6,20}$";
		return match(regex, str);
	}

	public static boolean isPostalcode(String str) {
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	public static boolean isHandset(String str) {
		String regex = "^[0,1]+[3,5]+\\d{9}$";
		// String regex = "^\\d{1,18}$";
		return match(regex, str);
	}

	public static boolean isIDcard(String str) {
		String regex = "(^\\d{18}$)|(^\\d{15}$)";
		return match(regex, str);
	}

	public static boolean isDecimal(String str) {
		String regex = "^[\\-\\+]?[0123456789]+(.[0123456789]*)?$";
		if (match(regex, str)) {
			try {
				Double.parseDouble(str);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static boolean isSignlessDecimal(String str) {
		String regex = "^[\\+]?[0-9]+(.[0-9]*)?$";
		return match(regex, str);
	}

	public static boolean isDecimalLength(String str, int intlen, int digitallen) {
		str = str.replace("[\\-\\+]", "");
		String[] strs = str.split("\\.");
		if (strs.length > 1) {
			if (digitallen < 0) {
				return !(strs[0].length() > intlen);
			} else {
				return !(strs[0].length() > intlen || strs[1].length() > digitallen);
			}
		} else {
			return !(strs[0].length() > intlen);
		}
	}

	public static boolean isMonth(String str) {
		String regex = "^(0?[[1-9]|1[0-2])$";
		return match(regex, str);
	}

	public static boolean isBeforeToday(String str) {
		Date nowdate = new Date();
		Date d;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			d = sdf.parse(str);
			cal.setTime(d);
			cal.add(Calendar.DATE, 1);
			if (nowdate.before(cal.getTime())) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean isDay(String str) {
		String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
		return match(regex, str);
	}

	public static boolean isDate(String str) {
		String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		return match(regex, str);
	}

	public static boolean isNumeric(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	@Deprecated
	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	public static boolean isDigitStrWithSign(String str) {
		if (null == str || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				if (0 < i || ('-' != c && '+' != c))
					return false;
			}
		}
		return true;
	}

	public static boolean isIntNumber(String str) {
		if ("0".equals(str))
			return true;
		String regex = "^[\\-\\+]?[1-9][0-9]*$";
		return match(regex, str);
	}

	public static boolean isUpChar(String str) {
		String regex = "^[A-Z]+$";
		return match(regex, str);
	}

	public static boolean isLowChar(String str) {
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	public static boolean isLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	public static boolean isChinese(String str) {
		String regex = "^[\u4e00-\u9fa5],{0,}$";
		return match(regex, str);
	}

	public static boolean isLength(String str) {
		String regex = "^.{8,}$";
		return match(regex, str);
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.lookingAt();
	}

	public static boolean checkHtmlTag(String str) {
		String regex = "^[a-zA-Z0-9],{0,}$";
		return match(regex, str);
	}

	public static boolean hasCrossScriptRisk(String qString, String regx) {
		if (qString != null) {
			qString = qString.trim();
			Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(qString);
			return m.find();
		}
		return false;
	}

	public static boolean checkString(String qString) {
		String regx = "!|！|@|◎|#|＃|(\\$)|￥|%|％|(\\^)|……|(\\&)|※|(\\*)|×|(\\()|（|(\\))|）|_|——|(\\+)|＋|(\\|)|§ ";
		return hasCrossScriptRisk(qString, regx);
	}

	public static boolean isLength(String str, int minlen, int maxlen) {
		int len = 0;
		if (str != null) {
			len = str.length();
		}
		if (len >= minlen && len <= maxlen) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isRepeat(String[] strs) {
		ArrayList<String> search = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			String str = strs[i];
			if ("".equals(str)) {
				continue;
			}
			int find = Arrays.binarySearch(search.toArray(), str);
			if (find >= 0) {
				return true;
			}
			search.add(str);
		}
		return false;
	}

	public static boolean hasFullText(String str) {
		if (str == null)
			return false;
		String hanzen = str.replaceAll("[\\x00-\\xff]", "**");
		return hanzen.length() == str.length() ? true : false;
	}
}
