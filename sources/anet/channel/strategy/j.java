package anet.channel.strategy;

import anet.channel.strategy.StrategyList;
import anet.channel.strategy.l;

/* loaded from: classes2.dex */
class j implements StrategyList.Predicate<IPConnStrategy> {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ l.a f7016a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f7017b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ ConnProtocol f7018c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ StrategyList f7019d;

    j(StrategyList strategyList, l.a aVar, String str, ConnProtocol connProtocol) {
        this.f7019d = strategyList;
        this.f7016a = aVar;
        this.f7017b = str;
        this.f7018c = connProtocol;
    }

    @Override // anet.channel.strategy.StrategyList.Predicate
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public boolean apply(IPConnStrategy iPConnStrategy) {
        return iPConnStrategy.getPort() == this.f7016a.f7021a && iPConnStrategy.getIp().equals(this.f7017b) && iPConnStrategy.protocol.equals(this.f7018c);
    }
}
