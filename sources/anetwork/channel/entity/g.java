package anetwork.channel.entity;

import anet.channel.request.Request;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;
import anet.channel.util.Utils;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.util.RequestConstant;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class g {

    /* renamed from: b, reason: collision with root package name */
    public RequestStatistic f7230b;

    /* renamed from: c, reason: collision with root package name */
    public final int f7231c;

    /* renamed from: d, reason: collision with root package name */
    public final int f7232d;

    /* renamed from: e, reason: collision with root package name */
    public final String f7233e;

    /* renamed from: f, reason: collision with root package name */
    public final int f7234f;

    /* renamed from: g, reason: collision with root package name */
    private ParcelableRequest f7235g;

    /* renamed from: h, reason: collision with root package name */
    private Request f7236h;

    /* renamed from: j, reason: collision with root package name */
    private int f7238j;

    /* renamed from: k, reason: collision with root package name */
    private final boolean f7239k;

    /* renamed from: i, reason: collision with root package name */
    private int f7237i = 0;

    /* renamed from: a, reason: collision with root package name */
    public int f7229a = 0;

    public g(ParcelableRequest parcelableRequest, int i2, boolean z2) {
        this.f7236h = null;
        this.f7238j = 0;
        if (parcelableRequest == null) {
            throw new IllegalArgumentException("request is null");
        }
        this.f7235g = parcelableRequest;
        this.f7234f = i2;
        this.f7239k = z2;
        this.f7233e = anetwork.channel.util.a.a(parcelableRequest.seqNo, i2 == 0 ? "HTTP" : "DGRD");
        int i3 = parcelableRequest.connectTimeout;
        this.f7231c = i3 <= 0 ? (int) (Utils.getNetworkTimeFactor() * 12000.0f) : i3;
        int i4 = parcelableRequest.readTimeout;
        this.f7232d = i4 <= 0 ? (int) (Utils.getNetworkTimeFactor() * 12000.0f) : i4;
        int i5 = parcelableRequest.retryTime;
        this.f7238j = (i5 < 0 || i5 > 3) ? 2 : i5;
        HttpUrl httpUrlL = l();
        RequestStatistic requestStatistic = new RequestStatistic(httpUrlL.host(), String.valueOf(parcelableRequest.bizId));
        this.f7230b = requestStatistic;
        requestStatistic.url = httpUrlL.simpleUrlString();
        this.f7236h = b(httpUrlL);
    }

    private Request b(HttpUrl httpUrl) {
        Request.Builder requestStatistic = new Request.Builder().setUrl(httpUrl).setMethod(this.f7235g.method).setBody(this.f7235g.bodyEntry).setReadTimeout(this.f7232d).setConnectTimeout(this.f7231c).setRedirectEnable(this.f7235g.allowRedirect).setRedirectTimes(this.f7237i).setBizId(this.f7235g.bizId).setSeq(this.f7233e).setRequestStatistic(this.f7230b);
        requestStatistic.setParams(this.f7235g.params);
        String str = this.f7235g.charset;
        if (str != null) {
            requestStatistic.setCharset(str);
        }
        requestStatistic.setHeaders(c(httpUrl));
        return requestStatistic.build();
    }

    private HttpUrl l() {
        HttpUrl httpUrl = HttpUrl.parse(this.f7235g.url);
        if (httpUrl == null) {
            throw new IllegalArgumentException("url is invalid. url=" + this.f7235g.url);
        }
        if (!NetworkConfigCenter.isSSLEnabled()) {
            ALog.i("anet.RequestConfig", "request ssl disabled.", this.f7233e, new Object[0]);
            httpUrl.downgradeSchemeAndLock();
        } else if (RequestConstant.FALSE.equalsIgnoreCase(this.f7235g.getExtProperty(RequestConstant.ENABLE_SCHEME_REPLACE))) {
            httpUrl.lockScheme();
        }
        return httpUrl;
    }

    public Request a() {
        return this.f7236h;
    }

    public boolean c() {
        return this.f7239k;
    }

    public boolean d() {
        return this.f7229a < this.f7238j;
    }

    public boolean e() {
        return NetworkConfigCenter.isHttpSessionEnable() && !RequestConstant.FALSE.equalsIgnoreCase(this.f7235g.getExtProperty(RequestConstant.ENABLE_HTTP_DNS)) && (NetworkConfigCenter.isAllowHttpIpRetry() || this.f7229a == 0);
    }

    public HttpUrl f() {
        return this.f7236h.getHttpUrl();
    }

    public String g() {
        return this.f7236h.getUrlString();
    }

    public Map<String, String> h() {
        return this.f7236h.getHeaders();
    }

    public boolean i() {
        return !RequestConstant.FALSE.equalsIgnoreCase(this.f7235g.getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    public boolean j() {
        return "true".equals(this.f7235g.getExtProperty(RequestConstant.CHECK_CONTENT_LENGTH));
    }

    public void k() {
        int i2 = this.f7229a + 1;
        this.f7229a = i2;
        this.f7230b.retryTimes = i2;
    }

    private Map<String, String> c(HttpUrl httpUrl) {
        String strHost = httpUrl.host();
        boolean z2 = !anet.channel.strategy.utils.c.a(strHost);
        if (strHost.length() > 2 && strHost.charAt(0) == '[' && strHost.charAt(strHost.length() - 1) == ']' && anet.channel.strategy.utils.c.b(strHost.substring(1, strHost.length() - 1))) {
            z2 = false;
        }
        HashMap map = new HashMap();
        Map<String, String> map2 = this.f7235g.headers;
        if (map2 != null) {
            for (Map.Entry<String, String> entry : map2.entrySet()) {
                String key = entry.getKey();
                if (!"Host".equalsIgnoreCase(key) && !":host".equalsIgnoreCase(key)) {
                    boolean zEqualsIgnoreCase = "true".equalsIgnoreCase(this.f7235g.getExtProperty(RequestConstant.KEEP_CUSTOM_COOKIE));
                    if (!"Cookie".equalsIgnoreCase(key) || zEqualsIgnoreCase) {
                        map.put(key, entry.getValue());
                    }
                } else if (!z2) {
                    map.put("Host", entry.getValue());
                }
            }
        }
        return map;
    }

    public void a(Request request) {
        this.f7236h = request;
    }

    public String a(String str) {
        return this.f7235g.getExtProperty(str);
    }

    public void a(HttpUrl httpUrl) {
        ALog.i("anet.RequestConfig", "redirect", this.f7233e, "to url", httpUrl.toString());
        this.f7237i++;
        this.f7230b.url = httpUrl.simpleUrlString();
        this.f7236h = b(httpUrl);
    }

    public int b() {
        return this.f7232d * (this.f7238j + 1);
    }
}
