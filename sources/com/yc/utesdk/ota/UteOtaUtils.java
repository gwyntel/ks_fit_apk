package com.yc.utesdk.ota;

import com.google.common.base.Ascii;
import com.umeng.analytics.pro.f;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.watchface.close.PostUtil;
import com.yc.utesdk.watchface.open.HttpRequestor;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class UteOtaUtils {
    public static final byte[] UTE_OTA_SECRET_KEY = {78, 70, -8, -59, 9, 85, 84, 69, 95, 82, 75, 12, -47, -10, 16, -5, Ascii.US, 103, 99, -33, Byte.MIN_VALUE, 122, 126, 112, -106, 13, 76, -45, 17, -114, 96, Ascii.SUB};
    private static UteOtaUtils instance;
    private final String GET_BT_VERSION_UPDATE = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getBtVersionUpdate";
    private final String GET_PATCH_LICATION_UPDATE = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getPatchlicationUpdate";
    private final String GET_BT_UI_VERSION_UPDATE = "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getBtUIVersionUpdate";

    public static UteOtaUtils getInstance() {
        if (instance == null) {
            instance = new UteOtaUtils();
        }
        return instance;
    }

    public UteServerVersion getUteBtServerVersion(String str, String str2) {
        return utendo("https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getBtVersionUpdate", str, str2);
    }

    public UteServerVersion getUteBtUiServerVersion(String str, String str2) {
        return utendo("https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getBtUIVersionUpdate", str, str2);
    }

    public UteServerVersion getUtePatchLicationServerVersion(String str, String str2) {
        return utendo("https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getPatchlicationUpdate", str, str2);
    }

    public final UteServerVersion utendo(String str, String str2, String str3) throws Throwable {
        String strDoPost = HttpRequestor.getInstance().doPost(str, "https://www.ute-tech.com.cn/ci-yc/index.php/api/client/getPatchlicationUpdate".equals(str) ? PostUtil.getInstance().getOtaPatchServerVersion(str2, str3) : PostUtil.getInstance().getOtaServerVersion(str2, str3));
        LogUtils.i("url =" + str);
        JSONObject jSONObject = new JSONObject(strDoPost);
        int i2 = jSONObject.getInt(AgooConstants.MESSAGE_FLAG);
        LogUtils.i("flag =" + i2 + ",jsonObject =" + jSONObject);
        if (i2 < 0) {
            return new UteServerVersion(i2);
        }
        return new UteServerVersion(i2, jSONObject.getString("btname"), jSONObject.getString(f.aF), jSONObject.getJSONArray("fileurl").getJSONObject(0).getString("urladdress"), jSONObject.getString("forceupdate"), jSONObject.getString("description"), jSONObject.getString("pubtime"));
    }
}
