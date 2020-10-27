package org.macbeth.http;


import com.google.common.base.Strings;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author xuzha
 * @description:
 * @date 2020/10/27
 */
public class MyOkHttpClient implements HttpClient {

    private static final Logger LOG = LoggerFactory.getLogger(MyOkHttpClient.class);

    private static OkHttpClient client = new OkHttpClient();

    private static MediaType JSON = MediaType.get("application/json; charset=utf-8");


    /**
     * Get Request
     * @param url
     * @return
     * @throws IOException
     */
    public String requestGet(String url) throws Exception {

        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        String result = null;
        Request request = new Request.Builder().url(url).header("Connection", "keep-alive").build();
        try (Response response = client.newCall(request).execute()) {
            result = response.body().string();
            return result;
        } finally {
            LOG.info("request get url {} res {}", url, null == result ? "Error" : result);
        }
    }

    /**
     * Post Request
     * @param url
     * @param paramInJson
     * @return
     * @throws IOException
     */
    public String requestPost(String url, String paramInJson) throws Exception {

        if (Strings.isNullOrEmpty(url)) {
            return null;
        }

        if (Strings.isNullOrEmpty(paramInJson)) {
            paramInJson = "{}";
        }

        String result = null;
        RequestBody body = RequestBody.create(paramInJson, JSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            result = response.body().string();
            return result;
        } finally {
            LOG.info("request get url {} res {}", url, null == result ? "Error" : result);
        }
    }
}
