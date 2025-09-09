package com.baseflow.permissionhandler;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.app.ActivityCompat;
import com.alibaba.fastjson.parser.JSONLexer;
import com.luck.picture.lib.permissions.PermissionConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin.text.Typography;
import org.apache.commons.lang.CharUtils;

/* loaded from: classes3.dex */
public class PermissionUtils {
    static int a(Activity activity, String str) {
        if (activity == null) {
            return 0;
        }
        boolean zWasPermissionDeniedBefore = wasPermissionDeniedBefore(activity, str);
        boolean z2 = true;
        boolean z3 = !c(activity, str);
        if (!zWasPermissionDeniedBefore) {
            z2 = z3;
        } else if (z3) {
            z2 = false;
        }
        if (!zWasPermissionDeniedBefore && z2) {
            setPermissionDenied(activity, str);
        }
        return (zWasPermissionDeniedBefore && z2) ? 4 : 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static List b(Context context, int i2) {
        String strDetermineBluetoothPermission;
        String strDetermineBluetoothPermission2;
        String strDetermineBluetoothPermission3;
        ArrayList arrayList = new ArrayList();
        switch (i2) {
            case 0:
            case 37:
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALENDAR")) {
                    arrayList.add("android.permission.WRITE_CALENDAR");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CALENDAR")) {
                    arrayList.add("android.permission.READ_CALENDAR");
                }
                return arrayList;
            case 1:
                if (hasPermissionInManifest(context, arrayList, "android.permission.CAMERA")) {
                    arrayList.add("android.permission.CAMERA");
                }
                return arrayList;
            case 2:
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CONTACTS")) {
                    arrayList.add("android.permission.READ_CONTACTS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CONTACTS")) {
                    arrayList.add("android.permission.WRITE_CONTACTS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.GET_ACCOUNTS")) {
                    arrayList.add("android.permission.GET_ACCOUNTS");
                }
                return arrayList;
            case 3:
            case 4:
            case 5:
                if (i2 != 4 || Build.VERSION.SDK_INT < 29) {
                    if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_COARSE_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_COARSE_LOCATION");
                    }
                    if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_FINE_LOCATION")) {
                        arrayList.add("android.permission.ACCESS_FINE_LOCATION");
                    }
                } else if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_BACKGROUND_LOCATION")) {
                    arrayList.add("android.permission.ACCESS_BACKGROUND_LOCATION");
                }
                return arrayList;
            case 6:
            case 11:
            case 20:
                return null;
            case 7:
            case 14:
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECORD_AUDIO")) {
                    arrayList.add("android.permission.RECORD_AUDIO");
                }
                return arrayList;
            case 8:
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_PHONE_STATE")) {
                    arrayList.add("android.permission.READ_PHONE_STATE");
                }
                int i3 = Build.VERSION.SDK_INT;
                if (i3 > 29 && hasPermissionInManifest(context, arrayList, "android.permission.READ_PHONE_NUMBERS")) {
                    arrayList.add("android.permission.READ_PHONE_NUMBERS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.CALL_PHONE")) {
                    arrayList.add("android.permission.CALL_PHONE");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_CALL_LOG")) {
                    arrayList.add("android.permission.READ_CALL_LOG");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALL_LOG")) {
                    arrayList.add("android.permission.WRITE_CALL_LOG");
                }
                if (hasPermissionInManifest(context, arrayList, "com.android.voicemail.permission.ADD_VOICEMAIL")) {
                    arrayList.add("com.android.voicemail.permission.ADD_VOICEMAIL");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.USE_SIP")) {
                    arrayList.add("android.permission.USE_SIP");
                }
                if (i3 >= 26 && hasPermissionInManifest(context, arrayList, "android.permission.ANSWER_PHONE_CALLS")) {
                    arrayList.add("android.permission.ANSWER_PHONE_CALLS");
                }
                return arrayList;
            case 9:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, PermissionConfig.READ_MEDIA_IMAGES)) {
                    arrayList.add(PermissionConfig.READ_MEDIA_IMAGES);
                }
                return arrayList;
            case 10:
            case 25:
            case 26:
            default:
                return arrayList;
            case 12:
                if (hasPermissionInManifest(context, arrayList, "android.permission.BODY_SENSORS")) {
                    arrayList.add("android.permission.BODY_SENSORS");
                }
                return arrayList;
            case 13:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SEND_SMS")) {
                    arrayList.add("android.permission.SEND_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_SMS")) {
                    arrayList.add("android.permission.RECEIVE_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.READ_SMS")) {
                    arrayList.add("android.permission.READ_SMS");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_WAP_PUSH")) {
                    arrayList.add("android.permission.RECEIVE_WAP_PUSH");
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.RECEIVE_MMS")) {
                    arrayList.add("android.permission.RECEIVE_MMS");
                }
                return arrayList;
            case 15:
                if (hasPermissionInManifest(context, arrayList, PermissionConfig.READ_EXTERNAL_STORAGE)) {
                    arrayList.add(PermissionConfig.READ_EXTERNAL_STORAGE);
                }
                int i4 = Build.VERSION.SDK_INT;
                if ((i4 < 29 || (i4 == 29 && Environment.isExternalStorageLegacy())) && hasPermissionInManifest(context, arrayList, PermissionConfig.WRITE_EXTERNAL_STORAGE)) {
                    arrayList.add(PermissionConfig.WRITE_EXTERNAL_STORAGE);
                }
                return arrayList;
            case 16:
                if (hasPermissionInManifest(context, arrayList, "android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")) {
                    arrayList.add("android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                }
                return arrayList;
            case 17:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.POST_NOTIFICATIONS")) {
                    arrayList.add("android.permission.POST_NOTIFICATIONS");
                }
                return arrayList;
            case 18:
                if (Build.VERSION.SDK_INT < 29) {
                    return null;
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_MEDIA_LOCATION")) {
                    arrayList.add("android.permission.ACCESS_MEDIA_LOCATION");
                }
                return arrayList;
            case 19:
                if (Build.VERSION.SDK_INT < 29) {
                    return null;
                }
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACTIVITY_RECOGNITION")) {
                    arrayList.add("android.permission.ACTIVITY_RECOGNITION");
                }
                return arrayList;
            case 21:
                if (hasPermissionInManifest(context, arrayList, "android.permission.BLUETOOTH")) {
                    arrayList.add("android.permission.BLUETOOTH");
                }
                return arrayList;
            case 22:
                if (Build.VERSION.SDK_INT >= 30 && hasPermissionInManifest(context, arrayList, "android.permission.MANAGE_EXTERNAL_STORAGE")) {
                    arrayList.add("android.permission.MANAGE_EXTERNAL_STORAGE");
                }
                return arrayList;
            case 23:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SYSTEM_ALERT_WINDOW")) {
                    arrayList.add("android.permission.SYSTEM_ALERT_WINDOW");
                }
                return arrayList;
            case 24:
                if (hasPermissionInManifest(context, arrayList, "android.permission.REQUEST_INSTALL_PACKAGES")) {
                    arrayList.add("android.permission.REQUEST_INSTALL_PACKAGES");
                }
                return arrayList;
            case 27:
                if (hasPermissionInManifest(context, arrayList, "android.permission.ACCESS_NOTIFICATION_POLICY")) {
                    arrayList.add("android.permission.ACCESS_NOTIFICATION_POLICY");
                }
                return arrayList;
            case 28:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission = determineBluetoothPermission(context, "android.permission.BLUETOOTH_SCAN")) != null) {
                    arrayList.add(strDetermineBluetoothPermission);
                }
                return arrayList;
            case 29:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission2 = determineBluetoothPermission(context, "android.permission.BLUETOOTH_ADVERTISE")) != null) {
                    arrayList.add(strDetermineBluetoothPermission2);
                }
                return arrayList;
            case 30:
                if (Build.VERSION.SDK_INT >= 31 && (strDetermineBluetoothPermission3 = determineBluetoothPermission(context, "android.permission.BLUETOOTH_CONNECT")) != null) {
                    arrayList.add(strDetermineBluetoothPermission3);
                }
                return arrayList;
            case 31:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.NEARBY_WIFI_DEVICES")) {
                    arrayList.add("android.permission.NEARBY_WIFI_DEVICES");
                }
                return arrayList;
            case 32:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, PermissionConfig.READ_MEDIA_VIDEO)) {
                    arrayList.add(PermissionConfig.READ_MEDIA_VIDEO);
                }
                return arrayList;
            case 33:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, PermissionConfig.READ_MEDIA_AUDIO)) {
                    arrayList.add(PermissionConfig.READ_MEDIA_AUDIO);
                }
                return arrayList;
            case 34:
                if (hasPermissionInManifest(context, arrayList, "android.permission.SCHEDULE_EXACT_ALARM")) {
                    arrayList.add("android.permission.SCHEDULE_EXACT_ALARM");
                }
                return arrayList;
            case 35:
                if (Build.VERSION.SDK_INT >= 33 && hasPermissionInManifest(context, arrayList, "android.permission.BODY_SENSORS_BACKGROUND")) {
                    arrayList.add("android.permission.BODY_SENSORS_BACKGROUND");
                }
                return arrayList;
            case 36:
                if (hasPermissionInManifest(context, arrayList, "android.permission.WRITE_CALENDAR")) {
                    arrayList.add("android.permission.WRITE_CALENDAR");
                }
                return arrayList;
        }
    }

    static boolean c(Activity activity, String str) {
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, str);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    static int d(String str) {
        str.hashCode();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -2062386608:
                if (str.equals("android.permission.READ_SMS")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1928411001:
                if (str.equals("android.permission.READ_CALENDAR")) {
                    c2 = 1;
                    break;
                }
                break;
            case -1925850455:
                if (str.equals("android.permission.POST_NOTIFICATIONS")) {
                    c2 = 2;
                    break;
                }
                break;
            case -1921431796:
                if (str.equals("android.permission.READ_CALL_LOG")) {
                    c2 = 3;
                    break;
                }
                break;
            case -1888586689:
                if (str.equals("android.permission.ACCESS_FINE_LOCATION")) {
                    c2 = 4;
                    break;
                }
                break;
            case -1813079487:
                if (str.equals("android.permission.MANAGE_EXTERNAL_STORAGE")) {
                    c2 = 5;
                    break;
                }
                break;
            case -1783097621:
                if (str.equals("android.permission.ACCESS_NOTIFICATION_POLICY")) {
                    c2 = 6;
                    break;
                }
                break;
            case -1561629405:
                if (str.equals("android.permission.SYSTEM_ALERT_WINDOW")) {
                    c2 = 7;
                    break;
                }
                break;
            case -1479758289:
                if (str.equals("android.permission.RECEIVE_WAP_PUSH")) {
                    c2 = '\b';
                    break;
                }
                break;
            case -1238066820:
                if (str.equals("android.permission.BODY_SENSORS")) {
                    c2 = '\t';
                    break;
                }
                break;
            case -1164582768:
                if (str.equals("android.permission.READ_PHONE_NUMBERS")) {
                    c2 = '\n';
                    break;
                }
                break;
            case -909527021:
                if (str.equals("android.permission.NEARBY_WIFI_DEVICES")) {
                    c2 = 11;
                    break;
                }
                break;
            case -895679497:
                if (str.equals("android.permission.RECEIVE_MMS")) {
                    c2 = '\f';
                    break;
                }
                break;
            case -895673731:
                if (str.equals("android.permission.RECEIVE_SMS")) {
                    c2 = CharUtils.CR;
                    break;
                }
                break;
            case -798669607:
                if (str.equals("android.permission.BLUETOOTH_CONNECT")) {
                    c2 = 14;
                    break;
                }
                break;
            case -406040016:
                if (str.equals(PermissionConfig.READ_EXTERNAL_STORAGE)) {
                    c2 = 15;
                    break;
                }
                break;
            case -63024214:
                if (str.equals("android.permission.ACCESS_COARSE_LOCATION")) {
                    c2 = 16;
                    break;
                }
                break;
            case -5573545:
                if (str.equals("android.permission.READ_PHONE_STATE")) {
                    c2 = 17;
                    break;
                }
                break;
            case 52602690:
                if (str.equals("android.permission.SEND_SMS")) {
                    c2 = 18;
                    break;
                }
                break;
            case 112197485:
                if (str.equals("android.permission.CALL_PHONE")) {
                    c2 = 19;
                    break;
                }
                break;
            case 175802396:
                if (str.equals(PermissionConfig.READ_MEDIA_IMAGES)) {
                    c2 = 20;
                    break;
                }
                break;
            case 214526995:
                if (str.equals("android.permission.WRITE_CONTACTS")) {
                    c2 = 21;
                    break;
                }
                break;
            case 361658321:
                if (str.equals("android.permission.BODY_SENSORS_BACKGROUND")) {
                    c2 = 22;
                    break;
                }
                break;
            case 463403621:
                if (str.equals("android.permission.CAMERA")) {
                    c2 = 23;
                    break;
                }
                break;
            case 603653886:
                if (str.equals("android.permission.WRITE_CALENDAR")) {
                    c2 = 24;
                    break;
                }
                break;
            case 610633091:
                if (str.equals("android.permission.WRITE_CALL_LOG")) {
                    c2 = 25;
                    break;
                }
                break;
            case 691260818:
                if (str.equals(PermissionConfig.READ_MEDIA_AUDIO)) {
                    c2 = JSONLexer.EOI;
                    break;
                }
                break;
            case 710297143:
                if (str.equals(PermissionConfig.READ_MEDIA_VIDEO)) {
                    c2 = 27;
                    break;
                }
                break;
            case 784519842:
                if (str.equals("android.permission.USE_SIP")) {
                    c2 = 28;
                    break;
                }
                break;
            case 970694249:
                if (str.equals("android.permission.SCHEDULE_EXACT_ALARM")) {
                    c2 = 29;
                    break;
                }
                break;
            case 1166454870:
                if (str.equals("android.permission.BLUETOOTH_ADVERTISE")) {
                    c2 = 30;
                    break;
                }
                break;
            case 1271781903:
                if (str.equals("android.permission.GET_ACCOUNTS")) {
                    c2 = 31;
                    break;
                }
                break;
            case 1365911975:
                if (str.equals(PermissionConfig.WRITE_EXTERNAL_STORAGE)) {
                    c2 = ' ';
                    break;
                }
                break;
            case 1777263169:
                if (str.equals("android.permission.REQUEST_INSTALL_PACKAGES")) {
                    c2 = '!';
                    break;
                }
                break;
            case 1780337063:
                if (str.equals("android.permission.ACTIVITY_RECOGNITION")) {
                    c2 = Typography.quote;
                    break;
                }
                break;
            case 1831139720:
                if (str.equals("android.permission.RECORD_AUDIO")) {
                    c2 = '#';
                    break;
                }
                break;
            case 1977429404:
                if (str.equals("android.permission.READ_CONTACTS")) {
                    c2 = '$';
                    break;
                }
                break;
            case 2024715147:
                if (str.equals("android.permission.ACCESS_BACKGROUND_LOCATION")) {
                    c2 = '%';
                    break;
                }
                break;
            case 2062356686:
                if (str.equals("android.permission.BLUETOOTH_SCAN")) {
                    c2 = Typography.amp;
                    break;
                }
                break;
            case 2114579147:
                if (str.equals("android.permission.ACCESS_MEDIA_LOCATION")) {
                    c2 = '\'';
                    break;
                }
                break;
            case 2133799037:
                if (str.equals("com.android.voicemail.permission.ADD_VOICEMAIL")) {
                    c2 = '(';
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
            case '\b':
            case '\f':
            case '\r':
            case 18:
                return 13;
            case 1:
            case 24:
                return 0;
            case 2:
                return 17;
            case 3:
            case '\n':
            case 17:
            case 19:
            case 25:
            case 28:
            case '(':
                return 8;
            case 4:
            case 16:
                return 3;
            case 5:
                return 22;
            case 6:
                return 27;
            case 7:
                return 23;
            case '\t':
                return 12;
            case 11:
                return 31;
            case 14:
                return 30;
            case 15:
            case ' ':
                return 15;
            case 20:
                return 9;
            case 21:
            case 31:
            case '$':
                return 2;
            case 22:
                return 35;
            case 23:
                return 1;
            case 26:
                return 33;
            case 27:
                return 32;
            case 29:
                return 34;
            case 30:
                return 29;
            case '!':
                return 24;
            case '\"':
                return 19;
            case '#':
                return 7;
            case '%':
                return 4;
            case '&':
                return 28;
            case '\'':
                return 18;
            default:
                return 20;
        }
    }

    private static String determineBluetoothPermission(Context context, String str) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31 && hasPermissionInManifest(context, null, str)) {
            return str;
        }
        if (i2 >= 29) {
            if (hasPermissionInManifest(context, null, "android.permission.ACCESS_FINE_LOCATION")) {
                return "android.permission.ACCESS_FINE_LOCATION";
            }
            return null;
        }
        if (hasPermissionInManifest(context, null, "android.permission.ACCESS_FINE_LOCATION")) {
            return "android.permission.ACCESS_FINE_LOCATION";
        }
        if (hasPermissionInManifest(context, null, "android.permission.ACCESS_COARSE_LOCATION")) {
            return "android.permission.ACCESS_COARSE_LOCATION";
        }
        return null;
    }

    static Integer e(Integer num, Integer num2) {
        HashSet hashSet = new HashSet();
        hashSet.add(num);
        hashSet.add(num2);
        return f(hashSet);
    }

    static Integer f(Collection collection) {
        if (collection.contains(4)) {
            return 4;
        }
        if (collection.contains(2)) {
            return 2;
        }
        if (collection.contains(0)) {
            return 0;
        }
        return collection.contains(3) ? 3 : 1;
    }

    static int g(Activity activity, String str, int i2) {
        if (i2 == -1) {
            return a(activity, str);
        }
        return 1;
    }

    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        return Build.VERSION.SDK_INT >= 33 ? packageManager.getPackageInfo(context.getPackageName(), PackageManager.PackageInfoFlags.of(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)) : packageManager.getPackageInfo(context.getPackageName(), 4096);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0021 A[Catch: Exception -> 0x001d, TryCatch #0 {Exception -> 0x001d, blocks: (B:4:0x0006, B:5:0x000a, B:7:0x0010, B:13:0x0021, B:15:0x0027, B:17:0x002d, B:19:0x0033, B:20:0x0042, B:22:0x0048), top: B:27:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027 A[Catch: Exception -> 0x001d, TryCatch #0 {Exception -> 0x001d, blocks: (B:4:0x0006, B:5:0x000a, B:7:0x0010, B:13:0x0021, B:15:0x0027, B:17:0x002d, B:19:0x0033, B:20:0x0042, B:22:0x0048), top: B:27:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean hasPermissionInManifest(android.content.Context r4, java.util.ArrayList<java.lang.String> r5, java.lang.String r6) {
        /*
            r0 = 1
            r1 = 0
            java.lang.String r2 = "permissions_handler"
            if (r5 == 0) goto L1f
            java.util.Iterator r5 = r5.iterator()     // Catch: java.lang.Exception -> L1d
        La:
            boolean r3 = r5.hasNext()     // Catch: java.lang.Exception -> L1d
            if (r3 == 0) goto L1f
            java.lang.Object r3 = r5.next()     // Catch: java.lang.Exception -> L1d
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Exception -> L1d
            boolean r3 = r3.equals(r6)     // Catch: java.lang.Exception -> L1d
            if (r3 == 0) goto La
            return r0
        L1d:
            r4 = move-exception
            goto L55
        L1f:
            if (r4 != 0) goto L27
            java.lang.String r4 = "Unable to detect current Activity or App Context."
            android.util.Log.d(r2, r4)     // Catch: java.lang.Exception -> L1d
            return r1
        L27:
            android.content.pm.PackageInfo r4 = getPackageInfo(r4)     // Catch: java.lang.Exception -> L1d
            if (r4 != 0) goto L33
            java.lang.String r4 = "Unable to get Package info, will not be able to determine permissions to request."
            android.util.Log.d(r2, r4)     // Catch: java.lang.Exception -> L1d
            return r1
        L33:
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Exception -> L1d
            java.lang.String[] r4 = r4.requestedPermissions     // Catch: java.lang.Exception -> L1d
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch: java.lang.Exception -> L1d
            r5.<init>(r4)     // Catch: java.lang.Exception -> L1d
            java.util.Iterator r4 = r5.iterator()     // Catch: java.lang.Exception -> L1d
        L42:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Exception -> L1d
            if (r5 == 0) goto L5a
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Exception -> L1d
            java.lang.String r5 = (java.lang.String) r5     // Catch: java.lang.Exception -> L1d
            boolean r5 = r5.equals(r6)     // Catch: java.lang.Exception -> L1d
            if (r5 == 0) goto L42
            return r0
        L55:
            java.lang.String r5 = "Unable to check manifest for permission: "
            android.util.Log.d(r2, r5, r4)
        L5a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baseflow.permissionhandler.PermissionUtils.hasPermissionInManifest(android.content.Context, java.util.ArrayList, java.lang.String):boolean");
    }

    private static void setPermissionDenied(Context context, String str) {
        context.getSharedPreferences(str, 0).edit().putBoolean("sp_permission_handler_permission_was_denied_before", true).apply();
    }

    private static boolean wasPermissionDeniedBefore(Context context, String str) {
        return context.getSharedPreferences(str, 0).getBoolean("sp_permission_handler_permission_was_denied_before", false);
    }
}
