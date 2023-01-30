package com.ampletec.commons.lang;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class URLVariables {

	public static String transactSQLInjection(String str) {
		String ret = str.replaceAll(".*([';]+|(--)+).*", "");
		return ret.replaceAll(" ", "");
	}
	
	public static String encode(Map<String, ?> paraMap) {
		return URLVariables.encode(paraMap, true, false, false);
	}

	public static String encode(Map<String, ?> paraMap, boolean urlEncode, boolean keyToLower,	boolean isAscii) {
		String buff = "";
		Map<String, ?> tmpMap = paraMap;
		try {
			List<Map.Entry<String, ?>> infoIds = new ArrayList<Map.Entry<String, ?>>(tmpMap.entrySet());
			if (isAscii) {
				// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
				Collections.sort(infoIds, new Comparator<Map.Entry<String, ?>>() {
					public int compare(Map.Entry<String, ?> o1, Map.Entry<String, ?> o2) {
						return (o1.getKey()).toString().compareTo(o2.getKey());
					}
				});
			}
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, ?> item : infoIds) {
				if (StringUtils.isNotBlank(item.getKey())) {
					try {
						String key = item.getKey();
						String val = "";
						if (Objects.nonNull(item.getValue())) {
							val = String.valueOf(item.getValue());
						}
						if (urlEncode) {
							val = URLEncoder.encode(val, "utf-8");
						}
						if (keyToLower) {
							buf.append(key.toLowerCase() + "=" + val);
						} else {
							buf.append(key + "=" + val);
						}
						buf.append("&");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}

}
