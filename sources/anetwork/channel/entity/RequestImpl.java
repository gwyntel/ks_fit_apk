package anetwork.channel.entity;

import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import anet.channel.util.ALog;
import anetwork.channel.Header;
import anetwork.channel.IBodyHandler;
import anetwork.channel.Param;
import anetwork.channel.Request;
import anetwork.channel.util.RequestConstant;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class RequestImpl implements Request {

    /* renamed from: a, reason: collision with root package name */
    @Deprecated
    private URI f7195a;

    /* renamed from: b, reason: collision with root package name */
    @Deprecated
    private URL f7196b;

    /* renamed from: c, reason: collision with root package name */
    private String f7197c;

    /* renamed from: e, reason: collision with root package name */
    private List<Header> f7199e;

    /* renamed from: g, reason: collision with root package name */
    private List<Param> f7201g;

    /* renamed from: k, reason: collision with root package name */
    private int f7205k;

    /* renamed from: l, reason: collision with root package name */
    private int f7206l;

    /* renamed from: m, reason: collision with root package name */
    private String f7207m;

    /* renamed from: n, reason: collision with root package name */
    private String f7208n;

    /* renamed from: o, reason: collision with root package name */
    private Map<String, String> f7209o;

    /* renamed from: d, reason: collision with root package name */
    private boolean f7198d = true;

    /* renamed from: f, reason: collision with root package name */
    private String f7200f = "GET";

    /* renamed from: h, reason: collision with root package name */
    private int f7202h = 2;

    /* renamed from: i, reason: collision with root package name */
    private String f7203i = "utf-8";

    /* renamed from: j, reason: collision with root package name */
    private BodyEntry f7204j = null;

    public RequestImpl() {
    }

    @Override // anetwork.channel.Request
    public void addHeader(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        if (this.f7199e == null) {
            this.f7199e = new ArrayList();
        }
        this.f7199e.add(new BasicHeader(str, str2));
    }

    @Override // anetwork.channel.Request
    public String getBizId() {
        return this.f7207m;
    }

    @Override // anetwork.channel.Request
    public BodyEntry getBodyEntry() {
        return this.f7204j;
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public IBodyHandler getBodyHandler() {
        return null;
    }

    @Override // anetwork.channel.Request
    public String getCharset() {
        return this.f7203i;
    }

    @Override // anetwork.channel.Request
    public int getConnectTimeout() {
        return this.f7205k;
    }

    @Override // anetwork.channel.Request
    public Map<String, String> getExtProperties() {
        return this.f7209o;
    }

    @Override // anetwork.channel.Request
    public String getExtProperty(String str) {
        Map<String, String> map = this.f7209o;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    @Override // anetwork.channel.Request
    public boolean getFollowRedirects() {
        return this.f7198d;
    }

    @Override // anetwork.channel.Request
    public List<Header> getHeaders() {
        return this.f7199e;
    }

    @Override // anetwork.channel.Request
    public String getMethod() {
        return this.f7200f;
    }

    @Override // anetwork.channel.Request
    public List<Param> getParams() {
        return this.f7201g;
    }

    @Override // anetwork.channel.Request
    public int getReadTimeout() {
        return this.f7206l;
    }

    @Override // anetwork.channel.Request
    public int getRetryTime() {
        return this.f7202h;
    }

    @Override // anetwork.channel.Request
    public String getSeqNo() {
        return this.f7208n;
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public URI getURI() {
        URI uri = this.f7195a;
        if (uri != null) {
            return uri;
        }
        if (this.f7197c != null) {
            try {
                this.f7195a = new URI(this.f7197c);
            } catch (Exception e2) {
                ALog.e("anet.RequestImpl", "uri error", this.f7208n, e2, new Object[0]);
            }
        }
        return this.f7195a;
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public URL getURL() {
        URL url = this.f7196b;
        if (url != null) {
            return url;
        }
        if (this.f7197c != null) {
            try {
                this.f7196b = new URL(this.f7197c);
            } catch (Exception e2) {
                ALog.e("anet.RequestImpl", "url error", this.f7208n, e2, new Object[0]);
            }
        }
        return this.f7196b;
    }

    @Override // anetwork.channel.Request
    public String getUrlString() {
        return this.f7197c;
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public boolean isCookieEnabled() {
        return !RequestConstant.FALSE.equals(getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    @Override // anetwork.channel.Request
    public void removeHeader(Header header) {
        List<Header> list = this.f7199e;
        if (list != null) {
            list.remove(header);
        }
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public void setBizId(int i2) {
        this.f7207m = String.valueOf(i2);
    }

    @Override // anetwork.channel.Request
    public void setBodyEntry(BodyEntry bodyEntry) {
        this.f7204j = bodyEntry;
    }

    @Override // anetwork.channel.Request
    public void setBodyHandler(IBodyHandler iBodyHandler) {
        this.f7204j = new BodyHandlerEntry(iBodyHandler);
    }

    @Override // anetwork.channel.Request
    public void setCharset(String str) {
        this.f7203i = str;
    }

    @Override // anetwork.channel.Request
    public void setConnectTimeout(int i2) {
        this.f7205k = i2;
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public void setCookieEnabled(boolean z2) {
        setExtProperty(RequestConstant.ENABLE_COOKIE, z2 ? "true" : RequestConstant.FALSE);
    }

    @Override // anetwork.channel.Request
    public void setExtProperty(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (this.f7209o == null) {
            this.f7209o = new HashMap();
        }
        this.f7209o.put(str, str2);
    }

    @Override // anetwork.channel.Request
    public void setFollowRedirects(boolean z2) {
        this.f7198d = z2;
    }

    @Override // anetwork.channel.Request
    public void setHeader(Header header) {
        if (header == null) {
            return;
        }
        if (this.f7199e == null) {
            this.f7199e = new ArrayList();
        }
        int size = this.f7199e.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            if (header.getName().equalsIgnoreCase(this.f7199e.get(i2).getName())) {
                this.f7199e.set(i2, header);
                break;
            }
            i2++;
        }
        if (i2 < this.f7199e.size()) {
            this.f7199e.add(header);
        }
    }

    @Override // anetwork.channel.Request
    public void setHeaders(List<Header> list) {
        this.f7199e = list;
    }

    @Override // anetwork.channel.Request
    public void setMethod(String str) {
        this.f7200f = str;
    }

    @Override // anetwork.channel.Request
    public void setParams(List<Param> list) {
        this.f7201g = list;
    }

    @Override // anetwork.channel.Request
    public void setReadTimeout(int i2) {
        this.f7206l = i2;
    }

    @Override // anetwork.channel.Request
    public void setRetryTime(int i2) {
        this.f7202h = i2;
    }

    @Override // anetwork.channel.Request
    public void setSeqNo(String str) {
        this.f7208n = str;
    }

    @Deprecated
    public void setUrL(URL url) {
        this.f7196b = url;
        this.f7197c = url.toString();
    }

    @Override // anetwork.channel.Request
    @Deprecated
    public void setUri(URI uri) {
        this.f7195a = uri;
    }

    @Override // anetwork.channel.Request
    public Header[] getHeaders(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (this.f7199e == null) {
            return null;
        }
        for (int i2 = 0; i2 < this.f7199e.size(); i2++) {
            if (this.f7199e.get(i2) != null && this.f7199e.get(i2).getName() != null && this.f7199e.get(i2).getName().equalsIgnoreCase(str)) {
                arrayList.add(this.f7199e.get(i2));
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        Header[] headerArr = new Header[arrayList.size()];
        arrayList.toArray(headerArr);
        return headerArr;
    }

    @Override // anetwork.channel.Request
    public void setBizId(String str) {
        this.f7207m = str;
    }

    @Deprecated
    public RequestImpl(URI uri) {
        this.f7195a = uri;
        this.f7197c = uri.toString();
    }

    @Deprecated
    public RequestImpl(URL url) {
        this.f7196b = url;
        this.f7197c = url.toString();
    }

    public RequestImpl(String str) {
        this.f7197c = str;
    }
}
