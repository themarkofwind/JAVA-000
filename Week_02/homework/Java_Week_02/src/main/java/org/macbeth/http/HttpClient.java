package org.macbeth.http;

/**
 * @author xuzha
 * @description:
 * @date 2020/10/27
 */
public interface HttpClient {

    String requestGet(String url) throws Exception;

    String requestPost(String url, String paramInJson) throws Exception;
}
