package com.alibaba.sdk.android.pluto.runtime;

import android.util.Log;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.PlutoConstants;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alibaba.sdk.android.pluto.annotation.BeanProperty;
import com.alibaba.sdk.android.pluto.annotation.Qualifier;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class BeanInjecterManager {
    private Pluto pluto;

    public BeanInjecterManager(Pluto pluto) {
        this.pluto = pluto;
    }

    public void inject(Object obj) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        if (obj == null) {
            return;
        }
        List<Field> injectionFields = this.pluto.getBeanMetadataManager().getInjectionFields(obj.getClass());
        if (injectionFields == null || injectionFields.size() == 0) {
            return;
        }
        inject(obj, injectionFields);
    }

    public void inject(Object obj, List<Field> list) throws IllegalAccessException, SecurityException, IllegalArgumentException {
        HashMap map;
        if (obj == null || list == null || list.size() == 0) {
            return;
        }
        for (Field field : list) {
            Autowired autowired = (Autowired) field.getAnnotation(Autowired.class);
            if (autowired != null) {
                Qualifier qualifier = (Qualifier) field.getAnnotation(Qualifier.class);
                if (qualifier == null || qualifier.filters() == null || qualifier.filters().length <= 0) {
                    map = null;
                } else {
                    map = new HashMap();
                    for (BeanProperty beanProperty : qualifier.filters()) {
                        if (beanProperty.key() != null && beanProperty.value() != null) {
                            map.put(beanProperty.key(), beanProperty.value());
                        }
                    }
                }
                Object bean = this.pluto.getBean(field.getType(), map, autowired.proxyDisabled());
                if (bean == null) {
                    Log.w(PlutoConstants.PLUTO_LOG_TAG, "No avaiable service for field " + field.getDeclaringClass().getName() + "." + field.getName());
                } else {
                    try {
                        field.setAccessible(true);
                        field.set(obj, bean);
                    } catch (Exception e2) {
                        Log.e(PlutoConstants.PLUTO_LOG_TAG, "Fail to inject the field " + field.getDeclaringClass().getName() + "." + field.getName(), e2);
                    }
                }
            }
        }
    }
}
