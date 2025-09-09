package com.google.android.gms.internal.p000authapi;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* loaded from: classes3.dex */
public final class zbw extends zba implements IInterface {
    zbw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.identity.internal.ISignInService");
    }

    public final void zbc(zbm zbmVar, BeginSignInRequest beginSignInRequest) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, zbmVar);
        zbc.zbc(parcelD, beginSignInRequest);
        e(1, parcelD);
    }

    public final void zbd(zbp zbpVar, GetPhoneNumberHintIntentRequest getPhoneNumberHintIntentRequest, String str) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, zbpVar);
        zbc.zbc(parcelD, getPhoneNumberHintIntentRequest);
        parcelD.writeString(str);
        e(4, parcelD);
    }

    public final void zbe(zbr zbrVar, GetSignInIntentRequest getSignInIntentRequest) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, zbrVar);
        zbc.zbc(parcelD, getSignInIntentRequest);
        e(3, parcelD);
    }

    public final void zbf(IStatusCallback iStatusCallback, String str) throws RemoteException {
        Parcel parcelD = d();
        zbc.zbd(parcelD, iStatusCallback);
        parcelD.writeString(str);
        e(2, parcelD);
    }
}
