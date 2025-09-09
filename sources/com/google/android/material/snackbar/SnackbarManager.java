package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager snackbarManager;
    private SnackbarRecord currentSnackbar;
    private SnackbarRecord nextSnackbar;
    private final Object lock = new Object();
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.google.android.material.snackbar.SnackbarManager.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            SnackbarManager.this.b((SnackbarRecord) message.obj);
            return true;
        }
    });

    interface Callback {
        void dismiss(int i2);

        void show();
    }

    private static class SnackbarRecord {

        /* renamed from: a, reason: collision with root package name */
        final WeakReference f13553a;

        /* renamed from: b, reason: collision with root package name */
        int f13554b;

        /* renamed from: c, reason: collision with root package name */
        boolean f13555c;

        SnackbarRecord(int i2, Callback callback) {
            this.f13553a = new WeakReference(callback);
            this.f13554b = i2;
        }

        boolean a(Callback callback) {
            return callback != null && this.f13553a.get() == callback;
        }
    }

    private SnackbarManager() {
    }

    static SnackbarManager a() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    private boolean cancelSnackbarLocked(SnackbarRecord snackbarRecord, int i2) {
        Callback callback = (Callback) snackbarRecord.f13553a.get();
        if (callback == null) {
            return false;
        }
        this.handler.removeCallbacksAndMessages(snackbarRecord);
        callback.dismiss(i2);
        return true;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.currentSnackbar;
        return snackbarRecord != null && snackbarRecord.a(callback);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        return snackbarRecord != null && snackbarRecord.a(callback);
    }

    private void scheduleTimeoutLocked(SnackbarRecord snackbarRecord) {
        int i2 = snackbarRecord.f13554b;
        if (i2 == -2) {
            return;
        }
        if (i2 <= 0) {
            i2 = i2 == -1 ? 1500 : LONG_DURATION_MS;
        }
        this.handler.removeCallbacksAndMessages(snackbarRecord);
        Handler handler = this.handler;
        handler.sendMessageDelayed(Message.obtain(handler, 0, snackbarRecord), i2);
    }

    private void showNextSnackbarLocked() {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        if (snackbarRecord != null) {
            this.currentSnackbar = snackbarRecord;
            this.nextSnackbar = null;
            Callback callback = (Callback) snackbarRecord.f13553a.get();
            if (callback != null) {
                callback.show();
            } else {
                this.currentSnackbar = null;
            }
        }
    }

    void b(SnackbarRecord snackbarRecord) {
        synchronized (this.lock) {
            try {
                if (this.currentSnackbar == snackbarRecord || this.nextSnackbar == snackbarRecord) {
                    cancelSnackbarLocked(snackbarRecord, 2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void dismiss(Callback callback, int i2) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    cancelSnackbarLocked(this.currentSnackbar, i2);
                } else if (isNextSnackbarLocked(callback)) {
                    cancelSnackbarLocked(this.nextSnackbar, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        boolean zIsCurrentSnackbarLocked;
        synchronized (this.lock) {
            zIsCurrentSnackbarLocked = isCurrentSnackbarLocked(callback);
        }
        return zIsCurrentSnackbarLocked;
    }

    public boolean isCurrentOrNext(Callback callback) {
        boolean z2;
        synchronized (this.lock) {
            try {
                z2 = isCurrentSnackbarLocked(callback) || isNextSnackbarLocked(callback);
            } finally {
            }
        }
        return z2;
    }

    public void onDismissed(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    this.currentSnackbar = null;
                    if (this.nextSnackbar != null) {
                        showNextSnackbarLocked();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    scheduleTimeoutLocked(this.currentSnackbar);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    if (!snackbarRecord.f13555c) {
                        snackbarRecord.f13555c = true;
                        this.handler.removeCallbacksAndMessages(snackbarRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    if (snackbarRecord.f13555c) {
                        snackbarRecord.f13555c = false;
                        scheduleTimeoutLocked(snackbarRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void show(int i2, Callback callback) {
        synchronized (this.lock) {
            try {
                if (isCurrentSnackbarLocked(callback)) {
                    SnackbarRecord snackbarRecord = this.currentSnackbar;
                    snackbarRecord.f13554b = i2;
                    this.handler.removeCallbacksAndMessages(snackbarRecord);
                    scheduleTimeoutLocked(this.currentSnackbar);
                    return;
                }
                if (isNextSnackbarLocked(callback)) {
                    this.nextSnackbar.f13554b = i2;
                } else {
                    this.nextSnackbar = new SnackbarRecord(i2, callback);
                }
                SnackbarRecord snackbarRecord2 = this.currentSnackbar;
                if (snackbarRecord2 == null || !cancelSnackbarLocked(snackbarRecord2, 4)) {
                    this.currentSnackbar = null;
                    showNextSnackbarLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
