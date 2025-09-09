package a.a.a.a.b;

import aisscanner.ScanCallback;
import aisscanner.ScanResult;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.mesh.TgScanManager;
import java.util.List;

/* loaded from: classes.dex */
public class va extends ScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TgScanManager f1644a;

    public va(TgScanManager tgScanManager) {
        this.f1644a = tgScanManager;
    }

    @Override // aisscanner.ScanCallback
    public void onBatchScanResults(@NonNull List<ScanResult> list) {
    }

    @Override // aisscanner.ScanCallback
    public void onScanFailed(int i2) {
        this.f1644a.mScanner.scanningStopped();
        if (this.f1644a.mScanHandler != null) {
            this.f1644a.mScanHandler.onScanFailed(-6, "onScanFailed: " + i2);
        }
        this.f1644a.loge("onScanFailed: " + i2);
    }

    @Override // aisscanner.ScanCallback
    public void onScanResult(int i2, @NonNull ScanResult scanResult) {
        if (this.f1644a.mScanner.isStopScanRequested()) {
            return;
        }
        if (this.f1644a.mScanHandler != null) {
            this.f1644a.mScanHandler.onScanResult(scanResult, this.f1644a.mScanner);
        }
        a.a.a.a.b.m.a.a(TgScanManager.TAG, "Scaned devices size: " + this.f1644a.mScanner.getDevices().size());
    }
}
