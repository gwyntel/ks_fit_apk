package anet.channel.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class StrategyTemplate {
    Map<String, ConnProtocol> templateMap = new ConcurrentHashMap();

    static class a {

        /* renamed from: a, reason: collision with root package name */
        static StrategyTemplate f6980a = new StrategyTemplate();

        a() {
        }
    }

    public static StrategyTemplate getInstance() {
        return a.f6980a;
    }

    public ConnProtocol getConnProtocol(String str) {
        return this.templateMap.get(str);
    }

    public void registerConnProtocol(String str, ConnProtocol connProtocol) {
        if (connProtocol != null) {
            this.templateMap.put(str, connProtocol);
            try {
                IStrategyInstance strategyCenter = StrategyCenter.getInstance();
                if (strategyCenter instanceof g) {
                    ((g) strategyCenter).f7010b.f6965c.a(str, connProtocol);
                }
            } catch (Exception unused) {
            }
        }
    }
}
