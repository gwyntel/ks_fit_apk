package com.alibaba.ailabs.tg.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.RequiresPermission;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public final class LocationUtils {
    private static final int TWO_MINUTES = 120000;
    private static OnLocationChangeListener mListener;
    private static LocationManager mLocationManager;
    private static MyLocationListener myLocationListener;

    private static class MyLocationListener implements LocationListener {
        private MyLocationListener() {
        }

        @Override // android.location.LocationListener
        public void onLocationChanged(Location location) {
            if (LocationUtils.mListener != null) {
                LocationUtils.mListener.onLocationChanged(location);
            }
        }

        @Override // android.location.LocationListener
        public void onProviderDisabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onProviderEnabled(String str) {
        }

        @Override // android.location.LocationListener
        public void onStatusChanged(String str, int i2, Bundle bundle) {
            if (LocationUtils.mListener != null) {
                LocationUtils.mListener.onStatusChanged(str, i2, bundle);
            }
            if (i2 == 0) {
                Log.d("LocationUtils", "当前GPS状态为服务区外状态");
            } else if (i2 == 1) {
                Log.d("LocationUtils", "当前GPS状态为暂停服务状态");
            } else {
                if (i2 != 2) {
                    return;
                }
                Log.d("LocationUtils", "当前GPS状态为可见状态");
            }
        }
    }

    public interface OnLocationChangeListener {
        void getLastKnownLocation(Location location);

        void onLocationChanged(Location location);

        void onStatusChanged(String str, int i2, Bundle bundle);
    }

    private LocationUtils() {
    }

    public static Address getAddress(Context context, double d2, double d3) throws IOException {
        try {
            List<Address> fromLocation = new Geocoder(context, Locale.getDefault()).getFromLocation(d2, d3, 1);
            if (fromLocation.size() > 0) {
                return fromLocation.get(0);
            }
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getCountryName(Context context, double d2, double d3) throws IOException {
        Address address = getAddress(context, d2, d3);
        return address == null ? "unknown" : address.getCountryName();
    }

    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(1);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);
        criteria.setBearingRequired(false);
        criteria.setAltitudeRequired(false);
        criteria.setPowerRequirement(1);
        return criteria;
    }

    public static String getLocality(Context context, double d2, double d3) throws IOException {
        Address address = getAddress(context, d2, d3);
        return address == null ? "unknown" : address.getLocality();
    }

    public static String getStreet(Context context, double d2, double d3) throws IOException {
        Address address = getAddress(context, d2, d3);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    public static boolean isBetterLocation(Location location, Location location2) {
        if (location2 == null) {
            return true;
        }
        long time = location.getTime() - location2.getTime();
        boolean z2 = time > 120000;
        boolean z3 = time < -120000;
        boolean z4 = time > 0;
        if (z2) {
            return true;
        }
        if (z3) {
            return false;
        }
        int accuracy = (int) (location.getAccuracy() - location2.getAccuracy());
        boolean z5 = accuracy > 0;
        boolean z6 = accuracy < 0;
        boolean z7 = accuracy > 200;
        boolean zIsSameProvider = isSameProvider(location.getProvider(), location2.getProvider());
        if (z6) {
            return true;
        }
        if (!z4 || z5) {
            return z4 && !z7 && zIsSameProvider;
        }
        return true;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager != null && locationManager.isProviderEnabled("gps");
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        return locationManager != null && (locationManager.isProviderEnabled("network") || locationManager.isProviderEnabled("gps"));
    }

    public static boolean isSameProvider(String str, String str2) {
        return str == null ? str2 == null : str.equals(str2);
    }

    public static void openGpsSettings(Context context) {
        context.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS").addFlags(268435456));
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public static boolean register(Context context, long j2, long j3, OnLocationChangeListener onLocationChangeListener) {
        if (onLocationChangeListener == null) {
            return false;
        }
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        mLocationManager = locationManager;
        if (locationManager == null || !(locationManager.isProviderEnabled("network") || mLocationManager.isProviderEnabled("gps"))) {
            Log.d("LocationUtils", "无法定位，请打开定位服务");
            return false;
        }
        mListener = onLocationChangeListener;
        String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
        Location lastKnownLocation = mLocationManager.getLastKnownLocation(bestProvider);
        if (lastKnownLocation != null) {
            onLocationChangeListener.getLastKnownLocation(lastKnownLocation);
        }
        if (myLocationListener == null) {
            myLocationListener = new MyLocationListener();
        }
        mLocationManager.requestLocationUpdates(bestProvider, j2, j3, myLocationListener);
        return true;
    }

    @SuppressLint({"MissingPermission"})
    public static void unregister() {
        LocationManager locationManager = mLocationManager;
        if (locationManager != null) {
            MyLocationListener myLocationListener2 = myLocationListener;
            if (myLocationListener2 != null) {
                locationManager.removeUpdates(myLocationListener2);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
        if (mListener != null) {
            mListener = null;
        }
    }
}
