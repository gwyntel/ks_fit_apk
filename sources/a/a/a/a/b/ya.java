package a.a.a.a.b;

import com.alibaba.ailabs.iot.mesh.TgScanManager;

/* loaded from: classes.dex */
public class ya implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TgScanManager f1650a;

    public ya(TgScanManager tgScanManager) {
        this.f1650a = tgScanManager;
    }

    @Override // java.lang.Runnable
    public void run() {
        TgScanManager tgScanManager = this.f1650a;
        tgScanManager.stopScan(tgScanManager.mContext);
        this.f1650a.logd("scan timeout");
    }
}
