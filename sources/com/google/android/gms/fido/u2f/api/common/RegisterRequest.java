package com.google.android.gms.fido.u2f.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.fido.u2f.api.common.ProtocolVersion;
import java.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "RegisterRequestCreator")
@Deprecated
/* loaded from: classes3.dex */
public class RegisterRequest extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<RegisterRequest> CREATOR = new zzg();
    public static final int U2F_V1_CHALLENGE_BYTE_LENGTH = 65;

    @SafeParcelable.VersionField(getter = "getVersionCode", id = 1)
    private final int zza;

    @SafeParcelable.Field(getter = "getProtocolVersionAsString", id = 2, type = "java.lang.String")
    private final ProtocolVersion zzb;

    @SafeParcelable.Field(getter = "getChallengeValue", id = 3)
    private final byte[] zzc;

    @SafeParcelable.Field(getter = "getAppId", id = 4)
    private final String zzd;

    RegisterRequest(int i2, String str, byte[] bArr, String str2) {
        this.zza = i2;
        try {
            this.zzb = ProtocolVersion.fromString(str);
            this.zzc = bArr;
            this.zzd = str2;
        } catch (ProtocolVersion.UnsupportedProtocolException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    @NonNull
    public static RegisterRequest parseFromJson(@NonNull JSONObject jSONObject) throws JSONException {
        try {
            try {
                try {
                    return new RegisterRequest(ProtocolVersion.fromString(jSONObject.has("version") ? jSONObject.getString("version") : null), Base64.decode(jSONObject.getString(ClientData.KEY_CHALLENGE), 8), jSONObject.has("appId") ? jSONObject.getString("appId") : null);
                } catch (IllegalArgumentException e2) {
                    throw new JSONException(e2.getMessage());
                }
            } catch (IllegalArgumentException e3) {
                throw new JSONException(e3.toString());
            }
        } catch (ProtocolVersion.UnsupportedProtocolException e4) {
            throw new JSONException(e4.toString());
        }
    }

    public boolean equals(@NonNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegisterRequest)) {
            return false;
        }
        RegisterRequest registerRequest = (RegisterRequest) obj;
        if (!Arrays.equals(this.zzc, registerRequest.zzc) || this.zzb != registerRequest.zzb) {
            return false;
        }
        String str = this.zzd;
        if (str == null) {
            if (registerRequest.zzd != null) {
                return false;
            }
        } else if (!str.equals(registerRequest.zzd)) {
            return false;
        }
        return true;
    }

    @NonNull
    public String getAppId() {
        return this.zzd;
    }

    @NonNull
    public byte[] getChallengeValue() {
        return this.zzc;
    }

    @NonNull
    public ProtocolVersion getProtocolVersion() {
        return this.zzb;
    }

    public int getVersionCode() {
        return this.zza;
    }

    public int hashCode() {
        int iHashCode = ((Arrays.hashCode(this.zzc) + 31) * 31) + this.zzb.hashCode();
        String str = this.zzd;
        return (iHashCode * 31) + (str == null ? 0 : str.hashCode());
    }

    @NonNull
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", this.zzb.toString());
            jSONObject.put(ClientData.KEY_CHALLENGE, Base64.encodeToString(this.zzc, 11));
            String str = this.zzd;
            if (str != null) {
                jSONObject.put("appId", str);
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getVersionCode());
        SafeParcelWriter.writeString(parcel, 2, this.zzb.toString(), false);
        SafeParcelWriter.writeByteArray(parcel, 3, getChallengeValue(), false);
        SafeParcelWriter.writeString(parcel, 4, getAppId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public RegisterRequest(@NonNull ProtocolVersion protocolVersion, @NonNull byte[] bArr, @NonNull String str) {
        this.zza = 1;
        this.zzb = (ProtocolVersion) Preconditions.checkNotNull(protocolVersion);
        this.zzc = (byte[]) Preconditions.checkNotNull(bArr);
        if (protocolVersion == ProtocolVersion.V1) {
            Preconditions.checkArgument(bArr.length == 65, "invalid challengeValue length for V1");
        }
        this.zzd = str;
    }
}
