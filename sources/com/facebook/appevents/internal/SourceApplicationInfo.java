package com.facebook.appevents.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import bolts.AppLinks;
import com.facebook.FacebookSdk;

/* loaded from: classes3.dex */
class SourceApplicationInfo {
    private static final String CALL_APPLICATION_PACKAGE_KEY = "com.facebook.appevents.SourceApplicationInfo.callingApplicationPackage";
    private static final String OPENED_BY_APP_LINK_KEY = "com.facebook.appevents.SourceApplicationInfo.openedByApplink";
    private static final String SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT = "_fbSourceApplicationHasBeenSet";
    private String callingApplicationPackage;
    private boolean openedByAppLink;

    public static class Factory {
        public static SourceApplicationInfo create(Activity activity) {
            String string;
            ComponentName callingActivity = activity.getCallingActivity();
            if (callingActivity != null) {
                string = callingActivity.getPackageName();
                if (string.equals(activity.getPackageName())) {
                    return null;
                }
            } else {
                string = "";
            }
            Intent intent = activity.getIntent();
            boolean z2 = false;
            if (intent != null && !intent.getBooleanExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, false)) {
                intent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
                Bundle appLinkData = AppLinks.getAppLinkData(intent);
                if (appLinkData != null) {
                    Bundle bundle = appLinkData.getBundle("referer_app_link");
                    if (bundle != null) {
                        string = bundle.getString("package");
                    }
                    z2 = true;
                }
            }
            if (intent != null) {
                intent.putExtra(SourceApplicationInfo.SOURCE_APPLICATION_HAS_BEEN_SET_BY_THIS_INTENT, true);
            }
            return new SourceApplicationInfo(string, z2);
        }
    }

    public static void clearSavedSourceApplicationInfoFromDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.remove(CALL_APPLICATION_PACKAGE_KEY);
        editorEdit.remove(OPENED_BY_APP_LINK_KEY);
        editorEdit.apply();
    }

    public static SourceApplicationInfo getStoredSourceApplicatioInfo() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext());
        if (defaultSharedPreferences.contains(CALL_APPLICATION_PACKAGE_KEY)) {
            return new SourceApplicationInfo(defaultSharedPreferences.getString(CALL_APPLICATION_PACKAGE_KEY, null), defaultSharedPreferences.getBoolean(OPENED_BY_APP_LINK_KEY, false));
        }
        return null;
    }

    public String getCallingApplicationPackage() {
        return this.callingApplicationPackage;
    }

    public boolean isOpenedByAppLink() {
        return this.openedByAppLink;
    }

    public String toString() {
        String str = this.openedByAppLink ? "Applink" : "Unclassified";
        if (this.callingApplicationPackage == null) {
            return str;
        }
        return str + "(" + this.callingApplicationPackage + ")";
    }

    public void writeSourceApplicationInfoToDisk() {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(FacebookSdk.getApplicationContext()).edit();
        editorEdit.putString(CALL_APPLICATION_PACKAGE_KEY, this.callingApplicationPackage);
        editorEdit.putBoolean(OPENED_BY_APP_LINK_KEY, this.openedByAppLink);
        editorEdit.apply();
    }

    private SourceApplicationInfo(String str, boolean z2) {
        this.callingApplicationPackage = str;
        this.openedByAppLink = z2;
    }
}
