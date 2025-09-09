package com.aliyun.alink.sdk.bone.plugins;

import android.content.Context;
import com.alipay.sdk.m.x.d;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.sdk.bone.plugins.alog.BoneALog;
import com.aliyun.alink.sdk.bone.plugins.app.BoneApp;
import com.aliyun.alink.sdk.bone.plugins.config.BoneConfig;
import com.aliyun.alink.sdk.bone.plugins.request.BoneRequest;
import com.aliyun.alink.sdk.bone.plugins.system.BoneSystem;
import com.aliyun.iot.aep.sdk.bridge.base.BaseBoneService;
import com.aliyun.iot.aep.sdk.bridge.core.context.JSContext;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCall;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneCallback;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneMethodSpec;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneService;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory;
import com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceMode;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class BaseBoneServiceFactory implements BoneServiceFactory {
    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public BoneService generateInstance(Context context, String str) {
        if (BoneALog.API_NAME.equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.alog.BoneALog$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneALog f11470a = new BoneALog();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11470a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    JSONArray jSONArray = boneCall.args;
                    if ("log".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11470a.log(jSONArray.optString(0), jSONArray.optString(1), jSONArray.optString(2), boneCallback);
                        return true;
                    }
                    if (!"uploadLog".equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11470a.uploadLog(jSONArray.optJSONObject(0), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11470a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11470a.getClass());
                    this.f11470a.onInitialize(context2);
                }
            };
        }
        if ("BoneUserTrack".equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.ut.BoneUserTrack$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneUserTrack f11491a = new BoneUserTrack();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11491a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
                    JSONArray jSONArray = boneCall.args;
                    if (!"record".equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11491a.record(jSONArray.optString(0), jSONArray.optJSONObject(1), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11491a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11491a.getClass());
                    this.f11491a.onInitialize(context2);
                }
            };
        }
        if (BoneApp.API_NAME.equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.app.BoneApp$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneApp f11473a = new BoneApp();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11473a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
                    JSONArray jSONArray = boneCall.args;
                    if ("reload".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11473a.reload(jSContext, boneCallback);
                        return true;
                    }
                    if (!d.f9883z.equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11473a.exit(jSContext, jSONArray.optJSONObject(0), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11473a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11473a.getClass());
                    this.f11473a.onInitialize(context2);
                }
            };
        }
        if (BoneRequest.API_NAME.equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.request.BoneRequest$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneRequest f11476a = new BoneRequest();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11476a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
                    JSONArray jSONArray = boneCall.args;
                    if (!"send".equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11476a.send(jSONArray.optString(0), jSONArray.optString(1), jSONArray.optJSONObject(2), jSONArray.optJSONObject(3), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11476a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11476a.getClass());
                    this.f11476a.onInitialize(context2);
                }
            };
        }
        if (BoneSystem.API_NAME.equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.system.BoneSystem$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneSystem f11483a = new BoneSystem();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11483a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) {
                    JSONArray jSONArray = boneCall.args;
                    if ("getSystemInfo".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.getSystemInfo(boneCallback);
                        return true;
                    }
                    if ("getContainerInfo".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.getContainerInfo(boneCallback);
                        return true;
                    }
                    if ("getNetworkType".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.getNetworkType(boneCallback);
                        return true;
                    }
                    if ("getTimeZone".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.getTimeZone(jSONArray.optJSONObject(0), boneCallback);
                        return true;
                    }
                    if ("isMobileDataEnable".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.isMobileDataEnable(boneCallback);
                        return true;
                    }
                    if ("isBluetoothEnabled".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.isBluetoothEnabled(boneCallback);
                        return true;
                    }
                    if ("stopListenNetworkStatusChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.stopListenNetworkStatusChange(boneCallback);
                        return true;
                    }
                    if ("startListenNetworkStatusChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.startListenNetworkStatusChange(jSContext, boneCallback);
                        return true;
                    }
                    if ("startListenBluetoothStatusChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.startListenBluetoothStatusChange(jSContext, boneCallback);
                        return true;
                    }
                    if ("stopListenBluetoothStatusChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.stopListenBluetoothStatusChange(boneCallback);
                        return true;
                    }
                    if ("startListenKeyBoardChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.startListenKeyBoardChange(jSContext, boneCallback);
                        return true;
                    }
                    if ("stopListenKeyBoardChange".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.stopListenKeyBoardChange(boneCallback);
                        return true;
                    }
                    if ("sendBroadcast".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11483a.sendBroadcast(jSContext, jSONArray.optString(0), jSONArray.optJSONObject(1), boneCallback);
                        return true;
                    }
                    if (!"setStatusBarStyle".equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11483a.setStatusBarStyle(jSContext, jSONArray.optInt(0), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11483a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11483a.getClass());
                    this.f11483a.onInitialize(context2);
                }
            };
        }
        if (BoneConfig.API_NAME.equalsIgnoreCase(str)) {
            return new BoneService() { // from class: com.aliyun.alink.sdk.bone.plugins.config.BoneConfig$$_Proxy

                /* renamed from: a, reason: collision with root package name */
                private BoneConfig f11475a = new BoneConfig();

                private final void a(Class cls) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    if (cls == null) {
                        return;
                    }
                    if (cls != BaseBoneService.class) {
                        a(cls.getSuperclass());
                        return;
                    }
                    try {
                        Field declaredField = cls.getDeclaredField("isBoneInit");
                        declaredField.setAccessible(true);
                        declaredField.set(this.f11475a, Boolean.FALSE);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public boolean onCall(JSContext jSContext, BoneCall boneCall, BoneCallback boneCallback) throws JSONException {
                    JSONArray jSONArray = boneCall.args;
                    if (TmpConstant.PROPERTY_IDENTIFIER_GET.equalsIgnoreCase(boneCall.methodName)) {
                        this.f11475a.get(jSONArray.optJSONArray(0), boneCallback);
                        return true;
                    }
                    if ("getAll".equalsIgnoreCase(boneCall.methodName)) {
                        this.f11475a.getAll(boneCallback);
                        return true;
                    }
                    if (!TmpConstant.PROPERTY_IDENTIFIER_SET.equalsIgnoreCase(boneCall.methodName)) {
                        return false;
                    }
                    this.f11475a.set(jSONArray.optJSONObject(0), boneCallback);
                    return true;
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onDestroy() {
                    this.f11475a.onDestroy();
                }

                @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneService
                public void onInitialize(Context context2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                    a(this.f11475a.getClass());
                    this.f11475a.onInitialize(context2);
                }
            };
        }
        return null;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public List<BoneMethodSpec> getMethods(String str) {
        ArrayList arrayList = new ArrayList();
        if (BoneALog.API_NAME.equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec = new BoneMethodSpec();
            boneMethodSpec.name = "log";
            boneMethodSpec.parameterTypes = new ArrayList(Arrays.asList(String.class, String.class, String.class, BoneCallback.class));
            arrayList.add(boneMethodSpec);
            BoneMethodSpec boneMethodSpec2 = new BoneMethodSpec();
            boneMethodSpec2.name = "uploadLog";
            boneMethodSpec2.parameterTypes = new ArrayList(Arrays.asList(JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec2);
        } else if ("BoneUserTrack".equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec3 = new BoneMethodSpec();
            boneMethodSpec3.name = "record";
            boneMethodSpec3.parameterTypes = new ArrayList(Arrays.asList(String.class, JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec3);
        } else if (BoneApp.API_NAME.equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec4 = new BoneMethodSpec();
            boneMethodSpec4.name = "reload";
            boneMethodSpec4.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, BoneCallback.class));
            arrayList.add(boneMethodSpec4);
            BoneMethodSpec boneMethodSpec5 = new BoneMethodSpec();
            boneMethodSpec5.name = d.f9883z;
            boneMethodSpec5.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec5);
        } else if (BoneRequest.API_NAME.equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec6 = new BoneMethodSpec();
            boneMethodSpec6.name = "send";
            boneMethodSpec6.parameterTypes = new ArrayList(Arrays.asList(String.class, String.class, JSONObject.class, JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec6);
        } else if (BoneSystem.API_NAME.equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec7 = new BoneMethodSpec();
            boneMethodSpec7.name = "getSystemInfo";
            boneMethodSpec7.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec7);
            BoneMethodSpec boneMethodSpec8 = new BoneMethodSpec();
            boneMethodSpec8.name = "getContainerInfo";
            boneMethodSpec8.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec8);
            BoneMethodSpec boneMethodSpec9 = new BoneMethodSpec();
            boneMethodSpec9.name = "getNetworkType";
            boneMethodSpec9.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec9);
            BoneMethodSpec boneMethodSpec10 = new BoneMethodSpec();
            boneMethodSpec10.name = "getTimeZone";
            boneMethodSpec10.parameterTypes = new ArrayList(Arrays.asList(JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec10);
            BoneMethodSpec boneMethodSpec11 = new BoneMethodSpec();
            boneMethodSpec11.name = "isMobileDataEnable";
            boneMethodSpec11.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec11);
            BoneMethodSpec boneMethodSpec12 = new BoneMethodSpec();
            boneMethodSpec12.name = "isBluetoothEnabled";
            boneMethodSpec12.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec12);
            BoneMethodSpec boneMethodSpec13 = new BoneMethodSpec();
            boneMethodSpec13.name = "stopListenNetworkStatusChange";
            boneMethodSpec13.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec13);
            BoneMethodSpec boneMethodSpec14 = new BoneMethodSpec();
            boneMethodSpec14.name = "startListenNetworkStatusChange";
            boneMethodSpec14.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, BoneCallback.class));
            arrayList.add(boneMethodSpec14);
            BoneMethodSpec boneMethodSpec15 = new BoneMethodSpec();
            boneMethodSpec15.name = "startListenBluetoothStatusChange";
            boneMethodSpec15.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, BoneCallback.class));
            arrayList.add(boneMethodSpec15);
            BoneMethodSpec boneMethodSpec16 = new BoneMethodSpec();
            boneMethodSpec16.name = "stopListenBluetoothStatusChange";
            boneMethodSpec16.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec16);
            BoneMethodSpec boneMethodSpec17 = new BoneMethodSpec();
            boneMethodSpec17.name = "startListenKeyBoardChange";
            boneMethodSpec17.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, BoneCallback.class));
            arrayList.add(boneMethodSpec17);
            BoneMethodSpec boneMethodSpec18 = new BoneMethodSpec();
            boneMethodSpec18.name = "stopListenKeyBoardChange";
            boneMethodSpec18.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec18);
            BoneMethodSpec boneMethodSpec19 = new BoneMethodSpec();
            boneMethodSpec19.name = "sendBroadcast";
            boneMethodSpec19.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, String.class, JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec19);
            BoneMethodSpec boneMethodSpec20 = new BoneMethodSpec();
            boneMethodSpec20.name = "setStatusBarStyle";
            boneMethodSpec20.parameterTypes = new ArrayList(Arrays.asList(JSContext.class, Integer.TYPE, BoneCallback.class));
            arrayList.add(boneMethodSpec20);
        } else if (BoneConfig.API_NAME.equalsIgnoreCase(str)) {
            BoneMethodSpec boneMethodSpec21 = new BoneMethodSpec();
            boneMethodSpec21.name = TmpConstant.PROPERTY_IDENTIFIER_GET;
            boneMethodSpec21.parameterTypes = new ArrayList(Arrays.asList(JSONArray.class, BoneCallback.class));
            arrayList.add(boneMethodSpec21);
            BoneMethodSpec boneMethodSpec22 = new BoneMethodSpec();
            boneMethodSpec22.name = "getAll";
            boneMethodSpec22.parameterTypes = new ArrayList(Arrays.asList(BoneCallback.class));
            arrayList.add(boneMethodSpec22);
            BoneMethodSpec boneMethodSpec23 = new BoneMethodSpec();
            boneMethodSpec23.name = TmpConstant.PROPERTY_IDENTIFIER_SET;
            boneMethodSpec23.parameterTypes = new ArrayList(Arrays.asList(JSONObject.class, BoneCallback.class));
            arrayList.add(boneMethodSpec23);
        }
        return arrayList;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public BoneServiceMode getMode(String str) {
        return BoneALog.API_NAME.equalsIgnoreCase(str) ? BoneServiceMode.ALWAYS_NEW : "BoneUserTrack".equalsIgnoreCase(str) ? BoneServiceMode.ALWAYS_NEW : BoneApp.API_NAME.equalsIgnoreCase(str) ? BoneServiceMode.ALWAYS_NEW : BoneRequest.API_NAME.equalsIgnoreCase(str) ? BoneServiceMode.SINGLE_INSTANCE : BoneSystem.API_NAME.equalsIgnoreCase(str) ? BoneServiceMode.DEFAULT : BoneConfig.API_NAME.equalsIgnoreCase(str) ? BoneServiceMode.ALWAYS_NEW : BoneServiceMode.DEFAULT;
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public String getSDKName() {
        return "BoneBaseSDK";
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public String getSDKVersion() {
        return "0.1.5";
    }

    @Override // com.aliyun.iot.aep.sdk.bridge.core.service.BoneServiceFactory
    public List<String> getServiceNameList() {
        return Arrays.asList(BoneALog.API_NAME, "BoneUserTrack", BoneApp.API_NAME, BoneRequest.API_NAME, BoneSystem.API_NAME, BoneConfig.API_NAME);
    }
}
