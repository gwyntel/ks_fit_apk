package com.google.android.gms.fido.fido2.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelableSerializer;
import com.google.android.gms.fido.fido2.api.common.ErrorCode;

@SafeParcelable.Class(creator = "AuthenticatorErrorResponseCreator")
@SafeParcelable.Reserved({1})
/* loaded from: classes3.dex */
public class AuthenticatorErrorResponse extends AuthenticatorResponse {

    @NonNull
    public static final Parcelable.Creator<AuthenticatorErrorResponse> CREATOR = new zzl();

    @NonNull
    @SafeParcelable.Field(getter = "getErrorCodeAsInt", id = 2, type = "int")
    private final ErrorCode zza;

    @Nullable
    @SafeParcelable.Field(getter = "getErrorMessage", id = 3)
    private final String zzb;

    @SafeParcelable.Field(defaultValue = "0", getter = "getInternalErrorCode", id = 4, type = "int")
    private final int zzc;

    AuthenticatorErrorResponse(int i2, String str, int i3) {
        try {
            this.zza = ErrorCode.toErrorCode(i2);
            this.zzb = str;
            this.zzc = i3;
        } catch (ErrorCode.UnsupportedErrorCodeException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    @NonNull
    public static AuthenticatorErrorResponse deserializeFromBytes(@NonNull byte[] bArr) {
        return (AuthenticatorErrorResponse) SafeParcelableSerializer.deserializeFromBytes(bArr, CREATOR);
    }

    public boolean equals(@NonNull Object obj) {
        if (!(obj instanceof AuthenticatorErrorResponse)) {
            return false;
        }
        AuthenticatorErrorResponse authenticatorErrorResponse = (AuthenticatorErrorResponse) obj;
        return Objects.equal(this.zza, authenticatorErrorResponse.zza) && Objects.equal(this.zzb, authenticatorErrorResponse.zzb) && Objects.equal(Integer.valueOf(this.zzc), Integer.valueOf(authenticatorErrorResponse.zzc));
    }

    @Override // com.google.android.gms.fido.fido2.api.common.AuthenticatorResponse
    @NonNull
    public byte[] getClientDataJSON() {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public ErrorCode getErrorCode() {
        return this.zza;
    }

    public int getErrorCodeAsInt() {
        return this.zza.getCode();
    }

    @Nullable
    public String getErrorMessage() {
        return this.zzb;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, Integer.valueOf(this.zzc));
    }

    @Override // com.google.android.gms.fido.fido2.api.common.AuthenticatorResponse
    @NonNull
    public byte[] serializeToBytes() {
        return SafeParcelableSerializer.serializeToBytes(this);
    }

    @NonNull
    public String toString() {
        com.google.android.gms.internal.fido.zzaj zzajVarZza = com.google.android.gms.internal.fido.zzak.zza(this);
        zzajVarZza.zza("errorCode", this.zza.getCode());
        String str = this.zzb;
        if (str != null) {
            zzajVarZza.zzb("errorMessage", str);
        }
        return zzajVarZza.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, getErrorCodeAsInt());
        SafeParcelWriter.writeString(parcel, 3, getErrorMessage(), false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzc);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
