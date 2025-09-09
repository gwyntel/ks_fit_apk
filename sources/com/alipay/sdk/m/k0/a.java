package com.alipay.sdk.m.k0;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface a extends IInterface {
    String a() throws RemoteException;

    String a(String str) throws RemoteException;

    String b(String str) throws RemoteException;

    /* renamed from: com.alipay.sdk.m.k0.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0047a extends Binder implements a {

        /* renamed from: a, reason: collision with root package name */
        public static final String f9403a = "com.samsung.android.deviceidservice.IDeviceIdService";

        /* renamed from: b, reason: collision with root package name */
        public static final int f9404b = 1;

        /* renamed from: c, reason: collision with root package name */
        public static final int f9405c = 2;

        /* renamed from: d, reason: collision with root package name */
        public static final int f9406d = 3;

        public AbstractBinderC0047a() {
            attachInterface(this, f9403a);
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f9403a);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof a)) ? new C0048a(iBinder) : (a) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(f9403a);
                String strA = a();
                parcel2.writeNoException();
                parcel2.writeString(strA);
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(f9403a);
                String strB = b(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strB);
                return true;
            }
            if (i2 != 3) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(f9403a);
                return true;
            }
            parcel.enforceInterface(f9403a);
            String strA2 = a(parcel.readString());
            parcel2.writeNoException();
            parcel2.writeString(strA2);
            return true;
        }

        /* renamed from: com.alipay.sdk.m.k0.a$a$a, reason: collision with other inner class name */
        public static class C0048a implements a {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f9407a;

            public C0048a(IBinder iBinder) {
                this.f9407a = iBinder;
            }

            @Override // com.alipay.sdk.m.k0.a
            public String a() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0047a.f9403a);
                    this.f9407a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f9407a;
            }

            @Override // com.alipay.sdk.m.k0.a
            public String b(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0047a.f9403a);
                    parcelObtain.writeString(str);
                    this.f9407a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String d() {
                return AbstractBinderC0047a.f9403a;
            }

            @Override // com.alipay.sdk.m.k0.a
            public String a(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0047a.f9403a);
                    parcelObtain.writeString(str);
                    this.f9407a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
