package org.android.spdy;

import anet.channel.util.HttpConstant;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import okhttp3.internal.http2.Header;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes5.dex */
public final class SpdyRequest {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    private int connectionTimeoutMs;
    private String domain;
    private Map<String, String> extHead;
    private String host;
    private String method;
    private int port;
    private RequestPriority priority;
    private String proxyIp;
    private int proxyPort;
    private int requestRdTimeoutMs;
    private int requestTimeoutMs;
    private int retryTimes;
    private URL url;

    public SpdyRequest(URL url, String str, int i2, String str2, int i3, String str3, RequestPriority requestPriority, int i4, int i5, int i6) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = "";
        this.host = str;
        this.port = i2;
        if (str2 != null && i3 != 0) {
            this.proxyIp = str2;
            this.proxyPort = i3;
        }
        this.method = str3;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
        this.requestTimeoutMs = i4;
        this.connectionTimeoutMs = i5;
        this.retryTimes = i6;
    }

    private String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.url.getPath());
        if (this.url.getQuery() != null) {
            sb.append("?");
            sb.append(this.url.getQuery());
        }
        if (this.url.getRef() != null) {
            sb.append(MqttTopic.MULTI_LEVEL_WILDCARD);
            sb.append(this.url.getRef());
        }
        if (sb.length() == 0) {
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
        }
        return sb.toString();
    }

    public void addHeader(String str, String str2) {
        this.extHead.put(str, str2);
    }

    public void addHeaders(Map<String, String> map) {
        this.extHead.putAll(map);
    }

    public String getAuthority() {
        return this.host + ":" + Integer.toString(this.port) + "/" + this.proxyIp + ":" + this.proxyPort;
    }

    public int getConnectionTimeoutMs() {
        return this.connectionTimeoutMs;
    }

    String getDomain() {
        return this.domain;
    }

    Map<String, String> getHeaders() {
        HashMap map = new HashMap(5);
        map.put(Header.TARGET_PATH_UTF8, getPath());
        map.put(Header.TARGET_METHOD_UTF8, this.method);
        map.put(":version", "HTTP/1.1");
        map.put(":host", this.url.getAuthority());
        map.put(Header.TARGET_SCHEME_UTF8, this.url.getProtocol());
        Map<String, String> map2 = this.extHead;
        if (map2 != null && map2.size() > 0) {
            map.putAll(this.extHead);
        }
        return map;
    }

    String getHost() {
        return this.host;
    }

    String getMethod() {
        return this.method;
    }

    int getPort() {
        int i2 = this.port;
        if (i2 < 0) {
            return 80;
        }
        return i2;
    }

    int getPriority() {
        return this.priority.getPriorityInt();
    }

    String getProxyIp() {
        return this.proxyIp;
    }

    int getProxyPort() {
        return this.proxyPort;
    }

    public int getRequestRdTimeoutMs() {
        return this.requestRdTimeoutMs;
    }

    public int getRequestTimeoutMs() {
        return this.requestTimeoutMs;
    }

    public int getRetryTimes() {
        return this.retryTimes;
    }

    URL getUrl() {
        return this.url;
    }

    String getUrlPath() {
        return this.url.getProtocol() + HttpConstant.SCHEME_SPLIT + this.url.getAuthority() + getPath();
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public void setRequestRdTimeoutMs(int i2) {
        if (i2 >= 0) {
            this.requestRdTimeoutMs = i2;
        }
    }

    public SpdyRequest(URL url, String str, int i2, String str2, RequestPriority requestPriority) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = "";
        this.host = str;
        this.port = i2;
        this.method = str2;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
    }

    public SpdyRequest(URL url, String str, RequestPriority requestPriority) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = "";
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
    }

    public SpdyRequest(URL url, String str, RequestPriority requestPriority, int i2, int i3) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = "";
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
        this.requestTimeoutMs = i2;
        this.connectionTimeoutMs = i3;
    }

    public SpdyRequest(URL url, String str) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = "";
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str;
        this.extHead = new HashMap(5);
        this.priority = RequestPriority.DEFAULT_PRIORITY;
    }

    public SpdyRequest(URL url, String str, String str2, int i2, String str3, int i3, String str4, RequestPriority requestPriority, int i4, int i5, int i6) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = str;
        this.host = str2;
        this.port = i2;
        if (str3 != null && i3 != 0) {
            this.proxyIp = str3;
            this.proxyPort = i3;
        }
        this.method = str4;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
        this.requestTimeoutMs = i4;
        this.connectionTimeoutMs = i5;
        this.retryTimes = i6;
    }

    public SpdyRequest(URL url, String str, String str2, int i2, String str3, RequestPriority requestPriority) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = str;
        this.host = str2;
        this.port = i2;
        this.method = str3;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
    }

    public SpdyRequest(URL url, String str, String str2, RequestPriority requestPriority) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = str;
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str2;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
    }

    public SpdyRequest(URL url, String str, String str2, RequestPriority requestPriority, int i2, int i3) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = str;
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str2;
        this.extHead = new HashMap(5);
        this.priority = requestPriority;
        if (requestPriority == null) {
            this.priority = RequestPriority.DEFAULT_PRIORITY;
        }
        this.requestTimeoutMs = i2;
        this.connectionTimeoutMs = i3;
    }

    public SpdyRequest(URL url, String str, String str2) {
        this.proxyIp = "0.0.0.0";
        this.proxyPort = 0;
        this.requestTimeoutMs = 0;
        this.requestRdTimeoutMs = 0;
        this.connectionTimeoutMs = 0;
        this.retryTimes = 0;
        this.url = url;
        this.domain = str;
        this.host = url.getHost();
        int port = url.getPort();
        this.port = port;
        if (port < 0) {
            this.port = url.getDefaultPort();
        }
        this.method = str2;
        this.extHead = new HashMap(5);
        this.priority = RequestPriority.DEFAULT_PRIORITY;
    }
}
