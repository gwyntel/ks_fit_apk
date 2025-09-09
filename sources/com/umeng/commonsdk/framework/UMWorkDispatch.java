package com.umeng.commonsdk.framework;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.ULog;
import com.umeng.commonsdk.utils.UMUtils;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UMWorkDispatch {
    public static final String GENERAL_CONTENT = "content";
    public static final String GENERAL_HEADER = "header";
    public static final String KEY_EXCEPTION = "exception";
    private static final int MSG_AUTO_PROCESS = 769;
    private static final int MSG_CHECKER_TIMER = 771;
    private static final int MSG_DELAY_PROCESS = 770;
    private static final int MSG_QUIT = 784;
    private static final int MSG_SEND_EVENT = 768;
    private static HandlerThread mNetTask;
    private static a mSender;
    private static Object mSenderInitLock = new Object();
    private static Handler mTaskHandler;

    private UMWorkDispatch() {
    }

    public static void Quit() {
        Handler handler = mTaskHandler;
        if (handler != null) {
            Message messageObtainMessage = handler.obtainMessage();
            messageObtainMessage.what = MSG_QUIT;
            mTaskHandler.sendMessage(messageObtainMessage);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void delayProcess() {
        JSONObject jSONObject;
        JSONObject jSONObjectBuildEnvelopeWithExtHeader;
        ULog.d("--->>> delayProcess Enter...");
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> delayProcess Enter...");
        Context appContext = UMModuleRegister.getAppContext();
        if (appContext == null || !UMFrUtils.isOnline(appContext)) {
            return;
        }
        long jMaxDataSpace = UMEnvelopeBuild.maxDataSpace(appContext);
        UMLogDataProtocol callbackFromModuleName = UMModuleRegister.getCallbackFromModuleName("analytics");
        if (callbackFromModuleName != null) {
            try {
                jSONObject = callbackFromModuleName.setupReportData(jMaxDataSpace);
                if (jSONObject == null) {
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> analyticsCB.setupReportData() return null");
                    return;
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(appContext, th);
                return;
            }
        } else {
            jSONObject = null;
        }
        if (jSONObject == null || jSONObject.length() <= 0) {
            return;
        }
        JSONObject jSONObject2 = (JSONObject) jSONObject.opt("header");
        JSONObject jSONObject3 = (JSONObject) jSONObject.opt("content");
        if (jSONObject2 == null || jSONObject3 == null || (jSONObjectBuildEnvelopeWithExtHeader = UMEnvelopeBuild.buildEnvelopeWithExtHeader(appContext, jSONObject2, jSONObject3)) == null) {
            return;
        }
        try {
            if (jSONObjectBuildEnvelopeWithExtHeader.has("exception")) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> autoProcess: Build envelope error code: " + jSONObjectBuildEnvelopeWithExtHeader.getInt("exception"));
            }
        } catch (Throwable unused) {
        }
        UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> autoProcess: removeCacheData ... ");
        callbackFromModuleName.removeCacheData(jSONObjectBuildEnvelopeWithExtHeader);
    }

    public static synchronized boolean eventHasExist(int i2) {
        Handler handler = mTaskHandler;
        if (handler == null) {
            return false;
        }
        return handler.hasMessages(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleEvent(Message message) {
        int i2 = message.arg1;
        Object obj = message.obj;
        UMLogDataProtocol callbackFromModuleName = UMModuleRegister.getCallbackFromModuleName(UMModuleRegister.eventType2ModuleName(i2));
        if (callbackFromModuleName != null) {
            ULog.d("--->>> dispatch:handleEvent: call back workEvent with msg type [ 0x" + Integer.toHexString(i2) + "]");
            callbackFromModuleName.workEvent(obj, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleQuit() {
        if (mSender == null || mNetTask == null) {
            return;
        }
        a.c();
        ULog.d("--->>> handleQuit: Quit dispatch thread.");
        mNetTask.quit();
        teardown();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0032 A[Catch: all -> 0x0039, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:13:0x0032, B:12:0x002b, B:5:0x0008, B:7:0x000c, B:9:0x001c), top: B:19:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static synchronized void init() {
        /*
            java.lang.Class<com.umeng.commonsdk.framework.UMWorkDispatch> r0 = com.umeng.commonsdk.framework.UMWorkDispatch.class
            monitor-enter(r0)
            java.lang.String r1 = "--->>> Dispatch: init Enter..."
            com.umeng.commonsdk.statistics.common.ULog.d(r1)     // Catch: java.lang.Throwable -> L39
            android.os.HandlerThread r1 = com.umeng.commonsdk.framework.UMWorkDispatch.mNetTask     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L32
            android.os.HandlerThread r1 = new android.os.HandlerThread     // Catch: java.lang.Throwable -> L2a
            java.lang.String r2 = "work_thread"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L2a
            com.umeng.commonsdk.framework.UMWorkDispatch.mNetTask = r1     // Catch: java.lang.Throwable -> L2a
            r1.start()     // Catch: java.lang.Throwable -> L2a
            android.os.Handler r1 = com.umeng.commonsdk.framework.UMWorkDispatch.mTaskHandler     // Catch: java.lang.Throwable -> L2a
            if (r1 != 0) goto L32
            com.umeng.commonsdk.framework.UMWorkDispatch$1 r1 = new com.umeng.commonsdk.framework.UMWorkDispatch$1     // Catch: java.lang.Throwable -> L2a
            android.os.HandlerThread r2 = com.umeng.commonsdk.framework.UMWorkDispatch.mNetTask     // Catch: java.lang.Throwable -> L2a
            android.os.Looper r2 = r2.getLooper()     // Catch: java.lang.Throwable -> L2a
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L2a
            com.umeng.commonsdk.framework.UMWorkDispatch.mTaskHandler = r1     // Catch: java.lang.Throwable -> L2a
            goto L32
        L2a:
            r1 = move-exception
            android.content.Context r2 = com.umeng.commonsdk.framework.UMModuleRegister.getAppContext()     // Catch: java.lang.Throwable -> L39
            com.umeng.commonsdk.internal.crash.UMCrashManager.reportCrash(r2, r1)     // Catch: java.lang.Throwable -> L39
        L32:
            java.lang.String r1 = "--->>> Dispatch: init Exit..."
            com.umeng.commonsdk.statistics.common.ULog.d(r1)     // Catch: java.lang.Throwable -> L39
            monitor-exit(r0)
            return
        L39:
            r1 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L39
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.framework.UMWorkDispatch.init():void");
    }

    public static void registerConnStateObserver(UMSenderStateNotify uMSenderStateNotify) {
        if (mSender != null) {
            a.a(uMSenderStateNotify);
        }
    }

    public static synchronized void removeEvent() {
        Handler handler = mTaskHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(MSG_CHECKER_TIMER);
    }

    public static void sendDelayProcessMsg(long j2) {
        Handler handler = mTaskHandler;
        if (handler != null) {
            if (handler.hasMessages(MSG_DELAY_PROCESS)) {
                UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> MSG_DELAY_PROCESS has exist. do nothing.");
                return;
            }
            UMRTLog.i(UMRTLog.RTLOG_TAG, "--->>> MSG_DELAY_PROCESS not exist. send it.");
            Message messageObtainMessage = mTaskHandler.obtainMessage();
            messageObtainMessage.what = MSG_DELAY_PROCESS;
            mTaskHandler.sendMessageDelayed(messageObtainMessage, j2);
        }
    }

    public static void sendEvent(Context context, int i2, UMLogDataProtocol uMLogDataProtocol, Object obj) {
        sendEventInternal(context, MSG_SEND_EVENT, i2, uMLogDataProtocol, obj, 0L);
    }

    public static void sendEventEx(Context context, int i2, UMLogDataProtocol uMLogDataProtocol, Object obj, long j2) {
        sendEventInternal(context, MSG_CHECKER_TIMER, i2, uMLogDataProtocol, obj, j2);
    }

    public static void sendEventInternal(Context context, int i2, int i3, UMLogDataProtocol uMLogDataProtocol, Object obj, long j2) {
        if (context == null || uMLogDataProtocol == null) {
            ULog.d("--->>> Context or UMLogDataProtocol parameter cannot be null!");
            return;
        }
        UMModuleRegister.registerAppContext(context.getApplicationContext());
        if (UMModuleRegister.registerCallback(i3, uMLogDataProtocol)) {
            if (mNetTask == null || mTaskHandler == null) {
                init();
            }
            try {
                if (mTaskHandler != null) {
                    if (UMUtils.isMainProgress(context)) {
                        synchronized (mSenderInitLock) {
                            try {
                                if (mSender == null) {
                                    UMFrUtils.syncLegacyEnvelopeIfNeeded(context);
                                    mSender = new a(context, mTaskHandler);
                                }
                            } finally {
                            }
                        }
                    }
                    Message messageObtainMessage = mTaskHandler.obtainMessage();
                    messageObtainMessage.what = i2;
                    messageObtainMessage.arg1 = i3;
                    messageObtainMessage.obj = obj;
                    mTaskHandler.sendMessageDelayed(messageObtainMessage, j2);
                }
            } catch (Throwable th) {
                UMCrashManager.reportCrash(UMModuleRegister.getAppContext(), th);
            }
        }
    }

    private static void teardown() {
        if (mNetTask != null) {
            mNetTask = null;
        }
        if (mTaskHandler != null) {
            mTaskHandler = null;
        }
        if (mSender != null) {
            mSender = null;
        }
    }

    public static void sendEvent(Context context, int i2, UMLogDataProtocol uMLogDataProtocol, Object obj, long j2) {
        sendEventInternal(context, MSG_SEND_EVENT, i2, uMLogDataProtocol, obj, j2);
    }

    public static synchronized boolean eventHasExist() {
        Handler handler = mTaskHandler;
        if (handler == null) {
            return false;
        }
        return handler.hasMessages(MSG_CHECKER_TIMER);
    }

    public static synchronized void removeEvent(int i2) {
        Handler handler = mTaskHandler;
        if (handler == null) {
            return;
        }
        handler.removeMessages(i2);
    }
}
