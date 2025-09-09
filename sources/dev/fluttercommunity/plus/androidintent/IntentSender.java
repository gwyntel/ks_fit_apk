package dev.fluttercommunity.plus.androidintent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class IntentSender {
    private static final String TAG = "IntentSender";

    @Nullable
    private Activity activity;

    @Nullable
    private Context applicationContext;

    public IntentSender(@Nullable Activity activity, @Nullable Context context) {
        this.activity = activity;
        this.applicationContext = context;
    }

    Intent a(String str, Integer num, String str2, Uri uri, Bundle bundle, String str3, ComponentName componentName, String str4) {
        if (this.applicationContext == null) {
            Log.wtf(TAG, "Trying to build an intent before the applicationContext was initialized.");
            return null;
        }
        Intent intent = new Intent();
        if (str != null) {
            intent.setAction(str);
        }
        if (num != null) {
            intent.addFlags(num.intValue());
        }
        if (!TextUtils.isEmpty(str2)) {
            intent.addCategory(str2);
        }
        if (uri != null && str4 == null) {
            intent.setData(uri);
        }
        if (str4 != null && uri == null) {
            intent.setType(str4);
        }
        if (str4 != null && uri != null) {
            intent.setDataAndType(uri, str4);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (!TextUtils.isEmpty(str3)) {
            intent.setPackage(str3);
            if (componentName != null) {
                intent.setComponent(componentName);
            }
        }
        return intent;
    }

    boolean b(Intent intent) {
        Context context = this.applicationContext;
        if (context != null) {
            return context.getPackageManager().resolveActivity(intent, 65536) != null;
        }
        Log.wtf(TAG, "Trying to resolve an activity before the applicationContext was initialized.");
        return false;
    }

    Map c(Intent intent) {
        Context context = this.applicationContext;
        if (context == null) {
            Log.wtf(TAG, "Trying to resolve an activity before the applicationContext was initialized.");
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity == null) {
            return null;
        }
        HashMap map = new HashMap();
        map.put("activityName", resolveInfoResolveActivity.activityInfo.name);
        map.put("packageName", resolveInfoResolveActivity.activityInfo.packageName);
        map.put("appName", resolveInfoResolveActivity.loadLabel(packageManager));
        return map;
    }

    void d(Intent intent) {
        if (this.applicationContext == null) {
            Log.wtf(TAG, "Trying to send an intent before the applicationContext was initialized.");
            return;
        }
        Log.v(TAG, "Sending intent " + intent);
        Activity activity = this.activity;
        if (activity != null) {
            activity.startActivity(intent);
        } else {
            intent.addFlags(268435456);
            this.applicationContext.startActivity(intent);
        }
    }

    void e(Intent intent) {
        if (this.applicationContext == null) {
            Log.wtf(TAG, "Trying to send an intent before the applicationContext was initialized.");
            return;
        }
        Log.v(TAG, "Sending service intent " + intent);
        Activity activity = this.activity;
        if (activity != null) {
            activity.startService(intent);
        } else {
            intent.addFlags(268435456);
            this.applicationContext.startService(intent);
        }
    }

    void f(Activity activity) {
        this.activity = activity;
    }

    void g(Context context) {
        this.applicationContext = context;
    }

    public void launchChooser(Intent intent, String str) {
        d(Intent.createChooser(intent, str));
    }

    public Intent parse(String str) throws URISyntaxException {
        return Intent.parseUri(str, 1);
    }

    public void sendBroadcast(Intent intent) {
        if (this.applicationContext == null) {
            Log.wtf(TAG, "Trying to send broadcast before the applicationContext was initialized.");
            return;
        }
        Log.v(TAG, "Sending broadcast " + intent);
        this.applicationContext.sendBroadcast(intent);
    }
}
