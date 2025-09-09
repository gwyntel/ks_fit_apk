package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zzq extends com.google.android.gms.internal.common.zza implements IInterface {
    zzq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    public final int zze() throws RemoteException {
        Parcel parcelA = a(6, d());
        int i2 = parcelA.readInt();
        parcelA.recycle();
        return i2;
    }

    public final int zzf(IObjectWrapper iObjectWrapper, String str, boolean z2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(z2 ? 1 : 0);
        Parcel parcelA = a(3, parcelD);
        int i2 = parcelA.readInt();
        parcelA.recycle();
        return i2;
    }

    public final int zzg(IObjectWrapper iObjectWrapper, String str, boolean z2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(z2 ? 1 : 0);
        Parcel parcelA = a(5, parcelD);
        int i2 = parcelA.readInt();
        parcelA.recycle();
        return i2;
    }

    public final IObjectWrapper zzh(IObjectWrapper iObjectWrapper, String str, int i2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(i2);
        Parcel parcelA = a(2, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }

    public final IObjectWrapper zzi(IObjectWrapper iObjectWrapper, String str, int i2, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(i2);
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper2);
        Parcel parcelA = a(8, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }

    public final IObjectWrapper zzj(IObjectWrapper iObjectWrapper, String str, int i2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(i2);
        Parcel parcelA = a(4, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }

    public final IObjectWrapper zzk(IObjectWrapper iObjectWrapper, String str, boolean z2, long j2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.common.zzc.zze(parcelD, iObjectWrapper);
        parcelD.writeString(str);
        parcelD.writeInt(z2 ? 1 : 0);
        parcelD.writeLong(j2);
        Parcel parcelA = a(7, parcelD);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelA.readStrongBinder());
        parcelA.recycle();
        return iObjectWrapperAsInterface;
    }
}
