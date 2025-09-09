package com.xiaomi.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
class ay implements ar {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f23464a;

    /* renamed from: a, reason: collision with other field name */
    private Context f198a;

    /* renamed from: a, reason: collision with other field name */
    private ServiceConnection f199a;

    /* renamed from: a, reason: collision with other field name */
    private volatile int f197a = 0;

    /* renamed from: a, reason: collision with other field name */
    private volatile a f200a = null;

    /* renamed from: a, reason: collision with other field name */
    private final Object f201a = new Object();

    /* JADX INFO: Access modifiers changed from: private */
    class a {

        /* renamed from: a, reason: collision with other field name */
        String f202a;

        /* renamed from: b, reason: collision with root package name */
        String f23466b;

        /* renamed from: c, reason: collision with root package name */
        String f23467c;

        /* renamed from: d, reason: collision with root package name */
        String f23468d;

        private a() {
            this.f202a = null;
            this.f23466b = null;
            this.f23467c = null;
            this.f23468d = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class b implements ServiceConnection {
        private b() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (ay.this.f200a != null) {
                return;
            }
            new Thread(new ba(this, iBinder)).start();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class c {
        static String a(IBinder iBinder, String str, String str2, String str3) {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                parcelObtain.writeString(str);
                parcelObtain.writeString(str2);
                parcelObtain.writeString(str3);
                iBinder.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    public ay(Context context) {
        this.f198a = context;
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b, reason: collision with other method in class */
    public void m186b() {
        ServiceConnection serviceConnection = this.f199a;
        if (serviceConnection != null) {
            try {
                this.f198a.unbindService(serviceConnection);
            } catch (Exception unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String b() throws NoSuchAlgorithmException {
        try {
            Signature[] signatureArr = this.f198a.getPackageManager().getPackageInfo(this.f198a.getPackageName(), 64).signatures;
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            StringBuilder sb = new StringBuilder();
            for (byte b2 : messageDigest.digest(signatureArr[0].toByteArray())) {
                sb.append(Integer.toHexString((b2 & 255) | 256).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public boolean mo180a() {
        return f23464a;
    }

    @Override // com.xiaomi.push.ar
    /* renamed from: a */
    public String mo179a() {
        a("getOAID");
        if (this.f200a == null) {
            return null;
        }
        return this.f200a.f23466b;
    }

    private void a() {
        boolean zBindService;
        this.f199a = new b();
        Intent intent = new Intent();
        intent.setClassName("com.heytap.openid", "com.heytap.openid.IdentifyService");
        intent.setAction("action.com.heytap.openid.OPEN_ID_SERVICE");
        try {
            zBindService = this.f198a.bindService(intent, this.f199a, 1);
        } catch (Exception unused) {
            zBindService = false;
        }
        this.f197a = zBindService ? 1 : 2;
    }

    private void a(String str) {
        if (this.f197a != 1 || Looper.myLooper() == Looper.getMainLooper()) {
            return;
        }
        synchronized (this.f201a) {
            try {
                com.xiaomi.channel.commonutils.logger.b.m91a("oppo's " + str + " wait...");
                this.f201a.wait(3000L);
            } catch (Exception unused) {
            }
        }
    }

    public static boolean a(Context context) throws PackageManager.NameNotFoundException {
        long longVersionCode;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.heytap.openid", 128);
            if (packageInfo != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    longVersionCode = packageInfo.getLongVersionCode();
                } else {
                    longVersionCode = packageInfo.versionCode;
                }
                boolean z2 = (packageInfo.applicationInfo.flags & 1) != 0;
                f23464a = longVersionCode >= 1;
                if (z2) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
