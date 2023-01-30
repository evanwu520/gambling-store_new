package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageOut;

public class TimeSyncMessage extends ResponseMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeSyncMessage() {
		super(MessageTypes.O_TIME_SYNC);
	}

	@Override
	protected void writePackage(PackageOut out) throws Throwable {
		// TODO Auto-generated method stub
		out.writeLong(System.currentTimeMillis());
	}
}