package a.a.a.a.b.a;

import b.C0337l;
import b.u;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequest;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.Deque;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import meshprovisioner.configuration.ProvisionedMeshNode;

/* loaded from: classes.dex */
public class K extends C0215d {

    /* renamed from: b, reason: collision with root package name */
    public static int f1244b = 3;

    /* renamed from: c, reason: collision with root package name */
    public static int f1245c = 300;

    /* renamed from: d, reason: collision with root package name */
    public String f1246d;

    /* renamed from: e, reason: collision with root package name */
    public final Deque<SIGMeshBizRequest> f1247e;

    /* renamed from: f, reason: collision with root package name */
    public int f1248f;

    /* renamed from: g, reason: collision with root package name */
    public int f1249g;

    /* renamed from: h, reason: collision with root package name */
    public volatile boolean f1250h;

    /* renamed from: i, reason: collision with root package name */
    public final AtomicBoolean f1251i;

    public K(C0337l c0337l, Deque<SIGMeshBizRequest> deque) {
        super(c0337l);
        this.f1246d = "" + K.class.getSimpleName();
        this.f1248f = 0;
        this.f1249g = 0;
        this.f1250h = false;
        this.f1251i = new AtomicBoolean(false);
        this.f1247e = deque;
    }

    public synchronized void b() {
        if (this.f1247e.size() != 0 && !this.f1251i.get()) {
            this.f1250h = true;
            new Thread(new J(this)).start();
            return;
        }
        this.f1250h = false;
    }

    public void c() {
        this.f1251i.set(false);
        if (this.f1250h) {
            a.a.a.a.b.m.a.d(this.f1246d, "Already in flight status, do nothing");
        } else {
            a.a.a.a.b.m.a.a(this.f1246d, "start called");
            b();
        }
    }

    public void d() {
        this.f1251i.set(true);
    }

    public boolean a() {
        return this.f1250h;
    }

    public void a(int i2) {
        f1244b = i2;
    }

    @Override // a.a.a.a.b.a.C0215d
    public void b(SIGMeshBizRequest sIGMeshBizRequest) {
        ProvisionedMeshNode provisionedMeshNode;
        try {
            sIGMeshBizRequest.q();
        } catch (Exception e2) {
            a.a.a.a.b.m.a.b(this.f1246d, e2.toString());
        }
        String bluetoothAddress = TmpConstant.GROUP_ROLE_UNKNOWN;
        u.a aVarD = a.a.a.a.b.G.a().d().d();
        if (aVarD != null && (provisionedMeshNode = (ProvisionedMeshNode) aVarD.d(sIGMeshBizRequest.j())) != null) {
            bluetoothAddress = provisionedMeshNode.getBluetoothAddress();
        }
        a.a.a.a.b.m.a.d(this.f1246d, String.format(Locale.US, "Request(to %s:%s) timeout", Utils.bytes2HexString(sIGMeshBizRequest.j()), bluetoothAddress));
        this.f1249g++;
        if (sIGMeshBizRequest.a()) {
            synchronized (this.f1247e) {
                this.f1247e.add(sIGMeshBizRequest);
            }
        } else {
            Utils.notifyFailed(sIGMeshBizRequest.m(), -13, "Timeout! the device is not reply");
        }
        if (this.f1249g == this.f1248f) {
            if (this.f1247e.isEmpty()) {
                this.f1250h = false;
            } else {
                b();
            }
        }
    }

    @Override // a.a.a.a.b.a.C0215d
    public void c(SIGMeshBizRequest sIGMeshBizRequest) {
        String bluetoothAddress;
        ProvisionedMeshNode provisionedMeshNode;
        try {
            sIGMeshBizRequest.q();
        } catch (Exception e2) {
            a.a.a.a.b.m.a.b(this.f1246d, e2.toString());
        }
        u.a aVarD = a.a.a.a.b.G.a().d().d();
        if (aVarD != null && (provisionedMeshNode = (ProvisionedMeshNode) aVarD.d(sIGMeshBizRequest.j())) != null) {
            bluetoothAddress = provisionedMeshNode.getBluetoothAddress();
        } else {
            bluetoothAddress = TmpConstant.GROUP_ROLE_UNKNOWN;
        }
        a.a.a.a.b.m.a.c(this.f1246d, String.format(Locale.US, "Request(to %s:%s) processed", Utils.bytes2HexString(sIGMeshBizRequest.j()), bluetoothAddress));
        int i2 = this.f1249g + 1;
        this.f1249g = i2;
        if (i2 == this.f1248f) {
            if (this.f1247e.isEmpty()) {
                this.f1250h = false;
            } else {
                b();
            }
        }
    }

    public K(C0337l c0337l, Deque<SIGMeshBizRequest> deque, String str) {
        this(c0337l, deque);
        this.f1246d += str;
    }

    public void b(int i2) {
        f1245c = i2;
    }
}
