package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zzr extends com.google.android.gms.internal.common.zza implements IInterface {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    public final IObjectWrapper zze(IObjectWrapper iObjectWrapper, String str, int i2, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(i2);
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper2);
        Parcel parcelA = a(2, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }

    public final IObjectWrapper zzf(IObjectWrapper iObjectWrapper, String str, int i2, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(i2);
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper2);
        Parcel parcelA = a(3, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }
}
