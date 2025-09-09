package com.alibaba.sdk.android.openaccount.trace;

import android.util.Log;
import com.alibaba.sdk.android.openaccount.OpenAccountConstants;
import com.alibaba.sdk.android.openaccount.OpenAccountSDK;
import com.alibaba.sdk.android.openaccount.ut.UserTrackerService;
import com.alibaba.sdk.android.openaccount.util.TraceHelper;
import com.alibaba.sdk.android.pluto.annotation.Autowired;
import com.alipay.sdk.m.u.h;
import com.aliyun.linksdk.alcs.AlcsConstant;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes2.dex */
public class TraceLoggerManager {
    public static final TraceLoggerManager INSTANCE = new TraceLoggerManager();
    private volatile long actionCountTraceTime;

    @Autowired
    private UserTrackerService userTrackerService;
    private int logLevel = 7;
    private UncaughtExceptionTraceHandler uncaughtExceptionTraceHandler = new UncaughtExceptionTraceHandler();
    private final Map<String, Map<String, AtomicInteger[]>> actionCountTrack = new LinkedHashMap();
    private final ReadWriteLock actionCountTrackLock = new ReentrantReadWriteLock();
    private volatile String msgHeaderBuffer = "";

    private TraceLoggerManager() {
    }

    private CharSequence buildMessage(String str, String str2, String str3) throws IOException {
        StringBuilder sb = new StringBuilder(this.msgHeaderBuffer);
        buildStrHeader(sb, str);
        if (str2 != null) {
            sb.append(str2);
        }
        if (str3 != null) {
            sb.append(" : ");
            sb.append(str3);
        }
        return sb;
    }

    private Appendable buildStrHeader(Appendable appendable, CharSequence... charSequenceArr) throws IOException {
        try {
            for (CharSequence charSequence : charSequenceArr) {
                appendable.append('[');
                if (charSequence != null) {
                    appendable.append(charSequence);
                }
                appendable.append(']');
            }
        } catch (Exception e2) {
            Log.w(OpenAccountConstants.LOG_TAG, e2);
        }
        return appendable;
    }

    private void logCat(String str, int i2, String str2, String str3, String str4) {
        Log.println(i2, str, buildMessage(str2, str3, str4).toString());
    }

    private void sendActionCountTrack(boolean z2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if ((z2 || jCurrentTimeMillis - this.actionCountTraceTime >= 60000) && this.userTrackerService != null) {
            try {
                this.actionCountTrackLock.readLock().lock();
                try {
                    for (Map.Entry<String, Map<String, AtomicInteger[]>> entry : this.actionCountTrack.entrySet()) {
                        String key = entry.getKey();
                        for (Map.Entry<String, AtomicInteger[]> entry2 : entry.getValue().entrySet()) {
                            AtomicInteger[] value = entry2.getValue();
                            if (value[0].get() > 0 || value[2].get() > 0) {
                                String key2 = entry2.getKey();
                                HashMap map = new HashMap();
                                map.put("success", String.valueOf(value[0]));
                                map.put("successTime", String.valueOf(value[1]));
                                map.put(h.f9784j, String.valueOf(value[2]));
                                map.put("failedTime", String.valueOf(value[3]));
                                this.userTrackerService.sendCustomHit(key2, 60L, key, map);
                                log(3, AgooConstants.MESSAGE_TRACE, "ActionCount", key2);
                            }
                        }
                    }
                    this.actionCountTrackLock.readLock().unlock();
                    this.actionCountTrackLock.writeLock().lock();
                    try {
                        this.actionCountTrack.clear();
                        this.actionCountTrackLock.writeLock().unlock();
                        this.actionCountTraceTime = jCurrentTimeMillis;
                    } catch (Throwable th) {
                        this.actionCountTrackLock.writeLock().unlock();
                        throw th;
                    }
                } catch (Throwable th2) {
                    this.actionCountTrackLock.readLock().unlock();
                    throw th2;
                }
            } catch (Exception e2) {
                Log.w(OpenAccountConstants.LOG_TAG, e2);
            }
        }
    }

    private void updateMsgHeader(CharSequence... charSequenceArr) {
        this.msgHeaderBuffer = buildStrHeader(new StringBuilder(), charSequenceArr).toString();
    }

    public ActionTraceLogger action(String str) {
        return new ActionTraceLogger(AlcsConstant.METHOD_DOMAIN_CORE, str);
    }

    public void actionCountTrack(String str, String str2, boolean z2, int i2) {
        this.actionCountTrackLock.readLock().lock();
        try {
            Map<String, AtomicInteger[]> map = this.actionCountTrack.get(str);
            AtomicInteger[] atomicIntegerArr = map != null ? map.get(str2) : null;
            this.actionCountTrackLock.readLock().unlock();
            if (atomicIntegerArr == null) {
                this.actionCountTrackLock.writeLock().lock();
                try {
                    Map<String, AtomicInteger[]> map2 = this.actionCountTrack.get(str);
                    if (map2 == null) {
                        map2 = new HashMap<>();
                        this.actionCountTrack.put(str, map2);
                    } else {
                        atomicIntegerArr = map2.get(str2);
                    }
                    if (atomicIntegerArr == null) {
                        atomicIntegerArr = new AtomicInteger[4];
                        for (int i3 = 0; i3 < 4; i3++) {
                            atomicIntegerArr[i3] = new AtomicInteger();
                        }
                        map2.put(str2, atomicIntegerArr);
                    }
                    this.actionCountTrackLock.writeLock().unlock();
                } catch (Throwable th) {
                    this.actionCountTrackLock.writeLock().unlock();
                    throw th;
                }
            }
            if (z2) {
                atomicIntegerArr[0].incrementAndGet();
                atomicIntegerArr[1].addAndGet(i2);
            } else {
                atomicIntegerArr[2].incrementAndGet();
                atomicIntegerArr[3].addAndGet(i2);
            }
            sendActionCountTrack(false);
        } catch (Throwable th2) {
            this.actionCountTrackLock.readLock().unlock();
            throw th2;
        }
    }

    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public synchronized void init() {
        try {
            updateMsgHeader(TraceHelper.clientTTID, this.userTrackerService.getDefaultUserTrackerId());
            this.uncaughtExceptionTraceHandler.init(OpenAccountSDK.getAndroidContext());
        } catch (Exception e2) {
            Log.w(OpenAccountConstants.LOG_TAG, e2);
        }
    }

    public void log(String str, int i2, String str2, String str3, String str4) {
        if (i2 < this.logLevel) {
            return;
        }
        logCat(str, i2, str2, str3, str4);
    }

    public synchronized void release() {
        sendActionCountTrack(true);
        this.actionCountTrackLock.writeLock().lock();
        try {
            this.actionCountTrack.clear();
        } finally {
            this.actionCountTrackLock.writeLock().unlock();
        }
    }

    public void setLogLevel(int i2) {
        this.logLevel = i2;
    }

    public ActionTraceLogger action(String str, String str2) {
        return new ActionTraceLogger(str, str2);
    }

    public void log(int i2, String str, String str2, String str3) {
        logCat(OpenAccountConstants.LOG_TAG, i2, str, str2, str3);
    }
}
