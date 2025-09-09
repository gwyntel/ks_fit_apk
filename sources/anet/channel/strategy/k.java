package anet.channel.strategy;

import java.util.Comparator;

/* loaded from: classes2.dex */
class k implements Comparator<IPConnStrategy> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ StrategyList f7020a;

    k(StrategyList strategyList) {
        this.f7020a = strategyList;
    }

    @Override // java.util.Comparator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(IPConnStrategy iPConnStrategy, IPConnStrategy iPConnStrategy2) {
        int i2;
        int i3;
        ConnHistoryItem connHistoryItem = (ConnHistoryItem) this.f7020a.f6970b.get(Integer.valueOf(iPConnStrategy.getUniqueId()));
        ConnHistoryItem connHistoryItem2 = (ConnHistoryItem) this.f7020a.f6970b.get(Integer.valueOf(iPConnStrategy2.getUniqueId()));
        int iA = connHistoryItem.a();
        int iA2 = connHistoryItem2.a();
        if (iA != iA2) {
            return iA - iA2;
        }
        if (iPConnStrategy.f6949a != iPConnStrategy2.f6949a) {
            i2 = iPConnStrategy.f6949a;
            i3 = iPConnStrategy2.f6949a;
        } else {
            i2 = iPConnStrategy.protocol.isHttp;
            i3 = iPConnStrategy2.protocol.isHttp;
        }
        return i2 - i3;
    }
}
