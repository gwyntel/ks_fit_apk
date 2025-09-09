package com.aliyun.common;

import android.text.TextUtils;
import android.util.Log;
import com.aliyun.aio.keep.API;
import com.aliyun.alink.linksdk.securesigner.util.Utils;
import com.umeng.message.entity.UMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class AlivcNativeLoader {

    /* renamed from: a, reason: collision with root package name */
    private static String f11515a = null;

    /* renamed from: b, reason: collision with root package name */
    private static final String f11516b = "AlivcNativeLoader";

    /* renamed from: c, reason: collision with root package name */
    private static final List<String> f11517c = new ArrayList();

    /* renamed from: d, reason: collision with root package name */
    private static final byte[] f11518d = new byte[0];

    /* renamed from: e, reason: collision with root package name */
    private static LoadLibraryCallback f11519e = null;

    @API
    public interface LoadLibraryCallback {
        void loadResult(String str, boolean z2, LoaderMessage loaderMessage);
    }

    @API
    public static class LoaderMessage {
        public String fileMd5;
        public int type = 0;
        public String loadPath = "system";

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("type:");
            sb.append(this.type == 0 ? "system" : UMessage.DISPLAY_TYPE_CUSTOM);
            sb.append(" loadPath:");
            sb.append(this.loadPath);
            sb.append(" fileMd5:");
            sb.append(this.fileMd5);
            return sb.toString();
        }
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        static boolean f11520a = false;

        public static void a(String str) {
            if (f11520a) {
                Log.e(AlivcNativeLoader.f11516b, str);
            }
        }

        public static void b(String str) {
            if (f11520a) {
                Log.i(AlivcNativeLoader.f11516b, str);
            }
        }

        public static void a(String str, Throwable th) {
            if (f11520a) {
                Log.e(AlivcNativeLoader.f11516b, str, th);
            }
        }
    }

    private static void b() {
        b("alivcffmpeg");
        b("all_in_one");
    }

    private static boolean c(String str) {
        boolean z2;
        try {
            System.loadLibrary(str);
            z2 = true;
        } catch (Throwable th) {
            a.a("failed to System.loadLibrary lib" + str + ".so", th);
            z2 = false;
        }
        if (z2) {
            synchronized (f11518d) {
                try {
                    List<String> list = f11517c;
                    if (!list.contains(str)) {
                        list.add(str);
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            a(str);
        } else {
            a(str, new LoaderMessage());
        }
        return z2;
    }

    @API
    public static String getNativePath() {
        return f11515a;
    }

    @API
    public static boolean loadLibrary(String str) {
        synchronized (f11518d) {
            try {
                if (f11517c.contains(str)) {
                    return true;
                }
                if (!TextUtils.isEmpty(f11515a) && new File(f11515a).exists()) {
                    boolean zB = b(str);
                    if (!zB) {
                        a.a("load " + str + " from custom error, try to load from system");
                        zB = c(str);
                        if (!zB) {
                            a.a("load " + str + " from system error");
                        }
                    }
                    return zB;
                }
                return c(str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @API
    public static boolean setNativePath(String str) {
        if (!new File(str).exists()) {
            return false;
        }
        f11515a = str;
        b();
        a.f11520a = true;
        return true;
    }

    public static String a(File file) throws NoSuchAlgorithmException, IOException {
        int i2;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(Utils.MD5);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[8192];
            while (true) {
                try {
                    int i3 = fileInputStream.read(bArr);
                    if (i3 <= 0) {
                        break;
                    }
                    messageDigest.update(bArr, 0, i3);
                } catch (Throwable th) {
                    fileInputStream.close();
                    throw th;
                }
            }
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b2 : bArrDigest) {
                String hexString = Integer.toHexString(b2 & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            String string = sb.toString();
            fileInputStream.close();
            return string;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static boolean b(String str) {
        boolean z2;
        String str2 = f11515a + "/lib" + str + ".so";
        try {
            System.load(str2);
            z2 = true;
        } catch (Throwable th) {
            a.a("failed to System.load lib" + str + ".so", th);
            z2 = false;
        }
        if (z2) {
            synchronized (f11518d) {
                try {
                    List<String> list = f11517c;
                    if (!list.contains(str)) {
                        list.add(str);
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            a(str);
        } else {
            LoaderMessage loaderMessage = new LoaderMessage();
            loaderMessage.type = 1;
            loaderMessage.loadPath = str2;
            loaderMessage.fileMd5 = a(new File(str2));
            a(str, loaderMessage);
        }
        return z2;
    }

    @API
    public static boolean setNativePath(String str, LoadLibraryCallback loadLibraryCallback) {
        if (!new File(str).exists()) {
            return false;
        }
        f11515a = str;
        b();
        f11519e = loadLibraryCallback;
        a.f11520a = true;
        return true;
    }

    private static void a(String str) {
        LoaderMessage loaderMessage = new LoaderMessage();
        a.b("onLoadSuccess soName:" + str + " message: " + loaderMessage);
        LoadLibraryCallback loadLibraryCallback = f11519e;
        if (loadLibraryCallback != null) {
            loadLibraryCallback.loadResult(str, true, loaderMessage);
        }
    }

    private static void a(String str, LoaderMessage loaderMessage) {
        a.a("onLoadFailed soName:" + str + " message: " + loaderMessage);
        LoadLibraryCallback loadLibraryCallback = f11519e;
        if (loadLibraryCallback != null) {
            loadLibraryCallback.loadResult(str, false, loaderMessage);
        }
    }
}
