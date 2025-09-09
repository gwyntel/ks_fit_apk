package com.huawei.hms.scankit.p;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.hms.framework.common.SystemPropUtils;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanBase;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/* loaded from: classes4.dex */
public class w7 {

    /* renamed from: a, reason: collision with root package name */
    private static String f17976a = null;

    /* renamed from: b, reason: collision with root package name */
    private static String f17977b = null;

    /* renamed from: c, reason: collision with root package name */
    public static boolean f17978c = true;

    public static boolean a(int[][] iArr, int i2) {
        return i2 >= 0 && i2 < iArr.length;
    }

    public static int b(int i2) {
        if (i2 == 8191) {
            return 8191;
        }
        if (i2 == HmsScanBase.QRCODE_SCAN_TYPE) {
            return 256;
        }
        if (i2 == HmsScanBase.AZTEC_SCAN_TYPE) {
            return 4096;
        }
        if (i2 == HmsScanBase.DATAMATRIX_SCAN_TYPE) {
            return 16;
        }
        if (i2 == HmsScanBase.PDF417_SCAN_TYPE) {
            return 2048;
        }
        if (i2 == HmsScanBase.CODE39_SCAN_TYPE) {
            return 2;
        }
        if (i2 == HmsScanBase.CODE93_SCAN_TYPE) {
            return 4;
        }
        if (i2 == HmsScanBase.CODE128_SCAN_TYPE) {
            return 1;
        }
        if (i2 == HmsScanBase.EAN13_SCAN_TYPE) {
            return 32;
        }
        if (i2 == HmsScanBase.EAN8_SCAN_TYPE) {
            return 64;
        }
        if (i2 == HmsScanBase.ITF14_SCAN_TYPE) {
            return 128;
        }
        if (i2 == HmsScanBase.UPCCODE_A_SCAN_TYPE) {
            return 512;
        }
        if (i2 == HmsScanBase.UPCCODE_E_SCAN_TYPE) {
            return 1024;
        }
        if (i2 == HmsScanBase.CODABAR_SCAN_TYPE) {
            return 8;
        }
        if (i2 == HmsScanBase.WX_SCAN_TYPE) {
            return 16384;
        }
        if (i2 == HmsScanBase.MULTI_FUNCTIONAL_SCAN_TYPE) {
            return 8192;
        }
        return i2;
    }

    public static boolean c(Context context) {
        if (b(context) && TextUtils.isEmpty(f17977b)) {
            f17977b = context.getSharedPreferences("scanExt", 0).getString("scanExt", "unSet");
        }
        return "forbid".equals(f17977b);
    }

    public static int d(Context context) {
        int identifier;
        if (context.getResources() == null || (identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android")) <= 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(identifier);
    }

    public static boolean e() {
        try {
            return "-1".equalsIgnoreCase(SystemPropUtils.getProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, "sys.hw_multiwin_for_camera", "android.os.SystemProperties", "UNKNOWN"));
        } catch (RuntimeException | Exception unused) {
            return false;
        }
    }

    public static boolean f(Context context) {
        return Build.VERSION.SDK_INT >= 24 && (context instanceof Activity) && ((Activity) context).isInMultiWindowMode();
    }

    public static boolean g(Context context) {
        try {
            return "CN".equalsIgnoreCase(SystemPropUtils.getProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, "ro.hw.country", "android.os.SystemProperties", "UNKNOWN"));
        } catch (RuntimeException | Exception unused) {
            return false;
        }
    }

    public static boolean h(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) >= 5.5d;
    }

    public static boolean i(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) >= 8.0d && ((float) displayMetrics.widthPixels) / ((float) displayMetrics.heightPixels) > 1.3f;
    }

    public static boolean j(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) >= 7.0d;
    }

    public static boolean k(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), a(), 0) == 0;
    }

    public static boolean a(int[] iArr, int i2) {
        return i2 >= 0 && i2 < iArr.length;
    }

    public static boolean f() {
        String str = Build.BRAND;
        return str != null && str.toLowerCase().equals("samsung");
    }

    public static boolean a(byte[] bArr, int i2) {
        return i2 >= 0 && i2 < bArr.length;
    }

    public static boolean a(byte[][] bArr, int i2) {
        return i2 >= 0 && i2 < bArr.length;
    }

    public static boolean d() {
        return "ar".equals(Locale.getDefault().getLanguage()) || "ur".equals(Locale.getDefault().getLanguage()) || "ug".equals(Locale.getDefault().getLanguage()) || "iw".equals(Locale.getDefault().getLanguage()) || "fa".equals(Locale.getDefault().getLanguage());
    }

    public static boolean e(Context context) {
        String string = context.getResources().getConfiguration().toString();
        return string.contains("hwMultiwindow-magic") || string.contains("hw-magic-windows");
    }

    public static boolean a(String[] strArr, int i2) {
        return i2 >= 0 && i2 < strArr.length;
    }

    public static boolean c(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        return rotation == 0 || rotation == 2;
    }

    public static boolean a(float[] fArr, int i2) {
        return i2 >= 0 && i2 < fArr.length;
    }

    public static boolean c() {
        String str = Build.MANUFACTURER;
        return str.equalsIgnoreCase("HUAWEI") || str.equalsIgnoreCase("honor");
    }

    public static HmsScan[] a(HmsScan[] hmsScanArr) {
        if (hmsScanArr != null && hmsScanArr.length != 0) {
            for (HmsScan hmsScan : hmsScanArr) {
                if (hmsScan != null) {
                    hmsScan.scanType = b(hmsScan.scanType);
                }
            }
        }
        return hmsScanArr;
    }

    public static int a(int i2) {
        if (i2 <= 0) {
            return 8191;
        }
        if (((i2 - 1) & i2) == 0) {
            return i2;
        }
        int i3 = HmsScanBase.AZTEC_SCAN_TYPE;
        int iB = (i2 & i3) != 0 ? b(i3) : 0;
        int i4 = HmsScanBase.CODABAR_SCAN_TYPE;
        if ((i2 & i4) != 0) {
            iB |= b(i4);
        }
        int i5 = HmsScanBase.CODE39_SCAN_TYPE;
        if ((i2 & i5) != 0) {
            iB |= b(i5);
        }
        int i6 = HmsScanBase.CODE93_SCAN_TYPE;
        if ((i2 & i6) != 0) {
            iB |= b(i6);
        }
        int i7 = HmsScanBase.CODE128_SCAN_TYPE;
        if ((i2 & i7) != 0) {
            iB |= b(i7);
        }
        int i8 = HmsScanBase.DATAMATRIX_SCAN_TYPE;
        if ((i2 & i8) != 0) {
            iB |= b(i8);
        }
        int i9 = HmsScanBase.EAN8_SCAN_TYPE;
        if ((i2 & i9) != 0) {
            iB |= b(i9);
        }
        int i10 = HmsScanBase.EAN13_SCAN_TYPE;
        if ((i2 & i10) != 0) {
            iB |= b(i10);
        }
        int i11 = HmsScanBase.QRCODE_SCAN_TYPE;
        if ((i2 & i11) != 0) {
            iB |= b(i11);
        }
        int i12 = HmsScanBase.ITF14_SCAN_TYPE;
        if ((i2 & i12) != 0) {
            iB |= b(i12);
        }
        int i13 = HmsScanBase.PDF417_SCAN_TYPE;
        if ((i2 & i13) != 0) {
            iB |= b(i13);
        }
        int i14 = HmsScanBase.UPCCODE_A_SCAN_TYPE;
        if ((i2 & i14) != 0) {
            iB |= b(i14);
        }
        int i15 = HmsScanBase.UPCCODE_E_SCAN_TYPE;
        if ((i2 & i15) != 0) {
            iB |= b(i15);
        }
        int i16 = HmsScanBase.MULTI_FUNCTIONAL_SCAN_TYPE;
        if ((i2 & i16) != 0) {
            iB |= b(i16);
        }
        int i17 = HmsScanBase.WX_SCAN_TYPE;
        return (i2 & i17) != 0 ? iB | b(i17) : iB;
    }

    public static boolean b() {
        try {
            return "-1".equalsIgnoreCase(SystemPropUtils.getProperty(TmpConstant.PROPERTY_IDENTIFIER_GET, "sys.multiwin_for_camera", "android.os.SystemProperties", "UNKNOWN"));
        } catch (RuntimeException | Exception unused) {
            return false;
        }
    }

    public static boolean b(Activity activity) {
        return a(activity) == 102;
    }

    public static boolean b(Context context) {
        if (TextUtils.isEmpty(f17976a)) {
            try {
                f17976a = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("scanExt", "unSet");
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("scanerror", "checkPermission NameNotFoundException");
            } catch (Exception unused2) {
                Log.e("scanerror", "checkPermission Exception");
            }
        }
        return "readUri".equals(f17976a);
    }

    private static String b(Context context, Intent intent) {
        Uri data = new Intent(intent).getData();
        if (DocumentsContract.isDocumentUri(context, data)) {
            String documentId = DocumentsContract.getDocumentId(data);
            if ("com.android.providers.media.documents".equals(data.getAuthority())) {
                return a(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=" + documentId.split(":")[1]);
            }
            if (!"com.android.providers.downloads.documents".equals(data.getAuthority())) {
                return null;
            }
            try {
                return a(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(documentId)), (String) null);
            } catch (NumberFormatException unused) {
                o4.b("ScankitUtils", "NumberFormatException in withAppendedId");
                return null;
            } catch (Exception unused2) {
                o4.b("ScankitUtils", "Exception in withAppendedId");
                return null;
            }
        }
        if ("content".equalsIgnoreCase(data.getScheme())) {
            return a(context, data, (String) null);
        }
        return null;
    }

    public static Bitmap a(Bitmap bitmap, int i2, int i3) {
        if (bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return null;
        }
        return a(bitmap, i2 / bitmap.getWidth(), i3 / bitmap.getHeight());
    }

    public static Bitmap a(Bitmap bitmap, float f2, float f3) {
        if (f2 <= 0.0f || f3 <= 0.0f) {
            return null;
        }
        float f4 = 1.0f / f2;
        float f5 = 1.0f / f3;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i2 = (int) (width * f2);
        int i3 = (int) (height * f3);
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int[] iArr2 = new int[i2 * i3];
        for (int i4 = 0; i4 < i3; i4++) {
            for (int i5 = 0; i5 < i2; i5++) {
                iArr2[(i4 * i2) + i5] = iArr[(((int) (i4 * f5)) * width) + ((int) (i5 * f4))];
            }
        }
        Log.d(">>>", "dstPixels:" + i2 + " x " + i3);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.setPixels(iArr2, 0, i2, 0, 0, i2, i3);
        return bitmapCreateBitmap;
    }

    public static int a(Context context, String str) {
        return context.checkPermission(str, Process.myPid(), Process.myUid()) == -1 ? -1 : 0;
    }

    public static String a(String str) {
        return AppOpsManager.permissionToOp(str);
    }

    private static String a() {
        String str = Build.BRAND;
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase("HUAWEI")) {
            return "navigationbar_is_min";
        }
        if (str.equalsIgnoreCase("XIAOMI")) {
            return "force_fsg_nav_bar";
        }
        return (str.equalsIgnoreCase("VIVO") || str.equalsIgnoreCase("OPPO")) ? "navigation_gesture_on" : "navigationbar_is_min";
    }

    public static ResolveInfo a(Intent intent, String str, Activity activity) {
        intent.setPackage(str);
        List<ResolveInfo> listQueryIntentActivities = activity.getPackageManager().queryIntentActivities(intent, 0);
        if (listQueryIntentActivities.isEmpty()) {
            return null;
        }
        return listQueryIntentActivities.get(0);
    }

    public static boolean a(String str, Activity activity) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = activity.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            o4.d("Utils", "NameNotFoundException Exception");
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        int i2 = packageInfo.applicationInfo.flags;
        return ((i2 & 1) == 1) || ((i2 & 128) == 1);
    }

    public static int a(Activity activity) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("com.huawei.android.app.ActivityManagerEx");
            Method declaredMethod = cls.getDeclaredMethod("getActivityWindowMode", Activity.class);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(cls, activity);
            if (objInvoke == null) {
                return -1;
            }
            return Integer.valueOf(String.valueOf(objInvoke)).intValue();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | NumberFormatException | InvocationTargetException unused) {
            return -1;
        }
    }

    public static Bitmap a(Bitmap bitmap, float f2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(f2, width / 2, height / 2);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (!bitmapCreateBitmap.equals(bitmap) && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bitmapCreateBitmap;
    }

    public static Bitmap a(Bitmap bitmap, int i2) {
        if (i2 == 0) {
            return a(bitmap, 90.0f);
        }
        if (i2 != 2) {
            return i2 != 3 ? bitmap : a(bitmap, 180.0f);
        }
        return a(bitmap, 270.0f);
    }

    public static Bitmap a(Context context, Intent intent) {
        Bitmap bitmapA;
        Bitmap bitmapCompressBitmap;
        if (!b(context)) {
            String strB = b(context, intent);
            if (TextUtils.isEmpty(strB)) {
                return null;
            }
            if (Build.VERSION.SDK_INT > 28 && context.getApplicationInfo() != null && context.getApplicationInfo().targetSdkVersion > 28) {
                o4.a("ScanBitmap", "compressBitmap above android 29");
                bitmapCompressBitmap = ScanUtil.compressBitmapForAndroid29(context, strB);
            } else {
                o4.a("ScanBitmap", "compressBitmap below android 29");
                bitmapCompressBitmap = ScanUtil.compressBitmap(context, strB);
            }
            if (bitmapCompressBitmap != null) {
                return bitmapCompressBitmap;
            }
            o4.a("ScanBitmap", "compressBitmap above android 29");
            return ScanUtil.compressBitmapForAndroid29(context, strB);
        }
        Uri data = intent.getData();
        if (data == null || (bitmapA = a(context, data)) == null) {
            return null;
        }
        return bitmapA;
    }

    private static String a(Context context, Uri uri, String str) {
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, null, null);
            if (cursorQuery != null) {
                string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndex("_data")) : null;
                cursorQuery.close();
            }
            return string;
        } catch (IllegalStateException unused) {
            o4.b("ScankitUtils", "IllegalStateException in getImagePath");
            return null;
        } catch (Exception unused2) {
            o4.b("ScankitUtils", "Exception in getImagePath");
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0051 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap a(android.content.Context r7, android.net.Uri r8) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "IOException in getImagePath"
            java.lang.String r1 = "ScankitUtils"
            android.graphics.BitmapFactory$Options r2 = new android.graphics.BitmapFactory$Options
            r2.<init>()
            r3 = 1
            r2.inJustDecodeBounds = r3
            android.content.Context r3 = r7.getApplicationContext()
            android.content.ContentResolver r3 = r3.getContentResolver()
            r4 = 0
            java.io.InputStream r8 = r3.openInputStream(r8)     // Catch: java.lang.Throwable -> L3d java.io.IOException -> L3f
            if (r8 == 0) goto L37
            byte[] r3 = a(r8)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            int r5 = r3.length     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            r6 = 0
            android.graphics.BitmapFactory.decodeByteArray(r3, r6, r5, r2)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            a(r7, r2)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            int r7 = r3.length     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            android.graphics.Bitmap r7 = android.graphics.BitmapFactory.decodeByteArray(r3, r6, r7, r2)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L40
            r8.close()     // Catch: java.io.IOException -> L30
            goto L33
        L30:
            com.huawei.hms.scankit.p.o4.b(r1, r0)
        L33:
            return r7
        L34:
            r7 = move-exception
            r4 = r8
            goto L4f
        L37:
            if (r8 == 0) goto L4e
            r8.close()     // Catch: java.io.IOException -> L4b
            goto L4e
        L3d:
            r7 = move-exception
            goto L4f
        L3f:
            r8 = r4
        L40:
            java.lang.String r7 = "compressBitmapFromUri IOException"
            com.huawei.hms.scankit.p.o4.b(r1, r7)     // Catch: java.lang.Throwable -> L34
            if (r8 == 0) goto L4e
            r8.close()     // Catch: java.io.IOException -> L4b
            goto L4e
        L4b:
            com.huawei.hms.scankit.p.o4.b(r1, r0)
        L4e:
            return r4
        L4f:
            if (r4 == 0) goto L58
            r4.close()     // Catch: java.io.IOException -> L55
            goto L58
        L55:
            com.huawei.hms.scankit.p.o4.b(r1, r0)
        L58:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.scankit.p.w7.a(android.content.Context, android.net.Uri):android.graphics.Bitmap");
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = inputStream.read(bArr);
            if (i2 != -1) {
                byteArrayOutputStream.write(bArr, 0, i2);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    private static void a(Context context, BitmapFactory.Options options) {
        ActivityManager.MemoryInfo memoryInfo;
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (i2 == 0 || i3 == 0) {
            return;
        }
        if (i2 > i3) {
            i2 = i3;
        }
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
        } catch (NullPointerException unused) {
            o4.b("exception", "NullPointerException");
        } catch (Exception unused2) {
            o4.b("exception", "Exception");
        }
        boolean z2 = ((double) memoryInfo.totalMem) / Math.pow(1024.0d, 3.0d) < 5.5d;
        int i4 = z2 ? 1200 : 3000;
        options.inSampleSize = i2 > i4 ? Math.round(i2 / i4) : 1;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
    }

    public static boolean a(Context context) {
        return b(context) || context.checkPermission("android.permission.CAMERA", Process.myPid(), Process.myUid()) == 0;
    }
}
