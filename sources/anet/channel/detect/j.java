package anet.channel.detect;

import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.l;

/* loaded from: classes2.dex */
final class j implements IConnStrategy {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ l.e f6737a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ConnProtocol f6738b;

    j(l.e eVar, ConnProtocol connProtocol) {
        this.f6737a = eVar;
        this.f6738b = connProtocol;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getConnectionTimeout() {
        return this.f6737a.f7052b.f7023c;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getHeartbeat() {
        return 0;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public String getIp() {
        return this.f6737a.f7051a;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getIpSource() {
        return 2;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getIpType() {
        return 1;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getPort() {
        return this.f6737a.f7052b.f7021a;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public ConnProtocol getProtocol() {
        return this.f6738b;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getReadTimeout() {
        return this.f6737a.f7052b.f7024d;
    }

    @Override // anet.channel.strategy.IConnStrategy
    public int getRetryTimes() {
        return 0;
    }
}
