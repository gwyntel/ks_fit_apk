package com.alibaba.sdk.android.pluto.runtime.impl;

import com.alibaba.sdk.android.openaccount.message.MessageUtils;
import com.alibaba.sdk.android.openaccount.trace.AliSDKLogger;
import com.alibaba.sdk.android.pluto.PlutoConstants;
import com.alibaba.sdk.android.pluto.runtime.BeanRegistration;
import com.alibaba.sdk.android.pluto.runtime.BeanRegistry;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/* loaded from: classes2.dex */
public class ProxyEnabledBeanRegistryDelegator implements BeanRegistry {
    private BeanRegistry delegator;

    public ProxyEnabledBeanRegistryDelegator(BeanRegistry beanRegistry) {
        this.delegator = beanRegistry;
    }

    public <T> T getBean(final Class<T> cls, final Map<String, String> map, boolean z2) {
        T t2 = (T) this.delegator.getBean(cls, map);
        if (t2 != null) {
            return t2;
        }
        if (!z2 && cls.isInterface()) {
            return cls.cast(Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{cls}, new InvocationHandler() { // from class: com.alibaba.sdk.android.pluto.runtime.impl.ProxyEnabledBeanRegistryDelegator.1
                @Override // java.lang.reflect.InvocationHandler
                public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                    Object bean = ProxyEnabledBeanRegistryDelegator.this.delegator.getBean(cls, map);
                    if (bean != null) {
                        return method.invoke(bean, objArr);
                    }
                    String name = cls.getName();
                    Map map2 = map;
                    AliSDKLogger.log(PlutoConstants.PLUTO_LOG_TAG, MessageUtils.createMessage(17, name, map2 != null ? map2.toString() : ""));
                    return null;
                }
            }));
        }
        return null;
    }

    @Override // com.alibaba.sdk.android.pluto.runtime.BeanRegistry
    public <T> T[] getBeans(Class<T> cls, Map<String, String> map) {
        return (T[]) this.delegator.getBeans(cls, map);
    }

    @Override // com.alibaba.sdk.android.pluto.runtime.BeanRegistry
    public void recycle() {
        this.delegator.recycle();
    }

    @Override // com.alibaba.sdk.android.pluto.runtime.BeanRegistry
    public BeanRegistration registerBean(Class<?>[] clsArr, Object obj, Map<String, String> map) {
        return this.delegator.registerBean(clsArr, obj, map);
    }

    @Override // com.alibaba.sdk.android.pluto.runtime.BeanRegistry
    public Object unregisterBean(BeanRegistration beanRegistration) {
        return this.delegator.unregisterBean(beanRegistration);
    }

    @Override // com.alibaba.sdk.android.pluto.runtime.BeanRegistry
    public <T> T getBean(Class<T> cls, Map<String, String> map) {
        return (T) getBean(cls, map, false);
    }
}
