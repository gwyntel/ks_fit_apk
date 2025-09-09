package org.android.spdy;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.facebook.internal.AnalyticsEvents;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes5.dex */
public class SoInstallMgrSdk {
    private static final String ARMEABI = "armeabi";
    private static final int EventID_SO_INIT = 21033;
    static final String LOGTAG = "INIT_SO";
    private static final String MIPS = "mips";
    private static final String X86 = "x86";
    static Context mContext;

    private static String _cpuType() {
        String str_getFieldReflectively = _getFieldReflectively(new Build(), "CPU_ABI");
        if (str_getFieldReflectively == null || str_getFieldReflectively.length() == 0 || str_getFieldReflectively.equals(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN)) {
            str_getFieldReflectively = ARMEABI;
        }
        return str_getFieldReflectively.toLowerCase();
    }

    private static String _getFieldReflectively(Build build, String str) {
        try {
            return Build.class.getField(str).get(build).toString();
        } catch (Exception unused) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
    }

    static boolean _loadUnzipSo(String str, int i2, ClassLoader classLoader) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            if (isExist(str, i2)) {
                if (classLoader == null) {
                    System.load(_targetSoFile(str, i2));
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    Method declaredMethod = Runtime.class.getDeclaredMethod("load", String.class, ClassLoader.class);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(runtime, _targetSoFile(str, i2), classLoader);
                }
            }
            return true;
        } catch (UnsatisfiedLinkError e2) {
            e2.printStackTrace();
            return false;
        } catch (Error e3) {
            e3.printStackTrace();
            return false;
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
    }

    static String _targetSoFile(String str, int i2) {
        Context context = mContext;
        if (context == null) {
            return "";
        }
        String path = "/data/data/" + context.getPackageName() + "/files";
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            path = filesDir.getPath();
        }
        return path + "/lib" + str + "bk" + i2 + ".so";
    }

    public static void init(Context context) {
        mContext = context;
    }

    public static boolean initSo(String str, int i2) {
        return initSo(str, i2, null);
    }

    static boolean isExist(String str, int i2) {
        return new File(_targetSoFile(str, i2)).exists();
    }

    static void removeSoIfExit(String str, int i2) {
        File file = new File(_targetSoFile(str, i2));
        if (file.exists()) {
            file.delete();
        }
    }

    static boolean unZipSelectedFiles(String str, int i2, ClassLoader classLoader) throws Throwable {
        Context context;
        FileChannel fileChannel;
        FileOutputStream fileOutputStreamOpenFileOutput;
        String str2 = "lib/armeabi/lib" + str + ".so";
        try {
            context = mContext;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (context == null) {
            return false;
        }
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        ZipFile zipFile = new ZipFile(applicationInfo != null ? applicationInfo.sourceDir : "");
        Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
            String name = zipEntryNextElement.getName();
            if (!name.contains("..") && !name.contains("\\") && !name.contains("%")) {
                if (zipEntryNextElement.getName().startsWith(str2)) {
                    InputStream inputStream = null;
                    FileChannel channel = null;
                    try {
                        removeSoIfExit(str, i2);
                        InputStream inputStream2 = zipFile.getInputStream(zipEntryNextElement);
                        try {
                            fileOutputStreamOpenFileOutput = context.openFileOutput("lib" + str + "bk" + i2 + ".so", 0);
                            try {
                                channel = fileOutputStreamOpenFileOutput.getChannel();
                                byte[] bArr = new byte[1024];
                                int i3 = 0;
                                while (true) {
                                    int i4 = inputStream2.read(bArr);
                                    if (i4 > 0) {
                                        channel.write(ByteBuffer.wrap(bArr, 0, i4));
                                        i3 += i4;
                                    } else {
                                        try {
                                            break;
                                        } catch (Exception e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                }
                                inputStream2.close();
                                if (channel != null) {
                                    try {
                                        channel.close();
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                try {
                                    fileOutputStreamOpenFileOutput.close();
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                                zipFile.close();
                                if (i3 > 0) {
                                    return _loadUnzipSo(str, i2, classLoader);
                                }
                                return false;
                            } catch (Throwable th) {
                                th = th;
                                fileChannel = channel;
                                inputStream = inputStream2;
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (Exception e6) {
                                        e6.printStackTrace();
                                    }
                                }
                                if (fileChannel != null) {
                                    try {
                                        fileChannel.close();
                                    } catch (Exception e7) {
                                        e7.printStackTrace();
                                    }
                                }
                                if (fileOutputStreamOpenFileOutput != null) {
                                    try {
                                        fileOutputStreamOpenFileOutput.close();
                                    } catch (Exception e8) {
                                        e8.printStackTrace();
                                    }
                                }
                                zipFile.close();
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileChannel = null;
                            fileOutputStreamOpenFileOutput = null;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileChannel = null;
                        fileOutputStreamOpenFileOutput = null;
                    }
                }
            }
            return false;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0041 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean initSo(java.lang.String r7, int r8, java.lang.ClassLoader r9) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            r0 = 2
            r1 = 0
            r2 = 1
            if (r9 != 0) goto Lf
            java.lang.System.loadLibrary(r7)     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            goto L3f
        L9:
            r0 = move-exception
            goto L32
        Lb:
            r0 = move-exception
            goto L37
        Ld:
            r0 = move-exception
            goto L3b
        Lf:
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            java.lang.Class[] r4 = new java.lang.Class[r0]     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            r4[r1] = r5     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            java.lang.Class<java.lang.ClassLoader> r5 = java.lang.ClassLoader.class
            r4[r2] = r5     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            java.lang.Class<java.lang.Runtime> r5 = java.lang.Runtime.class
            java.lang.String r6 = "loadLibrary"
            java.lang.reflect.Method r4 = r5.getDeclaredMethod(r6, r4)     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            r4.setAccessible(r2)     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            r0[r1] = r7     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            r0[r2] = r9     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            r4.invoke(r3, r0)     // Catch: java.lang.Error -> L9 java.lang.UnsatisfiedLinkError -> Lb java.lang.Exception -> Ld
            goto L3f
        L32:
            r0.printStackTrace()
        L35:
            r2 = r1
            goto L3f
        L37:
            r0.printStackTrace()
            goto L35
        L3b:
            r0.printStackTrace()
            goto L35
        L3f:
            if (r2 != 0) goto L89
            boolean r0 = isExist(r7, r8)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            if (r0 == 0) goto L58
            boolean r0 = _loadUnzipSo(r7, r8, r9)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            if (r0 == 0) goto L4e
            return r0
        L4e:
            removeSoIfExit(r7, r8)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            goto L58
        L52:
            r7 = move-exception
            goto L7d
        L54:
            r7 = move-exception
            goto L81
        L56:
            r7 = move-exception
            goto L85
        L58:
            java.lang.String r0 = _cpuType()     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            java.lang.String r3 = "mips"
            boolean r3 = r0.equalsIgnoreCase(r3)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            if (r3 != 0) goto L89
            java.lang.String r3 = "x86"
            boolean r0 = r0.equalsIgnoreCase(r3)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            if (r0 == 0) goto L6d
            goto L89
        L6d:
            boolean r7 = unZipSelectedFiles(r7, r8, r9)     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56 java.io.IOException -> L73 java.util.zip.ZipException -> L78
            r1 = r7
            goto L8a
        L73:
            r7 = move-exception
            r7.printStackTrace()     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            goto L89
        L78:
            r7 = move-exception
            r7.printStackTrace()     // Catch: java.lang.Error -> L52 java.lang.UnsatisfiedLinkError -> L54 java.lang.Exception -> L56
            goto L89
        L7d:
            r7.printStackTrace()
            goto L8a
        L81:
            r7.printStackTrace()
            goto L8a
        L85:
            r7.printStackTrace()
            goto L8a
        L89:
            r1 = r2
        L8a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SoInstallMgrSdk.initSo(java.lang.String, int, java.lang.ClassLoader):boolean");
    }
}
