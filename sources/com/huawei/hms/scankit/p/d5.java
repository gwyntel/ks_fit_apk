package com.huawei.hms.scankit.p;

import com.huawei.hms.scankit.util.LoadOpencvJNIUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class d5 {

    /* renamed from: a, reason: collision with root package name */
    public List<i2> f17108a = new ArrayList();

    /* renamed from: b, reason: collision with root package name */
    private int f17109b = 0;

    public void a(boolean z2, byte[] bArr, int i2, int i3, int i4, boolean z3) {
        float[] fArrMultiBarcodeDetect = LoadOpencvJNIUtil.multiBarcodeDetect(bArr, i2, i3, i4, z3);
        if (fArrMultiBarcodeDetect != null) {
            this.f17109b = fArrMultiBarcodeDetect.length / 10;
        } else {
            this.f17109b = 0;
        }
        for (int i5 = 0; i5 < this.f17109b; i5++) {
            int i6 = i5 * 10;
            if (w7.a(fArrMultiBarcodeDetect, i6)) {
                int i7 = i6 + 1;
                if (w7.a(fArrMultiBarcodeDetect, i7)) {
                    int i8 = i6 + 2;
                    if (w7.a(fArrMultiBarcodeDetect, i8)) {
                        int i9 = i6 + 3;
                        if (w7.a(fArrMultiBarcodeDetect, i9)) {
                            int i10 = i6 + 4;
                            if (w7.a(fArrMultiBarcodeDetect, i10)) {
                                int i11 = i6 + 5;
                                if (w7.a(fArrMultiBarcodeDetect, i11)) {
                                    int i12 = i6 + 6;
                                    if (w7.a(fArrMultiBarcodeDetect, i12)) {
                                        int i13 = i6 + 7;
                                        if (w7.a(fArrMultiBarcodeDetect, i13)) {
                                            int i14 = i6 + 8;
                                            if (w7.a(fArrMultiBarcodeDetect, i14)) {
                                                int i15 = i6 + 9;
                                                if (w7.a(fArrMultiBarcodeDetect, i15)) {
                                                    this.f17108a.add(new i2(z2, fArrMultiBarcodeDetect[i6], fArrMultiBarcodeDetect[i7], fArrMultiBarcodeDetect[i8], fArrMultiBarcodeDetect[i9], fArrMultiBarcodeDetect[i10], fArrMultiBarcodeDetect[i11], fArrMultiBarcodeDetect[i12], fArrMultiBarcodeDetect[i13], fArrMultiBarcodeDetect[i14], fArrMultiBarcodeDetect[i15]));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
