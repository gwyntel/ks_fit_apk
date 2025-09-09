package com.facebook.internal.instrument;

import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.Utility;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public final class InstrumentData {
    private static final String PARAM_APP_VERSION = "app_version";
    private static final String PARAM_CALLSTACK = "callstack";
    private static final String PARAM_DEVICE_MODEL = "device_model";
    private static final String PARAM_DEVICE_OS = "device_os_version";
    private static final String PARAM_REASON = "reason";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_TYPE = "type";

    @Nullable
    private String appVersion;

    @Nullable
    private String cause;
    private String filename;

    @Nullable
    private String stackTrace;

    @Nullable
    private Long timestamp;

    @Nullable
    private String type;

    /* renamed from: com.facebook.internal.instrument.InstrumentData$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$internal$instrument$InstrumentData$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$facebook$internal$instrument$InstrumentData$Type = iArr;
            try {
                iArr[Type.CrashReport.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$facebook$internal$instrument$InstrumentData$Type[Type.CrashShield.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$facebook$internal$instrument$InstrumentData$Type[Type.ThreadCheck.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum Type {
        CrashReport,
        CrashShield,
        ThreadCheck;

        @Override // java.lang.Enum
        public String toString() {
            int i2 = AnonymousClass1.$SwitchMap$com$facebook$internal$instrument$InstrumentData$Type[ordinal()];
            return i2 != 1 ? i2 != 2 ? i2 != 3 ? AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN : "ThreadCheck" : "CrashShield" : "CrashReport";
        }
    }

    public InstrumentData(Throwable th, Type type) {
        this.appVersion = Utility.getAppVersion();
        this.cause = InstrumentUtility.getCause(th);
        this.stackTrace = InstrumentUtility.getStackTrace(th);
        this.timestamp = Long.valueOf(System.currentTimeMillis() / 1000);
        this.type = type.toString();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(InstrumentUtility.CRASH_REPORT_PREFIX);
        stringBuffer.append(this.timestamp.toString());
        stringBuffer.append(".json");
        this.filename = stringBuffer.toString();
    }

    public void clear() {
        InstrumentUtility.deleteFile(this.filename);
    }

    public int compareTo(InstrumentData instrumentData) {
        Long l2 = this.timestamp;
        if (l2 == null) {
            return -1;
        }
        Long l3 = instrumentData.timestamp;
        if (l3 == null) {
            return 1;
        }
        return l3.compareTo(l2);
    }

    @Nullable
    public JSONObject getParameters() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PARAM_DEVICE_OS, Build.VERSION.RELEASE);
            jSONObject.put("device_model", Build.MODEL);
            String str = this.appVersion;
            if (str != null) {
                jSONObject.put("app_version", str);
            }
            Long l2 = this.timestamp;
            if (l2 != null) {
                jSONObject.put("timestamp", l2);
            }
            String str2 = this.cause;
            if (str2 != null) {
                jSONObject.put(PARAM_REASON, str2);
            }
            String str3 = this.stackTrace;
            if (str3 != null) {
                jSONObject.put(PARAM_CALLSTACK, str3);
            }
            String str4 = this.type;
            if (str4 != null) {
                jSONObject.put("type", str4);
            }
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    public boolean isValid() {
        return (this.stackTrace == null || this.timestamp == null) ? false : true;
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

    public InstrumentData(File file) {
        String name = file.getName();
        this.filename = name;
        JSONObject file2 = InstrumentUtility.readFile(name, true);
        if (file2 != null) {
            this.appVersion = file2.optString("app_version", null);
            this.cause = file2.optString(PARAM_REASON, null);
            this.stackTrace = file2.optString(PARAM_CALLSTACK, null);
            this.timestamp = Long.valueOf(file2.optLong("timestamp", 0L));
            this.type = file2.optString("type", null);
        }
    }
}
