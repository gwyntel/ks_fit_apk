package com.umeng.analytics.pro;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface b extends IInterface {

    public static class a implements b {
        @Override // com.umeng.analytics.pro.b
        public void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.umeng.analytics.pro.b
        public void a(int i2, Bundle bundle) throws RemoteException {
        }
    }

    void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws RemoteException;

    void a(int i2, Bundle bundle) throws RemoteException;

    /* renamed from: com.umeng.analytics.pro.b$b, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0172b extends Binder implements b {

        /* renamed from: a, reason: collision with root package name */
        static final int f21393a = 1;

        /* renamed from: b, reason: collision with root package name */
        static final int f21394b = 2;

        /* renamed from: c, reason: collision with root package name */
        private static final String f21395c = "com.hihonor.cloudservice.oaid.IOAIDCallBack";

        /* renamed from: com.umeng.analytics.pro.b$b$a */
        private static class a implements b {

            /* renamed from: a, reason: collision with root package name */
            public static b f21396a;

            /* renamed from: b, reason: collision with root package name */
            private IBinder f21397b;

            a(IBinder iBinder) {
                this.f21397b = iBinder;
            }

            public String a() {
                return AbstractBinderC0172b.f21395c;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f21397b;
            }

            @Override // com.umeng.analytics.pro.b
            public void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws Throwable {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0172b.f21395c);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeString(str);
                    try {
                        if (this.f21397b.transact(1, parcelObtain, parcelObtain2, 0) || AbstractBinderC0172b.a() == null) {
                            parcelObtain2.readException();
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                        } else {
                            AbstractBinderC0172b.a().a(i2, j2, z2, f2, d2, str);
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                        }
                    } catch (Throwable th) {
                        th = th;
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // com.umeng.analytics.pro.b
            public void a(int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0172b.f21395c);
                    parcelObtain.writeInt(i2);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (!this.f21397b.transact(2, parcelObtain, parcelObtain2, 0) && AbstractBinderC0172b.a() != null) {
                        AbstractBinderC0172b.a().a(i2, bundle);
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } else {
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }
        }

        public AbstractBinderC0172b() {
            attachInterface(this, f21395c);
        }

        public static b a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f21395c);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof b)) ? new a(iBinder) : (b) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(f21395c);
                a(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i2 != 2) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(f21395c);
                return true;
            }
            parcel.enforceInterface(f21395c);
            a(parcel.readInt(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            return true;
        }

        public static boolean a(b bVar) {
            if (a.f21396a != null || bVar == null) {
                return false;
            }
            a.f21396a = bVar;
            return true;
        }

        public static b a() {
            return a.f21396a;
        }
    }
}
