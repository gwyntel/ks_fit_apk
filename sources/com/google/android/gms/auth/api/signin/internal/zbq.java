package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public abstract class zbq extends com.google.android.gms.internal.p000authapi.zbb implements zbr {
    public zbq() {
        super("com.google.android.gms.auth.api.signin.internal.ISignInCallbacks");
    }

    @Override // com.google.android.gms.internal.p000authapi.zbb
    protected final boolean a(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        switch (i2) {
            case 101:
                GoogleSignInAccount googleSignInAccount = (GoogleSignInAccount) com.google.android.gms.internal.p000authapi.zbc.zba(parcel, GoogleSignInAccount.CREATOR);
                Status status = (Status) com.google.android.gms.internal.p000authapi.zbc.zba(parcel, Status.CREATOR);
                com.google.android.gms.internal.p000authapi.zbc.zbb(parcel);
                zbd(googleSignInAccount, status);
                break;
            case 102:
                Status status2 = (Status) com.google.android.gms.internal.p000authapi.zbc.zba(parcel, Status.CREATOR);
                com.google.android.gms.internal.p000authapi.zbc.zbb(parcel);
                zbc(status2);
                break;
            case 103:
                Status status3 = (Status) com.google.android.gms.internal.p000authapi.zbc.zba(parcel, Status.CREATOR);
                com.google.android.gms.internal.p000authapi.zbc.zbb(parcel);
                zbb(status3);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
