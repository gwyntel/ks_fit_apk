package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.AIWatchStatus;
import com.yc.utesdk.bean.AIWatchVoiceContent;
import com.yc.utesdk.bean.AIWatchVoiceInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.AIWatchListener;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class AIWatchProcessing {
    private static final String TAG = "AIWatchProcessing--";
    private static volatile AIWatchProcessing instance;
    private int cmdHead;
    private int flag;
    private AIWatchVoiceInfo mAIWatchVoiceInfo;
    private Handler mHandler;
    private final int head01F0 = 496;
    private byte[] mAllSendData = null;
    private boolean isSendDataFD = false;
    private boolean isSendFinish = true;
    private int mAppCrc = 0;
    private boolean mAIWatchEnable = false;
    private byte[] mAIWatchOpusData = new byte[0];
    private final int SEND_DATA_SEG_MSG = 1;

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                AIWatchProcessing.this.utenif(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 1;
            message2.arg1 = message.arg1;
            AIWatchProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public AIWatchProcessing() {
        utendo();
    }

    public static AIWatchProcessing getInstance() {
        if (instance == null) {
            synchronized (AIWatchProcessing.class) {
                try {
                    if (instance == null) {
                        instance = new AIWatchProcessing();
                    }
                } finally {
                }
            }
        }
        return instance;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.yc.utesdk.bean.AIWatchVoiceInfo] */
    public void doAIWatchData01F0(byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        AIWatchStatus aIWatchStatus;
        switch (((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[3] & 255)) {
            case 43521:
                this.mAIWatchEnable = (bArr[5] & 255) == 1;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onAIWatchNotify(3, Boolean.valueOf(this.mAIWatchEnable));
                return;
            case 43777:
                int i3 = bArr[4] & 255;
                if (i3 != 253) {
                    utenfor(i3);
                    return;
                }
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                LogUtils.i("setAIWatchVoiceContent 完成 bleCrc =" + (bArr[5] & 255));
                UteListenerManager.getInstance().onAIWatchStatus(true, 4);
                return;
            case 43778:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onAIWatchStatus(true, 1);
                return;
            case 43779:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onAIWatchStatus(true, 2);
                return;
            case 44033:
                AIWatchStatus aIWatchStatus2 = new AIWatchStatus();
                aIWatchStatus2.setStatus(bArr[5] & 255);
                uteListenerManager = UteListenerManager.getInstance();
                i2 = AIWatchListener.AI_WATCH_STATUS_NOTIFY;
                aIWatchStatus = aIWatchStatus2;
                break;
            case 44034:
                int i4 = ((bArr[4] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[5] & 255);
                if (i4 != 65021) {
                    int length = bArr.length - 6;
                    byte[] bArr2 = new byte[length];
                    System.arraycopy(bArr, 6, bArr2, 0, length);
                    if (i4 == 0) {
                        this.mAIWatchOpusData = bArr2;
                        return;
                    } else {
                        this.mAIWatchOpusData = ByteDataUtil.getInstance().copyTwoArrays(this.mAIWatchOpusData, bArr2);
                        return;
                    }
                }
                byte b2 = bArr[6];
                LogUtils.i("mGptVoiceOpusData =" + GBUtils.getInstance().bytes2HexString(this.mAIWatchOpusData));
                this.mAIWatchVoiceInfo = utendo(this.mAIWatchOpusData);
                uteListenerManager = UteListenerManager.getInstance();
                ?? r02 = this.mAIWatchVoiceInfo;
                i2 = AIWatchListener.AI_WATCH_VOICE_DATA_NOTIFY;
                aIWatchStatus = r02;
                break;
            default:
                return;
        }
        uteListenerManager.onAIWatchNotify(i2, aIWatchStatus);
    }

    public void queryAIWatchEnable() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_AI_WATCH_ENABLE);
        this.mAIWatchEnable = false;
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -16, -86, 1});
    }

    public void setAIWatchEnable(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_AI_WATCH_ENABLE);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -16, -85, 3, 1, z2 ? (byte) 1 : (byte) 0});
    }

    public void setAIWatchStatus(AIWatchStatus aIWatchStatus) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_AI_WATCH_STATUS);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{1, -16, -85, 2, 1, (byte) aIWatchStatus.getStatus()});
    }

    public void setAIWatchVoiceContent(AIWatchVoiceContent aIWatchVoiceContent) {
        byte[] bArrUtendo = utendo(aIWatchVoiceContent.getContent());
        if (bArrUtendo == null) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_AI_WATCH_VOICE_CONTENT);
        LogUtils.e("setAIWatchVoiceContent = " + new GBUtils().bytes2HexString(bArrUtendo));
        this.mAllSendData = bArrUtendo;
        this.cmdHead = 496;
        this.flag = 43777;
        this.mAppCrc = 0;
        utenif(0);
    }

    public final void utenfor(int i2) {
        Message message = new Message();
        message.what = 1;
        message.arg1 = i2 + 1;
        this.mHandler.sendMessageDelayed(message, 0L);
    }

    public final synchronized void utenif(int i2) {
        try {
            byte[] bArr = this.mAllSendData;
            if (bArr == null) {
                return;
            }
            int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
            int i3 = maxCommunicationLength - 5;
            int length = bArr.length;
            int i4 = length / i3;
            int i5 = length % i3;
            LogUtils.i(TAG, "max=" + i3 + ",length=" + length + ",sendCount=" + i4 + ",lastCount=" + i5 + ",section=" + i2 + ",isSendDataFD=" + this.isSendDataFD + ",ThreadId=" + Thread.currentThread().getId());
            int i6 = 0;
            if (i2 < i4) {
                byte[] bArr2 = new byte[maxCommunicationLength];
                int i7 = this.cmdHead;
                bArr2[0] = (byte) ((i7 >> 8) & 255);
                bArr2[1] = (byte) (i7 & 255);
                int i8 = this.flag;
                bArr2[2] = (byte) ((i8 >> 8) & 255);
                bArr2[3] = (byte) (i8 & 255);
                bArr2[4] = (byte) i2;
                System.arraycopy(bArr, i2 * i3, bArr2, 5, i3);
                while (i6 < maxCommunicationLength) {
                    this.mAppCrc = bArr2[i6] ^ this.mAppCrc;
                    i6++;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
                if (i5 == 0) {
                    this.isSendDataFD = true;
                }
            } else if (this.isSendDataFD) {
                utendo(this.mAppCrc);
                this.isSendDataFD = false;
            } else if (i5 != 0) {
                int i9 = i5 + 5;
                byte[] bArr3 = new byte[i9];
                int i10 = this.cmdHead;
                bArr3[0] = (byte) ((i10 >> 8) & 255);
                bArr3[1] = (byte) (i10 & 255);
                int i11 = this.flag;
                bArr3[2] = (byte) ((i11 >> 8) & 255);
                bArr3[3] = (byte) (i11 & 255);
                bArr3[4] = (byte) i2;
                System.arraycopy(bArr, i3 * i2, bArr3, 5, i5);
                while (i6 < i9) {
                    this.mAppCrc = bArr3[i6] ^ this.mAppCrc;
                    i6++;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr3);
                this.isSendDataFD = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public final AIWatchVoiceInfo utendo(byte[] bArr) {
        AIWatchVoiceInfo aIWatchVoiceInfo = new AIWatchVoiceInfo();
        if (bArr != null && bArr.length > 4) {
            int i2 = ((bArr[0] & 255) << 24) | ((bArr[1] & 255) << 16) | ((bArr[2] & 255) << 8) | (bArr[3] & 255);
            int length = bArr.length - 4;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 4, bArr2, 0, length);
            aIWatchVoiceInfo.setDataLength(i2);
            aIWatchVoiceInfo.setVoiceOpusData(bArr2);
        }
        return aIWatchVoiceInfo;
    }

    public final byte[] utendo(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > 128) {
                str = str.substring(0, 128);
            }
            String strString2unicode = GBUtils.getInstance().string2unicode(str);
            sb.append(String.format("%04X", Integer.valueOf(strString2unicode.length() / 2)));
            sb.append(strString2unicode);
        }
        return GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2) {
        int i3 = this.cmdHead;
        int i4 = this.flag;
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{(byte) ((i3 >> 8) & 255), (byte) (i3 & 255), (byte) ((i4 >> 8) & 255), (byte) (i4 & 255), -3, (byte) i2});
        this.mAppCrc = 0;
    }
}
