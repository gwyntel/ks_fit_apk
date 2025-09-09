package com.yc.utesdk.ble.open;

import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public class DeviceMode {
    public static final int IS_PLATFORM_DA = 256;
    public static final int IS_PLATFORM_FRK = 8192;
    public static final int IS_PLATFORM_JL = 16384;
    public static final int IS_PLATFORM_JL_AC707 = 262144;
    public static final int IS_PLATFORM_JX_3085L = 32768;
    public static final int IS_PLATFORM_JX_3085S = 65536;
    public static final int IS_PLATFORM_NRF = 1024;
    public static final int IS_PLATFORM_RK_RTL8773EWE = 131072;
    public static final int IS_PLATFORM_RTL8762Ax = 512;
    public static final int IS_PLATFORM_RTL8762Cx = 2048;
    public static final int IS_PLATFORM_ZK_AB569X = 524288;
    public static final int IS_SUPPORT_110_SPORTS_MODES = 2;
    public static final int IS_SUPPORT_24_HOUR_RATE_TEST = 16384;
    public static final int IS_SUPPORT_4G_MODULE = 131072;
    public static final int IS_SUPPORT_ACCOUNT_ID_AND_RANDOM_ID_BIND = 8388608;
    public static final int IS_SUPPORT_ACCOUNT_ID_BIND = 4;
    public static final int IS_SUPPORT_ACTICITY_TIME = 64;
    public static final int IS_SUPPORT_AGPS = 4194304;
    public static final int IS_SUPPORT_ALIPAY = 4194304;
    public static final int IS_SUPPORT_APGS = 1024;
    public static final int IS_SUPPORT_APP_CONTROL_MUSIC_SWITCH = 2;
    public static final int IS_SUPPORT_APP_SET_KEY = 8;
    public static final int IS_SUPPORT_AUTOTEST_HEARTRATE_INTERVAL = 16;
    public static final int IS_SUPPORT_BADMINTON = 1024;
    public static final int IS_SUPPORT_BAND_FIND_PHONE_FUNCTION = 32;
    public static final int IS_SUPPORT_BAND_LANGUAGE_DISPLAY = 256;
    public static final int IS_SUPPORT_BAND_LANGUAGE_FUNCTION = 8;
    public static final int IS_SUPPORT_BAND_LANGUAGE_FUNCTION_24 = 16384;
    public static final int IS_SUPPORT_BAND_LANGUAGE_FUNCTION_40 = 8388608;
    public static final int IS_SUPPORT_BAND_LOCAL_LANGUAGE = 1048576;
    public static final int IS_SUPPORT_BAND_LOST_FUNCTION = 4;
    public static final int IS_SUPPORT_BAND_QUICK_SWITCH_SETTING = 65536;
    public static final int IS_SUPPORT_BAND_SHOW_24HR_VALUE = 524288;
    public static final int IS_SUPPORT_BAND_SLEEP_ALGORITHM = 262144;
    public static final int IS_SUPPORT_BASEBALL = 4194304;
    public static final int IS_SUPPORT_BASKETBALL = 524288;
    public static final int IS_SUPPORT_BLE_ECG_ARITHMETIC = 128;
    public static final int IS_SUPPORT_BLOOD_PRESSURE_FUNCTION = 1048576;
    public static final int IS_SUPPORT_BLOOD_SUGAR_TEST = 131072;
    public static final int IS_SUPPORT_BLUETOOTH_DISCONNECT_REMINDER = 128;
    public static final int IS_SUPPORT_BOATING = 512;
    public static final int IS_SUPPORT_BODY_COMPOSITION = 64;
    public static final int IS_SUPPORT_BREATHE = 16384;
    public static final int IS_SUPPORT_BT3_0_AGREEMENT = 262144;
    public static final int IS_SUPPORT_CHAT_GPT = 2097152;
    public static final int IS_SUPPORT_COMPLEX_TEST = 524288;
    public static final int IS_SUPPORT_CONTACTS = 8192;
    public static final int IS_SUPPORT_CONTACT_SEND_FORMAT_UTE8 = 524288;
    public static final int IS_SUPPORT_CRICKET = 4;
    public static final int IS_SUPPORT_CSBP_CALIBRATION_PROTOCOL = 1048576;
    public static final int IS_SUPPORT_CSBP_EXTRA_FUNCTION = 1024;
    public static final int IS_SUPPORT_CS_ECG = 4096;
    public static final int IS_SUPPORT_CUSTOMIZED_BRACELET_INTERFACE = 32768;
    public static final int IS_SUPPORT_CUSTOMIZE_SMS_REPLY = 512;
    public static final int IS_SUPPORT_CUSTOMIZE_SPORTS_LISTS_SHOW = 8192;
    public static final int IS_SUPPORT_CUSTOMIZE_WIDGET = 16384;
    public static final int IS_SUPPORT_CUSTOM_SWITCH_SETTING = 256;
    public static final int IS_SUPPORT_CUSTOM_TIME_INTERVAL = 4096;
    public static final int IS_SUPPORT_CYWEE_SWIM_DATA = 512;
    public static final int IS_SUPPORT_DANCEING = 32;
    public static final int IS_SUPPORT_DELETE_ALL_CONTACTS = 1024;
    public static final int IS_SUPPORT_DEVICE_PARAM_QUERY_SET = 256;
    public static final int IS_SUPPORT_DEVICE_POWER_OFF = 8;
    public static final int IS_SUPPORT_DEVICE_POWER_SAVING_MODE = 65536;
    public static final int IS_SUPPORT_DEVICE_RESTART = 4096;
    public static final int IS_SUPPORT_DIAL_SWITCH = 2048;
    public static final int IS_SUPPORT_DO_NOT_DISTURB = 8;
    public static final int IS_SUPPORT_DRINK_WATER_REMIND = 524288;
    public static final int IS_SUPPORT_ECG = 8192;
    public static final int IS_SUPPORT_ELBP_MODULE = 16;
    public static final int IS_SUPPORT_END_CALL_FUNCTION = 4194304;
    public static final int IS_SUPPORT_FACTORY_TEST_MODE = 1048576;
    public static final int IS_SUPPORT_FOOTBALL = 2097152;
    public static final int IS_SUPPORT_FREE_TRAINING = 2097152;
    public static final int IS_SUPPORT_GLUCOSE_METABOLISM_ALGORITHM = 32768;
    public static final int IS_SUPPORT_GPS_FUNCTION = 2;
    public static final int IS_SUPPORT_GYMNASTICS = 256;
    public static final int IS_SUPPORT_HEART_RATE_DETECTION_CALIBRATION = 131072;
    public static final int IS_SUPPORT_HEART_RATE_FUNCTION = 524288;
    public static final int IS_SUPPORT_HEART_RATE_HEADSET = 32768;
    public static final int IS_SUPPORT_HEAR_RATE_5MINS = 512;
    public static final int IS_SUPPORT_HIKING = 131072;
    public static final int IS_SUPPORT_HOCKEY_SPORTS = 16;
    public static final int IS_SUPPORT_HOR_VER = 32;
    public static final int IS_SUPPORT_HRV_TEST = 262144;
    public static final int IS_SUPPORT_HR_SLEEP_ASSIST = 512;
    public static final int IS_SUPPORT_IBEACON = 4096;
    public static final int IS_SUPPORT_JUMPING_JACK = 1024;
    public static final int IS_SUPPORT_LABEL_ALARM_CLOCK = 131072;
    public static final int IS_SUPPORT_LABEL_ALARM_CLOCK_TYPE = 8192;
    public static final int IS_SUPPORT_LOCAL_ALARM_SETTING = 1;
    public static final int IS_SUPPORT_LOCAL_WATCH_FACE = 16;
    public static final int IS_SUPPORT_LONG_CONTENT_PUSH = 65536;
    public static final int IS_SUPPORT_LUNCH_BREAK_NOT_DISTURN_TIME_SET = 65536;
    public static final int IS_SUPPORT_MANY_WATCH_FACE = 4;
    public static final int IS_SUPPORT_MAX_HEAR_RATE_ALARM = 128;
    public static final int IS_SUPPORT_MESSAGE_REPLY = 131072;
    public static final int IS_SUPPORT_MIN_HEAR_RATE_ALARM = 2048;
    public static final int IS_SUPPORT_MODIFY_MAX_HEART_RATE = 4;
    public static final int IS_SUPPORT_MOOD_PRESSURE_FATIGUE = 32;
    public static final int IS_SUPPORT_MORE_FOREIGN_APP = 2048;
    public static final int IS_SUPPORT_MSG_PUSH_MAX_LENGTH_240 = 4194304;
    public static final int IS_SUPPORT_MULTIPLE_LANGUAGE = 4;
    public static final int IS_SUPPORT_MULTIPLE_SPORTS_MODES_HEART_RATE = 1;
    public static final int IS_SUPPORT_MULTIPLE_SPORT_TIME_PARSING = 262144;
    public static final int IS_SUPPORT_MUSIC_CONTENT_DISPLAY = 4096;
    public static final int IS_SUPPORT_MUSIC_CONTROL = 1;
    public static final int IS_SUPPORT_MUSIC_PLAYER = 8388608;
    public static final int IS_SUPPORT_MUTE_CALL_MODES = 64;
    public static final int IS_SUPPORT_NEW_PARAMETER_SETTINGS_FUNCTION = 1;
    public static final int IS_SUPPORT_NFC = 512;
    public static final int IS_SUPPORT_NOTIFY_BATTERY_CHANGE = 1024;
    public static final int IS_SUPPORT_NOTIFY_WATCH_FACE_INDEX = 512;
    public static final int IS_SUPPORT_ONLINE_DIAL = 256;
    public static final int IS_SUPPORT_ON_FOOT = 262144;
    public static final int IS_SUPPORT_ON_SCREEN_DURATION = 32;
    public static final int IS_SUPPORT_OXYGEN = 64;
    public static final int IS_SUPPORT_PASS_WORD_PAIR = 1;
    public static final int IS_SUPPORT_PHYSIOLOGICAL_PERIOD = 32768;
    public static final int IS_SUPPORT_PRAYER = 16384;
    public static final int IS_SUPPORT_PRESSURE_5_INTERVAL = 262144;
    public static final int IS_SUPPORT_PRESSURE_BLE4_REPORT = 524288;
    public static final int IS_SUPPORT_PUSH_MESSAGE_DISPLAY = 2048;
    public static final int IS_SUPPORT_QR_CODE_DISPLAY = 1;
    public static final int IS_SUPPORT_QUARTER_TIME_ZONE = 1;
    public static final int IS_SUPPORT_QUERY_ACTIVITY_TARGET_PARAM = 2048;
    public static final int IS_SUPPORT_QUERY_BAND_LANGUAGE = 32;
    public static final int IS_SUPPORT_QUERY_CUSTOMER_ID = 4;
    public static final int IS_SUPPORT_QUERY_PHYSIOLOGICAL_PARAM = 8192;
    public static final int IS_SUPPORT_QUERY_SMS_CONTENT = 4096;
    public static final int IS_SUPPORT_RAISE_HAND_BRIGHT = 64;
    public static final int IS_SUPPORT_RESET_HEART_RATE = 2;
    public static final int IS_SUPPORT_RIDE = 256;
    public static final int IS_SUPPORT_RUGBY = 8;
    public static final int IS_SUPPORT_RUNNING = 256;
    public static final int IS_SUPPORT_SEDENTARY_REMINDER = 65536;
    public static final int IS_SUPPORT_SET_TODAY_TARGET = 2097152;
    public static final int IS_SUPPORT_SEVEN_DAYS_WEATHER = 8192;
    public static final int IS_SUPPORT_SIT_TIME_FUNCTION = 16;
    public static final int IS_SUPPORT_SIT_UPS = 2;
    public static final int IS_SUPPORT_SKIP = 16384;
    public static final int IS_SUPPORT_SLEEP_ONE_HOUR_MERGE = 1024;
    public static final int IS_SUPPORT_SLEEP_REM = 8;
    public static final int IS_SUPPORT_SOS_CALL_CONTACTS_SWITCH = 64;
    public static final int IS_SUPPORT_SOS_CONTACTS = 2;
    public static final int IS_SUPPORT_SOS_RETURN_INSTRUCTION = 1048576;
    public static final int IS_SUPPORT_SPINNING_BIKE = 64;
    public static final int IS_SUPPORT_SPORTS_GPS = 128;
    public static final int IS_SUPPORT_SPORTS_TARGET_SET = 32768;
    public static final int IS_SUPPORT_SPORTS_TYPE_CALCULATION_FORMULA = 4096;
    public static final int IS_SUPPORT_SPORT_CONTROL = 2;
    public static final int IS_SUPPORT_SPORT_ELEVATION = 16384;
    public static final int IS_SUPPORT_SPORT_RECORD_REPORT_EXPANSION = 65536;
    public static final int IS_SUPPORT_STEP_CALORIES_CUMULATIVE = 4194304;
    public static final int IS_SUPPORT_SWIMMING = 16;
    public static final int IS_SUPPORT_SWITCH_BRACELET_LANGUAGE = 1024;
    public static final int IS_SUPPORT_SW_ALG = 32;
    public static final int IS_SUPPORT_SYNC_ALARM_CLOCK_SWITCH_STATUS = 256;
    public static final int IS_SUPPORT_SYNC_TIMESTAMP = 8192;
    public static final int IS_SUPPORT_TABLE_TENNIS = 512;
    public static final int IS_SUPPORT_TEMPERATURE_CALIBRATION = 32;
    public static final int IS_SUPPORT_TEMPERATURE_SAMPLING = 16;
    public static final int IS_SUPPORT_TEMPERATURE_TEST = 8;
    public static final int IS_SUPPORT_TEMPERATURE_UNIT_SWITCH = 4096;
    public static final int IS_SUPPORT_TENNIS = 2048;
    public static final int IS_SUPPORT_THREE_ALARM_CLOCK = 131072;
    public static final int IS_SUPPORT_TIME_ZONE_SETTING = 16;
    public static final int IS_SUPPORT_TRANSACTION_REMINDER = 262144;
    public static final int IS_SUPPORT_TREADMILL = 1;
    public static final int IS_SUPPORT_TURN_WRIST_CALIBRATION = 262144;
    public static final int IS_SUPPORT_TWO_DAY_WEATHER = 2;
    public static final int IS_SUPPORT_UI_OTA = 4194304;
    public static final int IS_SUPPORT_UNIVERSAL_INTERFACE_BIG_DATA = 131072;
    public static final int IS_SUPPORT_UV_FUNCTION = 2097152;
    public static final int IS_SUPPORT_VOLLEYBALL = 8388608;
    public static final int IS_SUPPORT_WASH_HANDS_REMINDER = 4;
    public static final int IS_SUPPORT_WEAHTER_CITY_NAME = 128;
    public static final int IS_SUPPORT_WEATHER_CODE_EXPAND = 16384;
    public static final int IS_SUPPORT_WEATHER_HUMIDITY_UV = 2048;
    public static final int IS_SUPPORT_WEATHER_NEXT_24HOURS = 256;
    public static final int IS_SUPPORT_WEIGHT_DECIMAL = 4;
    public static final int IS_SUPPORT_WIKI_EMOTIONAL_STRESS_ALGORITHM = 4194304;
    public static final int IS_SUPPORT_WRIST_DETECTION_SWITCH = 65536;
    public static final int IS_SUPPORT_YM_CALORIE_ALGORITHM = 32768;
    public static final int IS_SUPPORT_YOGA = 128;

    public static boolean isHasFunction_1(int i2) {
        int characterisicFunctionList1 = SPUtil.getInstance().getCharacterisicFunctionList1();
        boolean z2 = (characterisicFunctionList1 & i2) == i2;
        LogUtils.d("isSupport =" + z2 + ",functionType =" + i2 + ",function=" + characterisicFunctionList1);
        if (z2 || i2 != 524288) {
            if (!z2 && i2 == 1048576 && utendo()) {
                return true;
            }
        } else if (utenif() || utendo()) {
            return true;
        }
        return z2;
    }

    public static boolean isHasFunction_10(int i2) {
        int characterisicFunctionList10 = SPUtil.getInstance().getCharacterisicFunctionList10();
        boolean z2 = (characterisicFunctionList10 & i2) == i2;
        LogUtils.d("isSupport10 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList10);
        return z2;
    }

    public static boolean isHasFunction_11(int i2) {
        int characterisicFunctionList11 = SPUtil.getInstance().getCharacterisicFunctionList11();
        boolean z2 = (characterisicFunctionList11 & i2) == i2;
        LogUtils.d("isSupport11 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList11);
        return z2;
    }

    public static boolean isHasFunction_12(int i2) {
        int characterisicFunctionList12 = SPUtil.getInstance().getCharacterisicFunctionList12();
        boolean z2 = (characterisicFunctionList12 & i2) == i2;
        LogUtils.d("isSupport12 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList12);
        return z2;
    }

    public static boolean isHasFunction_13(int i2) {
        int characterisicFunctionList13 = SPUtil.getInstance().getCharacterisicFunctionList13();
        boolean z2 = (characterisicFunctionList13 & i2) == i2;
        LogUtils.d("isSupport13 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList13);
        return z2;
    }

    public static boolean isHasFunction_2(int i2) {
        int characterisicFunctionList2 = SPUtil.getInstance().getCharacterisicFunctionList2();
        boolean z2 = (characterisicFunctionList2 & i2) == i2;
        LogUtils.d("isSupport2 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList2);
        return z2;
    }

    public static boolean isHasFunction_3(int i2) {
        int characterisicFunctionList3 = SPUtil.getInstance().getCharacterisicFunctionList3();
        boolean z2 = (characterisicFunctionList3 & i2) == i2;
        LogUtils.d("isSupport3 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList3);
        return z2;
    }

    public static boolean isHasFunction_4(int i2) {
        int characterisicFunctionList4 = SPUtil.getInstance().getCharacterisicFunctionList4();
        boolean z2 = (characterisicFunctionList4 & i2) == i2;
        LogUtils.d("isSupport4 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList4);
        return z2;
    }

    public static boolean isHasFunction_5(int i2) {
        int characterisicFunctionList5 = SPUtil.getInstance().getCharacterisicFunctionList5();
        boolean z2 = (characterisicFunctionList5 & i2) == i2;
        LogUtils.d("isSupport5 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList5);
        return z2;
    }

    public static boolean isHasFunction_6(int i2) {
        int characterisicFunctionList6 = SPUtil.getInstance().getCharacterisicFunctionList6();
        boolean z2 = (characterisicFunctionList6 & i2) == i2;
        LogUtils.d("isSupport6 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList6);
        return z2;
    }

    public static boolean isHasFunction_7(int i2) {
        int characterisicFunctionList7 = SPUtil.getInstance().getCharacterisicFunctionList7();
        boolean z2 = (characterisicFunctionList7 & i2) == i2;
        LogUtils.d("isSupport7 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList7);
        return z2;
    }

    public static boolean isHasFunction_8(int i2) {
        int characterisicFunctionList8 = SPUtil.getInstance().getCharacterisicFunctionList8();
        boolean z2 = (characterisicFunctionList8 & i2) == i2;
        LogUtils.d("isSupport8 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList8);
        return z2;
    }

    public static boolean isHasFunction_9(int i2) {
        int characterisicFunctionList9 = SPUtil.getInstance().getCharacterisicFunctionList9();
        boolean z2 = (characterisicFunctionList9 & i2) == i2;
        LogUtils.d("isSupport9 =" + z2 + ",functionType =" + i2 + ",function =" + characterisicFunctionList9);
        return z2;
    }

    public static boolean utendo() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String str;
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        boolean z2 = false;
        if (deviceFirmware != null) {
            if (deviceFirmware.length() > 0) {
                String strSubstring = deviceFirmware.substring(0, 1);
                String strSubstring2 = deviceFirmware.length() > 1 ? deviceFirmware.substring(0, 2) : "AA";
                if (strSubstring.contains("B") || strSubstring2.contains("RB")) {
                    z2 = true;
                }
            }
            str = "是否支持血压 =" + z2;
        } else {
            str = "是否支持血压 =false";
        }
        LogUtils.d(str);
        return z2;
    }

    public static boolean utenif() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        boolean z2 = deviceFirmware != null && deviceFirmware.length() > 0 && (deviceFirmware.contains("MH") || deviceFirmware.contains("RH") || deviceFirmware.contains("UH"));
        LogUtils.d("是否支持心率 =" + z2);
        return z2;
    }
}
