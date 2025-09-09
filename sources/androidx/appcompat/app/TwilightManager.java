package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.core.content.PermissionChecker;
import com.heytap.mcssdk.constant.Constants;
import java.util.Calendar;

/* loaded from: classes.dex */
class TwilightManager {
    private static final int SUNRISE = 6;
    private static final int SUNSET = 22;
    private static final String TAG = "TwilightManager";
    private static TwilightManager sInstance;
    private final Context mContext;
    private final LocationManager mLocationManager;
    private final TwilightState mTwilightState = new TwilightState();

    private static class TwilightState {

        /* renamed from: a, reason: collision with root package name */
        boolean f2048a;

        /* renamed from: b, reason: collision with root package name */
        long f2049b;

        TwilightState() {
        }
    }

    TwilightManager(Context context, LocationManager locationManager) {
        this.mContext = context;
        this.mLocationManager = locationManager;
    }

    static TwilightManager a(Context context) {
        if (sInstance == null) {
            Context applicationContext = context.getApplicationContext();
            sInstance = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
        }
        return sInstance;
    }

    @SuppressLint({"MissingPermission"})
    private Location getLastKnownLocation() {
        Location lastKnownLocationForProvider = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? getLastKnownLocationForProvider("network") : null;
        Location lastKnownLocationForProvider2 = PermissionChecker.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 ? getLastKnownLocationForProvider("gps") : null;
        return (lastKnownLocationForProvider2 == null || lastKnownLocationForProvider == null) ? lastKnownLocationForProvider2 != null ? lastKnownLocationForProvider2 : lastKnownLocationForProvider : lastKnownLocationForProvider2.getTime() > lastKnownLocationForProvider.getTime() ? lastKnownLocationForProvider2 : lastKnownLocationForProvider;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    private Location getLastKnownLocationForProvider(String str) {
        try {
            if (this.mLocationManager.isProviderEnabled(str)) {
                return this.mLocationManager.getLastKnownLocation(str);
            }
            return null;
        } catch (Exception e2) {
            Log.d(TAG, "Failed to get last known location", e2);
            return null;
        }
    }

    private boolean isStateValid() {
        return this.mTwilightState.f2049b > System.currentTimeMillis();
    }

    private void updateState(@NonNull Location location) {
        long j2;
        TwilightState twilightState = this.mTwilightState;
        long jCurrentTimeMillis = System.currentTimeMillis();
        TwilightCalculator twilightCalculatorA = TwilightCalculator.a();
        twilightCalculatorA.calculateTwilight(jCurrentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        twilightCalculatorA.calculateTwilight(jCurrentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z2 = twilightCalculatorA.state == 1;
        long j3 = twilightCalculatorA.sunrise;
        long j4 = twilightCalculatorA.sunset;
        twilightCalculatorA.calculateTwilight(jCurrentTimeMillis + 86400000, location.getLatitude(), location.getLongitude());
        long j5 = twilightCalculatorA.sunrise;
        if (j3 == -1 || j4 == -1) {
            j2 = jCurrentTimeMillis + Constants.MILLS_OF_LAUNCH_INTERVAL;
        } else {
            if (jCurrentTimeMillis <= j4) {
                j5 = jCurrentTimeMillis > j3 ? j4 : j3;
            }
            j2 = j5 + 60000;
        }
        twilightState.f2048a = z2;
        twilightState.f2049b = j2;
    }

    boolean b() {
        TwilightState twilightState = this.mTwilightState;
        if (isStateValid()) {
            return twilightState.f2048a;
        }
        Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation != null) {
            updateState(lastKnownLocation);
            return twilightState.f2048a;
        }
        Log.i(TAG, "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i2 = Calendar.getInstance().get(11);
        return i2 < 6 || i2 >= 22;
    }
}
