package b;

import a.a.a.a.b.a.C0218g;
import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseIntArray;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import b.u;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;
import com.alibaba.ailabs.iot.mesh.MeshService;
import com.alibaba.ailabs.iot.mesh.R;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.provision.FastProvisionManager;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback;
import com.alibaba.ailabs.iot.mesh.ut.UtError;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.alibaba.sdk.android.openaccount.ui.OpenAccountUIConstants;
import com.google.android.gms.common.ConnectionResult;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.ProxyCommunicationQuality;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.SecureUtils;

/* loaded from: classes2.dex */
public class K implements BleMeshManagerCallbacks, FastProvisionTransportCallback {

    /* renamed from: a, reason: collision with root package name */
    public static String f7315a = "SubnetsBiz";

    /* renamed from: b, reason: collision with root package name */
    public static a f7316b = new a();

    /* renamed from: c, reason: collision with root package name */
    public static int f7317c = 100;
    public int G;

    /* renamed from: d, reason: collision with root package name */
    public int f7318d;

    /* renamed from: e, reason: collision with root package name */
    public Context f7319e;

    /* renamed from: g, reason: collision with root package name */
    public boolean f7321g;

    /* renamed from: h, reason: collision with root package name */
    public C0337l f7322h;

    /* renamed from: i, reason: collision with root package name */
    public boolean f7323i;

    /* renamed from: k, reason: collision with root package name */
    public BaseMeshNode f7325k;

    /* renamed from: m, reason: collision with root package name */
    public BluetoothDevice f7327m;

    /* renamed from: n, reason: collision with root package name */
    public boolean f7328n;

    /* renamed from: o, reason: collision with root package name */
    public Handler f7329o;

    /* renamed from: p, reason: collision with root package name */
    public String f7330p;

    /* renamed from: q, reason: collision with root package name */
    public MeshService.OnDisconnectListener f7331q;

    /* renamed from: r, reason: collision with root package name */
    public c f7332r;

    /* renamed from: s, reason: collision with root package name */
    public u.a f7333s;

    /* renamed from: t, reason: collision with root package name */
    public a.a.a.a.b.k.d f7334t;

    /* renamed from: u, reason: collision with root package name */
    public C0218g f7335u;

    /* renamed from: v, reason: collision with root package name */
    public a.a.a.a.b.h.a f7336v;

    /* renamed from: w, reason: collision with root package name */
    public a.a.a.a.b.i.J f7337w;

    /* renamed from: x, reason: collision with root package name */
    public q f7338x;

    /* renamed from: f, reason: collision with root package name */
    public final Map<String, d> f7320f = new LinkedHashMap();

    /* renamed from: j, reason: collision with root package name */
    public boolean f7324j = true;

    /* renamed from: l, reason: collision with root package name */
    public int f7326l = 0;

    /* renamed from: y, reason: collision with root package name */
    public final List<String> f7339y = new ArrayList();

    /* renamed from: z, reason: collision with root package name */
    public final List<ExtendedBluetoothDevice> f7340z = new LinkedList();
    public final List<d> A = new LinkedList();
    public final Map<String, Integer> B = new LinkedHashMap();
    public final Deque<SIGMeshBizRequest> C = new LinkedList();
    public b D = null;
    public boolean E = true;
    public boolean F = false;
    public Runnable H = null;

    /* JADX INFO: Access modifiers changed from: private */
    static final class a {

        /* renamed from: a, reason: collision with root package name */
        public final Queue<b> f7341a = new LinkedList();

        /* renamed from: b, reason: collision with root package name */
        public final Handler f7342b;

        /* renamed from: c, reason: collision with root package name */
        public Runnable f7343c;

        /* renamed from: d, reason: collision with root package name */
        public final AtomicInteger f7344d;

        /* renamed from: b.K$a$a, reason: collision with other inner class name */
        public interface InterfaceC0014a {
            void a(Object obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        class b {

            /* renamed from: a, reason: collision with root package name */
            public Object f7345a;

            /* renamed from: b, reason: collision with root package name */
            public InterfaceC0014a f7346b;

            /* renamed from: c, reason: collision with root package name */
            public Runnable f7347c;

            /* renamed from: d, reason: collision with root package name */
            public int f7348d;

            public b(Object obj, InterfaceC0014a interfaceC0014a, Runnable runnable, int i2) {
                this.f7345a = obj;
                this.f7346b = interfaceC0014a;
                this.f7347c = runnable;
                this.f7348d = i2;
            }
        }

        public a() {
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper != null) {
                this.f7342b = new Handler(looperMyLooper);
            } else {
                this.f7342b = new Handler(Looper.getMainLooper());
            }
            this.f7344d = new AtomicInteger(0);
        }

        public synchronized int a(Object obj, InterfaceC0014a interfaceC0014a, Runnable runnable) {
            boolean z2 = this.f7341a.size() > 0;
            int andIncrement = this.f7344d.getAndIncrement();
            b bVar = new b(obj, interfaceC0014a, runnable, andIncrement);
            this.f7341a.add(bVar);
            if (z2) {
                return andIncrement;
            }
            a(bVar);
            if (interfaceC0014a != null) {
                interfaceC0014a.a(obj);
            }
            return andIncrement;
        }

        public synchronized void a(int i2) {
            b bVarPeek;
            b bVarPeek2 = this.f7341a.peek();
            if (bVarPeek2 != null) {
                if (bVarPeek2.f7348d != i2) {
                    return;
                }
                Runnable runnable = this.f7343c;
                if (runnable != null) {
                    this.f7342b.removeCallbacks(runnable);
                }
                try {
                    this.f7341a.remove();
                    if (!this.f7341a.isEmpty() && (bVarPeek = this.f7341a.peek()) != null && bVarPeek.f7346b != null) {
                        a(bVarPeek);
                        bVarPeek.f7346b.a(bVarPeek.f7345a);
                    }
                } catch (NoSuchElementException unused) {
                }
            }
        }

        public final void a(b bVar) {
            Handler handler = this.f7342b;
            J j2 = new J(this, bVar);
            this.f7343c = j2;
            handler.postDelayed(j2, RtspMediaSource.DEFAULT_TIMEOUT_MS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b extends a.a.a.a.b.a.K {

        /* renamed from: j, reason: collision with root package name */
        public String f7350j;

        public b() {
            super(null, K.this.C);
            this.f7350j = "" + b.class.getSimpleName();
        }

        @Override // a.a.a.a.b.a.K
        public synchronized void b() {
            if (K.this.C.size() != 0 && !this.f1251i.get()) {
                a.a.a.a.b.m.a.a(this.f7350j, "nextRequest called");
                this.f1250h = true;
                new Thread(new M(this)).start();
                return;
            }
            this.f1250h = false;
        }
    }

    public interface c {
        void onConnectionStateChanged(K k2, int i2, int i3);

        void onMeshChannelReady(K k2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class d implements InterfaceC0338m {

        /* renamed from: a, reason: collision with root package name */
        public final BleMeshManager f7352a;

        /* renamed from: b, reason: collision with root package name */
        public final C0337l f7353b;

        /* renamed from: c, reason: collision with root package name */
        public final C0218g f7354c;

        /* renamed from: d, reason: collision with root package name */
        public final BluetoothDevice f7355d;

        /* renamed from: e, reason: collision with root package name */
        public long f7356e = -1;

        /* renamed from: f, reason: collision with root package name */
        public ProxyCommunicationQuality f7357f;

        public d(BleMeshManager bleMeshManager, BluetoothDevice bluetoothDevice) {
            this.f7352a = bleMeshManager;
            this.f7355d = bluetoothDevice;
            C0337l c0337l = new C0337l(K.this.f7319e);
            this.f7353b = c0337l;
            c0337l.a(this);
            c0337l.a(K.this.f7338x);
            this.f7354c = new C0218g(c0337l);
        }

        public void e() {
            this.f7354c.c();
        }

        public void f() {
            this.f7354c.d();
        }

        @Override // b.InterfaceC0338m
        public int getMtu() {
            int mtuSize = this.f7352a.getMtuSize();
            a.a.a.a.b.m.a.a(K.f7315a, "MtuSize: " + mtuSize);
            return mtuSize;
        }

        @Override // b.InterfaceC0338m
        public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
            if (this.f7352a.getConnectState() == 2) {
                a.a.a.a.b.m.a.a(K.f7315a, String.format("Send data to node(%s) via proxy node(%s)", MeshParserUtils.bytesToHex(baseMeshNode.getUnicastAddress(), false), this.f7355d.getAddress()));
                this.f7352a.sendPdu(bArr);
                a.a.a.a.b.l.c.a(baseMeshNode.getUnicastAddressInt(), "0");
            } else if (MeshDeviceInfoManager.getInstance().isLowCostDeviceExist()) {
                a.a.a.a.b.m.a.a(K.f7315a, String.format("Send data to node(%s) via adv channel", MeshParserUtils.bytesToHex(baseMeshNode.getUnicastAddress(), false)));
                if (FastProvisionManager.getInstance().getInProvisionProgress()) {
                    a.a.a.a.b.m.a.b(K.f7315a, "Exist provision activity for tinyMesh, discard");
                    return;
                }
                if (K.this.f7334t == null) {
                    K k2 = K.this;
                    Context context = k2.f7319e;
                    byte[] bArrD = K.this.f7333s.d();
                    K k3 = K.this;
                    k2.f7334t = new a.a.a.a.b.k.d(context, bArrD, k3, k3.f7337w);
                }
                K.this.f7334t.a(baseMeshNode, bArr);
                a.a.a.a.b.l.c.a(baseMeshNode.getUnicastAddressInt(), "1");
            }
        }

        public void a(List<SIGMeshBizRequest> list) {
            for (SIGMeshBizRequest sIGMeshBizRequest : list) {
                if (sIGMeshBizRequest.l().getOpcode() == 81) {
                    sIGMeshBizRequest.a(b());
                }
            }
            this.f7354c.a(list);
        }

        public final byte[] b() {
            if (this.f7355d == null || K.this.f7333s == null) {
                return null;
            }
            return Arrays.copyOf(SecureUtils.calculateSha256(String.format("%s,%s,67656e69657368617265343536313233", this.f7355d.getAddress().replace(":", "").toLowerCase(), MeshParserUtils.bytesToHex(K.this.f7333s.d(), false).toLowerCase()).getBytes()), 16);
        }

        public List<SIGMeshBizRequest> c() {
            return this.f7354c.b();
        }

        public boolean d() {
            return this.f7352a.isConnected() && this.f7352a.isReady();
        }

        public void a() {
            this.f7352a.close();
            this.f7352a.setNeedRequestMtu(true);
            f();
        }

        public d(BleMeshManager bleMeshManager, C0337l c0337l, BluetoothDevice bluetoothDevice) {
            this.f7352a = bleMeshManager;
            this.f7353b = c0337l;
            c0337l.a(this);
            c0337l.a(K.this.f7338x);
            this.f7355d = bluetoothDevice;
            this.f7354c = new C0218g(c0337l);
        }

        public void a(long j2) {
            this.f7356e = j2;
            this.f7354c.a(j2);
        }

        public void a(int i2) {
            this.f7354c.a(i2);
        }

        public void a(BleMeshManager.WriteReadType writeReadType) {
            BleMeshManager bleMeshManager = this.f7352a;
            if (bleMeshManager == null || !bleMeshManager.isConnected()) {
                return;
            }
            this.f7352a.changeReadWriteType(writeReadType);
        }

        public void a(ProxyCommunicationQuality proxyCommunicationQuality) {
            this.f7357f = proxyCommunicationQuality;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    interface e {
        void a(@NonNull d dVar);
    }

    public K(Context context, u.a aVar, q qVar) {
        this.f7318d = 3;
        this.G = 0;
        this.f7319e = context;
        f7315a += (hashCode() % 1000000);
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(hashCode() % 1000000));
        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
        int i2 = this.G;
        this.G = i2 + 1;
        sb.append(i2);
        String string = sb.toString();
        this.f7329o = new Handler(Looper.getMainLooper());
        this.f7333s = aVar;
        this.f7328n = aVar.i();
        this.f7318d = 1;
        C0337l c0337l = new C0337l(this.f7319e);
        this.f7322h = c0337l;
        c0337l.a(qVar);
        this.f7322h.a(new A(this));
        this.f7338x = qVar;
        this.f7335u = new C0218g(this.f7322h, string);
        this.f7321g = this.f7328n;
        BLEScannerProxy.getInstance().setOnMeshNetworkPUDListener(new B(this));
    }

    public boolean l() {
        return this.f7328n && this.f7321g && this.f7339y.size() < this.f7318d;
    }

    public final void m() {
        if (this.F) {
            BleMeshManager.WriteReadType writeReadType = BleMeshManager.WriteReadType.WRITE_AND_READ;
            for (Map.Entry<String, d> entry : this.f7320f.entrySet()) {
                BleMeshManager bleMeshManager = entry.getValue().f7352a;
                if (bleMeshManager != null && bleMeshManager.isConnected()) {
                    a.a.a.a.b.m.a.a(f7315a, String.format("read-write-splitting, make %s a %s node", entry.getKey(), writeReadType.toString()));
                    bleMeshManager.changeReadWriteType(writeReadType);
                    BleMeshManager.WriteReadType writeReadType2 = BleMeshManager.WriteReadType.WRITE_AND_READ;
                    writeReadType = writeReadType == writeReadType2 ? BleMeshManager.WriteReadType.WRITE : writeReadType2;
                }
            }
        }
    }

    public void n() {
        e();
    }

    public final void o() {
        b bVar;
        if (this.C.size() <= 0 || (bVar = this.D) == null) {
            return;
        }
        bVar.c();
    }

    @Override // aisble.BleManagerCallbacks
    public void onBatteryValueReceived(@NonNull BluetoothDevice bluetoothDevice, int i2) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBonded(@NonNull BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingFailed(@NonNull BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onBondingRequired(@NonNull BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataReceived(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        a.a.a.a.b.m.a.a(f7315a, "Received data from device: " + bluetoothDevice.getAddress());
        BaseMeshNode baseMeshNode = this.f7325k;
        if (baseMeshNode == null) {
            a.a.a.a.b.m.a.d(f7315a, "The mesh node is null, discard the received data!!!");
            return;
        }
        d dVar = this.f7320f.get(bluetoothDevice.getAddress());
        if (dVar == null) {
            a.a.a.a.b.m.a.d(f7315a, "Received message from unknown proxy interface, discard the received data!!!");
            return;
        }
        int realtimeRssiForProxyNode = dVar.f7352a.getRealtimeRssiForProxyNode();
        if (this.f7336v == null) {
            this.f7336v = new a.a.a.a.b.h.a();
        }
        this.f7336v.a(realtimeRssiForProxyNode);
        dVar.f7353b.a(baseMeshNode, i2, bArr, this.f7336v);
    }

    @Override // com.alibaba.ailabs.iot.mesh.ble.BleMeshManagerCallbacks
    public void onDataSent(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnected(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.c(f7315a, String.format("Subnets(%s, %s) connected", this.f7330p, bluetoothDevice.getAddress()));
        c(bluetoothDevice);
        m();
        a(bluetoothDevice, new G(this));
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceConnecting(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(f7315a, String.format("Subnets(%s, %s) connecting", this.f7330p, bluetoothDevice.getAddress()));
        d();
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnected(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.c(f7315a, String.format("Subnets(%s, %s) disconnected", this.f7330p, bluetoothDevice.getAddress()));
        c(bluetoothDevice);
        this.f7339y.remove(bluetoothDevice.getAddress());
        Integer num = this.B.get(bluetoothDevice.getAddress());
        if (num != null) {
            f7316b.a(num.intValue());
        }
        a.a.a.a.b.m.a.a(f7315a, "pending connect and connected queue size: " + this.f7339y.size());
        int i2 = this.f7326l;
        d();
        m();
        a(bluetoothDevice, new H(this));
        if (this.f7328n) {
            a(j());
        }
        int i3 = this.f7326l;
        if (i3 == 0) {
            a(i2, i3);
            k();
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceDisconnecting(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.a(f7315a, String.format("Subnets(%s, %s) disconnecting", this.f7330p, bluetoothDevice.getAddress()));
        d();
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceNotSupported(@NonNull BluetoothDevice bluetoothDevice) {
    }

    @Override // aisble.BleManagerCallbacks
    public void onDeviceReady(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.c(f7315a, String.format("Subnets(%s, %s) ready", this.f7330p, bluetoothDevice.getAddress()));
        if (this.f7327m == null) {
            this.f7327m = bluetoothDevice;
        }
        int i2 = this.f7326l;
        Integer num = this.B.get(bluetoothDevice.getAddress());
        if (num != null) {
            f7316b.a(num.intValue());
        }
        this.f7326l = 2;
        c();
        a(i2, this.f7326l);
        c cVar = this.f7332r;
        if (cVar != null) {
            cVar.onMeshChannelReady(this);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onError(@NonNull BluetoothDevice bluetoothDevice, String str, int i2) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onFastProvisionDataSend(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.i.J j2 = this.f7337w;
        if (j2 != null && j2.e()) {
            a.a.a.a.b.m.a.b(f7315a, "Exist provision activity for tinyMesh, discard");
            return;
        }
        a.a.a.a.b.m.a.c(f7315a, "onFastProvisionDataSend: " + ConvertUtils.bytes2HexString(bArr));
        this.f7322h.a(baseMeshNode, 18, bArr);
    }

    @Override // aisble.BleManagerCallbacks
    public void onLinkLossOccurred(@NonNull BluetoothDevice bluetoothDevice) {
        a.a.a.a.b.m.a.d(f7315a, String.format("Subnets(%s, %s) loss link", this.f7330p, bluetoothDevice.getAddress()));
        c(bluetoothDevice);
        this.f7339y.remove(bluetoothDevice.getAddress());
        int i2 = this.f7326l;
        d();
        m();
        a(bluetoothDevice, new I(this));
        if (this.f7328n) {
            a(j());
        }
        if (this.f7326l == 0) {
            this.f7323i = false;
            if (this.f7328n && i2 == 2) {
                a(false, UtError.MESH_LINK_LOSS_OCCURRED.getMsg());
                a(a(R.string.state_linkloss_occur));
            }
            k();
        }
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionTransportCallback
    public void onReceiveFastProvisionData(BaseMeshNode baseMeshNode, byte[] bArr) {
        a.a.a.a.b.i.J j2 = this.f7337w;
        if (j2 != null && j2.e()) {
            a.a.a.a.b.m.a.b(f7315a, "Exist provision activity for tinyMesh, discard");
        } else {
            this.f7323i = false;
            this.f7322h.a(baseMeshNode, 18, bArr, (a.a.a.a.b.h.a) null);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public void onServicesDiscovered(@NonNull BluetoothDevice bluetoothDevice, boolean z2) {
        a.a.a.a.b.m.a.a(f7315a, "onServicesDiscovered...");
        a(a(R.string.state_initializing));
        if (this.f7323i) {
            this.f7323i = false;
        } else if (this.f7324j && this.f7328n) {
            a(bluetoothDevice);
        }
    }

    @Override // aisble.BleManagerCallbacks
    public boolean shouldEnableBatteryLevelNotifications(@NonNull BluetoothDevice bluetoothDevice) {
        return false;
    }

    public final void c(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        Iterator<ExtendedBluetoothDevice> it = this.f7340z.iterator();
        while (it.hasNext()) {
            if (bluetoothDevice.getAddress().equalsIgnoreCase(it.next().getAddress())) {
                it.remove();
                return;
            }
        }
    }

    public final void d() {
        int i2;
        if (this.f7320f.size() == 0) {
            this.f7326l = 0;
            return;
        }
        int i3 = this.f7326l;
        int size = this.f7320f.size();
        SparseIntArray sparseIntArray = new SparseIntArray();
        Iterator<Map.Entry<String, d>> it = this.f7320f.entrySet().iterator();
        while (it.hasNext()) {
            BleMeshManager bleMeshManager = it.next().getValue().f7352a;
            sparseIntArray.put(bleMeshManager.getConnectState(), sparseIntArray.get(bleMeshManager.getConnectState(), 0) + 1);
        }
        if (sparseIntArray.get(2) > 0) {
            if (this.f7326l != 2) {
                o();
            }
            this.f7326l = 2;
            this.f7321g = this.f7339y.size() < this.f7318d;
            a.a.a.a.b.m.a.c(f7315a, "Current connection state change to STATE_CONNECTED, mIsMultiProxyAcceptable: " + this.f7321g);
        } else if (sparseIntArray.get(0) == size) {
            this.f7326l = 0;
            this.f7321g = true;
            a.a.a.a.b.m.a.d(f7315a, "Current connection state change to STATE_DISCONNECTED");
        } else if (sparseIntArray.get(1) > 0) {
            this.f7326l = 1;
        } else {
            this.f7326l = 3;
        }
        c cVar = this.f7332r;
        if (cVar == null || i3 == (i2 = this.f7326l)) {
            return;
        }
        cVar.onConnectionStateChanged(this, i3, i2);
    }

    public final void e() {
        a.a.a.a.b.m.a.a(f7315a, "Enqueue disconnect task");
        if (this.f7320f.size() == 0) {
            return;
        }
        Iterator<Map.Entry<String, d>> it = this.f7320f.entrySet().iterator();
        while (it.hasNext()) {
            BleMeshManager bleMeshManager = it.next().getValue().f7352a;
            if (bleMeshManager.getConnectState() == 2 || bleMeshManager.getConnectState() == 1) {
                bleMeshManager.disconnect().enqueue();
            }
        }
    }

    public int f() {
        return this.f7326l;
    }

    public C0337l g() {
        for (d dVar : this.A) {
            if (dVar.d()) {
                return dVar.f7353b;
            }
        }
        return this.f7322h;
    }

    public BluetoothDevice h() {
        return this.f7327m;
    }

    public List<BluetoothDevice> i() {
        LinkedList linkedList = new LinkedList();
        for (d dVar : this.A) {
            if (dVar.d()) {
                linkedList.add(dVar.f7355d);
            }
        }
        return linkedList;
    }

    public u.a j() {
        return this.f7333s;
    }

    public final void k() {
        MeshService.OnDisconnectListener onDisconnectListener = this.f7331q;
        if (onDisconnectListener != null) {
            onDisconnectListener.onDisconnected();
            this.f7331q = null;
        }
    }

    public void b(String str) {
        this.f7330p = str;
    }

    public void b(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) {
        if (extendedBluetoothDevice != null && this.f7339y.size() < this.f7318d) {
            if (b(extendedBluetoothDevice)) {
                a.a.a.a.b.m.a.d(f7315a, "Connect, already connected or pending connect to the device: " + extendedBluetoothDevice.getAddress() + ", do nothing");
                return;
            }
            this.f7339y.add(extendedBluetoothDevice.getAddress());
            boolean z3 = this.f7340z.size() == 0;
            this.f7340z.add(extendedBluetoothDevice);
            if (this.f7328n) {
                if (z3) {
                    Handler handler = this.f7329o;
                    C c2 = new C(this);
                    this.H = c2;
                    handler.postDelayed(c2, 3000L);
                }
                if (this.f7339y.size() >= this.f7318d) {
                    this.f7329o.removeCallbacks(this.H);
                    b();
                    Iterator<ExtendedBluetoothDevice> it = this.f7340z.iterator();
                    while (it.hasNext()) {
                        a(it.next());
                    }
                    this.f7340z.clear();
                    return;
                }
                return;
            }
            a(extendedBluetoothDevice);
            return;
        }
        a.a.a.a.b.m.a.b(f7315a, "Connect, device is null or exceeded pending queue limit");
    }

    public final void c(ExtendedBluetoothDevice extendedBluetoothDevice) {
        BleMeshManager bleMeshManagerA = a(extendedBluetoothDevice, true);
        if (bleMeshManagerA == null) {
            a.a.a.a.b.m.a.b(f7315a, "Connect, error when allocate bleMeshManager");
            return;
        }
        if (extendedBluetoothDevice.getScanRecord() != null) {
            UnprovisionedMeshNodeData unprovisionedMeshNodeData = new UnprovisionedMeshNodeData(extendedBluetoothDevice.getScanRecord().getServiceData(new ParcelUuid(BleMeshManager.MESH_PROVISIONING_UUID)));
            a.a.a.a.b.m.a.c(f7315a, "unprovisionedMeshNodeData.isFastProvisionMesh ? " + unprovisionedMeshNodeData.isFastProvisionMesh());
            if (unprovisionedMeshNodeData.isFastProvisionMesh()) {
                return;
            }
            bleMeshManagerA.connect(extendedBluetoothDevice.getDevice()).enqueue();
            this.f7324j = true;
            return;
        }
        a.a.a.a.b.m.a.c(f7315a, "mScannerRecord is null");
        bleMeshManagerA.connect(extendedBluetoothDevice.getDevice()).retry(10, ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED).enqueue();
        this.f7324j = true;
    }

    public void a(BaseMeshNode baseMeshNode) {
        this.f7325k = baseMeshNode;
    }

    public boolean a(BleMeshManager bleMeshManager, ProvisionedMeshNode provisionedMeshNode, a.a.a.a.b.i.J j2) {
        d dVar;
        int i2 = this.f7326l;
        if ((i2 == 2 || i2 == 1) && !l()) {
            a.a.a.a.b.m.a.d(f7315a, "Current connection state is connected or connecting: " + this.f7326l);
            return false;
        }
        if (bleMeshManager == null || bleMeshManager.getBluetoothDevice() == null || bleMeshManager.getConnectState() == 0) {
            return false;
        }
        a.a.a.a.b.m.a.c(f7315a, String.format("%s attach connection status", this.f7330p));
        this.f7339y.add(bleMeshManager.getBluetoothDevice().getAddress());
        if (this.f7320f.size() == 0) {
            dVar = new d(bleMeshManager, this.f7322h, bleMeshManager.getBluetoothDevice());
        } else {
            dVar = new d(bleMeshManager, bleMeshManager.getBluetoothDevice());
        }
        this.f7320f.put(bleMeshManager.getBluetoothDevice().getAddress(), dVar);
        this.A.add(dVar);
        this.f7337w = j2;
        bleMeshManager.setGattCallbacks(this);
        bleMeshManager.setProvisioningComplete(true);
        int i3 = this.f7326l;
        this.f7327m = bleMeshManager.getBluetoothDevice();
        this.f7326l = bleMeshManager.getConnectState();
        this.f7325k = provisionedMeshNode;
        d();
        a(i3, this.f7326l);
        return true;
    }

    public final void c() {
        Iterator<d> it = this.A.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (it.next().d()) {
                i2++;
            }
        }
        long j2 = 0;
        for (d dVar : this.A) {
            if (dVar.d()) {
                dVar.a(j2);
                j2 += f7317c;
                dVar.a(i2);
            }
        }
    }

    public final void b() {
        byte[] bArrA = SIGMeshBizRequestGenerator.a((byte) 8, this.f7339y, this.f7333s.c(), 0);
        a.a.a.a.a.g.c().d();
        a.a.a.a.a.g.c().a(bArrA, 500, new F(this));
    }

    public void b(BluetoothDevice bluetoothDevice) {
        int i2 = this.f7326l;
        if (i2 == 2 || i2 == 1) {
            a.a.a.a.b.m.a.c(f7315a, "No need to connect again");
            return;
        }
        if (bluetoothDevice == null) {
            a.a.a.a.b.m.a.b(f7315a, "device is null");
            return;
        }
        a.a.a.a.b.m.a.a(f7315a, "connect to specified device: " + bluetoothDevice.getAddress());
        b(new ExtendedBluetoothDevice(new ScanResult(bluetoothDevice, ScanRecord.parseFromBytes(new byte[0]), -50, 0L)), true);
    }

    public void a(c cVar) {
        this.f7332r = cVar;
    }

    public void a(Map<ProxyCommunicationQuality, List<ExtendedBluetoothDevice>> map) {
        char c2;
        char c3 = 2;
        if (map != null && this.f7339y.size() <= this.f7318d) {
            byte[] bArr = new byte[3];
            bArr[0] = 0;
            bArr[1] = 0;
            bArr[2] = 0;
            for (d dVar : this.A) {
                if (dVar != null && dVar.f7352a.isConnected() && dVar.f7357f != null && dVar.f7357f.getLevel() != 0) {
                    int level = dVar.f7357f.getLevel() - 1;
                    bArr[level] = (byte) (bArr[level] + 1);
                }
            }
            int i2 = (this.f7318d + 1) / 2;
            int i3 = 2;
            while (i3 >= 0) {
                int i4 = i2 - bArr[i3];
                if (i4 < 0) {
                    i4 = 0;
                }
                int i5 = i3 + 1;
                List<ExtendedBluetoothDevice> list = map.get(ProxyCommunicationQuality.getQualityViaLevel(i5));
                if (list != null && list.size() > 0) {
                    int i6 = 0;
                    while (i6 < i4 && i6 < list.size()) {
                        if (this.f7339y.contains(list.get(i6).getAddress())) {
                            c2 = c3;
                        } else {
                            c2 = 2;
                            a.a.a.a.b.m.a.a("multi_proxy_selector", String.format(Locale.getDefault(), "Connect %s, quality: %s, Rssi: %d", list.get(i6).getAddress(), ProxyCommunicationQuality.getQualityViaLevel(i5), Integer.valueOf(list.get(i6).getRssi())));
                            b(list.get(i6), true);
                            i4--;
                        }
                        i6++;
                        c3 = c2;
                    }
                }
                i2 = i4 + 1;
                i3--;
                c3 = c3;
            }
            return;
        }
        a.a.a.a.b.m.a.b(f7315a, "Connect, device is null or exceeded pending queue limit");
    }

    public final boolean b(ExtendedBluetoothDevice extendedBluetoothDevice) {
        if (extendedBluetoothDevice == null || TextUtils.isEmpty(extendedBluetoothDevice.getAddress())) {
            return false;
        }
        return this.f7339y.contains(extendedBluetoothDevice.getAddress());
    }

    public void b(int i2) {
        this.f7318d = i2;
    }

    public final void a(ExtendedBluetoothDevice extendedBluetoothDevice) {
        this.B.put(extendedBluetoothDevice.getAddress(), Integer.valueOf(f7316b.a(extendedBluetoothDevice, new D(this, extendedBluetoothDevice), new E(this, extendedBluetoothDevice))));
    }

    public void a(MeshService.OnDisconnectListener onDisconnectListener) {
        d();
        a.a.a.a.b.m.a.a(f7315a, "Prepare disconnect, current connection state:" + this.f7326l);
        this.f7331q = onDisconnectListener;
        if (this.f7326l == 0) {
            onDisconnectListener.onDisconnected();
            this.f7331q = null;
        } else {
            e();
        }
    }

    public void a(List<SIGMeshBizRequest> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        ArrayList<Pair> arrayList = new ArrayList();
        int size = 0;
        int i2 = 0;
        for (Map.Entry<String, d> entry : this.f7320f.entrySet()) {
            if (entry.getValue().d()) {
                i2++;
                arrayList.add(new Pair(entry.getValue(), new ArrayList()));
            }
        }
        if (i2 > 0) {
            LinkedList linkedList = new LinkedList();
            if (this.E) {
                synchronized (this.C) {
                    this.C.addAll(list);
                }
                synchronized (this) {
                    try {
                        if (this.D == null) {
                            b bVar = new b();
                            this.D = bVar;
                            bVar.b(100);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.D.c();
                return;
            }
            for (SIGMeshBizRequest sIGMeshBizRequest : list) {
                Pair pair = (Pair) arrayList.get(size);
                ((ArrayList) pair.second).add(sIGMeshBizRequest);
                sIGMeshBizRequest.a(((d) pair.first).f7353b);
                linkedList.add(sIGMeshBizRequest);
                size = (size + 1) % arrayList.size();
            }
            for (Pair pair2 : arrayList) {
                ((d) pair2.first).a((List<SIGMeshBizRequest>) pair2.second);
            }
            return;
        }
        if (this.f7333s.f()) {
            this.f7335u.a(list);
            return;
        }
        Iterator<SIGMeshBizRequest> it = list.iterator();
        while (it.hasNext()) {
            Utils.notifyFailed(it.next().m(), -23, "Unreachable");
        }
    }

    public void a(boolean z2) {
        this.f7335u.a(z2);
    }

    public final String a(int i2) {
        return this.f7319e.getString(i2);
    }

    public final void a(BluetoothDevice bluetoothDevice) {
        if (this.f7325k == null || bluetoothDevice == null) {
            return;
        }
        d dVar = this.f7320f.get(bluetoothDevice.getAddress());
        if (dVar != null) {
            C0337l c0337l = dVar.f7353b;
            ProvisionedMeshNode provisionedMeshNode = (ProvisionedMeshNode) this.f7325k;
            c0337l.a(provisionedMeshNode, 0, new byte[]{0});
            this.f7329o.postDelayed(new z(this, c0337l, provisionedMeshNode), 500L);
            return;
        }
        a.a.a.a.b.m.a.b(f7315a, "Internal error");
    }

    public final void a(boolean z2, String str) {
        Intent intent = new Intent(Utils.ACTION_IS_CONNECTED);
        intent.putExtra(Utils.EXTRA_DATA, z2);
        if (!TextUtils.isEmpty(str)) {
            intent.putExtra(Utils.EXTRA_CONNECT_FAIL_MSG, str);
        }
        LocalBroadcastManager.getInstance(this.f7319e).sendBroadcast(intent);
    }

    public final void a(String str) {
        Intent intent = new Intent(Utils.ACTION_CONNECTION_STATE);
        intent.putExtra(Utils.EXTRA_DATA, str);
        LocalBroadcastManager.getInstance(this.f7319e).sendBroadcast(intent);
    }

    public final void a(int i2, int i3) {
        if ((i3 == 2 || i3 == 0) && this.f7324j && this.f7328n) {
            if ((i2 == 1 && i3 == 2) || ((i2 == 2 && i3 == 0) || ((i2 == 3 && i3 == 0) || (i2 == 0 && i3 == 2)))) {
                a(i3 == 2, (String) null);
                if (i3 == 2) {
                    a(a(R.string.state_disconnected));
                }
            }
        }
    }

    public final BleMeshManager a(ExtendedBluetoothDevice extendedBluetoothDevice, boolean z2) {
        d dVar;
        if (extendedBluetoothDevice == null || extendedBluetoothDevice.getDevice() == null) {
            return null;
        }
        BluetoothDevice device = extendedBluetoothDevice.getDevice();
        String address = device.getAddress();
        d dVar2 = this.f7320f.get(address);
        ProxyCommunicationQuality qualityViaRssi = ProxyCommunicationQuality.getQualityViaRssi(extendedBluetoothDevice.getRssi());
        a.a.a.a.b.m.a.c(f7315a, "allocate mesh channel, communication quality: " + qualityViaRssi.getLevel());
        if (dVar2 != null && !z2) {
            dVar2.a(qualityViaRssi);
            return dVar2.f7352a;
        }
        Context context = this.f7319e;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(hashCode() % 1000000));
        sb.append(OpenAccountUIConstants.UNDER_LINE);
        int i2 = this.G;
        this.G = i2 + 1;
        sb.append(i2);
        BleMeshManager bleMeshManager = new BleMeshManager(context, sb.toString());
        bleMeshManager.setGattCallbacks(this);
        bleMeshManager.setProvisioningComplete(true);
        if (this.f7320f.size() == 0) {
            dVar = new d(bleMeshManager, this.f7322h, device);
        } else {
            dVar = new d(bleMeshManager, device);
        }
        dVar.a(qualityViaRssi);
        this.A.add(dVar);
        this.f7320f.put(address, dVar);
        return bleMeshManager;
    }

    public final void a(d dVar) {
        List<SIGMeshBizRequest> listC = dVar.c();
        if (listC == null || listC.size() == 0) {
            return;
        }
        a(listC);
    }

    public final void a(BluetoothDevice bluetoothDevice, e eVar) {
        d dVar;
        if (bluetoothDevice == null || eVar == null || (dVar = this.f7320f.get(bluetoothDevice.getAddress())) == null) {
            return;
        }
        eVar.a(dVar);
    }

    public final void a(u.a aVar) {
        a.a.a.a.b.m.a.c(f7315a, "Trigger re-connect " + aVar);
        a.a.a.a.b.G.a().d().a(aVar, false);
    }
}
