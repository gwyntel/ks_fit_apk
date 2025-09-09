package com.yc.utesdk.data;

import androidx.core.view.MotionEventCompat;
import com.yc.utesdk.bean.BrightScreenInfo;
import com.yc.utesdk.bean.WatchHeartRateReminderBean;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;

/* loaded from: classes4.dex */
public class DeviceParamProcessing {
    private static DeviceParamProcessing Instance;

    public static DeviceParamProcessing getInstance() {
        if (Instance == null) {
            Instance = new DeviceParamProcessing();
        }
        return Instance;
    }

    public void doGpsData69(byte[] bArr) {
        int i2 = ((bArr[1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (bArr[2] & 255);
        if (i2 == 43777) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().onQueryDeviceParam(true, 9, null);
        }
        switch (i2) {
            case 43521:
                int i3 = bArr[4] & 255;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 1, Integer.valueOf(i3));
                break;
            case 43522:
                int i4 = (bArr[4] & 255) == 1 ? 1 : 0;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 2, Integer.valueOf(i4));
                break;
            case 43523:
                int i5 = (bArr[4] & 255) == 1 ? 1 : 0;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 3, Integer.valueOf(i5));
                break;
            case 43524:
                int i6 = bArr[3] & 255;
                byte[] bArr2 = new byte[i6];
                System.arraycopy(bArr, 4, bArr2, 0, i6);
                String strUtf8ByteToString = GBUtils.getInstance().utf8ByteToString(bArr2);
                LogUtils.i("AA04 data =" + strUtf8ByteToString);
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 4, strUtf8ByteToString);
                break;
            case 43525:
                boolean z2 = (bArr[4] & 255) == 1;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 5, Boolean.valueOf(z2));
                break;
            case 43526:
                boolean z3 = (bArr[4] & 255) == 1;
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 6, Boolean.valueOf(z3));
                break;
            case 43527:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 7, new WatchHeartRateReminderBean((bArr[4] & 255) == 1, bArr[5] & 255, (bArr[6] & 255) == 1, bArr[7] & 255, (bArr[8] & 255) == 1, bArr[9] & 255));
                break;
            case 43528:
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                UteListenerManager.getInstance().onQueryDeviceParam(true, 8, new BrightScreenInfo(bArr[4] & 255, bArr[5] & 255, bArr[6] & 255, bArr[7] & 255, bArr[8] & 255));
                break;
        }
    }

    public void queryBrightScreenParam() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_BRIGHT_SCREEN_PARAM);
        WriteCommandToBLE.getInstance().writeChara(new byte[]{105, -86, 8});
    }

    public void queryBrightScreenTime() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(232);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 1});
    }

    public void queryHeartRateParam() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_HEART_RATE_PARAM);
        WriteCommandToBLE.getInstance().writeChara(new byte[]{105, -86, 7});
    }

    public void queryHeartRateSwitch() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(236);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 5});
    }

    public void queryPressureSwitch() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(237);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 6});
    }

    public void querySnCode() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(235);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 4});
    }

    public void queryTemperatureFormat() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(234);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 3});
    }

    public void queryTimeFormat() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(233);
        WriteCommandToBLE.getInstance().writeCharaBle5(new byte[]{105, -86, 2});
    }

    public void setBrightScreenParam(BrightScreenInfo brightScreenInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_BRIGHT_SCREEN_PARAM);
        int screenSwitch = brightScreenInfo.getScreenSwitch();
        int fromTimeHour = brightScreenInfo.getFromTimeHour();
        int fromTimeMinute = brightScreenInfo.getFromTimeMinute();
        int toTimeHour = brightScreenInfo.getToTimeHour();
        int toTimeMinute = brightScreenInfo.getToTimeMinute();
        SPUtil.getInstance().setBrightScreenSwitch(brightScreenInfo.getScreenSwitch());
        SPUtil.getInstance().setBrightScreenFromHour(brightScreenInfo.getFromTimeHour());
        SPUtil.getInstance().setBrightScreenFromMinute(brightScreenInfo.getFromTimeMinute());
        SPUtil.getInstance().setBrightScreenToHour(brightScreenInfo.getToTimeHour());
        SPUtil.getInstance().setBrightScreenToMinute(brightScreenInfo.getToTimeMinute());
        WriteCommandToBLE.getInstance().writeChara(new byte[]{105, -85, 1, 5, (byte) (screenSwitch & 255), (byte) (fromTimeHour & 255), (byte) (fromTimeMinute & 255), (byte) (toTimeHour & 255), (byte) (toTimeMinute & 255)});
    }
}
