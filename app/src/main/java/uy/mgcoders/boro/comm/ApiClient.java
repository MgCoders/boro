package uy.mgcoders.boro.comm;

import android.util.Log;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uy.mgcoders.boro.exp.BoroException;

/**
 * Created by r on 24/04/15.
 */
public class ApiClient {

    //TODO: Hacer singleton? . ver si la session queda asociada a httpclient.
    private static ApiClient instance;
    HttpClient httpclient = null;
    private String host = null;

    private ApiClient(){
        httpclient = new DefaultHttpClient();
    }

    public static ApiClient getInstance(){
        if (instance == null)
            instance = new ApiClient();
        return instance;

    }

    public void login(String host, String user, String password) throws BoroException {

        this.host = host;
        HttpPost httppost = new HttpPost(host+"/rest/login");


        try {

            httppost.setHeader("Connection", "keep-alive");
            httppost.setHeader("Content-type", "application/x-www-form-urlencoded");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", user));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);



            String responseAsText = EntityUtils.toString(response.getEntity());
            //Json sacar session
            Log.v("LOGIN", "Response from server: " + responseAsText);

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }


    }

    public static HttpResponse makeRequest(String uri, String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
