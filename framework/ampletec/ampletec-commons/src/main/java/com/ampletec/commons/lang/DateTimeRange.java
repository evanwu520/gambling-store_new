package com.ampletec.commons.lang;

import java.io.Serializable;



public class DateTimeRange implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DateTimeRange(long begin, long end) {
		this.begin = begin;
		this.end = end;
	}

	private Long begin;
	private Long end;
	public Long getBegin() {
		return begin;
	}
	public void setBegin(Long begin) {
		this.begin = begin;
	}
	public Long getEnd() {
		return end;
	}
	public void setEnd(Long end) {
		this.end = end;
	}
}
