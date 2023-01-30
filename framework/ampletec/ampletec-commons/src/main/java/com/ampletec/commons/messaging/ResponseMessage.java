package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageOut;

public abstract class ResponseMessage extends Message implements IResponseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseMessage(short iType) {
		this.type = iType;
	}

	protected PackageOut newPackageOut() {
		return new PackageOut(type);
	}

	protected abstract void writePackage(PackageOut out) throws Throwable;

	public final PackageOut response() throws Throwable {
		PackageOut out = new PackageOut(type);
		writePackage(out);
		return out;
	}
}
