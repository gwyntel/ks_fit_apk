package com.huawei.hms.hmsscankit;

import android.os.RemoteException;
import android.text.TextUtils;
import com.huawei.hms.hmsscankit.api.IOnResultCallback;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.scankit.p.o4;

/* loaded from: classes4.dex */
class e extends IOnResultCallback.Stub {

    /* renamed from: a, reason: collision with root package name */
    private final OnResultCallback f16566a;

    /* renamed from: b, reason: collision with root package name */
    private String f16567b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f16568c;

    e(OnResultCallback onResultCallback, boolean z2) {
        this.f16566a = onResultCallback;
        this.f16568c = z2;
    }

    @Override // com.huawei.hms.hmsscankit.api.IOnResultCallback
    public void onResult(HmsScan[] hmsScanArr) throws RemoteException {
        HmsScan hmsScan;
        o4.d("OnResultCallbackDelegat", "result callback sdk continueScan" + this.f16568c);
        if (this.f16568c) {
            this.f16566a.onResult(hmsScanArr);
            return;
        }
        if (hmsScanArr == null || hmsScanArr.length <= 0 || (hmsScan = hmsScanArr[0]) == null || TextUtils.equals(this.f16567b, hmsScan.getOriginalValue())) {
            return;
        }
        this.f16567b = hmsScanArr[0].getOriginalValue();
        o4.d("OnResultCallbackDelegat", "result callback sdk continueScan" + this.f16568c);
        this.f16566a.onResult(hmsScanArr);
    }
}
