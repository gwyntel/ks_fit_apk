package anet.channel.e;

import anet.channel.entity.ConnType;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.IStrategyFilter;

/* loaded from: classes2.dex */
final class b implements IStrategyFilter {
    b() {
    }

    @Override // anet.channel.strategy.IStrategyFilter
    public boolean accept(IConnStrategy iConnStrategy) {
        String str = iConnStrategy.getProtocol().protocol;
        return ConnType.HTTP3.equals(str) || ConnType.HTTP3_PLAIN.equals(str);
    }
}
