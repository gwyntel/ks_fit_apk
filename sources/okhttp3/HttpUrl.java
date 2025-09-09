package okhttp3;

import anet.channel.util.HttpConstant;
import com.alipay.sdk.m.n.a;
import com.taobao.accs.common.Constants;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import kotlin.text.Typography;
import okhttp3.internal.Util;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;
import okio.Buffer;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes5.dex */
public final class HttpUrl {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: a, reason: collision with root package name */
    final String f26132a;

    /* renamed from: b, reason: collision with root package name */
    final String f26133b;

    /* renamed from: c, reason: collision with root package name */
    final int f26134c;

    @Nullable
    private final String fragment;
    private final String password;
    private final List<String> pathSegments;

    @Nullable
    private final List<String> queryNamesAndValues;
    private final String url;
    private final String username;

    HttpUrl(Builder builder) {
        this.f26132a = builder.f26135a;
        this.username = h(builder.f26136b, false);
        this.password = h(builder.f26137c, false);
        this.f26133b = builder.f26138d;
        this.f26134c = builder.a();
        this.pathSegments = percentDecode(builder.f26140f, false);
        List<String> list = builder.f26141g;
        this.queryNamesAndValues = list != null ? percentDecode(list, true) : null;
        String str = builder.f26142h;
        this.fragment = str != null ? h(str, false) : null;
        this.url = builder.toString();
    }

    static String a(String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5, Charset charset) {
        int iCharCount = i2;
        while (iCharCount < i3) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt < 32 || iCodePointAt == 127 || (iCodePointAt >= 128 && z5)) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, iCharCount);
                d(buffer, str, iCharCount, i3, str2, z2, z3, z4, z5, charset);
                return buffer.readUtf8();
            }
            if (str2.indexOf(iCodePointAt) != -1 || ((iCodePointAt == 37 && (!z2 || (z3 && !j(str, iCharCount, i3)))) || (iCodePointAt == 43 && z4))) {
                Buffer buffer2 = new Buffer();
                buffer2.writeUtf8(str, i2, iCharCount);
                d(buffer2, str, iCharCount, i3, str2, z2, z3, z4, z5, charset);
                return buffer2.readUtf8();
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str.substring(i2, i3);
    }

    static String b(String str, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        return a(str, 0, str.length(), str2, z2, z3, z4, z5, null);
    }

    static String c(String str, String str2, boolean z2, boolean z3, boolean z4, boolean z5, Charset charset) {
        return a(str, 0, str.length(), str2, z2, z3, z4, z5, charset);
    }

    static void d(Buffer buffer, String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5, Charset charset) {
        Buffer buffer2 = null;
        while (i2 < i3) {
            int iCodePointAt = str.codePointAt(i2);
            if (!z2 || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                if (iCodePointAt == 43 && z4) {
                    buffer.writeUtf8(z2 ? MqttTopic.SINGLE_LEVEL_WILDCARD : "%2B");
                } else if (iCodePointAt < 32 || iCodePointAt == 127 || ((iCodePointAt >= 128 && z5) || str2.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && (!z2 || (z3 && !j(str, i2, i3)))))) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    if (charset == null || charset.equals(Util.UTF_8)) {
                        buffer2.writeUtf8CodePoint(iCodePointAt);
                    } else {
                        buffer2.writeString(str, i2, Character.charCount(iCodePointAt) + i2, charset);
                    }
                    while (!buffer2.exhausted()) {
                        byte b2 = buffer2.readByte();
                        buffer.writeByte(37);
                        char[] cArr = HEX_DIGITS;
                        buffer.writeByte((int) cArr[((b2 & 255) >> 4) & 15]);
                        buffer.writeByte((int) cArr[b2 & 15]);
                    }
                } else {
                    buffer.writeUtf8CodePoint(iCodePointAt);
                }
            }
            i2 += Character.charCount(iCodePointAt);
        }
    }

    public static int defaultPort(String str) {
        if (str.equals("http")) {
            return 80;
        }
        if (str.equals("https")) {
            return Constants.PORT;
        }
        return -1;
    }

    static void e(StringBuilder sb, List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String str = (String) list.get(i2);
            String str2 = (String) list.get(i2 + 1);
            if (i2 > 0) {
                sb.append(Typography.amp);
            }
            sb.append(str);
            if (str2 != null) {
                sb.append(a.f9565h);
                sb.append(str2);
            }
        }
    }

    static void f(StringBuilder sb, List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append((String) list.get(i2));
        }
    }

    static String g(String str, int i2, int i3, boolean z2) {
        for (int i4 = i2; i4 < i3; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt == '%' || (cCharAt == '+' && z2)) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, i4);
                i(buffer, str, i4, i3, z2);
                return buffer.readUtf8();
            }
        }
        return str.substring(i2, i3);
    }

    public static HttpUrl get(String str) {
        return new Builder().b(null, str).build();
    }

    static String h(String str, boolean z2) {
        return g(str, 0, str.length(), z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void i(okio.Buffer r5, java.lang.String r6, int r7, int r8, boolean r9) {
        /*
        L0:
            if (r7 >= r8) goto L42
            int r0 = r6.codePointAt(r7)
            r1 = 37
            if (r0 != r1) goto L2d
            int r1 = r7 + 2
            if (r1 >= r8) goto L2d
            int r2 = r7 + 1
            char r2 = r6.charAt(r2)
            int r2 = okhttp3.internal.Util.decodeHexDigit(r2)
            char r3 = r6.charAt(r1)
            int r3 = okhttp3.internal.Util.decodeHexDigit(r3)
            r4 = -1
            if (r2 == r4) goto L39
            if (r3 == r4) goto L39
            int r7 = r2 << 4
            int r7 = r7 + r3
            r5.writeByte(r7)
            r7 = r1
            goto L3c
        L2d:
            r1 = 43
            if (r0 != r1) goto L39
            if (r9 == 0) goto L39
            r1 = 32
            r5.writeByte(r1)
            goto L3c
        L39:
            r5.writeUtf8CodePoint(r0)
        L3c:
            int r0 = java.lang.Character.charCount(r0)
            int r7 = r7 + r0
            goto L0
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.HttpUrl.i(okio.Buffer, java.lang.String, int, int, boolean):void");
    }

    static boolean j(String str, int i2, int i3) {
        int i4 = i2 + 2;
        return i4 < i3 && str.charAt(i2) == '%' && Util.decodeHexDigit(str.charAt(i2 + 1)) != -1 && Util.decodeHexDigit(str.charAt(i4)) != -1;
    }

    static List k(String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 <= str.length()) {
            int iIndexOf = str.indexOf(38, i2);
            if (iIndexOf == -1) {
                iIndexOf = str.length();
            }
            int iIndexOf2 = str.indexOf(61, i2);
            if (iIndexOf2 == -1 || iIndexOf2 > iIndexOf) {
                arrayList.add(str.substring(i2, iIndexOf));
                arrayList.add(null);
            } else {
                arrayList.add(str.substring(i2, iIndexOf2));
                arrayList.add(str.substring(iIndexOf2 + 1, iIndexOf));
            }
            i2 = iIndexOf + 1;
        }
        return arrayList;
    }

    @Nullable
    public static HttpUrl parse(String str) {
        try {
            return get(str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private List<String> percentDecode(List<String> list, boolean z2) {
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            String str = list.get(i2);
            arrayList.add(str != null ? h(str, z2) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Nullable
    public String encodedFragment() {
        if (this.fragment == null) {
            return null;
        }
        return this.url.substring(this.url.indexOf(35) + 1);
    }

    public String encodedPassword() {
        if (this.password.isEmpty()) {
            return "";
        }
        return this.url.substring(this.url.indexOf(58, this.f26132a.length() + 3) + 1, this.url.indexOf(64));
    }

    public String encodedPath() {
        int iIndexOf = this.url.indexOf(47, this.f26132a.length() + 3);
        String str = this.url;
        return this.url.substring(iIndexOf, Util.delimiterOffset(str, iIndexOf, str.length(), "?#"));
    }

    public List<String> encodedPathSegments() {
        int iIndexOf = this.url.indexOf(47, this.f26132a.length() + 3);
        String str = this.url;
        int iDelimiterOffset = Util.delimiterOffset(str, iIndexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iDelimiterOffset) {
            int i2 = iIndexOf + 1;
            int iDelimiterOffset2 = Util.delimiterOffset(this.url, i2, iDelimiterOffset, IOUtils.DIR_SEPARATOR_UNIX);
            arrayList.add(this.url.substring(i2, iDelimiterOffset2));
            iIndexOf = iDelimiterOffset2;
        }
        return arrayList;
    }

    @Nullable
    public String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int iIndexOf = this.url.indexOf(63) + 1;
        String str = this.url;
        return this.url.substring(iIndexOf, Util.delimiterOffset(str, iIndexOf, str.length(), '#'));
    }

    public String encodedUsername() {
        if (this.username.isEmpty()) {
            return "";
        }
        int length = this.f26132a.length() + 3;
        String str = this.url;
        return this.url.substring(length, Util.delimiterOffset(str, length, str.length(), ":@"));
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof HttpUrl) && ((HttpUrl) obj).url.equals(this.url);
    }

    @Nullable
    public String fragment() {
        return this.fragment;
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    public String host() {
        return this.f26133b;
    }

    public boolean isHttps() {
        return this.f26132a.equals("https");
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.f26135a = this.f26132a;
        builder.f26136b = encodedUsername();
        builder.f26137c = encodedPassword();
        builder.f26138d = this.f26133b;
        builder.f26139e = this.f26134c != defaultPort(this.f26132a) ? this.f26134c : -1;
        builder.f26140f.clear();
        builder.f26140f.addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.f26142h = encodedFragment();
        return builder;
    }

    public String password() {
        return this.password;
    }

    public List<String> pathSegments() {
        return this.pathSegments;
    }

    public int pathSize() {
        return this.pathSegments.size();
    }

    public int port() {
        return this.f26134c;
    }

    @Nullable
    public String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        e(sb, this.queryNamesAndValues);
        return sb.toString();
    }

    @Nullable
    public String queryParameter(String str) {
        List<String> list = this.queryNamesAndValues;
        if (list == null) {
            return null;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            if (str.equals(this.queryNamesAndValues.get(i2))) {
                return this.queryNamesAndValues.get(i2 + 1);
            }
        }
        return null;
    }

    public String queryParameterName(int i2) {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.get(i2 * 2);
        }
        throw new IndexOutOfBoundsException();
    }

    public Set<String> queryParameterNames() {
        if (this.queryNamesAndValues == null) {
            return Collections.emptySet();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int size = this.queryNamesAndValues.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            linkedHashSet.add(this.queryNamesAndValues.get(i2));
        }
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public String queryParameterValue(int i2) {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.get((i2 * 2) + 1);
        }
        throw new IndexOutOfBoundsException();
    }

    public List<String> queryParameterValues(String str) {
        if (this.queryNamesAndValues == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = this.queryNamesAndValues.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            if (str.equals(this.queryNamesAndValues.get(i2))) {
                arrayList.add(this.queryNamesAndValues.get(i2 + 1));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int querySize() {
        List<String> list = this.queryNamesAndValues;
        if (list != null) {
            return list.size() / 2;
        }
        return 0;
    }

    public String redact() {
        return newBuilder("/...").username("").password("").build().toString();
    }

    @Nullable
    public HttpUrl resolve(String str) {
        Builder builderNewBuilder = newBuilder(str);
        if (builderNewBuilder != null) {
            return builderNewBuilder.build();
        }
        return null;
    }

    public String scheme() {
        return this.f26132a;
    }

    public String toString() {
        return this.url;
    }

    @Nullable
    public String topPrivateDomain() {
        if (Util.verifyAsIpAddress(this.f26133b)) {
            return null;
        }
        return PublicSuffixDatabase.get().getEffectiveTldPlusOne(this.f26133b);
    }

    public URI uri() {
        String string = newBuilder().c().toString();
        try {
            return new URI(string);
        } catch (URISyntaxException e2) {
            try {
                return URI.create(string.replaceAll("[\\u0000-\\u001F\\u007F-\\u009F\\p{javaWhitespace}]", ""));
            } catch (Exception unused) {
                throw new RuntimeException(e2);
            }
        }
    }

    public URL url() {
        try {
            return new URL(this.url);
        } catch (MalformedURLException e2) {
            throw new RuntimeException(e2);
        }
    }

    public String username() {
        return this.username;
    }

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        String f26135a;

        /* renamed from: d, reason: collision with root package name */
        String f26138d;

        /* renamed from: f, reason: collision with root package name */
        final List f26140f;

        /* renamed from: g, reason: collision with root package name */
        List f26141g;

        /* renamed from: h, reason: collision with root package name */
        String f26142h;

        /* renamed from: b, reason: collision with root package name */
        String f26136b = "";

        /* renamed from: c, reason: collision with root package name */
        String f26137c = "";

        /* renamed from: e, reason: collision with root package name */
        int f26139e = -1;

        public Builder() {
            ArrayList arrayList = new ArrayList();
            this.f26140f = arrayList;
            arrayList.add("");
        }

        private static String canonicalizeHost(String str, int i2, int i3) {
            return Util.canonicalizeHost(HttpUrl.g(str, i2, i3, false));
        }

        private boolean isDot(String str) {
            return str.equals(".") || str.equalsIgnoreCase("%2e");
        }

        private boolean isDotDot(String str) {
            return str.equals("..") || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        private static int parsePort(String str, int i2, int i3) throws NumberFormatException {
            int i4;
            try {
                i4 = Integer.parseInt(HttpUrl.a(str, i2, i3, "", false, false, false, true, null));
            } catch (NumberFormatException unused) {
            }
            if (i4 <= 0 || i4 > 65535) {
                return -1;
            }
            return i4;
        }

        private void pop() {
            if (!((String) this.f26140f.remove(r0.size() - 1)).isEmpty() || this.f26140f.isEmpty()) {
                this.f26140f.add("");
            } else {
                this.f26140f.set(r0.size() - 1, "");
            }
        }

        private static int portColonOffset(String str, int i2, int i3) {
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt == ':') {
                    return i2;
                }
                if (cCharAt == '[') {
                    do {
                        i2++;
                        if (i2 < i3) {
                        }
                    } while (str.charAt(i2) != ']');
                }
                i2++;
            }
            return i3;
        }

        private void push(String str, int i2, int i3, boolean z2, boolean z3) {
            String strA = HttpUrl.a(str, i2, i3, " \"<>^`{}|/\\?#", z3, false, false, true, null);
            if (isDot(strA)) {
                return;
            }
            if (isDotDot(strA)) {
                pop();
                return;
            }
            if (((String) this.f26140f.get(r11.size() - 1)).isEmpty()) {
                this.f26140f.set(r11.size() - 1, strA);
            } else {
                this.f26140f.add(strA);
            }
            if (z2) {
                this.f26140f.add("");
            }
        }

        private void removeAllCanonicalQueryParameters(String str) {
            for (int size = this.f26141g.size() - 2; size >= 0; size -= 2) {
                if (str.equals(this.f26141g.get(size))) {
                    this.f26141g.remove(size + 1);
                    this.f26141g.remove(size);
                    if (this.f26141g.isEmpty()) {
                        this.f26141g = null;
                        return;
                    }
                }
            }
        }

        private void resolvePath(String str, int i2, int i3) {
            if (i2 == i3) {
                return;
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt == '/' || cCharAt == '\\') {
                this.f26140f.clear();
                this.f26140f.add("");
                i2++;
            } else {
                List list = this.f26140f;
                list.set(list.size() - 1, "");
            }
            while (true) {
                int i4 = i2;
                if (i4 >= i3) {
                    return;
                }
                i2 = Util.delimiterOffset(str, i4, i3, "/\\");
                boolean z2 = i2 < i3;
                push(str, i4, i2, z2, true);
                if (z2) {
                    i2++;
                }
            }
        }

        private static int schemeDelimiterOffset(String str, int i2, int i3) {
            if (i3 - i2 < 2) {
                return -1;
            }
            char cCharAt = str.charAt(i2);
            if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                while (true) {
                    i2++;
                    if (i2 >= i3) {
                        break;
                    }
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 < 'a' || cCharAt2 > 'z') {
                        if (cCharAt2 < 'A' || cCharAt2 > 'Z') {
                            if (cCharAt2 < '0' || cCharAt2 > '9') {
                                if (cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.') {
                                    if (cCharAt2 == ':') {
                                        return i2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return -1;
        }

        private static int slashCount(String str, int i2, int i3) {
            int i4 = 0;
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt != '\\' && cCharAt != '/') {
                    break;
                }
                i4++;
                i2++;
            }
            return i4;
        }

        int a() {
            int i2 = this.f26139e;
            return i2 != -1 ? i2 : HttpUrl.defaultPort(this.f26135a);
        }

        public Builder addEncodedPathSegment(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegment == null");
            }
            push(str, 0, str.length(), false, true);
            return this;
        }

        public Builder addEncodedPathSegments(String str) {
            if (str != null) {
                return addPathSegments(str, true);
            }
            throw new NullPointerException("encodedPathSegments == null");
        }

        public Builder addEncodedQueryParameter(String str, @Nullable String str2) {
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.f26141g == null) {
                this.f26141g = new ArrayList();
            }
            this.f26141g.add(HttpUrl.b(str, " \"'<>#&=", true, false, true, true));
            this.f26141g.add(str2 != null ? HttpUrl.b(str2, " \"'<>#&=", true, false, true, true) : null);
            return this;
        }

        public Builder addPathSegment(String str) {
            if (str == null) {
                throw new NullPointerException("pathSegment == null");
            }
            push(str, 0, str.length(), false, false);
            return this;
        }

        public Builder addPathSegments(String str) {
            if (str != null) {
                return addPathSegments(str, false);
            }
            throw new NullPointerException("pathSegments == null");
        }

        public Builder addQueryParameter(String str, @Nullable String str2) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (this.f26141g == null) {
                this.f26141g = new ArrayList();
            }
            this.f26141g.add(HttpUrl.b(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
            this.f26141g.add(str2 != null ? HttpUrl.b(str2, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true) : null);
            return this;
        }

        Builder b(HttpUrl httpUrl, String str) throws NumberFormatException {
            int iDelimiterOffset;
            int i2;
            int iSkipLeadingAsciiWhitespace = Util.skipLeadingAsciiWhitespace(str, 0, str.length());
            int iSkipTrailingAsciiWhitespace = Util.skipTrailingAsciiWhitespace(str, iSkipLeadingAsciiWhitespace, str.length());
            int iSchemeDelimiterOffset = schemeDelimiterOffset(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace);
            if (iSchemeDelimiterOffset != -1) {
                if (str.regionMatches(true, iSkipLeadingAsciiWhitespace, "https:", 0, 6)) {
                    this.f26135a = "https";
                    iSkipLeadingAsciiWhitespace += 6;
                } else {
                    if (!str.regionMatches(true, iSkipLeadingAsciiWhitespace, "http:", 0, 5)) {
                        throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but was '" + str.substring(0, iSchemeDelimiterOffset) + "'");
                    }
                    this.f26135a = "http";
                    iSkipLeadingAsciiWhitespace += 5;
                }
            } else {
                if (httpUrl == null) {
                    throw new IllegalArgumentException("Expected URL scheme 'http' or 'https' but no colon was found");
                }
                this.f26135a = httpUrl.f26132a;
            }
            int iSlashCount = slashCount(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace);
            char c2 = '?';
            char c3 = '#';
            if (iSlashCount >= 2 || httpUrl == null || !httpUrl.f26132a.equals(this.f26135a)) {
                boolean z2 = false;
                boolean z3 = false;
                int i3 = iSkipLeadingAsciiWhitespace + iSlashCount;
                while (true) {
                    iDelimiterOffset = Util.delimiterOffset(str, i3, iSkipTrailingAsciiWhitespace, "@/\\?#");
                    char cCharAt = iDelimiterOffset != iSkipTrailingAsciiWhitespace ? str.charAt(iDelimiterOffset) : (char) 65535;
                    if (cCharAt == 65535 || cCharAt == c3 || cCharAt == '/' || cCharAt == '\\' || cCharAt == c2) {
                        break;
                    }
                    if (cCharAt == '@') {
                        if (z2) {
                            i2 = iDelimiterOffset;
                            this.f26137c += "%40" + HttpUrl.a(str, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                        } else {
                            int iDelimiterOffset2 = Util.delimiterOffset(str, i3, iDelimiterOffset, ':');
                            i2 = iDelimiterOffset;
                            String strA = HttpUrl.a(str, i3, iDelimiterOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                            if (z3) {
                                strA = this.f26136b + "%40" + strA;
                            }
                            this.f26136b = strA;
                            if (iDelimiterOffset2 != i2) {
                                this.f26137c = HttpUrl.a(str, iDelimiterOffset2 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true, null);
                                z2 = true;
                            }
                            z3 = true;
                        }
                        i3 = i2 + 1;
                    }
                    c2 = '?';
                    c3 = '#';
                }
                int iPortColonOffset = portColonOffset(str, i3, iDelimiterOffset);
                int i4 = iPortColonOffset + 1;
                if (i4 < iDelimiterOffset) {
                    this.f26138d = canonicalizeHost(str, i3, iPortColonOffset);
                    int port = parsePort(str, i4, iDelimiterOffset);
                    this.f26139e = port;
                    if (port == -1) {
                        throw new IllegalArgumentException("Invalid URL port: \"" + str.substring(i4, iDelimiterOffset) + Typography.quote);
                    }
                } else {
                    this.f26138d = canonicalizeHost(str, i3, iPortColonOffset);
                    this.f26139e = HttpUrl.defaultPort(this.f26135a);
                }
                if (this.f26138d == null) {
                    throw new IllegalArgumentException("Invalid URL host: \"" + str.substring(i3, iPortColonOffset) + Typography.quote);
                }
                iSkipLeadingAsciiWhitespace = iDelimiterOffset;
            } else {
                this.f26136b = httpUrl.encodedUsername();
                this.f26137c = httpUrl.encodedPassword();
                this.f26138d = httpUrl.f26133b;
                this.f26139e = httpUrl.f26134c;
                this.f26140f.clear();
                this.f26140f.addAll(httpUrl.encodedPathSegments());
                if (iSkipLeadingAsciiWhitespace == iSkipTrailingAsciiWhitespace || str.charAt(iSkipLeadingAsciiWhitespace) == '#') {
                    encodedQuery(httpUrl.encodedQuery());
                }
            }
            int iDelimiterOffset3 = Util.delimiterOffset(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace, "?#");
            resolvePath(str, iSkipLeadingAsciiWhitespace, iDelimiterOffset3);
            if (iDelimiterOffset3 < iSkipTrailingAsciiWhitespace && str.charAt(iDelimiterOffset3) == '?') {
                int iDelimiterOffset4 = Util.delimiterOffset(str, iDelimiterOffset3, iSkipTrailingAsciiWhitespace, '#');
                this.f26141g = HttpUrl.k(HttpUrl.a(str, iDelimiterOffset3 + 1, iDelimiterOffset4, " \"'<>#", true, false, true, true, null));
                iDelimiterOffset3 = iDelimiterOffset4;
            }
            if (iDelimiterOffset3 < iSkipTrailingAsciiWhitespace && str.charAt(iDelimiterOffset3) == '#') {
                this.f26142h = HttpUrl.a(str, 1 + iDelimiterOffset3, iSkipTrailingAsciiWhitespace, "", true, false, false, false, null);
            }
            return this;
        }

        public HttpUrl build() {
            if (this.f26135a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.f26138d != null) {
                return new HttpUrl(this);
            }
            throw new IllegalStateException("host == null");
        }

        Builder c() {
            int size = this.f26140f.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.f26140f.set(i2, HttpUrl.b((String) this.f26140f.get(i2), "[]", true, true, false, true));
            }
            List list = this.f26141g;
            if (list != null) {
                int size2 = list.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    String str = (String) this.f26141g.get(i3);
                    if (str != null) {
                        this.f26141g.set(i3, HttpUrl.b(str, "\\^`{|}", true, true, true, true));
                    }
                }
            }
            String str2 = this.f26142h;
            if (str2 != null) {
                this.f26142h = HttpUrl.b(str2, " \"#<>\\^`{|}", true, true, false, false);
            }
            return this;
        }

        public Builder encodedFragment(@Nullable String str) {
            this.f26142h = str != null ? HttpUrl.b(str, "", true, false, false, false) : null;
            return this;
        }

        public Builder encodedPassword(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPassword == null");
            }
            this.f26137c = HttpUrl.b(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder encodedPath(String str) {
            if (str == null) {
                throw new NullPointerException("encodedPath == null");
            }
            if (str.startsWith("/")) {
                resolvePath(str, 0, str.length());
                return this;
            }
            throw new IllegalArgumentException("unexpected encodedPath: " + str);
        }

        public Builder encodedQuery(@Nullable String str) {
            this.f26141g = str != null ? HttpUrl.k(HttpUrl.b(str, " \"'<>#", true, false, true, true)) : null;
            return this;
        }

        public Builder encodedUsername(String str) {
            if (str == null) {
                throw new NullPointerException("encodedUsername == null");
            }
            this.f26136b = HttpUrl.b(str, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
            return this;
        }

        public Builder fragment(@Nullable String str) {
            this.f26142h = str != null ? HttpUrl.b(str, "", false, false, false, false) : null;
            return this;
        }

        public Builder host(String str) {
            if (str == null) {
                throw new NullPointerException("host == null");
            }
            String strCanonicalizeHost = canonicalizeHost(str, 0, str.length());
            if (strCanonicalizeHost != null) {
                this.f26138d = strCanonicalizeHost;
                return this;
            }
            throw new IllegalArgumentException("unexpected host: " + str);
        }

        public Builder password(String str) {
            if (str == null) {
                throw new NullPointerException("password == null");
            }
            this.f26137c = HttpUrl.b(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        public Builder port(int i2) {
            if (i2 > 0 && i2 <= 65535) {
                this.f26139e = i2;
                return this;
            }
            throw new IllegalArgumentException("unexpected port: " + i2);
        }

        public Builder query(@Nullable String str) {
            this.f26141g = str != null ? HttpUrl.k(HttpUrl.b(str, " \"'<>#", false, false, true, true)) : null;
            return this;
        }

        public Builder removeAllEncodedQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("encodedName == null");
            }
            if (this.f26141g == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(HttpUrl.b(str, " \"'<>#&=", true, false, true, true));
            return this;
        }

        public Builder removeAllQueryParameters(String str) {
            if (str == null) {
                throw new NullPointerException("name == null");
            }
            if (this.f26141g == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(HttpUrl.b(str, " !\"#$&'(),/:;<=>?@[]\\^`{|}~", false, false, true, true));
            return this;
        }

        public Builder removePathSegment(int i2) {
            this.f26140f.remove(i2);
            if (this.f26140f.isEmpty()) {
                this.f26140f.add("");
            }
            return this;
        }

        public Builder scheme(String str) {
            if (str == null) {
                throw new NullPointerException("scheme == null");
            }
            if (str.equalsIgnoreCase("http")) {
                this.f26135a = "http";
            } else {
                if (!str.equalsIgnoreCase("https")) {
                    throw new IllegalArgumentException("unexpected scheme: " + str);
                }
                this.f26135a = "https";
            }
            return this;
        }

        public Builder setEncodedPathSegment(int i2, String str) {
            if (str == null) {
                throw new NullPointerException("encodedPathSegment == null");
            }
            String strA = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", true, false, false, true, null);
            this.f26140f.set(i2, strA);
            if (!isDot(strA) && !isDotDot(strA)) {
                return this;
            }
            throw new IllegalArgumentException("unexpected path segment: " + str);
        }

        public Builder setEncodedQueryParameter(String str, @Nullable String str2) {
            removeAllEncodedQueryParameters(str);
            addEncodedQueryParameter(str, str2);
            return this;
        }

        public Builder setPathSegment(int i2, String str) {
            if (str == null) {
                throw new NullPointerException("pathSegment == null");
            }
            String strA = HttpUrl.a(str, 0, str.length(), " \"<>^`{}|/\\?#", false, false, false, true, null);
            if (!isDot(strA) && !isDotDot(strA)) {
                this.f26140f.set(i2, strA);
                return this;
            }
            throw new IllegalArgumentException("unexpected path segment: " + str);
        }

        public Builder setQueryParameter(String str, @Nullable String str2) {
            removeAllQueryParameters(str);
            addQueryParameter(str, str2);
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            String str = this.f26135a;
            if (str != null) {
                sb.append(str);
                sb.append(HttpConstant.SCHEME_SPLIT);
            } else {
                sb.append("//");
            }
            if (!this.f26136b.isEmpty() || !this.f26137c.isEmpty()) {
                sb.append(this.f26136b);
                if (!this.f26137c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.f26137c);
                }
                sb.append('@');
            }
            String str2 = this.f26138d;
            if (str2 != null) {
                if (str2.indexOf(58) != -1) {
                    sb.append('[');
                    sb.append(this.f26138d);
                    sb.append(']');
                } else {
                    sb.append(this.f26138d);
                }
            }
            if (this.f26139e != -1 || this.f26135a != null) {
                int iA = a();
                String str3 = this.f26135a;
                if (str3 == null || iA != HttpUrl.defaultPort(str3)) {
                    sb.append(':');
                    sb.append(iA);
                }
            }
            HttpUrl.f(sb, this.f26140f);
            if (this.f26141g != null) {
                sb.append('?');
                HttpUrl.e(sb, this.f26141g);
            }
            if (this.f26142h != null) {
                sb.append('#');
                sb.append(this.f26142h);
            }
            return sb.toString();
        }

        public Builder username(String str) {
            if (str == null) {
                throw new NullPointerException("username == null");
            }
            this.f26136b = HttpUrl.b(str, " \"':;<=>@[]^`{}|/\\?#", false, false, false, true);
            return this;
        }

        private Builder addPathSegments(String str, boolean z2) {
            int i2 = 0;
            do {
                int iDelimiterOffset = Util.delimiterOffset(str, i2, str.length(), "/\\");
                push(str, i2, iDelimiterOffset, iDelimiterOffset < str.length(), z2);
                i2 = iDelimiterOffset + 1;
            } while (i2 <= str.length());
            return this;
        }
    }

    @Nullable
    public static HttpUrl get(URL url) {
        return parse(url.toString());
    }

    @Nullable
    public static HttpUrl get(URI uri) {
        return parse(uri.toString());
    }

    @Nullable
    public Builder newBuilder(String str) {
        try {
            return new Builder().b(this, str);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
