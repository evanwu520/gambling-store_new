package com.ampletec.boot.authorize.client.exception;

/**
 * 异常定义，需要更新说明，方便以后开发返回错误码
 * 
 * @author john
 * 
 */
public class TencentException extends AuthorizeInvokeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int NOT_LATEST = 40001;// 过期token
	public static int CODE_INVALID = 40029;// code 失效
	public static int CODE_BEEN_USED = 40163;// code 多次

	public TencentException(String info) {
		super(info);
	}

	public TencentException(int errCode, String info) {
		super(info);
		this.errCode = errCode;
	}
	

	private int errCode;
	public int getErrCode() {
		return this.errCode;
	}

	public String toString() {
		return "TencentException, CODE=" + this.errCode;
	}
}
