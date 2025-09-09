package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "DevicePublicKeyExtensionCreator")
/* loaded from: classes3.dex */
public final class zzu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzu> CREATOR = new zzv();

    @NonNull
    @SafeParcelable.Field(getter = "getDevicePublicKey", id = 1)
    private final boolean zza;

    @SafeParcelable.Constructor
    public zzu(@NonNull @SafeParcelable.Param(id = 1) boolean z2) {
        this.zza = ((Boolean) Preconditions.checkNotNull(Boolean.valueOf(z2))).booleanValue();
    }

    public final boolean equals(@Nullable Object obj) {
        return (obj instanceof zzu) && this.zza == ((zzu) obj).zza;
    }

    public final int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.zza));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zza);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
