package com.umeng.commonsdk.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.ay;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class UMFrUtils {
    private static final String KEY_LAST_INSTANT_SUCC_BUILD_TIME = "last_instant_build_time";
    private static final String KEY_LAST_SUCC_BUILD_TIME = "last_successful_build_time";
    private static Object mEnvelopeBuildTimeLock = new Object();
    private static String mDefaultEnvelopeDir = ay.b().b(ay.f21366a);
    private static String mDefaultEnvelopeDirPath = null;
    private static Object mEnvelopeFileLock = new Object();
    private static String sCurrentProcessName = "";

    private static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        try {
            return ((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", String.class).invoke(context, str)).intValue() == 0;
        } catch (Throwable th) {
            UMCrashManager.reportCrash(applicationContext, th);
            return false;
        }
    }

    public static int envelopeFileNumber(Context context) {
        String[] list;
        if (context != null) {
            try {
                File file = new File(getEnvelopeDirPath(context));
                synchronized (mEnvelopeFileLock) {
                    try {
                        if (file.isDirectory() && (list = file.list()) != null) {
                            return list.length;
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
            }
        }
        return 0;
    }

    public static String getCurrentProcessName(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (TextUtils.isEmpty(sCurrentProcessName)) {
            try {
                String processName = Build.VERSION.SDK_INT >= 28 ? Application.getProcessName() : "";
                if (TextUtils.isEmpty(processName)) {
                    int iMyPid = Process.myPid();
                    String processName2 = getProcessName(iMyPid);
                    if (TextUtils.isEmpty(processName2)) {
                        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
                        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null && runningAppProcesses.size() > 0) {
                            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                ActivityManager.RunningAppProcessInfo next = it.next();
                                if (next.pid == iMyPid) {
                                    sCurrentProcessName = next.processName;
                                    break;
                                }
                            }
                        }
                    } else {
                        sCurrentProcessName = processName2;
                    }
                } else {
                    sCurrentProcessName = processName;
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context.getApplicationContext(), th);
            }
        }
        return sCurrentProcessName;
    }

    private static long getDistanceDays(long j2, long j3) {
        return (j2 < j3 ? j3 - j2 : j2 - j3) / 86400000;
    }

    public static String getEnvelopeDirPath(Context context) {
        File file;
        synchronized (mEnvelopeFileLock) {
            try {
                if (mDefaultEnvelopeDirPath == null) {
                    mDefaultEnvelopeDirPath = context.getFilesDir().getAbsolutePath() + File.separator + "." + mDefaultEnvelopeDir;
                }
                file = new File(mDefaultEnvelopeDirPath);
            } finally {
                return mDefaultEnvelopeDirPath;
            }
            if (file.exists() || file.mkdir()) {
            } else {
                ULog.d("--->>> Create Envelope Directory failed!!!");
            }
        }
        return mDefaultEnvelopeDirPath;
    }

    public static File getEnvelopeFile(Context context) {
        if (context == null) {
            return null;
        }
        File file = new File(getEnvelopeDirPath(context));
        synchronized (mEnvelopeFileLock) {
            try {
                File[] fileArrListFiles = file.listFiles();
                if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                    Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.framework.UMFrUtils.2
                        @Override // java.util.Comparator
                        /* renamed from: a, reason: merged with bridge method [inline-methods] */
                        public int compare(File file2, File file3) {
                            long jLastModified = file2.lastModified() - file3.lastModified();
                            if (jLastModified > 0) {
                                return 1;
                            }
                            return jLastModified == 0 ? 0 : -1;
                        }
                    });
                    return fileArrListFiles[0];
                }
                return null;
            } finally {
            }
        }
    }

    public static long getLastInstantBuildTime(Context context) {
        long j2;
        synchronized (mEnvelopeBuildTimeLock) {
            j2 = PreferenceWrapper.getDefault(context).getLong(KEY_LAST_INSTANT_SUCC_BUILD_TIME, 0L);
        }
        return j2;
    }

    public static long getLastSuccessfulBuildTime(Context context) {
        long j2;
        synchronized (mEnvelopeBuildTimeLock) {
            j2 = PreferenceWrapper.getDefault(context).getLong(KEY_LAST_SUCC_BUILD_TIME, 0L);
        }
        return j2;
    }

    public static String getLegacyEnvelopeDir(Context context) {
        try {
            String currentProcessName = getCurrentProcessName(context);
            if (!TextUtils.isEmpty(currentProcessName)) {
                String strB = ay.b().b(ay.B);
                String strReplace = currentProcessName.replace(':', '_');
                ULog.d("--->>> getEnvelopeDir: use current process name as envelope directory.");
                return strB + strReplace;
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
        return mDefaultEnvelopeDir;
    }

    private static String getProcessName(int i2) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/" + i2 + "/cmdline"));
            try {
                String line = bufferedReader.readLine();
                if (!TextUtils.isEmpty(line)) {
                    line = line.trim();
                }
                try {
                    bufferedReader.close();
                } catch (Throwable unused) {
                }
                return line;
            } catch (Throwable unused2) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Throwable unused3) {
                    }
                }
                return null;
            }
        } catch (Throwable unused4) {
            bufferedReader = null;
        }
    }

    public static String getSubProcessName(Context context) {
        String strSubstring;
        strSubstring = "";
        try {
            String currentProcessName = getCurrentProcessName(context);
            int iIndexOf = currentProcessName.indexOf(":");
            strSubstring = iIndexOf >= 0 ? currentProcessName.substring(iIndexOf + 1) : "";
            if (iIndexOf < 0) {
                String packageName = context.getPackageName();
                return currentProcessName.length() > packageName.length() ? currentProcessName.substring(packageName.length() + 1, currentProcessName.length()) : currentProcessName;
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context.getApplicationContext(), th);
        }
        return strSubstring;
    }

    public static boolean hasEnvelopeFile(Context context, UMLogDataProtocol.UMBusinessType uMBusinessType) {
        File[] fileArrListFiles;
        String str = uMBusinessType == UMLogDataProtocol.UMBusinessType.U_INTERNAL ? "i" : "a";
        if (uMBusinessType == UMLogDataProtocol.UMBusinessType.U_ZeroEnv) {
            str = bc.aJ;
        }
        if (uMBusinessType == UMLogDataProtocol.UMBusinessType.U_Silent) {
            str = "h";
        }
        String envelopeDirPath = getEnvelopeDirPath(context);
        if (envelopeDirPath == null) {
            return false;
        }
        File file = new File(envelopeDirPath);
        synchronized (mEnvelopeFileLock) {
            try {
                fileArrListFiles = file.listFiles();
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
            } finally {
            }
            if (fileArrListFiles != null && fileArrListFiles.length != 0) {
                for (File file2 : fileArrListFiles) {
                    if (file2.getName().startsWith(str)) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            if (!checkPermission(context, "android.permission.ACCESS_NETWORK_STATE") || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null) {
                return false;
            }
            return activeNetworkInfo.isConnectedOrConnecting();
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context.getApplicationContext(), th);
            return false;
        }
    }

    public static boolean removeEnvelopeFile(File file) {
        Context appContext = UMModuleRegister.getAppContext();
        synchronized (mEnvelopeFileLock) {
            try {
                if (file != null) {
                    try {
                        if (file.exists()) {
                            return file.delete();
                        }
                    } catch (Throwable th) {
                        UMCrashManager.reportCrash(appContext, th);
                    }
                }
                return true;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public static void removeRedundantEnvelopeFiles(Context context, int i2) {
        File[] fileArrListFiles;
        File file = new File(getEnvelopeDirPath(context));
        synchronized (mEnvelopeFileLock) {
            try {
                fileArrListFiles = file.listFiles();
            } catch (Throwable th) {
                UMCrashManager.reportCrash(context, th);
            } finally {
            }
            if (fileArrListFiles != null && fileArrListFiles.length > i2) {
                Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.umeng.commonsdk.framework.UMFrUtils.1
                    @Override // java.util.Comparator
                    /* renamed from: a, reason: merged with bridge method [inline-methods] */
                    public int compare(File file2, File file3) {
                        long jLastModified = file2.lastModified() - file3.lastModified();
                        if (jLastModified > 0) {
                            return 1;
                        }
                        return jLastModified == 0 ? 0 : -1;
                    }
                });
                if (fileArrListFiles.length > i2) {
                    for (int i3 = 0; i3 < fileArrListFiles.length - i2; i3++) {
                        if (!fileArrListFiles[i3].delete()) {
                            ULog.d("--->>> remove [" + i3 + "] file fail.");
                        }
                    }
                }
            }
        }
    }

    public static int saveEnvelopeFile(Context context, String str, byte[] bArr) {
        if (bArr == null) {
            return 101;
        }
        File file = new File(getEnvelopeDirPath(context) + File.separator + str);
        synchronized (mEnvelopeFileLock) {
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        try {
                            fileOutputStream2.write(bArr);
                            fileOutputStream2.close();
                            boolean zA = com.umeng.commonsdk.statistics.internal.a.a(context).a(str);
                            boolean zB = com.umeng.commonsdk.statistics.internal.a.a(context).b(str);
                            if (zA) {
                                updateLastSuccessfulBuildTime(context);
                            }
                            if (zB) {
                                updateLastInstantBuildTime(context);
                            }
                            return 0;
                        } catch (IOException e2) {
                            e = e2;
                            fileOutputStream = fileOutputStream2;
                            UMCrashManager.reportCrash(context, e);
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th) {
                                    UMCrashManager.reportCrash(context, th);
                                }
                            }
                            return 101;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Throwable th3) {
                                    UMCrashManager.reportCrash(context, th3);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (IOException e3) {
                    e = e3;
                }
            } catch (Throwable th5) {
                throw th5;
            }
        }
    }

    public static void syncLegacyEnvelopeIfNeeded(Context context) {
        String legacyEnvelopeDir;
        if (context == null) {
            return;
        }
        try {
            legacyEnvelopeDir = getLegacyEnvelopeDir(context);
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
        if (TextUtils.isEmpty(legacyEnvelopeDir) || legacyEnvelopeDir.equals(mDefaultEnvelopeDir)) {
            return;
        }
        File file = new File(context.getFilesDir().getAbsolutePath() + "/." + legacyEnvelopeDir);
        if (file.exists()) {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                try {
                    if (file.isDirectory()) {
                        file.delete();
                        return;
                    }
                    return;
                } catch (Throwable th2) {
                    UMCrashManager.reportCrash(context, th2);
                    return;
                }
            }
            try {
                String envelopeDirPath = getEnvelopeDirPath(context);
                for (int i2 = 0; i2 < fileArrListFiles.length; i2++) {
                    fileArrListFiles[i2].renameTo(new File(envelopeDirPath + File.separator + fileArrListFiles[i2].getName()));
                }
                if (file.isDirectory()) {
                    file.delete();
                    return;
                }
                return;
            } catch (Throwable th3) {
                UMCrashManager.reportCrash(context, th3);
                return;
            }
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static byte[] toByteArray(String str) throws IOException {
        byte[] bArr;
        Context appContext = UMModuleRegister.getAppContext();
        synchronized (mEnvelopeFileLock) {
            FileChannel channel = null;
            try {
                try {
                    channel = new RandomAccessFile(str, "r").getChannel();
                    MappedByteBuffer mappedByteBufferLoad = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size()).load();
                    bArr = new byte[(int) channel.size()];
                    if (mappedByteBufferLoad.remaining() > 0) {
                        mappedByteBufferLoad.get(bArr, 0, mappedByteBufferLoad.remaining());
                    }
                } catch (IOException e2) {
                    UMCrashManager.reportCrash(appContext, e2);
                    throw e2;
                }
            } finally {
                try {
                    channel.close();
                } catch (Throwable th) {
                    UMCrashManager.reportCrash(appContext, th);
                }
            }
        }
        return bArr;
    }

    private static void updateLastInstantBuildTime(Context context) {
        synchronized (mEnvelopeBuildTimeLock) {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            sharedPreferences.edit().putLong(KEY_LAST_INSTANT_SUCC_BUILD_TIME, System.currentTimeMillis()).commit();
        }
    }

    private static void updateLastSuccessfulBuildTime(Context context) {
        synchronized (mEnvelopeBuildTimeLock) {
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            sharedPreferences.edit().putLong(KEY_LAST_SUCC_BUILD_TIME, System.currentTimeMillis()).commit();
        }
    }
}
