package com.ampletec.commons.messaging;

import java.io.Serializable;

public abstract class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected short type;

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
}
