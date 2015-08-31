package com.dealertrack.bam.resource;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Proxy oath and oath/refresh request from the app to the actual oath2 login service.
 * The login services doesn't allow CORS requests.  We proxy the request through our CORS enable application
 * to allow the front end to properly authenticate.
 *
 */
@Path("oauth")
@Component
public class OauthResource{

    private static final Logger log = LoggerFactory.getLogger(OauthResource.class);

    @Value("${oauth2-url}")
    private String oauth2Url;

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response postOauth(@FormParam("grant_type") final String grantType,
                              @FormParam("client_id") final String clientId,
                              @FormParam("username") final String username,
                              @FormParam("password") final String password) throws Exception{

        HttpPost httpPost = new HttpPost(oauth2Url);
        List<NameValuePair> formValues = new ArrayList();
        formValues.add(new BasicNameValuePair("grant_type", grantType));
        formValues.add(new BasicNameValuePair("client_id", clientId));
        formValues.add(new BasicNameValuePair("username", username));
        formValues.add(new BasicNameValuePair("password", password));
        httpPost.setEntity(new UrlEncodedFormEntity(formValues));

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

        return executeHttpRequestAndProxyResponse(httpPost);
    }


    @POST
    @Path("refresh")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response postOauthRefresh(@FormParam("grant_type") final String grantType,
                                     @FormParam("refresh_token") final String refreshToken) throws Exception{

        HttpPost httpPost = new HttpPost(oauth2Url + "/refresh");

        List<NameValuePair> formValues = new ArrayList();
        formValues.add(new BasicNameValuePair("grant_type", grantType));
        formValues.add(new BasicNameValuePair("refresh_token", refreshToken));
        httpPost.setEntity(new UrlEncodedFormEntity(formValues));

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

        return executeHttpRequestAndProxyResponse(httpPost);
    }


    /**
     *
     * Executes an httpRequest and then consumes and copies the org.apache.http.HttpResponse to a
     * suitable resource response that can be returned to the client
     *
     * @param httpRequest request method to execute
     * @return proxied Response from the executed request
     * @throws IOException
     */
    Response executeHttpRequestAndProxyResponse(HttpUriRequest httpRequest) throws IOException{
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpRequest)){

            //set the status code from the response
            Response.ResponseBuilder responseBuilder = Response.status(response.getStatusLine().getStatusCode());

            //copy all the headers from the response
            for(Header header : response.getAllHeaders()){
                responseBuilder.header(header.getName(), header.getValue());
            }

            //copy the response body
            HttpEntity entity = response.getEntity();
            responseBuilder.entity(EntityUtils.toByteArray(entity));

            return responseBuilder.build();
        }
    }
}
