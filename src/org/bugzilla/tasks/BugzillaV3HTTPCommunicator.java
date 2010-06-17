package org.bugzilla.tasks;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements login over http. Does not understand how to read bugs from bugzilla... subclasses should
 * do that.
 */
public abstract class BugzillaV3HTTPCommunicator implements BugzillaCommunicator {
    private URI uri;


    public BugzillaV3HTTPCommunicator(URI uri) {
        this.uri = uri;
    }

    private URI rebuildURI(URI uri, String newPath) throws URISyntaxException {
        String scheme = uri.getScheme();
        int port = uri.getPort();
        String host = uri.getHost();
        String userinfo = uri.getUserInfo();
        String query = uri.getQuery();
        String fragment = uri.getFragment();
        return new URI(scheme, userinfo,  host,  port, newPath,  query, fragment);
    }
    
    @Override
    public AuthToken login(String username, String password) throws IOException {
        /// index.cgi is the location for login

        URI queryURI;
        try {
            queryURI = rebuildURI(uri, "/index.cgi");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error while rebuilding URI: " , e);
        }

        HttpClient httpclient = new DefaultHttpClient();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("Bugzilla_login", username));
        formparams.add(new BasicNameValuePair("Bugzilla_password", password));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
        HttpPost httppost = new HttpPost(queryURI);
        httppost.setEntity(entity);
        httpclient.execute(httppost,localContext);
        List<Cookie> cookies = cookieStore.getCookies();
        if(cookies.size() == 0) {
            return null;
        }
        for(Cookie cookie: cookies) {
            if(cookie.getName().equals("Bugzilla_logincookie")) {
                // TODO: need to handle expiration time
                return new BugzillaCookieToken(cookie.getValue());
            }
        }
        return null;
    }

    @Override
    public BugzillaTask[] getIssues(AuthToken token, BugzillaQuery query) throws IOException {
        URI queryURI;
        try {
            queryURI = rebuildURI(uri, "/buglist.cgi");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error while rebuilding URI: " , e);
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(queryURI);
        httpget.setParams(queryToParams(query));
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            return process(instream);
        }
        throw new IOException("Server returned no data");
    }

    protected abstract HttpParams queryToParams(BugzillaQuery query);

    protected abstract BugzillaTask[] process(InputStream stream) throws IOException;
    
}
