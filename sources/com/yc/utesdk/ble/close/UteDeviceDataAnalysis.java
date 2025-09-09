package com.yc.utesdk.ble.close;

import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.command.ChatGPTCommandUtil;
import com.yc.utesdk.command.CommandTimeOutUtils;
import com.yc.utesdk.command.GpsCommandUtil;
import com.yc.utesdk.command.MessageReplyDataCommandUtil;
import com.yc.utesdk.data.ABCommandProcessing;
import com.yc.utesdk.data.AIWatchProcessing;
import com.yc.utesdk.data.BloodPressureDataProcessing;
import com.yc.utesdk.data.BloodSugarProcessing;
import com.yc.utesdk.data.BodyFatDataProcessing;
import com.yc.utesdk.data.BreathingRateDataProcessing;
import com.yc.utesdk.data.CsbpCommandProcessing;
import com.yc.utesdk.data.D1CommandProcessing;
import com.yc.utesdk.data.DataProcessing;
import com.yc.utesdk.data.Device4GModuleProcessing;
import com.yc.utesdk.data.DeviceParamProcessing;
import com.yc.utesdk.data.EcgDataProcessing;
import com.yc.utesdk.data.HeartRateDataProcessing;
import com.yc.utesdk.data.LanguageCommandProcessing;
import com.yc.utesdk.data.MessageRemindProcessing;
import com.yc.utesdk.data.MoodPressureProcessing;
import com.yc.utesdk.data.MultiSportsProcessing;
import com.yc.utesdk.data.OxygenDataProcessing;
import com.yc.utesdk.data.PrayProcessing;
import com.yc.utesdk.data.QRCodeProcessing;
import com.yc.utesdk.data.SleepDataProcessing;
import com.yc.utesdk.data.StandingTimeProcessing;
import com.yc.utesdk.data.TemperatureDataProcessing;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.close.ActsDialCommandUtil;
import com.yc.utesdk.watchface.close.WatchFaceProcessing;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import javax.crypto.NoSuchPaddingException;
import org.android.agoo.common.AgooConstants;

/* loaded from: classes4.dex */
public class UteDeviceDataAnalysis {
    private static UteDeviceDataAnalysis instance;

    public static UteDeviceDataAnalysis getInstance() {
        if (instance == null) {
            instance = new UteDeviceDataAnalysis();
        }
        return instance;
    }

    public void datePacketOperate(String str, byte[] bArr) throws ParseException {
        UteListenerManager uteListenerManager;
        int i2;
        int i3;
        String strSubstring = str.substring(0, 2);
        String strSubstring2 = str.length() >= 4 ? str.substring(0, 4) : "";
        if (strSubstring.equals("A3")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            i3 = 12;
        } else {
            if (strSubstring.equals("A1")) {
                CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                if (!strSubstring2.equals("A101")) {
                    UteListenerManager.getInstance().queryFirmwareVersionSuccess(utendo(str));
                    return;
                }
                if (str.length() <= 4) {
                    UteListenerManager.getInstance().queryDeviceDspVersionFail();
                    return;
                }
                String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(str.substring(4));
                LogUtils.i("dspVersion =" + strAsciiStringToString);
                UteListenerManager.getInstance().queryDeviceDspVersionSuccess(strAsciiStringToString);
                return;
            }
            if (!strSubstring.equals("A9")) {
                if (strSubstring.equals("A2")) {
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    int iMin = Math.min(bArr[1] & 255, 100);
                    UteListenerManager.getInstance().queryDeviceBatterySuccess(iMin);
                    if (DeviceMode.isHasFunction_10(1024)) {
                        UteListenerManager.getInstance().onBatteryChange(iMin, bArr[2] & 255);
                        return;
                    }
                    return;
                }
                if (strSubstring.equals("B1")) {
                    DataProcessing.getInstance().stepRealTimeDataOperate(bArr, DeviceMode.isHasFunction_1(256));
                    return;
                }
                if (strSubstring.equals("B2")) {
                    DataProcessing.getInstance().stepOffLineDataOperate(bArr, strSubstring2, DeviceMode.isHasFunction_1(256));
                    return;
                }
                if (strSubstring.equals("31")) {
                    SleepDataProcessing.getInstance().dealWithBandAlgorithm(strSubstring, str, bArr);
                    return;
                }
                if (strSubstring.equals("33")) {
                    if (str.equals("330401")) {
                        UteListenerManager.getInstance().onSimpleCallback(true, 2);
                        UteListenerManager.getInstance().onConnecteStateChange(2);
                        return;
                    }
                    if (str.equals("330402")) {
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 3;
                    } else if (str.equals("330403")) {
                        UteListenerManager.getInstance().onSimpleCallback(true, 4);
                        return;
                    } else if (str.equals("330404")) {
                        UteListenerManager.getInstance().onSimpleCallback(true, 5);
                        return;
                    } else {
                        if (!str.equals("330405")) {
                            return;
                        }
                        uteListenerManager = UteListenerManager.getInstance();
                        i2 = 6;
                    }
                } else {
                    if (strSubstring.equals("F7")) {
                        HeartRateDataProcessing.getInstance().dealWithHeartRate24(bArr, strSubstring2);
                        return;
                    }
                    if (strSubstring.equals("D6")) {
                        HeartRateDataProcessing.getInstance().dealWithHeartRateCommon(bArr, strSubstring2);
                        return;
                    }
                    if (strSubstring.equals("E5")) {
                        HeartRateDataProcessing.getInstance().dealWithHeartRateNormal(bArr, strSubstring2, str);
                        return;
                    }
                    if (strSubstring.equals("E6")) {
                        HeartRateDataProcessing.getInstance().offLineDataNormal(bArr, strSubstring2);
                        return;
                    }
                    if (strSubstring.equals("C7")) {
                        BloodPressureDataProcessing.getInstance().dealWithBloodPressure(bArr, strSubstring2, str);
                        return;
                    }
                    if (strSubstring.equals("C8")) {
                        BloodPressureDataProcessing.getInstance().offLineDataOperate(bArr, strSubstring2);
                        return;
                    }
                    if (strSubstring.equals("34")) {
                        OxygenDataProcessing.getInstance().dealWithOxygen(strSubstring, bArr);
                        return;
                    }
                    if (strSubstring.equals(AgooConstants.REPORT_NOT_ENCRYPT)) {
                        TemperatureDataProcessing.getInstance().dealWithTemperature(bArr);
                        return;
                    }
                    if (strSubstring.equals("AB")) {
                        ABCommandProcessing.getInstance().dealWithABCommand(bArr);
                        return;
                    }
                    if (strSubstring.equals("E9")) {
                        BodyFatDataProcessing.getInstance().dealWithBodyComposition(str, bArr);
                        return;
                    }
                    if (strSubstring.equals("3B")) {
                        BreathingRateDataProcessing.getInstance().dealWithBreathe(bArr);
                        return;
                    }
                    if (strSubstring.equals("DB")) {
                        MessageRemindProcessing.getInstance().dealWithPushMessageDisplay(str);
                        return;
                    }
                    if (strSubstring.equals("C5") || strSubstring.equals("C6")) {
                        MessageRemindProcessing.getInstance().dealWithMessageRemind(strSubstring, str, bArr);
                        return;
                    }
                    if (strSubstring.equals("D1")) {
                        D1CommandProcessing.getInstance().dealWithD1Command(bArr);
                        return;
                    }
                    if (strSubstring.equals("C1")) {
                        D1CommandProcessing.getInstance().dealWithC1Command(bArr);
                        return;
                    }
                    if (strSubstring.startsWith("C4")) {
                        if ((bArr[1] & 255) == 2) {
                            UteListenerManager.getInstance().onDeviceCameraStatus(true, 5);
                            return;
                        }
                        return;
                    }
                    if (strSubstring.equals("D3")) {
                        D1CommandProcessing.getInstance().dealWithD3Command(bArr);
                        return;
                    }
                    if (strSubstring.equals("D4")) {
                        D1CommandProcessing.getInstance().dealWithDrinkWater(bArr);
                        return;
                    }
                    if (strSubstring.equals("D7")) {
                        D1CommandProcessing.getInstance().dealWithD7Command(bArr);
                        return;
                    }
                    if (strSubstring.equals("AF")) {
                        LanguageCommandProcessing.getInstance().dealWithLanguage(bArr, strSubstring2, str);
                        return;
                    }
                    if (!strSubstring.equals("A0")) {
                        if (strSubstring.equals("29")) {
                            D1CommandProcessing.getInstance().dealWithFemaleMenstrualCycle(bArr);
                            return;
                        }
                        if (strSubstring.equals("CB") || strSubstring.equals("CA")) {
                            MessageRemindProcessing.getInstance().dealWithWeather(strSubstring, bArr);
                            return;
                        }
                        if (strSubstring.equals("BE")) {
                            LanguageCommandProcessing.getInstance().dealWithQuickSwitch(bArr);
                            return;
                        }
                        if (strSubstring.equals("F6")) {
                            MessageRemindProcessing.getInstance().dealWithCommonInterface(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("37")) {
                            MessageRemindProcessing.getInstance().dealWithContacts(bArr);
                            return;
                        }
                        if (strSubstring.equals("3A")) {
                            D1CommandProcessing.getInstance().dealwithMusicControl(bArr);
                            return;
                        }
                        if (strSubstring.equals("26")) {
                            WatchFaceProcessing.getInstance().dealWithOnlineDial(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("3F")) {
                            StandingTimeProcessing.getInstance().dealWithTodayTarget(bArr);
                            return;
                        }
                        if (strSubstring.startsWith("41")) {
                            D1CommandProcessing.getInstance().dealWithWashHands(bArr);
                            return;
                        }
                        if (strSubstring.equals("44")) {
                            MoodPressureProcessing.getInstance().dealwithMoodPressureFatigue(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("FD")) {
                            MultiSportsProcessing.getInstance().dealWithMultiSportsMode(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("49")) {
                            ABCommandProcessing.getInstance().dealWithDeviceWidget(bArr);
                            return;
                        }
                        if (strSubstring.equals("50")) {
                            MultiSportsProcessing.getInstance().dealWithSportTarget(bArr);
                            return;
                        }
                        if (strSubstring.equals("52")) {
                            MessageRemindProcessing.getInstance().dealWithEndCallReplySms(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("51")) {
                            ABCommandProcessing.getInstance().dealWithLableAlarmClock(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("55")) {
                            MoodPressureProcessing.getInstance().dealwithElbp(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("59")) {
                            WatchFaceProcessing.getInstance().dealWithLocalWatchFace(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("5A")) {
                            D1CommandProcessing.getInstance().dealWithBTDisconnectReminder(bArr);
                            return;
                        }
                        if (strSubstring.equals("3E")) {
                            CsbpCommandProcessing.getInstance().dealWithcSBPCalibration(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("5B")) {
                            MoodPressureProcessing.getInstance().dealwithElbs(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("5C")) {
                            MoodPressureProcessing.getInstance().dealwithElHRV(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("5D")) {
                            MoodPressureProcessing.getInstance().dealwithElComplex(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("E8")) {
                            D1CommandProcessing.getInstance().dealWithDeviceRestart(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("28")) {
                            EcgDataProcessing.getInstance().dealWithEcg(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("61")) {
                            D1CommandProcessing.getInstance().dealWithDeviceOnScreen61(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("FA")) {
                            D1CommandProcessing.getInstance().dealWithDevicePowerSavingMode(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("EB")) {
                            DataProcessing.getInstance().dealWithCyweeStep(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("EC")) {
                            SleepDataProcessing.getInstance().dealWithCyweeSleep(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("63")) {
                            D1CommandProcessing.getInstance().dealWithCustomFunctionSwitch(str, bArr);
                            return;
                        }
                        if (strSubstring.equals("65")) {
                            PrayProcessing.getInstance().dealWithPray(str, bArr);
                            return;
                        } else if (strSubstring.equals("56")) {
                            GpsCommandUtil.getInstance().do56FD(bArr);
                            return;
                        } else {
                            if (strSubstring2.equals("01C8")) {
                                D1CommandProcessing.getInstance().doCancelUpgrade01C8(bArr);
                                return;
                            }
                            return;
                        }
                    }
                    CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
                    uteListenerManager = UteListenerManager.getInstance();
                    i2 = 22;
                }
                uteListenerManager.onSimpleCallback(true, i2);
                return;
            }
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            i3 = 13;
        }
        utendo(true, i3);
    }

    public void datePacketOperate34F2(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, IOException, ParseException, InvalidAlgorithmParameterException {
        String strSubstring = str.substring(0, 2);
        String strSubstring2 = str.substring(0, 4);
        if (strSubstring.equals("32")) {
            SleepDataProcessing.getInstance().dealWithBandAlgorithm(strSubstring, str, bArr);
            return;
        }
        if (strSubstring.equals("34")) {
            OxygenDataProcessing.getInstance().dealWithOxygen(strSubstring, bArr);
            return;
        }
        if (strSubstring.equals("3B")) {
            BreathingRateDataProcessing.getInstance().dealWithBreathe(bArr);
            return;
        }
        if (strSubstring.equals("28")) {
            EcgDataProcessing.getInstance().dealWithEcg(str, bArr);
            return;
        }
        if (strSubstring.equals("F6")) {
            MessageRemindProcessing.getInstance().dealWithCommonInterface_5(str, bArr);
            return;
        }
        if (strSubstring.equals("3F")) {
            StandingTimeProcessing.getInstance().dealWithTodayTarget(bArr);
            return;
        }
        if (strSubstring.equals("CD")) {
            CommandTimeOutUtils.getInstance().cancelCommandTimeOut();
            UteListenerManager.getInstance().queryDeviceUiVersionSuccess(utendo(str, bArr));
            return;
        }
        if (strSubstring.equals("44")) {
            MoodPressureProcessing.getInstance().dealwithMoodPressureFatigue(str, bArr);
            return;
        }
        if (strSubstring.equals("38")) {
            LanguageCommandProcessing.getInstance().dealWithBt3Agreement(bArr);
            return;
        }
        if (strSubstring.equals("FD")) {
            MultiSportsProcessing.getInstance().dealWithMultiSportsMode(str, bArr);
            return;
        }
        if (strSubstring.equals("50")) {
            MultiSportsProcessing.getInstance().dealWithSportTarget(bArr);
            return;
        }
        if (strSubstring.equals("43")) {
            MessageRemindProcessing.getInstance().dealWithTimeZone(bArr);
            return;
        }
        if (strSubstring.equals("CB")) {
            MessageRemindProcessing.getInstance().dealWithWeather(strSubstring, bArr);
            return;
        }
        if (strSubstring.equals("46")) {
            MessageRemindProcessing.getInstance().dealWithCustomizeSMS(bArr);
            return;
        }
        if (strSubstring.equals("49")) {
            ABCommandProcessing.getInstance().dealWithDeviceWidget(bArr);
            return;
        }
        if (strSubstring.equals("52")) {
            MessageRemindProcessing.getInstance().dealWithEndCallReplySms(str, bArr);
            return;
        }
        if (strSubstring.equals("51")) {
            ABCommandProcessing.getInstance().dealWithLableAlarmClock(str, bArr);
            return;
        }
        if (strSubstring.equals("3E")) {
            CsbpCommandProcessing.getInstance().dealWithcSBPCalibration(str, bArr);
            return;
        }
        if (strSubstring.equals("55")) {
            MoodPressureProcessing.getInstance().dealwithElbp(str, bArr);
            return;
        }
        if (strSubstring.equals("58")) {
            StandingTimeProcessing.getInstance().dealWithActivityTimeData(bArr);
            return;
        }
        if (strSubstring.equals("5B")) {
            MoodPressureProcessing.getInstance().dealwithElbs(str, bArr);
            return;
        }
        if (strSubstring.equals("5C")) {
            MoodPressureProcessing.getInstance().dealwithElHRV(str, bArr);
            return;
        }
        if (strSubstring.equals("5D")) {
            MoodPressureProcessing.getInstance().dealwithElComplex(str, bArr);
            return;
        }
        if (strSubstring.equals("DC")) {
            QRCodeProcessing.getInstance().doQRCodeDC52(bArr);
            return;
        }
        if (strSubstring.equals("EC")) {
            SleepDataProcessing.getInstance().dealWithCyweeSleep(str, bArr);
            return;
        }
        if (strSubstring.equals("4A")) {
            MultiSportsProcessing.getInstance().dealWithCyweeSwimData(str, bArr);
            return;
        }
        if (strSubstring.equals("F7")) {
            HeartRateDataProcessing.getInstance().dealWithHeartRateSaveCustom(bArr);
            return;
        }
        if (strSubstring2.equals("01E3")) {
            ActsDialCommandUtil.getInstance().doWatchFaceData01E3(bArr);
            return;
        }
        if (strSubstring2.equals("01E4")) {
            ActsDialCommandUtil.getInstance().doWatchFaceData01E4(bArr);
            return;
        }
        if (strSubstring.equals("65")) {
            PrayProcessing.getInstance().dealWithPray(str, bArr);
            return;
        }
        if (strSubstring.equals("66")) {
            BloodSugarProcessing.getInstance().dealWithBloodSugar(str, bArr);
            return;
        }
        if (strSubstring.equals("67")) {
            Device4GModuleProcessing.getInstance().dealWithDevice4GModule(str, bArr);
            return;
        }
        if (strSubstring2.equals("01EF")) {
            ChatGPTCommandUtil.getInstance().doChatGptData01EF(bArr);
            return;
        }
        if (strSubstring2.equals("01E9")) {
            GpsCommandUtil.getInstance().doGpsData01E9(bArr);
            return;
        }
        if (strSubstring.equals("56")) {
            GpsCommandUtil.getInstance().do56FD(bArr);
            return;
        }
        if (strSubstring.equals("69")) {
            DeviceParamProcessing.getInstance().doGpsData69(bArr);
            return;
        }
        if (strSubstring.equals("6A")) {
            MessageReplyDataCommandUtil.getInstance().doMessageReplyData6A(bArr);
        } else if (strSubstring.equals("29")) {
            D1CommandProcessing.getInstance().dealWithFemaleMenstrualCycle(bArr);
        } else if (strSubstring2.equals("01F0")) {
            AIWatchProcessing.getInstance().doAIWatchData01F0(bArr);
        }
    }

    public final String utendo(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        if (bArr.length != 3 || (bArr[2] & 255) != 255) {
            String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(str.substring(4));
            LogUtils.i("bleUIVersion=" + strAsciiStringToString);
            SPUtil.getInstance().setDeviceUiVersion(strAsciiStringToString);
            return strAsciiStringToString;
        }
        String deviceFirmwareVersion = SPUtil.getInstance().getDeviceFirmwareVersion();
        SPUtil.getInstance().setDeviceUiVersion(deviceFirmwareVersion + "_U0000");
        return deviceFirmwareVersion + "_U0000";
    }

    public final String utendo(String str) {
        if (str.length() <= 2) {
            return "";
        }
        String strAsciiStringToString = GBUtils.getInstance().AsciiStringToString(str.substring(2));
        SPUtil.getInstance().setDeviceFirmwareVersion(strAsciiStringToString);
        return strAsciiStringToString;
    }

    public final void utendo(boolean z2, int i2) {
        UteListenerManager.getInstance().onSimpleCallback(z2, i2);
    }
}
