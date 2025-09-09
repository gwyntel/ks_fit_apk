package com.yc.utesdk.data;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import com.google.gson.Gson;
import com.yc.utesdk.bean.PrayerInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class PrayProcessing {
    private static PrayProcessing instance;
    private Handler mHandler;
    private int NOSectionPrayer = 0;
    private byte[] prayerData = null;
    public boolean isPrayerSendFD = false;
    private int prayerCRC = 0;
    public int maxMtu = 0;
    private StringBuilder clockInfo = new StringBuilder();
    private final int delayWriteTime = 20;
    private final int WRITE_PUSH_COMMAND_MSG = 0;
    private int mContentStatus = -1;
    private final int SYNC_PRAYER_SECTION = 1;
    private List<PrayerInfo> mPrayerInfos = new ArrayList();
    private int startIndex = 0;
    private int endIndex = 0;
    private List<byte[]> mList = new ArrayList();
    private WriteCommandToBLE mWriteCommandToBLE = WriteCommandToBLE.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            if (!DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                PrayProcessing.this.utendo(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 0;
            message2.arg1 = message.arg1;
            PrayProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public PrayProcessing() {
        utendo();
    }

    public static PrayProcessing getInstance() {
        if (instance == null) {
            instance = new PrayProcessing();
        }
        return instance;
    }

    public void dealWithPray(String str, byte[] bArr) {
        StringBuilder sb;
        UteListenerManager uteListenerManager;
        int i2;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i3 = bArr[1] & 255;
        if (i3 == 255) {
            UteListenerManager.getInstance().onDevicePrayerStatus(false, 1);
        }
        switch (i3) {
            case 170:
                if ((bArr[2] & 255) == 253) {
                    LogUtils.i("AA clockInfo =" + new Gson().toJson(this.clockInfo));
                    utendo(GBUtils.getInstance().hexStringToBytes(this.clockInfo.toString()), false);
                    sb = new StringBuilder();
                    this.clockInfo = sb;
                    break;
                }
                this.clockInfo.append(str.substring(6));
                CommandTimeOutUtils.getInstance().setCommandTimeOut(202);
                break;
            case 171:
                if ((bArr[2] & 255) == 253) {
                    LogUtils.i("clockInfo =" + new Gson().toJson(this.clockInfo));
                    utendo(GBUtils.getInstance().hexStringToBytes(this.clockInfo.toString()), true);
                    sb = new StringBuilder();
                    this.clockInfo = sb;
                    break;
                }
                this.clockInfo.append(str.substring(6));
                CommandTimeOutUtils.getInstance().setCommandTimeOut(202);
                break;
            case 172:
                if ((bArr[2] & 255) != 1) {
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 5;
                    uteListenerManager.onDevicePrayerStatus(true, i2);
                    break;
                } else {
                    UteListenerManager.getInstance().onDevicePrayerStatus(true, 6);
                    break;
                }
            default:
                switch (i3) {
                    case 250:
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 4;
                        uteListenerManager.onDevicePrayerStatus(true, i2);
                        break;
                    case 251:
                        if ((bArr[2] & 255) != 253) {
                            this.mContentStatus = 1;
                            utenif();
                            break;
                        }
                    case 252:
                        UteListenerManager.getInstance().onDevicePrayerStatus(true, 1);
                        break;
                }
                break;
        }
    }

    public void queryPrayer() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(202);
        this.mWriteCommandToBLE.writeCharaBle5(new byte[]{101, -86});
    }

    public void queryPrayerShowStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(204);
        this.mWriteCommandToBLE.writeCharaBle5(new byte[]{101, -84});
    }

    public void sendPrayerSection() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(201);
        if (this.prayerData == null) {
            return;
        }
        if (this.NOSectionPrayer == 0) {
            this.isPrayerSendFD = false;
        }
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        this.maxMtu = maxCommunicationLength;
        byte[] bArr = this.prayerData;
        int i2 = maxCommunicationLength - 3;
        int length = bArr.length;
        int i3 = length / i2;
        int i4 = length % i2;
        LogUtils.i("Prayer max=" + i2 + ",length=" + length + ",sendCount =" + i3 + "，lastCount = " + i4 + ",NOsectionAlarmClock=" + this.NOSectionPrayer);
        int i5 = this.NOSectionPrayer;
        int i6 = 2;
        if (i5 < i3) {
            int i7 = this.maxMtu;
            byte[] bArr2 = new byte[i7];
            bArr2[0] = 101;
            bArr2[1] = -5;
            bArr2[2] = (byte) (i5 & 255);
            int i8 = i5 * i2;
            while (true) {
                int i9 = this.NOSectionPrayer * i2;
                if (i8 >= i2 + i9) {
                    break;
                }
                bArr2[(i8 - i9) + 3] = bArr[i8];
                i8++;
            }
            this.mWriteCommandToBLE.writeCharaBle5(bArr2);
            while (i6 < i7) {
                this.prayerCRC ^= bArr2[i6];
                i6++;
            }
            this.NOSectionPrayer++;
            if (i4 != 0) {
                return;
            }
        } else {
            LogUtils.i("isAlarmClockSendFD = " + this.isPrayerSendFD);
            if (this.isPrayerSendFD) {
                utenfor();
                this.isPrayerSendFD = false;
            } else if (i4 != 0) {
                int i10 = i4 + 3;
                byte[] bArr3 = new byte[i10];
                bArr3[0] = 101;
                bArr3[1] = -5;
                int i11 = this.NOSectionPrayer;
                bArr3[2] = (byte) (i11 & 255);
                for (int i12 = i11 * i2; i12 < length; i12++) {
                    bArr3[(i12 - (this.NOSectionPrayer * i2)) + 3] = bArr[i12];
                }
                this.mWriteCommandToBLE.writeCharaBle5(bArr3);
                while (i6 < i10) {
                    this.prayerCRC ^= bArr3[i6];
                    i6++;
                }
                this.NOSectionPrayer++;
            }
        }
        this.isPrayerSendFD = true;
    }

    public void setPrayer(List<PrayerInfo> list) {
        LogUtils.i("setPrayer=" + new Gson().toJson(list));
        if (list == null || list.size() == 0) {
            utenint();
            return;
        }
        int size = list.size();
        byte[] bArrCopyTwoArrays = null;
        int i2 = 0;
        while (i2 < size) {
            PrayerInfo prayerInfo = list.get(i2);
            String calendar = prayerInfo.getCalendar();
            int i3 = Integer.parseInt(calendar.substring(0, 4));
            byte[] bArr = {(byte) ((65280 & i3) >> 8), (byte) (i3 & 255), (byte) (Integer.parseInt(calendar.substring(4, 6)) & 255), (byte) (Integer.parseInt(calendar.substring(6, 8)) & 255)};
            List<PrayerInfo.Pray> prayList = prayerInfo.getPrayList();
            byte[] bArrUtendo = utendo(utendo(4, prayList), 4, utendo(utendo(3, prayList), 3, utendo(utendo(2, prayList), 2, utendo(utendo(1, prayList), 1, utendo(utendo(0, prayList), 0, bArr)))));
            bArrCopyTwoArrays = i2 == 0 ? bArrUtendo : ByteDataUtil.getInstance().copyTwoArrays(bArrCopyTwoArrays, bArrUtendo);
            i2++;
        }
        LogUtils.i("dataAll=" + GBUtils.getInstance().bytes2HexString(bArrCopyTwoArrays));
        this.prayerData = bArrCopyTwoArrays;
        this.NOSectionPrayer = 0;
        this.isPrayerSendFD = false;
        this.prayerCRC = 0;
        sendPrayerSection();
    }

    public void showPray(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(203);
        byte[] bArr = new byte[3];
        bArr[0] = 101;
        bArr[1] = -6;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        this.mWriteCommandToBLE.writeCharaBle5(bArr);
    }

    public final void utenfor() {
        this.mWriteCommandToBLE.writeCharaBle5(new byte[]{101, -5, -3, (byte) (this.prayerCRC & 255)});
    }

    public final void utenif() {
        Message message = new Message();
        message.what = 0;
        message.arg1 = this.mContentStatus;
        this.mHandler.sendMessage(message);
    }

    public final void utenint() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(201);
        this.mWriteCommandToBLE.writeCharaBle5(new byte[]{101, -4});
    }

    public final byte[] utendo(PrayerInfo.Pray pray, int i2, byte[] bArr) {
        byte[] bArr2 = new byte[4];
        if (pray != null) {
            boolean zIsPraySwitch = pray.isPraySwitch();
            int prayTimeHour = pray.getPrayTimeHour();
            int prayTimeMinute = pray.getPrayTimeMinute();
            bArr2[0] = (byte) (i2 & 255);
            if (zIsPraySwitch) {
                bArr2[1] = 1;
            } else {
                bArr2[1] = 0;
            }
            bArr2[2] = (byte) (prayTimeHour & 255);
            bArr2[3] = (byte) (prayTimeMinute & 255);
        } else {
            bArr2[0] = (byte) (i2 & 255);
            bArr2[1] = 0;
            bArr2[2] = -1;
            bArr2[3] = -1;
        }
        return ByteDataUtil.getInstance().copyTwoArrays(bArr, bArr2);
    }

    public final PrayerInfo.Pray utendo(int i2, List list) {
        for (int i3 = 0; i3 < list.size(); i3++) {
            PrayerInfo.Pray pray = (PrayerInfo.Pray) list.get(i3);
            if (i2 == pray.getPrayType()) {
                return pray;
            }
        }
        return null;
    }

    public final String utendo(byte[] bArr) {
        int i2 = ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[1] & 255);
        int i3 = bArr[2] & 255;
        int i4 = bArr[3] & 255;
        String strValueOf = String.valueOf(i4);
        String strValueOf2 = String.valueOf(i3);
        String strValueOf3 = String.valueOf(i2);
        if (i4 < 10) {
            strValueOf = "0" + i4;
        }
        if (i3 < 10) {
            strValueOf2 = "0" + i3;
        }
        return i2 == 0 ? "00000000" : strValueOf3 + strValueOf2 + strValueOf;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utendo(byte[] bArr, boolean z2) {
        List<PrayerInfo> list;
        int i2 = 2;
        if (bArr == null) {
            LogUtils.i("PrayerData null");
            if (z2) {
                return;
            }
            UteListenerManager.getInstance().onDevicePrayerInfo(this.mPrayerInfos, 2);
            return;
        }
        int length = bArr.length;
        int i3 = this.endIndex + 24;
        this.endIndex = i3;
        if (i3 <= length) {
            byte[] bArr2 = new byte[24];
            System.arraycopy(bArr, this.startIndex, bArr2, 0, 24);
            this.mList.add(bArr2);
            int i4 = this.endIndex;
            this.startIndex = i4;
            if (i4 != length) {
                utendo(bArr, z2);
                return;
            }
            int size = this.mList.size();
            this.mPrayerInfos = new ArrayList();
            for (int i5 = 0; i5 < size; i5++) {
                byte[] bArr3 = this.mList.get(i5);
                String strUtendo = utendo(bArr3);
                ArrayList arrayList = new ArrayList();
                for (int i6 = 0; i6 < 5; i6++) {
                    int i7 = i6 * 4;
                    int i8 = bArr3[i7 + 4] & 255;
                    int i9 = bArr3[i7 + 5] & 255;
                    int i10 = bArr3[i7 + 6] & 255;
                    int i11 = bArr3[i7 + 7] & 255;
                    PrayerInfo.Pray pray = new PrayerInfo.Pray();
                    pray.setPrayType(i8);
                    boolean z3 = true;
                    if (i9 != 1) {
                        z3 = false;
                    }
                    pray.setPraySwitch(z3);
                    pray.setPrayTimeHour(i10);
                    pray.setPrayTimeMinute(i11);
                    arrayList.add(pray);
                }
                PrayerInfo prayerInfo = new PrayerInfo();
                prayerInfo.setCalendar(strUtendo);
                prayerInfo.setPrayList(arrayList);
                this.mPrayerInfos.add(prayerInfo);
            }
            this.startIndex = 0;
            this.endIndex = 0;
            this.mList = new ArrayList();
            LogUtils.i("mPrayerInfos =" + new Gson().toJson(this.mPrayerInfos));
            UteListenerManager uteListenerManager = UteListenerManager.getInstance();
            if (z2) {
                list = this.mPrayerInfos;
                i2 = 3;
            } else {
                list = this.mPrayerInfos;
            }
            uteListenerManager.onDevicePrayerInfo(list, i2);
            this.mPrayerInfos = new ArrayList();
        }
    }

    public final void utendo(int i2) {
        if (i2 != 1) {
            return;
        }
        sendPrayerSection();
    }
}
