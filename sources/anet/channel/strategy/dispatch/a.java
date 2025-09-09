package anet.channel.strategy.dispatch;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/* loaded from: classes2.dex */
class a {
    public static final String TAG = "awcn.AmdcThreadPoolExecutor";

    /* renamed from: b, reason: collision with root package name */
    private static Random f6998b = new Random();

    /* renamed from: a, reason: collision with root package name */
    private Map<String, Object> f6999a;

    a() {
    }

    /* renamed from: anet.channel.strategy.dispatch.a$a, reason: collision with other inner class name */
    private class RunnableC0012a implements Runnable {

        /* renamed from: b, reason: collision with root package name */
        private Map<String, Object> f7001b;

        RunnableC0012a(Map<String, Object> map) {
            this.f7001b = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            Map<String, Object> map;
            try {
                Map<String, Object> map2 = this.f7001b;
                if (map2 == null) {
                    synchronized (a.class) {
                        map = a.this.f6999a;
                        a.this.f6999a = null;
                    }
                    map2 = map;
                }
                if (NetworkStatusHelper.isConnected()) {
                    if (GlobalAppRuntimeInfo.getEnv() != map2.get("Env")) {
                        ALog.w(a.TAG, "task's env changed", null, new Object[0]);
                    } else {
                        b.a(d.a(map2));
                    }
                }
            } catch (Exception e2) {
                ALog.e(a.TAG, "exec amdc task failed.", null, e2, new Object[0]);
            }
        }

        RunnableC0012a() {
        }
    }

    public void a(Map<String, Object> map) {
        try {
            map.put("Env", GlobalAppRuntimeInfo.getEnv());
            synchronized (this) {
                try {
                    Map<String, Object> map2 = this.f6999a;
                    if (map2 == null) {
                        this.f6999a = map;
                        int iNextInt = f6998b.nextInt(3000) + 2000;
                        ALog.i(TAG, "merge amdc request", null, "delay", Integer.valueOf(iNextInt));
                        anet.channel.strategy.utils.a.a(new RunnableC0012a(), iNextInt);
                    } else {
                        Set set = (Set) map2.get(DispatchConstants.HOSTS);
                        Set set2 = (Set) map.get(DispatchConstants.HOSTS);
                        if (map.get("Env") != this.f6999a.get("Env")) {
                            this.f6999a = map;
                        } else if (set.size() + set2.size() <= 40) {
                            set2.addAll(set);
                            this.f6999a = map;
                        } else {
                            anet.channel.strategy.utils.a.a(new RunnableC0012a(map));
                        }
                    }
                } finally {
                }
            }
        } catch (Exception unused) {
        }
    }
}
