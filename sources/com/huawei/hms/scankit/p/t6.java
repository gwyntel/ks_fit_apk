package com.huawei.hms.scankit.p;

import android.graphics.Point;
import android.util.SparseArray;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;

/* loaded from: classes4.dex */
public abstract class t6 {

    /* renamed from: a, reason: collision with root package name */
    private static final SparseArray<Integer> f17820a;

    static {
        SparseArray<Integer> sparseArray = new SparseArray<>();
        f17820a = sparseArray;
        sparseArray.put(BarcodeFormat.AZTEC.ordinal(), Integer.valueOf(HmsScanBase.AZTEC_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.CODABAR.ordinal(), Integer.valueOf(HmsScanBase.CODABAR_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.CODE_39.ordinal(), Integer.valueOf(HmsScanBase.CODE39_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.CODE_93.ordinal(), Integer.valueOf(HmsScanBase.CODE93_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.CODE_128.ordinal(), Integer.valueOf(HmsScanBase.CODE128_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.DATA_MATRIX.ordinal(), Integer.valueOf(HmsScanBase.DATAMATRIX_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.EAN_8.ordinal(), Integer.valueOf(HmsScanBase.EAN8_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.EAN_13.ordinal(), Integer.valueOf(HmsScanBase.EAN13_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.ITF.ordinal(), Integer.valueOf(HmsScanBase.ITF14_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.PDF_417.ordinal(), Integer.valueOf(HmsScanBase.PDF417_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.QR_CODE.ordinal(), Integer.valueOf(HmsScanBase.QRCODE_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.UPC_A.ordinal(), Integer.valueOf(HmsScanBase.UPCCODE_A_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.UPC_E.ordinal(), Integer.valueOf(HmsScanBase.UPCCODE_E_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.WXCODE.ordinal(), Integer.valueOf(HmsScanBase.WX_SCAN_TYPE));
        sparseArray.put(BarcodeFormat.HARMONY_CODE.ordinal(), Integer.valueOf(HmsScanBase.MULTI_FUNCTIONAL_SCAN_TYPE));
    }

    public static int a(BarcodeFormat barcodeFormat) {
        if (barcodeFormat == null) {
            return HmsScanBase.FORMAT_UNKNOWN;
        }
        Integer num = f17820a.get(barcodeFormat.ordinal());
        return num == null ? HmsScanBase.FORMAT_UNKNOWN : num.intValue();
    }

    protected static String b(String str) {
        int iIndexOf = str.indexOf(92);
        if (iIndexOf < 0) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length - 1);
        sb.append(str.toCharArray(), 0, iIndexOf);
        boolean z2 = false;
        while (iIndexOf < length) {
            char cCharAt = str.charAt(iIndexOf);
            if (z2 || cCharAt != '\\') {
                sb.append(cCharAt);
                z2 = false;
            } else {
                z2 = true;
            }
            iIndexOf++;
        }
        return sb.toString();
    }

    public abstract HmsScan b(s6 s6Var);

    public static Point[] a(u6[] u6VarArr) {
        if (u6VarArr != null && u6VarArr.length > 0) {
            Point[] pointArr = new Point[u6VarArr.length];
            for (int i2 = 0; i2 < u6VarArr.length; i2++) {
                if (u6VarArr[i2] != null) {
                    pointArr[i2] = new Point((int) u6VarArr[i2].b(), (int) u6VarArr[i2].c());
                }
            }
            return pointArr;
        }
        return new Point[0];
    }

    protected static String a(s6 s6Var) {
        String strK = s6Var.k();
        if (strK == null) {
            return "";
        }
        return strK.startsWith("\ufeff") ? strK.substring(1) : strK;
    }

    protected static String a(String str) {
        if (str == null) {
            return str;
        }
        int length = str.length();
        while (length > 0 && str.charAt(length - 1) <= ' ') {
            length--;
        }
        return length < str.length() ? str.substring(0, length) : str;
    }
}
