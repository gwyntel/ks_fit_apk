package com.huawei.hms.core.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.huawei.hms.core.aidl.IAIDLCallback;

/* loaded from: classes4.dex */
public interface IAIDLInvoke extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.core.aidl.IAIDLInvoke";

    public static abstract class Stub extends Binder implements IAIDLInvoke {

        private static class a implements IAIDLInvoke {

            /* renamed from: b, reason: collision with root package name */
            public static IAIDLInvoke f16039b;

            /* renamed from: a, reason: collision with root package name */
            private IBinder f16040a;

            a(IBinder iBinder) {
                this.f16040a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f16040a;
            }

            @Override // com.huawei.hms.core.aidl.IAIDLInvoke
            public void asyncCall(DataBuffer dataBuffer, IAIDLCallback iAIDLCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIDLInvoke.DESCRIPTOR);
                    if (dataBuffer != null) {
                        parcelObtain.writeInt(1);
                        dataBuffer.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iAIDLCallback != null ? iAIDLCallback.asBinder() : null);
                    if (this.f16040a.transact(2, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        parcelObtain.recycle();
                    } else {
                        Stub.getDefaultImpl().asyncCall(dataBuffer, iAIDLCallback);
                        parcelObtain.recycle();
                    }
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            }

            @Override // com.huawei.hms.core.aidl.IAIDLInvoke
            public void syncCall(DataBuffer dataBuffer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIDLInvoke.DESCRIPTOR);
                    if (dataBuffer != null) {
                        parcelObtain.writeInt(1);
                        dataBuffer.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    if (this.f16040a.transact(1, parcelObtain, parcelObtain2, 0) || Stub.getDefaultImpl() == null) {
                        parcelObtain2.readException();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } else {
                        Stub.getDefaultImpl().syncCall(dataBuffer);
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

        public Stub() {
            attachInterface(this, IAIDLInvoke.DESCRIPTOR);
        }

        public static IAIDLInvoke asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAIDLInvoke.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIDLInvoke)) ? new a(iBinder) : (IAIDLInvoke) iInterfaceQueryLocalInterface;
        }

        public static IAIDLInvoke getDefaultImpl() {
            return a.f16039b;
        }

        public static boolean setDefaultImpl(IAIDLInvoke iAIDLInvoke) {
            if (a.f16039b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iAIDLInvoke == null) {
                return false;
            }
            a.f16039b = iAIDLInvoke;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1598968902) {
                parcel2.writeString(IAIDLInvoke.DESCRIPTOR);
                return true;
            }
            if (i2 == 1) {
                parcel.enforceInterface(IAIDLInvoke.DESCRIPTOR);
                syncCall(parcel.readInt() != 0 ? DataBuffer.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i2 != 2) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            parcel.enforceInterface(IAIDLInvoke.DESCRIPTOR);
            asyncCall(parcel.readInt() != 0 ? DataBuffer.CREATOR.createFromParcel(parcel) : null, IAIDLCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }
    }

    void asyncCall(DataBuffer dataBuffer, IAIDLCallback iAIDLCallback) throws RemoteException;

    void syncCall(DataBuffer dataBuffer) throws RemoteException;
}
