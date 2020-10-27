package org.macbeth.http;

import org.junit.Assert;
import org.junit.Test;

public class HttpClientTest {

    private HttpClient myHttpClient = new MyHttpClient();

    private HttpClient myOkHttpClient = new MyOkHttpClient();


    @Test
    public void testGetHttpGet() throws Exception {
        String res0 = myHttpClient.requestGet("http://baidu.com");
        String res1 = myOkHttpClient.requestGet("http://baidu.com");
        Assert.assertNotNull(res0);
        Assert.assertNotNull(res1);
    }


    @Test
    public void testGetHttpPost() throws Exception {
        String res0 = myHttpClient.requestPost("http://baidu.com", null);
        String res1 = myOkHttpClient.requestPost("http://baidu.com", null);
        Assert.assertNotNull(res0);
        Assert.assertNotNull(res1);
    }
}
