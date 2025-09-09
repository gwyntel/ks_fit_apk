package com.facebook.appevents.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.appevents.InternalAppEventsLogger;
import com.facebook.internal.Logger;
import com.facebook.internal.security.CertificateUtil;
import java.util.Locale;

/* loaded from: classes3.dex */
class SessionLogger {
    private static final long[] INACTIVE_SECONDS_QUANTA = {300000, com.heytap.mcssdk.constant.Constants.MILLS_OF_CONNECT_SUCCESS, 1800000, 3600000, 21600000, com.heytap.mcssdk.constant.Constants.MILLS_OF_LAUNCH_INTERVAL, 86400000, 172800000, 259200000, 604800000, 1209600000, 1814400000, 2419200000L, 5184000000L, 7776000000L, 10368000000L, 12960000000L, 15552000000L, 31536000000L};
    private static final String PACKAGE_CHECKSUM = "PCKGCHKSUM";
    private static final String TAG = "com.facebook.appevents.internal.SessionLogger";

    SessionLogger() {
    }

    @Nullable
    private static String computePackageChecksum(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String str = "PCKGCHKSUM;" + packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
            SharedPreferences sharedPreferences = context.getSharedPreferences(FacebookSdk.APP_EVENT_PREFERENCES, 0);
            String string = sharedPreferences.getString(str, null);
            if (string != null && string.length() == 32) {
                return string;
            }
            String strComputeChecksum = HashUtils.computeChecksum(packageManager.getApplicationInfo(context.getPackageName(), 0).sourceDir);
            sharedPreferences.edit().putString(str, strComputeChecksum).apply();
            return strComputeChecksum;
        } catch (Exception unused) {
            return null;
        }
    }

    private static int getQuantaIndex(long j2) {
        int i2 = 0;
        while (true) {
            long[] jArr = INACTIVE_SECONDS_QUANTA;
            if (i2 >= jArr.length || jArr[i2] >= j2) {
                break;
            }
            i2++;
        }
        return i2;
    }

    public static void logActivateApp(String str, SourceApplicationInfo sourceApplicationInfo, String str2, Context context) {
        String string = sourceApplicationInfo != null ? sourceApplicationInfo.toString() : "Unclassified";
        Bundle bundle = new Bundle();
        bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, string);
        bundle.putString(AppEventsConstants.EVENT_PARAM_PACKAGE_FP, computePackageChecksum(context));
        bundle.putString(AppEventsConstants.EVENT_PARAM_APP_CERT_HASH, CertificateUtil.getCertificateHash(context));
        InternalAppEventsLogger internalAppEventsLogger = new InternalAppEventsLogger(str, str2, null);
        internalAppEventsLogger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP, bundle);
        if (InternalAppEventsLogger.getFlushBehavior() != AppEventsLogger.FlushBehavior.EXPLICIT_ONLY) {
            internalAppEventsLogger.flush();
        }
    }

    private static void logClockSkewEvent() {
        Logger.log(LoggingBehavior.APP_EVENTS, TAG, "Clock skew detected");
    }

    public static void logDeactivateApp(String str, SessionInfo sessionInfo, String str2) {
        long diskRestoreTime = sessionInfo.getDiskRestoreTime() - sessionInfo.getSessionLastEventTime().longValue();
        Long lValueOf = Long.valueOf(diskRestoreTime);
        Long l2 = 0L;
        if (diskRestoreTime < 0) {
            logClockSkewEvent();
            lValueOf = l2;
        }
        long sessionLength = sessionInfo.getSessionLength();
        Long lValueOf2 = Long.valueOf(sessionLength);
        if (sessionLength < 0) {
            logClockSkewEvent();
        } else {
            l2 = lValueOf2;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(AppEventsConstants.EVENT_NAME_SESSION_INTERRUPTIONS, sessionInfo.getInterruptionCount());
        bundle.putString(AppEventsConstants.EVENT_NAME_TIME_BETWEEN_SESSIONS, String.format(Locale.ROOT, "session_quanta_%d", Integer.valueOf(getQuantaIndex(lValueOf.longValue()))));
        SourceApplicationInfo sourceApplicationInfo = sessionInfo.getSourceApplicationInfo();
        bundle.putString(AppEventsConstants.EVENT_PARAM_SOURCE_APPLICATION, sourceApplicationInfo != null ? sourceApplicationInfo.toString() : "Unclassified");
        bundle.putLong(Constants.LOG_TIME_APP_EVENT_KEY, sessionInfo.getSessionLastEventTime().longValue() / 1000);
        new InternalAppEventsLogger(str, str2, null).logEvent(AppEventsConstants.EVENT_NAME_DEACTIVATED_APP, l2.longValue() / 1000.0d, bundle);
    }
}
