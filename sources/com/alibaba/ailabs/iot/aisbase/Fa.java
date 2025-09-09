package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.callback.ILeScanCallback;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import java.util.List;

/* loaded from: classes2.dex */
public class Fa extends BLEScannerProxy.a {

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ BLEScannerProxy f8283d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Fa(BLEScannerProxy bLEScannerProxy, boolean z2, List list, ILeScanCallback iLeScanCallback) {
        super(z2, list, iLeScanCallback);
        this.f8283d = bLEScannerProxy;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy.a, aisscanner.ScanCallback
    public void onScanFailed(int i2) {
        super.onScanFailed(i2);
        this.f8283d.stopScan();
    }
}
