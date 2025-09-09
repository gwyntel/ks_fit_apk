package okhttp3.internal.http;

import androidx.webkit.ProxyConfig;
import anet.channel.request.Request;
import java.io.EOFException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import okhttp3.Challenge;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Response;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.ByteString;

/* loaded from: classes5.dex */
public final class HttpHeaders {
    private static final ByteString QUOTED_STRING_DELIMITERS = ByteString.encodeUtf8("\"\\");
    private static final ByteString TOKEN_DELIMITERS = ByteString.encodeUtf8("\t ,=");

    private HttpHeaders() {
    }

    public static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    public static boolean hasBody(Response response) {
        if (response.request().method().equals(Request.Method.HEAD)) {
            return false;
        }
        int iCode = response.code();
        return (((iCode >= 100 && iCode < 200) || iCode == 204 || iCode == 304) && contentLength(response) == -1 && !"chunked".equalsIgnoreCase(response.header(com.google.common.net.HttpHeaders.TRANSFER_ENCODING))) ? false : true;
    }

    public static boolean hasVaryAll(Response response) {
        return hasVaryAll(response.headers());
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x007d, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x007d, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0088  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void parseChallengeHeader(java.util.List<okhttp3.Challenge> r8, okio.Buffer r9) {
        /*
            r0 = 0
        L1:
            r1 = r0
        L2:
            if (r1 != 0) goto Le
            skipWhitespaceAndCommas(r9)
            java.lang.String r1 = readToken(r9)
            if (r1 != 0) goto Le
            return
        Le:
            boolean r2 = skipWhitespaceAndCommas(r9)
            java.lang.String r3 = readToken(r9)
            if (r3 != 0) goto L2c
            boolean r9 = r9.exhausted()
            if (r9 != 0) goto L1f
            return
        L1f:
            okhttp3.Challenge r9 = new okhttp3.Challenge
            java.util.Map r0 = java.util.Collections.emptyMap()
            r9.<init>(r1, r0)
            r8.add(r9)
            return
        L2c:
            r4 = 61
            int r5 = skipAll(r9, r4)
            boolean r6 = skipWhitespaceAndCommas(r9)
            if (r2 != 0) goto L60
            if (r6 != 0) goto L40
            boolean r2 = r9.exhausted()
            if (r2 == 0) goto L60
        L40:
            okhttp3.Challenge r2 = new okhttp3.Challenge
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r3)
            java.lang.String r3 = repeat(r4, r5)
            r6.append(r3)
            java.lang.String r3 = r6.toString()
            java.util.Map r3 = java.util.Collections.singletonMap(r0, r3)
            r2.<init>(r1, r3)
            r8.add(r2)
            goto L1
        L60:
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>()
            int r6 = skipAll(r9, r4)
            int r5 = r5 + r6
        L6a:
            if (r3 != 0) goto L7b
            java.lang.String r3 = readToken(r9)
            boolean r5 = skipWhitespaceAndCommas(r9)
            if (r5 == 0) goto L77
            goto L7d
        L77:
            int r5 = skipAll(r9, r4)
        L7b:
            if (r5 != 0) goto L88
        L7d:
            okhttp3.Challenge r4 = new okhttp3.Challenge
            r4.<init>(r1, r2)
            r8.add(r4)
            r1 = r3
            goto L2
        L88:
            r6 = 1
            if (r5 <= r6) goto L8c
            return
        L8c:
            boolean r6 = skipWhitespaceAndCommas(r9)
            if (r6 == 0) goto L93
            return
        L93:
            boolean r6 = r9.exhausted()
            if (r6 != 0) goto La8
            r6 = 0
            byte r6 = r9.getByte(r6)
            r7 = 34
            if (r6 != r7) goto La8
            java.lang.String r6 = readQuotedString(r9)
            goto Lac
        La8:
            java.lang.String r6 = readToken(r9)
        Lac:
            if (r6 != 0) goto Laf
            return
        Laf:
            java.lang.Object r3 = r2.put(r3, r6)
            java.lang.String r3 = (java.lang.String) r3
            if (r3 == 0) goto Lb8
            return
        Lb8:
            boolean r3 = skipWhitespaceAndCommas(r9)
            if (r3 != 0) goto Lc5
            boolean r3 = r9.exhausted()
            if (r3 != 0) goto Lc5
            return
        Lc5:
            r3 = r0
            goto L6a
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.HttpHeaders.parseChallengeHeader(java.util.List, okio.Buffer):void");
    }

    public static List<Challenge> parseChallenges(Headers headers, String str) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < headers.size(); i2++) {
            if (str.equalsIgnoreCase(headers.name(i2))) {
                parseChallengeHeader(arrayList, new Buffer().writeUtf8(headers.value(i2)));
            }
        }
        return arrayList;
    }

    public static int parseSeconds(String str, int i2) throws NumberFormatException {
        try {
            long j2 = Long.parseLong(str);
            if (j2 > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (j2 < 0) {
                return 0;
            }
            return (int) j2;
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    private static String readQuotedString(Buffer buffer) {
        if (buffer.readByte() != 34) {
            throw new IllegalArgumentException();
        }
        Buffer buffer2 = new Buffer();
        while (true) {
            long jIndexOfElement = buffer.indexOfElement(QUOTED_STRING_DELIMITERS);
            if (jIndexOfElement == -1) {
                return null;
            }
            if (buffer.getByte(jIndexOfElement) == 34) {
                buffer2.write(buffer, jIndexOfElement);
                buffer.readByte();
                return buffer2.readUtf8();
            }
            if (buffer.size() == jIndexOfElement + 1) {
                return null;
            }
            buffer2.write(buffer, jIndexOfElement);
            buffer.readByte();
            buffer2.write(buffer, 1L);
        }
    }

    private static String readToken(Buffer buffer) {
        try {
            long jIndexOfElement = buffer.indexOfElement(TOKEN_DELIMITERS);
            if (jIndexOfElement == -1) {
                jIndexOfElement = buffer.size();
            }
            if (jIndexOfElement != 0) {
                return buffer.readUtf8(jIndexOfElement);
            }
            return null;
        } catch (EOFException unused) {
            throw new AssertionError();
        }
    }

    public static void receiveHeaders(CookieJar cookieJar, HttpUrl httpUrl, Headers headers) {
        if (cookieJar == CookieJar.NO_COOKIES) {
            return;
        }
        List<Cookie> all = Cookie.parseAll(httpUrl, headers);
        if (all.isEmpty()) {
            return;
        }
        cookieJar.saveFromResponse(httpUrl, all);
    }

    private static String repeat(char c2, int i2) {
        char[] cArr = new char[i2];
        Arrays.fill(cArr, c2);
        return new String(cArr);
    }

    private static int skipAll(Buffer buffer, byte b2) {
        int i2 = 0;
        while (!buffer.exhausted() && buffer.getByte(0L) == b2) {
            i2++;
            buffer.readByte();
        }
        return i2;
    }

    public static int skipUntil(String str, int i2, String str2) {
        while (i2 < str.length() && str2.indexOf(str.charAt(i2)) == -1) {
            i2++;
        }
        return i2;
    }

    public static int skipWhitespace(String str, int i2) {
        char cCharAt;
        while (i2 < str.length() && ((cCharAt = str.charAt(i2)) == ' ' || cCharAt == '\t')) {
            i2++;
        }
        return i2;
    }

    private static boolean skipWhitespaceAndCommas(Buffer buffer) {
        boolean z2 = false;
        while (!buffer.exhausted()) {
            byte b2 = buffer.getByte(0L);
            if (b2 != 44) {
                if (b2 != 32 && b2 != 9) {
                    break;
                }
                buffer.readByte();
            } else {
                buffer.readByte();
                z2 = true;
            }
        }
        return z2;
    }

    private static long stringToLong(String str) {
        if (str == null) {
            return -1L;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    private static Set<String> varyFields(Response response) {
        return varyFields(response.headers());
    }

    public static Headers varyHeaders(Response response) {
        return varyHeaders(response.networkResponse().request().headers(), response.headers());
    }

    public static boolean varyMatches(Response response, Headers headers, okhttp3.Request request) {
        for (String str : varyFields(response)) {
            if (!Util.equal(headers.values(str), request.headers(str))) {
                return false;
            }
        }
        return true;
    }

    public static long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    public static boolean hasVaryAll(Headers headers) {
        return varyFields(headers).contains(ProxyConfig.MATCH_ALL_SCHEMES);
    }

    public static Set<String> varyFields(Headers headers) {
        Set<String> setEmptySet = Collections.emptySet();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (com.google.common.net.HttpHeaders.VARY.equalsIgnoreCase(headers.name(i2))) {
                String strValue = headers.value(i2);
                if (setEmptySet.isEmpty()) {
                    setEmptySet = new TreeSet<>((Comparator<? super String>) String.CASE_INSENSITIVE_ORDER);
                }
                for (String str : strValue.split(",")) {
                    setEmptySet.add(str.trim());
                }
            }
        }
        return setEmptySet;
    }

    public static Headers varyHeaders(Headers headers, Headers headers2) {
        Set<String> setVaryFields = varyFields(headers2);
        if (setVaryFields.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers.Builder builder = new Headers.Builder();
        int size = headers.size();
        for (int i2 = 0; i2 < size; i2++) {
            String strName = headers.name(i2);
            if (setVaryFields.contains(strName)) {
                builder.add(strName, headers.value(i2));
            }
        }
        return builder.build();
    }
}
