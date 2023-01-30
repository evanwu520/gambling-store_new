package com.ampletec.commons.encryption;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Deprecated
public class APIKEYUtils {

	private static final Log log = LogFactory.getLog(APIKEYUtils.class);
	
	public static void main(String[] args)
	{
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("appid", "cloudwaysb");
		paraMap.put("gamecode", "ba001");
		paraMap.put("gameno", System.currentTimeMillis() + "");
		paraMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		
		StringBuilder paramUrl = APIKEYUtils.adjustment(paraMap);
		String sgin = APIKEYUtils.sign(paraMap, "secret", "abcd1234");
	  	System.out.println(sgin);
//	  	System.out.println(paramUrl);
	  	System.out.println(paramUrl + "sgin=" + sgin);
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
						val = URLEncoder.encode(item.getValue().toString(), "UTF-8");
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