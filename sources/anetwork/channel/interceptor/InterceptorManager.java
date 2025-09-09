package anetwork.channel.interceptor;

import anet.channel.util.ALog;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class InterceptorManager {

    /* renamed from: a, reason: collision with root package name */
    private static final CopyOnWriteArrayList<Interceptor> f7240a = new CopyOnWriteArrayList<>();

    private InterceptorManager() {
    }

    public static void addInterceptor(Interceptor interceptor) {
        CopyOnWriteArrayList<Interceptor> copyOnWriteArrayList = f7240a;
        if (copyOnWriteArrayList.contains(interceptor)) {
            return;
        }
        copyOnWriteArrayList.add(interceptor);
        ALog.i("anet.InterceptorManager", "[addInterceptor]", null, "interceptors", copyOnWriteArrayList.toString());
    }

    public static boolean contains(Interceptor interceptor) {
        return f7240a.contains(interceptor);
    }

    public static Interceptor getInterceptor(int i2) {
        return f7240a.get(i2);
    }

    public static int getSize() {
        return f7240a.size();
    }

    public static void removeInterceptor(Interceptor interceptor) {
        CopyOnWriteArrayList<Interceptor> copyOnWriteArrayList = f7240a;
        copyOnWriteArrayList.remove(interceptor);
        ALog.i("anet.InterceptorManager", "[remoteInterceptor]", null, "interceptors", copyOnWriteArrayList.toString());
    }
}
