package com.aliyun.alink.linksdk.tmp.device.b;

import android.os.AsyncTask;
import com.aliyun.alink.linksdk.tmp.api.DeviceBasicData;
import com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener;
import com.aliyun.alink.linksdk.tmp.utils.TmpEnum;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class b implements IDiscoveryDeviceStateChangeListener {

    /* renamed from: a, reason: collision with root package name */
    private static final String f11378a = "[Tmp]AutoConnecterMgr";

    public void a() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        com.aliyun.alink.linksdk.tmp.device.d.a.a().a(this);
    }

    @Override // com.aliyun.alink.linksdk.tmp.listener.IDiscoveryDeviceStateChangeListener
    public void onDiscoveryDeviceStateChange(final DeviceBasicData deviceBasicData, TmpEnum.DiscoveryDeviceState discoveryDeviceState) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f11378a, "onDiscoveryDeviceStateChange state:" + discoveryDeviceState + " basicData:" + deviceBasicData);
        if (TmpEnum.DiscoveryDeviceState.DISCOVERY_STATE_ONLINE == discoveryDeviceState) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() { // from class: com.aliyun.alink.linksdk.tmp.device.b.b.1
                @Override // java.lang.Runnable
                public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    new a(deviceBasicData).a();
                }
            });
        }
    }
}
