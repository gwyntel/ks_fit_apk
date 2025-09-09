package b;

import aisscanner.ScanCallback;
import aisscanner.ScanResult;
import java.util.List;

/* loaded from: classes2.dex */
public class w extends ScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ y f7571a;

    public w(y yVar) {
        this.f7571a = yVar;
    }

    @Override // aisscanner.ScanCallback
    public void onBatchScanResults(List<ScanResult> list) {
    }

    @Override // aisscanner.ScanCallback
    public void onScanFailed(int i2) {
        a.a.a.a.b.m.a.b("SIGMeshNetworkTransportManager", "onScanFailed: " + i2);
        this.f7571a.f7575c = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x0173  */
    @Override // aisscanner.ScanCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onScanResult(int r19, aisscanner.ScanResult r20) {
        /*
            Method dump skipped, instructions count: 594
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: b.w.onScanResult(int, aisscanner.ScanResult):void");
    }
}
