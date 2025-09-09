package com.huawei.hms.scankit.p;

import com.google.zxing.common.StringUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public enum o0 {
    Cp437(new int[]{0, 2}, new String[0]),
    ISO8859_1(new int[]{1, 3}, "ISO-8859-1"),
    ISO8859_2(4, "ISO-8859-2"),
    ISO8859_3(5, "ISO-8859-3"),
    ISO8859_4(6, "ISO-8859-4"),
    ISO8859_5(7, "ISO-8859-5"),
    ISO8859_6(8, "ISO-8859-6"),
    ISO8859_7(9, "ISO-8859-7"),
    ISO8859_8(10, "ISO-8859-8"),
    ISO8859_9(11, "ISO-8859-9"),
    ISO8859_10(12, "ISO-8859-10"),
    ISO8859_11(13, "ISO-8859-11"),
    ISO8859_13(15, "ISO-8859-13"),
    ISO8859_14(16, "ISO-8859-14"),
    ISO8859_15(17, "ISO-8859-15"),
    ISO8859_16(18, "ISO-8859-16"),
    SJIS(20, "Shift_JIS"),
    Cp1250(21, "windows-1250"),
    Cp1251(22, "windows-1251"),
    Cp1252(23, "windows-1252"),
    Cp1256(24, "windows-1256"),
    UnicodeBigUnmarked(25, "UTF-16BE", "UnicodeBig"),
    UTF8(26, "UTF-8"),
    ASCII(new int[]{27, 170}, "US-ASCII"),
    Big5(28),
    GB18030(29, StringUtils.GB2312, "EUC_CN", "GBK"),
    EUC_KR(30, "EUC-KR");

    private static final Map<Integer, o0> D = new HashMap();
    private static final Map<String, o0> E = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    private final int[] f17609a;

    /* renamed from: b, reason: collision with root package name */
    private final String[] f17610b;

    static {
        for (o0 o0Var : values()) {
            for (int i2 : o0Var.f17609a) {
                D.put(Integer.valueOf(i2), o0Var);
            }
            E.put(o0Var.name(), o0Var);
            for (String str : o0Var.f17610b) {
                E.put(str, o0Var);
            }
        }
    }

    o0(int i2) {
        this(new int[]{i2}, new String[0]);
    }

    public int a() {
        return this.f17609a[0];
    }

    o0(int i2, String... strArr) {
        this.f17609a = new int[]{i2};
        this.f17610b = strArr;
    }

    public static o0 a(int i2) throws a {
        if (i2 < 0 || i2 >= 900) {
            throw a.a();
        }
        return D.get(Integer.valueOf(i2));
    }

    public static o0 a(String str) {
        return E.get(str);
    }

    o0(int[] iArr, String... strArr) {
        this.f17609a = iArr;
        this.f17610b = strArr;
    }
}
