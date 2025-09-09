package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.health.connect.client.records.OvulationTestRecord;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.TransportFactory;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.connector.AnalyticsConnector;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.Constants;
import com.google.firebase.messaging.reporting.MessagingClientEvent;
import com.google.firebase.messaging.reporting.MessagingClientEventExtension;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public class MessagingAnalytics {
    private static final String DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF = "export_to_big_query";
    private static final String FCM_PREFERENCES = "com.google.firebase.messaging";
    private static final String MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED = "delivery_metrics_exported_to_big_query_enabled";
    private static final String REENGAGEMENT_MEDIUM = "notification";
    private static final String REENGAGEMENT_SOURCE = "Firebase";

    static boolean a() {
        ApplicationInfo applicationInfo;
        Bundle bundle;
        try {
            FirebaseApp.getInstance();
            Context applicationContext = FirebaseApp.getInstance().getApplicationContext();
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("com.google.firebase.messaging", 0);
            if (sharedPreferences.contains(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF)) {
                return sharedPreferences.getBoolean(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF, false);
            }
            try {
                PackageManager packageManager = applicationContext.getPackageManager();
                if (packageManager != null && (applicationInfo = packageManager.getApplicationInfo(applicationContext.getPackageName(), 128)) != null && (bundle = applicationInfo.metaData) != null && bundle.containsKey(MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED)) {
                    return applicationInfo.metaData.getBoolean(MANIFEST_DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_ENABLED, false);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
            return false;
        } catch (IllegalStateException unused2) {
            Log.i(Constants.TAG, "FirebaseApp has not being initialized. Device might be in direct boot mode. Skip exporting delivery metrics to Big Query");
            return false;
        }
    }

    static MessagingClientEvent b(MessagingClientEvent.Event event, Intent intent) {
        if (intent == null) {
            return null;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            extras = Bundle.EMPTY;
        }
        MessagingClientEvent.Builder messageType = MessagingClientEvent.newBuilder().setTtl(p(extras)).setEvent(event).setInstanceId(f(extras)).setPackageName(m()).setSdkPlatform(MessagingClientEvent.SDKPlatform.ANDROID).setMessageType(k(extras));
        String strH = h(extras);
        if (strH != null) {
            messageType.setMessageId(strH);
        }
        String strO = o(extras);
        if (strO != null) {
            messageType.setTopic(strO);
        }
        String strC = c(extras);
        if (strC != null) {
            messageType.setCollapseKey(strC);
        }
        String strI = i(extras);
        if (strI != null) {
            messageType.setAnalyticsLabel(strI);
        }
        String strE = e(extras);
        if (strE != null) {
            messageType.setComposerLabel(strE);
        }
        long jN = n(extras);
        if (jN > 0) {
            messageType.setProjectNumber(jN);
        }
        return messageType.build();
    }

    static String c(Bundle bundle) {
        return bundle.getString(Constants.MessagePayloadKeys.COLLAPSE_KEY);
    }

    static String d(Bundle bundle) {
        return bundle.getString(Constants.AnalyticsKeys.COMPOSER_ID);
    }

    static String e(Bundle bundle) {
        return bundle.getString(Constants.AnalyticsKeys.COMPOSER_LABEL);
    }

    static String f(Bundle bundle) {
        String string = bundle.getString(Constants.MessagePayloadKeys.TO);
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        try {
            return (String) Tasks.await(FirebaseInstallations.getInstance(FirebaseApp.getInstance()).getId());
        } catch (InterruptedException | ExecutionException e2) {
            throw new RuntimeException(e2);
        }
    }

    static String g(Bundle bundle) {
        return bundle.getString(Constants.AnalyticsKeys.MESSAGE_CHANNEL);
    }

    @NonNull
    private static int getMessagePriority(String str) {
        if (OvulationTestRecord.Result.HIGH.equals(str)) {
            return 1;
        }
        return "normal".equals(str) ? 2 : 0;
    }

    static String h(Bundle bundle) {
        String string = bundle.getString(Constants.MessagePayloadKeys.MSGID);
        return string == null ? bundle.getString(Constants.MessagePayloadKeys.MSGID_SERVER) : string;
    }

    static String i(Bundle bundle) {
        return bundle.getString(Constants.AnalyticsKeys.MESSAGE_LABEL);
    }

    private static boolean isDirectBootMessage(Intent intent) {
        return FirebaseMessagingService.ACTION_DIRECT_BOOT_REMOTE_INTENT.equals(intent.getAction());
    }

    static String j(Bundle bundle) {
        return bundle.getString(Constants.AnalyticsKeys.MESSAGE_TIMESTAMP);
    }

    static MessagingClientEvent.MessageType k(Bundle bundle) {
        return (bundle == null || !NotificationParams.isNotification(bundle)) ? MessagingClientEvent.MessageType.DATA_MESSAGE : MessagingClientEvent.MessageType.DISPLAY_NOTIFICATION;
    }

    static String l(Bundle bundle) {
        return (bundle == null || !NotificationParams.isNotification(bundle)) ? "data" : "display";
    }

    public static void logNotificationDismiss(Intent intent) {
        r(Constants.ScionAnalytics.EVENT_NOTIFICATION_DISMISS, intent.getExtras());
    }

    public static void logNotificationForeground(Intent intent) {
        r(Constants.ScionAnalytics.EVENT_NOTIFICATION_FOREGROUND, intent.getExtras());
    }

    public static void logNotificationOpen(Bundle bundle) {
        setUserPropertyIfRequired(bundle);
        r(Constants.ScionAnalytics.EVENT_NOTIFICATION_OPEN, bundle);
    }

    public static void logNotificationReceived(Intent intent) {
        if (shouldUploadScionMetrics(intent)) {
            r(Constants.ScionAnalytics.EVENT_NOTIFICATION_RECEIVE, intent.getExtras());
        }
        if (shouldUploadFirelogAnalytics(intent)) {
            logToFirelog(MessagingClientEvent.Event.MESSAGE_DELIVERED, intent, FirebaseMessaging.getTransportFactory());
        }
    }

    private static void logToFirelog(MessagingClientEvent.Event event, Intent intent, @Nullable TransportFactory transportFactory) {
        if (transportFactory == null) {
            Log.e(Constants.TAG, "TransportFactory is null. Skip exporting message delivery metrics to Big Query");
            return;
        }
        MessagingClientEvent messagingClientEventB = b(event, intent);
        if (messagingClientEventB == null) {
            return;
        }
        try {
            transportFactory.getTransport(Constants.FirelogAnalytics.FCM_LOG_SOURCE, MessagingClientEventExtension.class, Encoding.of("proto"), new Transformer() { // from class: com.google.firebase.messaging.y
                @Override // com.google.android.datatransport.Transformer
                public final Object apply(Object obj) {
                    return ((MessagingClientEventExtension) obj).toByteArray();
                }
            }).send(Event.ofData(MessagingClientEventExtension.newBuilder().setMessagingClientEvent(messagingClientEventB).build()));
        } catch (RuntimeException e2) {
            Log.w(Constants.TAG, "Failed to send big query analytics payload.", e2);
        }
    }

    static String m() {
        return FirebaseApp.getInstance().getApplicationContext().getPackageName();
    }

    static long n(Bundle bundle) {
        if (bundle.containsKey(Constants.MessagePayloadKeys.SENDER_ID)) {
            try {
                return Long.parseLong(bundle.getString(Constants.MessagePayloadKeys.SENDER_ID));
            } catch (NumberFormatException e2) {
                Log.w(Constants.TAG, "error parsing project number", e2);
            }
        }
        FirebaseApp firebaseApp = FirebaseApp.getInstance();
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            try {
                return Long.parseLong(gcmSenderId);
            } catch (NumberFormatException e3) {
                Log.w(Constants.TAG, "error parsing sender ID", e3);
            }
        }
        String applicationId = firebaseApp.getOptions().getApplicationId();
        if (applicationId.startsWith("1:")) {
            String[] strArrSplit = applicationId.split(":");
            if (strArrSplit.length < 2) {
                return 0L;
            }
            String str = strArrSplit[1];
            if (str.isEmpty()) {
                return 0L;
            }
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException e4) {
                Log.w(Constants.TAG, "error parsing app ID", e4);
            }
        } else {
            try {
                return Long.parseLong(applicationId);
            } catch (NumberFormatException e5) {
                Log.w(Constants.TAG, "error parsing app ID", e5);
            }
        }
        return 0L;
    }

    static String o(Bundle bundle) {
        String string = bundle.getString("from");
        if (string == null || !string.startsWith("/topics/")) {
            return null;
        }
        return string;
    }

    static int p(Bundle bundle) {
        Object obj = bundle.get(Constants.MessagePayloadKeys.TTL);
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (!(obj instanceof String)) {
            return 0;
        }
        try {
            return Integer.parseInt((String) obj);
        } catch (NumberFormatException unused) {
            Log.w(Constants.TAG, "Invalid TTL: " + obj);
            return 0;
        }
    }

    static String q(Bundle bundle) {
        if (bundle.containsKey(Constants.AnalyticsKeys.MESSAGE_USE_DEVICE_TIME)) {
            return bundle.getString(Constants.AnalyticsKeys.MESSAGE_USE_DEVICE_TIME);
        }
        return null;
    }

    static void r(String str, Bundle bundle) {
        try {
            FirebaseApp.getInstance();
            if (bundle == null) {
                bundle = new Bundle();
            }
            Bundle bundle2 = new Bundle();
            String strD = d(bundle);
            if (strD != null) {
                bundle2.putString("_nmid", strD);
            }
            String strE = e(bundle);
            if (strE != null) {
                bundle2.putString(Constants.ScionAnalytics.PARAM_MESSAGE_NAME, strE);
            }
            String strI = i(bundle);
            if (!TextUtils.isEmpty(strI)) {
                bundle2.putString(Constants.ScionAnalytics.PARAM_LABEL, strI);
            }
            String strG = g(bundle);
            if (!TextUtils.isEmpty(strG)) {
                bundle2.putString(Constants.ScionAnalytics.PARAM_MESSAGE_CHANNEL, strG);
            }
            String strO = o(bundle);
            if (strO != null) {
                bundle2.putString(Constants.ScionAnalytics.PARAM_TOPIC, strO);
            }
            String strJ = j(bundle);
            if (strJ != null) {
                try {
                    bundle2.putInt(Constants.ScionAnalytics.PARAM_MESSAGE_TIME, Integer.parseInt(strJ));
                } catch (NumberFormatException e2) {
                    Log.w(Constants.TAG, "Error while parsing timestamp in GCM event", e2);
                }
            }
            String strQ = q(bundle);
            if (strQ != null) {
                try {
                    bundle2.putInt(Constants.ScionAnalytics.PARAM_MESSAGE_DEVICE_TIME, Integer.parseInt(strQ));
                } catch (NumberFormatException e3) {
                    Log.w(Constants.TAG, "Error while parsing use_device_time in GCM event", e3);
                }
            }
            String strL = l(bundle);
            if (Constants.ScionAnalytics.EVENT_NOTIFICATION_RECEIVE.equals(str) || Constants.ScionAnalytics.EVENT_NOTIFICATION_FOREGROUND.equals(str)) {
                bundle2.putString(Constants.ScionAnalytics.PARAM_MESSAGE_TYPE, strL);
            }
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Logging to scion event=" + str + " scionPayload=" + bundle2);
            }
            AnalyticsConnector analyticsConnector = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
            if (analyticsConnector != null) {
                analyticsConnector.logEvent("fcm", str, bundle2);
            } else {
                Log.w(Constants.TAG, "Unable to log event: analytics library is missing");
            }
        } catch (IllegalStateException unused) {
            Log.e(Constants.TAG, "Default FirebaseApp has not been initialized. Skip logging event to GA.");
        }
    }

    static void s(boolean z2) {
        FirebaseApp.getInstance().getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit().putBoolean(DELIVERY_METRICS_EXPORT_TO_BIG_QUERY_PREF, z2).apply();
    }

    private static void setUserPropertyIfRequired(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (!"1".equals(bundle.getString(Constants.AnalyticsKeys.TRACK_CONVERSIONS))) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                Log.d(Constants.TAG, "Received event with track-conversion=false. Do not set user property");
                return;
            }
            return;
        }
        AnalyticsConnector analyticsConnector = (AnalyticsConnector) FirebaseApp.getInstance().get(AnalyticsConnector.class);
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "Received event with track-conversion=true. Setting user property and reengagement event");
        }
        if (analyticsConnector == null) {
            Log.w(Constants.TAG, "Unable to set user property for conversion tracking:  analytics library is missing");
            return;
        }
        String string = bundle.getString(Constants.AnalyticsKeys.COMPOSER_ID);
        analyticsConnector.setUserProperty("fcm", Constants.ScionAnalytics.USER_PROPERTY_FIREBASE_LAST_NOTIFICATION, string);
        Bundle bundle2 = new Bundle();
        bundle2.putString("source", REENGAGEMENT_SOURCE);
        bundle2.putString("medium", "notification");
        bundle2.putString("campaign", string);
        analyticsConnector.logEvent("fcm", Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundle2);
    }

    public static boolean shouldUploadFirelogAnalytics(Intent intent) {
        if (intent == null || isDirectBootMessage(intent)) {
            return false;
        }
        return a();
    }

    public static boolean shouldUploadScionMetrics(Intent intent) {
        if (intent == null || isDirectBootMessage(intent)) {
            return false;
        }
        return shouldUploadScionMetrics(intent.getExtras());
    }

    public static boolean shouldUploadScionMetrics(Bundle bundle) {
        if (bundle == null) {
            return false;
        }
        return "1".equals(bundle.getString(Constants.AnalyticsKeys.ENABLED));
    }
}
