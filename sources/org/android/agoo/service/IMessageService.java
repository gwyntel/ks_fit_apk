package org.android.agoo.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IMessageService extends IInterface {

    public static abstract class Stub extends Binder implements IMessageService {
        private static final String DESCRIPTOR = "org.android.agoo.service.IMessageService";
        static final int TRANSACTION_ping = 1;
        static final int TRANSACTION_probe = 2;

        private static class a implements IMessageService {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f26554a;

            a(IBinder iBinder) {
                this.f26554a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f26554a;
            }

            @Override // org.android.agoo.service.IMessageService
            public boolean ping() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f26554a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // org.android.agoo.service.IMessageService
            public void probe() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f26554a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMessageService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return iInterfaceQueryLocalInterface instanceof IMessageService ? (IMessageService) iInterfaceQueryLocalInterface : new a(iBinder);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean zPing = ping();
                parcel2.writeNoException();
                parcel2.writeInt(zPing ? 1 : 0);
                return true;
            }
            if (i2 != 2) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            probe();
            parcel2.writeNoException();
            return true;
        }
    }

    boolean ping() throws RemoteException;

    void probe() throws RemoteException;
}
