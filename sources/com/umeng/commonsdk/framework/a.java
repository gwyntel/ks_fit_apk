package com.umeng.commonsdk.framework;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.media3.exoplayer.ExoPlayer;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.b;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.service.UMGlobalContext;
import com.umeng.commonsdk.statistics.c;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback;
import dev.fluttercommunity.plus.connectivity.ConnectivityBroadcastReceiver;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class a implements UMImprintChangeCallback {

    /* renamed from: a, reason: collision with root package name */
    private static HandlerThread f22211a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Handler f22212b = null;

    /* renamed from: c, reason: collision with root package name */
    private static Handler f22213c = null;

    /* renamed from: d, reason: collision with root package name */
    private static final int f22214d = 200;

    /* renamed from: e, reason: collision with root package name */
    private static final int f22215e = 273;

    /* renamed from: f, reason: collision with root package name */
    private static final int f22216f = 274;

    /* renamed from: g, reason: collision with root package name */
    private static final int f22217g = 512;

    /* renamed from: h, reason: collision with root package name */
    private static final int f22218h = 769;

    /* renamed from: i, reason: collision with root package name */
    private static FileObserverC0178a f22219i = null;

    /* renamed from: j, reason: collision with root package name */
    private static ConnectivityManager f22220j = null;

    /* renamed from: k, reason: collision with root package name */
    private static IntentFilter f22221k = null;

    /* renamed from: l, reason: collision with root package name */
    private static volatile boolean f22222l = false;

    /* renamed from: m, reason: collision with root package name */
    private static ArrayList<UMSenderStateNotify> f22223m = null;

    /* renamed from: p, reason: collision with root package name */
    private static final String f22226p = "report_policy";

    /* renamed from: q, reason: collision with root package name */
    private static final String f22227q = "report_interval";

    /* renamed from: s, reason: collision with root package name */
    private static final int f22229s = 15;

    /* renamed from: t, reason: collision with root package name */
    private static final int f22230t = 3;

    /* renamed from: u, reason: collision with root package name */
    private static final int f22231u = 90;

    /* renamed from: x, reason: collision with root package name */
    private static BroadcastReceiver f22234x;

    /* renamed from: n, reason: collision with root package name */
    private static Object f22224n = new Object();

    /* renamed from: o, reason: collision with root package name */
    private static ReentrantLock f22225o = new ReentrantLock();

    /* renamed from: r, reason: collision with root package name */
    private static boolean f22228r = false;

    /* renamed from: v, reason: collision with root package name */
    private static int f22232v = 15;

    /* renamed from: w, reason: collision with root package name */
    private static Object f22233w = new Object();

    /* renamed from: com.umeng.commonsdk.framework.a$a, reason: collision with other inner class name */
    static class FileObserverC0178a extends FileObserver {
        public FileObserverC0178a(String str) {
            super(str);
        }

        @Override // android.os.FileObserver
        public void onEvent(int i2, String str) {
            if ((i2 & 8) != 8) {
                return;
            }
            ULog.d("--->>> envelope file created >>> " + str);
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> envelope file created >>> " + str);
            a.c(273);
        }
    }

    static {
        Context appContext = UMGlobalContext.getAppContext();
        if (appContext != null) {
            f22220j = (ConnectivityManager) appContext.getSystemService("connectivity");
        }
        f22234x = new BroadcastReceiver() { // from class: com.umeng.commonsdk.framework.a.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION)) {
                    UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.E, b.a(context).a(), null);
                }
            }
        };
    }

    public a(Context context, Handler handler) {
        if (f22220j == null) {
            Context appContext = UMGlobalContext.getAppContext();
            if (f22220j != null) {
                f22220j = (ConnectivityManager) appContext.getSystemService("connectivity");
            }
        }
        f22213c = handler;
        try {
            if (f22211a == null) {
                HandlerThread handlerThread = new HandlerThread("NetWorkSender");
                f22211a = handlerThread;
                handlerThread.start();
                if (f22219i == null) {
                    FileObserverC0178a fileObserverC0178a = new FileObserverC0178a(UMFrUtils.getEnvelopeDirPath(context));
                    f22219i = fileObserverC0178a;
                    fileObserverC0178a.startWatching();
                    ULog.d("--->>> FileMonitor has already started!");
                }
                j();
                if (f22212b == null) {
                    f22212b = new Handler(f22211a.getLooper()) { // from class: com.umeng.commonsdk.framework.a.3
                        @Override // android.os.Handler
                        public void handleMessage(Message message) {
                            int i2 = message.what;
                            if (i2 == 273) {
                                ULog.d("--->>> handleMessage: recv MSG_PROCESS_NEXT msg.");
                                try {
                                    a.f22225o.tryLock(1L, TimeUnit.SECONDS);
                                    try {
                                        a.n();
                                    } catch (Throwable unused) {
                                    }
                                    a.f22225o.unlock();
                                    return;
                                } catch (Throwable unused2) {
                                    return;
                                }
                            }
                            if (i2 == 274) {
                                a.l();
                            } else {
                                if (i2 != 512) {
                                    return;
                                }
                                a.m();
                            }
                        }
                    };
                }
                ImprintHandler.getImprintService(context).registImprintCallback(f22226p, this);
                ImprintHandler.getImprintService(context).registImprintCallback(f22227q, this);
            }
        } catch (Throwable th) {
            UMCrashManager.reportCrash(context, th);
        }
    }

    public static int b() {
        int i2;
        synchronized (f22233w) {
            i2 = f22232v;
        }
        return i2;
    }

    public static void c() {
    }

    public static void d() {
        if (f22225o.tryLock()) {
            try {
                b(273);
            } finally {
                f22225o.unlock();
            }
        }
    }

    public static void e() {
        a(274, 3000);
    }

    private void j() {
        synchronized (f22233w) {
            try {
                if (AgooConstants.ACK_BODY_NULL.equals(UMEnvelopeBuild.imprintProperty(UMModuleRegister.getAppContext(), f22226p, ""))) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> switch to report_policy 11");
                    f22228r = true;
                    f22232v = 15;
                    int iIntValue = Integer.valueOf(UMEnvelopeBuild.imprintProperty(UMModuleRegister.getAppContext(), f22227q, AgooConstants.ACK_PACK_ERROR)).intValue();
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> set report_interval value to: " + iIntValue);
                    if (iIntValue < 3 || iIntValue > 90) {
                        f22232v = 15;
                    } else {
                        f22232v = iIntValue * 1000;
                    }
                } else {
                    f22228r = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static void k() {
        if (f22211a != null) {
            f22211a = null;
        }
        if (f22212b != null) {
            f22212b = null;
        }
        if (f22213c != null) {
            f22213c = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void l() {
        int size;
        synchronized (f22224n) {
            try {
                ArrayList<UMSenderStateNotify> arrayList = f22223m;
                if (arrayList != null && (size = arrayList.size()) > 0) {
                    for (int i2 = 0; i2 < size; i2++) {
                        f22223m.get(i2).onSenderIdle();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void m() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void n() {
        ULog.d("--->>> handleProcessNext: Enter...");
        if (f22222l) {
            Context appContext = UMModuleRegister.getAppContext();
            try {
                if (UMFrUtils.envelopeFileNumber(appContext) > 0) {
                    ULog.d("--->>> The envelope file exists.");
                    if (UMFrUtils.envelopeFileNumber(appContext) > 200) {
                        ULog.d("--->>> Number of envelope files is greater than 200, remove old files first.");
                        UMFrUtils.removeRedundantEnvelopeFiles(appContext, 200);
                    }
                    File envelopeFile = UMFrUtils.getEnvelopeFile(appContext);
                    if (envelopeFile != null) {
                        String path = envelopeFile.getPath();
                        ULog.d("--->>> Ready to send envelope file [" + path + "].");
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> send envelope file [ " + path + "].");
                        if (!new c(appContext).a(envelopeFile)) {
                            ULog.d("--->>> Send envelope file failed, abandon and wait next trigger!");
                            return;
                        }
                        ULog.d("--->>> Send envelope file success, delete it.");
                        if (!UMFrUtils.removeEnvelopeFile(envelopeFile)) {
                            ULog.d("--->>> Failed to delete already processed file. We try again after delete failed.");
                            UMFrUtils.removeEnvelopeFile(envelopeFile);
                        }
                        c(273);
                        return;
                    }
                }
                e();
            } catch (Throwable th) {
                UMCrashManager.reportCrash(appContext, th);
            }
        }
    }

    @Override // com.umeng.commonsdk.statistics.internal.UMImprintChangeCallback
    public void onImprintValueChanged(String str, String str2) {
        synchronized (f22233w) {
            try {
                if (f22226p.equals(str)) {
                    if (AgooConstants.ACK_BODY_NULL.equals(str2)) {
                        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> switch to report_policy 11");
                        f22228r = true;
                    } else {
                        f22228r = false;
                    }
                }
                if (f22227q.equals(str)) {
                    int iIntValue = Integer.valueOf(str2).intValue();
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> set report_interval value to: " + iIntValue);
                    if (iIntValue < 3 || iIntValue > 90) {
                        f22232v = 15000;
                    } else {
                        f22232v = iIntValue * 1000;
                    }
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> really set report_interval value to: " + f22232v);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void a(Context context) {
        if (f22220j != null || context == null) {
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        f22220j = connectivityManager;
        if (connectivityManager != null) {
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> createCMIfNeeded:注册网络状态监听器。");
            b(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void c(int i2) {
        Handler handler;
        if (!f22222l || (handler = f22212b) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        f22212b.sendMessage(messageObtainMessage);
    }

    @SuppressLint({"NewApi", "MissingPermission"})
    public static void b(Context context) {
        if (context == null) {
            UMRTLog.e(UMRTLog.RTLOG_TAG, "--->>> registerNetReceiver: context is null, registerNetReceiver failed.");
            return;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            if (DeviceConfig.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
                NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addTransportType(0).addTransportType(1).build();
                if (f22220j != null) {
                    final Context applicationContext = context.getApplicationContext();
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 注册网络状态监听器:registerNetworkCallback");
                    f22220j.registerNetworkCallback(networkRequestBuild, new ConnectivityManager.NetworkCallback() { // from class: com.umeng.commonsdk.framework.a.1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onAvailable(Network network) {
                            Context context2 = applicationContext;
                            UMWorkDispatch.sendEvent(context2, com.umeng.commonsdk.internal.a.E, b.a(context2).a(), null);
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                            super.onCapabilitiesChanged(network, networkCapabilities);
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public void onLost(Network network) {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> onLost");
                            Context context2 = applicationContext;
                            UMWorkDispatch.sendEvent(context2, com.umeng.commonsdk.internal.a.E, b.a(context2).a(), null, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                        }
                    });
                    return;
                }
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> ACCESS_NETWORK_STATE permission access denied.");
            return;
        }
        if (DeviceConfig.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
            if (f22220j == null || f22221k != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            f22221k = intentFilter;
            intentFilter.addAction(ConnectivityBroadcastReceiver.CONNECTIVITY_ACTION);
            if (f22234x != null) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> 注册网络状态监听器:registerReceiver");
                context.registerReceiver(f22234x, f22221k);
                return;
            }
            return;
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> ACCESS_NETWORK_STATE permission access denied.");
    }

    public static void a(UMSenderStateNotify uMSenderStateNotify) {
        synchronized (f22224n) {
            try {
                if (f22223m == null) {
                    f22223m = new ArrayList<>();
                }
                if (uMSenderStateNotify != null) {
                    for (int i2 = 0; i2 < f22223m.size(); i2++) {
                        if (uMSenderStateNotify == f22223m.get(i2)) {
                            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> addConnStateObserver: input item has exist.");
                            return;
                        }
                    }
                    f22223m.add(uMSenderStateNotify);
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(UMModuleRegister.getAppContext(), th);
            }
        }
    }

    public static boolean a() {
        boolean z2;
        synchronized (f22233w) {
            z2 = f22228r;
        }
        return z2;
    }

    public static void a(boolean z2) {
        int size;
        f22222l = z2;
        if (z2) {
            synchronized (f22224n) {
                try {
                    ArrayList<UMSenderStateNotify> arrayList = f22223m;
                    if (arrayList != null && (size = arrayList.size()) > 0) {
                        for (int i2 = 0; i2 < size; i2++) {
                            f22223m.get(i2).onConnectionAvailable();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            UMRTLog.e(UMRTLog.RTLOG_TAG, "网络状态通知：尝试发送 MSG_PROCESS_NEXT");
            d();
            return;
        }
        ULog.i("--->>> network disconnected.");
        f22222l = false;
    }

    private static void b(int i2) {
        Handler handler;
        if (!f22222l || (handler = f22212b) == null || handler.hasMessages(i2)) {
            return;
        }
        Message messageObtainMessage = f22212b.obtainMessage();
        messageObtainMessage.what = i2;
        f22212b.sendMessage(messageObtainMessage);
    }

    private static void a(int i2, long j2) {
        Handler handler;
        if (!f22222l || (handler = f22212b) == null) {
            return;
        }
        Message messageObtainMessage = handler.obtainMessage();
        messageObtainMessage.what = i2;
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> sendMsgDelayed: " + j2);
        f22212b.sendMessageDelayed(messageObtainMessage, j2);
    }

    private static void a(int i2, int i3) {
        Handler handler;
        if (!f22222l || (handler = f22212b) == null) {
            return;
        }
        handler.removeMessages(i2);
        Message messageObtainMessage = f22212b.obtainMessage();
        messageObtainMessage.what = i2;
        f22212b.sendMessageDelayed(messageObtainMessage, i3);
    }
}
