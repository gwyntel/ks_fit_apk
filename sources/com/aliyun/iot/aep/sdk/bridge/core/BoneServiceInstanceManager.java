package com.aliyun.iot.aep.sdk.bridge.core;

import android.content.Context;
import android.text.TextUtils;
import com.aliyun.alink.linksdk.tools.ALog;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneService;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class BoneServiceInstanceManager {

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, BoneServiceFactory> f11672b = new ConcurrentHashMap(16);

    /* renamed from: c, reason: collision with root package name */
    private static Map<String, BoneService> f11673c = new ConcurrentHashMap(16);

    /* renamed from: d, reason: collision with root package name */
    private static Map<String, Integer> f11674d = new ConcurrentHashMap(16);

    /* renamed from: a, reason: collision with root package name */
    private Context f11675a;

    /* renamed from: e, reason: collision with root package name */
    private Map<String, BoneService> f11676e = new ConcurrentHashMap(32);

    static class a implements BoneService {

        /* renamed from: a, reason: collision with root package name */
        String f11677a;

        /* renamed from: b, reason: collision with root package name */
        BoneService f11678b;

        @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
        public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws Exception {
            boolean zOnCall = this.f11678b.onCall(jSContext, boneCall, boneCallback);
            try {
                this.f11678b.onDestroy();
                return zOnCall;
            } catch (Exception e2) {
                ALog.e("BoneServiceInstanceManager", "exception happen when call BoneService.onDestroy(), service id is " + this.f11677a, e2);
                throw e2;
            }
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
        public void onDestroy() {
        }

        @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
        public void onInitialize(Context context) {
        }

        private a(String str, BoneService boneService) {
            this.f11677a = str;
            this.f11678b = boneService;
        }
    }

    public BoneServiceInstanceManager(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null");
        }
        this.f11675a = context;
    }

    private String a(String str) {
        int iIndexOf = str.indexOf("/") + 1;
        int iIndexOf2 = str.indexOf("@");
        return iIndexOf >= iIndexOf2 ? "" : str.substring(iIndexOf, iIndexOf2);
    }

    public String getServiceId(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("sdkName can not be empty");
        }
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("serviceName can not be empty");
        }
        String str3 = str + "/" + str2;
        Iterator<Map.Entry<String, BoneServiceFactory>> it = f11672b.entrySet().iterator();
        while (it.hasNext()) {
            String key = it.next().getKey();
            if (Pattern.matches(str3 + "@[0-9]+", key)) {
                return key;
            }
        }
        BoneServiceFactory boneServiceFactoryFindBoneServiceFactory = BoneServiceFactoryRegistry.findBoneServiceFactory(str, str2);
        if (boneServiceFactoryFindBoneServiceFactory == null) {
            return null;
        }
        String str4 = str3 + "@" + boneServiceFactoryFindBoneServiceFactory.hashCode();
        f11672b.put(str4, boneServiceFactoryFindBoneServiceFactory);
        return str4;
    }

    public BoneService getServiceInstance(String str) throws Exception {
        BoneService aVar = this.f11676e.get(str);
        if (aVar == null && f11673c.containsKey(str)) {
            aVar = f11673c.get(str);
            this.f11676e.put(str, aVar);
            f11674d.put(str, Integer.valueOf(f11674d.get(str).intValue() + 1));
        }
        if (aVar == null && f11672b.containsKey(str)) {
            BoneServiceFactory boneServiceFactory = f11672b.get(str);
            String strA = a(str);
            try {
                BoneService boneServiceGenerateInstance = boneServiceFactory.generateInstance(this.f11675a, strA);
                if (boneServiceGenerateInstance == null) {
                    throw new RuntimeException("BoneServiceFactory.generateInstance() return null, service id is " + str);
                }
                try {
                    boneServiceGenerateInstance.onInitialize(this.f11675a);
                    BoneServiceMode mode = boneServiceFactory.getMode(strA);
                    if (BoneServiceMode.DEFAULT == mode) {
                        this.f11676e.put(str, boneServiceGenerateInstance);
                    } else if (BoneServiceMode.SINGLE_INSTANCE == mode) {
                        this.f11676e.put(str, boneServiceGenerateInstance);
                        f11673c.put(str, boneServiceGenerateInstance);
                        f11674d.put(str, 1);
                    } else if (BoneServiceMode.ALWAYS_NEW == mode) {
                        aVar = new a(str, boneServiceGenerateInstance);
                    }
                    aVar = boneServiceGenerateInstance;
                } catch (Exception e2) {
                    ALog.e("BoneServiceInstanceManager", "exception happen when call BoneService.initialize()", e2);
                    throw e2;
                }
            } catch (Exception e3) {
                ALog.e("BoneServiceInstanceManager", "exception happen when call BoneServiceFactory.generateInstance()", e3);
                throw e3;
            }
        }
        if (aVar == null) {
            return null;
        }
        return aVar;
    }

    public void onDestroy() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        reset();
        this.f11675a = null;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:5|(2:27|7)(3:8|(2:11|(1:31)(3:28|13|(3:26|15|32)(2:29|16)))|30)|23|17|33|30|3) */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0084, code lost:
    
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0085, code lost:
    
        com.aliyun.alink.linksdk.tools.ALog.e("BoneServiceInstanceManager", "exception happen when call BoneService.onDestroy(), service name is " + r1.getKey(), r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void reset() throws java.lang.IllegalAccessException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r8 = this;
            java.util.Map<java.lang.String, com.aliyun.iot.aep.sdk.bridge.core.service.BoneService> r0 = r8.f11676e
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
        La:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto La1
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getKey()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r3 = r1.getValue()
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneService r3 = (com.aliyun.iot.aep.sdk.bridge.core.service.BoneService) r3
            java.util.Map<java.lang.String, com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory> r4 = com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.f11672b
            java.lang.Object r4 = r4.get(r2)
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory r4 = (com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory) r4
            java.lang.String r5 = "BoneServiceInstanceManager"
            if (r4 != 0) goto L43
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "reset: can not find factory for "
            r4.append(r6)
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            com.aliyun.alink.linksdk.tools.ALog.e(r5, r2)
            goto L80
        L43:
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode r6 = com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode.DEFAULT
            java.lang.String r7 = r8.a(r2)
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode r7 = r4.getMode(r7)
            if (r6 != r7) goto L50
            goto L80
        L50:
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode r6 = com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode.SINGLE_INSTANCE
            java.lang.String r7 = r8.a(r2)
            com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode r4 = r4.getMode(r7)
            if (r6 != r4) goto La
            java.util.Map<java.lang.String, java.lang.Integer> r4 = com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.f11674d
            java.lang.Object r4 = r4.get(r2)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            int r4 = r4 + (-1)
            if (r4 <= 0) goto L76
            java.util.Map<java.lang.String, java.lang.Integer> r1 = com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.f11674d
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)
            r1.put(r2, r3)
            goto La
        L76:
            java.util.Map<java.lang.String, java.lang.Integer> r4 = com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.f11674d
            r4.remove(r2)
            java.util.Map<java.lang.String, com.aliyun.iot.aep.sdk.bridge.core.service.BoneService> r4 = com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.f11673c
            r4.remove(r2)
        L80:
            r3.onDestroy()     // Catch: java.lang.Exception -> L84
            goto La
        L84:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "exception happen when call BoneService.onDestroy(), service name is "
            r3.append(r4)
            java.lang.Object r1 = r1.getKey()
            java.lang.String r1 = (java.lang.String) r1
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            com.aliyun.alink.linksdk.tools.ALog.e(r5, r1, r2)
            goto La
        La1:
            java.util.Map<java.lang.String, com.aliyun.iot.aep.sdk.bridge.core.service.BoneService> r0 = r8.f11676e
            r0.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.iot.aep.sdk.bridge.core.BoneServiceInstanceManager.reset():void");
    }
}
