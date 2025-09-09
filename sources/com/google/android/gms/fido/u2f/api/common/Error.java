package com.google.android.gms.fido.u2f.api.common;

import androidx.annotation.NonNull;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes3.dex */
public class Error {

    @NonNull
    @VisibleForTesting
    public static final String JSON_ERROR_CODE = "errorCode";

    @NonNull
    @VisibleForTesting
    public static final String JSON_ERROR_MESSAGE = "errorMessage";
    private final ErrorCode zza;
    private final String zzb;

    public Error(@NonNull ErrorCode errorCode) {
        this.zza = errorCode;
        this.zzb = null;
    }

    @NonNull
    public ErrorCode getErrorCode() {
        return this.zza;
    }

    @NonNull
    public String getErrorMessage() {
        return this.zzb;
    }

    @NonNull
    public JSONObject toJsonObject() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("errorCode", this.zza.getCode());
            String str = this.zzb;
            if (str != null) {
                jSONObject.put("errorMessage", str);
            }
            return jSONObject;
        } catch (JSONException e2) {
            throw new RuntimeException(e2);
        }
    }

    @NonNull
    public String toString() {
        return this.zzb == null ? String.format(Locale.ENGLISH, "{errorCode: %d}", Integer.valueOf(this.zza.getCode())) : String.format(Locale.ENGLISH, "{errorCode: %d, errorMessage: %s}", Integer.valueOf(this.zza.getCode()), this.zzb);
    }

    public Error(@NonNull ErrorCode errorCode, @NonNull String str) {
        this.zza = errorCode;
        this.zzb = str;
    }
}
