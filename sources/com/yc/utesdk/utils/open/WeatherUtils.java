package com.yc.utesdk.utils.open;

import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import com.aliyun.alink.linksdk.alcs.api.utils.ErrorCode;
import com.aliyun.alink.sdk.jsbridge.BoneBridgeNative;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.umeng.analytics.pro.bc;
import com.yc.utesdk.bean.WeatherDayInfo;
import com.yc.utesdk.bean.WeatherHourInfo;
import com.yc.utesdk.bean.WeatherInfo;
import com.yc.utesdk.bean.WeatherInfoResult;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.close.HttpUtil;
import com.yc.utesdk.utils.close.RSAUtils;
import com.yc.utesdk.watchface.close.PostUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import org.android.agoo.message.MessageService;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class WeatherUtils {
    private static WeatherUtils Instance = null;
    private static String url_china = "https://www.ute-tech.com.cn/ci3/index.php/weather/sdkw";
    private static String url_foreign = "https://ap.ute-tech.com.cn/ci3/index.php/weather/sdkw";

    public static WeatherUtils getInstance() {
        if (Instance == null) {
            Instance = new WeatherUtils();
        }
        return Instance;
    }

    public byte[] getDeviceWeatherData(WeatherInfo weatherInfo) {
        byte todayWeatherCode = (byte) (weatherInfo.getTodayWeatherCode() & 255);
        byte todayTmpCurrent = (byte) (weatherInfo.getTodayTmpCurrent() & 255);
        byte todayTmpMax = (byte) (weatherInfo.getTodayTmpMax() & 255);
        byte todayTmpMin = (byte) (weatherInfo.getTodayTmpMin() & 255);
        int todayPm25 = weatherInfo.getTodayPm25();
        int i2 = 8;
        byte b2 = (byte) ((todayPm25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8);
        int todayAqi = weatherInfo.getTodayAqi();
        int i3 = 5;
        byte[] bArr = {todayWeatherCode, 0, todayTmpCurrent, todayTmpMax, todayTmpMin, b2, (byte) (todayPm25 & 255), (byte) ((65280 & todayAqi) >> 8), (byte) (todayAqi & 255)};
        List<WeatherHourInfo> weatherHourInfoList = weatherInfo.getWeatherHourInfoList();
        int size = weatherHourInfoList.size();
        byte[] bArr2 = new byte[120];
        Arrays.fill(bArr2, (byte) -1);
        int iMin = Math.min(24, size);
        int i4 = 0;
        while (i4 < iMin) {
            WeatherHourInfo weatherHourInfo = weatherHourInfoList.get(i4);
            int i5 = i3 * i4;
            bArr2[i5] = (byte) (weatherHourInfo.getTemperature() & 255);
            bArr2[i5 + 1] = (byte) (weatherHourInfo.getHum() & 255);
            bArr2[i5 + 2] = (byte) (weatherHourInfo.getUv() & 255);
            bArr2[i5 + 3] = (byte) (weatherHourInfo.getWeatherCode() & 255);
            bArr2[i5 + 4] = 0;
            i4++;
            i3 = 5;
        }
        List<WeatherDayInfo> weatherDayInfoList = weatherInfo.getWeatherDayInfoList();
        byte[] bArr3 = new byte[48];
        int iMin2 = Math.min(6, weatherDayInfoList.size());
        int i6 = 0;
        while (i6 < iMin2) {
            WeatherDayInfo weatherDayInfo = weatherDayInfoList.get(i6);
            int i7 = i2 * i6;
            bArr3[i7] = (byte) (weatherDayInfo.getWeatherCode() & 255);
            bArr3[i7 + 1] = 0;
            bArr3[i7 + 2] = (byte) (weatherDayInfo.getTemperatureMax() & 255);
            bArr3[i7 + 3] = (byte) (weatherDayInfo.getTemperatureMin() & 255);
            bArr3[i7 + 4] = (byte) (weatherDayInfo.getDayHumDaytime() & 255);
            bArr3[i7 + 5] = (byte) (weatherDayInfo.getDayHumNighttime() & 255);
            bArr3[i7 + 6] = (byte) (weatherDayInfo.getDayUvDaytime() & 255);
            bArr3[i7 + 7] = (byte) (weatherDayInfo.getDayUvNighttime() & 255);
            i6++;
            i2 = 8;
        }
        return ByteDataUtil.getInstance().copyTwoArrays(ByteDataUtil.getInstance().copyTwoArrays(bArr, bArr2), bArr3);
    }

    public WeatherInfoResult getWeatherFormServer(String str, String str2, boolean z2) throws JSONException {
        String string;
        WeatherInfoResult weatherInfoResult = new WeatherInfoResult();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appkey", PostUtil.getInstance().getAppKey());
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("cityid", str);
            }
            jSONObject.put(bc.N, str2);
            jSONObject.put("package", UteBleClient.getContext().getPackageName());
            string = jSONObject.toString();
        } catch (JSONException e2) {
            e2.printStackTrace();
            weatherInfoResult.setStatus(3);
            string = "";
        }
        LogUtils.i("接口--获取天气--value=" + string);
        HashMap map = new HashMap();
        map.put("content", RSAUtils.encryptByPublicKeyForSplit(string));
        String strDoPost = HttpUtil.doPost(z2 ? url_china : url_foreign, map);
        LogUtils.i("接口--获取天气--returnData=" + strDoPost);
        if (!TextUtils.isEmpty(strDoPost)) {
            return utenif(strDoPost);
        }
        weatherInfoResult.setStatus(1);
        return weatherInfoResult;
    }

    public final boolean utendo(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public final int utenfor(String str) {
        if (str == null || str.length() <= 0 || str.contains(MessageService.MSG_DB_COMPLETE)) {
            return 1;
        }
        if (str.contains("101") || str.contains("102") || str.contains("103")) {
            return 2;
        }
        if (str.contains("104")) {
            return 3;
        }
        if (str.contains(ErrorCode.UNKNOWN_ERROR_CODE) || str.contains("301")) {
            return 4;
        }
        if (str.contains("302") || str.contains("303") || str.contains("304")) {
            return 5;
        }
        if (str.contains("404") || str.contains(BoneBridgeNative.ERROR_SUB_CODE_GATEWAY_NOT_MATCH_ARGUMENT_NUMBER) || str.contains("406")) {
            return 6;
        }
        if (str.contains("305") || str.contains("309")) {
            return 7;
        }
        if (str.contains("306") || str.contains("307") || str.contains("308") || str.contains("310") || str.contains("311") || str.contains("312") || str.contains("313")) {
            return 8;
        }
        if (str.contains("400") || str.contains("401") || str.contains("402") || str.contains("403") || str.contains("407")) {
            return 9;
        }
        if (str.contains("503") || str.contains("504") || str.contains("507") || str.contains("508")) {
            return 10;
        }
        if (str.contains(SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION) || str.contains(SyncBoneInvoker.ERROR_SUB_CODE_METHOD_NOT_IMPLEMENTED) || str.contains("502")) {
            return 11;
        }
        return (str.contains("200") || str.contains("201") || str.contains("202") || str.contains("203") || str.contains("204") || str.contains("205") || str.contains("206") || str.contains("207") || str.contains("208") || str.contains("209") || str.contains("210") || str.contains("211") || str.contains("212") || str.contains("213")) ? 12 : 1;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(39:6|7|(1:9)(1:12)|13|(1:15)(1:16)|17|(1:19)(1:20)|21|(1:23)(1:24)|25|(1:27)(1:28)|29|(1:31)(1:32)|33|(1:35)(1:36)|37|(1:39)(1:40)|41|(1:43)(1:44)|45|(1:47)(1:48)|49|(8:51|(1:53)(1:54)|55|(2:57|(1:63)(1:62))(1:64)|65|(4:67|(1:72)|74|76)(1:75)|73|76)(1:77)|78|(1:80)(1:81)|82|83|(4:302|84|85|(15:87|(17:89|90|(1:92)|95|(1:97)|98|(1:100)|101|(1:103)(1:104)|105|(1:107)|108|(1:110)(1:111)|112|(1:114)(1:115)|116|(1:118)(1:120))(1:121)|119|(14:123|(1:125)|126|(1:128)|129|(1:131)|132|(1:134)(1:135)|136|(1:138)(1:139)|140|(1:142)|143|(1:145))|146|(14:148|(1:150)|151|(1:153)|154|(1:156)|157|(1:159)|160|(1:162)|163|(1:165)|166|(1:168))|169|(14:171|(1:173)|174|(1:176)|177|(1:179)|180|(1:182)|183|(1:185)|186|(1:188)|189|(1:191))|192|(14:194|(1:196)|197|(1:199)|200|(1:202)|203|(1:205)|206|(1:208)|209|(1:211)|212|(1:214))|215|(14:217|(1:219)|220|(1:222)|223|(1:225)|226|(1:228)|229|(1:231)|232|(1:234)|235|(1:237))|238|(14:240|(1:242)|243|(1:245)|246|(1:248)|249|(1:251)|252|(1:254)|255|(1:257)|258|(2:260|308)(1:310))(1:309)|261)(1:307))|262|(1:270)(1:269)|271|(4:(1:283)(2:275|(1:282)(9:281|285|286|304|287|288|300|289|290))|300|289|290)|284|285|286|304|287|288|4) */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0820, code lost:
    
        r0 = e;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.yc.utesdk.bean.WeatherInfoResult utenif(java.lang.String r87) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 2115
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yc.utesdk.utils.open.WeatherUtils.utenif(java.lang.String):com.yc.utesdk.bean.WeatherInfoResult");
    }
}
