package com.alibaba.sdk.android.pluto.runtime;

import android.content.Context;
import android.util.Log;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.model.ResultCode;
import com.alibaba.sdk.android.pluto.Pluto;
import com.alibaba.sdk.android.pluto.PlutoConstants;
import com.alibaba.sdk.android.pluto.meta.BeanInfo;
import com.alibaba.sdk.android.pluto.runtime.BeanMetadataManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class BeanLoaderManager {
    private static final String TAG = "BeanLoaderManager";
    private Pluto pluto;

    public BeanLoaderManager(Pluto pluto) {
        this.pluto = pluto;
    }

    public List<ResultCode> load(BeanMetadataManager.BeanRuntimeInfo[] beanRuntimeInfoArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Constructor<?> constructor;
        Object[] objArr;
        if (beanRuntimeInfoArr == null || beanRuntimeInfoArr.length == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (BeanMetadataManager.BeanRuntimeInfo beanRuntimeInfo : beanRuntimeInfoArr) {
            BeanInfo beanInfo = beanRuntimeInfo.beanInfo;
            Object objNewInstance = beanInfo.instance;
            if (objNewInstance == null) {
                try {
                    beanInfo.implType.getDeclaredField("INSTANCE").get(null);
                } catch (Exception unused) {
                    Log.i(PlutoConstants.PLUTO_LOG_TAG, "No instance field detect for bean " + beanRuntimeInfo.name);
                }
                try {
                    try {
                        constructor = beanRuntimeInfo.beanInfo.implType.getConstructor(null);
                        objArr = null;
                    } catch (NoSuchMethodException unused2) {
                        throw new IllegalStateException("No support constructor method found");
                    }
                } catch (NoSuchMethodException unused3) {
                    constructor = beanRuntimeInfo.beanInfo.implType.getConstructor(Context.class);
                    objArr = new Object[]{this.pluto.getAndroidContext()};
                }
                try {
                    objNewInstance = constructor.newInstance(objArr);
                } catch (Exception e2) {
                    throw new IllegalStateException(e2);
                }
            }
            this.pluto.getBeanInjectManagaer().inject(objNewInstance, beanRuntimeInfo.injectFields);
            Method method = beanRuntimeInfo.initMethod;
            if (method != null) {
                try {
                    method.setAccessible(true);
                    Object objInvoke = beanRuntimeInfo.initMethod.getParameterTypes().length == 0 ? beanRuntimeInfo.initMethod.invoke(objNewInstance, null) : beanRuntimeInfo.initMethod.invoke(objNewInstance, OpenAccountSDK.getAndroidContext());
                    if (objInvoke instanceof ResultCode) {
                        ResultCode resultCode = (ResultCode) objInvoke;
                        if (!resultCode.isSuccess()) {
                            arrayList.add(resultCode);
                        }
                    }
                } catch (Exception e3) {
                    Log.e(PlutoConstants.PLUTO_LOG_TAG, "fail to invoke the init method", e3);
                    arrayList.add(ResultCode.create(10010, e3.getMessage()));
                }
            }
            Pluto pluto = this.pluto;
            BeanInfo beanInfo2 = beanRuntimeInfo.beanInfo;
            pluto.registerBean(beanInfo2.types, objNewInstance, beanInfo2.properties);
        }
        return arrayList;
    }
}
