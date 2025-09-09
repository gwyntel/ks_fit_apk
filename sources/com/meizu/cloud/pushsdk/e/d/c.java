package com.meizu.cloud.pushsdk.e.d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes4.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    private final String[] f19384a;

    public static final class b {

        /* renamed from: a, reason: collision with root package name */
        private final List<String> f19385a = new ArrayList(20);

        private void c(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (str.isEmpty()) {
                throw new IllegalArgumentException("name is empty");
            }
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt <= 31 || cCharAt >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in header name: %s", Integer.valueOf(cCharAt), Integer.valueOf(i2), str));
                }
            }
            if (str2 == null) {
                throw new IllegalArgumentException("value == null");
            }
            int length2 = str2.length();
            for (int i3 = 0; i3 < length2; i3++) {
                char cCharAt2 = str2.charAt(i3);
                if (cCharAt2 <= 31 || cCharAt2 >= 127) {
                    throw new IllegalArgumentException(String.format("Unexpected char %#04x at %d in %s value: %s", Integer.valueOf(cCharAt2), Integer.valueOf(i3), str, str2));
                }
            }
        }

        public b a(String str, String str2) {
            c(str, str2);
            return b(str, str2);
        }

        b b(String str, String str2) {
            this.f19385a.add(str);
            this.f19385a.add(str2.trim());
            return this;
        }

        public c a() {
            return new c(this);
        }
    }

    private c(b bVar) {
        this.f19384a = (String[]) bVar.f19385a.toArray(new String[bVar.f19385a.size()]);
    }

    public static c a(String... strArr) {
        if (strArr == null || strArr.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        String[] strArr2 = (String[]) strArr.clone();
        for (int i2 = 0; i2 < strArr2.length; i2++) {
            String str = strArr2[i2];
            if (str == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            strArr2[i2] = str.trim();
        }
        for (int i3 = 0; i3 < strArr2.length; i3 += 2) {
            String str2 = strArr2[i3];
            String str3 = strArr2[i3 + 1];
            if (str2.length() == 0 || str2.indexOf(0) != -1 || str3.indexOf(0) != -1) {
                throw new IllegalArgumentException("Unexpected header: " + str2 + ": " + str3);
            }
        }
        return new c(strArr2);
    }

    public b b() {
        b bVar = new b();
        Collections.addAll(bVar.f19385a, this.f19384a);
        return bVar;
    }

    public int c() {
        return this.f19384a.length / 2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int iC = c();
        for (int i2 = 0; i2 < iC; i2++) {
            sb.append(a(i2));
            sb.append(": ");
            sb.append(b(i2));
            sb.append("\n");
        }
        return sb.toString();
    }

    public String a(int i2) {
        return this.f19384a[i2 * 2];
    }

    public String b(int i2) {
        return this.f19384a[(i2 * 2) + 1];
    }

    private c(String[] strArr) {
        this.f19384a = strArr;
    }

    public String a(String str) {
        return a(this.f19384a, str);
    }

    private static String a(String[] strArr, String str) {
        for (int length = strArr.length - 2; length >= 0; length -= 2) {
            if (str.equalsIgnoreCase(strArr[length])) {
                return strArr[length + 1];
            }
        }
        return null;
    }

    public Set<String> a() {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        int iC = c();
        for (int i2 = 0; i2 < iC; i2++) {
            treeSet.add(a(i2));
        }
        return Collections.unmodifiableSet(treeSet);
    }
}
