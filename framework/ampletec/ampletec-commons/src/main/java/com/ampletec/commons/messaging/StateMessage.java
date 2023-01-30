package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageOut;

public class StateMessage extends ResponseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StateMessage(byte state) {
		super(MessageTypes.O_RESULT);
		m_oState = state;
	}

	protected byte m_oState;

	@Override
	protected void writePackage(PackageOut out) throws Throwable {
		// TODO Auto-generated method stub
		out.writeByte(m_oState);
	}

	public String toEigenString() {
		return "ErrorMessage<>";
	}

	public String toString() {
		return "ErrorMessage<" + "state:" + m_oState + ">";
	}
}