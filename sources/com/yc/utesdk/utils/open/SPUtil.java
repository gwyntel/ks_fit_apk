package com.yc.utesdk.utils.open;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import androidx.exifinterface.media.ExifInterface;
import androidx.webkit.ProxyConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.AESUtils;
import com.yc.utesdk.watchface.bean.WatchFaceCustomInfo;
import com.yc.utesdk.watchface.bean.acts.DeviceWatchFaceDao;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFace;
import com.yc.utesdk.watchface.bean.acts.ImageWatchFaceConfig;
import com.yc.utesdk.watchface.bean.acts.WatchFaceParams;
import com.yc.utesdk.watchface.open.WatchFaceUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class SPUtil {
    public static final String LAST_CONNECT_DEVICE_ADDRESS_DEFAULT = "00:00:00:00:00:00";
    private static boolean SP_ENCRYPT = true;
    private static SPUtil instance;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private SharedPreferences sp;

    public SPUtil() {
        utendo();
    }

    public static SPUtil getInstance() {
        if (instance == null) {
            instance = new SPUtil();
        }
        return instance;
    }

    public static boolean getSpEncrypt() {
        return SP_ENCRYPT;
    }

    public static void initialize(Context context) {
        if (instance == null) {
            instance = new SPUtil(context);
        }
    }

    public static void setSpEncrypt(boolean z2) {
        SP_ENCRYPT = z2;
    }

    public void clearFunctionList() {
        setCharacterisicFunctionList1(0);
        setCharacterisicFunctionList2(0);
        setCharacterisicFunctionList3(0);
        setCharacterisicFunctionList4(0);
        setCharacterisicFunctionList5(0);
        setCharacterisicFunctionList6(0);
        setCharacterisicFunctionList7(0);
        setCharacterisicFunctionList8(0);
        setCharacterisicFunctionList9(0);
        setCharacterisicFunctionList10(0);
        setCharacterisicFunctionList11(0);
        setCharacterisicFunctionList12(0);
        setCharacterisicFunctionList13(0);
    }

    public int getBandDisplayMaxSports() {
        return utendo("BandDisplayMaxSports", 25);
    }

    public int getBandDisplayMinSports() {
        return utendo("BandDisplayMinSports", 4);
    }

    public int getBandLanguageDisplay1() {
        return utendo("BandLanguageDisplay1", 0);
    }

    public int getBandLanguageDisplay2() {
        return utendo("BandLanguageDisplay2", 0);
    }

    public int getBandLanguageDisplay3() {
        return utendo("BandLanguageDisplay3", 0);
    }

    public int getBandLanguageDisplay4() {
        return utendo("BandLanguageDisplay4", 0);
    }

    public int getBandLanguageDisplay5() {
        return utendo("BandLanguageDisplay5", 0);
    }

    public int getBandSupportMaxSports() {
        return utendo("BandSupportMaxSports", 115);
    }

    public String getBpCalibrateCO() {
        return utendo("bp_calibrate_co", "");
    }

    public int getBreathingRateAutomaticEndTime() {
        return utendo("breathing_rate_automatic_end_time", 1260);
    }

    public int getBreathingRateAutomaticStartTime() {
        return utendo("breathing_rate_automatic_start_time", 480);
    }

    public int getBreathingRateAutomaticTestInterval() {
        return utendo("breathing_rate_automatic_test_interval", 10);
    }

    public boolean getBreathingRateAutomaticTestSwitch() {
        return utendo("breathing_rate_automatic_test_switch", false);
    }

    public boolean getBreathingRateTimePeriodSwitch() {
        return utendo("breathing_rate_time_period_switch", false);
    }

    public int getBrightScreenFromHour() {
        return utendo("bright_screen_from_hour_sp", 22);
    }

    public int getBrightScreenFromMinute() {
        return utendo("bright_screen_from_minute_sp", 0);
    }

    public int getBrightScreenSwitch() {
        return utendo("bright_screen_switch_sp", 0);
    }

    public int getBrightScreenToHour() {
        return utendo("bright_screen_to_hour_sp", 8);
    }

    public int getBrightScreenToMinute() {
        return utendo("bright_screen_to_minute_sp", 0);
    }

    public String getCallSmsPhoneNumber() {
        return utendo("call_sms_phone_number_sp", "");
    }

    public int getCelsiusFahrenheitValue() {
        return utendo("celsius_fahrenheit_value", 1);
    }

    public int getCharacterisicFunctionList1() {
        return utendo("characteristic_function_list_sp_1", 0);
    }

    public int getCharacterisicFunctionList10() {
        return utendo("characteristic_function_list_sp_10", 0);
    }

    public int getCharacterisicFunctionList11() {
        return utendo("characteristic_function_list_sp_11", 0);
    }

    public int getCharacterisicFunctionList12() {
        return utendo("characteristic_function_list_sp_12", 0);
    }

    public int getCharacterisicFunctionList13() {
        return utendo("characteristic_function_list_sp_13", 0);
    }

    public int getCharacterisicFunctionList2() {
        return utendo("characteristic_function_list_sp_2", 0);
    }

    public int getCharacterisicFunctionList3() {
        return utendo("characteristic_function_list_sp_3", 0);
    }

    public int getCharacterisicFunctionList4() {
        return utendo("characteristic_function_list_sp_4", 0);
    }

    public int getCharacterisicFunctionList5() {
        return utendo("characteristic_function_list_sp_5", 0);
    }

    public int getCharacterisicFunctionList6() {
        return utendo("characteristic_function_list_sp_6", 0);
    }

    public int getCharacterisicFunctionList7() {
        return utendo("characteristic_function_list_sp_7", 0);
    }

    public int getCharacterisicFunctionList8() {
        return utendo("characteristic_function_list_sp_8", 0);
    }

    public int getCharacterisicFunctionList9() {
        return utendo("characteristic_function_list_sp_9", 0);
    }

    public String getDeviceFirmware() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmwareVersion = getDeviceFirmwareVersion();
        return (!deviceFirmwareVersion.contains(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) || deviceFirmwareVersion.length() <= 2) ? "RH110" : deviceFirmwareVersion.substring(0, deviceFirmwareVersion.indexOf(ExifInterface.GPS_MEASUREMENT_INTERRUPTED));
    }

    public String getDeviceFirmwareVersion() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        return utendo("Device_Firmware_Version_sp", "0");
    }

    public int getDeviceHourFormat() {
        return utendo("set_Device_Hour_Format", 1);
    }

    public int getDeviceLostSwitch() {
        return utendo("device_lost_switch_sp", 0);
    }

    public String getDevicePatchVersion() {
        return utendo("Device_Patch_Version_sp", "00000");
    }

    public String getDeviceUiVersion() {
        return utendo("Device_Ui_Version_sp", "0");
    }

    public int getDeviceUnit() {
        return utendo("set_Device_Unit", 1);
    }

    public String getDialMaxDataSize() {
        return utendo("dial_max_data_size", "1048576");
    }

    public int getDialNumber() {
        return utendo("dial_number_sp", 0);
    }

    public int getDialScreenCompatibleLevel() {
        return utendo("dialScreen_compatibility_level", 0);
    }

    public int getDialScreenCornerAngle() {
        return utendo("dial_screen_corner_angle", 0);
    }

    public int getDoNotDisturbFromHour() {
        return utendo("do_not_disturb_from_hour_sp", 22);
    }

    public int getDoNotDisturbFromMinute() {
        return utendo("do_not_disturb_from_minute_sp", 0);
    }

    public int getDoNotDisturbMessageSwitch() {
        return utendo("do_not_disturb_message_switch_sp", 0);
    }

    public int getDoNotDisturbMotorSwitch() {
        return utendo("do_not_disturb_motor_switch_sp", 0);
    }

    public int getDoNotDisturbTimeSwitch() {
        return utendo("do_not_disturb_time_switch_sp", 0);
    }

    public int getDoNotDisturbToHour() {
        return utendo("do_not_disturb_to_hour_sp", 8);
    }

    public int getDoNotDisturbToMinute() {
        return utendo("do_not_disturb_to_minute_sp", 0);
    }

    public int getDrinkWaterLunchBreakNoRemind() {
        return utendo("DrinkWater_lunch_break_noremind", 1);
    }

    public int getDrinkWaterNoRemindFromTimeHour() {
        return utendo("DrinkWater_noremind_from_time_hour", 12);
    }

    public int getDrinkWaterNoRemindFromTimeMinute() {
        return utendo("DrinkWater_noremind_from_time_minute", 0);
    }

    public int getDrinkWaterNoRemindToTimeHour() {
        return utendo("DrinkWater_noremind_to_time_hour", 14);
    }

    public int getDrinkWaterNoRemindToTimeMinute() {
        return utendo("DrinkWater_noremind_to_time_minute", 0);
    }

    public int getDrinkWaterRemindFromTimeHour() {
        return utendo("DrinkWater_remind_from_time_hour", 8);
    }

    public int getDrinkWaterRemindFromTimeMinute() {
        return utendo("DrinkWater_remind_from_time_minute", 0);
    }

    public int getDrinkWaterRemindInterval() {
        return utendo("DrinkWater_remind_interval", 60);
    }

    public int getDrinkWaterRemindSwitch() {
        return utendo("DrinkWater_remind_switch", 0);
    }

    public int getDrinkWaterRemindToTimeHour() {
        return utendo("DrinkWater_remind_to_time_hour", 22);
    }

    public int getDrinkWaterRemindToTimeMinute() {
        return utendo("DrinkWater_remind_to_time_minute", 0);
    }

    public SharedPreferences.Editor getEditor() {
        return this.editor;
    }

    public int getEndCallSwitch() {
        return utendo("end_call_switch_sp", 1);
    }

    public int getFatigueInterfaceSwitch() {
        return utendo("fatigue_interface_switch_sp", 1);
    }

    public int getFatigueValueSp() {
        return utendo("fatigue_value_sp", -1);
    }

    public ImageWatchFace getGetImageWatchFaceSp() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("get_image_watch_face_sp", "");
        if (!TextUtils.isEmpty(strUtendo)) {
            try {
                return (ImageWatchFace) new Gson().fromJson(strUtendo, ImageWatchFace.class);
            } catch (Exception unused) {
            }
        }
        return new ImageWatchFace();
    }

    public int getHighestRate() {
        return utendo("highest_rate_sp", 150);
    }

    public int getHighestRateSwitch() {
        return utendo("highest_rate_switch_sp", 0);
    }

    public String getLastConnectDeviceAddress() {
        return utendo("last_connect_device_address", "00:00:00:00:00:00");
    }

    public int getLowestRate() {
        return utendo("lowest_rate_sp", 40);
    }

    public int getLowestRateSwitch() {
        return utendo("lowest_rate_switch_sp", 0);
    }

    public int getMaxCommunicationLength() {
        return utendo("max_communication_length_sp", 0);
    }

    public boolean getMaxMinTemperatureAlarmSwitch() {
        return utendo("max_min_temperature_alarm_switch", false);
    }

    public int getMaxOnlineWatchFaceCount() {
        return utendo("max_online_watch_face_count", 1);
    }

    public float getMaxTemperatureAlarm() {
        return utendo("max_temperature_alarm", 37.3f);
    }

    public float getMinTemperatureAlarm() {
        return utendo("min_temperature_alarm", 0.0f);
    }

    public String getMoodActivationCodeSp() {
        return utendo("mood_activation_code", "");
    }

    public int getMoodAutoEndTime() {
        return utendo("mood_auto_end_time_sp", 1260);
    }

    public int getMoodAutoInterval() {
        return utendo("mood_auto_interval_sp", 10);
    }

    public int getMoodAutoStartTime() {
        return utendo("mood_auto_start_time_sp", 480);
    }

    public boolean getMoodAutoSwitch() {
        return utendo("mood_auto_switch_sp", false);
    }

    public int getMoodInterfaceSwitch() {
        return utendo("mood_interface_switch_sp", 1);
    }

    public String getMoodOpenId() {
        return utendo("mood_open_id_sp", "e19d340fb9a9b09babd2c9a2c33ae203od");
    }

    public int getMoodPressureFatigueTestType() {
        return utendo("mood_pressure_fatigue_test_type_sp", 0);
    }

    public String getMoodSensorType() {
        return utendo("mood_sensor_type_sp", "VC32");
    }

    public int getMoodTestResultStatusSp() {
        return utendo("mood_test_result_status_sp", 0);
    }

    public boolean getMoodTimePeriodSwitch() {
        return utendo("mood_time_period_switch_sp", false);
    }

    public int getMoodValueSp() {
        return utendo("mood_value_sp", -1);
    }

    public int getMultiSportHighestHeartRate() {
        return utendo("multi_sport_highest_heart_rate", 150);
    }

    public int getMultiSportHighestHeartRateSwitch() {
        return utendo("multi_sport_highest_heart_rate_switch", 0);
    }

    public int getMultiSportLowestHeartRate() {
        return utendo("multi_sport_lowest_heart_rate", 50);
    }

    public int getMultiSportLowestHeartRateSwitch() {
        return utendo("multi_sport_lowest_heart_rate_switch", 0);
    }

    public boolean getNeedToRegenerateRandom63E() {
        return utendo("need_to_regenerate_random_63e_sp", true);
    }

    public int getOffScreenTime() {
        return utendo("off_screen_time", 5);
    }

    public int getOnlySupportEnCn() {
        return utendo("only_support_en_cn", 2);
    }

    public int getOxygenAutomaticEndTime() {
        return utendo("oxygen_automatic_end_time", 1260);
    }

    public int getOxygenAutomaticStartTime() {
        return utendo("oxygen_automatic_start_time", 480);
    }

    public int getOxygenAutomaticTestInterval() {
        return utendo("oxygen_automatic_test_interval", 10);
    }

    public boolean getOxygenAutomaticTestSwitch() {
        return utendo("oxygen_automatic_test_switch", false);
    }

    public boolean getOxygenTimePeriodSwitch() {
        return utendo("oxygen_time_period_switch", false);
    }

    public int getPersonageAge() {
        return utendo("personage_age_sp", 20);
    }

    public int getPersonageGender() {
        return utendo("personage_gender_sp", 1);
    }

    public int getPersonageHeight() {
        return utendo("personage_height", 170);
    }

    public int getPersonageStepLength() {
        return utendo("personage_step_length", 71);
    }

    public float getPersonageWeight() {
        return utendo("personage_body_weight", 60.0f);
    }

    public int getPressureInterfaceSwitch() {
        return utendo("pressure_interface_switch_sp", 1);
    }

    public int getPressureValueSp() {
        return utendo("pressure_value_sp", -1);
    }

    public int getPushMessageDisplay1() {
        return utendo("PushMessageDisplay1", 0);
    }

    public int getPushMessageDisplay2() {
        return utendo("PushMessageDisplay2", 0);
    }

    public int getPushMessageDisplay3() {
        return utendo("PushMessageDisplay3", 0);
    }

    public int getRaiseHandBrightScreenSwitch() {
        return utendo("raise_hand_bright_screen_switch_sp", 1);
    }

    public byte[] getRandomArrayBt3(byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("random_array_bt3", "");
        return TextUtils.isEmpty(strUtendo) ? bArr : GBUtils.getInstance().hexStringToBytes(strUtendo);
    }

    public int getRemindMessageType() {
        return utendo("remind_message_type_sp", 4);
    }

    public int getResolutionHeight() {
        String resolutionWidthHeight = getResolutionWidthHeight();
        if (resolutionWidthHeight.contains(ProxyConfig.MATCH_ALL_SCHEMES)) {
            return Integer.valueOf(resolutionWidthHeight.split("\\*")[1]).intValue();
        }
        return 240;
    }

    public int getResolutionWidth() {
        String resolutionWidthHeight = getResolutionWidthHeight();
        if (resolutionWidthHeight.contains(ProxyConfig.MATCH_ALL_SCHEMES)) {
            return Integer.valueOf(resolutionWidthHeight.split("\\*")[0]).intValue();
        }
        return 240;
    }

    public String getResolutionWidthHeight() {
        return utendo("resolution_width_height", WatchFaceUtil.WATCH_FACE_DPI_240x240);
    }

    public int getSedentaryLunchBreakNoRemind() {
        return utendo("sedentary_lunch_break_noremind", 1);
    }

    public int getSedentaryNoRemindFromTimeHour() {
        return utendo("sedentary_noremind_from_time_hour", 12);
    }

    public int getSedentaryNoRemindFromTimeMinute() {
        return utendo("sedentary_noremind_from_time_minute", 0);
    }

    public int getSedentaryNoRemindToTimeHour() {
        return utendo("sedentary_noremind_to_time_hour", 14);
    }

    public int getSedentaryNoRemindToTimeMinute() {
        return utendo("sedentary_noremind_to_time_minute", 0);
    }

    public int getSedentaryRemindFromTimeHour() {
        return utendo("sedentary_remind_from_time_hour", 8);
    }

    public int getSedentaryRemindFromTimeMinute() {
        return utendo("sedentary_remind_from_time_minute", 0);
    }

    public int getSedentaryRemindInterval() {
        return utendo("sedentary_remind_interval", 60);
    }

    public int getSedentaryRemindSwitch() {
        return utendo("sedentary_remind_switch", 0);
    }

    public int getSedentaryRemindToTimeHour() {
        return utendo("sedentary_remind_to_time_hour", 22);
    }

    public int getSedentaryRemindToTimeMinute() {
        return utendo("sedentary_remind_to_time_minute", 0);
    }

    public ImageWatchFaceConfig getSetImageWatchFaceSp() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("set_image_watch_face_sp", "");
        if (!TextUtils.isEmpty(strUtendo)) {
            try {
                return (ImageWatchFaceConfig) new Gson().fromJson(strUtendo, ImageWatchFaceConfig.class);
            } catch (Exception unused) {
            }
        }
        return new ImageWatchFaceConfig();
    }

    public String getSmsReceivedNameOrNumber() {
        return utendo("sms_received_number", "");
    }

    public SharedPreferences getSp() {
        return this.sp;
    }

    public boolean getStartTestBodyFatSwitch() {
        return utendo("start_test_body_fat_switch_sp", false);
    }

    public int getStepTask() {
        return utendo("step_task_sp", 8000);
    }

    public int getTemperatureAutomaticEndTime() {
        return utendo("temperature_automatic_end_time", 1260);
    }

    public int getTemperatureAutomaticStartTime() {
        return utendo("temperature_automatic_start_time", 480);
    }

    public int getTemperatureAutomaticTestInterval() {
        return utendo("temperature_automatic_test_interval", 10);
    }

    public boolean getTemperatureAutomaticTestSwitch() {
        return utendo("temperature_automatic_test_switch", false);
    }

    public boolean getTemperatureTimePeriodSwitch() {
        return utendo("temperature_time_period_switch", false);
    }

    public int getWashHandsLunchBreakNoRemind() {
        return utendo("WashHands_lunch_break_noremind", 1);
    }

    public int getWashHandsNoRemindFromTimeHour() {
        return utendo("WashHands_noremind_from_time_hour", 12);
    }

    public int getWashHandsNoRemindFromTimeMinute() {
        return utendo("WashHands_noremind_from_time_minute", 0);
    }

    public int getWashHandsNoRemindToTimeHour() {
        return utendo("WashHands_noremind_to_time_hour", 14);
    }

    public int getWashHandsNoRemindToTimeMinute() {
        return utendo("WashHands_noremind_to_time_minute", 0);
    }

    public int getWashHandsRemindFromTimeHour() {
        return utendo("WashHands_remind_from_time_hour", 8);
    }

    public int getWashHandsRemindFromTimeMinute() {
        return utendo("WashHands_remind_from_time_minute", 0);
    }

    public int getWashHandsRemindInterval() {
        return utendo("WashHands_remind_interval", 60);
    }

    public int getWashHandsRemindSwitch() {
        return utendo("WashHands_remind_switch", 0);
    }

    public int getWashHandsRemindToTimeHour() {
        return utendo("WashHands_remind_to_time_hour", 22);
    }

    public int getWashHandsRemindToTimeMinute() {
        return utendo("WashHands_remind_to_time_minute", 0);
    }

    public boolean getWatchFaceConfigurationSuccess() {
        return utendo("watch_face_configuration_success", false);
    }

    public List<WatchFaceCustomInfo> getWatchFaceCustomInfo() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("get_image_watch_face_default_bg_result", "");
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(strUtendo)) {
            return arrayList;
        }
        try {
            return (List) new Gson().fromJson(strUtendo, new TypeToken<List<WatchFaceCustomInfo>>() { // from class: com.yc.utesdk.utils.open.SPUtil.3
            }.getType());
        } catch (Exception unused) {
            return arrayList;
        }
    }

    public List<DeviceWatchFaceDao> getWatchFaceInfo() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("WatchFaceInfo", "");
        if (TextUtils.isEmpty(strUtendo)) {
            return null;
        }
        return (List) new Gson().fromJson(strUtendo, new TypeToken<List<DeviceWatchFaceDao>>() { // from class: com.yc.utesdk.utils.open.SPUtil.2
        }.getType());
    }

    public WatchFaceParams getWatchFaceParams() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String strUtendo = utendo("WatchFaceParams", "");
        if (TextUtils.isEmpty(strUtendo)) {
            return null;
        }
        return (WatchFaceParams) new Gson().fromJson(strUtendo, new TypeToken<WatchFaceParams>() { // from class: com.yc.utesdk.utils.open.SPUtil.1
        }.getType());
    }

    public int getWatchFaceShapeType() {
        return utendo("watch_face_shape_type", 2);
    }

    public int getYcPedLastHourValue() {
        return utendo("last_hour_value", 0);
    }

    public void removeDeviceFirmwareVersion() {
        utendo("Device_Firmware_Version_sp");
    }

    public void setBandDisplayMaxSports(int i2) {
        utenif("BandDisplayMaxSports", i2);
    }

    public void setBandDisplayMinSports(int i2) {
        utenif("BandDisplayMinSports", i2);
    }

    public void setBandLanguageDisplay1(int i2) {
        utenif("BandLanguageDisplay1", i2);
    }

    public void setBandLanguageDisplay2(int i2) {
        utenif("BandLanguageDisplay2", i2);
    }

    public void setBandLanguageDisplay3(int i2) {
        utenif("BandLanguageDisplay3", i2);
    }

    public void setBandLanguageDisplay4(int i2) {
        utenif("BandLanguageDisplay4", i2);
    }

    public void setBandLanguageDisplay5(int i2) {
        utenif("BandLanguageDisplay5", i2);
    }

    public void setBandSupportMaxSports(int i2) {
        utenif("BandSupportMaxSports", i2);
    }

    public void setBpCalibrateCO(String str) {
        utenif("bp_calibrate_co", str);
    }

    public void setBreathingRateAutomaticEndTime(int i2) {
        utenif("breathing_rate_automatic_end_time", i2);
    }

    public void setBreathingRateAutomaticStartTime(int i2) {
        utenif("breathing_rate_automatic_start_time", i2);
    }

    public void setBreathingRateAutomaticTestInterval(int i2) {
        utenif("breathing_rate_automatic_test_interval", i2);
    }

    public void setBreathingRateAutomaticTestSwitch(boolean z2) {
        utenif("breathing_rate_automatic_test_switch", z2);
    }

    public void setBreathingRateTimePeriodSwitch(boolean z2) {
        utenif("breathing_rate_time_period_switch", z2);
    }

    public void setBrightScreenFromHour(int i2) {
        utenif("bright_screen_from_hour_sp", i2);
    }

    public void setBrightScreenFromMinute(int i2) {
        utenif("bright_screen_from_minute_sp", i2);
    }

    public void setBrightScreenSwitch(int i2) {
        utenif("bright_screen_switch_sp", i2);
    }

    public void setBrightScreenToHour(int i2) {
        utenif("bright_screen_to_hour_sp", i2);
    }

    public void setBrightScreenToMinute(int i2) {
        utenif("bright_screen_to_minute_sp", i2);
    }

    public void setCallSmsPhoneNumber(String str) {
        utenif("call_sms_phone_number_sp", str);
    }

    public void setCelsiusFahrenheitValue(int i2) {
        utenif("celsius_fahrenheit_value", i2);
    }

    public void setCharacterisicFunctionList1(int i2) {
        utenif("characteristic_function_list_sp_1", i2);
    }

    public void setCharacterisicFunctionList10(int i2) {
        utenif("characteristic_function_list_sp_10", i2);
    }

    public void setCharacterisicFunctionList11(int i2) {
        utenif("characteristic_function_list_sp_11", i2);
    }

    public void setCharacterisicFunctionList12(int i2) {
        utenif("characteristic_function_list_sp_12", i2);
    }

    public void setCharacterisicFunctionList13(int i2) {
        utenif("characteristic_function_list_sp_13", i2);
    }

    public void setCharacterisicFunctionList2(int i2) {
        utenif("characteristic_function_list_sp_2", i2);
    }

    public void setCharacterisicFunctionList3(int i2) {
        utenif("characteristic_function_list_sp_3", i2);
    }

    public void setCharacterisicFunctionList4(int i2) {
        utenif("characteristic_function_list_sp_4", i2);
    }

    public void setCharacterisicFunctionList5(int i2) {
        utenif("characteristic_function_list_sp_5", i2);
    }

    public void setCharacterisicFunctionList6(int i2) {
        utenif("characteristic_function_list_sp_6", i2);
    }

    public void setCharacterisicFunctionList7(int i2) {
        utenif("characteristic_function_list_sp_7", i2);
    }

    public void setCharacterisicFunctionList8(int i2) {
        utenif("characteristic_function_list_sp_8", i2);
    }

    public void setCharacterisicFunctionList9(int i2) {
        utenif("characteristic_function_list_sp_9", i2);
    }

    public void setDeviceFirmwareVersion(String str) {
        utenif("Device_Firmware_Version_sp", str);
    }

    public void setDeviceHourFormat(int i2) {
        utenif("set_Device_Hour_Format", i2);
    }

    public void setDeviceLostSwitch(int i2) {
        utenif("device_lost_switch_sp", i2);
    }

    public void setDevicePatchVersion(String str) {
        utenif("Device_Patch_Version_sp", str);
    }

    public void setDeviceUiVersion(String str) {
        utenif("Device_Ui_Version_sp", str);
    }

    public void setDeviceUnit(int i2) {
        utenif("set_Device_Unit", i2);
    }

    public void setDialMaxDataSize(String str) {
        utenif("dial_max_data_size", str);
    }

    public void setDialNumber(int i2) {
        utenif("dial_number_sp", i2);
    }

    public void setDialScreenCompatibleLevel(int i2) {
        utenif("dialScreen_compatibility_level", i2);
    }

    public void setDialScreenCornerAngle(int i2) {
        utenif("dial_screen_corner_angle", i2);
    }

    public void setDoNotDisturbFromHour(int i2) {
        utenif("do_not_disturb_from_hour_sp", i2);
    }

    public void setDoNotDisturbFromMinute(int i2) {
        utenif("do_not_disturb_from_minute_sp", i2);
    }

    public void setDoNotDisturbMessageSwitch(int i2) {
        utenif("do_not_disturb_message_switch_sp", i2);
    }

    public void setDoNotDisturbMotorSwitch(int i2) {
        utenif("do_not_disturb_motor_switch_sp", i2);
    }

    public void setDoNotDisturbTimeSwitch(int i2) {
        utenif("do_not_disturb_time_switch_sp", i2);
    }

    public void setDoNotDisturbToHour(int i2) {
        utenif("do_not_disturb_to_hour_sp", i2);
    }

    public void setDoNotDisturbToMinute(int i2) {
        utenif("do_not_disturb_to_minute_sp", i2);
    }

    public void setDrinkWaterLunchBreakNoRemind(int i2) {
        utenif("DrinkWater_lunch_break_noremind", i2);
    }

    public void setDrinkWaterNoRemindFromTimeHour(int i2) {
        utenif("DrinkWater_noremind_from_time_hour", i2);
    }

    public void setDrinkWaterNoRemindFromTimeMinute(int i2) {
        utenif("DrinkWater_noremind_from_time_minute", i2);
    }

    public void setDrinkWaterNoRemindToTimeHour(int i2) {
        utenif("DrinkWater_noremind_to_time_hour", i2);
    }

    public void setDrinkWaterNoRemindToTimeMinute(int i2) {
        utenif("DrinkWater_noremind_to_time_minute", i2);
    }

    public void setDrinkWaterRemindFromTimeHour(int i2) {
        utenif("DrinkWater_remind_from_time_hour", i2);
    }

    public void setDrinkWaterRemindFromTimeMinute(int i2) {
        utenif("DrinkWater_remind_from_time_minute", i2);
    }

    public void setDrinkWaterRemindInterval(int i2) {
        utenif("DrinkWater_remind_interval", i2);
    }

    public void setDrinkWaterRemindSwitch(int i2) {
        utenif("DrinkWater_remind_switch", i2);
    }

    public void setDrinkWaterRemindToTimeHour(int i2) {
        utenif("DrinkWater_remind_to_time_hour", i2);
    }

    public void setDrinkWaterRemindToTimeMinute(int i2) {
        utenif("DrinkWater_remind_to_time_minute", i2);
    }

    public void setEndCallSwitch(int i2) {
        utenif("end_call_switch_sp", i2);
    }

    public void setFatigueInterfaceSwitch(int i2) {
        utenif("fatigue_interface_switch_sp", i2);
    }

    public void setFatigueValueSp(int i2) {
        utenif("fatigue_value_sp", i2);
    }

    public void setGetImageWatchFaceSp(ImageWatchFace imageWatchFace) {
        utenif("get_image_watch_face_sp", imageWatchFace != null ? new Gson().toJson(imageWatchFace) : "");
    }

    public void setHighestRate(int i2) {
        utenif("highest_rate_sp", i2);
    }

    public void setHighestRateSwitch(int i2) {
        utenif("highest_rate_switch_sp", i2);
    }

    public void setLastConnectDeviceAddress(String str) {
        utenif("last_connect_device_address", str);
    }

    public void setLowestRate(int i2) {
        utenif("lowest_rate_sp", i2);
    }

    public void setLowestRateSwitch(int i2) {
        utenif("lowest_rate_switch_sp", i2);
    }

    public void setMaxCommunicationLength(int i2) {
        utenif("max_communication_length_sp", i2);
    }

    public void setMaxMinTemperatureAlarmSwitch(boolean z2) {
        utenif("max_min_temperature_alarm_switch", z2);
    }

    public void setMaxOnlineWatchFaceCount(int i2) {
        utenif("max_online_watch_face_count", i2);
    }

    public void setMaxTemperatureAlarm(float f2) {
        utenif("max_temperature_alarm", f2);
    }

    public void setMinTemperatureAlarm(float f2) {
        utenif("min_temperature_alarm", f2);
    }

    public void setMoodActivationCodeSp(String str) {
        utenif("mood_activation_code", str);
    }

    public void setMoodAutoEndTime(int i2) {
        utenif("mood_auto_end_time_sp", i2);
    }

    public void setMoodAutoInterval(int i2) {
        utenif("mood_auto_interval_sp", i2);
    }

    public void setMoodAutoStartTime(int i2) {
        utenif("mood_auto_start_time_sp", i2);
    }

    public void setMoodAutoSwitch(boolean z2) {
        utenif("mood_auto_switch_sp", z2);
    }

    public void setMoodInterfaceSwitch(int i2) {
        utenif("mood_interface_switch_sp", i2);
    }

    public void setMoodOpenId(String str) {
        utenif("mood_open_id_sp", str);
    }

    public void setMoodPressureFatigueTestType(int i2) {
        utenif("mood_pressure_fatigue_test_type_sp", i2);
    }

    public void setMoodSensorType(String str) {
        utenif("mood_sensor_type_sp", str);
    }

    public void setMoodTestResultStatusSp(int i2) {
        utenif("mood_test_result_status_sp", i2);
    }

    public void setMoodTimePeriodSwitch(boolean z2) {
        utenif("mood_time_period_switch_sp", z2);
    }

    public void setMoodValueSp(int i2) {
        utenif("mood_value_sp", i2);
    }

    public void setMultiSportHighestHeartRate(int i2) {
        utenif("multi_sport_highest_heart_rate", i2);
    }

    public void setMultiSportHighestHeartRateSwitch(int i2) {
        utenif("multi_sport_highest_heart_rate_switch", i2);
    }

    public void setMultiSportLowestHeartRate(int i2) {
        utenif("multi_sport_lowest_heart_rate", i2);
    }

    public void setMultiSportLowestHeartRateSwitch(int i2) {
        utenif("multi_sport_lowest_heart_rate_switch", i2);
    }

    public void setNeedToRegenerateRandom63E(boolean z2) {
        utenif("need_to_regenerate_random_63e_sp", z2);
    }

    public void setOffScreenTime(int i2) {
        utenif("off_screen_time", i2);
    }

    public void setOnlySupportEnCn(int i2) {
        utenif("only_support_en_cn", i2);
    }

    public void setOxygenAutomaticEndTime(int i2) {
        utenif("oxygen_automatic_end_time", i2);
    }

    public void setOxygenAutomaticStartTime(int i2) {
        utenif("oxygen_automatic_start_time", i2);
    }

    public void setOxygenAutomaticTestInterval(int i2) {
        utenif("oxygen_automatic_test_interval", i2);
    }

    public void setOxygenAutomaticTestSwitch(boolean z2) {
        utenif("oxygen_automatic_test_switch", z2);
    }

    public void setOxygenTimePeriodSwitch(boolean z2) {
        utenif("oxygen_time_period_switch", z2);
    }

    public void setPersonageAge(int i2) {
        utenif("personage_age_sp", i2);
    }

    public void setPersonageGender(int i2) {
        utenif("personage_gender_sp", i2);
    }

    public void setPersonageHeight(int i2) {
        utenif("personage_height", i2);
    }

    public void setPersonageStepLength(int i2) {
        utenif("personage_step_length", i2);
    }

    public void setPersonageWeight(float f2) {
        utenif("personage_body_weight", f2);
    }

    public void setPressureInterfaceSwitch(int i2) {
        utenif("pressure_interface_switch_sp", i2);
    }

    public void setPressureValueSp(int i2) {
        utenif("pressure_value_sp", i2);
    }

    public void setPushMessageDisplay1(int i2) {
        utenif("PushMessageDisplay1", i2);
    }

    public void setPushMessageDisplay2(int i2) {
        utenif("PushMessageDisplay2", i2);
    }

    public void setPushMessageDisplay3(int i2) {
        utenif("PushMessageDisplay3", i2);
    }

    public void setRaiseHandBrightScreenSwitch(int i2) {
        utenif("raise_hand_bright_screen_switch_sp", i2);
    }

    public void setRandomArrayBt3(byte[] bArr) {
        utenif("random_array_bt3", GBUtils.getInstance().bytes2HexString(bArr));
    }

    public void setRemindMessageType(int i2) {
        utenif("remind_message_type_sp", i2);
    }

    public void setResolutionWidthHeight(String str) {
        utenif("resolution_width_height", str);
    }

    public void setSedentaryLunchBreakNoRemind(int i2) {
        utenif("sedentary_lunch_break_noremind", i2);
    }

    public void setSedentaryNoRemindFromTimeHour(int i2) {
        utenif("sedentary_noremind_from_time_hour", i2);
    }

    public void setSedentaryNoRemindFromTimeMinute(int i2) {
        utenif("sedentary_noremind_from_time_minute", i2);
    }

    public void setSedentaryNoRemindToTimeHour(int i2) {
        utenif("sedentary_noremind_to_time_hour", i2);
    }

    public void setSedentaryNoRemindToTimeMinute(int i2) {
        utenif("sedentary_noremind_to_time_minute", i2);
    }

    public void setSedentaryRemindFromTimeHour(int i2) {
        utenif("sedentary_remind_from_time_hour", i2);
    }

    public void setSedentaryRemindFromTimeMinute(int i2) {
        utenif("sedentary_remind_from_time_minute", i2);
    }

    public void setSedentaryRemindInterval(int i2) {
        utenif("sedentary_remind_interval", i2);
    }

    public void setSedentaryRemindSwitch(int i2) {
        utenif("sedentary_remind_switch", i2);
    }

    public void setSedentaryRemindToTimeHour(int i2) {
        utenif("sedentary_remind_to_time_hour", i2);
    }

    public void setSedentaryRemindToTimeMinute(int i2) {
        utenif("sedentary_remind_to_time_minute", i2);
    }

    public void setSetImageWatchFaceSp(ImageWatchFaceConfig imageWatchFaceConfig) {
        utenif("set_image_watch_face_sp", imageWatchFaceConfig != null ? new Gson().toJson(imageWatchFaceConfig) : "");
    }

    public void setSmsReceivedNameOrNumber(String str) {
        utenif("sms_received_number", str);
    }

    public void setStartTestBodyFatSwitch(boolean z2) {
        utenif("start_test_body_fat_switch_sp", z2);
    }

    public void setStepTask(int i2) {
        utenif("step_task_sp", i2);
    }

    public void setTemperatureAutomaticEndTime(int i2) {
        utenif("temperature_automatic_end_time", i2);
    }

    public void setTemperatureAutomaticStartTime(int i2) {
        utenif("temperature_automatic_start_time", i2);
    }

    public void setTemperatureAutomaticTestInterval(int i2) {
        utenif("temperature_automatic_test_interval", i2);
    }

    public void setTemperatureAutomaticTestSwitch(boolean z2) {
        utenif("temperature_automatic_test_switch", z2);
    }

    public void setTemperatureTimePeriodSwitch(boolean z2) {
        utenif("temperature_time_period_switch", z2);
    }

    public void setWashHandsLunchBreakNoRemind(int i2) {
        utenif("WashHands_lunch_break_noremind", i2);
    }

    public void setWashHandsNoRemindFromTimeHour(int i2) {
        utenif("WashHands_noremind_from_time_hour", i2);
    }

    public void setWashHandsNoRemindFromTimeMinute(int i2) {
        utenif("WashHands_noremind_from_time_minute", i2);
    }

    public void setWashHandsNoRemindToTimeHour(int i2) {
        utenif("WashHands_noremind_to_time_hour", i2);
    }

    public void setWashHandsNoRemindToTimeMinute(int i2) {
        utenif("WashHands_noremind_to_time_minute", i2);
    }

    public void setWashHandsRemindFromTimeHour(int i2) {
        utenif("WashHands_remind_from_time_hour", i2);
    }

    public void setWashHandsRemindFromTimeMinute(int i2) {
        utenif("WashHands_remind_from_time_minute", i2);
    }

    public void setWashHandsRemindInterval(int i2) {
        utenif("WashHands_remind_interval", i2);
    }

    public void setWashHandsRemindSwitch(int i2) {
        utenif("WashHands_remind_switch", i2);
    }

    public void setWashHandsRemindToTimeHour(int i2) {
        utenif("WashHands_remind_to_time_hour", i2);
    }

    public void setWashHandsRemindToTimeMinute(int i2) {
        utenif("WashHands_remind_to_time_minute", i2);
    }

    public void setWatchFaceConfigurationSuccess(boolean z2) {
        utenif("watch_face_configuration_success", z2);
    }

    public void setWatchFaceCustomInfo(List<WatchFaceCustomInfo> list) {
        utenif("get_image_watch_face_default_bg_result", list != null ? new Gson().toJson(list) : "");
    }

    public void setWatchFaceInfo(List<DeviceWatchFaceDao> list) {
        utenif("WatchFaceInfo", new Gson().toJson(list));
    }

    public void setWatchFaceParams(WatchFaceParams watchFaceParams) {
        utenif("WatchFaceParams", new Gson().toJson(watchFaceParams));
    }

    public void setWatchFaceShapeType(int i2) {
        if (i2 == 0) {
            i2 = 2;
        }
        utenif("watch_face_shape_type", i2);
    }

    public void setYcPedLastHourValue(int i2) {
        utenif("last_hour_value", i2);
    }

    public final Object utendo(String str, Object obj) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String string;
        String string2;
        if (!getSpEncrypt()) {
            return obj instanceof String ? this.sp.getString(str, (String) obj) : obj instanceof Boolean ? Boolean.valueOf(this.sp.getBoolean(str, ((Boolean) obj).booleanValue())) : obj instanceof Integer ? Integer.valueOf(this.sp.getInt(str, ((Integer) obj).intValue())) : obj instanceof Float ? Float.valueOf(this.sp.getFloat(str, ((Float) obj).floatValue())) : obj instanceof Long ? Long.valueOf(this.sp.getLong(str, ((Long) obj).longValue())) : obj;
        }
        String strByteToBase64Str = AESUtils.byteToBase64Str(AESUtils.encryptByAES(str.getBytes(), AESUtils.AES_KEY.getBytes()));
        if (obj instanceof String) {
            String str2 = (String) obj;
            String string3 = this.sp.getString(strByteToBase64Str, str2);
            return (string3 == null || TextUtils.isEmpty(string3) || string3.equals(str2)) ? obj : new String(AESUtils.decryptByAES(AESUtils.base64StrToByte(string3), AESUtils.AES_KEY.getBytes())).trim();
        }
        if (obj instanceof Boolean) {
            String string4 = Boolean.toString(((Boolean) obj).booleanValue());
            String string5 = this.sp.getString(strByteToBase64Str, string4);
            return (string5 == null || TextUtils.isEmpty(string5) || string5.equals(string4)) ? obj : Boolean.valueOf(new String(AESUtils.decryptByAES(AESUtils.base64StrToByte(string5), AESUtils.AES_KEY.getBytes())).trim());
        }
        if (obj instanceof Integer) {
            String string6 = Integer.toString(((Integer) obj).intValue());
            String string7 = this.sp.getString(strByteToBase64Str, string6);
            return (string7 == null || TextUtils.isEmpty(string7) || string7.equals(string6)) ? obj : Integer.valueOf(new String(AESUtils.decryptByAES(AESUtils.base64StrToByte(string7), AESUtils.AES_KEY.getBytes())).trim());
        }
        if (!(obj instanceof Float)) {
            return (!(obj instanceof Long) || (string2 = this.sp.getString(strByteToBase64Str, (string = Long.toString(((Long) obj).longValue())))) == null || TextUtils.isEmpty(string2) || string2.equals(string)) ? obj : Long.valueOf(new String(AESUtils.decryptByAES(AESUtils.base64StrToByte(string2), AESUtils.AES_KEY.getBytes())).trim());
        }
        String string8 = Float.toString(((Float) obj).floatValue());
        String string9 = this.sp.getString(strByteToBase64Str, string8);
        return (string9 == null || TextUtils.isEmpty(string9) || string9.equals(string8)) ? obj : Float.valueOf(new String(AESUtils.decryptByAES(AESUtils.base64StrToByte(string9), AESUtils.AES_KEY.getBytes())).trim());
    }

    public final void utenif(String str, Object obj) {
        SharedPreferences.Editor editorPutLong;
        if (getSpEncrypt()) {
            String string = obj instanceof String ? (String) obj : obj instanceof Boolean ? Boolean.toString(((Boolean) obj).booleanValue()) : obj instanceof Integer ? Integer.toString(((Integer) obj).intValue()) : obj instanceof Float ? Float.toString(((Float) obj).floatValue()) : obj instanceof Long ? Long.toString(((Long) obj).longValue()) : null;
            this.editor.putString(AESUtils.byteToBase64Str(AESUtils.encryptByAES(str.getBytes(), AESUtils.AES_KEY.getBytes())), (string == null || TextUtils.isEmpty(string)) ? "" : AESUtils.byteToBase64Str(AESUtils.encryptByAES(string.getBytes(), AESUtils.AES_KEY.getBytes()))).apply();
            return;
        }
        if (obj instanceof String) {
            editorPutLong = this.editor.putString(str, (String) obj);
        } else if (obj instanceof Boolean) {
            editorPutLong = this.editor.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Integer) {
            editorPutLong = this.editor.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Float) {
            editorPutLong = this.editor.putFloat(str, ((Float) obj).floatValue());
        } else if (!(obj instanceof Long)) {
            return;
        } else {
            editorPutLong = this.editor.putLong(str, ((Long) obj).longValue());
        }
        editorPutLong.apply();
    }

    public SPUtil(Context context) {
        this.mContext = context;
        utendo();
    }

    public final void utendo(String str) {
        if (getSpEncrypt()) {
            utenif(AESUtils.byteToBase64Str(AESUtils.encryptByAES(str.getBytes(), AESUtils.AES_KEY.getBytes())));
        } else {
            utenif(str);
        }
    }

    public final void utenif(String str) {
        this.editor.remove(str);
    }

    public final boolean utendo(String str, boolean z2) {
        return ((Boolean) utendo(str, Boolean.valueOf(z2))).booleanValue();
    }

    public final void utenif(String str, boolean z2) {
        utenif(str, Boolean.valueOf(z2));
    }

    public final float utendo(String str, float f2) {
        return ((Float) utendo(str, Float.valueOf(f2))).floatValue();
    }

    public final void utenif(String str, float f2) {
        utenif(str, Float.valueOf(f2));
    }

    public final int utendo(String str, int i2) {
        return ((Integer) utendo(str, Integer.valueOf(i2))).intValue();
    }

    public final void utenif(String str, int i2) {
        utenif(str, Integer.valueOf(i2));
    }

    public final String utendo(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        return (String) utendo(str, (Object) str2);
    }

    public final void utenif(String str, String str2) {
        utenif(str, (Object) str2);
    }

    public final void utendo() {
        Context context = this.mContext;
        if (context == null) {
            LogUtils.initializeLog("UteBleClient.initialize()");
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("ute_sdk_setting_sP", 0);
        this.sp = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }
}
