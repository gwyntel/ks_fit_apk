package com.google.android.gms.fido.u2f.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fido.u2f.api.common.ProtocolVersion;
import com.google.android.gms.internal.fido.zzaj;
import com.google.android.gms.internal.fido.zzak;
import com.google.android.gms.internal.fido.zzbf;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "RegisterResponseDataCreator")
@SafeParcelable.Reserved({1})
@Deprecated
/* loaded from: classes3.dex */
public class RegisterResponseData extends ResponseData {

    @NonNull
    public static final Parcelable.Creator<RegisterResponseData> CREATOR = new zzi();

    @SafeParcelable.Field(getter = "getRegisterData", id = 2)
    private final byte[] zza;

    @SafeParcelable.Field(getter = "getProtocolVersionAsString", id = 3, type = "java.lang.String")
    private final ProtocolVersion zzb;

    @Nullable
    @SafeParcelable.Field(getter = "getClientDataString", id = 4)
    private final String zzc;

    public RegisterResponseData(@NonNull byte[] bArr) {
        this.zza = (byte[]) Preconditions.checkNotNull(bArr);
        this.zzb = ProtocolVersion.V1;
        this.zzc = null;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof RegisterResponseData)) {
            return false;
        }
        RegisterResponseData registerResponseData = (RegisterResponseData) obj;
        return Objects.equal(this.zzb, registerResponseData.zzb) && Arrays.equals(this.zza, registerResponseData.zza) && Objects.equal(this.zzc, registerResponseData.zzc);
    }

    @NonNull
    public String getClientDataString() {
        return this.zzc;
    }

    @NonNull
    public ProtocolVersion getProtocolVersion() {
        return this.zzb;
    }

    @NonNull
    public byte[] getRegisterData() {
        return this.zza;
    }

    public int getVersionCode() {
        ProtocolVersion protocolVersion = ProtocolVersion.UNKNOWN;
        int iOrdinal = this.zzb.ordinal();
        int i2 = 1;
        if (iOrdinal != 1) {
            i2 = 2;
            if (iOrdinal != 2) {
                return -1;
            }
        }
        return i2;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzb, Integer.valueOf(Arrays.hashCode(this.zza)), this.zzc);
    }

    @Override // com.google.android.gms.fido.u2f.api.common.ResponseData
    @NonNull
    public JSONObject toJsonObject() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("registrationData", Base64.encodeToString(this.zza, 11));
            jSONObject.put("version", this.zzb.toString());
            String str = this.zzc;
            if (str != null) {
                jSONObject.put(SignResponseData.JSON_RESPONSE_DATA_CLIENT_DATA, Base64.encodeToString(str.getBytes(), 11));
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public String toString() {
        zzaj zzajVarZza = zzak.zza(this);
        zzajVarZza.zzb("protocolVersion", this.zzb);
        zzbf zzbfVarZzd = zzbf.zzd();
        byte[] bArr = this.zza;
        zzajVarZza.zzb("registerData", zzbfVarZzd.zze(bArr, 0, bArr.length));
        String str = this.zzc;
        if (str != null) {
            zzajVarZza.zzb("clientDataString", str);
        }
        return zzajVarZza.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByteArray(parcel, 2, getRegisterData(), false);
        SafeParcelWriter.writeString(parcel, 3, this.zzb.toString(), false);
        SafeParcelWriter.writeString(parcel, 4, getClientDataString(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public RegisterResponseData(@NonNull byte[] bArr, @NonNull ProtocolVersion protocolVersion, @Nullable String str) {
        this.zza = (byte[]) Preconditions.checkNotNull(bArr);
        this.zzb = (ProtocolVersion) Preconditions.checkNotNull(protocolVersion);
        Preconditions.checkArgument(protocolVersion != ProtocolVersion.UNKNOWN);
        if (protocolVersion == ProtocolVersion.V1) {
            Preconditions.checkArgument(str == null);
            this.zzc = null;
        } else {
            this.zzc = (String) Preconditions.checkNotNull(str);
        }
    }

    RegisterResponseData(byte[] bArr, String str, String str2) {
        this.zza = bArr;
        try {
            this.zzb = ProtocolVersion.fromString(str);
            this.zzc = str2;
        } catch (ProtocolVersion.UnsupportedProtocolException e2) {
            throw new IllegalArgumentException(e2);
        }
    }
}
