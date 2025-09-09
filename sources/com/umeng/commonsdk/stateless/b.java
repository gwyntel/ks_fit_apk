package com.umeng.commonsdk.stateless;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentFilter;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.analytics.pro.bc;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.UMServerURL;
import com.umeng.commonsdk.statistics.common.ULog;
import java.io.File;
import java.util.LinkedList;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final int f22300a = 273;

    /* renamed from: b, reason: collision with root package name */
    private static Context f22301b = null;

    /* renamed from: c, reason: collision with root package name */
    private static HandlerThread f22302c = null;

    /* renamed from: d, reason: collision with root package name */
    private static Handler f22303d = null;

    /* renamed from: f, reason: collision with root package name */
    private static final int f22305f = 274;

    /* renamed from: g, reason: collision with root package name */
    private static final int f22306g = 275;

    /* renamed from: h, reason: collision with root package name */
    private static final int f22307h = 512;

    /* renamed from: i, reason: collision with root package name */
    private static a f22308i = null;

    /* renamed from: j, reason: collision with root package name */
    private static IntentFilter f22309j = null;

    /* renamed from: k, reason: collision with root package name */
    private static volatile boolean f22310k = false;

    /* renamed from: e, reason: collision with root package name */
    private static Object f22304e = new Object();

    /* renamed from: l, reason: collision with root package name */
    private static LinkedList<String> f22311l = new LinkedList<>();

    static class a extends FileObserver {
        public a(String str) {
            super(str);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i2, String str) {
            if ((i2 & 8) != 8) {
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> envelope file created >>> " + str);
            b.a(274);
        }
    }

    public b(Context context) {
        synchronized (f22304e) {
            if (context != null) {
                try {
                    try {
                        Context applicationContext = context.getApplicationContext();
                        f22301b = applicationContext;
                        if (applicationContext != null && f22302c == null) {
                            HandlerThread handlerThread = new HandlerThread("SL-NetWorkSender");
                            f22302c = handlerThread;
                            handlerThread.start();
                            if (f22308i == null) {
                                String str = f22301b.getFilesDir() + File.separator + com.umeng.commonsdk.stateless.a.f22295f;
                                File file = new File(str);
                                if (!file.exists()) {
                                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 2号数据仓目录不存在，创建之。");
                                    file.mkdir();
                                }
                                a aVar = new a(str);
                                f22308i = aVar;
                                aVar.startWatching();
                                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 2号数据仓File Monitor启动.");
                            }
                            if (f22303d == null) {
                                f22303d = new Handler(f22302c.getLooper()) { // from class: com.umeng.commonsdk.stateless.b.1
                                    @Override // android.os.Handler
                                    public void handleMessage(Message message) {
                                        int i2 = message.what;
                                        if (i2 != 512) {
                                            switch (i2) {
                                                case 273:
                                                    b.l();
                                                    return;
                                                case 274:
                                                    b.n();
                                                    return;
                                                case 275:
                                                    b.p();
                                                    break;
                                                default:
                                                    return;
                                            }
                                        }
                                        b.q();
                                    }
                                };
                            }
                        }
                    } finally {
                    }
                } catch (Throwable th) {
                }
            }
        }
    }

    public static void a(boolean z2) {
        f22310k = z2;
        if (!z2) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>网络断连： 2号数据仓");
        } else {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>网络可用： 触发2号数据仓信封消费动作。");
            b(274);
        }
    }

    public static void b(int i2) {
        Handler handler;
        try {
            if (!f22310k || (handler = f22303d) == null || handler.hasMessages(i2)) {
                return;
            }
            Message messageObtainMessage = f22303d.obtainMessage();
            messageObtainMessage.what = i2;
            f22303d.sendMessage(messageObtainMessage);
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f22301b, th);
        }
    }

    public static void c() {
        b(275);
    }

    public static void d() {
        b(512);
    }

    private static void i() {
        File[] fileArrC = d.c(f22301b);
        if (fileArrC != null) {
            if (f22311l.size() > 0) {
                f22311l.clear();
            }
            for (File file : fileArrC) {
                f22311l.add(file.getAbsolutePath());
            }
        }
    }

    private static String j() {
        String str = null;
        try {
            String strPeek = f22311l.peek();
            if (strPeek == null) {
                return strPeek;
            }
            try {
                f22311l.removeFirst();
                return strPeek;
            } catch (Throwable unused) {
                str = strPeek;
                return str;
            }
        } catch (Throwable unused2) {
        }
    }

    @TargetApi(9)
    private static void k() {
        String strPollFirst;
        byte[] bArrA;
        if (f22311l.size() <= 0) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> todoList无内容，无需处理。");
            return;
        }
        do {
            strPollFirst = f22311l.pollFirst();
            if (!TextUtils.isEmpty(strPollFirst)) {
                File file = new File(strPollFirst);
                if (file.exists()) {
                    c cVar = new c(f22301b);
                    try {
                        bArrA = d.a(strPollFirst);
                    } catch (Exception unused) {
                        bArrA = null;
                    }
                    String name = file.getName();
                    String strSubstring = !TextUtils.isEmpty(name) ? name.substring(0, 1) : bc.aN;
                    String strC = d.c(d.d(name));
                    if (SdkVersion.SDK_TYPE == 0) {
                        cVar.a();
                    } else {
                        com.umeng.commonsdk.stateless.a.f22296g = com.umeng.commonsdk.stateless.a.f22299j;
                        cVar.b();
                    }
                    if (cVar.a(bArrA, strC, com.umeng.commonsdk.vchannel.a.f22585c.equalsIgnoreCase(strC) ? com.umeng.commonsdk.vchannel.a.f22583a : com.umeng.commonsdk.stateless.a.f22297h, strSubstring) && !file.delete()) {
                        file.delete();
                    }
                } else {
                    UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 信封文件不存在，处理下一个文件。");
                }
            }
        } while (strPollFirst != null);
        f22311l.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void l() {
        File fileA;
        byte[] bArrA;
        if (!f22310k || f22301b == null) {
            return;
        }
        do {
            try {
                fileA = d.a(f22301b);
                if (fileA != null && fileA.getParentFile() != null && !TextUtils.isEmpty(fileA.getParentFile().getName())) {
                    c cVar = new c(f22301b);
                    String str = new String(Base64.decode(fileA.getParentFile().getName(), 0));
                    if (com.umeng.commonsdk.internal.a.f22237a.equalsIgnoreCase(str) || com.umeng.commonsdk.internal.a.f22238b.equalsIgnoreCase(str) || com.umeng.commonsdk.internal.a.H.equalsIgnoreCase(str)) {
                        new File(fileA.getAbsolutePath()).delete();
                    } else {
                        ULog.i("walle", "[stateless] handleProcessNext, pathUrl is " + str);
                        try {
                            bArrA = d.a(fileA.getAbsolutePath());
                        } catch (Exception unused) {
                            bArrA = null;
                        }
                        String str2 = bc.aN;
                        if (UMServerURL.PATH_SHARE.equalsIgnoreCase(str)) {
                            str2 = "s";
                        }
                        if (UMServerURL.PATH_PUSH_LAUNCH.equalsIgnoreCase(str) || UMServerURL.PATH_PUSH_REGIST.equalsIgnoreCase(str) || UMServerURL.PATH_PUSH_LOG.equalsIgnoreCase(str)) {
                            str2 = "p";
                        }
                        if (SdkVersion.SDK_TYPE == 0) {
                            cVar.a();
                        } else {
                            com.umeng.commonsdk.stateless.a.f22296g = com.umeng.commonsdk.stateless.a.f22299j;
                            cVar.b();
                        }
                        if (!cVar.a(bArrA, str, com.umeng.commonsdk.vchannel.a.f22585c.equalsIgnoreCase(str) ? com.umeng.commonsdk.vchannel.a.f22583a : com.umeng.commonsdk.stateless.a.f22297h, str2)) {
                            ULog.i("walle", "[stateless] Send envelope file failed, abandon and wait next trigger!");
                            return;
                        }
                        ULog.i("walle", "[stateless] Send envelope file success, delete it.");
                        File file = new File(fileA.getAbsolutePath());
                        if (!file.delete()) {
                            ULog.i("walle", "[stateless] Failed to delete already processed file. We try again after delete failed.");
                            file.delete();
                        }
                    }
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(f22301b, th);
            }
        } while (fileA != null);
        m();
    }

    private static void m() {
        try {
            File file = new File(f22301b.getFilesDir() + File.separator + com.umeng.commonsdk.stateless.a.f22294e);
            if (file.exists() && file.isDirectory()) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> 2号数据仓：删除stateless目录。");
                d.a(file);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void n() {
        if (!f22310k || f22301b == null) {
            return;
        }
        i();
        k();
        c();
    }

    private static void o() {
        try {
            File file = new File(f22301b.getFilesDir() + File.separator + com.umeng.commonsdk.stateless.a.f22294e);
            if (file.exists() && file.isDirectory()) {
                UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>2号数据仓：检测到stateless目录。");
                b(273);
            }
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void p() {
        o();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void q() {
    }

    public static boolean a() {
        synchronized (f22304e) {
            try {
                return f22308i != null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void b() {
        UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>>信封构建成功： 触发2号数据仓信封消费动作。");
        b(274);
    }

    public static void a(int i2) {
        Handler handler;
        if (!f22310k || (handler = f22303d) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        f22303d.sendMessage(messageObtainMessage);
    }
}
