package b;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanCallback;
import aisscanner.ScanFilter;
import aisscanner.ScanResult;
import aisscanner.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import b.K;
import b.u;
import com.alibaba.ailabs.iot.mesh.bean.ExtendedBluetoothDevice;
import com.alibaba.ailabs.iot.mesh.ble.BleMeshManager;
import com.alibaba.ailabs.iot.mesh.managers.MeshDeviceInfoManager;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import meshprovisioner.ProxyCommunicationQuality;

/* loaded from: classes2.dex */
public class y implements K.c {

    /* renamed from: a, reason: collision with root package name */
    public Context f7573a;

    /* renamed from: d, reason: collision with root package name */
    public u f7576d;

    /* renamed from: e, reason: collision with root package name */
    public q f7577e;

    /* renamed from: c, reason: collision with root package name */
    public volatile boolean f7575c = false;

    /* renamed from: f, reason: collision with root package name */
    public List<String> f7578f = new ArrayList();

    /* renamed from: i, reason: collision with root package name */
    public List<K.c> f7581i = new ArrayList();

    /* renamed from: j, reason: collision with root package name */
    public Map<String, ScanResult> f7582j = new LinkedHashMap();

    /* renamed from: k, reason: collision with root package name */
    public long f7583k = -1;

    /* renamed from: l, reason: collision with root package name */
    public volatile boolean f7584l = false;

    /* renamed from: m, reason: collision with root package name */
    public final Runnable f7585m = new v(this);

    /* renamed from: n, reason: collision with root package name */
    public final ScanCallback f7586n = new w(this);

    /* renamed from: b, reason: collision with root package name */
    public Handler f7574b = new Handler(Looper.getMainLooper());

    /* renamed from: g, reason: collision with root package name */
    public Queue<K> f7579g = new LinkedList();

    /* renamed from: h, reason: collision with root package name */
    public Queue<a> f7580h = new ConcurrentLinkedQueue();

    /* JADX INFO: Access modifiers changed from: private */
    static class a {

        /* renamed from: a, reason: collision with root package name */
        public u.a f7587a;

        /* renamed from: b, reason: collision with root package name */
        public boolean f7588b = false;

        public a(u.a aVar) {
            this.f7587a = aVar;
        }

        public void b() {
            this.f7588b = true;
        }

        public boolean a() {
            return this.f7588b;
        }
    }

    public y(Context context, u uVar, q qVar) {
        this.f7573a = context;
        this.f7576d = uVar;
        this.f7577e = qVar;
    }

    @Override // b.K.c
    public void onConnectionStateChanged(K k2, int i2, int i3) {
        if (i3 != 0) {
            if (i3 == 2) {
                if (!this.f7579g.contains(k2)) {
                    this.f7579g.add(k2);
                }
                Iterator<a> it = this.f7580h.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    a next = it.next();
                    if (next.f7587a != null && next.f7587a.equals(k2.j())) {
                        next.b();
                        break;
                    }
                }
                if (this.f7580h.size() > 0 && !a()) {
                    a.a.a.a.b.m.a.c("SIGMeshNetworkTransportManager", "All waiting connection task finished");
                    e();
                    this.f7580h.clear();
                }
                for (K.c cVar : this.f7581i) {
                    if (cVar != null) {
                        cVar.onConnectionStateChanged(k2, i2, i3);
                    }
                }
                return;
            }
            if (i3 != 3) {
                return;
            }
        }
        this.f7579g.remove(k2);
        if (k2.l()) {
            a(k2.j(), false);
        }
    }

    @Override // b.K.c
    public void onMeshChannelReady(K k2) {
        for (K.c cVar : this.f7581i) {
            if (cVar != null) {
                cVar.onMeshChannelReady(k2);
            }
        }
    }

    public boolean b(byte[] bArr) {
        return bArr != null && bArr[0] == 0;
    }

    public final void c() {
        this.f7574b.removeCallbacks(this.f7585m);
        this.f7574b.postDelayed(this.f7585m, 20000L);
    }

    public void d() {
        if (!Utils.isBleEnabled()) {
            a.a.a.a.b.m.a.b("SIGMeshNetworkTransportManager", "Invalid scan: Bluetooth adapter is disabled");
            return;
        }
        if (this.f7575c) {
            return;
        }
        a.a.a.a.b.m.a.a("SIGMeshNetworkTransportManager", "startScan...");
        this.f7575c = true;
        this.f7583k = System.currentTimeMillis();
        ScanSettings scanSettingsBuild = new ScanSettings.Builder().setScanMode(1).setReportDelay(0L).setUseHardwareFilteringIfSupported(true).build();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ScanFilter.Builder().setServiceUuid(new ParcelUuid(BleMeshManager.MESH_PROXY_UUID)).build());
        if (Utils.isBleEnabled()) {
            BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            try {
                if (Build.VERSION.SDK_INT < 31 || Utils.checkBlePermission(this.f7573a)) {
                    scanner.startScan(arrayList, scanSettingsBuild, this.f7586n);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            this.f7574b.postDelayed(this.f7585m, 20000L);
        }
    }

    public final void e() {
        a.a.a.a.b.m.a.a("SIGMeshNetworkTransportManager", "stopScan...");
        this.f7574b.removeCallbacks(this.f7585m);
        if (Utils.isBleEnabled()) {
            BluetoothLeScannerCompat.getScanner().stopScan(this.f7586n);
        }
        this.f7575c = false;
        this.f7583k = -1L;
        this.f7582j.clear();
    }

    public void f() {
        if (this.f7584l) {
            a.a.a.a.b.m.a.d("SIGMeshNetworkTransportManager", "tryConnectAllSubnets: connection is not currently allowed");
            return;
        }
        u.a aVarD = a.a.a.a.b.G.a().d().d();
        if (aVarD != null && aVarD.e() == null) {
            aVarD.a(new K(this.f7573a, aVarD, this.f7577e));
        }
        d();
    }

    public void g() {
        a.a.a.a.b.m.a.a("SIGMeshNetworkTransportManager", "Try stop connect activity");
        if (this.f7575c) {
            e();
        }
    }

    public void h() {
        a.a.a.a.b.m.a.c("SIGMeshNetworkTransportManager", "unlock connection");
        this.f7584l = false;
    }

    public void b(K.c cVar) {
        this.f7581i.remove(cVar);
    }

    public void b() {
        a.a.a.a.b.m.a.c("SIGMeshNetworkTransportManager", "lock connection");
        this.f7584l = true;
    }

    public void a(u.a aVar, boolean z2) {
        if (this.f7584l) {
            a.a.a.a.b.m.a.d("SIGMeshNetworkTransportManager", "tryConnectSpecifiedSubnets: connection is not currently allowed");
            return;
        }
        a.a.a.a.b.m.a.a("SIGMeshNetworkTransportManager", "Try connect target " + aVar);
        if (aVar.f() && !aVar.h()) {
            a.a.a.a.b.m.a.a("SIGMeshNetworkTransportManager", String.format("%s is available, do nothing", aVar));
            return;
        }
        if (this.f7580h.size() > 2) {
            this.f7580h.remove();
        }
        if (aVar.e() == null && aVar.i()) {
            aVar.a(new K(this.f7573a, aVar, this.f7577e));
        }
        a(aVar);
        if (this.f7575c) {
            a.a.a.a.b.m.a.d("SIGMeshNetworkTransportManager", "Currently scanning, do nothing");
            if (z2) {
                c();
                return;
            }
            return;
        }
        d();
    }

    public void b(u.a aVar) {
        for (a aVar2 : this.f7580h) {
            if (aVar2 != null && aVar2.f7587a.equals(aVar)) {
                this.f7580h.remove(aVar2);
                return;
            }
        }
    }

    public boolean a(byte[] bArr) {
        return bArr != null && bArr[0] == 1;
    }

    public void a(K.c cVar) {
        if (cVar != null) {
            this.f7581i.add(cVar);
        }
    }

    public final void a(u.a aVar) {
        for (a aVar2 : this.f7580h) {
            if (aVar2 != null && aVar2.f7587a.equals(aVar)) {
                return;
            }
        }
        this.f7580h.add(new a(aVar));
    }

    public final boolean a(String str) {
        return this.f7583k != -1 && System.currentTimeMillis() - this.f7583k > 3000 && MeshDeviceInfoManager.getInstance().isLowPowerDeviceViaMac(str);
    }

    public final boolean a() {
        for (a aVar : this.f7580h) {
            if (!aVar.a() || aVar.f7587a.h()) {
                return true;
            }
        }
        return false;
    }

    public final void a(ScanResult scanResult, K k2) {
        if (scanResult == null) {
            return;
        }
        this.f7582j.put(scanResult.getDevice().getAddress(), scanResult);
        a.a.a.a.b.m.a.a("multi_proxy_selector", "Cache device: " + scanResult.getDevice().getAddress() + ", Rssi: " + scanResult.getRssi());
        if (this.f7583k == -1 || System.currentTimeMillis() - this.f7583k <= 3000 || k2 == null) {
            return;
        }
        a.a.a.a.b.m.a.a("multi_proxy_selector", "Order proxy devices via quality level");
        this.f7583k = System.currentTimeMillis();
        ArrayList<ScanResult> arrayList = new ArrayList(this.f7582j.values());
        Collections.sort(arrayList, new x(this));
        HashMap map = new HashMap();
        for (ScanResult scanResult2 : arrayList) {
            ProxyCommunicationQuality qualityViaRssi = ProxyCommunicationQuality.getQualityViaRssi(scanResult2.getRssi());
            List<ExtendedBluetoothDevice> linkedList = map.get(qualityViaRssi);
            if (linkedList == null) {
                linkedList = new LinkedList<>();
                map.put(qualityViaRssi, linkedList);
            }
            linkedList.add(new ExtendedBluetoothDevice(scanResult2));
        }
        k2.a(map);
    }
}
