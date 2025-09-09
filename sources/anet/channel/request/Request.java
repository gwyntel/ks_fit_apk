package anet.channel.request;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import kotlin.text.Typography;
import org.json.JSONException;

/* loaded from: classes2.dex */
public class Request {
    public static final String DEFAULT_CHARSET = "UTF-8";

    /* renamed from: a, reason: collision with root package name */
    public final RequestStatistic f6850a;

    /* renamed from: b, reason: collision with root package name */
    private HttpUrl f6851b;

    /* renamed from: c, reason: collision with root package name */
    private HttpUrl f6852c;

    /* renamed from: d, reason: collision with root package name */
    private HttpUrl f6853d;

    /* renamed from: e, reason: collision with root package name */
    private URL f6854e;

    /* renamed from: f, reason: collision with root package name */
    private String f6855f;

    /* renamed from: g, reason: collision with root package name */
    private Map<String, String> f6856g;

    /* renamed from: h, reason: collision with root package name */
    private Map<String, String> f6857h;

    /* renamed from: i, reason: collision with root package name */
    private String f6858i;

    /* renamed from: j, reason: collision with root package name */
    private BodyEntry f6859j;

    /* renamed from: k, reason: collision with root package name */
    private boolean f6860k;

    /* renamed from: l, reason: collision with root package name */
    private String f6861l;

    /* renamed from: m, reason: collision with root package name */
    private String f6862m;

    /* renamed from: n, reason: collision with root package name */
    private int f6863n;

    /* renamed from: o, reason: collision with root package name */
    private int f6864o;

    /* renamed from: p, reason: collision with root package name */
    private int f6865p;

    /* renamed from: q, reason: collision with root package name */
    private HostnameVerifier f6866q;

    /* renamed from: r, reason: collision with root package name */
    private SSLSocketFactory f6867r;

    /* renamed from: s, reason: collision with root package name */
    private boolean f6868s;

    public static class Builder {

        /* renamed from: a, reason: collision with root package name */
        private HttpUrl f6869a;

        /* renamed from: b, reason: collision with root package name */
        private HttpUrl f6870b;

        /* renamed from: e, reason: collision with root package name */
        private Map<String, String> f6873e;

        /* renamed from: f, reason: collision with root package name */
        private String f6874f;

        /* renamed from: g, reason: collision with root package name */
        private BodyEntry f6875g;

        /* renamed from: j, reason: collision with root package name */
        private HostnameVerifier f6878j;

        /* renamed from: k, reason: collision with root package name */
        private SSLSocketFactory f6879k;

        /* renamed from: l, reason: collision with root package name */
        private String f6880l;

        /* renamed from: m, reason: collision with root package name */
        private String f6881m;

        /* renamed from: q, reason: collision with root package name */
        private boolean f6885q;

        /* renamed from: c, reason: collision with root package name */
        private String f6871c = "GET";

        /* renamed from: d, reason: collision with root package name */
        private Map<String, String> f6872d = new HashMap();

        /* renamed from: h, reason: collision with root package name */
        private boolean f6876h = true;

        /* renamed from: i, reason: collision with root package name */
        private int f6877i = 0;

        /* renamed from: n, reason: collision with root package name */
        private int f6882n = 10000;

        /* renamed from: o, reason: collision with root package name */
        private int f6883o = 10000;

        /* renamed from: p, reason: collision with root package name */
        private RequestStatistic f6884p = null;

        public Builder addHeader(String str, String str2) {
            this.f6872d.put(str, str2);
            return this;
        }

        public Builder addParam(String str, String str2) {
            if (this.f6873e == null) {
                this.f6873e = new HashMap();
            }
            this.f6873e.put(str, str2);
            this.f6870b = null;
            return this;
        }

        public Request build() {
            if (this.f6875g == null && this.f6873e == null && Method.a(this.f6871c)) {
                ALog.e("awcn.Request", "method " + this.f6871c + " must have a request body", null, new Object[0]);
            }
            if (this.f6875g != null && !Method.b(this.f6871c)) {
                ALog.e("awcn.Request", "method " + this.f6871c + " should not have a request body", null, new Object[0]);
                this.f6875g = null;
            }
            BodyEntry bodyEntry = this.f6875g;
            if (bodyEntry != null && bodyEntry.getContentType() != null) {
                addHeader("Content-Type", this.f6875g.getContentType());
            }
            return new Request(this);
        }

        public Builder setAllowRequestInBg(boolean z2) {
            this.f6885q = z2;
            return this;
        }

        public Builder setBizId(String str) {
            this.f6880l = str;
            return this;
        }

        public Builder setBody(BodyEntry bodyEntry) {
            this.f6875g = bodyEntry;
            return this;
        }

        public Builder setCharset(String str) {
            this.f6874f = str;
            this.f6870b = null;
            return this;
        }

        public Builder setConnectTimeout(int i2) {
            if (i2 > 0) {
                this.f6882n = i2;
            }
            return this;
        }

        public Builder setHeaders(Map<String, String> map) {
            this.f6872d.clear();
            if (map != null) {
                this.f6872d.putAll(map);
            }
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.f6878j = hostnameVerifier;
            return this;
        }

        public Builder setMethod(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("method is null or empty");
            }
            if ("GET".equalsIgnoreCase(str)) {
                this.f6871c = "GET";
            } else if ("POST".equalsIgnoreCase(str)) {
                this.f6871c = "POST";
            } else if (Method.OPTION.equalsIgnoreCase(str)) {
                this.f6871c = Method.OPTION;
            } else if (Method.HEAD.equalsIgnoreCase(str)) {
                this.f6871c = Method.HEAD;
            } else if (Method.PUT.equalsIgnoreCase(str)) {
                this.f6871c = Method.PUT;
            } else if ("DELETE".equalsIgnoreCase(str)) {
                this.f6871c = "DELETE";
            } else {
                this.f6871c = "GET";
            }
            return this;
        }

        public Builder setParams(Map<String, String> map) {
            this.f6873e = map;
            this.f6870b = null;
            return this;
        }

        public Builder setReadTimeout(int i2) {
            if (i2 > 0) {
                this.f6883o = i2;
            }
            return this;
        }

        public Builder setRedirectEnable(boolean z2) {
            this.f6876h = z2;
            return this;
        }

        public Builder setRedirectTimes(int i2) {
            this.f6877i = i2;
            return this;
        }

        public Builder setRequestStatistic(RequestStatistic requestStatistic) {
            this.f6884p = requestStatistic;
            return this;
        }

        public Builder setSeq(String str) {
            this.f6881m = str;
            return this;
        }

        public Builder setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.f6879k = sSLSocketFactory;
            return this;
        }

        public Builder setUrl(HttpUrl httpUrl) {
            this.f6869a = httpUrl;
            this.f6870b = null;
            return this;
        }

        public Builder setUrl(String str) {
            HttpUrl httpUrl = HttpUrl.parse(str);
            this.f6869a = httpUrl;
            this.f6870b = null;
            if (httpUrl != null) {
                return this;
            }
            throw new IllegalArgumentException("toURL is invalid! toURL = " + str);
        }
    }

    public static final class Method {
        public static final String DELETE = "DELETE";
        public static final String GET = "GET";
        public static final String HEAD = "HEAD";
        public static final String OPTION = "OPTIONS";
        public static final String POST = "POST";
        public static final String PUT = "PUT";

        static boolean a(String str) {
            return str.equals("POST") || str.equals(PUT);
        }

        static boolean b(String str) {
            return a(str) || str.equals("DELETE") || str.equals(OPTION);
        }
    }

    private Map<String, String> a() {
        return AwcnConfig.isCookieHeaderRedundantFix() ? new HashMap(this.f6856g) : this.f6856g;
    }

    private void b() {
        String strA = anet.channel.strategy.utils.c.a(this.f6857h, getContentEncoding());
        if (!TextUtils.isEmpty(strA)) {
            if (Method.a(this.f6855f) && this.f6859j == null) {
                try {
                    this.f6859j = new ByteArrayEntry(strA.getBytes(getContentEncoding()));
                    this.f6856g.put("Content-Type", "application/x-www-form-urlencoded; charset=" + getContentEncoding());
                } catch (UnsupportedEncodingException unused) {
                }
            } else {
                String strUrlString = this.f6851b.urlString();
                StringBuilder sb = new StringBuilder(strUrlString);
                if (sb.indexOf("?") == -1) {
                    sb.append('?');
                } else if (strUrlString.charAt(strUrlString.length() - 1) != '&') {
                    sb.append(Typography.amp);
                }
                sb.append(strA);
                HttpUrl httpUrl = HttpUrl.parse(sb.toString());
                if (httpUrl != null) {
                    this.f6852c = httpUrl;
                }
            }
        }
        if (this.f6852c == null) {
            this.f6852c = this.f6851b;
        }
    }

    public boolean containsBody() {
        return this.f6859j != null;
    }

    public String getBizId() {
        return this.f6861l;
    }

    public byte[] getBodyBytes() {
        if (this.f6859j == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        try {
            postBody(byteArrayOutputStream);
        } catch (IOException unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public int getConnectTimeout() {
        return this.f6864o;
    }

    public String getContentEncoding() {
        String str = this.f6858i;
        return str != null ? str : "UTF-8";
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.f6856g);
    }

    public String getHost() {
        return this.f6852c.host();
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.f6866q;
    }

    public HttpUrl getHttpUrl() {
        return this.f6852c;
    }

    public String getMethod() {
        return this.f6855f;
    }

    public int getReadTimeout() {
        return this.f6865p;
    }

    public int getRedirectTimes() {
        return this.f6863n;
    }

    public String getSeq() {
        return this.f6862m;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.f6867r;
    }

    public URL getUrl() {
        if (this.f6854e == null) {
            HttpUrl httpUrl = this.f6853d;
            if (httpUrl == null) {
                httpUrl = this.f6852c;
            }
            this.f6854e = httpUrl.toURL();
        }
        return this.f6854e;
    }

    public String getUrlString() {
        return this.f6852c.urlString();
    }

    public boolean isAllowRequestInBg() {
        return this.f6868s;
    }

    public boolean isRedirectEnable() {
        return this.f6860k;
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.f6871c = this.f6855f;
        builder.f6872d = a();
        builder.f6873e = this.f6857h;
        builder.f6875g = this.f6859j;
        builder.f6874f = this.f6858i;
        builder.f6876h = this.f6860k;
        builder.f6877i = this.f6863n;
        builder.f6878j = this.f6866q;
        builder.f6879k = this.f6867r;
        builder.f6869a = this.f6851b;
        builder.f6870b = this.f6852c;
        builder.f6880l = this.f6861l;
        builder.f6881m = this.f6862m;
        builder.f6882n = this.f6864o;
        builder.f6883o = this.f6865p;
        builder.f6884p = this.f6850a;
        builder.f6885q = this.f6868s;
        return builder;
    }

    public int postBody(OutputStream outputStream) throws IOException {
        BodyEntry bodyEntry = this.f6859j;
        if (bodyEntry != null) {
            return bodyEntry.writeTo(outputStream);
        }
        return 0;
    }

    public void setDnsOptimize(String str, int i2) throws JSONException {
        if (str != null) {
            if (this.f6853d == null) {
                this.f6853d = new HttpUrl(this.f6852c);
            }
            this.f6853d.replaceIpAndPort(str, i2);
        } else {
            this.f6853d = null;
        }
        this.f6854e = null;
        this.f6850a.setIPAndPort(str, i2);
    }

    public void setUrlScheme(boolean z2) {
        if (this.f6853d == null) {
            this.f6853d = new HttpUrl(this.f6852c);
        }
        this.f6853d.setScheme(z2 ? "https" : "http");
        this.f6854e = null;
    }

    private Request(Builder builder) {
        this.f6855f = "GET";
        this.f6860k = true;
        this.f6863n = 0;
        this.f6864o = 10000;
        this.f6865p = 10000;
        this.f6855f = builder.f6871c;
        this.f6856g = builder.f6872d;
        this.f6857h = builder.f6873e;
        this.f6859j = builder.f6875g;
        this.f6858i = builder.f6874f;
        this.f6860k = builder.f6876h;
        this.f6863n = builder.f6877i;
        this.f6866q = builder.f6878j;
        this.f6867r = builder.f6879k;
        this.f6861l = builder.f6880l;
        this.f6862m = builder.f6881m;
        this.f6864o = builder.f6882n;
        this.f6865p = builder.f6883o;
        this.f6851b = builder.f6869a;
        HttpUrl httpUrl = builder.f6870b;
        this.f6852c = httpUrl;
        if (httpUrl == null) {
            b();
        }
        this.f6850a = builder.f6884p != null ? builder.f6884p : new RequestStatistic(getHost(), this.f6861l);
        this.f6868s = builder.f6885q;
    }
}
