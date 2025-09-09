package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.l;
import anet.channel.strategy.utils.SerialLruCache;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class StrategyConfig implements Serializable {
    public static final String NO_RESULT = "No_Result";

    /* renamed from: a, reason: collision with root package name */
    private SerialLruCache<String, String> f6960a = null;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, String> f6961b = null;

    /* renamed from: c, reason: collision with root package name */
    private transient StrategyInfoHolder f6962c = null;

    StrategyConfig() {
    }

    StrategyConfig a() {
        StrategyConfig strategyConfig = new StrategyConfig();
        synchronized (this) {
            strategyConfig.f6960a = new SerialLruCache<>(this.f6960a, 256);
            strategyConfig.f6961b = new ConcurrentHashMap(this.f6961b);
            strategyConfig.f6962c = this.f6962c;
        }
        return strategyConfig;
    }

    void b() {
        if (this.f6960a == null) {
            this.f6960a = new SerialLruCache<>(256);
        }
        if (this.f6961b == null) {
            this.f6961b = new ConcurrentHashMap();
        }
    }

    String b(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        synchronized (this) {
            str2 = this.f6961b.get(str);
        }
        return str2;
    }

    void a(StrategyInfoHolder strategyInfoHolder) {
        this.f6962c = strategyInfoHolder;
    }

    void a(l.d dVar) {
        if (dVar.f7044b == null) {
            return;
        }
        synchronized (this) {
            int i2 = 0;
            TreeMap treeMap = null;
            while (true) {
                try {
                    l.b[] bVarArr = dVar.f7044b;
                    if (i2 >= bVarArr.length) {
                        break;
                    }
                    l.b bVar = bVarArr[i2];
                    if (bVar.f7038j) {
                        this.f6960a.remove(bVar.f7029a);
                    } else if (bVar.f7032d != null) {
                        if (treeMap == null) {
                            treeMap = new TreeMap();
                        }
                        treeMap.put(bVar.f7029a, bVar.f7032d);
                    } else {
                        if (!"http".equalsIgnoreCase(bVar.f7031c) && !"https".equalsIgnoreCase(bVar.f7031c)) {
                            this.f6960a.put(bVar.f7029a, NO_RESULT);
                        } else {
                            this.f6960a.put(bVar.f7029a, bVar.f7031c);
                        }
                        if (!TextUtils.isEmpty(bVar.f7033e)) {
                            this.f6961b.put(bVar.f7029a, bVar.f7033e);
                        } else {
                            this.f6961b.remove(bVar.f7029a);
                        }
                    }
                    i2++;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (treeMap != null) {
                for (Map.Entry entry : treeMap.entrySet()) {
                    String str = (String) entry.getValue();
                    if (this.f6960a.containsKey(str)) {
                        this.f6960a.put(entry.getKey(), this.f6960a.get(str));
                    } else {
                        this.f6960a.put(entry.getKey(), NO_RESULT);
                    }
                }
            }
        }
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.StrategyConfig", "", null, "SchemeMap", this.f6960a.toString());
            ALog.d("awcn.StrategyConfig", "", null, "UnitMap", this.f6961b.toString());
        }
    }

    String a(String str) {
        String str2;
        if (TextUtils.isEmpty(str) || !anet.channel.strategy.utils.c.c(str)) {
            return null;
        }
        synchronized (this) {
            try {
                str2 = this.f6960a.get(str);
                if (str2 == null) {
                    this.f6960a.put(str, NO_RESULT);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (str2 == null) {
            this.f6962c.d().a(str, false);
        } else if (NO_RESULT.equals(str2)) {
            return null;
        }
        return str2;
    }
}
