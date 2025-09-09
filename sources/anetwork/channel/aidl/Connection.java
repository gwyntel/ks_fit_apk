package anetwork.channel.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.statist.StatisticData;
import java.util.Map;

/* loaded from: classes2.dex */
public interface Connection extends IInterface {

    public static abstract class Stub extends Binder implements Connection {
        private static final String DESCRIPTOR = "anetwork.channel.aidl.Connection";
        static final int TRANSACTION_cancel = 6;
        static final int TRANSACTION_getConnHeadFields = 4;
        static final int TRANSACTION_getDesc = 3;
        static final int TRANSACTION_getInputStream = 1;
        static final int TRANSACTION_getStatisticData = 5;
        static final int TRANSACTION_getStatusCode = 2;

        private static class Proxy implements Connection {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f7098a;

            Proxy(IBinder iBinder) {
                this.f7098a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f7098a;
            }

            @Override // anetwork.channel.aidl.Connection
            public void cancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.Connection
            public Map getConnHeadFields() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.Connection
            public String getDesc() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.Connection
            public ParcelableInputStream getInputStream() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ParcelableInputStream.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.Connection
            public StatisticData getStatisticData() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() == 1 ? (StatisticData) parcelObtain2.readSerializable() : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // anetwork.channel.aidl.Connection
            public int getStatusCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f7098a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static Connection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof Connection)) ? new Proxy(iBinder) : (Connection) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i2) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    ParcelableInputStream inputStream = getInputStream();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(inputStream != null ? inputStream.asBinder() : null);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    int statusCode = getStatusCode();
                    parcel2.writeNoException();
                    parcel2.writeInt(statusCode);
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    String desc = getDesc();
                    parcel2.writeNoException();
                    parcel2.writeString(desc);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    Map connHeadFields = getConnHeadFields();
                    parcel2.writeNoException();
                    parcel2.writeMap(connHeadFields);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    StatisticData statisticData = getStatisticData();
                    parcel2.writeNoException();
                    if (statisticData != null) {
                        parcel2.writeInt(1);
                        parcel2.writeSerializable(statisticData);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    cancel();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i2, parcel, parcel2, i3);
            }
        }
    }

    void cancel() throws RemoteException;

    Map getConnHeadFields() throws RemoteException;

    String getDesc() throws RemoteException;

    ParcelableInputStream getInputStream() throws RemoteException;

    StatisticData getStatisticData() throws RemoteException;

    int getStatusCode() throws RemoteException;
}
