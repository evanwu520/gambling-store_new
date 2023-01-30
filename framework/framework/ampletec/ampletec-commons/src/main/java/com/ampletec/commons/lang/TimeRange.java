package com.ampletec.commons.lang;


import java.util.Date;

public class TimeRange {
	
//	public static TimeRange intercept
	
	public static TimeRange duration(long beginOfMillis, int secs) {
		TimeRange range = new TimeRange();
		range.begin = new Date(beginOfMillis);
		range.end = new Date(beginOfMillis + secs * 1000);
		return range;
	}
	
	@SuppressWarnings("deprecation")
	public static TimeRange intraday(int begin, int end) {
		TimeRange range = new TimeRange();		
		long todayOfMillis = CalendarUtil.todayOfMillis();
		Date now = new Date(System.currentTimeMillis());
		range.begin = new Date(todayOfMillis + begin * 1000);
		range.end = new Date(todayOfMillis + end * 1000);
		if (range.begin.after(now)) {
			range.begin = CalendarUtil.after(range.begin, -1);
			range.end = CalendarUtil.after(range.end, -1);
		}
		return range;
	}
	
	public TimeRange() {}
	
	public TimeRange(int begin, int end) {
		this((long)(begin * 1000), (long)(end * 1000));
	}
	
	public TimeRange(Date begin, Date end) {
		this.begin = begin;
		this.end = end;
	}
	
	public TimeRange(long beginOfMillis, long endOfMillis) {
		begin = new Date(beginOfMillis);
		end = new Date(endOfMillis);
	}

	private Date begin;
	private Date end;
	public Date begin() {
		return begin;
	}
	public Date end() {
		return end;
	}
	
	public boolean include(Date d) {
		if (d.before(end) && d.after(begin)) {
			return true;
		}
		return false;
	}
	
	public boolean includeNow() {
		Date now = new Date(System.currentTimeMillis());
		if (now.before(end) && now.after(begin)) {
			return true;
		}
		return false;
	}
}
