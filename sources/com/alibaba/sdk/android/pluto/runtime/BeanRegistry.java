package com.alibaba.sdk.android.pluto.runtime;

import java.util.Map;

/* loaded from: classes2.dex */
public interface BeanRegistry {
    <T> T getBean(Class<T> cls, Map<String, String> map);

    <T> T[] getBeans(Class<T> cls, Map<String, String> map);

    void recycle();

    BeanRegistration registerBean(Class<?>[] clsArr, Object obj, Map<String, String> map);

    Object unregisterBean(BeanRegistration beanRegistration);
}
