package androidx.health.platform.client.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.health.platform.client.permission.Permission;
import androidx.health.platform.client.request.AggregateDataRequest;
import androidx.health.platform.client.request.DeleteDataRangeRequest;
import androidx.health.platform.client.request.DeleteDataRequest;
import androidx.health.platform.client.request.GetChangesRequest;
import androidx.health.platform.client.request.GetChangesTokenRequest;
import androidx.health.platform.client.request.ReadDataRangeRequest;
import androidx.health.platform.client.request.ReadDataRequest;
import androidx.health.platform.client.request.ReadExerciseRouteRequest;
import androidx.health.platform.client.request.RegisterForDataNotificationsRequest;
import androidx.health.platform.client.request.RequestContext;
import androidx.health.platform.client.request.UnregisterFromDataNotificationsRequest;
import androidx.health.platform.client.request.UpsertDataRequest;
import androidx.health.platform.client.request.UpsertExerciseRouteRequest;
import androidx.health.platform.client.service.IAggregateDataCallback;
import androidx.health.platform.client.service.IDeleteDataCallback;
import androidx.health.platform.client.service.IDeleteDataRangeCallback;
import androidx.health.platform.client.service.IFilterGrantedPermissionsCallback;
import androidx.health.platform.client.service.IGetChangesCallback;
import androidx.health.platform.client.service.IGetChangesTokenCallback;
import androidx.health.platform.client.service.IGetGrantedPermissionsCallback;
import androidx.health.platform.client.service.IInsertDataCallback;
import androidx.health.platform.client.service.IReadDataCallback;
import androidx.health.platform.client.service.IReadDataRangeCallback;
import androidx.health.platform.client.service.IReadExerciseRouteCallback;
import androidx.health.platform.client.service.IRegisterForDataNotificationsCallback;
import androidx.health.platform.client.service.IRevokeAllPermissionsCallback;
import androidx.health.platform.client.service.IUnregisterFromDataNotificationsCallback;
import androidx.health.platform.client.service.IUpdateDataCallback;
import androidx.health.platform.client.service.IUpsertExerciseRouteCallback;
import java.util.List;

/* loaded from: classes.dex */
public interface IHealthDataService extends IInterface {
    public static final int CURRENT_API_VERSION = 5;
    public static final String DESCRIPTOR = "androidx.health.platform.client.service.IHealthDataService";
    public static final int MIN_API_VERSION = 1;

    public static class Default implements IHealthDataService {
        @Override // androidx.health.platform.client.service.IHealthDataService
        public void aggregate(RequestContext requestContext, AggregateDataRequest aggregateDataRequest, IAggregateDataCallback iAggregateDataCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void deleteData(RequestContext requestContext, DeleteDataRequest deleteDataRequest, IDeleteDataCallback iDeleteDataCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void deleteDataRange(RequestContext requestContext, DeleteDataRangeRequest deleteDataRangeRequest, IDeleteDataRangeCallback iDeleteDataRangeCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void filterGrantedPermissions(RequestContext requestContext, List<Permission> list, IFilterGrantedPermissionsCallback iFilterGrantedPermissionsCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public int getApiVersion() throws RemoteException {
            return 0;
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void getChanges(RequestContext requestContext, GetChangesRequest getChangesRequest, IGetChangesCallback iGetChangesCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void getChangesToken(RequestContext requestContext, GetChangesTokenRequest getChangesTokenRequest, IGetChangesTokenCallback iGetChangesTokenCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void getGrantedPermissions(RequestContext requestContext, List<Permission> list, IGetGrantedPermissionsCallback iGetGrantedPermissionsCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void insertData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IInsertDataCallback iInsertDataCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void readData(RequestContext requestContext, ReadDataRequest readDataRequest, IReadDataCallback iReadDataCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void readDataRange(RequestContext requestContext, ReadDataRangeRequest readDataRangeRequest, IReadDataRangeCallback iReadDataRangeCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void readExerciseRoute(RequestContext requestContext, ReadExerciseRouteRequest readExerciseRouteRequest, IReadExerciseRouteCallback iReadExerciseRouteCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void registerForDataNotifications(RequestContext requestContext, RegisterForDataNotificationsRequest registerForDataNotificationsRequest, IRegisterForDataNotificationsCallback iRegisterForDataNotificationsCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void revokeAllPermissions(RequestContext requestContext, IRevokeAllPermissionsCallback iRevokeAllPermissionsCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void unregisterFromDataNotifications(RequestContext requestContext, UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest, IUnregisterFromDataNotificationsCallback iUnregisterFromDataNotificationsCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void updateData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IUpdateDataCallback iUpdateDataCallback) throws RemoteException {
        }

        @Override // androidx.health.platform.client.service.IHealthDataService
        public void upsertExerciseRoute(RequestContext requestContext, UpsertExerciseRouteRequest upsertExerciseRouteRequest, IUpsertExerciseRouteCallback iUpsertExerciseRouteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IHealthDataService {

        private static class Proxy implements IHealthDataService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void aggregate(RequestContext requestContext, AggregateDataRequest aggregateDataRequest, IAggregateDataCallback iAggregateDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, aggregateDataRequest, 0);
                    parcelObtain.writeStrongInterface(iAggregateDataCallback);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void deleteData(RequestContext requestContext, DeleteDataRequest deleteDataRequest, IDeleteDataCallback iDeleteDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, deleteDataRequest, 0);
                    parcelObtain.writeStrongInterface(iDeleteDataCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void deleteDataRange(RequestContext requestContext, DeleteDataRangeRequest deleteDataRangeRequest, IDeleteDataRangeCallback iDeleteDataRangeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, deleteDataRangeRequest, 0);
                    parcelObtain.writeStrongInterface(iDeleteDataRangeCallback);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void filterGrantedPermissions(RequestContext requestContext, List<Permission> list, IFilterGrantedPermissionsCallback iFilterGrantedPermissionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedList(parcelObtain, list, 0);
                    parcelObtain.writeStrongInterface(iFilterGrantedPermissionsCallback);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public int getApiVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void getChanges(RequestContext requestContext, GetChangesRequest getChangesRequest, IGetChangesCallback iGetChangesCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, getChangesRequest, 0);
                    parcelObtain.writeStrongInterface(iGetChangesCallback);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void getChangesToken(RequestContext requestContext, GetChangesTokenRequest getChangesTokenRequest, IGetChangesTokenCallback iGetChangesTokenCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, getChangesTokenRequest, 0);
                    parcelObtain.writeStrongInterface(iGetChangesTokenCallback);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void getGrantedPermissions(RequestContext requestContext, List<Permission> list, IGetGrantedPermissionsCallback iGetGrantedPermissionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedList(parcelObtain, list, 0);
                    parcelObtain.writeStrongInterface(iGetGrantedPermissionsCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IHealthDataService.DESCRIPTOR;
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void insertData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IInsertDataCallback iInsertDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, upsertDataRequest, 0);
                    parcelObtain.writeStrongInterface(iInsertDataCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void readData(RequestContext requestContext, ReadDataRequest readDataRequest, IReadDataCallback iReadDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, readDataRequest, 0);
                    parcelObtain.writeStrongInterface(iReadDataCallback);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void readDataRange(RequestContext requestContext, ReadDataRangeRequest readDataRangeRequest, IReadDataRangeCallback iReadDataRangeCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, readDataRangeRequest, 0);
                    parcelObtain.writeStrongInterface(iReadDataRangeCallback);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void readExerciseRoute(RequestContext requestContext, ReadExerciseRouteRequest readExerciseRouteRequest, IReadExerciseRouteCallback iReadExerciseRouteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, readExerciseRouteRequest, 0);
                    parcelObtain.writeStrongInterface(iReadExerciseRouteCallback);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void registerForDataNotifications(RequestContext requestContext, RegisterForDataNotificationsRequest registerForDataNotificationsRequest, IRegisterForDataNotificationsCallback iRegisterForDataNotificationsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, registerForDataNotificationsRequest, 0);
                    parcelObtain.writeStrongInterface(iRegisterForDataNotificationsCallback);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void revokeAllPermissions(RequestContext requestContext, IRevokeAllPermissionsCallback iRevokeAllPermissionsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    parcelObtain.writeStrongInterface(iRevokeAllPermissionsCallback);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void unregisterFromDataNotifications(RequestContext requestContext, UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest, IUnregisterFromDataNotificationsCallback iUnregisterFromDataNotificationsCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, unregisterFromDataNotificationsRequest, 0);
                    parcelObtain.writeStrongInterface(iUnregisterFromDataNotificationsCallback);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void updateData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IUpdateDataCallback iUpdateDataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, upsertDataRequest, 0);
                    parcelObtain.writeStrongInterface(iUpdateDataCallback);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // androidx.health.platform.client.service.IHealthDataService
            public void upsertExerciseRoute(RequestContext requestContext, UpsertExerciseRouteRequest upsertExerciseRouteRequest, IUpsertExerciseRouteCallback iUpsertExerciseRouteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHealthDataService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, requestContext, 0);
                    _Parcel.writeTypedObject(parcelObtain, upsertExerciseRouteRequest, 0);
                    parcelObtain.writeStrongInterface(iUpsertExerciseRouteCallback);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IHealthDataService.DESCRIPTOR);
        }

        public static IHealthDataService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHealthDataService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IHealthDataService)) ? new Proxy(iBinder) : (IHealthDataService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 >= 1 && i2 <= 16777215) {
                parcel.enforceInterface(IHealthDataService.DESCRIPTOR);
            }
            if (i2 == 1598968902) {
                parcel2.writeString(IHealthDataService.DESCRIPTOR);
                return true;
            }
            if (i2 == 1) {
                int apiVersion = getApiVersion();
                parcel2.writeNoException();
                parcel2.writeInt(apiVersion);
            } else if (i2 != 4) {
                switch (i2) {
                    case 9:
                        revokeAllPermissions((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), IRevokeAllPermissionsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 10:
                        insertData((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (UpsertDataRequest) _Parcel.readTypedObject(parcel, UpsertDataRequest.CREATOR), IInsertDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 11:
                        deleteData((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (DeleteDataRequest) _Parcel.readTypedObject(parcel, DeleteDataRequest.CREATOR), IDeleteDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 12:
                        readData((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (ReadDataRequest) _Parcel.readTypedObject(parcel, ReadDataRequest.CREATOR), IReadDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 13:
                        updateData((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (UpsertDataRequest) _Parcel.readTypedObject(parcel, UpsertDataRequest.CREATOR), IUpdateDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 14:
                        deleteDataRange((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (DeleteDataRangeRequest) _Parcel.readTypedObject(parcel, DeleteDataRangeRequest.CREATOR), IDeleteDataRangeCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 15:
                        aggregate((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (AggregateDataRequest) _Parcel.readTypedObject(parcel, AggregateDataRequest.CREATOR), IAggregateDataCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 16:
                        readDataRange((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (ReadDataRangeRequest) _Parcel.readTypedObject(parcel, ReadDataRangeRequest.CREATOR), IReadDataRangeCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 17:
                        getChangesToken((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (GetChangesTokenRequest) _Parcel.readTypedObject(parcel, GetChangesTokenRequest.CREATOR), IGetChangesTokenCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 18:
                        getChanges((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (GetChangesRequest) _Parcel.readTypedObject(parcel, GetChangesRequest.CREATOR), IGetChangesCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 19:
                        registerForDataNotifications((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (RegisterForDataNotificationsRequest) _Parcel.readTypedObject(parcel, RegisterForDataNotificationsRequest.CREATOR), IRegisterForDataNotificationsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 20:
                        unregisterFromDataNotifications((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (UnregisterFromDataNotificationsRequest) _Parcel.readTypedObject(parcel, UnregisterFromDataNotificationsRequest.CREATOR), IUnregisterFromDataNotificationsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 21:
                        upsertExerciseRoute((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (UpsertExerciseRouteRequest) _Parcel.readTypedObject(parcel, UpsertExerciseRouteRequest.CREATOR), IUpsertExerciseRouteCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 22:
                        readExerciseRoute((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), (ReadExerciseRouteRequest) _Parcel.readTypedObject(parcel, ReadExerciseRouteRequest.CREATOR), IReadExerciseRouteCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    case 23:
                        filterGrantedPermissions((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), parcel.createTypedArrayList(Permission.CREATOR), IFilterGrantedPermissionsCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        break;
                    default:
                        return super.onTransact(i2, parcel, parcel2, i3);
                }
            } else {
                getGrantedPermissions((RequestContext) _Parcel.readTypedObject(parcel, RequestContext.CREATOR), parcel.createTypedArrayList(Permission.CREATOR), IGetGrantedPermissionsCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
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
        public static <T extends Parcelable> void writeTypedList(Parcel parcel, List<T> list, int i2) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i3 = 0; i3 < size; i3++) {
                writeTypedObject(parcel, list.get(i3), i2);
            }
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

    void aggregate(RequestContext requestContext, AggregateDataRequest aggregateDataRequest, IAggregateDataCallback iAggregateDataCallback) throws RemoteException;

    void deleteData(RequestContext requestContext, DeleteDataRequest deleteDataRequest, IDeleteDataCallback iDeleteDataCallback) throws RemoteException;

    void deleteDataRange(RequestContext requestContext, DeleteDataRangeRequest deleteDataRangeRequest, IDeleteDataRangeCallback iDeleteDataRangeCallback) throws RemoteException;

    void filterGrantedPermissions(RequestContext requestContext, List<Permission> list, IFilterGrantedPermissionsCallback iFilterGrantedPermissionsCallback) throws RemoteException;

    int getApiVersion() throws RemoteException;

    void getChanges(RequestContext requestContext, GetChangesRequest getChangesRequest, IGetChangesCallback iGetChangesCallback) throws RemoteException;

    void getChangesToken(RequestContext requestContext, GetChangesTokenRequest getChangesTokenRequest, IGetChangesTokenCallback iGetChangesTokenCallback) throws RemoteException;

    void getGrantedPermissions(RequestContext requestContext, List<Permission> list, IGetGrantedPermissionsCallback iGetGrantedPermissionsCallback) throws RemoteException;

    void insertData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IInsertDataCallback iInsertDataCallback) throws RemoteException;

    void readData(RequestContext requestContext, ReadDataRequest readDataRequest, IReadDataCallback iReadDataCallback) throws RemoteException;

    void readDataRange(RequestContext requestContext, ReadDataRangeRequest readDataRangeRequest, IReadDataRangeCallback iReadDataRangeCallback) throws RemoteException;

    void readExerciseRoute(RequestContext requestContext, ReadExerciseRouteRequest readExerciseRouteRequest, IReadExerciseRouteCallback iReadExerciseRouteCallback) throws RemoteException;

    void registerForDataNotifications(RequestContext requestContext, RegisterForDataNotificationsRequest registerForDataNotificationsRequest, IRegisterForDataNotificationsCallback iRegisterForDataNotificationsCallback) throws RemoteException;

    void revokeAllPermissions(RequestContext requestContext, IRevokeAllPermissionsCallback iRevokeAllPermissionsCallback) throws RemoteException;

    void unregisterFromDataNotifications(RequestContext requestContext, UnregisterFromDataNotificationsRequest unregisterFromDataNotificationsRequest, IUnregisterFromDataNotificationsCallback iUnregisterFromDataNotificationsCallback) throws RemoteException;

    void updateData(RequestContext requestContext, UpsertDataRequest upsertDataRequest, IUpdateDataCallback iUpdateDataCallback) throws RemoteException;

    void upsertExerciseRoute(RequestContext requestContext, UpsertExerciseRouteRequest upsertExerciseRouteRequest, IUpsertExerciseRouteCallback iUpsertExerciseRouteCallback) throws RemoteException;
}
