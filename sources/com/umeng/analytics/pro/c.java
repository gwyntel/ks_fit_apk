package com.umeng.analytics.pro;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.umeng.analytics.pro.b;

/* loaded from: classes4.dex */
public interface c extends IInterface {

    public static class a implements c {
        @Override // com.umeng.analytics.pro.c
        public void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.umeng.analytics.pro.c
        public void b(com.umeng.analytics.pro.b bVar) throws RemoteException {
        }

        @Override // com.umeng.analytics.pro.c
        public void a(com.umeng.analytics.pro.b bVar) throws RemoteException {
        }
    }

    void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws RemoteException;

    void a(com.umeng.analytics.pro.b bVar) throws RemoteException;

    void b(com.umeng.analytics.pro.b bVar) throws RemoteException;

    public static abstract class b extends Binder implements c {

        /* renamed from: a, reason: collision with root package name */
        static final int f21547a = 1;

        /* renamed from: b, reason: collision with root package name */
        static final int f21548b = 2;

        /* renamed from: c, reason: collision with root package name */
        static final int f21549c = 3;

        /* renamed from: d, reason: collision with root package name */
        private static final String f21550d = "com.hihonor.cloudservice.oaid.IOAIDService";

        private static class a implements c {

            /* renamed from: a, reason: collision with root package name */
            public static c f21551a;

            /* renamed from: b, reason: collision with root package name */
            private IBinder f21552b;

            a(IBinder iBinder) {
                this.f21552b = iBinder;
            }

            public String a() {
                return b.f21550d;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f21552b;
            }

            @Override // com.umeng.analytics.pro.c
            public void b(com.umeng.analytics.pro.b bVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21550d);
                    parcelObtain.writeStrongBinder(bVar != null ? bVar.asBinder() : null);
                    if (this.f21552b.transact(3, parcelObtain, parcelObtain2, 0) || b.a() == null) {
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } else {
                        b.a().b(bVar);
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // com.umeng.analytics.pro.c
            public void a(int i2, long j2, boolean z2, float f2, double d2, String str) throws Throwable {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21550d);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeString(str);
                    try {
                        if (this.f21552b.transact(1, parcelObtain, parcelObtain2, 0) || b.a() == null) {
                            parcelObtain2.readException();
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                        } else {
                            b.a().a(i2, j2, z2, f2, d2, str);
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

            @Override // com.umeng.analytics.pro.c
            public void a(com.umeng.analytics.pro.b bVar) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(b.f21550d);
                    parcelObtain.writeStrongBinder(bVar != null ? bVar.asBinder() : null);
                    if (!this.f21552b.transact(2, parcelObtain, parcelObtain2, 0) && b.a() != null) {
                        b.a().a(bVar);
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

        public b() {
            attachInterface(this, f21550d);
        }

        public static c a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f21550d);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof c)) ? new a(iBinder) : (c) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(f21550d);
                a(parcel.readInt(), parcel.readLong(), parcel.readInt() != 0, parcel.readFloat(), parcel.readDouble(), parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(f21550d);
                a(b.AbstractBinderC0172b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i2 != 3) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(f21550d);
                return true;
            }
            parcel.enforceInterface(f21550d);
            b(b.AbstractBinderC0172b.a(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        public static boolean a(c cVar) {
            if (a.f21551a != null || cVar == null) {
                return false;
            }
            a.f21551a = cVar;
            return true;
        }

        public static c a() {
            return a.f21551a;
        }
    }
}
