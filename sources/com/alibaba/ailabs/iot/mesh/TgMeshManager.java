package com.alibaba.ailabs.iot.mesh;

import a.a.a.a.b.C0224b;
import a.a.a.a.b.C0226d;
import a.a.a.a.b.G;
import a.a.a.a.b.a.C0212a;
import a.a.a.a.b.a.I;
import a.a.a.a.b.c.c;
import a.a.a.a.b.j.a;
import a.a.a.a.b.l.f;
import a.a.a.a.b.m.b;
import a.a.a.a.b.na;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Pair;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import anetwork.channel.util.RequestConstant;
import b.u;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.ConnectionParams;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedMeshNode;
import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.bean.MeshDeviceInfo;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.callback.ConfigActionListener;
import com.alibaba.ailabs.iot.mesh.callback.DeviceOnlineStatusListener;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler;
import com.alibaba.ailabs.iot.mesh.grouping.IMeshGroupingService;
import com.alibaba.ailabs.iot.mesh.grouping.MeshGroupingService;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.task.bean.MeshControlDevice;
import com.alibaba.ailabs.iot.mesh.ut.UtError;
import com.alibaba.ailabs.iot.mesh.ut.UtTraceInfo;
import com.alibaba.ailabs.iot.mesh.utils.Constants;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.network.Callback;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.ailabs.tg.utils.ListUtils;
import com.alibaba.android.multiendinonebridge.IResponseCallback;
import com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alipay.sdk.m.u.i;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.facebook.share.internal.ShareConstants;
import com.huawei.hms.push.constant.RemoteMessageConst;
import datasource.MeshConfigCallback;
import datasource.bean.ConfigResultMap;
import datasource.bean.ConfigurationData;
import datasource.bean.Sigmesh;
import datasource.bean.SigmeshIotDev;
import datasource.bean.SubscribeGroupAddr;
import datasource.channel.MeshNetworkBizOverUnifiedConnectChannel;
import datasource.implemention.DefaultMeshConfig;
import datasource.implemention.MeshRequestService;
import datasource.implemention.NetworkBusinessManager;
import datasource.implemention.data.IotGetListDeviceBaseInfoWithStatusRespData;
import datasource.implemention.model.GenieBoxOnlineStatus;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.configuration.SequenceNumber;
import meshprovisioner.configuration.bean.CfgMsgModelSubscriptionStatus;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class TgMeshManager {
    public static final String KEY_PROVISION_COMBO_MESH_WIFI_BSSID = "BSSID";
    public static final String KEY_PROVISION_COMBO_MESH_WIFI_PASSWORD = "password";
    public static final String KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX = "regionIndex";
    public static final String KEY_PROVISION_COMBO_MESH_WIFI_SSID = "ssid";
    public static final String TAG = "tg_mesh_sdk_" + TgMeshManager.class.getSimpleName();
    public volatile String connectFail;
    public ExecutorService fixedThreadPool;
    public Handler handler;
    public boolean isBluetoothEnabled;
    public volatile boolean isBound;
    public volatile boolean isConnected;
    public boolean isConnecting;
    public volatile boolean isEnvInitialized;
    public volatile boolean isInitialized;
    public boolean isLocationEnabled;
    public MeshService.b mBinder;
    public final BroadcastReceiver mBroadcastReceiver;
    public final Deque<SIGMeshBizRequest> mConfigTaskQueue;
    public Context mContext;
    public a mControlTask;
    public ExtendedMeshNode mExtendedMeshNode;
    public volatile AtomicBoolean mInConfigProgress;
    public List<MeshStatusCallback> mMeshStatusCallbacks;
    public SIGMeshBizRequest.NetworkParameter mNetworkParameter;
    public final List<String> mPendingProvisionDeviceAddress;
    public OnReadyToBindHandler mPendingReadyToBindHandler;
    public byte mReceivedConfigCount;
    public ServiceConnection mServiceConnection;
    public ExecutorService mSingleThreadExecutor;
    public List<MeshMsgListener> meshMsgListenerList;
    public c multiEndinOneProxy;
    public String productKey;
    public int provisionFinishedCounter;
    public AtomicBoolean translateControlCommandByCloud;

    /* renamed from: com.alibaba.ailabs.iot.mesh.TgMeshManager$33, reason: invalid class name */
    static /* synthetic */ class AnonymousClass33 {
        public static final /* synthetic */ int[] $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus;
        public static final /* synthetic */ int[] $SwitchMap$com$alibaba$ailabs$iot$mesh$biz$SIGMeshBizRequest$Type;

        static {
            int[] iArr = new int[MeshNodeStatus.values().length];
            $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus = iArr;
            try {
                iArr[MeshNodeStatus.PROVISIONING_CAPABILITIES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[MeshNodeStatus.PROVISIONING_COMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[MeshNodeStatus.PROVISIONING_FAILED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[MeshNodeStatus.REQUEST_FAILED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[MeshNodeStatus.COMBO_WIFI_CONFIG_STATUS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[SIGMeshBizRequest.Type.values().length];
            $SwitchMap$com$alibaba$ailabs$iot$mesh$biz$SIGMeshBizRequest$Type = iArr2;
            try {
                iArr2[SIGMeshBizRequest.Type.CONFIG_MODEL_SUBSCRIPTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public enum DevOnlineStatus {
        DEV_ST_DEFAULT(-1),
        DEV_ST_ONLINE(0),
        DEV_ST_OFFLINE(1),
        DEV_ST_WAIT_CONFIRM(2),
        DEV_ST_UNKNOWN(3);

        public int status;

        DevOnlineStatus(int i2) {
            this.status = i2;
        }

        public static DevOnlineStatus fromIntValue(int i2) {
            return i2 != -1 ? i2 != 0 ? i2 != 1 ? DEV_ST_UNKNOWN : DEV_ST_OFFLINE : DEV_ST_ONLINE : DEV_ST_DEFAULT;
        }

        public int getStatus() {
            return this.status;
        }
    }

    class MeshConnection implements ServiceConnection {
        public MeshConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (!(iBinder instanceof MeshService.b)) {
                a.a.a.a.b.m.a.b(TgMeshManager.TAG, "Bind service occurs exception");
                return;
            }
            TgMeshManager.this.mBinder = (MeshService.b) iBinder;
            if (TgMeshManager.this.mPendingReadyToBindHandler != null) {
                TgMeshManager.this.mBinder.a(TgMeshManager.this.mPendingReadyToBindHandler);
            }
            a.a.a.a.b.m.a.a(TgMeshManager.TAG, "Connected to meshService");
            a.a.a.a.b.m.a.a(TgMeshManager.TAG, "isInitialized: " + TgMeshManager.this.isInitialized);
            TgMeshManager.this.isBound = true;
            if (TgMeshManager.this.isInitialized) {
                TgMeshManager.this.publishStatus(1, "Initialize success...");
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "Initialize success...");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            TgMeshManager.this.mBinder = null;
            TgMeshManager.this.isBound = false;
            TgMeshManager.this.publishStatus(-1, "Disconnected to meshService");
            a.a.a.a.b.m.a.b(TgMeshManager.TAG, "Disconnected to meshService");
        }
    }

    private static class SingletonClassInstance {
        public static final TgMeshManager instance = new TgMeshManager();
    }

    private int checkEnv() {
        if (!this.isInitialized) {
            publishStatus(-1, "TgMeshManager has not initialized");
            a.a.a.a.b.m.a.b(TAG, "TgMeshManager has not initialized, " + hashCode());
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.MESH_MANAGER_NOT_INITIALIZED_ERROR;
            b.a("ALSMesh", "ble", this.productKey, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
            return 20003;
        }
        if (!this.isBound) {
            publishStatus(-1, "Has not call the \"enter\" or enter failed, please try enter again");
            a.a.a.a.b.m.a.b(TAG, "TgMeshManager has not bond to service, " + hashCode());
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum2 = MeshUtConst$MeshErrorEnum.MESH_MANAGER_NOT_BIND_SERVICE_ERROR;
            b.a("ALSMesh", "ble", this.productKey, "", 0L, meshUtConst$MeshErrorEnum2.getErrorCode(), meshUtConst$MeshErrorEnum2.getErrorMsg());
            return 20012;
        }
        this.isBluetoothEnabled = Utils.isBleEnabled();
        this.isLocationEnabled = Utils.isLocationEnabled(this.mContext);
        boolean zIsLocationPermissionsGranted = Utils.isLocationPermissionsGranted(this.mContext);
        if (Build.VERSION.SDK_INT < 33 && !this.isLocationEnabled) {
            publishStatus(-9, "Location not enabled");
            a.a.a.a.b.m.a.b(TAG, "Location not enabled, " + hashCode());
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum3 = MeshUtConst$MeshErrorEnum.LOCATION_NOT_ENABLED_ERROR;
            b.a("ALSMesh", "ble", this.productKey, "", 0L, meshUtConst$MeshErrorEnum3.getErrorCode(), meshUtConst$MeshErrorEnum3.getErrorMsg());
            return 20002;
        }
        if (this.isBluetoothEnabled) {
            if (zIsLocationPermissionsGranted) {
                return 0;
            }
            publishStatus(-10, "location permission not granted");
            a.a.a.a.b.m.a.b(TAG, "location permission not granted,  " + hashCode());
            return 20013;
        }
        publishStatus(-11, "Bluetooth not enabled");
        a.a.a.a.b.m.a.b(TAG, "Bluetooth disabled, " + hashCode());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum4 = MeshUtConst$MeshErrorEnum.BLUETOOTH_NOT_ENABLED_ERROR;
        b.a("ALSMesh", "ble", this.productKey, "", 0L, meshUtConst$MeshErrorEnum4.getErrorCode(), meshUtConst$MeshErrorEnum4.getErrorMsg());
        return 20001;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deviceControlByServer(String str, String str2, final IActionListener iActionListener, final UtTraceInfo utTraceInfo) {
        a.a.a.a.b.m.a.a(TAG, "deviceControlByServer...");
        na.a().a(str, "3404", str2, new MeshConfigCallback<List<Sigmesh>>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.32
            @Override // datasource.MeshConfigCallback
            public void onFailure(String str3, String str4) {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "errorCode:" + str3 + ", errorMessage:" + str4);
                UtTraceInfo utTraceInfo2 = utTraceInfo;
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(str4);
                a.a.a.a.b.l.c.a(utTraceInfo2, 20005, sb.toString());
                iActionListener.onFailure(-20, str4);
            }

            @Override // datasource.MeshConfigCallback
            public void onSuccess(List<Sigmesh> list) {
                u.a aVarH;
                if (!ListUtils.isEmpty(list) && list.get(0) != null) {
                    Sigmesh sigmesh = list.get(0);
                    String devId = sigmesh.getDevId();
                    if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                        int destAddr = sigmesh.getDevice().getDestAddr();
                        int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                        int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                        if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                            byte[] opCodeBytes = Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16));
                            byte[] byteArray = MeshParserUtils.toByteArray(sigmesh.getAction().getParameters());
                            ProvisionedMeshNode provisionedMeshNodeB = G.a().d().b(devId);
                            if (provisionedMeshNodeB != null && (aVarH = G.a().d().h(provisionedMeshNodeB.getNetworkKey())) != null) {
                                provisionedMeshNodeB = (ProvisionedMeshNode) aVarH.a(destAddr);
                            }
                            if (provisionedMeshNodeB == null) {
                                a.a.a.a.b.m.a.d(TgMeshManager.TAG, "Can't find the mesh node via device id");
                                u.a aVarD = G.a().d().d();
                                if (aVarD != null) {
                                    a.a.a.a.b.m.a.d(TgMeshManager.TAG, "Can't find the mesh node via device id, try primary subnets");
                                    provisionedMeshNodeB = (ProvisionedMeshNode) aVarD.a(destAddr);
                                }
                            }
                            ProvisionedMeshNode provisionedMeshNode = provisionedMeshNodeB;
                            if (TgMeshManager.this.mBinder == null) {
                                iActionListener.onFailure(-30, "Bind MeshService error");
                                return;
                            } else {
                                TgMeshManager.this.mBinder.a(provisionedMeshNode, appKeyIndex, netKeyIndex, Utils.byteArray2Int(opCodeBytes), byteArray, iActionListener, false);
                                return;
                            }
                        }
                    }
                }
                a.a.a.a.b.l.c.a(utTraceInfo, 20010, "mtop success while parameter illetal");
                iActionListener.onFailure(-21, "Wrong parameter");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execWakeUpDevice(String str, boolean z2, final IActionListener<Boolean> iActionListener) {
        na.a().a(str, z2, new MeshConfigCallback<List<Sigmesh>>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.12
            @Override // datasource.MeshConfigCallback
            public void onFailure(String str2, String str3) {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "execWakeUpDevice failure:" + str2);
                iActionListener.onFailure(-28, str3);
            }

            @Override // datasource.MeshConfigCallback
            public void onSuccess(List<Sigmesh> list) throws InterruptedException {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "execWakeUpDevice");
                if (ListUtils.isEmpty(list)) {
                    iActionListener.onFailure(-28, "sigmesh is null");
                } else if (TgMeshManager.this.mBinder == null || !TgMeshManager.this.mBinder.k()) {
                    iActionListener.onFailure(-29, "app not connect proxy");
                } else {
                    TgMeshManager.this.sendWakeUpMessage(list, iActionListener);
                }
            }
        });
    }

    public static TgMeshManager getInstance() {
        return SingletonClassInstance.instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void groupControlByServer(String str, String str2, String str3, final IActionListener<Boolean> iActionListener) {
        if (TextUtils.isEmpty(str3)) {
            Utils.notifyFailed(iActionListener, -21, "Wrong parameter jsonString=null.");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            Utils.notifyFailed(iActionListener, -21, "Wrong parameter groupId=null.");
        } else if (TextUtils.isEmpty(str2)) {
            Utils.notifyFailed(iActionListener, -21, "Wrong parameter method=null.");
        } else {
            na.a().d(str, str2, str3, new MeshConfigCallback<List<Sigmesh>>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.31
                @Override // datasource.MeshConfigCallback
                public void onFailure(String str4, String str5) {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "errorCode:" + str4 + ", errorMessage:" + str5);
                    Utils.notifyFailed(iActionListener, -20, str5);
                }

                @Override // datasource.MeshConfigCallback
                public void onSuccess(List<Sigmesh> list) throws NumberFormatException {
                    String str4 = TgMeshManager.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("sendGroupMessage: ");
                    sb.append(list != null ? list.size() : 0);
                    sb.append(i.f9802b);
                    sb.append(list == null);
                    a.a.a.a.b.m.a.a(str4, sb.toString());
                    if (!ListUtils.isEmpty(list) && list.get(0) != null) {
                        Sigmesh sigmesh = list.get(0);
                        if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                            int destAddr = sigmesh.getDevice().getDestAddr();
                            int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                            int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                            a.a.a.a.b.m.a.a(TgMeshManager.TAG, "sendGroupMessage: unicastAddress=" + destAddr + ";appKeyInder=" + appKeyIndex + ";netKeyIndex=" + netKeyIndex);
                            if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                                int i2 = Integer.parseInt(sigmesh.getAction().getOpcode(), 16);
                                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "sendGroupMessage: opcodeInt=" + i2);
                                TgMeshManager.this.mBinder.a(netKeyIndex, appKeyIndex, destAddr, Utils.byteArray2Int(Utils.getOpCodeBytes(i2)), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), iActionListener);
                                return;
                            }
                        }
                    }
                    Utils.notifyFailed(iActionListener, -21, "Wrong parameter");
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBindState(Intent intent) {
        if (intent.getExtras() == null) {
            return;
        }
        int i2 = intent.getExtras().getInt(Utils.EXTRA_BIND_CODE, 0);
        if (i2 == -2) {
            String string = intent.getExtras().getString(Utils.EXTRA_BIND_STATE_MSG);
            publishStatus(-5, "unbind_user_failed: " + string);
            a.a.a.a.b.m.a.a(TAG, "unbind_user_failed: " + string);
            return;
        }
        if (i2 == -1) {
            String string2 = intent.getExtras().getString(Utils.EXTRA_BIND_STATE_MSG);
            publishStatus(-4, "bind_user_failed: " + string2);
            a.a.a.a.b.m.a.a(TAG, "bind_user_failed: " + string2);
            return;
        }
        if (i2 == 1) {
            publishStatus(4, intent.getExtras().getString(Utils.EXTRA_BIND_STATE_MSG));
            a.a.a.a.b.m.a.a(TAG, "bind_user_success, procuctKey:" + this.productKey);
            return;
        }
        if (i2 == 2) {
            publishStatus(5, "unbind_user_success");
            a.a.a.a.b.m.a.a(TAG, "unbind_user_success");
        } else {
            if (i2 != 10) {
                return;
            }
            publishStatus(10, "remove_node_success");
            a.a.a.a.b.m.a.a(TAG, "remove_node_success");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleConfigurationStates(Intent intent) {
        if (intent.getExtras() == null) {
            return;
        }
        int i2 = intent.getExtras().getInt(Utils.EXTRA_CONFIGURATION_STATE);
        a.a.a.a.b.m.a.a(TAG, "configurationState: " + i2);
        MeshNodeStatus meshNodeStatusFromStatusCode = MeshNodeStatus.fromStatusCode(i2);
        if (this.mBinder.f() instanceof ProvisionedMeshNode) {
            this.mExtendedMeshNode.updateMeshNode((ProvisionedMeshNode) this.mBinder.f());
        }
        int i3 = AnonymousClass33.$SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[meshNodeStatusFromStatusCode.ordinal()];
        if (i3 == 3) {
            publishStatus(-3, intent.getExtras().getString(Utils.EXTRA_CONFIGURATION_FAIL_MSG));
        } else if (i3 != 4) {
            publishStatus(0, meshNodeStatusFromStatusCode.name());
        } else {
            publishStatus(-8, intent.getExtras().getString(Utils.EXTRA_REQUEST_FAIL_MSG));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void handleGenericState(android.content.Intent r7) {
        /*
            r6 = this;
            java.lang.String r0 = r7.getAction()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto Lcc
            android.os.Bundle r1 = r7.getExtras()
            if (r1 != 0) goto L12
            goto Lcc
        L12:
            int r1 = r0.hashCode()
            r2 = 781060501(0x2e8e0995, float:6.4591145E-11)
            r3 = 1
            if (r1 == r2) goto L3b
            r2 = 1264097733(0x4b5899c5, float:1.4195141E7)
            if (r1 == r2) goto L31
            r2 = 1322041106(0x4eccbf12, float:1.717537E9)
            if (r1 == r2) goto L27
            goto L45
        L27:
            java.lang.String r1 = "ACTION_GENERIC_ON_OFF_STATE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L45
            r0 = 0
            goto L46
        L31:
            java.lang.String r1 = "ACTION_GENERIC_LEVEL_STATE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L45
            r0 = r3
            goto L46
        L3b:
            java.lang.String r1 = "ACTION_VENDOR_MODEL_MESSAGE_STATE"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L45
            r0 = 2
            goto L46
        L45:
            r0 = -1
        L46:
            java.lang.String r1 = "EXTRA_GENERIC_TRANSITION_RES"
            java.lang.String r2 = "EXTRA_GENERIC_TRANSITION_STEPS"
            java.lang.String r4 = "EXTRA_GENERIC_TARGET_STATE"
            java.lang.String r5 = "EXTRA_GENERIC_PRESENT_STATE"
            if (r0 == 0) goto L90
            if (r0 == r3) goto L53
            goto Lcc
        L53:
            android.os.Bundle r0 = r7.getExtras()
            int r0 = r0.getInt(r5)
            android.os.Bundle r3 = r7.getExtras()
            int r3 = r3.getInt(r4)
            android.os.Bundle r4 = r7.getExtras()
            r4.getInt(r2)
            android.os.Bundle r7 = r7.getExtras()
            r7.getInt(r1)
            java.lang.String r7 = com.alibaba.ailabs.iot.mesh.TgMeshManager.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "generic_on_off_state, presentLevel: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = ", targetLevel: "
            r1.append(r0)
            r1.append(r3)
            java.lang.String r0 = r1.toString()
            a.a.a.a.b.m.a.a(r7, r0)
            goto Lcc
        L90:
            android.os.Bundle r0 = r7.getExtras()
            boolean r0 = r0.getBoolean(r5)
            android.os.Bundle r3 = r7.getExtras()
            boolean r3 = r3.getBoolean(r4)
            android.os.Bundle r4 = r7.getExtras()
            r4.getInt(r2)
            android.os.Bundle r7 = r7.getExtras()
            r7.getInt(r1)
            java.lang.String r7 = com.alibaba.ailabs.iot.mesh.TgMeshManager.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "generic_on_off_state, presentOnOffState: "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = ", targetOnOffState: "
            r1.append(r0)
            r1.append(r3)
            java.lang.String r0 = r1.toString()
            a.a.a.a.b.m.a.a(r7, r0)
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.TgMeshManager.handleGenericState(android.content.Intent):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleProvisioningStates(Intent intent) {
        if (intent.getExtras() == null || this.mBinder == null) {
            return;
        }
        MeshNodeStatus meshNodeStatusFromStatusCode = MeshNodeStatus.fromStatusCode(intent.getExtras().getInt(Utils.EXTRA_PROVISIONING_STATE));
        int i2 = AnonymousClass33.$SwitchMap$com$alibaba$ailabs$iot$mesh$bean$MeshNodeStatus[meshNodeStatusFromStatusCode.ordinal()];
        if (i2 == 1) {
            this.mExtendedMeshNode.updateMeshNode(this.mBinder.f());
            publishStatus(0, meshNodeStatusFromStatusCode.name());
            return;
        }
        if (i2 == 2) {
            publishStatus(3, meshNodeStatusFromStatusCode.name());
            this.isConnecting = false;
        } else {
            if (i2 == 3) {
                publishStatus(-3, intent.getExtras().getString(Utils.EXTRA_PROVISIONING_FAIL_MSG));
                return;
            }
            if (i2 == 4) {
                publishStatus(-8, intent.getExtras().getString(Utils.EXTRA_REQUEST_FAIL_MSG));
            } else if (i2 != 5) {
                publishStatus(0, meshNodeStatusFromStatusCode.name());
            } else {
                publishStatus(20, intent.getExtras().getString(Utils.EXTRA_PROVISIONING_FAIL_MSG));
            }
        }
    }

    private void initBizServiceModule() {
        if (d.b.a().a(IMeshGroupingService.class) == null) {
            d.b.a().a(IMeshGroupingService.class, new MeshGroupingService());
        }
    }

    private void isGenieBoxOnline(final IActionListener<Boolean> iActionListener) {
        ((MeshRequestService) NetworkBusinessManager.getService(MeshRequestService.class)).getListDeviceBaseInfoWithStatus().enqueue(new Callback<IotGetListDeviceBaseInfoWithStatusRespData>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.9
            public void onFailure(int i2, String str, String str2) {
            }

            public void onSuccess(int i2, IotGetListDeviceBaseInfoWithStatusRespData iotGetListDeviceBaseInfoWithStatusRespData) {
                if (iotGetListDeviceBaseInfoWithStatusRespData == null || iotGetListDeviceBaseInfoWithStatusRespData.getModel() == null) {
                    iActionListener.onFailure(-3, "failed to query genie box status");
                    return;
                }
                if (iotGetListDeviceBaseInfoWithStatusRespData.getModel().size() == 0) {
                    iActionListener.onFailure(-4, "size of genie boxs is 0");
                    return;
                }
                Iterator<Map.Entry<String, GenieBoxOnlineStatus>> it = iotGetListDeviceBaseInfoWithStatusRespData.getModel().entrySet().iterator();
                while (it.hasNext()) {
                    GenieBoxOnlineStatus value = it.next().getValue();
                    if (value.getOnline() == 1) {
                        a.a.a.a.b.m.a.c(TgMeshManager.TAG, "genie box online " + value.getUuid());
                        iActionListener.onSuccess(Boolean.TRUE);
                        return;
                    }
                }
                a.a.a.a.b.m.a.c(TgMeshManager.TAG, "all genie are offline");
                iActionListener.onFailure(-5, "all genie are offline");
            }
        });
    }

    private boolean isLowCostDeviceExist() {
        return MeshDeviceInfoManager.getInstance().isLowCostDeviceExist();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void nextRequest() {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "nextRequest called, mInConfigProgress: " + this.mInConfigProgress);
        if (this.mInConfigProgress.compareAndSet(false, true)) {
            if (this.mConfigTaskQueue.size() <= 0) {
                this.mInConfigProgress.set(false);
                return;
            }
            final SIGMeshBizRequest first = this.mConfigTaskQueue.getFirst();
            if (first != null) {
                this.mInConfigProgress.set(true);
                if (AnonymousClass33.$SwitchMap$com$alibaba$ailabs$iot$mesh$biz$SIGMeshBizRequest$Type[first.l().ordinal()] == 1) {
                    if (this.mBinder == null) {
                        Utils.notifyFailed(first.m(), -30, "TgMeshManager has not bond to MeshService");
                        if (this.mConfigTaskQueue.size() == 0) {
                            return;
                        }
                        try {
                            this.mConfigTaskQueue.removeFirst();
                        } catch (NoSuchElementException unused) {
                        }
                        nextRequest();
                        return;
                    }
                    a.a.a.a.b.m.a.a(str, "Execute ConfigModelSubRequest");
                    final C0212a c0212a = (C0212a) first;
                    if (!TextUtils.isEmpty(c0212a.s()) && c0212a.s().length() == 32) {
                        first.p();
                        this.mBinder.a(c0212a.r(), c0212a.s(), c0212a.v(), c0212a.t(), c0212a.w(), c0212a.u(), new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.29
                            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                            public void onFailure(int i2, String str2) {
                                a.a.a.a.b.m.a.b(TgMeshManager.TAG, "On Failed config model subscription, devId: " + c0212a.t() + ", errorCode: " + i2 + " ,desc: " + str2);
                                TgMeshManager.this.mInConfigProgress.set(false);
                                first.b();
                                Utils.notifyFailed(first.m(), i2, str2);
                                try {
                                    TgMeshManager.this.mConfigTaskQueue.removeFirst();
                                } catch (NoSuchElementException unused2) {
                                }
                                if (TgMeshManager.this.mConfigTaskQueue.size() == 0) {
                                    return;
                                }
                                TgMeshManager.this.nextRequest();
                            }

                            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                            public void onSuccess(Boolean bool) {
                                a.a.a.a.b.m.a.c(TgMeshManager.TAG, "On successful config model subscription, devId: " + c0212a.t());
                                TgMeshManager.this.mInConfigProgress.set(false);
                                first.b();
                                Utils.notifySuccess((IActionListener<Boolean>) first.m(), Boolean.TRUE);
                                try {
                                    TgMeshManager.this.mConfigTaskQueue.removeFirst();
                                } catch (NoSuchElementException unused2) {
                                }
                                if (TgMeshManager.this.mConfigTaskQueue.size() == 0) {
                                    return;
                                }
                                TgMeshManager.this.nextRequest();
                            }
                        }, 0);
                    }
                    Utils.notifyFailed(first.m(), -32, "DeviceKey Cannot be empty or illegal deviceKey");
                    if (this.mConfigTaskQueue.size() == 0) {
                        return;
                    }
                    try {
                        this.mConfigTaskQueue.removeFirst();
                    } catch (NoSuchElementException unused2) {
                    }
                    nextRequest();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallback(int i2, String str, String str2, byte[] bArr, byte[] bArr2, byte[] bArr3, int i3, String str3) {
        MeshAccessPayload meshAccessPayload = new MeshAccessPayload();
        meshAccessPayload.setOpCode(str2);
        meshAccessPayload.setParameters(bArr);
        meshAccessPayload.setNetKey(bArr3);
        meshAccessPayload.setTranslatedTSLDesc(null);
        meshAccessPayload.setSequenceNo(i3);
        meshAccessPayload.setIotId(str3);
        if (i2 == 0 && !TextUtils.isEmpty(str)) {
            try {
                JSONObject object = JSON.parseObject(str);
                if (object.getBoolean("success").booleanValue()) {
                    JSONObject jSONObject = object.getJSONObject("data");
                    if (jSONObject != null && jSONObject.containsKey("results")) {
                        meshAccessPayload.setTranslatedTSLDesc(jSONObject.getJSONObject("results").toJSONString());
                    }
                } else {
                    a.a.a.a.b.m.a.d(TAG, "Translation failed");
                }
            } catch (JSONException e2) {
                a.a.a.a.b.m.a.b(TAG, e2.toString());
            }
        }
        a.a.a.a.b.m.a.c(TAG, "notifyCallback sequenceNumber: " + i3 + " tsl: " + meshAccessPayload.getTranslatedTSLDesc());
        Iterator<MeshMsgListener> it = this.meshMsgListenerList.iterator();
        while (it.hasNext()) {
            try {
                it.next().onReceiveMeshMessage(bArr2, meshAccessPayload);
            } catch (Exception e3) {
                a.a.a.a.b.m.a.b(TAG, e3.toString());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMeshNetworkReady() {
        IoTMultiendInOneBridge.getInstance().onProxyConnected();
        for (int i2 = 0; i2 < this.mConfigTaskQueue.size(); i2++) {
            ((SIGMeshBizRequest) ((LinkedList) this.mConfigTaskQueue).get(i2)).b();
        }
        nextRequest();
        offerBizRequest(SIGMeshBizRequestGenerator.e());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTransactionStateReceived(Intent intent) {
        ExtendedMeshNode extendedMeshNode;
        String action = intent.getAction();
        ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) this.mBinder.f();
        if (TextUtils.isEmpty(action)) {
            return;
        }
        action.hashCode();
        if (action.equals(Utils.ACTION_TRANSACTION_STATE) && (extendedMeshNode = this.mExtendedMeshNode) != null) {
            extendedMeshNode.updateMeshNode(provisionedMeshNode);
            if (intent.getExtras() != null) {
                intent.getExtras().getInt(Utils.EXTRA_ELEMENT_ADDRESS);
                intent.getBooleanExtra(Utils.EXTRA_DATA, true);
                publishStatus(-3, "transaction_failed");
                a.a.a.a.b.m.a.a(TAG, "transaction_failed");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void publishStatus(int i2, String str) {
        a.a.a.a.b.m.a.a(TAG, "publishStatus() called with: statusCode = [" + i2 + "], statusMsg = [" + str + "]");
        Iterator it = new ArrayList(this.mMeshStatusCallbacks).iterator();
        while (it.hasNext()) {
            MeshStatusCallback meshStatusCallback = (MeshStatusCallback) it.next();
            if (meshStatusCallback != null) {
                meshStatusCallback.onStatus(i2, str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendWakeUpMessage(List<Sigmesh> list, final IActionListener<Boolean> iActionListener) throws InterruptedException {
        Sigmesh sigmesh = list.get(0);
        if (sigmesh.getDevice() == null || sigmesh.getAction() == null) {
            return;
        }
        int destAddr = sigmesh.getDevice().getDestAddr();
        int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
        int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
        if (sigmesh.getAction().getOpcode() == null || sigmesh.getAction().getParameters() == null) {
            return;
        }
        byte[] opCodeBytes = Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16));
        sendMessge(destAddr, appKeyIndex, netKeyIndex, Utils.byteArray2Int(opCodeBytes), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), new IActionListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.13
            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onFailure(int i2, String str) {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "sendWakeUpMessage failure:" + i2 + ";msg:" + str);
                iActionListener.onFailure(i2, str);
            }

            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onSuccess(Object obj) {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "sendWakeUpMessage success");
                iActionListener.onSuccess(Boolean.TRUE);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addDevice(final com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice r11, final java.util.Map<java.lang.String, java.lang.Object> r12) {
        /*
            r10 = this;
            java.lang.String r0 = com.alibaba.ailabs.iot.mesh.TgMeshManager.TAG
            java.lang.String r1 = "Add device with extensions ..."
            a.a.a.a.b.m.a.a(r0, r1)
            if (r11 != 0) goto L13
            r11 = -7
            java.lang.String r12 = "DeviceId is null"
            r10.publishStatus(r11, r12)
            a.a.a.a.b.m.a.b(r0, r12)
            return
        L13:
            int r1 = r10.checkEnv()
            if (r1 == 0) goto L1a
            return
        L1a:
            com.alibaba.ailabs.iot.mesh.MeshService$b r1 = r10.mBinder
            if (r1 == 0) goto L26
            int r1 = r1.d()
            r2 = 1
            if (r1 != r2) goto L26
            goto L27
        L26:
            r2 = 0
        L27:
            r10.isConnecting = r2
            if (r2 == 0) goto L38
            java.lang.String r11 = "in connecting"
            a.a.a.a.b.m.a.a(r0, r11)
            r11 = 8
            java.lang.String r12 = "connecting..."
            r10.publishStatus(r11, r12)
            return
        L38:
            aisscanner.ScanRecord r1 = r11.getScanRecord()
            if (r1 == 0) goto L9d
            android.os.ParcelUuid r2 = new android.os.ParcelUuid
            java.util.UUID r3 = com.alibaba.ailabs.iot.mesh.ble.BleMeshManager.MESH_PROVISIONING_UUID
            r2.<init>(r3)
            byte[] r8 = r1.getServiceData(r2)
            if (r8 == 0) goto L7f
            int r1 = r8.length
            r2 = 16
            if (r1 < r2) goto L7f
            int r1 = com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil.extractPIDFromUUID(r8)
            java.lang.String r2 = java.lang.String.valueOf(r1)
            r10.productKey = r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "productKey = "
            r2.append(r3)
            java.lang.String r3 = r10.productKey
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            a.a.a.a.b.m.a.c(r0, r2)
            java.lang.String r6 = java.lang.String.valueOf(r1)
            r7 = 0
            java.lang.String r9 = ""
            java.lang.String r4 = "ALSMesh"
            java.lang.String r5 = "ble"
            a.a.a.a.b.m.b.a(r4, r5, r6, r7, r8, r9)
            goto La2
        L7f:
            if (r8 != 0) goto L87
            java.lang.String r1 = "service data is null"
            a.a.a.a.b.m.a.b(r0, r1)
            goto La2
        L87:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "service data length = "
            r1.append(r2)
            int r2 = r8.length
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            a.a.a.a.b.m.a.c(r0, r1)
            goto La2
        L9d:
            java.lang.String r1 = "scanRecord is null"
            a.a.a.a.b.m.a.c(r0, r1)
        La2:
            com.alibaba.ailabs.iot.mesh.MeshService$b r0 = r10.mBinder
            if (r0 == 0) goto Lae
            com.alibaba.ailabs.iot.mesh.TgMeshManager$6 r1 = new com.alibaba.ailabs.iot.mesh.TgMeshManager$6
            r1.<init>()
            r0.a(r1)
        Lae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.TgMeshManager.addDevice(com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice, java.util.Map):void");
    }

    public void batchBindDevices(List<UnprovisionedBluetoothMeshDevice> list) {
        batchBindDevices(list, null);
    }

    public void bindDevice(UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice) {
        bindDevice(unprovisionedBluetoothMeshDevice, null);
    }

    public void cancelAllBizRequest() {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.a();
        }
    }

    public void configModelSubscription(List<Sigmesh> list, final ConfigActionListener<Boolean> configActionListener) {
        if (list == null || list.size() == 0) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (Sigmesh sigmesh : list) {
            final String devId = sigmesh.getDevId();
            final Sigmesh.Action action = sigmesh.getAction();
            Sigmesh.Device device = sigmesh.getDevice();
            SIGMeshBizRequest.Type type = SIGMeshBizRequest.Type.COMMON_REQUEST_RESPONSE;
            type.setOpcode(Integer.parseInt(action.getOpcode(), 16));
            type.setExpectedOpcode((short) Integer.parseInt(action.getStatusOpcode(), 16));
            u.a aVarD = G.a().d().d();
            ProvisionedMeshNode provisionedMeshNode = aVarD != null ? (ProvisionedMeshNode) aVarD.d(AddressUtils.getUnicastAddressBytes(device.getDestAddr())) : null;
            SIGMeshBizRequest sIGMeshBizRequestA = new SIGMeshBizRequest.a().a(type).a(provisionedMeshNode).a(AddressUtils.getUnicastAddressBytes(device.getDestAddr())).a(new I<Object>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.26
                @Override // a.a.a.a.b.a.I
                public Pair<Integer, ?> parseResponse(Object obj) {
                    if (obj instanceof CfgMsgModelSubscriptionStatus) {
                        return CfgMsgModelSubscriptionStatus.parseStatus(TgMeshManager.this.mContext, ((CfgMsgModelSubscriptionStatus) obj).getStatus());
                    }
                    if (obj instanceof byte[]) {
                        return CfgMsgModelSubscriptionStatus.parseStatus(TgMeshManager.this.mContext, ((byte[]) obj)[0]);
                    }
                    return null;
                }
            }).a(true).a(new IActionListener<Object>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.25
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i2, String str) {
                    ConfigActionListener configActionListener2 = configActionListener;
                    if (configActionListener2 != null) {
                        configActionListener2.onFailure(devId, i2, str);
                    }
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Object obj) {
                    ConfigActionListener configActionListener2 = configActionListener;
                    if (configActionListener2 != null) {
                        configActionListener2.onSuccess(devId, Boolean.TRUE);
                    }
                }
            }).a(new SIGMeshBizRequest.b() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.24
                @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
                public byte[] getEncodedParameters() {
                    return ConvertUtils.hexString2Bytes(action.getParameters());
                }
            }).a();
            sIGMeshBizRequestA.a(ConvertUtils.hexString2Bytes(device.getDeviceKey()));
            if (provisionedMeshNode != null && provisionedMeshNode.getDeviceKey() == null) {
                provisionedMeshNode.setDeviceKey(ConvertUtils.hexString2Bytes(device.getDeviceKey()));
            }
            linkedList.add(sIGMeshBizRequestA);
        }
        offerBizRequest(linkedList);
    }

    public void configModelSubscriptionAdd(String str, int i2, int i3, int i4, int i5, IActionListener<Boolean> iActionListener) {
        if (a.a.a.a.b.d.a.f1335b) {
            offerBizRequest(SIGMeshBizRequestGenerator.a(this.mContext, TmpConstant.GROUP_OP_ADD, str, i2, i3, i4, i5, iActionListener));
            return;
        }
        final C0212a c0212aA = SIGMeshBizRequest.a(TmpConstant.GROUP_OP_ADD, str, i2, i3, i4, i5);
        c0212aA.a(iActionListener);
        boolean z2 = this.mConfigTaskQueue.size() == 0 && isConnectedToMesh();
        if (!isConnectedToMesh()) {
            c0212aA.a(5000, new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.22
                @Override // java.lang.Runnable
                public void run() {
                    TgMeshManager.this.mConfigTaskQueue.remove(c0212aA);
                }
            });
        }
        this.mConfigTaskQueue.add(c0212aA);
        if (z2) {
            nextRequest();
        }
    }

    public void configModelSubscriptionDelete(String str, int i2, int i3, int i4, int i5, IActionListener<Boolean> iActionListener) {
        if (a.a.a.a.b.d.a.f1335b) {
            offerBizRequest(SIGMeshBizRequestGenerator.a(this.mContext, RequestParameters.SUBRESOURCE_DELETE, str, i2, i3, i4, i5, iActionListener));
            return;
        }
        final C0212a c0212aA = SIGMeshBizRequest.a(RequestParameters.SUBRESOURCE_DELETE, str, i2, i3, i4, i5);
        c0212aA.a(iActionListener);
        boolean z2 = this.mConfigTaskQueue.size() == 0 && isConnectedToMesh();
        this.mConfigTaskQueue.add(c0212aA);
        if (!isConnectedToMesh()) {
            c0212aA.a(5000, new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.23
                @Override // java.lang.Runnable
                public void run() {
                    TgMeshManager.this.mConfigTaskQueue.remove(c0212aA);
                }
            });
        }
        if (z2) {
            nextRequest();
        }
    }

    public void configuration(Map<String, ConfigurationData> map, ConfigActionListener<Boolean> configActionListener) {
        char c2;
        byte[] bArrArray;
        int i2 = 8;
        char c3 = 1;
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<String, ConfigurationData> entry : map.entrySet()) {
            String key = entry.getKey();
            String str = TAG;
            a.a.a.a.b.m.a.c(str, "Configuration for device: " + key);
            ConfigurationData value = entry.getValue();
            String deviceKey = value.getDeviceKey();
            int iIntValue = ((Integer) value.getPrimaryUnicastAddress()).intValue();
            Sigmesh.Device device = value.getDevice();
            Sigmesh.Action action = value.getAction();
            if (action == null || device == null) {
                ConfigResultMap configResultMap = value.getConfigResultMap();
                if (configResultMap != null) {
                    List<SubscribeGroupAddr> subscribeGroupAddr = configResultMap.getSubscribeGroupAddr();
                    if (subscribeGroupAddr == null || subscribeGroupAddr.size() == 0) {
                        subscribeGroupAddr = configResultMap.getConfigModelSubscription();
                    }
                    if (subscribeGroupAddr == null || subscribeGroupAddr.size() == 0) {
                        c2 = c3;
                        if (configActionListener != null) {
                            configActionListener.onSuccess(key, Boolean.TRUE);
                        }
                        a.a.a.a.b.m.a.d(str, "No subscribe group address task, return");
                    } else {
                        SubscribeGroupAddr subscribeGroupAddr2 = subscribeGroupAddr.get(0);
                        int iIntValue2 = subscribeGroupAddr2.getModelId().intValue();
                        byte[] unicastAddressBytes = AddressUtils.getUnicastAddressBytes(subscribeGroupAddr2.getModelElementAddr().intValue());
                        byte[] unicastAddressBytes2 = AddressUtils.getUnicastAddressBytes(subscribeGroupAddr2.getGroupAddr().intValue());
                        if (iIntValue2 < -32768 || iIntValue2 > 32767) {
                            ByteBuffer byteBufferOrder = ByteBuffer.allocate(i2).order(ByteOrder.LITTLE_ENDIAN);
                            byteBufferOrder.put(unicastAddressBytes[c3]);
                            byteBufferOrder.put(unicastAddressBytes[0]);
                            byteBufferOrder.put(unicastAddressBytes2[c3]);
                            byteBufferOrder.put(unicastAddressBytes2[0]);
                            byte[] bArr = {(byte) ((iIntValue2 >> 24) & 255), (byte) ((iIntValue2 >> 16) & 255), (byte) ((iIntValue2 >> 8) & 255), (byte) (iIntValue2 & 255)};
                            byteBufferOrder.put(bArr[1]);
                            byteBufferOrder.put(bArr[0]);
                            byteBufferOrder.put(bArr[3]);
                            byteBufferOrder.put(bArr[2]);
                            bArrArray = byteBufferOrder.array();
                        } else {
                            ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
                            byteBufferOrder2.put(unicastAddressBytes[c3]);
                            byteBufferOrder2.put(unicastAddressBytes[0]);
                            byteBufferOrder2.put(unicastAddressBytes2[c3]);
                            byteBufferOrder2.put(unicastAddressBytes2[0]);
                            byteBufferOrder2.putShort((short) iIntValue2);
                            bArrArray = byteBufferOrder2.array();
                        }
                        Sigmesh sigmesh = new Sigmesh();
                        Sigmesh.Device device2 = new Sigmesh.Device();
                        device2.setDeviceKey(deviceKey);
                        device2.setDestAddr(iIntValue);
                        device2.setAppKeyIndex(0);
                        device2.setNetKeyIndex(0);
                        device2.setTtl(5);
                        Sigmesh.Action action2 = new Sigmesh.Action();
                        action2.setOpcode(Integer.toHexString(subscribeGroupAddr2.getOpcode().intValue()));
                        action2.setStatusOpcode(Integer.toHexString(subscribeGroupAddr2.getStatusOpcode().intValue()));
                        action2.setParameters(MeshParserUtils.bytesToHex(bArrArray, false));
                        c2 = 1;
                        a.a.a.a.b.m.a.a(str, String.format("Configuration for device(%s,%d) with action:[%s,%s,%s]", key, Integer.valueOf(device2.getDestAddr()), action2.getOpcode(), action2.getStatusOpcode(), action2.getParameters()));
                        sigmesh.setDevice(device2);
                        sigmesh.setAction(action2);
                        sigmesh.setDevId(key);
                        linkedList.add(sigmesh);
                    }
                    c3 = c2;
                    i2 = 8;
                } else if (configActionListener != null) {
                    configActionListener.onFailure(key, -32, "configurationData not be empty");
                }
            } else {
                if (!TextUtils.isEmpty(deviceKey)) {
                    device.setDeviceKey(deviceKey);
                }
                Sigmesh sigmesh2 = new Sigmesh();
                sigmesh2.setDevice(device);
                sigmesh2.setAction(action);
                sigmesh2.setDevId(key);
                linkedList.add(sigmesh2);
            }
        }
        if (linkedList.size() > 0) {
            configModelSubscription(linkedList, configActionListener);
        }
    }

    public void connect(final ExtendedBluetoothDevice extendedBluetoothDevice, final boolean z2) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Connect...");
        if (extendedBluetoothDevice == null) {
            publishStatus(-7, "DeviceId is null");
            a.a.a.a.b.m.a.b(str, "DeviceId is null");
            return;
        }
        if (extendedBluetoothDevice.getScanRecord() != null) {
            byte[] serviceData = extendedBluetoothDevice.getScanRecord().getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
            if (serviceData != null && serviceData.length >= 16) {
                int i2 = (serviceData[3] & 255) + ((serviceData[4] & 255) << 8) + ((serviceData[5] & 255) << 16) + ((serviceData[6] & 255) << 24);
                this.productKey = String.valueOf(i2);
                a.a.a.a.b.m.a.c(str, "productKey = " + this.productKey);
                b.a("ALSMesh", "ble", String.valueOf(i2), false, serviceData, "");
            } else if (serviceData == null) {
                a.a.a.a.b.m.a.b(str, "service data is null");
            } else {
                a.a.a.a.b.m.a.c(str, "service data length = " + serviceData.length);
            }
        } else {
            a.a.a.a.b.m.a.c(str, "scanRecord is null");
        }
        if (checkEnv() != 0) {
            return;
        }
        MeshService.b bVar = this.mBinder;
        boolean z3 = bVar != null && bVar.d() == 1;
        this.isConnecting = z3;
        if (z3) {
            a.a.a.a.b.m.a.a(str, "in connecting");
            publishStatus(8, "connecting...");
            return;
        }
        MeshService.b bVar2 = this.mBinder;
        if (bVar2 != null) {
            if (Constants.f8774a) {
                bVar2.a(extendedBluetoothDevice, z2);
                this.isConnecting = true;
            } else {
                bVar2.a(new MeshService.OnDisconnectListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.5
                    @Override // com.alibaba.ailabs.iot.mesh.MeshService.OnDisconnectListener
                    public void onDisconnected() {
                        a.a.a.a.b.m.a.a(TgMeshManager.TAG, "onDisconnected, start connect device");
                        if (TgMeshManager.this.mBinder == null || TgMeshManager.this.isConnecting) {
                            return;
                        }
                        TgMeshManager.this.mBinder.a(extendedBluetoothDevice, z2);
                        TgMeshManager.this.isConnecting = true;
                    }
                });
            }
        }
        a.a.a.a.b.m.a.a(str, "Connect to device: " + extendedBluetoothDevice.getName());
    }

    public void disconnect() {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Disconnect..");
        if (checkEnv() != 0) {
            return;
        }
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.a(new MeshService.OnDisconnectListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.7
                @Override // com.alibaba.ailabs.iot.mesh.MeshService.OnDisconnectListener
                public void onDisconnected() {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "OnDisconnected");
                }
            });
            a.a.a.a.b.m.a.a(str, "prepare disconnect to device");
        }
        this.isConnecting = false;
    }

    public <S extends T, T> S getBizService(Class<T> cls) {
        return (S) d.b.a().a(cls);
    }

    public BaseMeshNode getMeshNode() {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            return bVar.f();
        }
        return null;
    }

    public int getNetkeyIndex() {
        MeshService.b bVar = this.mBinder;
        if (bVar == null) {
            return 0;
        }
        bVar.g();
        return 0;
    }

    public String getProxyNodeAddress() {
        u.a aVarD = G.a().d().d();
        if (aVarD == null || aVarD.e() == null || aVarD.e().h() == null) {
            return null;
        }
        return aVarD.e().h().getAddress();
    }

    public List<String> getProxyNodeAddresses() {
        LinkedList linkedList = new LinkedList();
        u.a aVarD = G.a().d().d();
        if (aVarD != null && aVarD.e() != null && aVarD.e().i() != null) {
            Iterator<BluetoothDevice> it = aVarD.e().i().iterator();
            while (it.hasNext()) {
                linkedList.add(it.next().getAddress());
            }
        }
        return linkedList;
    }

    public synchronized TgMeshManager init(Context context, AuthInfoListener authInfoListener) {
        try {
            String str = TAG;
            a.a.a.a.b.m.a.a(str, "init, " + hashCode() + ", isInitialized: " + this.isInitialized + ", isBound: " + this.isBound);
            if (!this.isEnvInitialized) {
                if (context != null && authInfoListener != null) {
                    this.mContext = context.getApplicationContext();
                    this.isBluetoothEnabled = Utils.isBleEnabled();
                    this.isLocationEnabled = Utils.isLocationEnabled(context);
                    na.a().a(authInfoListener, a.a.a.a.b.d.a.f1334a ? new DefaultMeshConfig() : new MeshNetworkBizOverUnifiedConnectChannel());
                    SequenceNumber.getInstance().init(this.mContext, na.a().b());
                    LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.mBroadcastReceiver, Utils.createIntentFilters());
                    this.isEnvInitialized = true;
                }
                a.a.a.a.b.m.a.d(str, "Context is null or authInfoListener is null...");
                return this;
            }
            if (!this.isBound) {
                try {
                    Intent intent = new Intent(this.mContext, (Class<?>) MeshService.class);
                    if (this.mServiceConnection == null) {
                        this.mServiceConnection = new MeshConnection();
                    }
                    boolean zBindService = this.mContext.bindService(intent, this.mServiceConnection, 1);
                    StringBuilder sb = new StringBuilder();
                    sb.append("bindService... ");
                    sb.append(zBindService);
                    a.a.a.a.b.m.a.a(str, sb.toString());
                } catch (Exception e2) {
                    a.a.a.a.b.m.a.b(TAG, e2.getMessage());
                    e2.printStackTrace();
                }
            }
            a.a.a.a.b.l.c.b();
            return this;
        } catch (Throwable th) {
            throw th;
        }
    }

    public void initIoTMultiend(String str) {
        a.a.a.a.b.m.a.c(TAG, "Start init IoTMultiendInOneSDK with uuid: " + str);
        c cVar = this.multiEndinOneProxy;
        if (cVar == null) {
            this.multiEndinOneProxy = new c(str, na.a());
        } else {
            cVar.a(str);
        }
        IoTMultiendInOneBridge.getInstance().setUpstreamProxy(this.multiEndinOneProxy);
        new Thread(new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.15
            @Override // java.lang.Runnable
            public void run() {
                IoTMultiendInOneBridge.getInstance().init(TgMeshManager.this.mContext);
                if (TgMeshManager.this.isConnected) {
                    IoTMultiendInOneBridge.getInstance().enableHeartbeat(true);
                }
            }
        }).start();
    }

    public void isConnected4JsBridge(final IActionListener<Boolean> iActionListener) {
        a.a.a.a.b.m.a.c(TAG, "isConnected4JsBridge..");
        if (isLowCostDeviceExist()) {
            isGenieBoxOnline(new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.8
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i2, String str) {
                    iActionListener.onSuccess(Boolean.TRUE);
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Boolean bool) {
                    iActionListener.onFailure(-3, "recommend go genie box control");
                }
            });
        } else {
            iActionListener.onFailure(-2, "not low-cost mesh device exist");
        }
    }

    public boolean isConnectedToMesh() {
        if (!this.isInitialized) {
            a.a.a.a.b.m.a.b(TAG, "TgMeshManager has not initialized, " + hashCode());
            return false;
        }
        if (!this.isBound) {
            a.a.a.a.b.m.a.b(TAG, "TgMeshManager has not bond to service, " + hashCode());
            return false;
        }
        this.isBluetoothEnabled = Utils.isBleEnabled();
        this.isLocationEnabled = Utils.isLocationEnabled(this.mContext);
        boolean zIsLocationPermissionsGranted = Utils.isLocationPermissionsGranted(this.mContext);
        if (Build.VERSION.SDK_INT < 33 && !this.isLocationEnabled) {
            a.a.a.a.b.m.a.b(TAG, "Location not enabled, " + hashCode());
            return false;
        }
        if (!this.isBluetoothEnabled) {
            a.a.a.a.b.m.a.b(TAG, "Bluetooth disabled, " + hashCode());
            return false;
        }
        if (zIsLocationPermissionsGranted) {
            u.a aVarD = G.a().d().d();
            return aVarD != null && aVarD.f();
        }
        a.a.a.a.b.m.a.b(TAG, "location permission not granted,  " + hashCode());
        return false;
    }

    public boolean isConnecting() {
        return this.isConnecting;
    }

    public boolean isInitialized() {
        return this.isInitialized && this.isBound;
    }

    public boolean isLowPowerDevice(String str) {
        return MeshDeviceInfoManager.getInstance().isLowPowerDevice(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str));
    }

    public boolean isMeshNodeReachable(String str) {
        String strCoverIotIdToDevId = MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str);
        u.a aVarC = G.a().d().c(strCoverIotIdToDevId);
        boolean z2 = aVarC != null && aVarC.f();
        a.a.a.a.b.m.a.a("TgMeshManager", String.format("%s:%s reachable result: %b", str, strCoverIotIdToDevId, Boolean.valueOf(z2)));
        return z2;
    }

    public void localControlTranslation(final String str, final String str2, final IActionListener<String> iActionListener) {
        a.a.a.a.b.m.a.c(TAG, String.format("localControlTranslation, devId:%s, params:%s", str, str2));
        this.handler.post(new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.16
            @Override // java.lang.Runnable
            public void run() {
                IoTMultiendInOneBridge.getInstance().localControlTranslation(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str), str2, new IResponseCallback() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.16.1
                    @Override // com.alibaba.android.multiendinonebridge.IResponseCallback
                    public void onResponse(int i2, String str3) {
                        a.a.a.a.b.m.a.a(TgMeshManager.TAG, "localControlTranslation code: " + i2 + ", data: " + str3);
                        if (i2 != 0) {
                            Utils.notifyFailed(iActionListener, -26, "local control failed");
                        } else if (JSON.parseObject(str3).getInteger("code").intValue() == 200) {
                            Utils.notifySuccess((IActionListener<String>) iActionListener, str3);
                        } else {
                            Utils.notifyFailed(iActionListener, -27, "The script does not support translation");
                        }
                    }
                });
            }
        });
    }

    public synchronized void notifyMeshMessage(final byte[] bArr, final String str, final byte[] bArr2, final byte[] bArr3, final int i2, final String str2) {
        String strBytes2HexString = ConvertUtils.bytes2HexString(bArr2);
        short s2 = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN).getShort();
        SigmeshIotDev sigmeshIotDevQueryDeviceInfoByUnicastAddress = MeshDeviceInfoManager.getInstance().queryDeviceInfoByUnicastAddress(s2, bArr3);
        if (sigmeshIotDevQueryDeviceInfoByUnicastAddress != null) {
            a.a.a.a.b.m.a.c(TAG, String.format("localStateTranslation, devId:%s, opCode:%s, params:%s, receivedConfigCount:%d, seq:%s", sigmeshIotDevQueryDeviceInfoByUnicastAddress.getDevId(), str, strBytes2HexString, Byte.valueOf(this.mReceivedConfigCount), Integer.valueOf(i2)));
            IoTMultiendInOneBridge.getInstance().localStateTranslation(sigmeshIotDevQueryDeviceInfoByUnicastAddress.getDevId(), str, strBytes2HexString, new IResponseCallback() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.2
                @Override // com.alibaba.android.multiendinonebridge.IResponseCallback
                public void onResponse(int i3, String str3) {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "localStateTranslation code: " + i3 + ", data: " + str3);
                    TgMeshManager.this.notifyCallback(i3, str3, str, bArr2, bArr, bArr3, i2, str2);
                }
            });
            return;
        }
        a.a.a.a.b.m.a.d(TAG, "Failed to get device based on uniCast address: " + ((int) s2));
        notifyCallback(-1, null, str, bArr2, bArr, bArr3, i2, str2);
    }

    public void offerBizRequest(SIGMeshBizRequest sIGMeshBizRequest) {
        if (this.mBinder != null) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(sIGMeshBizRequest);
            this.mBinder.b(linkedList);
        }
    }

    public DevOnlineStatus queryDeviceOnlineStatus(String str) {
        u.a aVarC = G.a().d().c(str);
        boolean z2 = aVarC != null && aVarC.f();
        DevOnlineStatus devOnlineStatusFromIntValue = DevOnlineStatus.fromIntValue(IoTMultiendInOneBridge.getInstance().queryDevOnlineStatus(str));
        return (z2 || devOnlineStatusFromIntValue != DevOnlineStatus.DEV_ST_ONLINE) ? devOnlineStatusFromIntValue : DevOnlineStatus.DEV_ST_OFFLINE;
    }

    public synchronized TgMeshManager refresh() {
        a.a.a.a.b.m.a.a(TAG, "refresh...");
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.j();
            this.isConnecting = true;
        }
        return this;
    }

    public void refreshSIGMeshNodeList() {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.c();
        }
    }

    public TgMeshManager registerCallback(MeshStatusCallback meshStatusCallback) {
        a.a.a.a.b.m.a.a(TAG, "registerCallback...");
        if (meshStatusCallback != null && !this.mMeshStatusCallbacks.contains(meshStatusCallback)) {
            this.mMeshStatusCallbacks.add(meshStatusCallback);
        }
        return this;
    }

    public void registerDeviceOnlineStatusListener(DeviceOnlineStatusListener deviceOnlineStatusListener) {
        c cVar = this.multiEndinOneProxy;
        if (cVar == null) {
            a.a.a.a.b.m.a.b(TAG, "Internal error: multiEndinOneProxy is null");
        } else {
            cVar.a(deviceOnlineStatusListener);
        }
    }

    public void registerFastProvisionMeshScanStrategy() {
    }

    public synchronized void registerMeshMessageListener(MeshMsgListener meshMsgListener) {
        if (!this.meshMsgListenerList.contains(meshMsgListener)) {
            this.meshMsgListenerList.add(meshMsgListener);
        }
    }

    public void removeNodeAfterUnbind(String str, int i2, int i3) {
        String str2 = TAG;
        a.a.a.a.b.m.a.c(str2, "removeNodeAfterUnbind: deviceKey = " + str + ", netKeyIndex = " + i2 + ", primaryUnicastAddress = " + i3);
        if (TextUtils.isEmpty(str)) {
            publishStatus(-1, "DeviceId is not empty or productKey is empty");
            a.a.a.a.b.m.a.b(str2, "ProductKey is empty");
        } else {
            if (checkEnv() != 0) {
                return;
            }
            offerBizRequest(SIGMeshBizRequestGenerator.a(null, null, str, i2, i3, new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.10
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i4, String str3) {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "onFailure() called with: errorCode = [" + i4 + "], desc = [" + str3 + "]");
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Boolean bool) {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "onSuccess() called with: result = [" + bool + "]");
                }
            }));
            a.a.a.a.b.m.a.a(str2, "removeNodeAfterUnbind");
        }
    }

    public void resetIoTMultiendInOneBridge() {
        a.a.a.a.b.m.a.c(TAG, "Reset IoTMultiendInOneBridge");
        IoTMultiendInOneBridge.getInstance().reset();
    }

    public void sendGroupMessage(String str, int i2, String str2, String str3, Map<String, Object> map, IActionListener<Boolean> iActionListener) {
    }

    public void sendMessge(int i2, int i3, int i4, int i5, byte[] bArr, final IActionListener iActionListener) throws InterruptedException {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "SendMessage, opcode(" + i5 + "), parameters(" + MeshParserUtils.bytesToHex(bArr, false));
        if (i2 == 65535) {
            a.a.a.a.b.m.a.d(str, "The application cannot send messages to this multicast address: 0xffff");
            return;
        }
        if (i5 != 33281) {
            a.a.a.a.b.e.a.a();
        }
        final UtTraceInfo utTraceInfoA = f.a().a(i2);
        a.a.a.a.b.l.c.a("SDKReceiveCmd", utTraceInfoA);
        int iCheckEnv = checkEnv();
        if (iCheckEnv != 0) {
            a.a.a.a.b.l.c.a(utTraceInfoA, iCheckEnv, "");
            iActionListener.onFailure(-22, "Unavailable");
            return;
        }
        if (G.a().d().d() == null) {
            a.a.a.a.b.l.c.a(utTraceInfoA, 20003, "Subnets are not initialized");
            Utils.notifyFailed(iActionListener, -25, "Subnets are not initialized");
            return;
        }
        if (!MeshDeviceInfoManager.getInstance().isLowPowerDevice(i2)) {
            MeshService.b bVar = this.mBinder;
            if (bVar == null) {
                a.a.a.a.b.l.c.a(utTraceInfoA, 20012, "mesh network not connected");
                iActionListener.onFailure(-24, "binder is null");
                return;
            }
            bVar.a(i4, i3, i2, i5, bArr, new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.14
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i6, String str2) {
                    Utils.notifyFailed(iActionListener, i6, str2);
                    if (i6 == -30) {
                        a.a.a.a.b.l.c.a(utTraceInfoA, 20003, "Subnets are not initialized");
                    } else {
                        if (i6 != -23) {
                            return;
                        }
                        UtTraceInfo utTraceInfo = utTraceInfoA;
                        UtError utError = UtError.MESH_NETWORK_NOT_CONNECT;
                        a.a.a.a.b.l.c.a(utTraceInfo, utError.getCode(), utError.getMsg());
                    }
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Boolean bool) {
                    Utils.notifySuccess((IActionListener<Boolean>) iActionListener, bool);
                }
            });
            a.a.a.a.b.m.a.a(str, "sendMessage: " + i2);
            return;
        }
        a.a.a.a.b.m.a.a(str, "lowerPower sendMessage");
        MeshControlDevice meshControlDevice = new MeshControlDevice();
        meshControlDevice.d(i2);
        meshControlDevice.a(i3);
        meshControlDevice.b(i4);
        meshControlDevice.c(i5);
        meshControlDevice.a(bArr);
        meshControlDevice.a(iActionListener);
        meshControlDevice.a(false);
        this.mControlTask.a(new a.a.a.a.b.j.b(this.mBinder, meshControlDevice));
    }

    public void sendMsg2LowCostMeshDevice(int i2, int i3, int i4, int i5, byte[] bArr, IActionListener iActionListener) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "sendMsg2LowCostMeshDevice, opcode(" + i5 + "), parameters(" + MeshParserUtils.bytesToHex(bArr, false));
        UtTraceInfo utTraceInfoA = f.a().a(i2);
        a.a.a.a.b.l.c.a("SDKReceiveCmd", utTraceInfoA);
        int iCheckEnv = checkEnv();
        if (iCheckEnv != 0) {
            a.a.a.a.b.l.c.a(utTraceInfoA, iCheckEnv, "");
            iActionListener.onFailure(-22, "Unavailable");
            return;
        }
        if (this.mBinder == null) {
            a.a.a.a.b.l.c.a(utTraceInfoA, 20012, "mesh network not connected");
            iActionListener.onFailure(-24, "binder is null");
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("mesh_device_type", Integer.valueOf(Constants.AliMeshSolutionType.TINY_MESH_ADV_V1.getSolutionType()));
        this.mBinder.a(i4, i3, i2, i5, bArr, linkedHashMap, (IActionListener<Boolean>) iActionListener);
        a.a.a.a.b.m.a.a(str, "SendMessage: " + i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setGatewayAccsMsg(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = com.alibaba.ailabs.iot.mesh.TgMeshManager.TAG
            r1 = 1
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r9
            java.lang.String r4 = "Received config msg via accs, value: %s"
            java.lang.String r2 = java.lang.String.format(r4, r2)
            a.a.a.a.b.m.a.c(r0, r2)
            boolean r2 = android.text.TextUtils.isEmpty(r9)
            if (r2 == 0) goto L18
            return
        L18:
            java.lang.Object r2 = com.alibaba.fastjson.JSON.parse(r9)
            com.alibaba.fastjson.JSONObject r2 = (com.alibaba.fastjson.JSONObject) r2
            java.lang.String r4 = "model"
            com.alibaba.fastjson.JSONObject r4 = r2.getJSONObject(r4)
            if (r4 == 0) goto L27
            r2 = r4
        L27:
            java.lang.String r4 = "commandName"
            java.lang.String r4 = r2.getString(r4)
            java.lang.String r5 = "IotDeviceInfoSync"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto Lcc
            java.lang.String r9 = "payload"
            com.alibaba.fastjson.JSONObject r4 = r2.getJSONObject(r9)
            if (r4 != 0) goto L43
            java.lang.String r9 = "Illegal data, empty payload"
            a.a.a.a.b.m.a.d(r0, r9)
            return
        L43:
            java.lang.String r5 = "messageType"
            java.lang.String r5 = r4.getString(r5)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "messageType: "
            r6.append(r7)
            r6.append(r5)
            java.lang.String r6 = r6.toString()
            a.a.a.a.b.m.a.a(r0, r6)
            java.lang.String r6 = "productTSL"
            boolean r6 = r6.equals(r5)
            if (r6 == 0) goto L8c
            com.alibaba.fastjson.JSONObject r9 = r2.getJSONObject(r9)
            java.lang.String r0 = "data"
            com.alibaba.fastjson.JSONArray r9 = r9.getJSONArray(r0)
        L6f:
            int r0 = r9.size()
            if (r3 >= r0) goto Lc1
            com.alibaba.fastjson.JSONObject r0 = r9.getJSONObject(r3)
            java.lang.String r4 = "url"
            java.lang.String r5 = r0.getString(r4)
            java.lang.String r6 = "(?i)^https://"
            java.lang.String r7 = "http://"
            java.lang.String r5 = r5.replaceFirst(r6, r7)
            r0.put(r4, r5)
            int r3 = r3 + r1
            goto L6f
        L8c:
            java.lang.String r9 = "deviceBind"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto Lc1
            java.lang.String r9 = "Parse device bind list..."
            a.a.a.a.b.m.a.a(r0, r9)
            java.lang.Class<datasource.bean.local.DeviceBindModel> r9 = datasource.bean.local.DeviceBindModel.class
            java.lang.Object r9 = r4.toJavaObject(r9)
            datasource.bean.local.DeviceBindModel r9 = (datasource.bean.local.DeviceBindModel) r9
            com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager r0 = com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager.getInstance()
            r0.parseDeviceBindList(r9)
            boolean r9 = a.a.a.a.b.d.a.f1334a
            if (r9 != 0) goto Lc1
            java.lang.Class<datasource.bean.local.DeviceModel> r9 = datasource.bean.local.DeviceModel.class
            java.lang.Object r9 = r2.toJavaObject(r9)
            datasource.bean.local.DeviceModel r9 = (datasource.bean.local.DeviceModel) r9
            com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager r0 = com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager.getInstance()
            datasource.bean.local.DeviceConverModel r9 = r0.coverDeviceBind(r9)
            java.lang.String r9 = com.alibaba.fastjson.JSON.toJSONString(r9)
            goto Lc2
        Lc1:
            r9 = 0
        Lc2:
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            if (r0 == 0) goto Lcc
            java.lang.String r9 = r2.toJSONString()
        Lcc:
            com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge r0 = com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge.getInstance()
            r0.onRecvTextMsg(r9)
            byte r9 = r8.mReceivedConfigCount
            int r9 = r9 + r1
            byte r9 = (byte) r9
            r8.mReceivedConfigCount = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.TgMeshManager.setGatewayAccsMsg(java.lang.String):void");
    }

    public void setMacAddressWhiteList(List<String> list) {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.c(list);
        }
    }

    public void setMeshDeviceInfoList(List<MeshDeviceInfo> list) {
        a.a.a.a.b.m.a.c(TAG, "setMeshDeviceInfoList");
        MeshDeviceInfoManager.getInstance().setMeshDeviceInfo(list);
    }

    public void setOnReadyToBindHandler(OnReadyToBindHandler onReadyToBindHandler) {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.a(onReadyToBindHandler);
        } else {
            this.mPendingReadyToBindHandler = onReadyToBindHandler;
        }
    }

    public void setTraceInfo(UtTraceInfo utTraceInfo) {
        f.a().a(utTraceInfo);
    }

    public synchronized void stop(Context context) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onStop...");
        if (context == null) {
            a.a.a.a.b.m.a.d(str, "Context is null...");
            return;
        }
        if (this.isBound) {
            this.mBinder.b();
            try {
                Context context2 = this.mContext;
                if (context2 != null) {
                    context2.unbindService(this.mServiceConnection);
                }
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
            this.mBinder = null;
            this.isBound = false;
        }
        context.stopService(new Intent(context, (Class<?>) MeshService.class));
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this.mBroadcastReceiver);
        this.mMeshStatusCallbacks.clear();
        this.isConnecting = false;
        this.isConnected = false;
        this.connectFail = UtError.MESH_INIT_FAILED_STOP.getMsg();
        this.isEnvInitialized = false;
        this.isInitialized = false;
        this.isEnvInitialized = false;
        this.mServiceConnection = null;
        a.a.a.a.b.m.a.c(TAG, "set isInitialized" + this.isInitialized);
    }

    public void stopAddNode() {
        BLEScannerProxy.getInstance().unlock();
        G.a().d().h();
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.l();
        }
        try {
            ExecutorService executorService = this.fixedThreadPool;
            if (executorService != null && !executorService.isTerminated()) {
                this.fixedThreadPool.shutdownNow();
                this.fixedThreadPool = null;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        G.a().d().f();
    }

    public void unbind(String str, String str2, boolean z2) {
        String str3 = TAG;
        a.a.a.a.b.m.a.a(str3, "Unbind..");
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            publishStatus(-1, "DeviceId is not empty or productKey is empty");
            a.a.a.a.b.m.a.b(str3, "DeviceId is not empty or productKey is empty");
        } else {
            if (checkEnv() != 0) {
                return;
            }
            if (!this.isConnected) {
                publishStatus(-2, "disconnected...");
                return;
            }
            MeshService.b bVar = this.mBinder;
            if (bVar != null) {
                bVar.a(str, str2, z2);
                a.a.a.a.b.m.a.a(str3, "Unbind to device");
            }
        }
    }

    public void unregisterCallback(MeshStatusCallback meshStatusCallback) {
        if (meshStatusCallback != null) {
            this.mMeshStatusCallbacks.remove(meshStatusCallback);
        }
    }

    public void unregisterDeviceOnlineStatusListener(DeviceOnlineStatusListener deviceOnlineStatusListener) {
        c cVar = this.multiEndinOneProxy;
        if (cVar == null) {
            a.a.a.a.b.m.a.b(TAG, "Internal error: multiEndinOneProxy is null");
        } else if (deviceOnlineStatusListener != null) {
            cVar.b(deviceOnlineStatusListener);
        }
    }

    public synchronized void unregisterMeshMessageListener(MeshMsgListener meshMsgListener) {
        List<MeshMsgListener> list = this.meshMsgListenerList;
        if (list != null) {
            list.remove(meshMsgListener);
        }
    }

    public void updateMeshNetworkParameters(boolean z2) {
        if (!z2 || SIGMeshBizRequest.NetworkParameter.changeNetworkParameter(this.mNetworkParameter, false)) {
            this.mNetworkParameter = SIGMeshBizRequest.NetworkParameter.getNetworkParameter(MeshDeviceInfoManager.getInstance().getSigmeshIotDevMap().size());
            C0226d.a().b();
        }
    }

    public void wakeUpDevice(final String str, final IActionListener<Boolean> iActionListener) {
        a.a.a.a.b.m.a.a(TAG, "wakeUpDevice");
        isConnected4JsBridge(new IActionListener<Boolean>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.11
            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onFailure(int i2, String str2) {
                TgMeshManager.this.execWakeUpDevice(str, true, iActionListener);
            }

            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onSuccess(Boolean bool) {
                TgMeshManager.this.execWakeUpDevice(str, false, iActionListener);
            }
        });
    }

    public void wifiConfig(String str, Map<String, Object> map, IActionListener iActionListener) {
        a.a.a.a.b.m.a.c(TAG, String.format("config wifi info over mesh channel, devId [%s]", str));
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.a(str, map, iActionListener);
        } else {
            Utils.notifyFailed(iActionListener, -24, "mesh binder is null");
        }
    }

    public TgMeshManager() {
        this.productKey = "";
        this.isEnvInitialized = false;
        this.handler = new Handler(Looper.getMainLooper());
        this.mControlTask = new a(Looper.getMainLooper());
        this.mPendingReadyToBindHandler = null;
        this.fixedThreadPool = null;
        this.mSingleThreadExecutor = null;
        this.mPendingProvisionDeviceAddress = new LinkedList();
        this.mReceivedConfigCount = (byte) 0;
        this.translateControlCommandByCloud = new AtomicBoolean(false);
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:9:0x0021  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onReceive(android.content.Context r9, android.content.Intent r10) {
                /*
                    Method dump skipped, instructions count: 1000
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.TgMeshManager.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        this.provisionFinishedCounter = 0;
        this.mConfigTaskQueue = new LinkedList();
        this.mInConfigProgress = new AtomicBoolean(false);
        this.mExtendedMeshNode = new ExtendedMeshNode(new UnprovisionedMeshNode());
        this.mMeshStatusCallbacks = new ArrayList();
        this.meshMsgListenerList = new CopyOnWriteArrayList();
        this.multiEndinOneProxy = new c(na.a());
        initBizServiceModule();
    }

    public void batchBindDevices(List<UnprovisionedBluetoothMeshDevice> list, Map<String, Object> map) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "batchBindDevices() called with: devicesList.size = [" + list.size() + "]");
        a.a.a.a.b.m.a.c(str, "Build.VERSION.RELEASE: " + Build.VERSION.RELEASE + ", Build.MODEL: " + Build.MODEL + ", Build.BOARD: " + Build.BOARD);
        if (list.isEmpty()) {
            publishStatus(-7, "DeviceId is null");
            a.a.a.a.b.m.a.b(str, "DeviceId is null");
            return;
        }
        BLEScannerProxy.getInstance().lock();
        u uVarD = G.a().d();
        G.a().d().e();
        uVarD.a(false, new MeshService.OnDisconnectListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.4
            @Override // com.alibaba.ailabs.iot.mesh.MeshService.OnDisconnectListener
            public void onDisconnected() {
            }
        });
        uVarD.e();
        boolean z2 = this.mPendingProvisionDeviceAddress.size() == 0;
        a.a.a.a.b.m.a.c(str, "Fist provision: " + z2);
        Iterator<UnprovisionedBluetoothMeshDevice> it = list.iterator();
        while (it.hasNext()) {
            this.mPendingProvisionDeviceAddress.add(it.next().getBluetoothDevice().getAddress());
        }
        int sigMeshProductID = list.get(0).getSigMeshProductID();
        if (z2) {
            C0224b c0224bB = C0224b.b();
            int iMin = Math.min(4, this.mPendingProvisionDeviceAddress.size());
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < iMin; i2++) {
                arrayList.add(this.mPendingProvisionDeviceAddress.get(i2));
            }
            c0224bB.a(this.mPendingProvisionDeviceAddress);
            c0224bB.a(arrayList, sigMeshProductID);
        }
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            bindDevice(list.get(i3), map);
        }
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.a(list.get(size - 1).getBluetoothDevice().getAddress());
        }
    }

    public void bindDevice(UnprovisionedBluetoothMeshDevice unprovisionedBluetoothMeshDevice, final Map<String, Object> map) {
        ExecutorService executorService;
        if (unprovisionedBluetoothMeshDevice == null) {
            publishStatus(-7, "DeviceId is null");
            a.a.a.a.b.m.a.b(TAG, "DeviceId is null");
            return;
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Start binding device: " + unprovisionedBluetoothMeshDevice.getAddress() + " threadId=" + Thread.currentThread());
        b.a("ALSMesh", "ble", String.valueOf(unprovisionedBluetoothMeshDevice.getSigMeshProductID()), true, unprovisionedBluetoothMeshDevice.getUUID(), "");
        if (checkEnv() != 0) {
            return;
        }
        if (!Constants.f8774a) {
            MeshService.b bVar = this.mBinder;
            boolean z2 = bVar != null && bVar.d() == 1;
            this.isConnecting = z2;
            if (z2) {
                a.a.a.a.b.m.a.a(str, "in connecting");
                publishStatus(8, "connecting...");
                return;
            }
        }
        if (this.mBinder != null) {
            final ExtendedBluetoothDevice extendedBluetoothDevice = new ExtendedBluetoothDevice(unprovisionedBluetoothMeshDevice.getMeshScanResult());
            boolean zIsSupportGatt = unprovisionedBluetoothMeshDevice.isSupportGatt();
            extendedBluetoothDevice.setConfigurationInfo(unprovisionedBluetoothMeshDevice.getmUnprovisionedMeshNodeData().getConfigurationInfo());
            extendedBluetoothDevice.setPk(unprovisionedBluetoothMeshDevice.getPk());
            if (!Constants.f8774a) {
                a.a.a.a.b.m.a.a(str, "Connecting...");
                this.mBinder.a(extendedBluetoothDevice, false, map);
                this.isConnecting = true;
                return;
            }
            if (zIsSupportGatt) {
                if (this.fixedThreadPool == null) {
                    int i2 = 3;
                    int iA = a.a.a.a.b.i.a.a.a(this.mContext, "max_provisioner_number", 3);
                    if (iA <= 3 && iA >= 1) {
                        i2 = iA;
                    }
                    this.fixedThreadPool = Executors.newFixedThreadPool(i2);
                }
                executorService = this.fixedThreadPool;
            } else {
                if (this.mSingleThreadExecutor == null) {
                    this.mSingleThreadExecutor = Executors.newSingleThreadExecutor();
                }
                executorService = this.mSingleThreadExecutor;
            }
            executorService.execute(new Runnable() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        TgMeshManager.this.mBinder.a(extendedBluetoothDevice, false, map);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
            this.isConnecting = true;
            this.mBinder.a(unprovisionedBluetoothMeshDevice.getBluetoothDevice().getAddress());
        }
    }

    public void sendGroupMessage(final String str, final String str2, String str3, Map<String, Object> map, final IActionListener<Boolean> iActionListener) throws NumberFormatException {
        int i2;
        a.a.a.a.b.e.a.a();
        final JSONObject jSONObject = new JSONObject();
        if (a.a.a.a.b.d.a.f1334a) {
            jSONObject.put(RemoteMessageConst.MSGID, (Object) "");
            jSONObject.put("devId", map.get("deviceId"));
            jSONObject.put("familyId", map.get("familyId"));
            HashMap map2 = new HashMap();
            map2.put("pushGenie", ((Integer) map.get("channel")).intValue() > 0 ? "true" : RequestConstant.FALSE);
            jSONObject.put(ShareConstants.MEDIA_EXTENSION, (Object) map2);
        } else if (map.containsKey("iotId")) {
            jSONObject.put("iotId", map.get("iotId"));
        }
        jSONObject.put("method", (Object) str2);
        jSONObject.put("version", (Object) "1.0");
        Map map3 = (Map) JSON.parseObject(str3, Map.class);
        jSONObject.put("params", (Object) map3);
        a.a.a.a.b.m.a.a(TAG, "sendGroupMessage: " + jSONObject.toJSONString());
        try {
            i2 = Integer.parseInt(String.valueOf(map.get("groupAddress")));
        } catch (Exception unused) {
            i2 = 0;
        }
        final int i3 = i2;
        if (i3 == 0) {
            groupControlByServer(str, str2, jSONObject.toJSONString(), iActionListener);
            return;
        }
        if (!"thing.attribute.set".equals(str2)) {
            groupControlByServer(str, str2, jSONObject.toJSONString(), iActionListener);
            return;
        }
        String strCoverIotIdToDevId = MeshDeviceInfoManager.getInstance().coverIotIdToDevId((String) map.get("deviceId"));
        a.a.a.a.b.m.a.a(TAG, "local group control decision making:" + strCoverIotIdToDevId);
        IoTMultiendInOneBridge.getInstance().localControlTranslation(strCoverIotIdToDevId, JSON.toJSONString(map3), new IResponseCallback() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.30
            @Override // com.alibaba.android.multiendinonebridge.IResponseCallback
            public void onResponse(int i4, String str4) {
                a.a.a.a.b.m.a.a(TgMeshManager.TAG, "localGroupControlTranslation code: " + i4 + ", result: " + str4);
                if (i4 == 0 && TgMeshManager.this.mBinder != null) {
                    JSONObject object = JSON.parseObject(str4);
                    if (object.getInteger("code").intValue() == 200) {
                        List javaList = object.getJSONArray("data").toJavaList(Sigmesh.class);
                        if (!ListUtils.isEmpty(javaList) && javaList.get(0) != null) {
                            Sigmesh sigmesh = (Sigmesh) javaList.get(0);
                            if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                                int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                                int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                                if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                                    TgMeshManager.this.mBinder.a(netKeyIndex, appKeyIndex, i3, Utils.byteArray2Int(Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16))), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), iActionListener);
                                    return;
                                }
                            }
                        }
                    }
                }
                TgMeshManager.this.groupControlByServer(str, str2, jSONObject.toJSONString(), iActionListener);
            }
        });
    }

    public void offerBizRequest(List<SIGMeshBizRequest> list) {
        MeshService.b bVar = this.mBinder;
        if (bVar != null) {
            bVar.b(list);
        }
    }

    public void configModelSubscription(Sigmesh.Device device, final Sigmesh.Action action, IActionListener<Boolean> iActionListener) {
        if (device != null && action != null) {
            SIGMeshBizRequest.Type type = SIGMeshBizRequest.Type.COMMON_REQUEST_RESPONSE;
            type.setOpcode(Integer.parseInt(action.getOpcode(), 16));
            type.setExpectedOpcode(Integer.parseInt(action.getStatusOpcode(), 16));
            u.a aVarD = G.a().d().d();
            SIGMeshBizRequest sIGMeshBizRequestA = new SIGMeshBizRequest.a().a(type).a(aVarD != null ? (ProvisionedMeshNode) aVarD.d(AddressUtils.getUnicastAddressBytes(device.getDestAddr())) : null).a(AddressUtils.getUnicastAddressBytes(device.getDestAddr())).a(new I<Object>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.28
                @Override // a.a.a.a.b.a.I
                public Pair<Integer, ?> parseResponse(Object obj) {
                    if (obj instanceof CfgMsgModelSubscriptionStatus) {
                        return CfgMsgModelSubscriptionStatus.parseStatus(TgMeshManager.this.mContext, ((CfgMsgModelSubscriptionStatus) obj).getStatus());
                    }
                    boolean z2 = obj instanceof byte[];
                    return null;
                }
            }).a(true).a(iActionListener).a(new SIGMeshBizRequest.b() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.27
                @Override // com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest.b
                public byte[] getEncodedParameters() {
                    return ConvertUtils.hexString2Bytes(action.getParameters());
                }
            }).a();
            sIGMeshBizRequestA.a(ConvertUtils.hexString2Bytes(device.getDeviceKey()));
            offerBizRequest(sIGMeshBizRequestA);
            return;
        }
        Utils.notifyFailed(iActionListener, -32, "device and action not be empty");
    }

    public void connect(List<Integer> list) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "Connect to the mesh network");
        if (checkEnv() != 0) {
            return;
        }
        MeshService.b bVar = this.mBinder;
        boolean z2 = bVar != null && bVar.d() == 1;
        this.isConnecting = z2;
        if (z2) {
            publishStatus(8, "connecting...");
            return;
        }
        u.a aVarD = G.a().d().d();
        if (aVarD == null) {
            this.isConnected = false;
        } else {
            this.isConnected = aVarD.j();
        }
        if (this.isConnected) {
            publishStatus(2, "connected...");
            return;
        }
        MeshService.b bVar2 = this.mBinder;
        if (bVar2 != null) {
            bVar2.a(list);
            this.isConnecting = true;
        }
        a.a.a.a.b.m.a.a(str, "Reconnect to mesh");
    }

    public void sendMessge(final String str, int i2, String str2, String str3, IActionListener iActionListener) {
        String str4 = TAG;
        a.a.a.a.b.m.a.c(str4, String.format("sendMessageV1, devId:%s, channel:%d, method:%s, params:%s", str, Integer.valueOf(i2), str2, str3));
        final UtTraceInfo utTraceInfoA = f.a().a(str);
        a.a.a.a.b.l.c.a("SDKReceiveCmd", utTraceInfoA);
        final JSONObject jSONObject = new JSONObject();
        jSONObject.put("method", (Object) str2);
        jSONObject.put("devId", (Object) str);
        jSONObject.put(RemoteMessageConst.MSGID, (Object) "");
        jSONObject.put("version", (Object) "1.0");
        Map map = (Map) JSON.parseObject(str3, Map.class);
        if (iActionListener == null) {
            iActionListener = new IActionListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.17
                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onFailure(int i3, String str5) {
                }

                @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
                public void onSuccess(Object obj) {
                }
            };
        }
        final IActionListener iActionListener2 = iActionListener;
        if ("thing.service.invoke".equals(str2)) {
            String str5 = (String) map.get("serviceName");
            if (TextUtils.isEmpty(str5)) {
                iActionListener2.onFailure(-21, "Missing parameter: serviceName");
                return;
            } else {
                map.remove("serviceName");
                jSONObject.put("serviceName", (Object) str5);
            }
        }
        jSONObject.put("params", (Object) map);
        final HashMap map2 = new HashMap();
        map2.put("pushGenie", i2 > 0 ? "true" : RequestConstant.FALSE);
        jSONObject.put(ShareConstants.MEDIA_EXTENSION, (Object) map2);
        if (i2 <= 0 && "thing.attribute.set".equals(str2)) {
            a.a.a.a.b.m.a.a(str4, "local control decision making");
            IoTMultiendInOneBridge.getInstance().localControlTranslation(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str), JSON.toJSONString(map), new IResponseCallback() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.18
                @Override // com.alibaba.android.multiendinonebridge.IResponseCallback
                public void onResponse(int i3, String str6) throws InterruptedException {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "localControlTranslation code: " + i3 + ", result: " + str6);
                    if (i3 == 0) {
                        JSONObject object = JSON.parseObject(str6);
                        if (object.getInteger("code").intValue() == 200) {
                            List javaList = object.getJSONArray("data").toJavaList(Sigmesh.class);
                            if (!ListUtils.isEmpty(javaList) && javaList.get(0) != null) {
                                Sigmesh sigmesh = (Sigmesh) javaList.get(0);
                                if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                                    int destAddr = sigmesh.getDevice().getDestAddr();
                                    int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                                    int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                                    if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                                        TgMeshManager.this.sendMessge(destAddr, appKeyIndex, netKeyIndex, Utils.byteArray2Int(Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16))), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), iActionListener2);
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    if (i3 == -2) {
                        map2.put("pushGenie", "true");
                        jSONObject.put(ShareConstants.MEDIA_EXTENSION, (Object) map2);
                    }
                    TgMeshManager.this.deviceControlByServer(str, jSONObject.toJSONString(), iActionListener2, utTraceInfoA);
                }
            });
        } else {
            na.a().a(str, "3404", jSONObject.toJSONString(), new MeshConfigCallback<List<Sigmesh>>() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.19
                @Override // datasource.MeshConfigCallback
                public void onFailure(String str6, String str7) {
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "errorCode:" + str6 + ", errorMessage:" + str7);
                    UtTraceInfo utTraceInfo = utTraceInfoA;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str6);
                    sb.append(str7);
                    a.a.a.a.b.l.c.a(utTraceInfo, 20005, sb.toString());
                    iActionListener2.onFailure(-20, str7);
                }

                @Override // datasource.MeshConfigCallback
                public void onSuccess(List<Sigmesh> list) throws InterruptedException {
                    if (!ListUtils.isEmpty(list) && list.get(0) != null) {
                        Sigmesh sigmesh = list.get(0);
                        if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                            int destAddr = sigmesh.getDevice().getDestAddr();
                            int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                            int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                            if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                                byte[] opCodeBytes = Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16));
                                TgMeshManager.this.sendMessge(destAddr, appKeyIndex, netKeyIndex, Utils.byteArray2Int(opCodeBytes), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), iActionListener2);
                                return;
                            }
                        }
                    }
                    a.a.a.a.b.l.c.a(utTraceInfoA, 20010, "mtop success while parameter illetal");
                    iActionListener2.onFailure(-21, "Wrong parameter");
                }
            });
        }
    }

    public void connect(ConnectionParams connectionParams) {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("connect to mesh with params: ");
        sb.append(connectionParams == null ? TmpConstant.GROUP_ROLE_UNKNOWN : connectionParams.toString());
        a.a.a.a.b.m.a.a(str, sb.toString());
        if (connectionParams != null) {
            connectionParams.setDeviceId(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(connectionParams.getDeviceId()));
        }
        MeshService.b bVar = this.mBinder;
        if (bVar == null) {
            a.a.a.a.b.m.a.d(str, "MeshService not bind");
        } else {
            bVar.a(connectionParams);
        }
    }

    public void sendMessge(final String str, int i2, String str2, String str3, Map<String, Object> map, final IActionListener iActionListener) {
        String str4 = TAG;
        a.a.a.a.b.m.a.c(str4, String.format("sendMessageV1, devId:%s, channel:%d, method:%s, params:%s", str, Integer.valueOf(i2), str2, str3));
        final UtTraceInfo utTraceInfoA = f.a().a(str);
        a.a.a.a.b.l.c.a("SDKReceiveCmd", utTraceInfoA);
        final JSONObject jSONObject = new JSONObject();
        if (a.a.a.a.b.d.a.f1334a) {
            jSONObject.put("devId", (Object) str);
            jSONObject.put(RemoteMessageConst.MSGID, (Object) "");
            HashMap map2 = new HashMap();
            map2.put("pushGenie", i2 > 0 ? "true" : RequestConstant.FALSE);
            jSONObject.put(ShareConstants.MEDIA_EXTENSION, (Object) map2);
            if (map.containsKey(AlinkConstants.KEY_CATEGORY_KEY) && "Scene".equals((String) map.get(AlinkConstants.KEY_CATEGORY_KEY))) {
                try {
                    offerBizRequest(SIGMeshBizRequestGenerator.a(str, (short) ((Integer) ((Map) JSON.parseObject(str3, Map.class)).values().toArray()[0]).intValue(), (IActionListener<Boolean>) iActionListener));
                    return;
                } catch (Exception e2) {
                    a.a.a.a.b.m.a.b(TAG, e2.toString());
                    return;
                }
            }
        } else {
            if (map.containsKey("iotId")) {
                jSONObject.put("iotId", map.get("iotId"));
            }
            if (map.containsKey("productKey")) {
                jSONObject.put("productKey", map.get("productKey"));
            }
            if (map.containsKey(AlinkConstants.KEY_CATEGORY_KEY) && "Scene".equals((String) map.get(AlinkConstants.KEY_CATEGORY_KEY))) {
                try {
                    offerBizRequest(SIGMeshBizRequestGenerator.a(str, (short) ((Integer) ((Map) JSON.parseObject(str3, Map.class)).values().toArray()[0]).intValue(), (IActionListener<Boolean>) iActionListener));
                    return;
                } catch (Exception e3) {
                    a.a.a.a.b.m.a.b(TAG, e3.toString());
                    return;
                }
            }
        }
        jSONObject.put("method", (Object) str2);
        jSONObject.put("version", (Object) "1.0");
        Map map3 = (Map) JSON.parseObject(str3, Map.class);
        IActionListener iActionListener2 = iActionListener == null ? new IActionListener() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.20
            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onFailure(int i3, String str5) {
            }

            @Override // com.alibaba.ailabs.iot.mesh.callback.IActionListener
            public void onSuccess(Object obj) {
            }
        } : iActionListener;
        if ("thing.service.invoke".equals(str2)) {
            String str5 = (String) map3.get("serviceName");
            if (TextUtils.isEmpty(str5)) {
                iActionListener2.onFailure(-21, "Missing parameter: serviceName");
                return;
            } else {
                map3.remove("serviceName");
                jSONObject.put("serviceName", (Object) str5);
            }
        }
        jSONObject.put("params", (Object) map3);
        if (i2 <= 0 && "thing.attribute.set".equals(str2)) {
            final String strCoverIotIdToDevId = MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str);
            a.a.a.a.b.m.a.a(str4, "local control decision making:" + strCoverIotIdToDevId);
            final IActionListener iActionListener3 = iActionListener2;
            IoTMultiendInOneBridge.getInstance().localControlTranslation(strCoverIotIdToDevId, JSON.toJSONString(map3), new IResponseCallback() { // from class: com.alibaba.ailabs.iot.mesh.TgMeshManager.21
                @Override // com.alibaba.android.multiendinonebridge.IResponseCallback
                public void onResponse(int i3, String str6) {
                    u.a aVarC;
                    a.a.a.a.b.m.a.a(TgMeshManager.TAG, "localControlTranslation code: " + i3 + ", result: " + str6);
                    if (i3 == 0 && TgMeshManager.this.mBinder != null) {
                        JSONObject object = JSON.parseObject(str6);
                        if (object.getInteger("code").intValue() == 200) {
                            List javaList = object.getJSONArray("data").toJavaList(Sigmesh.class);
                            if (!ListUtils.isEmpty(javaList) && javaList.get(0) != null) {
                                Sigmesh sigmesh = (Sigmesh) javaList.get(0);
                                if (sigmesh.getDevice() != null && sigmesh.getAction() != null) {
                                    int destAddr = sigmesh.getDevice().getDestAddr();
                                    int appKeyIndex = sigmesh.getDevice().getAppKeyIndex();
                                    int netKeyIndex = sigmesh.getDevice().getNetKeyIndex();
                                    if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null) {
                                        byte[] opCodeBytes = Utils.getOpCodeBytes(Integer.parseInt(sigmesh.getAction().getOpcode(), 16));
                                        if (a.a.a.a.b.d.a.f1335b && (aVarC = G.a().d().c(strCoverIotIdToDevId)) != null && aVarC.i()) {
                                            TgMeshManager.this.offerBizRequest(SIGMeshBizRequestGenerator.a(destAddr, MeshParserUtils.bytesToHex(opCodeBytes, false), sigmesh.getAction().getParameters(), (IActionListener<Boolean>) iActionListener));
                                            return;
                                        } else {
                                            TgMeshManager.this.mBinder.a(G.a().d().b(strCoverIotIdToDevId), appKeyIndex, netKeyIndex, Utils.byteArray2Int(opCodeBytes), MeshParserUtils.toByteArray(sigmesh.getAction().getParameters()), iActionListener3, false);
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    TgMeshManager.this.deviceControlByServer(str, jSONObject.toJSONString(), iActionListener3, utTraceInfoA);
                }
            });
            return;
        }
        deviceControlByServer(str, jSONObject.toJSONString(), iActionListener2, utTraceInfoA);
    }
}
