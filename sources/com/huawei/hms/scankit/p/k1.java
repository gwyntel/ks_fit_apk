package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class k1 {

    /* renamed from: a, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17466a;

    /* renamed from: b, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17467b;

    /* renamed from: c, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17468c;

    /* renamed from: d, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17469d;

    /* renamed from: e, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17470e;

    /* renamed from: f, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17471f;

    /* renamed from: g, reason: collision with root package name */
    public static final Set<BarcodeFormat> f17472g;

    /* renamed from: h, reason: collision with root package name */
    private static final Map<String, Set<BarcodeFormat>> f17473h;

    static {
        EnumSet enumSetOf = EnumSet.of(BarcodeFormat.QR_CODE);
        f17469d = enumSetOf;
        EnumSet enumSetOf2 = EnumSet.of(BarcodeFormat.AZTEC);
        f17470e = enumSetOf2;
        EnumSet enumSetOf3 = EnumSet.of(BarcodeFormat.DATA_MATRIX);
        f17471f = enumSetOf3;
        EnumSet enumSetOf4 = EnumSet.of(BarcodeFormat.PDF_417);
        f17472g = enumSetOf4;
        EnumSet enumSetOf5 = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
        f17468c = enumSetOf5;
        EnumSet enumSetOf6 = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8);
        f17466a = enumSetOf6;
        EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) enumSetOf6);
        f17467b = enumSetCopyOf;
        enumSetCopyOf.addAll(enumSetOf5);
        HashMap map = new HashMap();
        f17473h = map;
        map.put("ONE_D_MODE", enumSetCopyOf);
        map.put("QR_CODE_MODE", enumSetOf);
        map.put("PRODUCT_MODE", enumSetOf6);
        map.put("AZTEC_MODE", enumSetOf2);
        map.put("DATA_MATRIX_MODE", enumSetOf3);
        map.put("PDF417_MODE", enumSetOf4);
    }
}
