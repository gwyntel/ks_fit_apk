package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.l;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class StrategyTable implements Serializable {

    /* renamed from: e, reason: collision with root package name */
    protected static Comparator<StrategyCollection> f6973e = new o();

    /* renamed from: a, reason: collision with root package name */
    protected String f6974a;

    /* renamed from: b, reason: collision with root package name */
    protected volatile String f6975b;

    /* renamed from: c, reason: collision with root package name */
    Map<String, Long> f6976c;

    /* renamed from: d, reason: collision with root package name */
    protected transient boolean f6977d = false;

    /* renamed from: f, reason: collision with root package name */
    private HostLruCache f6978f;

    /* renamed from: g, reason: collision with root package name */
    private volatile transient int f6979g;

    private static class HostLruCache extends SerialLruCache<String, StrategyCollection> {
        public HostLruCache(int i2) {
            super(i2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // anet.channel.strategy.utils.SerialLruCache
        public boolean entryRemoved(Map.Entry<String, StrategyCollection> entry) {
            if (!entry.getValue().f6955d) {
                return true;
            }
            Iterator<Map.Entry<String, StrategyCollection>> it = entrySet().iterator();
            while (it.hasNext()) {
                if (!it.next().getValue().f6955d) {
                    it.remove();
                    return false;
                }
            }
            return false;
        }
    }

    protected StrategyTable(String str) {
        this.f6974a = str;
        a();
    }

    private void b() {
        if (HttpDispatcher.getInstance().isInitHostsChanged(this.f6974a)) {
            for (String str : HttpDispatcher.getInstance().getInitHosts()) {
                this.f6978f.put(str, new StrategyCollection(str));
            }
        }
    }

    private void c() {
        TreeSet treeSet;
        try {
            if (HttpDispatcher.getInstance().isInitHostsChanged(this.f6974a)) {
                synchronized (this.f6978f) {
                    try {
                        treeSet = null;
                        for (String str : HttpDispatcher.getInstance().getInitHosts()) {
                            if (!this.f6978f.containsKey(str)) {
                                this.f6978f.put(str, new StrategyCollection(str));
                                if (treeSet == null) {
                                    treeSet = new TreeSet();
                                }
                                treeSet.add(str);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (treeSet != null) {
                    a(treeSet);
                }
            }
        } catch (Exception e2) {
            ALog.e("awcn.StrategyTable", "checkInitHost failed", this.f6974a, e2, new Object[0]);
        }
    }

    protected void a() {
        if (this.f6978f == null) {
            this.f6978f = new HostLruCache(256);
            b();
        }
        Iterator<StrategyCollection> it = this.f6978f.values().iterator();
        while (it.hasNext()) {
            it.next().checkInit();
        }
        ALog.i("awcn.StrategyTable", "strategy map", null, "size", Integer.valueOf(this.f6978f.size()));
        this.f6979g = GlobalAppRuntimeInfo.isTargetProcess() ? 0 : -1;
        if (this.f6976c == null) {
            this.f6976c = new ConcurrentHashMap();
        }
    }

    public String getCnameByHost(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this.f6978f) {
            strategyCollection = this.f6978f.get(str);
        }
        if (strategyCollection != null && strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0) {
            a(str);
        }
        if (strategyCollection != null) {
            return strategyCollection.f6954c;
        }
        return null;
    }

    public List<IConnStrategy> queryByHost(String str) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str) || !anet.channel.strategy.utils.c.c(str)) {
            return Collections.EMPTY_LIST;
        }
        c();
        synchronized (this.f6978f) {
            try {
                strategyCollection = this.f6978f.get(str);
                if (strategyCollection == null) {
                    strategyCollection = new StrategyCollection(str);
                    this.f6978f.put(str, strategyCollection);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (strategyCollection.f6953b == 0 || (strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0)) {
            a(str);
        }
        return strategyCollection.queryStrategyList();
    }

    public void update(l.d dVar) {
        l.b[] bVarArr;
        String str;
        ALog.i("awcn.StrategyTable", "update strategyTable with httpDns response", this.f6974a, new Object[0]);
        try {
            this.f6975b = dVar.f7043a;
            this.f6979g = dVar.f7048f;
            bVarArr = dVar.f7044b;
        } catch (Throwable th) {
            ALog.e("awcn.StrategyTable", "fail to update strategyTable", this.f6974a, th, new Object[0]);
        }
        if (bVarArr == null) {
            return;
        }
        synchronized (this.f6978f) {
            for (l.b bVar : bVarArr) {
                try {
                    if (bVar != null && (str = bVar.f7029a) != null) {
                        if (bVar.f7038j) {
                            this.f6978f.remove(str);
                        } else {
                            StrategyCollection strategyCollection = this.f6978f.get(str);
                            if (strategyCollection == null) {
                                strategyCollection = new StrategyCollection(bVar.f7029a);
                                this.f6978f.put(bVar.f7029a, strategyCollection);
                            }
                            strategyCollection.update(bVar);
                        }
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
        this.f6977d = true;
        if (ALog.isPrintLog(1)) {
            StringBuilder sb = new StringBuilder("uniqueId : ");
            sb.append(this.f6974a);
            sb.append("\n-------------------------domains:------------------------------------");
            ALog.d("awcn.StrategyTable", sb.toString(), null, new Object[0]);
            synchronized (this.f6978f) {
                try {
                    for (Map.Entry<String, StrategyCollection> entry : this.f6978f.entrySet()) {
                        sb.setLength(0);
                        sb.append(entry.getKey());
                        sb.append(" = ");
                        sb.append(entry.getValue().toString());
                        ALog.d("awcn.StrategyTable", sb.toString(), null, new Object[0]);
                    }
                } finally {
                }
            }
        }
    }

    private void b(Set<String> set) {
        TreeSet treeSet = new TreeSet(f6973e);
        synchronized (this.f6978f) {
            treeSet.addAll(this.f6978f.values());
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Iterator it = treeSet.iterator();
        while (it.hasNext()) {
            StrategyCollection strategyCollection = (StrategyCollection) it.next();
            if (!strategyCollection.isExpired() || set.size() >= 40) {
                return;
            }
            strategyCollection.f6953b = 30000 + jCurrentTimeMillis;
            set.add(strategyCollection.f6952a);
        }
    }

    private void a(String str) {
        TreeSet treeSet = new TreeSet();
        treeSet.add(str);
        a(treeSet);
    }

    protected void a(String str, boolean z2) {
        StrategyCollection strategyCollection;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.f6978f) {
            try {
                strategyCollection = this.f6978f.get(str);
                if (strategyCollection == null) {
                    strategyCollection = new StrategyCollection(str);
                    this.f6978f.put(str, strategyCollection);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2 || strategyCollection.f6953b == 0 || (strategyCollection.isExpired() && AmdcRuntimeInfo.getAmdcLimitLevel() == 0)) {
            a(str);
        }
    }

    private void a(Set<String> set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        if ((GlobalAppRuntimeInfo.isAppBackground() && AppLifecycle.lastEnterBackgroundTime > 0) || !NetworkStatusHelper.isConnected()) {
            ALog.i("awcn.StrategyTable", "app in background or no network", this.f6974a, new Object[0]);
            return;
        }
        int amdcLimitLevel = AmdcRuntimeInfo.getAmdcLimitLevel();
        if (amdcLimitLevel == 3) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        synchronized (this.f6978f) {
            try {
                Iterator<String> it = set.iterator();
                while (it.hasNext()) {
                    StrategyCollection strategyCollection = this.f6978f.get(it.next());
                    if (strategyCollection != null) {
                        strategyCollection.f6953b = 30000 + jCurrentTimeMillis;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (amdcLimitLevel == 0) {
            b(set);
        }
        HttpDispatcher.getInstance().sendAmdcRequest(set, this.f6979g);
    }

    void a(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        StrategyCollection strategyCollection;
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.StrategyTable", "[notifyConnEvent]", null, "Host", str, "IConnStrategy", iConnStrategy, "ConnEvent", connEvent);
        }
        String str2 = iConnStrategy.getProtocol().protocol;
        if (ConnType.HTTP3.equals(str2) || ConnType.HTTP3_PLAIN.equals(str2)) {
            anet.channel.e.a.a(connEvent.isSuccess);
            ALog.e("awcn.StrategyTable", "enable http3", null, "uniqueId", this.f6974a, "enable", Boolean.valueOf(connEvent.isSuccess));
        }
        if (!connEvent.isSuccess && anet.channel.strategy.utils.c.b(iConnStrategy.getIp())) {
            this.f6976c.put(str, Long.valueOf(System.currentTimeMillis()));
            ALog.e("awcn.StrategyTable", "disable ipv6", null, "uniqueId", this.f6974a, "host", str);
        }
        synchronized (this.f6978f) {
            strategyCollection = this.f6978f.get(str);
        }
        if (strategyCollection != null) {
            strategyCollection.notifyConnEvent(iConnStrategy, connEvent);
        }
    }

    boolean a(String str, long j2) {
        Long l2 = this.f6976c.get(str);
        if (l2 == null) {
            return false;
        }
        if (l2.longValue() + j2 >= System.currentTimeMillis()) {
            return true;
        }
        this.f6976c.remove(str);
        return false;
    }
}
