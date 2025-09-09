package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.aiscan.common.BarcodeFormat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class n3 {
    public static List<List<BarcodeFormat>> a(int i2) {
        int i3 = 11;
        int i4 = i2 <= 0 ? 8191 : i2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        if (i4 > 0) {
            BarcodeFormat[] barcodeFormatArr = {BarcodeFormat.CODE_128, BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODABAR, BarcodeFormat.DATA_MATRIX, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.ITF, BarcodeFormat.QR_CODE, BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.PDF_417, BarcodeFormat.AZTEC, BarcodeFormat.HARMONY_CODE, BarcodeFormat.WXCODE};
            int[] iArr = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384};
            if ((i4 & iArr[8]) != 0) {
                arrayList.add(barcodeFormatArr[8]);
            }
            if ((iArr[11] & i4) != 0) {
                arrayList3.add(barcodeFormatArr[11]);
            }
            int i5 = 0;
            while (i5 < 15) {
                if ((i4 & iArr[i5]) != 0 && i5 != 8 && i5 != i3) {
                    if (i5 == 4) {
                        arrayList5.add(barcodeFormatArr[i5]);
                    } else if (i5 == 12) {
                        arrayList4.add(barcodeFormatArr[i5]);
                    } else if (i5 == 13) {
                        arrayList6.add(barcodeFormatArr[i5]);
                    } else if (i5 == 14) {
                        arrayList7.add(barcodeFormatArr[i5]);
                    } else {
                        arrayList2.add(barcodeFormatArr[i5]);
                    }
                }
                i5++;
                i3 = 11;
            }
        }
        ArrayList arrayList8 = new ArrayList();
        arrayList8.add(arrayList);
        arrayList8.add(arrayList2);
        arrayList8.add(arrayList3);
        arrayList8.add(arrayList5);
        arrayList8.add(arrayList4);
        arrayList8.add(arrayList6);
        arrayList8.add(arrayList7);
        return arrayList8;
    }
}
