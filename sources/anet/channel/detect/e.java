package anet.channel.detect;

import anet.channel.AwcnConfig;
import anet.channel.strategy.IStrategyListener;
import anet.channel.strategy.l;
import anet.channel.util.ALog;

/* loaded from: classes2.dex */
class e implements IStrategyListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f6727a;

    e(d dVar) {
        this.f6727a = dVar;
    }

    @Override // anet.channel.strategy.IStrategyListener
    public void onStrategyUpdated(l.d dVar) {
        l.c[] cVarArr;
        int i2 = 0;
        ALog.i("anet.HorseRaceDetector", "onStrategyUpdated", null, new Object[0]);
        if (!AwcnConfig.isHorseRaceEnable() || (cVarArr = dVar.f7045c) == null || cVarArr.length == 0) {
            return;
        }
        synchronized (this.f6727a.f6725a) {
            while (true) {
                try {
                    l.c[] cVarArr2 = dVar.f7045c;
                    if (i2 < cVarArr2.length) {
                        l.c cVar = cVarArr2[i2];
                        this.f6727a.f6725a.put(cVar.f7041a, cVar);
                        i2++;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
