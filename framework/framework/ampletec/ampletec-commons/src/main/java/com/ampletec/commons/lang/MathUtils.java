package com.ampletec.commons.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class MathUtils {
	private static DecimalFormat df = new DecimalFormat("#.##");
	private static DecimalFormat df2 = new DecimalFormat("0.00");

	public static double formatFront(double dv) {
		String st = df.format(dv);
		return Double.parseDouble(st);
	}

	public static String formatKeepTwo(double dv) {
		return df2.format(dv);
	}

	public static double sum(Double[] da) {
		double ret = 0d;
		for (double d : da) {
			// ret = MathUtils.add(d, ret);
			ret += d;
		}
		return ret;
	}

	public static double sum(List<Double> lt) {
		double ret = 0d;
		for (double d : lt) {
			ret += d;
		}
		return ret;
	}

	public static double add(double d1, double d2) { // 进行加法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	public static double sub(double d1, double d2) { // 进行减法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double d1, double d2) { // 进行乘法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double d1, double d2, int len) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double round(double d, int len) { // 进行四舍五入
		// 操作
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，
		// 表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double toomit(double d, int decimals) {
		String vl = String.format("%." + (decimals + 1) + "f", d);
		if (vl.indexOf(".") > 0) {
			decimals += 1;
			int end = vl.indexOf(".") + decimals;
			end = end > vl.length() ? vl.length() : end;
			vl = vl.substring(0, end);
		}
		return Double.valueOf(vl);
	}
	
	public static int sumOfEveryNumeric(long num) {
		String numStr = String.valueOf(num);
		int sum = 0;
		for(int i=0; i < numStr.length(); i ++) {
			sum += Integer.valueOf(String.valueOf(numStr.charAt(i)));
		}
		return sum;
	}

	// if no_same_points && max()-min()<3
	public static boolean isStraight(int a, int b, int c) {
		return isMixed(a, b, c) && Math.max(c, Math.max(a, b)) - Math.min(c, Math.min(a, b)) < 3;
	}

	public static boolean isMixed(int a, int b, int c) {
		return a != b && b != c && a != c;
	}

	public static boolean existSame(int a, int b, int c) {
		return a == b || b == c || a == c;
	}

	/**
	 * 转换牌
	 * 
	 * @param index
	 *            1、2、3、4……13、14、15、16……50、51、52
	 * @return 点数：point
	 */
	public static Integer indexToValue(int index) {
		if (index == 0)
			return 0;
		return (int) Math.ceil((index - 1) / 4) + 1;
	}

	public static Integer indexToFlower(int index) {
		return (index % 4) == 0 ? 4 : (index % 4);
	}

	public static void main(String[] args) {
		System.out.println(MathUtils.sumOfEveryNumeric(326447882L));
		double t = MathUtils.round(198.0001 * 9.7 - 198, 3);
		System.out.println(t);
		System.out.println(MathUtils.toomit(t, 2));
		String result = String.format("%." + 2 + "f", t);
		System.out.println(result);

		System.out.println(MathUtils.toomit(12.002, 2));
		System.out.println(MathUtils.toomit(12.102, 2));
		System.out.println(MathUtils.toomit(12.192, 2));
		System.out.println(MathUtils.toomit(12.199, 2));
		
		System.out.println(MathUtils.round(10.195122, 3));
	}
}
