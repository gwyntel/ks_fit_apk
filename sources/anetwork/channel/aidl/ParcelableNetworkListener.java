package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableInputStream;

/* loaded from: classes2.dex */
public interface ParcelableNetworkListener extends IInterface {

    public static abstract class Stub extends Binder implements ParcelableNetworkListener {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.ParcelableNetworkListener";
        static final int TRANSACTION_getListenerState = 5;
        static final int TRANSACTION_onDataReceived = 1;
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onInputStreamGet = 4;
        static final int TRANSACTION_onResponseCode = 3;

        private static class Proxy implements ParcelableNetworkListener {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f7122a;

            Proxy(IBinder iBinder) {
                this.f7122a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f7122a;
            }

            @Override // anetwork.channel.aidl.ParcelableNetworkListener
            public byte getListenerState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7122a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readByte();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.ParcelableNetworkListener
            public void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultProgressEvent != null) {
                        parcelObtain.writeInt(1);
                        defaultProgressEvent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f7122a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // anetwork.channel.aidl.ParcelableNetworkListener
            public void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (defaultFinishEvent != null) {
                        parcelObtain.writeInt(1);
                        defaultFinishEvent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f7122a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // anetwork.channel.aidl.ParcelableNetworkListener
            public void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(parcelableInputStream != null ? parcelableInputStream.asBinder() : null);
                    this.f7122a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // anetwork.channel.aidl.ParcelableNetworkListener
            public boolean onResponseCode(int i2, ParcelableHeader parcelableHeader) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i2);
                    if (parcelableHeader != null) {
                        parcelObtain.writeInt(1);
                        parcelableHeader.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f7122a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    boolean z2 = parcelObtain2.readInt() != 0;
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    return z2;
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

        public static ParcelableNetworkListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ParcelableNetworkListener)) ? new Proxy(iBinder) : (ParcelableNetworkListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onDataReceived(parcel.readInt() != 0 ? DefaultProgressEvent.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                onFinished(parcel.readInt() != 0 ? DefaultFinishEvent.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i2 == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean zOnResponseCode = onResponseCode(parcel.readInt(), parcel.readInt() != 0 ? ParcelableHeader.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                parcel2.writeInt(zOnResponseCode ? 1 : 0);
                return true;
            }
            if (i2 == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                onInputStreamGet(ParcelableInputStream.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i2 != 5) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            byte listenerState = getListenerState();
            parcel2.writeNoException();
            parcel2.writeByte(listenerState);
            return true;
        }
    }

    byte getListenerState() throws RemoteException;

    void onDataReceived(DefaultProgressEvent defaultProgressEvent) throws RemoteException;

    void onFinished(DefaultFinishEvent defaultFinishEvent) throws RemoteException;

    void onInputStreamGet(ParcelableInputStream parcelableInputStream) throws RemoteException;

    boolean onResponseCode(int i2, ParcelableHeader parcelableHeader) throws RemoteException;
}
