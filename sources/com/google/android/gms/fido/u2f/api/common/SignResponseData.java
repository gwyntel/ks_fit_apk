package com.google.android.gms.fido.u2f.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.MimeTypes;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.fido.zzaj;
import com.google.android.gms.internal.fido.zzak;
import com.google.android.gms.internal.fido.zzbf;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "SignResponseDataCreator")
@SafeParcelable.Reserved({1})
@Deprecated
/* loaded from: classes3.dex */
public class SignResponseData extends ResponseData {

    @NonNull
    public static final Parcelable.Creator<SignResponseData> CREATOR = new zzl();

    @NonNull
    @VisibleForTesting
    public static final String JSON_RESPONSE_DATA_CLIENT_DATA = "clientData";

    @NonNull
    @VisibleForTesting
    public static final String JSON_RESPONSE_DATA_KEY_HANDLE = "keyHandle";

    @NonNull
    @VisibleForTesting
    public static final String JSON_RESPONSE_DATA_SIGNATURE_DATA = "signatureData";

    @SafeParcelable.Field(getter = "getKeyHandle", id = 2)
    private final byte[] zza;

    @SafeParcelable.Field(getter = "getClientDataString", id = 3)
    private final String zzb;

    @SafeParcelable.Field(getter = "getSignatureData", id = 4)
    private final byte[] zzc;

    @SafeParcelable.Field(getter = "getApplication", id = 5)
    private final byte[] zzd;

    @Deprecated
    public SignResponseData(@NonNull byte[] bArr, @NonNull String str, @NonNull byte[] bArr2) {
        this(bArr, str, bArr2, new byte[0]);
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof SignResponseData)) {
            return false;
        }
        SignResponseData signResponseData = (SignResponseData) obj;
        return Arrays.equals(this.zza, signResponseData.zza) && Objects.equal(this.zzb, signResponseData.zzb) && Arrays.equals(this.zzc, signResponseData.zzc) && Arrays.equals(this.zzd, signResponseData.zzd);
    }

    @NonNull
    public String getClientDataString() {
        return this.zzb;
    }

    @NonNull
    public byte[] getKeyHandle() {
        return this.zza;
    }

    @NonNull
    public byte[] getSignatureData() {
        return this.zzc;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(Arrays.hashCode(this.zza)), this.zzb, Integer.valueOf(Arrays.hashCode(this.zzc)), Integer.valueOf(Arrays.hashCode(this.zzd)));
    }

    @Override // com.google.android.gms.fido.u2f.api.common.ResponseData
    @NonNull
    public JSONObject toJsonObject() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(JSON_RESPONSE_DATA_KEY_HANDLE, Base64.encodeToString(this.zza, 11));
            jSONObject.put(JSON_RESPONSE_DATA_CLIENT_DATA, Base64.encodeToString(this.zzb.getBytes(), 11));
            jSONObject.put(JSON_RESPONSE_DATA_SIGNATURE_DATA, Base64.encodeToString(this.zzc, 11));
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public String toString() {
        zzaj zzajVarZza = zzak.zza(this);
        zzbf zzbfVarZzd = zzbf.zzd();
        byte[] bArr = this.zza;
        zzajVarZza.zzb(JSON_RESPONSE_DATA_KEY_HANDLE, zzbfVarZzd.zze(bArr, 0, bArr.length));
        zzajVarZza.zzb("clientDataString", this.zzb);
        zzbf zzbfVarZzd2 = zzbf.zzd();
        byte[] bArr2 = this.zzc;
        zzajVarZza.zzb(JSON_RESPONSE_DATA_SIGNATURE_DATA, zzbfVarZzd2.zze(bArr2, 0, bArr2.length));
        zzbf zzbfVarZzd3 = zzbf.zzd();
        byte[] bArr3 = this.zzd;
        zzajVarZza.zzb(MimeTypes.BASE_TYPE_APPLICATION, zzbfVarZzd3.zze(bArr3, 0, bArr3.length));
        return zzajVarZza.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeByteArray(parcel, 2, getKeyHandle(), false);
        SafeParcelWriter.writeString(parcel, 3, getClientDataString(), false);
        SafeParcelWriter.writeByteArray(parcel, 4, getSignatureData(), false);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzd, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @SafeParcelable.Constructor
    public SignResponseData(@NonNull @SafeParcelable.Param(id = 2) byte[] bArr, @NonNull @SafeParcelable.Param(id = 3) String str, @NonNull @SafeParcelable.Param(id = 4) byte[] bArr2, @NonNull @SafeParcelable.Param(id = 5) byte[] bArr3) {
        this.zza = (byte[]) Preconditions.checkNotNull(bArr);
        this.zzb = (String) Preconditions.checkNotNull(str);
        this.zzc = (byte[]) Preconditions.checkNotNull(bArr2);
        this.zzd = (byte[]) Preconditions.checkNotNull(bArr3);
    }
}
