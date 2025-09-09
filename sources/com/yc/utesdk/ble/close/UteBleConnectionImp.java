package com.yc.utesdk.ble.close;

import android.content.Context;
import androidx.annotation.NonNull;
import com.yc.utesdk.bean.AIAgentMemoInfo;
import com.yc.utesdk.bean.AIWatchStatus;
import com.yc.utesdk.bean.AIWatchVoiceContent;
import com.yc.utesdk.bean.AlarmClockInfo;
import com.yc.utesdk.bean.AppLocationDataInfo;
import com.yc.utesdk.bean.BloodSugarLevelInfo;
import com.yc.utesdk.bean.BloodSugarTirInfo;
import com.yc.utesdk.bean.BloodSugarValueInfo;
import com.yc.utesdk.bean.BodyFatPersonInfo;
import com.yc.utesdk.bean.BrightScreenInfo;
import com.yc.utesdk.bean.ChatGptAnswerContent;
import com.yc.utesdk.bean.ChatGptMemoInfo;
import com.yc.utesdk.bean.ChatGptStatus;
import com.yc.utesdk.bean.ChatGptVoiceContent;
import com.yc.utesdk.bean.ContactsInfo;
import com.yc.utesdk.bean.DeviceParametersInfo;
import com.yc.utesdk.bean.DeviceWidgetInfo;
import com.yc.utesdk.bean.DoNotDisturbInfo;
import com.yc.utesdk.bean.DrinkWaterRemindInfo;
import com.yc.utesdk.bean.EcgHeartRateInfo;
import com.yc.utesdk.bean.EphemerisFileInfo;
import com.yc.utesdk.bean.GpsCoordinates;
import com.yc.utesdk.bean.ImageWatchFaceConfigRk;
import com.yc.utesdk.bean.LabelAlarmClockInfo;
import com.yc.utesdk.bean.LoginElServerInfo;
import com.yc.utesdk.bean.MessageReplyDataInfo;
import com.yc.utesdk.bean.MultiSportHeartRateWarningInfo;
import com.yc.utesdk.bean.MusicPushInfo;
import com.yc.utesdk.bean.PowerSavingModeInfo;
import com.yc.utesdk.bean.PrayerInfo;
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
import com.yc.utesdk.ble.open.DevicePlatform;
import com.yc.utesdk.ble.open.UteBleConnection;
import com.yc.utesdk.command.ChatGPTCommandUtil;
import com.yc.utesdk.command.GpsCommandUtil;
import com.yc.utesdk.command.MessageReplyDataCommandUtil;
import com.yc.utesdk.command.WriteCommandToBLE;
import com.yc.utesdk.data.AIWatchProcessing;
import com.yc.utesdk.data.DeviceParamProcessing;
import com.yc.utesdk.data.PrayProcessing;
import com.yc.utesdk.listener.AIAgentMemoSyncListener;
import com.yc.utesdk.listener.AIWatchListener;
import com.yc.utesdk.listener.BleConnectStateListener;
import com.yc.utesdk.listener.BloodPressureChangeListener;
import com.yc.utesdk.listener.BloodSugarListener;
import com.yc.utesdk.listener.BluetoothDisconnectReminderListener;
import com.yc.utesdk.listener.BodyFatChangeListener;
import com.yc.utesdk.listener.BreathingRateChangeListener;
import com.yc.utesdk.listener.CallSmsAppRemindListener;
import com.yc.utesdk.listener.ChatGPTListener;
import com.yc.utesdk.listener.ChatGptMemoSyncListener;
import com.yc.utesdk.listener.CommonInterfaceListener;
import com.yc.utesdk.listener.ContactsSyncListener;
import com.yc.utesdk.listener.ContinuousPpgListener;
import com.yc.utesdk.listener.CsbpChangeListener;
import com.yc.utesdk.listener.Device4GModuleListener;
import com.yc.utesdk.listener.DeviceAlarmListener;
import com.yc.utesdk.listener.DeviceBatteryListener;
import com.yc.utesdk.listener.DeviceBt3Listener;
import com.yc.utesdk.listener.DeviceCameraListener;
import com.yc.utesdk.listener.DeviceDspVersionListener;
import com.yc.utesdk.listener.DeviceFirmwareVersionListener;
import com.yc.utesdk.listener.DeviceLabelAlarmListener;
import com.yc.utesdk.listener.DeviceLanguageListener;
import com.yc.utesdk.listener.DeviceMusicListener;
import com.yc.utesdk.listener.DeviceParamListener;
import com.yc.utesdk.listener.DevicePrayerListener;
import com.yc.utesdk.listener.DeviceQRCodeListener;
import com.yc.utesdk.listener.DeviceShortcutSwitchListener;
import com.yc.utesdk.listener.DeviceUiVersionListener;
import com.yc.utesdk.listener.DeviceWidgetListener;
import com.yc.utesdk.listener.EcgChangeListener;
import com.yc.utesdk.listener.ElComplexListener;
import com.yc.utesdk.listener.ElHRVListener;
import com.yc.utesdk.listener.ElbpListener;
import com.yc.utesdk.listener.ElbsListener;
import com.yc.utesdk.listener.FileService;
import com.yc.utesdk.listener.GPSListener;
import com.yc.utesdk.listener.GattCallbackListener;
import com.yc.utesdk.listener.HeartRateChangeListener;
import com.yc.utesdk.listener.LocalWatchFaceListener;
import com.yc.utesdk.listener.MenstrualListener;
import com.yc.utesdk.listener.MessageReplyListener;
import com.yc.utesdk.listener.MoodPressureListener;
import com.yc.utesdk.listener.MultiSportTargetListener;
import com.yc.utesdk.listener.MultiSportsListener;
import com.yc.utesdk.listener.OxygenChangeListener;
import com.yc.utesdk.listener.QuickReplySmsListener;
import com.yc.utesdk.listener.SimpleCallbackListener;
import com.yc.utesdk.listener.SleepChangeListener;
import com.yc.utesdk.listener.SosContactsListener;
import com.yc.utesdk.listener.StepChangeListener;
import com.yc.utesdk.listener.SuloseCommandListener;
import com.yc.utesdk.listener.TemperatureChangeListener;
import com.yc.utesdk.listener.TodayTargetListener;
import com.yc.utesdk.listener.UteListenerManager;
import com.yc.utesdk.listener.WatchFaceCustomListener;
import com.yc.utesdk.listener.WatchFaceOnlineListener;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.bean.WatchFaceSerialNumberInfo;
import com.yc.utesdk.watchface.bean.acts.ApplyWatchFace;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFaceConfig;
import com.yc.utesdk.watchface.close.ActsDialCommandUtil;
import com.yc.utesdk.watchface.close.PostUtil;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class UteBleConnectionImp implements UteBleConnection {
    private static UteBleConnectionImp instance;

    /* renamed from: a, reason: collision with root package name */
    Context f24886a;

    public UteBleConnectionImp(Context context) {
        this.f24886a = context;
    }

    public static synchronized UteBleConnectionImp getInstance(Context context) {
        try {
            if (instance == null) {
                if (context == null) {
                    LogUtils.i("The provided context must not be null!");
                } else {
                    instance = new UteBleConnectionImp(context);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return instance;
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void abortDialBin(int i2, int i3) {
        ActsDialCommandUtil.getInstance().abortDialBin(i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void activeElbsAlgorithm() {
        WriteCommandToBLE.getInstance().queryElbpRequestCode();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void activeMoodAlgorithm() {
        WriteCommandToBLE.getInstance().queryMoodRequestCode();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void appInterruptContinuousPpgSampling() {
        WriteCommandToBLE.getInstance().appInterruptContinuousPpgSampling();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void appRemind(int i2, String str) throws UnsupportedEncodingException {
        WriteCommandToBLE.getInstance().appRemind(i2, str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void appStartContinuousPpgSampling() {
        WriteCommandToBLE.getInstance().appStartContinuousPpgSampling();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void applyWatchFace(ApplyWatchFace applyWatchFace) {
        ActsDialCommandUtil.getInstance().applyWatchFace(applyWatchFace);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void autoTestCommonHeartRate(boolean z2) {
        WriteCommandToBLE.getInstance().autoTestCommonHeartRate(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void autoTestCommonHeartRateInterval(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().autoTestCommonHeartRateInterval(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void autoTestHeartRate(boolean z2) {
        WriteCommandToBLE.getInstance().autoTestHeartRate(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void autoTestHeartRateInterval(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().autoTestHeartRateInterval(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void breathingRateAutomaticTest(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().breathingRateAutomaticTest(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void breathingRateTimePeriod(boolean z2, int i2, int i3) {
        WriteCommandToBLE.getInstance().breathingRateTimePeriod(z2, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void calibrationTemperature() {
        WriteCommandToBLE.getInstance().calibrationTemperature();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void callRemind(String str) throws UnsupportedEncodingException {
        WriteCommandToBLE.getInstance().callRemind(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void callerInterfaceDisappears() {
        WriteCommandToBLE.getInstance().callerInterfaceDisappears();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void cancelUpgradeJx() {
        WriteCommandToBLE.getInstance().cancelUpgradeJx();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void clearGlucoseMetabolismSampledData() {
        WriteCommandToBLE.getInstance().clearGlucoseMetabolismSampledData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void commonInterfaceBleToSdk(byte[] bArr) {
        WriteCommandToBLE.getInstance().commonInterfaceBleToSdk(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void commonInterfaceSdkToBle(byte[] bArr) {
        WriteCommandToBLE.getInstance().commonInterfaceSdkToBle(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void continueSports(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        WriteCommandToBLE.getInstance().continueSports(sportsModeControlInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpQueryCalibrationStatus() {
        WriteCommandToBLE.getInstance().csbpQueryCalibrationStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpQueryChip() {
        WriteCommandToBLE.getInstance().csbpQueryChip();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpQueryDeviceActivateStatus() {
        WriteCommandToBLE.getInstance().csbpQueryDeviceActivateStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpQueryDeviceModuleId() {
        WriteCommandToBLE.getInstance().csbpQueryDeviceModuleId();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpResetCalibrateInfo() {
        WriteCommandToBLE.getInstance().csbpResetCalibrateInfo();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpSendActivateCodeToDevice(String str) {
        WriteCommandToBLE.getInstance().csbpSendActivateCodeToDevice(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpSendCoParam(String str) {
        WriteCommandToBLE.getInstance().csbpSendCoParam(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpSetMedicationHightBp(boolean z2, boolean z3) {
        WriteCommandToBLE.getInstance().csbpSetMedicationHightBp(z2, z3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpStartCalibrate() {
        WriteCommandToBLE.getInstance().csbpStartCalibrate();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpStopCalibrate() {
        WriteCommandToBLE.getInstance().csbpStopCalibrate();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void csbpSyncHeartRateAndOxygen() {
        WriteCommandToBLE.getInstance().csbpSyncHeartRateAndOxygen();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteDeviceAllContacts() {
        WriteCommandToBLE.getInstance().deleteDeviceAllContacts();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteDeviceTimeZone() {
        WriteCommandToBLE.getInstance().deleteDeviceTimeZone();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteDeviceWatchFaceOnline(int i2) {
        WriteCommandToBLE.getInstance().deleteDeviceWatchFaceOnline(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteMoodPressureData() {
        WriteCommandToBLE.getInstance().deleteMoodPressureData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteTemperatureHistoricalData() {
        WriteCommandToBLE.getInstance().deleteTemperatureHistoricalData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceFactoryDataReset() {
        WriteCommandToBLE.getInstance().deviceFactoryDataReset();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceMusicPlayStatus(int i2) {
        WriteCommandToBLE.getInstance().deviceMusicPlayStatus(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceMusicSwitchStatus(boolean z2) {
        WriteCommandToBLE.getInstance().deviceMusicSwitchStatus(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceMusicVolumeStatus(int i2, int i3) {
        WriteCommandToBLE.getInstance().deviceMusicVolumeStatus(i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceOnScreenDuration(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().deviceOnScreenDuration(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void devicePowerOff() {
        WriteCommandToBLE.getInstance().devicePowerOff();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void devicePowerSavingMode(PowerSavingModeInfo powerSavingModeInfo) {
        WriteCommandToBLE.getInstance().devicePowerSavingMode(powerSavingModeInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deviceRestart() {
        WriteCommandToBLE.getInstance().deviceRestart();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void doNotDisturb(DoNotDisturbInfo doNotDisturbInfo) {
        WriteCommandToBLE.getInstance().doNotDisturb(doNotDisturbInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void downloadImageWatchFace(@NonNull File file, String str, int i2, FileService.Callback callback) {
        ActsDialCommandUtil.getInstance().downloadImageWatchFace(file, str, i2, callback);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void drinkWaterRemind(DrinkWaterRemindInfo drinkWaterRemindInfo) {
        WriteCommandToBLE.getInstance().drinkWaterRemind(drinkWaterRemindInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void ecgParsingFail() {
        WriteCommandToBLE.getInstance().ecgParsingFail();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void ecgParsingSuccess(EcgHeartRateInfo ecgHeartRateInfo) {
        WriteCommandToBLE.getInstance().ecgParsingSuccess(ecgHeartRateInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void femaleMenstrualCycle(boolean z2, String str, int i2, int i3) throws NumberFormatException {
        WriteCommandToBLE.getInstance().femaleMenstrualCycle(z2, str, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void femaleMenstrualFunctionSwitch(boolean z2) {
        WriteCommandToBLE.getInstance().femaleMenstrualFunctionSwitch(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void getEphemerisParams() {
        GpsCommandUtil.getInstance().getEphemerisParams();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void getImageWatchFace() {
        ActsDialCommandUtil.getInstance().getImageWatchFace();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void getImageWatchFaceParamsRk() {
        WriteCommandToBLE.getInstance().getImageWatchFaceParamsRk();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void getWatchFaceInfo() {
        ActsDialCommandUtil.getInstance().getWatchFaceInfo();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void getWatchFaceParams() {
        ActsDialCommandUtil.getInstance().getWatchFaceParams();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void glucoseMetabolismAlgorithm(boolean z2) {
        WriteCommandToBLE.getInstance().glucoseMetabolismAlgorithm(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void heartRateStorageInterval(int i2) {
        WriteCommandToBLE.getInstance().heartRateStorageInterval(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void isNeedPairing(boolean z2) {
        NotifyUtils.getInstance().isNeedPairing(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void isSetWatchFace(boolean z2) {
        NotifyUtils.getInstance().setWatchFace(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void isUpgrade(boolean z2) {
        NotifyUtils.getInstance().setUpgrade(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void makeDeviceVibration(int i2) {
        WriteCommandToBLE.getInstance().makeDeviceVibration(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void moodPressureFatigueAutoTest(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().moodPressureFatigueAutoTest(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void moodPressureFatigueTimePeriod(boolean z2, int i2, int i3) {
        WriteCommandToBLE.getInstance().moodPressureFatigueTimePeriod(z2, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void notifyBluetoothOff() {
        UteListenerManager.getInstance().notifyBluetoothOff();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void notifyDeviceContinuousPpgSampling() {
        WriteCommandToBLE.getInstance().notifyDeviceContinuousPpgSampling();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void notifyDeviceReplySmsResult(boolean z2) {
        WriteCommandToBLE.getInstance().notifyDeviceReplySmsResult(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openBluetoothDisconnectReminder(boolean z2) {
        WriteCommandToBLE.getInstance().openBluetoothDisconnectReminder(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openDebugElbpPpgMiddleData(boolean z2) {
        WriteCommandToBLE.getInstance().openElbpPpgMiddleData(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openDeviceBt3(boolean z2) {
        WriteCommandToBLE.getInstance().openDeviceBt3(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openDeviceCameraMode(boolean z2) {
        WriteCommandToBLE.getInstance().openDeviceCameraMode(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openDeviceFindPhone(boolean z2) {
        WriteCommandToBLE.getInstance().openDeviceFindPhone(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openQuickReplySms(boolean z2) {
        WriteCommandToBLE.getInstance().openQuickReplySms(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void openSosContactsSwitch(boolean z2) {
        WriteCommandToBLE.getInstance().openSosContactsSwitch(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void oxygenAutomaticTest(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().oxygenAutomaticTest(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void oxygenTimePeriod(boolean z2, int i2, int i3) {
        WriteCommandToBLE.getInstance().oxygenTimePeriod(z2, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void pauseSports(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        WriteCommandToBLE.getInstance().pauseSports(sportsModeControlInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void prepareSyncWatchFaceData() {
        WriteCommandToBLE.getInstance().prepareSyncWatchFaceData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void query4gModuleImei() {
        WriteCommandToBLE.getInstance().query4gModuleImei();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryAIWatchEnable() {
        AIWatchProcessing.getInstance().queryAIWatchEnable();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryAllWatchFaceInfo() {
        WriteCommandToBLE.getInstance().queryAllWatchFaceInfo();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryBluetoothDisconnectReminder() {
        WriteCommandToBLE.getInstance().queryBluetoothDisconnectReminder();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryBodyFatCurrentTestStatus() {
        WriteCommandToBLE.getInstance().queryBodyFatCurrentTestStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryBreathingRateCurrentTestStatus() {
        WriteCommandToBLE.getInstance().queryBreathingRateCurrentTestStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryBrightScreenParam() {
        DeviceParamProcessing.getInstance().queryBrightScreenParam();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryBrightScreenTime() {
        DeviceParamProcessing.getInstance().queryBrightScreenTime();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryCurrentSports() {
        WriteCommandToBLE.getInstance().queryCurrentSports();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryCustomizeSMS() {
        WriteCommandToBLE.getInstance().queryCustomizeSMS();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceBattery() {
        WriteCommandToBLE.getInstance().queryDeviceBattery();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceBt3State() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        WriteCommandToBLE.getInstance().queryDeviceBt3State();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceCurrentLanguage() {
        WriteCommandToBLE.getInstance().queryDeviceCurrentLanguage();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceDspVersion(DeviceDspVersionListener deviceDspVersionListener) {
        UteListenerManager.getInstance().setDeviceDspVersionListener(deviceDspVersionListener);
        WriteCommandToBLE.getInstance().queryDeviceDspVersion();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceLabelAlarmClock() {
        WriteCommandToBLE.getInstance().queryDeviceLabelAlarmClock();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceRemindDisplay() {
        WriteCommandToBLE.getInstance().queryDeviceRemindDisplay();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceShortcutSwitch() {
        WriteCommandToBLE.getInstance().queryDeviceShortcutSwitch();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceShortcutSwitchStatus() {
        WriteCommandToBLE.getInstance().queryDeviceShortcutSwitchStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceUiVersion(DeviceUiVersionListener deviceUiVersionListener) {
        UteListenerManager.getInstance().setDeviceUiVersionListener(deviceUiVersionListener);
        WriteCommandToBLE.getInstance().queryDeviceUiVersion();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceWidget() {
        WriteCommandToBLE.getInstance().queryDeviceWidget();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDoNotDisturb() {
        WriteCommandToBLE.getInstance().queryDoNotDisturb();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDrinkWaterRemind() {
        WriteCommandToBLE.getInstance().queryDrinkWaterRemind();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryEcgSamplingFrequency() {
        WriteCommandToBLE.getInstance().queryEcgSamplingFrequency();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElComplexTestStatus() {
        WriteCommandToBLE.getInstance().queryElComplexTestStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElHRVTestStatus() {
        WriteCommandToBLE.getInstance().queryElHRVTestStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElbpAlgorithmVersion() {
        WriteCommandToBLE.getInstance().queryElbpAlgorithmVersion();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElbpSamplingStatus() {
        WriteCommandToBLE.getInstance().queryElbpSamplingStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElbsAlgorithmActiveState() {
        WriteCommandToBLE.getInstance().queryElbsAlgorithmActiveState();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryElbsTestStatus() {
        WriteCommandToBLE.getInstance().queryElbsTestStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryEphemerisState() {
        GpsCommandUtil.getInstance().queryEphemerisState();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryFemaleMenstrualFunctionSwitch() {
        WriteCommandToBLE.getInstance().queryFemaleMenstrualFunctionSwitch();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryFirmwareVersion() {
        WriteCommandToBLE.getInstance().queryFirmwareVersion();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryFirmwareVersionBeforePaired(DeviceFirmwareVersionListener deviceFirmwareVersionListener) {
        UteListenerManager.getInstance().setDeviceFirmwareVersionListener(deviceFirmwareVersionListener);
        WriteCommandToBLE.getInstance().queryFirmwareVersionBeforePaired();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryGlucoseMetabolismAlgorithm() {
        WriteCommandToBLE.getInstance().queryGlucoseMetabolismAlgorithm();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryHeartRateParam() {
        DeviceParamProcessing.getInstance().queryHeartRateParam();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryHeartRateSwitch() {
        DeviceParamProcessing.getInstance().queryHeartRateSwitch();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryLocalWatchFace() {
        WriteCommandToBLE.getInstance().queryLocalWatchFace();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryMenstrualDataForMonth(String str) throws NumberFormatException {
        WriteCommandToBLE.getInstance().queryMenstrualDataForMonth(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryMenstrualParam() {
        WriteCommandToBLE.getInstance().queryMenstrualParam();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryMoodAlgorithmActiveState() {
        WriteCommandToBLE.getInstance().queryMoodAlgorithmActiveState();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryMoodPressureTestState() {
        WriteCommandToBLE.getInstance().queryMoodPressureTestState();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryMoodSensorType() {
        WriteCommandToBLE.getInstance().queryMoodSensorType();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryPrayer() {
        PrayProcessing.getInstance().queryPrayer();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryPrayerShowStatus() {
        PrayProcessing.getInstance().queryPrayerShowStatus();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryPressureSwitch() {
        DeviceParamProcessing.getInstance().queryPressureSwitch();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void querySOS() {
        WriteCommandToBLE.getInstance().querySOS();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void querySedentaryRemind() {
        WriteCommandToBLE.getInstance().querySedentaryRemind();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void querySnCode() {
        DeviceParamProcessing.getInstance().querySnCode();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void querySosCallContactsCount() {
        WriteCommandToBLE.getInstance().querySosCallContactsCount();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void querySportsModeList() {
        WriteCommandToBLE.getInstance().querySportsModeList();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryTemperatureFormat() {
        DeviceParamProcessing.getInstance().queryTemperatureFormat();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryTimeFormat() {
        DeviceParamProcessing.getInstance().queryTimeFormat();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryTodayTarget(int i2) {
        WriteCommandToBLE.getInstance().queryTodayTarget(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryWhichWatchFaceInfo(int i2) {
        WriteCommandToBLE.getInstance().queryWhichWatchFaceInfo(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void readDeviceWatchFaceConfiguration() {
        WriteCommandToBLE.getInstance().readDeviceWatchFaceConfiguration();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void reportRestHeartRate() {
        WriteCommandToBLE.getInstance().reportRestHeartRate();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void reportRestHeartRateHourBest() {
        WriteCommandToBLE.getInstance().reportRestHeartRateHourBest();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sedentaryRemind(SedentaryRemindInfo sedentaryRemindInfo) {
        WriteCommandToBLE.getInstance().sedentaryRemind(sedentaryRemindInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendAccountId(int i2) {
        WriteCommandToBLE.getInstance().sendAccountId(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendAccountIdAndRandomCode(int i2, int i3, boolean z2) {
        WriteCommandToBLE.getInstance().sendAccountIdAndRandomCode(i2, i3, z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendAppLocationData(AppLocationDataInfo appLocationDataInfo) {
        WriteCommandToBLE.getInstance().sendAppLocationData(appLocationDataInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendAppLocationStatus(boolean z2) {
        WriteCommandToBLE.getInstance().sendAppLocationStatus(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendBloodSugarLevel(BloodSugarLevelInfo bloodSugarLevelInfo) {
        WriteCommandToBLE.getInstance().sendBloodSugarLevel(bloodSugarLevelInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendBloodSugarTIR(BloodSugarTirInfo bloodSugarTirInfo) {
        WriteCommandToBLE.getInstance().sendBloodSugarTIR(bloodSugarTirInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendBloodSugarValue(BloodSugarValueInfo bloodSugarValueInfo) {
        WriteCommandToBLE.getInstance().sendBloodSugarValue(bloodSugarValueInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendElbsCalibrationModel(byte[] bArr) {
        WriteCommandToBLE.getInstance().sendElbsCalibrationModel(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendGlucoseMetabolismSampledCrcResult(boolean z2) {
        WriteCommandToBLE.getInstance().sendGlucoseMetabolismSampledCrcResult(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendGpsCoordinates(@NonNull GpsCoordinates gpsCoordinates) {
        GpsCommandUtil.getInstance().sendGpsCoordinates(gpsCoordinates);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendMenstrualEndDay(String str) throws NumberFormatException {
        WriteCommandToBLE.getInstance().sendMenstrualEndDay(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendMenstrualStartDay(String str) throws NumberFormatException {
        WriteCommandToBLE.getInstance().sendMenstrualStartDay(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendMessageReplyData(MessageReplyDataInfo messageReplyDataInfo) {
        MessageReplyDataCommandUtil.getInstance().sendMessageReplyData(messageReplyDataInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendQRCodeData(QRCodeInfo qRCodeInfo) {
        WriteCommandToBLE.getInstance().sendQRCodeData(qRCodeInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendSportsDataToBle(SportsModeControlInfo sportsModeControlInfo) throws NumberFormatException {
        WriteCommandToBLE.getInstance().sendSportsDataToBle(sportsModeControlInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void sendWatchFaceData(byte[] bArr) {
        WriteCommandToBLE.getInstance().sendOnlineDialData(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void set4gReportInterval(int i2) {
        WriteCommandToBLE.getInstance().set4gReportInterval(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAIAgentType(List<Integer> list) {
        ChatGPTCommandUtil.getInstance().setAIAgentType(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAIWatchEnable(boolean z2) {
        AIWatchProcessing.getInstance().setAIWatchEnable(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAIWatchListener(AIWatchListener aIWatchListener) {
        UteListenerManager.getInstance().setAIWatchListener(aIWatchListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAIWatchStatus(AIWatchStatus aIWatchStatus) {
        AIWatchProcessing.getInstance().setAIWatchStatus(aIWatchStatus);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAIWatchVoiceContent(AIWatchVoiceContent aIWatchVoiceContent) {
        AIWatchProcessing.getInstance().setAIWatchVoiceContent(aIWatchVoiceContent);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setAppRingStatusToDevice(boolean z2) {
        WriteCommandToBLE.getInstance().setAppRingStatusToDevice(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBloodPressureChangeListener(BloodPressureChangeListener bloodPressureChangeListener) {
        UteListenerManager.getInstance().setBloodPressureChangeListener(bloodPressureChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBloodSugarListener(BloodSugarListener bloodSugarListener) {
        UteListenerManager.getInstance().setBloodSugarListener(bloodSugarListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBluetoothDisconnectReminderListener(BluetoothDisconnectReminderListener bluetoothDisconnectReminderListener) {
        UteListenerManager.getInstance().setBluetoothDisconnectReminderListener(bluetoothDisconnectReminderListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBodyFatChangeListener(BodyFatChangeListener bodyFatChangeListener) {
        UteListenerManager.getInstance().setBodyFatChangeListener(bodyFatChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBreathingRateChangeListener(BreathingRateChangeListener breathingRateChangeListener) {
        UteListenerManager.getInstance().setBreathingRateChangeListener(breathingRateChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setBrightScreenParam(BrightScreenInfo brightScreenInfo) {
        DeviceParamProcessing.getInstance().setBrightScreenParam(brightScreenInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setCallMuteMode(boolean z2) {
        WriteCommandToBLE.getInstance().setCallMuteMode(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setCallSmsAppRemindListener(CallSmsAppRemindListener callSmsAppRemindListener) {
        UteListenerManager.getInstance().setCallSmsAppRemindListener(callSmsAppRemindListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setChatGPTListener(ChatGPTListener chatGPTListener) {
        UteListenerManager.getInstance().setChatGPTListener(chatGPTListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setChatGptAnswerContent(ChatGptAnswerContent chatGptAnswerContent) {
        ChatGPTCommandUtil.getInstance().setChatGptAnswerContent(chatGptAnswerContent);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setChatGptLanguageEnvironment(int i2) {
        ChatGPTCommandUtil.getInstance().setChatGptLanguageEnvironment(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setChatGptStatus(ChatGptStatus chatGptStatus) {
        ChatGPTCommandUtil.getInstance().setChatGptStatus(chatGptStatus);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setChatGptVoiceContent(ChatGptVoiceContent chatGptVoiceContent) {
        ChatGPTCommandUtil.getInstance().setChatGptVoiceContent(chatGptVoiceContent);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setCommonInterfaceListener(CommonInterfaceListener commonInterfaceListener) {
        UteListenerManager.getInstance().setCommonInterfaceListener(commonInterfaceListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setConnectStateListener(BleConnectStateListener bleConnectStateListener) {
        UteListenerManager.getInstance().setConnectStateListener(bleConnectStateListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setContactsSyncListener(ContactsSyncListener contactsSyncListener) {
        UteListenerManager.getInstance().setContactsSyncListener(contactsSyncListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setContinuousPpgListener(ContinuousPpgListener continuousPpgListener) {
        UteListenerManager.getInstance().setContinuousPpgListener(continuousPpgListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setCsbpChangeListener(CsbpChangeListener csbpChangeListener) {
        UteListenerManager.getInstance().setCsbpChangeListener(csbpChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDelayCallBack(boolean z2, int i2) {
        UteListenerManager.getInstance().setDelayCallBack(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDevice4GModuleListener(Device4GModuleListener device4GModuleListener) {
        UteListenerManager.getInstance().setDevice4GModuleListener(device4GModuleListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceAlarmClock(AlarmClockInfo alarmClockInfo) {
        WriteCommandToBLE.getInstance().setDeviceAlarmClock(alarmClockInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceAlarmListener(DeviceAlarmListener deviceAlarmListener) {
        UteListenerManager.getInstance().setDeviceAlarmListener(deviceAlarmListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceBatteryListener(DeviceBatteryListener deviceBatteryListener) {
        UteListenerManager.getInstance().setDeviceBatteryListener(deviceBatteryListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceBt3Listener(DeviceBt3Listener deviceBt3Listener) {
        UteListenerManager.getInstance().setDeviceBt3Listener(deviceBt3Listener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceCameraListener(DeviceCameraListener deviceCameraListener) {
        UteListenerManager.getInstance().setDeviceCameraListener(deviceCameraListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceFirmwareVersionListener(DeviceFirmwareVersionListener deviceFirmwareVersionListener) {
        UteListenerManager.getInstance().setDeviceFirmwareVersionListener(deviceFirmwareVersionListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceLabelAlarmClock(List<LabelAlarmClockInfo> list) throws NumberFormatException {
        WriteCommandToBLE.getInstance().setDeviceLabelAlarmClock(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceLabelAlarmListener(DeviceLabelAlarmListener deviceLabelAlarmListener) {
        UteListenerManager.getInstance().setDeviceLabelAlarmListener(deviceLabelAlarmListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceLanguage(int i2) {
        WriteCommandToBLE.getInstance().setDeviceLanguage(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceLanguageListener(DeviceLanguageListener deviceLanguageListener) {
        UteListenerManager.getInstance().setDeviceLanguageListener(deviceLanguageListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceMusicListener(DeviceMusicListener deviceMusicListener) {
        UteListenerManager.getInstance().setDeviceMusicListener(deviceMusicListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceParamListener(DeviceParamListener deviceParamListener) {
        UteListenerManager.getInstance().setDeviceParamListener(deviceParamListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDevicePrayer(List<PrayerInfo> list) {
        PrayProcessing.getInstance().setPrayer(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDevicePrayerListener(DevicePrayerListener devicePrayerListener) {
        UteListenerManager.getInstance().setOnDevicePrayerListener(devicePrayerListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceQRCodeListener(DeviceQRCodeListener deviceQRCodeListener) {
        UteListenerManager.getInstance().setDeviceQRCodeListener(deviceQRCodeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceShortcutSwitchListener(DeviceShortcutSwitchListener deviceShortcutSwitchListener) {
        UteListenerManager.getInstance().setDeviceShortcutSwitchListener(deviceShortcutSwitchListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceTimeZone(List<TimeZoneInfo> list) {
        WriteCommandToBLE.getInstance().setDeviceTimeZone(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceUnitHourFormat(UnitHourFormatInfo unitHourFormatInfo) {
        WriteCommandToBLE.getInstance().setDeviceUnitHourFormat(unitHourFormatInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceWeather(SevenDayWeatherInfo sevenDayWeatherInfo) throws UnsupportedEncodingException {
        WriteCommandToBLE.getInstance().setDeviceWeather(sevenDayWeatherInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceWeatherCityName(String str) {
        WriteCommandToBLE.getInstance().setDeviceWeatherCityName(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceWidget(List<DeviceWidgetInfo> list) {
        WriteCommandToBLE.getInstance().setDeviceWidget(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceWidgetListener(DeviceWidgetListener deviceWidgetListener) {
        UteListenerManager.getInstance().setDeviceWidgetListener(deviceWidgetListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setEcgChangeListener(EcgChangeListener ecgChangeListener) {
        UteListenerManager.getInstance().setEcgChangeListener(ecgChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElComplexListener(ElComplexListener elComplexListener) {
        UteListenerManager.getInstance().setElComplexListener(elComplexListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElHRVListener(ElHRVListener elHRVListener) {
        UteListenerManager.getInstance().setElHRVListener(elHRVListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElbpDataToDevice(boolean z2, String str, int i2, int i3) {
        WriteCommandToBLE.getInstance().setElbpDataToDevice(z2, str, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElbpListener(ElbpListener elbpListener) {
        UteListenerManager.getInstance().setElbpListener(elbpListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElbpSamplingInterval(int i2, boolean z2, int i3) {
        WriteCommandToBLE.getInstance().setElbpSamplingInterval(i2, z2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setElbsListener(ElbsListener elbsListener) {
        UteListenerManager.getInstance().setElbsListener(elbsListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setEphemerisStatus(int i2) {
        GpsCommandUtil.getInstance().setEphemerisStatus(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setGpsListener(GPSListener gPSListener) {
        UteListenerManager.getInstance().setGpsListener(gPSListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setHMaxHeartRate(int i2) {
        WriteCommandToBLE.getInstance().setHMaxHeartRate(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setHeartRateChangeListener(HeartRateChangeListener heartRateChangeListener) {
        UteListenerManager.getInstance().setHeartRateChangeListener(heartRateChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setImageWatchFace(ImageWatchFaceConfig imageWatchFaceConfig) {
        ActsDialCommandUtil.getInstance().setImageWatchFace(imageWatchFaceConfig);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setImageWatchFaceConfigRk(ImageWatchFaceConfigRk imageWatchFaceConfigRk) {
        WriteCommandToBLE.getInstance().setImageWatchFaceConfigRk(imageWatchFaceConfigRk);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setLargeModeType(List<Integer> list) {
        ChatGPTCommandUtil.getInstance().setLargeModeType(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setLocalWatchFace(int i2) {
        WriteCommandToBLE.getInstance().setLocalWatchFace(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setLocalWatchFaceListener(LocalWatchFaceListener localWatchFaceListener) {
        UteListenerManager.getInstance().setLocalWatchFaceListener(localWatchFaceListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setLoginElServerInfo(LoginElServerInfo loginElServerInfo) {
        WriteCommandToBLE.getInstance().setLoginElServerInfo(loginElServerInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMaxMinAlarmTemperature(boolean z2, float f2, float f3) {
        WriteCommandToBLE.getInstance().setMaxMinAlarmTemperature(z2, f2, f3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMenstrualListener(MenstrualListener menstrualListener) {
        UteListenerManager.getInstance().setMenstrualListener(menstrualListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMessageReplyListener(MessageReplyListener messageReplyListener) {
        UteListenerManager.getInstance().setMessageReplyListener(messageReplyListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMoodPressureListener(MoodPressureListener moodPressureListener) {
        UteListenerManager.getInstance().setMoodPressureListener(moodPressureListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMultiSportHeartRateWarning(MultiSportHeartRateWarningInfo multiSportHeartRateWarningInfo) {
        WriteCommandToBLE.getInstance().setMultiSportHeartRateWarning(multiSportHeartRateWarningInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMultiSportTarget(int i2, boolean z2, int i3) {
        WriteCommandToBLE.getInstance().setMultiSportTarget(i2, z2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMultiSportTargetListener(MultiSportTargetListener multiSportTargetListener) {
        UteListenerManager.getInstance().setMultiSportTargetListener(multiSportTargetListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setMultiSportsListener(MultiSportsListener multiSportsListener) {
        UteListenerManager.getInstance().setMultiSportsListener(multiSportsListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setOnGattCallbackListener(GattCallbackListener gattCallbackListener) {
        UteListenerManager.getInstance().setOnGattCallbackListener(gattCallbackListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setOxygenChangeListener(OxygenChangeListener oxygenChangeListener) {
        UteListenerManager.getInstance().setOxygenChangeListener(oxygenChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setQuickReplySmsListener(QuickReplySmsListener quickReplySmsListener) {
        UteListenerManager.getInstance().setQuickReplySmsListener(quickReplySmsListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSimpleCallbackListener(SimpleCallbackListener simpleCallbackListener) {
        UteListenerManager.getInstance().setSimpleCallbackListener(simpleCallbackListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSleepChangeListener(SleepChangeListener sleepChangeListener) {
        UteListenerManager.getInstance().setSleepChangeListener(sleepChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSongInformationToDevice(MusicPushInfo musicPushInfo) throws InterruptedException {
        WriteCommandToBLE.getInstance().setSongInformationToDevice(musicPushInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSosContactsListener(SosContactsListener sosContactsListener) {
        UteListenerManager.getInstance().setSosContactsListener(sosContactsListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSportsModeList(List<SportsModeInfo> list) {
        WriteCommandToBLE.getInstance().setSportsModeList(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setStepChangeListener(StepChangeListener stepChangeListener) {
        UteListenerManager.getInstance().setStepChangeListener(stepChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setSuloseCommandListener(SuloseCommandListener suloseCommandListener) {
        UteListenerManager.getInstance().setSuloseCommandListener(suloseCommandListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setTemperatureChangeListener(TemperatureChangeListener temperatureChangeListener) {
        UteListenerManager.getInstance().setTemperatureChangeListener(temperatureChangeListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setTodayTarget(int i2, boolean z2, int i3) {
        WriteCommandToBLE.getInstance().setTodayTarget(i2, z2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setTodayTargetListener(TodayTargetListener todayTargetListener) {
        UteListenerManager.getInstance().setTodayTargetListener(todayTargetListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setUteAppKey(String str) {
        PostUtil.getInstance().setAppKey(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setWatchFaceCustomListener(WatchFaceCustomListener watchFaceCustomListener) {
        UteListenerManager.getInstance().setWatchFaceCustomListener(watchFaceCustomListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setWatchFaceMode(int i2) {
        WatchFaceUtil.getInstance().setWatchFaceMode(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setWatchFaceOnlineListener(WatchFaceOnlineListener watchFaceOnlineListener) {
        UteListenerManager.getInstance().setWatchFaceOnlineListener(watchFaceOnlineListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setWhichWatchFace(int i2) {
        WriteCommandToBLE.getInstance().setWhichWatchFace(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void showPrayer(boolean z2) {
        PrayProcessing.getInstance().showPray(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void smsRemind(String str, String str2) throws UnsupportedEncodingException {
        SPUtil.getInstance().setCallSmsPhoneNumber("");
        WriteCommandToBLE.getInstance().smsRemind(str, str2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startElbpPpgSampling() {
        WriteCommandToBLE.getInstance().startElbpPpgSampling();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startSports(int i2, int i3) {
        WriteCommandToBLE.getInstance().startSports(i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestBloodPressure(boolean z2) {
        WriteCommandToBLE.getInstance().startTestBloodPressure(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestBodyFat(BodyFatPersonInfo bodyFatPersonInfo) {
        WriteCommandToBLE.getInstance().startTestBodyFat(bodyFatPersonInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestBreathingRate(boolean z2) {
        WriteCommandToBLE.getInstance().startTestBreathingRate(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestEcg() {
        WriteCommandToBLE.getInstance().startTestEcg();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestElComplex(boolean z2) {
        WriteCommandToBLE.getInstance().startTestElComplex(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestElHRV(boolean z2) {
        WriteCommandToBLE.getInstance().startTestElHRV(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestElbs(boolean z2) {
        WriteCommandToBLE.getInstance().startTestElbs(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestHeartRate(boolean z2) {
        WriteCommandToBLE.getInstance().startTestHeartRate(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestMoodPressureFatigue(int i2, String str) {
        SPUtil.getInstance().setMoodOpenId(str);
        WriteCommandToBLE.getInstance().startTestMoodPressureFatigue(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestOxygen(boolean z2) {
        WriteCommandToBLE.getInstance().startTestOxygen(z2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void startTestTemperature() {
        WriteCommandToBLE.getInstance().startTestTemperature();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopDeviceVibration() {
        WriteCommandToBLE.getInstance().stopDeviceVibration();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopElbpPpgSampling() {
        WriteCommandToBLE.getInstance().stopElbpPpgSampling();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopSendLocationData() {
        WriteCommandToBLE.getInstance().stopSendLocationData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopSports(int i2) {
        WriteCommandToBLE.getInstance().stopSports(i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopSyncWatchFaceData() {
        if (DevicePlatform.getInstance().isJXPlatform()) {
            ActsDialCommandUtil.getInstance().stopOnlineDialData();
        } else {
            WriteCommandToBLE.getInstance().stopOnlineDialData();
        }
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void stopTestMoodPressureFatigue() {
        WriteCommandToBLE.getInstance().stopTestMoodPressureFatigue(null);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void switchWatchFace(WatchFaceSerialNumberInfo watchFaceSerialNumberInfo) throws NumberFormatException {
        WriteCommandToBLE.getInstance().switchWatchFace(watchFaceSerialNumberInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncAIAgentMemoToDevice(List<AIAgentMemoInfo> list, AIAgentMemoSyncListener aIAgentMemoSyncListener) {
        ChatGPTCommandUtil.getInstance().syncAIAgentMemoToDevice(list, aIAgentMemoSyncListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncActivityTimeData() {
        WriteCommandToBLE.getInstance().syncActivityTimeData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBloodPressureData() {
        WriteCommandToBLE.getInstance().syncBloodPressureData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBodyFatData() {
        WriteCommandToBLE.getInstance().syncBodyFatData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBreathingRateData() throws NumberFormatException, ParseException {
        WriteCommandToBLE.getInstance().syncBreathingRateData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncChatGptMemoToDevice(List<ChatGptMemoInfo> list, ChatGptMemoSyncListener chatGptMemoSyncListener) {
        ChatGPTCommandUtil.getInstance().syncChatGptMemoToDevice(list, chatGptMemoSyncListener);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncContactsToDevice(List<ContactsInfo> list) {
        WriteCommandToBLE.getInstance().syncContactsToDevice(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncDeviceElbpMiddleData() {
        WriteCommandToBLE.getInstance().syncDeviceElbpMiddleData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncDeviceElbpPpgData() {
        WriteCommandToBLE.getInstance().syncDeviceElbpPpgData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncDeviceParameters(DeviceParametersInfo deviceParametersInfo) {
        WriteCommandToBLE.getInstance().syncDeviceParameters(deviceParametersInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncDeviceTime() {
        WriteCommandToBLE.getInstance().syncDeviceTime();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncEcgData() {
        WriteCommandToBLE.getInstance().syncEcgData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncEcgHistorySamplingData() {
        WriteCommandToBLE.getInstance().syncEcgHistorySamplingData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncGlucoseMetabolismSampledData() {
        WriteCommandToBLE.getInstance().syncGlucoseMetabolismSampledData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncHeartRateData() {
        WriteCommandToBLE.getInstance().syncHeartRateData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncMoodPressureData() {
        WriteCommandToBLE.getInstance().syncMoodPressureData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncMultipleSportsData() throws NumberFormatException {
        WriteCommandToBLE.getInstance().syncMultipleSportsData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncOxygenData() throws NumberFormatException, ParseException {
        WriteCommandToBLE.getInstance().syncOxygenData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncQuickReplySmsContent(List<ReplySmsInfo> list) {
        WriteCommandToBLE.getInstance().syncQuickReplySmsContent(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncRestHeartRateData() {
        WriteCommandToBLE.getInstance().syncRestHeartRateData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncSleepData() {
        WriteCommandToBLE.getInstance().syncSleepData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncSosCallContacts(List<SosCallInfo> list) {
        WriteCommandToBLE.getInstance().syncSosCallContacts(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncStandingTimeData() {
        WriteCommandToBLE.getInstance().syncStandingTimeData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncStepData() {
        WriteCommandToBLE.getInstance().syncStepData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncTemperatureData() {
        WriteCommandToBLE.getInstance().syncTemperatureData();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public boolean syncWatchFaceOnlineData(String str) {
        return WriteCommandToBLE.getInstance().syncWatchFaceToDevice(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void temperatureAutomaticTest(boolean z2, int i2) {
        WriteCommandToBLE.getInstance().temperatureAutomaticTest(z2, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void temperatureTimePeriod(boolean z2, int i2, int i3) {
        WriteCommandToBLE.getInstance().temperatureTimePeriod(z2, i2, i3);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void uploadEphemeris(@NonNull List<EphemerisFileInfo> list, FileService.MultiCallback multiCallback) {
        GpsCommandUtil.getInstance().uploadEphemeris(list, multiCallback);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void uploadImageWatchFace(@NonNull File file, int i2) {
        ActsDialCommandUtil.getInstance().uploadImageWatchFace(file, i2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void uploadWatchFace(File file) {
        ActsDialCommandUtil.getInstance().uploadWatchFace(file);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void washHandsRemind(WashHandsRemindInfo washHandsRemindInfo) {
        WriteCommandToBLE.getInstance().washHandsRemind(washHandsRemindInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void writeSuloseCommand60(byte[] bArr) {
        WriteCommandToBLE.getInstance().writeSuloseCommand60(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void writeSuloseCommand61(byte[] bArr) {
        WriteCommandToBLE.getInstance().writeSuloseCommand61(bArr);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void callRemind(String str, String str2) throws UnsupportedEncodingException {
        SPUtil.getInstance().setCallSmsPhoneNumber(str2);
        WriteCommandToBLE.getInstance().callRemind(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void deleteDeviceWatchFaceOnline(List<Integer> list) {
        WriteCommandToBLE.getInstance().deleteDeviceWatchFaceOnline(list);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryDeviceBattery(DeviceBatteryListener deviceBatteryListener) {
        UteListenerManager.getInstance().setDeviceBatteryListener(deviceBatteryListener);
        WriteCommandToBLE.getInstance().queryDeviceBattery();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void queryFirmwareVersion(DeviceFirmwareVersionListener deviceFirmwareVersionListener) {
        UteListenerManager.getInstance().setDeviceFirmwareVersionListener(deviceFirmwareVersionListener);
        WriteCommandToBLE.getInstance().queryFirmwareVersion();
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void setDeviceWeather(WeatherInfo weatherInfo) {
        WriteCommandToBLE.getInstance().setDeviceWeather(weatherInfo);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void smsRemind(String str, String str2, String str3) throws UnsupportedEncodingException {
        SPUtil.getInstance().setCallSmsPhoneNumber(str3);
        WriteCommandToBLE.getInstance().smsRemind(str, str2);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBloodPressureData(String str) {
        WriteCommandToBLE.getInstance().syncBloodPressureData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBodyFatData(String str) {
        WriteCommandToBLE.getInstance().syncBodyFatData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncBreathingRateData(String str) throws NumberFormatException, ParseException {
        WriteCommandToBLE.getInstance().syncBreathingRateData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncEcgHistorySamplingData(String str) {
        WriteCommandToBLE.getInstance().syncEcgHistorySamplingData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncHeartRateData(String str) {
        WriteCommandToBLE.getInstance().syncHeartRateData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncMoodPressureData(String str) {
        WriteCommandToBLE.getInstance().syncMoodPressureData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncMultipleSportsData(String str) throws NumberFormatException {
        WriteCommandToBLE.getInstance().syncMultipleSportsData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncOxygenData(String str) throws NumberFormatException, ParseException {
        WriteCommandToBLE.getInstance().syncOxygenData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncSleepData(String str) {
        WriteCommandToBLE.getInstance().syncSleepData(str);
    }

    @Override // com.yc.utesdk.ble.open.UteBleConnection
    public void syncStepData(String str) {
        WriteCommandToBLE.getInstance().syncStepData(str);
    }
}
