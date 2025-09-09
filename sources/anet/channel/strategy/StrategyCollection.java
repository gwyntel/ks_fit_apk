package anet.channel.strategy;

import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.PolicyVersionStat;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.strategy.l;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
class StrategyCollection implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    String f6952a;

    /* renamed from: b, reason: collision with root package name */
    volatile long f6953b;

    /* renamed from: c, reason: collision with root package name */
    volatile String f6954c;

    /* renamed from: d, reason: collision with root package name */
    boolean f6955d;

    /* renamed from: e, reason: collision with root package name */
    int f6956e;

    /* renamed from: f, reason: collision with root package name */
    private StrategyList f6957f;

    /* renamed from: g, reason: collision with root package name */
    private transient long f6958g;

    /* renamed from: h, reason: collision with root package name */
    private transient boolean f6959h;

    public StrategyCollection() {
        this.f6957f = null;
        this.f6953b = 0L;
        this.f6954c = null;
        this.f6955d = false;
        this.f6956e = 0;
        this.f6958g = 0L;
        this.f6959h = true;
    }

    public synchronized void checkInit() {
        if (System.currentTimeMillis() - this.f6953b > 172800000) {
            this.f6957f = null;
            return;
        }
        StrategyList strategyList = this.f6957f;
        if (strategyList != null) {
            strategyList.checkInit();
        }
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.f6953b;
    }

    public synchronized void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        StrategyList strategyList = this.f6957f;
        if (strategyList != null) {
            strategyList.notifyConnEvent(iConnStrategy, connEvent);
            if (!connEvent.isSuccess && this.f6957f.shouldRefresh()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f6958g > 60000) {
                    StrategyCenter.getInstance().forceRefreshStrategy(this.f6952a);
                    this.f6958g = jCurrentTimeMillis;
                }
            }
        }
    }

    public synchronized List<IConnStrategy> queryStrategyList() {
        if (this.f6957f == null) {
            return Collections.EMPTY_LIST;
        }
        if (this.f6959h) {
            this.f6959h = false;
            PolicyVersionStat policyVersionStat = new PolicyVersionStat(this.f6952a, this.f6956e);
            policyVersionStat.reportType = 0;
            AppMonitor.getInstance().commitStat(policyVersionStat);
        }
        return this.f6957f.getStrategyList();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("\nStrategyList = ");
        sb.append(this.f6953b);
        StrategyList strategyList = this.f6957f;
        if (strategyList != null) {
            sb.append(strategyList.toString());
        } else if (this.f6954c != null) {
            sb.append('[');
            sb.append(this.f6952a);
            sb.append("=>");
            sb.append(this.f6954c);
            sb.append(']');
        } else {
            sb.append("[]");
        }
        return sb.toString();
    }

    public synchronized void update(l.b bVar) {
        l.e[] eVarArr;
        l.a[] aVarArr;
        this.f6953b = System.currentTimeMillis() + (bVar.f7030b * 1000);
        if (!bVar.f7029a.equalsIgnoreCase(this.f6952a)) {
            ALog.e("StrategyCollection", "update error!", null, "host", this.f6952a, "dnsInfo.host", bVar.f7029a);
            return;
        }
        int i2 = this.f6956e;
        int i3 = bVar.f7040l;
        if (i2 != i3) {
            this.f6956e = i3;
            PolicyVersionStat policyVersionStat = new PolicyVersionStat(this.f6952a, i3);
            policyVersionStat.reportType = 1;
            AppMonitor.getInstance().commitStat(policyVersionStat);
        }
        this.f6954c = bVar.f7032d;
        String[] strArr = bVar.f7034f;
        if ((strArr != null && strArr.length != 0 && (aVarArr = bVar.f7036h) != null && aVarArr.length != 0) || ((eVarArr = bVar.f7037i) != null && eVarArr.length != 0)) {
            if (this.f6957f == null) {
                this.f6957f = new StrategyList();
            }
            this.f6957f.update(bVar);
            return;
        }
        this.f6957f = null;
    }

    protected StrategyCollection(String str) {
        this.f6957f = null;
        this.f6953b = 0L;
        this.f6954c = null;
        this.f6955d = false;
        this.f6956e = 0;
        this.f6958g = 0L;
        this.f6959h = true;
        this.f6952a = str;
        this.f6955d = DispatchConstants.isAmdcServerDomain(str);
    }
}
