package com.ampletec.commons.lang;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class SerializeUtil {

	public static byte[] serialize(Object object) throws Exception {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public static Object unserialize(byte[] bytes) throws Exception {
		if (bytes == null) {
			return null;
		}
		try {
			ByteArrayInputStream bais = null;
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new Exception(e);
		}
	}

	public static String serializeToString(Object obj) throws Exception {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toString("ISO-8859-1");
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			oos.close();
			baos.close();
		}
	}

	public static Object unserialize(String str) throws Exception {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		byteArrayInputStream.close();
		return object;
	}
}
