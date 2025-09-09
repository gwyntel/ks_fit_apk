package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.HideFirstParty;
import com.google.android.gms.common.internal.zag;
import com.google.errorprone.annotations.InlineMe;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;

/* loaded from: classes3.dex */
public final class GooglePlayServicesUtil extends GooglePlayServicesUtilLight {

    @NonNull
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";

    @NonNull
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";

    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;

    @NonNull
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Nullable
    @Deprecated
    public static Dialog getErrorDialog(int i2, @NonNull Activity activity, int i3) {
        return getErrorDialog(i2, activity, i3, null);
    }

    @NonNull
    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i2, @NonNull Context context, int i3) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(context, i2, i3);
    }

    @NonNull
    @Deprecated
    public static String getErrorString(int i2) {
        return GooglePlayServicesUtilLight.getErrorString(i2);
    }

    @NonNull
    public static Context getRemoteContext(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getRemoteContext(context);
    }

    @NonNull
    public static Resources getRemoteResource(@NonNull Context context) {
        return GooglePlayServicesUtilLight.getRemoteResource(context);
    }

    @ResultIgnorabilityUnspecified
    @HideFirstParty
    @Deprecated
    public static int isGooglePlayServicesAvailable(@NonNull Context context) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i2) {
        return GooglePlayServicesUtilLight.isUserRecoverableError(i2);
    }

    @ResultIgnorabilityUnspecified
    @Deprecated
    public static boolean showErrorDialogFragment(int i2, @NonNull Activity activity, int i3) {
        return showErrorDialogFragment(i2, activity, i3, null);
    }

    @Deprecated
    public static void showErrorNotification(int i2, @NonNull Context context) throws Resources.NotFoundException {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, i2) || GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(context, i2)) {
            googleApiAvailability.d(context);
        } else {
            googleApiAvailability.showErrorNotification(context, i2);
        }
    }

    @Nullable
    @Deprecated
    public static Dialog getErrorDialog(int i2, @NonNull Activity activity, int i3, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        if (true == GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(activity, i2)) {
            i2 = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, i2, i3, onCancelListener);
    }

    @KeepForSdk
    @Deprecated
    public static int isGooglePlayServicesAvailable(@NonNull Context context, int i2) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context, i2);
    }

    @ResultIgnorabilityUnspecified
    @InlineMe(imports = {"androidx.fragment.app.Fragment", "com.google.android.gms.common.GooglePlayServicesUtil"}, replacement = "GooglePlayServicesUtil.showErrorDialogFragment(errorCode, activity, (Fragment) null, requestCode, cancelListener)")
    @Deprecated
    public static boolean showErrorDialogFragment(int i2, @NonNull Activity activity, int i3, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        return showErrorDialogFragment(i2, activity, null, i3, onCancelListener);
    }

    @ResultIgnorabilityUnspecified
    public static boolean showErrorDialogFragment(int i2, @NonNull Activity activity, @Nullable Fragment fragment, int i3, @Nullable DialogInterface.OnCancelListener onCancelListener) {
        if (true == GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(activity, i2)) {
            i2 = 18;
        }
        int i4 = i2;
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return googleApiAvailability.showErrorDialogFragment(activity, i4, i3, onCancelListener);
        }
        Dialog dialogA = googleApiAvailability.a(activity, i4, zag.zac(fragment, GoogleApiAvailability.getInstance().getErrorResolutionIntent(activity, i4, "d"), i3), onCancelListener, null);
        if (dialogA == null) {
            return false;
        }
        googleApiAvailability.b(activity, dialogA, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }
}
