package com.huawei.hms.feature.dynamic;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.common.util.ExtractNativeUtils;
import com.huawei.hms.common.util.Logger;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class AssetLoadManager {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16041a = "AssetLoadManager";

    /* renamed from: b, reason: collision with root package name */
    public static final String f16042b = "dynamic_modules";

    /* renamed from: c, reason: collision with root package name */
    public static final String f16043c = ".apk";

    /* renamed from: d, reason: collision with root package name */
    public static final String f16044d = "com.huawei.hms.feature.dynamic.descriptors.";

    /* renamed from: e, reason: collision with root package name */
    public static final String f16045e = ".AssetModuleDescriptor";

    public static int a(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.e(f16041a, "Invalid context or moduleName.");
            return 0;
        }
        try {
            return context.getClassLoader().loadClass(f16044d + str + f16045e).getDeclaredField("MODULE_VERSION").getInt(null);
        } catch (ClassNotFoundException unused) {
            Logger.w(f16041a, "Cannot get the class of module descriptor for " + str);
            return 0;
        } catch (Exception e2) {
            Logger.w(f16041a, "Get local asset module info failed.", e2);
            return 0;
        }
    }

    public static Bundle b(Context context, String str) throws Throwable {
        try {
            String[] list = context.getAssets().list("dynamic_modules" + File.separator + str);
            if (list != null && list.length != 0) {
                String str2 = list[0];
                int iA = a(context, str);
                String strA = a(context, str, iA, str2);
                if (!TextUtils.isEmpty(strA) && new File(strA).exists()) {
                    if (ExtractNativeUtils.a(context, strA) && ExtractNativeUtils.a(new File(strA), ModuleCopy.trimLastSection(strA)) != 0) {
                        Logger.w(f16041a, "Extract native to current dir failed.");
                        return new Bundle();
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString(b.f16126j, str);
                    bundle.putString(b.f16130n, strA);
                    bundle.putInt(b.f16129m, iA);
                    Logger.i(f16041a, "Get dynamic module info from asset success: ModuleName:" + str + ", ModuleVersion:" + iA + ", ModulePath:" + strA);
                    return bundle;
                }
                Logger.w(f16041a, "Decompress module from assets failed.");
                return new Bundle();
            }
            Logger.w(f16041a, "No module apk in asset path.");
            return new Bundle();
        } catch (Exception e2) {
            Logger.i(f16041a, "getModuleFromAsset failed.", e2);
            return new Bundle();
        }
    }

    public static Bundle getAssetModuleInfo(Context context, String str) throws Throwable {
        if (context == null || TextUtils.isEmpty(str)) {
            Logger.w(f16041a, "The context or moduleName is null.");
            return new Bundle();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(ModuleCopy.getProtectedPath(context));
            String str2 = File.separator;
            sb.append(str2);
            sb.append("dynamic_modules");
            sb.append(str2);
            sb.append(str);
            File file = new File(sb.toString());
            if (file.exists()) {
                Bundle bundleA = a(context, file, str);
                if (bundleA.getInt(b.f16129m) > 0) {
                    Logger.i(f16041a, "Successfully get module info from decompressed asset path.");
                    return bundleA;
                }
            }
            Bundle bundleB = b(context, str);
            if (bundleB.getInt(b.f16129m) > 0) {
                Logger.i(f16041a, "Successfully get module info from asset.");
                return bundleB;
            }
        } catch (Exception e2) {
            Logger.i(f16041a, "getDataModuleInfo failed.", e2);
        }
        return new Bundle();
    }

    public static Bundle a(Context context, File file, String str) throws IOException {
        String[] list = file.list();
        if (list == null || list.length == 0) {
            Logger.w(f16041a, "No version in module path.");
            return new Bundle();
        }
        int iMax = 0;
        for (String str2 : list) {
            iMax = Math.max(Integer.parseInt(str2), iMax);
        }
        if (iMax == 0) {
            Logger.w(f16041a, "Cannot get module version path.");
            return new Bundle();
        }
        try {
            String canonicalPath = file.getCanonicalPath();
            ModuleCopy.clearLowVersionModule(iMax, canonicalPath, list, f16041a);
            if (a(context, str) > iMax) {
                Logger.i(f16041a, "There is a higher loader version in assets.");
                return new Bundle();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(canonicalPath);
            String str3 = File.separator;
            sb.append(str3);
            sb.append(iMax);
            sb.append(str3);
            sb.append(str);
            sb.append(".apk");
            String string = sb.toString();
            if (!new File(string).exists()) {
                Logger.w(f16041a, "Cannot find module apk in asset decompressed path.");
                return new Bundle();
            }
            Bundle bundle = new Bundle();
            bundle.putString(b.f16126j, str);
            bundle.putString(b.f16130n, string);
            bundle.putInt(b.f16129m, iMax);
            Logger.i(f16041a, "Get module info from decompressed asset path success: ModuleName:" + str + ", ModuleVersion:" + iMax + ", ModulePath:" + string);
            return bundle;
        } catch (IOException e2) {
            Logger.w(f16041a, "request modulePath error: " + e2.getMessage());
            return new Bundle();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r11v9, types: [java.io.Closeable, java.io.InputStream] */
    public static String a(Context context, String str, int i2, String str2) throws Throwable {
        BufferedInputStream bufferedInputStream;
        FileOutputStream fileOutputStream;
        Closeable closeable;
        Closeable closeable2;
        String str3;
        String str4;
        Closeable closeable3 = null;
        try {
            try {
                AssetManager assets = context.getAssets();
                StringBuilder sb = new StringBuilder();
                sb.append("dynamic_modules");
                str3 = File.separator;
                sb.append(str3);
                sb.append(str);
                sb.append(str3);
                sb.append((String) str2);
                str2 = assets.open(sb.toString());
            } catch (Exception e2) {
                e = e2;
                str2 = 0;
            } catch (Throwable th) {
                th = th;
                str2 = 0;
            }
            try {
                bufferedInputStream = new BufferedInputStream(str2);
                try {
                    str4 = ModuleCopy.getProtectedPath(context) + str3 + "dynamic_modules" + str3 + str + str3 + ((int) i2);
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = null;
                    closeable = str2;
                } catch (Throwable th2) {
                    th = th2;
                    ModuleCopy.closeQuietly(bufferedInputStream);
                    ModuleCopy.closeQuietly(closeable3);
                    ModuleCopy.closeQuietly(str2);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = null;
                bufferedInputStream = null;
                closeable = str2;
                Logger.w(f16041a, "Cannot find module:" + str + " in assets.", e);
                ModuleCopy.closeQuietly(bufferedInputStream);
                ModuleCopy.closeQuietly(fileOutputStream);
                closeable2 = closeable;
                ModuleCopy.closeQuietly(closeable2);
                return null;
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = null;
                ModuleCopy.closeQuietly(bufferedInputStream);
                ModuleCopy.closeQuietly(closeable3);
                ModuleCopy.closeQuietly(str2);
                throw th;
            }
            if (!new File(str4).exists() && !new File(str4).mkdirs()) {
                Logger.w(f16041a, "mkdirs local loaderPath failed.");
                ModuleCopy.closeQuietly(bufferedInputStream);
                ModuleCopy.closeQuietly(null);
                closeable2 = str2;
                ModuleCopy.closeQuietly(closeable2);
                return null;
            }
            String str5 = str4 + str3 + str + ".apk";
            fileOutputStream = new FileOutputStream(str5);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int i3 = bufferedInputStream.read(bArr, 0, 4096);
                    if (i3 == -1) {
                        Logger.i(f16041a, "Decompress module:" + str + " from assets success.");
                        ModuleCopy.closeQuietly(bufferedInputStream);
                        ModuleCopy.closeQuietly(fileOutputStream);
                        ModuleCopy.closeQuietly(str2);
                        return str5;
                    }
                    fileOutputStream.write(bArr, 0, i3);
                }
            } catch (Exception e5) {
                e = e5;
                closeable = str2;
                Logger.w(f16041a, "Cannot find module:" + str + " in assets.", e);
                ModuleCopy.closeQuietly(bufferedInputStream);
                ModuleCopy.closeQuietly(fileOutputStream);
                closeable2 = closeable;
                ModuleCopy.closeQuietly(closeable2);
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
            closeable3 = i2;
        }
    }
}
