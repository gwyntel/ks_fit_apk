package com.alibaba.ailabs.iot.mesh.provision;

import a.a.a.a.a.a.a.b.a;
import a.a.a.a.a.a.b.a.c;
import a.a.a.a.a.a.b.a.d;
import a.a.a.a.a.g;
import a.a.a.a.b.i.C0234c;
import a.a.a.a.b.i.C0236e;
import a.a.a.a.b.i.C0237f;
import a.a.a.a.b.i.C0238g;
import a.a.a.a.b.i.C0239h;
import a.a.a.a.b.i.C0240i;
import a.a.a.a.b.i.C0241j;
import a.a.a.a.b.i.C0242k;
import a.a.a.a.b.i.C0243l;
import a.a.a.a.b.i.C0244m;
import a.a.a.a.b.i.C0245n;
import a.a.a.a.b.i.RunnableC0235d;
import aisble.callback.DataReceivedCallback;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import b.InterfaceC0326a;
import b.p;
import b.s;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionConfigCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback;
import com.alibaba.ailabs.iot.mesh.provision.state.AbsFastProvisionState$State;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

@RequiresApi(api = 21)
/* loaded from: classes2.dex */
public class FastProvisionManager implements FastProvisionStatusCallback {
    public static final int MESH_FAST_PROVISION = 4096;
    public static final long STOP_SCAN_DELAY_TIME = 10000;
    public static final String TAG = "InexpensiveMesh.Manager";
    public static volatile FastProvisionManager instance;
    public Context appContext;
    public InterfaceC0326a cloudConfirmationProvisioningCallbacks;
    public String confirmationCloud;
    public byte[] confirmationKey;
    public a controlMsg;
    public AbsFastProvisionState$State currentStatus;
    public byte[] deviceKey;
    public FastProvisionConfigCallback fastProvisionConfigCallback;
    public Handler mHandler;
    public p mProvisoningStatusCallbacks;
    public BaseMeshNode meshNode;
    public s provisioningSettings;
    public byte[] randomA;
    public byte[] randomB;
    public a.a.a.a.b.i.c.a tinyMeshTransportLayer;
    public FastProvisionTransportCallback transportCallback;
    public UnprovisionedMeshNode unprovisionedMeshNode;
    public UnprovisionedMeshNodeData unprovisionedMeshNodeData;
    public byte[] confirmationDevice = new byte[16];
    public boolean mInProvisionProgress = false;
    public BroadcastReceiver mProvisionAndBindStatusReceiver = null;
    public final DataReceivedCallback dataReceivedCallback = new C0237f(this);
    public final Runnable mStopScanRunnable = new RunnableC0235d(this);
    public List<a.a.a.a.a.a.a.a.a> controlRespList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void assembleControlResp(byte[] bArr) {
        a.a.a.a.b.m.a.c(TAG, "assembleControlResp " + ConvertUtils.bytes2HexString(bArr));
        if (this.controlMsg == null) {
            a.a.a.a.b.m.a.b(TAG, "There is no controlMsg");
            return;
        }
        a.a.a.a.a.a.a.a.a aVarA = a.a.a.a.a.a.a.a.a.a(bArr);
        if (aVarA == null) {
            a.a.a.a.b.m.a.b(TAG, "failed to parse " + ConvertUtils.bytes2HexString(bArr));
            return;
        }
        byte bGenerateNetworkId = generateNetworkId();
        if (aVarA.d() != bGenerateNetworkId) {
            a.a.a.a.b.m.a.b(TAG, "network id not equal, abandon. Expect " + ((int) bGenerateNetworkId) + ", receive " + ((int) this.controlMsg.d()));
            return;
        }
        if (this.controlRespList.isEmpty()) {
            this.controlRespList.add(aVarA);
            checkControlBufferAndSend();
        } else {
            a.a.a.a.a.a.a.a.a aVar = this.controlRespList.get(0);
            if (aVar.c() != aVarA.c()) {
                a.a.a.a.b.m.a.b(TAG, "clear old cache ...");
                this.controlRespList.clear();
                this.controlRespList.add(aVarA);
                checkControlBufferAndSend();
            } else {
                if (aVar.e() != aVarA.e()) {
                    a.a.a.a.b.m.a.b(TAG, "total package number illegal, expect " + aVar.e() + ", receive " + aVarA.e());
                    return;
                }
                Iterator<a.a.a.a.a.a.a.a.a> it = this.controlRespList.iterator();
                while (it.hasNext()) {
                    if (it.next().a() == aVarA.a()) {
                        a.a.a.a.b.m.a.c(TAG, "index duplicate");
                        return;
                    }
                }
                this.controlRespList.add(aVarA);
                checkControlBufferAndSend();
            }
        }
    }

    private void checkControlBufferAndSend() {
        a.a.a.a.a.a.a.a.a aVar = this.controlRespList.get(0);
        a.a.a.a.b.m.a.c(TAG, "checkControlBufferAndSend, expect " + aVar.e() + ", current " + this.controlRespList.size());
        Collections.sort(this.controlRespList, new C0245n(this));
        if (aVar.e() == this.controlRespList.size()) {
            Iterator<a.a.a.a.a.a.a.a.a> it = this.controlRespList.iterator();
            int length = 0;
            while (it.hasNext()) {
                byte[] bArrB = it.next().b();
                if (bArrB != null) {
                    length += bArrB.length;
                }
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length + 1);
            byteBufferAllocate.put((byte) 0);
            Iterator<a.a.a.a.a.a.a.a.a> it2 = this.controlRespList.iterator();
            while (it2.hasNext()) {
                byte[] bArrB2 = it2.next().b();
                if (bArrB2 != null) {
                    byteBufferAllocate.put(bArrB2);
                }
            }
            this.transportCallback.onReceiveFastProvisionData(this.meshNode, byteBufferAllocate.array());
        }
    }

    private byte[] generateConfirmationKey(byte[] bArr, byte[] bArr2) throws UnsupportedEncodingException {
        try {
            byte[] bytes = (ConvertUtils.bytes2HexString(bArr).toLowerCase() + ConvertUtils.bytes2HexString(bArr2).toLowerCase() + "ConfirmationKey").getBytes("ASCII");
            StringBuilder sb = new StringBuilder();
            sb.append("confirmationBytes: ");
            sb.append(ConvertUtils.bytes2HexString(bytes));
            a.a.a.a.b.m.a.c(TAG, sb.toString());
            byte[] bArrCalculateSha256 = SecureUtils.calculateSha256(bytes);
            if (bArrCalculateSha256 == null || bArrCalculateSha256.length < 16) {
                return null;
            }
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArrCalculateSha256, 0, bArr3, 0, 16);
            a.a.a.a.b.m.a.c(TAG, "" + ConvertUtils.bytes2HexString(bArr3));
            return bArr3;
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private byte[] generateDeviceKey(String str) {
        try {
            byte[] bArrCalculateSha256 = SecureUtils.calculateSha256((str + "DeviceKey").getBytes("ASCII"));
            if (bArrCalculateSha256 == null || bArrCalculateSha256.length < 16) {
                return null;
            }
            byte[] bArr = new byte[16];
            System.arraycopy(bArrCalculateSha256, 0, bArr, 0, 16);
            return bArr;
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private byte generateNetworkId() {
        s sVar = this.provisioningSettings;
        if (sVar == null) {
            return (byte) 0;
        }
        byte[] byteArray = MeshParserUtils.toByteArray(sVar.h());
        a.a.a.a.b.m.a.c(TAG, "networkKey=" + ConvertUtils.bytes2HexString(byteArray));
        return SecureUtils.calculateK2(byteArray, SecureUtils.K2_MASTER_INPUT).getNid();
    }

    public static FastProvisionManager getInstance() {
        if (instance == null) {
            synchronized (FastProvisionManager.class) {
                try {
                    if (instance == null) {
                        instance = new FastProvisionManager();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isProvisionStatus() {
        return this.currentStatus.getState() <= AbsFastProvisionState$State.PROVISIONING_COMPLETE.getState();
    }

    private byte[] mergeByteArray(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onProvisionProgressFinished() {
        this.mInProvisionProgress = false;
        unRegisterProvisionAndBindStatusReceiver();
    }

    private void registerProvisionAndBindStatusReceiver() {
        if (this.appContext == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Utils.ACTION_PROVISIONING_STATE);
        intentFilter.addAction(Utils.ACTION_BIND_STATE);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.appContext);
        C0236e c0236e = new C0236e(this);
        this.mProvisionAndBindStatusReceiver = c0236e;
        localBroadcastManager.registerReceiver(c0236e, intentFilter);
    }

    private void sendRandomToCloud(byte[] bArr, byte[] bArr2, InterfaceC0326a.b bVar) throws UnsupportedEncodingException {
        a.a.a.a.b.m.a.c(TAG, "sendRandomToCloud:");
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
        byte[] bArrGenerateConfirmationKey = generateConfirmationKey(bArr, bArr2);
        this.confirmationKey = bArrGenerateConfirmationKey;
        if (bArrGenerateConfirmationKey == null) {
            a.a.a.a.b.m.a.b(TAG, "failed to generate confirmationKey");
            onProvisionFailed(-1, "failed to generate confirmationKey");
            return;
        }
        InterfaceC0326a interfaceC0326a = this.cloudConfirmationProvisioningCallbacks;
        if (interfaceC0326a != null) {
            interfaceC0326a.generateConfirmationValue(this.unprovisionedMeshNodeData, bArrGenerateConfirmationKey, bArr3, bVar);
            onSendRandomToCloud(this.unprovisionedMeshNodeData);
        } else {
            a.a.a.a.b.m.a.b(TAG, "cloudConfirmationProvisioningCallbacks is null");
            onProvisionFailed(-1, "cloudConfirmationProvisioningCallbacks is null");
        }
    }

    private void unRegisterProvisionAndBindStatusReceiver() {
        Context context = this.appContext;
        if (context == null || this.mProvisionAndBindStatusReceiver == null) {
            return;
        }
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this.mProvisionAndBindStatusReceiver);
    }

    private void verifyConfirmation(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, byte[] bArr3, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        InterfaceC0326a interfaceC0326a = this.cloudConfirmationProvisioningCallbacks;
        if (interfaceC0326a != null) {
            interfaceC0326a.checkConfirmationValueMatches(this.unprovisionedMeshNode, unprovisionedMeshNodeData, bArr, bArr2, bArr3, interfaceC0015a);
        } else {
            a.a.a.a.b.m.a.b(TAG, "cloudConfirmationProvisioningCallbacks is null");
            onProvisionFailed(-1, "cloudConfirmationProvisioningCallbacks is null");
        }
    }

    public void disConnect(IActionListener<Boolean> iActionListener) {
        a.a.a.a.b.i.c.a aVar = this.tinyMeshTransportLayer;
        if (aVar != null) {
            aVar.a(iActionListener);
        }
    }

    public void disable() {
        a.a.a.a.b.i.c.a aVar = this.tinyMeshTransportLayer;
        if (aVar != null) {
            aVar.b();
        }
    }

    public void dispatchProvisionData(byte[] bArr) {
        if (bArr.length < 3) {
            return;
        }
        byte b2 = bArr[0];
        if (b2 == 1) {
            onReceiveDeviceConfirmationFromDevice(this.unprovisionedMeshNodeData, bArr);
            return;
        }
        if (b2 == 3) {
            onReceiveSendProvisionDataRspFromDevice(this.unprovisionedMeshNodeData, bArr);
            return;
        }
        a.a.a.a.b.m.a.b(TAG, "failed to handle " + ConvertUtils.bytes2HexString(bArr));
    }

    public boolean getInProvisionProgress() {
        return this.mInProvisionProgress;
    }

    public synchronized void init(Context context, InterfaceC0326a interfaceC0326a, s sVar, FastProvisionConfigCallback fastProvisionConfigCallback, FastProvisionTransportCallback fastProvisionTransportCallback, p pVar) {
        this.appContext = context.getApplicationContext();
        this.mHandler = new Handler(Looper.getMainLooper());
        registerFastProvisionMeshScanStrategy();
        g.c().a(this.appContext);
        this.cloudConfirmationProvisioningCallbacks = interfaceC0326a;
        this.provisioningSettings = sVar;
        this.fastProvisionConfigCallback = fastProvisionConfigCallback;
        this.mProvisoningStatusCallbacks = pVar;
        this.transportCallback = fastProvisionTransportCallback;
        this.currentStatus = AbsFastProvisionState$State.PROVISIONING_COMPLETE;
        this.controlRespList.clear();
        this.controlMsg = null;
    }

    public synchronized void initTransportLayer(UnprovisionedMeshNodeData unprovisionedMeshNodeData, BluetoothDevice bluetoothDevice, IConnectCallback iConnectCallback) {
        try {
            a.a.a.a.b.m.a.a(TAG, "initTransportLayer supportGatt:" + unprovisionedMeshNodeData.isFastSupportGatt());
            if (unprovisionedMeshNodeData.isFastSupportGatt()) {
                this.tinyMeshTransportLayer = new a.a.a.a.b.i.c.p();
            } else {
                this.tinyMeshTransportLayer = new a.a.a.a.b.i.c.g();
            }
            this.tinyMeshTransportLayer.init(this.appContext);
            this.tinyMeshTransportLayer.a(bluetoothDevice, iConnectCallback);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onAddAppKeyMsgRespReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(TAG, "onAddAppKeyMsgRespReceived");
        this.currentStatus = AbsFastProvisionState$State.ADD_APP_KEY_RESP_RECEIVED;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onAddAppKeyMsgSend(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(TAG, "onAddAppKeyMsgSend");
        this.currentStatus = AbsFastProvisionState$State.ADD_APP_KEY_SEND;
        this.fastProvisionConfigCallback.advertiseAppKey(provisionedMeshNode, new C0244m(this));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onBroadcastingRandoms(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(TAG, "onBroadcastingRandoms");
        this.currentStatus = AbsFastProvisionState$State.BROADCASTING_RANDOMS;
        this.unprovisionedMeshNodeData = unprovisionedMeshNodeData;
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            return;
        }
        startScanDeviceAdvertise(this.appContext);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onConfigInfoReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(TAG, "onConfigInfoReceived");
        this.currentStatus = AbsFastProvisionState$State.CONFIG_INFO_RECEIVED;
        this.fastProvisionConfigCallback.advertiseAppKey(provisionedMeshNode, new C0243l(this, provisionedMeshNode));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onProvisionFailed(int i2, String str) {
        a.a.a.a.b.m.a.c(TAG, "onProvisionFailed: errorCode = " + i2 + ", errorMsg = " + str);
        this.currentStatus = AbsFastProvisionState$State.PROVISIONING_FAILED;
        onProvisionProgressFinished();
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveConfirmationFromCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData, String str) {
        a.a.a.a.b.m.a.c(TAG, "onReceiveConfirmationFromCloud");
        this.confirmationCloud = str;
        byte[] bArrGenerateDeviceKey = generateDeviceKey(str);
        this.deviceKey = bArrGenerateDeviceKey;
        if (bArrGenerateDeviceKey == null) {
            a.a.a.a.b.m.a.b(TAG, "failed to generate deviceKey");
            onProvisionFailed(-1, "failed to generate deviceKey");
            return;
        }
        this.currentStatus = AbsFastProvisionState$State.CONFIRMATION_CLOUD_RECEIVED;
        c cVar = new c(unprovisionedMeshNodeData.getMac(), this.randomA, this.randomB);
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            startScanDeviceAdvertise(this.appContext);
        }
        this.tinyMeshTransportLayer.a(cVar.a(), new C0239h(this, unprovisionedMeshNodeData));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveDeviceConfirmationFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        int state = this.currentStatus.getState();
        AbsFastProvisionState$State absFastProvisionState$State = AbsFastProvisionState$State.CONFIRMATION_DEVICE_RECEIVED;
        if (state >= absFastProvisionState$State.getState()) {
            a.a.a.a.b.m.a.b(TAG, "duplicate CONFIRMATION_DEVICE_RECEIVED. skip");
            return;
        }
        this.currentStatus = absFastProvisionState$State;
        a.a.a.a.b.m.a.c(TAG, "onReceiveDeviceConfirmationFromDevice: randomA" + ConvertUtils.bytes2HexString(this.randomA) + ", randomB " + ConvertUtils.bytes2HexString(this.randomB));
        a.a.a.a.b.m.c.a(TAG, bArr);
        byte[] bArr2 = this.confirmationDevice;
        System.arraycopy(bArr, 3, bArr2, 0, bArr2.length);
        verifyConfirmation(unprovisionedMeshNodeData, this.confirmationDevice, this.confirmationKey, mergeByteArray(this.randomA, this.randomB), new C0240i(this));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveProvisionInfoRspFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        this.currentStatus = AbsFastProvisionState$State.PROVISION__CONFIG_RESP_RECEIVED;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveSendProvisionDataRspFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        int state = this.currentStatus.getState();
        AbsFastProvisionState$State absFastProvisionState$State = AbsFastProvisionState$State.ENCRYPTED_PROVISION_MSG_RSP_RECEIVE;
        if (state >= absFastProvisionState$State.getState()) {
            a.a.a.a.b.m.a.b(TAG, "duplicate ENCRYPTED_PROVISION_MSG_RSP_RECEIVE. skip");
            return;
        }
        this.currentStatus = absFastProvisionState$State;
        a.a.a.a.b.m.a.c(TAG, "onReceiveSendProvisionDataRspFromDevice: " + ConvertUtils.bytes2HexString(bArr));
        ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(this.unprovisionedMeshNode);
        provisionedMeshNode.setDeviceKey(this.deviceKey);
        int i2 = this.provisioningSettings.i();
        provisionedMeshNode.setUnicastAddress(new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
        this.meshNode = provisionedMeshNode;
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            this.mProvisoningStatusCallbacks.onProvisioningComplete(provisionedMeshNode);
        } else {
            this.fastProvisionConfigCallback.requestConfigMsg(provisionedMeshNode, new C0242k(this, provisionedMeshNode));
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveVerifyResultFromCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(TAG, "onReceiveVerifyResultFromCloud");
        this.currentStatus = AbsFastProvisionState$State.DATA_VERIFY_SUCCESS_FROM_CLOUD;
        byte[] byteArray = MeshParserUtils.toByteArray(this.provisioningSettings.h());
        a.a.a.a.b.m.a.c(TAG, "networkKey: " + ConvertUtils.bytes2HexString(byteArray));
        byte bF = (byte) (this.provisioningSettings.f() & 255);
        int i2 = this.provisioningSettings.i();
        d dVar = new d(unprovisionedMeshNodeData.getMac(), (byte) 0, byteArray, bF, new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)}, this.confirmationCloud);
        if (dVar.b()) {
            this.tinyMeshTransportLayer.a(dVar.a(), new C0241j(this));
        } else {
            onProvisionFailed(-1, "failed to generate encrypted provision data");
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendProvisionConfigInfo(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.currentStatus = AbsFastProvisionState$State.PROVISION_CONFIG_SEND;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendProvisionDataToDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.currentStatus = AbsFastProvisionState$State.ENCRYPTED_PROVISION_MSG_SEND;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendRandomAndDeviceConfirmationToCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.currentStatus = AbsFastProvisionState$State.CONFIRMATION_DEVICE_SEND_TO_CLOUD;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendRandomToCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(TAG, "onSendRandomToCloud");
        this.currentStatus = AbsFastProvisionState$State.CONFIRMATION_KEY_AND_RANDOM_SEND_TO_CLOUD;
    }

    public void registerFastProvisionMeshScanStrategy() {
        a.a.a.a.b.m.a.c(TAG, "registerFastProvisionMeshScanStrategy");
    }

    public void sendFastProvisionControlPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        byte[] bArr2;
        a.a.a.a.b.m.a.c(TAG, "before split package " + ConvertUtils.bytes2HexString(bArr));
        this.meshNode = baseMeshNode;
        if (bArr.length >= 1) {
            int length = bArr.length - 1;
            bArr2 = new byte[length];
            System.arraycopy(bArr, 1, bArr2, 0, length);
        } else {
            int length2 = bArr.length;
            bArr2 = new byte[length2];
            System.arraycopy(bArr, 0, bArr2, 0, length2);
        }
        this.controlMsg = new a(bArr2, generateNetworkId(), new C0234c(this, baseMeshNode, bArr));
        g.c().a(this.controlMsg);
    }

    public void setUnprovisionedMeshNode(UnprovisionedMeshNode unprovisionedMeshNode) {
        this.unprovisionedMeshNode = unprovisionedMeshNode;
        this.meshNode = unprovisionedMeshNode;
    }

    public void startFastProvision(UnprovisionedMeshNodeData unprovisionedMeshNodeData) throws UnsupportedEncodingException {
        this.mInProvisionProgress = true;
        registerProvisionAndBindStatusReceiver();
        this.unprovisionedMeshNodeData = unprovisionedMeshNodeData;
        this.mHandler.removeCallbacks(this.mStopScanRunnable);
        stopScan();
        this.randomA = SecureUtils.generateRandomNumber(64);
        byte[] bArrGenerateRandomNumber = SecureUtils.generateRandomNumber(64);
        this.randomB = bArrGenerateRandomNumber;
        sendRandomToCloud(this.randomA, bArrGenerateRandomNumber, new C0238g(this, unprovisionedMeshNodeData));
    }

    public void startScanDeviceAdvertise(Context context) {
        a.a.a.a.b.m.a.c(TAG, "startScanDeviceAdvertise execute");
        registerFastProvisionMeshScanStrategy();
        a.a.a.a.b.i.c.a aVar = this.tinyMeshTransportLayer;
        if (aVar != null) {
            aVar.a(this.dataReceivedCallback);
        }
    }

    public void stopDelayScan() {
        this.mHandler.postDelayed(this.mStopScanRunnable, 10000L);
    }

    public void stopScan() {
        a.a.a.a.b.i.c.a aVar = this.tinyMeshTransportLayer;
        if (aVar != null) {
            aVar.a();
        }
    }
}
