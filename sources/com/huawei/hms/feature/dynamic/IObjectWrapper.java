package com.huawei.hms.feature.dynamic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IObjectWrapper extends IInterface {
    public static final String DESCRIPTOR = "com.huawei.hms.feature.dynamic.IObjectWrapper";

    public static class Default implements IObjectWrapper {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IObjectWrapper {

        public static class Proxy implements IObjectWrapper {
            public static IObjectWrapper sDefaultImpl;

            /* renamed from: a, reason: collision with root package name */
            public IBinder f16096a;

            public Proxy(IBinder iBinder) {
                this.f16096a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f16096a;
            }

            public String getInterfaceDescriptor() {
                return IObjectWrapper.DESCRIPTOR;
            }
        }

        public Stub() {
            attachInterface(this, IObjectWrapper.DESCRIPTOR);
        }

        public static IObjectWrapper asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IObjectWrapper.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IObjectWrapper)) ? new Proxy(iBinder) : (IObjectWrapper) iInterfaceQueryLocalInterface;
        }

        public static IObjectWrapper getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IObjectWrapper iObjectWrapper) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iObjectWrapper == null) {
                return false;
            }
            Proxy.sDefaultImpl = iObjectWrapper;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 != 1598968902) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            parcel2.writeString(IObjectWrapper.DESCRIPTOR);
            return true;
        }
    }
}
