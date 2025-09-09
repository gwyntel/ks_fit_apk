package okhttp3;

import com.alipay.sdk.m.n.a;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpDate;

/* loaded from: classes5.dex */
public final class Cookie {
    private final String domain;
    private final long expiresAt;
    private final boolean hostOnly;
    private final boolean httpOnly;
    private final String name;
    private final String path;
    private final boolean persistent;
    private final boolean secure;
    private final String value;
    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
    private static final Pattern MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
    private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        String f26121a;

        /* renamed from: b, reason: collision with root package name */
        String f26122b;

        /* renamed from: d, reason: collision with root package name */
        String f26124d;

        /* renamed from: f, reason: collision with root package name */
        boolean f26126f;

        /* renamed from: g, reason: collision with root package name */
        boolean f26127g;

        /* renamed from: h, reason: collision with root package name */
        boolean f26128h;

        /* renamed from: i, reason: collision with root package name */
        boolean f26129i;

        /* renamed from: c, reason: collision with root package name */
        long f26123c = HttpDate.MAX_DATE;

        /* renamed from: e, reason: collision with root package name */
        String f26125e = "/";

        public Cookie build() {
            return new Cookie(this);
        }

        public Builder domain(String str) {
            return domain(str, false);
        }

        public Builder expiresAt(long j2) {
            if (j2 <= 0) {
                j2 = Long.MIN_VALUE;
            }
            if (j2 > HttpDate.MAX_DATE) {
                j2 = 253402300799999L;
            }
            this.f26123c = j2;
            this.f26128h = true;
            return this;
        }

        public Builder hostOnlyDomain(String str) {
            return domain(str, true);
        }

        public Builder httpOnly() {
            this.f26127g = true;
            return this;
        }

        public Builder name(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (!str.trim().equals(str)) {
                throw new IllegalArgumentException("name is not trimmed");
            }
            this.f26121a = str;
            return this;
        }

        public Builder path(String str) {
            if (!str.startsWith("/")) {
                throw new IllegalArgumentException("path must start with '/'");
            }
            this.f26125e = str;
            return this;
        }

        public Builder secure() {
            this.f26126f = true;
            return this;
        }

        public Builder value(String str) {
            if (str == null) {
                throw new NullPointerException("value == null");
            }
            if (!str.trim().equals(str)) {
                throw new IllegalArgumentException("value is not trimmed");
            }
            this.f26122b = str;
            return this;
        }

        private Builder domain(String str, boolean z2) {
            if (str == null) {
                throw new NullPointerException("domain == null");
            }
            String strCanonicalizeHost = Util.canonicalizeHost(str);
            if (strCanonicalizeHost != null) {
                this.f26124d = strCanonicalizeHost;
                this.f26129i = z2;
                return this;
            }
            throw new IllegalArgumentException("unexpected domain: " + str);
        }
    }

    private Cookie(String str, String str2, long j2, String str3, String str4, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.name = str;
        this.value = str2;
        this.expiresAt = j2;
        this.domain = str3;
        this.path = str4;
        this.secure = z2;
        this.httpOnly = z3;
        this.hostOnly = z4;
        this.persistent = z5;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00c1 A[PHI: r0
      0x00c1: PHI (r0v16 long) = (r0v2 long), (r0v6 long) binds: [B:41:0x00bf, B:52:0x00e2] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static okhttp3.Cookie a(long r23, okhttp3.HttpUrl r25, java.lang.String r26) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.Cookie.a(long, okhttp3.HttpUrl, java.lang.String):okhttp3.Cookie");
    }

    private static int dateCharacterOffset(String str, int i2, int i3, boolean z2) {
        while (i2 < i3) {
            char cCharAt = str.charAt(i2);
            if (((cCharAt < ' ' && cCharAt != '\t') || cCharAt >= 127 || (cCharAt >= '0' && cCharAt <= '9') || ((cCharAt >= 'a' && cCharAt <= 'z') || ((cCharAt >= 'A' && cCharAt <= 'Z') || cCharAt == ':'))) == (!z2)) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    private static boolean domainMatch(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        return str.endsWith(str2) && str.charAt((str.length() - str2.length()) - 1) == '.' && !Util.verifyAsIpAddress(str);
    }

    @Nullable
    public static Cookie parse(HttpUrl httpUrl, String str) {
        return a(System.currentTimeMillis(), httpUrl, str);
    }

    public static List<Cookie> parseAll(HttpUrl httpUrl, Headers headers) {
        List<String> listValues = headers.values("Set-Cookie");
        int size = listValues.size();
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < size; i2++) {
            Cookie cookie = parse(httpUrl, listValues.get(i2));
            if (cookie != null) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(cookie);
            }
        }
        return arrayList != null ? Collections.unmodifiableList(arrayList) : Collections.emptyList();
    }

    private static String parseDomain(String str) {
        if (str.endsWith(".")) {
            throw new IllegalArgumentException();
        }
        if (str.startsWith(".")) {
            str = str.substring(1);
        }
        String strCanonicalizeHost = Util.canonicalizeHost(str);
        if (strCanonicalizeHost != null) {
            return strCanonicalizeHost;
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static long parseExpires(java.lang.String r12, int r13, int r14) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 287
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.Cookie.parseExpires(java.lang.String, int, int):long");
    }

    private static long parseMaxAge(String str) throws NumberFormatException {
        try {
            long j2 = Long.parseLong(str);
            if (j2 <= 0) {
                return Long.MIN_VALUE;
            }
            return j2;
        } catch (NumberFormatException e2) {
            if (str.matches("-?\\d+")) {
                return str.startsWith(Constants.ACCEPT_TIME_SEPARATOR_SERVER) ? Long.MIN_VALUE : Long.MAX_VALUE;
            }
            throw e2;
        }
    }

    private static boolean pathMatch(HttpUrl httpUrl, String str) {
        String strEncodedPath = httpUrl.encodedPath();
        if (strEncodedPath.equals(str)) {
            return true;
        }
        if (strEncodedPath.startsWith(str)) {
            return str.endsWith("/") || strEncodedPath.charAt(str.length()) == '/';
        }
        return false;
    }

    String b(boolean z2) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(a.f9565h);
        sb.append(this.value);
        if (this.persistent) {
            if (this.expiresAt == Long.MIN_VALUE) {
                sb.append("; max-age=0");
            } else {
                sb.append("; expires=");
                sb.append(HttpDate.format(new Date(this.expiresAt)));
            }
        }
        if (!this.hostOnly) {
            sb.append("; domain=");
            if (z2) {
                sb.append(".");
            }
            sb.append(this.domain);
        }
        sb.append("; path=");
        sb.append(this.path);
        if (this.secure) {
            sb.append("; secure");
        }
        if (this.httpOnly) {
            sb.append("; httponly");
        }
        return sb.toString();
    }

    public String domain() {
        return this.domain;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Cookie)) {
            return false;
        }
        Cookie cookie = (Cookie) obj;
        return cookie.name.equals(this.name) && cookie.value.equals(this.value) && cookie.domain.equals(this.domain) && cookie.path.equals(this.path) && cookie.expiresAt == this.expiresAt && cookie.secure == this.secure && cookie.httpOnly == this.httpOnly && cookie.persistent == this.persistent && cookie.hostOnly == this.hostOnly;
    }

    public long expiresAt() {
        return this.expiresAt;
    }

    public int hashCode() {
        int iHashCode = (((((((527 + this.name.hashCode()) * 31) + this.value.hashCode()) * 31) + this.domain.hashCode()) * 31) + this.path.hashCode()) * 31;
        long j2 = this.expiresAt;
        return ((((((((iHashCode + ((int) (j2 ^ (j2 >>> 32)))) * 31) + (!this.secure ? 1 : 0)) * 31) + (!this.httpOnly ? 1 : 0)) * 31) + (!this.persistent ? 1 : 0)) * 31) + (!this.hostOnly ? 1 : 0);
    }

    public boolean hostOnly() {
        return this.hostOnly;
    }

    public boolean httpOnly() {
        return this.httpOnly;
    }

    public boolean matches(HttpUrl httpUrl) {
        if ((this.hostOnly ? httpUrl.host().equals(this.domain) : domainMatch(httpUrl.host(), this.domain)) && pathMatch(httpUrl, this.path)) {
            return !this.secure || httpUrl.isHttps();
        }
        return false;
    }

    public String name() {
        return this.name;
    }

    public String path() {
        return this.path;
    }

    public boolean persistent() {
        return this.persistent;
    }

    public boolean secure() {
        return this.secure;
    }

    public String toString() {
        return b(false);
    }

    public String value() {
        return this.value;
    }

    Cookie(Builder builder) {
        String str = builder.f26121a;
        if (str != null) {
            String str2 = builder.f26122b;
            if (str2 != null) {
                String str3 = builder.f26124d;
                if (str3 != null) {
                    this.name = str;
                    this.value = str2;
                    this.expiresAt = builder.f26123c;
                    this.domain = str3;
                    this.path = builder.f26125e;
                    this.secure = builder.f26126f;
                    this.httpOnly = builder.f26127g;
                    this.persistent = builder.f26128h;
                    this.hostOnly = builder.f26129i;
                    return;
                }
                throw new NullPointerException("builder.domain == null");
            }
            throw new NullPointerException("builder.value == null");
        }
        throw new NullPointerException("builder.name == null");
    }
}
