package org.macbeth.http;

import com.google.common.base.Strings;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * @author xuzha
 * @description:
 * @date 2020/10/27
 */
public class MyHttpClient implements HttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(MyOkHttpClient.class);

    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    @Override
    public String requestGet(String url) throws Exception {

        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        URI uri = new URIBuilder(url).build();
        HttpGet httpGet = new HttpGet(uri);
        return request(httpGet);
    }

    @Override
    public String requestPost(String url, String paramInJson) throws Exception {

        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        if (Strings.isNullOrEmpty(paramInJson)) {
            paramInJson = "{}";
        }

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(paramInJson, "UTF-8"));
        return request(httpPost);
    }

    /**
     * 发送请求
     * @param httpUriRequest
     * @return
     * @throws Exception
     */
    private String request(HttpUriRequest httpUriRequest) throws Exception {
        String result = null;
        try (CloseableHttpResponse response = httpclient.execute(httpUriRequest)) {
            if (null != response) {
                StatusLine statusLine = response.getStatusLine();
                if (null != statusLine && statusLine.getStatusCode() == 200) {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            }
        } finally {
            LOG.info("request get url {} res {}", httpUriRequest.getURI().toString(), null == result ? "Error" : result);
        }
        return result;
    }
}
