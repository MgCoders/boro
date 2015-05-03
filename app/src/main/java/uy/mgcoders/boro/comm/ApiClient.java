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
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uy.mgcoders.boro.exp.BoroException;
import uy.mgcoders.boro.exp.NotLoggedInException;
import uy.mgcoders.boro.objects.Author;
import uy.mgcoders.boro.objects.Issue;
import uy.mgcoders.boro.objects.IssueCompact;
import uy.mgcoders.boro.objects.IssueCompacts;
import uy.mgcoders.boro.objects.WorkItem;
import uy.mgcoders.boro.objects.WorkItemTypes;
import uy.mgcoders.boro.objects.WorkItems;
import uy.mgcoders.boro.util.DateFormatTransformer;
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


            Serializer serializer = new Persister();

            Reader reader = new StringReader(xml);

            IssueCompacts compacts = serializer.read(IssueCompacts.class, reader, false);

            for (IssueCompact c : compacts.getIssues()) {
                result.add(new Issue(c));
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public WorkItemTypes getWorkTypes(String projectId) throws BoroException {
        WorkItemTypes types = null;

        if (httpclient == null) throw new NotLoggedInException("fail");

        HttpGet request = new HttpGet();
        try {

            request.setURI(new URI(host + "/rest/admin/project/" + projectId + "/timetracking/worktype"));

            HttpResponse response = httpclient.execute(request);

            if (response.getStatusLine().getStatusCode() == 400)
                throw new BoroException("Time tracking disabled for project");

            HttpEntity httpEntity = response.getEntity();
            String xml = EntityUtils.toString(httpEntity);


            Serializer serializer = new Persister();

            Reader reader = new StringReader(xml);

            types = serializer.read(WorkItemTypes.class, reader, false);


            Log.v("GET_TYPES", "Work Item Types : " + types.getWorkTypes().size());


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return types;
    }


    public List<WorkItem> getWorkItems(String issueId) throws BoroException {
        if (httpclient == null) throw new NotLoggedInException("fail");

        List<WorkItem> result = new ArrayList<>();

        HttpGet request = new HttpGet();
        try {

            request.setURI(new URI(host + "/rest/issue/3_Li-13/timetracking/workitem/"));

            HttpResponse response = httpclient.execute(request);

            if (response.getStatusLine().getStatusCode() == 400)
                throw new BoroException("Time tracking disabled for project");

            HttpEntity httpEntity = response.getEntity();
            String xml = EntityUtils.toString(httpEntity);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z");


            RegistryMatcher m = new RegistryMatcher();
            m.bind(Date.class, new DateFormatTransformer(format));


            Serializer serializer = new Persister(m);

            Reader reader = new StringReader(xml);

            WorkItems items = serializer.read(WorkItems.class, reader, false);


            Log.v("GET_TASKS", "Post parameters : " + items.toString());


        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;


    }

    public boolean registerWork(WorkItem mWork, Issue issue) throws BoroException {


        Author a = new Author();
        a.setLogin(issue.getAsignee());
        mWork.setAuthor(a);
        mWork.setDescription(mWork.getDescription() + " --Registered with Boro");

        if (httpclient == null) throw new NotLoggedInException("fail");

        HttpPost httppost = new HttpPost(host + "/rest/issue/" + issue.getId() + "/timetracking/workitem");
        try {

            httppost.setHeader("Content-type", "application/xml; charset=UTF-8");
            httppost.setHeader("Vary", "Accept-Encoding");

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S z");


            RegistryMatcher m = new RegistryMatcher();
            m.bind(Date.class, new DateFormatTransformer(format));


            Serializer serializer = new Persister(m);

            Writer writer = new StringWriter();

            serializer.write(mWork, writer);

            StringEntity e = new StringEntity(writer.toString(), HTTP.UTF_8);
            e.setContentEncoding("UTF-8");
            httppost.setEntity(e);

            HttpParams params = httppost.getParams();


            HttpResponse response = httpclient.execute(httppost);

            Log.v("REGISTER_WORK", "Sending 'POST' request to URL : " + httppost.getURI());
            Log.v("REGISTER_WORK", "Post parameters : " + httppost.getEntity());
            Log.v("REGISTER_WORK", "Response Code : " +
                    response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() != 201) return false;


            String responseAsText = EntityUtils.toString(response.getEntity());

            Log.v("REGISTER_WORK", "Response from server: " + responseAsText);


        } catch (Exception e) {
            throw new BoroException(e.getMessage());
        }


        return true;
    }
}
