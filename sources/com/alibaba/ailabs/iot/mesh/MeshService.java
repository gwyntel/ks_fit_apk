package com.alibaba.ailabs.iot.mesh;

import a.a.a.a.b.G;
import a.a.a.a.b.H;
import a.a.a.a.b.I;
import a.a.a.a.b.L;
import a.a.a.a.b.M;
import a.a.a.a.b.N;
import a.a.a.a.b.O;
import a.a.a.a.b.Q;
import a.a.a.a.b.S;
import a.a.a.a.b.T;
import a.a.a.a.b.U;
import a.a.a.a.b.V;
import a.a.a.a.b.W;
import a.a.a.a.b.X;
import a.a.a.a.b.Y;
import a.a.a.a.b.Z;
import a.a.a.a.b.ca;
import a.a.a.a.b.da;
import a.a.a.a.b.ea;
import a.a.a.a.b.fa;
import a.a.a.a.b.ga;
import a.a.a.a.b.ha;
import a.a.a.a.b.i.C0232a;
import a.a.a.a.b.i.C0233b;
import a.a.a.a.b.i.J;
import a.a.a.a.b.i.P;
import a.a.a.a.b.ia;
import a.a.a.a.b.ja;
import a.a.a.a.b.ka;
import a.a.a.a.b.l.c;
import a.a.a.a.b.la;
import a.a.a.a.b.ma;
import a.a.a.a.b.na;
import a.a.a.a.b.oa;
import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanFilter;
import aisscanner.ScanRecord;
import aisscanner.ScanSettings;
import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.SparseArray;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.media3.exoplayer.ExoPlayer;
import b.C0337l;
import b.InterfaceC0326a;
import b.InterfaceC0338m;
import b.K;
import b.e.i;
import b.p;
import b.q;
import b.s;
import b.u;
import com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker;
import com.alibaba.ailabs.iot.mesh.bean.ConnectionParams;
import com.alibaba.ailabs.iot.mesh.bean.DeviceStatusesPayload;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.bean.MeshAccessPayload;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.callback.MeshMsgListener;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback;
import com.alibaba.ailabs.iot.mesh.ut.UtError;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.ailabs.iot.mesh.utils.Constants;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.android.multiendinonebridge.IoTMultiendInOneBridge;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.aliyun.alink.business.devicecenter.config.genie.smartconfig.constants.DeviceCommonConstants;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.bean.AddPublish;
import datasource.bean.BindModel;
import datasource.bean.ControlProtocol;
import datasource.bean.DeviceStatus;
import datasource.bean.IotDevice;
import datasource.bean.ProvisionAppKey;
import datasource.bean.Sigmesh;
import datasource.bean.SigmeshKey;
import datasource.bean.SubscribeGroupAddr;
import datasource.channel.data.DownStreamControlData;
import datasource.channel.data.DownStreamControlDataForTg;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.MeshModel;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.configuration.SequenceNumber;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.Element;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class MeshService extends Service implements BleMeshManagerCallbacks, p, q, InterfaceC0338m, InterfaceC0326a, FastProvisionConfigCallback, FastProvisionTransportCallback {
    public static final String TAG = "tg_mesh_sdk_" + MeshService.class.getSimpleName();
    public static final int TIME_OUT = 500;
    public ExtendedBluetoothDevice deviceTobeProvision;
    public boolean isInitialized;
    public boolean isRetry;
    public a mAppKeyAddTimeoutTask;
    public List<BindModel> mBindModel;
    public BleMeshManager mBleMeshManager;
    public BluetoothDevice mBluetoothDevice;
    public C0233b mConcurrentProvisionContext;
    public boolean mConnectToMeshNetwork;
    public J mFastProvisionWorker;
    public Handler mHandler;
    public boolean mIsConnected;
    public boolean mIsReconnecting;
    public boolean mIsScanning;
    public C0337l mMeshManagerApi;
    public List<Integer> mModelIds;
    public List<Integer> mNetKeyIndexes;
    public OnDisconnectListener mOnConnectionStateListener;
    public Map<String, Object> mProvisioningExtensionsParams;
    public s mProvisioningSettings;
    public List<AddPublish> mPublishGroupAddrs;
    public ScanRecord mScannerRecord;
    public SparseArray<SigmeshKey> mSigmeshKeys;
    public List<SubscribeGroupAddr> mSubscribeGroupAddrs;
    public UnprovisionedMeshNodeData mUnprovisionedMeshNodeData;
    public boolean mIsProvisioningComplete = false;
    public boolean mIsConfigurationComplete = false;
    public BaseMeshNode mCurrentProvisionMeshNode = null;
    public boolean mDeviceIsReadyInConfigurationStep = false;
    public boolean mDeviceIsReadyInProvisioningStep = false;
    public boolean mProvisionInfoReady = false;
    public ConcurrentLinkedQueue<Integer> mAppKeyIndexQueue = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<String> mAppKeyQueue = new ConcurrentLinkedQueue<>();
    public boolean mShouldAddAppKeyBeAdded = false;
    public boolean mRequestStopAddNode = false;
    public SparseArray<Integer> mConfirmationOpCodeMap = new SparseArray<>();
    public List<String> mMacAddressWhiteList = new ArrayList();
    public SparseArray<Byte> mPreviousMessageTIDRecord = new SparseArray<>();
    public OnReadyToBindHandler mOnReadyToBindHandler = null;
    public b mBinder = new b();
    public boolean USE_MESH_V2 = true;
    public int mAppKeyRetryAddCount = 0;
    public String mCurConfigModelSubAction = "";
    public ArrayList<DeviceProvisioningWorker> deviceProvisioningWorkerArray = null;
    public DeviceProvisioningWorker.OnProvisionFinishedListener mGlobalProvisionFinishedListener = null;
    public LongSparseArray<MeshMsgListener> mMeshNodeSpecifiedMessageListenerArray = new LongSparseArray<>();
    public final ScanCallback scanCallback = new da(this);
    public final Runnable mScannerTimeout = new ea(this);
    public final Runnable mProvisionTimeout = new fa(this);
    public final Runnable mReconnectRunnable = new ga(this);

    public interface OnDisconnectListener {
        void onDisconnected();
    }

    private class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public String f8723a;

        /* renamed from: b, reason: collision with root package name */
        public int f8724b;

        /* renamed from: c, reason: collision with root package name */
        public ProvisionedMeshNode f8725c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f8726d;

        public a(String str, int i2, ProvisionedMeshNode provisionedMeshNode, boolean z2) {
            this.f8723a = str;
            this.f8724b = i2;
            this.f8725c = provisionedMeshNode;
            this.f8726d = z2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (MeshService.this.mAppKeyRetryAddCount >= 2) {
                if (this.f8726d) {
                    MeshService.this.onAppKeyStatusReceived(this.f8725c, true, 0, 0, this.f8724b);
                    return;
                }
                return;
            }
            a.a.a.a.b.m.a.c(MeshService.TAG, "retry to add app key: appKeyIndex = " + this.f8724b + ", mAppKey = " + this.f8723a);
            MeshService.this.mMeshManagerApi.a(this.f8725c, this.f8724b, this.f8723a);
            MeshService meshService = MeshService.this;
            meshService.mAppKeyRetryAddCount = meshService.mAppKeyRetryAddCount + 1;
            MeshService.this.mHandler.postDelayed(this, 500L);
            a.a.a.a.b.m.a.a(MeshService.TAG, "addAppKey");
        }
    }

    public class b extends Binder {
        public b() {
        }

        public void a(String str, Map<String, Object> map, IActionListener iActionListener) {
            if (a.a.a.a.b.d.a.f1335b || a.a.a.a.b.d.a.f1334a) {
                ProvisionedMeshNode provisionedMeshNodeB = G.a().d().b(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str));
                byte[] bArr = {6, -16};
                P p2 = new P(str, map, new ia(this, iActionListener, provisionedMeshNodeB, bArr));
                p2.d();
                if (provisionedMeshNodeB != null) {
                    a(p2.b(), 13936641, bArr, new ja(this, p2));
                    return;
                }
                return;
            }
            MeshService.this.mProvisioningExtensionsParams = map;
            u.a aVarD = G.a().d().d();
            if (aVarD == null) {
                Utils.notifyFailed(iActionListener, -30, "Primary subnets not initialized");
                return;
            }
            K kE = aVarD.e();
            if (kE == null || !aVarD.f()) {
                Utils.notifyFailed(iActionListener, -30, "Primary subnets not initialized");
                return;
            }
            MeshService.this.wifiConfig(kE, G.a().d().b(MeshDeviceInfoManager.getInstance().coverIotIdToDevId(str)), iActionListener);
        }

        public void b() {
            MeshService.this.stopScan();
            MeshService.this.mHandler.removeCallbacks(MeshService.this.mReconnectRunnable);
            MeshService.this.mIsReconnecting = false;
            MeshService.this.mIsProvisioningComplete = false;
            MeshService.this.mIsConfigurationComplete = false;
            MeshService.this.mBleMeshManager.disconnect().enqueue();
            a.a.a.a.b.m.a.a(MeshService.TAG, "disconnect");
        }

        public void c() {
            MeshService.this.fullRefreshProvisionInfo();
        }

        public int d() {
            return MeshService.this.mBleMeshManager.getConnectState();
        }

        public C0337l e() {
            return MeshService.this.mMeshManagerApi;
        }

        public BaseMeshNode f() {
            return MeshService.this.mCurrentProvisionMeshNode;
        }

        public int g() {
            return ((Integer) MeshService.this.mNetKeyIndexes.get(0)).intValue();
        }

        public String h() {
            return MeshService.this.mMeshManagerApi.b(MeshParserUtils.toByteArray(MeshService.this.mProvisioningSettings.h()));
        }

        public Map<Integer, ProvisionedMeshNode> i() {
            return MeshService.this.mMeshManagerApi.c();
        }

        public void j() {
            a.a.a.a.b.m.a.a(MeshService.TAG, "init...");
            MeshService.this.isRetry = false;
            MeshService.this.init();
        }

        public boolean k() {
            return MeshService.this.mConnectToMeshNetwork && MeshService.this.mIsConnected;
        }

        public void l() {
            if (MeshService.this.mConnectToMeshNetwork) {
                return;
            }
            MeshService.this.mHandler.removeCallbacks(MeshService.this.mProvisionTimeout);
            if (MeshService.this.deviceTobeProvision != null && !MeshService.this.mIsProvisioningComplete) {
                MeshService.this.mMeshManagerApi.g();
            }
            MeshService.this.mRequestStopAddNode = true;
            if (MeshService.this.mIsConnected) {
                b();
            }
            if (MeshService.this.deviceProvisioningWorkerArray == null || MeshService.this.deviceProvisioningWorkerArray.isEmpty()) {
                return;
            }
            int size = MeshService.this.deviceProvisioningWorkerArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                DeviceProvisioningWorker deviceProvisioningWorker = (DeviceProvisioningWorker) MeshService.this.deviceProvisioningWorkerArray.get(i2);
                if (deviceProvisioningWorker != null) {
                    deviceProvisioningWorker.r();
                }
            }
            MeshService.this.deviceProvisioningWorkerArray.clear();
        }

        public void c(List<String> list) {
            MeshService.this.mMacAddressWhiteList.addAll(list);
        }

        public void b(List<SIGMeshBizRequest> list) {
            K kE;
            if (list == null || list.size() == 0) {
                return;
            }
            u.a aVarH = list.get(0).h();
            if (aVarH != null && (kE = aVarH.e()) != null) {
                kE.a(list);
                return;
            }
            Iterator<SIGMeshBizRequest> it = list.iterator();
            while (it.hasNext()) {
                Utils.notifyFailed(it.next().m(), -30, "Internal error");
            }
        }

        public void a(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2, Map<String, Object> map) {
            if (!z2) {
                MeshService.this.mProvisioningExtensionsParams = map;
            }
            MeshService.this.connect(extendedBluetoothDevice, z2);
        }

        public void a(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) {
            MeshService.this.connect(extendedBluetoothDevice, z2);
        }

        public void a(String str) {
            MeshService.this.mConcurrentProvisionContext.a(str);
        }

        public void a(ConnectionParams connectionParams) {
            u uVarD = G.a().d();
            uVarD.a(MeshService.this.getApplicationContext(), MeshService.this);
            uVarD.a(connectionParams);
        }

        public void a(List<Integer> list) {
            if (MeshService.this.USE_MESH_V2) {
                MeshService.this.mHandler.post(new ka(this));
                return;
            }
            String str = MeshService.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("Connect via unicast addresses(");
            sb.append(list == null ? "0" : Integer.valueOf(list.size()));
            sb.append(")");
            a.a.a.a.b.m.a.a(str, sb.toString());
            if (!MeshService.this.isInitialized) {
                a.a.a.a.b.m.a.d(MeshService.TAG, "Not initialized");
                return;
            }
            if (list != null) {
                for (Integer num : list) {
                    if (num == null) {
                        a.a.a.a.b.m.a.d(MeshService.TAG, "unicast address is null!");
                    } else if (!MeshService.this.mMeshManagerApi.c().containsKey(num)) {
                        UnprovisionedMeshNode unprovisionedMeshNode = new UnprovisionedMeshNode();
                        unprovisionedMeshNode.setUnicastAddress(new byte[]{(byte) ((num.intValue() >> 8) & 255), (byte) (num.intValue() & 255)});
                        unprovisionedMeshNode.setNetworkKey(MeshParserUtils.toByteArray(MeshService.this.mProvisioningSettings.h()));
                        unprovisionedMeshNode.setKeyIndex(MeshParserUtils.addKeyIndexPadding(Integer.valueOf(MeshService.this.mProvisioningSettings.g())));
                        unprovisionedMeshNode.setIvIndex(ByteBuffer.allocate(4).putInt(MeshService.this.mProvisioningSettings.f()).array());
                        unprovisionedMeshNode.setConfigured(true);
                        unprovisionedMeshNode.setIsProvisioned(true);
                        ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
                        int size = MeshService.this.mProvisioningSettings.c().size();
                        for (int i2 = 0; i2 < size; i2++) {
                            provisionedMeshNode.setAddedAppKey(i2, MeshService.this.mProvisioningSettings.c().get(i2));
                        }
                        MeshService.this.mMeshManagerApi.c().put(num, provisionedMeshNode);
                    }
                }
            }
            MeshService.this.mHandler.post(MeshService.this.mReconnectRunnable);
        }

        public void a(OnDisconnectListener onDisconnectListener) {
            a.a.a.a.b.m.a.c(MeshService.TAG, "Disconnect with callback, current Connect state: " + MeshService.this.mBleMeshManager.getConnectState());
            MeshService.this.stopScan();
            MeshService.this.mHandler.removeCallbacks(MeshService.this.mReconnectRunnable);
            MeshService.this.mIsReconnecting = false;
            MeshService.this.mIsProvisioningComplete = false;
            MeshService.this.mIsConfigurationComplete = false;
            if (MeshService.this.mBleMeshManager.getConnectState() == 2) {
                MeshService.this.mOnConnectionStateListener = onDisconnectListener;
                b();
            }
            if (MeshService.this.USE_MESH_V2) {
                G.a().d().a(true, onDisconnectListener);
                a.a.a.a.b.m.a.a(MeshService.TAG, "disconnect");
            }
        }

        public void a(String str, String str2, boolean z2) {
            a.a.a.a.b.m.a.a(MeshService.TAG, DeviceCommonConstants.VALUE_BOX_UNBIND);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("deviceId", (Object) str);
            jSONObject.put("productKey", (Object) str2);
            jSONObject.put("skillId", (Object) "3404");
            HashMap map = new HashMap();
            map.put("pushGenie", Boolean.valueOf(z2));
            jSONObject.put("params", (Object) map);
        }

        public void a(int i2, int i3, int i4, int i5, byte[] bArr, IActionListener<Boolean> iActionListener) {
            a(i2, i3, i4, i5, bArr, (Map<String, Object>) null, iActionListener);
        }

        public void a(int i2, int i3, int i4, int i5, byte[] bArr, Map<String, Object> map, IActionListener<Boolean> iActionListener) {
            String strBytesToHex;
            u.a aVarD = G.a().d().d();
            if (aVarD == null) {
                Utils.notifyFailed(iActionListener, -30, "Internal error(the target network cannot be found, there is a problem with Mesh initialization logic)");
                return;
            }
            if ((map == null ? 0 : ((Integer) map.get("mesh_device_type")).intValue()) != Constants.AliMeshSolutionType.TINY_MESH_ADV_V1.getSolutionType() && !aVarD.f()) {
                Utils.notifyFailed(iActionListener, -23, "Unreachable");
                return;
            }
            if (!aVarD.j()) {
                u uVarD = G.a().d();
                uVarD.a(MeshService.this.getApplicationContext(), MeshService.this);
                uVarD.a(aVarD, true);
            }
            SparseArray<byte[]> sparseArrayA = aVarD.a();
            if (sparseArrayA.size() > 0) {
                strBytesToHex = MeshParserUtils.bytesToHex(sparseArrayA.get(sparseArrayA.keyAt(0)), false);
            } else {
                strBytesToHex = "";
            }
            if (strBytesToHex.isEmpty()) {
                strBytesToHex = MeshService.this.mProvisioningSettings.c().get(i3);
            }
            String str = strBytesToHex;
            ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) aVarD.a(i4);
            if (provisionedMeshNode == null) {
                provisionedMeshNode = (ProvisionedMeshNode) aVarD.b();
            }
            aVarD.e().g().a(provisionedMeshNode, true, str, AddressUtils.getUnicastAddressBytes(i4), false, i3, i5, bArr);
            Utils.notifySuccess(iActionListener, Boolean.TRUE);
        }

        public void a(ProvisionedMeshNode provisionedMeshNode, int i2, int i3, int i4, byte[] bArr, IActionListener iActionListener, boolean z2) {
            if (provisionedMeshNode == null) {
                a.a.a.a.b.m.a.b(MeshService.TAG, "sendMessageV2 failed, target mesh node is null");
                iActionListener.onFailure(-25, "target mesh node is null");
                return;
            }
            Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
            if (addedAppKeys == null) {
                a.a.a.a.b.m.a.b(MeshService.TAG, "sendMessageV2 failed, appKeys is null");
                iActionListener.onFailure(-25, "AppKey is null");
                c.a(provisionedMeshNode.getUnicastAddressInt(), 20019, "appKeys is null");
                return;
            }
            Integer next = addedAppKeys.keySet().iterator().next();
            int iIntValue = next.intValue();
            String str = provisionedMeshNode.getAddedAppKeys().get(next);
            u.a aVarH = G.a().d().h(provisionedMeshNode.getNetworkKey());
            if (aVarH == null) {
                iActionListener.onFailure(-25, "Subnets are not initialized");
                c.a(provisionedMeshNode.getUnicastAddressInt(), 20018, "Can't find the corresponding subnet(" + MeshParserUtils.bytesToHex(provisionedMeshNode.getNetworkKey(), false) + ")");
                return;
            }
            K kE = aVarH.e();
            if (kE != null && kE.f() == 2) {
                kE.g().a(provisionedMeshNode, true, str, provisionedMeshNode.getUnicastAddress(), false, iIntValue, i4, bArr);
                iActionListener.onSuccess(Integer.valueOf(Utils.byteArray2Int(provisionedMeshNode.getUnicastAddress())));
                return;
            }
            iActionListener.onFailure(-23, "Unreachable");
            c.a(provisionedMeshNode.getUnicastAddressInt(), 20015, "Mesh node not reachable");
            a.a.a.a.b.m.a.b(MeshService.TAG, "sendMessageV2 failed, target mesh is unreachable, try connect...");
            u uVarD = G.a().d();
            uVarD.a(MeshService.this.getApplicationContext(), MeshService.this);
            uVarD.a(aVarH, false);
        }

        public void a(OnReadyToBindHandler onReadyToBindHandler) {
            MeshService.this.mOnReadyToBindHandler = onReadyToBindHandler;
        }

        @SuppressLint({"DefaultLocale"})
        public void a(String str, String str2, int i2, int i3, int i4, int i5, IActionListener<Boolean> iActionListener, int i6) {
            a.a.a.a.b.m.a.c(MeshService.TAG, String.format("Config model subscription, action: %s, primaryAddress: %d, elementAddress: %d, subscriptionAddress: %d, retryCount: " + i6, str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
            Pair<u.a, ProvisionedMeshNode> pairA = a(i2, iActionListener);
            if (pairA != null) {
                C0337l c0337lG = ((u.a) pairA.first).e().g();
                if (((ProvisionedMeshNode) pairA.second).getDeviceKey() == null) {
                    ((ProvisionedMeshNode) pairA.second).setDeviceKey(MeshParserUtils.toByteArray(str2));
                }
                la laVar = new la(this, i6, str, str2, i2, i3, i4, i5, iActionListener);
                c0337lG.a(AddressUtils.getUnicastAddressBytes(i2), -32737, new ma(this, laVar, iActionListener));
                MeshService.this.mHandler.postDelayed(laVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                MeshService.this.mCurConfigModelSubAction = str;
                if (TmpConstant.GROUP_OP_ADD.equals(str)) {
                    c0337lG.a((ProvisionedMeshNode) pairA.second, AddressUtils.getUnicastAddressBytes(i3), AddressUtils.getUnicastAddressBytes(i4), i5);
                } else if (RequestParameters.SUBRESOURCE_DELETE.equals(str)) {
                    c0337lG.b((ProvisionedMeshNode) pairA.second, AddressUtils.getUnicastAddressBytes(i3), AddressUtils.getUnicastAddressBytes(i4), i5);
                }
            }
        }

        public final Pair<u.a, ProvisionedMeshNode> a(int i2, IActionListener<Boolean> iActionListener) {
            u.a aVarD = G.a().d().d();
            if (aVarD == null) {
                Utils.notifyFailed(iActionListener, -30, "Internal error(the target network cannot be found, there is a problem with Mesh initialization logic)");
                return null;
            }
            ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) aVarD.a(i2);
            if (provisionedMeshNode == null) {
                Utils.notifyFailed(iActionListener, -30, "Internal error(the target mesh node cannot be found, there is a problem with Mesh initialization logic)");
                return null;
            }
            if (!aVarD.f()) {
                Utils.notifyFailed(iActionListener, -23, "Unreachable");
                return null;
            }
            return new Pair<>(aVarD, provisionedMeshNode);
        }

        public void a() {
            K kE = G.a().d().d().e();
            if (kE != null) {
                kE.a(true);
            }
        }

        public void a(byte[] bArr, int i2, byte[] bArr2, MeshMsgListener meshMsgListener) {
            long keyOfMeshNodeDesiredMessageMap = MeshService.this.getKeyOfMeshNodeDesiredMessageMap(bArr, i2, bArr2);
            a.a.a.a.b.m.a.a(MeshService.TAG, "Register message listener with key: " + keyOfMeshNodeDesiredMessageMap + ", address: " + MeshParserUtils.bytesToHex(bArr, false) + ", opcode: " + i2);
            if (MeshService.this.mMeshNodeSpecifiedMessageListenerArray.get(keyOfMeshNodeDesiredMessageMap) != null) {
                a.a.a.a.b.m.a.a(MeshService.TAG, String.format("Update desired message listener for(%s:%d)", MeshParserUtils.bytesToHex(bArr, false), Integer.valueOf(i2)));
            }
            MeshService.this.mMeshNodeSpecifiedMessageListenerArray.put(keyOfMeshNodeDesiredMessageMap, meshMsgListener);
        }

        public void a(byte[] bArr, int i2, byte[] bArr2) {
            long keyOfMeshNodeDesiredMessageMap = MeshService.this.getKeyOfMeshNodeDesiredMessageMap(bArr, i2, bArr2);
            a.a.a.a.b.m.a.a(MeshService.TAG, "Unregister message listener with key: " + keyOfMeshNodeDesiredMessageMap + ", address: " + MeshParserUtils.bytesToHex(bArr, false) + ", opcode: " + i2);
            MeshService.this.mMeshNodeSpecifiedMessageListenerArray.remove(keyOfMeshNodeDesiredMessageMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addShareAppKey(ProvisionedMeshNode provisionedMeshNode) {
        String strPoll = this.mAppKeyQueue.poll();
        if (strPoll != null) {
            Integer numPoll = this.mAppKeyIndexQueue.poll();
            if (numPoll == null) {
                numPoll = 0;
            }
            Integer num = numPoll;
            Handler handler = this.mHandler;
            a aVar = new a(strPoll, num.intValue(), provisionedMeshNode, true);
            this.mAppKeyAddTimeoutTask = aVar;
            handler.postDelayed(aVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            this.mMeshManagerApi.a(provisionedMeshNode, num.intValue(), strPoll);
            a.a.a.a.b.m.a.c(TAG, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + strPoll);
        }
    }

    private void bindAppKey(ProvisionedMeshNode provisionedMeshNode, Element element, int i2, List<Integer> list) {
        Integer numRemove;
        if (list.size() == 0 || (numRemove = list.remove(0)) == null) {
            return;
        }
        MeshModel meshModel = element.getMeshModels().get(numRemove);
        if (meshModel == null) {
            bindAppKey(provisionedMeshNode, element, i2, list);
        } else {
            this.mMeshManagerApi.a(provisionedMeshNode, element.getElementAddress(), meshModel, i2);
            a.a.a.a.b.m.a.a(TAG, "bindAppKey");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkIfNodeIdentityMatches(byte[] bArr) {
        for (Map.Entry<Integer, ProvisionedMeshNode> entry : this.mMeshManagerApi.c().entrySet()) {
            if (this.mMeshManagerApi != null && entry.getValue().getIdentityKey() != null && this.mMeshManagerApi.b(entry.getValue(), bArr)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkMacAddressInWhiteList(String str) {
        List<String> list = this.mMacAddressWhiteList;
        if (list == null || list.size() == 0) {
            return true;
        }
        return this.mMacAddressWhiteList.contains(str.toUpperCase());
    }

    private void closeChannelQuietly(boolean z2) {
        BleMeshManager bleMeshManager = this.mBleMeshManager;
        if (bleMeshManager == null) {
            this.mBleMeshManager = new BleMeshManager(getApplicationContext());
            return;
        }
        if (bleMeshManager.getConnectState() == 2) {
            this.mBleMeshManager.disconnect().enqueue();
        } else {
            this.mBleMeshManager.close();
        }
        if (z2) {
            this.mBleMeshManager = new BleMeshManager(getApplicationContext());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configProxyFilter() {
        BaseMeshNode baseMeshNode = this.mCurrentProvisionMeshNode;
        if (baseMeshNode == null || !baseMeshNode.getSupportFastProvision()) {
            byte[] bArr = {0};
            SigmeshKey sigmeshKey = this.mSigmeshKeys.get(0);
            if (sigmeshKey == null || sigmeshKey.getProvisionNetKey() == null) {
                return;
            }
            UnprovisionedMeshNode unprovisionedMeshNode = new UnprovisionedMeshNode();
            unprovisionedMeshNode.setIvIndex(ByteBuffer.allocate(4).putInt(this.mProvisioningSettings.f()).array());
            unprovisionedMeshNode.setNetworkKey(MeshParserUtils.toByteArray(sigmeshKey.getProvisionNetKey().getNetKey()));
            unprovisionedMeshNode.setConfigurationSrc(this.mMeshManagerApi.b());
            ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
            int size = this.mProvisioningSettings.c().size();
            for (int i2 = 0; i2 < size; i2++) {
                provisionedMeshNode.setAddedAppKey(i2, this.mProvisioningSettings.c().get(i2));
            }
            provisionedMeshNode.setIsProvisioned(true);
            provisionedMeshNode.setConfigured(true);
            this.mMeshManagerApi.a(provisionedMeshNode, 0, bArr);
            this.mHandler.postDelayed(new V(this, provisionedMeshNode), 500L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connect(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) {
        G.a().d().g();
        byte[] serviceData = null;
        if (!z2) {
            this.mHandler.removeCallbacks(this.mProvisionTimeout);
            this.mCurrentProvisionMeshNode = null;
        }
        String strValueOf = "";
        if (!this.isInitialized) {
            Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
            intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.PROVISIONING_FAILED.getState());
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.NO_INITIALIZED_ERROR;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("device_mac_address", (Object) extendedBluetoothDevice.getAddress());
            jSONObject.put(TmpConstant.SERVICE_DESC, (Object) meshUtConst$MeshErrorEnum.getErrorMsg());
            intent.putExtra(Utils.EXTRA_PROVISIONING_FAIL_MSG, jSONObject.toJSONString());
            ScanRecord scanRecord = extendedBluetoothDevice.getScanRecord();
            if (scanRecord != null) {
                serviceData = scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
                strValueOf = String.valueOf(AliMeshUUIDParserUtil.extractPIDFromUUID(serviceData));
            }
            a.a.a.a.b.m.b.a("ALSMesh", "ble", strValueOf, false, serviceData, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            return;
        }
        if (Constants.f8774a && !z2) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new ha(this, extendedBluetoothDevice, z2)).start();
                return;
            }
            if (this.deviceProvisioningWorkerArray == null) {
                this.deviceProvisioningWorkerArray = new ArrayList<>();
            }
            DeviceProvisioningWorker deviceProvisioningWorker = new DeviceProvisioningWorker(getApplicationContext(), this, this.mSigmeshKeys, this.mMeshManagerApi.b(), this.mOnReadyToBindHandler, this.mConcurrentProvisionContext);
            deviceProvisioningWorker.a(this.mGlobalProvisionFinishedListener);
            deviceProvisioningWorker.a(extendedBluetoothDevice, z2, this.mProvisioningExtensionsParams);
            this.deviceProvisioningWorkerArray.add(deviceProvisioningWorker);
            return;
        }
        if (extendedBluetoothDevice == null) {
            a.a.a.a.b.m.a.b(TAG, "device is null");
            return;
        }
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "connect to device: " + extendedBluetoothDevice.getAddress() + ", isProvisioned：" + z2 + ", threadId=" + Thread.currentThread());
        boolean z3 = false;
        if (!z2) {
            this.deviceTobeProvision = extendedBluetoothDevice;
            this.mRequestStopAddNode = false;
            this.mIsReconnecting = false;
            this.mIsProvisioningComplete = false;
            this.mIsConfigurationComplete = false;
            this.mProvisionInfoReady = false;
            this.mDeviceIsReadyInProvisioningStep = false;
            this.mDeviceIsReadyInConfigurationStep = false;
            this.mAppKeyIndexQueue.clear();
            this.mAppKeyQueue.clear();
        }
        this.mBleMeshManager.setProvisioningComplete(z2);
        this.mScannerRecord = extendedBluetoothDevice.getScanRecord();
        this.mBluetoothDevice = extendedBluetoothDevice.getDevice();
        ScanRecord scanRecord2 = this.mScannerRecord;
        if (scanRecord2 != null) {
            byte[] serviceData2 = scanRecord2.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
            UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(serviceData2);
            String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
            String str2 = unprovisionedMeshNodeData.getProductId() + "";
            if (!z2) {
                requestProvisionInfo(serviceData2, unprovisionedMeshNodeData, deviceMac, str2);
            }
            a.a.a.a.b.m.a.c(str, ConvertUtils.bytes2HexString(serviceData2));
            boolean zIsFastProvisionMesh = unprovisionedMeshNodeData.isFastProvisionMesh();
            a.a.a.a.b.m.a.c(str, "unprovisionedMeshNodeData.isFastProvisionMesh: " + unprovisionedMeshNodeData.isFastProvisionMesh() + ", isProvisioned: " + z2);
            if (!z2) {
                closeChannelQuietly(true);
            }
            if (z2 || !unprovisionedMeshNodeData.isFastProvisionMesh()) {
                this.mBleMeshManager.setGattCallbacks(this);
                this.mBleMeshManager.connect(extendedBluetoothDevice.getDevice()).retry(5, 1000).enqueue();
                if (z2 && !this.mIsReconnecting) {
                    z3 = true;
                }
                this.mConnectToMeshNetwork = z3;
                a.a.a.a.b.m.a.a(str, "mConnectToMeshNetwork: " + this.mConnectToMeshNetwork);
            } else {
                this.mShouldAddAppKeyBeAdded = true;
                this.mConnectToMeshNetwork = false;
                J j2 = this.mFastProvisionWorker;
                if (j2 != null) {
                    j2.a(unprovisionedMeshNodeData, extendedBluetoothDevice.getDevice(), new H(this));
                }
            }
            z3 = zIsFastProvisionMesh;
        } else {
            a.a.a.a.b.m.a.c(str, "mScannerRecord is null");
            this.mBleMeshManager.connect(extendedBluetoothDevice.getDevice()).retry(5, 1000).enqueue();
            this.mConnectToMeshNetwork = z2 && !this.mIsReconnecting;
        }
        if (z2) {
            return;
        }
        this.mHandler.postDelayed(this.mProvisionTimeout, oa.a(z3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fullRefreshProvisionInfo() {
        a.a.a.a.b.m.a.a(TAG, "fullRefreshProvisionInfo called...");
        na.a().a(SequenceNumber.getInstance().getUUID(), new Z(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getInfoByAuthInfo(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        this.mIsConfigurationComplete = true;
        onProvisionSuccess();
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(this.mScannerRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)));
        String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
        String str = unprovisionedMeshNodeData.getProductId() + "";
        String strBytesToHex = MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false);
        int unicastAddressInt = AddressUtils.getUnicastAddressInt(bArr);
        IotDevice iotDevice = new IotDevice();
        iotDevice.setDevId(strBytesToHex.toLowerCase());
        iotDevice.setPlatform("SIGMESH");
        iotDevice.setSource(PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
        iotDevice.setMac(deviceMac);
        iotDevice.setProductKey(str);
        iotDevice.setUnicastAddress(unicastAddressInt);
        iotDevice.setUserId(na.a().b());
        if (this.mProvisioningExtensionsParams != null) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("familyId", (Object) String.valueOf(this.mProvisioningExtensionsParams.get("familyId")));
            iotDevice.setExtensions(jSONObject.toJSONString());
        }
        iotDevice.setUuid(na.a().c());
        ArrayList arrayList = new ArrayList();
        arrayList.add(iotDevice);
        String jSONString = JSON.toJSONString(arrayList);
        a.a.a.a.b.m.a.a(TAG, "Use delegate to handle bind logic");
        if (a.a.a.a.b.d.a.f1334a) {
            na.a().b("iot", "bindBLEDevice", jSONString, new T(this, iotDevice));
            return;
        }
        OnReadyToBindHandler onReadyToBindHandler = this.mOnReadyToBindHandler;
        if (onReadyToBindHandler != null) {
            onReadyToBindHandler.onReadyToBind(jSONString, new U(this, iotDevice));
        }
    }

    private long getKeyOfMeshNodeDesiredMessageMap(byte[] bArr, int i2) {
        long j2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        for (int i3 = 0; i3 < MeshParserUtils.getOpCodes(i2).length; i3++) {
            j2 = (j2 << 8) | (r7[i3] & 255);
        }
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceReadyInProvisioningStep() {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "handle device ready event in provisioning step, provisioning info ready flag: " + this.mProvisionInfoReady);
        if (this.mProvisionInfoReady) {
            a.a.a.a.b.m.a.a(str, "identifyNode after device(GATT connected or adv) is ready");
            byte[] serviceData = this.mScannerRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
            this.mMeshManagerApi.a(this.mBluetoothDevice.getAddress(), this.mBluetoothDevice.getName(), serviceData, new UnprovisionedMeshNodeData(serviceData), this.mFastProvisionWorker);
            sendBroadcastConnectionState("identifyNode");
        }
    }

    private void handleDisConnectivityStates() {
        if (!this.mIsProvisioningComplete) {
            this.mIsConnected = false;
        } else if (this.mIsReconnecting) {
            Intent intent = new Intent(Utils.ACTION_IS_RECONNECTING);
            intent.putExtra(Utils.EXTRA_DATA, true);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            a.a.a.a.b.m.a.a(TAG, "deviceTobeProvision:" + this.deviceTobeProvision);
            connect(this.deviceTobeProvision, true);
        } else if (this.mIsConfigurationComplete) {
            this.mIsConnected = false;
        }
        OnDisconnectListener onDisconnectListener = this.mOnConnectionStateListener;
        if (onDisconnectListener != null) {
            onDisconnectListener.onDisconnected();
            this.mOnConnectionStateListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDownStreamControlData(DownStreamControlData downStreamControlData) {
        u.a aVarC;
        if (downStreamControlData == null || downStreamControlData.getDownControlProtocols() == null) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (ControlProtocol controlProtocol : downStreamControlData.getDownControlProtocols()) {
            if (controlProtocol != null && controlProtocol.getSigmesh() != null) {
                for (Sigmesh sigmesh : controlProtocol.getSigmesh()) {
                    int destAddr = sigmesh.getDevice().getDestAddr();
                    sigmesh.getDevice().getAppKeyIndex();
                    sigmesh.getDevice().getNetKeyIndex();
                    if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null && (aVarC = G.a().d().c(controlProtocol.getDevId())) != null && aVarC.i()) {
                        linkedList.add(SIGMeshBizRequestGenerator.a(destAddr, sigmesh.getAction().getOpcode(), sigmesh.getAction().getParameters(), (IActionListener<Boolean>) null));
                    }
                }
            }
        }
        b bVar = this.mBinder;
        if (bVar != null) {
            bVar.b(linkedList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDownStreamControlDataForTg(DownStreamControlDataForTg downStreamControlDataForTg, String str) {
        u.a aVarC;
        String str2 = TAG;
        a.a.a.a.b.m.a.a(str2, "handleDownStreamControlDataForTg() called with: data = [" + downStreamControlDataForTg + "]");
        if (downStreamControlDataForTg == null || downStreamControlDataForTg.getPushCmd() == null) {
            a.a.a.a.b.m.a.d(str2, "handleDownStreamControlDataForTg return,  data or pushCommond is null.");
            return;
        }
        if (TextUtils.isEmpty(str)) {
            a.a.a.a.b.m.a.d(str2, "handleDownStreamControlDataForTg return,  devId is null.");
            return;
        }
        LinkedList linkedList = new LinkedList();
        ControlProtocol pushCmd = downStreamControlDataForTg.getPushCmd();
        if (pushCmd == null || pushCmd.getSigmesh() == null || pushCmd.getSigmesh().isEmpty()) {
            a.a.a.a.b.m.a.d(str2, "handleDownStreamControlDataForTg return,  sigmesh array is null.");
            return;
        }
        for (Sigmesh sigmesh : pushCmd.getSigmesh()) {
            int destAddr = sigmesh.getDevice().getDestAddr();
            sigmesh.getDevice().getAppKeyIndex();
            sigmesh.getDevice().getNetKeyIndex();
            if (sigmesh.getAction().getOpcode() != null && sigmesh.getAction().getParameters() != null && (aVarC = G.a().d().c(str)) != null && aVarC.i()) {
                int length = sigmesh.getAction().getParameters().length();
                if (length % 2 != 0) {
                    a.a.a.a.b.m.a.d(TAG, "handleDownStreamControlDataForTg return,  sigmesh.getAction().getParameters().length() is odd number. len=" + length);
                } else {
                    linkedList.add(SIGMeshBizRequestGenerator.a(destAddr, sigmesh.getAction().getOpcode(), sigmesh.getAction().getParameters(), (IActionListener<Boolean>) null));
                }
            }
        }
        a.a.a.a.b.m.a.a(TAG, "handleDownStreamControlDataForTg() offerBizRequest [" + linkedList + "], size=" + Integer.valueOf(linkedList.size()));
        b bVar = this.mBinder;
        if (bVar != null) {
            bVar.b(linkedList);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleProvisionFailed(MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum, String str) {
        UnprovisionedMeshNodeData unprovisionedMeshNodeData;
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.PROVISIONING_FAILED.getState());
        JSONObject jSONObject = new JSONObject();
        ExtendedBluetoothDevice extendedBluetoothDevice = this.deviceTobeProvision;
        String lowerCase = extendedBluetoothDevice != null ? extendedBluetoothDevice.getAddress().toLowerCase() : null;
        if (TextUtils.isEmpty(lowerCase) && (unprovisionedMeshNodeData = this.mUnprovisionedMeshNodeData) != null) {
            lowerCase = unprovisionedMeshNodeData.getDeviceMac().toLowerCase();
        }
        jSONObject.put("device_mac_address", (Object) lowerCase);
        jSONObject.put(TmpConstant.SERVICE_DESC, (Object) str);
        intent.putExtra(Utils.EXTRA_PROVISIONING_FAIL_MSG, jSONObject.toJSONString());
        String str2 = "";
        if (this.mUnprovisionedMeshNodeData != null) {
            str2 = this.mUnprovisionedMeshNodeData.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str2, false, null, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.mHandler.removeCallbacks(this.mProvisionTimeout);
        reset();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        i.c().a(getApplicationContext());
        a.a.a.a.b.m.a.a(TAG, "init called...");
        na.a().a(SequenceNumber.getInstance().getUUID(), new ca(this));
    }

    private void onProvisionSuccess() {
        u.a aVarD = G.a().d().d();
        if (aVarD == null) {
            return;
        }
        K kE = aVarD.e();
        if (kE == null) {
            kE = new K(getApplicationContext(), aVarD, this);
            aVarD.a(kE);
        }
        wifiConfig(kE, (ProvisionedMeshNode) this.mCurrentProvisionMeshNode, new W(this));
        if (!kE.a(this.mBleMeshManager, (ProvisionedMeshNode) this.mCurrentProvisionMeshNode, this.mFastProvisionWorker)) {
            this.mBleMeshManager.disconnect().enqueue();
        }
        this.mBleMeshManager = new BleMeshManager(getApplicationContext());
        BaseMeshNode baseMeshNode = this.mCurrentProvisionMeshNode;
        if (baseMeshNode == null || !baseMeshNode.getSupportFastGattProvision()) {
            return;
        }
        FastProvisionManager.getInstance().disConnect(new X(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProvisionedDeviceFound(ExtendedBluetoothDevice extendedBluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onProvisionedDeviceFound...");
        connect(extendedBluetoothDevice, true);
    }

    @RequiresApi(api = 18)
    private void prepareToReconnect() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("prepareToReconnect blemesh is:");
        sb.append(this.mBleMeshManager != null);
        a.a.a.a.b.m.a.a(str, sb.toString());
        this.mIsReconnecting = true;
        this.mBleMeshManager.setProvisioningComplete(true);
        BaseMeshNode baseMeshNode = this.mCurrentProvisionMeshNode;
        if (baseMeshNode == null || !baseMeshNode.getSupportFastGattProvision()) {
            if (this.mCurrentProvisionMeshNode != null) {
                this.mBleMeshManager.discoveryServices(true);
                return;
            }
            return;
        }
        a.a.a.a.b.m.a.a(str, "deviceTobeProvision:" + this.deviceTobeProvision);
        J j2 = this.mFastProvisionWorker;
        if (j2 != null) {
            j2.b();
        }
        connect(this.deviceTobeProvision, true);
        this.mOnConnectionStateListener = null;
    }

    private void requestConfirmation(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = this.mUnprovisionedMeshNodeData;
        if (unprovisionedMeshNodeData != null) {
            na.a().a(unprovisionedMeshNodeData.getDeviceMac(), MeshParserUtils.bytesToHex(provisionedMeshNode.getDeviceKey(), false), this.mUnprovisionedMeshNodeData.getProductId() + "", (this.mUnprovisionedMeshNodeData.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(this.mUnprovisionedMeshNodeData.getDeviceUuid(), false).toLowerCase() : "").toLowerCase(), new a.a.a.a.b.K(this, iActionListener));
        }
    }

    private void requestProvisionInfo(byte[] bArr, UnprovisionedMeshNodeData unprovisionedMeshNodeData, String str, String str2) {
        a.a.a.a.b.m.a.c(TAG, "requestProvisionInfo with productId " + str2);
        na.a().c(str, str2, unprovisionedMeshNodeData.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false).toLowerCase() : "", new I(this, unprovisionedMeshNodeData, bArr));
    }

    private void reset() {
        a.a.a.a.b.m.a.c(TAG, "Reset...");
        this.mAppKeyIndexQueue.clear();
        this.mAppKeyQueue.clear();
        this.mConnectToMeshNetwork = false;
        if (this.mBleMeshManager.getConnectState() == 2) {
            this.mBleMeshManager.disconnect().enqueue();
        } else {
            this.mBleMeshManager.close();
        }
        this.mHandler.removeCallbacks(this.mReconnectRunnable);
        this.mIsReconnecting = false;
        this.mIsProvisioningComplete = false;
        this.mIsConfigurationComplete = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastBindState(int i2, String str) {
        Intent intent = new Intent(Utils.ACTION_BIND_STATE);
        intent.putExtra(Utils.EXTRA_BIND_CODE, i2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(Utils.EXTRA_BIND_STATE_MSG, str);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendBroadcastConfiguration(int i2) {
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, i2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastConnectionState(String str) {
        Intent intent = new Intent(Utils.ACTION_CONNECTION_STATE);
        intent.putExtra(Utils.EXTRA_DATA, str);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastIsConnected(boolean z2, String str) {
        Intent intent = new Intent(Utils.ACTION_IS_CONNECTED);
        intent.putExtra(Utils.EXTRA_DATA, z2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(Utils.EXTRA_CONNECT_FAIL_MSG, str);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastProvisionedNodeFound(String str) {
        Intent intent = new Intent(Utils.ACTION_PROVISIONED_NODE_FOUND);
        intent.putExtra(Utils.EXTRA_DATA, str);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBroadcastProvisioningState(int i2) {
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, i2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScan() {
        a.a.a.a.b.m.a.a(TAG, "startScan...");
        this.mIsScanning = true;
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(1).setReportDelay(0L).setUseHardwareFilteringIfSupported(true).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID)).build());
        if (Utils.isBleEnabled()) {
            try {
                BluetoothLeScannerCompat.getScanner().startScan(arrayList, scanSettingsBuild, this.scanCallback);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.mHandler.postDelayed(this.mScannerTimeout, 60000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopScan() {
        a.a.a.a.b.m.a.a(TAG, "stopScan...");
        this.mHandler.removeCallbacks(this.mScannerTimeout);
        if (Utils.isBleEnabled()) {
            BluetoothLeScannerCompat.getScanner().stopScan(this.scanCallback);
        }
        this.mIsScanning = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void wifiConfig(K k2, ProvisionedMeshNode provisionedMeshNode, IActionListener iActionListener) {
        Map<String, Object> map = this.mProvisioningExtensionsParams;
        if (map == null || !map.containsKey("ssid")) {
            return;
        }
        a.a.a.a.b.m.a.a(TAG, "WiFi config start");
        k2.g().a(provisionedMeshNode, true, provisionedMeshNode.getAddedAppKeys().get(0), provisionedMeshNode.getUnicastAddress(), false, 0, Integer.parseInt("D1A801", 16), C0232a.a((String) this.mProvisioningExtensionsParams.get("ssid"), (String) this.mProvisioningExtensionsParams.get("password"), this.mProvisioningExtensionsParams.containsKey(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) ? (String) this.mProvisioningExtensionsParams.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) : null, this.mProvisioningExtensionsParams.containsKey(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX) ? ((Byte) this.mProvisioningExtensionsParams.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX)).byteValue() : (byte) 0), iActionListener);
        this.mProvisioningExtensionsParams = null;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void advertiseAppKey(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        sendBroadcastConfiguration(MeshNodeStatus.COMPOSITION_DATA_STATUS_RECEIVED.getState());
        if (this.mShouldAddAppKeyBeAdded) {
            this.mShouldAddAppKeyBeAdded = false;
            this.mHandler.postDelayed(new Y(this, provisionedMeshNode), 500L);
        }
    }

    @Override // b.InterfaceC0326a
    public void checkConfirmationValueMatches(UnprovisionedMeshNode unprovisionedMeshNode, UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, byte[] bArr3, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        a.a.a.a.b.m.a.a(TAG, "checkConfirmationValueMatches");
        this.mCurrentProvisionMeshNode = unprovisionedMeshNode;
        this.mUnprovisionedMeshNodeData = unprovisionedMeshNodeData;
        String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
        int productId = unprovisionedMeshNodeData.getProductId();
        String lowerCase = MeshParserUtils.bytesToHex(bArr3, false).toLowerCase();
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr2, false);
        String lowerCase2 = MeshParserUtils.bytesToHex(bArr, false).toLowerCase();
        String strBytesToHex2 = unprovisionedMeshNodeData.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false) : "";
        na.a().a(deviceMac, strBytesToHex, lowerCase, lowerCase2, productId + "", strBytesToHex2, new M(this, interfaceC0015a));
    }

    @Override // b.InterfaceC0326a
    public void generateConfirmationValue(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, InterfaceC0326a.b bVar) {
        a.a.a.a.b.m.a.a(TAG, "generateConfirmationValue");
        this.mUnprovisionedMeshNodeData = unprovisionedMeshNodeData;
        String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr2, false);
        na.a().a(deviceMac, strBytesToHex.toLowerCase(), MeshParserUtils.bytesToHex(bArr, false), unprovisionedMeshNodeData.getProductId() + "", this.mUnprovisionedMeshNodeData.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(this.mUnprovisionedMeshNodeData.getDeviceUuid(), false) : "", new L(this, bVar, unprovisionedMeshNodeData));
    }

    @Override // b.q
    public ProvisionedMeshNode getMeshNode(byte[] bArr, byte[] bArr2) {
        return (ProvisionedMeshNode) G.a().d().a(bArr, bArr2);
    }

    @Override // b.InterfaceC0338m
    public int getMtu() {
        int mtuSize = a.a.a.a.b.d.a.f1336c ? 35 : this.mBleMeshManager.getMtuSize();
        a.a.a.a.b.m.a.a(TAG, "getMtu, MtuSize: " + mtuSize);
        return mtuSize;
    }

    public void onAcknowledgedVendorModelMessageSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onAcknowledgedVendorModelMessageSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onAppKeyAddSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "ProvisionedMeshNode, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.SENDING_APP_KEY_ADD.getState());
    }

    @Override // b.q
    public void onAppKeyBindSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onAppKeyBindSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.APP_BIND_SENT.getState());
    }

    @Override // b.q
    public void onAppKeyBindStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4, int i5) {
        a.a.a.a.b.m.a.a(TAG, "onAppKeyBindStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName() + ", status:" + i2);
        if (!this.mAppKeyQueue.isEmpty()) {
            addShareAppKey(provisionedMeshNode);
            return;
        }
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.APP_BIND_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, i3);
        intent.putExtra("EXTRA_APP_KEY_INDEX", i4);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i5);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.mHandler.postDelayed(new O(this, provisionedMeshNode, i3, i4), 500L);
    }

    @Override // b.q
    public void onAppKeyStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4) {
        if (Constants.f8774a) {
            a.a.a.a.b.m.a.c(TAG, "now use provisioning work model, ignore appKey status callback");
            return;
        }
        this.mHandler.removeCallbacks(this.mAppKeyAddTimeoutTask);
        this.mAppKeyAddTimeoutTask = null;
        this.mAppKeyRetryAddCount = 0;
        a.a.a.a.b.m.a.a(TAG, "onAppKeyStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName() + ", status: " + i2);
        if (!z2) {
            handleProvisionFailed(MeshUtConst$MeshErrorEnum.APPKEY_ADD_ERROR, "appKey add status: " + i2);
            return;
        }
        SigmeshKey sigmeshKey = this.mSigmeshKeys.get(i3);
        if (sigmeshKey != null && sigmeshKey.getProvisionAppKeys() != null) {
            Iterator<ProvisionAppKey> it = sigmeshKey.getProvisionAppKeys().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ProvisionAppKey next = it.next();
                if (next.getAppKeyIndex() == i4) {
                    provisionedMeshNode.setAddedAppKey(i4, next.getAppKey());
                    break;
                }
            }
        }
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.APP_KEY_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra("EXTRA_APP_KEY_INDEX", i3);
        intent.putExtra("EXTRA_APP_KEY_INDEX", i4);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.mHandler.postDelayed(new N(this, provisionedMeshNode, i4), 100L);
    }

    @Override // b.q
    public void onAppKeyUnbindSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onAppKeyUnbindSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.APP_UNBIND_SENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i2) {
        a.a.a.a.b.m.a.a(TAG, "onBatteryValueReceived...");
    }

    @Override // b.q
    public void onBlockAcknowledgementReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onBlockAcknowledgementReceived, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.BLOCK_ACKNOWLEDGEMENT_RECEIVED.getState());
    }

    @Override // b.q
    public void onBlockAcknowledgementSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onBlockAcknowledgementSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.SENDING_BLOCK_ACKNOWLEDGEMENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onBonded(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onBonded...");
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingFailed(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingRequired(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onBondingRequired...");
    }

    @Override // b.q
    public void onCommonMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, String str, byte[] bArr2, a.a.a.a.b.h.a aVar) throws NumberFormatException {
        String str2;
        String str3 = str;
        if ("801F".equalsIgnoreCase(str3)) {
            if (!this.mCurConfigModelSubAction.equals(TmpConstant.GROUP_OP_ADD)) {
                str2 = this.mCurConfigModelSubAction.equals(RequestParameters.SUBRESOURCE_DELETE) ? "801C" : "801B";
            }
            str3 = str2;
        }
        int i2 = Integer.parseInt(str3, 16);
        byte[] opCodeBytes = Utils.getOpCodeBytes(i2);
        String strConvertDevIdToIotId = MeshDeviceInfoManager.getInstance().convertDevIdToIotId(provisionedMeshNode.getDevId());
        String str4 = TAG;
        a.a.a.a.b.m.a.a(str4, String.format("Common message status received from %s, opcode: %s, parameters: %s", strConvertDevIdToIotId, str3, ConvertUtils.bytes2HexString(bArr2)));
        long keyOfMeshNodeDesiredMessageMap = getKeyOfMeshNodeDesiredMessageMap(bArr, i2);
        if ((i2 == 13871105 || i2 == 13936641) && bArr2 != null && bArr2.length >= 3) {
            keyOfMeshNodeDesiredMessageMap = getKeyOfMeshNodeDesiredMessageMap(bArr, i2, new byte[]{bArr2[1], bArr2[2]});
        }
        MeshMsgListener meshMsgListener = this.mMeshNodeSpecifiedMessageListenerArray.get(keyOfMeshNodeDesiredMessageMap);
        a.a.a.a.b.m.a.a(str4, String.format(Locale.getDefault(), "key: %d, userCallback: %s", Long.valueOf(keyOfMeshNodeDesiredMessageMap), meshMsgListener));
        if (meshMsgListener != null) {
            MeshAccessPayload meshAccessPayload = new MeshAccessPayload();
            meshAccessPayload.setOpCode(str3);
            meshAccessPayload.setParameters(bArr2);
            meshAccessPayload.setNetKey(provisionedMeshNode.getNetworkKey());
            meshAccessPayload.setTranslatedTSLDesc(null);
            meshAccessPayload.setSequenceNo(provisionedMeshNode.getSequenceNumber());
            meshAccessPayload.setIotId(strConvertDevIdToIotId);
            meshMsgListener.onReceiveMeshMessage(bArr, meshAccessPayload);
        }
        TgMeshManager.getInstance().notifyMeshMessage(bArr, str3, bArr2, provisionedMeshNode.getNetworkKey(), provisionedMeshNode.getSequenceNumber(), strConvertDevIdToIotId);
        Integer num = this.mConfirmationOpCodeMap.get(i2);
        if (num != null) {
            Byte b2 = this.mPreviousMessageTIDRecord.get(Utils.byteArray2Int(bArr));
            Byte bValueOf = (bArr2 == null || bArr2.length <= 0) ? null : Byte.valueOf(bArr2[0]);
            if (bValueOf != null) {
                if (b2 != null && b2.byteValue() == bValueOf.byteValue()) {
                    return;
                }
                this.mPreviousMessageTIDRecord.put(Utils.byteArray2Int(bArr), bValueOf);
                byte[] opCodeBytes2 = Utils.getOpCodeBytes(num.intValue());
                byte[] bArr3 = {bArr2[0]};
                String str5 = this.mProvisioningSettings.c().get(0);
                a.a.a.a.b.m.a.c(str4, "Ack: opcode(" + ConvertUtils.bytes2HexString(opCodeBytes2) + "), parameter(" + ConvertUtils.bytes2HexString(bArr3) + ")");
                if (this.USE_MESH_V2) {
                    Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
                    if (addedAppKeys != null) {
                        Integer next = addedAppKeys.keySet().iterator().next();
                        int iIntValue = next.intValue();
                        String str6 = addedAppKeys.get(next);
                        K kE = G.a().d().h(provisionedMeshNode.getNetworkKey()).e();
                        if (kE != null) {
                            kE.g().a(provisionedMeshNode, true, str6, bArr, false, iIntValue, Utils.byteArray2Int(opCodeBytes2), bArr3);
                        } else {
                            a.a.a.a.b.m.a.b(str4, "subnets is null");
                            this.mMeshManagerApi.a(provisionedMeshNode, true, str6, bArr, false, iIntValue, Utils.byteArray2Int(opCodeBytes2), bArr3);
                        }
                    }
                } else {
                    this.mMeshManagerApi.a(provisionedMeshNode, true, str5, bArr, false, 0, Utils.byteArray2Int(opCodeBytes2), bArr3);
                }
            }
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("opcode", (Object) Utils.bytes2HexString(opCodeBytes));
        jSONObject.put(PushConstants.PARAMS, (Object) MeshParserUtils.bytesToHex(bArr2, false));
        String jSONString = jSONObject.toJSONString();
        int unicastAddressInt = AddressUtils.getUnicastAddressInt(bArr);
        DeviceStatusesPayload deviceStatusesPayload = new DeviceStatusesPayload();
        ArrayList arrayList = new ArrayList();
        DeviceStatusesPayload.SubDeviceStatuses subDeviceStatuses = new DeviceStatusesPayload.SubDeviceStatuses(unicastAddressInt, jSONString);
        if (aVar != null) {
            subDeviceStatuses.setLinkQualityMetric(JSON.toJSONString(aVar));
        }
        arrayList.add(subDeviceStatuses);
        deviceStatusesPayload.setSubDeviceStatuses(arrayList);
        IoTMultiendInOneBridge.getInstance().onDeviceStatusChange(JSON.toJSONString(deviceStatusesPayload));
        jSONObject.put("srcAddr", (Object) Integer.valueOf(unicastAddressInt));
        Intent intent = new Intent(Utils.ACTION_COMMON_MESSAGE_STATUS_RECEIVED);
        intent.putExtra(Utils.EXTRA_STATUS, jSONObject.toJSONString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setUserId(na.a().b());
        String strC = na.a().c();
        deviceStatus.setUuid(strC);
        deviceStatus.setUnicastAddress(unicastAddressInt);
        deviceStatus.setStatus(jSONString);
        deviceStatus.setIotId(strConvertDevIdToIotId);
        deviceStatus.setSequenceNo(provisionedMeshNode.getSequenceNumber());
        c.a(unicastAddressInt, 2, false);
        na.a().a(strC, Collections.singletonList(deviceStatus), new Q(this, unicastAddressInt));
    }

    @Override // b.q
    public void onCompositionDataStatusReceived(ProvisionedMeshNode provisionedMeshNode) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onCompositionDataStatusReceived, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.COMPOSITION_DATA_STATUS_RECEIVED.getState());
        if (this.mShouldAddAppKeyBeAdded) {
            this.mShouldAddAppKeyBeAdded = false;
            String strPoll = this.mAppKeyQueue.poll();
            if (strPoll == null) {
                a.a.a.a.b.m.a.d(str, "Empty appKey queue");
                return;
            }
            Integer numPoll = this.mAppKeyIndexQueue.poll();
            if (numPoll == null) {
                numPoll = 0;
            }
            Integer num = numPoll;
            a.a.a.a.b.m.a.c(str, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + strPoll);
            Handler handler = this.mHandler;
            a aVar = new a(strPoll, num.intValue(), provisionedMeshNode, false);
            this.mAppKeyAddTimeoutTask = aVar;
            handler.postDelayed(aVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            this.mMeshManagerApi.a(provisionedMeshNode, num.intValue(), strPoll);
            a.a.a.a.b.m.a.a(str, "addAppKey");
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "MeshService create called... ");
        this.mHandler = new Handler();
        this.mSigmeshKeys = new SparseArray<>();
        this.mBindModel = new ArrayList();
        BleMeshManager bleMeshManager = new BleMeshManager(this);
        this.mBleMeshManager = bleMeshManager;
        bleMeshManager.setGattCallbacks(this);
        C0337l c0337l = new C0337l(this);
        this.mMeshManagerApi = c0337l;
        c0337l.a((InterfaceC0338m) this);
        this.mMeshManagerApi.a((p) this);
        this.mMeshManagerApi.a((InterfaceC0326a) this);
        this.mMeshManagerApi.a((q) this);
        this.mConcurrentProvisionContext = new C0233b();
        this.mFastProvisionWorker = new J();
        this.isInitialized = false;
        this.isRetry = false;
        this.mConfirmationOpCodeMap.put(13936641, 13959592);
        this.mConfirmationOpCodeMap.put(13543425, 13435304);
        init();
        a.a.a.a.b.m.a.a(str, "MeshService created, " + hashCode());
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataReceived(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onDataReceived, device: " + bluetoothDevice.getName() + ", mac: " + bluetoothDevice.getAddress() + ", mtu: " + i2 + ", pdu: " + bArr);
        BaseMeshNode baseMeshNode = this.mCurrentProvisionMeshNode;
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.d(str, "provision mesh node is null");
        } else {
            this.mMeshManagerApi.a(baseMeshNode, i2, bArr, (a.a.a.a.b.h.a) null);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataSent(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onDataSent, device: " + bluetoothDevice.getName() + ", mac: " + bluetoothDevice.getAddress() + ", mtu: " + i2 + ", pdu: " + bArr);
        BaseMeshNode baseMeshNode = this.mCurrentProvisionMeshNode;
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.d(str, "provision mesh node is null");
        } else {
            this.mMeshManagerApi.a(baseMeshNode, i2, bArr);
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        a.a.a.a.b.m.a.c(TAG, "onDestroy...");
        stopForeground(true);
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnected(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onDeviceConnected to device: " + bluetoothDevice.getName());
        if (this.mIsReconnecting) {
            return;
        }
        this.mBluetoothDevice = bluetoothDevice;
        this.mIsConnected = true;
        if (this.mConnectToMeshNetwork) {
            sendBroadcastIsConnected(true, null);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnecting(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onDeviceConnecting...");
        sendBroadcastConnectionState(getString(R.string.state_connecting));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnected(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onDeviceDisconnected...");
        handleDisConnectivityStates();
        sendBroadcastIsConnected(false, UtError.MESH_DISCONNECT.getMsg());
        sendBroadcastConnectionState(getString(R.string.state_disconnected));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnecting(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onDeviceDisconnecting...");
        this.mIsConnected = false;
        sendBroadcastConnectionState(getString(R.string.state_disconnecting));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceNotSupported(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.b(TAG, "onDeviceNotSupported...");
        if (this.mIsProvisioningComplete) {
            return;
        }
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.DEVICE_NOT_SUPPORT_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg());
        String str = "";
        if (this.mUnprovisionedMeshNodeData != null) {
            str = this.mUnprovisionedMeshNodeData.getProductId() + "";
        }
        a.a.a.a.b.m.b.a("ALSMesh", "ble", str, false, null, "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceReady(BluetoothDevice bluetoothDevice) {
        BaseMeshNode baseMeshNode;
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onDeviceReady...");
        if (this.mRequestStopAddNode) {
            a.a.a.a.b.m.a.d(str, "onDeviceReady, But User terminated the process");
            return;
        }
        Intent intent = new Intent(Utils.ACTION_ON_DEVICE_READY);
        intent.putExtra(Utils.EXTRA_DATA, true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        if (this.mConnectToMeshNetwork) {
            return;
        }
        if (!this.mBleMeshManager.isProvisioningComplete()) {
            a.a.a.a.b.m.a.a(str, "Provisioning Not Complete");
            return;
        }
        if (this.mIsConfigurationComplete || (baseMeshNode = this.mCurrentProvisionMeshNode) == null) {
            a.a.a.a.b.m.a.a(str, "Configuration Not Complete");
            return;
        }
        baseMeshNode.setBluetoothDeviceAddress(bluetoothDevice.getAddress());
        BaseMeshNode baseMeshNode2 = this.mCurrentProvisionMeshNode;
        if (baseMeshNode2 == null || baseMeshNode2.getDeviceKey() == null || this.mCurrentProvisionMeshNode.getUnicastAddress() == null) {
            return;
        }
        this.mDeviceIsReadyInConfigurationStep = true;
        this.mShouldAddAppKeyBeAdded = true;
        a.a.a.a.b.m.a.a(str, "getCompositionData");
        sendBroadcastConnectionState("getCompositionData");
        onCompositionDataStatusReceived((ProvisionedMeshNode) this.mCurrentProvisionMeshNode);
    }

    @Override // aisble.BleManagerCallbacks
    public void onError(BluetoothDevice bluetoothDevice, String str, int i2) {
        a.a.a.a.b.m.a.b(TAG, "onError: " + str);
        handleProvisionFailed(MeshUtConst$MeshErrorEnum.CALLBACK_ERROR, str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onFastProvisionDataSend(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.c(TAG, "onFastProvisionDataSend: " + ConvertUtils.bytes2HexString(bArr));
        this.mMeshManagerApi.a(baseMeshNode, 18, bArr);
    }

    @Override // b.q
    public void onGenericLevelGetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onGenericLevelGetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    public void onGenericLevelSetSent(ProvisionedMeshNode provisionedMeshNode, boolean z2, boolean z3, int i2) {
        a.a.a.a.b.m.a.a(TAG, "onGenericLevelSetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericLevelSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onGenericLevelSetUnacknowledgedSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericLevelStatusReceived(ProvisionedMeshNode provisionedMeshNode, int i2, int i3, int i4, int i5) {
        a.a.a.a.b.m.a.a(TAG, "onGenericLevelStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_LEVEL_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_PRESENT_STATE, i2);
        intent.putExtra(Utils.EXTRA_GENERIC_TARGET_STATE, i3);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_STEPS, i4);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_RES, i5);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGenericOnOffGetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onGenericOnOffGetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    public void onGenericOnOffSetSent(ProvisionedMeshNode provisionedMeshNode, boolean z2, boolean z3, int i2) {
        a.a.a.a.b.m.a.a(TAG, "onGenericOnOffSetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericOnOffSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onGenericOnOffSetUnacknowledgedSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_ON_OFF_SET_UNACK, "");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGenericOnOffStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, Boolean bool, int i2, int i3) {
        a.a.a.a.b.m.a.a(TAG, "onGenericOnOffStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_ON_OFF_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_PRESENT_STATE, z2);
        intent.putExtra(Utils.EXTRA_GENERIC_TARGET_STATE, bool);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_STEPS, i2);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_RES, i3);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGetCompositionDataSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onGetCompositionDataSent, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.COMPOSITION_DATA_GET_SENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onLinkLossOccurred(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "onLinklossOccur...");
        this.mBleMeshManager.close();
        sendBroadcastIsConnected(false, UtError.MESH_LINK_LOSS_OCCURRED.getMsg());
        this.mIsReconnecting = false;
        this.mIsProvisioningComplete = false;
        this.mIsConfigurationComplete = false;
        sendBroadcastConnectionState(getString(R.string.state_linkloss_occur));
        handleDisConnectivityStates();
    }

    @Override // b.q
    public void onMeshNodeResetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onMeshNodeResetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_DATA_NODE_RESET, "");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // b.q
    public void onMeshNodeResetStatusReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onMeshNodeResetStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.NODE_RESET_STATUS_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningAuthenticationInputRequested(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningAuthenticationInputRequested, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_AUTHENTICATION_INPUT_WAITING.getState());
    }

    @Override // b.p
    public void onProvisioningCapabilitiesReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onProvisioningCapabilitiesReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        this.mIsProvisioningComplete = false;
        this.mIsConfigurationComplete = false;
        this.mMeshManagerApi.a(unprovisionedMeshNode);
        a.a.a.a.b.m.a.a(str, "startProvisioning");
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_CAPABILITIES.getState());
    }

    @Override // b.p
    public void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onProvisioningComplete, provisionedMeshNode: " + provisionedMeshNode.getNodeName());
        if (this.mRequestStopAddNode) {
            a.a.a.a.b.m.a.d(str, "onProvisioningComplete, But user terminated the process");
            return;
        }
        a.a.a.a.b.m.a.a(str, "provision complete device isSupportGatt:" + provisionedMeshNode.getSupportFastGattProvision());
        provisionedMeshNode.setDevId(MeshParserUtils.bytesToHex(this.mUnprovisionedMeshNodeData.getDeviceUuid(), false).toLowerCase());
        G.a().d().a(provisionedMeshNode, true, true);
        this.mCurrentProvisionMeshNode = provisionedMeshNode;
        prepareToReconnect();
        requestConfirmation(provisionedMeshNode, new a.a.a.a.b.J(this));
    }

    @Override // b.p
    public void onProvisioningConfirmationReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningConfirmationReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_CONFIRMATION_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningConfirmationSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningConfirmationSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_CONFIRMATION_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningDataSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningDataSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_DATA_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningFailed(UnprovisionedMeshNode unprovisionedMeshNode, int i2) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningFailed, meshNode: " + unprovisionedMeshNode.getNodeName());
        this.mIsProvisioningComplete = false;
        handleProvisionFailed(MeshUtConst$MeshErrorEnum.ILLEGAL_PROVISION_DATA_RECEIVED, "inner error code: " + i2);
    }

    @Override // b.p
    public void onProvisioningInputCompleteSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningInputCompleteSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_INPUT_COMPLETE.getState());
    }

    @Override // b.p
    public void onProvisioningInviteSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningInviteSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        this.mCurrentProvisionMeshNode = unprovisionedMeshNode;
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_INVITE.getState());
    }

    @Override // b.p
    public void onProvisioningPublicKeyReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningPublicKeyReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_PUBLIC_KEY_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningPublicKeySent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningPublicKeySent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_PUBLIC_KEY_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningRandomReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningRandomReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_RANDOM_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningRandomSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningRandomSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_RANDOM_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningStartSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onProvisioningStartSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        sendBroadcastProvisioningState(MeshNodeStatus.PROVISIONING_START.getState());
    }

    @Override // b.q
    public void onPublicationSetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onPublicationSetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.PUBLISH_ADDRESS_SET_SENT.getState());
    }

    @Override // b.q
    public void onPublicationStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3) {
        a.a.a.a.b.m.a.a(TAG, "onPublicationStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.PUBLISH_ADDRESS_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, bArr);
        intent.putExtra(Utils.EXTRA_PUBLISH_ADDRESS, bArr2);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i3);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        this.mHandler.postDelayed(new a.a.a.a.b.P(this, provisionedMeshNode, bArr), 500L);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onReceiveFastProvisionData(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.c(TAG, "onReceiveFastProvisionData " + ConvertUtils.bytes2HexString(bArr));
        BaseMeshNode baseMeshNode2 = this.mCurrentProvisionMeshNode;
        if (baseMeshNode2 == null) {
            return;
        }
        baseMeshNode2.setIsProvisioned(true);
        if (this.mCurrentProvisionMeshNode == null) {
            return;
        }
        this.mIsReconnecting = false;
        this.mIsConnected = true;
        this.mMeshManagerApi.a(baseMeshNode, 18, bArr, (a.a.a.a.b.h.a) null);
    }

    @Override // aisble.BleManagerCallbacks
    public void onServicesDiscovered(BluetoothDevice bluetoothDevice, boolean z2) {
        String str = TAG;
        a.a.a.a.b.m.a.a(str, "onServicesDiscovered...");
        sendBroadcastConnectionState(getString(R.string.state_initializing));
        if (this.mIsReconnecting) {
            this.mIsReconnecting = false;
            return;
        }
        if (this.mConnectToMeshNetwork) {
            configProxyFilter();
            return;
        }
        if (this.mRequestStopAddNode) {
            a.a.a.a.b.m.a.d(str, "onServicesDiscovered, But User terminated the process");
            return;
        }
        this.mDeviceIsReadyInProvisioningStep = true;
        this.mIsProvisioningComplete = false;
        this.mIsConfigurationComplete = false;
        handleDeviceReadyInProvisioningStep();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i2, int i3) {
        return 2;
    }

    @Override // b.q
    public void onSubscriptionAddSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onSubscriptionAddSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.SUBSCRIPTION_ADD_SENT.getState());
    }

    @Override // b.q
    public void onSubscriptionDeleteSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onSubscriptionDeleteSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        sendBroadcastConfiguration(MeshNodeStatus.SUBSCRIPTION_DELETE_SENT.getState());
    }

    @Override // b.q
    public void onSubscriptionStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3) {
        a.a.a.a.b.m.a.a(TAG, "onSubscriptionStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        if (!this.mIsProvisioningComplete || this.mIsConfigurationComplete) {
            return;
        }
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.SUBSCRIPTION_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, bArr);
        intent.putExtra(Utils.EXTRA_PUBLISH_ADDRESS, bArr2);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i3);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        List<SubscribeGroupAddr> list = this.mSubscribeGroupAddrs;
        if (list == null || list.size() <= 0) {
            getInfoByAuthInfo(provisionedMeshNode.getUnicastAddress());
            return;
        }
        SubscribeGroupAddr subscribeGroupAddrRemove = this.mSubscribeGroupAddrs.remove(0);
        if (subscribeGroupAddrRemove == null || subscribeGroupAddrRemove.getGroupAddr() == null || subscribeGroupAddrRemove.getModelId() == null) {
            return;
        }
        Integer groupAddr = subscribeGroupAddrRemove.getGroupAddr();
        this.mMeshManagerApi.a(provisionedMeshNode, bArr, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddrRemove.getModelId().intValue());
    }

    @Override // b.q
    public void onTransactionFailed(ProvisionedMeshNode provisionedMeshNode, int i2, boolean z2) {
        a.a.a.a.b.m.a.a(TAG, "onTransactionFailed, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_TRANSACTION_STATE);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, i2);
        intent.putExtra(Utils.EXTRA_DATA, z2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onUnacknowledgedVendorModelMessageSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onUnacknowledgedVendorModelMessageSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onUnknownPduReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(TAG, "onUnknownPduReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onVendorModelMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.a(TAG, "onVendorModelMessageStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_VENDOR_MODEL_MESSAGE_STATE);
        intent.putExtra(Utils.EXTRA_DATA, bArr);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void requestConfigMsg(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        provisionedMeshNode.setDevId(MeshParserUtils.bytesToHex(this.mUnprovisionedMeshNodeData.getDeviceUuid(), false));
        G.a().d().a(provisionedMeshNode, true, true);
        this.mCurrentProvisionMeshNode = provisionedMeshNode;
        requestConfirmation(provisionedMeshNode, new S(this, iActionListener));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void requestProvisionMsg(ScanRecord scanRecord) {
        a.a.a.a.b.m.a.a(TAG, "requestProvisionMsg...");
        sendBroadcastConnectionState(getString(R.string.state_initializing));
        if (this.mIsReconnecting) {
            this.mIsReconnecting = false;
            return;
        }
        if (this.mConnectToMeshNetwork) {
            configProxyFilter();
            return;
        }
        byte[] serviceData = this.mScannerRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(serviceData);
        requestProvisionInfo(serviceData, unprovisionedMeshNodeData, unprovisionedMeshNodeData.getDeviceMac(), unprovisionedMeshNodeData.getProductId() + "");
        this.mIsProvisioningComplete = false;
        this.mIsConfigurationComplete = false;
    }

    @Override // b.InterfaceC0338m
    @RequiresApi(api = 18)
    public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.a(TAG, "sendPdu, meshNode: " + baseMeshNode.getNodeName() + ", mac: " + baseMeshNode.getBluetoothDeviceAddress() + ", pdu: " + bArr);
        if (!baseMeshNode.getSupportFastProvision()) {
            this.mBleMeshManager.sendPdu(bArr);
            c.a(baseMeshNode.getUnicastAddressInt(), "0");
            return;
        }
        if (this.mBleMeshManager.getConnectState() == 2) {
            this.mBleMeshManager.sendPdu(bArr);
        } else {
            J j2 = this.mFastProvisionWorker;
            if (j2 != null) {
                j2.a(baseMeshNode, bArr);
            }
        }
        c.a(baseMeshNode.getUnicastAddressInt(), "1");
    }

    @Override // aisble.BleManagerCallbacks
    public boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(TAG, "shouldEnableBatteryLevelNotifications...");
        return false;
    }

    @Override // android.app.Service
    public b onBind(Intent intent) {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getKeyOfMeshNodeDesiredMessageMap(byte[] bArr, int i2, byte[] bArr2) {
        long j2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
        byte[] opCodes = MeshParserUtils.getOpCodes(i2);
        for (byte b2 : opCodes) {
            j2 = (j2 << 8) | (b2 & 255);
        }
        return (bArr2 == null || bArr2.length != 2) ? j2 : (((j2 << 8) | (bArr2[0] & 255)) << 8) | (bArr2[1] & 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindAppKey(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, int i2, List<Integer> list) {
        Integer numRemove;
        if (list.size() == 0 || (numRemove = list.remove(0)) == null) {
            return;
        }
        this.mMeshManagerApi.a(provisionedMeshNode, bArr, numRemove.intValue(), i2);
        a.a.a.a.b.m.a.a(TAG, "bindAppKey");
    }
}
