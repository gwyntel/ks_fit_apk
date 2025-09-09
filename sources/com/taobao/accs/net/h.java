package com.taobao.accs.net;

import anet.channel.entity.ConnType;
import anet.channel.strategy.IConnStrategy;
import anet.channel.strategy.StrategyCenter;
import anet.channel.strategy.dispatch.HttpDispatcher;
import com.taobao.accs.utl.ALog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private int f20225a = 0;

    /* renamed from: b, reason: collision with root package name */
    private List<IConnStrategy> f20226b = new ArrayList();

    public h(String str) {
        HttpDispatcher.getInstance().addListener(new i(this));
        a(str);
    }

    public List<IConnStrategy> a(String str) {
        List<IConnStrategy> connStrategyListByHost;
        if ((this.f20225a == 0 || this.f20226b.isEmpty()) && (connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str)) != null && !connStrategyListByHost.isEmpty()) {
            this.f20226b.clear();
            for (IConnStrategy iConnStrategy : connStrategyListByHost) {
                ConnType connTypeValueOf = ConnType.valueOf(iConnStrategy.getProtocol());
                if (connTypeValueOf.getTypeLevel() == ConnType.TypeLevel.SPDY && connTypeValueOf.isSSL()) {
                    this.f20226b.add(iConnStrategy);
                }
            }
        }
        return this.f20226b;
    }

    public void b() {
        this.f20225a++;
        if (ALog.isPrintLog(ALog.Level.D)) {
            ALog.d("HttpDnsProvider", "updateStrategyPos StrategyPos:" + this.f20225a, new Object[0]);
        }
    }

    public int c() {
        return this.f20225a;
    }

    public void b(String str) {
        StrategyCenter.getInstance().forceRefreshStrategy(str);
    }

    public IConnStrategy a() {
        return a(this.f20226b);
    }

    public IConnStrategy a(List<IConnStrategy> list) {
        if (list != null && !list.isEmpty()) {
            int i2 = this.f20225a;
            if (i2 < 0 || i2 >= list.size()) {
                this.f20225a = 0;
            }
            return list.get(this.f20225a);
        }
        ALog.d("HttpDnsProvider", "strategys null or 0", new Object[0]);
        return null;
    }
}
