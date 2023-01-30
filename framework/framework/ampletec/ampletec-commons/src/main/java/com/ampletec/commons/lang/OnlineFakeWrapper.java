package com.ampletec.commons.lang;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OnlineFakeWrapper {


	public static final SimpleDateFormat FORMAT_NODAY = new SimpleDateFormat("HH");
	public static final SimpleDateFormat FORMAT_MINUTE = new SimpleDateFormat("mm");

	private static int incrNum(int start, int end, int base, int interval) {
		int c = end - start;
		int p = c * interval / base;
		int ret = start + p;
		return ret;
	}

	public static int getMultFakeLine(int lineNum, double mult) {
		int fnum = getFakeLine(lineNum);
		if (mult > 0)
			fnum = (int) (fnum * mult);
		return fnum;
	}

	public static int getFakeLine(int lineNum) {
		int num = 0;
		String str = FORMAT_NODAY.format(new Date());
		int hour = Integer.valueOf(str);
//		Random random = new Random();
		int min = Integer.valueOf(FORMAT_MINUTE.format(new Date()));
		if (hour == 9) {
			num = incrNum(50, 100, 60, min);
		} else if (hour == 10) {
			num = incrNum(100, 150, 60, min);
		} else if (hour == 11) {
			num = incrNum(150, 200, 60, min);
		} else if (hour == 12) {
			num = incrNum(200, 300, 60, min);
		} else if (hour == 13) {
			num = incrNum(300, 400, 60, min);
		} else if (hour == 14) {
			num = incrNum(400, 500, 60, min);
		} else if (hour == 15) {
			num = incrNum(500, 600, 60, min);
		} else if (hour == 16) {
			num = incrNum(600, 700, 60, min);
		} else if (hour == 17) {
			num = incrNum(700, 800, 60, min);
		} else if (hour == 18) {
			num = incrNum(800, 900, 60, min);
		} else if (hour == 19) {
			num = incrNum(900, 1000, 60, min);
		} else if (hour == 20) {
			num = incrNum(1000, 950, 60, min);
		} else if (hour == 21) {
			num = incrNum(950, 900, 60, min);
		} else if (hour == 22) {
			num = incrNum(900, 800, 60, min);
		} else if (hour == 23) {
			num = incrNum(800, 700, 60, min);
		} else if (hour == 24) {
			num = incrNum(700, 600, 60, min);
		} else if (hour == 0) {
			num = incrNum(700, 600, 60, min);
		} else if (hour == 1) {
			num = incrNum(600, 500, 60, min);
		} else if (hour == 2) {
			num = incrNum(500, 400, 60, min);
		} else if (hour == 3) {
			num = incrNum(400, 300, 60, min);
		} else if (hour == 4) {
			num = incrNum(300, 250, 60, min);
		} else if (hour == 5) {
			num = incrNum(250, 200, 60, min);
		} else if (hour == 6) {
			num = incrNum(200, 150, 60, min);
		} else if (hour == 7) {
			num = incrNum(150, 100, 60, min);
		} else if (hour == 8) {
			num = incrNum(100, 50, 60, min);
		}
		num += lineNum + (int) (new Random().nextInt(3) + 1);
		return num;
	}

	public static void main(String[] args) {
		System.out.println(getFakeLine(2));
	}
}
