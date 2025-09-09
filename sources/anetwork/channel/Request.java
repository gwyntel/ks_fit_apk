package anetwork.channel;

import anet.channel.request.BodyEntry;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public interface Request {
    void addHeader(String str, String str2);

    String getBizId();

    BodyEntry getBodyEntry();

    @Deprecated
    IBodyHandler getBodyHandler();

    String getCharset();

    int getConnectTimeout();

    Map<String, String> getExtProperties();

    String getExtProperty(String str);

    boolean getFollowRedirects();

    List<Header> getHeaders();

    Header[] getHeaders(String str);

    String getMethod();

    List<Param> getParams();

    int getReadTimeout();

    int getRetryTime();

    String getSeqNo();

    @Deprecated
    URI getURI();

    @Deprecated
    URL getURL();

    String getUrlString();

    @Deprecated
    boolean isCookieEnabled();

    void removeHeader(Header header);

    @Deprecated
    void setBizId(int i2);

    void setBizId(String str);

    void setBodyEntry(BodyEntry bodyEntry);

    @Deprecated
    void setBodyHandler(IBodyHandler iBodyHandler);

    void setCharset(String str);

    void setConnectTimeout(int i2);

    @Deprecated
    void setCookieEnabled(boolean z2);

    void setExtProperty(String str, String str2);

    void setFollowRedirects(boolean z2);

    void setHeader(Header header);

    void setHeaders(List<Header> list);

    void setMethod(String str);

    void setParams(List<Param> list);

    void setReadTimeout(int i2);

    void setRetryTime(int i2);

    void setSeqNo(String str);

    @Deprecated
    void setUri(URI uri);
}
