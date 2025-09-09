package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import anetwork.channel.aidl.Connection;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableNetworkListener;

/* loaded from: classes2.dex */
public interface RemoteNetwork extends IInterface {

    public static abstract class Stub extends Binder implements RemoteNetwork {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.RemoteNetwork";
        static final int TRANSACTION_asyncSend = 2;
        static final int TRANSACTION_getConnection = 3;
        static final int TRANSACTION_syncSend = 1;

        private static class Proxy implements RemoteNetwork {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f7123a;

            Proxy(IBinder iBinder) {
                this.f7123a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f7123a;
            }

            @Override // anetwork.channel.aidl.RemoteNetwork
            public ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        parcelObtain.writeInt(1);
                        parcelableRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(parcelableNetworkListener != null ? parcelableNetworkListener.asBinder() : null);
                    this.f7123a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    ParcelableFuture parcelableFutureAsInterface = ParcelableFuture.Stub.asInterface(parcelObtain2.readStrongBinder());
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return parcelableFutureAsInterface;
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // anetwork.channel.aidl.RemoteNetwork
            public Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        parcelObtain.writeInt(1);
                        parcelableRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f7123a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    Connection connectionAsInterface = Connection.Stub.asInterface(parcelObtain2.readStrongBinder());
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return connectionAsInterface;
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // anetwork.channel.aidl.RemoteNetwork
            public NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableRequest != null) {
                        parcelObtain.writeInt(1);
                        parcelableRequest.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f7123a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    NetworkResponse networkResponseCreateFromParcel = parcelObtain2.readInt() != 0 ? NetworkResponse.CREATOR.createFromParcel(parcelObtain2) : null;
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return networkResponseCreateFromParcel;
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static RemoteNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof RemoteNetwork)) ? new Proxy(iBinder) : (RemoteNetwork) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                NetworkResponse networkResponseSyncSend = syncSend(parcel.readInt() != 0 ? ParcelableRequest.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                if (networkResponseSyncSend != null) {
                    parcel2.writeInt(1);
                    networkResponseSyncSend.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                ParcelableFuture parcelableFutureAsyncSend = asyncSend(parcel.readInt() != 0 ? ParcelableRequest.CREATOR.createFromParcel(parcel) : null, ParcelableNetworkListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeStrongBinder(parcelableFutureAsyncSend != null ? parcelableFutureAsyncSend.asBinder() : null);
                return true;
            }
            if (i2 != 3) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            Connection connection = getConnection(parcel.readInt() != 0 ? ParcelableRequest.CREATOR.createFromParcel(parcel) : null);
            parcel2.writeNoException();
            parcel2.writeStrongBinder(connection != null ? connection.asBinder() : null);
            return true;
        }
    }

    ParcelableFuture asyncSend(ParcelableRequest parcelableRequest, ParcelableNetworkListener parcelableNetworkListener) throws RemoteException;

    Connection getConnection(ParcelableRequest parcelableRequest) throws RemoteException;

    NetworkResponse syncSend(ParcelableRequest parcelableRequest) throws RemoteException;
}
