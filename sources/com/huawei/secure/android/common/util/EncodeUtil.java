package com.huawei.secure.android.common.util;

import android.text.TextUtils;
import android.util.Log;
import java.util.Locale;
import kotlin.text.Typography;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes4.dex */
public class EncodeUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18502a = "EncodeUtil";

    /* renamed from: b, reason: collision with root package name */
    private static final char[] f18503b = {',', '.', '-', '_'};

    /* renamed from: c, reason: collision with root package name */
    private static final String[] f18504c = new String[256];

    static {
        for (char c2 = 0; c2 < 255; c2 = (char) (c2 + 1)) {
            if ((c2 < '0' || c2 > '9') && ((c2 < 'A' || c2 > 'Z') && (c2 < 'a' || c2 > 'z'))) {
                f18504c[c2] = b(c2).intern();
            } else {
                f18504c[c2] = null;
            }
        }
    }

    private static String a(char[] cArr, String str) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            sb.append(a(cArr, Character.valueOf(str.charAt(i2))));
        }
        return sb.toString();
    }

    private static String b(char c2) {
        return Integer.toHexString(c2);
    }

    public static String decodeForJavaScript(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            b bVar = new b(str);
            while (bVar.a()) {
                Character chA = a(bVar);
                if (chA != null) {
                    sb.append(chA);
                } else {
                    sb.append(bVar.d());
                }
            }
            return sb.toString();
        } catch (Exception e2) {
            Log.e(f18502a, "decode js: " + e2.getMessage());
            return "";
        }
    }

    public static String encodeForJavaScript(String str) {
        return encodeForJavaScript(str, f18503b);
    }

    public static String encodeForJavaScript(String str, char[] cArr) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return a(cArr, str);
        } catch (Exception e2) {
            Log.e(f18502a, "encode js: " + e2.getMessage());
            return "";
        }
    }

    private static String a(char[] cArr, Character ch) {
        if (a(ch.charValue(), cArr)) {
            return "" + ch;
        }
        if (a(ch.charValue()) == null) {
            return "" + ch;
        }
        String hexString = Integer.toHexString(ch.charValue());
        if (ch.charValue() < 256) {
            return "\\x" + "00".substring(hexString.length()) + hexString.toUpperCase(Locale.ENGLISH);
        }
        return "\\u" + "0000".substring(hexString.length()) + hexString.toUpperCase(Locale.ENGLISH);
    }

    private static boolean a(char c2, char[] cArr) {
        for (char c3 : cArr) {
            if (c2 == c3) {
                return true;
            }
        }
        return false;
    }

    private static String a(char c2) {
        if (c2 < 255) {
            return f18504c[c2];
        }
        return b(c2);
    }

    private static Character a(b bVar) throws NumberFormatException {
        bVar.c();
        Character chD = bVar.d();
        if (chD == null) {
            bVar.i();
            return null;
        }
        if (chD.charValue() != '\\') {
            bVar.i();
            return null;
        }
        Character chD2 = bVar.d();
        if (chD2 == null) {
            bVar.i();
            return null;
        }
        if (chD2.charValue() == 'b') {
            return '\b';
        }
        if (chD2.charValue() == 't') {
            return '\t';
        }
        if (chD2.charValue() == 'n') {
            return '\n';
        }
        if (chD2.charValue() == 'v') {
            return (char) 11;
        }
        if (chD2.charValue() == 'f') {
            return '\f';
        }
        if (chD2.charValue() == 'r') {
            return Character.valueOf(CharUtils.CR);
        }
        if (chD2.charValue() == '\"') {
            return Character.valueOf(Typography.quote);
        }
        if (chD2.charValue() == '\'') {
            return '\'';
        }
        if (chD2.charValue() == '\\') {
            return Character.valueOf(IOUtils.DIR_SEPARATOR_WINDOWS);
        }
        int i2 = 0;
        if (Character.toLowerCase(chD2.charValue()) == 'x') {
            StringBuilder sb = new StringBuilder();
            while (i2 < 2) {
                Character chE = bVar.e();
                if (chE != null) {
                    sb.append(chE);
                    i2++;
                } else {
                    bVar.i();
                    return null;
                }
            }
            try {
                int i3 = Integer.parseInt(sb.toString(), 16);
                if (Character.isValidCodePoint(i3)) {
                    return Character.valueOf((char) i3);
                }
            } catch (NumberFormatException unused) {
                bVar.i();
                return null;
            }
        } else if (Character.toLowerCase(chD2.charValue()) == 'u') {
            StringBuilder sb2 = new StringBuilder();
            while (i2 < 4) {
                Character chE2 = bVar.e();
                if (chE2 != null) {
                    sb2.append(chE2);
                    i2++;
                } else {
                    bVar.i();
                    return null;
                }
            }
            try {
                int i4 = Integer.parseInt(sb2.toString(), 16);
                if (Character.isValidCodePoint(i4)) {
                    return Character.valueOf((char) i4);
                }
            } catch (NumberFormatException unused2) {
                bVar.i();
                return null;
            }
        } else if (b.c(chD2)) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(chD2);
            Character chD3 = bVar.d();
            if (!b.c(chD3)) {
                bVar.a(chD3);
            } else {
                sb3.append(chD3);
                Character chD4 = bVar.d();
                if (!b.c(chD4)) {
                    bVar.a(chD4);
                } else {
                    sb3.append(chD4);
                }
            }
            try {
                int i5 = Integer.parseInt(sb3.toString(), 8);
                if (Character.isValidCodePoint(i5)) {
                    return Character.valueOf((char) i5);
                }
            } catch (NumberFormatException unused3) {
                bVar.i();
                return null;
            }
        }
        return chD2;
    }
}
