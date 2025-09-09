package androidx.health.platform.client.impl.sdkservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import androidx.health.platform.client.impl.sdkservice.IGetIsInForegroundCallback;
import androidx.health.platform.client.impl.sdkservice.IGetPermissionTokenCallback;
import androidx.health.platform.client.impl.sdkservice.ISetPermissionTokenCallback;

/* loaded from: classes.dex */
public interface IHealthDataSdkService extends IInterface {
    public static final String DESCRIPTOR = "androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService";

    public static class Default implements IHealthDataSdkService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
        public void getIsInForeground(String str, IGetIsInForegroundCallback iGetIsInForegroundCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
        public void getPermissionToken(String str, IGetPermissionTokenCallback iGetPermissionTokenCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
        public void setPermissionToken(String str, String str2, ISetPermissionTokenCallback iSetPermissionTokenCallback) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IHealthDataSdkService {

        private static class Proxy implements IHealthDataSdkService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHealthDataSdkService.DESCRIPTOR;
            }

            @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
            public void getIsInForeground(String str, IGetIsInForegroundCallback iGetIsInForegroundCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataSdkService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iGetIsInForegroundCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
            public void getPermissionToken(String str, IGetPermissionTokenCallback iGetPermissionTokenCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataSdkService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iGetPermissionTokenCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.impl.sdkservice.IHealthDataSdkService
            public void setPermissionToken(String str, String str2, ISetPermissionTokenCallback iSetPermissionTokenCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataSdkService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongInterface(iSetPermissionTokenCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IHealthDataSdkService.DESCRIPTOR);
        }

        public static IHealthDataSdkService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHealthDataSdkService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IHealthDataSdkService)) ? new Proxy(iBinder) : (IHealthDataSdkService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(IHealthDataSdkService.DESCRIPTOR);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(IHealthDataSdkService.DESCRIPTOR);
                return true;
            }
            if (i2 == 1) {
                setPermissionToken(parcel.readString(), parcel.readString(), ISetPermissionTokenCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i2 == 2) {
                getPermissionToken(parcel.readString(), IGetPermissionTokenCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else {
                if (i2 != 3) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                getIsInForeground(parcel.readString(), IGetIsInForegroundCallback.Stub.asInterface(parcel.readStrongBinder()));
            }
            return true;
        }
    }

    void getIsInForeground(String str, IGetIsInForegroundCallback iGetIsInForegroundCallback) throws RemoteException;

    void getPermissionToken(String str, IGetPermissionTokenCallback iGetPermissionTokenCallback) throws RemoteException;

    void setPermissionToken(String str, String str2, ISetPermissionTokenCallback iSetPermissionTokenCallback) throws RemoteException;
}
