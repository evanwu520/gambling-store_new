package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageOut;

public class ErrorMessage extends ResponseMessage {
	
	public static final ErrorMessage UNAUTHORIZED = new ErrorMessage((short)401, "Unauthorized");
	
	public static final ErrorMessage PARAM_ERROR = new ErrorMessage((short)501, "Parameter error");
	
	public static final ErrorMessage INTERNAL_SERVER_ERROR = new ErrorMessage((short)500, "Internal Server Error");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final short MESSAGE_CODE = 0;

	public ErrorMessage(short errorid) {
		super(ErrorMessage.MESSAGE_CODE);
		m_iErrorid = errorid;
	}
	
	public ErrorMessage(short errorid, String desc) {
		this(errorid);
		m_strDesc = desc;
	}
	
	protected short m_iErrorid;
	
	protected String m_strDesc;

	@Override
	protected void writePackage(PackageOut out) throws Throwable {
		// TODO Auto-generated method stub
		out.writeShort(m_iErrorid);
		out.writeUTF(m_strDesc);
	}

	public String toEigenString() {
		return "ErrorMessage<>";
	}

	public String toString() {
		return "ErrorMessage<" + "errorid:" + m_iErrorid + ">";
	}
}