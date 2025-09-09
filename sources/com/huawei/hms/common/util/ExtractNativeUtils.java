package com.huawei.hms.common.util;

import android.content.Context;
import android.os.Build;
import com.huawei.hms.feature.dynamic.ModuleCopy;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes4.dex */
public class ExtractNativeUtils {

    /* renamed from: a, reason: collision with root package name */
    public static final String f16011a = "ExtractNativeUtils";

    /* renamed from: b, reason: collision with root package name */
    public static final int f16012b = 0;

    /* renamed from: c, reason: collision with root package name */
    public static final int f16013c = -1;

    /* renamed from: d, reason: collision with root package name */
    public static final int f16014d = 50;

    /* renamed from: e, reason: collision with root package name */
    public static final int f16015e = 52428800;

    /* renamed from: f, reason: collision with root package name */
    public static final Pattern f16016f = Pattern.compile("lib/([^/]+)/(.*\\.so)$");

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public String f16017a;

        /* renamed from: b, reason: collision with root package name */
        public ZipEntry f16018b;

        /* renamed from: c, reason: collision with root package name */
        public String f16019c;

        public b(ZipEntry zipEntry, String str, String str2) {
            this.f16018b = zipEntry;
            this.f16017a = str;
            this.f16019c = str2;
        }
    }

    public static int a(File file, String str) throws Throwable {
        Logger.i(f16011a, "begin extractNativeLibrary");
        int i2 = 0;
        ZipFile zipFile = null;
        try {
            try {
                ZipFile zipFile2 = new ZipFile(file);
                try {
                    try {
                        Enumeration<? extends ZipEntry> enumerationEntries = zipFile2.entries();
                        HashMap map = new HashMap();
                        int iA = a(enumerationEntries, (HashMap<String, HashSet<b>>) map, 0);
                        if (iA == -1) {
                            Logger.e(f16011a, "Unsafe zip name!");
                            try {
                                zipFile2.close();
                            } catch (IOException e2) {
                                Logger.e(f16011a, "IOException:", e2);
                            }
                            return -1;
                        }
                        if (iA > 50) {
                            Logger.e(f16011a, "the total number is larger than the max");
                            try {
                                zipFile2.close();
                            } catch (IOException e3) {
                                Logger.e(f16011a, "IOException:", e3);
                            }
                            return -1;
                        }
                        Iterator it = map.keySet().iterator();
                        int iA2 = 0;
                        while (it.hasNext()) {
                            try {
                                Set<b> set = (Set) map.get((String) it.next());
                                if (set == null) {
                                    Logger.e(f16011a, "Get nativeZipEntries failed.");
                                    try {
                                        zipFile2.close();
                                    } catch (IOException e4) {
                                        Logger.e(f16011a, "IOException:", e4);
                                    }
                                    return -1;
                                }
                                for (b bVar : set) {
                                    String str2 = str + File.separator + bVar.f16019c;
                                    ModuleCopy.makeDirectory(str2);
                                    new File(str2).setExecutable(true, false);
                                    iA2 = a(zipFile2, bVar, str2);
                                    if (iA2 != 0) {
                                        try {
                                            zipFile2.close();
                                        } catch (IOException e5) {
                                            Logger.e(f16011a, "IOException:", e5);
                                        }
                                        return iA2;
                                    }
                                    new File(str2, bVar.f16017a).setReadable(true, false);
                                }
                            } catch (IOException e6) {
                                e = e6;
                                i2 = iA2;
                                zipFile = zipFile2;
                                Logger.e(f16011a, "catch IOException ", e);
                                if (zipFile != null) {
                                    try {
                                        zipFile.close();
                                    } catch (IOException e7) {
                                        e = e7;
                                        iA2 = i2;
                                        Logger.e(f16011a, "IOException:", e);
                                        return iA2;
                                    }
                                }
                                return i2;
                            }
                        }
                        try {
                            zipFile2.close();
                            return iA2;
                        } catch (IOException e8) {
                            e = e8;
                            Logger.e(f16011a, "IOException:", e);
                            return iA2;
                        }
                    } catch (Throwable th) {
                        th = th;
                        zipFile = zipFile2;
                        if (zipFile != null) {
                            try {
                                zipFile.close();
                            } catch (IOException e9) {
                                Logger.e(f16011a, "IOException:", e9);
                            }
                        }
                        throw th;
                    }
                } catch (IOException e10) {
                    e = e10;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e11) {
            e = e11;
        }
    }

    public static int a(Enumeration enumeration, HashMap<String, HashSet<b>> map, int i2) {
        while (enumeration.hasMoreElements()) {
            Object objNextElement = enumeration.nextElement();
            if (objNextElement != null && (objNextElement instanceof ZipEntry)) {
                ZipEntry zipEntry = (ZipEntry) objNextElement;
                String name = zipEntry.getName();
                if (name.contains("../")) {
                    Logger.e(f16011a, "Unsafe zip name!");
                    return -1;
                }
                Matcher matcher = f16016f.matcher(name);
                if (matcher.matches()) {
                    String strGroup = matcher.group(1);
                    String strGroup2 = matcher.group(2);
                    HashSet<b> hashSet = map.get(strGroup);
                    if (hashSet == null) {
                        hashSet = new HashSet<>();
                        map.put(strGroup, hashSet);
                    }
                    hashSet.add(new b(zipEntry, strGroup2, strGroup));
                    i2++;
                }
            }
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x002d, code lost:
    
        com.huawei.hms.common.util.Logger.e(com.huawei.hms.common.util.ExtractNativeUtils.f16011a, "so file too big , " + r10.f16019c + " , " + r10.f16017a);
     */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00b8 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00a4 -> B:77:0x00a7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.util.zip.ZipFile r9, com.huawei.hms.common.util.ExtractNativeUtils.b r10, java.lang.String r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 193
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.common.util.ExtractNativeUtils.a(java.util.zip.ZipFile, com.huawei.hms.common.util.ExtractNativeUtils$b, java.lang.String):int");
    }

    public static boolean a(Context context, String str) {
        if (Build.VERSION.SDK_INT <= 23) {
            Logger.i(f16011a, "The android version is below android 6.");
            return true;
        }
        try {
            if ((context.getPackageManager().getPackageArchiveInfo(str, 128).applicationInfo.flags & 268435456) == 268435456) {
                Logger.i(f16011a, "The extract-native-flag has set, need to extract.");
                return true;
            }
            Logger.i(f16011a, "The extract-native-flag has not set, No need to extract.");
            return false;
        } catch (Exception unused) {
            Logger.w(f16011a, "Get package name failed: name not found.");
            return true;
        }
    }
}
