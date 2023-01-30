package com.ampletec.servlet.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ampletec.commons.lang.SplitCharacter;

public class HttpServletUtils {

	public static final String BROWSER_QQ = "QQ";

	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Enumeration<?> enu = request.getParameterNames();
		Map<String, String> paramMap = new HashMap<String, String>();
		while (enu.hasMoreElements()) {
			String paraName = String.valueOf(enu.nextElement());
			String paraValue = request.getParameter(paraName);
			paramMap.put(paraName, paraValue);
		}
		
		return paramMap;
	}
	
	public static String getRequestParamsString(HttpServletRequest request) {
		String ret = "";
		Enumeration<?> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = String.valueOf(enu.nextElement());
			String paraValue = request.getParameter(paraName);			
//			ret += " " + paraName + ": " + paraValue;
			ret += paraName + "=" + paraValue + "&";
		}
		return ret;
	}
	
	@Deprecated
	public static String getRequestHostII(HttpServletRequest request) {
		return StringUtils.replace(request.getRequestURL().toString(), request.getRequestURI(), "");
	}
	
	public static String getRequestHost(HttpServletRequest request) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		String host = url.getHost();
		return host;
	}
	
	/**
	 * 获取真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRealIP(HttpServletRequest request) {
		String ip = request.getHeader("ali-cdn-real-ip");
		if (!isEffective(ip) || isInternalAddr(ip)) {
			ip = request.getHeader("HTTP_ALI_CDN_REAL_IP");
		}
		if (!isEffective(ip) || isInternalAddr(ip)) {
			ip = request.getHeader("x-original-forwarded-for");
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("HTTP_X_ORIGINAL_FORWARDED_FOR");
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (isEffective(ip) && ip.indexOf(",") > -1) {
				String[] array = ip.split(",");
				for (String str : array) {
					if (isEffective(str)) {
						ip = str;
						break;
					}
				}
			}
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (!isEffective(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (!isEffective(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getOsAndBrowserInfo(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";
		// =================OS Info=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			String wd = userAgent.substring(userAgent.indexOf("Windows"));
			os = (wd.split("\\)")[0]).replace(" ", "-");
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = (userAgent.substring(userAgent.indexOf("Mac")).split("\\)")[0]).replace(" ", "-");
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix" + "-" + (userAgent.substring(userAgent.indexOf("X11") + 3).split("\\)")[0]).replace(" ", "-");
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			if (userAgent.toLowerCase().indexOf("Mobile") >= 0) {
				os = "Android-Mobile";
			} else {
				os = "Android";
			}
			os = os + "-" + (userAgent.substring(userAgent.indexOf("Android") + 7).split("\\)")[0]).replace(" ", "-");
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
			os = os + "-" + (userAgent.substring(userAgent.indexOf("IPhone") + 6).split("\\)")[0]).replace(" ", "-");
		} else if (userAgent.toLowerCase().indexOf("ipad") >= 0) {
			os = "IPad";
			os = os + "-" + (userAgent.substring(userAgent.indexOf("IPad") + 4).split("\\)")[0]).replace(" ", "-");
		} else {
			os = "uk";
		}
		os = os.replace(";", "");
		// ===============Browser===========================
		if (userAgent.toLowerCase().indexOf("micromessenger") > -1) {// 微信客户端
			browser = "WeiXin" + "-" + (userAgent.substring(userAgent.indexOf("MicroMessenger") + 16).split(" ")[0]).replace("/", "-");
		} else if (userAgent.toLowerCase().indexOf("qq/") > -1) {// qq客户端
			browser = BROWSER_QQ + "-" + (userAgent.toLowerCase().substring(userAgent.indexOf("qq/") + 3).split(" ")[0]).replace("/", "-");
		} else if (user.contains("edge")) {
			browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (user.contains("opr")) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
			}
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";
		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browser = "uk";
		}

		return os + SplitCharacter.LINE + browser;
	}

	private static boolean isEffective(String remoteAddr) {
		if ((null != remoteAddr) && (!"".equals(remoteAddr.trim()))
				&& (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
			return true;
		}
		return false;
	}
	
	
	public static boolean isInternalAddr(String ip) {
		String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";//正则表达式=。 =、懒得做文字处理了、
		Pattern p = Pattern.compile(reg);
		Matcher matcher = p.matcher(ip);	
		return matcher.find();
	}
	
	public static void main(String args[]) {
		
		String ip = "172.20.0.6";
		if (!isEffective(ip) || isInternalAddr(ip)) {
			System.out.println("in");
		}
		System.out.println(isInternalAddr("172.20.0.6"));
	}
}
