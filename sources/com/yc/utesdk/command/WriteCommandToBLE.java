package com.yc.utesdk.command;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.business.devicecenter.config.ble.BreezeConstants;
import com.google.zxing.common.StringUtils;
import com.yc.utesdk.bean.AlarmClockInfo;
import com.yc.utesdk.bean.AppLocationDataInfo;
import com.yc.utesdk.bean.BloodSugarLevelInfo;
import com.yc.utesdk.bean.BloodSugarTirInfo;
import com.yc.utesdk.bean.BloodSugarValueInfo;
import com.yc.utesdk.bean.BodyFatPersonInfo;
import com.yc.utesdk.bean.ContactsInfo;
import com.yc.utesdk.bean.DeviceParametersInfo;
import com.yc.utesdk.bean.DeviceWidgetInfo;
import com.yc.utesdk.bean.DoNotDisturbInfo;
import com.yc.utesdk.bean.DrinkWaterRemindInfo;
import com.yc.utesdk.bean.EcgHeartRateInfo;
import com.yc.utesdk.bean.ImageWatchFaceConfigRk;
import com.yc.utesdk.bean.LabelAlarmClockInfo;
import com.yc.utesdk.bean.LoginElServerInfo;
import com.yc.utesdk.bean.MoodPressureFatigueInfo;
import com.yc.utesdk.bean.MultiSportHeartRateWarningInfo;
import com.yc.utesdk.bean.MusicPushInfo;
import com.yc.utesdk.bean.PowerSavingModeInfo;
import com.yc.utesdk.bean.QRCodeInfo;
import com.yc.utesdk.bean.ReplySmsInfo;
import com.yc.utesdk.bean.SedentaryRemindInfo;
import com.yc.utesdk.bean.SevenDayWeatherInfo;
import com.yc.utesdk.bean.SosCallInfo;
import com.yc.utesdk.bean.SportsModeControlInfo;
import com.yc.utesdk.bean.SportsModeInfo;
import com.yc.utesdk.bean.TimeZoneInfo;
import com.yc.utesdk.bean.UnitHourFormatInfo;
import com.yc.utesdk.bean.WashHandsRemindInfo;
import com.yc.utesdk.bean.WeatherInfo;
import com.yc.utesdk.ble.close.DeviceBusyLockUtils;
import com.yc.utesdk.ble.close.KeyType;
import com.yc.utesdk.ble.close.NotifyUtils;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.data.BloodPressureDataProcessing;
import com.yc.utesdk.data.BreathingRateDataProcessing;
import com.yc.utesdk.data.HeartRateDataProcessing;
import com.yc.utesdk.data.MultiSportsProcessing;
import com.yc.utesdk.data.OxygenDataProcessing;
import com.yc.utesdk.data.QRCodeProcessing;
import com.yc.utesdk.data.StandingTimeProcessing;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.EcgUtil;
import com.yc.utesdk.utils.open.ByteDataUtil;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.RoundingUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.utils.open.WeatherUtils;
import com.yc.utesdk.watchface.bean.WatchFaceSerialNumberInfo;
import com.yc.utesdk.watchface.close.OnlineDialTimeOut;
import com.yc.utesdk.watchface.close.Rgb;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.crypto.NoSuchPaddingException;
import org.joda.time.DateTimeConstants;

/* loaded from: classes4.dex */
public class WriteCommandToBLE {
    private static WriteCommandToBLE Instance;
    public static int musicControlCRC;
    private byte CODataCRC;

    /* renamed from: b, reason: collision with root package name */
    byte[] f24892b;
    private List<DeviceWidgetInfo> deviceWidgetList;
    private byte[] mBloodSugarLevelData;
    private byte[] mBloodSugarValueData;
    public Handler mHandler;
    private byte[] mLoginElServerInfoData;
    private byte sosCallCRC;
    private List<Integer> supList;
    private String syncSportTime;
    private int totalCount;
    private byte universalInterfaceCRC;
    private byte weatherNext24HoursCRC;
    private int timeZoneCRC = 0;
    private int NOsectionTimeZone = 0;
    private int timeZoneListSize = 0;
    private List<TimeZoneInfo> timeZoneInfos = new ArrayList();
    public final int DELAY_SEND_ONLINE_DIAL_MSG = 2;
    public final int CSBP_SENDCALIBRATION_CO_SECTION_MSG = 3;
    public final int WEATHER_NEXT_24HOURE_SECTION_MSG = 4;
    private int lastWatchFaceProgress = -1;
    public int NOsectionOnline = 0;
    public int maxCommunicationLength = 0;
    private byte[] textDataOnline = null;
    public boolean isSendFinisCommand = false;
    private boolean isHadSendWatchFinis = false;
    private int intervalEnd = 500;
    private int lastErase = 0;
    private int currErase = 0;
    private boolean isReduceDialSpeed = false;
    public int NOsection = 0;
    private byte[] textData = null;
    public boolean isSendFD = false;
    public int msgPushMaxLength = 160;

    /* renamed from: a, reason: collision with root package name */
    SevenDayWeatherInfo f24891a = null;
    public int NOsectionForWeather = 0;
    private byte[] weatherNext24HoursData = null;
    public boolean isSendweatherNext24HoursFD = false;
    public int NOsectionUniversalInterface = 0;
    private byte[] universalInterfaceData = null;
    public boolean isSendUniversalFD = false;
    public boolean isSending = false;
    private int universalInterfaceType = 0;
    private int mSportType = 1;
    private int mSportInterval = 1;
    private boolean swimDataSyncFinish = false;
    private boolean isSendSportListFinish = false;
    private int NOsectionSport = 0;
    private int sportListCRC = 0;
    private List<SportsModeInfo> displaySportsList = new ArrayList();
    private byte[] mEcgParsingSuccessData = null;
    private int mEcgAppCrc = 0;
    private boolean isEcgParsingSuccessFDFD = false;
    public int NOsectionSports = 0;
    public int NOsectionCSBpSendCalibrationCO = 0;
    private byte[] cSBpSendCalibrationCOData = null;
    public boolean isSendcSBpCODataFD = false;
    public int NOsectionContact = 0;
    private boolean isSendContactFinish = false;
    private List<ContactsInfo> contactListsGlobal = new ArrayList();
    private boolean isSendSosCallFinish = false;
    private List<SosCallInfo> sosCallInfos = new ArrayList();
    public int nOsectionSosCall = 0;
    private int mElbsCalibrationModelCrc = 0;
    private boolean isElbsCalibrationModelFD = false;
    private int mLoginElServerInfoCrc = 0;
    private boolean isLoginElServerInfoFD = false;
    private int NOsectionCustomizeSMS = 0;
    private int customizeSMSSize = 0;
    private List<ReplySmsInfo> customizeSMSInfos = new ArrayList();
    private int NOsectionAlarmClock = 0;
    private byte[] alarmClockData = null;
    public boolean isAlarmClockSendFD = false;
    private int alarmClockCRC = 0;
    private boolean isSendCustomzieWidgetFinish = false;
    private int NOsectionCustomzieWidget = 0;
    private int customzieWidgettCRC = 0;
    private int mBloodSugarValueCrc = 0;
    private boolean isBloodSugarValueFD = false;
    private int mBloodSugarValueInterval = 0;
    private int mBloodSugarLevelCrc = 0;
    private boolean isBloodSugarLevelFD = false;
    private int mBloodSugarLevelInterval = 0;
    private OnlineDialTimeOut mOnlineDialTimeOut = OnlineDialTimeOut.getInstance();

    public class utendo extends Handler {
        public utendo(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i2;
            int i3 = message.what;
            if (i3 != 2) {
                if (i3 == 3) {
                    WriteCommandToBLE writeCommandToBLE = WriteCommandToBLE.this;
                    writeCommandToBLE.cSBpSendCalibrationCOOnSegments(writeCommandToBLE.NOsectionCSBpSendCalibrationCO);
                    return;
                } else {
                    if (i3 != 4) {
                        return;
                    }
                    WriteCommandToBLE writeCommandToBLE2 = WriteCommandToBLE.this;
                    writeCommandToBLE2.setDeviceWeatherSegments(writeCommandToBLE2.NOsectionForWeather);
                    return;
                }
            }
            if (!UteBleClient.getUteBleClient().isConnected()) {
                WriteCommandToBLE.this.stopOnlineDialData();
                UteListenerManager.getInstance().onWatchFaceOnlineStatus(6);
                return;
            }
            if (DeviceBusyLockUtils.getInstance().getDeviceBusy()) {
                Message message2 = new Message();
                message2.what = 2;
                WriteCommandToBLE.this.mHandler.sendMessageDelayed(message2, 2L);
                return;
            }
            WriteCommandToBLE writeCommandToBLE3 = WriteCommandToBLE.this;
            int i4 = writeCommandToBLE3.NOsectionOnline + 1;
            writeCommandToBLE3.NOsectionOnline = i4;
            writeCommandToBLE3.sendSectionOnlineDialData(i4);
            if (WriteCommandToBLE.this.totalCount > 0) {
                WriteCommandToBLE writeCommandToBLE4 = WriteCommandToBLE.this;
                i2 = (writeCommandToBLE4.NOsectionOnline * 100) / writeCommandToBLE4.totalCount;
            } else {
                i2 = 0;
            }
            if (WriteCommandToBLE.this.lastWatchFaceProgress != i2) {
                LogUtils.i("progress = " + i2 + ",current =" + WriteCommandToBLE.this.NOsectionOnline + ",totalCount =" + WriteCommandToBLE.this.totalCount);
                UteListenerManager.getInstance().onWatchFaceOnlineProgress(i2);
                WriteCommandToBLE.this.lastWatchFaceProgress = i2;
            }
        }
    }

    public WriteCommandToBLE() {
        utenthis();
    }

    public static WriteCommandToBLE getInstance() {
        if (Instance == null) {
            Instance = new WriteCommandToBLE();
        }
        return Instance;
    }

    public void appInterruptContinuousPpgSampling() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(218);
        writeCharaBle5(utenfor());
    }

    public void appRemind(int i2, String str) throws UnsupportedEncodingException {
        utendo(i2, str);
    }

    public void appStartContinuousPpgSampling() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(216);
        writeCharaBle5(utenint());
    }

    public void autoTestCommonHeartRate(boolean z2) {
        byte[] bArr = new byte[2];
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(246);
            bArr[0] = -42;
            bArr[1] = 2;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(247);
            bArr[0] = -42;
            bArr[1] = 1;
        }
        writeChara(bArr);
    }

    public void autoTestCommonHeartRateInterval(boolean z2, int i2) {
        byte[] bArr = new byte[3];
        bArr[0] = -42;
        bArr[1] = 16;
        if (z2) {
            bArr[2] = (byte) i2;
        } else {
            bArr[2] = 0;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_COMMON_RATE_TEST_INTERVAL);
        writeChara(bArr);
    }

    public void autoTestHeartRate(boolean z2) {
        byte[] bArr = new byte[2];
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(20);
            bArr[0] = -9;
            bArr[1] = 1;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(21);
            bArr[0] = -9;
            bArr[1] = 2;
        }
        writeChara(bArr);
    }

    public void autoTestHeartRateInterval(boolean z2, int i2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i3;
        byte[] bArr = new byte[4];
        bArr[0] = -9;
        bArr[1] = 9;
        if (z2) {
            bArr[2] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 177;
        } else {
            bArr[2] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 178;
        }
        commandTimeOutUtils.setCommandTimeOut(i3);
        bArr[3] = (byte) i2;
        writeChara(bArr);
    }

    public void breathingRateAutomaticTest(boolean z2, int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(48);
        byte[] bArr = new byte[5];
        bArr[0] = 59;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((65280 & i2) >> 8);
        bArr[4] = (byte) (i2 & 255);
        writeChara(bArr);
    }

    public void breathingRateTimePeriod(boolean z2, int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(49);
        byte[] bArr = new byte[7];
        bArr[0] = 59;
        bArr[1] = 4;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((i2 / 60) & 255);
        bArr[4] = (byte) ((i2 % 60) & 255);
        bArr[5] = (byte) ((i3 / 60) & 255);
        bArr[6] = (byte) ((i3 % 60) & 255);
        writeChara(bArr);
    }

    public void cSBpAPPResponsePMCrcOKCommand(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = 62;
        bArr[1] = 0;
        if (z2) {
            bArr[2] = -3;
        } else {
            bArr[2] = -1;
        }
        writeCharaBle5(bArr);
    }

    public void cSBpSendCalibrationCOOnSegments(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(130);
        if (i2 == 0) {
            this.CODataCRC = (byte) 0;
            this.isSendcSBpCODataFD = false;
        }
        byte[] bArr = this.cSBpSendCalibrationCOData;
        int i3 = this.maxCommunicationLength;
        int i4 = i3 - 4;
        int length = bArr.length;
        int i5 = length % i4;
        if (i2 < length / i4) {
            byte[] bArr2 = new byte[i3];
            bArr2[0] = 62;
            bArr2[1] = 1;
            bArr2[2] = -6;
            bArr2[3] = (byte) (i2 & 255);
            int i6 = i2 * i4;
            for (int i7 = i6; i7 < i4 + i6; i7++) {
                byte b2 = bArr[i7];
                bArr2[(i7 - i6) + 4] = b2;
                this.CODataCRC = (byte) (this.CODataCRC ^ b2);
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isSendcSBpCODataFD) {
            utendo(this.CODataCRC);
            this.isSendcSBpCODataFD = false;
        } else if (i5 != 0) {
            byte[] bArr3 = new byte[i5 + 4];
            bArr3[0] = 62;
            bArr3[1] = 1;
            bArr3[2] = -6;
            bArr3[3] = (byte) (i2 & 255);
            int i8 = i4 * i2;
            for (int i9 = i8; i9 < length; i9++) {
                byte b3 = bArr[i9];
                bArr3[(i9 - i8) + 4] = b3;
                this.CODataCRC = (byte) (this.CODataCRC ^ b3);
            }
            writeCharaBle5(bArr3);
        }
        this.isSendcSBpCODataFD = true;
    }

    public void calibrationTemperature() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(38);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_RES, 7});
    }

    public void callRemind(String str) throws UnsupportedEncodingException {
        utendo(0, str);
    }

    public void callerInterfaceDisappears() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(97);
        writeChara(new byte[]{-63, 4});
    }

    public void cancelUpgradeJx() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(276);
        writeChara(new byte[]{1, -56, -85, 1, 1, 0});
    }

    public void clearGlucoseMetabolismSampledData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(209);
        writeCharaBle5(new byte[]{102, 4});
    }

    public void commonInterfaceBleToSdk(byte[] bArr) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(83);
        utendo(bArr, 1);
    }

    public void commonInterfaceSdkToBle(byte[] bArr) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(82);
        utendo(bArr, 0);
    }

    public void continueSports(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(56);
        MultiSportsProcessing.getInstance().setNotifyStatus(false);
        utendo(51, sportsModeControlInfo);
    }

    public void controlBandDisplaySportsList(List<Integer> list) {
        this.supList = list;
        controlBandDisplaySportsListSection(0);
    }

    public void controlBandDisplaySportsListSection(int i2) {
        int size = this.supList.size();
        int bandDisplayMinSports = SPUtil.getInstance().getBandDisplayMinSports();
        int bandDisplayMaxSports = SPUtil.getInstance().getBandDisplayMaxSports();
        if (size < bandDisplayMinSports || size > bandDisplayMaxSports) {
            LogUtils.i("sendTextKey", "运动列表个数不在手环支持范围内");
            return;
        }
        int i3 = size / 15;
        int i4 = size % 15;
        int i5 = 0;
        if (i3 == 0) {
            byte[] bArr = new byte[i4 + 5];
            bArr[0] = -3;
            bArr[1] = 85;
            bArr[2] = 0;
            bArr[4] = (byte) (size & 255);
            bArr[3] = (byte) ((size & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            while (i5 < i4) {
                int iIntValue = this.supList.get(i5).intValue();
                LogUtils.i("sendTextKey", "num=" + iIntValue);
                bArr[i5 + 5] = (byte) (iIntValue & 255);
                i5++;
            }
            writeChara(bArr);
            return;
        }
        LogUtils.i("sendTextKey", "i=" + i2);
        if (i2 >= i3) {
            byte[] bArr2 = new byte[i4 + 3];
            bArr2[0] = -3;
            bArr2[1] = 85;
            bArr2[2] = (byte) (i2 & 255);
            while (i5 < i4) {
                int iIntValue2 = this.supList.get((15 * i2) + i5).intValue();
                LogUtils.i("sendTextKey", "num=" + iIntValue2);
                bArr2[i5 + 3] = (byte) (iIntValue2 & 255);
                i5++;
            }
            writeChara(bArr2);
            return;
        }
        byte[] bArr3 = new byte[20];
        bArr3[0] = -3;
        bArr3[1] = 85;
        bArr3[2] = (byte) (i2 & 255);
        bArr3[4] = (byte) (size & 255);
        bArr3[3] = (byte) ((size & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        int i6 = i2 * 15;
        for (int i7 = i6; i7 < 15 + i6; i7++) {
            int iIntValue3 = this.supList.get(i7).intValue();
            LogUtils.i("sendTextKey", "num=" + iIntValue3);
            bArr3[(i7 - i6) + 5] = (byte) (iIntValue3 & 255);
        }
        writeChara(bArr3);
    }

    public void csbpQueryCalibrationStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(125);
        writeCharaBle5(new byte[]{62, -86});
    }

    public void csbpQueryChip() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(131);
        writeCharaBle5(new byte[]{62, 6, -86});
    }

    public void csbpQueryDeviceActivateStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(132);
        writeCharaBle5(new byte[]{62, 5, -86});
    }

    public void csbpQueryDeviceModuleId() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(133);
        writeCharaBle5(new byte[]{62, 5, 1});
    }

    public void csbpResetCalibrateInfo() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(129);
        writeCharaBle5(new byte[]{62, 4, 0});
    }

    public void csbpSendActivateCodeToDevice(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(134);
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(str);
        writeCharaBle5(ByteDataUtil.getInstance().copyTwoArrays(new byte[]{62, 5, 2, (byte) bArrHexStringToBytes.length}, bArrHexStringToBytes));
    }

    public void csbpSendCoParam(String str) {
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(str);
        if (bArrHexStringToBytes == null) {
            LogUtils.w("cSBpSendCalibrationCO  data =" + bArrHexStringToBytes);
            return;
        }
        this.cSBpSendCalibrationCOData = bArrHexStringToBytes;
        this.NOsectionCSBpSendCalibrationCO = 0;
        this.CODataCRC = (byte) 0;
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        cSBpSendCalibrationCOOnSegments(this.NOsectionCSBpSendCalibrationCO);
    }

    public void csbpSetMedicationHightBp(boolean z2, boolean z3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(128);
        byte[] bArr = new byte[4];
        bArr[0] = 62;
        bArr[1] = 3;
        if (z3) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        if (z2) {
            bArr[3] = 1;
        } else {
            bArr[3] = 0;
        }
        writeCharaBle5(bArr);
    }

    public void csbpStartCalibrate() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(126);
        writeCharaBle5(new byte[]{62, 0});
    }

    public void csbpStopCalibrate() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(127);
        writeCharaBle5(new byte[]{62, 2, -3});
    }

    public void csbpSyncHeartRateAndOxygen() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(160);
        writeCharaBle5(new byte[]{62, 9, -6});
    }

    public void cyweeSwimDataSyncFinish(boolean z2) throws NumberFormatException {
        this.swimDataSyncFinish = z2;
        syncMultipleSportsData(this.syncSportTime);
    }

    public void defaultRateCalibration() {
        writeChara(new byte[]{-5, -3});
    }

    public void defaultTurnWristCalibration() {
        writeChara(new byte[]{-4, -3});
    }

    public void deleteDeviceAllContacts() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(140);
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -70});
    }

    public void deleteDeviceTimeZone() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(75);
        writeCharaBle5(new byte[]{67, -5});
    }

    public void deleteDeviceWatchFaceOnline(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(159);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 8, (byte) i2});
    }

    public void deleteMoodPressureData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(108);
        writeCharaBle5(utenbyte());
    }

    public void deleteTemperatureHistoricalData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(37);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_RES, 5});
    }

    public void deviceFactoryDataReset() {
        writeChara(new byte[]{-83});
    }

    public void deviceMusicPlayStatus(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(60);
        byte[] bArr = new byte[3];
        bArr[0] = -47;
        bArr[1] = 12;
        if (i2 == 1) {
            bArr[2] = 1;
        } else {
            bArr[2] = 2;
        }
        writeChara(bArr);
    }

    public void deviceMusicSwitchStatus(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(186);
        byte[] bArr = new byte[3];
        bArr[0] = 58;
        bArr[1] = 7;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        writeChara(bArr);
    }

    public void deviceMusicVolumeStatus(int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(61);
        byte[] bArr = new byte[3];
        bArr[0] = -47;
        if (i2 == 0) {
            bArr[1] = 13;
        } else {
            bArr[1] = 14;
        }
        bArr[2] = (byte) (i3 & 255);
        writeChara(bArr);
    }

    public void deviceOnScreenDuration(boolean z2, int i2) {
        byte[] bArr;
        CommandTimeOutUtils commandTimeOutUtils;
        int i3;
        if (z2) {
            bArr = new byte[]{97, 1, (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i2 & 255)};
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 179;
        } else {
            bArr = new byte[]{97, 0};
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 180;
        }
        commandTimeOutUtils.setCommandTimeOut(i3);
        writeChara(bArr);
    }

    public void devicePowerOff() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(172);
        writeChara(new byte[]{-24, 17});
    }

    public void devicePowerSavingMode(PowerSavingModeInfo powerSavingModeInfo) {
        byte[] bArr;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(181);
        if (powerSavingModeInfo.getPowerSwitch()) {
            bArr = new byte[7];
            bArr[0] = -6;
            bArr[1] = 1;
            if (powerSavingModeInfo.getTimingSwitch()) {
                bArr[2] = 1;
                bArr[3] = (byte) (powerSavingModeInfo.getFromTimeHour() & 255);
                bArr[4] = (byte) (powerSavingModeInfo.getFromTimeMinute() & 255);
                bArr[5] = (byte) (powerSavingModeInfo.getToTimeHour() & 255);
                bArr[6] = (byte) (powerSavingModeInfo.getToTimeMinute() & 255);
            } else {
                bArr[2] = 0;
                bArr[3] = 0;
                bArr[4] = 0;
                bArr[5] = 0;
                bArr[6] = 0;
            }
        } else {
            bArr = new byte[]{-6, 0};
        }
        writeChara(bArr);
    }

    public void deviceRestart() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(173);
        writeChara(new byte[]{-24, 18});
    }

    public void doNotDisturb(DoNotDisturbInfo doNotDisturbInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(69);
        int endCallSwitch = doNotDisturbInfo.getEndCallSwitch();
        int messageSwitch = doNotDisturbInfo.getMessageSwitch();
        int motorSwitch = doNotDisturbInfo.getMotorSwitch();
        int disturbTimeSwitch = doNotDisturbInfo.getDisturbTimeSwitch();
        int fromTimeHour = doNotDisturbInfo.getFromTimeHour();
        int fromTimeMinute = doNotDisturbInfo.getFromTimeMinute();
        int toTimeHour = doNotDisturbInfo.getToTimeHour();
        int toTimeMinute = doNotDisturbInfo.getToTimeMinute();
        SPUtil.getInstance().setEndCallSwitch(endCallSwitch);
        SPUtil.getInstance().setDoNotDisturbMotorSwitch(motorSwitch);
        SPUtil.getInstance().setDoNotDisturbMessageSwitch(messageSwitch);
        SPUtil.getInstance().setDoNotDisturbTimeSwitch(disturbTimeSwitch);
        SPUtil.getInstance().setDoNotDisturbFromHour(fromTimeHour);
        SPUtil.getInstance().setDoNotDisturbFromMinute(fromTimeMinute);
        SPUtil.getInstance().setDoNotDisturbToHour(toTimeHour);
        SPUtil.getInstance().setDoNotDisturbToMinute(toTimeMinute);
        int[] iArr = {endCallSwitch, messageSwitch, motorSwitch};
        int[] iArr2 = new int[4];
        iArr2[0] = 0;
        iArr2[1] = 0;
        iArr2[2] = 0;
        iArr2[3] = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            iArr2[i2] = iArr[i2] == 1 ? 1 : 0;
        }
        int i3 = (iArr2[0] * 8) + (iArr2[1] * 4) + (iArr2[2] * 2) + iArr2[3];
        byte[] bArr = new byte[7];
        bArr[0] = -41;
        bArr[1] = (byte) i3;
        bArr[2] = (byte) (fromTimeHour & 255);
        bArr[3] = (byte) (fromTimeMinute & 255);
        bArr[4] = (byte) (toTimeHour & 255);
        bArr[5] = (byte) (toTimeMinute & 255);
        if (disturbTimeSwitch == 1) {
            bArr[6] = 1;
        } else {
            bArr[6] = 0;
        }
        writeChara(bArr);
    }

    public void drinkWaterRemind(DrinkWaterRemindInfo drinkWaterRemindInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(67);
        int remindSwitch = drinkWaterRemindInfo.getRemindSwitch();
        int fromTimeHour = drinkWaterRemindInfo.getFromTimeHour();
        int fromTimeMinute = drinkWaterRemindInfo.getFromTimeMinute();
        int toTimeHour = drinkWaterRemindInfo.getToTimeHour();
        int toTimeMinute = drinkWaterRemindInfo.getToTimeMinute();
        int remindInterval = drinkWaterRemindInfo.getRemindInterval();
        int lunchBreakNoRemind = drinkWaterRemindInfo.getLunchBreakNoRemind();
        SPUtil.getInstance().setDrinkWaterRemindSwitch(remindSwitch);
        SPUtil.getInstance().setDrinkWaterRemindFromTimeHour(fromTimeHour);
        SPUtil.getInstance().setDrinkWaterRemindFromTimeMinute(fromTimeMinute);
        SPUtil.getInstance().setDrinkWaterRemindToTimeHour(toTimeHour);
        SPUtil.getInstance().setDrinkWaterRemindToTimeMinute(toTimeMinute);
        SPUtil.getInstance().setDrinkWaterRemindInterval(remindInterval);
        SPUtil.getInstance().setDrinkWaterLunchBreakNoRemind(lunchBreakNoRemind);
        byte[] bArr = new byte[12];
        if (DeviceMode.isHasFunction_6(65536)) {
            bArr = new byte[16];
            int noRemindFromTimeHour = drinkWaterRemindInfo.getNoRemindFromTimeHour();
            int noRemindFromTimeMinute = drinkWaterRemindInfo.getNoRemindFromTimeMinute();
            int noRemindToTimeHour = drinkWaterRemindInfo.getNoRemindToTimeHour();
            int noRemindToTimeMinute = drinkWaterRemindInfo.getNoRemindToTimeMinute();
            SPUtil.getInstance().setDrinkWaterNoRemindFromTimeHour(noRemindFromTimeHour);
            SPUtil.getInstance().setDrinkWaterNoRemindFromTimeMinute(noRemindFromTimeMinute);
            SPUtil.getInstance().setDrinkWaterNoRemindToTimeHour(noRemindToTimeHour);
            SPUtil.getInstance().setDrinkWaterNoRemindToTimeMinute(noRemindToTimeMinute);
            bArr[12] = (byte) (noRemindFromTimeHour & 255);
            bArr[13] = (byte) (noRemindFromTimeMinute & 255);
            bArr[14] = (byte) (noRemindToTimeHour & 255);
            bArr[15] = (byte) (noRemindToTimeMinute & 255);
        }
        bArr[0] = -44;
        if (remindSwitch == 1) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        bArr[2] = (byte) (remindInterval & 255);
        bArr[3] = 2;
        bArr[4] = 3;
        bArr[5] = 1;
        bArr[6] = 0;
        bArr[7] = (byte) (fromTimeHour & 255);
        bArr[8] = (byte) (fromTimeMinute & 255);
        bArr[9] = (byte) (toTimeHour & 255);
        bArr[10] = (byte) (toTimeMinute & 255);
        if (lunchBreakNoRemind == 1) {
            bArr[11] = 1;
        } else {
            bArr[11] = 0;
        }
        writeChara(bArr);
    }

    public void ecgParsingFail() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(230);
        writeCharaBle5(new byte[]{40, 18, -1});
    }

    public void ecgParsingSuccess(EcgHeartRateInfo ecgHeartRateInfo) {
        if (ecgHeartRateInfo == null || ecgHeartRateInfo.getHeartRateDataList() == null || ecgHeartRateInfo.getHeartRateDataList().size() == 0) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(231);
        int verHeartRate = ecgHeartRateInfo.getVerHeartRate();
        List<EcgHeartRateInfo.HeartRateData> heartRateDataList = ecgHeartRateInfo.getHeartRateDataList();
        int iMin = Math.min(heartRateDataList.size(), 5);
        this.mEcgAppCrc = 0;
        this.isEcgParsingSuccessFDFD = false;
        byte[] bArr = {40, 18, 0, (byte) (verHeartRate & 255), (byte) (iMin & 255)};
        for (int i2 = 2; i2 < 5; i2++) {
            this.mEcgAppCrc ^= bArr[i2];
        }
        writeCharaBle5(bArr);
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < iMin; i3++) {
            int heartRateCode = heartRateDataList.get(i3).getHeartRateCode();
            String strString2unicode = GBUtils.getInstance().string2unicode(heartRateDataList.get(i3).getHeartRateText());
            if (strString2unicode.length() > 80) {
                strString2unicode = strString2unicode.substring(0, 80);
            }
            sb.append(String.format("%02X", Integer.valueOf(heartRateCode)));
            sb.append(String.format("%02X", Integer.valueOf(strString2unicode.length() / 2)));
            sb.append(strString2unicode);
        }
        LogUtils.i("ecgParsingSuccess = " + ((Object) sb));
        this.mEcgParsingSuccessData = GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public void ecgParsingSuccessSegments(int i2) {
        byte[] bArr = this.mEcgParsingSuccessData;
        if (bArr == null) {
            return;
        }
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 3;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 2;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 40;
            bArr2[1] = 18;
            bArr2[2] = (byte) (i2 + 1);
            System.arraycopy(bArr, i2 * i3, bArr2, 3, i3);
            while (i6 < maxCommunicationLength) {
                this.mEcgAppCrc ^= bArr2[i6];
                i6++;
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isEcgParsingSuccessFDFD) {
            utendo(this.mEcgAppCrc);
            this.isEcgParsingSuccessFDFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 3;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = 40;
            bArr3[1] = 18;
            bArr3[2] = (byte) (i2 + 1);
            System.arraycopy(bArr, i3 * i2, bArr3, 3, i5);
            while (i6 < i7) {
                this.mEcgAppCrc ^= bArr3[i6];
                i6++;
            }
            writeCharaBle5(bArr3);
        }
        this.isEcgParsingSuccessFDFD = true;
    }

    public void femaleMenstrualCycle(boolean z2, String str, int i2, int i3) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(71);
        if (str == null || str.length() != 8) {
            return;
        }
        int i4 = Integer.parseInt(str.substring(0, 4));
        int i5 = Integer.parseInt(str.substring(4, 6));
        int i6 = Integer.parseInt(str.substring(6, 8));
        byte[] bArr = new byte[8];
        bArr[0] = 41;
        if (z2) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        bArr[3] = (byte) (i4 & 255);
        bArr[2] = (byte) ((65280 & i4) >> 8);
        bArr[4] = (byte) (i5 & 255);
        bArr[5] = (byte) (i6 & 255);
        bArr[6] = (byte) (i2 & 255);
        bArr[7] = (byte) (i3 & 255);
        writeChara(bArr);
    }

    public void femaleMenstrualFunctionSwitch(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = 99;
        bArr[1] = 1;
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(188);
            bArr[2] = 1;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(189);
            bArr[2] = 0;
        }
        writeChara(bArr);
    }

    public int getContactsInfoListSize() {
        return this.contactListsGlobal.size();
    }

    public int getElbsCalibrationModelCrc() {
        return this.mElbsCalibrationModelCrc & 255;
    }

    public void getImageWatchFaceParamsRk() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_IMAGE_WATCH_FACE_PARAMS);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 10});
    }

    public boolean getIsSending() {
        return this.isSending;
    }

    public int getLoginElServerInfoFD() {
        return this.mLoginElServerInfoCrc & 255;
    }

    public int getSportListCRC() {
        return this.sportListCRC;
    }

    public boolean getUniversalInterfaceCRCResult(byte b2) {
        return b2 == this.universalInterfaceCRC;
    }

    public void glucoseMetabolismAlgorithm(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(205);
        writeCharaBle5(new byte[]{102, 1, z2 ? (byte) 1 : (byte) 0});
    }

    public void heartRateStorageInterval(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(193);
        writeChara(new byte[]{-9, 10, (byte) (i2 & 255)});
    }

    public void ignoreAccountIdBind() {
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, 1});
    }

    public boolean isReduceDialSpeed() {
        return this.isReduceDialSpeed;
    }

    public void makeDeviceVibration(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(8);
        writeChara(new byte[]{-85, 0, 0, 0, 1, (byte) i2, 7, 1});
    }

    public void moodPressureFatigueAutoTest(boolean z2, int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(102);
        writeChara(utendo(z2, i2));
    }

    public void moodPressureFatigueTimePeriod(boolean z2, int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(103);
        writeChara(utendo(z2, i2, i3));
    }

    public void notifyDeviceContinuousPpgSampling() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(217);
        writeCharaBle5(utenlong());
    }

    public void notifyDeviceReplySmsResult(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[2];
        bArr[0] = 82;
        if (z2) {
            bArr[1] = -2;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 114;
        } else {
            bArr[1] = -1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 115;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void openBluetoothDisconnectReminder(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[2];
        bArr[0] = 90;
        if (z2) {
            bArr[1] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 150;
        } else {
            bArr[1] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 151;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void openDeviceBt3(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[3];
        bArr[0] = 56;
        bArr[1] = 2;
        if (z2) {
            bArr[2] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 88;
        } else {
            bArr[2] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 89;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArr);
    }

    public void openDeviceCameraMode(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[2];
        bArr[0] = -60;
        if (z2) {
            bArr[1] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 76;
        } else {
            bArr[1] = 3;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 77;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void openDeviceFindPhone(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(63);
        byte[] bArr = new byte[3];
        bArr[0] = -47;
        bArr[1] = 10;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        writeChara(bArr);
    }

    public void openElbpPpgMiddleData(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArrUtendo = utendo(z2);
        if (z2) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 141;
        } else {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 142;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArrUtendo);
    }

    public void openQuickReplySms(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[2];
        bArr[0] = 82;
        if (z2) {
            bArr[1] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 111;
        } else {
            bArr[1] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 112;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void openSosContactsSwitch(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[3];
        bArr[0] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
        bArr[1] = -81;
        if (z2) {
            bArr[2] = 1;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 147;
        } else {
            bArr[2] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 148;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArr);
    }

    public void oxygenAutomaticTest(boolean z2, int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(30);
        SPUtil.getInstance().setOxygenAutomaticTestSwitch(z2);
        SPUtil.getInstance().setOxygenAutomaticTestInterval(i2);
        byte[] bArr = new byte[5];
        bArr[0] = Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((65280 & i2) >> 8);
        bArr[4] = (byte) (i2 & 255);
        writeChara(bArr);
    }

    public void oxygenTimePeriod(boolean z2, int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(31);
        SPUtil.getInstance().setOxygenTimePeriodSwitch(z2);
        SPUtil.getInstance().setOxygenAutomaticStartTime(i2);
        SPUtil.getInstance().setOxygenAutomaticEndTime(i3);
        byte[] bArr = new byte[7];
        bArr[0] = Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE;
        bArr[1] = 4;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((i2 / 60) & 255);
        bArr[4] = (byte) ((i2 % 60) & 255);
        bArr[5] = (byte) ((i3 / 60) & 255);
        bArr[6] = (byte) ((i3 % 60) & 255);
        writeChara(bArr);
    }

    public void pauseSports(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(55);
        MultiSportsProcessing.getInstance().setNotifyStatus(false);
        utendo(34, sportsModeControlInfo);
    }

    public void prepareSyncWatchFaceData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(86);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 2});
    }

    public void query4gModuleImei() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(214);
        writeCharaBle5(new byte[]{103, 0});
    }

    public void queryAllWatchFaceInfo() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(228);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 9});
    }

    public void queryBandCurrentDisplaySports() {
        writeChara(new byte[]{-3, 119, -86});
    }

    public void queryBandSupSports() {
        writeChara(new byte[]{-3, 102, -86});
    }

    public void queryBluetoothDisconnectReminder() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(149);
        writeChara(new byte[]{90, -86});
    }

    public void queryBodyFatCurrentTestStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(42);
        writeChara(new byte[]{-23, -86});
    }

    public void queryBreathingRateCurrentTestStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(50);
        writeChara(new byte[]{59, -86});
    }

    public void queryCurrentSports() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(52);
        writeChara(new byte[]{-3, -86});
    }

    public void queryCustomTestStatusFormBle() {
        writeChara(new byte[]{74, 76, 89, -86});
    }

    public void queryCustomizeSMS() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(245);
        writeCharaBle5(new byte[]{70, -4});
    }

    public void queryDeviceBattery() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(3);
        writeChara(new byte[]{-94});
    }

    public void queryDeviceBt3State() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(87);
        byte[] bArr = {56, 1, 2, 0, 0, 0, 0, 0};
        byte[] randomArrayBt3 = new byte[4];
        if (SPUtil.getInstance().getNeedToRegenerateRandom63E()) {
            new SecureRandom().nextBytes(randomArrayBt3);
            SPUtil.getInstance().setRandomArrayBt3(randomArrayBt3);
            SPUtil.getInstance().setNeedToRegenerateRandom63E(false);
        } else {
            randomArrayBt3 = SPUtil.getInstance().getRandomArrayBt3(randomArrayBt3);
        }
        System.arraycopy(randomArrayBt3, 0, bArr, 4, randomArrayBt3.length);
        writeCharaBle5(bArr);
    }

    public void queryDeviceCurrentLanguage() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(78);
        writeChara(new byte[]{-81, -86});
    }

    public void queryDeviceDspVersion() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(9);
        writeChara(new byte[]{-95, 1});
    }

    public void queryDeviceLabelAlarmClock() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(117);
        writeCharaBle5(new byte[]{81, -86});
    }

    public void queryDeviceRemindDisplay() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(65);
        writeChara(new byte[]{-37, -86});
    }

    public void queryDeviceShortcutSwitch() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(80);
        writeChara(new byte[]{-66, 1});
    }

    public void queryDeviceShortcutSwitchStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(81);
        writeChara(new byte[]{-66, 2});
    }

    public void queryDeviceUiVersion() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(2);
        writeChara(new byte[]{-51, 1});
    }

    public void queryDeviceWidget() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(123);
        writeCharaBle5(new byte[]{73, -86});
    }

    public void queryDoNotDisturb() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_DO_NOT_DISTURB);
        writeChara(new byte[]{-41, -6});
    }

    public void queryDrinkWaterRemind() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_DRINK_WATER_REMIND);
        writeChara(new byte[]{-44, -6});
    }

    public void queryEcgSamplingFrequency() {
        writeCharaBle5(new byte[]{40, 15});
    }

    public void queryElComplexTestStatus() {
        byte[] bArrUtenvoid = utenvoid();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(169);
        writeCharaBle5(bArrUtenvoid);
    }

    public void queryElHRVTestStatus() {
        byte[] bArrUtenbreak = utenbreak();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(166);
        writeCharaBle5(bArrUtenbreak);
    }

    public void queryElbpAlgorithmVersion() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(185);
        writeCharaBle5(utencatch());
    }

    public void queryElbpRequestCode() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(183);
        writeCharaBle5(utentry());
    }

    public void queryElbpSamplingStatus() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(135);
        writeCharaBle5(utenclass());
    }

    public void queryElbsAlgorithmActiveState() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(182);
        writeCharaBle5(utennew());
    }

    public void queryElbsTestStatus() {
        byte[] bArrUtenconst = utenconst();
        CommandTimeOutUtils.getInstance().setCommandTimeOut(163);
        writeCharaBle5(bArrUtenconst);
    }

    public void queryFemaleMenstrualFunctionSwitch() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(187);
        writeChara(new byte[]{99, -86, 1});
    }

    public void queryFirmwareVersion() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(1);
        writeChara(new byte[]{-95});
    }

    public void queryFirmwareVersionBeforePaired() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(1);
        NotifyUtils.getInstance().writeChara(new byte[]{-95});
    }

    public void queryGlucoseMetabolismAlgorithm() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(206);
        writeCharaBle5(new byte[]{102, 2});
    }

    public void queryLocalWatchFace() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(145);
        writeChara(new byte[]{89, -6});
    }

    public void queryMenstrualDataForMonth(String str) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_MENSTRUAL_DATA_FOR_MONTH);
        int i2 = Integer.parseInt(str.substring(0, 4));
        byte b2 = (byte) (i2 & 255);
        writeChara(new byte[]{41, -86, 1, (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), b2, (byte) (Integer.parseInt(str.substring(4, 6)) & 255)});
    }

    public void queryMenstrualParam() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(244);
        writeChara(new byte[]{41, -6});
    }

    public void queryMoodAlgorithmActiveState() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(106);
        writeChara(utencase());
    }

    public void queryMoodPressureTestState() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(104);
        writeChara(utenchar());
    }

    public void queryMoodRequestCode() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(107);
        writeChara(utenelse());
    }

    public void queryMoodSensorType() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(105);
        writeChara(utengoto());
    }

    public void queryOxygenTestStatus() {
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE, -86});
    }

    public void querySOS() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_SOS_CONTACT);
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -67});
    }

    public void querySedentaryRemind() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.QUERY_SEDENTARY_REMIND);
        writeChara(new byte[]{-45, -6});
    }

    public void querySosCallContactsCount() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(109);
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -86});
    }

    public void querySportsModeList() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(58);
        writeCharaBle5(new byte[]{-3, 72, -86});
    }

    public void queryTodayTarget(int i2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i3;
        if (i2 == 1) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 238;
        } else if (i2 == 2) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 239;
        } else if (i2 == 3) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 240;
        } else if (i2 == 4) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 241;
        } else {
            if (i2 != 5) {
                if (i2 == 6) {
                    commandTimeOutUtils = CommandTimeOutUtils.getInstance();
                    i3 = 243;
                }
                writeChara(new byte[]{63, -86, (byte) (i2 & 255)});
            }
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i3 = 242;
        }
        commandTimeOutUtils.setCommandTimeOut(i3);
        writeChara(new byte[]{63, -86, (byte) (i2 & 255)});
    }

    public void queryWhichWatchFaceInfo(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(227);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 5, (byte) i2});
    }

    public void readDeviceWatchFaceConfiguration() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(85);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 1});
    }

    public void reportRestHeartRate() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(154);
        writeChara(new byte[]{-9, 5});
    }

    public void reportRestHeartRateHourBest() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(155);
        writeChara(new byte[]{-9, 7});
    }

    public void responseDeviceControlSport(byte[] bArr) {
        writeChara(bArr);
    }

    public void sedentaryRemind(SedentaryRemindInfo sedentaryRemindInfo) {
        byte[] bArr;
        byte b2;
        byte[] bArr2;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(66);
        int remindSwitch = sedentaryRemindInfo.getRemindSwitch();
        int fromTimeHour = sedentaryRemindInfo.getFromTimeHour();
        int fromTimeMinute = sedentaryRemindInfo.getFromTimeMinute();
        int toTimeHour = sedentaryRemindInfo.getToTimeHour();
        int toTimeMinute = sedentaryRemindInfo.getToTimeMinute();
        int remindInterval = sedentaryRemindInfo.getRemindInterval();
        int lunchBreakNoRemind = sedentaryRemindInfo.getLunchBreakNoRemind();
        SPUtil.getInstance().setSedentaryRemindSwitch(remindSwitch);
        SPUtil.getInstance().setSedentaryRemindFromTimeHour(fromTimeHour);
        SPUtil.getInstance().setSedentaryRemindFromTimeMinute(fromTimeMinute);
        SPUtil.getInstance().setSedentaryRemindToTimeHour(toTimeHour);
        SPUtil.getInstance().setSedentaryRemindToTimeMinute(toTimeMinute);
        SPUtil.getInstance().setSedentaryRemindInterval(remindInterval);
        SPUtil.getInstance().setSedentaryLunchBreakNoRemind(lunchBreakNoRemind);
        boolean zIsHasFunction_2 = DeviceMode.isHasFunction_2(16);
        boolean zIsHasFunction_6 = DeviceMode.isHasFunction_6(65536);
        if (zIsHasFunction_2 || zIsHasFunction_6) {
            byte[] bArr3 = new byte[12];
            if (zIsHasFunction_6) {
                bArr = new byte[16];
                int noRemindFromTimeHour = sedentaryRemindInfo.getNoRemindFromTimeHour();
                int noRemindFromTimeMinute = sedentaryRemindInfo.getNoRemindFromTimeMinute();
                int noRemindToTimeHour = sedentaryRemindInfo.getNoRemindToTimeHour();
                int noRemindToTimeMinute = sedentaryRemindInfo.getNoRemindToTimeMinute();
                SPUtil.getInstance().setSedentaryNoRemindFromTimeHour(noRemindFromTimeHour);
                SPUtil.getInstance().setSedentaryNoRemindFromTimeMinute(noRemindFromTimeMinute);
                SPUtil.getInstance().setSedentaryNoRemindToTimeHour(noRemindToTimeHour);
                SPUtil.getInstance().setSedentaryNoRemindToTimeMinute(noRemindToTimeMinute);
                bArr[12] = (byte) (noRemindFromTimeHour & 255);
                bArr[13] = (byte) (noRemindFromTimeMinute & 255);
                bArr[14] = (byte) (noRemindToTimeHour & 255);
                bArr[15] = (byte) (noRemindToTimeMinute & 255);
            } else {
                bArr = bArr3;
            }
            bArr[7] = (byte) (fromTimeHour & 255);
            bArr[8] = (byte) (fromTimeMinute & 255);
            bArr[9] = (byte) (toTimeHour & 255);
            bArr[10] = (byte) (toTimeMinute & 255);
            if (lunchBreakNoRemind == 1) {
                bArr[11] = 1;
                b2 = 0;
            } else {
                b2 = 0;
                bArr[11] = 0;
            }
            bArr2 = bArr;
        } else {
            bArr2 = new byte[7];
            b2 = 0;
        }
        bArr2[b2] = -45;
        if (remindSwitch == 1) {
            bArr2[1] = 1;
        } else {
            bArr2[1] = b2;
        }
        bArr2[2] = (byte) (remindInterval & 255);
        bArr2[3] = 2;
        bArr2[4] = 3;
        bArr2[5] = 1;
        bArr2[6] = 0;
        writeChara(bArr2);
    }

    public void sendAccountId(int i2) {
        NotifyUtils.getInstance().writeChara(new byte[]{Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, 2, (byte) (((-16777216) & i2) >> 24), (byte) ((16711680 & i2) >> 16), (byte) ((65280 & i2) >> 8), (byte) (i2 & 255)});
    }

    public void sendAccountIdAndRandomCode(int i2, int i3, boolean z2) {
        byte[] bArr = new byte[11];
        bArr[0] = Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES;
        bArr[1] = 2;
        bArr[2] = (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24);
        bArr[3] = (byte) ((i2 & 16711680) >> 16);
        bArr[4] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[5] = (byte) (i2 & 255);
        bArr[6] = (byte) ((i3 & ViewCompat.MEASURED_STATE_MASK) >> 24);
        bArr[7] = (byte) ((i3 & 16711680) >> 16);
        bArr[8] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[9] = (byte) (i3 & 255);
        if (z2) {
            bArr[10] = 0;
        } else {
            bArr[10] = 1;
        }
        NotifyUtils.getInstance().writeChara(bArr);
    }

    public void sendAppLocationData(AppLocationDataInfo appLocationDataInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(157);
        double latitude = appLocationDataInfo.getLatitude();
        double longitude = appLocationDataInfo.getLongitude();
        float speed = appLocationDataInfo.getSpeed();
        int pointDistance = appLocationDataInfo.getPointDistance();
        int totalDistance = appLocationDataInfo.getTotalDistance();
        int i2 = (int) (latitude * 1000000.0d);
        int i3 = (int) (longitude * 1000000.0d);
        int i4 = (int) (speed * 100.0f);
        writeCharaBle5(new byte[]{-3, 2, (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((i2 & 16711680) >> 16), (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i2 & 255), (byte) ((i3 & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((i3 & 16711680) >> 16), (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255), (byte) ((i4 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i4 & 255), (byte) ((pointDistance & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((pointDistance & 16711680) >> 16), (byte) ((pointDistance & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (pointDistance & 255), (byte) ((totalDistance & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((totalDistance & 16711680) >> 16), (byte) ((totalDistance & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (totalDistance & 255)});
    }

    public void sendAppLocationStatus(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(156);
        byte[] bArr = new byte[3];
        bArr[0] = -3;
        bArr[1] = 1;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        writeCharaBle5(bArr);
    }

    public void sendBloodSugarLevel(BloodSugarLevelInfo bloodSugarLevelInfo) {
        int interval = bloodSugarLevelInfo.getInterval();
        if (interval < 1) {
            return;
        }
        byte[] bArr = new byte[DateTimeConstants.MINUTES_PER_DAY / interval];
        Arrays.fill(bArr, (byte) -1);
        List<BloodSugarLevelInfo.TimeLevel> timeLevelList = bloodSugarLevelInfo.getTimeLevelList();
        for (int i2 = 0; i2 < timeLevelList.size(); i2++) {
            BloodSugarLevelInfo.TimeLevel timeLevel = timeLevelList.get(i2);
            int timeMinute = timeLevel.getTimeMinute();
            int level = timeLevel.getLevel();
            if (timeMinute % interval == 0) {
                bArr[timeMinute / interval] = (byte) level;
            }
        }
        LogUtils.d("sendBloodSugarLevel = " + GBUtils.getInstance().bytes2HexString(bArr));
        this.mBloodSugarLevelInterval = interval;
        this.mBloodSugarLevelData = bArr;
        this.mBloodSugarLevelCrc = 0;
        this.isBloodSugarLevelFD = false;
        sendBloodSugarLevelSegments(0);
    }

    public void sendBloodSugarLevelSegments(int i2) {
        if (this.mBloodSugarLevelData == null) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(211);
        byte[] bArr = this.mBloodSugarLevelData;
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 5;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 2;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 102;
            bArr2[1] = 6;
            bArr2[2] = -6;
            bArr2[3] = (byte) this.mBloodSugarLevelInterval;
            bArr2[4] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 5, i3);
            while (i6 < maxCommunicationLength) {
                this.mBloodSugarLevelCrc ^= bArr2[i6];
                i6++;
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isBloodSugarLevelFD) {
            utenint(this.mBloodSugarLevelCrc);
            this.isBloodSugarLevelFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 5;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = 102;
            bArr3[1] = 6;
            bArr3[2] = -6;
            bArr3[3] = (byte) this.mBloodSugarLevelInterval;
            bArr3[4] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 5, i5);
            while (i6 < i7) {
                this.mBloodSugarLevelCrc ^= bArr3[i6];
                i6++;
            }
            writeCharaBle5(bArr3);
        }
        this.isBloodSugarLevelFD = true;
    }

    public void sendBloodSugarTIR(BloodSugarTirInfo bloodSugarTirInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(212);
        writeCharaBle5(new byte[]{102, 7, (byte) (bloodSugarTirInfo.getTir() & 255), (byte) (bloodSugarTirInfo.getPerfect() & 255), (byte) (bloodSugarTirInfo.getCommon() & 255), (byte) (bloodSugarTirInfo.getBad() & 255)});
    }

    public void sendBloodSugarValue(BloodSugarValueInfo bloodSugarValueInfo) {
        int interval = bloodSugarValueInfo.getInterval();
        if (interval < 1) {
            return;
        }
        byte[] bArr = new byte[(DateTimeConstants.MINUTES_PER_DAY / interval) * 2];
        Arrays.fill(bArr, (byte) -1);
        List<BloodSugarValueInfo.TimeValue> timeValueList = bloodSugarValueInfo.getTimeValueList();
        for (int i2 = 0; i2 < timeValueList.size(); i2++) {
            BloodSugarValueInfo.TimeValue timeValue = timeValueList.get(i2);
            int timeMinute = timeValue.getTimeMinute();
            int value = (int) (timeValue.getValue() * 100.0f);
            if (timeMinute % interval == 0) {
                int i3 = (timeMinute * 2) / interval;
                bArr[i3] = (byte) ((65280 & value) >> 8);
                bArr[i3 + 1] = (byte) (value & 255);
            }
        }
        LogUtils.d("sendBloodSugarValue = " + GBUtils.getInstance().bytes2HexString(bArr));
        this.mBloodSugarValueInterval = interval;
        this.mBloodSugarValueData = bArr;
        this.mBloodSugarValueCrc = 0;
        this.isBloodSugarValueFD = false;
        sendBloodSugarValueSegments(0);
    }

    public void sendBloodSugarValueSegments(int i2) {
        if (this.mBloodSugarValueData == null) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(210);
        byte[] bArr = this.mBloodSugarValueData;
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 5;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 2;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 102;
            bArr2[1] = 5;
            bArr2[2] = -6;
            bArr2[3] = (byte) this.mBloodSugarValueInterval;
            bArr2[4] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 5, i3);
            while (i6 < maxCommunicationLength) {
                this.mBloodSugarValueCrc ^= bArr2[i6];
                i6++;
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isBloodSugarValueFD) {
            utennew(this.mBloodSugarValueCrc);
            this.isBloodSugarValueFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 5;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = 102;
            bArr3[1] = 5;
            bArr3[2] = -6;
            bArr3[3] = (byte) this.mBloodSugarValueInterval;
            bArr3[4] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 5, i5);
            while (i6 < i7) {
                this.mBloodSugarValueCrc ^= bArr3[i6];
                i6++;
            }
            writeCharaBle5(bArr3);
        }
        this.isBloodSugarValueFD = true;
    }

    public void sendCalibratTimeoutToBLE() {
        writeChara(new byte[]{-81, -1, -1, -1, -1});
    }

    public void sendContactsData() {
        LogUtils.i("发送第一条");
        this.NOsectionContact = 0;
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        sendSectionContacts();
    }

    public void sendCustomizeWidgetListsEnd() {
        writeCharaBle5(new byte[]{73, -3, (byte) (this.customzieWidgettCRC & 255)});
    }

    public void sendDeviceElbpMiddleDataCrcResults(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(139);
        writeCharaBle5(utenif(z2));
    }

    public void sendDeviceElbpPpgDataCrcResults(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(138);
        writeCharaBle5(utenfor(z2));
    }

    public void sendDeviceWidgetSection() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(124);
        List<DeviceWidgetInfo> list = this.deviceWidgetList;
        if (list == null || list.size() <= 0) {
            return;
        }
        int size = this.deviceWidgetList.size();
        int i2 = this.NOsectionCustomzieWidget;
        int i3 = (this.maxCommunicationLength - 2) / 3;
        if (i3 == 0) {
            return;
        }
        int i4 = size % i3;
        if (i2 < size / i3) {
            int i5 = (i3 * 3) + 2;
            byte[] bArr = new byte[i5];
            bArr[0] = 73;
            bArr[1] = (byte) (i2 & 255);
            for (int i6 = i3 * i2; i6 < (i2 + 1) * i3; i6++) {
                DeviceWidgetInfo deviceWidgetInfo = this.deviceWidgetList.get(i6);
                int widgetId = deviceWidgetInfo.getWidgetId();
                int widgetDisplayState = deviceWidgetInfo.getWidgetDisplayState();
                int widgetPosition = deviceWidgetInfo.getWidgetPosition();
                int i7 = i6 * 3;
                bArr[i7 + 2] = (byte) (widgetId & 255);
                bArr[i7 + 3] = (byte) (widgetDisplayState & 255);
                bArr[i7 + 4] = (byte) (widgetPosition & 255);
            }
            if (i4 == 0) {
                this.isSendCustomzieWidgetFinish = true;
            }
            for (int i8 = 1; i8 < i5; i8++) {
                this.customzieWidgettCRC ^= bArr[i8];
            }
            this.NOsectionCustomzieWidget++;
            writeCharaBle5(bArr);
            return;
        }
        if (this.isSendCustomzieWidgetFinish) {
            sendCustomizeWidgetListsEnd();
            this.isSendCustomzieWidgetFinish = false;
            return;
        }
        if (i4 != 0) {
            int i9 = i3 * i2;
            int i10 = ((size - i9) * 3) + 2;
            byte[] bArr2 = new byte[i10];
            bArr2[0] = 73;
            bArr2[1] = (byte) (i2 & 255);
            while (i9 < size) {
                DeviceWidgetInfo deviceWidgetInfo2 = this.deviceWidgetList.get(i9);
                int widgetId2 = deviceWidgetInfo2.getWidgetId();
                int widgetDisplayState2 = deviceWidgetInfo2.getWidgetDisplayState();
                int widgetPosition2 = deviceWidgetInfo2.getWidgetPosition();
                int i11 = i9 * 3;
                bArr2[i11 + 2] = (byte) (widgetId2 & 255);
                bArr2[i11 + 3] = (byte) (widgetDisplayState2 & 255);
                bArr2[i11 + 4] = (byte) (widgetPosition2 & 255);
                i9++;
            }
            for (int i12 = 1; i12 < i10; i12++) {
                this.customzieWidgettCRC ^= bArr2[i12];
            }
            this.isSendCustomzieWidgetFinish = true;
            this.NOsectionCustomzieWidget++;
            writeCharaBle5(bArr2);
        }
    }

    public void sendElbsCalibrationModel(byte[] bArr) {
        this.f24892b = bArr;
        this.mElbsCalibrationModelCrc = 0;
        this.isElbsCalibrationModelFD = false;
        sendElbsCalibrationModelDataSegments(0);
    }

    public void sendElbsCalibrationModelDataSegments(int i2) {
        if (this.f24892b == null) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(184);
        byte[] bArr = this.f24892b;
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 4;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 3;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 85;
            bArr2[1] = 7;
            bArr2[2] = -6;
            bArr2[3] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 4, i3);
            while (i6 < maxCommunicationLength) {
                this.mElbsCalibrationModelCrc ^= bArr2[i6];
                i6++;
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isElbsCalibrationModelFD) {
            utentry(this.mElbsCalibrationModelCrc);
            this.isElbsCalibrationModelFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 4;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = 85;
            bArr3[1] = 7;
            bArr3[2] = -6;
            bArr3[3] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 4, i5);
            while (i6 < i7) {
                this.mElbsCalibrationModelCrc ^= bArr3[i6];
                i6++;
            }
            writeCharaBle5(bArr3);
        }
        this.isElbsCalibrationModelFD = true;
    }

    public void sendElbsToDevice(float f2) {
        writeCharaBle5(utendo(f2));
    }

    public void sendGlucoseMetabolismSampledCrcResult(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(208);
        byte[] bArr = new byte[3];
        bArr[0] = 102;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = -3;
        } else {
            bArr[2] = -1;
        }
        writeCharaBle5(bArr);
    }

    public void sendLabelAlarmClockSection() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(118);
        if (this.alarmClockData == null) {
            return;
        }
        if (this.NOsectionAlarmClock == 0) {
            this.isAlarmClockSendFD = false;
        }
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        this.maxCommunicationLength = maxCommunicationLength;
        byte[] bArr = this.alarmClockData;
        int i2 = maxCommunicationLength - 2;
        int length = bArr.length;
        int i3 = length / i2;
        int i4 = length % i2;
        LogUtils.i("LabelAlarmClock max=" + i2 + ",length=" + length + ",sendCount =" + i3 + "，lastCount = " + i4 + ",NOsectionAlarmClock=" + this.NOsectionAlarmClock);
        int i5 = this.NOsectionAlarmClock;
        if (i5 < i3) {
            int i6 = this.maxCommunicationLength;
            byte[] bArr2 = new byte[i6];
            bArr2[0] = 81;
            bArr2[1] = (byte) (i5 & 255);
            int i7 = i5 * i2;
            while (true) {
                int i8 = this.NOsectionAlarmClock * i2;
                if (i7 >= i2 + i8) {
                    break;
                }
                bArr2[(i7 - i8) + 2] = bArr[i7];
                i7++;
            }
            writeCharaBle5(bArr2);
            for (int i9 = 1; i9 < i6; i9++) {
                this.alarmClockCRC ^= bArr2[i9];
            }
            this.NOsectionAlarmClock++;
            if (i4 != 0) {
                return;
            }
        } else {
            LogUtils.i("isAlarmClockSendFD = " + this.isAlarmClockSendFD);
            if (this.isAlarmClockSendFD) {
                utensuper();
                this.isAlarmClockSendFD = false;
            } else if (i4 != 0) {
                int i10 = i4 + 2;
                byte[] bArr3 = new byte[i10];
                bArr3[0] = 81;
                int i11 = this.NOsectionAlarmClock;
                bArr3[1] = (byte) (i11 & 255);
                for (int i12 = i11 * i2; i12 < length; i12++) {
                    bArr3[(i12 - (this.NOsectionAlarmClock * i2)) + 2] = bArr[i12];
                }
                writeCharaBle5(bArr3);
                for (int i13 = 1; i13 < i10; i13++) {
                    this.alarmClockCRC ^= bArr3[i13];
                }
                this.NOsectionAlarmClock++;
            }
        }
        this.isAlarmClockSendFD = true;
    }

    public void sendLoginElServerInfoSegments(int i2) {
        if (this.mLoginElServerInfoData == null) {
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(213);
        byte[] bArr = this.mLoginElServerInfoData;
        int maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        int i3 = maxCommunicationLength - 4;
        int length = bArr.length;
        int i4 = length / i3;
        int i5 = length % i3;
        int i6 = 3;
        if (i2 < i4) {
            byte[] bArr2 = new byte[maxCommunicationLength];
            bArr2[0] = 85;
            bArr2[1] = 15;
            bArr2[2] = -6;
            bArr2[3] = (byte) i2;
            System.arraycopy(bArr, i2 * i3, bArr2, 4, i3);
            while (i6 < maxCommunicationLength) {
                this.mLoginElServerInfoCrc ^= bArr2[i6];
                i6++;
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isLoginElServerInfoFD) {
            utenbyte(this.mLoginElServerInfoCrc);
            this.isLoginElServerInfoFD = false;
        } else if (i5 != 0) {
            int i7 = i5 + 4;
            byte[] bArr3 = new byte[i7];
            bArr3[0] = 85;
            bArr3[1] = 15;
            bArr3[2] = -6;
            bArr3[3] = (byte) i2;
            System.arraycopy(bArr, i3 * i2, bArr3, 4, i5);
            while (i6 < i7) {
                this.mLoginElServerInfoCrc ^= bArr3[i6];
                i6++;
            }
            writeCharaBle5(bArr3);
        }
        this.isLoginElServerInfoFD = true;
    }

    public void sendMenstrualEndDay(String str) throws NumberFormatException {
        int i2;
        int i3;
        int i4;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(273);
        if (TextUtils.isEmpty(str) || str.length() != 8) {
            i2 = 0;
            i3 = 0;
            i4 = 0;
        } else {
            i3 = Integer.parseInt(str.substring(0, 4));
            i4 = Integer.parseInt(str.substring(4, 6));
            i2 = Integer.parseInt(str.substring(6, 8));
        }
        writeChara(new byte[]{41, -85, 2, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255), (byte) (i4 & 255), (byte) (i2 & 255)});
    }

    public void sendMenstrualStartDay(String str) throws NumberFormatException {
        int i2;
        int i3;
        int i4;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SEND_MENSTRUAL_START_DAY);
        if (TextUtils.isEmpty(str) || str.length() != 8) {
            i2 = 0;
            i3 = 0;
            i4 = 0;
        } else {
            i3 = Integer.parseInt(str.substring(0, 4));
            i4 = Integer.parseInt(str.substring(4, 6));
            i2 = Integer.parseInt(str.substring(6, 8));
        }
        writeChara(new byte[]{41, -85, 1, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255), (byte) (i4 & 255), (byte) (i2 & 255)});
    }

    public void sendOnlineDialData(byte[] bArr) {
        if (bArr != null) {
            setReduceDialSpeed(false);
            this.NOsectionOnline = 0;
            this.textDataOnline = bArr;
            this.isSendFinisCommand = false;
            this.isHadSendWatchFinis = false;
            this.lastErase = 0;
            this.currErase = 0;
            this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
            sendSectionOnlineDialData(this.NOsectionOnline);
        }
    }

    public void sendPhoneNumber(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(116);
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().stringToASCII(str));
        int length = bArrHexStringToBytes.length;
        byte[] bArr = new byte[length + 3];
        bArr[0] = 69;
        bArr[1] = -6;
        bArr[2] = (byte) (length & 255);
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2 + 3] = bArrHexStringToBytes[i2];
        }
        writeCharaBle5(bArr);
    }

    public void sendQRCodeData(QRCodeInfo qRCodeInfo) {
        QRCodeProcessing.getInstance().sendQRCodeData(qRCodeInfo);
    }

    public void sendSectionContacts() {
        int i2;
        int i3;
        int i4 = this.NOsectionContact;
        if (DeviceMode.isHasFunction_6(524288)) {
            i2 = 59;
            i3 = 20;
        } else {
            i2 = 39;
            i3 = 10;
        }
        int i5 = (this.maxCommunicationLength - 3) / i2;
        LogUtils.i("每次发送多少个联系人=" + i5);
        if (i5 == 0) {
            return;
        }
        int size = this.contactListsGlobal.size();
        int i6 = size / i5;
        int i7 = size % i5;
        LogUtils.i("sendCount=" + i6 + ",lastCount=" + i7 + ",section=" + i4);
        int i8 = 15;
        byte b2 = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
        if (i4 < i6) {
            byte[] bArr = new byte[this.maxCommunicationLength];
            bArr[0] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
            bArr[1] = -5;
            bArr[2] = (byte) (i5 & 255);
            int i9 = i5 * i4;
            int i10 = i9;
            int i11 = 0;
            while (i10 < (i4 + 1) * i5) {
                int i12 = i11 + 1;
                if (i10 == i9) {
                    i12 = i11 + 3;
                }
                String phone = this.contactListsGlobal.get(i10).getPhone();
                String name = this.contactListsGlobal.get(i10).getName();
                int length = phone.length();
                int length2 = name.length();
                if (length > i8) {
                    phone = phone.substring(0, i8);
                }
                if (length2 > i3) {
                    name = name.substring(0, i3);
                }
                String strInt2unicode = GBUtils.getInstance().int2unicode(phone);
                String strString2unicode = GBUtils.getInstance().string2unicode(name);
                byte[] bArrHexStringToBytessForContact = GBUtils.getInstance().hexStringToBytessForContact(strInt2unicode);
                byte[] bArrHexStringToBytessForContact2 = GBUtils.getInstance().hexStringToBytessForContact(strString2unicode);
                int length3 = bArrHexStringToBytessForContact.length;
                int length4 = bArrHexStringToBytessForContact2.length;
                bArr[i12] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
                bArr[i12 + 1] = -5;
                bArr[i12 + 2] = (byte) (length3 & 255);
                int i13 = i12 + 3;
                bArr[i13] = (byte) (length4 & 255);
                for (byte b3 : bArrHexStringToBytessForContact) {
                    i13++;
                    bArr[i13] = b3;
                }
                i11 = i13;
                for (byte b4 : bArrHexStringToBytessForContact2) {
                    i11++;
                    bArr[i11] = b4;
                }
                i10++;
                i8 = 15;
            }
            if (i7 == 0) {
                this.isSendContactFinish = true;
            }
            this.NOsectionContact++;
            writeCharaBle5(bArr);
            return;
        }
        LogUtils.i("准备发尾数 isSendContactFinish=" + this.isSendContactFinish + ",lastCount=" + i7);
        if (this.isSendContactFinish) {
            utenshort();
            this.isSendContactFinish = false;
            return;
        }
        if (i7 != 0) {
            byte[] bArr2 = new byte[this.maxCommunicationLength];
            bArr2[0] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
            bArr2[1] = -5;
            bArr2[2] = (byte) (i7 & 255);
            int i14 = i6 * i5;
            int i15 = 0;
            while (i14 < size) {
                int i16 = i15 + 1;
                if (i14 == i5 * i4) {
                    i16 = i15 + 3;
                }
                String phone2 = this.contactListsGlobal.get(i14).getPhone();
                String name2 = this.contactListsGlobal.get(i14).getName();
                int length5 = phone2.length();
                int length6 = name2.length();
                if (length5 > 15) {
                    phone2 = phone2.substring(0, 15);
                }
                if (length6 > i3) {
                    name2 = name2.substring(0, i3);
                }
                String strInt2unicode2 = GBUtils.getInstance().int2unicode(phone2);
                String strString2unicode2 = GBUtils.getInstance().string2unicode(name2);
                byte[] bArrHexStringToBytessForContact3 = GBUtils.getInstance().hexStringToBytessForContact(strInt2unicode2);
                byte[] bArrHexStringToBytessForContact4 = GBUtils.getInstance().hexStringToBytessForContact(strString2unicode2);
                int length7 = bArrHexStringToBytessForContact3.length;
                int length8 = bArrHexStringToBytessForContact4.length;
                bArr2[i16] = b2;
                bArr2[i16 + 1] = -5;
                bArr2[i16 + 2] = (byte) (length7 & 255);
                int i17 = i16 + 3;
                bArr2[i17] = (byte) (length8 & 255);
                for (byte b5 : bArrHexStringToBytessForContact3) {
                    i17++;
                    bArr2[i17] = b5;
                }
                i15 = i17;
                for (byte b6 : bArrHexStringToBytessForContact4) {
                    i15++;
                    bArr2[i15] = b6;
                }
                i14++;
                b2 = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
            }
            this.NOsectionContact++;
            this.isSendContactFinish = true;
            writeCharaBle5(bArr2);
        }
    }

    public synchronized void sendSectionOnlineDialData(int i2) {
        int i3;
        Handler handler;
        int i4;
        try {
            byte[] bArr = this.textDataOnline;
            if (bArr == null) {
                return;
            }
            int i5 = this.maxCommunicationLength;
            int i6 = i5 - 3;
            int length = bArr.length;
            int i7 = length / i6;
            int i8 = length % i6;
            if (i2 == 0) {
                this.isSendFinisCommand = false;
                this.isHadSendWatchFinis = false;
                if (i8 == 0) {
                    this.totalCount = i7;
                } else {
                    this.totalCount = i7 + 1;
                }
            }
            if (i2 < i7) {
                byte[] bArr2 = new byte[i5];
                bArr2[0] = 39;
                bArr2[1] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                bArr2[2] = (byte) (i2 & 255);
                int i9 = i6 * i2;
                int i10 = i9;
                while (true) {
                    i3 = (i2 + 1) * i6;
                    if (i10 >= i3) {
                        break;
                    }
                    int i11 = i10 - i9;
                    if (i11 < i6 && i10 < length) {
                        bArr2[i11 + 3] = bArr[i10];
                    }
                    i10++;
                }
                writeCharaBle5(bArr2);
                Message message = new Message();
                message.what = 2;
                if (i2 == i7 - 1 && i8 == 0) {
                    this.isSendFinisCommand = true;
                    LogUtils.i("0最后一条延迟" + this.intervalEnd + "ms, section =" + i2);
                    handler = this.mHandler;
                    i4 = this.intervalEnd;
                } else {
                    int i12 = i3 / 4096;
                    this.currErase = i12;
                    if (this.lastErase != i12) {
                        this.lastErase = i12;
                        LogUtils.i("等待BLE返回260304,section =" + i2);
                        this.mOnlineDialTimeOut.setCommandTimeOut(4);
                    } else if (this.isReduceDialSpeed) {
                        handler = this.mHandler;
                        i4 = WatchFaceUtil.REDUCE_SPEED_SEND_ONLINE_DIAL_TIME;
                    } else if (i2 <= 0 || i2 % 10 != 0) {
                        this.mHandler.sendMessage(message);
                    } else {
                        handler = this.mHandler;
                        i4 = WatchFaceUtil.DELAY_SYNC_WATCH_FACE_DATA_TIME;
                    }
                }
                handler.sendMessageDelayed(message, i4);
            } else {
                if (this.isHadSendWatchFinis) {
                    LogUtils.i("已经发送260300了，不用再发送了");
                } else if (this.isSendFinisCommand) {
                    this.isHadSendWatchFinis = true;
                    utenthrow();
                    this.isSendFinisCommand = false;
                    this.mHandler.removeMessages(2);
                } else if (i8 != 0) {
                    byte[] bArr3 = new byte[i8 + 3];
                    bArr3[0] = 39;
                    bArr3[1] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                    bArr3[2] = (byte) (i2 & 255);
                    int i13 = i6 * i2;
                    for (int i14 = i13; i14 < length; i14++) {
                        int i15 = i14 - i13;
                        if (i15 < i6 && i14 < length) {
                            bArr3[i15 + 3] = bArr[i14];
                        }
                    }
                    writeCharaBle5(bArr3);
                    Message message2 = new Message();
                    message2.what = 2;
                    LogUtils.i("1最后一条延迟" + this.intervalEnd + "ms, section =" + i2);
                    this.mHandler.sendMessageDelayed(message2, (long) this.intervalEnd);
                }
                this.isSendFinisCommand = true;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public void sendSectionSosCall() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = this.nOsectionSosCall;
        if (DeviceMode.isHasFunction_6(524288)) {
            i2 = 59;
            i3 = 20;
        } else {
            i2 = 39;
            i3 = 10;
        }
        int i7 = (this.maxCommunicationLength - 3) / i2;
        if (i7 == 0) {
            return;
        }
        int size = this.sosCallInfos.size();
        int i8 = size / i7;
        int i9 = size % i7;
        LogUtils.i("sendCount=" + i8 + ",lastCount=" + i9 + ",section=" + i6);
        int i10 = 15;
        if (i6 < i8) {
            int i11 = this.maxCommunicationLength;
            byte[] bArr = new byte[i11];
            bArr[0] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
            bArr[1] = -84;
            bArr[2] = (byte) (i7 & 255);
            int i12 = i7 * i6;
            int i13 = 0;
            int i14 = i12;
            while (i14 < i7 * (i6 + 1)) {
                int i15 = i13 + 1;
                if (i14 == i12) {
                    i15 = i13 + 3;
                }
                String phone = this.sosCallInfos.get(i14).getPhone();
                String name = this.sosCallInfos.get(i14).getName();
                String strReplace = phone.replace(" ", "");
                int length = strReplace.length();
                int i16 = i12;
                int length2 = name.length();
                if (length > 15) {
                    i5 = 0;
                    strReplace = strReplace.substring(0, 15);
                } else {
                    i5 = 0;
                }
                if (length2 > i3) {
                    name = name.substring(i5, i3);
                }
                String strInt2unicode = GBUtils.getInstance().int2unicode(strReplace);
                String strString2unicode = GBUtils.getInstance().string2unicode(name);
                byte[] bArrHexStringToBytessForContact = GBUtils.getInstance().hexStringToBytessForContact(strInt2unicode);
                byte[] bArrHexStringToBytessForContact2 = GBUtils.getInstance().hexStringToBytessForContact(strString2unicode);
                int length3 = bArrHexStringToBytessForContact.length;
                int length4 = bArrHexStringToBytessForContact2.length;
                bArr[i15] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
                bArr[i15 + 1] = -84;
                bArr[i15 + 2] = (byte) (length3 & 255);
                int i17 = i15 + 3;
                bArr[i17] = (byte) (length4 & 255);
                for (byte b2 : bArrHexStringToBytessForContact) {
                    i17++;
                    bArr[i17] = b2;
                }
                for (byte b3 : bArrHexStringToBytessForContact2) {
                    i17++;
                    bArr[i17] = b3;
                }
                i14++;
                i13 = i17;
                i12 = i16;
            }
            if (i9 == 0) {
                this.isSendSosCallFinish = true;
            }
            for (int i18 = 2; i18 < i11; i18++) {
                this.sosCallCRC = (byte) (this.sosCallCRC ^ bArr[i18]);
            }
            this.nOsectionSosCall++;
            writeCharaBle5(bArr);
            return;
        }
        if (this.isSendSosCallFinish) {
            utendouble();
            this.isSendSosCallFinish = false;
            return;
        }
        if (i9 != 0) {
            int i19 = this.maxCommunicationLength;
            byte[] bArr2 = new byte[i19];
            bArr2[0] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
            bArr2[1] = -84;
            bArr2[2] = (byte) (i9 & 255);
            int i20 = i8 * i7;
            int i21 = 0;
            while (i20 < size) {
                int i22 = i21 + 1;
                if (i20 == i7 * i6) {
                    i22 = i21 + 3;
                }
                String phone2 = this.sosCallInfos.get(i20).getPhone();
                String name2 = this.sosCallInfos.get(i20).getName();
                String strReplace2 = phone2.replace(" ", "");
                int length5 = strReplace2.length();
                int length6 = name2.length();
                if (length5 > i10) {
                    i4 = 0;
                    strReplace2 = strReplace2.substring(0, i10);
                } else {
                    i4 = 0;
                }
                if (length6 > i3) {
                    name2 = name2.substring(i4, i3);
                }
                String strInt2unicode2 = GBUtils.getInstance().int2unicode(strReplace2);
                String strString2unicode2 = GBUtils.getInstance().string2unicode(name2);
                byte[] bArrHexStringToBytessForContact3 = GBUtils.getInstance().hexStringToBytessForContact(strInt2unicode2);
                byte[] bArrHexStringToBytessForContact4 = GBUtils.getInstance().hexStringToBytessForContact(strString2unicode2);
                int length7 = bArrHexStringToBytessForContact3.length;
                int length8 = bArrHexStringToBytessForContact4.length;
                bArr2[i22] = Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK;
                bArr2[i22 + 1] = -84;
                bArr2[i22 + 2] = (byte) (length7 & 255);
                int i23 = i22 + 3;
                bArr2[i23] = (byte) (length8 & 255);
                int length9 = bArrHexStringToBytessForContact3.length;
                for (int i24 = i4; i24 < length9; i24++) {
                    i23++;
                    bArr2[i23] = bArrHexStringToBytessForContact3[i24];
                }
                int length10 = bArrHexStringToBytessForContact4.length;
                int i25 = i23;
                for (int i26 = i4; i26 < length10; i26++) {
                    i25++;
                    bArr2[i25] = bArrHexStringToBytessForContact4[i26];
                }
                i20++;
                i21 = i25;
                i10 = 15;
            }
            for (int i27 = 2; i27 < i19; i27++) {
                this.sosCallCRC = (byte) (this.sosCallCRC ^ bArr2[i27]);
            }
            this.nOsectionSosCall++;
            this.isSendSosCallFinish = true;
            writeCharaBle5(bArr2);
        }
    }

    public void sendSportManagementListsSection() {
        int size = this.displaySportsList.size();
        if (size > 0) {
            int i2 = this.NOsectionSport;
            int i3 = (this.maxCommunicationLength - 3) / 3;
            if (i3 == 0) {
                return;
            }
            int i4 = size / i3;
            int i5 = size % i3;
            LogUtils.i("sportsSize=" + size + ",section=" + i2 + ",onceCount=" + i3 + ",sendCount=" + i4 + ",lastCount=" + i5 + ",maxCommunicationLength=" + this.maxCommunicationLength);
            int i6 = 2;
            if (i2 < i4) {
                int i7 = (i3 * 3) + 3;
                byte[] bArr = new byte[i7];
                bArr[0] = -3;
                bArr[1] = 72;
                bArr[2] = (byte) (i2 & 255);
                int i8 = i3 * i2;
                for (int i9 = i8; i9 < (i2 + 1) * i3; i9++) {
                    int sportsMode = this.displaySportsList.get(i9).getSportsMode();
                    int position = this.displaySportsList.get(i9).getPosition();
                    int displayStatus = this.displaySportsList.get(i9).getDisplayStatus();
                    int i10 = ((i9 - i8) + 1) * 3;
                    bArr[i10] = (byte) (sportsMode & 255);
                    bArr[i10 + 1] = (byte) (displayStatus & 255);
                    bArr[i10 + 2] = (byte) (position & 255);
                }
                if (i5 == 0) {
                    this.isSendSportListFinish = true;
                }
                while (i6 < i7) {
                    this.sportListCRC ^= bArr[i6];
                    i6++;
                }
                this.NOsectionSport++;
                writeCharaBle5(bArr);
                return;
            }
            LogUtils.i("准备发尾数 isSendSportListFinish=" + this.isSendSportListFinish + ",lastCount=" + i5);
            if (this.isSendSportListFinish) {
                utenimport();
                this.isSendSportListFinish = false;
                return;
            }
            if (i5 != 0) {
                int i11 = i3 * i2;
                int i12 = ((size - i11) * 3) + 3;
                byte[] bArr2 = new byte[i12];
                bArr2[0] = -3;
                bArr2[1] = 72;
                bArr2[2] = (byte) (i2 & 255);
                for (int i13 = i11; i13 < size; i13++) {
                    int sportsMode2 = this.displaySportsList.get(i13).getSportsMode();
                    int position2 = this.displaySportsList.get(i13).getPosition();
                    int displayStatus2 = this.displaySportsList.get(i13).getDisplayStatus();
                    int i14 = ((i13 - i11) + 1) * 3;
                    bArr2[i14] = (byte) (sportsMode2 & 255);
                    bArr2[i14 + 1] = (byte) (displayStatus2 & 255);
                    bArr2[i14 + 2] = (byte) (position2 & 255);
                }
                while (i6 < i12) {
                    this.sportListCRC ^= bArr2[i6];
                    i6++;
                }
                this.isSendSportListFinish = true;
                this.NOsectionSport++;
                writeCharaBle5(bArr2);
            }
        }
    }

    public void sendSportsDataToBle(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(57);
        utendo(68, sportsModeControlInfo);
    }

    public void sendTextSectionKey(int i2) {
        byte[] bArr = this.textData;
        if (bArr == null) {
            return;
        }
        if (i2 == 0) {
            this.isSendFD = false;
        }
        int length = bArr.length;
        LogUtils.i("sendTextSectionKey  length =" + length);
        int i3 = this.msgPushMaxLength;
        if (length > i3) {
            length = i3;
        }
        int i4 = length / 18;
        int i5 = length % 18;
        LogUtils.i("length=" + length + ",sendCount =" + i4 + "，lastCount = " + i5);
        if (i2 < i4) {
            byte[] bArr2 = new byte[20];
            bArr2[0] = -59;
            bArr2[1] = (byte) (i2 & 255);
            int i6 = i2 * 18;
            for (int i7 = i6; i7 < 18 + i6; i7++) {
                bArr2[(i7 - i6) + 2] = bArr[i7];
            }
            writeChara(bArr2);
            if (i5 != 0) {
                return;
            }
        } else {
            LogUtils.i("isSendFD = " + this.isSendFD);
            if (this.isSendFD) {
                utenfinal();
                this.isSendFD = false;
            } else if (i5 != 0) {
                byte[] bArr3 = new byte[i5 + 2];
                bArr3[0] = -59;
                bArr3[1] = (byte) (i2 & 255);
                int i8 = i2 * 18;
                for (int i9 = i8; i9 < length; i9++) {
                    bArr3[(i9 - i8) + 2] = bArr[i9];
                }
                writeChara(bArr3);
            }
        }
        this.isSendFD = true;
    }

    public void sendTextSectionKey6(int i2) {
        byte[] bArr = this.textData;
        if (bArr == null) {
            return;
        }
        if (i2 == 0) {
            this.isSendFD = false;
        }
        int length = bArr.length;
        if (length > 32) {
            length = 32;
        }
        int i3 = length / 18;
        int i4 = length % 18;
        LogUtils.i("length=" + length + ",sendCount =" + i3 + "，lastCount = " + i4);
        if (i2 < i3) {
            byte[] bArr2 = new byte[20];
            bArr2[0] = -58;
            bArr2[1] = (byte) (i2 & 255);
            int i5 = i2 * 18;
            for (int i6 = i5; i6 < 18 + i5; i6++) {
                bArr2[(i6 - i5) + 2] = bArr[i6];
            }
            writeChara(bArr2);
            if (i4 != 0) {
                return;
            }
        } else if (this.isSendFD) {
            this.isSendFD = false;
            utenfloat();
        } else if (i4 != 0) {
            byte[] bArr3 = new byte[i4 + 2];
            bArr3[0] = -58;
            bArr3[1] = (byte) (i2 & 255);
            int i7 = i2 * 18;
            for (int i8 = i7; i8 < length; i8++) {
                bArr3[(i8 - i7) + 2] = bArr[i8];
            }
            writeChara(bArr3);
        }
        this.isSendFD = true;
    }

    public void sendTextToBle6(String str, int i2) throws UnsupportedEncodingException {
        byte[] bytes;
        String strBytes2HexString;
        LogUtils.i("sendTextKey6 body =" + str);
        if (str == null || str.equals("")) {
            str = "unkown";
        }
        LogUtils.d("转化后body=" + str);
        GBUtils gBUtils = GBUtils.getInstance();
        if (DeviceMode.isHasFunction_1(4)) {
            strBytes2HexString = gBUtils.string2unicode(str);
        } else {
            try {
                bytes = str.getBytes(StringUtils.GB2312);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                bytes = null;
            }
            strBytes2HexString = gBUtils.bytes2HexString(bytes);
        }
        byte[] bArrHexStringToBytess6 = gBUtils.hexStringToBytess6(strBytes2HexString, i2);
        LogUtils.i("body =" + str + "，dataString = " + strBytes2HexString);
        if (bArrHexStringToBytess6 != null) {
            this.textData = bArrHexStringToBytess6;
            this.NOsection = 0;
            this.isSendFD = false;
            sendTextSectionKey6(0);
        }
    }

    public void sendTimeZoneSection() {
        LogUtils.i("timeZoneListSize =" + this.timeZoneListSize + ",NOsectionTimeZone =" + this.NOsectionTimeZone);
        int i2 = this.NOsectionTimeZone;
        if (i2 >= this.timeZoneListSize) {
            writeCharaBle5(new byte[]{67, -3, (byte) (this.timeZoneCRC & 255)});
            return;
        }
        float timeZone = this.timeZoneInfos.get(i2).getTimeZone();
        int i3 = (int) timeZone;
        int iAbs = Math.abs((int) ((timeZone - i3) * (DeviceMode.isHasFunction_8(1) ? 100 : 10)));
        LogUtils.i("timeZone =" + timeZone + ",timeZoneInt =" + i3 + ",timeZoneDecimalInt =" + iAbs);
        String timeZoneRegion = this.timeZoneInfos.get(this.NOsectionTimeZone).getTimeZoneRegion();
        if (TextUtils.isEmpty(timeZoneRegion)) {
            timeZoneRegion = "Region";
        }
        byte[] bytes = timeZoneRegion.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        if (length > 90) {
            length = 90;
        }
        LogUtils.i("UTF_8 length =" + length);
        int i4 = length + 7;
        byte[] bArr = new byte[i4];
        bArr[0] = 67;
        bArr[1] = -6;
        bArr[2] = (byte) (this.timeZoneListSize & 255);
        bArr[3] = (byte) (this.NOsectionTimeZone & 255);
        bArr[4] = (byte) (length & 255);
        System.arraycopy(bytes, 0, bArr, 5, length);
        bArr[5 + length] = (byte) (i3 & 255);
        bArr[length + 6] = (byte) (iAbs & 255);
        for (int i5 = 2; i5 < i4; i5++) {
            this.timeZoneCRC ^= bArr[i5];
        }
        writeCharaBle5(bArr);
        this.NOsectionTimeZone++;
    }

    public void sendUniversalFD(byte b2) {
        writeChara(new byte[]{-10, -3, b2});
    }

    public void sendUniversalFD_5(byte b2) {
        writeCharaBle5(new byte[]{-10, -1, -3, b2});
    }

    public void sendUniversalSerialNumber(int i2) {
        LogUtils.i("sendUniversalSerialNumber " + i2);
        writeChara(new byte[]{-10, (byte) i2});
    }

    public void sendUniversalSerialNumber_5(int i2) {
        writeCharaBle5(new byte[]{-10, (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i2 & 255)});
    }

    public void set4gReportInterval(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(215);
        writeCharaBle5(new byte[]{103, 1, (byte) ((65280 & i2) >> 8), (byte) (i2 & 255)});
    }

    public void setAppRingStatusToDevice(boolean z2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(64);
        byte[] bArr = new byte[4];
        bArr[0] = -47;
        bArr[1] = 10;
        bArr[2] = 1;
        if (z2) {
            bArr[3] = 1;
        } else {
            bArr[3] = 0;
        }
        writeChara(bArr);
    }

    public void setCallMuteMode(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = -47;
        bArr[1] = 18;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(96);
        if (z2) {
            bArr[2] = 2;
        } else {
            bArr[2] = 1;
        }
        writeChara(bArr);
    }

    public void setDeviceAlarmClock(AlarmClockInfo alarmClockInfo) {
        int i2;
        int alarmId = alarmClockInfo.getAlarmId();
        int alarmStatus = alarmClockInfo.getAlarmStatus();
        int alarmHour = alarmClockInfo.getAlarmHour();
        int alarmMinute = alarmClockInfo.getAlarmMinute();
        int alarmShakeCount = alarmClockInfo.getAlarmShakeCount();
        int alarmCycle = alarmClockInfo.getAlarmCycle();
        boolean zIsHasFunction_6 = DeviceMode.isHasFunction_6(256);
        switch (alarmId) {
            case 1:
                i2 = 11;
                break;
            case 2:
                i2 = 12;
                break;
            case 3:
                i2 = 13;
                break;
            case 4:
                i2 = 14;
                break;
            case 5:
                i2 = 15;
                break;
            case 6:
                i2 = 16;
                break;
            case 7:
                i2 = 17;
                break;
            case 8:
                i2 = 18;
                break;
            case 9:
                i2 = 190;
                break;
            case 10:
                i2 = 191;
                break;
            default:
                i2 = 1;
                break;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(i2);
        if (alarmId != 0) {
            if (zIsHasFunction_6) {
                byte[] bArr = new byte[10];
                bArr[0] = -85;
                bArr[1] = (byte) alarmCycle;
                bArr[2] = (byte) alarmHour;
                bArr[3] = (byte) alarmMinute;
                bArr[4] = 2;
                if (alarmCycle == 0) {
                    bArr[5] = 0;
                    bArr[6] = 0;
                } else {
                    bArr[5] = (byte) (alarmShakeCount & 255);
                    bArr[6] = 2;
                }
                bArr[7] = 0;
                bArr[8] = (byte) (alarmId & 255);
                if (alarmStatus == 1) {
                    bArr[9] = 1;
                } else {
                    bArr[9] = 0;
                }
                writeChara(bArr);
                return;
            }
            byte[] bArr2 = new byte[9];
            if (alarmStatus != 1) {
                bArr2[0] = -85;
                bArr2[1] = (byte) alarmCycle;
                bArr2[2] = (byte) alarmHour;
                bArr2[3] = (byte) alarmMinute;
                bArr2[4] = 0;
                bArr2[5] = 0;
                bArr2[6] = 0;
            } else if (alarmCycle == 0) {
                bArr2[0] = -85;
                bArr2[1] = (byte) alarmCycle;
                bArr2[2] = (byte) alarmHour;
                bArr2[3] = (byte) alarmMinute;
                bArr2[4] = 2;
                bArr2[5] = 0;
                bArr2[6] = 0;
            } else {
                bArr2[0] = -85;
                bArr2[1] = (byte) alarmCycle;
                bArr2[2] = (byte) alarmHour;
                bArr2[3] = (byte) alarmMinute;
                bArr2[4] = 2;
                bArr2[5] = (byte) (alarmShakeCount & 255);
                bArr2[6] = 2;
            }
            bArr2[7] = 0;
            bArr2[8] = (byte) (alarmId & 255);
            writeChara(bArr2);
        }
    }

    public void setDeviceLabelAlarmClock(List<LabelAlarmClockInfo> list) throws NumberFormatException {
        int length;
        byte[] bArr;
        int i2;
        WriteCommandToBLE writeCommandToBLE = this;
        List<LabelAlarmClockInfo> list2 = list;
        if (list2 == null || list.size() < 1) {
            LogUtils.i("闹钟个数为0");
            utenthrows();
            return;
        }
        boolean zIsHasFunction_8 = DeviceMode.isHasFunction_8(8192);
        int size = list.size();
        byte[] bArrCopyTwoArrays = null;
        int i3 = 0;
        while (i3 < size) {
            LabelAlarmClockInfo labelAlarmClockInfo = list2.get(i3);
            int alarmId = labelAlarmClockInfo.getAlarmId();
            int alarmStatus = labelAlarmClockInfo.getAlarmStatus();
            int alarmHour = labelAlarmClockInfo.getAlarmHour();
            int alarmMinute = labelAlarmClockInfo.getAlarmMinute();
            int alarmShakeCount = labelAlarmClockInfo.getAlarmShakeCount();
            int shakeLevel = labelAlarmClockInfo.getShakeLevel();
            int alarmCycle = labelAlarmClockInfo.getAlarmCycle();
            String strTimesStampToDate = CalendarUtils.timesStampToDate(labelAlarmClockInfo.getAlarmTimeStamp());
            String alarmDescription = labelAlarmClockInfo.getAlarmDescription();
            int alarmLabelType = labelAlarmClockInfo.getAlarmLabelType();
            int i4 = size;
            int i5 = Integer.parseInt(strTimesStampToDate.substring(0, 4));
            int i6 = Integer.parseInt(strTimesStampToDate.substring(4, 6));
            byte[] bArr2 = bArrCopyTwoArrays;
            int i7 = Integer.parseInt(strTimesStampToDate.substring(6, 8));
            int i8 = i3;
            int i9 = Integer.parseInt(strTimesStampToDate.substring(8, 10));
            int i10 = Integer.parseInt(strTimesStampToDate.substring(10, 12));
            int i11 = Integer.parseInt(strTimesStampToDate.substring(12, 14));
            byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().string2unicode(alarmDescription));
            if (bArrHexStringToBytes != null) {
                length = bArrHexStringToBytes.length;
                if (length > 100) {
                    length = 100;
                }
            } else {
                length = 0;
            }
            if (zIsHasFunction_8) {
                bArr = new byte[length + 16];
                bArr[14] = (byte) (alarmLabelType & 255);
                bArr[15] = (byte) (length & 255);
                i2 = 16;
            } else {
                bArr = new byte[length + 15];
                bArr[14] = (byte) (length & 255);
                i2 = 15;
            }
            boolean z2 = zIsHasFunction_8;
            bArr[0] = (byte) ((i5 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr[1] = (byte) (i5 & 255);
            bArr[2] = (byte) (i6 & 255);
            bArr[3] = (byte) (i7 & 255);
            bArr[4] = (byte) (i9 & 255);
            bArr[5] = (byte) (i10 & 255);
            bArr[6] = (byte) (i11 & 255);
            bArr[7] = (byte) (alarmId & 255);
            bArr[8] = (byte) (alarmStatus & 255);
            bArr[9] = (byte) (alarmHour & 255);
            bArr[10] = (byte) (alarmMinute & 255);
            bArr[11] = (byte) (alarmCycle & 255);
            bArr[12] = (byte) (alarmShakeCount & 255);
            bArr[13] = (byte) (shakeLevel & 255);
            if (bArrHexStringToBytes != null) {
                System.arraycopy(bArrHexStringToBytes, 0, bArr, i2, length);
            }
            bArrCopyTwoArrays = i8 == 0 ? bArr : ByteDataUtil.getInstance().copyTwoArrays(bArr2, bArr);
            i3 = i8 + 1;
            writeCommandToBLE = this;
            list2 = list;
            size = i4;
            zIsHasFunction_8 = z2;
        }
        writeCommandToBLE.alarmClockData = bArrCopyTwoArrays;
        writeCommandToBLE.NOsectionAlarmClock = 0;
        writeCommandToBLE.isAlarmClockSendFD = false;
        writeCommandToBLE.alarmClockCRC = 0;
        sendLabelAlarmClockSection();
    }

    public void setDeviceLanguage(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(79);
        writeChara(new byte[]{-81, -85, (byte) (i2 & 255)});
    }

    public void setDeviceTimeZone(List<TimeZoneInfo> list) {
        this.timeZoneInfos = list;
        if (list == null || list.size() <= 0) {
            deleteDeviceTimeZone();
            return;
        }
        this.timeZoneCRC = 0;
        this.timeZoneListSize = this.timeZoneInfos.size();
        this.NOsectionTimeZone = 0;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(74);
        sendTimeZoneSection();
    }

    public void setDeviceUnitHourFormat(UnitHourFormatInfo unitHourFormatInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(70);
        int deviceUnit = unitHourFormatInfo.getDeviceUnit();
        int deviceHourFormat = unitHourFormatInfo.getDeviceHourFormat();
        byte[] bArr = new byte[3];
        bArr[0] = -96;
        if (deviceUnit == -1) {
            deviceUnit = SPUtil.getInstance().getDeviceUnit();
        } else {
            SPUtil.getInstance().setDeviceUnit(deviceUnit);
        }
        if (deviceUnit == 0) {
            bArr[1] = 2;
        } else {
            bArr[1] = 1;
        }
        if (deviceHourFormat == -1) {
            deviceHourFormat = SPUtil.getInstance().getDeviceHourFormat();
        } else {
            SPUtil.getInstance().setDeviceHourFormat(deviceHourFormat);
        }
        if (deviceHourFormat == 0) {
            bArr[2] = 2;
        } else {
            bArr[2] = 1;
        }
        writeChara(bArr);
    }

    public void setDeviceWeather(SevenDayWeatherInfo sevenDayWeatherInfo) throws UnsupportedEncodingException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(72);
        if (DeviceMode.isHasFunction_1(8192) || DeviceMode.isHasFunction_6(128)) {
            LogUtils.i("支持7天天气, 同步7天的天气");
            utendo(sevenDayWeatherInfo);
        } else if (!DeviceMode.isHasFunction_1(2)) {
            LogUtils.i("不支持2天天气, 也不支持7天天气");
        } else {
            LogUtils.i("支持2天天气, 同步2天的天气");
            utenif(sevenDayWeatherInfo);
        }
    }

    public void setDeviceWeatherCityName(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(73);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        int iMin = Math.min(bytes.length, 100);
        byte[] bArr = new byte[iMin + 3];
        bArr[0] = -53;
        bArr[1] = -1;
        bArr[2] = (byte) (iMin & 255);
        for (int i2 = 0; i2 < iMin; i2++) {
            bArr[i2 + 3] = bytes[i2];
        }
        writeCharaBle5(bArr);
    }

    public void setDeviceWeatherSegments(int i2) {
        if (i2 == 0) {
            this.weatherNext24HoursCRC = (byte) 0;
            this.isSendweatherNext24HoursFD = false;
        }
        byte[] bArr = this.weatherNext24HoursData;
        if (bArr == null || bArr.length == 0) {
            return;
        }
        int i3 = this.maxCommunicationLength;
        int i4 = i3 - 2;
        int length = bArr.length;
        int i5 = length % i4;
        if (i2 < length / i4) {
            byte[] bArr2 = new byte[i3];
            bArr2[0] = -53;
            bArr2[1] = (byte) ((i2 + 1) & 255);
            int i6 = i2 * i4;
            for (int i7 = i6; i7 < i4 + i6; i7++) {
                bArr2[(i7 - i6) + 2] = bArr[i7];
            }
            for (int i8 = 1; i8 < i3; i8++) {
                this.weatherNext24HoursCRC = (byte) (this.weatherNext24HoursCRC ^ bArr2[i8]);
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isSendweatherNext24HoursFD) {
            utenif(this.weatherNext24HoursCRC);
            this.isSendweatherNext24HoursFD = false;
        } else if (i5 != 0) {
            int i9 = i5 + 2;
            byte[] bArr3 = new byte[i9];
            bArr3[0] = -53;
            bArr3[1] = (byte) ((i2 + 1) & 255);
            int i10 = i4 * i2;
            for (int i11 = i10; i11 < length; i11++) {
                bArr3[(i11 - i10) + 2] = bArr[i11];
            }
            for (int i12 = 1; i12 < i9; i12++) {
                this.weatherNext24HoursCRC = (byte) (this.weatherNext24HoursCRC ^ bArr3[i12]);
            }
            writeCharaBle5(bArr3);
        }
        this.isSendweatherNext24HoursFD = true;
    }

    public void setDeviceWidget(List<DeviceWidgetInfo> list) {
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        this.deviceWidgetList = list;
        this.customzieWidgettCRC = 0;
        this.NOsectionCustomzieWidget = 0;
        sendDeviceWidgetSection();
    }

    public void setElbpDataToDevice(boolean z2, String str, int i2, int i3) {
        byte[] bArrUtendo = utendo(z2, str, i2, i3);
        CommandTimeOutUtils.getInstance().setCommandTimeOut(170);
        writeCharaBle5(bArrUtendo);
    }

    public void setElbpSamplingInterval(int i2, boolean z2, int i3) {
        byte[] bArrUtendo = utendo(i2, z2, i3);
        CommandTimeOutUtils.getInstance().setCommandTimeOut(171);
        writeCharaBle5(bArrUtendo);
    }

    public void setHMaxHeartRate(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(152);
        writeCharaBle5(new byte[]{80, 5, (byte) (i2 & 255)});
    }

    public void setImageWatchFaceConfigRk(ImageWatchFaceConfigRk imageWatchFaceConfigRk) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SET_IMAGE_WATCH_FACE_CONFIG_RK);
        byte picIndex = (byte) imageWatchFaceConfigRk.getPicIndex();
        byte positionIndex = (byte) imageWatchFaceConfigRk.getPositionIndex();
        int fontColor = imageWatchFaceConfigRk.getFontColor();
        int previewFileSize = imageWatchFaceConfigRk.getPreviewFileSize();
        int bgFileSize = imageWatchFaceConfigRk.getBgFileSize();
        int crc32 = imageWatchFaceConfigRk.getCrc32();
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 11, picIndex, 0, positionIndex, (byte) ((fontColor >> 16) & 255), (byte) ((fontColor >> 8) & 255), (byte) (fontColor & 255), (byte) ((previewFileSize >> 24) & 255), (byte) ((previewFileSize >> 16) & 255), (byte) ((previewFileSize >> 8) & 255), (byte) (previewFileSize & 255), (byte) ((bgFileSize >> 24) & 255), (byte) ((bgFileSize >> 16) & 255), (byte) ((bgFileSize >> 8) & 255), (byte) (bgFileSize & 255), (byte) ((crc32 >> 24) & 255), (byte) ((crc32 >> 16) & 255), (byte) ((crc32 >> 8) & 255), (byte) (crc32 & 255)});
    }

    public void setIsSending(boolean z2) {
        this.isSending = z2;
    }

    public void setLocalWatchFace(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(146);
        writeChara(new byte[]{89, -5, (byte) (i2 & 255)});
    }

    public void setLoginElServerInfo(LoginElServerInfo loginElServerInfo) {
        this.mLoginElServerInfoData = utendo(loginElServerInfo);
        this.mLoginElServerInfoCrc = 0;
        this.isLoginElServerInfoFD = false;
        sendLoginElServerInfoSegments(0);
    }

    public void setMaxMinAlarmTemperature(boolean z2, float f2) {
        setMaxMinAlarmTemperature(z2, f2, 0.0f);
    }

    public void setMultiSportHeartRateWarning(MultiSportHeartRateWarningInfo multiSportHeartRateWarningInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(119);
        int highestSwitch = multiSportHeartRateWarningInfo.getHighestSwitch();
        int highestHeartRate = multiSportHeartRateWarningInfo.getHighestHeartRate();
        int lowestSwitch = multiSportHeartRateWarningInfo.getLowestSwitch();
        int lowestHeartRate = multiSportHeartRateWarningInfo.getLowestHeartRate();
        SPUtil.getInstance().setMultiSportHighestHeartRateSwitch(highestSwitch);
        SPUtil.getInstance().setMultiSportHighestHeartRate(highestHeartRate);
        SPUtil.getInstance().setMultiSportLowestHeartRateSwitch(lowestSwitch);
        SPUtil.getInstance().setMultiSportLowestHeartRate(lowestHeartRate);
        writeCharaBle5(new byte[]{80, 1, (byte) (highestHeartRate & 255), (byte) (highestSwitch & 255), (byte) (lowestHeartRate & 255), (byte) (lowestSwitch & 255)});
    }

    public void setMultiSportTarget(int i2, boolean z2, int i3) {
        if (i2 == 2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(120);
            byte[] bArr = new byte[7];
            bArr[0] = 80;
            bArr[1] = 2;
            bArr[2] = (byte) (((-16777216) & i3) >> 24);
            bArr[3] = (byte) ((16711680 & i3) >> 16);
            bArr[4] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr[5] = (byte) (i3 & 255);
            if (z2) {
                bArr[6] = 1;
            } else {
                bArr[6] = 0;
            }
            writeCharaBle5(bArr);
            return;
        }
        if (i2 != 3) {
            if (i2 != 4) {
                return;
            }
            CommandTimeOutUtils.getInstance().setCommandTimeOut(122);
            byte[] bArr2 = new byte[5];
            bArr2[0] = 80;
            bArr2[1] = 4;
            bArr2[2] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr2[3] = (byte) (i3 & 255);
            if (z2) {
                bArr2[4] = 1;
            } else {
                bArr2[4] = 0;
            }
            writeCharaBle5(bArr2);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(121);
        byte[] bArr3 = new byte[7];
        bArr3[0] = 80;
        bArr3[1] = 3;
        bArr3[2] = (byte) (((-16777216) & i3) >> 24);
        bArr3[3] = (byte) ((16711680 & i3) >> 16);
        bArr3[4] = (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr3[5] = (byte) (i3 & 255);
        if (z2) {
            bArr3[6] = 1;
        } else {
            bArr3[6] = 0;
        }
        writeCharaBle5(bArr3);
    }

    public void setReduceDialSpeed(boolean z2) {
        LogUtils.i("2603setReduceDialspeed =" + z2 + "," + WatchFaceUtil.REDUCE_SPEED_SEND_ONLINE_DIAL_TIME);
        this.isReduceDialSpeed = z2;
    }

    public void setSongInformationToDevice(MusicPushInfo musicPushInfo) throws InterruptedException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(62);
        utendo(musicPushInfo.getPlayStatus(), musicPushInfo.getPlayMode(), musicPushInfo.getVolumePer(), musicPushInfo.getTotalTime(), musicPushInfo.getCurrentTime(), musicPushInfo.getSongName(), musicPushInfo.getArtist());
    }

    public void setSportsModeList(List<SportsModeInfo> list) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(59);
        this.sportListCRC = 0;
        this.NOsectionSport = 0;
        this.displaySportsList = list;
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        sendSportManagementListsSection();
    }

    public void setTodayTarget(int i2, boolean z2, int i3) {
        byte[] bArr = new byte[5];
        if (i2 == 1) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(90);
            bArr = new byte[]{0, 1, 0, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        } else if (i2 == 2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(91);
            bArr = new byte[]{0, 2, 0, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        } else if (i2 == 3) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(92);
            bArr = new byte[]{0, 3, 0, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        } else if (i2 == 4) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(93);
            bArr = new byte[]{0, 4, 0, (byte) ((i3 & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((i3 & 16711680) >> 16), (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        } else if (i2 == 5) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(94);
            bArr = new byte[]{0, 5, 0, (byte) ((i3 & ViewCompat.MEASURED_STATE_MASK) >> 24), (byte) ((i3 & 16711680) >> 16), (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        } else if (i2 == 6) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(143);
            bArr = new byte[]{0, 6, 0, (byte) ((i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (i3 & 255)};
        }
        bArr[0] = 63;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        writeChara(bArr);
    }

    public void setWhichWatchFace(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(229);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 6, (byte) i2});
    }

    public void smsRemind(String str, String str2) throws UnsupportedEncodingException {
        SPUtil.getInstance().setSmsReceivedNameOrNumber(str);
        utendo(3, str2);
    }

    public void startCalibrate() {
        writeChara(new byte[]{-81, 0, 0, 0, 0});
    }

    public void startElbpPpgSampling() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(136);
        writeCharaBle5(utenpublic());
    }

    public void startRateCalibration() {
        writeChara(new byte[]{-5, 1});
    }

    public void startSports(int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(53);
        MultiSportsProcessing.getInstance().setNotifyStatus(false);
        this.mSportType = i2;
        this.mSportInterval = i3;
        byte[] bArr = new byte[4];
        bArr[0] = -3;
        bArr[1] = 17;
        bArr[2] = (byte) (i2 & 255);
        if (i3 <= 0 || i3 > 255) {
            bArr[3] = 1;
        } else {
            bArr[3] = (byte) (i3 & 255);
        }
        writeChara(bArr);
    }

    public void startTestBloodPressure(boolean z2) {
        byte[] bArr = new byte[2];
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(25);
            bArr[0] = -57;
            bArr[1] = 17;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(26);
            bArr[0] = -57;
            bArr[1] = 0;
        }
        writeChara(bArr);
    }

    public void startTestBodyFat(BodyFatPersonInfo bodyFatPersonInfo) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        boolean startTest = bodyFatPersonInfo.getStartTest();
        int bodyGender = bodyFatPersonInfo.getBodyGender();
        int bodyAge = bodyFatPersonInfo.getBodyAge();
        int bodyHeight = bodyFatPersonInfo.getBodyHeight();
        float bodyWeight = bodyFatPersonInfo.getBodyWeight();
        int i3 = (int) bodyWeight;
        SPUtil.getInstance().setStartTestBodyFatSwitch(startTest);
        SPUtil.getInstance().setPersonageGender(bodyGender);
        SPUtil.getInstance().setPersonageAge(bodyAge);
        SPUtil.getInstance().setPersonageHeight(bodyHeight);
        SPUtil.getInstance().setPersonageWeight(bodyWeight);
        byte[] bArr = new byte[8];
        bArr[0] = -23;
        bArr[1] = 17;
        bArr[2] = (byte) (bodyHeight & 255);
        bArr[3] = (byte) (bodyAge & 255);
        if (bodyGender == 0) {
            bArr[4] = 0;
        } else {
            bArr[4] = 1;
        }
        int i4 = i3 * 100;
        bArr[6] = (byte) (i4 & 255);
        bArr[5] = (byte) ((65280 & i4) >> 8);
        if (startTest) {
            bArr[7] = 17;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 41;
        } else {
            bArr[7] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 40;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void startTestBreathingRate(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArr = new byte[2];
        bArr[0] = 59;
        if (z2) {
            bArr[1] = 17;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 46;
        } else {
            bArr[1] = 0;
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 47;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeChara(bArr);
    }

    public void startTestEcg() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(44);
        writeCharaBle5(new byte[]{40, 16});
    }

    public void startTestElComplex(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArrUtenint = utenint(z2);
        if (z2) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 167;
        } else {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 168;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArrUtenint);
    }

    public void startTestElHRV(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArrUtennew = utennew(z2);
        if (z2) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 164;
        } else {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 165;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArrUtennew);
    }

    public void startTestElbs(boolean z2) {
        CommandTimeOutUtils commandTimeOutUtils;
        int i2;
        byte[] bArrUtentry = utentry(z2);
        if (z2) {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 161;
        } else {
            commandTimeOutUtils = CommandTimeOutUtils.getInstance();
            i2 = 162;
        }
        commandTimeOutUtils.setCommandTimeOut(i2);
        writeCharaBle5(bArrUtentry);
    }

    public void startTestHeartRate(boolean z2) {
        byte[] bArr = new byte[2];
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(22);
            bArr[0] = -27;
            bArr[1] = 17;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(23);
            bArr[0] = -27;
            bArr[1] = 0;
        }
        writeChara(bArr);
    }

    public void startTestMoodPressureFatigue(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(100);
        writeChara(utenfor(i2));
    }

    public void startTestOxygen(boolean z2) {
        byte[] bArr = new byte[2];
        bArr[0] = Constants.CMD_TYPE.CMD_REQUEST_SIGNATURE;
        if (z2) {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(28);
            bArr[1] = 17;
        } else {
            CommandTimeOutUtils.getInstance().setCommandTimeOut(29);
            bArr[1] = 0;
        }
        writeChara(bArr);
    }

    public void startTestTemperature() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(33);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_RES, 1});
    }

    public void startTurnWristCalibration() {
        writeChara(new byte[]{-4, 1});
    }

    public void stopDeviceVibration() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(8);
        writeChara(new byte[]{-85, 0, 0, 0, 0, 0, 0, 0});
    }

    public void stopElbpPpgSampling() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(137);
        writeCharaBle5(utenreturn());
    }

    public void stopOnlineDialData() {
        Handler handler = this.mHandler;
        if (handler != null) {
            this.textDataOnline = null;
            this.lastErase = 0;
            this.currErase = 0;
            handler.removeMessages(2);
        }
    }

    public void stopRateCalibration() {
        writeChara(new byte[]{-5, 0});
    }

    public void stopSendLocationData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(158);
        writeCharaBle5(new byte[]{-3, 1, 85});
    }

    public void stopSports(int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(54);
        MultiSportsProcessing.getInstance().setNotifyStatus(false);
        this.mSportType = i2;
        byte[] bArr = new byte[4];
        bArr[0] = -3;
        bArr[1] = 0;
        bArr[2] = (byte) (i2 & 255);
        int i3 = this.mSportInterval;
        if (i3 <= 0 || i3 > 255) {
            bArr[3] = 1;
        } else {
            bArr[3] = (byte) (i3 & 255);
        }
        writeChara(bArr);
    }

    public void stopTestMoodPressureFatigue(MoodPressureFatigueInfo moodPressureFatigueInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(101);
        writeChara(utendo(moodPressureFatigueInfo));
    }

    public void stopTurnWristCalibration() {
        writeChara(new byte[]{-4, 0});
    }

    public void switchWatchFace(WatchFaceSerialNumberInfo watchFaceSerialNumberInfo) throws NumberFormatException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(KeyType.SWITCH_WATCH_FACE_RK);
        byte displayIndex = (byte) watchFaceSerialNumberInfo.getDisplayIndex();
        int i2 = Integer.parseInt(watchFaceSerialNumberInfo.getBleID());
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 13, displayIndex, (byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
    }

    public void syncActivityTimeData() {
        syncActivityTimeData("");
    }

    public void syncBloodPressureData() {
        syncBloodPressureData("");
    }

    public void syncBodyFatData() {
        syncBodyFatData("");
    }

    public void syncBreathingRateData() throws NumberFormatException, ParseException {
        syncBreathingRateData("");
    }

    public void syncContactsToDevice(List<ContactsInfo> list) {
        if (list == null || list.size() < 1) {
            UteListenerManager.getInstance().onContactsSyncStatus(3);
            return;
        }
        CommandTimeOutUtils.getInstance().setCommandTimeOut(84);
        this.contactListsGlobal = list;
        int size = list.size();
        LogUtils.i("selectedSize =" + size);
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -6, (byte) (size & 255)});
    }

    public void syncCustomizeSMSSection() {
        LogUtils.i("customizeSMSSize =" + this.customizeSMSSize + ",NOsectionCustomizeSMS =" + this.NOsectionCustomizeSMS);
        int i2 = this.NOsectionCustomizeSMS;
        if (i2 >= this.customizeSMSSize) {
            writeCharaBle5(new byte[]{70, -3, 0});
            return;
        }
        byte[] bytes = this.customizeSMSInfos.get(i2).getSmsContent().getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        if (length > 90) {
            length = 90;
        }
        byte[] bArr = new byte[length + 5];
        bArr[0] = 70;
        bArr[1] = -6;
        bArr[2] = (byte) (this.customizeSMSSize & 255);
        bArr[3] = (byte) (this.NOsectionCustomizeSMS & 255);
        bArr[4] = (byte) (length & 255);
        System.arraycopy(bytes, 0, bArr, 5, length);
        writeCharaBle5(bArr);
        this.NOsectionCustomizeSMS++;
    }

    public void syncCyweeSwimData() throws NumberFormatException {
        syncCyweeSwimData("");
    }

    public void syncDeviceElbpMiddleData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(139);
        writeCharaBle5(utenstatic());
    }

    public void syncDeviceElbpPpgData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(138);
        writeCharaBle5(utenswitch());
    }

    public void syncDeviceParameters(DeviceParametersInfo deviceParametersInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(5);
        if (DeviceMode.isHasFunction_2(1)) {
            utenif(deviceParametersInfo);
        } else {
            utendo(deviceParametersInfo);
        }
    }

    public void syncDeviceTime() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(4);
        Calendar calendar = Calendar.getInstance();
        int i2 = calendar.get(1);
        byte b2 = (byte) (i2 & 255);
        writeChara(new byte[]{-93, (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), b2, (byte) ((calendar.get(2) + 1) & 255), (byte) (calendar.get(5) & 255), (byte) (calendar.get(11) & 255), (byte) (calendar.get(12) & 255), (byte) (calendar.get(13) & 255)});
    }

    public void syncEcgData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(43);
        writeCharaBle5(new byte[]{40, 11});
    }

    public void syncEcgHistorySamplingData() {
        syncEcgHistorySamplingData(null);
    }

    public void syncElbpActivationCode(byte[] bArr) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(183);
        writeCharaBle5(utendo(bArr));
    }

    public void syncElbsData(String str) {
        writeChara(utenfor(str));
    }

    public void syncGlucoseMetabolismSampledData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(207);
        writeCharaBle5(new byte[]{102, 3, -6});
    }

    public void syncHeartRateData() {
        syncHeartRateData("");
    }

    public void syncMoodActivationCode(byte[] bArr) {
        writeCharaBle5(utenif(bArr));
    }

    public void syncMoodPressureData() {
        syncMoodPressureData("");
    }

    public void syncMultipleSportsData() throws NumberFormatException {
        syncMultipleSportsData("");
    }

    public void syncOxygenData() throws NumberFormatException, ParseException {
        syncOxygenData("");
    }

    public void syncQuickReplySmsContent(List<ReplySmsInfo> list) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(113);
        if (list == null || list.size() <= 0) {
            utenif();
            return;
        }
        this.customizeSMSInfos = list;
        this.customizeSMSSize = list.size();
        this.NOsectionCustomizeSMS = 0;
        syncCustomizeSMSSection();
    }

    public void syncRestHeartRateData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(153);
        writeChara(new byte[]{-9, 8});
    }

    public void syncSevenDayWeatherSection(int i2) {
        SevenDayWeatherInfo sevenDayWeatherInfo = this.f24891a;
        if (sevenDayWeatherInfo != null) {
            if (i2 != 1) {
                if (i2 == 2) {
                    byte[] bArr = {-53, 3, (byte) (sevenDayWeatherInfo.getSixthDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getSixthDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getSixthDayTmpMin()) & 255), (byte) (sevenDayWeatherInfo.getSeventhDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getSeventhDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getSeventhDayTmpMin()) & 255)};
                    if (DeviceMode.isHasFunction_6(2048)) {
                        writeCharaBle5(bArr);
                        return;
                    } else {
                        writeChara(bArr);
                        return;
                    }
                }
                return;
            }
            byte[] bArr2 = {-53, 2, (byte) (sevenDayWeatherInfo.getSecondDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getSecondDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getSecondDayTmpMin()) & 255), (byte) (sevenDayWeatherInfo.getThirdDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getThirdDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getThirdDayTmpMin()) & 255), (byte) (sevenDayWeatherInfo.getFourthDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getFourthDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getFourthDayTmpMin()) & 255), (byte) (sevenDayWeatherInfo.getFifthDayWeatherCode() & 255), 0, (byte) (utenif(sevenDayWeatherInfo.getFifthDayTmpMax()) & 255), (byte) (utenif(sevenDayWeatherInfo.getFifthDayTmpMin()) & 255)};
            if (DeviceMode.isHasFunction_6(2048)) {
                writeCharaBle5(bArr2);
            } else {
                writeChara(bArr2);
            }
        }
    }

    public void syncSleepData() {
        syncSleepData("");
    }

    public void syncSosCallContacts(List<SosCallInfo> list) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(110);
        if (list == null || list.size() < 1) {
            utendo();
        } else {
            this.sosCallInfos = list;
            utenwhile();
        }
    }

    public void syncStandingTimeData() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(95);
        writeChara(new byte[]{63, 1, 2});
    }

    public void syncStepData() {
        syncStepData("");
    }

    public void syncTemperatureData() {
        syncTemperatureData("");
    }

    public boolean syncWatchFaceToDevice(String str) throws Throwable {
        byte[] binToByte;
        try {
            binToByte = Rgb.getInstance().readBinToByte(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            binToByte = null;
        }
        if (binToByte == null) {
            return false;
        }
        sendOnlineDialData(binToByte);
        return true;
    }

    public void syncWristDetectionSwitch(boolean z2) {
        byte[] bArr = new byte[2];
        bArr[0] = 61;
        if (z2) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        writeChara(bArr);
    }

    public void temperatureAutomaticTest(boolean z2, int i2) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(34);
        SPUtil.getInstance().setTemperatureAutomaticTestSwitch(z2);
        SPUtil.getInstance().setTemperatureAutomaticTestInterval(i2);
        byte[] bArr = new byte[5];
        bArr[0] = Constants.CMD_TYPE.CMD_OTA_RES;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((65280 & i2) >> 8);
        bArr[4] = (byte) (i2 & 255);
        writeChara(bArr);
    }

    public void temperatureTimePeriod(boolean z2, int i2, int i3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(35);
        SPUtil.getInstance().setTemperatureTimePeriodSwitch(z2);
        SPUtil.getInstance().setTemperatureAutomaticStartTime(i2);
        SPUtil.getInstance().setTemperatureAutomaticEndTime(i3);
        byte[] bArr = new byte[7];
        bArr[0] = Constants.CMD_TYPE.CMD_OTA_RES;
        bArr[1] = 4;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((i2 / 60) & 255);
        bArr[4] = (byte) ((i2 % 60) & 255);
        bArr[5] = (byte) ((i3 / 60) & 255);
        bArr[6] = (byte) ((i3 % 60) & 255);
        writeChara(bArr);
    }

    public void universalInterfaceOnSegments(int i2) {
        if (i2 == 0) {
            if (this.universalInterfaceType == 1) {
                setIsSending(false);
            } else {
                setIsSending(true);
            }
            this.isSendUniversalFD = false;
        }
        byte[] bArr = this.universalInterfaceData;
        int length = bArr.length;
        int i3 = length % 18;
        if (i2 < length / 18) {
            byte[] bArr2 = new byte[20];
            bArr2[0] = -10;
            byte b2 = (byte) (i2 & 255);
            bArr2[1] = b2;
            this.universalInterfaceCRC = (byte) (b2 ^ this.universalInterfaceCRC);
            int i4 = i2 * 18;
            for (int i5 = i4; i5 < 18 + i4; i5++) {
                byte b3 = bArr[i5];
                bArr2[(i5 - i4) + 2] = b3;
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ b3);
            }
            writeChara(bArr2);
            if (i3 != 0) {
                return;
            }
        } else if (this.isSendUniversalFD) {
            sendUniversalFD(this.universalInterfaceCRC);
            this.isSendUniversalFD = false;
        } else if (i3 != 0) {
            byte[] bArr3 = new byte[i3 + 2];
            bArr3[0] = -10;
            byte b4 = (byte) (i2 & 255);
            bArr3[1] = b4;
            this.universalInterfaceCRC = (byte) (b4 ^ this.universalInterfaceCRC);
            int i6 = i2 * 18;
            for (int i7 = i6; i7 < length; i7++) {
                byte b5 = bArr[i7];
                bArr3[(i7 - i6) + 2] = b5;
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ b5);
            }
            writeChara(bArr3);
        }
        this.isSendUniversalFD = true;
    }

    public void universalInterfaceOnSegments_5(int i2) {
        String str;
        if (i2 == 0) {
            if (this.universalInterfaceType == 1) {
                setIsSending(false);
                str = "ble到sdk流程开始";
            } else {
                setIsSending(true);
                str = "sdk到ble流程开始";
            }
            LogUtils.d(str);
            this.isSendUniversalFD = false;
        }
        byte[] bArr = this.universalInterfaceData;
        int i3 = this.maxCommunicationLength;
        int i4 = i3 - 3;
        int length = bArr.length;
        int i5 = length % i4;
        if (i2 < length / i4) {
            byte[] bArr2 = new byte[i3];
            bArr2[0] = -10;
            byte b2 = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr2[1] = b2;
            byte b3 = (byte) (i2 & 255);
            bArr2[2] = b3;
            this.universalInterfaceCRC = (byte) (((byte) (b2 ^ this.universalInterfaceCRC)) ^ b3);
            int i6 = i2 * i4;
            for (int i7 = i6; i7 < i4 + i6; i7++) {
                byte b4 = bArr[i7];
                bArr2[(i7 - i6) + 3] = b4;
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ b4);
            }
            writeCharaBle5(bArr2);
            if (i5 != 0) {
                return;
            }
        } else if (this.isSendUniversalFD) {
            sendUniversalFD_5(this.universalInterfaceCRC);
            this.isSendUniversalFD = false;
        } else if (i5 != 0) {
            byte[] bArr3 = new byte[i5 + 3];
            bArr3[0] = -10;
            byte b5 = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
            bArr3[1] = b5;
            byte b6 = (byte) (i2 & 255);
            bArr3[2] = b6;
            this.universalInterfaceCRC = (byte) (((byte) (b5 ^ this.universalInterfaceCRC)) ^ b6);
            int i8 = i4 * i2;
            for (int i9 = i8; i9 < length; i9++) {
                byte b7 = bArr[i9];
                bArr3[(i9 - i8) + 3] = b7;
                this.universalInterfaceCRC = (byte) (this.universalInterfaceCRC ^ b7);
            }
            writeCharaBle5(bArr3);
        }
        this.isSendUniversalFD = true;
    }

    public final byte[] utenbreak() {
        return new byte[]{92, -86};
    }

    public final byte[] utenbyte() {
        return new byte[]{68, 12};
    }

    public final byte[] utencase() {
        return new byte[]{68, 13};
    }

    public final byte[] utencatch() {
        return new byte[]{85, 6};
    }

    public final byte[] utenchar() {
        return new byte[]{68, -86};
    }

    public final byte[] utenclass() {
        return new byte[]{85, -86};
    }

    public final byte[] utenconst() {
        return new byte[]{91, -86};
    }

    public final void utendouble() {
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -83, -3, this.sosCallCRC});
    }

    public final byte[] utenelse() {
        return new byte[]{68, 14, 17};
    }

    public final void utenfinal() {
        writeChara(new byte[]{-59, -3});
    }

    public final void utenfloat() {
        writeChara(new byte[]{-58, -3});
    }

    public final byte[] utenfor() {
        return new byte[]{68, Constants.CMD_TYPE.CMD_GET_FIRMWARE_VERSION_RES, 1};
    }

    public final byte[] utengoto() {
        return new byte[]{68, 15};
    }

    public final void utenimport() {
        writeCharaBle5(new byte[]{-3, 72, -3, (byte) (this.sportListCRC & 255)});
    }

    public final byte[] utenint() {
        return new byte[]{68, 32, 0};
    }

    public final byte[] utenlong() {
        return new byte[]{68, 32, 1};
    }

    public final byte[] utennative() {
        return new byte[]{85, 4, -1};
    }

    public final byte[] utennew() {
        return new byte[]{85, 13};
    }

    public final byte[] utenpublic() {
        return new byte[]{85, 17, 0};
    }

    public final byte[] utenreturn() {
        return new byte[]{85, 0, 1};
    }

    public final void utenshort() {
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -4, -3});
    }

    public final byte[] utenstatic() {
        return new byte[]{85, 3, -6};
    }

    public final void utensuper() {
        writeCharaBle5(new byte[]{81, -3, (byte) (this.alarmClockCRC & 255)});
    }

    public final byte[] utenswitch() {
        return new byte[]{85, 2, -6};
    }

    public final void utenthis() {
        if (this.mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("HandlerThread");
            handlerThread.start();
            this.mHandler = new utendo(handlerThread.getLooper());
        }
    }

    public final void utenthrow() {
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_VERIFY_RES, 3, 0});
    }

    public final void utenthrows() {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(118);
        writeCharaBle5(new byte[]{81, -4});
    }

    public final byte[] utentry() {
        return new byte[]{85, 14, 17};
    }

    public final byte[] utenvoid() {
        return new byte[]{93, -86};
    }

    public final void utenwhile() {
        this.sosCallCRC = (byte) 0;
        this.nOsectionSosCall = 0;
        this.isSendSosCallFinish = false;
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        sendSectionSosCall();
    }

    public void washHandsRemind(WashHandsRemindInfo washHandsRemindInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(68);
        int remindSwitch = washHandsRemindInfo.getRemindSwitch();
        int fromTimeHour = washHandsRemindInfo.getFromTimeHour();
        int fromTimeMinute = washHandsRemindInfo.getFromTimeMinute();
        int toTimeHour = washHandsRemindInfo.getToTimeHour();
        int toTimeMinute = washHandsRemindInfo.getToTimeMinute();
        int remindInterval = washHandsRemindInfo.getRemindInterval();
        int lunchBreakNoRemind = washHandsRemindInfo.getLunchBreakNoRemind();
        SPUtil.getInstance().setWashHandsRemindSwitch(remindSwitch);
        SPUtil.getInstance().setWashHandsRemindFromTimeHour(fromTimeHour);
        SPUtil.getInstance().setWashHandsRemindFromTimeMinute(fromTimeMinute);
        SPUtil.getInstance().setWashHandsRemindToTimeHour(toTimeHour);
        SPUtil.getInstance().setWashHandsRemindToTimeMinute(toTimeMinute);
        SPUtil.getInstance().setWashHandsRemindInterval(remindInterval);
        SPUtil.getInstance().setWashHandsLunchBreakNoRemind(lunchBreakNoRemind);
        byte[] bArr = new byte[12];
        if (DeviceMode.isHasFunction_6(65536)) {
            bArr = new byte[16];
            int noRemindFromTimeHour = washHandsRemindInfo.getNoRemindFromTimeHour();
            int noRemindFromTimeMinute = washHandsRemindInfo.getNoRemindFromTimeMinute();
            int noRemindToTimeHour = washHandsRemindInfo.getNoRemindToTimeHour();
            int noRemindToTimeMinute = washHandsRemindInfo.getNoRemindToTimeMinute();
            SPUtil.getInstance().setWashHandsNoRemindFromTimeHour(noRemindFromTimeHour);
            SPUtil.getInstance().setWashHandsNoRemindFromTimeMinute(noRemindFromTimeMinute);
            SPUtil.getInstance().setWashHandsNoRemindToTimeHour(noRemindToTimeHour);
            SPUtil.getInstance().setWashHandsNoRemindToTimeMinute(noRemindToTimeMinute);
            bArr[12] = (byte) (noRemindFromTimeHour & 255);
            bArr[13] = (byte) (noRemindFromTimeMinute & 255);
            bArr[14] = (byte) (noRemindToTimeHour & 255);
            bArr[15] = (byte) (noRemindToTimeMinute & 255);
        }
        bArr[0] = 65;
        if (remindSwitch == 1) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        bArr[2] = (byte) (remindInterval & 255);
        bArr[3] = 2;
        bArr[4] = 3;
        bArr[5] = 1;
        bArr[6] = 0;
        bArr[7] = (byte) (fromTimeHour & 255);
        bArr[8] = (byte) (fromTimeMinute & 255);
        bArr[9] = (byte) (toTimeHour & 255);
        bArr[10] = (byte) (toTimeMinute & 255);
        if (lunchBreakNoRemind == 1) {
            bArr[11] = 1;
        } else {
            bArr[11] = 0;
        }
        writeChara(bArr);
    }

    public void writeChara(byte[] bArr) {
        if (UteBleClient.getUteBleClient().isConnected()) {
            NotifyUtils.getInstance().writeChara(bArr);
        }
    }

    public void writeCharaBle5(byte[] bArr) {
        if (UteBleClient.getUteBleClient().isConnected()) {
            NotifyUtils.getInstance().writeCharaBle5(bArr);
        }
    }

    public void writeCharaBleFrk(byte[] bArr) {
        NotifyUtils.getInstance().writeCharaBleUUid(bArr, UUIDUtils.UUID_FRK_WRITE_DATA);
    }

    public void writeSuloseCommand60(byte[] bArr) {
        utenfor(bArr);
    }

    public void writeSuloseCommand61(byte[] bArr) {
        utenint(bArr);
    }

    public void deleteDeviceWatchFaceOnline(List<Integer> list) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(159);
        byte[] bArr = new byte[list.size() + 3];
        bArr[0] = Constants.CMD_TYPE.CMD_OTA_VERIFY_RES;
        bArr[1] = 12;
        bArr[2] = (byte) list.size();
        for (int i2 = 0; i2 < list.size(); i2++) {
            bArr[i2 + 3] = (byte) list.get(i2).intValue();
        }
        writeChara(bArr);
    }

    public void setDeviceWeather(WeatherInfo weatherInfo) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(72);
        this.NOsectionForWeather = 0;
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        this.weatherNext24HoursData = WeatherUtils.getInstance().getDeviceWeatherData(weatherInfo);
        setDeviceWeatherSegments(this.NOsectionForWeather);
    }

    public void setMaxMinAlarmTemperature(boolean z2, float f2, float f3) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(36);
        SPUtil.getInstance().setMaxMinTemperatureAlarmSwitch(z2);
        SPUtil.getInstance().setMaxTemperatureAlarm(f2);
        SPUtil.getInstance().setMinTemperatureAlarm(f3);
        int i2 = (int) (f2 * 100.0f);
        int i3 = (int) (f3 * 100.0f);
        byte[] bArr = new byte[7];
        bArr[0] = Constants.CMD_TYPE.CMD_OTA_RES;
        bArr[1] = 6;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[4] = (byte) (i2 & 255);
        bArr[5] = (byte) ((65280 & i3) >> 8);
        bArr[6] = (byte) (i3 & 255);
        writeChara(bArr);
    }

    public void syncActivityTimeData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(144);
        StandingTimeProcessing.getInstance().resetCrc();
        writeChara(CommandUtil.getInstance().getCommandType(13, str));
    }

    public void syncBloodPressureData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(24);
        BloodPressureDataProcessing.getInstance().clearBloodPressureList();
        writeChara(CommandUtil.getInstance().getCommandType(4, str));
    }

    public void syncBodyFatData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(39);
        writeChara(CommandUtil.getInstance().getCommandType(10, str));
    }

    public void syncBreathingRateData(String str) throws NumberFormatException, ParseException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(45);
        BreathingRateDataProcessing.getInstance().clearBreatheInfoList();
        byte[] commandType = CommandUtil.getInstance().getCommandType(11, str);
        if (DeviceMode.isHasFunction_9(4096)) {
            writeCharaBle5(commandType);
        } else {
            writeChara(commandType);
        }
    }

    public void syncCyweeSwimData(String str) throws NumberFormatException {
        this.syncSportTime = str;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(192);
        byte[] bArr = new byte[9];
        bArr[0] = 74;
        bArr[1] = -6;
        if (TextUtils.isEmpty(str) || str.length() != 12) {
            bArr[2] = 0;
            bArr[3] = 0;
            bArr[4] = 0;
            bArr[5] = 0;
            bArr[6] = 0;
            bArr[7] = 0;
            bArr[8] = 0;
        } else {
            int i2 = Integer.parseInt(str.substring(0, 4));
            int i3 = Integer.parseInt(str.substring(4, 6));
            int i4 = Integer.parseInt(str.substring(6, 8));
            int i5 = Integer.parseInt(str.substring(8, 10));
            int i6 = Integer.parseInt(str.substring(10, 12));
            bArr[2] = (byte) ((65280 & i2) >> 8);
            bArr[3] = (byte) (i2 & 255);
            bArr[4] = (byte) (i3 & 255);
            bArr[5] = (byte) (i4 & 255);
            bArr[6] = (byte) (i5 & 255);
            bArr[7] = (byte) (i6 & 255);
            bArr[8] = 0;
        }
        writeCharaBle5(bArr);
    }

    public void syncEcgHistorySamplingData(String str) {
        byte[] bArr;
        LogUtils.i("ecgTime=" + str);
        CommandTimeOutUtils.getInstance().setCommandTimeOut(176);
        if (TextUtils.isEmpty(str)) {
            bArr = new byte[]{40, 12};
        } else {
            byte[] bArrTime2Byte = EcgUtil.getInstance().time2Byte(str);
            byte[] bArr2 = new byte[8];
            bArr2[0] = 40;
            bArr2[1] = 12;
            for (int i2 = 0; i2 < bArrTime2Byte.length; i2++) {
                bArr2[i2 + 2] = bArrTime2Byte[i2];
            }
            bArr = bArr2;
        }
        writeCharaBle5(bArr);
    }

    public void syncHeartRateData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(19);
        HeartRateDataProcessing.getInstance().clearHeartRateList();
        writeChara(CommandUtil.getInstance().getCommandType(3, str));
    }

    public void syncMoodPressureData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(99);
        writeChara(utendo(str));
    }

    public void syncMultipleSportsData(String str) throws NumberFormatException {
        if (DeviceMode.isHasFunction_9(512) && !this.swimDataSyncFinish) {
            syncCyweeSwimData(str);
            return;
        }
        this.swimDataSyncFinish = false;
        CommandTimeOutUtils.getInstance().setCommandTimeOut(51);
        writeChara(CommandUtil.getInstance().getCommandType(9, str));
    }

    public void syncOxygenData(String str) throws NumberFormatException, ParseException {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(27);
        OxygenDataProcessing.getInstance().clearOxygenInfoList();
        byte[] commandType = CommandUtil.getInstance().getCommandType(12, str);
        if (DeviceMode.isHasFunction_9(4096)) {
            writeCharaBle5(commandType);
        } else {
            writeChara(commandType);
        }
    }

    public void syncSleepData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(7);
        if (DeviceMode.isHasFunction_8(32)) {
            writeChara(CommandUtil.getInstance().getCommandType(15, ""));
        } else {
            writeChara(CommandUtil.getInstance().getCommandType(2, str));
        }
    }

    public void syncStepData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(6);
        if (DeviceMode.isHasFunction_8(32)) {
            writeCharaBle5(CommandUtil.getInstance().getCommandType(14, ""));
        } else {
            writeChara(CommandUtil.getInstance().getCommandType(1, str));
        }
    }

    public void syncTemperatureData(String str) {
        CommandTimeOutUtils.getInstance().setCommandTimeOut(32);
        writeChara(new byte[]{Constants.CMD_TYPE.CMD_OTA_RES, -6});
    }

    public final void utenbyte(int i2) {
        LogUtils.i("sendLoginElServerInfoFD mAppCrc =" + (i2 & 255));
        writeCharaBle5(new byte[]{85, 15, -3, (byte) i2});
        this.mLoginElServerInfoData = null;
    }

    public final byte[] utenfor(int i2) {
        byte[] bArr = new byte[3];
        bArr[0] = 68;
        bArr[1] = 17;
        if (i2 == 0) {
            bArr[2] = 0;
        } else if (i2 == 1) {
            bArr[2] = 1;
        } else if (i2 == 2) {
            bArr[2] = 2;
        } else {
            bArr[2] = 0;
        }
        return bArr;
    }

    public final void utenif() {
        writeCharaBle5(new byte[]{70, -5});
    }

    public final void utenint(int i2) {
        LogUtils.i("sendBloodSugarLevelFD mAppCrc =" + (i2 & 255));
        writeCharaBle5(new byte[]{102, 6, -3, (byte) i2});
        this.mBloodSugarLevelData = null;
    }

    public final void utennew(int i2) {
        LogUtils.i("sendBloodSugarValueFD mAppCrc =" + (i2 & 255));
        writeCharaBle5(new byte[]{102, 5, -3, (byte) i2});
        this.mBloodSugarValueData = null;
    }

    public final void utentry(int i2) {
        LogUtils.i("setLanguage mAppCrc =" + (i2 & 255));
        writeCharaBle5(new byte[]{85, 7, -3, (byte) i2});
        this.f24892b = null;
    }

    public final void utendo(byte b2) {
        writeCharaBle5(new byte[]{62, 1, -3, b2});
    }

    public final byte[] utenfor(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = 85;
        bArr[1] = 2;
        if (z2) {
            bArr[2] = -3;
        } else {
            bArr[2] = -1;
        }
        return bArr;
    }

    public final int utenif(int i2) {
        return i2 < 0 ? Math.abs(i2) | 128 : i2;
    }

    public final byte[] utenint(boolean z2) {
        byte[] bArr = new byte[2];
        bArr[0] = 93;
        if (z2) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        return bArr;
    }

    public final byte[] utennew(boolean z2) {
        byte[] bArr = new byte[2];
        bArr[0] = 92;
        if (z2) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        return bArr;
    }

    public final byte[] utentry(boolean z2) {
        byte[] bArr = new byte[2];
        bArr[0] = 91;
        if (z2) {
            bArr[1] = 1;
        } else {
            bArr[1] = 0;
        }
        return bArr;
    }

    public final void utendo(int i2, SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        int sportsDurationTime = sportsModeControlInfo.getSportsDurationTime();
        float sportsDistance = sportsModeControlInfo.getSportsDistance();
        int sportsCalories = sportsModeControlInfo.getSportsCalories();
        float sportsPace = sportsModeControlInfo.getSportsPace();
        boolean zIsGPSModes = sportsModeControlInfo.isGPSModes();
        int i3 = Integer.parseInt(MultiSportsModesUtils.getInstance().formatSimpleData(sportsPace).split("\\.")[0]);
        int i4 = (int) (((Integer.parseInt(r3.split("\\.")[1]) / 100.0f) * 60.0f) % 60.0f);
        if ((i3 * 60) + i4 > 5999.0f) {
            i4 = 0;
            i3 = 0;
        }
        int i5 = sportsDurationTime / 3600;
        int i6 = sportsDurationTime % 3600;
        int i7 = i6 / 60;
        int i8 = i6 % 60;
        String str = MultiSportsModesUtils.getInstance().formatSimpleData(sportsDistance / 1000.0f) + "";
        int iStrToInt = MultiSportsModesUtils.getInstance().StrToInt(str.split("\\.")[0]);
        int iStrToInt2 = MultiSportsModesUtils.getInstance().StrToInt(str.split("\\.")[1]);
        byte[] bArr = zIsGPSModes ? new byte[13] : new byte[7];
        bArr[0] = -3;
        bArr[1] = (byte) i2;
        bArr[2] = (byte) (this.mSportType & 255);
        int i9 = this.mSportInterval;
        if (i9 <= 0 || i9 > 255) {
            bArr[3] = 1;
        } else {
            bArr[3] = (byte) (i9 & 255);
        }
        bArr[4] = (byte) (i5 & 255);
        bArr[5] = (byte) (i7 & 255);
        bArr[6] = (byte) (i8 & 255);
        if (zIsGPSModes) {
            bArr[7] = (byte) ((65280 & sportsCalories) >> 8);
            bArr[8] = (byte) (sportsCalories & 255);
            bArr[9] = (byte) (iStrToInt & 255);
            bArr[10] = (byte) (iStrToInt2 & 255);
            bArr[11] = (byte) (i3 & 255);
            bArr[12] = (byte) (i4 & 255);
        }
        writeChara(bArr);
    }

    public final byte[] utenfor(String str) throws NumberFormatException {
        byte[] bArr;
        byte[] bArrUtenif = utenif(str);
        if (bArrUtenif == null || bArrUtenif.length != 6) {
            bArr = new byte[2];
        } else {
            bArr = new byte[8];
            System.arraycopy(bArrUtenif, 0, bArr, 2, bArrUtenif.length);
        }
        bArr[0] = 91;
        bArr[1] = -6;
        return bArr;
    }

    public final byte[] utenif(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 3];
        bArr2[0] = 68;
        bArr2[1] = 14;
        bArr2[2] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr2, 3, bArr.length);
        return bArr2;
    }

    public final void utenint(byte[] bArr) {
        if (UteBleClient.getUteBleClient().isConnected()) {
            NotifyUtils.getInstance().writeCharaBleUUid(bArr, UUIDUtils.ONLY_WRITE_UUID_61);
        }
    }

    public final void utendo() {
        writeCharaBle5(new byte[]{Constants.CMD_TYPE.CMD_NOTIFY_STATUS_ACK, -82});
    }

    public final void utenfor(byte[] bArr) {
        if (UteBleClient.getUteBleClient().isConnected()) {
            NotifyUtils.getInstance().writeCharaBleUUid(bArr, UUIDUtils.ONLY_WRITE_UUID_60);
        }
    }

    public final byte[] utenif(String str) throws NumberFormatException {
        byte[] bArr = new byte[0];
        if (TextUtils.isEmpty(str) || str.length() != 12 || !DeviceMode.isHasFunction_4(8192)) {
            return bArr;
        }
        int i2 = Integer.parseInt(str.substring(0, 4));
        return new byte[]{(byte) ((65280 & i2) >> 8), (byte) (i2 & 255), (byte) (Integer.parseInt(str.substring(4, 6)) & 255), (byte) (Integer.parseInt(str.substring(6, 8)) & 255), (byte) (Integer.parseInt(str.substring(8, 10)) & 255), (byte) (Integer.parseInt(str.substring(10, 12)) & 255)};
    }

    public final void utendo(int i2) {
        LogUtils.i("SendMessage mAppCrc =" + (i2 & 255));
        writeCharaBle5(new byte[]{40, 18, -3, (byte) i2});
        this.mEcgParsingSuccessData = null;
    }

    public final byte[] utenif(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = 85;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = -3;
        } else {
            bArr[2] = -1;
        }
        return bArr;
    }

    public final byte[] utendo(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 3];
        bArr2[0] = 85;
        bArr2[1] = 14;
        bArr2[2] = (byte) bArr.length;
        System.arraycopy(bArr, 0, bArr2, 3, bArr.length);
        return bArr2;
    }

    public final void utenif(byte b2) {
        writeCharaBle5(new byte[]{-53, -3, b2});
    }

    public final byte[] utendo(boolean z2, int i2) {
        byte[] bArr = new byte[5];
        bArr[0] = 68;
        bArr[1] = 3;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((65280 & i2) >> 8);
        bArr[4] = (byte) (i2 & 255);
        return bArr;
    }

    public final void utenif(DeviceParametersInfo deviceParametersInfo) {
        int lowestRateSwitch;
        int celsiusFahrenheitValue;
        int onlySupportEnCn;
        int deviceLostSwitch;
        if (deviceParametersInfo == null) {
            return;
        }
        byte[] bArr = new byte[19];
        for (int i2 = 2; i2 < 19; i2++) {
            bArr[i2] = -1;
        }
        bArr[0] = -87;
        bArr[1] = 0;
        int bodyHeight = deviceParametersInfo.getBodyHeight();
        if (bodyHeight != -1) {
            int i3 = (int) (bodyHeight * 0.418d);
            SPUtil.getInstance().setPersonageHeight(bodyHeight);
            SPUtil.getInstance().setPersonageStepLength(i3);
            if (!DeviceMode.isHasFunction_2(4096)) {
                bodyHeight = i3;
            }
            bArr[2] = (byte) (bodyHeight & 255);
        }
        float bodyWeight = deviceParametersInfo.getBodyWeight();
        if (bodyWeight != -1.0f) {
            SPUtil.getInstance().setPersonageWeight(bodyWeight);
            if (DeviceMode.isHasFunction_3(64) || DeviceMode.isHasFunction_9(4)) {
                bArr[3] = (byte) (RoundingUtils.getInstance().roundingToInt((bodyWeight - ((int) bodyWeight)) * 100.0f) & 255);
            } else {
                bArr[3] = 0;
            }
            bArr[4] = (byte) (((int) bodyWeight) & 255);
        }
        int offScreenTime = deviceParametersInfo.getOffScreenTime();
        if (offScreenTime != -1) {
            SPUtil.getInstance().setOffScreenTime(offScreenTime);
            bArr[5] = (byte) (offScreenTime & 255);
        }
        int stepTask = deviceParametersInfo.getStepTask();
        if (stepTask != -1) {
            SPUtil.getInstance().setStepTask(stepTask);
            bArr[9] = (byte) (stepTask & 255);
            bArr[8] = (byte) ((65280 & stepTask) >> 8);
            bArr[7] = (byte) ((16711680 & stepTask) >> 16);
            bArr[6] = (byte) ((stepTask & ViewCompat.MEASURED_STATE_MASK) >> 24);
        }
        int raiseHandBrightScreenSwitch = deviceParametersInfo.getRaiseHandBrightScreenSwitch();
        if (raiseHandBrightScreenSwitch != -1) {
            SPUtil.getInstance().setRaiseHandBrightScreenSwitch(raiseHandBrightScreenSwitch);
            if (raiseHandBrightScreenSwitch == 1) {
                bArr[10] = 1;
            } else if (raiseHandBrightScreenSwitch == 0) {
                bArr[10] = 0;
            }
        }
        int highestRateSwitch = deviceParametersInfo.getHighestRateSwitch();
        if (highestRateSwitch != -1) {
            SPUtil.getInstance().setHighestRateSwitch(highestRateSwitch);
            if (highestRateSwitch == 1) {
                bArr[11] = (byte) deviceParametersInfo.getHighestRate();
                SPUtil.getInstance().setHighestRate(deviceParametersInfo.getHighestRate());
            } else if (highestRateSwitch == 0) {
                bArr[11] = -2;
            }
        }
        bArr[12] = 0;
        int bodyAge = deviceParametersInfo.getBodyAge();
        if (bodyAge != -1) {
            SPUtil.getInstance().setPersonageAge(bodyAge);
            bArr[13] = (byte) (bodyAge & 255);
        }
        int bodyGender = deviceParametersInfo.getBodyGender();
        if (bodyGender != -1) {
            SPUtil.getInstance().setPersonageGender(bodyGender);
            if (bodyGender == 1) {
                bArr[14] = 1;
            } else if (bodyGender == 0) {
                bArr[14] = 2;
            }
        }
        if (DeviceMode.isHasFunction_2(4) && (deviceLostSwitch = deviceParametersInfo.getDeviceLostSwitch()) != -1) {
            SPUtil.getInstance().setDeviceLostSwitch(deviceLostSwitch);
            if (deviceLostSwitch == 1) {
                bArr[15] = 1;
            } else if (deviceLostSwitch == 0) {
                bArr[15] = 0;
            }
        }
        if (DeviceMode.isHasFunction_3(1024) && (onlySupportEnCn = deviceParametersInfo.getOnlySupportEnCn()) != -1) {
            SPUtil.getInstance().setOnlySupportEnCn(onlySupportEnCn);
            if (onlySupportEnCn == 1) {
                bArr[16] = 2;
            } else if (onlySupportEnCn == 0) {
                bArr[16] = 1;
            }
        }
        if (DeviceMode.isHasFunction_3(4096) && (celsiusFahrenheitValue = deviceParametersInfo.getCelsiusFahrenheitValue()) != -1) {
            SPUtil.getInstance().setCelsiusFahrenheitValue(celsiusFahrenheitValue);
            if (celsiusFahrenheitValue == 1) {
                bArr[17] = 1;
            } else if (celsiusFahrenheitValue == 0) {
                bArr[17] = 2;
            }
        }
        if (DeviceMode.isHasFunction_4(2048) && (lowestRateSwitch = deviceParametersInfo.getLowestRateSwitch()) != -1) {
            SPUtil.getInstance().setLowestRateSwitch(lowestRateSwitch);
            if (lowestRateSwitch == 1) {
                bArr[18] = (byte) deviceParametersInfo.getLowestRate();
                SPUtil.getInstance().setLowestRate(deviceParametersInfo.getLowestRate());
            } else if (lowestRateSwitch == 0) {
                bArr[18] = 0;
            }
        }
        writeChara(bArr);
    }

    public final byte[] utendo(MoodPressureFatigueInfo moodPressureFatigueInfo) {
        byte[] bArr = new byte[6];
        bArr[0] = 68;
        bArr[1] = 0;
        if (moodPressureFatigueInfo == null) {
            bArr[2] = -1;
            bArr[3] = -1;
            bArr[4] = -1;
            bArr[5] = -9;
        } else {
            if (moodPressureFatigueInfo.getMoodValue() == -1) {
                bArr[2] = -1;
            } else {
                bArr[2] = (byte) moodPressureFatigueInfo.getMoodValue();
            }
            if (moodPressureFatigueInfo.getPressureValue() == -1) {
                bArr[3] = -1;
            } else {
                bArr[3] = (byte) moodPressureFatigueInfo.getPressureValue();
            }
            if (moodPressureFatigueInfo.getFatigueValue() == -1) {
                bArr[4] = -1;
            } else {
                bArr[4] = (byte) moodPressureFatigueInfo.getFatigueValue();
            }
            if (moodPressureFatigueInfo.getTestResultStatus() == 0) {
                bArr[5] = 0;
            } else {
                bArr[5] = BreezeConstants.BREEZE_PROVISION_VERSION;
            }
        }
        return bArr;
    }

    public final void utenif(SevenDayWeatherInfo sevenDayWeatherInfo) {
        int todayWeatherCode = sevenDayWeatherInfo.getTodayWeatherCode();
        int todayTmpCurrent = sevenDayWeatherInfo.getTodayTmpCurrent();
        int todayTmpMax = sevenDayWeatherInfo.getTodayTmpMax();
        int todayTmpMin = sevenDayWeatherInfo.getTodayTmpMin();
        int todayPm25 = sevenDayWeatherInfo.getTodayPm25();
        int todayAqi = sevenDayWeatherInfo.getTodayAqi();
        int secondDayWeatherCode = sevenDayWeatherInfo.getSecondDayWeatherCode();
        int secondDayTmpMax = sevenDayWeatherInfo.getSecondDayTmpMax();
        int secondDayTmpMin = sevenDayWeatherInfo.getSecondDayTmpMin();
        writeChara(new byte[]{-54, (byte) (todayWeatherCode & 255), 0, (byte) (utenif(todayTmpCurrent) & 255), (byte) (utenif(todayTmpMax) & 255), (byte) (utenif(todayTmpMin) & 255), (byte) ((todayPm25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8), (byte) (todayPm25 & 255), (byte) ((65280 & todayAqi) >> 8), (byte) (todayAqi & 255), (byte) (secondDayWeatherCode & 255), 0, (byte) (utenif(secondDayTmpMax) & 255), (byte) (utenif(secondDayTmpMin) & 255)});
    }

    public final byte[] utendo(String str) throws NumberFormatException {
        byte[] bArr;
        byte[] bArrUtenif = utenif(str);
        if (bArrUtenif == null || bArrUtenif.length != 6) {
            bArr = new byte[2];
        } else {
            bArr = new byte[8];
            System.arraycopy(bArrUtenif, 0, bArr, 2, bArrUtenif.length);
        }
        bArr[0] = 68;
        bArr[1] = -6;
        return bArr;
    }

    public final byte[] utendo(boolean z2, int i2, int i3) {
        byte[] bArr = new byte[7];
        bArr[0] = 68;
        bArr[1] = 4;
        if (z2) {
            bArr[2] = 1;
        } else {
            bArr[2] = 0;
        }
        bArr[3] = (byte) ((i2 / 60) & 255);
        bArr[4] = (byte) ((i2 % 60) & 255);
        bArr[5] = (byte) ((i3 / 60) & 255);
        bArr[6] = (byte) ((i3 % 60) & 255);
        return bArr;
    }

    public final byte[] utendo(boolean z2) {
        byte[] bArr = new byte[3];
        bArr[0] = 85;
        bArr[1] = -18;
        if (z2) {
            bArr[2] = 17;
        } else {
            bArr[2] = 0;
        }
        return bArr;
    }

    public final byte[] utendo(float f2) {
        return new byte[]{91, 2, (byte) ((int) f2), (byte) ((f2 - r0) * 10.0f)};
    }

    public final void utendo(int i2, int i3, int i4, int i5, int i6, String str, String str2) throws InterruptedException {
        musicControlCRC = 0;
        if (i2 != -1 || i3 != -1 || i4 != -1 || i5 != -1 || i6 != -1) {
            byte[] bArr = new byte[13];
            bArr[0] = 58;
            bArr[1] = 1;
            if (i2 == 2) {
                bArr[2] = 1;
            } else if (i2 == 1) {
                bArr[2] = 2;
            } else {
                bArr[2] = -1;
            }
            if (i3 == 0 || i3 == 1 || i3 == 2) {
                bArr[3] = (byte) i3;
            } else {
                bArr[3] = -1;
            }
            if (i4 == -1) {
                bArr[4] = -1;
            } else {
                bArr[4] = (byte) i4;
            }
            if (i5 == -1) {
                bArr[5] = -1;
                bArr[6] = -1;
                bArr[7] = -1;
                bArr[8] = -1;
            } else {
                bArr[5] = (byte) ((i5 & ViewCompat.MEASURED_STATE_MASK) >> 24);
                bArr[6] = (byte) ((i5 & 16711680) >> 16);
                bArr[7] = (byte) ((i5 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
                bArr[8] = (byte) (i5 & 255);
            }
            if (i6 == -1) {
                bArr[9] = -1;
                bArr[10] = -1;
                bArr[11] = -1;
                bArr[12] = -1;
            } else {
                bArr[9] = (byte) (((-16777216) & i6) >> 24);
                bArr[10] = (byte) ((16711680 & i6) >> 16);
                bArr[11] = (byte) ((65280 & i6) >> 8);
                bArr[12] = (byte) (i6 & 255);
            }
            for (int i7 = 0; i7 < 13; i7++) {
                musicControlCRC ^= bArr[i7];
            }
            writeCharaBle5(bArr);
        }
        if (TextUtils.isEmpty(str)) {
            LogUtils.i("歌曲名为空");
        } else {
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            int length = bytes.length;
            LogUtils.i("data size=" + length);
            if (length > 52) {
                length = 52;
            }
            int i8 = length + 3;
            byte[] bArr2 = new byte[i8];
            bArr2[0] = 58;
            bArr2[1] = 6;
            bArr2[2] = (byte) (length & 255);
            for (int i9 = 0; i9 < length; i9++) {
                bArr2[i9 + 3] = bytes[i9];
            }
            for (int i10 = 0; i10 < i8; i10++) {
                musicControlCRC ^= bArr2[i10];
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            writeCharaBle5(bArr2);
        }
        if (TextUtils.isEmpty(str2)) {
            LogUtils.i("歌手名为空");
        } else {
            byte[] bytes2 = str2.getBytes(StandardCharsets.UTF_8);
            int length2 = bytes2.length;
            LogUtils.i("artist data size=" + length2);
            int i11 = length2 <= 52 ? length2 : 52;
            int i12 = i11 + 4;
            byte[] bArr3 = new byte[i12];
            bArr3[0] = 58;
            bArr3[1] = 4;
            bArr3[2] = 0;
            bArr3[3] = (byte) (i11 & 255);
            for (int i13 = 0; i13 < i11; i13++) {
                bArr3[i13 + 4] = bytes2[i13];
            }
            for (int i14 = 0; i14 < i12; i14++) {
                musicControlCRC ^= bArr3[i14];
            }
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
            writeCharaBle5(bArr3);
        }
        byte[] bArr4 = {58, -6};
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e4) {
            e4.printStackTrace();
        }
        writeCharaBle5(bArr4);
    }

    public final void utendo(int i2, String str) throws UnsupportedEncodingException {
        byte[] bytes;
        String strBytes2HexString;
        SPUtil.getInstance().setRemindMessageType(i2);
        CommandTimeOutUtils.getInstance().setCommandTimeOut(98);
        GBUtils gBUtils = GBUtils.getInstance();
        if (DeviceMode.isHasFunction_1(4)) {
            strBytes2HexString = gBUtils.string2unicode(str);
        } else {
            try {
                bytes = str.getBytes(StringUtils.GB2312);
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
                bytes = null;
            }
            strBytes2HexString = gBUtils.bytes2HexString(bytes);
        }
        byte[] bArrHexStringToBytess = gBUtils.hexStringToBytess(strBytes2HexString, i2);
        LogUtils.i("messageType =" + i2 + "，body =" + str + "，dataString = " + strBytes2HexString);
        if (bArrHexStringToBytess != null) {
            this.msgPushMaxLength = DeviceMode.isHasFunction_6(4194304) ? 242 : 162;
            this.textData = bArrHexStringToBytess;
            this.NOsection = 0;
            this.isSendFD = false;
            sendTextSectionKey(0);
        }
    }

    public final byte[] utendo(boolean z2, String str, int i2, int i3) {
        return z2 ? utendo(str, i2, i3) : utennative();
    }

    public final byte[] utendo(int i2, boolean z2, int i3) {
        byte[] bArr = new byte[6];
        bArr[0] = 85;
        bArr[1] = 5;
        bArr[2] = (byte) i2;
        if (z2) {
            bArr[3] = 1;
        } else {
            bArr[3] = 0;
        }
        bArr[4] = (byte) ((65280 & i3) >> 8);
        bArr[5] = (byte) (i3 & 255);
        return bArr;
    }

    public final byte[] utendo(LoginElServerInfo loginElServerInfo) throws NumberFormatException {
        String str;
        StringBuilder sb = new StringBuilder();
        String appKey = loginElServerInfo.getAppKey();
        if (TextUtils.isEmpty(appKey)) {
            appKey = "";
        }
        String strStringToASCII = GBUtils.getInstance().stringToASCII(appKey);
        if (strStringToASCII.length() > 255) {
            strStringToASCII = strStringToASCII.substring(0, 255);
        }
        sb.append(String.format("%02X", Integer.valueOf(strStringToASCII.length() / 2)));
        sb.append(strStringToASCII);
        sb.append(String.format("%02X", Integer.valueOf(loginElServerInfo.getAccountType())));
        String account = loginElServerInfo.getAccount();
        if (TextUtils.isEmpty(account)) {
            account = "";
        }
        String strStringToASCII2 = GBUtils.getInstance().stringToASCII(account);
        if (strStringToASCII2.length() > 255) {
            strStringToASCII2 = strStringToASCII2.substring(0, 255);
        }
        sb.append(String.format("%02X", Integer.valueOf(strStringToASCII2.length() / 2)));
        sb.append(strStringToASCII2);
        String name = loginElServerInfo.getName();
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        String strBytes2HexString = GBUtils.getInstance().bytes2HexString(name.getBytes(StandardCharsets.UTF_8));
        if (strBytes2HexString.length() > 255) {
            strBytes2HexString = strBytes2HexString.substring(0, 255);
        }
        sb.append(String.format("%02X", Integer.valueOf(strBytes2HexString.length() / 2)));
        sb.append(strBytes2HexString);
        String password = loginElServerInfo.getPassword();
        String strStringToASCII3 = GBUtils.getInstance().stringToASCII(TextUtils.isEmpty(password) ? "" : password);
        if (strStringToASCII3.length() > 255) {
            strStringToASCII3 = strStringToASCII3.substring(0, 255);
        }
        sb.append(String.format("%02X", Integer.valueOf(strStringToASCII3.length() / 2)));
        sb.append(strStringToASCII3);
        sb.append(String.format("%02X", Integer.valueOf(loginElServerInfo.getGender())));
        String birthday = loginElServerInfo.getBirthday();
        if (TextUtils.isEmpty(birthday)) {
            birthday = "20010808";
        }
        if (TextUtils.isEmpty(birthday) || birthday.length() < 8) {
            str = "07D00808";
        } else {
            int i2 = Integer.parseInt(birthday.substring(0, 4));
            int i3 = Integer.parseInt(birthday.substring(4, 6));
            int i4 = Integer.parseInt(birthday.substring(6, 8));
            sb.append(String.format("%04X", Integer.valueOf(i2)));
            sb.append(String.format("%02X", Integer.valueOf(i3)));
            str = String.format("%02X", Integer.valueOf(i4));
        }
        sb.append(str);
        sb.append(String.format("%02X", Integer.valueOf(loginElServerInfo.getHeight())));
        sb.append(String.format("%02X", Integer.valueOf(loginElServerInfo.getWeight())));
        return GBUtils.getInstance().hexStringToBytes(sb.toString());
    }

    public final byte[] utendo(String str, int i2, int i3) throws NumberFormatException {
        byte[] bArr = new byte[10];
        bArr[0] = 85;
        bArr[1] = 4;
        bArr[8] = (byte) (i3 & 255);
        bArr[9] = (byte) (i2 & 255);
        if (TextUtils.isEmpty(str) || str.length() != 12) {
            bArr[2] = 0;
            bArr[3] = 0;
            bArr[4] = 0;
            bArr[5] = 0;
            bArr[6] = 0;
            bArr[7] = 0;
        } else {
            int i4 = Integer.parseInt(str.substring(0, 4));
            int i5 = Integer.parseInt(str.substring(4, 6));
            int i6 = Integer.parseInt(str.substring(6, 8));
            int i7 = Integer.parseInt(str.substring(8, 10));
            int i8 = Integer.parseInt(str.substring(10, 12));
            bArr[2] = (byte) ((65280 & i4) >> 8);
            bArr[3] = (byte) (i4 & 255);
            bArr[4] = (byte) (i5 & 255);
            bArr[5] = (byte) (i6 & 255);
            bArr[6] = (byte) (i7 & 255);
            bArr[7] = (byte) (i8 & 255);
        }
        return bArr;
    }

    public final void utendo(DeviceParametersInfo deviceParametersInfo) {
        byte[] bArr = new byte[19];
        bArr[0] = -87;
        bArr[1] = 0;
        int bodyHeight = deviceParametersInfo.getBodyHeight();
        if (bodyHeight == -1) {
            bodyHeight = SPUtil.getInstance().getPersonageHeight();
        } else {
            SPUtil.getInstance().setPersonageHeight(bodyHeight);
        }
        int i2 = (int) (bodyHeight * 0.418d);
        SPUtil.getInstance().setPersonageStepLength(i2);
        if (!DeviceMode.isHasFunction_2(4096)) {
            bodyHeight = i2;
        }
        bArr[2] = (byte) (bodyHeight & 255);
        float bodyWeight = deviceParametersInfo.getBodyWeight();
        if (bodyWeight == -1.0f) {
            bodyWeight = SPUtil.getInstance().getPersonageWeight();
        } else {
            SPUtil.getInstance().setPersonageWeight(bodyWeight);
        }
        if (DeviceMode.isHasFunction_3(64) || DeviceMode.isHasFunction_9(4)) {
            bArr[3] = (byte) (RoundingUtils.getInstance().roundingToInt((bodyWeight - ((int) bodyWeight)) * 100.0f) & 255);
        } else {
            bArr[3] = 0;
        }
        bArr[4] = (byte) (((int) bodyWeight) & 255);
        int offScreenTime = deviceParametersInfo.getOffScreenTime();
        if (offScreenTime == -1) {
            offScreenTime = SPUtil.getInstance().getOffScreenTime();
        } else {
            SPUtil.getInstance().setOffScreenTime(offScreenTime);
        }
        bArr[5] = (byte) (offScreenTime & 255);
        int stepTask = deviceParametersInfo.getStepTask();
        if (stepTask == -1) {
            stepTask = SPUtil.getInstance().getStepTask();
        } else {
            SPUtil.getInstance().setStepTask(stepTask);
        }
        bArr[9] = (byte) (stepTask & 255);
        bArr[8] = (byte) ((65280 & stepTask) >> 8);
        bArr[7] = (byte) ((16711680 & stepTask) >> 16);
        bArr[6] = (byte) ((stepTask & ViewCompat.MEASURED_STATE_MASK) >> 24);
        int raiseHandBrightScreenSwitch = deviceParametersInfo.getRaiseHandBrightScreenSwitch();
        if (raiseHandBrightScreenSwitch == -1) {
            raiseHandBrightScreenSwitch = SPUtil.getInstance().getRaiseHandBrightScreenSwitch();
        } else {
            SPUtil.getInstance().setRaiseHandBrightScreenSwitch(raiseHandBrightScreenSwitch);
        }
        if (raiseHandBrightScreenSwitch == 1) {
            bArr[10] = 1;
        } else {
            bArr[10] = 0;
        }
        int highestRateSwitch = deviceParametersInfo.getHighestRateSwitch();
        if (highestRateSwitch == -1) {
            highestRateSwitch = SPUtil.getInstance().getHighestRateSwitch();
        } else {
            SPUtil.getInstance().setHighestRateSwitch(highestRateSwitch);
        }
        int highestRate = deviceParametersInfo.getHighestRate();
        if (highestRate == -1) {
            highestRate = SPUtil.getInstance().getHighestRate();
        } else {
            SPUtil.getInstance().setHighestRate(highestRate);
        }
        if (highestRateSwitch == 1) {
            bArr[11] = (byte) highestRate;
        } else if (highestRateSwitch == 0) {
            bArr[11] = -1;
        } else {
            bArr[11] = 0;
        }
        bArr[12] = 0;
        int bodyAge = deviceParametersInfo.getBodyAge();
        if (bodyAge == -1) {
            bodyAge = SPUtil.getInstance().getPersonageAge();
        } else {
            SPUtil.getInstance().setPersonageAge(bodyAge);
        }
        bArr[13] = (byte) (bodyAge & 255);
        int bodyGender = deviceParametersInfo.getBodyGender();
        if (bodyGender == -1) {
            bodyGender = SPUtil.getInstance().getPersonageGender();
        } else {
            SPUtil.getInstance().setPersonageGender(bodyGender);
        }
        if (bodyGender == 1) {
            bArr[14] = 1;
        } else if (bodyGender == 0) {
            bArr[14] = 2;
        } else {
            bArr[14] = 0;
        }
        if (DeviceMode.isHasFunction_2(4)) {
            int deviceLostSwitch = deviceParametersInfo.getDeviceLostSwitch();
            if (deviceLostSwitch == -1) {
                deviceLostSwitch = SPUtil.getInstance().getDeviceLostSwitch();
            } else {
                SPUtil.getInstance().setDeviceLostSwitch(deviceLostSwitch);
            }
            if (deviceLostSwitch == 1) {
                bArr[15] = 1;
            } else if (deviceLostSwitch == 0) {
                bArr[15] = 0;
            } else {
                bArr[15] = 0;
            }
        } else {
            bArr[15] = 0;
        }
        if (DeviceMode.isHasFunction_3(1024)) {
            int onlySupportEnCn = deviceParametersInfo.getOnlySupportEnCn();
            if (onlySupportEnCn == -1) {
                onlySupportEnCn = SPUtil.getInstance().getOnlySupportEnCn();
            } else {
                SPUtil.getInstance().setOnlySupportEnCn(onlySupportEnCn);
            }
            if (onlySupportEnCn == 1) {
                bArr[16] = 2;
            } else if (onlySupportEnCn == 0) {
                bArr[16] = 1;
            } else {
                bArr[16] = 0;
            }
        } else {
            bArr[16] = 0;
        }
        if (DeviceMode.isHasFunction_3(4096)) {
            int celsiusFahrenheitValue = deviceParametersInfo.getCelsiusFahrenheitValue();
            if (celsiusFahrenheitValue == -1) {
                celsiusFahrenheitValue = SPUtil.getInstance().getCelsiusFahrenheitValue();
            } else {
                SPUtil.getInstance().setCelsiusFahrenheitValue(celsiusFahrenheitValue);
            }
            if (celsiusFahrenheitValue == 1) {
                bArr[17] = 1;
            } else if (celsiusFahrenheitValue == 0) {
                bArr[17] = 2;
            } else {
                bArr[17] = 0;
            }
        } else {
            bArr[17] = 0;
        }
        if (DeviceMode.isHasFunction_4(2048)) {
            int lowestRateSwitch = deviceParametersInfo.getLowestRateSwitch();
            if (lowestRateSwitch == -1) {
                lowestRateSwitch = SPUtil.getInstance().getLowestRateSwitch();
            } else {
                SPUtil.getInstance().setLowestRateSwitch(lowestRateSwitch);
            }
            int lowestRate = deviceParametersInfo.getLowestRate();
            if (lowestRate == -1) {
                lowestRate = SPUtil.getInstance().getLowestRate();
            } else {
                SPUtil.getInstance().setLowestRate(lowestRate);
            }
            if (lowestRateSwitch == 1) {
                bArr[18] = (byte) lowestRate;
            } else if (lowestRateSwitch == 0) {
                bArr[18] = 0;
            } else {
                bArr[18] = 0;
            }
        } else {
            bArr[18] = 0;
        }
        writeChara(bArr);
    }

    public final void utendo(SevenDayWeatherInfo sevenDayWeatherInfo) throws UnsupportedEncodingException {
        int length;
        this.f24891a = sevenDayWeatherInfo;
        int i2 = 0;
        this.NOsectionForWeather = 0;
        int todayWeatherCode = sevenDayWeatherInfo.getTodayWeatherCode();
        int todayTmpCurrent = sevenDayWeatherInfo.getTodayTmpCurrent();
        int todayTmpMax = sevenDayWeatherInfo.getTodayTmpMax();
        int todayTmpMin = sevenDayWeatherInfo.getTodayTmpMin();
        int todayPm25 = sevenDayWeatherInfo.getTodayPm25();
        int todayAqi = sevenDayWeatherInfo.getTodayAqi();
        int hum = sevenDayWeatherInfo.getHum();
        int uv = sevenDayWeatherInfo.getUv();
        String cityName = sevenDayWeatherInfo.getCityName();
        int iUtenif = utenif(todayTmpCurrent);
        int iUtenif2 = utenif(todayTmpMax);
        int iUtenif3 = utenif(todayTmpMin);
        boolean zIsHasFunction_6 = DeviceMode.isHasFunction_6(2048);
        byte[] bArr = zIsHasFunction_6 ? new byte[21] : new byte[19];
        bArr[0] = -53;
        bArr[1] = 1;
        bArr[2] = (byte) (todayWeatherCode & 255);
        bArr[3] = 0;
        bArr[4] = (byte) (iUtenif & 255);
        bArr[5] = (byte) (iUtenif2 & 255);
        bArr[6] = (byte) (iUtenif3 & 255);
        bArr[7] = (byte) ((todayPm25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        bArr[8] = (byte) (todayPm25 & 255);
        bArr[9] = (byte) ((65280 & todayAqi) >> 8);
        bArr[10] = (byte) (todayAqi & 255);
        if (!TextUtils.isEmpty(cityName)) {
            try {
                byte[] bytes = cityName.getBytes(StringUtils.GB2312);
                if (bytes != null) {
                    byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(GBUtils.getInstance().bytes2HexString(bytes), 1);
                    if (bArrHexStringToBytes != null && (length = bArrHexStringToBytes.length) > 0) {
                        if (length > 8) {
                            while (i2 < 8) {
                                bArr[i2 + 11] = bArrHexStringToBytes[i2];
                                i2++;
                            }
                        } else {
                            while (i2 < length) {
                                bArr[i2 + 11] = bArrHexStringToBytes[i2];
                                i2++;
                            }
                        }
                    }
                }
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        if (!zIsHasFunction_6) {
            writeChara(bArr);
            return;
        }
        bArr[19] = (byte) (hum & 255);
        bArr[20] = (byte) (uv & 255);
        writeCharaBle5(bArr);
    }

    public final void utendo(byte[] bArr, int i2) {
        if (bArr == null) {
            LogUtils.w("universalInterface  data =" + bArr);
            return;
        }
        this.universalInterfaceType = i2;
        this.universalInterfaceData = bArr;
        this.NOsectionUniversalInterface = 0;
        this.universalInterfaceCRC = (byte) 0;
        if (!DeviceMode.isHasFunction_5(131072)) {
            universalInterfaceOnSegments(this.NOsectionUniversalInterface);
            return;
        }
        this.maxCommunicationLength = SPUtil.getInstance().getMaxCommunicationLength();
        LogUtils.w("universalInterface  maxcommunicationLength =" + this.maxCommunicationLength);
        universalInterfaceOnSegments_5(this.NOsectionUniversalInterface);
    }
}
