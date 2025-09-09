package com.aliyun.alink.linksdk.tmp.device.d;

import com.alibaba.fastjson.JSON;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11400a = "[Tmp]DiscoveryDeviceStateMgr";

    /* renamed from: b, reason: collision with root package name */
    private Map<Integer, IDiscoveryDeviceStateChangeListener> f11401b;

    /* renamed from: com.aliyun.alink.linksdk.tmp.device.d.a$a, reason: collision with other inner class name */
    private static class C0082a {

        /* renamed from: a, reason: collision with root package name */
        protected static a f11402a = new a();

        private C0082a() {
        }
    }

    public static a a() {
        return C0082a.f11402a;
    }

    public void b(IDiscoveryDeviceStateChangeListener iDiscoveryDeviceStateChangeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11400a, "removeDiscoveryDeviceStateChangeListener listener:" + iDiscoveryDeviceStateChangeListener);
        if (iDiscoveryDeviceStateChangeListener == null) {
            return;
        }
        this.f11401b.remove(Integer.valueOf(iDiscoveryDeviceStateChangeListener.hashCode()));
    }

    private a() {
        this.f11401b = new ConcurrentHashMap();
    }

    public void a(IDiscoveryDeviceStateChangeListener iDiscoveryDeviceStateChangeListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11400a, "addDiscoveryDeviceStateChangeListener listener:" + iDiscoveryDeviceStateChangeListener);
        if (iDiscoveryDeviceStateChangeListener == null) {
            return;
        }
        this.f11401b.put(Integer.valueOf(iDiscoveryDeviceStateChangeListener.hashCode()), iDiscoveryDeviceStateChangeListener);
    }

    public void a(DeviceBasicData deviceBasicData, TmpEnum.DiscoveryDeviceState discoveryDeviceState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuilder sb = new StringBuilder();
        sb.append("onDiscoveryDeviceStateChange basicData:");
        sb.append(deviceBasicData == null ? "" : deviceBasicData.toString());
        sb.append(" state:");
        sb.append(discoveryDeviceState);
        sb.append(" mDiscoveryDevStateChangeListenerList:");
        sb.append(this.f11401b);
        ALog.d(f11400a, sb.toString());
        ALog.d(f11400a, JSON.toJSONString(deviceBasicData));
        Map<Integer, IDiscoveryDeviceStateChangeListener> map = this.f11401b;
        if (map != null && !map.isEmpty()) {
            Iterator<Map.Entry<Integer, IDiscoveryDeviceStateChangeListener>> it = this.f11401b.entrySet().iterator();
            while (it.hasNext()) {
                IDiscoveryDeviceStateChangeListener value = it.next().getValue();
                if (value != null) {
                    value.onDiscoveryDeviceStateChange(deviceBasicData, discoveryDeviceState);
                }
            }
            return;
        }
        ALog.w(f11400a, "onDiscoveryDeviceStateChange mDiscoveryDevStateChangeListenerList empty");
    }
}
