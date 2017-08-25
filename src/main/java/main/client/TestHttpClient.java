package main.client;

import main.util.JsonRpcChecker;
import main.util.Methods;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static main.util.JsonRpcBuilder.getJsonRpc;

public class TestHttpClient implements IClient {

    private String url;

    public static final HttpClient httpClient = HttpClientBuilder.create().build();

    private int requestCount = 0;

    public TestHttpClient(String url) {
        this.url = url;
    }

    public String processRequest(Methods method, Object object) {
        try {
            requestCount++;
            HttpPost request = new HttpPost(url);
            request.setHeader("content-type", "application/json");
            request.setEntity(new StringEntity(getJsonRpc(method.name(), object)));
            HttpResponse response = httpClient.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            JsonRpcChecker.checkJsonRpcMessage(result);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getRequestCount() {
        return requestCount;
    }


}
