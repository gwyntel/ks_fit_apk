package anet.channel.util;

import java.net.MalformedURLException;
import java.net.URL;

/* loaded from: classes2.dex */
public class HttpUrl {
    private String host;
    private volatile boolean isSchemeLocked;
    private String path;
    private int port;
    private String scheme;
    private String simpleUrl;
    private String url;

    private HttpUrl() {
        this.isSchemeLocked = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x00af, code lost:
    
        if (r5 <= 65535) goto L116;
     */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0130  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static anet.channel.util.HttpUrl parse(java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.HttpUrl.parse(java.lang.String):anet.channel.util.HttpUrl");
    }

    public boolean containsNonDefaultPort() {
        return this.port != 0 && (("http".equals(this.scheme) && this.port != 80) || ("https".equals(this.scheme) && this.port != 443));
    }

    public void downgradeSchemeAndLock() {
        this.isSchemeLocked = true;
        if ("http".equals(this.scheme)) {
            return;
        }
        this.scheme = "http";
        String str = this.url;
        this.url = StringUtils.concatString("http", ":", str.substring(str.indexOf("//")));
    }

    public int getPort() {
        return this.port;
    }

    public String host() {
        return this.host;
    }

    public boolean isSchemeLocked() {
        return this.isSchemeLocked;
    }

    public void lockScheme() {
        this.isSchemeLocked = true;
    }

    public String path() {
        return this.path;
    }

    public void replaceIpAndPort(String str, int i2) {
        if (str != null) {
            int iIndexOf = this.url.indexOf("//") + 2;
            while (iIndexOf < this.url.length() && this.url.charAt(iIndexOf) != '/') {
                iIndexOf++;
            }
            boolean zB = anet.channel.strategy.utils.c.b(str);
            StringBuilder sb = new StringBuilder(this.url.length() + str.length());
            sb.append(this.scheme);
            sb.append(HttpConstant.SCHEME_SPLIT);
            if (zB) {
                sb.append('[');
            }
            sb.append(str);
            if (zB) {
                sb.append(']');
            }
            if (i2 != 0) {
                sb.append(':');
                sb.append(i2);
            } else if (this.port != 0) {
                sb.append(':');
                sb.append(this.port);
            }
            sb.append(this.url.substring(iIndexOf));
            this.url = sb.toString();
        }
    }

    public String scheme() {
        return this.scheme;
    }

    public void setScheme(String str) {
        if (this.isSchemeLocked || str.equalsIgnoreCase(this.scheme)) {
            return;
        }
        this.scheme = str;
        String str2 = this.url;
        String strConcatString = StringUtils.concatString(str, ":", str2.substring(str2.indexOf("//")));
        this.url = strConcatString;
        this.simpleUrl = StringUtils.concatString(str, ":", this.simpleUrl.substring(strConcatString.indexOf("//")));
    }

    public String simpleUrlString() {
        return this.simpleUrl;
    }

    public String toString() {
        return this.url;
    }

    public URL toURL() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException unused) {
            return null;
        }
    }

    public String urlString() {
        return this.url;
    }

    public HttpUrl(HttpUrl httpUrl) {
        this.isSchemeLocked = false;
        this.scheme = httpUrl.scheme;
        this.host = httpUrl.host;
        this.path = httpUrl.path;
        this.url = httpUrl.url;
        this.simpleUrl = httpUrl.simpleUrl;
        this.isSchemeLocked = httpUrl.isSchemeLocked;
    }
}
