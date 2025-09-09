package anet.channel.strategy;

import java.util.Comparator;

/* loaded from: classes2.dex */
final class o implements Comparator<StrategyCollection> {
    o() {
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(StrategyCollection strategyCollection, StrategyCollection strategyCollection2) {
        return strategyCollection.f6953b != strategyCollection2.f6953b ? (int) (strategyCollection.f6953b - strategyCollection2.f6953b) : strategyCollection.f6952a.compareTo(strategyCollection2.f6952a);
    }
}
