package com.google.android.gms.common.moduleinstall.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* loaded from: classes3.dex */
public final class zaf extends com.google.android.gms.internal.base.zaa implements IInterface {
    zaf(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.moduleinstall.internal.IModuleInstallService");
    }

    public final void zae(zae zaeVar, ApiFeatureRequest apiFeatureRequest) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, zaeVar);
        com.google.android.gms.internal.base.zac.zac(parcelD, apiFeatureRequest);
        f(1, parcelD);
    }

    public final void zaf(zae zaeVar, ApiFeatureRequest apiFeatureRequest) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, zaeVar);
        com.google.android.gms.internal.base.zac.zac(parcelD, apiFeatureRequest);
        f(3, parcelD);
    }

    public final void zag(zae zaeVar, ApiFeatureRequest apiFeatureRequest, zah zahVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, zaeVar);
        com.google.android.gms.internal.base.zac.zac(parcelD, apiFeatureRequest);
        com.google.android.gms.internal.base.zac.zad(parcelD, zahVar);
        f(2, parcelD);
    }

    public final void zah(IStatusCallback iStatusCallback, ApiFeatureRequest apiFeatureRequest) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, iStatusCallback);
        com.google.android.gms.internal.base.zac.zac(parcelD, apiFeatureRequest);
        f(4, parcelD);
    }

    public final void zai(IStatusCallback iStatusCallback, zah zahVar) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zad(parcelD, iStatusCallback);
        com.google.android.gms.internal.base.zac.zad(parcelD, zahVar);
        f(6, parcelD);
    }
}
