package com.google.android.gms.common.internal.service;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.TelemetryData;

/* loaded from: classes3.dex */
public final class zai extends com.google.android.gms.internal.base.zaa implements IInterface {
    zai(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.service.IClientTelemetryService");
    }

    public final void zae(TelemetryData telemetryData) throws RemoteException {
        Parcel parcelD = d();
        com.google.android.gms.internal.base.zac.zac(parcelD, telemetryData);
        g(1, parcelD);
    }
}
