package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;

@SafeParcelable.Class(creator = "AuthenticationExtensionsClientOutputsCreator")
/* loaded from: classes3.dex */
public class AuthenticationExtensionsClientOutputs extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<AuthenticationExtensionsClientOutputs> CREATOR = new zzc();

    @Nullable
    @SafeParcelable.Field(getter = "getUvmEntries", id = 1)
    private final UvmEntries zza;

    @Nullable
    @SafeParcelable.Field(getter = "getDevicePubKey", id = 2)
    private final zzf zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getCredProps", id = 3)
    private final AuthenticationExtensionsCredPropsOutputs zzc;

    @Nullable
    @SafeParcelable.Field(getter = "getPrf", id = 4)
    private final zzh zzd;

    public static final class Builder {

        @Nullable
        private UvmEntries zza;

        @Nullable
        private AuthenticationExtensionsCredPropsOutputs zzb;

        @NonNull
        public AuthenticationExtensionsClientOutputs build() {
            return new AuthenticationExtensionsClientOutputs(this.zza, null, this.zzb, null);
        }

        @NonNull
        public Builder setCredProps(@Nullable AuthenticationExtensionsCredPropsOutputs authenticationExtensionsCredPropsOutputs) {
            this.zzb = authenticationExtensionsCredPropsOutputs;
            return this;
        }

        @NonNull
        public Builder setUserVerificationMethodEntries(@Nullable UvmEntries uvmEntries) {
            this.zza = uvmEntries;
            return this;
        }
    }

    AuthenticationExtensionsClientOutputs(UvmEntries uvmEntries, zzf zzfVar, AuthenticationExtensionsCredPropsOutputs authenticationExtensionsCredPropsOutputs, zzh zzhVar) {
        this.zza = uvmEntries;
        this.zzb = zzfVar;
        this.zzc = authenticationExtensionsCredPropsOutputs;
        this.zzd = zzhVar;
    }

    @NonNull
    public static AuthenticationExtensionsClientOutputs deserializeFromBytes(@NonNull byte[] bArr) {
        return (AuthenticationExtensionsClientOutputs) SafeParcelableSerializer.deserializeFromBytes(bArr, CREATOR);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof AuthenticationExtensionsClientOutputs)) {
            return false;
        }
        AuthenticationExtensionsClientOutputs authenticationExtensionsClientOutputs = (AuthenticationExtensionsClientOutputs) obj;
        return Objects.equal(this.zza, authenticationExtensionsClientOutputs.zza) && Objects.equal(this.zzb, authenticationExtensionsClientOutputs.zzb) && Objects.equal(this.zzc, authenticationExtensionsClientOutputs.zzc) && Objects.equal(this.zzd, authenticationExtensionsClientOutputs.zzd);
    }

    @Nullable
    public AuthenticationExtensionsCredPropsOutputs getCredProps() {
        return this.zzc;
    }

    @Nullable
    public UvmEntries getUvmEntries() {
        return this.zza;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd);
    }

    @NonNull
    public byte[] serializeToBytes() {
        return SafeParcelableSerializer.serializeToBytes(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getUvmEntries(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzb, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 3, getCredProps(), i2, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzd, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
