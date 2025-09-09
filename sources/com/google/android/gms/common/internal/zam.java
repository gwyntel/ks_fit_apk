package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zam extends com.google.android.gms.internal.base.zaa implements IInterface {
    zam(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    public final IObjectWrapper zae(IObjectWrapper iObjectWrapper, zax zaxVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, iObjectWrapper);
        com.google.android.gms.internal.base.zac.zac(parcelD, zaxVar);
        Parcel parcelE = e(2, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelE.readStrongBinder());
        parcelE.recycle();
        return iObjectWrapperAsInterface;
    }
}
