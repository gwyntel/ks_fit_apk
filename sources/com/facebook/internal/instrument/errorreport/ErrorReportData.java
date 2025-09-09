package com.facebook.internal.instrument.errorreport;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class ErrorReportData {
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PRARAM_ERROR_MESSAGE = "error_message";

    @Nullable
    private String errorMessage;
    private String filename;

    @Nullable
    private Long timestamp;

    public ErrorReportData(String str) {
        this.timestamp = Long.valueOf(System.currentTimeMillis() / 1000);
        this.errorMessage = str;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(InstrumentUtility.ERROR_REPORT_PREFIX);
        stringBuffer.append(this.timestamp);
        stringBuffer.append(".json");
        this.filename = stringBuffer.toString();
    }

    public void clear() {
        InstrumentUtility.deleteFile(this.filename);
    }

    public int compareTo(ErrorReportData errorReportData) {
        Long l2 = this.timestamp;
        if (l2 == null) {
            return -1;
        }
        Long l3 = errorReportData.timestamp;
        if (l3 == null) {
            return 1;
        }
        return l3.compareTo(l2);
    }

    @Nullable
    public JSONObject getParameters() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            Long l2 = this.timestamp;
            if (l2 != null) {
                jSONObject.put("timestamp", l2);
            }
            jSONObject.put("error_message", this.errorMessage);
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    public boolean isValid() {
        return (this.errorMessage == null || this.timestamp == null) ? false : true;
    }

    public void save() throws IOException {
        if (isValid()) {
            InstrumentUtility.writeFile(this.filename, toString());
        }
    }

    @Nullable
    public String toString() throws JSONException {
        JSONObject parameters = getParameters();
        if (parameters == null) {
            return null;
        }
        return parameters.toString();
    }

    public ErrorReportData(File file) {
        String name = file.getName();
        this.filename = name;
        JSONObject file2 = InstrumentUtility.readFile(name, true);
        if (file2 != null) {
            this.timestamp = Long.valueOf(file2.optLong("timestamp", 0L));
            this.errorMessage = file2.optString("error_message", null);
        }
    }
}
