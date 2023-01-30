package com.ampletec.commons.messaging;

import com.ampletec.commons.net.PackageOut;

public interface IResponseMessage {
	public PackageOut response() throws Throwable;
}
