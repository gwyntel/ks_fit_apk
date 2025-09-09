package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.yc.utesdk.bean.QRCodeInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.NotifyUtils;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class QRCodeProcessing {
    private static QRCodeProcessing instance;
    private Handler mHandler;
    private final int headDC52 = 56402;
    private final int SEND_DATA_SEG_MSG = 1;
    private byte[] mAllSendData = null;
    private boolean isSendDataFD = false;
    private int mAppCrc = 0;

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
                QRCodeProcessing.this.utenif(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 1;
            message2.arg1 = message.arg1;
            QRCodeProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public QRCodeProcessing() {
        utendo();
    }

    public static synchronized QRCodeProcessing getInstance() {
        try {
            if (instance == null) {
                instance = new QRCodeProcessing();
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    public void doQRCodeDC52(byte[] bArr) {
        UteListenerManager uteListenerManager;
        if ((bArr[1] & 255) != 82) {
            return;
        }
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i2 = 2;
        int i3 = bArr[2] & 255;
        if (i3 == 253) {
            LogUtils.i("BLE接收二维码数据成功");
            uteListenerManager = UteListenerManager.getInstance();
        } else if (i3 != 255) {
            utenfor(i3);
            return;
        } else {
            LogUtils.i("BLE接收二维码数据失败或数据不对");
            uteListenerManager = UteListenerManager.getInstance();
            i2 = 3;
        }
        uteListenerManager.onDeviceQRCodeState(i2);
    }

    public void sendQRCodeData(QRCodeInfo qRCodeInfo) {
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().string2unicode(qRCodeInfo.getTitle()));
        if (bArrHexStringToBytes == null || bArrHexStringToBytes.length == 0) {
            bArrHexStringToBytes = new byte[0];
        } else if (bArrHexStringToBytes.length > 100) {
            bArrHexStringToBytes = Arrays.copyOf(bArrHexStringToBytes, 100);
        }
        byte[] bArrHexStringToBytes2 = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().string2unicode(qRCodeInfo.getUrl()));
        if (bArrHexStringToBytes2 == null || bArrHexStringToBytes2.length == 0) {
            bArrHexStringToBytes2 = new byte[0];
        } else if (bArrHexStringToBytes2.length > 300) {
            bArrHexStringToBytes2 = Arrays.copyOf(bArrHexStringToBytes2, 300);
        }
        byte[] bArrUtendo = utendo(new byte[]{(byte) bArrHexStringToBytes.length, (byte) ((bArrHexStringToBytes2.length >> 8) & 255), (byte) (bArrHexStringToBytes2.length & 255)}, bArrHexStringToBytes);
        this.mAllSendData = bArrUtendo;
        this.mAllSendData = utendo(bArrUtendo, bArrHexStringToBytes2);
        LogUtils.i("mAllSendData.length() =" + this.mAllSendData.length);
        this.mAppCrc = 0;
        this.isSendDataFD = false;
        UteListenerManager.getInstance().onDeviceQRCodeState(1);
        utenif(0);
    }

    public final void utenfor(int i2) {
        Message message = new Message();
        message.what = 1;
        message.arg1 = i2 + 1;
        this.mHandler.sendMessageDelayed(message, 0L);
    }

    public final void utenif(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(174);
        byte[] bArr = this.mAllSendData;
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 3;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 0;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = -36;
            bArr2[1] = 82;
            bArr2[2] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 3, i3);
            while (i6 < maxCommunicationLength) {
                this.mAppCrc = bArr2[i6] ^ this.mAppCrc;
                i6++;
            }
            utendo(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isSendDataFD) {
            utendo(this.mAppCrc);
            this.isSendDataFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 3;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = -36;
            bArr3[1] = 82;
            bArr3[2] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 3, i5);
            while (i6 < i7) {
                this.mAppCrc = bArr3[i6] ^ this.mAppCrc;
                i6++;
            }
            utendo(bArr3);
        }
        this.isSendDataFD = true;
    }

    public static byte[] utendo(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(int i2) {
        LogUtils.i("sendQRCodeData mAppCrc =" + (i2 & 255));
        utendo(new byte[]{-36, 82, -3, (byte) i2});
    }

    public final void utendo(byte[] bArr) {
        if (UteBleClient.getUteBleClient().isConnected()) {
            NotifyUtils.getInstance().writeCharaBle5(bArr);
        }
    }
}
