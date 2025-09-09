package bolts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.facebook.internal.NativeProtocol;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class MeasurementEvent {
    public static final String APP_LINK_NAVIGATE_IN_EVENT_NAME = "al_nav_in";
    public static final String APP_LINK_NAVIGATE_OUT_EVENT_NAME = "al_nav_out";
    public static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    public static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    public static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private Context appContext;
    private Bundle args;
    private String name;

    private MeasurementEvent(Context context, String str, Bundle bundle) {
        this.appContext = context.getApplicationContext();
        this.name = str;
        this.args = bundle;
    }

    static void a(Context context, String str, Intent intent, Map map) {
        Bundle bundle = new Bundle();
        if (intent != null) {
            Bundle appLinkData = AppLinks.getAppLinkData(intent);
            if (appLinkData != null) {
                bundle = getApplinkLogData(context, str, appLinkData, intent);
            } else {
                Uri data = intent.getData();
                if (data != null) {
                    bundle.putString("intentData", data.toString());
                }
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str2 : extras.keySet()) {
                        bundle.putString(str2, objectToJSONString(extras.get(str2)));
                    }
                }
            }
        }
        if (map != null) {
            for (String str3 : map.keySet()) {
                bundle.putString(str3, (String) map.get(str3));
            }
        }
        new MeasurementEvent(context, str, bundle).sendBroadcast();
    }

    private static Bundle getApplinkLogData(Context context, String str, Bundle bundle, Intent intent) {
        Bundle bundle2 = new Bundle();
        ComponentName componentNameResolveActivity = intent.resolveActivity(context.getPackageManager());
        if (componentNameResolveActivity != null) {
            bundle2.putString("class", componentNameResolveActivity.getShortClassName());
        }
        if (APP_LINK_NAVIGATE_OUT_EVENT_NAME.equals(str)) {
            if (componentNameResolveActivity != null) {
                bundle2.putString("package", componentNameResolveActivity.getPackageName());
            }
            if (intent.getData() != null) {
                bundle2.putString("outputURL", intent.getData().toString());
            }
            if (intent.getScheme() != null) {
                bundle2.putString("outputURLScheme", intent.getScheme());
            }
        } else if (APP_LINK_NAVIGATE_IN_EVENT_NAME.equals(str)) {
            if (intent.getData() != null) {
                bundle2.putString("inputURL", intent.getData().toString());
            }
            if (intent.getScheme() != null) {
                bundle2.putString("inputURLScheme", intent.getScheme());
            }
        }
        for (String str2 : bundle.keySet()) {
            Object obj = bundle.get(str2);
            if (obj instanceof Bundle) {
                Bundle bundle3 = (Bundle) obj;
                for (String str3 : bundle3.keySet()) {
                    String strObjectToJSONString = objectToJSONString(bundle3.get(str3));
                    if (str2.equals("referer_app_link")) {
                        if (str3.equalsIgnoreCase("url")) {
                            bundle2.putString("refererURL", strObjectToJSONString);
                        } else if (str3.equalsIgnoreCase(NativeProtocol.BRIDGE_ARG_APP_NAME_STRING)) {
                            bundle2.putString("refererAppName", strObjectToJSONString);
                        } else if (str3.equalsIgnoreCase("package")) {
                            bundle2.putString("sourceApplication", strObjectToJSONString);
                        }
                    }
                    bundle2.putString(str2 + "/" + str3, strObjectToJSONString);
                }
            } else {
                String strObjectToJSONString2 = objectToJSONString(obj);
                if (str2.equals("target_url")) {
                    Uri uri = Uri.parse(strObjectToJSONString2);
                    bundle2.putString("targetURL", uri.toString());
                    bundle2.putString("targetURLHost", uri.getHost());
                } else {
                    bundle2.putString(str2, strObjectToJSONString2);
                }
            }
        }
        return bundle2;
    }

    private static String objectToJSONString(Object obj) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof JSONArray) || (obj instanceof JSONObject)) {
            return obj.toString();
        }
        try {
            return obj instanceof Collection ? new JSONArray((Collection) obj).toString() : obj instanceof Map ? new JSONObject((Map) obj).toString() : obj.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    private void sendBroadcast() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this.name == null) {
            Log.d(getClass().getName(), "Event name is required");
        }
        try {
            int i2 = LocalBroadcastManager.f4613a;
            Method method = LocalBroadcastManager.class.getMethod("getInstance", Context.class);
            Method method2 = LocalBroadcastManager.class.getMethod("sendBroadcast", Intent.class);
            Object objInvoke = method.invoke(null, this.appContext);
            Intent intent = new Intent(MEASUREMENT_EVENT_NOTIFICATION_NAME);
            intent.putExtra(MEASUREMENT_EVENT_NAME_KEY, this.name);
            intent.putExtra(MEASUREMENT_EVENT_ARGS_KEY, this.args);
            method2.invoke(objInvoke, intent);
        } catch (Exception unused) {
            Log.d(getClass().getName(), "LocalBroadcastManager in android support library is required to raise bolts event.");
        }
    }
}
