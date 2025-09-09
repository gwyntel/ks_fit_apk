package anet.channel.strategy;

import anet.channel.statist.StrategyStatObject;
import anet.channel.strategy.StrategyInfoHolder;
import java.io.Serializable;
import java.util.Map;

/* loaded from: classes2.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Map.Entry f7007a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ StrategyInfoHolder.LruStrategyMap f7008b;

    f(StrategyInfoHolder.LruStrategyMap lruStrategyMap, Map.Entry entry) {
        this.f7008b = lruStrategyMap;
        this.f7007a = entry;
    }

    @Override // java.lang.Runnable
    public void run() {
        StrategyTable strategyTable = (StrategyTable) this.f7007a.getValue();
        if (strategyTable.f6977d) {
            StrategyStatObject strategyStatObject = new StrategyStatObject(1);
            strategyStatObject.writeStrategyFileId = strategyTable.f6974a;
            m.a((Serializable) this.f7007a.getValue(), strategyTable.f6974a, strategyStatObject);
            strategyTable.f6977d = false;
        }
    }
}
