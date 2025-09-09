package com.umeng.commonsdk;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.analytics.pro.ay;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.utils.onMessageSendListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class UMConfigureImpl {

    /* renamed from: e, reason: collision with root package name */
    private static final int f22100e = 1000;

    /* renamed from: f, reason: collision with root package name */
    private static ScheduledExecutorService f22101f;

    /* renamed from: g, reason: collision with root package name */
    private static Context f22102g;

    /* renamed from: a, reason: collision with root package name */
    private static String f22096a = ay.b().b(ay.f21380o);

    /* renamed from: b, reason: collision with root package name */
    private static CopyOnWriteArrayList<onMessageSendListener> f22097b = new CopyOnWriteArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    private static int f22098c = 0;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f22099d = false;

    /* renamed from: h, reason: collision with root package name */
    private static int f22103h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static Runnable f22104i = new Runnable() { // from class: com.umeng.commonsdk.UMConfigureImpl.1
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (UMConfigureImpl.f22098c == 0 || UMConfigureImpl.f22103h >= 10) {
                    if (!UMConfigureImpl.f22099d) {
                        boolean unused = UMConfigureImpl.f22099d = true;
                        UMConfigureImpl.b(UMConfigureImpl.f22102g);
                    }
                    if (UMConfigureImpl.f22101f != null) {
                        UMConfigureImpl.f22101f.shutdown();
                        ScheduledExecutorService unused2 = UMConfigureImpl.f22101f = null;
                    }
                }
                UMConfigureImpl.f();
            } catch (Exception unused3) {
            }
        }
    };

    static /* synthetic */ int f() {
        int i2 = f22103h;
        f22103h = i2 + 1;
        return i2;
    }

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        f22102g = context;
        try {
            if (f22098c < 1 || d(context)) {
                UMEnvelopeBuild.setTransmissionSendFlag(true);
            } else {
                UMEnvelopeBuild.setTransmissionSendFlag(false);
                c(context);
                if (f22101f == null) {
                    ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(1);
                    f22101f = scheduledExecutorServiceNewScheduledThreadPool;
                    scheduledExecutorServiceNewScheduledThreadPool.scheduleAtFixedRate(f22104i, 0L, 100L, TimeUnit.MILLISECONDS);
                }
            }
        } catch (Exception unused) {
        }
    }

    public static synchronized void registerInterruptFlag() {
        try {
            f22098c++;
        } catch (Exception unused) {
        }
    }

    public static synchronized void registerMessageSendListener(onMessageSendListener onmessagesendlistener) {
        CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList;
        try {
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList2 = f22097b;
            if (copyOnWriteArrayList2 != null) {
                copyOnWriteArrayList2.add(onmessagesendlistener);
            }
            if (UMEnvelopeBuild.getTransmissionSendFlag() && (copyOnWriteArrayList = f22097b) != null && copyOnWriteArrayList.size() > 0) {
                Iterator<onMessageSendListener> it = f22097b.iterator();
                while (it.hasNext()) {
                    it.next().onMessageSend();
                }
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void removeInterruptFlag() {
        try {
            f22098c--;
        } catch (Exception unused) {
        }
    }

    public static synchronized void removeMessageSendListener(onMessageSendListener onmessagesendlistener) {
        try {
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList = f22097b;
            if (copyOnWriteArrayList != null) {
                copyOnWriteArrayList.remove(onmessagesendlistener);
            }
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized void b(Context context) {
        try {
            UMEnvelopeBuild.setTransmissionSendFlag(true);
            CopyOnWriteArrayList<onMessageSendListener> copyOnWriteArrayList = f22097b;
            if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                Iterator<onMessageSendListener> it = f22097b.iterator();
                while (it.hasNext()) {
                    it.next().onMessageSend();
                }
            }
        } catch (Exception unused) {
        }
    }

    private static void c(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(f22096a, 0);
            if (sharedPreferences != null) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putBoolean(f22096a, true);
                editorEdit.commit();
            }
        } catch (Throwable unused) {
        }
    }

    private static boolean d(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(f22096a, 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getBoolean(f22096a, false);
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }
}
