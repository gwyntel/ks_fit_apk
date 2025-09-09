package com.aliyun.alink.business.devicecenter.utils;

import android.os.Handler;
import android.os.Message;
import com.aliyun.alink.business.devicecenter.log.ALog;

/* loaded from: classes2.dex */
public class TimerUtils {
    public static final int MSG_DIAGNOSE = 1056769;
    public static final int MSG_GET_NETWORK_ENV_TIMEOUT = 1060866;
    public static final int MSG_GET_TOKEN_TIMEOUT = 1054982;
    public static final int MSG_PROVISION_TIMEOUT = 1054981;
    public static final int MSG_SCAN_BLE_TIMEOUT = 1060865;

    /* renamed from: a, reason: collision with root package name */
    public InternalHandler f10661a = null;

    /* renamed from: b, reason: collision with root package name */
    public int f10662b;

    public interface ITimerCallback {
        void onTimeout();
    }

    private static final class InternalHandler extends Handler {

        /* renamed from: a, reason: collision with root package name */
        public ITimerCallback f10663a;

        public InternalHandler(ITimerCallback iTimerCallback) {
            super(HandlerThreadUtils.getInstance().getLooper());
            this.f10663a = iTimerCallback;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message == null) {
            }
            switch (message.what) {
                case TimerUtils.MSG_PROVISION_TIMEOUT /* 1054981 */:
                case TimerUtils.MSG_GET_TOKEN_TIMEOUT /* 1054982 */:
                case TimerUtils.MSG_DIAGNOSE /* 1056769 */:
                case TimerUtils.MSG_SCAN_BLE_TIMEOUT /* 1060865 */:
                case TimerUtils.MSG_GET_NETWORK_ENV_TIMEOUT /* 1060866 */:
                    ALog.d("TimerUtils", "onTimeout timerCallback=" + this.f10663a + ", what=" + message.what);
                    try {
                        ITimerCallback iTimerCallback = this.f10663a;
                        if (iTimerCallback != null) {
                            iTimerCallback.onTimeout();
                        }
                        this.f10663a = null;
                        break;
                    } catch (Exception e2) {
                        ALog.w("TimerUtils", "onTimeout exception " + e2);
                        this.f10663a = null;
                        return;
                    }
            }
        }
    }

    public TimerUtils(int i2) {
        this.f10662b = i2;
    }

    public int getTimeout() {
        return this.f10662b;
    }

    public boolean isStart(int i2) {
        InternalHandler internalHandler = this.f10661a;
        if (internalHandler != null) {
            return internalHandler.hasMessages(i2);
        }
        return false;
    }

    public void setCallback(ITimerCallback iTimerCallback) {
        this.f10661a = new InternalHandler(iTimerCallback);
        ALog.d("TimerUtils", "TimerUtils internalHandler=" + this.f10661a + ", looper=" + this.f10661a.getLooper());
    }

    public void start(int i2) {
        ALog.d("TimerUtils", "startTimer message=" + i2 + ", this=" + this + ", timeout=" + this.f10662b);
        InternalHandler internalHandler = this.f10661a;
        if (internalHandler != null) {
            internalHandler.removeMessages(i2);
            this.f10661a.sendEmptyMessageDelayed(i2, this.f10662b);
        }
    }

    public void stop(int i2) {
        ALog.d("TimerUtils", "stopTimer message=" + i2 + ", this=" + this + ", timeout=" + this.f10662b);
        InternalHandler internalHandler = this.f10661a;
        if (internalHandler != null) {
            internalHandler.removeMessages(i2);
        }
    }
}
