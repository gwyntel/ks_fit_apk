package com.huawei.hms.hmsscankit;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.huawei.hms.feature.dynamic.DynamicModule;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.ml.scan.HmsScanFrame;
import com.huawei.hms.ml.scan.HmsScanFrameOptions;
import com.huawei.hms.ml.scan.HmsScanResult;
import com.huawei.hms.mlsdk.common.MLFrame;
import com.huawei.hms.scankit.p.c5;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.w7;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes4.dex */
public class ScanUtil {
    public static final int CAMERA_INIT_ERROR = -1000;
    static final String CONTEXT_METHOD = "initializeModule";
    static final String CONTEXT_PATH = "com.huawei.hms.feature.DynamicModuleInitializer";
    static final String CREATOR_PATH = "com.huawei.hms.scankit.Creator";
    public static final int ERROR_ABNORMAL_RESTART = 3;
    public static final int ERROR_INVALID_PARAM = 4;
    public static final int ERROR_NO_CAMERA_PERMISSION = 1;
    public static final int ERROR_NO_READ_PERMISSION = 2;
    private static final int LITE_VERSION = 10320300;
    private static final int MAX_BITMAP_SIZE = 52428800;
    static final String MODULE_SCANKIT = "huawei_module_scankit";
    static final String MODULE_SCANKIT_LOCAL = "huawei_module_scankit_local";
    private static final int NEW_VERSION = 21002300;
    public static final String RESULT = "SCAN_RESULT";
    public static final String RESULT_CODE = "SCAN_RESULT_CODE";
    public static final int SCAN_NO_DETECTED = 4096;
    public static final int SUCCESS = 0;
    private static final int WR_VERSION = 201000300;

    public static Bitmap buildBitmap(String str, int i2, int i3, int i4, HmsBuildBitmapOption hmsBuildBitmapOption) throws WriterException {
        return new c5().a(str, i2, i3, i4, hmsBuildBitmapOption);
    }

    private static HmsScanResult checkHmsScan(HmsScan[] hmsScanArr, HmsScanFrameOptions hmsScanFrameOptions) {
        return hmsScanArr.length == 0 ? new HmsScanResult(4096, null) : (hmsScanArr[0].getOriginalValue() != "" || hmsScanArr[0].getZoomValue() <= 1.0d) ? hmsScanArr[0].getOriginalValue() != "" ? new HmsScanResult(0, hmsScanArr) : new HmsScanResult(4096, hmsScanArr) : new HmsScanResult(4098, hmsScanArr);
    }

    private static boolean checkVersion(int i2, int i3) {
        if (i2 == LITE_VERSION && (i3 < NEW_VERSION || i3 == WR_VERSION)) {
            return true;
        }
        if (i2 != LITE_VERSION) {
            return i2 < NEW_VERSION || i3 == WR_VERSION || i3 < NEW_VERSION;
        }
        return false;
    }

    public static Bitmap compressBitmap(Context context, String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        dealBitmapFactoryOption(context, options);
        return BitmapFactory.decodeFile(str, options);
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0038: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]) (LINE:57), block:B:10:0x0038 */
    /* JADX WARN: Removed duplicated region for block: B:11:0x003a A[Catch: all -> 0x0037, Exception -> 0x0061, NullPointerException -> 0x006b, TryCatch #2 {all -> 0x0037, blocks: (B:5:0x001f, B:7:0x0025, B:11:0x003a, B:13:0x0045, B:21:0x0061, B:26:0x006b), top: B:40:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005a A[Catch: Exception -> 0x0076, TRY_ENTER, TRY_LEAVE, TryCatch #3 {Exception -> 0x0076, blocks: (B:16:0x005a, B:23:0x0066, B:28:0x0072), top: B:42:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x008e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap compressBitmapForAndroid29(android.content.Context r11, java.lang.String r12) {
        /*
            java.lang.String r0 = "_id"
            java.lang.String r1 = "Exception"
            java.lang.String r2 = "exception"
            r3 = 0
            android.content.ContentResolver r4 = r11.getContentResolver()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60 java.lang.NullPointerException -> L6a
            android.net.Uri r10 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60 java.lang.NullPointerException -> L6a
            java.lang.String[] r6 = new java.lang.String[]{r0}     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60 java.lang.NullPointerException -> L6a
            java.lang.String r7 = "_data=?"
            java.lang.String[] r8 = new java.lang.String[]{r12}     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60 java.lang.NullPointerException -> L6a
            r9 = 0
            r5 = r10
            android.database.Cursor r4 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L60 java.lang.NullPointerException -> L6a
            if (r4 == 0) goto L3a
            boolean r5 = r4.moveToFirst()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            if (r5 == 0) goto L3a
            int r12 = r4.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            int r12 = r4.getInt(r12)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            android.net.Uri r12 = android.net.Uri.withAppendedPath(r10, r12)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
        L35:
            r3 = r12
            goto L58
        L37:
            r11 = move-exception
            r3 = r4
            goto L8c
        L3a:
            java.io.File r0 = new java.io.File     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            r0.<init>(r12)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            boolean r0 = r0.exists()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            if (r0 == 0) goto L58
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            r0.<init>()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            java.lang.String r5 = "_data"
            r0.put(r5, r12)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            android.content.ContentResolver r12 = r11.getContentResolver()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            android.net.Uri r12 = r12.insert(r10, r0)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L61 java.lang.NullPointerException -> L6b
            goto L35
        L58:
            if (r4 == 0) goto L79
            r4.close()     // Catch: java.lang.Exception -> L76
            goto L79
        L5e:
            r11 = move-exception
            goto L8c
        L60:
            r4 = r3
        L61:
            com.huawei.hms.scankit.p.o4.b(r2, r1)     // Catch: java.lang.Throwable -> L37
            if (r4 == 0) goto L79
            r4.close()     // Catch: java.lang.Exception -> L76
            goto L79
        L6a:
            r4 = r3
        L6b:
            java.lang.String r12 = "NullPointerException"
            com.huawei.hms.scankit.p.o4.b(r2, r12)     // Catch: java.lang.Throwable -> L37
            if (r4 == 0) goto L79
            r4.close()     // Catch: java.lang.Exception -> L76
            goto L79
        L76:
            com.huawei.hms.scankit.p.o4.b(r2, r1)
        L79:
            android.graphics.BitmapFactory$Options r12 = new android.graphics.BitmapFactory$Options
            r12.<init>()
            r0 = 1
            r12.inJustDecodeBounds = r0
            getBitmapFromUri(r11, r3, r12)
            dealBitmapFactoryOption(r11, r12)
            android.graphics.Bitmap r11 = getBitmapFromUri(r11, r3, r12)
            return r11
        L8c:
            if (r3 == 0) goto L95
            r3.close()     // Catch: java.lang.Exception -> L92
            goto L95
        L92:
            com.huawei.hms.scankit.p.o4.b(r2, r1)
        L95:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hmsscankit.ScanUtil.compressBitmapForAndroid29(android.content.Context, java.lang.String):android.graphics.Bitmap");
    }

    private static void dealBitmapFactoryOption(Context context, BitmapFactory.Options options) {
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

    public static HmsScanResult decode(Context context, HmsScanFrame hmsScanFrame, HmsScanFrameOptions hmsScanFrameOptions) {
        if (hmsScanFrame == null || (hmsScanFrame.getYuvImage() == null && hmsScanFrame.getBitmap() == null)) {
            return new HmsScanResult(4, new HmsScan[0]);
        }
        o4.d("Scankit", "frame height " + hmsScanFrame.getHeight() + " width " + hmsScanFrame.getWidth());
        if (hmsScanFrame.getHeight() * hmsScanFrame.getHeight() > 52428800 || hmsScanFrame.getHeight() * hmsScanFrame.getHeight() == 0) {
            o4.e("ScanUtil", "input image is invalid:" + hmsScanFrame.getWidth() + " " + hmsScanFrame.getHeight());
            return new HmsScanResult(4, new HmsScan[0]);
        }
        try {
            if (g.f16576d == Integer.MIN_VALUE) {
                g.f16576d = g.a(context);
            }
            if (g.f16577e == Integer.MIN_VALUE) {
                g.f16577e = DynamicModule.getRemoteVersion(context.getApplicationContext(), MODULE_SCANKIT);
            }
        } catch (Exception unused) {
            o4.b("Scankit", "get remote version failed");
        }
        if (hmsScanFrameOptions != null && hmsScanFrameOptions.isMultiMode()) {
            HmsScan[] hmsScanArrA = b.a(context, hmsScanFrame, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(hmsScanFrameOptions.getType(), new int[0]).setPhotoMode(hmsScanFrameOptions.isPhotoMode()).setParseResult(hmsScanFrameOptions.isParseResult()).create());
            return hmsScanArrA.length == 0 ? new HmsScanResult(4096, hmsScanArrA) : (hmsScanArrA.length == 1 && hmsScanArrA[0].getZoomValue() > 1.0d && TextUtils.isEmpty(hmsScanArrA[0].getOriginalValue())) ? new HmsScanResult(4098, hmsScanArrA) : (hmsScanArrA.length < 1 || TextUtils.isEmpty(hmsScanArrA[0].getOriginalValue())) ? new HmsScanResult(4096, hmsScanArrA) : new HmsScanResult(0, hmsScanArrA);
        }
        HmsScanAnalyzerOptions hmsScanAnalyzerOptionsCreate = hmsScanFrameOptions == null ? new HmsScanAnalyzerOptions.Creator().create() : new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(hmsScanFrameOptions.getType(), new int[0]).setPhotoMode(hmsScanFrameOptions.isPhotoMode()).setParseResult(hmsScanFrameOptions.isParseResult()).create();
        if (hmsScanFrame.getBitmap() != null) {
            return checkHmsScan(f.a(context, hmsScanFrame.getBitmap(), hmsScanAnalyzerOptionsCreate), hmsScanFrameOptions);
        }
        if (hmsScanFrame.getYuvImage() == null) {
            return new HmsScanResult(4, new HmsScan[0]);
        }
        Log.i("scankit", "local version " + g.f16576d + " remote version" + g.f16577e);
        return checkVersion(g.f16576d, g.f16577e) ? f.a(context, hmsScanFrame, hmsScanAnalyzerOptionsCreate) : f.a(context, hmsScanFrame.getYuvImage().getYuvData(), hmsScanFrame.getYuvImage().getWidth(), hmsScanFrame.getYuvImage().getHeight(), hmsScanAnalyzerOptionsCreate);
    }

    public static HmsScan[] decodeWithBitmap(Context context, Bitmap bitmap, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) {
        if (bitmap != null && bitmap.getWidth() * bitmap.getHeight() <= 52428800) {
            return f.a(context, bitmap, hmsScanAnalyzerOptions);
        }
        if (bitmap != null) {
            o4.e("ScanUtil", "input image is too large:" + bitmap.getWidth());
        }
        return new HmsScan[0];
    }

    public static HmsScan[] detectForHmsDector(Context context, MLFrame mLFrame, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) {
        return b.a(context, mLFrame, hmsScanAnalyzerOptions);
    }

    private static Bitmap getBitmapFromUri(Context context, Uri uri, BitmapFactory.Options options) throws IOException {
        if (uri == null) {
            o4.a("ScanBitmap", "uri == null");
            return null;
        }
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            Bitmap bitmapDecodeFileDescriptor = BitmapFactory.decodeFileDescriptor(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor(), null, options);
            parcelFileDescriptorOpenFileDescriptor.close();
            return bitmapDecodeFileDescriptor;
        } catch (FileNotFoundException unused) {
            o4.b("exception", "FileNotFoundException");
            return null;
        } catch (IOException unused2) {
            o4.b("exception", "IOException");
            return null;
        } catch (Exception unused3) {
            o4.b("exception", "Exception");
            return null;
        }
    }

    public static boolean isScanAvailable(Context context) {
        return true;
    }

    public static boolean selfPermissionGranted(Context context, int i2, String str) {
        if (i2 >= 23) {
            if (w7.a(str) == null || context.checkSelfPermission(str) == 0) {
                return true;
            }
        } else if (w7.a(context, str) == 0) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int startScan(android.app.Activity r5, int r6, com.huawei.hms.ml.scan.HmsScanAnalyzerOptions r7) {
        /*
            java.lang.String r0 = "exception"
            java.lang.String r1 = "startScan before"
            java.lang.String r2 = "ScanUtil"
            com.huawei.hms.scankit.p.o4.d(r2, r1)
            android.content.pm.PackageManager r1 = r5.getPackageManager()     // Catch: java.lang.RuntimeException -> L1c android.content.pm.PackageManager.NameNotFoundException -> L22
            java.lang.String r3 = r5.getPackageName()     // Catch: java.lang.RuntimeException -> L1c android.content.pm.PackageManager.NameNotFoundException -> L22
            r4 = 16384(0x4000, float:2.2959E-41)
            android.content.pm.PackageInfo r1 = r1.getPackageInfo(r3, r4)     // Catch: java.lang.RuntimeException -> L1c android.content.pm.PackageManager.NameNotFoundException -> L22
            android.content.pm.ApplicationInfo r1 = r1.applicationInfo     // Catch: java.lang.RuntimeException -> L1c android.content.pm.PackageManager.NameNotFoundException -> L22
            int r0 = r1.targetSdkVersion     // Catch: java.lang.RuntimeException -> L1c android.content.pm.PackageManager.NameNotFoundException -> L22
            goto L29
        L1c:
            java.lang.String r1 = "RuntimeException"
            com.huawei.hms.scankit.p.o4.b(r0, r1)
            goto L27
        L22:
            java.lang.String r1 = "NameNotFoundException"
            com.huawei.hms.scankit.p.o4.b(r0, r1)
        L27:
            r0 = 28
        L29:
            java.lang.String r1 = "android.permission.CAMERA"
            boolean r0 = selfPermissionGranted(r5, r0, r1)
            boolean r1 = com.huawei.hms.scankit.p.w7.f17978c
            if (r1 != 0) goto L3a
            java.lang.String r5 = "startScan failed"
            com.huawei.hms.scankit.p.o4.d(r2, r5)
            r5 = 3
            return r5
        L3a:
            if (r0 != 0) goto L45
            if (r7 == 0) goto L43
            boolean r0 = r7.showGuide
            if (r0 == 0) goto L43
            goto L45
        L43:
            r5 = 1
            return r5
        L45:
            android.content.Intent r0 = new android.content.Intent
            java.lang.Class<com.huawei.hms.hmsscankit.ScanKitActivity> r1 = com.huawei.hms.hmsscankit.ScanKitActivity.class
            r0.<init>(r5, r1)
            if (r7 == 0) goto L6a
            int r1 = r7.mode
            java.lang.String r3 = "ScanFormatValue"
            r0.putExtra(r3, r1)
            int r1 = r7.viewType
            java.lang.String r3 = "ScanViewValue"
            r0.putExtra(r3, r1)
            boolean r1 = r7.errorCheck
            java.lang.String r3 = "ScanErrorCheck"
            r0.putExtra(r3, r1)
            boolean r7 = r7.showGuide
            java.lang.String r1 = "ScanGuide"
            r0.putExtra(r1, r7)
        L6a:
            java.lang.String r7 = "startScan success"
            com.huawei.hms.scankit.p.o4.d(r2, r7)
            r7 = 0
            com.huawei.hms.scankit.p.w7.f17978c = r7
            r5.startActivityForResult(r0, r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hmsscankit.ScanUtil.startScan(android.app.Activity, int, com.huawei.hms.ml.scan.HmsScanAnalyzerOptions):int");
    }
}
