package com.umeng.analytics.pro;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface a extends IInterface {

    /* renamed from: com.umeng.analytics.pro.a$a, reason: collision with other inner class name */
    public static class C0170a implements a {
        @Override // com.umeng.analytics.pro.a
        public String a(String str) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.umeng.analytics.pro.a
        public String b() throws RemoteException {
            return null;
        }

        @Override // com.umeng.analytics.pro.a
        public String c(String str) throws RemoteException {
            return null;
        }

        @Override // com.umeng.analytics.pro.a
        public String d(String str) throws RemoteException {
            return null;
        }

        @Override // com.umeng.analytics.pro.a
        public String e(String str) throws RemoteException {
            return null;
        }

        @Override // com.umeng.analytics.pro.a
        public boolean a() throws RemoteException {
            return false;
        }

        @Override // com.umeng.analytics.pro.a
        public String b(String str) throws RemoteException {
            return null;
        }
    }

    String a(String str) throws RemoteException;

    boolean a() throws RemoteException;

    String b() throws RemoteException;

    String b(String str) throws RemoteException;

    String c(String str) throws RemoteException;

    String d(String str) throws RemoteException;

    String e(String str) throws RemoteException;

    public static abstract class b extends Binder implements a {

        /* renamed from: a, reason: collision with root package name */
        static final int f21266a = 1;

        /* renamed from: b, reason: collision with root package name */
        static final int f21267b = 2;

        /* renamed from: c, reason: collision with root package name */
        static final int f21268c = 3;

        /* renamed from: d, reason: collision with root package name */
        static final int f21269d = 4;

        /* renamed from: e, reason: collision with root package name */
        static final int f21270e = 5;

        /* renamed from: f, reason: collision with root package name */
        static final int f21271f = 6;

        /* renamed from: g, reason: collision with root package name */
        static final int f21272g = 7;

        /* renamed from: h, reason: collision with root package name */
        private static final String f21273h = "com.coolpad.deviceidsupport.IDeviceIdManager";

        /* renamed from: com.umeng.analytics.pro.a$b$a, reason: collision with other inner class name */
        private static class C0171a implements a {

            /* renamed from: a, reason: collision with root package name */
            public static a f21274a;

            /* renamed from: b, reason: collision with root package name */
            private IBinder f21275b;

            C0171a(IBinder iBinder) {
                this.f21275b = iBinder;
            }

            @Override // com.umeng.analytics.pro.a
            public String a(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    parcelObtain.writeString(str);
                    if (!this.f21275b.transact(1, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().a(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f21275b;
            }

            @Override // com.umeng.analytics.pro.a
            public String b(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    parcelObtain.writeString(str);
                    if (!this.f21275b.transact(2, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().b(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String c() {
                return b.f21273h;
            }

            @Override // com.umeng.analytics.pro.a
            public String d(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    parcelObtain.writeString(str);
                    if (!this.f21275b.transact(4, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().d(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String e(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    parcelObtain.writeString(str);
                    if (!this.f21275b.transact(5, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().e(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String c(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    parcelObtain.writeString(str);
                    if (!this.f21275b.transact(3, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().c(str);
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public boolean a() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    if (!this.f21275b.transact(6, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().a();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String b() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21273h);
                    if (!this.f21275b.transact(7, parcelObtain, parcelObtain2, 0) && b.c() != null) {
                        return b.c().b();
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public b() {
            attachInterface(this, f21273h);
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f21273h);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof a)) ? new C0171a(iBinder) : (a) iInterfaceQueryLocalInterface;
        }

        public static a c() {
            return C0171a.f21274a;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1598968902) {
                parcel2.writeString(f21273h);
                return true;
            }
            switch (i2) {
                case 1:
                    parcel.enforceInterface(f21273h);
                    String strA = a(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strA);
                    return true;
                case 2:
                    parcel.enforceInterface(f21273h);
                    String strB = b(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strB);
                    return true;
                case 3:
                    parcel.enforceInterface(f21273h);
                    String strC = c(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strC);
                    return true;
                case 4:
                    parcel.enforceInterface(f21273h);
                    String strD = d(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strD);
                    return true;
                case 5:
                    parcel.enforceInterface(f21273h);
                    String strE = e(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strE);
                    return true;
                case 6:
                    parcel.enforceInterface(f21273h);
                    boolean zA = a();
                    parcel2.writeNoException();
                    parcel2.writeInt(zA ? 1 : 0);
                    return true;
                case 7:
                    parcel.enforceInterface(f21273h);
                    String strB2 = b();
                    parcel2.writeNoException();
                    parcel2.writeString(strB2);
                    return true;
                default:
                    return super.onTransact(i2, parcel, parcel2, i3);
            }
        }

        public static boolean a(a aVar) {
            if (C0171a.f21274a != null || aVar == null) {
                return false;
            }
            C0171a.f21274a = aVar;
            return true;
        }
    }
}
