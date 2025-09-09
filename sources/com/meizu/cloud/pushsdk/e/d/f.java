package com.meizu.cloud.pushsdk.e.d;

import anet.channel.util.HttpConstant;
import com.taobao.accs.common.Constants;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f19388a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: b, reason: collision with root package name */
    private final String f19389b;

    /* renamed from: c, reason: collision with root package name */
    private final String f19390c;

    /* renamed from: d, reason: collision with root package name */
    private final String f19391d;

    /* renamed from: e, reason: collision with root package name */
    private final String f19392e;

    /* renamed from: f, reason: collision with root package name */
    private final int f19393f;

    /* renamed from: g, reason: collision with root package name */
    private final List<String> f19394g;

    /* renamed from: h, reason: collision with root package name */
    private final List<String> f19395h;

    /* renamed from: i, reason: collision with root package name */
    private final String f19396i;

    /* renamed from: j, reason: collision with root package name */
    private final String f19397j;

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        String f19398a;

        /* renamed from: d, reason: collision with root package name */
        String f19401d;

        /* renamed from: f, reason: collision with root package name */
        final List<String> f19403f;

        /* renamed from: g, reason: collision with root package name */
        List<String> f19404g;

        /* renamed from: h, reason: collision with root package name */
        String f19405h;

        /* renamed from: b, reason: collision with root package name */
        String f19399b = "";

        /* renamed from: c, reason: collision with root package name */
        String f19400c = "";

        /* renamed from: e, reason: collision with root package name */
        int f19402e = -1;

        enum a {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST
        }

        public b() {
            ArrayList arrayList = new ArrayList();
            this.f19403f = arrayList;
            arrayList.add("");
        }

        private static int c(String str, int i2, int i3) throws NumberFormatException {
            int i4;
            try {
                i4 = Integer.parseInt(f.b(str, i2, i3, "", false, false, false, true));
            } catch (NumberFormatException unused) {
            }
            if (i4 <= 0 || i4 > 65535) {
                return -1;
            }
            return i4;
        }

        private static int d(String str, int i2, int i3) {
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

        private void e(String str, int i2, int i3) {
            if (i2 == i3) {
                return;
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt == '/' || cCharAt == '\\') {
                this.f19403f.clear();
                this.f19403f.add("");
                i2++;
            } else {
                List<String> list = this.f19403f;
                list.set(list.size() - 1, "");
            }
            while (true) {
                int i4 = i2;
                if (i4 >= i3) {
                    return;
                }
                i2 = m.a(str, i4, i3, "/\\");
                boolean z2 = i2 < i3;
                a(str, i4, i2, z2, true);
                if (z2) {
                    i2++;
                }
            }
        }

        private static int f(String str, int i2, int i3) {
            if (i3 - i2 < 2) {
                return -1;
            }
            char cCharAt = str.charAt(i2);
            boolean z2 = cCharAt < 'a' || cCharAt > 'z';
            boolean z3 = cCharAt < 'A' || cCharAt > 'Z';
            if (z2 && z3) {
                return -1;
            }
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
            return -1;
        }

        private static int g(String str, int i2, int i3) {
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

        a a(f fVar, String str) throws NumberFormatException {
            int iA;
            int i2;
            int iA2 = m.a(str, 0, str.length());
            int iB = m.b(str, iA2, str.length());
            if (f(str, iA2, iB) != -1) {
                if (str.regionMatches(true, iA2, "https:", 0, 6)) {
                    this.f19398a = "https";
                    iA2 += 6;
                } else {
                    if (!str.regionMatches(true, iA2, "http:", 0, 5)) {
                        return a.UNSUPPORTED_SCHEME;
                    }
                    this.f19398a = "http";
                    iA2 += 5;
                }
            } else {
                if (fVar == null) {
                    return a.MISSING_SCHEME;
                }
                this.f19398a = fVar.f19389b;
            }
            int iG = g(str, iA2, iB);
            char c2 = '?';
            char c3 = '#';
            if (iG >= 2 || fVar == null || !fVar.f19389b.equals(this.f19398a)) {
                boolean z2 = false;
                boolean z3 = false;
                int i3 = iA2 + iG;
                while (true) {
                    iA = m.a(str, i3, iB, "@/\\?#");
                    char cCharAt = iA != iB ? str.charAt(iA) : (char) 65535;
                    if (cCharAt == 65535 || cCharAt == c3 || cCharAt == '/' || cCharAt == '\\' || cCharAt == c2) {
                        break;
                    }
                    if (cCharAt == '@') {
                        if (z2) {
                            i2 = iA;
                            this.f19400c += "%40" + f.b(str, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                        } else {
                            int iA3 = m.a(str, i3, iA, ':');
                            i2 = iA;
                            String strB = f.b(str, i3, iA3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                            if (z3) {
                                strB = this.f19399b + "%40" + strB;
                            }
                            this.f19399b = strB;
                            if (iA3 != i2) {
                                this.f19400c = f.b(str, iA3 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                z2 = true;
                            }
                            z3 = true;
                        }
                        i3 = i2 + 1;
                        c2 = '?';
                        c3 = '#';
                    }
                }
                int iD = d(str, i3, iA);
                int i4 = iD + 1;
                this.f19401d = a(str, i3, iD);
                if (i4 < iA) {
                    int iC = c(str, i4, iA);
                    this.f19402e = iC;
                    if (iC == -1) {
                        return a.INVALID_PORT;
                    }
                } else {
                    this.f19402e = f.a(this.f19398a);
                }
                if (this.f19401d == null) {
                    return a.INVALID_HOST;
                }
                iA2 = iA;
            } else {
                this.f19399b = fVar.e();
                this.f19400c = fVar.b();
                this.f19401d = fVar.f19392e;
                this.f19402e = fVar.f19393f;
                this.f19403f.clear();
                this.f19403f.addAll(fVar.c());
                if (iA2 == iB || str.charAt(iA2) == '#') {
                    a(fVar.d());
                }
            }
            int iA4 = m.a(str, iA2, iB, "?#");
            e(str, iA2, iA4);
            if (iA4 < iB && str.charAt(iA4) == '?') {
                int iA5 = m.a(str, iA4, iB, '#');
                this.f19404g = f.c(f.b(str, iA4 + 1, iA5, " \"'<>#", true, false, true, true));
                iA4 = iA5;
            }
            if (iA4 < iB && str.charAt(iA4) == '#') {
                this.f19405h = f.b(str, 1 + iA4, iB, "", true, false, false, false);
            }
            return a.SUCCESS;
        }

        int b() {
            int i2 = this.f19402e;
            return i2 != -1 ? i2 : f.a(this.f19398a);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f19398a);
            sb.append(HttpConstant.SCHEME_SPLIT);
            if (!this.f19399b.isEmpty() || !this.f19400c.isEmpty()) {
                sb.append(this.f19399b);
                if (!this.f19400c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.f19400c);
                }
                sb.append('@');
            }
            if (this.f19401d.indexOf(58) != -1) {
                sb.append('[');
                sb.append(this.f19401d);
                sb.append(']');
            } else {
                sb.append(this.f19401d);
            }
            int iB = b();
            if (iB != f.a(this.f19398a)) {
                sb.append(':');
                sb.append(iB);
            }
            f.b(sb, this.f19403f);
            if (this.f19404g != null) {
                sb.append('?');
                f.a(sb, this.f19404g);
            }
            if (this.f19405h != null) {
                sb.append('#');
                sb.append(this.f19405h);
            }
            return sb.toString();
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
        private static java.net.InetAddress b(java.lang.String r12, int r13, int r14) {
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
                boolean r12 = a(r12, r6, r14, r1, r13)
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
                int r10 = com.meizu.cloud.pushsdk.e.d.f.a(r10)
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
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.e.d.f.b.b(java.lang.String, int, int):java.net.InetAddress");
        }

        private void c() {
            if (!this.f19403f.remove(r0.size() - 1).isEmpty() || this.f19403f.isEmpty()) {
                this.f19403f.add("");
            } else {
                this.f19403f.set(r0.size() - 1, "");
            }
        }

        public b a(String str) {
            this.f19404g = str != null ? f.c(f.a(str, " \"'<>#", true, false, true, true)) : null;
            return this;
        }

        private boolean b(String str) {
            return ".".equals(str) || "%2e".equalsIgnoreCase(str);
        }

        private boolean c(String str) {
            return "..".equals(str) || "%2e.".equalsIgnoreCase(str) || ".%2e".equalsIgnoreCase(str) || "%2e%2e".equalsIgnoreCase(str);
        }

        public b a(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.f19404g == null) {
                this.f19404g = new ArrayList();
            }
            this.f19404g.add(f.a(str, " \"'<>#&=", false, false, true, true));
            this.f19404g.add(str2 != null ? f.a(str2, " \"'<>#&=", false, false, true, true) : null);
            return this;
        }

        public f a() {
            if (this.f19398a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.f19401d != null) {
                return new f(this);
            }
            throw new IllegalStateException("host == null");
        }

        private static String a(String str, int i2, int i3) {
            String strB = f.b(str, i2, i3, false);
            if (!strB.contains(":")) {
                return m.b(strB);
            }
            InetAddress inetAddressB = (strB.startsWith("[") && strB.endsWith("]")) ? b(strB, 1, strB.length() - 1) : b(strB, 0, strB.length());
            if (inetAddressB == null) {
                return null;
            }
            byte[] address = inetAddressB.getAddress();
            if (address.length == 16) {
                return a(address);
            }
            throw new AssertionError();
        }

        private static String a(byte[] bArr) {
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
            com.meizu.cloud.pushsdk.e.h.b bVar = new com.meizu.cloud.pushsdk.e.h.b();
            while (i3 < bArr.length) {
                if (i3 == i2) {
                    bVar.b(58);
                    i3 += i5;
                    if (i3 == 16) {
                        bVar.b(58);
                    }
                } else {
                    if (i3 > 0) {
                        bVar.b(58);
                    }
                    bVar.e(((bArr[i3] & 255) << 8) | (bArr[i3 + 1] & 255));
                    i3 += 2;
                }
            }
            return bVar.a();
        }

        private void a(String str, int i2, int i3, boolean z2, boolean z3) {
            String strB = f.b(str, i2, i3, " \"<>^`{}|/\\?#", z3, false, false, true);
            if (b(strB)) {
                return;
            }
            if (c(strB)) {
                c();
                return;
            }
            if (this.f19403f.get(r10.size() - 1).isEmpty()) {
                this.f19403f.set(r10.size() - 1, strB);
            } else {
                this.f19403f.add(strB);
            }
            if (z2) {
                this.f19403f.add("");
            }
        }

        private static boolean a(String str, int i2, int i3, byte[] bArr, int i4) {
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
    }

    private f(b bVar) {
        this.f19389b = bVar.f19398a;
        this.f19390c = a(bVar.f19399b, false);
        this.f19391d = a(bVar.f19400c, false);
        this.f19392e = bVar.f19401d;
        this.f19393f = bVar.b();
        this.f19394g = a(bVar.f19403f, false);
        List<String> list = bVar.f19404g;
        this.f19395h = list != null ? a(list, true) : null;
        String str = bVar.f19405h;
        this.f19396i = str != null ? a(str, false) : null;
        this.f19397j = bVar.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(char c2) {
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

    public String d() {
        if (this.f19395h == null) {
            return null;
        }
        int iIndexOf = this.f19397j.indexOf(63);
        int i2 = iIndexOf + 1;
        String str = this.f19397j;
        return this.f19397j.substring(i2, m.a(str, iIndexOf + 2, str.length(), '#'));
    }

    public String e() {
        if (this.f19390c.isEmpty()) {
            return "";
        }
        int length = this.f19389b.length() + 3;
        String str = this.f19397j;
        return this.f19397j.substring(length, m.a(str, length, str.length(), ":@"));
    }

    public boolean equals(Object obj) {
        return (obj instanceof f) && ((f) obj).f19397j.equals(this.f19397j);
    }

    public b f() {
        b bVar = new b();
        bVar.f19398a = this.f19389b;
        bVar.f19399b = e();
        bVar.f19400c = b();
        bVar.f19401d = this.f19392e;
        bVar.f19402e = this.f19393f != a(this.f19389b) ? this.f19393f : -1;
        bVar.f19403f.clear();
        bVar.f19403f.addAll(c());
        bVar.a(d());
        bVar.f19405h = a();
        return bVar;
    }

    public int hashCode() {
        return this.f19397j.hashCode();
    }

    public String toString() {
        return this.f19397j;
    }

    public static int a(String str) {
        if ("http".equals(str)) {
            return 80;
        }
        if ("https".equals(str)) {
            return Constants.PORT;
        }
        return -1;
    }

    public static f b(String str) {
        b bVar = new b();
        if (bVar.a((f) null, str) == b.a.SUCCESS) {
            return bVar.a();
        }
        return null;
    }

    public List<String> c() {
        int iIndexOf = this.f19397j.indexOf(47, this.f19389b.length() + 3);
        String str = this.f19397j;
        int iA = m.a(str, iIndexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iA) {
            int i2 = iIndexOf + 1;
            int iA2 = m.a(this.f19397j, i2, iA, IOUtils.DIR_SEPARATOR_UNIX);
            arrayList.add(this.f19397j.substring(i2, iA2));
            iIndexOf = iA2;
        }
        return arrayList;
    }

    static List<String> c(String str) {
        String strSubstring;
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
                strSubstring = null;
            } else {
                arrayList.add(str.substring(i2, iIndexOf2));
                strSubstring = str.substring(iIndexOf2 + 1, iIndexOf);
            }
            arrayList.add(strSubstring);
            i2 = iIndexOf + 1;
        }
        return arrayList;
    }

    public String a() {
        if (this.f19396i == null) {
            return null;
        }
        return this.f19397j.substring(this.f19397j.indexOf(35) + 1);
    }

    public String b() {
        if (this.f19391d.isEmpty()) {
            return "";
        }
        return this.f19397j.substring(this.f19397j.indexOf(58, this.f19389b.length() + 3) + 1, this.f19397j.indexOf(64));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        int iCharCount = i2;
        while (iCharCount < i3) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (a(iCodePointAt, iCharCount, str, i3, str2, z2, z3, z4, z5)) {
                com.meizu.cloud.pushsdk.e.h.b bVar = new com.meizu.cloud.pushsdk.e.h.b();
                bVar.a(str, i2, iCharCount);
                a(bVar, str, iCharCount, i3, str2, z2, z3, z4, z5);
                return bVar.a();
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str.substring(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(String str, int i2, int i3, boolean z2) {
        for (int i4 = i2; i4 < i3; i4++) {
            char cCharAt = str.charAt(i4);
            boolean z3 = false;
            boolean z4 = cCharAt == '%';
            if (cCharAt == '+' && z2) {
                z3 = true;
            }
            if (z4 || z3) {
                com.meizu.cloud.pushsdk.e.h.b bVar = new com.meizu.cloud.pushsdk.e.h.b();
                bVar.a(str, i2, i4);
                a(bVar, str, i4, i3, z2);
                return bVar.a();
            }
        }
        return str.substring(i2, i3);
    }

    static String a(String str, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        return b(str, 0, str.length(), str2, z2, z3, z4, z5);
    }

    static void b(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append(list.get(i2));
        }
    }

    static String a(String str, boolean z2) {
        return b(str, 0, str.length(), z2);
    }

    private List<String> a(List<String> list, boolean z2) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            arrayList.add(next != null ? a(next, z2) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static void a(com.meizu.cloud.pushsdk.e.h.b bVar, String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        com.meizu.cloud.pushsdk.e.h.b bVar2 = null;
        int iCharCount = i2;
        while (iCharCount < i3) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt == 43 && z4) {
                bVar.a(z2 ? MqttTopic.SINGLE_LEVEL_WILDCARD : "%2B");
            } else if (a(iCodePointAt, iCharCount, str, i3, str2, z2, z3, z4, z5)) {
                if (bVar2 == null) {
                    bVar2 = new com.meizu.cloud.pushsdk.e.h.b();
                }
                bVar2.c(iCodePointAt);
                while (!bVar2.h()) {
                    byte bI = bVar2.i();
                    bVar.b(37);
                    char[] cArr = f19388a;
                    bVar.b((int) cArr[((bI & 255) >> 4) & 15]);
                    bVar.b((int) cArr[bI & 15]);
                }
            } else {
                bVar.c(iCodePointAt);
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void a(com.meizu.cloud.pushsdk.e.h.b r5, java.lang.String r6, int r7, int r8, boolean r9) {
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
            int r2 = b(r2)
            char r3 = r6.charAt(r1)
            int r3 = b(r3)
            r4 = -1
            if (r2 == r4) goto L39
            if (r3 == r4) goto L39
            int r7 = r2 << 4
            int r7 = r7 + r3
            r5.b(r7)
            r7 = r1
            goto L3c
        L2d:
            r1 = 43
            if (r0 != r1) goto L39
            if (r9 == 0) goto L39
            r1 = 32
            r5.b(r1)
            goto L3c
        L39:
            r5.c(r0)
        L3c:
            int r0 = java.lang.Character.charCount(r0)
            int r7 = r7 + r0
            goto L0
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.e.d.f.a(com.meizu.cloud.pushsdk.e.h.b, java.lang.String, int, int, boolean):void");
    }

    static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String str = list.get(i2);
            String str2 = list.get(i2 + 1);
            if (i2 > 0) {
                sb.append(Typography.amp);
            }
            sb.append(str);
            if (str2 != null) {
                sb.append(com.alipay.sdk.m.n.a.f9565h);
                sb.append(str2);
            }
        }
    }

    private static boolean a(int i2, int i3, String str, int i4, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (i2 < 32 || i2 == 127) {
            return true;
        }
        if ((i2 >= 128 && z5) || str2.indexOf(i2) != -1) {
            return true;
        }
        boolean z6 = !z2 || (z3 && !a(str, i3, i4));
        if (i2 == 37 && z6) {
            return true;
        }
        return i2 == 43 && z4;
    }

    private static boolean a(String str, int i2, int i3) {
        int i4 = i2 + 2;
        return i4 < i3 && str.charAt(i2) == '%' && b(str.charAt(i2 + 1)) != -1 && b(str.charAt(i4)) != -1;
    }
}
