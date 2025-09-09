package com.yc.utesdk.watchface.close;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes4.dex */
public class OnlineDialTimeOut {
    private static volatile OnlineDialTimeOut instance;
    private int commandType = 0;
    private utendo mc = new utendo(5000, 1000);

    public class utendo extends CountDownTimer {
        public utendo(long j2, long j3) {
            super(j2, j3);
        }

        @Override // android.os.CountDownTimer
        public void onFinish() {
            LogUtils.i("表盘计时器 超时了 commandType=" + OnlineDialTimeOut.this.commandType + "，当做发送数据段OK，发下一段");
            Message message = new Message();
            Objects.requireNonNull(WriteCommandToBLE.getInstance());
            message.what = 2;
            WriteCommandToBLE.getInstance().mHandler.sendMessageDelayed(message, (long) WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME);
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j2) {
        }
    }

    public static OnlineDialTimeOut getInstance() {
        if (instance == null) {
            synchronized (OnlineDialTimeOut.class) {
                try {
                    if (instance == null) {
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            instance = new OnlineDialTimeOut();
                        } else {
                            final CountDownLatch countDownLatch = new CountDownLatch(1);
                            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.yc.utesdk.watchface.close.a
                                @Override // java.lang.Runnable
                                public final void run() {
                                    OnlineDialTimeOut.utendo(countDownLatch);
                                }
                            });
                            try {
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
        LogUtils.i("表盘计时器 关闭 commandType=" + this.commandType);
        utendo utendoVar = this.mc;
        if (utendoVar != null) {
            utendoVar.cancel();
        }
    }

    public void setCommandTimeOut(int i2) {
        LogUtils.i("表盘计时器 开启 commandType=" + i2);
        this.commandType = i2;
        utendo utendoVar = this.mc;
        if (utendoVar != null) {
            utendoVar.start();
        }
    }

    public static /* synthetic */ void utendo(CountDownLatch countDownLatch) {
        if (instance == null) {
            instance = new OnlineDialTimeOut();
        }
        countDownLatch.countDown();
    }
}
