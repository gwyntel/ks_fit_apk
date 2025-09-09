package com.aliyun.alink.linksdk.alcs.coap;

import anet.channel.util.HttpConstant;
import com.aliyun.alink.linksdk.alcs.api.utils.AlcsConstUtils;
import com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class AlcsCoAPRequest extends AlcsCoAPMessage {
    private static final Pattern IP_PATTERN = Pattern.compile("(\\[[0-9a-f:]+\\]|[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
    private static final String TAG = "AlcsCoAPRequest";
    private AlcsCoAPConstant.Code code;
    private String scheme;
    private Principal senderIdentity;
    private Map<String, String> userContext;

    public AlcsCoAPRequest(AlcsCoAPConstant.Code code) {
        this(code, AlcsCoAPConstant.Type.CON);
    }

    public static AlcsCoAPRequest newDelete() {
        return new AlcsCoAPRequest(AlcsCoAPConstant.Code.DELETE);
    }

    public static AlcsCoAPRequest newGet() {
        return new AlcsCoAPRequest(AlcsCoAPConstant.Code.GET);
    }

    public static AlcsCoAPRequest newPost() {
        return new AlcsCoAPRequest(AlcsCoAPConstant.Code.POST);
    }

    public static AlcsCoAPRequest newPut() {
        return new AlcsCoAPRequest(AlcsCoAPConstant.Code.PUT);
    }

    private void validateBeforeSending() {
        if (getDestination() == null) {
            throw new NullPointerException("Destination is null");
        }
        if (getDestinationPort() == 0) {
            throw new NullPointerException("Destination port is 0");
        }
    }

    public AlcsCoAPConstant.Code getCode() {
        return this.code;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public int getRawCode() {
        AlcsCoAPConstant.Code code = this.code;
        if (code == null) {
            return 0;
        }
        return code.value;
    }

    public String getScheme() {
        String str = this.scheme;
        return str == null ? "coap" : str;
    }

    public Principal getSenderIdentity() {
        return this.senderIdentity;
    }

    public String getURI() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String uriHost = getOptions().getUriHost();
        if (uriHost == null) {
            uriHost = getDestination() != null ? getDestination().getHostAddress() : "localhost";
        }
        String str = uriHost;
        Integer uriPort = getOptions().getUriPort();
        if (uriPort == null) {
            uriPort = Integer.valueOf(getDestinationPort());
        }
        if (uriPort.intValue() <= 0) {
            uriPort = -1;
        } else if (AlcsCoAPConstant.isSupportedScheme(getScheme()) && AlcsCoAPConstant.getDefaultPort(getScheme()) == uriPort.intValue()) {
            uriPort = -1;
        }
        try {
            return new URI(getScheme(), null, str, uriPort.intValue(), "/" + getOptions().getUriPathString(), getOptions().getURIQueryCount() > 0 ? getOptions().getUriQueryString() : null, null).toASCIIString();
        } catch (URISyntaxException e2) {
            ALog.e(TAG, "cannot create URI from request", e2);
            return null;
        }
    }

    public Map<String, String> getUserContext() {
        return this.userContext;
    }

    public final boolean isObserve() {
        return getOptions().hasObserve() && getOptions().getObserve().intValue() == 0;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public void setCanceled(boolean z2) {
        super.setCanceled(z2);
        if (z2) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public final AlcsCoAPRequest setObserve() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.code != AlcsCoAPConstant.Code.GET) {
            ALog.e(TAG, "observe option can only be set on a GET request");
            return this;
        }
        getOptions().setObserve(0);
        return this;
    }

    public final AlcsCoAPRequest setObserveCancel() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.code != AlcsCoAPConstant.Code.GET) {
            ALog.e(TAG, "observe option can only be set on a GET request");
            return this;
        }
        getOptions().setObserve(1);
        return this;
    }

    public AlcsCoAPRequest setOptions(URI uri) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (uri == null) {
            ALog.e(TAG, "URI must not be null");
            return this;
        }
        if (!AlcsCoAPConstant.isSupportedScheme(uri.getScheme())) {
            ALog.e(TAG, "unsupported URI scheme: " + uri.getScheme());
            return this;
        }
        if (uri.getFragment() != null) {
            ALog.e(TAG, "URI must not contain a fragment");
            return this;
        }
        if (getDestination() == null) {
            ALog.e(TAG, "destination address must be set");
            return this;
        }
        if (uri.getHost() != null) {
            String lowerCase = uri.getHost().toLowerCase();
            if (IP_PATTERN.matcher(lowerCase).matches()) {
                try {
                    if (!InetAddress.getByName(lowerCase).equals(getDestination())) {
                        ALog.e(TAG, "URI's literal host IP address does not match request's destination address");
                        return this;
                    }
                } catch (UnknownHostException unused) {
                    ALog.w(AlcsConstUtils.TAG, "could not parse IP address of URI despite successful IP address pattern matching");
                }
            } else {
                getOptions().setUriHost(lowerCase);
            }
        }
        this.scheme = uri.getScheme().toLowerCase();
        int port = uri.getPort();
        if (port <= 0) {
            port = AlcsCoAPConstant.getDefaultPort(this.scheme);
        }
        setDestinationPort(port);
        String path = uri.getPath();
        if (path != null && path.length() > 1) {
            getOptions().setUriPath(path);
        }
        String query = uri.getQuery();
        if (query != null) {
            getOptions().setUriQuery(query);
        }
        return this;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public void setRejected(boolean z2) {
        super.setRejected(z2);
        if (z2) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public void setRequestCode(int i2) {
        this.code = AlcsCoAPConstant.Code.valueOf(i2);
    }

    public void setScheme(String str) {
        this.scheme = str;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public void setSendError(Throwable th) {
        super.setSendError(th);
        if (th != null) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public AlcsCoAPRequest setSenderIdentity(Principal principal) {
        this.senderIdentity = principal;
        return this;
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public void setTimedOut(boolean z2) {
        super.setTimedOut(z2);
        if (z2) {
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public AlcsCoAPRequest setURI(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str2;
        if (str == null) {
            ALog.e(TAG, "URI must not be null");
            return this;
        }
        try {
            if (str.contains(HttpConstant.SCHEME_SPLIT)) {
                str2 = str;
            } else {
                str2 = "coap://" + str;
                ALog.w(AlcsConstUtils.TAG, "update your code to supply an RFC 7252 compliant URI including a scheme");
            }
            return setURI(new URI(str2));
        } catch (URISyntaxException e2) {
            ALog.e(TAG, "invalid uri: " + str, e2);
            return this;
        }
    }

    public AlcsCoAPRequest setUserContext(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            this.userContext = Collections.emptyMap();
        } else {
            this.userContext = Collections.unmodifiableMap(new HashMap(map));
        }
        return this;
    }

    public AlcsCoAPRequest(int i2) {
        super(AlcsCoAPConstant.Type.CON);
        this.code = AlcsCoAPConstant.Code.valueOf(i2);
    }

    @Override // com.aliyun.alink.linksdk.alcs.coap.AlcsCoAPMessage
    public AlcsCoAPRequest setPayload(String str) {
        super.setPayload(str);
        return this;
    }

    public AlcsCoAPRequest(AlcsCoAPConstant.Code code, AlcsCoAPConstant.Type type) {
        super(type);
        this.code = code;
    }

    public AlcsCoAPRequest setURI(URI uri) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (uri == null) {
            ALog.e(TAG, "URI must not be null");
            return this;
        }
        String host = uri.getHost() == null ? "localhost" : uri.getHost();
        try {
            setDestination(InetAddress.getByName(host));
            return setOptions(new URI(uri.getScheme(), null, host, uri.getPort(), uri.getPath(), uri.getQuery(), uri.getFragment()));
        } catch (URISyntaxException e2) {
            ALog.w(AlcsConstUtils.TAG, "cannot set URI on request" + e2);
            return this;
        } catch (UnknownHostException unused) {
            ALog.e(TAG, "cannot resolve host name: " + host);
            return this;
        }
    }
}
