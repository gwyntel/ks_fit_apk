package com.aliyun.alink.linksdk.alcs.lpbs.bridge.a;

import com.aliyun.alink.linksdk.alcs.data.ica.ICADiscoveryDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.api.PluginMgrConfig;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalAuthRegister;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalDiscovery;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalGroupConnect;
import com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalProbe;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalDeviceInfo;
import com.aliyun.alink.linksdk.alcs.lpbs.data.PalInitData;
import com.aliyun.alink.linksdk.alcs.lpbs.data.group.PalGroupInfo;
import com.aliyun.alink.linksdk.alcs.pal.ica.ICAAlcsNative;
import com.aliyun.alink.linksdk.tools.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class c implements IPalBridge {

    /* renamed from: a, reason: collision with root package name */
    private static final String f10796a = "[AlcsLPBS]ICAAlcsBridge";

    /* renamed from: c, reason: collision with root package name */
    private PalInitData f10798c;

    /* renamed from: b, reason: collision with root package name */
    private Map<String, ICADiscoveryDeviceInfo> f10797b = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    private e f10799d = new e(this);

    /* renamed from: e, reason: collision with root package name */
    private g f10800e = new g();

    public c(PluginMgrConfig pluginMgrConfig) {
    }

    public void a(String str, ICADiscoveryDeviceInfo iCADiscoveryDeviceInfo) {
        this.f10797b.put(str, iCADiscoveryDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public void deInitBridge() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ALog.d(f10796a, " deInitBridge");
        ICAAlcsNative.deInitPal();
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public IPalGroupConnect getGroupConnect(PalGroupInfo palGroupInfo) {
        return new com.aliyun.alink.linksdk.alcs.lpbs.bridge.a.a.a(this, palGroupInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public IPalAuthRegister getPalAuthRegister() {
        return this.f10800e;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public IPalConnect getPalConnect(PalDeviceInfo palDeviceInfo) {
        return new d(this, palDeviceInfo);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public IPalDiscovery getPalDiscovery() {
        return this.f10799d;
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public IPalProbe getPalProbe() {
        return new f(this);
    }

    @Override // com.aliyun.alink.linksdk.alcs.lpbs.bridge.IPalBridge
    public void initBridge(PalInitData palInitData) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.f10798c = palInitData;
        ALog.d(f10796a, " initBridge initData:" + palInitData);
        ICAAlcsNative.initPal(m.a(palInitData));
    }

    public ICADiscoveryDeviceInfo a(String str) {
        return this.f10797b.get(str);
    }
}
