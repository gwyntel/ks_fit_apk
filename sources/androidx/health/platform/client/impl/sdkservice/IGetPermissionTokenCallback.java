package androidx.health.platform.client.impl.sdkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGetPermissionTokenCallback extends IInterface {
    public static final String DESCRIPTOR = "androidx.health.platform.client.impl.sdkservice.IGetPermissionTokenCallback";

    public static class Default implements IGetPermissionTokenCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.health.platform.client.impl.sdkservice.IGetPermissionTokenCallback
        public void onSuccess(String str) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IGetPermissionTokenCallback {

        private static class Proxy implements IGetPermissionTokenCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetPermissionTokenCallback.DESCRIPTOR;
            }

            @Override // androidx.health.platform.client.impl.sdkservice.IGetPermissionTokenCallback
            public void onSuccess(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGetPermissionTokenCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IGetPermissionTokenCallback.DESCRIPTOR);
        }

        public static IGetPermissionTokenCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetPermissionTokenCallback.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGetPermissionTokenCallback)) ? new Proxy(iBinder) : (IGetPermissionTokenCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(IGetPermissionTokenCallback.DESCRIPTOR);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(IGetPermissionTokenCallback.DESCRIPTOR);
                return true;
            }
            if (i2 != 1) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            onSuccess(parcel.readString());
            return true;
        }
    }

    void onSuccess(String str) throws RemoteException;
}
