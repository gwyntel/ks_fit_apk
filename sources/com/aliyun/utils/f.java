package com.aliyun.utils;

import android.util.Log;
import com.aliyun.common.AlivcNativeLoader;
import com.aliyun.dns.DomainProcessor;
import com.aliyun.loader.MediaLoader;
import com.aliyun.loader.VodMediaLoader;
import com.aliyun.player.AliPlayerGlobalSettings;
import com.aliyun.player.BuildConfig;
import com.aliyun.player.HlsKeyGenerator;
import com.aliyun.player.nativeclass.JniListPlayerBase;
import com.aliyun.player.nativeclass.JniSaasListPlayer;
import com.aliyun.player.nativeclass.JniSaasPlayer;
import com.aliyun.player.nativeclass.JniUrlListPlayer;
import com.aliyun.player.nativeclass.JniUrlPlayer;
import com.aliyun.player.nativeclass.NativeExternalPlayer;
import com.aliyun.player.nativeclass.NativePlayerBase;
import com.aliyun.private_service.PrivateService;
import com.aliyun.thumbnail.ThumbnailHelper;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.ass.AssUtils;

/* loaded from: classes3.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final String f12140a = "f";

    /* renamed from: b, reason: collision with root package name */
    private static boolean f12141b = false;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f12142c = false;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f12143d = false;

    /* renamed from: e, reason: collision with root package name */
    private static boolean f12144e = false;

    public static synchronized boolean a() {
        try {
            if (c()) {
                return true;
            }
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace.length > 4 ? stackTrace[3].getMethodName() : "";
            Log.e(f12140a, "downloader so not loaded, ignore api call: " + methodName);
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized boolean b() {
        try {
            if (d()) {
                return true;
            }
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            String methodName = stackTrace.length > 4 ? stackTrace[3].getMethodName() : "";
            Log.e(f12140a, "player so not loaded, ignore api call: " + methodName);
            return false;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x000f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized boolean c() {
        /*
            java.lang.Class<com.aliyun.utils.f> r0 = com.aliyun.utils.f.class
            monitor-enter(r0)
            boolean r1 = com.aliyun.utils.f.f12144e     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            boolean r1 = com.aliyun.utils.f.f12143d     // Catch: java.lang.Throwable -> Ld
            if (r1 == 0) goto Lf
            r1 = 1
            goto L10
        Ld:
            r1 = move-exception
            goto L12
        Lf:
            r1 = 0
        L10:
            monitor-exit(r0)
            return r1
        L12:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.f.c():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x000f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized boolean d() {
        /*
            java.lang.Class<com.aliyun.utils.f> r0 = com.aliyun.utils.f.class
            monitor-enter(r0)
            boolean r1 = com.aliyun.utils.f.f12142c     // Catch: java.lang.Throwable -> Ld
            if (r1 != 0) goto Lf
            boolean r1 = com.aliyun.utils.f.f12141b     // Catch: java.lang.Throwable -> Ld
            if (r1 == 0) goto Lf
            r1 = 1
            goto L10
        Ld:
            r1 = move-exception
            goto L12
        Lf:
            r1 = 0
        L10:
            monitor-exit(r0)
            return r1
        L12:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Ld
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.utils.f.d():boolean");
    }

    public static synchronized void e() {
        f();
        if (f12144e) {
            Log.e(f12140a, "Force not load downloader for test!");
        } else {
            if (f12143d) {
                return;
            }
            f12143d = AlivcNativeLoader.loadLibrary("saasDownloader");
        }
    }

    public static synchronized void f() {
        if (f12142c) {
            Log.e(f12140a, "Force not load player for test!");
            return;
        }
        if (f12141b) {
            return;
        }
        boolean z2 = true;
        f12141b = true;
        JniUrlPlayer.loadClass();
        JniSaasPlayer.loadClass();
        JniListPlayerBase.loadClass();
        JniUrlListPlayer.loadClass();
        JniSaasListPlayer.loadClass();
        PrivateService.loadClass();
        DomainProcessor.loadClass();
        MediaLoader.loadClass();
        VodMediaLoader.loadClass();
        AliPlayerGlobalSettings.loadClass();
        HlsKeyGenerator.loadClass();
        NativeExternalPlayer.loadClass();
        NativePlayerBase.loadClass();
        ThumbnailHelper.loadClass();
        DeviceInfoUtils.loadClass();
        Logger.loadClass();
        AssUtils.loadClass();
        boolean zLoadLibrary = AlivcNativeLoader.loadLibrary("alivcffmpeg");
        f12141b = zLoadLibrary;
        if (!zLoadLibrary || !AlivcNativeLoader.loadLibrary(BuildConfig.PLAYER_LIB_NAME)) {
            z2 = false;
        }
        f12141b = z2;
    }
}
