package com.yc.utesdk.command;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.yc.utesdk.command.ElpoTimeOut;
import com.yc.utesdk.log.LogUtils;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class ElpoTimeOut {
    private static volatile ElpoTimeOut instance;
    private int commandType = 0;
    private utendo mc;

    public class utendo extends CountDownTimer {
        public utendo(long j2, long j3) {
            super(j2, j3);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            LogUtils.i("星历计时器 超时了 commandType=" + ElpoTimeOut.this.commandType + "，当做发送数据段OK，发下一段");
            Message message = new Message();
            Objects.requireNonNull(GpsCommandUtil.getInstance());
            message.what = 2;
            GpsCommandUtil.getInstance().mHandler.sendMessageDelayed(message, 20L);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j2) {
        }
    }

    public ElpoTimeOut() {
        LogUtils.i("init ElpoTimeOut T=" + Thread.currentThread().getId());
        this.mc = new utendo(5000L, 1000L);
    }

    public static ElpoTimeOut getInstance() {
        if (instance == null) {
            synchronized (ElpoTimeOut.class) {
                try {
                    if (instance == null) {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            instance = new ElpoTimeOut();
                        } else {
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            LogUtils.i("ElpoTimeOut--latch 0=" + countDownLatch + "," + Thread.currentThread().getId());
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: k0.b
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ElpoTimeOut.utendo(countDownLatch);
                                }
                            });
                            try {
                                LogUtils.i("ElpoTimeOut--1=" + Thread.currentThread().getId() + "," + instance);
                                countDownLatch.await();
                            } catch (InterruptedException e2) {
                                Thread.currentThread().interrupt();
                                LogUtils.e("初始化中断", e2);
                            }
                        }
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    public void cancelCommandTimeOut() {
        if (this.mc != null) {
            LogUtils.i("星历计时器 关闭 commandType=" + this.commandType);
            this.mc.cancel();
        }
    }

    public void setCommandTimeOut(int i2) {
        if (this.mc != null) {
            LogUtils.i("星历计时器 开启 commandType=" + i2);
            this.commandType = i2;
            this.mc.start();
        }
    }

    public static /* synthetic */ void utendo(CountDownLatch countDownLatch) {
        LogUtils.i("ElpoTimeOut--2=" + Thread.currentThread().getId() + "," + instance);
        if (instance == null) {
            LogUtils.i("ElpoTimeOut--3=" + Thread.currentThread().getId() + "," + instance);
            instance = new ElpoTimeOut();
        }
        countDownLatch.countDown();
    }
}
