package com.yc.utesdk.watchface.close;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.media3.extractor.text.ttml.TtmlNode;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.google.gson.Gson;
import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.f;
import com.yc.utesdk.ble.open.DeviceMode;
import com.yc.utesdk.ble.open.DevicePlatform;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.SPUtil;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PostUtil {
    public static final String ADD_WATCH = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/addWatch";
    public static final String GET_WATCH = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getWatchs";
    public static final String GET_WATCH_CLASS = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getWatchClass";
    public static final String GET_WATCH_ISSETWATCH = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/issetWatch";
    public static final String GET_WATCH_ZIPS = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getWatchZips";
    private static PostUtil instance;
    private Context mContext = UteBleClient.getContext();
    private String ycAppKey;

    public static PostUtil getInstance() {
        if (instance == null) {
            instance = new PostUtil();
        }
        return instance;
    }

    public Map<String, String> addWatchHashMap(String str, int i2) {
        HashMap map = new HashMap();
        map.put("appkey", getInstance().getAppKey());
        map.put("btname", str);
        map.put("id", i2 + "");
        return map;
    }

    public String getAppKey() throws PackageManager.NameNotFoundException {
        String str;
        Context context = this.mContext;
        str = "";
        if (context == null) {
            return "";
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("YCAPPKEY");
                str = string != null ? string : "";
                LogUtils.i("CommonUtil.getAppKey->Could not read APPKEY meta-data from AndroidManifest.xml.");
            }
        } catch (Exception e2) {
            LogUtils.e("CommonUtil.getAppKey->Could not read APPKEY meta-data from AndroidManifest.xml.");
            e2.printStackTrace();
        }
        return TextUtils.isEmpty(str) ? utendo() : str;
    }

    public HashMap getOtaPatchServerVersion(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        String deviceAddress = UteBleClient.getUteBleClient().getDeviceAddress();
        HashMap map = new HashMap();
        String appKey = getAppKey();
        HashMap map2 = new HashMap();
        map2.put("appkey", appKey);
        map2.put("versioncode", str);
        map2.put("btname", deviceFirmware);
        map2.put(AlinkConstants.KEY_MAC, deviceAddress);
        map2.put(bc.N, str2);
        map.put("content", new Gson().toJson(map2));
        return map;
    }

    public HashMap getOtaServerVersion(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        String deviceAddress = UteBleClient.getUteBleClient().getDeviceAddress();
        HashMap map = new HashMap();
        String appKey = getAppKey();
        HashMap map2 = new HashMap();
        map2.put("appkey", appKey);
        map2.put(f.aF, str);
        map2.put("btname", deviceFirmware);
        map2.put(AlinkConstants.KEY_MAC, deviceAddress);
        map2.put(bc.N, str2);
        String json = new Gson().toJson(map2);
        map.put("content", json);
        LogUtils.i("mapJson =" + json);
        return map;
    }

    public HashMap getWatchClassHashMap(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        HashMap map = new HashMap();
        String appKey = getAppKey();
        HashMap map2 = new HashMap();
        map2.put("appkey", appKey);
        map2.put("btname", deviceFirmware);
        map2.put(bc.N, str);
        map.put("content", new Gson().toJson(map2));
        return map;
    }

    public HashMap getWatchHashMap(int i2, int i3, String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, PackageManager.NameNotFoundException, InvalidKeyException, NumberFormatException, InvalidAlgorithmParameterException {
        String deviceFirmware = SPUtil.getInstance().getDeviceFirmware();
        String deviceAddress = UteBleClient.getUteBleClient().getDeviceAddress();
        String resolutionWidthHeight = SPUtil.getInstance().getResolutionWidthHeight();
        String dialMaxDataSize = SPUtil.getInstance().getDialMaxDataSize();
        int watchFaceShapeType = SPUtil.getInstance().getWatchFaceShapeType();
        int dialScreenCompatibleLevel = SPUtil.getInstance().getDialScreenCompatibleLevel();
        int i4 = DeviceMode.isHasFunction_7(8192) ? 1 : DevicePlatform.getInstance().isJXPlatformATS3085L() ? 2 : DevicePlatform.getInstance().isJXPlatformATS3085S() ? 4 : DevicePlatform.getInstance().isRKPlatform8773() ? 5 : DevicePlatform.getInstance().isZKPlatform() ? 6 : 0;
        HashMap map = new HashMap();
        String appKey = getAppKey();
        HashMap map2 = new HashMap();
        map2.put("appkey", appKey);
        map2.put("btname", deviceFirmware);
        map2.put("type", "0");
        map2.put("compatible", dialScreenCompatibleLevel + "");
        map2.put("shape", watchFaceShapeType + "");
        map2.put("sort", "1");
        map2.put("start", i2 + "");
        map2.put(TtmlNode.END, i3 + "");
        if (!TextUtils.isEmpty(str2)) {
            map2.put("class", str2);
        }
        map2.put(AlinkConstants.KEY_MAC, deviceAddress);
        map2.put("dpi", resolutionWidthHeight);
        map2.put(DispatchConstants.PLATFORM, String.valueOf(i4));
        map2.put("maxCapacity", dialMaxDataSize);
        map2.put(bc.N, str);
        map.put("content", new Gson().toJson(map2));
        return map;
    }

    public Map<String, String> getWatchZipsHashMap(String str, String str2, int i2, boolean z2) {
        HashMap map = new HashMap();
        map.put("appkey", getAppKey());
        map.put("btname", str);
        map.put("type", i2 + "");
        map.put("dpi", str2);
        map.put("os", "android");
        map.put("debug", z2 ? "1" : "-1");
        return map;
    }

    public String issetWatchHashMap(String str, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appkey", getAppKey());
            jSONObject.put("btname", str);
            jSONObject.put("ids", jSONArray);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject.toString();
    }

    public void setAppKey(String str) {
        this.ycAppKey = str;
    }

    public final String utendo() {
        return this.ycAppKey;
    }
}
