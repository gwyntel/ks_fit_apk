package bolts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import bolts.AppLink;
import com.alipay.sdk.m.u.h;
import com.facebook.internal.AnalyticsEvents;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AppLinkNavigation {
    private static final String KEY_NAME_REFERER_APP_LINK = "referer_app_link";
    private static final String KEY_NAME_REFERER_APP_LINK_APP_NAME = "app_name";
    private static final String KEY_NAME_REFERER_APP_LINK_PACKAGE = "package";
    private static final String KEY_NAME_USER_AGENT = "user_agent";
    private static final String KEY_NAME_VERSION = "version";
    private static final String VERSION = "1.0";
    private static AppLinkResolver defaultResolver;
    private final AppLink appLink;
    private final Bundle appLinkData;
    private final Bundle extras;

    public enum NavigationResult {
        FAILED(h.f9784j, false),
        WEB(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB, true),
        APP(PushConstants.EXTRA_APPLICATION_PENDING_INTENT, true);

        private String code;
        private boolean succeeded;

        NavigationResult(String str, boolean z2) {
            this.code = str;
            this.succeeded = z2;
        }

        public String getCode() {
            return this.code;
        }

        public boolean isSucceeded() {
            return this.succeeded;
        }
    }

    public AppLinkNavigation(AppLink appLink, Bundle bundle, Bundle bundle2) {
        if (appLink == null) {
            throw new IllegalArgumentException("appLink must not be null.");
        }
        bundle = bundle == null ? new Bundle() : bundle;
        bundle2 = bundle2 == null ? new Bundle() : bundle2;
        this.appLink = appLink;
        this.extras = bundle;
        this.appLinkData = bundle2;
    }

    private Bundle buildAppLinkDataForNavigation(Context context) {
        String string;
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        if (context != null) {
            String packageName = context.getPackageName();
            if (packageName != null) {
                bundle2.putString("package", packageName);
            }
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null && (string = context.getString(applicationInfo.labelRes)) != null) {
                bundle2.putString("app_name", string);
            }
        }
        bundle.putAll(getAppLinkData());
        bundle.putString("target_url", getAppLink().getSourceUrl().toString());
        bundle.putString("version", "1.0");
        bundle.putString("user_agent", "Bolts Android 1.4.0");
        bundle.putBundle(KEY_NAME_REFERER_APP_LINK, bundle2);
        bundle.putBundle("extras", getExtras());
        return bundle;
    }

    public static AppLinkResolver getDefaultResolver() {
        return defaultResolver;
    }

    private JSONObject getJSONForBundle(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            jSONObject.put(str, getJSONValue(bundle.get(str)));
        }
        return jSONObject;
    }

    private Object getJSONValue(Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            return getJSONForBundle((Bundle) obj);
        }
        if (obj instanceof CharSequence) {
            return obj.toString();
        }
        if (obj instanceof List) {
            JSONArray jSONArray = new JSONArray();
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                jSONArray.put(getJSONValue(it.next()));
            }
            return jSONArray;
        }
        int i2 = 0;
        if (obj instanceof SparseArray) {
            JSONArray jSONArray2 = new JSONArray();
            SparseArray sparseArray = (SparseArray) obj;
            while (i2 < sparseArray.size()) {
                jSONArray2.put(sparseArray.keyAt(i2), getJSONValue(sparseArray.valueAt(i2)));
                i2++;
            }
            return jSONArray2;
        }
        if (obj instanceof Character) {
            return obj.toString();
        }
        if (obj instanceof Boolean) {
            return obj;
        }
        if (obj instanceof Number) {
            return ((obj instanceof Double) || (obj instanceof Float)) ? Double.valueOf(((Number) obj).doubleValue()) : Long.valueOf(((Number) obj).longValue());
        }
        if (obj instanceof boolean[]) {
            JSONArray jSONArray3 = new JSONArray();
            boolean[] zArr = (boolean[]) obj;
            int length = zArr.length;
            while (i2 < length) {
                jSONArray3.put(getJSONValue(Boolean.valueOf(zArr[i2])));
                i2++;
            }
            return jSONArray3;
        }
        if (obj instanceof char[]) {
            JSONArray jSONArray4 = new JSONArray();
            char[] cArr = (char[]) obj;
            int length2 = cArr.length;
            while (i2 < length2) {
                jSONArray4.put(getJSONValue(Character.valueOf(cArr[i2])));
                i2++;
            }
            return jSONArray4;
        }
        if (obj instanceof CharSequence[]) {
            JSONArray jSONArray5 = new JSONArray();
            CharSequence[] charSequenceArr = (CharSequence[]) obj;
            int length3 = charSequenceArr.length;
            while (i2 < length3) {
                jSONArray5.put(getJSONValue(charSequenceArr[i2]));
                i2++;
            }
            return jSONArray5;
        }
        if (obj instanceof double[]) {
            JSONArray jSONArray6 = new JSONArray();
            double[] dArr = (double[]) obj;
            int length4 = dArr.length;
            while (i2 < length4) {
                jSONArray6.put(getJSONValue(Double.valueOf(dArr[i2])));
                i2++;
            }
            return jSONArray6;
        }
        if (obj instanceof float[]) {
            JSONArray jSONArray7 = new JSONArray();
            float[] fArr = (float[]) obj;
            int length5 = fArr.length;
            while (i2 < length5) {
                jSONArray7.put(getJSONValue(Float.valueOf(fArr[i2])));
                i2++;
            }
            return jSONArray7;
        }
        if (obj instanceof int[]) {
            JSONArray jSONArray8 = new JSONArray();
            int[] iArr = (int[]) obj;
            int length6 = iArr.length;
            while (i2 < length6) {
                jSONArray8.put(getJSONValue(Integer.valueOf(iArr[i2])));
                i2++;
            }
            return jSONArray8;
        }
        if (obj instanceof long[]) {
            JSONArray jSONArray9 = new JSONArray();
            long[] jArr = (long[]) obj;
            int length7 = jArr.length;
            while (i2 < length7) {
                jSONArray9.put(getJSONValue(Long.valueOf(jArr[i2])));
                i2++;
            }
            return jSONArray9;
        }
        if (obj instanceof short[]) {
            JSONArray jSONArray10 = new JSONArray();
            short[] sArr = (short[]) obj;
            int length8 = sArr.length;
            while (i2 < length8) {
                jSONArray10.put(getJSONValue(Short.valueOf(sArr[i2])));
                i2++;
            }
            return jSONArray10;
        }
        if (!(obj instanceof String[])) {
            return null;
        }
        JSONArray jSONArray11 = new JSONArray();
        String[] strArr = (String[]) obj;
        int length9 = strArr.length;
        while (i2 < length9) {
            jSONArray11.put(getJSONValue(strArr[i2]));
            i2++;
        }
        return jSONArray11;
    }

    private static AppLinkResolver getResolver(Context context) {
        return getDefaultResolver() != null ? getDefaultResolver() : new WebViewAppLinkResolver(context);
    }

    public static Task<NavigationResult> navigateInBackground(final Context context, Uri uri, AppLinkResolver appLinkResolver) {
        return appLinkResolver.getAppLinkFromUrlInBackground(uri).onSuccess((Continuation<AppLink, TContinuationResult>) new Continuation<AppLink, NavigationResult>() { // from class: bolts.AppLinkNavigation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // bolts.Continuation
            public NavigationResult then(Task<AppLink> task) throws Exception {
                return AppLinkNavigation.navigate(context, task.getResult());
            }
        }, Task.UI_THREAD_EXECUTOR);
    }

    private void sendAppLinkNavigateEventBroadcast(Context context, Intent intent, NavigationResult navigationResult, JSONException jSONException) {
        HashMap map = new HashMap();
        if (jSONException != null) {
            map.put("error", jSONException.getLocalizedMessage());
        }
        map.put("success", navigationResult.isSucceeded() ? "1" : "0");
        map.put("type", navigationResult.getCode());
        MeasurementEvent.a(context, MeasurementEvent.APP_LINK_NAVIGATE_OUT_EVENT_NAME, intent, map);
    }

    public static void setDefaultResolver(AppLinkResolver appLinkResolver) {
        defaultResolver = appLinkResolver;
    }

    public AppLink getAppLink() {
        return this.appLink;
    }

    public Bundle getAppLinkData() {
        return this.appLinkData;
    }

    public Bundle getExtras() {
        return this.extras;
    }

    public NavigationResult navigate(Context context) {
        Intent intent;
        PackageManager packageManager = context.getPackageManager();
        Bundle bundleBuildAppLinkDataForNavigation = buildAppLinkDataForNavigation(context);
        Iterator<AppLink.Target> it = getAppLink().getTargets().iterator();
        while (true) {
            if (!it.hasNext()) {
                intent = null;
                break;
            }
            AppLink.Target next = it.next();
            intent = new Intent("android.intent.action.VIEW");
            if (next.getUrl() != null) {
                intent.setData(next.getUrl());
            } else {
                intent.setData(this.appLink.getSourceUrl());
            }
            intent.setPackage(next.getPackageName());
            if (next.getClassName() != null) {
                intent.setClassName(next.getPackageName(), next.getClassName());
            }
            intent.putExtra("al_applink_data", bundleBuildAppLinkDataForNavigation);
            if (packageManager.resolveActivity(intent, 65536) != null) {
                break;
            }
        }
        NavigationResult navigationResult = NavigationResult.FAILED;
        if (intent != null) {
            navigationResult = NavigationResult.APP;
        } else {
            Uri webUrl = getAppLink().getWebUrl();
            if (webUrl != null) {
                try {
                    intent = new Intent("android.intent.action.VIEW", webUrl.buildUpon().appendQueryParameter("al_applink_data", getJSONForBundle(bundleBuildAppLinkDataForNavigation).toString()).build());
                    navigationResult = NavigationResult.WEB;
                } catch (JSONException e2) {
                    sendAppLinkNavigateEventBroadcast(context, intent, NavigationResult.FAILED, e2);
                    throw new RuntimeException(e2);
                }
            } else {
                intent = null;
            }
        }
        sendAppLinkNavigateEventBroadcast(context, intent, navigationResult, null);
        if (intent != null) {
            context.startActivity(intent);
        }
        return navigationResult;
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(url.toString()), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str, AppLinkResolver appLinkResolver) {
        return navigateInBackground(context, Uri.parse(str), appLinkResolver);
    }

    public static Task<NavigationResult> navigateInBackground(Context context, Uri uri) {
        return navigateInBackground(context, uri, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, URL url) {
        return navigateInBackground(context, url, getResolver(context));
    }

    public static Task<NavigationResult> navigateInBackground(Context context, String str) {
        return navigateInBackground(context, str, getResolver(context));
    }

    public static NavigationResult navigate(Context context, AppLink appLink) {
        return new AppLinkNavigation(appLink, null, null).navigate(context);
    }
}
