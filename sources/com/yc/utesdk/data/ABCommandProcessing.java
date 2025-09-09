package com.yc.utesdk.data;

import aisble.error.GattError;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.DeviceWidgetInfo;
import com.yc.utesdk.bean.LabelAlarmClockInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.GBUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ABCommandProcessing {
    private static ABCommandProcessing instance;
    private Handler mHandler;
    private byte[] queryDeviceWidgetData;
    private StringBuilder clockInfo = new StringBuilder();
    private final int delayWriteTime50 = 20;
    private int mContentStatus = -1;
    private final int WRITE_PUSH_COMMAND_MSG = 0;
    private final int SYNC_LABEL_ALARM_CLOCK_SECTION = 1;
    private final int SYNC_CUSTOMIZE_WIDGET_LISTS = 2;
    private List<byte[]> mLlist = new ArrayList();
    private int startIndex = 0;
    private int endIndex = 0;
    private List<LabelAlarmClockInfo> mAlarmClockInfos = new ArrayList();

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
                ABCommandProcessing.this.utendo(message.arg1);
                return;
            }
            LogUtils.i("设备忙，等20ms");
            Message message2 = new Message();
            message2.what = 0;
            message2.arg1 = message.arg1;
            ABCommandProcessing.this.mHandler.sendMessageDelayed(message2, 20L);
        }
    }

    public ABCommandProcessing() {
        utendo();
    }

    public static ABCommandProcessing getInstance() {
        if (instance == null) {
            instance = new ABCommandProcessing();
        }
        return instance;
    }

    public void dealWithABCommand(byte[] bArr) {
        UteListenerManager uteListenerManager;
        int i2;
        int i3 = bArr[1] & 255;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        switch (i3) {
            case 0:
                UteListenerManager.getInstance().onSimpleCallback(true, 14);
                return;
            case 1:
                UteListenerManager.getInstance().onDeviceAlarmStatus(true, 1);
                return;
            case 2:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 2;
                break;
            case 3:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 3;
                break;
            case 4:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 4;
                break;
            case 5:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 5;
                break;
            case 6:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 6;
                break;
            case 7:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 7;
                break;
            case 8:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 8;
                break;
            case 9:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 9;
                break;
            case 10:
                uteListenerManager = UteListenerManager.getInstance();
                i2 = 10;
                break;
            default:
                return;
        }
        uteListenerManager.onDeviceAlarmStatus(true, i2);
    }

    public void dealWithDeviceWidget(byte[] bArr) {
        UteListenerManager uteListenerManager;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        boolean z2 = true;
        int i2 = bArr[1] & 255;
        if (i2 != 170) {
            if (i2 == 251) {
                this.mContentStatus = 2;
                utenif();
                return;
            }
            if (i2 == 253) {
                uteListenerManager = UteListenerManager.getInstance();
            } else {
                if (i2 != 255) {
                    return;
                }
                uteListenerManager = UteListenerManager.getInstance();
                z2 = false;
            }
            uteListenerManager.onDeviceWidgetStatus(z2, 2);
            return;
        }
        if ((bArr[2] & 255) == 253) {
            utenfor(this.queryDeviceWidgetData);
            this.queryDeviceWidgetData = null;
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(123);
        int i3 = bArr[2] & 255;
        byte[] bArrUtendo = utendo(bArr, 3);
        if (i3 == 0) {
            this.queryDeviceWidgetData = bArrUtendo;
        } else {
            this.queryDeviceWidgetData = ByteDataUtil.getInstance().copyTwoArrays(this.queryDeviceWidgetData, bArrUtendo);
        }
    }

    public void dealWithLableAlarmClock(String str, byte[] bArr) throws ParseException {
        StringBuilder sb;
        CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
        int i2 = bArr[1] & 255;
        if (i2 != 170) {
            if (i2 == 255) {
                UteListenerManager.getInstance().onDeviceLabelAlarmStatus(false, 1);
                return;
            }
            switch (i2) {
                case 251:
                    this.mContentStatus = 1;
                    utenif();
                    break;
                case 252:
                case GattError.GATT_CCCD_CFG_ERROR /* 253 */:
                    UteListenerManager.getInstance().onDeviceLabelAlarmStatus(true, 1);
                    break;
            }
            return;
        }
        int i3 = bArr[2] & 255;
        if (i3 == 253) {
            utenif(GBUtils.getInstance().hexStringToBytes(this.clockInfo.toString()));
            sb = new StringBuilder();
        } else if (i3 != 255) {
            this.clockInfo.append(str.substring(6));
            CommandTimeOutUtils.getInstance().setCommandTimeOut(117);
            return;
        } else {
            this.clockInfo.append(str.substring(6));
            utenif(GBUtils.getInstance().hexStringToBytes(this.clockInfo.toString()));
            sb = new StringBuilder();
        }
        this.clockInfo = sb;
    }

    public final void utenfor(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int length = bArr.length / 3;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 3;
            byte b2 = bArr[i3];
            byte b3 = bArr[i3 + 1];
            DeviceWidgetInfo deviceWidgetInfo = new DeviceWidgetInfo(b2, b3, bArr[i3 + 2]);
            arrayList.add(deviceWidgetInfo);
            if (b3 == 1) {
                arrayList2.add(deviceWidgetInfo);
            } else {
                arrayList3.add(deviceWidgetInfo);
            }
        }
        UteListenerManager.getInstance().onQueryDeviceWidgetSuccess(arrayList, arrayList2, arrayList3);
    }

    public final void utenif(byte[] bArr) throws ParseException {
        String str;
        if (bArr == null) {
            str = "LabelAlarmClockData null";
        } else {
            int length = bArr.length;
            char c2 = 7;
            if (length != 7) {
                boolean zIsHasFunction_8 = DeviceMode.isHasFunction_8(8192);
                int i2 = zIsHasFunction_8 ? 16 : 15;
                int i3 = this.startIndex;
                int i4 = i3 + i2;
                if (length >= i4) {
                    int i5 = (bArr[i4 - 1] & 255) + i2;
                    int i6 = this.endIndex + i5;
                    this.endIndex = i6;
                    if (i6 <= length) {
                        byte[] bArr2 = new byte[i5];
                        System.arraycopy(bArr, i3, bArr2, 0, i5);
                        this.mLlist.add(bArr2);
                        int i7 = this.endIndex;
                        this.startIndex = i7;
                        if (i7 != length) {
                            utenif(bArr);
                            return;
                        }
                        int size = this.mLlist.size();
                        this.mAlarmClockInfos = new ArrayList();
                        if (size > 0) {
                            int i8 = 0;
                            while (i8 < size) {
                                byte[] bArr3 = this.mLlist.get(i8);
                                long jDateToStamp = CalendarUtils.dateToStamp(utendo(bArr3));
                                int i9 = bArr3[c2] & 255;
                                int i10 = bArr3[8] & 255;
                                int i11 = bArr3[9] & 255;
                                int i12 = bArr3[10] & 255;
                                int i13 = bArr3[11] & 255;
                                int i14 = bArr3[12] & 255;
                                int i15 = bArr3[13] & 255;
                                int i16 = zIsHasFunction_8 ? bArr3[14] & 255 : 0;
                                int i17 = bArr3[i2 - 1] & 255;
                                byte[] bArr4 = new byte[i17];
                                System.arraycopy(bArr3, i2, bArr4, 0, i17);
                                this.mAlarmClockInfos.add(new LabelAlarmClockInfo(i9, i10, i11, i12, i14, i13, GBUtils.getInstance().unicode2String(bArr4), jDateToStamp, i15, i16));
                                i8++;
                                c2 = 7;
                            }
                        }
                        this.startIndex = 0;
                        this.endIndex = 0;
                        this.mLlist = new ArrayList();
                        UteListenerManager.getInstance().onQueryDeviceLabelAlarmSuccess(this.mAlarmClockInfos);
                        this.mAlarmClockInfos = new ArrayList();
                        return;
                    }
                    return;
                }
                UteListenerManager.getInstance().onQueryDeviceLabelAlarmSuccess(this.mAlarmClockInfos);
            }
            str = "LabelAlarmClockData totalLen =" + length;
        }
        LogUtils.i(str);
        UteListenerManager.getInstance().onQueryDeviceLabelAlarmSuccess(this.mAlarmClockInfos);
    }

    public final void utenif() {
        Message message = new Message();
        message.what = 0;
        message.arg1 = this.mContentStatus;
        this.mHandler.sendMessage(message);
    }

    public final String utendo(byte[] bArr) {
        int i2 = ((bArr[0] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[1] & 255);
        int i3 = bArr[2] & 255;
        int i4 = bArr[3] & 255;
        int i5 = bArr[4] & 255;
        int i6 = bArr[5] & 255;
        int i7 = bArr[6] & 255;
        String strValueOf = String.valueOf(i7);
        String strValueOf2 = String.valueOf(i6);
        String strValueOf3 = String.valueOf(i5);
        String strValueOf4 = String.valueOf(i4);
        String strValueOf5 = String.valueOf(i3);
        String strValueOf6 = String.valueOf(i2);
        if (i7 < 10) {
            strValueOf = "0" + i7;
        }
        if (i6 < 10) {
            strValueOf2 = "0" + i6;
        }
        if (i5 < 10) {
            strValueOf3 = "0" + i5;
        }
        if (i4 < 10) {
            strValueOf4 = "0" + i4;
        }
        if (i3 < 10) {
            strValueOf5 = "0" + i3;
        }
        return i2 == 0 ? "00000000000000" : strValueOf6 + strValueOf5 + strValueOf4 + strValueOf3 + strValueOf2 + strValueOf;
    }

    public final void utendo() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final byte[] utendo(byte[] bArr, int i2) {
        int length = bArr.length - i2;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i2, bArr2, 0, length);
        return bArr2;
    }

    public final void utendo(int i2) {
        if (i2 == 1) {
            WriteCommandToBLE.getInstance().sendLabelAlarmClockSection();
        } else {
            if (i2 != 2) {
                return;
            }
            WriteCommandToBLE.getInstance().sendDeviceWidgetSection();
        }
    }
}
