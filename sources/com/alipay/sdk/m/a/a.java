package com.alipay.sdk.m.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public interface a extends IInterface {

    /* renamed from: com.alipay.sdk.m.a.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0040a extends Binder implements a {

        /* renamed from: com.alipay.sdk.m.a.a$a$a, reason: collision with other inner class name */
        public static class C0041a implements a {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f9166a;

            public C0041a(IBinder iBinder) {
                this.f9166a = iBinder;
            }

            public String a(String str, String str2, String str3) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.f9166a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f9166a;
            }
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.heytap.openid.IOpenID");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof a)) ? new C0041a(iBinder) : (a) iInterfaceQueryLocalInterface;
        }
    }

    public class b {

        /* renamed from: a, reason: collision with root package name */
        public static boolean f9167a = false;

        /* renamed from: b, reason: collision with root package name */
        public static boolean f9168b = false;
    }

    public class c {

        /* renamed from: a, reason: collision with root package name */
        public a f9169a = null;

        /* renamed from: b, reason: collision with root package name */
        public String f9170b = null;

        /* renamed from: c, reason: collision with root package name */
        public String f9171c = null;

        /* renamed from: d, reason: collision with root package name */
        public final Object f9172d = new Object();

        /* renamed from: e, reason: collision with root package name */
        public ServiceConnection f9173e = new ServiceConnectionC0042a();

        /* renamed from: com.alipay.sdk.m.a.a$c$a, reason: collision with other inner class name */
        public class ServiceConnectionC0042a implements ServiceConnection {
            public ServiceConnectionC0042a() {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                c.this.f9169a = AbstractBinderC0040a.a(iBinder);
                synchronized (c.this.f9172d) {
                    c.this.f9172d.notify();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                c.this.f9169a = null;
            }
        }

        public static class b {

            /* renamed from: a, reason: collision with root package name */
            public static final c f9175a = new c(null);
        }

        public /* synthetic */ c(ServiceConnectionC0042a serviceConnectionC0042a) {
        }

        public boolean a(Context context) throws PackageManager.NameNotFoundException {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.heytap.openid", 0);
                return Build.VERSION.SDK_INT >= 28 ? packageInfo != null && packageInfo.getLongVersionCode() >= 1 : packageInfo != null && packageInfo.versionCode >= 1;
            } catch (PackageManager.NameNotFoundException e2) {
                e2.printStackTrace();
                return false;
            }
        }

        public final String b(Context context, String str) throws NoSuchAlgorithmException {
            Signature[] signatureArr;
            if (TextUtils.isEmpty(this.f9170b)) {
                this.f9170b = context.getPackageName();
            }
            if (TextUtils.isEmpty(this.f9171c)) {
                String string = null;
                try {
                    signatureArr = context.getPackageManager().getPackageInfo(this.f9170b, 64).signatures;
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                    signatureArr = null;
                }
                if (signatureArr != null && signatureArr.length > 0) {
                    byte[] byteArray = signatureArr[0].toByteArray();
                    try {
                        MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                        if (messageDigest != null) {
                            byte[] bArrDigest = messageDigest.digest(byteArray);
                            StringBuilder sb = new StringBuilder();
                            for (byte b2 : bArrDigest) {
                                sb.append(Integer.toHexString((b2 & 255) | 256).substring(1, 3));
                            }
                            string = sb.toString();
                        }
                    } catch (NoSuchAlgorithmException e3) {
                        e3.printStackTrace();
                    }
                }
                this.f9171c = string;
            }
            String strA = ((AbstractBinderC0040a.C0041a) this.f9169a).a(this.f9170b, this.f9171c, str);
            return TextUtils.isEmpty(strA) ? "" : strA;
        }

        public synchronized String a(Context context, String str) {
            try {
            } catch (Throwable th) {
                throw th;
            }
            if (Looper.myLooper() != Looper.getMainLooper()) {
                if (this.f9169a == null) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.heytap.openid", "com.heytap.openid.IdentifyService"));
                    intent.setAction("action.com.heytap.openid.OPEN_ID_SERVICE");
                    if (context.bindService(intent, this.f9173e, 1)) {
                        synchronized (this.f9172d) {
                            try {
                                this.f9172d.wait(3000L);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    if (this.f9169a == null) {
                        return "";
                    }
                    try {
                        return b(context, str);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                        return "";
                    }
                }
                try {
                    return b(context, str);
                } catch (RemoteException e4) {
                    e4.printStackTrace();
                    return "";
                }
                throw th;
            }
            throw new IllegalStateException("Cannot run on MainThread");
        }
    }
}
