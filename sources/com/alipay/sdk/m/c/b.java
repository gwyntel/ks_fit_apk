package com.alipay.sdk.m.c;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class b implements com.alipay.sdk.m.b.b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f9197a = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService";

    /* renamed from: b, reason: collision with root package name */
    public static final int f9198b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f9199c = 2;

    /* renamed from: com.alipay.sdk.m.c.b$b, reason: collision with other inner class name */
    public static final class ServiceConnectionC0043b implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        public boolean f9200a;

        /* renamed from: b, reason: collision with root package name */
        public final LinkedBlockingQueue<IBinder> f9201b;

        public ServiceConnectionC0043b() {
            this.f9200a = false;
            this.f9201b = new LinkedBlockingQueue<>();
        }

        public IBinder a() throws InterruptedException {
            if (this.f9200a) {
                throw new IllegalStateException();
            }
            this.f9200a = true;
            return this.f9201b.poll(5L, TimeUnit.SECONDS);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f9201b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public static final class c implements IInterface {

        /* renamed from: a, reason: collision with root package name */
        public IBinder f9202a;

        public c(IBinder iBinder) {
            this.f9202a = iBinder;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f9202a;
        }

        public String d() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(b.f9197a);
                this.f9202a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        public boolean e() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(b.f9197a);
                this.f9202a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readInt() != 0;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    @Override // com.alipay.sdk.m.b.b
    public String a(Context context) {
        ServiceConnectionC0043b serviceConnectionC0043b = new ServiceConnectionC0043b();
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        if (context.bindService(intent, serviceConnectionC0043b, 1)) {
            try {
                return new c(serviceConnectionC0043b.a()).d();
            } catch (Exception unused) {
            } finally {
                context.unbindService(serviceConnectionC0043b);
            }
        }
        return null;
    }
}
