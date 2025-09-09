package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.AwcnConfig;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.StrategyStatObject;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.dispatch.AmdcRuntimeInfo;
import anet.channel.strategy.l;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import com.facebook.internal.AnalyticsEvents;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
class StrategyInfoHolder implements NetworkStatusHelper.INetworkStatusChangeListener {

    /* renamed from: a, reason: collision with root package name */
    Map<String, StrategyTable> f6963a = new LruStrategyMap();

    /* renamed from: b, reason: collision with root package name */
    volatile StrategyConfig f6964b = null;

    /* renamed from: c, reason: collision with root package name */
    final a f6965c = new a();

    /* renamed from: d, reason: collision with root package name */
    private final StrategyTable f6966d = new StrategyTable(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN);

    /* renamed from: e, reason: collision with root package name */
    private final Set<String> f6967e = new HashSet();

    /* renamed from: f, reason: collision with root package name */
    private volatile String f6968f = "";

    /* JADX INFO: Access modifiers changed from: private */
    static class LruStrategyMap extends SerialLruCache<String, StrategyTable> {
        public LruStrategyMap() {
            super(3);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // anet.channel.strategy.utils.SerialLruCache
        public boolean entryRemoved(Map.Entry<String, StrategyTable> entry) {
            anet.channel.strategy.utils.a.a(new f(this, entry));
            return true;
        }
    }

    private StrategyInfoHolder() {
        try {
            e();
            g();
        } catch (Throwable unused) {
        }
        f();
    }

    public static StrategyInfoHolder a() {
        return new StrategyInfoHolder();
    }

    private void e() {
        NetworkStatusHelper.addStatusChangeListener(this);
        this.f6968f = a(NetworkStatusHelper.getStatus());
    }

    private void f() {
        Iterator<Map.Entry<String, StrategyTable>> it = this.f6963a.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().a();
        }
        synchronized (this) {
            try {
                if (this.f6964b == null) {
                    StrategyConfig strategyConfig = new StrategyConfig();
                    strategyConfig.b();
                    strategyConfig.a(this);
                    this.f6964b = strategyConfig;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private void g() {
        ALog.i("awcn.StrategyInfoHolder", "restore", null, new Object[0]);
        String str = this.f6968f;
        if (!AwcnConfig.isAsyncLoadStrategyEnable()) {
            if (!TextUtils.isEmpty(str)) {
                a(str, true);
            }
            this.f6964b = (StrategyConfig) m.a("StrategyConfig", null);
            if (this.f6964b != null) {
                this.f6964b.b();
                this.f6964b.a(this);
            }
        }
        anet.channel.strategy.utils.a.a(new d(this, str));
    }

    void b() {
        NetworkStatusHelper.removeStatusChangeListener(this);
    }

    void c() {
        synchronized (this) {
            try {
                for (StrategyTable strategyTable : this.f6963a.values()) {
                    if (strategyTable.f6977d) {
                        StrategyStatObject strategyStatObject = new StrategyStatObject(1);
                        String str = strategyTable.f6974a;
                        strategyStatObject.writeStrategyFileId = str;
                        m.a(strategyTable, str, strategyStatObject);
                        strategyTable.f6977d = false;
                    }
                }
                m.a(this.f6964b.a(), "StrategyConfig", null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    StrategyTable d() {
        StrategyTable strategyTable = this.f6966d;
        String str = this.f6968f;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.f6963a) {
                strategyTable = this.f6963a.get(str);
                if (strategyTable == null) {
                    strategyTable = new StrategyTable(str);
                    this.f6963a.put(str, strategyTable);
                }
            }
        }
        return strategyTable;
    }

    @Override // anet.channel.status.NetworkStatusHelper.INetworkStatusChangeListener
    public void onNetworkStatusChanged(NetworkStatusHelper.NetworkStatus networkStatus) {
        this.f6968f = a(networkStatus);
        String str = this.f6968f;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.f6963a) {
            try {
                if (!this.f6963a.containsKey(str)) {
                    anet.channel.strategy.utils.a.a(new e(this, str));
                }
            } finally {
            }
        }
    }

    protected void a(String str, boolean z2) {
        StrategyStatObject strategyStatObject;
        synchronized (this.f6967e) {
            if (this.f6967e.contains(str)) {
                return;
            }
            this.f6967e.add(str);
            if (z2) {
                strategyStatObject = new StrategyStatObject(0);
                strategyStatObject.readStrategyFileId = str;
            } else {
                strategyStatObject = null;
            }
            StrategyTable strategyTable = (StrategyTable) m.a(str, strategyStatObject);
            if (strategyTable != null) {
                strategyTable.a();
                synchronized (this.f6963a) {
                    this.f6963a.put(strategyTable.f6974a, strategyTable);
                }
            }
            synchronized (this.f6967e) {
                this.f6967e.remove(str);
            }
            if (z2) {
                strategyStatObject.isSucceed = strategyTable != null ? 1 : 0;
                AppMonitor.getInstance().commitStat(strategyStatObject);
            }
        }
    }

    private String a(NetworkStatusHelper.NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String strMd5ToHex = StringUtils.md5ToHex(NetworkStatusHelper.getWifiBSSID());
            return "WIFI$" + (TextUtils.isEmpty(strMd5ToHex) ? "" : strMd5ToHex);
        }
        if (!networkStatus.isMobile()) {
            return "";
        }
        return networkStatus.getType() + "$" + NetworkStatusHelper.getApn();
    }

    void a(l.d dVar) {
        int i2 = dVar.f7049g;
        if (i2 != 0) {
            AmdcRuntimeInfo.updateAmdcLimit(i2, dVar.f7050h);
        }
        d().update(dVar);
        this.f6964b.a(dVar);
    }
}
