package com.facebook.internal;

import android.net.Uri;
import androidx.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public final class FetchedAppSettings {
    private boolean IAPAutomaticLoggingEnabled;
    private boolean automaticLoggingEnabled;
    private boolean codelessEventsEnabled;
    private Map<String, Map<String, DialogFeatureConfig>> dialogConfigMap;
    private FacebookRequestErrorClassification errorClassification;
    private JSONArray eventBindings;
    private String nuxContent;
    private boolean nuxEnabled;

    @Nullable
    private String rawAamRules;

    @Nullable
    private String restrictiveDataSetting;
    private String sdkUpdateMessage;
    private int sessionTimeoutInSeconds;
    private String smartLoginBookmarkIconURL;
    private String smartLoginMenuIconURL;
    private EnumSet<SmartLoginOption> smartLoginOptions;

    @Nullable
    private String suggestedEventsSetting;
    private boolean supportsImplicitLogging;
    private boolean trackUninstallEnabled;

    public static class DialogFeatureConfig {
        private static final String DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR = "\\|";
        private static final String DIALOG_CONFIG_NAME_KEY = "name";
        private static final String DIALOG_CONFIG_URL_KEY = "url";
        private static final String DIALOG_CONFIG_VERSIONS_KEY = "versions";
        private String dialogName;
        private Uri fallbackUrl;
        private String featureName;
        private int[] featureVersionSpec;

        private DialogFeatureConfig(String str, String str2, Uri uri, int[] iArr) {
            this.dialogName = str;
            this.featureName = str2;
            this.fallbackUrl = uri;
            this.featureVersionSpec = iArr;
        }

        public static DialogFeatureConfig parseDialogConfig(JSONObject jSONObject) {
            String strOptString = jSONObject.optString("name");
            if (Utility.isNullOrEmpty(strOptString)) {
                return null;
            }
            String[] strArrSplit = strOptString.split(DIALOG_CONFIG_DIALOG_NAME_FEATURE_NAME_SEPARATOR);
            if (strArrSplit.length != 2) {
                return null;
            }
            String str = strArrSplit[0];
            String str2 = strArrSplit[1];
            if (Utility.isNullOrEmpty(str) || Utility.isNullOrEmpty(str2)) {
                return null;
            }
            String strOptString2 = jSONObject.optString("url");
            return new DialogFeatureConfig(str, str2, Utility.isNullOrEmpty(strOptString2) ? null : Uri.parse(strOptString2), parseVersionSpec(jSONObject.optJSONArray(DIALOG_CONFIG_VERSIONS_KEY)));
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static int[] parseVersionSpec(org.json.JSONArray r7) throws java.lang.NumberFormatException {
            /*
                if (r7 == 0) goto L2e
                int r0 = r7.length()
                int[] r1 = new int[r0]
                r2 = 0
            L9:
                if (r2 >= r0) goto L2f
                r3 = -1
                int r4 = r7.optInt(r2, r3)
                if (r4 != r3) goto L28
                java.lang.String r5 = r7.optString(r2)
                boolean r6 = com.facebook.internal.Utility.isNullOrEmpty(r5)
                if (r6 != 0) goto L28
                int r3 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.NumberFormatException -> L21
                goto L29
            L21:
                r4 = move-exception
                java.lang.String r5 = "FacebookSDK"
                com.facebook.internal.Utility.logd(r5, r4)
                goto L29
            L28:
                r3 = r4
            L29:
                r1[r2] = r3
                int r2 = r2 + 1
                goto L9
            L2e:
                r1 = 0
            L2f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.FetchedAppSettings.DialogFeatureConfig.parseVersionSpec(org.json.JSONArray):int[]");
        }

        public String getDialogName() {
            return this.dialogName;
        }

        public Uri getFallbackUrl() {
            return this.fallbackUrl;
        }

        public String getFeatureName() {
            return this.featureName;
        }

        public int[] getVersionSpec() {
            return this.featureVersionSpec;
        }
    }

    public FetchedAppSettings(boolean z2, String str, boolean z3, int i2, EnumSet<SmartLoginOption> enumSet, Map<String, Map<String, DialogFeatureConfig>> map, boolean z4, FacebookRequestErrorClassification facebookRequestErrorClassification, String str2, String str3, boolean z5, boolean z6, JSONArray jSONArray, String str4, boolean z7, @Nullable String str5, @Nullable String str6, @Nullable String str7) {
        this.supportsImplicitLogging = z2;
        this.nuxContent = str;
        this.nuxEnabled = z3;
        this.dialogConfigMap = map;
        this.errorClassification = facebookRequestErrorClassification;
        this.sessionTimeoutInSeconds = i2;
        this.automaticLoggingEnabled = z4;
        this.smartLoginOptions = enumSet;
        this.smartLoginBookmarkIconURL = str2;
        this.smartLoginMenuIconURL = str3;
        this.IAPAutomaticLoggingEnabled = z5;
        this.codelessEventsEnabled = z6;
        this.eventBindings = jSONArray;
        this.sdkUpdateMessage = str4;
        this.trackUninstallEnabled = z7;
        this.rawAamRules = str5;
        this.suggestedEventsSetting = str6;
        this.restrictiveDataSetting = str7;
    }

    public static DialogFeatureConfig getDialogFeatureConfig(String str, String str2, String str3) {
        FetchedAppSettings appSettingsWithoutQuery;
        Map<String, DialogFeatureConfig> map;
        if (Utility.isNullOrEmpty(str2) || Utility.isNullOrEmpty(str3) || (appSettingsWithoutQuery = FetchedAppSettingsManager.getAppSettingsWithoutQuery(str)) == null || (map = appSettingsWithoutQuery.getDialogConfigurations().get(str2)) == null) {
            return null;
        }
        return map.get(str3);
    }

    public boolean getAutomaticLoggingEnabled() {
        return this.automaticLoggingEnabled;
    }

    public boolean getCodelessEventsEnabled() {
        return this.codelessEventsEnabled;
    }

    public Map<String, Map<String, DialogFeatureConfig>> getDialogConfigurations() {
        return this.dialogConfigMap;
    }

    public FacebookRequestErrorClassification getErrorClassification() {
        return this.errorClassification;
    }

    public JSONArray getEventBindings() {
        return this.eventBindings;
    }

    public boolean getIAPAutomaticLoggingEnabled() {
        return this.IAPAutomaticLoggingEnabled;
    }

    public String getNuxContent() {
        return this.nuxContent;
    }

    public boolean getNuxEnabled() {
        return this.nuxEnabled;
    }

    @Nullable
    public String getRawAamRules() {
        return this.rawAamRules;
    }

    @Nullable
    public String getRestrictiveDataSetting() {
        return this.restrictiveDataSetting;
    }

    public String getSdkUpdateMessage() {
        return this.sdkUpdateMessage;
    }

    public int getSessionTimeoutInSeconds() {
        return this.sessionTimeoutInSeconds;
    }

    public String getSmartLoginBookmarkIconURL() {
        return this.smartLoginBookmarkIconURL;
    }

    public String getSmartLoginMenuIconURL() {
        return this.smartLoginMenuIconURL;
    }

    public EnumSet<SmartLoginOption> getSmartLoginOptions() {
        return this.smartLoginOptions;
    }

    @Nullable
    public String getSuggestedEventsSetting() {
        return this.suggestedEventsSetting;
    }

    public boolean getTrackUninstallEnabled() {
        return this.trackUninstallEnabled;
    }

    public boolean supportsImplicitLogging() {
        return this.supportsImplicitLogging;
    }
}
