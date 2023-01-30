package com.ampletec.commons.lang;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.zone.ZoneRules;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
	public static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
	public static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    public static final DateTimeFormatter SHORT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static final DateTimeFormatter DATETIME_FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
	

	public static int now() {
		return (int)(DateTime.current() / 1000);
	}
	
	public static long current() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static long toTicks(long millis) {
		return (millis*10000)+621355968000000000L;  
	}
	
	public static long ticks() {
		return (System.currentTimeMillis()*10000)+621355968000000000L;  
	}
	
	public static long dayOfMillis(long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTimeInMillis();
	}
	
	public static long dayOfMillis(Date date) {
		return dayOfMillis(date.getTime());
	}
	


	public static long beginOfMillis(long timeMillis, int offset) {
		long dayMillis = dayOfMillis(timeMillis);
		long dayOfLeftMillis = timeMillis - dayMillis;
		long offsetMillis = (offset * 1000);
		long beginMillis = 0L;
		if (dayOfLeftMillis > offsetMillis) {
			beginMillis = dayMillis + offsetMillis;
		} else {
			beginMillis = dayMillis + offsetMillis - 86400000L;
		}
		
		return beginMillis;
	}
	
	public static long dayOfMillis2(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDateTime ldt = LocalDateTime.of(localDate, LocalTime.MIN);
		return ldt.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli();
	}
	
	
	@Deprecated
	public static String print(long millisecond) {
		return print(new Date(millisecond), DATETIME_FORMATTER);
	}
	@Deprecated
	public static String print(String pattern) {
		return print(new Date(System.currentTimeMillis()), DateTimeFormatter.ofPattern(pattern));
	}
	@Deprecated
	public static String print(Date date) {
		return print(date, DATETIME_FORMATTER);
	}
	@Deprecated
	public static String print(Date date, String pattern) {
		return print(date, DateTimeFormatter.ofPattern(pattern));
	}
	@Deprecated
	public static String print(Date date, DateTimeFormatter formatter) {
		return dateToLocalTime(date).format(formatter);
	}
	
	public static String format(long millisecond, DateTimeFormatter formatter)  {
		return format(new Date(millisecond), formatter);
	}
	
	public static String format(long millisecond, String pattern) {
		return format(new Date(millisecond), DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String format(long millisecond) {
		return format(new Date(millisecond), DATETIME_FORMATTER);
	}
	
	public static String format(String pattern) {
		return format(new Date(System.currentTimeMillis()), DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String format(Date date) {
		return format(date, DATETIME_FORMATTER);
	}
	
	public static String format(Date date, String pattern) {
		return format(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	public static String format(Date date, DateTimeFormatter formatter) {
		return dateToLocalTime(date).format(formatter);
	}
	
	public static LocalDateTime dateToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
	
	//判断美国的时间是否在夏令时
    public static boolean isDaylightTime(LocalDateTime a) {
        LocalDateTime startDate = a.withMonth(3).toLocalDate().atTime(2, 0);
        LocalDateTime startlightDay = startDate.with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.SUNDAY));
        //更新为11月
        LocalDateTime endDate = a.withMonth(11).toLocalDate().atTime(1, 59,59);
        LocalDateTime endlightDay = endDate.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.SUNDAY));
 
        if (a.isBefore(startlightDay) || a.isAfter(endlightDay)) {
            return false;
        }
        return true;
    }
 
    //传入指定时间和时区
    public static boolean isDaylightTime(LocalDateTime a,ZoneId dest) {
        ZonedDateTime z1 = a.atZone(dest);
        ZoneRules rules = dest.getRules();
        boolean flag = rules.isDaylightSavings(z1.toInstant());
        return flag;
    }
    
    public static void main(String[] args) {
    	
    	int updatetime = 1643346929;
    	Long millisecond = updatetime * 1000L;
    	System.out.println(millisecond);
    	System.out.println(DateTime.format(updatetime * 1000L));
    	
    	
    	int nextShoeNum = 1;
    	int createtime = 1638687836;
    	long beginMillis = DateTime.beginOfMillis(createtime * 1000L, 21600);
		long leftMillis = DateTime.current() - beginMillis;
		if(leftMillis > 86400000L) {
			nextShoeNum = 1;
		} else {
			nextShoeNum = 1 + 1;
		}
		
		System.out.println(nextShoeNum);
    }
}
