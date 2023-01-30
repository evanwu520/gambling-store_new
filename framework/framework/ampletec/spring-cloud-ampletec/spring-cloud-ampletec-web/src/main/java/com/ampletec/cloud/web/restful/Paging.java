package com.ampletec.cloud.web.restful;

import java.io.Serializable;

import com.github.pagehelper.PageInfo;

import lombok.Data;

@Data
public class Paging implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Paging(PageInfo<?> page) {
		this.data = page.getList();
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.totalPage = page.getPages();
		this.totalCount = page.getTotal();
	}

	private Integer status;
    private Integer pageNum;
    private Integer pageSize;
    private Long totalCount;
    private Integer totalPage;
    private Object data;
    private Object result = 0;
}
