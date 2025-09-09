package com.baseflow.geolocator.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.baseflow.geolocator.errors.ErrorCallback;
import com.baseflow.geolocator.errors.ErrorCodes;
import com.baseflow.geolocator.errors.PermissionUndefinedException;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PermissionManager implements PluginRegistry.RequestPermissionsResultListener {
    private static final int PERMISSION_REQUEST_CODE = 109;
    private static PermissionManager permissionManagerInstance;

    @Nullable
    private Activity activity;

    @Nullable
    private ErrorCallback errorCallback;

    @Nullable
    private PermissionResultCallback resultCallback;

    private PermissionManager() {
    }

    public static synchronized PermissionManager getInstance() {
        try {
            if (permissionManagerInstance == null) {
                permissionManagerInstance = new PermissionManager();
            }
        } catch (Throwable th) {
            throw th;
        }
        return permissionManagerInstance;
    }

    private static List<String> getLocationPermissionsFromManifest(Context context) throws PermissionUndefinedException {
        boolean zHasPermissionInManifest = PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_FINE_LOCATION");
        boolean zHasPermissionInManifest2 = PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_COARSE_LOCATION");
        if (!zHasPermissionInManifest && !zHasPermissionInManifest2) {
            throw new PermissionUndefinedException();
        }
        ArrayList arrayList = new ArrayList();
        if (zHasPermissionInManifest) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (zHasPermissionInManifest2) {
            arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
        }
        return arrayList;
    }

    @RequiresApi(api = 29)
    private boolean hasBackgroundAccess(String[] strArr, int[] iArr) {
        int iIndexOf = indexOf(strArr, "android.permission.ACCESS_BACKGROUND_LOCATION");
        return iIndexOf >= 0 && iArr[iIndexOf] == 0;
    }

    private static <T> int indexOf(T[] tArr, T t2) {
        return Arrays.asList(tArr).indexOf(t2);
    }

    public LocationPermission checkPermissionStatus(Context context) throws PermissionUndefinedException {
        Iterator<String> it = getLocationPermissionsFromManifest(context).iterator();
        while (it.hasNext()) {
            if (ContextCompat.checkSelfPermission(context, it.next()) == 0) {
                return Build.VERSION.SDK_INT < 29 ? LocationPermission.always : !PermissionUtils.hasPermissionInManifest(context, "android.permission.ACCESS_BACKGROUND_LOCATION") ? LocationPermission.whileInUse : ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_BACKGROUND_LOCATION") == 0 ? LocationPermission.always : LocationPermission.whileInUse;
            }
        }
        return LocationPermission.denied;
    }

    public boolean hasPermission(Context context) throws PermissionUndefinedException {
        LocationPermission locationPermissionCheckPermissionStatus = checkPermissionStatus(context);
        return locationPermissionCheckPermissionStatus == LocationPermission.whileInUse || locationPermissionCheckPermissionStatus == LocationPermission.always;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        if (i2 != 109) {
            return false;
        }
        Activity activity = this.activity;
        if (activity == null) {
            Log.e("Geolocator", "Trying to process permission result without an valid Activity instance");
            ErrorCallback errorCallback = this.errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(ErrorCodes.activityMissing);
            }
            return false;
        }
        try {
            List<String> locationPermissionsFromManifest = getLocationPermissionsFromManifest(activity);
            if (iArr.length == 0) {
                Log.i("Geolocator", "The grantResults array is empty. This can happen when the user cancels the permission request");
                return false;
            }
            LocationPermission locationPermission = LocationPermission.denied;
            char c2 = 65535;
            boolean z2 = false;
            boolean z3 = false;
            for (String str : locationPermissionsFromManifest) {
                int iIndexOf = indexOf(strArr, str);
                if (iIndexOf >= 0) {
                    z2 = true;
                }
                if (iArr[iIndexOf] == 0) {
                    c2 = 0;
                }
                if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, str)) {
                    z3 = true;
                }
            }
            if (!z2) {
                Log.w("Geolocator", "Location permissions not part of permissions send to onRequestPermissionsResult method.");
                return false;
            }
            if (c2 == 0) {
                locationPermission = (Build.VERSION.SDK_INT < 29 || hasBackgroundAccess(strArr, iArr)) ? LocationPermission.always : LocationPermission.whileInUse;
            } else if (!z3) {
                locationPermission = LocationPermission.deniedForever;
            }
            PermissionResultCallback permissionResultCallback = this.resultCallback;
            if (permissionResultCallback != null) {
                permissionResultCallback.onResult(locationPermission);
            }
            return true;
        } catch (PermissionUndefinedException unused) {
            ErrorCallback errorCallback2 = this.errorCallback;
            if (errorCallback2 != null) {
                errorCallback2.onError(ErrorCodes.permissionDefinitionsNotFound);
            }
            return false;
        }
    }

    public void requestPermission(Activity activity, PermissionResultCallback permissionResultCallback, ErrorCallback errorCallback) throws PermissionUndefinedException {
        if (activity == null) {
            errorCallback.onError(ErrorCodes.activityMissing);
            return;
        }
        int i2 = Build.VERSION.SDK_INT;
        List<String> locationPermissionsFromManifest = getLocationPermissionsFromManifest(activity);
        if (i2 >= 29 && PermissionUtils.hasPermissionInManifest(activity, "android.permission.ACCESS_BACKGROUND_LOCATION") && checkPermissionStatus(activity) == LocationPermission.whileInUse) {
            locationPermissionsFromManifest.add("android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        this.errorCallback = errorCallback;
        this.resultCallback = permissionResultCallback;
        this.activity = activity;
        ActivityCompat.requestPermissions(activity, (String[]) locationPermissionsFromManifest.toArray(new String[0]), 109);
    }
}
