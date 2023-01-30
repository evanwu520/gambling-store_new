package com.ampletec.commons.encryption;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.lang3.StringUtils;

public class APIKeyEncryptor {

	private static final Log log = LogFactory.getLog(APIKeyEncryptor.class);
			
	public static void main(String[] args)
	{
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appid", "app2881111a0de78c8d9d");
		paraMap.put("gameid", "G-077508BA3HVG");
		paraMap.put("size", 10);
		paraMap.put("requestid", "f5d99c04-fb7a-4bac-8530-ef22fddd8ef6");//UUID.randomUUID().toString());
		paraMap.put("timestamp", "1618488136");//DateTime.now());
		
		StringBuilder paramUrl = APIKeyEncryptor.adjustment(paraMap);
		System.out.println("order: " + paramUrl.toString());
		String sgin = APIKeyEncryptor.sign(paraMap, "secret", "sec33ce872a385e585f8");
	  	System.out.println(sgin);
//	  	System.out.println(paramUrl);
	  	System.out.println(paramUrl + "sign=" + sgin);
	}
	
	public static Map<String, ?> encrypt(Map<String, ?> paraMap, String signKey, String secretKey, String secretValue) {
//		String sign = 
		APIKeyEncryptor.sign(paraMap, secretKey, secretValue);
//		paraMap.put(signKey, sign);
		return paraMap;
	}
	
	public static StringBuilder adjustment(Map<String, ?> paraMap) {
		StringBuilder buff = new StringBuilder();
		Map<String, ?> tmpMap = paraMap;
		try {
			List<Map.Entry<String, ?>> infoIds = new ArrayList<Map.Entry<String, ?>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, ?>>() {
				public int compare(Map.Entry<String, ?> o1, Map.Entry<String, ?> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			
			for (Map.Entry<String, ?> item : infoIds) {
				String key = item.getKey();
				if (StringUtils.isNotBlank(key)) {
					String val = "";
					if (Objects.nonNull(item.getValue())) {
						val = URLEncoder.encode(String.valueOf(item.getValue()), "UTF-8");
					}
					buff.append(key + "=" + val);
					buff.append("&");
				}
			}//buf.append(secretKey + "=" + secretValue);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return buff;
	}

	public static String sign(Map<String, ?> paraMap, String secretKey, String secretValue) {
		StringBuilder buff = adjustment(paraMap);
		buff.append(secretKey + "=" + secretValue);
		return MD5.sign(buff.toString());
	}
}