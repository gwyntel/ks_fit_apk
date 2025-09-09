package anet.channel.strategy;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.statist.StrategyStatObject;
import anet.channel.util.ALog;
import anet.channel.util.SerializeHelper;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/* loaded from: classes2.dex */
class m {

    /* renamed from: a, reason: collision with root package name */
    private static File f7054a = null;

    /* renamed from: b, reason: collision with root package name */
    private static volatile boolean f7055b = false;

    /* renamed from: c, reason: collision with root package name */
    private static Comparator<File> f7056c = new n();

    m() {
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                File file = new File(context.getFilesDir(), "awcn_strategy");
                f7054a = file;
                if (!a(file)) {
                    ALog.e("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", f7054a.getAbsolutePath());
                }
                if (!GlobalAppRuntimeInfo.isTargetProcess()) {
                    String currentProcess = GlobalAppRuntimeInfo.getCurrentProcess();
                    File file2 = new File(f7054a, currentProcess.substring(currentProcess.indexOf(58) + 1));
                    f7054a = file2;
                    if (!a(file2)) {
                        ALog.e("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", f7054a.getAbsolutePath());
                    }
                }
                ALog.i("awcn.StrategySerializeHelper", "StrateyFolder", null, "path", f7054a.getAbsolutePath());
                if (!f7055b) {
                    c();
                } else {
                    a();
                    f7055b = false;
                }
            } catch (Throwable th) {
                ALog.e("awcn.StrategySerializeHelper", "StrategySerializeHelper initialize failed!!!", null, th, new Object[0]);
            }
        }
    }

    static synchronized File[] b() {
        File file = f7054a;
        if (file == null) {
            return null;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles != null) {
            Arrays.sort(fileArrListFiles, f7056c);
        }
        return fileArrListFiles;
    }

    static synchronized void c() {
        try {
            File[] fileArrB = b();
            if (fileArrB == null) {
                return;
            }
            int i2 = 0;
            for (File file : fileArrB) {
                if (!file.isDirectory()) {
                    if (System.currentTimeMillis() - file.lastModified() > 172800000) {
                        file.delete();
                    } else if (file.getName().startsWith("WIFI")) {
                        int i3 = i2 + 1;
                        if (i2 > 10) {
                            file.delete();
                        }
                        i2 = i3;
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private static boolean a(File file) {
        if (file == null || file.exists()) {
            return true;
        }
        return file.mkdir();
    }

    public static File a(String str) {
        a(f7054a);
        return new File(f7054a, str);
    }

    static synchronized void a() {
        ALog.i("awcn.StrategySerializeHelper", "clear start.", null, new Object[0]);
        File file = f7054a;
        if (file == null) {
            ALog.w("awcn.StrategySerializeHelper", "folder path not initialized, wait to clear", null, new Object[0]);
            f7055b = true;
            return;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                file2.delete();
            }
        }
        ALog.i("awcn.StrategySerializeHelper", "clear end.", null, new Object[0]);
    }

    static synchronized void a(Serializable serializable, String str, StrategyStatObject strategyStatObject) {
        SerializeHelper.persist(serializable, a(str), strategyStatObject);
    }

    static synchronized <T> T a(String str, StrategyStatObject strategyStatObject) {
        return (T) SerializeHelper.restore(a(str), strategyStatObject);
    }
}
