package net.kentandcds4r.chuckerview;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
        g_proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

        // flags
        if(request <= 0) {
            sendGetRequest();
        }
        else if(request == 1) {
            sendPostRequest();
        }
        else if(request >= 2)
        {
            sendGetRequest();
            sendPostRequest();
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
                    if(g_httpURLConnectionGet != null) {
                        g_httpURLConnectionGet = null;
                    }

                    if(g_useProxy && g_proxy != null)
                    {
                        g_httpURLConnectionGet = (HttpURLConnection) new URL(str2).openConnection(g_proxy);
                    }
                    else
                    {
                        g_httpURLConnectionGet = (HttpURLConnection) new URL(str2).openConnection();
                    }

                    g_httpURLConnectionGet.setRequestMethod("GET");
                    g_httpURLConnectionGet.setConnectTimeout(g_iConnectionTimeout);
                    g_httpURLConnectionGet.connect();
                    g_httpURLConnectionGet.getResponseCode();

                    System.out.println("Get request accepted!");
                } catch (Exception e) {
                    System.out.println("Get request declined!");
                    e.printStackTrace();
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
                    if(g_httpURLConnectionGet != null) {
                        g_httpURLConnectionGet = null;
                    }

                    if(g_useProxy && g_proxy != null)
                    {
                        g_httpURLConnectionPost = (HttpURLConnection) new URL(str2).openConnection(g_proxy);
                    }
                    else
                    {
                        g_httpURLConnectionPost = (HttpURLConnection) new URL(str2).openConnection();
                    }

                    g_httpURLConnectionPost.setRequestMethod("POST");
                    g_httpURLConnectionPost.setConnectTimeout(g_iConnectionTimeout);
                    g_httpURLConnectionPost.connect();
                    g_httpURLConnectionPost.getResponseCode();

                    System.out.println("Post request accepted!");
                } catch (Exception e) {
                    System.out.println("Post request declined!");
                    e.printStackTrace();
                }
            });
        }
    }
}