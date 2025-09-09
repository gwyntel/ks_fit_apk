package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.util.ALog;
import java.io.File;

/* loaded from: classes2.dex */
class d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f6989a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ StrategyInfoHolder f6990b;

    d(StrategyInfoHolder strategyInfoHolder, String str) {
        this.f6990b = strategyInfoHolder;
        this.f6989a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ALog.i("awcn.StrategyInfoHolder", "start loading strategy files", null, new Object[0]);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (AwcnConfig.isAsyncLoadStrategyEnable()) {
                ALog.i("awcn.StrategyInfoHolder", "load strategy async", null, new Object[0]);
                if (!TextUtils.isEmpty(this.f6989a)) {
                    this.f6990b.a(this.f6989a, true);
                }
                StrategyConfig strategyConfig = (StrategyConfig) m.a("StrategyConfig", null);
                if (strategyConfig != null) {
                    strategyConfig.b();
                    strategyConfig.a(this.f6990b);
                    synchronized (this.f6990b) {
                        this.f6990b.f6964b = strategyConfig;
                    }
                }
            }
            File[] fileArrB = m.b();
            if (fileArrB == null) {
                return;
            }
            int i2 = 0;
            for (int i3 = 0; i3 < fileArrB.length && i2 < 2; i3++) {
                File file = fileArrB[i3];
                if (!file.isDirectory()) {
                    String name = file.getName();
                    if (!name.equals(this.f6989a) && !name.startsWith("StrategyConfig")) {
                        this.f6990b.a(name, false);
                        i2++;
                    }
                }
            }
            ALog.i("awcn.StrategyInfoHolder", "end loading strategy files", null, "total cost", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
        } catch (Exception unused) {
        }
    }
}
