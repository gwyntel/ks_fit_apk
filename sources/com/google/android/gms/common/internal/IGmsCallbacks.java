package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public interface IGmsCallbacks extends IInterface {
    void onPostInitComplete(int i2, @NonNull IBinder iBinder, @NonNull Bundle bundle) throws RemoteException;

    void zzb(int i2, @NonNull Bundle bundle) throws RemoteException;

    void zzc(int i2, IBinder iBinder, zzk zzkVar) throws RemoteException;
}
