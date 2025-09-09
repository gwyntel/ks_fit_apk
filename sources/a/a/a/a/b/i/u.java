package a.a.a.a.b.i;

import aisble.callback.DataReceivedCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.media3.exoplayer.ExoPlayer;
import b.InterfaceC0329d;
import com.alibaba.ailabs.iot.mesh.callback.IConnectCallback;
import com.alibaba.ailabs.iot.mesh.provision.callback.AliMeshProvisioningFrameworkStatusCallbacks;
import com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback;
import com.alibaba.ailabs.iot.mesh.provision.state.FastProvisioningState;
import datasource.bean.ProvisionInfo;
import meshprovisioner.BaseMeshNode;
import meshprovisioner.configuration.ProvisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNode;
import meshprovisioner.states.UnprovisionedMeshNodeData;
import meshprovisioner.utils.MeshParserUtils;
import meshprovisioner.utils.UnprovisionedMeshNodeUtil;

/* loaded from: classes.dex */
public class u implements InterfaceC0329d, FastProvisionV2StatusCallback {

    /* renamed from: a, reason: collision with root package name */
    public String f1500a;

    /* renamed from: b, reason: collision with root package name */
    public String f1501b;

    /* renamed from: c, reason: collision with root package name */
    public Context f1502c;

    /* renamed from: d, reason: collision with root package name */
    public AliMeshProvisioningFrameworkStatusCallbacks f1503d;

    /* renamed from: e, reason: collision with root package name */
    public IConnectCallback f1504e;

    /* renamed from: f, reason: collision with root package name */
    public UnprovisionedMeshNodeData f1505f;

    /* renamed from: g, reason: collision with root package name */
    public UnprovisionedMeshNode f1506g;

    /* renamed from: h, reason: collision with root package name */
    public ProvisionedMeshNode f1507h;

    /* renamed from: i, reason: collision with root package name */
    public BluetoothDevice f1508i;

    /* renamed from: j, reason: collision with root package name */
    public a.a.a.a.b.i.c.a f1509j;

    /* renamed from: k, reason: collision with root package name */
    public FastProvisioningState f1510k;

    /* renamed from: l, reason: collision with root package name */
    public b.u f1511l;

    /* renamed from: m, reason: collision with root package name */
    public ProvisionInfo f1512m;

    /* renamed from: n, reason: collision with root package name */
    public Handler f1513n;

    /* renamed from: o, reason: collision with root package name */
    public Runnable f1514o;

    /* renamed from: p, reason: collision with root package name */
    public Runnable f1515p;

    /* renamed from: q, reason: collision with root package name */
    public int f1516q;

    /* renamed from: r, reason: collision with root package name */
    public IConnectCallback f1517r;

    /* renamed from: s, reason: collision with root package name */
    public final DataReceivedCallback f1518s;

    public u(Context context) {
        this.f1500a = "FastProvisionV2Worker";
        this.f1514o = null;
        this.f1515p = null;
        this.f1517r = new C0246o(this);
        this.f1518s = new C0247p(this);
        this.f1502c = context;
        this.f1511l = a.a.a.a.b.G.a().d();
        this.f1513n = new Handler(Looper.getMainLooper());
    }

    @Override // b.InterfaceC0329d
    public void a(ProvisionedMeshNode provisionedMeshNode) {
    }

    @Override // b.InterfaceC0329d
    public void b(ProvisionedMeshNode provisionedMeshNode) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback
    public void onProvisioningComplete(ProvisionedMeshNode provisionedMeshNode) {
        this.f1507h = provisionedMeshNode;
        provisionedMeshNode.setIsProvisioned(true);
        this.f1507h.setDevId(MeshParserUtils.bytesToHex(this.f1505f.getDeviceUuid(), false));
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback
    public void onProvisioningDataSent(UnprovisionedMeshNode unprovisionedMeshNode) {
    }

    @Override // com.alibaba.ailabs.iot.mesh.provision.callback.FastProvisionV2StatusCallback
    public void onProvisioningFailed(BaseMeshNode baseMeshNode, int i2, String str) {
        a.a.a.a.b.m.a.c(this.f1500a, "onProvisionFailed: errorCode = " + i2 + ", errorMsg = " + str);
        c();
        AliMeshProvisioningFrameworkStatusCallbacks aliMeshProvisioningFrameworkStatusCallbacks = this.f1503d;
        if (aliMeshProvisioningFrameworkStatusCallbacks != null) {
            aliMeshProvisioningFrameworkStatusCallbacks.onProvisioningFailed(this.f1506g, i2);
        }
    }

    @Override // b.InterfaceC0329d
    public void sendPdu(BaseMeshNode baseMeshNode, byte[] bArr) {
        this.f1509j.a(bArr, new q(this));
    }

    public void c() {
        if (this.f1509j != null) {
            a.a.a.a.b.m.a.c(this.f1500a, "Release transport layer.");
            this.f1509j.a();
        }
    }

    public final void d() {
        this.f1516q++;
        a.a.a.a.b.i.b.a aVar = new a.a.a.a.b.i.b.a(this.f1507h, this, this, this.f1512m);
        this.f1510k = aVar;
        aVar.a();
    }

    public final void e() {
        a.a.a.a.b.i.b.b bVar = new a.a.a.a.b.i.b.b(this.f1506g, this, this, this.f1512m);
        this.f1510k = bVar;
        bVar.a();
        a(a.a.a.a.b.i.b.a.class);
    }

    public final void f() {
        Handler handler = this.f1513n;
        s sVar = new s(this);
        this.f1515p = sVar;
        handler.postDelayed(sVar, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
    }

    public final void g() {
        this.f1513n.removeCallbacks(this.f1515p);
    }

    public a.a.a.a.b.i.c.a b() {
        return this.f1509j;
    }

    public void a(UnprovisionedMeshNodeData unprovisionedMeshNodeData, BluetoothDevice bluetoothDevice, AliMeshProvisioningFrameworkStatusCallbacks aliMeshProvisioningFrameworkStatusCallbacks, IConnectCallback iConnectCallback) {
        this.f1505f = unprovisionedMeshNodeData;
        this.f1508i = bluetoothDevice;
        this.f1503d = aliMeshProvisioningFrameworkStatusCallbacks;
        this.f1504e = iConnectCallback;
        a.a.a.a.b.i.c.r rVar = new a.a.a.a.b.i.c.r(this.f1501b);
        this.f1509j = rVar;
        rVar.init(this.f1502c);
        this.f1509j.a(this.f1518s);
    }

    public u(Context context, String str) {
        this(context);
        this.f1501b = str;
        this.f1500a += str;
    }

    public void a(ProvisionInfo provisionInfo) {
        if (provisionInfo == null) {
            return;
        }
        this.f1512m = provisionInfo;
        this.f1506g = UnprovisionedMeshNodeUtil.buildUnprovisionedMeshNode(this.f1502c, this.f1508i.getAddress(), "", MeshParserUtils.bytesToHex(MeshParserUtils.toByteArray(provisionInfo.getNetKeys().get(0)), false), (provisionInfo.getNetKeyIndexes() == null || provisionInfo.getAppKeyIndexes().size() <= 0) ? 0 : provisionInfo.getNetKeyIndexes().get(0).intValue(), 0, 0, provisionInfo.getPrimaryUnicastAddress().intValue(), 5, this.f1511l.b(), this.f1505f.getDeviceUuid(), null);
        this.f1509j.a(this.f1508i, this.f1517r);
    }

    public final void a(Class cls) {
        Handler handler = this.f1513n;
        r rVar = new r(this, cls);
        this.f1514o = rVar;
        handler.postDelayed(rVar, 1500L);
    }

    public final void a() {
        Runnable runnable = this.f1514o;
        if (runnable != null) {
            this.f1513n.removeCallbacks(runnable);
        }
    }
}
