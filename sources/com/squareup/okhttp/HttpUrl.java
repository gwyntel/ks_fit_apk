package com.squareup.okhttp;

import anet.channel.util.HttpConstant;
import com.alipay.sdk.m.n.a;
import com.taobao.accs.common.Constants;
import java.net.IDN;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.text.Typography;
import okio.Buffer;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public final class HttpUrl {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private final String fragment;
    private final String host;
    private final String password;
    private final List<String> pathSegments;
    private final int port;
    private final List<String> queryNamesAndValues;
    private final String scheme;
    private final String url;
    private final String username;

    public static final class Builder {

        /* renamed from: a, reason: collision with root package name */
        String f19886a;

        /* renamed from: d, reason: collision with root package name */
        String f19889d;

        /* renamed from: f, reason: collision with root package name */
        final List f19891f;

        /* renamed from: g, reason: collision with root package name */
        List f19892g;

        /* renamed from: h, reason: collision with root package name */
        String f19893h;

        /* renamed from: b, reason: collision with root package name */
        String f19887b = "";

        /* renamed from: c, reason: collision with root package name */
        String f19888c = "";

        /* renamed from: e, reason: collision with root package name */
        int f19890e = -1;

        public Builder() {
            ArrayList arrayList = new ArrayList();
            this.f19891f = arrayList;
            arrayList.add("");
        }

        private static String canonicalizeHost(String str, int i2, int i3) {
            int length;
            String strL = HttpUrl.l(str, i2, i3);
            if (!strL.startsWith("[") || !strL.endsWith("]")) {
                String strDomainToAscii = domainToAscii(strL);
                if (strDomainToAscii != null && HttpUrl.delimiterOffset(strDomainToAscii, 0, length, "\u0000\t\n\r #%/:?@[\\]") == (length = strDomainToAscii.length())) {
                    return strDomainToAscii;
                }
                return null;
            }
            InetAddress inetAddressDecodeIpv6 = decodeIpv6(strL, 1, strL.length() - 1);
            if (inetAddressDecodeIpv6 == null) {
                return null;
            }
            byte[] address = inetAddressDecodeIpv6.getAddress();
            if (address.length == 16) {
                return inet6AddressToAscii(address);
            }
            throw new AssertionError();
        }

        private static boolean decodeIpv4Suffix(String str, int i2, int i3, byte[] bArr, int i4) {
            int i5 = i4;
            while (i2 < i3) {
                if (i5 == bArr.length) {
                    return false;
                }
                if (i5 != i4) {
                    if (str.charAt(i2) != '.') {
                        return false;
                    }
                    i2++;
                }
                int i6 = i2;
                int i7 = 0;
                while (i6 < i3) {
                    char cCharAt = str.charAt(i6);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    if ((i7 == 0 && i2 != i6) || (i7 = ((i7 * 10) + cCharAt) - 48) > 255) {
                        return false;
                    }
                    i6++;
                }
                if (i6 - i2 == 0) {
                    return false;
                }
                bArr[i5] = (byte) i7;
                i5++;
                i2 = i6;
            }
            return i5 == i4 + 4;
        }

        /* JADX WARN: Code restructure failed: missing block: B:42:0x0079, code lost:
        
            if (r4 == 16) goto L50;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x007b, code lost:
        
            if (r5 != (-1)) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x007d, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x007e, code lost:
        
            r12 = r4 - r5;
            java.lang.System.arraycopy(r1, r5, r1, 16 - r12, r12);
            java.util.Arrays.fill(r1, r5, (16 - r4) + r5, (byte) 0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x008e, code lost:
        
            return java.net.InetAddress.getByAddress(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0094, code lost:
        
            throw new java.lang.AssertionError();
         */
        /* JADX WARN: Removed duplicated region for block: B:31:0x004f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static java.net.InetAddress decodeIpv6(java.lang.String r12, int r13, int r14) {
            /*
                r0 = 16
                byte[] r1 = new byte[r0]
                r2 = 0
                r3 = -1
                r4 = r2
                r5 = r3
                r6 = r5
            L9:
                r7 = 0
                if (r13 >= r14) goto L79
                if (r4 != r0) goto Lf
                return r7
            Lf:
                int r8 = r13 + 2
                r9 = 2
                if (r8 > r14) goto L27
                java.lang.String r10 = "::"
                boolean r10 = r12.regionMatches(r13, r10, r2, r9)
                if (r10 == 0) goto L27
                if (r5 == r3) goto L1f
                return r7
            L1f:
                int r4 = r4 + 2
                r5 = r4
                if (r8 != r14) goto L25
                goto L79
            L25:
                r6 = r8
                goto L4b
            L27:
                if (r4 == 0) goto L34
                java.lang.String r8 = ":"
                r10 = 1
                boolean r8 = r12.regionMatches(r13, r8, r2, r10)
                if (r8 == 0) goto L36
                int r13 = r13 + 1
            L34:
                r6 = r13
                goto L4b
            L36:
                java.lang.String r8 = "."
                boolean r13 = r12.regionMatches(r13, r8, r2, r10)
                if (r13 == 0) goto L4a
                int r13 = r4 + (-2)
                boolean r12 = decodeIpv4Suffix(r12, r6, r14, r1, r13)
                if (r12 != 0) goto L47
                return r7
            L47:
                int r4 = r4 + 2
                goto L79
            L4a:
                return r7
            L4b:
                r8 = r2
                r13 = r6
            L4d:
                if (r13 >= r14) goto L60
                char r10 = r12.charAt(r13)
                int r10 = com.squareup.okhttp.HttpUrl.h(r10)
                if (r10 != r3) goto L5a
                goto L60
            L5a:
                int r8 = r8 << 4
                int r8 = r8 + r10
                int r13 = r13 + 1
                goto L4d
            L60:
                int r10 = r13 - r6
                if (r10 == 0) goto L78
                r11 = 4
                if (r10 <= r11) goto L68
                goto L78
            L68:
                int r7 = r4 + 1
                int r10 = r8 >>> 8
                r10 = r10 & 255(0xff, float:3.57E-43)
                byte r10 = (byte) r10
                r1[r4] = r10
                int r4 = r4 + r9
                r8 = r8 & 255(0xff, float:3.57E-43)
                byte r8 = (byte) r8
                r1[r7] = r8
                goto L9
            L78:
                return r7
            L79:
                if (r4 == r0) goto L8a
                if (r5 != r3) goto L7e
                return r7
            L7e:
                int r12 = r4 - r5
                int r13 = 16 - r12
                java.lang.System.arraycopy(r1, r5, r1, r13, r12)
                int r0 = r0 - r4
                int r0 = r0 + r5
                java.util.Arrays.fill(r1, r5, r0, r2)
            L8a:
                java.net.InetAddress r12 = java.net.InetAddress.getByAddress(r1)     // Catch: java.net.UnknownHostException -> L8f
                return r12
            L8f:
                java.lang.AssertionError r12 = new java.lang.AssertionError
                r12.<init>()
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.HttpUrl.Builder.decodeIpv6(java.lang.String, int, int):java.net.InetAddress");
        }

        private static String domainToAscii(String str) {
            try {
                String lowerCase = IDN.toASCII(str).toLowerCase(Locale.US);
                if (lowerCase.isEmpty()) {
                    return null;
                }
                return lowerCase;
            } catch (IllegalArgumentException unused) {
                return null;
            }
        }

        private static String inet6AddressToAscii(byte[] bArr) {
            int i2 = -1;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i4 < bArr.length) {
                int i6 = i4;
                while (i6 < 16 && bArr[i6] == 0 && bArr[i6 + 1] == 0) {
                    i6 += 2;
                }
                int i7 = i6 - i4;
                if (i7 > i5) {
                    i2 = i4;
                    i5 = i7;
                }
                i4 = i6 + 2;
            }
            Buffer buffer = new Buffer();
            while (i3 < bArr.length) {
                if (i3 == i2) {
                    buffer.writeByte(58);
                    i3 += i5;
                    if (i3 == 16) {
                        buffer.writeByte(58);
                    }
                } else {
                    if (i3 > 0) {
                        buffer.writeByte(58);
                    }
                    buffer.writeHexadecimalUnsignedLong(((bArr[i3] & 255) << 8) | (bArr[i3 + 1] & 255));
                    i3 += 2;
                }
            }
            return buffer.readUtf8();
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
                i4 = Integer.parseInt(HttpUrl.e(str, i2, i3, "", false, false));
            } catch (NumberFormatException unused) {
            }
            if (i4 <= 0 || i4 > 65535) {
                return -1;
            }
            return i4;
        }

        private void pop() {
            if (!((String) this.f19891f.remove(r0.size() - 1)).isEmpty() || this.f19891f.isEmpty()) {
                this.f19891f.add("");
            } else {
                this.f19891f.set(r0.size() - 1, "");
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
            String strE = HttpUrl.e(str, i2, i3, " \"<>^`{}|/\\?#", z3, false);
            if (isDot(strE)) {
                return;
            }
            if (isDotDot(strE)) {
                pop();
                return;
            }
            if (((String) this.f19891f.get(r8.size() - 1)).isEmpty()) {
                this.f19891f.set(r8.size() - 1, strE);
            } else {
                this.f19891f.add(strE);
            }
            if (z2) {
                this.f19891f.add("");
            }
        }

        private void removeAllCanonicalQueryParameters(String str) {
            for (int size = this.f19892g.size() - 2; size >= 0; size -= 2) {
                if (str.equals(this.f19892g.get(size))) {
                    this.f19892g.remove(size + 1);
                    this.f19892g.remove(size);
                    if (this.f19892g.isEmpty()) {
                        this.f19892g = null;
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
                this.f19891f.clear();
                this.f19891f.add("");
                i2++;
            } else {
                List list = this.f19891f;
                list.set(list.size() - 1, "");
            }
            while (true) {
                int i4 = i2;
                if (i4 >= i3) {
                    return;
                }
                i2 = HttpUrl.delimiterOffset(str, i4, i3, "/\\");
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
                            if (cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.') {
                                if (cCharAt2 == ':') {
                                    return i2;
                                }
                            }
                        }
                    }
                }
            }
            return -1;
        }

        private int skipLeadingAsciiWhitespace(String str, int i2, int i3) {
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                    return i2;
                }
                i2++;
            }
            return i3;
        }

        private int skipTrailingAsciiWhitespace(String str, int i2, int i3) {
            for (int i4 = i3 - 1; i4 >= i2; i4--) {
                char cCharAt = str.charAt(i4);
                if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != '\f' && cCharAt != '\r' && cCharAt != ' ') {
                    return i4 + 1;
                }
            }
            return i2;
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
            int i2 = this.f19890e;
            return i2 != -1 ? i2 : HttpUrl.defaultPort(this.f19886a);
        }

        public Builder addEncodedPathSegment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPathSegment == null");
            }
            push(str, 0, str.length(), false, true);
            return this;
        }

        public Builder addEncodedQueryParameter(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("encodedName == null");
            }
            if (this.f19892g == null) {
                this.f19892g = new ArrayList();
            }
            this.f19892g.add(HttpUrl.f(str, " \"'<>#&=", true, true));
            this.f19892g.add(str2 != null ? HttpUrl.f(str2, " \"'<>#&=", true, true) : null);
            return this;
        }

        public Builder addPathSegment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("pathSegment == null");
            }
            push(str, 0, str.length(), false, false);
            return this;
        }

        public Builder addQueryParameter(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.f19892g == null) {
                this.f19892g = new ArrayList();
            }
            this.f19892g.add(HttpUrl.f(str, " \"'<>#&=", false, true));
            this.f19892g.add(str2 != null ? HttpUrl.f(str2, " \"'<>#&=", false, true) : null);
            return this;
        }

        HttpUrl b(HttpUrl httpUrl, String str) throws NumberFormatException {
            int iDelimiterOffset;
            int i2;
            boolean z2 = false;
            int iSkipLeadingAsciiWhitespace = skipLeadingAsciiWhitespace(str, 0, str.length());
            int iSkipTrailingAsciiWhitespace = skipTrailingAsciiWhitespace(str, iSkipLeadingAsciiWhitespace, str.length());
            if (schemeDelimiterOffset(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace) != -1) {
                if (str.regionMatches(true, iSkipLeadingAsciiWhitespace, "https:", 0, 6)) {
                    this.f19886a = "https";
                    iSkipLeadingAsciiWhitespace += 6;
                } else {
                    if (!str.regionMatches(true, iSkipLeadingAsciiWhitespace, "http:", 0, 5)) {
                        return null;
                    }
                    this.f19886a = "http";
                    iSkipLeadingAsciiWhitespace += 5;
                }
            } else {
                if (httpUrl == null) {
                    return null;
                }
                this.f19886a = httpUrl.scheme;
            }
            int iSlashCount = slashCount(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace);
            char c2 = '?';
            char c3 = '#';
            if (iSlashCount >= 2 || httpUrl == null || !httpUrl.scheme.equals(this.f19886a)) {
                int i3 = iSkipLeadingAsciiWhitespace + iSlashCount;
                boolean z3 = false;
                while (true) {
                    iDelimiterOffset = HttpUrl.delimiterOffset(str, i3, iSkipTrailingAsciiWhitespace, "@/\\?#");
                    char cCharAt = iDelimiterOffset != iSkipTrailingAsciiWhitespace ? str.charAt(iDelimiterOffset) : (char) 65535;
                    if (cCharAt == 65535 || cCharAt == c3 || cCharAt == '/' || cCharAt == '\\' || cCharAt == c2) {
                        break;
                    }
                    if (cCharAt == '@') {
                        if (z2) {
                            i2 = iDelimiterOffset;
                            this.f19888c += "%40" + HttpUrl.e(str, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false);
                        } else {
                            int iDelimiterOffset2 = HttpUrl.delimiterOffset(str, i3, iDelimiterOffset, ":");
                            i2 = iDelimiterOffset;
                            String strE = HttpUrl.e(str, i3, iDelimiterOffset2, " \"':;<=>@[]^`{}|/\\?#", true, false);
                            if (z3) {
                                strE = this.f19887b + "%40" + strE;
                            }
                            this.f19887b = strE;
                            if (iDelimiterOffset2 != i2) {
                                this.f19888c = HttpUrl.e(str, iDelimiterOffset2 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false);
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
                    this.f19889d = canonicalizeHost(str, i3, iPortColonOffset);
                    int port = parsePort(str, i4, iDelimiterOffset);
                    this.f19890e = port;
                    if (port == -1) {
                        return null;
                    }
                } else {
                    this.f19889d = canonicalizeHost(str, i3, iPortColonOffset);
                    this.f19890e = HttpUrl.defaultPort(this.f19886a);
                }
                if (this.f19889d == null) {
                    return null;
                }
                iSkipLeadingAsciiWhitespace = iDelimiterOffset;
            } else {
                this.f19887b = httpUrl.encodedUsername();
                this.f19888c = httpUrl.encodedPassword();
                this.f19889d = httpUrl.host;
                this.f19890e = httpUrl.port;
                this.f19891f.clear();
                this.f19891f.addAll(httpUrl.encodedPathSegments());
                if (iSkipLeadingAsciiWhitespace == iSkipTrailingAsciiWhitespace || str.charAt(iSkipLeadingAsciiWhitespace) == '#') {
                    encodedQuery(httpUrl.encodedQuery());
                }
            }
            int iDelimiterOffset3 = HttpUrl.delimiterOffset(str, iSkipLeadingAsciiWhitespace, iSkipTrailingAsciiWhitespace, "?#");
            resolvePath(str, iSkipLeadingAsciiWhitespace, iDelimiterOffset3);
            if (iDelimiterOffset3 < iSkipTrailingAsciiWhitespace && str.charAt(iDelimiterOffset3) == '?') {
                int iDelimiterOffset4 = HttpUrl.delimiterOffset(str, iDelimiterOffset3, iSkipTrailingAsciiWhitespace, MqttTopic.MULTI_LEVEL_WILDCARD);
                this.f19892g = HttpUrl.n(HttpUrl.e(str, iDelimiterOffset3 + 1, iDelimiterOffset4, " \"'<>#", true, true));
                iDelimiterOffset3 = iDelimiterOffset4;
            }
            if (iDelimiterOffset3 < iSkipTrailingAsciiWhitespace && str.charAt(iDelimiterOffset3) == '#') {
                this.f19893h = HttpUrl.e(str, 1 + iDelimiterOffset3, iSkipTrailingAsciiWhitespace, "", true, false);
            }
            return build();
        }

        public HttpUrl build() {
            if (this.f19886a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.f19889d != null) {
                return new HttpUrl(this);
            }
            throw new IllegalStateException("host == null");
        }

        public Builder encodedFragment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedFragment == null");
            }
            this.f19893h = HttpUrl.f(str, "", true, false);
            return this;
        }

        public Builder encodedPassword(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPassword == null");
            }
            this.f19888c = HttpUrl.f(str, " \"':;<=>@[]^`{}|/\\?#", true, false);
            return this;
        }

        public Builder encodedPath(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPath == null");
            }
            if (str.startsWith("/")) {
                resolvePath(str, 0, str.length());
                return this;
            }
            throw new IllegalArgumentException("unexpected encodedPath: " + str);
        }

        public Builder encodedQuery(String str) {
            this.f19892g = str != null ? HttpUrl.n(HttpUrl.f(str, " \"'<>#", true, true)) : null;
            return this;
        }

        public Builder encodedUsername(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedUsername == null");
            }
            this.f19887b = HttpUrl.f(str, " \"':;<=>@[]^`{}|/\\?#", true, false);
            return this;
        }

        public Builder fragment(String str) {
            if (str == null) {
                throw new IllegalArgumentException("fragment == null");
            }
            this.f19893h = HttpUrl.f(str, "", false, false);
            return this;
        }

        public Builder host(String str) {
            if (str == null) {
                throw new IllegalArgumentException("host == null");
            }
            String strCanonicalizeHost = canonicalizeHost(str, 0, str.length());
            if (strCanonicalizeHost != null) {
                this.f19889d = strCanonicalizeHost;
                return this;
            }
            throw new IllegalArgumentException("unexpected host: " + str);
        }

        public Builder password(String str) {
            if (str == null) {
                throw new IllegalArgumentException("password == null");
            }
            this.f19888c = HttpUrl.f(str, " \"':;<=>@[]^`{}|/\\?#", false, false);
            return this;
        }

        public Builder port(int i2) {
            if (i2 > 0 && i2 <= 65535) {
                this.f19890e = i2;
                return this;
            }
            throw new IllegalArgumentException("unexpected port: " + i2);
        }

        public Builder query(String str) {
            this.f19892g = str != null ? HttpUrl.n(HttpUrl.f(str, " \"'<>#", false, true)) : null;
            return this;
        }

        public Builder removeAllEncodedQueryParameters(String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedName == null");
            }
            if (this.f19892g == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(HttpUrl.f(str, " \"'<>#&=", true, true));
            return this;
        }

        public Builder removeAllQueryParameters(String str) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.f19892g == null) {
                return this;
            }
            removeAllCanonicalQueryParameters(HttpUrl.f(str, " \"'<>#&=", false, true));
            return this;
        }

        public Builder removePathSegment(int i2) {
            this.f19891f.remove(i2);
            if (this.f19891f.isEmpty()) {
                this.f19891f.add("");
            }
            return this;
        }

        public Builder scheme(String str) {
            if (str == null) {
                throw new IllegalArgumentException("scheme == null");
            }
            if (str.equalsIgnoreCase("http")) {
                this.f19886a = "http";
            } else {
                if (!str.equalsIgnoreCase("https")) {
                    throw new IllegalArgumentException("unexpected scheme: " + str);
                }
                this.f19886a = "https";
            }
            return this;
        }

        public Builder setEncodedPathSegment(int i2, String str) {
            if (str == null) {
                throw new IllegalArgumentException("encodedPathSegment == null");
            }
            String strE = HttpUrl.e(str, 0, str.length(), " \"<>^`{}|/\\?#", true, false);
            this.f19891f.set(i2, strE);
            if (!isDot(strE) && !isDotDot(strE)) {
                return this;
            }
            throw new IllegalArgumentException("unexpected path segment: " + str);
        }

        public Builder setEncodedQueryParameter(String str, String str2) {
            removeAllEncodedQueryParameters(str);
            addEncodedQueryParameter(str, str2);
            return this;
        }

        public Builder setPathSegment(int i2, String str) {
            if (str == null) {
                throw new IllegalArgumentException("pathSegment == null");
            }
            String strE = HttpUrl.e(str, 0, str.length(), " \"<>^`{}|/\\?#", false, false);
            if (!isDot(strE) && !isDotDot(strE)) {
                this.f19891f.set(i2, strE);
                return this;
            }
            throw new IllegalArgumentException("unexpected path segment: " + str);
        }

        public Builder setQueryParameter(String str, String str2) {
            removeAllQueryParameters(str);
            addQueryParameter(str, str2);
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f19886a);
            sb.append(HttpConstant.SCHEME_SPLIT);
            if (!this.f19887b.isEmpty() || !this.f19888c.isEmpty()) {
                sb.append(this.f19887b);
                if (!this.f19888c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.f19888c);
                }
                sb.append('@');
            }
            if (this.f19889d.indexOf(58) != -1) {
                sb.append('[');
                sb.append(this.f19889d);
                sb.append(']');
            } else {
                sb.append(this.f19889d);
            }
            int iA = a();
            if (iA != HttpUrl.defaultPort(this.f19886a)) {
                sb.append(':');
                sb.append(iA);
            }
            HttpUrl.j(sb, this.f19891f);
            if (this.f19892g != null) {
                sb.append('?');
                HttpUrl.i(sb, this.f19892g);
            }
            if (this.f19893h != null) {
                sb.append('#');
                sb.append(this.f19893h);
            }
            return sb.toString();
        }

        public Builder username(String str) {
            if (str == null) {
                throw new IllegalArgumentException("username == null");
            }
            this.f19887b = HttpUrl.f(str, " \"':;<=>@[]^`{}|/\\?#", false, false);
            return this;
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

    /* JADX INFO: Access modifiers changed from: private */
    public static int delimiterOffset(String str, int i2, int i3, String str2) {
        while (i2 < i3) {
            if (str2.indexOf(str.charAt(i2)) != -1) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    static String e(String str, int i2, int i3, String str2, boolean z2, boolean z3) {
        int iCharCount = i2;
        while (iCharCount < i3) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt < 32 || iCodePointAt >= 127 || str2.indexOf(iCodePointAt) != -1 || ((iCodePointAt == 37 && !z2) || (z3 && iCodePointAt == 43))) {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, iCharCount);
                g(buffer, str, iCharCount, i3, str2, z2, z3);
                return buffer.readUtf8();
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str.substring(i2, i3);
    }

    static String f(String str, String str2, boolean z2, boolean z3) {
        return e(str, 0, str.length(), str2, z2, z3);
    }

    static void g(Buffer buffer, String str, int i2, int i3, String str2, boolean z2, boolean z3) {
        Buffer buffer2 = null;
        while (i2 < i3) {
            int iCodePointAt = str.codePointAt(i2);
            if (!z2 || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                if (z3 && iCodePointAt == 43) {
                    buffer.writeUtf8(z2 ? "%20" : "%2B");
                } else if (iCodePointAt < 32 || iCodePointAt >= 127 || str2.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && !z2)) {
                    if (buffer2 == null) {
                        buffer2 = new Buffer();
                    }
                    buffer2.writeUtf8CodePoint(iCodePointAt);
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

    public static HttpUrl get(URL url) {
        return parse(url.toString());
    }

    static int h(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        if (c2 >= 'a' && c2 <= 'f') {
            return c2 - 'W';
        }
        if (c2 < 'A' || c2 > 'F') {
            return -1;
        }
        return c2 - '7';
    }

    static void i(StringBuilder sb, List list) {
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

    static void j(StringBuilder sb, List list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append((String) list.get(i2));
        }
    }

    static String k(String str) {
        return l(str, 0, str.length());
    }

    static String l(String str, int i2, int i3) {
        for (int i4 = i2; i4 < i3; i4++) {
            if (str.charAt(i4) == '%') {
                Buffer buffer = new Buffer();
                buffer.writeUtf8(str, i2, i4);
                m(buffer, str, i4, i3);
                return buffer.readUtf8();
            }
        }
        return str.substring(i2, i3);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void m(okio.Buffer r5, java.lang.String r6, int r7, int r8) {
        /*
        L0:
            if (r7 >= r8) goto L36
            int r0 = r6.codePointAt(r7)
            r1 = 37
            if (r0 != r1) goto L2d
            int r1 = r7 + 2
            if (r1 >= r8) goto L2d
            int r2 = r7 + 1
            char r2 = r6.charAt(r2)
            int r2 = h(r2)
            char r3 = r6.charAt(r1)
            int r3 = h(r3)
            r4 = -1
            if (r2 == r4) goto L2d
            if (r3 == r4) goto L2d
            int r7 = r2 << 4
            int r7 = r7 + r3
            r5.writeByte(r7)
            r7 = r1
            goto L30
        L2d:
            r5.writeUtf8CodePoint(r0)
        L30:
            int r0 = java.lang.Character.charCount(r0)
            int r7 = r7 + r0
            goto L0
        L36:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.HttpUrl.m(okio.Buffer, java.lang.String, int, int):void");
    }

    static List n(String str) {
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

    public static HttpUrl parse(String str) {
        return new Builder().b(null, str);
    }

    private List<String> percentDecode(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            arrayList.add(next != null ? k(next) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

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
        return this.url.substring(this.url.indexOf(58, this.scheme.length() + 3) + 1, this.url.indexOf(64));
    }

    public String encodedPath() {
        int iIndexOf = this.url.indexOf(47, this.scheme.length() + 3);
        String str = this.url;
        return this.url.substring(iIndexOf, delimiterOffset(str, iIndexOf, str.length(), "?#"));
    }

    public List<String> encodedPathSegments() {
        int iIndexOf = this.url.indexOf(47, this.scheme.length() + 3);
        String str = this.url;
        int iDelimiterOffset = delimiterOffset(str, iIndexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iDelimiterOffset) {
            int i2 = iIndexOf + 1;
            int iDelimiterOffset2 = delimiterOffset(this.url, i2, iDelimiterOffset, "/");
            arrayList.add(this.url.substring(i2, iDelimiterOffset2));
            iIndexOf = iDelimiterOffset2;
        }
        return arrayList;
    }

    public String encodedQuery() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        int iIndexOf = this.url.indexOf(63);
        int i2 = iIndexOf + 1;
        String str = this.url;
        return this.url.substring(i2, delimiterOffset(str, iIndexOf + 2, str.length(), MqttTopic.MULTI_LEVEL_WILDCARD));
    }

    public String encodedUsername() {
        if (this.username.isEmpty()) {
            return "";
        }
        int length = this.scheme.length() + 3;
        String str = this.url;
        return this.url.substring(length, delimiterOffset(str, length, str.length(), ":@"));
    }

    public boolean equals(Object obj) {
        return (obj instanceof HttpUrl) && ((HttpUrl) obj).url.equals(this.url);
    }

    public String fragment() {
        return this.fragment;
    }

    public int hashCode() {
        return this.url.hashCode();
    }

    public String host() {
        return this.host;
    }

    public boolean isHttps() {
        return this.scheme.equals("https");
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.f19886a = this.scheme;
        builder.f19887b = encodedUsername();
        builder.f19888c = encodedPassword();
        builder.f19889d = this.host;
        if (this.port == defaultPort(this.scheme)) {
            builder.f19890e = -1;
        } else {
            builder.f19890e = this.port;
        }
        builder.f19891f.clear();
        builder.f19891f.addAll(encodedPathSegments());
        builder.encodedQuery(encodedQuery());
        builder.f19893h = encodedFragment();
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
        return this.port;
    }

    public String query() {
        if (this.queryNamesAndValues == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        i(sb, this.queryNamesAndValues);
        return sb.toString();
    }

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
        return this.queryNamesAndValues.get(i2 * 2);
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
        return this.queryNamesAndValues.get((i2 * 2) + 1);
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

    public HttpUrl resolve(String str) {
        return new Builder().b(this, str);
    }

    public String scheme() {
        return this.scheme;
    }

    public String toString() {
        return this.url;
    }

    public URI uri() {
        try {
            return new URI(this.url);
        } catch (URISyntaxException unused) {
            throw new IllegalStateException("not valid as a java.net.URI: " + this.url);
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

    private HttpUrl(Builder builder) {
        this.scheme = builder.f19886a;
        this.username = k(builder.f19887b);
        this.password = k(builder.f19888c);
        this.host = builder.f19889d;
        this.port = builder.a();
        this.pathSegments = percentDecode(builder.f19891f);
        List<String> list = builder.f19892g;
        this.queryNamesAndValues = list != null ? percentDecode(list) : null;
        String str = builder.f19893h;
        this.fragment = str != null ? k(str) : null;
        this.url = builder.toString();
    }

    public static HttpUrl get(URI uri) {
        return parse(uri.toString());
    }
}
