package a.a.a.a.b.i;

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
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
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
import java.util.Random;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class J implements FastProvisionStatusCallback {
    public Runnable A;
    public final int B;
    public int C;
    public String D;
    public final DataReceivedCallback E;
    public final Runnable F;

    /* renamed from: a, reason: collision with root package name */
    public String f1373a;

    /* renamed from: b, reason: collision with root package name */
    public Context f1374b;

    /* renamed from: c, reason: collision with root package name */
    public byte[] f1375c;

    /* renamed from: d, reason: collision with root package name */
    public byte[] f1376d;

    /* renamed from: e, reason: collision with root package name */
    public String f1377e;

    /* renamed from: f, reason: collision with root package name */
    public byte[] f1378f;

    /* renamed from: g, reason: collision with root package name */
    public byte[] f1379g;

    /* renamed from: h, reason: collision with root package name */
    public byte[] f1380h;

    /* renamed from: i, reason: collision with root package name */
    public UnprovisionedMeshNodeData f1381i;

    /* renamed from: j, reason: collision with root package name */
    public UnprovisionedMeshNode f1382j;

    /* renamed from: k, reason: collision with root package name */
    public BluetoothDevice f1383k;

    /* renamed from: l, reason: collision with root package name */
    public BaseMeshNode f1384l;

    /* renamed from: m, reason: collision with root package name */
    public a.a.a.a.a.a.a.b.a f1385m;

    /* renamed from: n, reason: collision with root package name */
    public List<a.a.a.a.a.a.a.a.a> f1386n;

    /* renamed from: o, reason: collision with root package name */
    public a.a.a.a.b.i.c.a f1387o;

    /* renamed from: p, reason: collision with root package name */
    public FastProvisionConfigCallback f1388p;

    /* renamed from: q, reason: collision with root package name */
    public InterfaceC0326a f1389q;

    /* renamed from: r, reason: collision with root package name */
    public b.p f1390r;

    /* renamed from: s, reason: collision with root package name */
    public b.s f1391s;

    /* renamed from: t, reason: collision with root package name */
    public AbsFastProvisionState$State f1392t;

    /* renamed from: u, reason: collision with root package name */
    public FastProvisionTransportCallback f1393u;

    /* renamed from: v, reason: collision with root package name */
    public Handler f1394v;

    /* renamed from: w, reason: collision with root package name */
    public boolean f1395w;

    /* renamed from: x, reason: collision with root package name */
    public BroadcastReceiver f1396x;

    /* renamed from: y, reason: collision with root package name */
    public String f1397y;

    /* renamed from: z, reason: collision with root package name */
    public Runnable f1398z;

    public J() {
        this.f1373a = FastProvisionManager.TAG;
        this.f1378f = new byte[16];
        this.f1395w = false;
        this.f1396x = null;
        this.f1398z = null;
        this.A = null;
        this.B = 2;
        this.C = 0;
        this.E = new z(this);
        this.F = new w(this);
        this.f1386n = new ArrayList();
    }

    public final void j() {
        if (this.f1374b == null) {
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Utils.ACTION_PROVISIONING_STATE);
        intentFilter.addAction(Utils.ACTION_BIND_STATE);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.f1374b);
        x xVar = new x(this);
        this.f1396x = xVar;
        localBroadcastManager.registerReceiver(xVar, intentFilter);
    }

    public void k() {
        if (this.f1387o != null) {
            a.a.a.a.b.m.a.c(this.f1373a, "Release transport layer.");
            this.f1387o.a();
        }
        Runnable runnable = this.A;
        if (runnable != null) {
            this.f1394v.removeCallbacks(runnable);
        }
    }

    public void l() {
        this.f1394v.postDelayed(this.F, 10000L);
    }

    public void m() {
        a.a.a.a.b.i.c.a aVar = this.f1387o;
        if (aVar == null || !(aVar instanceof a.a.a.a.b.i.c.g)) {
            return;
        }
        aVar.a();
    }

    public final void n() {
        Context context = this.f1374b;
        if (context == null || this.f1396x == null) {
            return;
        }
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this.f1396x);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onAddAppKeyMsgRespReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(this.f1373a, "onAddAppKeyMsgRespReceived");
        this.f1392t = AbsFastProvisionState$State.ADD_APP_KEY_RESP_RECEIVED;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onAddAppKeyMsgSend(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(this.f1373a, "onAddAppKeyMsgSend");
        this.f1392t = AbsFastProvisionState$State.ADD_APP_KEY_SEND;
        this.f1388p.advertiseAppKey(provisionedMeshNode, new H(this));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onBroadcastingRandoms(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(this.f1373a, "onBroadcastingRandoms");
        this.f1392t = AbsFastProvisionState$State.BROADCASTING_RANDOMS;
        this.f1381i = unprovisionedMeshNodeData;
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            return;
        }
        a(this.f1374b);
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onConfigInfoReceived(ProvisionedMeshNode provisionedMeshNode) {
        a.a.a.a.b.m.a.c(this.f1373a, "onConfigInfoReceived");
        this.f1392t = AbsFastProvisionState$State.CONFIG_INFO_RECEIVED;
        this.f1388p.advertiseAppKey(provisionedMeshNode, new G(this, provisionedMeshNode));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onProvisionFailed(int i2, String str) {
        a.a.a.a.b.m.a.c(this.f1373a, "onProvisionFailed: errorCode = " + i2 + ", errorMsg = " + str);
        this.f1392t = AbsFastProvisionState$State.PROVISIONING_FAILED;
        b.p pVar = this.f1390r;
        if (pVar != null) {
            pVar.onProvisioningFailed(this.f1382j, i2);
        }
        i();
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveConfirmationFromCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData, String str) {
        a.a.a.a.b.m.a.c(this.f1373a, "onReceiveConfirmationFromCloud");
        this.f1377e = str;
        byte[] bArrA = a(str);
        this.f1379g = bArrA;
        if (bArrA == null) {
            a.a.a.a.b.m.a.b(this.f1373a, "failed to generate deviceKey");
            onProvisionFailed(-1, "failed to generate deviceKey");
            return;
        }
        this.f1392t = AbsFastProvisionState$State.CONFIRMATION_CLOUD_RECEIVED;
        a.a.a.a.a.a.b.a.c cVar = new a.a.a.a.a.a.b.a.c(unprovisionedMeshNodeData.getMac(), this.f1375c, this.f1376d);
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            a(this.f1374b);
        }
        this.f1387o.a(cVar.a(), new A(this, unprovisionedMeshNodeData));
        int i2 = this.C;
        if (i2 >= 2) {
            if (this.f1381i.isFastSupportGatt()) {
                h();
            }
        } else {
            this.C = i2 + 1;
            Handler handler = this.f1394v;
            B b2 = new B(this, unprovisionedMeshNodeData, str);
            this.f1398z = b2;
            handler.postDelayed(b2, 1500L);
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveDeviceConfirmationFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        Runnable runnable = this.f1398z;
        if (runnable != null) {
            this.f1394v.removeCallbacks(runnable);
        }
        int state = this.f1392t.getState();
        AbsFastProvisionState$State absFastProvisionState$State = AbsFastProvisionState$State.CONFIRMATION_DEVICE_RECEIVED;
        if (state >= absFastProvisionState$State.getState()) {
            a.a.a.a.b.m.a.b(this.f1373a, "duplicate CONFIRMATION_DEVICE_RECEIVED. skip");
            return;
        }
        this.f1392t = absFastProvisionState$State;
        a.a.a.a.b.m.a.c(this.f1373a, "onReceiveDeviceConfirmationFromDevice: randomA" + ConvertUtils.bytes2HexString(this.f1375c) + ", randomB " + ConvertUtils.bytes2HexString(this.f1376d));
        a.a.a.a.b.m.c.a(this.f1373a, bArr);
        byte[] bArr2 = this.f1378f;
        System.arraycopy(bArr, 3, bArr2, 0, bArr2.length);
        a(unprovisionedMeshNodeData, this.f1378f, this.f1380h, b(this.f1375c, this.f1376d), new C(this));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveProvisionInfoRspFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        this.f1392t = AbsFastProvisionState$State.PROVISION__CONFIG_RESP_RECEIVED;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveSendProvisionDataRspFromDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr) {
        int state = this.f1392t.getState();
        AbsFastProvisionState$State absFastProvisionState$State = AbsFastProvisionState$State.ENCRYPTED_PROVISION_MSG_RSP_RECEIVE;
        if (state >= absFastProvisionState$State.getState()) {
            a.a.a.a.b.m.a.b(this.f1373a, "duplicate ENCRYPTED_PROVISION_MSG_RSP_RECEIVE. skip");
            return;
        }
        this.f1392t = absFastProvisionState$State;
        a.a.a.a.b.m.a.c(this.f1373a, "onReceiveSendProvisionDataRspFromDevice: " + ConvertUtils.bytes2HexString(bArr));
        ProvisionedMeshNode provisionedMeshNode = new ProvisionedMeshNode(this.f1382j);
        provisionedMeshNode.setDeviceKey(this.f1379g);
        int i2 = this.f1391s.i();
        provisionedMeshNode.setUnicastAddress(new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
        this.f1384l = provisionedMeshNode;
        if (unprovisionedMeshNodeData.isFastSupportGatt()) {
            this.f1390r.onProvisioningComplete(provisionedMeshNode);
        } else {
            this.f1388p.requestConfigMsg(provisionedMeshNode, new F(this, provisionedMeshNode));
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onReceiveVerifyResultFromCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(this.f1373a, "onReceiveVerifyResultFromCloud");
        this.f1392t = AbsFastProvisionState$State.DATA_VERIFY_SUCCESS_FROM_CLOUD;
        byte[] byteArray = MeshParserUtils.toByteArray(this.f1391s.h());
        a.a.a.a.b.m.a.c(this.f1373a, "networkKey: " + ConvertUtils.bytes2HexString(byteArray));
        byte bF = (byte) (this.f1391s.f() & 255);
        int i2 = this.f1391s.i();
        a.a.a.a.a.a.b.a.d dVar = new a.a.a.a.a.a.b.a.d(unprovisionedMeshNodeData.getMac(), (byte) 0, byteArray, bF, new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)}, this.f1377e);
        if (dVar.b()) {
            this.f1387o.a(dVar.a(), new E(this));
        } else {
            onProvisionFailed(-1, "failed to generate encrypted provision data");
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendProvisionConfigInfo(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1392t = AbsFastProvisionState$State.PROVISION_CONFIG_SEND;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendProvisionDataToDevice(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1392t = AbsFastProvisionState$State.ENCRYPTED_PROVISION_MSG_SEND;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendRandomAndDeviceConfirmationToCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1392t = AbsFastProvisionState$State.CONFIRMATION_DEVICE_SEND_TO_CLOUD;
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionStatusCallback
    public void onSendRandomToCloud(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        a.a.a.a.b.m.a.c(this.f1373a, "onSendRandomToCloud");
        this.f1392t = AbsFastProvisionState$State.CONFIRMATION_KEY_AND_RANDOM_SEND_TO_CLOUD;
    }

    public void b(byte[] bArr) {
        if (bArr.length < 3) {
            return;
        }
        byte b2 = bArr[0];
        if (b2 == 1) {
            onReceiveDeviceConfirmationFromDevice(this.f1381i, bArr);
            return;
        }
        if (b2 == 3) {
            onReceiveSendProvisionDataRspFromDevice(this.f1381i, bArr);
            return;
        }
        a.a.a.a.b.m.a.b(this.f1373a, "failed to handle " + ConvertUtils.bytes2HexString(bArr));
    }

    public final byte c() {
        b.s sVar = this.f1391s;
        if (sVar == null) {
            return (byte) 0;
        }
        byte[] byteArray = MeshParserUtils.toByteArray(sVar.h());
        a.a.a.a.b.m.a.c(this.f1373a, "networkKey=" + ConvertUtils.bytes2HexString(byteArray));
        return SecureUtils.calculateK2(byteArray, SecureUtils.K2_MASTER_INPUT).getNid();
    }

    public final void d() throws UnsupportedEncodingException {
        this.f1375c = SecureUtils.generateRandomNumber(64);
        this.f1376d = SecureUtils.generateRandomNumberWithSeedByNonaTime(64);
        byte[] bArrHexString2Bytes = ConvertUtils.hexString2Bytes(Integer.toHexString(hashCode()));
        Random random = new Random();
        if (bArrHexString2Bytes != null && bArrHexString2Bytes.length > 0) {
            this.f1375c[random.nextInt(8)] = bArrHexString2Bytes[random.nextInt(bArrHexString2Bytes.length)];
            this.f1376d[random.nextInt(8)] = bArrHexString2Bytes[random.nextInt(bArrHexString2Bytes.length)];
        }
        a(this.f1375c, this.f1376d, new y(this));
    }

    public boolean e() {
        return this.f1395w;
    }

    public a.a.a.a.b.i.c.a f() {
        return this.f1387o;
    }

    public final boolean g() {
        return this.f1392t.getState() <= AbsFastProvisionState$State.PROVISIONING_COMPLETE.getState();
    }

    public final void h() {
        a.a.a.a.b.m.a.d(this.f1373a, "onInvalidConnectionMayHappen()");
        if (this.f1387o != null) {
            a.a.a.a.b.m.a.c(this.f1373a, "Release transport layer.");
            this.f1387o.a();
        }
        onProvisionFailed(-1, "Invalid connection may happen");
    }

    public final void i() {
        this.f1395w = false;
        n();
    }

    public synchronized void a(UnprovisionedMeshNodeData unprovisionedMeshNodeData, BluetoothDevice bluetoothDevice, IConnectCallback iConnectCallback) {
        try {
            a.a.a.a.b.m.a.a(this.f1373a, "initTransportLayer supportGatt:" + unprovisionedMeshNodeData.isFastSupportGatt());
            if (a.a.a.a.b.d.a.f1335b || unprovisionedMeshNodeData.isFastSupportGatt()) {
                this.f1387o = new a.a.a.a.b.i.c.r(this.f1397y);
            } else {
                this.f1387o = new a.a.a.a.b.i.c.g();
            }
            this.f1387o.init(this.f1374b);
            this.f1387o.a(bluetoothDevice, iConnectCallback);
            this.f1381i = unprovisionedMeshNodeData;
            this.f1383k = bluetoothDevice;
            d();
        } catch (Throwable th) {
            throw th;
        }
    }

    public final byte[] b(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public void b() {
        a.a.a.a.b.i.c.a aVar = this.f1387o;
        if (aVar != null) {
            aVar.b();
        }
    }

    public J(String str) {
        this();
        this.f1373a += str;
        this.f1397y = str;
    }

    public synchronized void a(Context context, InterfaceC0326a interfaceC0326a, b.s sVar, FastProvisionConfigCallback fastProvisionConfigCallback, FastProvisionTransportCallback fastProvisionTransportCallback, b.p pVar) {
        this.f1374b = context.getApplicationContext();
        this.f1394v = new Handler(Looper.getMainLooper());
        a.a.a.a.a.g.c().a(this.f1374b);
        this.f1389q = interfaceC0326a;
        this.f1391s = sVar;
        this.f1388p = fastProvisionConfigCallback;
        this.f1390r = pVar;
        this.f1393u = fastProvisionTransportCallback;
        this.f1392t = AbsFastProvisionState$State.PROVISIONING_COMPLETE;
        this.f1386n.clear();
        this.f1385m = null;
    }

    public void a(UnprovisionedMeshNode unprovisionedMeshNode) {
        this.f1382j = unprovisionedMeshNode;
        this.f1384l = unprovisionedMeshNode;
    }

    public void a(UnprovisionedMeshNodeData unprovisionedMeshNodeData) {
        this.f1395w = true;
        this.C = 0;
        j();
        this.f1381i = unprovisionedMeshNodeData;
        this.f1394v.removeCallbacks(this.F);
        m();
        String str = this.D;
        if (str == null || this.f1392t != AbsFastProvisionState$State.PROVISIONING_COMPLETE) {
            return;
        }
        onReceiveConfirmationFromCloud(unprovisionedMeshNodeData, str);
    }

    public void a(Context context) {
        a.a.a.a.b.m.a.c(this.f1373a, "startScanDeviceAdvertise execute");
        a.a.a.a.b.i.c.a aVar = this.f1387o;
        if (aVar != null) {
            aVar.a(this.E);
        }
    }

    public void a(IActionListener<Boolean> iActionListener) {
        a.a.a.a.b.i.c.a aVar = this.f1387o;
        if (aVar != null) {
            aVar.a(iActionListener);
        }
    }

    public final synchronized void a(byte[] bArr) {
        a.a.a.a.b.m.a.c(this.f1373a, "assembleControlResp " + ConvertUtils.bytes2HexString(bArr));
        if (this.f1385m == null) {
            a.a.a.a.b.m.a.b(this.f1373a, "There is no controlMsg");
            return;
        }
        a.a.a.a.a.a.a.a.a aVarA = a.a.a.a.a.a.a.a.a.a(bArr);
        if (aVarA == null) {
            a.a.a.a.b.m.a.b(this.f1373a, "failed to parse " + ConvertUtils.bytes2HexString(bArr));
            return;
        }
        byte bC = c();
        if (aVarA.d() != bC) {
            a.a.a.a.b.m.a.b(this.f1373a, "network id not equal, abandon. Expect " + ((int) bC) + ", receive " + ((int) this.f1385m.d()));
            return;
        }
        if (this.f1386n.isEmpty()) {
            this.f1386n.add(aVarA);
            a();
        } else {
            a.a.a.a.a.a.a.a.a aVar = this.f1386n.get(0);
            if (aVar.c() != aVarA.c()) {
                a.a.a.a.b.m.a.b(this.f1373a, "clear old cache ...");
                this.f1386n.clear();
                this.f1386n.add(aVarA);
                a();
            } else {
                if (aVar.e() != aVarA.e()) {
                    a.a.a.a.b.m.a.b(this.f1373a, "total package number illegal, expect " + aVar.e() + ", receive " + aVarA.e());
                    return;
                }
                Iterator<a.a.a.a.a.a.a.a.a> it = this.f1386n.iterator();
                while (it.hasNext()) {
                    if (it.next().a() == aVarA.a()) {
                        a.a.a.a.b.m.a.c(this.f1373a, "index duplicate");
                        return;
                    }
                }
                this.f1386n.add(aVarA);
                a();
            }
        }
    }

    public final void a() {
        a.a.a.a.a.a.a.a.a aVar = this.f1386n.get(0);
        a.a.a.a.b.m.a.c(this.f1373a, "checkControlBufferAndSend, expect " + aVar.e() + ", current " + this.f1386n.size());
        Collections.sort(this.f1386n, new I(this));
        if (aVar.e() == this.f1386n.size()) {
            Iterator<a.a.a.a.a.a.a.a.a> it = this.f1386n.iterator();
            int length = 0;
            while (it.hasNext()) {
                byte[] bArrB = it.next().b();
                if (bArrB != null) {
                    length += bArrB.length;
                }
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length + 1);
            byteBufferAllocate.put((byte) 0);
            Iterator<a.a.a.a.a.a.a.a.a> it2 = this.f1386n.iterator();
            while (it2.hasNext()) {
                byte[] bArrB2 = it2.next().b();
                if (bArrB2 != null) {
                    byteBufferAllocate.put(bArrB2);
                }
            }
            this.f1393u.onReceiveFastProvisionData(this.f1384l, byteBufferAllocate.array());
        }
    }

    public final byte[] a(String str) {
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

    public final void a(byte[] bArr, byte[] bArr2, InterfaceC0326a.b bVar) throws UnsupportedEncodingException {
        a.a.a.a.b.m.a.c(this.f1373a, "sendRandomToCloud:");
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, bArr2.length, bArr.length);
        byte[] bArrA = a(bArr, bArr2);
        this.f1380h = bArrA;
        if (bArrA == null) {
            a.a.a.a.b.m.a.b(this.f1373a, "failed to generate confirmationKey");
            onProvisionFailed(-1, "failed to generate confirmationKey");
            return;
        }
        InterfaceC0326a interfaceC0326a = this.f1389q;
        if (interfaceC0326a != null) {
            interfaceC0326a.generateConfirmationValue(this.f1381i, bArrA, bArr3, bVar);
            onSendRandomToCloud(this.f1381i);
        } else {
            a.a.a.a.b.m.a.b(this.f1373a, "cloudConfirmationProvisioningCallbacks is null");
            onProvisionFailed(-1, "cloudConfirmationProvisioningCallbacks is null");
        }
    }

    public final void a(UnprovisionedMeshNodeData unprovisionedMeshNodeData, byte[] bArr, byte[] bArr2, byte[] bArr3, InterfaceC0326a.InterfaceC0015a interfaceC0015a) {
        InterfaceC0326a interfaceC0326a = this.f1389q;
        if (interfaceC0326a != null) {
            interfaceC0326a.checkConfirmationValueMatches(this.f1382j, unprovisionedMeshNodeData, bArr, bArr2, bArr3, interfaceC0015a);
        } else {
            a.a.a.a.b.m.a.b(this.f1373a, "cloudConfirmationProvisioningCallbacks is null");
            onProvisionFailed(-1, "cloudConfirmationProvisioningCallbacks is null");
        }
    }

    public final byte[] a(byte[] bArr, byte[] bArr2) throws UnsupportedEncodingException {
        try {
            byte[] bytes = (ConvertUtils.bytes2HexString(bArr).toLowerCase() + ConvertUtils.bytes2HexString(bArr2).toLowerCase() + "ConfirmationKey").getBytes("ASCII");
            String str = this.f1373a;
            StringBuilder sb = new StringBuilder();
            sb.append("confirmationBytes: ");
            sb.append(ConvertUtils.bytes2HexString(bytes));
            a.a.a.a.b.m.a.c(str, sb.toString());
            byte[] bArrCalculateSha256 = SecureUtils.calculateSha256(bytes);
            if (bArrCalculateSha256 == null || bArrCalculateSha256.length < 16) {
                return null;
            }
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArrCalculateSha256, 0, bArr3, 0, 16);
            a.a.a.a.b.m.a.c(this.f1373a, "" + ConvertUtils.bytes2HexString(bArr3));
            return bArr3;
        } catch (UnsupportedEncodingException | UnsupportedCharsetException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void a(BaseMeshNode baseMeshNode, byte[] bArr) {
        byte[] bArr2;
        a.a.a.a.b.m.a.c(this.f1373a, "before split package " + ConvertUtils.bytes2HexString(bArr));
        this.f1384l = baseMeshNode;
        if (bArr.length >= 1) {
            int length = bArr.length - 1;
            bArr2 = new byte[length];
            System.arraycopy(bArr, 1, bArr2, 0, length);
        } else {
            int length2 = bArr.length;
            bArr2 = new byte[length2];
            System.arraycopy(bArr, 0, bArr2, 0, length2);
        }
        this.f1385m = new a.a.a.a.a.a.a.b.a(bArr2, c(), new v(this, baseMeshNode, bArr));
        a.a.a.a.a.g.c().b(this.f1374b);
        a.a.a.a.a.g.c().a(this.f1385m);
    }
}
