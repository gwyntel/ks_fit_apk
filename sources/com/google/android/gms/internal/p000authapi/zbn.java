package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest;
import com.google.android.gms.auth.api.identity.SavePasswordRequest;

/* loaded from: classes3.dex */
public final class zbn extends zba implements IInterface {
    zbn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ICredentialSavingService");
    }

    public final void zbc(zbt zbtVar, SaveAccountLinkingTokenRequest saveAccountLinkingTokenRequest) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, zbtVar);
        zbc.zbc(parcelD, saveAccountLinkingTokenRequest);
        e(1, parcelD);
    }

    public final void zbd(zbv zbvVar, SavePasswordRequest savePasswordRequest) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, zbvVar);
        zbc.zbc(parcelD, savePasswordRequest);
        e(2, parcelD);
    }
}
