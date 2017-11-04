package com.glumov.convertandsend.client;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Dmitriy Glumov on 04/11/2017.
 */
@Component
public class PostJSON {
    private static final Logger LOG = LoggerFactory.getLogger(PostJSON.class);

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    public void send(String url, String json) throws IOException, AuthenticationException {

        LOG.info("Start send to url: {}", url);


        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(json);

        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        CloseableHttpResponse response = client.execute(httpPost);
        LOG.info("Response code: {}", response.toString());
        client.close();
    }
}
