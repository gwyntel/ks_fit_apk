package com.google.android.gms.fido.u2f.api.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

@SafeParcelable.Class(creator = "RegisteredKeyCreator")
@SafeParcelable.Reserved({1})
@Deprecated
/* loaded from: classes3.dex */
public class RegisteredKey extends AbstractSafeParcelable {

    @NonNull
    public static final Parcelable.Creator<RegisteredKey> CREATOR = new zzj();

    /* renamed from: a, reason: collision with root package name */
    String f12945a;

    @SafeParcelable.Field(getter = "getKeyHandle", id = 2)
    private final KeyHandle zzb;

    @SafeParcelable.Field(getter = "getAppId", id = 4)
    private final String zzc;

    public RegisteredKey(@NonNull KeyHandle keyHandle) {
        this(keyHandle, null, null);
    }

    @NonNull
    public static RegisteredKey parseFromJson(@NonNull JSONObject jSONObject) throws JSONException {
        return new RegisteredKey(KeyHandle.parseFromJson(jSONObject), jSONObject.has(ClientData.KEY_CHALLENGE) ? jSONObject.getString(ClientData.KEY_CHALLENGE) : null, jSONObject.has("appId") ? jSONObject.getString("appId") : null);
    }

    public boolean equals(@NonNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegisteredKey)) {
            return false;
        }
        RegisteredKey registeredKey = (RegisteredKey) obj;
        String str = this.f12945a;
        if (str == null) {
            if (registeredKey.f12945a != null) {
                return false;
            }
        } else if (!str.equals(registeredKey.f12945a)) {
            return false;
        }
        if (!this.zzb.equals(registeredKey.zzb)) {
            return false;
        }
        String str2 = this.zzc;
        if (str2 == null) {
            if (registeredKey.zzc != null) {
                return false;
            }
        } else if (!str2.equals(registeredKey.zzc)) {
            return false;
        }
        return true;
    }

    @NonNull
    public String getAppId() {
        return this.zzc;
    }

    @NonNull
    public String getChallengeValue() {
        return this.f12945a;
    }

    @NonNull
    public KeyHandle getKeyHandle() {
        return this.zzb;
    }

    public int hashCode() {
        String str = this.f12945a;
        int iHashCode = (((str == null ? 0 : str.hashCode()) + 31) * 31) + this.zzb.hashCode();
        String str2 = this.zzc;
        return (iHashCode * 31) + (str2 != null ? str2.hashCode() : 0);
    }

    @NonNull
    public JSONObject toJson() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            String str = this.f12945a;
            if (str != null) {
                jSONObject.put(ClientData.KEY_CHALLENGE, str);
            }
            JSONObject jSONObjectZza = this.zzb.zza();
            Iterator<String> itKeys = jSONObjectZza.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                jSONObject.put(next, jSONObjectZza.get(next));
            }
            String str2 = this.zzc;
            if (str2 != null) {
                jSONObject.put("appId", str2);
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public String toString() throws JSONException {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(SignResponseData.JSON_RESPONSE_DATA_KEY_HANDLE, Base64.encodeToString(this.zzb.getBytes(), 11));
            if (this.zzb.getProtocolVersion() != ProtocolVersion.UNKNOWN) {
                jSONObject.put("version", this.zzb.getProtocolVersion().toString());
            }
            if (this.zzb.getTransports() != null) {
                jSONObject.put("transports", this.zzb.getTransports().toString());
            }
            String str = this.f12945a;
            if (str != null) {
                jSONObject.put(ClientData.KEY_CHALLENGE, str);
            }
            String str2 = this.zzc;
            if (str2 != null) {
                jSONObject.put("appId", str2);
            }
            return jSONObject.toString();
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NonNull Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getKeyHandle(), i2, false);
        SafeParcelWriter.writeString(parcel, 3, getChallengeValue(), false);
        SafeParcelWriter.writeString(parcel, 4, getAppId(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @SafeParcelable.Constructor
    public RegisteredKey(@NonNull @SafeParcelable.Param(id = 2) KeyHandle keyHandle, @NonNull @SafeParcelable.Param(id = 3) String str, @NonNull @SafeParcelable.Param(id = 4) String str2) {
        this.zzb = (KeyHandle) Preconditions.checkNotNull(keyHandle);
        this.f12945a = str;
        this.zzc = str2;
    }
}
