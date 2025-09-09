package com.baseflow.geolocator.location;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.location.GnssStatus;
import android.location.GnssStatus$Callback;
import android.location.Location;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Calendar;

/* loaded from: classes3.dex */
public class NmeaClient {
    public static final String GNSS_SATELLITES_USED_IN_FIX_EXTRA = "geolocator_mslSatellitesUsedInFix";
    public static final String GNSS_SATELLITE_COUNT_EXTRA = "geolocator_mslSatelliteCount";
    public static final String NMEA_ALTITUDE_EXTRA = "geolocator_mslAltitude";
    private final Context context;

    @TargetApi(24)
    private GnssStatus$Callback gnssCallback;
    private double gnss_satellite_count;
    private double gnss_satellites_used_in_fix;
    private String lastNmeaMessage;

    @Nullable
    private Calendar lastNmeaMessageTime;
    private boolean listenerAdded = false;
    private final LocationManager locationManager;

    @Nullable
    private final LocationOptions locationOptions;

    @TargetApi(24)
    private OnNmeaMessageListener nmeaMessageListener;

    public NmeaClient(@NonNull Context context, @Nullable LocationOptions locationOptions) {
        this.context = context;
        this.locationOptions = locationOptions;
        this.locationManager = (LocationManager) context.getSystemService("location");
        if (Build.VERSION.SDK_INT >= 24) {
            this.nmeaMessageListener = new OnNmeaMessageListener() { // from class: com.baseflow.geolocator.location.w
                @Override // android.location.OnNmeaMessageListener
                public final void onNmeaMessage(String str, long j2) {
                    this.f12233a.lambda$new$0(str, j2);
                }
            };
            this.gnssCallback = new GnssStatus$Callback() { // from class: com.baseflow.geolocator.location.NmeaClient.1
                public void onSatelliteStatusChanged(@NonNull GnssStatus gnssStatus) {
                    NmeaClient.this.gnss_satellite_count = gnssStatus.getSatelliteCount();
                    NmeaClient.this.gnss_satellites_used_in_fix = 0.0d;
                    for (int i2 = 0; i2 < NmeaClient.this.gnss_satellite_count; i2++) {
                        if (gnssStatus.usedInFix(i2)) {
                            NmeaClient.this.gnss_satellites_used_in_fix += 1.0d;
                        }
                    }
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str, long j2) {
        if (str.trim().matches("^\\$..GGA.*$")) {
            this.lastNmeaMessage = str;
            this.lastNmeaMessageTime = Calendar.getInstance();
        }
    }

    public void enrichExtrasWithNmea(@Nullable Location location) throws NumberFormatException {
        if (location == null) {
            return;
        }
        if (location.getExtras() == null) {
            location.setExtras(Bundle.EMPTY);
        }
        location.getExtras().putDouble(GNSS_SATELLITE_COUNT_EXTRA, this.gnss_satellite_count);
        location.getExtras().putDouble(GNSS_SATELLITES_USED_IN_FIX_EXTRA, this.gnss_satellites_used_in_fix);
        if (this.lastNmeaMessage == null || this.locationOptions == null || !this.listenerAdded) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, -5);
        Calendar calendar2 = this.lastNmeaMessageTime;
        if ((calendar2 == null || !calendar2.before(calendar)) && this.locationOptions.isUseMSLAltitude()) {
            String[] strArrSplit = this.lastNmeaMessage.split(",");
            String str = strArrSplit[0];
            if (!this.lastNmeaMessage.trim().matches("^\\$..GGA.*$") || strArrSplit.length <= 9 || strArrSplit[9].isEmpty()) {
                return;
            }
            double d2 = Double.parseDouble(strArrSplit[9]);
            if (location.getExtras() == null) {
                location.setExtras(Bundle.EMPTY);
            }
            location.getExtras().putDouble(NMEA_ALTITUDE_EXTRA, d2);
        }
    }

    @SuppressLint({"MissingPermission"})
    public void start() {
        if (this.listenerAdded || this.locationOptions == null || Build.VERSION.SDK_INT < 24 || this.locationManager == null || this.context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != 0) {
            return;
        }
        this.locationManager.addNmeaListener(this.nmeaMessageListener, (Handler) null);
        this.locationManager.registerGnssStatusCallback(this.gnssCallback, (Handler) null);
        this.listenerAdded = true;
    }

    public void stop() {
        LocationManager locationManager;
        if (this.locationOptions == null || Build.VERSION.SDK_INT < 24 || (locationManager = this.locationManager) == null) {
            return;
        }
        locationManager.removeNmeaListener(this.nmeaMessageListener);
        this.locationManager.unregisterGnssStatusCallback(this.gnssCallback);
        this.listenerAdded = false;
    }
}
