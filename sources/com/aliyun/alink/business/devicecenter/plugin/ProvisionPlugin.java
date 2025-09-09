package com.aliyun.alink.business.devicecenter.plugin;

import android.content.Context;
import com.aliyun.alink.business.devicecenter.api.add.LinkType;
import com.aliyun.alink.business.devicecenter.config.IConfigStrategy;
import com.aliyun.alink.business.devicecenter.log.ALog;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class ProvisionPlugin implements IProvisionPlugin {

    /* renamed from: a, reason: collision with root package name */
    public ConcurrentHashMap<LinkType, Class<? extends IConfigStrategy>> f10521a;

    private static class SingletonHolder {

        /* renamed from: a, reason: collision with root package name */
        public static final ProvisionPlugin f10522a = new ProvisionPlugin();
    }

    public static ProvisionPlugin getInstance() {
        return SingletonHolder.f10522a;
    }

    public IConfigStrategy createStrategy(Context context, LinkType linkType) {
        ALog.d("ProvisionPlugin", "createStrategy() called with: context = [" + context + "], linkType = [" + linkType + "]");
        if (linkType == null) {
            throw new IllegalArgumentException("linkType empty.");
        }
        if (!this.f10521a.containsKey(linkType)) {
            ALog.w("ProvisionPlugin", "provision strategy not exist.");
            return null;
        }
        Class<? extends IConfigStrategy> cls = this.f10521a.get(linkType);
        if (cls == null) {
            ALog.w("ProvisionPlugin", "strategyHashMap classType=null, linkType=" + linkType);
            return null;
        }
        try {
            return cls.getConstructor(Context.class).newInstance(context);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            ALog.w("ProvisionPlugin", "createStrategy IllegalAccessException=" + e2);
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            ALog.w("ProvisionPlugin", "createStrategy InstantiationException=" + e3);
            return null;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            ALog.w("ProvisionPlugin", "createStrategy NoSuchMethodException=" + e4);
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            ALog.w("ProvisionPlugin", "createStrategy InvocationTargetException=" + e5);
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            ALog.w("ProvisionPlugin", "createStrategy exception=" + th);
            return null;
        }
    }

    public Class<? extends IConfigStrategy> getStrategyType(LinkType linkType) {
        ALog.d("ProvisionPlugin", "getStrategyType() called with: linkType = [" + linkType + "]");
        if (linkType == null) {
            return null;
        }
        return this.f10521a.get(linkType);
    }

    public boolean isStrategyExist(LinkType linkType) {
        if (linkType == null) {
            return false;
        }
        return this.f10521a.containsKey(linkType);
    }

    @Override // com.aliyun.alink.business.devicecenter.plugin.IProvisionPlugin
    public void registerProvisionStrategy(LinkType linkType, Class<? extends IConfigStrategy> cls) {
        if (linkType == null) {
            ALog.w("ProvisionPlugin", "registerProvisionStrategy fail, linkType=null");
            throw new IllegalArgumentException("linkType empty.");
        }
        if (cls != null) {
            if (this.f10521a.containsKey(linkType)) {
                return;
            }
            this.f10521a.put(linkType, cls);
        } else {
            ALog.w("ProvisionPlugin", "registerProvisionStrategy fail, linkType=" + linkType);
            throw new IllegalArgumentException("strategy class type empty.");
        }
    }

    @Override // com.aliyun.alink.business.devicecenter.plugin.IProvisionPlugin
    public void unregisterProvisionStrategy(LinkType linkType) {
        ALog.d("ProvisionPlugin", "unregisterProvisionStrategy() called with: linkType = [" + linkType + "]");
        if (linkType != null && this.f10521a.containsKey(linkType)) {
            this.f10521a.remove(linkType);
        }
    }

    public ProvisionPlugin() {
        this.f10521a = null;
        this.f10521a = new ConcurrentHashMap<>();
    }
}
