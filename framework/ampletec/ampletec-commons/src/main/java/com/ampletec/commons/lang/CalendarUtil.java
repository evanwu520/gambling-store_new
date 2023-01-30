package com.ampletec.commons.lang;

/**
 * @(#)CalendarUtil.java
 *
 *
 * @author herowei
 * @version 0.1 2010/06/23
 */
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Deprecated
public class CalendarUtil {
	
	public static final int ONEDAYSECS = 86400;
	public static final int ONEDAYMILLIS = 86400000;

	/**
	 * 时间范围：年
	 */
	public static final int YEAR = 1;

	/**
	 * 时间范围：季度
	 */
	public static final int QUARTER = 2;

	/**
	 * 时间范围：月
	 */
	public static final int MONTH = 3;

	/**
	 * 时间范围：旬
	 */
	public static final int TENDAYS = 4;

	/**
	 * 时间范围：周
	 */
	public static final int WEEK = 5;

	/**
	 * 时间范围：日
	 */
	public static final int DAY = 6;

	/* 基准时间 */
	private Date fiducialDate = null;

	private Calendar cal = null;

	private CalendarUtil(Date fiducialDate) {
		if (fiducialDate != null) {
			this.fiducialDate = fiducialDate;
		} else {
			this.fiducialDate = new Date(System.currentTimeMillis());
		}

		this.cal = Calendar.getInstance();
		this.cal.setTime(this.fiducialDate);
//		this.cal.set(Calendar.HOUR_OF_DAY, 0);
//		this.cal.set(Calendar.MINUTE, 0);
//		this.cal.set(Calendar.SECOND, 0);
//		this.cal.set(Calendar.MILLISECOND, 0);

		this.fiducialDate = this.cal.getTime();
	}
	
	public static int current() {
		return (int) System.currentTimeMillis() / 1000;
	}
	
//	public static Date now() {
//		return new Date(System.currentTimeMillis());
//	}

	/**
	 * 获取DateHelper实例
	 * 
	 * @param fiducialDate
	 *            基准时间
	 * @return Date
	 */
	public static CalendarUtil getInstance(Date fiducialDate) {
		return new CalendarUtil(fiducialDate);
	}

	/**
	 * 获取DateHelper实例, 使用当前时间作为基准时间
	 * 
	 * @return Date
	 */
	public static CalendarUtil getInstance() {
		return new CalendarUtil(null);
	}
	
	

	/**
	 * 获取年的第一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getFirstDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取年的最后一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getLastDayOfYear(int offset) {
		cal.setTime(this.fiducialDate);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + offset);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}

	/**
	 * 获取季度的第一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getFirstDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);
		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.APRIL);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.OCTOBER);
			cal.set(Calendar.DAY_OF_MONTH, 1);
		}
		return cal.getTime();
	}

	public Date getYesterday() {
		long time = this.fiducialDate.getTime() - 60 * 60 * 24 * 1000;
		return new Date(time);
	}

	public Date getTomorrow() {
		long time = this.fiducialDate.getTime() + 60 * 60 * 24 * 1000;
		return new Date(time);
	}

	/**
	 * 获取季度的最后一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getLastDayOfQuarter(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset * 3);

		int mon = cal.get(Calendar.MONTH);
		if (mon >= Calendar.JANUARY && mon <= Calendar.MARCH) {
			cal.set(Calendar.MONTH, Calendar.MARCH);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		if (mon >= Calendar.APRIL && mon <= Calendar.JUNE) {
			cal.set(Calendar.MONTH, Calendar.JUNE);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.JULY && mon <= Calendar.SEPTEMBER) {
			cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 30);
		}
		if (mon >= Calendar.OCTOBER && mon <= Calendar.DECEMBER) {
			cal.set(Calendar.MONTH, Calendar.DECEMBER);
			cal.set(Calendar.DAY_OF_MONTH, 31);
		}
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getFirstDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取月的最后一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getLastDayOfMonth(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.MONTH, offset + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取旬的第一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getFirstDayOfTendays(int offset) {
		cal.setTime(this.fiducialDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day >= 21) {
			day = 21;
		} else if (day >= 11) {
			day = 11;
		} else {
			day = 1;
		}

		if (offset > 0) {
			day = day + 10 * offset;
			int monOffset = day / 30;
			day = day % 30;
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		} else {
			int monOffset = 10 * offset / 30;
			int dayOffset = 10 * offset % 30;
			if ((day + dayOffset) > 0) {
				day = day + dayOffset;
			} else {
				monOffset = monOffset - 1;
				day = day - dayOffset - 10;
			}
			cal.add(Calendar.MONTH, monOffset);
			cal.set(Calendar.DAY_OF_MONTH, day);
		}
		return cal.getTime();
	}

	/**
	 * 获取旬的最后一天
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getLastDayOfTendays(int offset) {
		Date date = getFirstDayOfTendays(offset + 1);
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取周的第一天(MONDAY)
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getFirstDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}

	/**
	 * 获取周的最后一天(SUNDAY)
	 * 
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date getLastDayOfWeek(int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(Calendar.DAY_OF_MONTH, offset * 7);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DAY_OF_MONTH, 6);
		return cal.getTime();
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的时间量
	 * 
	 * @param field
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * @param offset
	 *            偏移量
	 * @return Date
	 */
	public Date add(int field, int offset) {
		cal.setTime(this.fiducialDate);
		cal.add(field, offset);
		return cal.getTime();
	}
	
	public long differ(int field, Date date){
		long diffTime = (cal.getTime().getTime() - date.getTime()) / 1000;
		long diff = 0;
		switch (field){
		case CalendarUtil.DAY :
			diff = diffTime / (3600 * 24);
			break;
		case CalendarUtil.WEEK :
			diff = diffTime / (3600 * 24 * 7);
			break;
		//case 
		}
		
		return Math.abs( diff);
	}

	/**
	 * 根据日历的规则，为基准时间添加指定日历字段的单个时间单元
	 * 
	 * @param field
	 *            日历字段, 使用Calendar类定义的日历字段常量
	 * @param up
	 *            指定日历字段的值的滚动方向。true:向上滚动 / false:向下滚动
	 * @return Date
	 */
	public Date roll(int field, boolean up) {
		cal.setTime(this.fiducialDate);
		cal.roll(field, up);
		return cal.getTime();
	}
	
	public static long append(long time, int offset, TimeUnit unit) {
		int result = 0;
		switch (unit){
		case SECONDS:
			result = 1000 * offset;
			break;
		case MINUTES:
			result = 60 * 1000 * offset;
			break;
		case HOURS:
			result = 60 * 60 * 1000 * offset;
			break;
		case DAYS:
			result = 24 * 60 * 60 * 1000 * offset;
			break;
		default:
			result = offset;
			break;
		}
		return time + result;
	}
	
	public static Long appendOnceday(long time){
		return CalendarUtil.append(time, 1, TimeUnit.DAYS);
	}
	
	public static long calculate(int year, int month, int date, int hour, int minute){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime().getTime() / 1000;
	}
	
	public static long getDateOfMillis(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTimeInMillis();
	}
	
	public static long todayOfMillis() {
		return getDateOfMillis(System.currentTimeMillis());
	}
	
	public static Date intraday(int time) {
		return new Date(todayOfMillis() + time * 1000);
	}
	
	/**
	 * 取当前日期的字符串形式,"XXXX年XX月XX日"
	 * 
	 * @return java.lang.String
	 */
	public static String getPrintDate() {
		return getPrintDate(new Date());
	}
	
	/**
	 * 取当前日期的字符串形式,"XXXX年XX月XX日"
	 * 
	 * @return java.lang.String
	 */
	public static String getPrintDate(Date date) {
		String printDate = "";
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		printDate += calendar.get(Calendar.YEAR) + "年";
		printDate += (calendar.get(Calendar.MONTH) + 1) + "月";
		printDate += calendar.get(Calendar.DATE) + "日 ";
		printDate += calendar.get(Calendar.HOUR_OF_DAY) + "时";
		printDate += calendar.get(Calendar.MINUTE) + "分";
		printDate += calendar.get(Calendar.SECOND) + "秒";
		return printDate;
	}
	
	
	public static void print(long time){
		String printDate = getPrintDate(new Date(time));
		System.out.println(printDate);
	}

	public static Date getToday(){
		return CalendarUtil.getInstance().fiducialDate;
	}
	
	public static Date getNowData() {
		return CalendarUtil.getInstance().fiducialDate;
	}

	/**
	 * 得到当前时间 return java.sql.Timestamp
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	

	/**
	 * 得到为星期?索引
	 * @return int
	 */
	public static int getTodayOfWeek() {
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//SimpleDateFormat 是一个以与语言环境相关的方式来格式化和分析日期的具体类。它允许进行格式化（日期 -> 文本）、分析（文本 -> 日期）和规范化。
		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		try {
			date = sdfInput.parse(sdfInput.format(date));
		} catch (ParseException ex) {
		}
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//get 和 set 的字段数字，指示一个星期中的某天。
		return dayOfWeek;
	}
	
	
	
	
	public static Date after(int offset){
		return CalendarUtil.after(offset, CalendarUtil.DAY);
	}
	
	public static Date after(int offset, int field){
		return CalendarUtil.getInstance().add(field, -(offset));
	}
	
	public static Date after(Date d, int offset){
		return CalendarUtil.getInstance(d).add(CalendarUtil.DAY, offset);
	}
	
	public static Date after(Date d, int field, int offset){
		return CalendarUtil.getInstance(d).add(field, offset);
	}
	
	public static Date afterHours(Date d, int offset){
		return CalendarUtil.getInstance(d).add(Calendar.HOUR_OF_DAY, offset);
	}
	
	public static Date afterOnceday(Date d){
		return CalendarUtil.after(d, 1);
	}
	
	public static long diffday(Date date){
		return CalendarUtil.getInstance().differ(CalendarUtil.DAY, date);
	}
}
