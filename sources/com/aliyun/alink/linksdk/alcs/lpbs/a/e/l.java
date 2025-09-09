package com.aliyun.alink.linksdk.alcs.lpbs.a.e;

import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgr;
import com.aliyun.alink.linksdk.alcs.lpbs.component.auth.IAuthProvider;
import com.aliyun.alink.linksdk.alcs.lpbs.component.cloud.ICloudChannelFactory;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.listener.PalDeviceStateListener;
import com.aliyun.alink.linksdk.alcs.lpbs.plugin.IPlugin;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class l extends e {

    /* renamed from: c, reason: collision with root package name */
    private static final String f10758c = "[AlcsLPBS]RegisterLayer";

    /* renamed from: a, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.a.a f10759a;

    /* renamed from: b, reason: collision with root package name */
    private com.aliyun.alink.linksdk.alcs.lpbs.a.d.a f10760b;

    public l(com.aliyun.alink.linksdk.alcs.lpbs.a.d.a aVar, com.aliyun.alink.linksdk.alcs.lpbs.a.a.a aVar2, e eVar) {
        super(eVar);
        this.f10759a = aVar2;
        this.f10760b = aVar;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public boolean regAuthProvider(String str, IAuthProvider iAuthProvider) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        IPlugin pluginByPluginId = PluginMgr.getInstance().getPluginByPluginId(str);
        ALog.d(f10758c, "regAuthProvider pluginId:" + str + " provider:" + iAuthProvider + " plugin:" + pluginByPluginId);
        if (pluginByPluginId == null || pluginByPluginId.getPalBridge().getPalAuthRegister() == null) {
            return true;
        }
        pluginByPluginId.getPalBridge().getPalAuthRegister().setAuthProvider(iAuthProvider);
        return true;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.api.IAlcsPal
    public void regCloudChannelFactory(ICloudChannelFactory iCloudChannelFactory) {
        ALog.d(f10758c, "regCloudChannelFactory mChannelMgr:" + this.f10759a + " factory:" + iCloudChannelFactory);
        this.f10759a.a(iCloudChannelFactory);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean regDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10758c, "regDeviceStateListener mDevStateListenerMgr:" + this.f10760b + " listener:" + palDeviceStateListener);
        return this.f10760b.a(palDeviceInfo, palDeviceStateListener);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.a.e.e, com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect
    public boolean unregDeviceStateListener(PalDeviceInfo palDeviceInfo, PalDeviceStateListener palDeviceStateListener) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10758c, "unregDeviceStateListener mDevStateListenerMgr:" + this.f10760b + " listener:" + palDeviceStateListener);
        return this.f10760b.b(palDeviceInfo, palDeviceStateListener);
    }
}
