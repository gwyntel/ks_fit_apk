package com.aliyun.iot.aep.sdk.page;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.R;
import com.aliyun.iot.link.ui.component.LinkAlertDialog;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class LocationUtil {

    /* renamed from: a, reason: collision with root package name */
    private static LocationManager f11983a;

    /* renamed from: b, reason: collision with root package name */
    private static LocationListener f11984b = new LocationListener() { // from class: com.aliyun.iot.aep.sdk.page.LocationUtil.1
        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i2, Bundle bundle) {
        }
    };

    static Address a(Context context) throws IOException {
        Location locationB = b(context);
        if (locationB != null) {
            try {
                List<Address> fromLocation = new Geocoder(context, Locale.getDefault()).getFromLocation(locationB.getLatitude(), locationB.getLongitude(), 1);
                if (fromLocation.size() > 0) {
                    return fromLocation.get(0);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        Log.i("LocationUtil", "解析获取国家码失败");
        return null;
    }

    private static Location b(Context context) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            try {
                Location lastKnownLocation = ((LocationManager) context.getSystemService("location")).getLastKnownLocation("network");
                if (lastKnownLocation != null) {
                    Log.i("LocationUtil", "获取定位信息成功");
                    return lastKnownLocation;
                }
            } catch (Exception e2) {
                Log.i("LocationUtil", "获取定位信息出错" + e2.getMessage());
            }
        } else {
            Log.i("LocationUtil", "提示无法定位,请先打开位置权限");
        }
        Log.i("LocationUtil", "获取定位信息失败");
        return null;
    }

    public static void cancelLocating() {
        LocationManager locationManager = f11983a;
        if (locationManager != null) {
            locationManager.removeUpdates(f11984b);
            f11983a = null;
        }
    }

    public static boolean isLocationEnabled(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void remindStartLocateService(final Context context) {
        new LinkAlertDialog.Builder(context).setTitle(AApplication.getInstance().getString(R.string.component_unopened_location_service)).setMessage("").setPositiveButton(AApplication.getInstance().getString(R.string.component_set_up), new LinkAlertDialog.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.LocationUtil.2
            @Override // com.aliyun.iot.link.ui.component.LinkAlertDialog.OnClickListener
            public void onClick(LinkAlertDialog linkAlertDialog) {
                context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                linkAlertDialog.dismiss();
            }
        }).setNegativeButton(AApplication.getInstance().getString(R.string.component_cancel), new LinkAlertDialog.OnClickListener() { // from class: com.aliyun.iot.aep.sdk.page.a
            @Override // com.aliyun.iot.link.ui.component.LinkAlertDialog.OnClickListener
            public final void onClick(LinkAlertDialog linkAlertDialog) {
                linkAlertDialog.dismiss();
            }
        }).create().show();
    }

    public static void requestLocation(Context context) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0 && ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            Log.i("LocationUtil", "提示无法定位,请先打开位置权限");
            return;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            f11983a = locationManager;
            locationManager.requestLocationUpdates("network", 1L, 1.0f, f11984b);
        } catch (Exception e2) {
            Log.i("LocationUtil", "请求定位信息出错" + e2.getMessage());
        }
    }
}
