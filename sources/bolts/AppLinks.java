package bolts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes2.dex */
public final class AppLinks {
    public static Bundle getAppLinkData(Intent intent) {
        return intent.getBundleExtra("al_applink_data");
    }

    public static Bundle getAppLinkExtras(Intent intent) {
        Bundle appLinkData = getAppLinkData(intent);
        if (appLinkData == null) {
            return null;
        }
        return appLinkData.getBundle("extras");
    }

    public static Uri getTargetUrl(Intent intent) {
        String string;
        Bundle appLinkData = getAppLinkData(intent);
        return (appLinkData == null || (string = appLinkData.getString("target_url")) == null) ? intent.getData() : Uri.parse(string);
    }

    public static Uri getTargetUrlFromInboundIntent(Context context, Intent intent) {
        String string;
        Bundle appLinkData = getAppLinkData(intent);
        if (appLinkData == null || (string = appLinkData.getString("target_url")) == null) {
            return null;
        }
        MeasurementEvent.a(context, MeasurementEvent.APP_LINK_NAVIGATE_IN_EVENT_NAME, intent, null);
        return Uri.parse(string);
    }
}
