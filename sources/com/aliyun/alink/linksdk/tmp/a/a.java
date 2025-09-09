package com.aliyun.alink.linksdk.tmp.a;

import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11094a = "[Tmp]LocalDeviceListChangeNotifier";

    /* renamed from: c, reason: collision with root package name */
    private static volatile a f11095c;

    /* renamed from: b, reason: collision with root package name */
    private Map<Integer, InterfaceC0074a> f11096b = new ConcurrentHashMap();

    /* renamed from: com.aliyun.alink.linksdk.tmp.a.a$a, reason: collision with other inner class name */
    public interface InterfaceC0074a {

        /* renamed from: a, reason: collision with root package name */
        public static final int f11097a = 1;

        /* renamed from: b, reason: collision with root package name */
        public static final int f11098b = 2;

        /* renamed from: c, reason: collision with root package name */
        public static final int f11099c = 3;

        void onDeviceListChange(int i2, DeviceBasicData deviceBasicData);
    }

    private a() {
    }

    public static a a() {
        if (f11095c == null) {
            synchronized (a.class) {
                try {
                    if (f11095c == null) {
                        f11095c = new a();
                    }
                } finally {
                }
            }
        }
        return f11095c;
    }

    protected void b(InterfaceC0074a interfaceC0074a) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (interfaceC0074a == null) {
            ALog.e(f11094a, "addDeviceListChangeListener listChangeListener empty");
        } else {
            this.f11096b.remove(Integer.valueOf(interfaceC0074a.hashCode()));
        }
    }

    protected void a(InterfaceC0074a interfaceC0074a) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (interfaceC0074a == null) {
            ALog.e(f11094a, "addDeviceListChangeListener listChangeListener empty");
        } else {
            this.f11096b.put(Integer.valueOf(interfaceC0074a.hashCode()), interfaceC0074a);
        }
    }

    protected void a(int i2, DeviceBasicData deviceBasicData) {
        Map<Integer, InterfaceC0074a> map = this.f11096b;
        if (map == null || map.isEmpty()) {
            return;
        }
        for (Map.Entry entry : new HashMap(this.f11096b).entrySet()) {
            if (entry.getValue() != null) {
                ((InterfaceC0074a) entry.getValue()).onDeviceListChange(i2, deviceBasicData);
            }
        }
    }
}
