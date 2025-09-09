package com.huawei.hms.hmsscankit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.huawei.agconnect.AGConnectInstance;
import com.huawei.hms.feature.DynamicModuleInitializer;
import com.huawei.hms.feature.dynamic.ObjectWrapper;
import com.huawei.hms.hmsscankit.api.IRemoteCreator;
import com.huawei.hms.hmsscankit.api.IRemoteDecoderDelegate;
import com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.huawei.hms.ml.scan.HmsScanFrame;
import com.huawei.hms.ml.scan.HmsScanResult;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.s6;
import com.huawei.hms.scankit.p.u6;
import com.huawei.hms.scankit.p.w3;
import com.huawei.hms.scankit.p.w7;
import com.huawei.hms.scankit.p.x3;
import com.huawei.hms.scankit.p.y3;
import com.huawei.hms.scankit.p.y6;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static volatile x3 f16569a;

    /* renamed from: b, reason: collision with root package name */
    private static volatile IRemoteDecoderDelegate f16570b;

    /* renamed from: c, reason: collision with root package name */
    private static volatile w3 f16571c;

    /* renamed from: d, reason: collision with root package name */
    private static volatile IRemoteFrameDecoderDelegate f16572d;

    class a extends SimpleDateFormat {
        a(String str) {
            super(str);
            setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    static HmsScan[] a(Context context, Bitmap bitmap, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) {
        return a(context, bitmap, hmsScanAnalyzerOptions, hmsScanAnalyzerOptions.mode);
    }

    static HmsScan[] a(Context context, Bitmap bitmap, HmsScanAnalyzerOptions hmsScanAnalyzerOptions, int i2) {
        HmsScan[] hmsScanArr = new HmsScan[0];
        if (f16570b == null) {
            IRemoteCreator iRemoteCreatorC = g.c(context);
            if (iRemoteCreatorC == null) {
                return hmsScanArr;
            }
            try {
                f16570b = iRemoteCreatorC.newRemoteDecoderDelegate();
            } catch (RemoteException unused) {
                o4.b("ScankitRemoteDecoder", "RemoteException");
            }
        }
        if (f16570b == null) {
            return hmsScanArr;
        }
        try {
            Bundle bundle = new Bundle();
            if (hmsScanAnalyzerOptions != null) {
                bundle.putInt(DetailRect.FORMAT_FLAG, i2);
                bundle.putBoolean(DetailRect.PHOTO_MODE, hmsScanAnalyzerOptions.photoMode);
                bundle.putBoolean(DetailRect.PARSE_RESULT, hmsScanAnalyzerOptions.parseResult);
            }
            bundle.putInt(DetailRect.TYPE_TRANS, 3);
            bundle.putString(DetailRect.CP_PACKAGE, y3.b(context));
            bundle.putBoolean(DetailRect.SUPPORT_ROLLBACK, g.f16575c);
            bundle.putBoolean(DetailRect.USE_APK, g.f16573a);
            bundle.putAll(y3.a(context));
            HmsScan[] hmsScanArrDecodeWithBitmap = f16570b.decodeWithBitmap(ObjectWrapper.wrap(bitmap), ObjectWrapper.wrap(bundle));
            a(hmsScanArrDecodeWithBitmap);
            if (g.a()) {
                o4.d("ScankitRemoteDecoder", "iRemoteDecoderDelegate decodeWithBitmap rollback");
                IRemoteCreator iRemoteCreatorC2 = g.c(context);
                if (iRemoteCreatorC2 == null) {
                    return hmsScanArr;
                }
                try {
                    f16570b = iRemoteCreatorC2.newRemoteDecoderDelegate();
                } catch (RemoteException unused2) {
                    o4.b("ScankitRemoteDecoder", "RemoteException");
                }
                hmsScanArrDecodeWithBitmap = f16570b.decodeWithBitmap(ObjectWrapper.wrap(bitmap), ObjectWrapper.wrap(bundle));
            }
            return hmsScanArrDecodeWithBitmap != null ? hmsScanArrDecodeWithBitmap : hmsScanArr;
        } catch (RemoteException unused3) {
            o4.b("ScankitRemoteDecoder", "RemoteException");
            return hmsScanArr;
        }
    }

    public static void a(HmsScan[] hmsScanArr) {
        if (hmsScanArr.length == 1 && a(hmsScanArr[0].getCornerPoints())) {
            g.f16574b = true;
        } else {
            g.f16574b = false;
        }
    }

    public static void a(s6[] s6VarArr) {
        if (s6VarArr.length == 1 && a(s6VarArr[0].j())) {
            g.f16574b = true;
        } else {
            g.f16574b = false;
        }
    }

    private static boolean a(Point[] pointArr) {
        if (pointArr == null || pointArr.length == 0) {
            return false;
        }
        for (Point point : pointArr) {
            if (point.x != -2 && point.y != -2) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(u6[] u6VarArr) {
        if (u6VarArr == null || u6VarArr.length == 0) {
            return false;
        }
        for (u6 u6Var : u6VarArr) {
            if (u6Var.b() - (-2.0f) > 1.0E-4d && u6Var.c() - (-2.0f) > 1.0E-4d) {
                return false;
            }
        }
        return true;
    }

    static HmsScanResult a(Context context, byte[] bArr, int i2, int i3, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) throws IllegalAccessException, InstantiationException {
        HmsScanResult hmsScanResult = new HmsScanResult(4096, new HmsScan[0]);
        if (f16570b == null) {
            IRemoteCreator iRemoteCreatorC = g.c(context);
            if (iRemoteCreatorC == null) {
                return hmsScanResult;
            }
            try {
                f16570b = iRemoteCreatorC.newRemoteDecoderDelegate();
            } catch (RemoteException unused) {
                o4.b("ScankitRemoteDecoder", "RemoteException");
            }
        }
        if (f16570b == null) {
            return hmsScanResult;
        }
        try {
            Bundle bundle = new Bundle();
            if (hmsScanAnalyzerOptions != null) {
                bundle.putInt(DetailRect.FORMAT_FLAG, hmsScanAnalyzerOptions.mode);
                bundle.putBoolean(DetailRect.PHOTO_MODE, hmsScanAnalyzerOptions.photoMode);
                bundle.putBoolean(DetailRect.PARSE_RESULT, hmsScanAnalyzerOptions.parseResult);
            }
            bundle.putBoolean(DetailRect.SUPPORT_ROLLBACK, g.f16575c);
            bundle.putBoolean(DetailRect.USE_APK, g.f16573a);
            bundle.putInt(DetailRect.TYPE_TRANS, 3);
            bundle.putString(DetailRect.CP_PACKAGE, y3.b(context));
            bundle.putAll(y3.a(context));
            HmsScanResult hmsScanResultDecodeWithBuffer = f16570b.decodeWithBuffer(bArr, i2, i3, ObjectWrapper.wrap(bundle));
            if (hmsScanResultDecodeWithBuffer != null && hmsScanResultDecodeWithBuffer.getHmsScans() != null) {
                a(hmsScanResultDecodeWithBuffer.getHmsScans());
                if (g.a()) {
                    o4.d("ScankitRemoteDecoder", "iRemoteDecoderDelegate decodeWithBuffer rollback");
                    IRemoteCreator iRemoteCreatorC2 = g.c(context);
                    if (iRemoteCreatorC2 == null) {
                        return hmsScanResult;
                    }
                    try {
                        f16570b = iRemoteCreatorC2.newRemoteDecoderDelegate();
                    } catch (RemoteException unused2) {
                        o4.b("ScankitRemoteDecoder", "RemoteException");
                    }
                    hmsScanResultDecodeWithBuffer = f16570b.decodeWithBuffer(bArr, i2, i3, ObjectWrapper.wrap(bundle));
                }
            }
            return hmsScanResultDecodeWithBuffer != null ? hmsScanResultDecodeWithBuffer : hmsScanResult;
        } catch (RemoteException unused3) {
            o4.b("ScankitRemoteDecoder", "RemoteException");
            return hmsScanResult;
        }
    }

    static HmsScanResult a(Context context, HmsScanFrame hmsScanFrame, HmsScanAnalyzerOptions hmsScanAnalyzerOptions) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        w3.c cVarA;
        HmsScan hmsScan;
        new HmsScanResult(4096, new HmsScan[0]);
        Log.d("Scankit", "use decodeCompatibility");
        if (f16572d == null) {
            Log.d("ScankitRemoteDecoder", "use remote decoder");
            a(context);
        }
        if (f16572d != null) {
            try {
                Bundle bundle = new Bundle();
                bundle.putParcelable("Screen", new Point(hmsScanFrame.getWidth(), hmsScanFrame.getHeight()));
                bundle.putParcelable("Rect", new Rect(0, 0, hmsScanFrame.getWidth(), hmsScanFrame.getHeight()));
                bundle.putBoolean(DetailRect.SUPPORT_ROLLBACK, g.f16575c);
                bundle.putBoolean(DetailRect.USE_APK, g.f16573a);
                if (f16571c == null) {
                    try {
                        try {
                            f16571c = new w3(bundle, DetailRect.PHOTO_MODE);
                            f16571c.a("single");
                            cVarA = f16571c.a(false, hmsScanFrame.getHeight() * hmsScanFrame.getWidth());
                        } catch (RuntimeException unused) {
                            o4.b("ScankitRemoteDecoder", "RuntimeException");
                        }
                    } catch (Exception unused2) {
                        o4.b("ScankitRemoteDecoder", "Exception");
                    }
                } else {
                    cVarA = null;
                }
                int iB = hmsScanAnalyzerOptions != null ? w7.b(hmsScanAnalyzerOptions.mode) : 8191;
                s6[] s6VarArrDecode = f16572d.decode(hmsScanFrame.getYuvImage().getYuvData(), hmsScanFrame.getWidth(), hmsScanFrame.getHeight(), 0, iB, ObjectWrapper.wrap(bundle));
                a(s6VarArrDecode);
                if (g.a()) {
                    o4.b("ScankitRemoteDecoder", "iRemoteFrameDecoderDelegate decode rollback");
                    a(context);
                    s6VarArrDecode = f16572d.decode(hmsScanFrame.getYuvImage().getYuvData(), hmsScanFrame.getWidth(), hmsScanFrame.getHeight(), 0, iB, ObjectWrapper.wrap(bundle));
                }
                HmsScan[] hmsScanArrA = y6.a(s6VarArrDecode);
                if (f16571c != null) {
                    f16571c.a(hmsScanArrA, cVarA);
                }
                if (hmsScanArrA.length != 0 && (hmsScan = hmsScanArrA[0]) != null && hmsScan.getOriginalValue() != null && hmsScanArrA[0].getOriginalValue().length() != 0) {
                    return new HmsScanResult(0, hmsScanArrA);
                }
                return new HmsScanResult(4096, hmsScanArrA);
            } catch (RemoteException unused3) {
                Log.e("Scankit", "RemoteException");
            }
        }
        return new HmsScanResult(4096, new HmsScan[0]);
    }

    private static void a(Bundle bundle) {
        if (DynamicModuleInitializer.getContext() == null) {
            try {
                g.b(AGConnectInstance.getInstance().getContext());
            } catch (ClassNotFoundException unused) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog ClassNotFoundException");
            } catch (IllegalAccessException unused2) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog IllegalAccessException");
            } catch (NoClassDefFoundError unused3) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog NoClassDefFoundError");
                return;
            } catch (NoSuchMethodException unused4) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog NoSuchMethodException");
            } catch (InvocationTargetException unused5) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog InvocationTargetException");
            } catch (Exception unused6) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog Exception");
            }
        }
        if (f16569a == null) {
            try {
                f16569a = new x3();
                f16569a.c(bundle);
            } catch (RuntimeException unused7) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog RuntimeException");
            } catch (Exception unused8) {
                o4.b("ScankitRemoteDecoder", "buildBitmapLog Exception");
            }
        }
    }

    public static Bundle a(String str, int i2, int i3, int i4, HmsBuildBitmapOption hmsBuildBitmapOption) {
        Bundle bundle = new Bundle();
        bundle.putInt("contentLength", str == null ? -1 : str.length());
        bundle.putInt("scanType", i2);
        bundle.putInt("reqWidth", i3);
        bundle.putInt("reqHeight", i4);
        bundle.putString("buildBitmapOption", hmsBuildBitmapOption == null ? TmpConstant.GROUP_ROLE_UNKNOWN : hmsBuildBitmapOption.toString());
        bundle.putString("apiName", "BuildBitmap");
        bundle.putLong("callTime", System.currentTimeMillis());
        bundle.putString(HiAnalyticsConstant.HaKey.BI_KEY_TRANSID, UUID.randomUUID().toString());
        return bundle;
    }

    public static void a(int i2, Bitmap bitmap, Bundle bundle) {
        if (bundle != null) {
            bundle.putInt("result", i2);
            bundle.putInt("outputWidth", bitmap == null ? -1 : bitmap.getWidth());
            bundle.putInt("outputHeight", bitmap != null ? bitmap.getHeight() : -1);
            long j2 = bundle.getLong("callTime");
            bundle.putLong("costTime", System.currentTimeMillis() - j2);
            bundle.putString("callTime", new a("yyyyMMddHHmmss.SSS").format(Long.valueOf(j2)));
            a(bundle);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static void a(android.content.Context r3) throws java.lang.IllegalAccessException, java.lang.InstantiationException, java.lang.ClassNotFoundException {
        /*
            java.lang.String r0 = "ScankitRemoteDecoder"
            android.content.Context r3 = com.huawei.hms.hmsscankit.g.e(r3)     // Catch: java.lang.Throwable -> L6 java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
        L6:
            java.lang.ClassLoader r1 = r3.getClassLoader()     // Catch: java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
            java.lang.String r2 = "com.huawei.hms.scankit.DecoderCreator"
            java.lang.Class r1 = r1.loadClass(r2)     // Catch: java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
            java.lang.ClassLoader r3 = r3.getClassLoader()     // Catch: java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
            java.lang.String r2 = "com.huawei.hms.scankit.aiscan.common.BarcodeFormat"
            r3.loadClass(r2)     // Catch: java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
            java.lang.Object r3 = r1.newInstance()     // Catch: java.lang.InstantiationException -> L1e java.lang.ClassNotFoundException -> L24 java.lang.IllegalAccessException -> L2a
            goto L30
        L1e:
            java.lang.String r3 = "InstantiationException"
            com.huawei.hms.scankit.p.o4.a(r0, r3)
            goto L2f
        L24:
            java.lang.String r3 = "ClassNotFoundException"
            com.huawei.hms.scankit.p.o4.a(r0, r3)
            goto L2f
        L2a:
            java.lang.String r3 = "IllegalAccessException"
            com.huawei.hms.scankit.p.o4.a(r0, r3)
        L2f:
            r3 = 0
        L30:
            boolean r1 = r3 instanceof android.os.IBinder
            if (r1 == 0) goto L46
            android.os.IBinder r3 = (android.os.IBinder) r3
            com.huawei.hms.hmsscankit.api.IRemoteDecoderCreator r3 = com.huawei.hms.hmsscankit.api.IRemoteDecoderCreator.Stub.asInterface(r3)
            com.huawei.hms.hmsscankit.api.IRemoteFrameDecoderDelegate r3 = r3.newRemoteFrameDecoderDelegate()     // Catch: java.lang.Exception -> L41
            com.huawei.hms.hmsscankit.f.f16572d = r3     // Catch: java.lang.Exception -> L41
            goto L46
        L41:
            java.lang.String r3 = "remoteception"
            com.huawei.hms.scankit.p.o4.a(r0, r3)
        L46:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hmsscankit.f.a(android.content.Context):void");
    }
}
