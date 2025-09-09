package com.yc.utesdk.command;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.MessageReplyDataInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class MessageReplyDataCommandUtil {
    private static MessageReplyDataCommandUtil Instance = null;
    private static final String TAG = "MessageReplyDataCommandUtil--";
    private Handler mHandler;
    private byte[] mAllSendData = null;
    private int head6A = 106;
    private boolean isSendDataFD = false;
    private int mAppCrc = 0;
    private byte[] mNotifyMessageReplyData = new byte[0];
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
                MessageReplyDataCommandUtil.this.utenif(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 1;
            message2.arg1 = message.arg1;
            MessageReplyDataCommandUtil.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public MessageReplyDataCommandUtil() {
        utendo();
    }

    public static MessageReplyDataCommandUtil getInstance() {
        if (Instance == null) {
            Instance = new MessageReplyDataCommandUtil();
        }
        return Instance;
    }

    public void analyNotifyMessageReplyData(byte[] bArr) {
        MessageReplyDataInfo messageReplyDataInfo = new MessageReplyDataInfo();
        if (bArr != null && bArr.length > 8) {
            int i2 = ((bArr[0] & 255) << 8) | (bArr[1] & 255);
            int i3 = ((bArr[3] & 255) << 8) | (bArr[2] & 255);
            int i4 = ((bArr[5] & 255) << 8) | (bArr[4] & 255);
            int i5 = ((bArr[7] & 255) << 8) | (bArr[6] & 255);
            int length = bArr.length - 8;
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 8, bArr2, 0, length);
            messageReplyDataInfo.setMagic(i3);
            messageReplyDataInfo.setCommandId(i4);
            messageReplyDataInfo.setPayLoad(bArr2);
            LogUtils.i("dataLength =" + i2 + ",magic =" + i3 + ",commandId =" + i4 + ",payLoadLen =" + i5);
            StringBuilder sb = new StringBuilder();
            sb.append("payLoad =");
            sb.append(GBUtils.getInstance().bytes2HexString(bArr2));
            LogUtils.i(sb.toString());
        }
        UteListenerManager.getInstance().onMessageReplyData(messageReplyDataInfo);
    }

    public void doMessageReplyData6A(byte[] bArr) {
        int i2 = ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255);
        if (i2 == 43777) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            int i3 = bArr[3] & 255;
            if (i3 != 253) {
                utenfor(i3);
                return;
            }
            LogUtils.i("sendMessageReplyData 完成 bleCrc =" + (bArr[4] & 255));
            UteListenerManager.getInstance().onMessageReplyState(true, 1);
            return;
        }
        if (i2 != 44033) {
            return;
        }
        int i4 = bArr[3] & 255;
        if (i4 == 253) {
            byte b2 = bArr[4];
            LogUtils.i("mNotifyMessageReplyData =" + GBUtils.getInstance().bytes2HexString(this.mNotifyMessageReplyData));
            analyNotifyMessageReplyData(this.mNotifyMessageReplyData);
            return;
        }
        int length = bArr.length - 4;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 4, bArr2, 0, length);
        if (i4 == 0) {
            this.mNotifyMessageReplyData = bArr2;
        } else {
            this.mNotifyMessageReplyData = ByteDataUtil.getInstance().copyTwoArrays(this.mNotifyMessageReplyData, bArr2);
        }
    }

    public void sendMessageReplyData(MessageReplyDataInfo messageReplyDataInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SEND_MESSAGE_REPLY_DATA_COMMAND);
        byte[] bArrUtendo = utendo(messageReplyDataInfo);
        LogUtils.e("sendMessageReplyData = " + new GBUtils().bytes2HexString(bArrUtendo));
        this.mAllSendData = bArrUtendo;
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
            int i3 = maxCommunicationLength - 4;
            int length = bArr.length;
            int i4 = length / i3;
            int i5 = length % i3;
            LogUtils.i(TAG, "max=" + i3 + ",length=" + length + ",sendCount=" + i4 + ",lastCount=" + i5 + ",section=" + i2 + ",isSendDataFD=" + this.isSendDataFD + ",ThreadId=" + Thread.currentThread().getId());
            int i6 = 0;
            if (i2 < i4) {
                byte[] bArr2 = new byte[maxCommunicationLength];
                bArr2[0] = (byte) (this.head6A & 255);
                bArr2[1] = -85;
                bArr2[2] = 1;
                bArr2[3] = (byte) i2;
                System.arraycopy(bArr, i3 * i2, bArr2, 4, i3);
                while (i6 < maxCommunicationLength) {
                    this.mAppCrc = bArr2[i6] ^ this.mAppCrc;
                    i6++;
                }
                WriteCommandToBLE.getInstance().writeCharaBle5(bArr2);
                if (i5 == 0 && i2 == i4 - 1) {
                    this.isSendDataFD = true;
                }
            } else if (this.isSendDataFD) {
                utendo(this.mAppCrc);
                this.isSendDataFD = false;
            } else if (i5 != 0) {
                int i7 = i5 + 4;
                byte[] bArr3 = new byte[i7];
                bArr3[0] = (byte) (this.head6A & 255);
                bArr3[1] = -85;
                bArr3[2] = 1;
                bArr3[3] = (byte) i2;
                System.arraycopy(bArr, i3 * i2, bArr3, 4, i5);
                while (i6 < i7) {
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

    public final byte[] utendo(MessageReplyDataInfo messageReplyDataInfo) {
        int magic = messageReplyDataInfo.getMagic();
        int commandId = messageReplyDataInfo.getCommandId();
        byte[] payLoad = messageReplyDataInfo.getPayLoad();
        byte[] bArrCopyTwoArrays = new byte[8];
        bArrCopyTwoArrays[3] = (byte) ((magic >> 8) & 255);
        bArrCopyTwoArrays[2] = (byte) (magic & 255);
        bArrCopyTwoArrays[5] = (byte) ((commandId >> 8) & 255);
        bArrCopyTwoArrays[4] = (byte) (commandId & 255);
        int length = payLoad != null ? payLoad.length : 0;
        bArrCopyTwoArrays[7] = (byte) ((length >> 8) & 255);
        bArrCopyTwoArrays[6] = (byte) (length & 255);
        if (length > 0) {
            bArrCopyTwoArrays = ByteDataUtil.getInstance().copyTwoArrays(bArrCopyTwoArrays, payLoad);
        }
        bArrCopyTwoArrays[0] = (byte) (((bArrCopyTwoArrays.length - 2) >> 8) & 255);
        bArrCopyTwoArrays[1] = (byte) ((bArrCopyTwoArrays.length - 2) & 255);
        return bArrCopyTwoArrays;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2) {
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{(byte) (this.head6A & 255), -85, 1, -3, (byte) i2});
        this.mAppCrc = 0;
    }
}
