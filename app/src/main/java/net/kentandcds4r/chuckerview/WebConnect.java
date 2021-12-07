package net.kentandcds4r.chuckerview;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class WebConnect
{
    private String    g_url = "";
    private boolean   g_useProxy = true;
    private int       g_request = 0;
    private String    g_host = "";
    private int       g_port = 0;
    private int       g_iConnectionTimeout = 2000;
    private MainActivity  g_context = null;

    private Handler g_handler = null;
    private HandlerThread g_hthread = null;

    private HttpURLConnection g_httpURLConnectionPost = null;
    private HttpURLConnection g_httpURLConnectionGet = null;

    private Proxy g_proxy = null;

    public WebConnect(MainActivity context, Handler handler, HandlerThread hthread, String url, boolean useProxy, int request, String host, int port, int iConnectionTimeout)
    {
        g_context = context;
        g_handler = handler;
        g_hthread = hthread;
        g_url = url;
        g_useProxy = useProxy;
        g_request = request;
        g_host = host;
        g_port = port;
        g_iConnectionTimeout = iConnectionTimeout;

        if(useProxy) {
            g_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        }

        // flags
        if(request <= 0) {
            sendGetRequest();
        }
        else if(request == 1) {
            sendPostRequest();
        }
        else if(request == 2)
        {
            sendGetRequest();
            sendPostRequest();
        }
        else if(request == 5) {
            sendTestRequest();
        }
    }

    public void disconnect()
    {
        if(g_httpURLConnectionGet != null) {
            g_httpURLConnectionGet.disconnect();
            g_httpURLConnectionGet = null;
        }

        if(g_httpURLConnectionPost != null) {
            g_httpURLConnectionPost.disconnect();
            g_httpURLConnectionPost = null;
        }
    }

    public void sendTestRequest()
    {
        System.out.println("Sending test request..");
        String proxyHost = g_host;
        int proxyPort = g_port;
        InetSocketAddress proxyAddr = new InetSocketAddress(proxyHost, proxyPort);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddr);

        HttpURLConnection urlConnection = null;
        URL request = null;
        try {
            System.out.println("Sending test request suc");
            request = new URL(g_url);
            urlConnection = (HttpURLConnection) request.openConnection(proxy);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(36000);
            urlConnection.setReadTimeout(44000);
            //urlConnection.connect();
        } catch (IOException e) {
            System.out.println("Sending test request err");
            e.printStackTrace();
        }
    }

    public void sendGetRequest()
    {
        if(g_url.length() <= 0) {
            System.out.println("Invalid URL (#1)");
            return;
        }

        System.out.println("Sending get request..");

        String str;
        try {
            str = new String(g_url.getBytes(), "windows-1251");
        } catch (UnsupportedEncodingException unused) {
            str = null;
        }
        if (str != null) {
            final String str2 = str;
            g_handler.post(() -> {
                try {
                    if(g_useProxy && g_proxy != null)
                    {
                        g_httpURLConnectionGet = (HttpURLConnection) new URL(str2).openConnection(g_proxy);
                    }
                    else
                    {
                        g_httpURLConnectionGet = (HttpURLConnection) new URL(str2).openConnection();
                    }

                    g_httpURLConnectionGet.setRequestMethod("GET");
                    g_httpURLConnectionGet.setDoInput(true);
                    g_httpURLConnectionGet.setConnectTimeout(5000);
                    g_httpURLConnectionGet.setReadTimeout(5000);
                    g_httpURLConnectionGet.setUseCaches(false);
                    g_httpURLConnectionGet.connect();
                    g_httpURLConnectionGet.getResponseCode();

                    System.out.println("Connected!");
                } catch (Exception e) {
                    System.out.println("Connection failed! Message: " + e.getMessage() + " Cause: " + e.getCause());
                }
            });
        }
    }

    public void sendPostRequest()
    {
        if(g_url.length() <= 0) {
            System.out.println("Invalid URL (#2)");
            return;
        }

        System.out.println("Sending post request..");

        String str;
        try {
            str = new String(g_url.getBytes(), "windows-1251");
        } catch (UnsupportedEncodingException unused) {
            str = null;
        }
        if (str != null) {
            final String str2 = str;
            g_handler.post(() -> {
                try {
                    if(g_useProxy && g_proxy != null)
                    {
                        g_httpURLConnectionPost = (HttpURLConnection) new URL(str2).openConnection(g_proxy);
                    }
                    else
                    {
                        g_httpURLConnectionPost = (HttpURLConnection) new URL(str2).openConnection();
                    }

                    g_httpURLConnectionPost.setRequestMethod("POST");
                    g_httpURLConnectionPost.setDoInput(true);
                    g_httpURLConnectionPost.setConnectTimeout(5000);
                    g_httpURLConnectionPost.setReadTimeout(5000);
                    g_httpURLConnectionPost.setUseCaches(false);
                    g_httpURLConnectionPost.connect();
                    g_httpURLConnectionPost.getResponseCode();

                    System.out.println("Connected!");
                } catch (Exception e) {
                    System.out.println("Connection failed! Message: " + e.getMessage() + " Cause: " + e.getCause());
                }
            });
        }
    }


}
