package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

/* loaded from: classes3.dex */
public final class zaf extends com.google.android.gms.internal.base.zaa implements IInterface {
    zaf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zae(int i2) throws RemoteException {
        Parcel parcelD = d();
        parcelD.writeInt(i2);
        f(7, parcelD);
    }

    public final void zaf(IAccountAccessor iAccountAccessor, int i2, boolean z2) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, iAccountAccessor);
        parcelD.writeInt(i2);
        parcelD.writeInt(z2 ? 1 : 0);
        f(9, parcelD);
    }

    public final void zag(zai zaiVar, zae zaeVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zac(parcelD, zaiVar);
        com.google.android.gms.internal.base.zac.zad(parcelD, zaeVar);
        f(12, parcelD);
    }
}
