package com.alibaba.ailabs.iot.mesh.grouping;

import a.a.a.a.b.G;
import android.os.Handler;
import android.os.Looper;
import b.K;
import b.u;
import com.alibaba.ailabs.iot.mesh.TgMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.ConfigActionListener;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.lowenergy.LowEnergyAssist;
import com.alibaba.ailabs.tg.utils.LogUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class MeshGroupingService implements IMeshGroupingService {
    public static final String ACTION_ADD = "ADD";
    public static final String ACTION_DELETE = "DELETE";
    public static final String ACTION_NOTHING = "EXISTS";
    public int mCurrentProxyConnectionState;
    public final String TAG = "MeshGroupingService";
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public K.c onConnectionStateChangeListener = null;

    /* JADX INFO: Access modifiers changed from: private */
    public void makeSureNetworkIsInReady(final JSONObject jSONObject, final ConfigActionListener<Boolean> configActionListener) {
        final u uVarD = G.a().d();
        u.a aVarD = uVarD.d();
        K.c cVar = this.onConnectionStateChangeListener;
        if (cVar != null) {
            uVarD.b(cVar);
            this.onConnectionStateChangeListener = null;
        }
        if (aVarD != null) {
            LogUtils.i("MeshGroupingService", "primary subnets is available: " + aVarD.f());
            if (aVarD.f()) {
                this.mMainThreadHandler.postDelayed(new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.2
                    @Override // java.lang.Runnable
                    public void run() {
                        MeshGroupingService.this.realConfigModelSubscription(jSONObject, configActionListener);
                    }
                }, 1000L);
                return;
            }
            this.mCurrentProxyConnectionState = 0;
            uVarD.a(aVarD, true);
            final Runnable runnable = new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.3
                @Override // java.lang.Runnable
                public void run() {
                    LogUtils.w("MeshGroupingService", "The mesh network does not ready(connect successfully) within the expected time");
                    uVarD.b(MeshGroupingService.this.onConnectionStateChangeListener);
                    MeshGroupingService.this.onConnectionStateChangeListener = null;
                    MeshGroupingService.this.realConfigModelSubscription(jSONObject, configActionListener);
                }
            };
            K.c cVar2 = new K.c() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.4
                @Override // b.K.c
                public void onConnectionStateChanged(K k2, int i2, int i3) {
                    LogUtils.d("MeshGroupingService", "mesh connection state changed to : " + i3);
                }

                @Override // b.K.c
                public void onMeshChannelReady(K k2) {
                    LogUtils.d("MeshGroupingService", "mesh network channel ready: " + k2);
                    if (MeshGroupingService.this.mCurrentProxyConnectionState != 2) {
                        uVarD.b(this);
                        MeshGroupingService.this.onConnectionStateChangeListener = null;
                        MeshGroupingService.this.mCurrentProxyConnectionState = 2;
                        MeshGroupingService.this.mMainThreadHandler.removeCallbacks(runnable);
                        MeshGroupingService.this.mMainThreadHandler.postDelayed(new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.4.1
                            @Override // java.lang.Runnable
                            public void run() {
                                AnonymousClass4 anonymousClass4 = AnonymousClass4.this;
                                MeshGroupingService.this.realConfigModelSubscription(jSONObject, configActionListener);
                            }
                        }, 1000L);
                    }
                }
            };
            this.onConnectionStateChangeListener = cVar2;
            uVarD.a(cVar2);
            this.mMainThreadHandler.postDelayed(runnable, 30000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void realConfigModelSubscription(JSONObject jSONObject, final ConfigActionListener<Boolean> configActionListener) {
        LogUtils.i("MeshGroupingService", "realConfigModelSubscription called, config size " + jSONObject.size());
        for (final String str : jSONObject.keySet()) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            String string = jSONObject2.getString("deviceKey");
            int intValue = jSONObject2.getIntValue("primaryAddress");
            int intValue2 = jSONObject2.getIntValue("elementAddress");
            int intValue3 = jSONObject2.getIntValue("subscriptionAddress");
            int intValue4 = jSONObject2.getIntValue("modelIdentifier");
            String string2 = jSONObject2.getString("action");
            IActionListener<Boolean> iActionListener = new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.5
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i2, String str2) {
                    LogUtils.e("MeshGroupingService", "On failed to config subscription, devId: " + str + ", errorCode: " + i2 + ", errmsg: " + str2);
                    ConfigActionListener configActionListener2 = configActionListener;
                    if (configActionListener2 != null) {
                        configActionListener2.onFailure(str, i2, str2);
                    }
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Boolean bool) {
                    LogUtils.i("MeshGroupingService", "On successful to config subscription, devId: " + str);
                    ConfigActionListener configActionListener2 = configActionListener;
                    if (configActionListener2 != null) {
                        configActionListener2.onSuccess(str, Boolean.TRUE);
                    }
                }
            };
            if (ACTION_ADD.equals(string2)) {
                TgMeshManager.getInstance().configModelSubscriptionAdd(string, intValue, intValue2, intValue3, intValue4, iActionListener);
            } else if ("DELETE".equals(string2)) {
                TgMeshManager.getInstance().configModelSubscriptionDelete(string, intValue, intValue2, intValue3, intValue4, iActionListener);
            } else if (configActionListener != null) {
                configActionListener.onSuccess(str, Boolean.TRUE);
            }
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.grouping.IMeshGroupingService
    public void configModelSubscription(String str, String str2, String str3, final ConfigActionListener<Boolean> configActionListener) {
        LogUtils.d("MeshGroupingService", "configModelSubscription called, productKey: " + str);
        JSONObject object = JSON.parseObject(str3);
        final JSONObject jSONObject = object.getJSONObject("preUpdateIotIdMap");
        LogUtils.i("MeshGroupingService", "isLowEnergy: " + object.getBooleanValue("isLowEnergy"));
        if (!object.getBooleanValue("isLowEnergy")) {
            realConfigModelSubscription(jSONObject, configActionListener);
            return;
        }
        Iterator<String> it = jSONObject.keySet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            String string = jSONObject.getJSONObject(it.next()).getString("action");
            if (ACTION_ADD.equals(string) || "DELETE".equals(string)) {
                i2++;
            }
        }
        LowEnergyAssist.wakeupLowEnergyDevice(str, str2, (i2 * 15) + 10, new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService.1
            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onFailure(int i3, String str4) {
                LogUtils.i("MeshGroupingService", "On failed to wake up low-energy devices, desc: " + str4);
                MeshGroupingService.this.makeSureNetworkIsInReady(jSONObject, configActionListener);
            }

            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onSuccess(Boolean bool) {
                LogUtils.i("MeshGroupingService", "On successful to wake up low-energy device");
                MeshGroupingService.this.makeSureNetworkIsInReady(jSONObject, configActionListener);
            }
        });
    }
}
