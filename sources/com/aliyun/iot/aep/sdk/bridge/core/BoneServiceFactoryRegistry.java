package com.aliyun.iot.aep.sdk.bridge.core;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes3.dex */
public class BoneServiceFactoryRegistry {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, BoneServiceFactory> f11671a = new ConcurrentHashMap(10);

    public static List<String> dump() {
        ArrayList arrayList = new ArrayList(f11671a.size());
        Iterator<Map.Entry<String, BoneServiceFactory>> it = f11671a.entrySet().iterator();
        while (it.hasNext()) {
            BoneServiceFactory value = it.next().getValue();
            String sDKName = value.getSDKName();
            String sDKVersion = value.getSDKVersion();
            for (String str : value.getServiceNameList()) {
                if (!TextUtils.isEmpty(str)) {
                    arrayList.add(sDKName + "@" + sDKVersion + "/" + str);
                }
            }
        }
        return arrayList;
    }

    public static List<BoneServiceFactory> dumpAll() {
        return new ArrayList(new HashSet(f11671a.values()));
    }

    public static BoneServiceFactory findBoneServiceFactory(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sdkName can not be empty");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("serviceName can not be empty");
        }
        return f11671a.get(str + "/" + str2);
    }

    public static void register(BoneServiceFactory boneServiceFactory) {
        if (boneServiceFactory == null) {
            throw new IllegalArgumentException("factory can not be null");
        }
        String sDKName = boneServiceFactory.getSDKName();
        if (TextUtils.isEmpty(sDKName)) {
            throw new IllegalArgumentException("factory.getSDKName() can not return empty");
        }
        if (TextUtils.isEmpty(boneServiceFactory.getSDKVersion())) {
            throw new IllegalArgumentException("factory.getSDKVersion() can not return empty");
        }
        List<String> serviceNameList = boneServiceFactory.getServiceNameList();
        if (serviceNameList == null) {
            throw new IllegalArgumentException("factory.getServiceNameList() can not return null");
        }
        if (serviceNameList.isEmpty()) {
            throw new IllegalArgumentException("factory.getServiceNameList() can not be empty");
        }
        for (String str : serviceNameList) {
            if (!TextUtils.isEmpty(str)) {
                String str2 = sDKName + "/" + str;
                synchronized (f11671a) {
                    try {
                        if (f11671a.containsKey(str2)) {
                            ALog.e("BoneServiceRegistry", str2 + " has been registered, if need to replace it, please call BoneServiceFactoryRegistry.unregister(serviceName) first");
                        } else {
                            f11671a.put(str2, boneServiceFactory);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public static BoneServiceFactory unregister(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sdkName can not be empty");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("serviceName can not be empty");
        }
        String str3 = str + "/" + str2;
        synchronized (f11671a) {
            try {
                if (!f11671a.containsKey(str3)) {
                    return null;
                }
                return f11671a.remove(str3);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
