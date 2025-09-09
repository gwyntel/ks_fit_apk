package a.a.a.a.b;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.os.Looper;
import com.alibaba.ailabs.iot.mesh.biz.SIGMeshBizRequestGenerator;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: a.a.a.a.b.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public class C0224b {

    /* renamed from: a, reason: collision with root package name */
    public static C0224b f1299a;

    /* renamed from: b, reason: collision with root package name */
    public static AtomicBoolean f1300b = new AtomicBoolean(false);

    /* renamed from: c, reason: collision with root package name */
    public List<String> f1301c = new LinkedList();

    /* renamed from: d, reason: collision with root package name */
    public List<String> f1302d = new LinkedList();

    /* renamed from: e, reason: collision with root package name */
    public List<String> f1303e = new LinkedList();

    /* renamed from: f, reason: collision with root package name */
    public int f1304f = 4;

    /* renamed from: g, reason: collision with root package name */
    public boolean f1305g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f1306h;

    /* renamed from: i, reason: collision with root package name */
    public int f1307i;

    /* renamed from: j, reason: collision with root package name */
    public Runnable f1308j;

    /* renamed from: k, reason: collision with root package name */
    public Handler f1309k;

    /* renamed from: l, reason: collision with root package name */
    public List<BleMeshManager> f1310l;

    public C0224b() {
        this.f1305g = a.a.a.a.b.d.a.f1335b || a.a.a.a.b.d.a.f1334a;
        this.f1306h = false;
        this.f1310l = new LinkedList();
        this.f1309k = new Handler(Looper.getMainLooper());
    }

    public static C0224b b() {
        if (f1299a == null) {
            synchronized (a.a.a.a.a.g.class) {
                try {
                    if (f1299a == null) {
                        f1299a = new C0224b();
                    }
                } finally {
                }
            }
        }
        return f1299a;
    }

    public void a(List<String> list, int i2) {
        this.f1302d = list;
        this.f1307i = i2;
        a();
    }

    public void a(List<String> list) {
        this.f1301c = list;
    }

    public synchronized void a(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return;
        }
        String address = bluetoothDevice.getAddress();
        a.a.a.a.b.m.a.b("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "onPrepareConnectRemoteDevice: " + address);
        if (this.f1302d.contains(address)) {
            a.a.a.a.b.m.a.d("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "Already in sliding window, do nothing");
            return;
        }
        if (this.f1302d.size() < this.f1304f) {
            this.f1302d.add(address);
            if (this.f1302d.size() < this.f1304f) {
                for (int i2 = 0; i2 < this.f1301c.size() && this.f1302d.size() < this.f1304f; i2++) {
                    if (!this.f1302d.contains(this.f1301c.get(i2))) {
                        this.f1302d.add(this.f1301c.get(i2));
                    }
                }
            }
            a();
        } else {
            this.f1303e.add(address);
        }
    }

    public void b(BleMeshManager bleMeshManager) {
        a.a.a.a.b.m.a.a("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "removeWriteableChannel: " + bleMeshManager);
        this.f1310l.remove(bleMeshManager);
    }

    public synchronized void a(BluetoothDevice bluetoothDevice, boolean z2) {
        boolean z3;
        if (bluetoothDevice == null) {
            return;
        }
        try {
            a.a.a.a.b.m.a.b("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "onConnectFinished: " + bluetoothDevice.getAddress());
            List<String> list = this.f1301c;
            if (list != null) {
                ListIterator<String> listIterator = list.listIterator();
                while (true) {
                    if (listIterator.hasNext()) {
                        if (listIterator.next().equalsIgnoreCase(bluetoothDevice.getAddress())) {
                            listIterator.remove();
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            List<String> list2 = this.f1302d;
            if (list2 != null) {
                ListIterator<String> listIterator2 = list2.listIterator();
                while (true) {
                    if (listIterator2.hasNext()) {
                        if (listIterator2.next().equalsIgnoreCase(bluetoothDevice.getAddress())) {
                            listIterator2.remove();
                            break;
                        }
                    } else {
                        break;
                    }
                }
                List<String> list3 = this.f1303e;
                if (list3 == null || list3.size() <= 0) {
                    z3 = false;
                } else {
                    z3 = false;
                    while (this.f1303e.size() > 0 && this.f1302d.size() < this.f1304f) {
                        this.f1302d.add(this.f1303e.get(0));
                        this.f1303e.remove(0);
                        z3 = true;
                    }
                }
                if (z3) {
                    if (this.f1302d.size() < this.f1304f) {
                        for (int i2 = 0; i2 < this.f1301c.size() && this.f1302d.size() < this.f1304f; i2++) {
                            if (!this.f1302d.contains(this.f1301c.get(i2))) {
                                this.f1302d.add(this.f1301c.get(i2));
                            }
                        }
                    }
                    a();
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void a(BleMeshManager bleMeshManager) {
        a.a.a.a.b.m.a.a("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "addWriteableChannel: " + bleMeshManager);
        this.f1310l.add(bleMeshManager);
    }

    public final void a() {
        List<String> list;
        if (this.f1305g && (list = this.f1302d) != null && list.size() > 0) {
            b.u uVarD = G.a().d();
            boolean z2 = false;
            byte[] bArrA = SIGMeshBizRequestGenerator.a((byte) 11, this.f1302d, (uVarD == null || uVarD.d() == null) ? (byte) 0 : uVarD.d().c(), this.f1307i);
            Runnable runnable = this.f1308j;
            if (runnable != null) {
                this.f1309k.removeCallbacks(runnable);
            }
            if (this.f1306h) {
                boolean z3 = false;
                for (BleMeshManager bleMeshManager : this.f1310l) {
                    if (bleMeshManager != null && bleMeshManager.isConnected()) {
                        a.a.a.a.b.m.a.d("AdjustConnectableAdvertiseIntervalSlidingWindowManager", "Use gatt channel to adjust adv internal, bleMeshManager: " + bleMeshManager);
                        byte[] bArr = new byte[bArrA.length + 2];
                        bArr[0] = 1;
                        bArr[1] = -88;
                        System.arraycopy(bArrA, 0, bArr, 2, bArrA.length);
                        bleMeshManager.sendPdu(bArr);
                        z3 = true;
                    }
                }
                z2 = z3;
            }
            if (z2) {
                return;
            }
            f1300b.set(true);
            a.a.a.a.a.g.c().a(bArrA, 500, new C0211a(this));
        }
    }
}
