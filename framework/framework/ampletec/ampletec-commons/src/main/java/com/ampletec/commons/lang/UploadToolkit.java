package com.ampletec.commons.lang;



import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

public class UploadToolkit {
	
	private static final Log log = LogFactory.getLog(UploadToolkit.class);

	/**
     * 请求参数设置
     */
    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000)
            .setConnectTimeout(30000).setConnectionRequestTimeout(30000).build();

    /**
     * 使用httpClient 上传带参数的图片流
     *
     * @param url
     *            上传地址
     * @param stream
     *            图片流
     * @param fileName
     *            图片名称
     * @return
     * @throws IOException
     */
    public static JSONObject postFile(String url, InputStream stream, String fileName, String fileDir) throws IOException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpclient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("file", stream, ContentType.MULTIPART_FORM_DATA, fileName)
                .addTextBody("fileDir", fileDir).setCharset(Charset.forName("UTF-8")).build();
        httpPost.setEntity(reqEntity);
        CloseableHttpResponse httpResponse = null;
        try {
            httpPost.setConfig(requestConfig);
            httpResponse = httpclient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = httpResponse.getEntity();
                String result = EntityUtils.toString(resEntity);
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
        	log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                // 关闭连接,释放资源
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
            	log.error(ExceptionUtils.getStackTrace(e));
            }
        }
        JSONObject object = new JSONObject();
        object.put("result", -3);
        object.put("desc","server error");
        return  object;
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     */
    public static String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
        	log.error(ExceptionUtils.getStackTrace(e));
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
            	log.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return responseContent;
    }
}
