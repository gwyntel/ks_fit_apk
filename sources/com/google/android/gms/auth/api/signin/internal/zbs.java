package com.google.android.gms.auth.api.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/* loaded from: classes3.dex */
public final class zbs extends com.google.android.gms.internal.p000authapi.zba implements IInterface {
    zbs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.signin.internal.ISignInService");
    }

    public final void zbc(zbr zbrVar, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.p000authapi.zbc.zbd(parcelD, zbrVar);
        com.google.android.gms.internal.p000authapi.zbc.zbc(parcelD, googleSignInOptions);
        e(103, parcelD);
    }

    public final void zbd(zbr zbrVar, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.p000authapi.zbc.zbd(parcelD, zbrVar);
        com.google.android.gms.internal.p000authapi.zbc.zbc(parcelD, googleSignInOptions);
        e(102, parcelD);
    }

    public final void zbe(zbr zbrVar, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.p000authapi.zbc.zbd(parcelD, zbrVar);
        com.google.android.gms.internal.p000authapi.zbc.zbc(parcelD, googleSignInOptions);
        e(101, parcelD);
    }
}
