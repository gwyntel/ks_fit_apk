package a.a.a.a.a;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.RequiresApi;
import com.alibaba.ailabs.iot.bleadvertise.callback.BleAdvertiseCallback;
import com.alibaba.ailabs.iot.mesh.utils.Utils;
import com.alibaba.ailabs.tg.utils.ConvertUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class g {

    /* renamed from: a, reason: collision with root package name */
    public static volatile g f1166a;

    /* renamed from: b, reason: collision with root package name */
    public BluetoothLeAdvertiser f1167b;

    /* renamed from: c, reason: collision with root package name */
    public BluetoothManager f1168c;

    /* renamed from: d, reason: collision with root package name */
    public BluetoothAdapter f1169d;

    /* renamed from: j, reason: collision with root package name */
    public AdvertiseCallback f1175j;

    /* renamed from: m, reason: collision with root package name */
    public Context f1178m;

    /* renamed from: g, reason: collision with root package name */
    public int f1172g = 0;

    /* renamed from: l, reason: collision with root package name */
    public volatile boolean f1177l = false;

    /* renamed from: e, reason: collision with root package name */
    public Handler f1170e = new Handler(Looper.getMainLooper());

    /* renamed from: f, reason: collision with root package name */
    public Runnable f1171f = new a.a.a.a.a.a(this);

    /* renamed from: h, reason: collision with root package name */
    public a f1173h = new a(null, null);

    /* renamed from: i, reason: collision with root package name */
    public b f1174i = new b(null, null);

    /* renamed from: k, reason: collision with root package name */
    public LinkedBlockingDeque<a.a.a.a.a.a.a.b.a> f1176k = new LinkedBlockingDeque<>();

    class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public a.a.a.a.a.a.a.b.a f1179a;

        /* renamed from: c, reason: collision with root package name */
        public List<byte[]> f1181c;

        /* renamed from: e, reason: collision with root package name */
        public BleAdvertiseCallback<Boolean> f1183e;

        /* renamed from: b, reason: collision with root package name */
        public boolean f1180b = false;

        /* renamed from: d, reason: collision with root package name */
        public int f1182d = -1;

        /* renamed from: f, reason: collision with root package name */
        public int f1184f = 0;

        public a(List<byte[]> list, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
            this.f1181c = list;
            this.f1183e = bleAdvertiseCallback;
        }

        public static /* synthetic */ int c(a aVar) {
            int i2 = aVar.f1184f;
            aVar.f1184f = i2 + 1;
            return i2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.f1181c != null) {
                int iA = a();
                if (!this.f1180b) {
                    a.a.a.a.b.m.a.b("AdvertiseManager", "AlternateAdvertiseTask not allowed " + ((int) this.f1179a.c()));
                    return;
                }
                a.a.a.a.b.m.a.c("AdvertiseManager", "running AlternateAdvertiseTask " + ((int) this.f1179a.c()) + ", index " + iA);
                byte[] bArr = this.f1181c.get(iA);
                g.this.f1170e.removeCallbacks(g.this.f1174i);
                g.this.f1174i.a(bArr);
                g.this.f1174i.a(new f(this));
                g.this.f1170e.post(g.this.f1174i);
            }
        }

        public final int a() {
            int i2 = this.f1182d + 1;
            this.f1182d = i2;
            int size = i2 % this.f1181c.size();
            this.f1182d = size;
            return size;
        }

        @RequiresApi(api = 21)
        public final void b() {
            StringBuilder sb = new StringBuilder();
            sb.append("reset msg ");
            a.a.a.a.a.a.a.b.a aVar = this.f1179a;
            sb.append(aVar == null ? TmpConstant.GROUP_ROLE_UNKNOWN : Byte.valueOf(aVar.c()));
            a.a.a.a.b.m.a.c("AdvertiseManager", sb.toString());
            this.f1184f = 0;
            this.f1182d = -1;
            g.this.f1170e.removeCallbacks(this);
            g.this.f1170e.removeCallbacks(g.this.f1174i);
            this.f1180b = false;
            g.this.f1174i.a();
        }

        public void a(a.a.a.a.a.a.a.b.a aVar) {
            this.f1179a = aVar;
            this.f1181c = aVar.a();
        }

        public void a(BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
            this.f1183e = bleAdvertiseCallback;
        }

        public void a(boolean z2) {
            this.f1180b = z2;
        }
    }

    class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public byte[] f1186a;

        /* renamed from: b, reason: collision with root package name */
        public BleAdvertiseCallback<Boolean> f1187b;

        public b(byte[] bArr, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
            this.f1186a = bArr;
            this.f1187b = bleAdvertiseCallback;
        }

        @Override // java.lang.Runnable
        @RequiresApi(api = 21)
        public void run() {
            g.this.a(this.f1186a, this.f1187b);
        }

        public void a(byte[] bArr) {
            this.f1186a = bArr;
        }

        public void a(BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
            this.f1187b = bleAdvertiseCallback;
        }

        @RequiresApi(api = 21)
        public final void a() {
            a.a.a.a.b.m.a.c("AdvertiseManager", "reset");
            g.this.d();
        }
    }

    public static g c() {
        if (f1166a == null) {
            synchronized (g.class) {
                try {
                    if (f1166a == null) {
                        f1166a = new g();
                    }
                } finally {
                }
            }
        }
        return f1166a;
    }

    public void b(Context context) {
        this.f1178m = context;
    }

    @RequiresApi(api = 21)
    public void d() {
        a.a.a.a.b.m.a.c("AdvertiseManager", "stopAdvertise");
        if (this.f1167b == null || this.f1175j == null || !Utils.isBleEnabled()) {
            return;
        }
        try {
            this.f1167b.stopAdvertising(this.f1175j);
        } catch (Exception unused) {
        }
    }

    public void b(byte[] bArr, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
        this.f1170e.post(new a.a.a.a.a.b(this, bArr, bleAdvertiseCallback));
    }

    @RequiresApi(api = 21)
    public void a(Context context) {
        if (!context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "ble not supported");
            return;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        this.f1168c = bluetoothManager;
        if (bluetoothManager == null) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "failed to get bluetoothManager");
            return;
        }
        BluetoothAdapter adapter = bluetoothManager.getAdapter();
        this.f1169d = adapter;
        if (adapter == null) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "failed to get bluetoothAdapter");
            return;
        }
        try {
            BluetoothLeAdvertiser bluetoothLeAdvertiser = adapter.getBluetoothLeAdvertiser();
            this.f1167b = bluetoothLeAdvertiser;
            if (bluetoothLeAdvertiser == null) {
                a.a.a.a.b.m.a.b("AdvertiseManager", "the device not support peripheral");
            }
        } catch (NullPointerException unused) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "failed Null pointer exists in room");
        }
    }

    public synchronized byte b() {
        int i2 = this.f1172g;
        if (i2 >= 255) {
            this.f1172g = 0;
            return (byte) 0;
        }
        this.f1172g = i2 + 1;
        return (byte) i2;
    }

    @RequiresApi(api = 21)
    public AdvertiseSettings a(boolean z2, int i2) {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setAdvertiseMode(2);
        builder.setConnectable(z2);
        builder.setTimeout(i2);
        builder.setTxPowerLevel(3);
        AdvertiseSettings advertiseSettingsBuild = builder.build();
        if (advertiseSettingsBuild == null) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "mAdvertiseSettings == null");
        }
        return advertiseSettingsBuild;
    }

    @RequiresApi(api = 21)
    public AdvertiseData a(byte[] bArr) {
        if (bArr == null) {
            a.a.a.a.b.m.a.c("AdvertiseManager", "createAdvertiseData: failed. payload is empty");
            return null;
        }
        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addManufacturerData(43009, bArr);
        AdvertiseData advertiseDataBuild = builder.build();
        if (advertiseDataBuild == null) {
            a.a.a.a.b.m.a.b("AdvertiseManager", "mAdvertiseSettings == null");
        }
        return advertiseDataBuild;
    }

    @RequiresApi(api = 21)
    public final void a(byte[] bArr, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
        BluetoothAdapter bluetoothAdapter;
        a.a.a.a.b.m.a.c("AdvertiseManager", " call startAdvertise ----" + ConvertUtils.bytes2HexString(bArr));
        if (this.f1167b != null && (bluetoothAdapter = this.f1169d) != null && bluetoothAdapter.isEnabled()) {
            AdvertiseData advertiseDataA = a(bArr);
            if (advertiseDataA == null) {
                bleAdvertiseCallback.onFailure(-1, "failed to create advertise data");
                return;
            }
            d();
            this.f1170e.removeCallbacks(this.f1171f);
            this.f1175j = a(bleAdvertiseCallback);
            a.a.a.a.b.m.a.c("AdvertiseManager", "try to advertise----");
            if (Build.VERSION.SDK_INT >= 31) {
                Context context = this.f1178m;
                if (context != null && Utils.checkBlePermission(context)) {
                    this.f1167b.startAdvertising(a(true, 60000), advertiseDataA, this.f1175j);
                }
            } else {
                this.f1167b.startAdvertising(a(true, 60000), advertiseDataA, this.f1175j);
            }
            this.f1170e.postDelayed(this.f1171f, 60000L);
            return;
        }
        a.a.a.a.b.m.a.b("AdvertiseManager", "failed start advertise");
        bleAdvertiseCallback.onFailure(-1, "failed to start advertise");
    }

    @RequiresApi(api = 21)
    public void a(byte[] bArr, int i2, BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
        BluetoothAdapter bluetoothAdapter;
        a.a.a.a.b.m.a.c("AdvertiseManager", " call startAdvertise ----" + ConvertUtils.bytes2HexString(bArr));
        if (this.f1167b != null && (bluetoothAdapter = this.f1169d) != null && bluetoothAdapter.isEnabled()) {
            AdvertiseData advertiseDataA = a(bArr);
            if (advertiseDataA == null) {
                bleAdvertiseCallback.onFailure(-1, "failed to create advertise data");
                return;
            }
            d();
            this.f1170e.removeCallbacks(this.f1171f);
            this.f1175j = a(bleAdvertiseCallback);
            a.a.a.a.b.m.a.c("AdvertiseManager", "try to advertise----");
            this.f1167b.startAdvertising(a(true, i2), advertiseDataA, this.f1175j);
            return;
        }
        a.a.a.a.b.m.a.b("AdvertiseManager", "failed start advertise");
        bleAdvertiseCallback.onFailure(-1, "failed to start advertise");
    }

    public synchronized void a(a.a.a.a.a.a.a.b.a aVar) {
        try {
            this.f1176k.offer(aVar);
            if (!this.f1177l) {
                a();
            } else {
                a.a.a.a.b.m.a.b("AdvertiseManager", "watting for advertising");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    @RequiresApi(api = 21)
    public final void a() {
        a.a.a.a.b.m.a.c("AdvertiseManager", "checkAndSendControlCmd");
        a.a.a.a.a.a.a.b.a aVarPoll = this.f1176k.poll();
        if (aVarPoll != null) {
            this.f1173h.b();
            this.f1173h.a(aVarPoll);
            this.f1173h.a(new c(this, aVarPoll));
            this.f1173h.a(true);
            this.f1177l = true;
            this.f1170e.post(this.f1173h);
            this.f1170e.postDelayed(new d(this), 4000L);
            return;
        }
        a.a.a.a.b.m.a.c("AdvertiseManager", "no task in queue. return");
        this.f1177l = false;
    }

    @RequiresApi(api = 21)
    public final AdvertiseCallback a(BleAdvertiseCallback<Boolean> bleAdvertiseCallback) {
        return new e(this, bleAdvertiseCallback);
    }
}
