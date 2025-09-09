package a.a.a.a.b.l;

import android.os.Handler;
import android.os.Looper;
import com.alibaba.ailabs.iot.mesh.ut.UtTraceInfo;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    public static volatile f f1569a;

    /* renamed from: b, reason: collision with root package name */
    public final ConcurrentHashMap<String, ConcurrentLinkedQueue<UtTraceInfo>> f1570b = new ConcurrentHashMap<>();

    /* renamed from: c, reason: collision with root package name */
    public final Handler f1571c = new Handler(Looper.getMainLooper());

    public static f a() {
        if (f1569a == null) {
            synchronized (f.class) {
                try {
                    if (f1569a == null) {
                        f1569a = new f();
                    }
                } finally {
                }
            }
        }
        return f1569a;
    }

    public UtTraceInfo b(int i2) {
        ConcurrentLinkedQueue<UtTraceInfo> concurrentLinkedQueue = this.f1570b.get(String.valueOf(i2));
        UtTraceInfo utTraceInfoPoll = concurrentLinkedQueue != null ? concurrentLinkedQueue.poll() : null;
        if (utTraceInfoPoll != null) {
            return utTraceInfoPoll;
        }
        UtTraceInfo utTraceInfo = new UtTraceInfo();
        utTraceInfo.setUnicastAddress(i2);
        return utTraceInfo;
    }

    public UtTraceInfo a(UtTraceInfo utTraceInfo) {
        if (utTraceInfo == null) {
            return null;
        }
        String str = utTraceInfo.getUnicastAddress() + "";
        ConcurrentLinkedQueue<UtTraceInfo> concurrentLinkedQueue = this.f1570b.get(str);
        if (concurrentLinkedQueue == null) {
            concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
            this.f1570b.put(str, concurrentLinkedQueue);
        }
        concurrentLinkedQueue.add(utTraceInfo);
        this.f1570b.put(str, concurrentLinkedQueue);
        this.f1571c.postDelayed(new e(this, str, utTraceInfo), 3000L);
        return utTraceInfo;
    }

    public boolean a(String str, UtTraceInfo utTraceInfo) {
        a.a.a.a.b.m.a.c("UtTraceManager", "removeSpecialTraceInfo: " + str + ", " + utTraceInfo);
        ConcurrentLinkedQueue<UtTraceInfo> concurrentLinkedQueue = this.f1570b.get(str);
        if (concurrentLinkedQueue == null) {
            return false;
        }
        a.a.a.a.b.m.a.c("UtTraceManager", "removeSpecialTraceId " + utTraceInfo);
        return concurrentLinkedQueue.remove(utTraceInfo);
    }

    public UtTraceInfo a(int i2) {
        ConcurrentLinkedQueue<UtTraceInfo> concurrentLinkedQueue = this.f1570b.get(i2 + "");
        UtTraceInfo utTraceInfoPeek = concurrentLinkedQueue != null ? concurrentLinkedQueue.peek() : null;
        if (utTraceInfoPeek != null) {
            return utTraceInfoPeek;
        }
        UtTraceInfo utTraceInfo = new UtTraceInfo();
        utTraceInfo.setUnicastAddress(i2);
        return utTraceInfo;
    }

    public UtTraceInfo a(String str) {
        Iterator<Map.Entry<String, ConcurrentLinkedQueue<UtTraceInfo>>> it = this.f1570b.entrySet().iterator();
        while (it.hasNext()) {
            ConcurrentLinkedQueue<UtTraceInfo> value = it.next().getValue();
            if (value != null) {
                Iterator<UtTraceInfo> it2 = value.iterator();
                while (it2.hasNext()) {
                    UtTraceInfo next = it2.next();
                    if (next != null && next.getDeviceId().equals(str)) {
                        return next;
                    }
                }
            }
        }
        UtTraceInfo utTraceInfo = new UtTraceInfo();
        utTraceInfo.setDeviceId(str);
        return utTraceInfo;
    }
}
