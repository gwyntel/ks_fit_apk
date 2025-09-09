package com.alibaba.sdk.android.oss.common;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import androidx.media3.datasource.cache.CacheDataSink;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.yc.utesdk.utils.open.CalendarUtils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes2.dex */
public class OSSLogToFileUtils {
    private static final String LOG_DIR_NAME = "OSSLog";
    private static OSSLogToFileUtils instance;
    private static Context sContext;
    private static File sLogFile;
    private boolean useSdCard = true;
    private static LogThreadPoolManager logService = LogThreadPoolManager.newInstance();
    private static SimpleDateFormat sLogSDF = new SimpleDateFormat(CalendarUtils.yyyy_MM_dd_HH_mm_ss);
    private static long LOG_MAX_SIZE = CacheDataSink.DEFAULT_FRAGMENT_SIZE;

    private static class WriteCall implements Runnable {
        private Object mStr;

        public WriteCall(Object obj) {
            this.mStr = obj;
        }

        private PrintWriter printEx(PrintWriter printWriter) {
            printWriter.println("crash_time：" + OSSLogToFileUtils.sLogSDF.format(new Date()));
            ((Throwable) this.mStr).printStackTrace(printWriter);
            return printWriter;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            if (OSSLogToFileUtils.sLogFile != null) {
                OSSLogToFileUtils.getInstance();
                if (OSSLogToFileUtils.getLogFileSize(OSSLogToFileUtils.sLogFile) > OSSLogToFileUtils.LOG_MAX_SIZE) {
                    OSSLogToFileUtils.getInstance().resetLogFile();
                }
                try {
                    PrintWriter printWriter = new PrintWriter((Writer) new FileWriter(OSSLogToFileUtils.sLogFile, true), true);
                    if (this.mStr instanceof Throwable) {
                        printEx(printWriter);
                    } else {
                        printWriter.println(OSSLogToFileUtils.getInstance().getFunctionInfo(null) + " - " + this.mStr.toString());
                    }
                    printWriter.println("------>end of log");
                    printWriter.println();
                    printWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private OSSLogToFileUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getFunctionInfo(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr != null) {
            return null;
        }
        return "[" + sLogSDF.format(new Date()) + "]";
    }

    public static OSSLogToFileUtils getInstance() {
        if (instance == null) {
            synchronized (OSSLogToFileUtils.class) {
                try {
                    if (instance == null) {
                        instance = new OSSLogToFileUtils();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public static long getLocalLogFileSize() {
        return getLogFileSize(sLogFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getLogFile() throws IOException {
        boolean z2;
        File file;
        if (this.useSdCard && Environment.getExternalStorageState().equals("mounted")) {
            z2 = readSDCardSpace() > LOG_MAX_SIZE / 1024;
            file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + LOG_DIR_NAME);
        } else {
            z2 = readSystemSpace() > LOG_MAX_SIZE / 1024;
            file = new File(sContext.getFilesDir().getPath() + File.separator + LOG_DIR_NAME);
        }
        if (!z2) {
            return null;
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file.getPath() + "/logs.csv");
        if (file2.exists()) {
            return file2;
        }
        createNewFile(file2);
        return file2;
    }

    public static long getLogFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0L;
        }
        return file.length();
    }

    public static void init(Context context, ClientConfiguration clientConfiguration) {
        File file;
        OSSLog.logDebug("init ...", false);
        if (clientConfiguration != null) {
            LOG_MAX_SIZE = clientConfiguration.getMaxLogSize();
        }
        if (sContext != null && instance != null && (file = sLogFile) != null && file.exists()) {
            OSSLog.logDebug("LogToFileUtils has been init ...", false);
            return;
        }
        sContext = context.getApplicationContext();
        instance = getInstance();
        logService.addExecuteTask(new Runnable() { // from class: com.alibaba.sdk.android.oss.common.OSSLogToFileUtils.1
            @Override // java.lang.Runnable
            public void run() throws IOException {
                File unused = OSSLogToFileUtils.sLogFile = OSSLogToFileUtils.instance.getLogFile();
                if (OSSLogToFileUtils.sLogFile != null) {
                    OSSLog.logInfo("LogFilePath is: " + OSSLogToFileUtils.sLogFile.getPath(), false);
                    if (OSSLogToFileUtils.LOG_MAX_SIZE < OSSLogToFileUtils.getLogFileSize(OSSLogToFileUtils.sLogFile)) {
                        OSSLog.logInfo("init reset log file", false);
                        OSSLogToFileUtils.instance.resetLogFile();
                    }
                }
            }
        });
    }

    private long readSDCardSpace() {
        long availableBlocks;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableBlocks = statFs.getAvailableBlocks() * statFs.getBlockSize();
        } else {
            availableBlocks = 0;
        }
        OSSLog.logDebug("sd卡存储空间:" + String.valueOf(availableBlocks) + "kb", false);
        return availableBlocks;
    }

    private long readSystemSpace() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        long availableBlocks = (statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1024;
        OSSLog.logDebug("内部存储空间:" + String.valueOf(availableBlocks) + "kb", false);
        return availableBlocks;
    }

    public static void reset() {
        sContext = null;
        instance = null;
        sLogFile = null;
    }

    public void createNewFile(File file) throws IOException {
        try {
            file.createNewFile();
        } catch (Exception e2) {
            OSSLog.logError("Create log file failure !!! " + e2.toString(), false);
        }
    }

    public void deleteLogFile() {
        File file = new File(sLogFile.getParent() + "/logs.csv");
        if (file.exists()) {
            OSSLog.logDebug("delete Log File ... ", false);
            file.delete();
        }
    }

    public void deleteLogFileDir() {
        deleteLogFile();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + LOG_DIR_NAME);
        if (file.exists()) {
            OSSLog.logDebug("delete Log FileDir ... ", false);
            file.delete();
        }
    }

    public void resetLogFile() throws IOException {
        OSSLog.logDebug("Reset Log File ... ", false);
        if (!sLogFile.getParentFile().exists()) {
            OSSLog.logDebug("Reset Log make File dir ... ", false);
            sLogFile.getParentFile().mkdir();
        }
        File file = new File(sLogFile.getParent() + "/logs.csv");
        if (file.exists()) {
            file.delete();
        }
        createNewFile(file);
    }

    public void setUseSdCard(boolean z2) {
        this.useSdCard = z2;
    }

    public synchronized void write(Object obj) {
        File file;
        try {
            if (OSSLog.isEnableLog()) {
                if (sContext != null && instance != null && (file = sLogFile) != null) {
                    if (!file.exists()) {
                        resetLogFile();
                    }
                    logService.addExecuteTask(new WriteCall(obj));
                }
            }
        } finally {
        }
    }
}
