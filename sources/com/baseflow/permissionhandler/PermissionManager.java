package com.baseflow.permissionhandler;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.kingsmith.miot.KsProperty;
import com.luck.picture.lib.permissions.PermissionConfig;
import io.flutter.plugin.common.PluginRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class PermissionManager implements PluginRegistry.ActivityResultListener, PluginRegistry.RequestPermissionsResultListener {

    @Nullable
    private Activity activity;

    @NonNull
    private final Context context;
    private int pendingRequestCount;
    private Map<Integer, Integer> requestResults;

    @Nullable
    private RequestPermissionsSuccessCallback successCallback;

    @FunctionalInterface
    interface CheckPermissionsSuccessCallback {
        void onSuccess(int i2);
    }

    @FunctionalInterface
    interface RequestPermissionsSuccessCallback {
        void onSuccess(Map<Integer, Integer> map);
    }

    @FunctionalInterface
    interface ShouldShowRequestPermissionRationaleSuccessCallback {
        void onSuccess(boolean z2);
    }

    public PermissionManager(@NonNull Context context) {
        this.context = context;
    }

    private int checkBluetoothPermissionStatus() {
        List listB = PermissionUtils.b(this.context, 21);
        if (listB != null && !listB.isEmpty()) {
            return 1;
        }
        Log.d("permissions_handler", "Bluetooth permission missing in manifest");
        return 0;
    }

    private int checkNotificationPermissionStatus() {
        if (Build.VERSION.SDK_INT < 33) {
            return NotificationManagerCompat.from(this.context).areNotificationsEnabled() ? 1 : 0;
        }
        if (this.context.checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0) {
            return 1;
        }
        return PermissionUtils.a(this.activity, "android.permission.POST_NOTIFICATIONS");
    }

    private int determinePermissionStatus(int i2) {
        if (i2 == 17) {
            return checkNotificationPermissionStatus();
        }
        if (i2 == 21) {
            return checkBluetoothPermissionStatus();
        }
        if ((i2 == 30 || i2 == 28 || i2 == 29) && Build.VERSION.SDK_INT < 31) {
            return checkBluetoothPermissionStatus();
        }
        if ((i2 == 37 || i2 == 0) && !isValidManifestForCalendarFullAccess()) {
            return 0;
        }
        List<String> listB = PermissionUtils.b(this.context, i2);
        if (listB == null) {
            Log.d("permissions_handler", "No android specific permissions needed for: " + i2);
            return 1;
        }
        if (listB.size() == 0) {
            Log.d("permissions_handler", "No permissions found in manifest for: " + listB + i2);
            return (i2 != 22 || Build.VERSION.SDK_INT >= 30) ? 0 : 2;
        }
        if (this.context.getApplicationInfo().targetSdkVersion >= 23) {
            HashSet hashSet = new HashSet();
            for (String str : listB) {
                if (i2 == 16) {
                    String packageName = this.context.getPackageName();
                    PowerManager powerManager = (PowerManager) this.context.getSystemService(KsProperty.Power);
                    if (powerManager == null || !powerManager.isIgnoringBatteryOptimizations(packageName)) {
                        hashSet.add(0);
                    } else {
                        hashSet.add(1);
                    }
                } else if (i2 == 22) {
                    if (Build.VERSION.SDK_INT < 30) {
                        hashSet.add(2);
                    }
                    hashSet.add(Integer.valueOf(Environment.isExternalStorageManager() ? 1 : 0));
                } else if (i2 == 23) {
                    hashSet.add(Integer.valueOf(Settings.canDrawOverlays(this.context) ? 1 : 0));
                } else if (i2 == 24) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        hashSet.add(Integer.valueOf(this.context.getPackageManager().canRequestPackageInstalls() ? 1 : 0));
                    }
                } else if (i2 == 27) {
                    hashSet.add(Integer.valueOf(((NotificationManager) this.context.getSystemService("notification")).isNotificationPolicyAccessGranted() ? 1 : 0));
                } else if (i2 == 34) {
                    if (Build.VERSION.SDK_INT >= 31) {
                        hashSet.add(Integer.valueOf(((AlarmManager) this.context.getSystemService(NotificationCompat.CATEGORY_ALARM)).canScheduleExactAlarms() ? 1 : 0));
                    } else {
                        hashSet.add(1);
                    }
                } else if (i2 == 9 || i2 == 32) {
                    int iCheckSelfPermission = ContextCompat.checkSelfPermission(this.context, str);
                    if ((Build.VERSION.SDK_INT >= 34 ? ContextCompat.checkSelfPermission(this.context, PermissionConfig.READ_MEDIA_VISUAL_USER_SELECTED) : iCheckSelfPermission) == 0 && iCheckSelfPermission == -1) {
                        hashSet.add(3);
                    } else if (iCheckSelfPermission == 0) {
                        hashSet.add(1);
                    } else {
                        hashSet.add(Integer.valueOf(PermissionUtils.a(this.activity, str)));
                    }
                } else if (ContextCompat.checkSelfPermission(this.context, str) != 0) {
                    hashSet.add(Integer.valueOf(PermissionUtils.a(this.activity, str)));
                }
            }
            if (!hashSet.isEmpty()) {
                return PermissionUtils.f(hashSet).intValue();
            }
        }
        return 1;
    }

    private boolean isValidManifestForCalendarFullAccess() {
        List listB = PermissionUtils.b(this.context, 37);
        boolean z2 = listB != null && listB.contains("android.permission.WRITE_CALENDAR");
        boolean z3 = listB != null && listB.contains("android.permission.READ_CALENDAR");
        if (z2 && z3) {
            return true;
        }
        if (!z2) {
            Log.d("permissions_handler", "android.permission.WRITE_CALENDAR missing in manifest");
        }
        if (!z3) {
            Log.d("permissions_handler", "android.permission.READ_CALENDAR missing in manifest");
        }
        return false;
    }

    private void launchSpecialPermission(String str, int i2) {
        if (this.activity == null) {
            return;
        }
        Intent intent = new Intent(str);
        if (!str.equals("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS")) {
            intent.setData(Uri.parse("package:" + this.activity.getPackageName()));
        }
        this.activity.startActivityForResult(intent, i2);
        this.pendingRequestCount++;
    }

    void a(int i2, CheckPermissionsSuccessCallback checkPermissionsSuccessCallback) {
        checkPermissionsSuccessCallback.onSuccess(determinePermissionStatus(i2));
    }

    void b(List list, RequestPermissionsSuccessCallback requestPermissionsSuccessCallback, ErrorCallback errorCallback) {
        if (this.pendingRequestCount > 0) {
            errorCallback.onError("PermissionHandler.PermissionManager", "A request for permissions is already running, please wait for it to finish before doing another request (note that you can request multiple permissions at the same time).");
            return;
        }
        if (this.activity == null) {
            Log.d("permissions_handler", "Unable to detect current Activity.");
            errorCallback.onError("PermissionHandler.PermissionManager", "Unable to detect current Android Activity.");
            return;
        }
        this.successCallback = requestPermissionsSuccessCallback;
        this.requestResults = new HashMap();
        this.pendingRequestCount = 0;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (determinePermissionStatus(num.intValue()) != 1) {
                List listB = PermissionUtils.b(this.activity, num.intValue());
                if (listB != null && !listB.isEmpty()) {
                    int i2 = Build.VERSION.SDK_INT;
                    if (num.intValue() == 16) {
                        launchSpecialPermission("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS", 209);
                    } else if (i2 >= 30 && num.intValue() == 22) {
                        launchSpecialPermission("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION", 210);
                    } else if (num.intValue() == 23) {
                        launchSpecialPermission("android.settings.action.MANAGE_OVERLAY_PERMISSION", 211);
                    } else if (i2 >= 26 && num.intValue() == 24) {
                        launchSpecialPermission("android.settings.MANAGE_UNKNOWN_APP_SOURCES", 212);
                    } else if (num.intValue() == 27) {
                        launchSpecialPermission("android.settings.NOTIFICATION_POLICY_ACCESS_SETTINGS", 213);
                    } else if (i2 >= 31 && num.intValue() == 34) {
                        launchSpecialPermission("android.settings.REQUEST_SCHEDULE_EXACT_ALARM", 214);
                    } else if (num.intValue() != 37 && num.intValue() != 0) {
                        arrayList.addAll(listB);
                        this.pendingRequestCount += listB.size();
                    } else if (isValidManifestForCalendarFullAccess()) {
                        arrayList.add("android.permission.WRITE_CALENDAR");
                        arrayList.add("android.permission.READ_CALENDAR");
                        this.pendingRequestCount += 2;
                    } else {
                        this.requestResults.put(num, 0);
                    }
                } else if (!this.requestResults.containsKey(num)) {
                    this.requestResults.put(num, 0);
                    if (num.intValue() != 22 || Build.VERSION.SDK_INT >= 30) {
                        this.requestResults.put(num, 0);
                    } else {
                        this.requestResults.put(num, 2);
                    }
                }
            } else if (!this.requestResults.containsKey(num)) {
                this.requestResults.put(num, 1);
            }
        }
        if (arrayList.size() > 0) {
            ActivityCompat.requestPermissions(this.activity, (String[]) arrayList.toArray(new String[0]), 24);
        }
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback2 = this.successCallback;
        if (requestPermissionsSuccessCallback2 == null || this.pendingRequestCount != 0) {
            return;
        }
        requestPermissionsSuccessCallback2.onSuccess(this.requestResults);
    }

    void c(int i2, ShouldShowRequestPermissionRationaleSuccessCallback shouldShowRequestPermissionRationaleSuccessCallback, ErrorCallback errorCallback) {
        Activity activity = this.activity;
        if (activity == null) {
            Log.d("permissions_handler", "Unable to detect current Activity.");
            errorCallback.onError("PermissionHandler.PermissionManager", "Unable to detect current Android Activity.");
            return;
        }
        List listB = PermissionUtils.b(activity, i2);
        if (listB == null) {
            Log.d("permissions_handler", "No android specific permissions needed for: " + i2);
            shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(false);
            return;
        }
        if (!listB.isEmpty()) {
            shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(ActivityCompat.shouldShowRequestPermissionRationale(this.activity, (String) listB.get(0)));
            return;
        }
        Log.d("permissions_handler", "No permissions found in manifest for: " + i2 + " no need to show request rationale");
        shouldShowRequestPermissionRationaleSuccessCallback.onSuccess(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // io.flutter.plugin.common.PluginRegistry.ActivityResultListener
    public boolean onActivityResult(int i2, int i3, Intent intent) {
        int i4;
        int iCanScheduleExactAlarms;
        Activity activity = this.activity;
        boolean z2 = false;
        z2 = false;
        if (activity == null) {
            return false;
        }
        if (this.requestResults == null) {
            this.pendingRequestCount = 0;
            return false;
        }
        if (i2 == 209) {
            String packageName = this.context.getPackageName();
            PowerManager powerManager = (PowerManager) this.context.getSystemService(KsProperty.Power);
            if (powerManager != null && powerManager.isIgnoringBatteryOptimizations(packageName)) {
                z2 = true;
            }
            i4 = 16;
            iCanScheduleExactAlarms = z2;
        } else if (i2 == 210) {
            if (Build.VERSION.SDK_INT < 30) {
                return false;
            }
            i4 = 22;
            iCanScheduleExactAlarms = Environment.isExternalStorageManager();
        } else if (i2 == 211) {
            i4 = 23;
            iCanScheduleExactAlarms = Settings.canDrawOverlays(activity);
        } else if (i2 == 212) {
            if (Build.VERSION.SDK_INT < 26) {
                return false;
            }
            i4 = 24;
            iCanScheduleExactAlarms = activity.getPackageManager().canRequestPackageInstalls();
        } else if (i2 == 213) {
            i4 = 27;
            iCanScheduleExactAlarms = ((NotificationManager) activity.getSystemService("notification")).isNotificationPolicyAccessGranted();
        } else {
            if (i2 != 214) {
                return false;
            }
            i4 = 34;
            iCanScheduleExactAlarms = Build.VERSION.SDK_INT >= 31 ? ((AlarmManager) activity.getSystemService(NotificationCompat.CATEGORY_ALARM)).canScheduleExactAlarms() : true;
        }
        this.requestResults.put(Integer.valueOf(i4), Integer.valueOf(iCanScheduleExactAlarms));
        int i5 = this.pendingRequestCount - 1;
        this.pendingRequestCount = i5;
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback = this.successCallback;
        if (requestPermissionsSuccessCallback != null && i5 == 0) {
            requestPermissionsSuccessCallback.onSuccess(this.requestResults);
        }
        return true;
    }

    @Override // io.flutter.plugin.common.PluginRegistry.RequestPermissionsResultListener
    public boolean onRequestPermissionsResult(int i2, @NonNull String[] strArr, @NonNull int[] iArr) {
        int iD;
        if (i2 != 24) {
            this.pendingRequestCount = 0;
            return false;
        }
        if (this.requestResults == null) {
            return false;
        }
        if (strArr.length == 0 && iArr.length == 0) {
            Log.w("permissions_handler", "onRequestPermissionsResult is called without results. This is probably caused by interfering request codes. If you see this error, please file an issue in flutter-permission-handler, including a list of plugins used by this application: https://github.com/Baseflow/flutter-permission-handler/issues");
            return false;
        }
        List listAsList = Arrays.asList(strArr);
        int iIndexOf = listAsList.indexOf("android.permission.WRITE_CALENDAR");
        if (iIndexOf >= 0) {
            int iG = PermissionUtils.g(this.activity, "android.permission.WRITE_CALENDAR", iArr[iIndexOf]);
            this.requestResults.put(36, Integer.valueOf(iG));
            int iIndexOf2 = listAsList.indexOf("android.permission.READ_CALENDAR");
            if (iIndexOf2 >= 0) {
                Integer numE = PermissionUtils.e(Integer.valueOf(iG), Integer.valueOf(PermissionUtils.g(this.activity, "android.permission.READ_CALENDAR", iArr[iIndexOf2])));
                numE.intValue();
                this.requestResults.put(37, numE);
                this.requestResults.put(0, numE);
            }
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            String str = strArr[i3];
            if (!str.equals("android.permission.WRITE_CALENDAR") && !str.equals("android.permission.READ_CALENDAR") && (iD = PermissionUtils.d(str)) != 20) {
                int i4 = iArr[i3];
                if (iD == 8) {
                    this.requestResults.put(8, PermissionUtils.e(this.requestResults.get(8), Integer.valueOf(PermissionUtils.g(this.activity, str, i4))));
                } else if (iD == 7) {
                    if (!this.requestResults.containsKey(7)) {
                        this.requestResults.put(7, Integer.valueOf(PermissionUtils.g(this.activity, str, i4)));
                    }
                    if (!this.requestResults.containsKey(14)) {
                        this.requestResults.put(14, Integer.valueOf(PermissionUtils.g(this.activity, str, i4)));
                    }
                } else if (iD == 4) {
                    int iG2 = PermissionUtils.g(this.activity, str, i4);
                    if (!this.requestResults.containsKey(4)) {
                        this.requestResults.put(4, Integer.valueOf(iG2));
                    }
                } else if (iD == 3) {
                    int iG3 = PermissionUtils.g(this.activity, str, i4);
                    if (Build.VERSION.SDK_INT < 29 && !this.requestResults.containsKey(4)) {
                        this.requestResults.put(4, Integer.valueOf(iG3));
                    }
                    if (!this.requestResults.containsKey(5)) {
                        this.requestResults.put(5, Integer.valueOf(iG3));
                    }
                    this.requestResults.put(Integer.valueOf(iD), Integer.valueOf(iG3));
                } else if (iD == 9 || iD == 32) {
                    this.requestResults.put(Integer.valueOf(iD), Integer.valueOf(determinePermissionStatus(iD)));
                } else if (!this.requestResults.containsKey(Integer.valueOf(iD))) {
                    this.requestResults.put(Integer.valueOf(iD), Integer.valueOf(PermissionUtils.g(this.activity, str, i4)));
                }
            }
        }
        int length = this.pendingRequestCount - iArr.length;
        this.pendingRequestCount = length;
        RequestPermissionsSuccessCallback requestPermissionsSuccessCallback = this.successCallback;
        if (requestPermissionsSuccessCallback == null || length != 0) {
            return true;
        }
        requestPermissionsSuccessCallback.onSuccess(this.requestResults);
        return true;
    }

    public void setActivity(@Nullable Activity activity) {
        this.activity = activity;
    }
}
