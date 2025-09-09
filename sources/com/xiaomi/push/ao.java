package com.xiaomi.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;

/* loaded from: classes4.dex */
class ao implements ar {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f23442a;

    /* renamed from: a, reason: collision with other field name */
    private Context f176a;

    /* renamed from: a, reason: collision with other field name */
    private ServiceConnection f177a;

    /* renamed from: a, reason: collision with other field name */
    private volatile int f175a = 0;

    /* renamed from: a, reason: collision with other field name */
    private volatile String f179a = null;

    /* renamed from: b, reason: collision with other field name */
    private volatile boolean f180b = false;

    /* renamed from: b, reason: collision with root package name */
    private volatile String f23443b = null;

    /* renamed from: a, reason: collision with other field name */
    private final Object f178a = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    class a implements ServiceConnection {
        private a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            new Thread(new aq(this, iBinder)).start();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public ao(Context context) {
        this.f176a = context;
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        ServiceConnection serviceConnection = this.f177a;
        if (serviceConnection != null) {
            try {
                this.f176a.unbindService(serviceConnection);
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a, reason: collision with other method in class */
    public boolean mo180a() {
        return f23442a;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a, reason: collision with other method in class */
    public String mo179a() {
        a("getOAID");
        return this.f179a;
    }

    private void a() {
        boolean zBindService;
        this.f177a = new a();
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        try {
            zBindService = this.f176a.bindService(intent, this.f177a, 1);
        } catch (Exception unused) {
            zBindService = false;
        }
        this.f175a = zBindService ? 1 : 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class b {
        static String a(IBinder iBinder) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(com.alipay.sdk.m.c.b.f9197a);
                iBinder.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        static boolean m181a(IBinder iBinder) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(com.alipay.sdk.m.c.b.f9197a);
                iBinder.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readInt() != 0;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    private void a(String str) {
        if (this.f175a != 1 || Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        synchronized (this.f178a) {
            try {
                com.xiaomi.channel.commonutils.logger.b.m91a("huawei's " + str + " wait...");
                this.f178a.wait(3000L);
            } catch (Exception unused) {
            }
        }
    }

    public static boolean a(Context context) throws PackageManager.NameNotFoundException {
        boolean z2;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.huawei.hwid", 128);
            z2 = (packageInfo.applicationInfo.flags & 1) != 0;
            f23442a = packageInfo.versionCode >= 20602000;
        } catch (Exception unused) {
        }
        return z2;
    }
}
