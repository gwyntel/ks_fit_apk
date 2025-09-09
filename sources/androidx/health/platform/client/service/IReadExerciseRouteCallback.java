package androidx.health.platform.client.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.health.platform.client.error.ErrorStatus;
import androidx.health.platform.client.response.ReadExerciseRouteResponse;

/* loaded from: classes.dex */
public interface IReadExerciseRouteCallback extends IInterface {
    public static final String DESCRIPTOR = "androidx.health.platform.client.service.IReadExerciseRouteCallback";

    public static class Default implements IReadExerciseRouteCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.health.platform.client.service.IReadExerciseRouteCallback
        public void onError(ErrorStatus errorStatus) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IReadExerciseRouteCallback
        public void onSuccess(ReadExerciseRouteResponse readExerciseRouteResponse) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IReadExerciseRouteCallback {

        private static class Proxy implements IReadExerciseRouteCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IReadExerciseRouteCallback.DESCRIPTOR;
            }

            @Override // androidx.health.platform.client.service.IReadExerciseRouteCallback
            public void onError(ErrorStatus errorStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IReadExerciseRouteCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, errorStatus, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IReadExerciseRouteCallback
            public void onSuccess(ReadExerciseRouteResponse readExerciseRouteResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IReadExerciseRouteCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, readExerciseRouteResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IReadExerciseRouteCallback.DESCRIPTOR);
        }

        public static IReadExerciseRouteCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IReadExerciseRouteCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IReadExerciseRouteCallback)) ? new Proxy(iBinder) : (IReadExerciseRouteCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(IReadExerciseRouteCallback.DESCRIPTOR);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(IReadExerciseRouteCallback.DESCRIPTOR);
                return true;
            }
            if (i2 == 1) {
                onSuccess((ReadExerciseRouteResponse) _Parcel.readTypedObject(parcel, ReadExerciseRouteResponse.CREATOR));
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

    void onSuccess(ReadExerciseRouteResponse readExerciseRouteResponse) throws RemoteException;
}
