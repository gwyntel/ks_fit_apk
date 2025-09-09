package com.alibaba.ailabs.iot.mesh;

import a.a.a.a.b.A;
import a.a.a.a.b.B;
import a.a.a.a.b.C;
import a.a.a.a.b.C0224b;
import a.a.a.a.b.C0227e;
import a.a.a.a.b.C0228f;
import a.a.a.a.b.C0229g;
import a.a.a.a.b.C0230h;
import a.a.a.a.b.C0231i;
import a.a.a.a.b.C0248j;
import a.a.a.a.b.C0252n;
import a.a.a.a.b.C0254p;
import a.a.a.a.b.C0255q;
import a.a.a.a.b.C0256s;
import a.a.a.a.b.C0259v;
import a.a.a.a.b.C0260w;
import a.a.a.a.b.D;
import a.a.a.a.b.E;
import a.a.a.a.b.F;
import a.a.a.a.b.G;
import a.a.a.a.b.RunnableC0249k;
import a.a.a.a.b.RunnableC0250l;
import a.a.a.a.b.RunnableC0251m;
import a.a.a.a.b.RunnableC0253o;
import a.a.a.a.b.RunnableC0257t;
import a.a.a.a.b.RunnableC0258u;
import a.a.a.a.b.RunnableC0261x;
import a.a.a.a.b.RunnableC0262y;
import a.a.a.a.b.RunnableC0263z;
import a.a.a.a.b.i.C0232a;
import a.a.a.a.b.i.C0233b;
import a.a.a.a.b.i.J;
import a.a.a.a.b.i.u;
import a.a.a.a.b.l.c;
import a.a.a.a.b.m.b;
import a.a.a.a.b.na;
import a.a.a.a.b.oa;
import a.a.a.a.b.r;
import aisble.BleManager;
import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanFilter;
import aisscanner.ScanRecord;
import aisscanner.ScanSettings;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.NonNull;
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
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.bean.MeshNodeStatus;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;
import com.alibaba.ailabs.iot.mesh.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.contant.MeshUtConst$MeshErrorEnum;
import com.alibaba.ailabs.iot.mesh.delegate.OnReadyToBindHandler;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.provision.WiFiConfigReplyParser;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback;
import com.alibaba.ailabs.iot.mesh.ut.UtError;
import com.alibaba.ailabs.iot.mesh.utils.AliMeshUUIDParserUtil;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import datasource.bean.AddPublish;
import datasource.bean.BindModel;
import datasource.bean.ConfigurationData;
import datasource.bean.DeviceStatus;
import datasource.bean.IotDevice;
import datasource.bean.ProvisionAppKey;
import datasource.bean.ProvisionInfo;
import datasource.bean.ProvisionNetKey;
import datasource.bean.SigmeshKey;
import datasource.bean.SubscribeGroupAddr;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.AddressUtils;
import meshprovisioner.utils.MeshParserUtils;

/* loaded from: classes2.dex */
public class DeviceProvisioningWorker implements BleMeshManagerCallbacks, p, InterfaceC0338m, InterfaceC0326a, FastProvisionConfigCallback, FastProvisionTransportCallback, q {

    /* renamed from: a, reason: collision with root package name */
    public static final Semaphore f8692a = new Semaphore(f());
    public MeshService.OnDisconnectListener A;
    public OnReadyToBindHandler C;
    public q D;
    public a E;
    public ExtendedBluetoothDevice G;
    public boolean H;
    public boolean J;
    public boolean O;
    public J P;
    public Runnable S;
    public C0233b U;
    public OnProvisionFinishedListener W;
    public Map<String, Object> Y;

    /* renamed from: c, reason: collision with root package name */
    public Context f8694c;

    /* renamed from: d, reason: collision with root package name */
    public BleMeshManager f8695d;

    /* renamed from: e, reason: collision with root package name */
    public C0337l f8696e;

    /* renamed from: f, reason: collision with root package name */
    public boolean f8697f;

    /* renamed from: m, reason: collision with root package name */
    public s f8704m;

    /* renamed from: q, reason: collision with root package name */
    public BluetoothDevice f8708q;

    /* renamed from: r, reason: collision with root package name */
    public ScanRecord f8709r;

    /* renamed from: s, reason: collision with root package name */
    public Handler f8710s;

    /* renamed from: t, reason: collision with root package name */
    public SparseArray<SigmeshKey> f8711t;

    /* renamed from: u, reason: collision with root package name */
    public List<BindModel> f8712u;

    /* renamed from: v, reason: collision with root package name */
    public List<Integer> f8713v;

    /* renamed from: w, reason: collision with root package name */
    public List<Integer> f8714w;

    /* renamed from: x, reason: collision with root package name */
    public List<SubscribeGroupAddr> f8715x;

    /* renamed from: y, reason: collision with root package name */
    public List<AddPublish> f8716y;

    /* renamed from: z, reason: collision with root package name */
    public UnprovisionedMeshNodeData f8717z;

    /* renamed from: b, reason: collision with root package name */
    public String f8693b = DeviceProvisioningWorker.class.getSimpleName();

    /* renamed from: g, reason: collision with root package name */
    public boolean f8698g = false;

    /* renamed from: h, reason: collision with root package name */
    public boolean f8699h = false;

    /* renamed from: i, reason: collision with root package name */
    public BaseMeshNode f8700i = null;

    /* renamed from: j, reason: collision with root package name */
    public boolean f8701j = false;

    /* renamed from: k, reason: collision with root package name */
    public boolean f8702k = false;

    /* renamed from: l, reason: collision with root package name */
    public boolean f8703l = false;

    /* renamed from: n, reason: collision with root package name */
    public ConcurrentLinkedQueue<Integer> f8705n = new ConcurrentLinkedQueue<>();

    /* renamed from: o, reason: collision with root package name */
    public ConcurrentLinkedQueue<String> f8706o = new ConcurrentLinkedQueue<>();

    /* renamed from: p, reason: collision with root package name */
    public boolean f8707p = false;
    public boolean B = false;
    public int F = 0;
    public List<String> I = new ArrayList();
    public SparseArray<Integer> K = new SparseArray<>();
    public SparseArray<Byte> L = new SparseArray<>();
    public boolean M = true;
    public boolean N = false;
    public CountDownLatch Q = null;
    public AtomicBoolean R = new AtomicBoolean(false);
    public volatile boolean T = false;
    public boolean V = true;
    public u X = null;
    public Runnable Z = null;
    public final int aa = 40000;
    public WiFiConfigReplyParser ba = null;
    public final Runnable ca = new RunnableC0253o(this);
    public final Runnable da = new RunnableC0262y(this);
    public final Runnable ea = new RunnableC0263z(this);
    public final ScanCallback fa = new A(this);

    public interface OnProvisionFinishedListener {
        boolean OnProvisionFinished(BluetoothDevice bluetoothDevice, boolean z2);
    }

    private class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public String f8718a;

        /* renamed from: b, reason: collision with root package name */
        public int f8719b;

        /* renamed from: c, reason: collision with root package name */
        public ProvisionedMeshNode f8720c;

        /* renamed from: d, reason: collision with root package name */
        public boolean f8721d;

        public a(String str, int i2, ProvisionedMeshNode provisionedMeshNode, boolean z2) {
            this.f8718a = str;
            this.f8719b = i2;
            this.f8720c = provisionedMeshNode;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (DeviceProvisioningWorker.this.F >= 2) {
                if (this.f8721d) {
                    DeviceProvisioningWorker.this.onAppKeyStatusReceived(this.f8720c, true, 0, 0, this.f8719b);
                    return;
                }
                return;
            }
            a.a.a.a.b.m.a.c(DeviceProvisioningWorker.this.f8693b, "retry to add app key: appKeyIndex = " + this.f8719b + ", mAppKey = " + this.f8718a);
            DeviceProvisioningWorker.this.f8696e.a(this.f8720c, this.f8719b, this.f8718a);
            DeviceProvisioningWorker deviceProvisioningWorker = DeviceProvisioningWorker.this;
            deviceProvisioningWorker.F = deviceProvisioningWorker.F + 1;
            this.f8721d = DeviceProvisioningWorker.this.F >= 2;
            DeviceProvisioningWorker.this.f8710s.postDelayed(this, 500L);
            a.a.a.a.b.m.a.a(DeviceProvisioningWorker.this.f8693b, "addAppKey");
        }
    }

    public DeviceProvisioningWorker(Context context, q qVar, SparseArray<SigmeshKey> sparseArray, byte[] bArr, OnReadyToBindHandler onReadyToBindHandler, C0233b c0233b) {
        this.C = null;
        this.P = null;
        this.f8694c = context;
        this.D = qVar;
        this.f8711t = sparseArray;
        this.C = onReadyToBindHandler;
        this.f8693b += (hashCode() % 1000000);
        this.f8712u = new ArrayList();
        this.f8710s = new Handler(Looper.getMainLooper());
        BleMeshManager bleMeshManager = new BleMeshManager(this.f8694c, String.valueOf(hashCode() % 1000000));
        this.f8695d = bleMeshManager;
        bleMeshManager.setGattCallbacks(this);
        C0337l c0337l = new C0337l(this.f8694c);
        this.f8696e = c0337l;
        c0337l.a((InterfaceC0338m) this);
        this.f8696e.a((p) this);
        this.f8696e.a((InterfaceC0326a) this);
        this.f8696e.h(bArr);
        this.f8696e.a((q) this);
        this.f8704m = this.f8696e.d();
        this.U = c0233b;
        J j2 = new J(String.valueOf(hashCode() % 1000000));
        this.P = j2;
        j2.a(this.f8694c, this, this.f8704m, this, this, this);
        this.K.put(13936641, 13959592);
        this.K.put(13543425, 13435304);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void advertiseAppKey(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        b(MeshNodeStatus.COMPOSITION_DATA_STATUS_RECEIVED.getState());
        if (this.f8707p) {
            this.f8707p = false;
            this.f8710s.postDelayed(new RunnableC0249k(this, provisionedMeshNode), 500L);
        }
    }

    @Override // b.InterfaceC0326a
    public void checkConfirmationValueMatches(UnprovisionedMeshNode unprovisionedMeshNode, UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, byte[] bArr3, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        a.a.a.a.b.m.a.a(this.f8693b, "checkConfirmationValueMatches");
        this.f8700i = unprovisionedMeshNode;
        this.f8717z = unprovisionedMeshNodeData;
        String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
        int productId = unprovisionedMeshNodeData.getProductId();
        String lowerCase = MeshParserUtils.bytesToHex(bArr3, false).toLowerCase();
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr2, false);
        String lowerCase2 = MeshParserUtils.bytesToHex(bArr, false).toLowerCase();
        String strBytesToHex2 = unprovisionedMeshNodeData.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false) : "";
        na.a().a(deviceMac, strBytesToHex, lowerCase, lowerCase2, productId + "", strBytesToHex2, new C0231i(this, interfaceC0015a));
    }

    @Override // b.InterfaceC0326a
    public void generateConfirmationValue(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, InterfaceC0326a.b bVar) {
        a.a.a.a.b.m.a.a(this.f8693b, "generateConfirmationValue");
        this.f8717z = unprovisionedMeshNodeData;
        String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
        String strBytesToHex = MeshParserUtils.bytesToHex(bArr2, false);
        na.a().a(deviceMac, strBytesToHex.toLowerCase(), MeshParserUtils.bytesToHex(bArr, false), unprovisionedMeshNodeData.getProductId() + "", this.f8717z.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(this.f8717z.getDeviceUuid(), false) : "", new C0230h(this, bVar, unprovisionedMeshNodeData));
    }

    @Override // b.q
    public ProvisionedMeshNode getMeshNode(byte[] bArr, byte[] bArr2) {
        return (ProvisionedMeshNode) G.a().d().a(bArr, bArr2);
    }

    @Override // b.InterfaceC0338m
    public int getMtu() {
        int mtuSize = a.a.a.a.b.d.a.f1336c ? 35 : this.f8695d.getMtuSize();
        a.a.a.a.b.m.a.a(this.f8693b, "getMtu, MtuSize: " + mtuSize);
        return mtuSize;
    }

    @Override // b.q
    public void onAppKeyAddSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "ProvisionedMeshNode, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.SENDING_APP_KEY_ADD.getState());
    }

    @Override // b.q
    public void onAppKeyBindSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onAppKeyBindSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.APP_BIND_SENT.getState());
    }

    @Override // b.q
    public void onAppKeyBindStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4, int i5) {
        a.a.a.a.b.m.a.a(this.f8693b, "onAppKeyBindStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName() + ", status:" + i2);
        if (!z2) {
            a(MeshUtConst$MeshErrorEnum.APPKEY_BIND_ERROR, "appKey bind status: " + i2);
            return;
        }
        if (!this.f8706o.isEmpty()) {
            a(provisionedMeshNode);
            return;
        }
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.APP_BIND_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, i3);
        intent.putExtra("EXTRA_APP_KEY_INDEX", i4);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i5);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        this.f8710s.postDelayed(new RunnableC0250l(this, provisionedMeshNode, i3, i4), 500L);
    }

    @Override // b.q
    public void onAppKeyStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, int i3, int i4) {
        a.a.a.a.b.m.a.a(this.f8693b, "onAppKeyStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName() + ", status: " + i2);
        this.f8710s.removeCallbacks(this.E);
        this.E = null;
        this.F = 0;
        if (!z2) {
            a(MeshUtConst$MeshErrorEnum.APPKEY_ADD_ERROR, "appKey add status: " + i2);
            return;
        }
        SigmeshKey sigmeshKey = this.f8711t.get(i3);
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
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        a.a.a.a.b.m.a.a(this.f8693b, "size of appKey Queue: " + this.f8706o.size());
        if (this.f8712u.size() == 0) {
            if (this.f8706o.isEmpty()) {
                a(provisionedMeshNode.getUnicastAddress(), (DeviceStatus) null);
                return;
            } else {
                a(provisionedMeshNode);
                return;
            }
        }
        BindModel bindModel = this.f8712u.get(0);
        if (bindModel == null || bindModel.getModelElementAddr() == null) {
            return;
        }
        Integer modelElementAddr = bindModel.getModelElementAddr();
        List<Integer> modelIds = bindModel.getModelIds();
        this.f8714w = modelIds;
        if (modelElementAddr == null || modelIds == null) {
            return;
        }
        a(provisionedMeshNode, AddressUtils.getUnicastAddressBytes(modelElementAddr.intValue()), i4, this.f8714w);
    }

    @Override // b.q
    public void onAppKeyUnbindSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onAppKeyUnbindSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.APP_UNBIND_SENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onBatteryValueReceived(BluetoothDevice bluetoothDevice, int i2) {
        a.a.a.a.b.m.a.a(this.f8693b, "onBatteryValueReceived...");
    }

    @Override // b.q
    public void onBlockAcknowledgementReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onBlockAcknowledgementReceived, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.BLOCK_ACKNOWLEDGEMENT_RECEIVED.getState());
    }

    @Override // b.q
    public void onBlockAcknowledgementSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onBlockAcknowledgementSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.SENDING_BLOCK_ACKNOWLEDGEMENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onBonded(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f8693b, "onBonded...");
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingFailed(BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingRequired(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f8693b, "onBondingRequired...");
    }

    @Override // b.q
    public void onCommonMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, String str, byte[] bArr2, a.a.a.a.b.h.a aVar) throws NumberFormatException {
        int i2 = Integer.parseInt(str, 16);
        byte[] opCodeBytes = Utils.getOpCodeBytes(i2);
        String strConvertDevIdToIotId = MeshDeviceInfoManager.getInstance().convertDevIdToIotId(provisionedMeshNode.getDevId());
        a.a.a.a.b.m.a.a(this.f8693b, String.format("Common message status received from %s, opcode: %s, parameters: %s", strConvertDevIdToIotId, str, ConvertUtils.bytes2HexString(bArr2)));
        TgMeshManager.getInstance().notifyMeshMessage(bArr, str, bArr2, provisionedMeshNode.getNetworkKey(), provisionedMeshNode.getSequenceNumber(), strConvertDevIdToIotId);
        a(bArr, str, bArr2);
        Integer num = this.K.get(i2);
        if (num != null) {
            Byte b2 = this.L.get(Utils.byteArray2Int(bArr));
            Byte bValueOf = (bArr2 == null || bArr2.length <= 0) ? null : Byte.valueOf(bArr2[0]);
            if (bValueOf != null) {
                if (b2 != null && b2.byteValue() == bValueOf.byteValue()) {
                    return;
                }
                this.L.put(Utils.byteArray2Int(bArr), bValueOf);
                byte[] opCodeBytes2 = Utils.getOpCodeBytes(num.intValue());
                byte[] bArr3 = {bArr2[0]};
                String str2 = this.f8704m.c().get(0);
                a.a.a.a.b.m.a.c(this.f8693b, "Ack: opcode(" + ConvertUtils.bytes2HexString(opCodeBytes2) + "), parameter(" + ConvertUtils.bytes2HexString(bArr3) + ")");
                if (this.M) {
                    Map<Integer, String> addedAppKeys = provisionedMeshNode.getAddedAppKeys();
                    if (addedAppKeys != null) {
                        Integer next = addedAppKeys.keySet().iterator().next();
                        int iIntValue = next.intValue();
                        String str3 = addedAppKeys.get(next);
                        K kE = G.a().d().h(provisionedMeshNode.getNetworkKey()).e();
                        if (kE != null) {
                            kE.g().a(provisionedMeshNode, true, str3, bArr, false, iIntValue, Utils.byteArray2Int(opCodeBytes2), bArr3);
                        } else {
                            a.a.a.a.b.m.a.b(this.f8693b, "subnets is null");
                            this.f8696e.a(provisionedMeshNode, true, str3, bArr, false, iIntValue, Utils.byteArray2Int(opCodeBytes2), bArr3);
                        }
                    }
                } else {
                    this.f8696e.a(provisionedMeshNode, true, str2, bArr, false, 0, Utils.byteArray2Int(opCodeBytes2), bArr3);
                }
            }
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("opcode", (Object) Utils.bytes2HexString(opCodeBytes));
        jSONObject.put(PushConstants.PARAMS, (Object) MeshParserUtils.bytesToHex(bArr2, false));
        String jSONString = jSONObject.toJSONString();
        int unicastAddressInt = AddressUtils.getUnicastAddressInt(bArr);
        jSONObject.put("srcAddr", (Object) Integer.valueOf(unicastAddressInt));
        Intent intent = new Intent(Utils.ACTION_COMMON_MESSAGE_STATUS_RECEIVED);
        intent.putExtra(Utils.EXTRA_STATUS, jSONObject.toJSONString());
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setUserId(na.a().b());
        String strC = na.a().c();
        deviceStatus.setUuid(strC);
        deviceStatus.setUnicastAddress(unicastAddressInt);
        deviceStatus.setStatus(jSONString);
        deviceStatus.setIotId(strConvertDevIdToIotId);
        c.a(unicastAddressInt, 2, false);
        na.a().a(strC, Collections.singletonList(deviceStatus), new C0252n(this, unicastAddressInt));
    }

    @Override // b.q
    public void onCompositionDataStatusReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onCompositionDataStatusReceived, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.COMPOSITION_DATA_STATUS_RECEIVED.getState());
        if (this.f8707p) {
            this.f8707p = false;
            String strPoll = this.f8706o.poll();
            if (strPoll == null) {
                a.a.a.a.b.m.a.d(this.f8693b, "Empty appKey queue");
                return;
            }
            Integer numPoll = this.f8705n.poll();
            if (numPoll == null) {
                numPoll = 0;
            }
            Integer num = numPoll;
            a.a.a.a.b.m.a.c(this.f8693b, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + strPoll);
            Handler handler = this.f8710s;
            a aVar = new a(strPoll, num.intValue(), provisionedMeshNode, false);
            this.E = aVar;
            handler.postDelayed(aVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            this.f8696e.a(provisionedMeshNode, num.intValue(), strPoll);
            a.a.a.a.b.m.a.a(this.f8693b, "addAppKey");
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataReceived(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        String str = this.f8693b;
        StringBuilder sb = new StringBuilder();
        sb.append("onDataReceived, device: ");
        sb.append(bluetoothDevice.getName());
        sb.append(", mac: ");
        sb.append(bluetoothDevice.getAddress());
        sb.append(", mtu: ");
        sb.append(i2);
        sb.append(", pdu length: ");
        sb.append(bArr == null ? 0 : bArr.length);
        a.a.a.a.b.m.a.a(str, sb.toString());
        BaseMeshNode baseMeshNode = this.f8700i;
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.d(this.f8693b, "provision mesh node is null");
        } else {
            this.f8696e.a(baseMeshNode, i2, bArr, (a.a.a.a.b.h.a) null);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataSent(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        String str = this.f8693b;
        StringBuilder sb = new StringBuilder();
        sb.append("onDataSent, device: ");
        sb.append(bluetoothDevice.getName());
        sb.append(", mac: ");
        sb.append(bluetoothDevice.getAddress());
        sb.append(", mtu: ");
        sb.append(i2);
        sb.append(", pdu length: ");
        sb.append(bArr == null ? 0 : bArr.length);
        a.a.a.a.b.m.a.a(str, sb.toString());
        BaseMeshNode baseMeshNode = this.f8700i;
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.d(this.f8693b, "provision mesh node is null");
        } else {
            this.f8696e.a(baseMeshNode, i2, bArr);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnected(BluetoothDevice bluetoothDevice) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        a.a.a.a.b.m.a.a(this.f8693b, "onDeviceConnected to device: " + bluetoothDevice.getName());
        if (this.H) {
            return;
        }
        this.f8695d.refreshGattCacheImmediately();
        o();
        this.f8708q = bluetoothDevice;
        this.f8697f = true;
        if (this.O) {
            a(true, (String) null);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnecting(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f8693b, "onDeviceConnecting...");
        b(this.f8694c.getString(R.string.state_connecting));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnected(BluetoothDevice bluetoothDevice) throws InterruptedException {
        a.a.a.a.b.m.a.a(this.f8693b, "onDeviceDisconnected...");
        j();
        a(false, UtError.MESH_DISCONNECT.getMsg());
        b(this.f8694c.getString(R.string.state_disconnected));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnecting(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f8693b, "onDeviceDisconnecting...");
        this.f8697f = false;
        b(this.f8694c.getString(R.string.state_disconnecting));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceNotSupported(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.b(this.f8693b, "onDeviceNotSupported...");
        if (this.f8698g) {
            return;
        }
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.REQUEST_FAILED.getState());
        MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.DEVICE_NOT_SUPPORT_ERROR;
        intent.putExtra(Utils.EXTRA_REQUEST_FAIL_MSG, meshUtConst$MeshErrorEnum.getErrorMsg());
        String str = "";
        if (this.f8717z != null) {
            str = this.f8717z.getProductId() + "";
        }
        b.a("ALSMesh", "ble", str, false, this.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)), "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceReady(BluetoothDevice bluetoothDevice) {
        BaseMeshNode baseMeshNode;
        a.a.a.a.b.m.a.a(this.f8693b, "onDeviceReady...");
        if (this.B) {
            a.a.a.a.b.m.a.d(this.f8693b, "onDeviceReady, But User terminated the process");
            return;
        }
        Intent intent = new Intent(Utils.ACTION_ON_DEVICE_READY);
        intent.putExtra(Utils.EXTRA_DATA, true);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        if (this.O) {
            return;
        }
        if (!this.f8695d.isProvisioningComplete()) {
            a.a.a.a.b.m.a.a(this.f8693b, "Provisioning Not Complete");
            this.f8702k = true;
            this.f8698g = false;
            this.f8699h = false;
            i();
            return;
        }
        if (this.f8699h || (baseMeshNode = this.f8700i) == null) {
            a.a.a.a.b.m.a.a(this.f8693b, "Configuration Not Complete");
            return;
        }
        baseMeshNode.setBluetoothDeviceAddress(bluetoothDevice.getAddress());
        BaseMeshNode baseMeshNode2 = this.f8700i;
        if (baseMeshNode2 == null || baseMeshNode2.getDeviceKey() == null || this.f8700i.getUnicastAddress() == null) {
            return;
        }
        this.f8701j = true;
        this.f8707p = true;
        a.a.a.a.b.m.a.a(this.f8693b, "getCompositionData");
        b("getCompositionData");
        onCompositionDataStatusReceived((ProvisionedMeshNode) this.f8700i);
    }

    @Override // aisble.BleManagerCallbacks
    public void onError(BluetoothDevice bluetoothDevice, String str, int i2) {
        a.a.a.a.b.m.a.b(this.f8693b, "onError: " + str + ", provision finished ? " + this.T);
        if (this.T || BleManager.BleManagerGattCallback.ERROR_WRITE_CHARACTERISTIC.equals(str) || BleManager.BleManagerGattCallback.ERROR_WRITE_DESCRIPTOR.equalsIgnoreCase(str)) {
            return;
        }
        a(MeshUtConst$MeshErrorEnum.CALLBACK_ERROR, str);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onFastProvisionDataSend(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f8693b, "onFastProvisionDataSend: " + ConvertUtils.bytes2HexString(bArr));
        this.f8696e.a(baseMeshNode, 18, bArr);
    }

    @Override // b.q
    public void onGenericLevelGetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericLevelGetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericLevelSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericLevelSetUnacknowledgedSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericLevelStatusReceived(ProvisionedMeshNode provisionedMeshNode, int i2, int i3, int i4, int i5) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericLevelStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_LEVEL_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_PRESENT_STATE, i2);
        intent.putExtra(Utils.EXTRA_GENERIC_TARGET_STATE, i3);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_STEPS, i4);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_RES, i5);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGenericOnOffGetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericOnOffGetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onGenericOnOffSetUnacknowledgedSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericOnOffSetUnacknowledgedSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_ON_OFF_SET_UNACK, "");
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGenericOnOffStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, Boolean bool, int i2, int i3) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGenericOnOffStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_GENERIC_ON_OFF_STATE);
        intent.putExtra(Utils.EXTRA_GENERIC_PRESENT_STATE, z2);
        intent.putExtra(Utils.EXTRA_GENERIC_TARGET_STATE, bool);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_STEPS, i2);
        intent.putExtra(Utils.EXTRA_GENERIC_TRANSITION_RES, i3);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // b.q
    public void onGetCompositionDataSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onGetCompositionDataSent, ProvisionedMeshNode: " + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.COMPOSITION_DATA_GET_SENT.getState());
    }

    @Override // aisble.BleManagerCallbacks
    public void onLinkLossOccurred(BluetoothDevice bluetoothDevice) throws InterruptedException {
        a.a.a.a.b.m.a.a(this.f8693b, "onLinklossOccur...");
        this.f8695d.close();
        J j2 = this.P;
        if (j2 != null) {
            j2.a(new F(this));
            this.P.b();
        }
        a(false, UtError.MESH_LINK_LOSS_OCCURRED.getMsg());
        this.H = false;
        this.f8698g = false;
        this.f8699h = false;
        b(this.f8694c.getString(R.string.state_linkloss_occur));
        j();
    }

    @Override // b.q
    public void onMeshNodeResetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onMeshNodeResetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_DATA_NODE_RESET, "");
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // b.q
    public void onMeshNodeResetStatusReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onMeshNodeResetStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.NODE_RESET_STATUS_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningAuthenticationInputRequested(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningAuthenticationInputRequested, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_AUTHENTICATION_INPUT_WAITING.getState());
    }

    @Override // b.p
    public void onProvisioningCapabilitiesReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningCapabilitiesReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        c();
        this.f8698g = false;
        this.f8699h = false;
        this.f8696e.a(unprovisionedMeshNode);
        a.a.a.a.b.m.a.a(this.f8693b, "startProvisioning");
        c(MeshNodeStatus.PROVISIONING_CAPABILITIES.getState());
    }

    @Override // b.p
    public void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode) throws IllegalAccessException, InterruptedException, IllegalArgumentException, InvocationTargetException {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningComplete, provisionedMeshNode: " + provisionedMeshNode.getNodeName());
        if (this.B) {
            a.a.a.a.b.m.a.d(this.f8693b, "onProvisioningComplete, But user terminated the process");
            return;
        }
        a.a.a.a.b.m.a.a(this.f8693b, "provision complete device isSupportGatt:" + provisionedMeshNode.getSupportFastGattProvision());
        provisionedMeshNode.setDevId(MeshParserUtils.bytesToHex(this.f8717z.getDeviceUuid(), false).toLowerCase());
        G.a().d().a(provisionedMeshNode, true, true);
        this.f8700i = provisionedMeshNode;
        n();
        a(provisionedMeshNode, new C0228f(this));
    }

    @Override // b.p
    public void onProvisioningConfirmationReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningConfirmationReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_CONFIRMATION_RECEIVED.getState());
        c();
    }

    @Override // b.p
    public void onProvisioningConfirmationSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningConfirmationSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_CONFIRMATION_SENT.getState());
        a(2000);
    }

    @Override // b.p
    public void onProvisioningDataSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningDataSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_DATA_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningFailed(UnprovisionedMeshNode unprovisionedMeshNode, int i2) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningFailed, meshNode: " + unprovisionedMeshNode.getNodeName());
        if (this.T) {
            return;
        }
        this.f8698g = false;
        a(MeshUtConst$MeshErrorEnum.ILLEGAL_PROVISION_DATA_RECEIVED, "inner error code: " + i2);
    }

    @Override // b.p
    public void onProvisioningInputCompleteSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningInputCompleteSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_INPUT_COMPLETE.getState());
    }

    @Override // b.p
    public void onProvisioningInviteSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningInviteSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        this.f8700i = unprovisionedMeshNode;
        c(MeshNodeStatus.PROVISIONING_INVITE.getState());
        a(2000);
    }

    @Override // b.p
    public void onProvisioningPublicKeyReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningPublicKeyReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_PUBLIC_KEY_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningPublicKeySent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningPublicKeySent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_PUBLIC_KEY_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningRandomReceived(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningRandomReceived, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_RANDOM_RECEIVED.getState());
    }

    @Override // b.p
    public void onProvisioningRandomSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningRandomSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_RANDOM_SENT.getState());
    }

    @Override // b.p
    public void onProvisioningStartSent(UnprovisionedMeshNode unprovisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisioningStartSent, meshNode: " + unprovisionedMeshNode.getNodeName());
        c(MeshNodeStatus.PROVISIONING_START.getState());
    }

    @Override // b.q
    public void onPublicationSetSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onPublicationSetSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.PUBLISH_ADDRESS_SET_SENT.getState());
    }

    @Override // b.q
    public void onPublicationStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3) {
        a.a.a.a.b.m.a.a(this.f8693b, "onPublicationStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.PUBLISH_ADDRESS_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, bArr);
        intent.putExtra(Utils.EXTRA_PUBLISH_ADDRESS, bArr2);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i3);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        this.f8710s.postDelayed(new RunnableC0251m(this, provisionedMeshNode, bArr), 500L);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onReceiveFastProvisionData(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f8693b, "onReceiveFastProvisionData " + ConvertUtils.bytes2HexString(bArr));
        BaseMeshNode baseMeshNode2 = this.f8700i;
        if (baseMeshNode2 == null) {
            return;
        }
        baseMeshNode2.setIsProvisioned(true);
        if (this.f8700i == null) {
            return;
        }
        this.H = false;
        this.f8697f = true;
        this.f8696e.a(baseMeshNode, 18, bArr, (a.a.a.a.b.h.a) null);
    }

    @Override // aisble.BleManagerCallbacks
    public void onServicesDiscovered(@NonNull BluetoothDevice bluetoothDevice, boolean z2) {
        a.a.a.a.b.m.a.a(this.f8693b, "onServicesDiscovered...");
        b(this.f8694c.getString(R.string.state_initializing));
        if (this.H) {
            this.H = false;
        } else if (this.O) {
            d();
        } else if (this.B) {
            a.a.a.a.b.m.a.d(this.f8693b, "onServicesDiscovered, But User terminated the process");
        }
    }

    @Override // b.q
    public void onSubscriptionAddSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onSubscriptionAddSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.SUBSCRIPTION_ADD_SENT.getState());
    }

    @Override // b.q
    public void onSubscriptionDeleteSent(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onSubscriptionDeleteSent, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        b(MeshNodeStatus.SUBSCRIPTION_DELETE_SENT.getState());
    }

    @Override // b.q
    public void onSubscriptionStatusReceived(ProvisionedMeshNode provisionedMeshNode, boolean z2, int i2, byte[] bArr, byte[] bArr2, int i3) {
        a.a.a.a.b.m.a.a(this.f8693b, "onSubscriptionStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        if (!this.f8698g || this.f8699h) {
            return;
        }
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, MeshNodeStatus.SUBSCRIPTION_STATUS_RECEIVED.getState());
        intent.putExtra(Utils.EXTRA_IS_SUCCESS, z2);
        intent.putExtra(Utils.EXTRA_STATUS, i2);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, bArr);
        intent.putExtra(Utils.EXTRA_PUBLISH_ADDRESS, bArr2);
        intent.putExtra(Utils.EXTRA_MODEL_ID, i3);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        List<SubscribeGroupAddr> list = this.f8715x;
        if (list == null || list.size() <= 0) {
            a(provisionedMeshNode.getUnicastAddress(), (DeviceStatus) null);
            return;
        }
        SubscribeGroupAddr subscribeGroupAddrRemove = this.f8715x.remove(0);
        if (subscribeGroupAddrRemove == null || subscribeGroupAddrRemove.getGroupAddr() == null || subscribeGroupAddrRemove.getModelId() == null) {
            return;
        }
        Integer groupAddr = subscribeGroupAddrRemove.getGroupAddr();
        this.f8696e.a(provisionedMeshNode, bArr, new byte[]{(byte) ((groupAddr.intValue() >> 8) & 255), (byte) (groupAddr.intValue() & 255)}, subscribeGroupAddrRemove.getModelId().intValue());
    }

    @Override // b.q
    public void onTransactionFailed(ProvisionedMeshNode provisionedMeshNode, int i2, boolean z2) {
        a.a.a.a.b.m.a.a(this.f8693b, "onTransactionFailed, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_TRANSACTION_STATE);
        intent.putExtra(Utils.EXTRA_ELEMENT_ADDRESS, i2);
        intent.putExtra(Utils.EXTRA_DATA, z2);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // b.q
    public void onUnknownPduReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.a(this.f8693b, "onUnknownPduReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
    }

    @Override // b.q
    public void onVendorModelMessageStatusReceived(ProvisionedMeshNode provisionedMeshNode, byte[] bArr) {
        a.a.a.a.b.m.a.a(this.f8693b, "onVendorModelMessageStatusReceived, ProvisionedMeshNode" + provisionedMeshNode.getNodeName());
        Intent intent = new Intent(Utils.ACTION_VENDOR_MODEL_MESSAGE_STATE);
        intent.putExtra(Utils.EXTRA_DATA, bArr);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void requestConfigMsg(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        provisionedMeshNode.setDevId(MeshParserUtils.bytesToHex(this.f8717z.getDeviceUuid(), false));
        G.a().d().a(provisionedMeshNode, true, true);
        this.f8700i = provisionedMeshNode;
        a(provisionedMeshNode, new C0248j(this, iActionListener));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback
    public void requestProvisionMsg(ScanRecord scanRecord) {
        a.a.a.a.b.m.a.a(this.f8693b, "requestProvisionMsg...");
        b(this.f8694c.getString(R.string.state_initializing));
        if (this.H) {
            this.H = false;
            return;
        }
        if (this.O) {
            d();
            return;
        }
        byte[] serviceData = this.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(serviceData);
        a(serviceData, unprovisionedMeshNodeData, unprovisionedMeshNodeData.getDeviceMac(), unprovisionedMeshNodeData.getProductId() + "");
        this.f8698g = false;
        this.f8699h = false;
    }

    @Override // b.InterfaceC0338m
    @RequiresApi(api = 18)
    public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        J j2;
        String str = this.f8693b;
        StringBuilder sb = new StringBuilder();
        sb.append("sendPdu, meshNode: ");
        sb.append(baseMeshNode.getNodeName());
        sb.append(", mac: ");
        sb.append(baseMeshNode.getBluetoothDeviceAddress());
        sb.append(", pdu length: ");
        sb.append(bArr == null ? 0 : bArr.length);
        a.a.a.a.b.m.a.a(str, sb.toString());
        if (!baseMeshNode.getSupportFastProvision()) {
            this.f8695d.sendPdu(bArr);
            c.a(baseMeshNode.getUnicastAddressInt(), "0");
            return;
        }
        if (this.f8695d.getConnectState() == 2) {
            this.f8695d.sendPdu(bArr);
        } else if (a.a.a.a.b.d.a.f1334a && (j2 = this.P) != null) {
            j2.a(baseMeshNode, bArr);
        }
        c.a(baseMeshNode.getUnicastAddressInt(), "1");
    }

    @Override // aisble.BleManagerCallbacks
    public boolean shouldEnableBatteryLevelNotifications(BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(this.f8693b, "shouldEnableBatteryLevelNotifications...");
        return false;
    }

    public static int f() {
        String str = Build.MODEL;
        if ("SM-N9760".equals(str) || "SM-G975U1".equals(str) || "SM-A6060".equals(str)) {
            return 3;
        }
        return "Pixel 5".equals(str) ? 2 : 1;
    }

    public final int g() {
        return "VOG-AL00".equals(Build.MODEL) ? 3000 : 7000;
    }

    public String h() {
        ExtendedBluetoothDevice extendedBluetoothDevice;
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = this.f8717z;
        String lowerCase = (unprovisionedMeshNodeData == null || TextUtils.isEmpty(unprovisionedMeshNodeData.getDeviceMac())) ? null : this.f8717z.getDeviceMac().toLowerCase();
        return (!TextUtils.isEmpty(lowerCase) || (extendedBluetoothDevice = this.G) == null || extendedBluetoothDevice.getAddress() == null) ? lowerCase : this.G.getAddress().toLowerCase();
    }

    public final void i() {
        a.a.a.a.b.m.a.a(this.f8693b, "handle device ready event in provisioning step, provisioning info ready flag: " + this.f8703l);
        if (this.f8703l) {
            a.a.a.a.b.m.a.a(this.f8693b, "identifyNode after device(GATT connected or adv) is ready");
            new Thread(new E(this)).start();
        }
    }

    public final void j() throws InterruptedException {
        BleMeshManager bleMeshManager = this.f8695d;
        if (!(bleMeshManager == null ? this.f8698g : bleMeshManager.isProvisioningComplete())) {
            this.f8697f = false;
        } else if (this.H) {
            Intent intent = new Intent(Utils.ACTION_IS_RECONNECTING);
            intent.putExtra(Utils.EXTRA_DATA, true);
            LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
            a.a.a.a.b.m.a.a(this.f8693b, "deviceTobeProvision:" + this.G);
            b(this.G, true);
        } else if (this.f8699h) {
            this.f8697f = false;
        }
        MeshService.OnDisconnectListener onDisconnectListener = this.A;
        if (onDisconnectListener != null) {
            onDisconnectListener.onDisconnected();
            this.A = null;
        }
    }

    public final boolean k() {
        ScanRecord scanRecord = this.f8709r;
        if (scanRecord != null) {
            return AliMeshUUIDParserUtil.isComboMesh(scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)));
        }
        Map<String, Object> map = this.Y;
        return map != null && map.containsKey("ssid");
    }

    public final boolean l() {
        ExtendedBluetoothDevice extendedBluetoothDevice = this.G;
        if (extendedBluetoothDevice == null || this.U == null) {
            return false;
        }
        return extendedBluetoothDevice.getAddress().equalsIgnoreCase(this.U.a());
    }

    public final void m() {
        boolean zA;
        BaseMeshNode baseMeshNode;
        J j2;
        this.T = true;
        G.a().d().a((ProvisionedMeshNode) this.f8700i, true, true);
        if (k()) {
            a.a.a.a.b.m.a.a(this.f8693b, "Detect current is combo device");
            b((ProvisionedMeshNode) this.f8700i);
            return;
        }
        u.a aVarD = G.a().d().d();
        if (aVarD == null) {
            p();
            return;
        }
        K kE = aVarD.e();
        if (kE == null) {
            kE = new K(this.f8694c, aVarD, this.D);
            aVarD.a(kE);
        }
        if (this.G.getAddress().equalsIgnoreCase(this.U.a())) {
            a.a.a.a.b.m.a.c(this.f8693b, "Hit last task, attach connection info to subnetsBiz");
            zA = kE.a(this.f8695d, (ProvisionedMeshNode) this.f8700i, this.P);
        } else {
            zA = false;
        }
        if (zA) {
            this.N = true;
            BLEScannerProxy.getInstance().unlock();
        } else {
            p();
        }
        this.f8695d = new BleMeshManager(this.f8694c);
        if (this.N || (baseMeshNode = this.f8700i) == null || !baseMeshNode.getSupportFastGattProvision() || (j2 = this.P) == null) {
            return;
        }
        j2.a(new C0256s(this));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void n() throws java.lang.IllegalAccessException, java.lang.InterruptedException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r5 = this;
            java.lang.String r0 = r5.f8693b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "prepareToReconnect blemesh is:"
            r1.append(r2)
            com.alibaba.ailabs.iot.mesh.ble.BleMeshManager r2 = r5.f8695d
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L14
            r2 = r4
            goto L15
        L14:
            r2 = r3
        L15:
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            a.a.a.a.b.m.a.a(r0, r1)
            r5.H = r4
            com.alibaba.ailabs.iot.mesh.ble.BleMeshManager r0 = r5.f8695d
            r0.setProvisioningComplete(r4)
            meshprovisioner.BaseMeshNode r0 = r5.f8700i
            if (r0 == 0) goto L7e
            boolean r0 = r0.getSupportFastGattProvision()
            if (r0 == 0) goto L7e
            java.lang.String r0 = r5.f8693b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "deviceTobeProvision:"
            r1.append(r2)
            com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice r2 = r5.G
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            a.a.a.a.b.m.a.a(r0, r1)
            a.a.a.a.b.i.J r0 = r5.P
            r1 = 0
            if (r0 == 0) goto L67
            r0.b()
            a.a.a.a.b.i.J r0 = r5.P
            a.a.a.a.b.i.c.a r0 = r0.f()
            boolean r0 = r0 instanceof a.a.a.a.b.i.c.r
            if (r0 == 0) goto L67
            a.a.a.a.b.i.J r0 = r5.P
            a.a.a.a.b.i.c.a r0 = r0.f()
            a.a.a.a.b.i.c.r r0 = (a.a.a.a.b.i.c.r) r0
            com.alibaba.ailabs.iot.mesh.ble.BleMeshManager r0 = r0.d()
            goto L68
        L67:
            r0 = r1
        L68:
            if (r0 == 0) goto L76
            r0.setProvisioningComplete(r4)
            r0.setGattCallbacks(r5)
            r5.f8695d = r0
            r0.discoveryServices(r3)
            goto L7b
        L76:
            com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice r0 = r5.G
            r5.b(r0, r4)
        L7b:
            r5.A = r1
            goto L87
        L7e:
            meshprovisioner.BaseMeshNode r0 = r5.f8700i
            if (r0 == 0) goto L87
            com.alibaba.ailabs.iot.mesh.ble.BleMeshManager r0 = r5.f8695d
            r0.discoveryServices(r3)
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.ailabs.iot.mesh.DeviceProvisioningWorker.n():void");
    }

    public final void o() {
        if (this.V && this.R.get()) {
            ExtendedBluetoothDevice extendedBluetoothDevice = this.G;
            if (extendedBluetoothDevice != null && extendedBluetoothDevice.getDevice() != null && this.f8717z.isSupportLargeScaleMeshNetwork()) {
                C0224b.b().a(this.G.getDevice(), true);
            }
            a.a.a.a.b.m.a.c(this.f8693b, "Thread(" + Thread.currentThread().getName() + ") release global connection semaphore");
            f8692a.release();
            this.R.set(false);
        }
    }

    public final void p() {
        a.a.a.a.b.m.a.c(this.f8693b, "Reset...");
        this.f8705n.clear();
        this.f8706o.clear();
        this.O = false;
        if (!this.N) {
            if (this.f8695d.getConnectState() == 2) {
                a.a.a.a.b.m.a.a(this.f8693b, "mBleMeshManager.getConnectState() == STATE_CONNECTED");
                this.f8695d.disconnectImmediately();
            } else {
                this.f8695d.disconnect().enqueue();
                this.f8695d.close();
            }
            if (this.P != null) {
                a.a.a.a.b.m.a.a(this.f8693b, "mFastProvisionWorker.reset()");
                this.P.k();
            }
            a.a.a.a.b.i.u uVar = this.X;
            if (uVar != null) {
                uVar.c();
            }
        }
        this.f8710s.removeCallbacks(this.da);
        this.f8710s.removeCallbacks(this.E);
        this.H = false;
        this.f8698g = false;
        this.f8699h = false;
        this.f8696e.g();
        C0224b.b().b(this.f8695d);
        if (l()) {
            BLEScannerProxy.getInstance().unlock();
        }
    }

    public final void q() {
        a.a.a.a.b.m.a.a(this.f8693b, "startScan...");
        this.J = true;
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(1).setReportDelay(0L).setUseHardwareFilteringIfSupported(true).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID)).build());
        if (Utils.isBleEnabled()) {
            try {
                BluetoothLeScannerCompat.getScanner().startScan(arrayList, scanSettingsBuild, this.fa);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.f8710s.postDelayed(this.ea, 60000L);
        }
    }

    public void r() {
        a.a.a.a.b.m.a.c(this.f8693b, "stop() called " + this.O);
        if (this.O) {
            return;
        }
        this.f8710s.removeCallbacks(this.ca);
        if (this.G != null && !this.f8698g) {
            this.f8696e.g();
        }
        this.B = true;
        if (this.f8697f) {
            e();
        }
    }

    public final void s() {
        a.a.a.a.b.m.a.a(this.f8693b, "stopScan...");
        this.f8710s.removeCallbacks(this.ea);
        if (Utils.isBleEnabled()) {
            BluetoothLeScannerCompat.getScanner().stopScan(this.fa);
        }
        this.J = false;
    }

    public final void d() {
        BaseMeshNode baseMeshNode = this.f8700i;
        if (baseMeshNode == null || !baseMeshNode.getSupportFastProvision()) {
            byte[] bArr = {0};
            SigmeshKey sigmeshKey = this.f8711t.get(0);
            if (sigmeshKey == null || sigmeshKey.getProvisionNetKey() == null) {
                return;
            }
            UnprovisionedMeshNode unprovisionedMeshNode = new UnprovisionedMeshNode();
            unprovisionedMeshNode.setIvIndex(ByteBuffer.allocate(4).putInt(this.f8704m.f()).array());
            unprovisionedMeshNode.setNetworkKey(MeshParserUtils.toByteArray(sigmeshKey.getProvisionNetKey().getNetKey()));
            unprovisionedMeshNode.setConfigurationSrc(this.f8696e.b());
            ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(unprovisionedMeshNode);
            int size = this.f8704m.c().size();
            for (int i2 = 0; i2 < size; i2++) {
                provisionedMeshNode.setAddedAppKey(i2, this.f8704m.c().get(i2));
            }
            provisionedMeshNode.setIsProvisioned(true);
            provisionedMeshNode.setConfigured(true);
            this.f8696e.a(provisionedMeshNode, 0, bArr);
            this.f8710s.postDelayed(new r(this, provisionedMeshNode), 500L);
        }
    }

    public final void e() {
        s();
        p();
        a.a.a.a.b.m.a.a(this.f8693b, "disconnect");
    }

    public final void c(String str) {
        Intent intent = new Intent(Utils.ACTION_PROVISIONED_NODE_FOUND);
        intent.putExtra(Utils.EXTRA_DATA, str);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    public final void b(ExtendedBluetoothDevice extendedBluetoothDevice) throws InterruptedException {
        a.a.a.a.b.m.a.a(this.f8693b, "onProvisionedDeviceFound...");
        b(extendedBluetoothDevice, true);
    }

    public final void b(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) throws InterruptedException {
        G.a().d().g();
        if (!z2) {
            a();
        }
        if (!z2) {
            this.f8710s.removeCallbacks(this.ca);
            this.f8700i = null;
        }
        if (extendedBluetoothDevice == null) {
            a.a.a.a.b.m.a.b(this.f8693b, "device is null");
            return;
        }
        a.a.a.a.b.m.a.a(this.f8693b, "Start for device: " + extendedBluetoothDevice.getAddress());
        if (this.f8717z.isSupportLargeScaleMeshNetwork()) {
            C0224b.b().a(extendedBluetoothDevice.getDevice());
        }
        a.a.a.a.b.m.a.a(this.f8693b, "connect to device: " + extendedBluetoothDevice.getAddress() + ", isProvisioned：" + z2);
        boolean z3 = false;
        if (!z2) {
            this.G = extendedBluetoothDevice;
            this.B = false;
            this.H = false;
            this.f8698g = false;
            this.f8699h = false;
            this.f8703l = false;
            this.f8702k = false;
            this.f8701j = false;
            this.f8705n.clear();
            this.f8706o.clear();
        }
        this.f8695d.setProvisioningComplete(z2);
        ScanRecord scanRecord = this.f8709r;
        if (scanRecord != null) {
            byte[] serviceData = scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
            a.a.a.a.b.m.a.a(this.f8693b, "service data: " + Utils.bytes2HexString(serviceData));
            UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(serviceData);
            String deviceMac = unprovisionedMeshNodeData.getDeviceMac();
            String str = unprovisionedMeshNodeData.getProductId() + "";
            if (!z2 && !unprovisionedMeshNodeData.isSupportFastProvisioningV2()) {
                a(serviceData, unprovisionedMeshNodeData, deviceMac, str);
            }
            a.a.a.a.b.m.a.c(this.f8693b, ConvertUtils.bytes2HexString(serviceData));
            boolean zIsFastProvisionMesh = unprovisionedMeshNodeData.isFastProvisionMesh();
            a.a.a.a.b.m.a.c(this.f8693b, "unprovisionedMeshNodeData.isFastProvisionMesh: " + unprovisionedMeshNodeData.isFastProvisionMesh() + ", isProvisioned: " + z2);
            if (!z2 && unprovisionedMeshNodeData.isSupportFastProvisioningV2()) {
                a(extendedBluetoothDevice.getConfigurationInfo());
            } else if (!z2 && unprovisionedMeshNodeData.isFastProvisionMesh()) {
                this.f8707p = true;
                this.O = false;
                J j2 = this.P;
                if (j2 != null) {
                    j2.a(this.f8694c, this, this.f8704m, this, this, this);
                    this.P.a(unprovisionedMeshNodeData, extendedBluetoothDevice.getDevice(), new B(this));
                }
            } else {
                if (!z2) {
                    BleMeshManager bleMeshManager = this.f8695d;
                    if (bleMeshManager != null) {
                        bleMeshManager.disconnect().enqueue();
                    }
                    this.f8695d = new BleMeshManager(this.f8694c.getApplicationContext(), String.valueOf(hashCode() % 1000000));
                }
                this.f8695d.setGattCallbacks(this);
                this.f8695d.connect(extendedBluetoothDevice.getDevice()).retry(5, 1000).enqueue();
                if (z2 && !this.H) {
                    z3 = true;
                }
                this.O = z3;
                a.a.a.a.b.m.a.a(this.f8693b, "mConnectToMeshNetwork: " + this.O);
            }
            z3 = zIsFastProvisionMesh;
        } else {
            a.a.a.a.b.m.a.c(this.f8693b, "mScannerRecord is null");
            this.f8695d.connect(extendedBluetoothDevice.getDevice()).retry(5, 1000).enqueue();
            this.O = z2 && !this.H;
        }
        if (z2) {
            return;
        }
        this.f8710s.postDelayed(this.ca, oa.a(z3));
    }

    public final void c(int i2) {
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, i2);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    public final void c() {
        Runnable runnable = this.S;
        if (runnable == null) {
            return;
        }
        this.f8710s.removeCallbacks(runnable);
        this.S = null;
    }

    public final boolean a(byte[] bArr) {
        for (Map.Entry<Integer, ProvisionedMeshNode> entry : this.f8696e.c().entrySet()) {
            if (this.f8696e != null && entry.getValue().getIdentityKey() != null && this.f8696e.b(entry.getValue(), bArr)) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(String str) {
        List<String> list = this.I;
        if (list == null || list.size() == 0) {
            return true;
        }
        return this.I.contains(str.toUpperCase());
    }

    public void a(OnProvisionFinishedListener onProvisionFinishedListener) {
        this.W = onProvisionFinishedListener;
    }

    public void a(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2, Map<String, Object> map) {
        this.Y = map;
        a(extendedBluetoothDevice, z2);
    }

    public void a(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) throws InterruptedException {
        a.a.a.a.b.m.a.a(this.f8693b, "startProvisioning() called with: device = [" + extendedBluetoothDevice + "], isProvisioned = [" + z2 + "]");
        if (extendedBluetoothDevice == null) {
            return;
        }
        CountDownLatch countDownLatch = this.Q;
        if (countDownLatch != null) {
            countDownLatch.notify();
            this.Q = null;
        }
        this.Q = new CountDownLatch(1);
        a(extendedBluetoothDevice);
        b(extendedBluetoothDevice, z2);
        try {
            this.Q.await();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        a.a.a.a.b.m.a.c(this.f8693b, "execution complete");
    }

    public final void a(ExtendedBluetoothDevice extendedBluetoothDevice) {
        this.f8709r = extendedBluetoothDevice.getScanRecord();
        this.f8708q = extendedBluetoothDevice.getDevice();
        ScanRecord scanRecord = this.f8709r;
        if (scanRecord != null) {
            UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(scanRecord.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)));
            this.f8717z = unprovisionedMeshNodeData;
            this.V = !unprovisionedMeshNodeData.isFastProvisionMesh() || this.f8717z.isFastSupportGatt();
        }
    }

    public final void a(ConfigurationData configurationData) {
        if (configurationData == null) {
            a(MeshUtConst$MeshErrorEnum.INVALID_PROVISIONING_PARAMETER, "Configuration info can not be null for fastProvisioningV2");
            return;
        }
        if (configurationData.getConfigResultMap() != null && configurationData.getConfigResultMap().getSigmeshKeys() != null && configurationData.getConfigResultMap().getSigmeshKeys().size() != 0 && configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionNetKey() != null && configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionAppKeys() != null && configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionAppKeys().size() != 0 && configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionAppKeys().get(0) != null) {
            a.a.a.a.b.i.u uVar = new a.a.a.a.b.i.u(this.f8694c, String.valueOf(hashCode() % 1000000));
            this.X = uVar;
            uVar.a(this.f8717z, this.f8708q, new C(this), new D(this));
            ProvisionInfo provisionInfo = new ProvisionInfo();
            provisionInfo.setPrimaryUnicastAddress((Integer) configurationData.getPrimaryUnicastAddress());
            provisionInfo.setServerConfirmation(configurationData.getServerConfirmation());
            ProvisionNetKey provisionNetKey = configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionNetKey();
            ArrayList arrayList = new ArrayList();
            arrayList.add(Integer.valueOf(provisionNetKey.getNetKeyIndex()));
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(provisionNetKey.getNetKey());
            provisionInfo.setNetKeyIndexes(arrayList);
            provisionInfo.setNetKeys(arrayList2);
            List<ProvisionAppKey> provisionAppKeys = configurationData.getConfigResultMap().getSigmeshKeys().get(0).getProvisionAppKeys();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList(2);
            for (ProvisionAppKey provisionAppKey : provisionAppKeys) {
                arrayList4.add(provisionAppKey.getAppKey());
                arrayList3.add(Integer.valueOf(provisionAppKey.getAppKeyIndex()));
            }
            provisionInfo.setAppKeyIndexes(arrayList3);
            provisionInfo.setAppKeys(arrayList4);
            this.X.a(provisionInfo);
            return;
        }
        a(MeshUtConst$MeshErrorEnum.INVALID_PROVISIONING_PARAMETER, "netKey and appKey can not be null for fastProvisioningV2");
    }

    public final void b(String str) {
        Intent intent = new Intent(Utils.ACTION_CONNECTION_STATE);
        intent.putExtra(Utils.EXTRA_DATA, str);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    public final void b(int i2) {
        Intent intent = new Intent(Utils.ACTION_CONFIGURATION_STATE);
        intent.putExtra(Utils.EXTRA_CONFIGURATION_STATE, i2);
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    public final void b(ProvisionedMeshNode provisionedMeshNode) {
        Map<String, Object> map = this.Y;
        if (map == null || !map.containsKey("ssid")) {
            return;
        }
        a.a.a.a.b.m.a.c(this.f8693b, "WiFi config start, mesh channel connected: " + this.f8695d.isConnected());
        this.f8696e.a(provisionedMeshNode, provisionedMeshNode.getAddedAppKeys().get(0), provisionedMeshNode.getUnicastAddress(), 13740033, 13871105, C0232a.a((String) this.Y.get("ssid"), (String) this.Y.get("password"), this.Y.containsKey(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) ? (String) this.Y.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_BSSID) : null, this.Y.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX) != null ? ((Byte) this.Y.get(TgMeshManager.KEY_PROVISION_COMBO_MESH_WIFI_REGION_INDEX)).byteValue() : (byte) 0), new C0259v(this));
        this.Y = null;
    }

    public final void a(byte[] bArr, UnprovisionedMeshNodeData unprovisionedMeshNodeData, String str, String str2) {
        String lowerCase;
        a.a.a.a.b.m.a.c(this.f8693b, "requestProvisionInfo with productId " + str2);
        if (unprovisionedMeshNodeData.getDeviceUuid() != null) {
            lowerCase = MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false).toLowerCase();
        } else {
            lowerCase = "";
        }
        na.a().c(str, str2, lowerCase, new C0227e(this, unprovisionedMeshNodeData, bArr));
    }

    public final void a(ProvisionedMeshNode provisionedMeshNode, IActionListener<Boolean> iActionListener) {
        a.a.a.a.b.m.a.c(this.f8693b, "requestConfirmation, fire provisionComplete request");
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = this.f8717z;
        if (unprovisionedMeshNodeData != null) {
            na.a().a(unprovisionedMeshNodeData.getDeviceMac(), MeshParserUtils.bytesToHex(provisionedMeshNode.getDeviceKey(), false), this.f8717z.getProductId() + "", (this.f8717z.getDeviceUuid() != null ? MeshParserUtils.bytesToHex(this.f8717z.getDeviceUuid(), false).toLowerCase() : "").toLowerCase(), new C0229g(this, iActionListener));
        }
    }

    public final void b() {
        Runnable runnable = this.Z;
        if (runnable != null) {
            this.f8710s.removeCallbacks(runnable);
            this.Z = null;
        }
    }

    public final void a(ProvisionedMeshNode provisionedMeshNode) {
        String strPoll = this.f8706o.poll();
        if (strPoll != null) {
            Integer numPoll = this.f8705n.poll();
            if (numPoll == null) {
                numPoll = 0;
            }
            Integer num = numPoll;
            Handler handler = this.f8710s;
            a aVar = new a(strPoll, num.intValue(), provisionedMeshNode, true);
            this.E = aVar;
            handler.postDelayed(aVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            this.f8696e.a(provisionedMeshNode, num.intValue(), strPoll);
            a.a.a.a.b.m.a.c(this.f8693b, "try to add app key: appKeyIndex = " + num + ", mAppKey = " + strPoll);
        }
    }

    public final void a(ProvisionedMeshNode provisionedMeshNode, byte[] bArr, int i2, List<Integer> list) {
        Integer numRemove;
        if (list.size() == 0 || (numRemove = list.remove(0)) == null) {
            return;
        }
        this.f8696e.a(provisionedMeshNode, bArr, numRemove.intValue(), i2);
        a.a.a.a.b.m.a.a(this.f8693b, "bindAppKey");
    }

    public final void a(byte[] bArr, DeviceStatus deviceStatus) {
        JSONObject jSONObject;
        a.a.a.a.b.m.a.a(this.f8693b, "getInfoByAuthInfo");
        if (bArr == null) {
            a.a.a.a.b.m.a.b(this.f8693b, "getInfoByAuthInfo: unicast address is null");
            return;
        }
        this.f8699h = true;
        m();
        ExtendedBluetoothDevice extendedBluetoothDevice = this.G;
        if (extendedBluetoothDevice != null) {
            extendedBluetoothDevice.getPk();
        }
        UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(this.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)));
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
        iotDevice.setUuid(na.a().c());
        if (deviceStatus != null) {
            jSONObject = new JSONObject();
            jSONObject.put("deviceStatus", (Object) JSON.toJSONString(deviceStatus));
        } else {
            jSONObject = null;
        }
        Map<String, Object> map = this.Y;
        if (map != null && !TextUtils.isEmpty(String.valueOf(map.get("familyId")))) {
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            jSONObject.put("familyId", (Object) String.valueOf(this.Y.get("familyId")));
        }
        if (jSONObject != null) {
            iotDevice.setExtensions(jSONObject.toJSONString());
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(iotDevice);
        String jSONString = JSON.toJSONString(arrayList);
        i.c().f(this.f8704m.h(), bArr);
        a.a.a.a.b.m.a.a(this.f8693b, "Use delegate to handle bind logic args:" + jSONString);
        if (a.a.a.a.b.d.a.f1334a) {
            na.a().b("iot", "bindBLEDevice", jSONString, new C0254p(this, iotDevice, jSONString));
            return;
        }
        OnReadyToBindHandler onReadyToBindHandler = this.C;
        if (onReadyToBindHandler != null) {
            onReadyToBindHandler.onReadyToBind(jSONString, new C0255q(this, iotDevice));
        }
    }

    public final void a(MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum, String str) {
        this.T = true;
        o();
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.PROVISIONING_FAILED.getState());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("device_mac_address", (Object) h());
        jSONObject.put(TmpConstant.SERVICE_DESC, (Object) str);
        intent.putExtra(Utils.EXTRA_PROVISIONING_FAIL_MSG, jSONObject.toJSONString());
        String str2 = "";
        if (this.f8717z != null) {
            str2 = this.f8717z.getProductId() + "";
        }
        b.a("ALSMesh", "ble", str2, false, this.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)), "", 0L, meshUtConst$MeshErrorEnum.getErrorCode(), meshUtConst$MeshErrorEnum.getErrorMsg());
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        this.f8710s.removeCallbacks(this.ca);
        CountDownLatch countDownLatch = this.Q;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
        p();
    }

    public final void a(boolean z2, String str) {
        Intent intent = new Intent(Utils.ACTION_IS_CONNECTED);
        intent.putExtra(Utils.EXTRA_DATA, z2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        intent.putExtra(Utils.EXTRA_CONNECT_FAIL_MSG, str);
    }

    public final void a(int i2, String str) {
        Intent intent = new Intent(Utils.ACTION_BIND_STATE);
        intent.putExtra(Utils.EXTRA_BIND_CODE, i2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(Utils.EXTRA_BIND_STATE_MSG, str);
        }
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
        if (i2 == 1) {
            byte[] serviceData = this.f8709r.getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID));
            b.a("ALSMesh", "ble", String.valueOf(AliMeshUUIDParserUtil.extractPIDFromUUID(serviceData)), false, serviceData, "", 0L);
        } else {
            MeshUtConst$MeshErrorEnum meshUtConst$MeshErrorEnum = MeshUtConst$MeshErrorEnum.BIND_API_FAILED;
            a(meshUtConst$MeshErrorEnum, meshUtConst$MeshErrorEnum.getErrorMsg());
        }
        CountDownLatch countDownLatch = this.Q;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public final void a() throws InterruptedException {
        if (this.V) {
            a.a.a.a.b.m.a.c(this.f8693b, Thread.currentThread().getName() + " acquire global connection semaphore");
            if (this.R.get()) {
                return;
            }
            try {
                f8692a.acquire();
                this.R.set(true);
                String str = this.f8693b;
                StringBuilder sb = new StringBuilder();
                sb.append(Thread.currentThread().getName());
                sb.append(" global connection semaphore acquired");
                a.a.a.a.b.m.a.c(str, sb.toString());
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.f8710s.postDelayed(new RunnableC0257t(this), g());
        }
    }

    public final void a(int i2) {
        Handler handler = this.f8710s;
        RunnableC0258u runnableC0258u = new RunnableC0258u(this);
        this.S = runnableC0258u;
        handler.postDelayed(runnableC0258u, i2);
    }

    public final void a(byte[] bArr, String str, byte[] bArr2) {
        if (!Arrays.equals(bArr, this.f8700i.getUnicastAddress()) || !"d4a801".equalsIgnoreCase(str) || bArr2 == null || bArr2.length < 6) {
            return;
        }
        byte b2 = bArr2[0];
        if (Arrays.equals(new byte[]{bArr2[2], bArr2[1]}, new byte[]{-16, 6})) {
            if (this.ba == null) {
                this.ba = new WiFiConfigReplyParser(new C0260w(this));
            }
            int length = bArr2.length - 3;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArr2, 3, bArr3, 0, length);
            this.ba.a(bArr3);
        }
    }

    public final void a(boolean z2, int i2, int i3, String str) {
        b();
        m();
        if (z2) {
            a.a.a.a.b.m.a.c(this.f8693b, "on successful to config Wi-Fi info");
        } else {
            a.a.a.a.b.m.a.b(this.f8693b, "on failed to config Wi-Fi info, error code: " + i2 + " , " + str);
        }
        Intent intent = new Intent(Utils.ACTION_PROVISIONING_STATE);
        intent.putExtra(Utils.EXTRA_PROVISIONING_STATE, MeshNodeStatus.COMBO_WIFI_CONFIG_STATUS.getState());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("device_mac_address", (Object) h());
        jSONObject.put("isSuccess", (Object) Boolean.valueOf(z2));
        if (!z2) {
            jSONObject.put("errorCode", (Object) Integer.valueOf(i2));
            jSONObject.put("subErrorCode", (Object) Integer.valueOf(i3));
            jSONObject.put("errorMessage", (Object) str);
        }
        intent.putExtra(Utils.EXTRA_PROVISIONING_FAIL_MSG, jSONObject.toJSONString());
        LocalBroadcastManager.getInstance(this.f8694c).sendBroadcast(intent);
    }

    public final void a(byte b2) {
        a.a.a.a.b.m.a.c(this.f8693b, String.format("D3 ack recevied, code: %02X", Byte.valueOf(b2)));
        if (b2 == 1) {
            Handler handler = this.f8710s;
            RunnableC0261x runnableC0261x = new RunnableC0261x(this);
            this.Z = runnableC0261x;
            handler.postDelayed(runnableC0261x, 40000L);
            return;
        }
        a(false, -71, (int) b2, "");
    }

    public final void a(IotDevice iotDevice, String str) {
        UnprovisionedMeshNodeData unprovisionedMeshNodeData;
        a.a.a.a.b.m.a.a(this.f8693b, "bindDevice request success");
        OnReadyToBindHandler onReadyToBindHandler = this.C;
        if (onReadyToBindHandler != null) {
            onReadyToBindHandler.onReadyToBind(str, null);
        }
        BaseMeshNode baseMeshNode = this.f8700i;
        if (baseMeshNode != null && !baseMeshNode.getSupportFastProvision()) {
            this.O = true;
            a(true, (String) null);
        }
        this.f8710s.removeCallbacks(this.ca);
        a(1, JSON.toJSONString(iotDevice));
        J j2 = this.P;
        if (j2 != null) {
            j2.l();
        }
        if (this.f8700i == null || (unprovisionedMeshNodeData = this.f8717z) == null) {
            return;
        }
        try {
            a.a.a.a.b.e.a.a(String.valueOf(this.f8717z.getProductId()), MeshParserUtils.bytesToHex(unprovisionedMeshNodeData.getDeviceUuid(), false), this.f8717z.getDeviceMac(), this.f8700i.getUnicastAddressInt(), MeshParserUtils.bytesToHex(this.f8700i.getDeviceKey(), false));
        } catch (Exception unused) {
        }
    }
}
