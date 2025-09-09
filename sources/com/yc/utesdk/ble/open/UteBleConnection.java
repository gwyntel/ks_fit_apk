package com.yc.utesdk.ble.open;

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
import com.yc.utesdk.listener.WatchFaceCustomListener;
import com.yc.utesdk.listener.WatchFaceOnlineListener;
import com.yc.utesdk.watchface.bean.WatchFaceSerialNumberInfo;
import com.yc.utesdk.watchface.bean.acts.ApplyWatchFace;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFaceConfig;
import java.io.File;
import java.util.List;

/* loaded from: classes4.dex */
public interface UteBleConnection {
    void abortDialBin(int i2, int i3);

    void activeElbsAlgorithm();

    void activeMoodAlgorithm();

    void appInterruptContinuousPpgSampling();

    void appRemind(int i2, String str);

    void appStartContinuousPpgSampling();

    void applyWatchFace(ApplyWatchFace applyWatchFace);

    void autoTestCommonHeartRate(boolean z2);

    void autoTestCommonHeartRateInterval(boolean z2, int i2);

    void autoTestHeartRate(boolean z2);

    void autoTestHeartRateInterval(boolean z2, int i2);

    void breathingRateAutomaticTest(boolean z2, int i2);

    void breathingRateTimePeriod(boolean z2, int i2, int i3);

    void calibrationTemperature();

    void callRemind(String str);

    void callRemind(String str, String str2);

    void callerInterfaceDisappears();

    void cancelUpgradeJx();

    void clearGlucoseMetabolismSampledData();

    void commonInterfaceBleToSdk(byte[] bArr);

    void commonInterfaceSdkToBle(byte[] bArr);

    void continueSports(SportsModeControlInfo sportsModeControlInfo);

    void csbpQueryCalibrationStatus();

    void csbpQueryChip();

    void csbpQueryDeviceActivateStatus();

    void csbpQueryDeviceModuleId();

    void csbpResetCalibrateInfo();

    void csbpSendActivateCodeToDevice(String str);

    void csbpSendCoParam(String str);

    void csbpSetMedicationHightBp(boolean z2, boolean z3);

    void csbpStartCalibrate();

    void csbpStopCalibrate();

    void csbpSyncHeartRateAndOxygen();

    void deleteDeviceAllContacts();

    void deleteDeviceTimeZone();

    void deleteDeviceWatchFaceOnline(int i2);

    void deleteDeviceWatchFaceOnline(List<Integer> list);

    void deleteMoodPressureData();

    void deleteTemperatureHistoricalData();

    void deviceFactoryDataReset();

    void deviceMusicPlayStatus(int i2);

    void deviceMusicSwitchStatus(boolean z2);

    void deviceMusicVolumeStatus(int i2, int i3);

    void deviceOnScreenDuration(boolean z2, int i2);

    void devicePowerOff();

    void devicePowerSavingMode(PowerSavingModeInfo powerSavingModeInfo);

    void deviceRestart();

    void doNotDisturb(DoNotDisturbInfo doNotDisturbInfo);

    void downloadImageWatchFace(@NonNull File file, String str, int i2, FileService.Callback callback);

    void drinkWaterRemind(DrinkWaterRemindInfo drinkWaterRemindInfo);

    void ecgParsingFail();

    void ecgParsingSuccess(EcgHeartRateInfo ecgHeartRateInfo);

    void femaleMenstrualCycle(boolean z2, String str, int i2, int i3);

    void femaleMenstrualFunctionSwitch(boolean z2);

    void getEphemerisParams();

    void getImageWatchFace();

    void getImageWatchFaceParamsRk();

    void getWatchFaceInfo();

    void getWatchFaceParams();

    void glucoseMetabolismAlgorithm(boolean z2);

    void heartRateStorageInterval(int i2);

    void isNeedPairing(boolean z2);

    void isSetWatchFace(boolean z2);

    void isUpgrade(boolean z2);

    void makeDeviceVibration(int i2);

    void moodPressureFatigueAutoTest(boolean z2, int i2);

    void moodPressureFatigueTimePeriod(boolean z2, int i2, int i3);

    void notifyBluetoothOff();

    void notifyDeviceContinuousPpgSampling();

    void notifyDeviceReplySmsResult(boolean z2);

    void openBluetoothDisconnectReminder(boolean z2);

    void openDebugElbpPpgMiddleData(boolean z2);

    void openDeviceBt3(boolean z2);

    void openDeviceCameraMode(boolean z2);

    void openDeviceFindPhone(boolean z2);

    void openQuickReplySms(boolean z2);

    void openSosContactsSwitch(boolean z2);

    void oxygenAutomaticTest(boolean z2, int i2);

    void oxygenTimePeriod(boolean z2, int i2, int i3);

    void pauseSports(SportsModeControlInfo sportsModeControlInfo);

    void prepareSyncWatchFaceData();

    void query4gModuleImei();

    void queryAIWatchEnable();

    void queryAllWatchFaceInfo();

    void queryBluetoothDisconnectReminder();

    void queryBodyFatCurrentTestStatus();

    void queryBreathingRateCurrentTestStatus();

    void queryBrightScreenParam();

    void queryBrightScreenTime();

    void queryCurrentSports();

    void queryCustomizeSMS();

    void queryDeviceBattery();

    void queryDeviceBattery(DeviceBatteryListener deviceBatteryListener);

    void queryDeviceBt3State();

    void queryDeviceCurrentLanguage();

    void queryDeviceDspVersion(DeviceDspVersionListener deviceDspVersionListener);

    void queryDeviceLabelAlarmClock();

    void queryDeviceRemindDisplay();

    void queryDeviceShortcutSwitch();

    void queryDeviceShortcutSwitchStatus();

    void queryDeviceUiVersion(DeviceUiVersionListener deviceUiVersionListener);

    void queryDeviceWidget();

    void queryDoNotDisturb();

    void queryDrinkWaterRemind();

    void queryEcgSamplingFrequency();

    void queryElComplexTestStatus();

    void queryElHRVTestStatus();

    void queryElbpAlgorithmVersion();

    void queryElbpSamplingStatus();

    void queryElbsAlgorithmActiveState();

    void queryElbsTestStatus();

    void queryEphemerisState();

    void queryFemaleMenstrualFunctionSwitch();

    void queryFirmwareVersion();

    void queryFirmwareVersion(DeviceFirmwareVersionListener deviceFirmwareVersionListener);

    void queryFirmwareVersionBeforePaired(DeviceFirmwareVersionListener deviceFirmwareVersionListener);

    void queryGlucoseMetabolismAlgorithm();

    void queryHeartRateParam();

    void queryHeartRateSwitch();

    void queryLocalWatchFace();

    void queryMenstrualDataForMonth(String str);

    void queryMenstrualParam();

    void queryMoodAlgorithmActiveState();

    void queryMoodPressureTestState();

    void queryMoodSensorType();

    void queryPrayer();

    void queryPrayerShowStatus();

    void queryPressureSwitch();

    void querySOS();

    void querySedentaryRemind();

    void querySnCode();

    void querySosCallContactsCount();

    void querySportsModeList();

    void queryTemperatureFormat();

    void queryTimeFormat();

    void queryTodayTarget(int i2);

    void queryWhichWatchFaceInfo(int i2);

    void readDeviceWatchFaceConfiguration();

    void reportRestHeartRate();

    void reportRestHeartRateHourBest();

    void sedentaryRemind(SedentaryRemindInfo sedentaryRemindInfo);

    void sendAccountId(int i2);

    void sendAccountIdAndRandomCode(int i2, int i3, boolean z2);

    void sendAppLocationData(AppLocationDataInfo appLocationDataInfo);

    void sendAppLocationStatus(boolean z2);

    void sendBloodSugarLevel(BloodSugarLevelInfo bloodSugarLevelInfo);

    void sendBloodSugarTIR(BloodSugarTirInfo bloodSugarTirInfo);

    void sendBloodSugarValue(BloodSugarValueInfo bloodSugarValueInfo);

    void sendElbsCalibrationModel(byte[] bArr);

    void sendGlucoseMetabolismSampledCrcResult(boolean z2);

    void sendGpsCoordinates(@NonNull GpsCoordinates gpsCoordinates);

    void sendMenstrualEndDay(String str);

    void sendMenstrualStartDay(String str);

    void sendMessageReplyData(MessageReplyDataInfo messageReplyDataInfo);

    void sendQRCodeData(QRCodeInfo qRCodeInfo);

    void sendSportsDataToBle(SportsModeControlInfo sportsModeControlInfo);

    void sendWatchFaceData(byte[] bArr);

    void set4gReportInterval(int i2);

    void setAIAgentType(List<Integer> list);

    void setAIWatchEnable(boolean z2);

    void setAIWatchListener(AIWatchListener aIWatchListener);

    void setAIWatchStatus(AIWatchStatus aIWatchStatus);

    void setAIWatchVoiceContent(AIWatchVoiceContent aIWatchVoiceContent);

    void setAppRingStatusToDevice(boolean z2);

    void setBloodPressureChangeListener(BloodPressureChangeListener bloodPressureChangeListener);

    void setBloodSugarListener(BloodSugarListener bloodSugarListener);

    void setBluetoothDisconnectReminderListener(BluetoothDisconnectReminderListener bluetoothDisconnectReminderListener);

    void setBodyFatChangeListener(BodyFatChangeListener bodyFatChangeListener);

    void setBreathingRateChangeListener(BreathingRateChangeListener breathingRateChangeListener);

    void setBrightScreenParam(BrightScreenInfo brightScreenInfo);

    void setCallMuteMode(boolean z2);

    void setCallSmsAppRemindListener(CallSmsAppRemindListener callSmsAppRemindListener);

    void setChatGPTListener(ChatGPTListener chatGPTListener);

    void setChatGptAnswerContent(ChatGptAnswerContent chatGptAnswerContent);

    void setChatGptLanguageEnvironment(int i2);

    void setChatGptStatus(ChatGptStatus chatGptStatus);

    void setChatGptVoiceContent(ChatGptVoiceContent chatGptVoiceContent);

    void setCommonInterfaceListener(CommonInterfaceListener commonInterfaceListener);

    void setConnectStateListener(BleConnectStateListener bleConnectStateListener);

    void setContactsSyncListener(ContactsSyncListener contactsSyncListener);

    void setContinuousPpgListener(ContinuousPpgListener continuousPpgListener);

    void setCsbpChangeListener(CsbpChangeListener csbpChangeListener);

    void setDelayCallBack(boolean z2, int i2);

    void setDevice4GModuleListener(Device4GModuleListener device4GModuleListener);

    void setDeviceAlarmClock(AlarmClockInfo alarmClockInfo);

    void setDeviceAlarmListener(DeviceAlarmListener deviceAlarmListener);

    void setDeviceBatteryListener(DeviceBatteryListener deviceBatteryListener);

    void setDeviceBt3Listener(DeviceBt3Listener deviceBt3Listener);

    void setDeviceCameraListener(DeviceCameraListener deviceCameraListener);

    void setDeviceFirmwareVersionListener(DeviceFirmwareVersionListener deviceFirmwareVersionListener);

    void setDeviceLabelAlarmClock(List<LabelAlarmClockInfo> list);

    void setDeviceLabelAlarmListener(DeviceLabelAlarmListener deviceLabelAlarmListener);

    void setDeviceLanguage(int i2);

    void setDeviceLanguageListener(DeviceLanguageListener deviceLanguageListener);

    void setDeviceMusicListener(DeviceMusicListener deviceMusicListener);

    void setDeviceParamListener(DeviceParamListener deviceParamListener);

    void setDevicePrayer(List<PrayerInfo> list);

    void setDevicePrayerListener(DevicePrayerListener devicePrayerListener);

    void setDeviceQRCodeListener(DeviceQRCodeListener deviceQRCodeListener);

    void setDeviceShortcutSwitchListener(DeviceShortcutSwitchListener deviceShortcutSwitchListener);

    void setDeviceTimeZone(List<TimeZoneInfo> list);

    void setDeviceUnitHourFormat(UnitHourFormatInfo unitHourFormatInfo);

    void setDeviceWeather(SevenDayWeatherInfo sevenDayWeatherInfo);

    void setDeviceWeather(WeatherInfo weatherInfo);

    void setDeviceWeatherCityName(String str);

    void setDeviceWidget(List<DeviceWidgetInfo> list);

    void setDeviceWidgetListener(DeviceWidgetListener deviceWidgetListener);

    void setEcgChangeListener(EcgChangeListener ecgChangeListener);

    void setElComplexListener(ElComplexListener elComplexListener);

    void setElHRVListener(ElHRVListener elHRVListener);

    void setElbpDataToDevice(boolean z2, String str, int i2, int i3);

    void setElbpListener(ElbpListener elbpListener);

    void setElbpSamplingInterval(int i2, boolean z2, int i3);

    void setElbsListener(ElbsListener elbsListener);

    void setEphemerisStatus(int i2);

    void setGpsListener(GPSListener gPSListener);

    void setHMaxHeartRate(int i2);

    void setHeartRateChangeListener(HeartRateChangeListener heartRateChangeListener);

    void setImageWatchFace(ImageWatchFaceConfig imageWatchFaceConfig);

    void setImageWatchFaceConfigRk(ImageWatchFaceConfigRk imageWatchFaceConfigRk);

    void setLargeModeType(List<Integer> list);

    void setLocalWatchFace(int i2);

    void setLocalWatchFaceListener(LocalWatchFaceListener localWatchFaceListener);

    void setLoginElServerInfo(LoginElServerInfo loginElServerInfo);

    void setMaxMinAlarmTemperature(boolean z2, float f2, float f3);

    void setMenstrualListener(MenstrualListener menstrualListener);

    void setMessageReplyListener(MessageReplyListener messageReplyListener);

    void setMoodPressureListener(MoodPressureListener moodPressureListener);

    void setMultiSportHeartRateWarning(MultiSportHeartRateWarningInfo multiSportHeartRateWarningInfo);

    void setMultiSportTarget(int i2, boolean z2, int i3);

    void setMultiSportTargetListener(MultiSportTargetListener multiSportTargetListener);

    void setMultiSportsListener(MultiSportsListener multiSportsListener);

    void setOnGattCallbackListener(GattCallbackListener gattCallbackListener);

    void setOxygenChangeListener(OxygenChangeListener oxygenChangeListener);

    void setQuickReplySmsListener(QuickReplySmsListener quickReplySmsListener);

    void setSimpleCallbackListener(SimpleCallbackListener simpleCallbackListener);

    void setSleepChangeListener(SleepChangeListener sleepChangeListener);

    void setSongInformationToDevice(MusicPushInfo musicPushInfo);

    void setSosContactsListener(SosContactsListener sosContactsListener);

    void setSportsModeList(List<SportsModeInfo> list);

    void setStepChangeListener(StepChangeListener stepChangeListener);

    void setSuloseCommandListener(SuloseCommandListener suloseCommandListener);

    void setTemperatureChangeListener(TemperatureChangeListener temperatureChangeListener);

    void setTodayTarget(int i2, boolean z2, int i3);

    void setTodayTargetListener(TodayTargetListener todayTargetListener);

    void setUteAppKey(String str);

    void setWatchFaceCustomListener(WatchFaceCustomListener watchFaceCustomListener);

    void setWatchFaceMode(int i2);

    void setWatchFaceOnlineListener(WatchFaceOnlineListener watchFaceOnlineListener);

    void setWhichWatchFace(int i2);

    void showPrayer(boolean z2);

    void smsRemind(String str, String str2);

    void smsRemind(String str, String str2, String str3);

    void startElbpPpgSampling();

    void startSports(int i2, int i3);

    void startTestBloodPressure(boolean z2);

    void startTestBodyFat(BodyFatPersonInfo bodyFatPersonInfo);

    void startTestBreathingRate(boolean z2);

    void startTestEcg();

    void startTestElComplex(boolean z2);

    void startTestElHRV(boolean z2);

    void startTestElbs(boolean z2);

    void startTestHeartRate(boolean z2);

    void startTestMoodPressureFatigue(int i2, String str);

    void startTestOxygen(boolean z2);

    void startTestTemperature();

    void stopDeviceVibration();

    void stopElbpPpgSampling();

    void stopSendLocationData();

    void stopSports(int i2);

    void stopSyncWatchFaceData();

    void stopTestMoodPressureFatigue();

    void switchWatchFace(WatchFaceSerialNumberInfo watchFaceSerialNumberInfo);

    void syncAIAgentMemoToDevice(List<AIAgentMemoInfo> list, AIAgentMemoSyncListener aIAgentMemoSyncListener);

    void syncActivityTimeData();

    void syncBloodPressureData();

    void syncBloodPressureData(String str);

    void syncBodyFatData();

    void syncBodyFatData(String str);

    void syncBreathingRateData();

    void syncBreathingRateData(String str);

    void syncChatGptMemoToDevice(List<ChatGptMemoInfo> list, ChatGptMemoSyncListener chatGptMemoSyncListener);

    void syncContactsToDevice(List<ContactsInfo> list);

    void syncDeviceElbpMiddleData();

    void syncDeviceElbpPpgData();

    void syncDeviceParameters(DeviceParametersInfo deviceParametersInfo);

    void syncDeviceTime();

    void syncEcgData();

    void syncEcgHistorySamplingData();

    void syncEcgHistorySamplingData(String str);

    void syncGlucoseMetabolismSampledData();

    void syncHeartRateData();

    void syncHeartRateData(String str);

    void syncMoodPressureData();

    void syncMoodPressureData(String str);

    void syncMultipleSportsData();

    void syncMultipleSportsData(String str);

    void syncOxygenData();

    void syncOxygenData(String str);

    void syncQuickReplySmsContent(List<ReplySmsInfo> list);

    void syncRestHeartRateData();

    void syncSleepData();

    void syncSleepData(String str);

    void syncSosCallContacts(List<SosCallInfo> list);

    void syncStandingTimeData();

    void syncStepData();

    void syncStepData(String str);

    void syncTemperatureData();

    boolean syncWatchFaceOnlineData(String str);

    void temperatureAutomaticTest(boolean z2, int i2);

    void temperatureTimePeriod(boolean z2, int i2, int i3);

    void uploadEphemeris(@NonNull List<EphemerisFileInfo> list, FileService.MultiCallback multiCallback);

    void uploadImageWatchFace(@NonNull File file, int i2);

    void uploadWatchFace(File file);

    void washHandsRemind(WashHandsRemindInfo washHandsRemindInfo);

    void writeSuloseCommand60(byte[] bArr);

    void writeSuloseCommand61(byte[] bArr);
}
