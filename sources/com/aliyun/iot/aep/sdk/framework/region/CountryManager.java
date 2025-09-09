package com.aliyun.iot.aep.sdk.framework.region;

import android.content.res.AssetManager;
import android.text.TextUtils;
import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.iot.aep.sdk.IoTSmart;
import com.aliyun.iot.aep.sdk.framework.AApplication;
import com.aliyun.iot.aep.sdk.framework.base.ApiDataCallBack;
import com.aliyun.iot.aep.sdk.framework.config.GlobalConfig;
import com.aliyun.iot.aep.sdk.framework.utils.SpUtil;
import com.aliyun.iot.aep.sdk.log.ALog;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/* loaded from: classes3.dex */
public class CountryManager {
    public static final String COUNTRY_CHINA_ABBR = "CN";

    /* renamed from: a, reason: collision with root package name */
    private static String f11810a = "key_country_list_all";

    /* renamed from: b, reason: collision with root package name */
    private static String f11811b = "key_country_url_list_all";

    /* renamed from: c, reason: collision with root package name */
    private static String f11812c = "https://api-iot.ap-southeast-1.aliyuncs.com/living/account/countrylist/get";

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(List list) {
        return list == null || list.isEmpty();
    }

    public static void clearCountryCache() {
        SpUtil.putList(AApplication.getInstance(), f11810a, new ArrayList());
    }

    private static void d(List<IoTSmart.Country> list) {
        IoTSmart.Country next;
        IoTSmart.Country country = IoTSmart.getCountry();
        if (country == null || c(list)) {
            return;
        }
        Iterator<IoTSmart.Country> it = list.iterator();
        while (it.hasNext() && (next = it.next()) != null) {
            if (country.domainAbbreviation.equals(next.domainAbbreviation)) {
                IoTSmart.setCountry(next, new IoTSmart.ICountrySetCallBack() { // from class: com.aliyun.iot.aep.sdk.framework.region.CountryManager.1
                    @Override // com.aliyun.iot.aep.sdk.IoTSmart.ICountrySetCallBack
                    public void onCountrySet(boolean z2) {
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void e(List<IoTSmart.Country> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        SpUtil.putList(AApplication.getInstance(), f11810a, list);
    }

    public static IoTSmart.Country getCountry(String str) throws IOException {
        IoTSmart.Country next;
        List<IoTSmart.Country> countryListFromJsonFile = getCountryListFromJsonFile();
        if (!c(countryListFromJsonFile) && !TextUtils.isEmpty(str)) {
            Iterator<IoTSmart.Country> it = countryListFromJsonFile.iterator();
            while (it.hasNext() && (next = it.next()) != null) {
                if (str.equals(next.domainAbbreviation)) {
                    return next;
                }
            }
        }
        return null;
    }

    public static List<IoTSmart.Country> getCountryListFromJsonFile() throws IOException {
        String strC = c();
        if (TextUtils.isEmpty(strC)) {
            return null;
        }
        return JSON.parseArray(strC, IoTSmart.Country.class);
    }

    public static String getCurrentCountryAbbr() {
        IoTSmart.Country country = GlobalConfig.getInstance().getCountry();
        if (country != null) {
            return country.domainAbbreviation;
        }
        return null;
    }

    public static boolean isChina(IoTSmart.Country country) {
        if (country != null) {
            return "CN".equalsIgnoreCase(country.domainAbbreviation);
        }
        return false;
    }

    public static boolean isCountrySet() {
        return GlobalConfig.getInstance().getCountry() != null;
    }

    public static void queryCountryList(ApiDataCallBack apiDataCallBack) throws IOException {
        a(apiDataCallBack);
    }

    public static void requestCountryList(OkHttpClient okHttpClient, String str, final ApiDataCallBack apiDataCallBack) {
        if (okHttpClient != null && !TextUtils.isEmpty(str)) {
            okHttpClient.newCall(new Request.Builder().url(str).build()).enqueue(new Callback() { // from class: com.aliyun.iot.aep.sdk.framework.region.CountryManager.4
                @Override // okhttp3.Callback
                public void onFailure(Call call, IOException iOException) {
                    ALog.e("CountryManager", "获取国家列表失败", iOException);
                    ApiDataCallBack apiDataCallBack2 = apiDataCallBack;
                    if (apiDataCallBack2 != null) {
                        apiDataCallBack2.onFail(0, iOException.toString());
                    }
                }

                @Override // okhttp3.Callback
                public void onResponse(Call call, Response response) {
                    try {
                        List array = JSON.parseArray(response.body().string(), IoTSmart.Country.class);
                        ApiDataCallBack apiDataCallBack2 = apiDataCallBack;
                        if (apiDataCallBack2 != null) {
                            apiDataCallBack2.onSuccess(array);
                        }
                        CountryManager.e(array);
                    } catch (Exception e2) {
                        ApiDataCallBack apiDataCallBack3 = apiDataCallBack;
                        if (apiDataCallBack3 != null) {
                            apiDataCallBack3.onFail(0, e2.toString());
                        }
                    }
                }
            });
        } else if (apiDataCallBack != null) {
            apiDataCallBack.onFail(0, "OK http error in requestCountryList");
        }
    }

    public static void requestCountryListUrl(final ApiDataCallBack apiDataCallBack) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10L, TimeUnit.SECONDS);
        final OkHttpClient okHttpClientBuild = builder.hostnameVerifier(new HostnameVerifier() { // from class: com.aliyun.iot.aep.sdk.framework.region.CountryManager.2
            @Override // javax.net.ssl.HostnameVerifier
            public boolean verify(String str, SSLSession sSLSession) {
                return str.contains("gaic.alicdn.com") || str.contains("api.link.aliyun.com") || str.contains("api-iot.ap-southeast-1.aliyuncs.com");
            }
        }).build();
        okHttpClientBuild.newCall(new Request.Builder().addHeader("Content-Type", "application/json").addHeader("X-Ca-Stage", GlobalConfig.getInstance().getApiEnv().toUpperCase()).url(f11812c).post(RequestBody.create(MediaType.parse(HttpConstant.CLOUDAPI_CONTENT_TYPE_JSON), "{\n \"request\": {\n  \"apiVer\": \"1.0.0\",\n   \"language\": \"" + GlobalConfig.getInstance().getLanguage() + "\" }}")).build()).enqueue(new Callback() { // from class: com.aliyun.iot.aep.sdk.framework.region.CountryManager.3
            @Override // okhttp3.Callback
            public void onFailure(Call call, IOException iOException) {
                ApiDataCallBack apiDataCallBack2 = apiDataCallBack;
                if (apiDataCallBack2 != null) {
                    apiDataCallBack2.onFail(0, iOException.toString());
                }
                ALog.e("CountryManager", "获取国家列表url失败", iOException);
            }

            @Override // okhttp3.Callback
            public void onResponse(Call call, Response response) {
                ApiDataCallBack apiDataCallBack2;
                try {
                    JSONObject object = JSON.parseObject(response.body().string());
                    ALog.d("CountryManager", object.toJSONString());
                    String string = object.getJSONObject("data").getString("downloadUrl");
                    if (string.equals(CountryManager.d())) {
                        List listE = CountryManager.e();
                        if (!CountryManager.c(listE) && (apiDataCallBack2 = apiDataCallBack) != null) {
                            apiDataCallBack2.onSuccess(listE);
                            return;
                        }
                    }
                    CountryManager.b(string);
                    CountryManager.requestCountryList(okHttpClientBuild, string, apiDataCallBack);
                } catch (Exception e2) {
                    ApiDataCallBack apiDataCallBack3 = apiDataCallBack;
                    if (apiDataCallBack3 != null) {
                        apiDataCallBack3.onFail(0, e2.toString());
                    }
                }
            }
        });
    }

    public static void updateCountry() {
        d(getCountryListFromJsonFile());
    }

    private static String c() throws IOException {
        String language;
        AssetManager assets = AApplication.getInstance().getAssets();
        StringBuilder sb = new StringBuilder();
        try {
            language = IoTSmart.getLanguage();
            ALog.d("CountryManager", "current language:" + language);
        } catch (FileNotFoundException unused) {
            ALog.w("CountryManager", "config file not found");
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (TextUtils.isEmpty(language)) {
            return "";
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assets.open("sdk_fw_countries/" + language.toLowerCase() + ".json")));
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            sb.append(line);
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str) {
        SpUtil.putString(AApplication.getInstance(), f11811b, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<IoTSmart.Country> e() {
        return SpUtil.getList(AApplication.getInstance(), f11810a);
    }

    private static void a(ApiDataCallBack apiDataCallBack) throws IOException {
        List<IoTSmart.Country> countryListFromJsonFile = getCountryListFromJsonFile();
        if (apiDataCallBack == null) {
            return;
        }
        if (c(countryListFromJsonFile)) {
            apiDataCallBack.onFail(-1, "empty country list");
        } else {
            apiDataCallBack.onSuccess(countryListFromJsonFile);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String d() {
        return SpUtil.getString(AApplication.getInstance(), f11811b);
    }
}
