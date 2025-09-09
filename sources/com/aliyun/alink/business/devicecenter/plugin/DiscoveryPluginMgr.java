package com.aliyun.alink.business.devicecenter.plugin;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.aliyun.alink.business.devicecenter.api.discovery.DiscoveryType;
import com.aliyun.alink.business.devicecenter.discover.IDiscoverChain;
import com.aliyun.alink.business.devicecenter.discover.annotation.DeviceDiscovery;
import com.aliyun.alink.business.devicecenter.log.ALog;
import com.aliyun.alink.business.devicecenter.utils.ClassUtil;
import com.aliyun.alink.business.devicecenter.utils.ContextHolder;
import com.aliyun.alink.business.devicecenter.utils.FileUtils;
import com.aliyun.alink.business.devicecenter.utils.ReflectUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class DiscoveryPluginMgr {

    /* renamed from: a, reason: collision with root package name */
    public static final DiscoveryPluginMgr f10519a = new DiscoveryPluginMgr();

    /* renamed from: b, reason: collision with root package name */
    public final ConcurrentHashMap<DiscoveryType, Class<? extends IDiscoverChain>> f10520b = new ConcurrentHashMap<>();

    public static DiscoveryPluginMgr getInstance() {
        return f10519a;
    }

    public IDiscoverChain createDiscoverChain(DiscoveryType discoveryType, Context context, Map<String, Object> map) {
        ALog.d("DiscoveryPluginMgr", "createDiscoverChain() called with: context = [" + context + "], linkType = [" + discoveryType + "]");
        if (discoveryType == null) {
            throw new IllegalArgumentException("discoveryType empty.");
        }
        if (!this.f10520b.containsKey(discoveryType)) {
            ALog.w("DiscoveryPluginMgr", "discovery chain not exist. discoveryType: " + discoveryType);
            return null;
        }
        Class<? extends IDiscoverChain> cls = this.f10520b.get(discoveryType);
        if (cls != null) {
            return map == null ? (IDiscoverChain) ReflectUtils.newInstance(cls, context) : (IDiscoverChain) ReflectUtils.newInstance(cls, context, map);
        }
        ALog.w("DiscoveryPluginMgr", "mDiscoverChains classType=null, discoveryType=" + discoveryType);
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void init() throws IOException {
        if (this.f10520b.isEmpty()) {
            for (Class<?> cls : ClassUtil.getClasses(JSON.parseObject(FileUtils.ReadFile(ContextHolder.getInstance().getAppContext().getAssets().open("config.conf"))).getJSONArray("deviceDiscovery").toJavaList(String.class))) {
                if (cls.isAnnotationPresent(DeviceDiscovery.class) && IDiscoverChain.class.isAssignableFrom(cls)) {
                    for (DiscoveryType discoveryType : ((DeviceDiscovery) cls.getAnnotation(DeviceDiscovery.class)).discoveryType()) {
                        if (this.f10520b.containsKey(discoveryType)) {
                            ALog.e("DiscoveryPluginMgr", "DiscoverType: " + discoveryType + " has exist, " + cls.getName() + " will be ignore");
                        } else {
                            this.f10520b.put(discoveryType, cls);
                            ALog.i("DiscoveryPluginMgr", "register discoverChain: " + discoveryType + " with class: " + cls.getName());
                        }
                    }
                }
            }
        }
    }
}
