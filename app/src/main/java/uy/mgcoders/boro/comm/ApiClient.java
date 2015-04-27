package uy.mgcoders.boro.comm;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import uy.mgcoders.boro.exp.BoroException;
import uy.mgcoders.boro.exp.NotLoggedInException;
import uy.mgcoders.boro.objects.Issue;
import uy.mgcoders.boro.util.Query;

/**
 * Created by r on 24/04/15.
 */
public class ApiClient {

    //TODO: Hacer singleton? . ver si la session queda asociada a httpclient.
    private static ApiClient instance;
    HttpClient httpclient = null;
    private String host = null;

    private ApiClient() {

    }

    public static ApiClient getInstance() {
        if (instance == null)
            instance = new ApiClient();
        return instance;

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

    public void login(String host, String user, String password) throws BoroException {

        this.host = host;
        this.httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(host + "/rest/user/login");


        try {

            httppost.setHeader("Connection", "keep-alive");
            httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httppost.setHeader("Cookie", "");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("login", user));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            StringEntity e = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
            httppost.setEntity(e);

            HttpResponse response = httpclient.execute(httppost);

            Log.v("LOGIN", "Sending 'POST' request to URL : " + httppost.getURI());
            Log.v("LOGIN", "Post parameters : " + httppost.getEntity());
            Log.v("LOGIN", "Response Code : " +
                    response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() != 200) throw new BoroException("fail");


            String responseAsText = EntityUtils.toString(response.getEntity());
            //Json sacar session
            Log.v("LOGIN", "Response from server: " + responseAsText);

        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }


    }

    public List<Issue> getTasks(Query query) throws NotLoggedInException {

        if (httpclient == null) throw new NotLoggedInException("fail");

        List<Issue> result = new ArrayList<>();

        HttpGet request = new HttpGet();
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("filter", query.getContent()));
            String paramString = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
            request.setURI(new URI(host + "/rest/issue/?" + paramString));

            HttpResponse response = httpclient.execute(request);
            HttpEntity httpEntity = response.getEntity();
            String xml = EntityUtils.toString(httpEntity);
            XmlParser xmlParser = new XmlParser();
            Document doc = xmlParser.getDomElement(xml); // getting DOM element
            NodeList nl = doc.getElementsByTagName(XmlParser.KEY_ISSUE);

            for (int i = 0; i < nl.getLength(); i++) {
                Node n = nl.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    result.add(xmlParser.getIssue(n));
                    Log.v("GET_TASKS", "Post parameters : " + n.toString());
                }


            }
            {

            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }
}
