package com.google.android.gms.cloudmessaging;

import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public final class zzd implements Parcelable {
    public static final Parcelable.Creator<zzd> CREATOR = new zzb();

    /* renamed from: a, reason: collision with root package name */
    Messenger f12669a;

    /* renamed from: b, reason: collision with root package name */
    IMessengerCompat f12670b;

    public zzd(IBinder iBinder) {
        this.f12669a = new Messenger(iBinder);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return zza().equals(((zzd) obj).zza());
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public final int hashCode() {
        return zza().hashCode();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        Messenger messenger = this.f12669a;
        if (messenger != null) {
            parcel.writeStrongBinder(messenger.getBinder());
        } else {
            parcel.writeStrongBinder(this.f12670b.asBinder());
        }
    }

    public final IBinder zza() {
        Messenger messenger = this.f12669a;
        return messenger != null ? messenger.getBinder() : this.f12670b.asBinder();
    }

    public final void zzb(Message message) throws RemoteException {
        Messenger messenger = this.f12669a;
        if (messenger != null) {
            messenger.send(message);
        } else {
            this.f12670b.send(message);
        }
    }
}
