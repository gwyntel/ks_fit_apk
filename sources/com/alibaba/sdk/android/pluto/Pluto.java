package com.alibaba.sdk.android.pluto;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.pluto.meta.ModuleInfo;
import com.alibaba.sdk.android.pluto.runtime.BeanInjecterManager;
import com.alibaba.sdk.android.pluto.runtime.BeanLoaderManager;
import com.alibaba.sdk.android.pluto.runtime.BeanMetadataManager;
import com.alibaba.sdk.android.pluto.runtime.BeanRegistration;
import com.alibaba.sdk.android.pluto.runtime.impl.DefaultBeanRegistry;
import com.alibaba.sdk.android.pluto.runtime.impl.ProxyEnabledBeanRegistryDelegator;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class Pluto {
    public static final Pluto DEFAULT_INSTANCE = new Pluto();
    private Context context;
    private volatile boolean initialized;
    private boolean proxyDisabled;
    private Set<ModuleInfo> moduleInfos = new LinkedHashSet();
    private BeanInjecterManager beanInjectManagaer = new BeanInjecterManager(this);
    private BeanLoaderManager beanLoaderManager = new BeanLoaderManager(this);
    private BeanMetadataManager beanMetadataManager = new BeanMetadataManager();
    private ProxyEnabledBeanRegistryDelegator beanRegistry = new ProxyEnabledBeanRegistryDelegator(new DefaultBeanRegistry());

    public Context getAndroidContext() {
        return this.context;
    }

    public <T> T getBean(Class<T> cls, Map<String, String> map) {
        return (T) getBean(cls, map, false);
    }

    public BeanInjecterManager getBeanInjectManagaer() {
        return this.beanInjectManagaer;
    }

    public BeanLoaderManager getBeanLoaderManager() {
        return this.beanLoaderManager;
    }

    public BeanMetadataManager getBeanMetadataManager() {
        return this.beanMetadataManager;
    }

    public <T> T[] getBeans(Class<T> cls, Map<String, String> map) {
        return (T[]) this.beanRegistry.getBeans(cls, map);
    }

    public Set<ModuleInfo> getModuleInfos() {
        return Collections.unmodifiableSet(this.moduleInfos);
    }

    public List<ResultCode> init(Context context) {
        if (this.initialized) {
            return Collections.emptyList();
        }
        this.context = context;
        try {
            List<ResultCode> listLoad = this.beanLoaderManager.load(this.beanMetadataManager.buildBeanRuntimeInfos(this.moduleInfos));
            if (listLoad.size() == 0) {
                this.initialized = true;
            }
            return listLoad;
        } catch (Exception e2) {
            Log.e(PlutoConstants.PLUTO_LOG_TAG, "Fail to init the pluto container", e2);
            return Collections.singletonList(ResultCode.create(10010, e2.getMessage()));
        }
    }

    public void inject(Object obj) {
        try {
            this.beanInjectManagaer.inject(obj);
        } catch (Exception e2) {
            Log.e(PlutoConstants.PLUTO_LOG_TAG, "fail to execute the injection for bean " + obj.getClass().getName(), e2);
        }
    }

    public BeanRegistration registerBean(Class<?> cls, Object obj, Map<String, String> map) {
        return this.beanRegistry.registerBean(new Class[]{cls}, obj, map);
    }

    public void registerModule(ModuleInfo moduleInfo) {
        this.moduleInfos.add(moduleInfo);
    }

    public void setBeanInjectManagaer(BeanInjecterManager beanInjecterManager) {
        this.beanInjectManagaer = beanInjecterManager;
    }

    public void setBeanLoaderManager(BeanLoaderManager beanLoaderManager) {
        this.beanLoaderManager = beanLoaderManager;
    }

    public void setBeanMetadataManager(BeanMetadataManager beanMetadataManager) {
        this.beanMetadataManager = beanMetadataManager;
    }

    public void setBeanProxyDisabled(boolean z2) {
        this.proxyDisabled = z2;
    }

    public <T> T getBean(Class<T> cls, Map<String, String> map, boolean z2) {
        return (T) this.beanRegistry.getBean(cls, map, this.proxyDisabled || z2);
    }

    public <T> T[] getBeans(Class<T> cls) {
        return (T[]) getBeans(cls, null);
    }

    public BeanRegistration registerBean(Class<?>[] clsArr, Object obj, Map<String, String> map) {
        return this.beanRegistry.registerBean(clsArr, obj, map);
    }

    public <T> T getBean(Class<T> cls) {
        return (T) getBean(cls, null);
    }
}
