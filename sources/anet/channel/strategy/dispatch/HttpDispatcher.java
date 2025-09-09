package anet.channel.strategy.dispatch;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.ALog;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes2.dex */
public class HttpDispatcher {

    /* renamed from: a, reason: collision with root package name */
    private CopyOnWriteArraySet<IDispatchEventListener> f6991a;

    /* renamed from: b, reason: collision with root package name */
    private anet.channel.strategy.dispatch.a f6992b;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f6993c;

    /* renamed from: d, reason: collision with root package name */
    private Set<String> f6994d;

    /* renamed from: e, reason: collision with root package name */
    private Set<String> f6995e;

    /* renamed from: f, reason: collision with root package name */
    private AtomicBoolean f6996f;

    public interface IDispatchEventListener {
        void onEvent(DispatchEvent dispatchEvent);
    }

    private static class a {

        /* renamed from: a, reason: collision with root package name */
        static HttpDispatcher f6997a = new HttpDispatcher();

        private a() {
        }
    }

    public static HttpDispatcher getInstance() {
        return a.f6997a;
    }

    public static void setInitHosts(List<String> list) {
        if (list != null) {
            DispatchConstants.initHostArray = (String[]) list.toArray(new String[0]);
        }
    }

    void a(DispatchEvent dispatchEvent) {
        Iterator<IDispatchEventListener> it = this.f6991a.iterator();
        while (it.hasNext()) {
            try {
                it.next().onEvent(dispatchEvent);
            } catch (Exception unused) {
            }
        }
    }

    public synchronized void addHosts(List<String> list) {
        if (list != null) {
            this.f6995e.addAll(list);
            this.f6994d.clear();
        }
    }

    public void addListener(IDispatchEventListener iDispatchEventListener) {
        this.f6991a.add(iDispatchEventListener);
    }

    public synchronized Set<String> getInitHosts() {
        a();
        return new HashSet(this.f6995e);
    }

    public boolean isInitHostsChanged(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean zContains = this.f6994d.contains(str);
        if (!zContains) {
            this.f6994d.add(str);
        }
        return !zContains;
    }

    public void removeListener(IDispatchEventListener iDispatchEventListener) {
        this.f6991a.remove(iDispatchEventListener);
    }

    public void sendAmdcRequest(Set<String> set, int i2) {
        if (!this.f6993c || set == null || set.isEmpty()) {
            ALog.e("awcn.HttpDispatcher", "invalid parameter", null, new Object[0]);
            return;
        }
        if (ALog.isPrintLog(2)) {
            ALog.i("awcn.HttpDispatcher", "sendAmdcRequest", null, DispatchConstants.HOSTS, set.toString());
        }
        HashMap map = new HashMap();
        map.put(DispatchConstants.HOSTS, set);
        map.put(DispatchConstants.CONFIG_VERSION, String.valueOf(i2));
        this.f6992b.a(map);
    }

    public void setEnable(boolean z2) {
        this.f6993c = z2;
    }

    public void switchENV() {
        this.f6994d.clear();
        this.f6995e.clear();
        this.f6996f.set(false);
    }

    private HttpDispatcher() {
        this.f6991a = new CopyOnWriteArraySet<>();
        this.f6992b = new anet.channel.strategy.dispatch.a();
        this.f6993c = true;
        this.f6994d = Collections.newSetFromMap(new ConcurrentHashMap());
        this.f6995e = new TreeSet();
        this.f6996f = new AtomicBoolean();
        a();
    }

    private void a() {
        if (this.f6996f.get() || GlobalAppRuntimeInfo.getContext() == null || !this.f6996f.compareAndSet(false, true)) {
            return;
        }
        this.f6995e.add(DispatchConstants.getAmdcServerDomain());
        if (GlobalAppRuntimeInfo.isTargetProcess()) {
            this.f6995e.addAll(Arrays.asList(DispatchConstants.initHostArray));
        }
    }
}
