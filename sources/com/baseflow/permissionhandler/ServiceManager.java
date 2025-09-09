package com.baseflow.permissionhandler;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.List;

/* loaded from: classes3.dex */
final class ServiceManager {

    @FunctionalInterface
    interface SuccessCallback {
        void onSuccess(int i2);
    }

    ServiceManager() {
    }

    private List<ResolveInfo> getCallAppsList(PackageManager packageManager) {
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:123123"));
        return Build.VERSION.SDK_INT >= 33 ? packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0L)) : packageManager.queryIntentActivities(intent, 0);
    }

    private boolean isBluetoothServiceEnabled(Context context) {
        return ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter().isEnabled();
    }

    private static boolean isLocationServiceEnablePreKitKat(Context context) {
        return false;
    }

    private boolean isLocationServiceEnabled(Context context) {
        if (Build.VERSION.SDK_INT < 28) {
            return isLocationServiceEnabledKitKat(context);
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(LocationManager.class);
        if (locationManager == null) {
            return false;
        }
        return locationManager.isLocationEnabled();
    }

    private static boolean isLocationServiceEnabledKitKat(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    void a(int i2, Context context, SuccessCallback successCallback, ErrorCallback errorCallback) {
        if (context == null) {
            Log.d("permissions_handler", "Context cannot be null.");
            errorCallback.onError("PermissionHandler.ServiceManager", "Android context cannot be null.");
            return;
        }
        if (i2 == 3 || i2 == 4 || i2 == 5) {
            successCallback.onSuccess(isLocationServiceEnabled(context) ? 1 : 0);
            return;
        }
        if (i2 == 21) {
            successCallback.onSuccess(isBluetoothServiceEnabled(context) ? 1 : 0);
            return;
        }
        if (i2 != 8) {
            if (i2 == 16) {
                successCallback.onSuccess(1);
                return;
            } else {
                successCallback.onSuccess(2);
                return;
            }
        }
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature("android.hardware.telephony")) {
            successCallback.onSuccess(2);
            return;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null || telephonyManager.getPhoneType() == 0) {
            successCallback.onSuccess(2);
            return;
        }
        if (getCallAppsList(packageManager).isEmpty()) {
            successCallback.onSuccess(2);
        } else if (telephonyManager.getSimState() != 5) {
            successCallback.onSuccess(0);
        } else {
            successCallback.onSuccess(1);
        }
    }
}
