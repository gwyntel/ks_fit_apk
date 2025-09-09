package androidx.health.platform.client.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.response.InsertDataResponse;

/* loaded from: classes.dex */
public interface IInsertDataCallback extends IInterface {
    public static final String DESCRIPTOR = "androidx.health.platform.client.service.IInsertDataCallback";

    public static class Default implements IInsertDataCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.health.platform.client.service.IInsertDataCallback
        public void onError(ErrorStatus errorStatus) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IInsertDataCallback
        public void onSuccess(InsertDataResponse insertDataResponse) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IInsertDataCallback {

        private static class Proxy implements IInsertDataCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInsertDataCallback.DESCRIPTOR;
            }

            @Override // androidx.health.platform.client.service.IInsertDataCallback
            public void onError(ErrorStatus errorStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInsertDataCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, errorStatus, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IInsertDataCallback
            public void onSuccess(InsertDataResponse insertDataResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IInsertDataCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, insertDataResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IInsertDataCallback.DESCRIPTOR);
        }

        public static IInsertDataCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInsertDataCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IInsertDataCallback)) ? new Proxy(iBinder) : (IInsertDataCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(IInsertDataCallback.DESCRIPTOR);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(IInsertDataCallback.DESCRIPTOR);
                return true;
            }
            if (i2 == 1) {
                onSuccess((InsertDataResponse) _Parcel.readTypedObject(parcel, InsertDataResponse.CREATOR));
            } else {
                if (i2 != 2) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                onError((ErrorStatus) _Parcel.readTypedObject(parcel, ErrorStatus.CREATOR));
            }
            return true;
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t2, int i2) {
            if (t2 == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t2.writeToParcel(parcel, i2);
            }
        }
    }

    void onError(ErrorStatus errorStatus) throws RemoteException;

    void onSuccess(InsertDataResponse insertDataResponse) throws RemoteException;
}
