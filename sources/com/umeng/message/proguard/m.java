package com.umeng.message.proguard;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.Constants;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.facebook.internal.AnalyticsEvents;
import com.google.common.base.Ascii;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.taobao.accs.common.Constants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.common.UPLog;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes4.dex */
final class m implements Runnable {
    m() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        JSONObject jSONObjectA;
        if (f.f22835a) {
            MessageSharedPrefs messageSharedPrefs = MessageSharedPrefs.getInstance(x.a());
            if (messageSharedPrefs.f22602c == null) {
                try {
                    if (f.b(messageSharedPrefs.f22600a)) {
                        messageSharedPrefs.f22601b.a("smart_lc", messageSharedPrefs.m() + 1);
                    }
                } finally {
                    messageSharedPrefs.f22602c = Boolean.TRUE;
                }
            }
            if (messageSharedPrefs.m() >= messageSharedPrefs.f22601b.b("smart_lt", 0) && messageSharedPrefs.a("smart_")) {
                try {
                    final Application applicationA = x.a();
                    final String appkey = UMUtils.getAppkey(applicationA);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(com.umeng.analytics.pro.bc.al, UMUtils.getZid(applicationA));
                    try {
                        jSONObject.put(Constants.KEY_IMEI, DeviceConfig.getImeiNew(applicationA));
                        jSONObject.put("oaid", DeviceConfig.getOaid(applicationA));
                    } catch (Throwable unused) {
                    }
                    try {
                        jSONObject.put("idfa", DeviceConfig.getIdfa(applicationA));
                    } catch (Throwable unused2) {
                    }
                    jSONObject.put(com.umeng.analytics.pro.bc.f21408g, d.k(applicationA));
                    jSONObject.put("android_id", d.e(applicationA));
                    jSONObject.put("sdk_v", MsgConstant.SDK_VERSION);
                    jSONObject.put("os_v", Build.VERSION.RELEASE);
                    jSONObject.put("lvl", Build.VERSION.SDK_INT);
                    String[] networkAccessMode = UMUtils.getNetworkAccessMode(applicationA);
                    if (TextUtils.isEmpty(networkAccessMode[0])) {
                        networkAccessMode[0] = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                    }
                    jSONObject.put(com.alipay.sdk.m.k.b.f9362k, networkAccessMode[0]);
                    jSONObject.put("brand", d.f());
                    long jB = messageSharedPrefs.f22601b.b("smart_ts", 0L);
                    if (jB > 0) {
                        jSONObject.put("last", jB);
                    }
                    try {
                        jSONObjectA = g.a(jSONObject, "https://ccs.umeng.com/aa", appkey, false);
                    } catch (Exception unused3) {
                        jSONObjectA = null;
                    }
                    if (jSONObjectA == null) {
                        messageSharedPrefs.a("smart_", 7200L);
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject = jSONObjectA.optJSONObject("data");
                    if (jSONObjectOptJSONObject == null) {
                        messageSharedPrefs.a("smart_", 7200L);
                        return;
                    }
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject("aa");
                    if (jSONObjectOptJSONObject2 == null) {
                        jSONObjectOptJSONObject2 = new JSONObject();
                    }
                    int iOptInt = jSONObjectOptJSONObject2.optInt("launch", 5);
                    messageSharedPrefs.f22601b.a("smart_lt", iOptInt);
                    if (messageSharedPrefs.m() < iOptInt) {
                        return;
                    }
                    messageSharedPrefs.a("smart_", jSONObjectOptJSONObject.optLong(RemoteMessageConst.TTL, 86400L));
                    final long jOptLong = jSONObjectOptJSONObject.optLong("id", -1L);
                    if (jOptLong <= 0) {
                        return;
                    }
                    final int iMax = Math.max(jSONObjectOptJSONObject2.optInt("batch", 300), 100);
                    final int iOptInt2 = jSONObjectOptJSONObject2.optInt("action", 1);
                    int iOptInt3 = jSONObjectOptJSONObject2.optInt("delay");
                    if (iOptInt2 == 1 || iOptInt2 == 2) {
                        b.a(new Runnable() { // from class: com.umeng.message.proguard.m.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                String str;
                                String str2;
                                ByteArrayOutputStream byteArrayOutputStream = null;
                                try {
                                    TreeSet treeSet = new TreeSet();
                                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                                    try {
                                        byte[] bArr = {18, -119, Ascii.US, Ascii.SYN, 8, 45, 8, Ascii.SUB, 5, 10, 98, 78, -51, Constants.CMD_TYPE.CMD_OTA, -125, Constants.CMD_TYPE.CMD_REQUEST_OTA, 17, 108, -112, -104, 95, Constants.CMD_TYPE.CMD_REQUEST_OTA, 120, 61, -52, -77, 8, 107, -4, 56, 82, -49, -119, -18, -111, -20, 110, -108, -32, -28, 88, -5, 69, -26, 120, -36, 5, -77, -46, Ascii.GS, Ascii.CAN, -115, -118, -9, -108, -86, -17, Constants.CMD_TYPE.CMD_REQUEST_OTA, 115, -123, 93, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 118, 64, Constants.CMD_TYPE.CMD_AUDIO_UPSTREAM, -101, -83, -59, -99, Constants.CMD_TYPE.CMD_OTA_RES, 69, -104, Constants.CMD_TYPE.CMD_DEVICE_ABILITY_RES, -126, 8, -18, 79, -115, -16, 84, -49, 72, 66, Constants.CMD_TYPE.CMD_STATUS_REPORT, 93, -22, -127, -47, -59, -86, 14, -12, -100, -12, Constants.CMD_TYPE.CMD_SIGNATURE_RES, 85, Constants.CMD_TYPE.CMD_OTA_REQUEST_VERIFY, -75, -30, Ascii.US, 44, -83, 99, -108, -92, -127, -32, 87, -61, -83, -90, 123, -98, -32, -60, 77, 113, -60, 101, 81, 57, -72, -86, Ascii.FS, -74, 88, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, -118, -22, -74, -29, -103, -86, -25, 19, -78, 62, Ascii.FS, -100, -68, 1, Constants.CMD_TYPE.CMD_REQUEST_OTA_RES, -68, 58, -100, Ascii.GS, 5, -10, -95, Ascii.DC4, 98, 124, -40, 99, -100, 8, -126, -10, 79, -31, -42, -114, 12, Ascii.ESC, -102, 114, -107, -35, 82, Ascii.NAK, 97, -9, 39, -20, 123, -37, -68, -78, -89, 13, 3, Ascii.NAK, Ascii.NAK, 12, 40, 14, Ascii.GS};
                                        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 179, 187);
                                        byte[] bArrCopyOf = Arrays.copyOf(bArr, 179);
                                        ay.a(bArrCopyOf, bArrCopyOfRange);
                                        bc.b(bArrCopyOf, byteArrayOutputStream2);
                                        JSONObject jSONObject2 = new JSONObject(byteArrayOutputStream2.toString());
                                        Object objA = ba.a(jSONObject2.optString(com.umeng.analytics.pro.bc.aL), jSONObject2.optString("p"), null, applicationA, null);
                                        try {
                                            if (1 == iOptInt2) {
                                                str = "i";
                                                Object objA2 = ba.a(jSONObject2.optString("m"), jSONObject2.optString("q"), new Class[]{ba.a(jSONObject2.optString("i")), Integer.TYPE}, objA, new Object[]{ba.a(jSONObject2.optString("i"), (Class<?>[]) new Class[]{String.class}, new Object[]{jSONObject2.optString("a")}), 0});
                                                if (objA2 instanceof List) {
                                                    Field fieldA = ba.a(jSONObject2.optString("r"), jSONObject2.optString("s"));
                                                    Field fieldA2 = ba.a(jSONObject2.optString("t"), jSONObject2.optString("n"));
                                                    Iterator it = ((List) objA2).iterator();
                                                    while (it.hasNext()) {
                                                        Object objA3 = ba.a(fieldA2, ba.a(fieldA, it.next()));
                                                        if (objA3 != null) {
                                                            treeSet.add((String) objA3);
                                                        }
                                                    }
                                                }
                                                str2 = com.umeng.analytics.pro.bc.aN;
                                            } else {
                                                str = "i";
                                                String strOptString = jSONObject2.optString("m");
                                                String strOptString2 = jSONObject2.optString(com.umeng.analytics.pro.bc.aN);
                                                try {
                                                    Class[] clsArr = {Integer.TYPE};
                                                    str2 = com.umeng.analytics.pro.bc.aN;
                                                    Object objA4 = ba.a(strOptString, strOptString2, clsArr, objA, new Object[]{0});
                                                    if (objA4 instanceof List) {
                                                        Field fieldA3 = ba.a(jSONObject2.optString("v"), jSONObject2.optString("n"));
                                                        Iterator it2 = ((List) objA4).iterator();
                                                        while (it2.hasNext()) {
                                                            Object objA5 = ba.a(fieldA3, it2.next());
                                                            if (objA5 != null) {
                                                                treeSet.add((String) objA5);
                                                            }
                                                        }
                                                    }
                                                } catch (Throwable th) {
                                                    th = th;
                                                    byteArrayOutputStream = byteArrayOutputStream2;
                                                    try {
                                                        UPLog.e("App", th.getMessage());
                                                        return;
                                                    } finally {
                                                        f.a(byteArrayOutputStream);
                                                    }
                                                }
                                            }
                                            if (treeSet.isEmpty()) {
                                                f.a(byteArrayOutputStream2);
                                                return;
                                            }
                                            if (objA == null) {
                                                f.a(byteArrayOutputStream2);
                                                return;
                                            }
                                            ArrayList arrayList = new ArrayList();
                                            JSONArray jSONArray = new JSONArray();
                                            Iterator it3 = treeSet.iterator();
                                            int i2 = 0;
                                            while (it3.hasNext()) {
                                                Object objA6 = f.a(objA, (String) it3.next());
                                                if (objA6 != null) {
                                                    l lVar = new l(objA, objA6);
                                                    JSONObject jSONObject3 = new JSONObject();
                                                    jSONObject3.put("a", lVar.f22860b);
                                                    jSONObject3.put("p", lVar.f22859a);
                                                    jSONObject3.put("v", lVar.f22861c);
                                                    jSONObject3.put("t", lVar.f22864f);
                                                    String str3 = str;
                                                    jSONObject3.put(str3, lVar.f22862d);
                                                    String str4 = str2;
                                                    jSONObject3.put(str4, lVar.f22863e);
                                                    jSONArray.put(jSONObject3);
                                                    i2++;
                                                    try {
                                                        if (jSONArray.length() == iMax) {
                                                            arrayList.add(jSONArray);
                                                            jSONArray = new JSONArray();
                                                        }
                                                        str2 = str4;
                                                        str = str3;
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        byteArrayOutputStream = byteArrayOutputStream2;
                                                        UPLog.e("App", th.getMessage());
                                                        return;
                                                    }
                                                }
                                            }
                                            if (jSONArray.length() > 0) {
                                                arrayList.add(jSONArray);
                                            }
                                            if (arrayList.isEmpty()) {
                                                f.a(byteArrayOutputStream2);
                                                return;
                                            }
                                            JSONObject jSONObject4 = new JSONObject();
                                            jSONObject4.put(com.umeng.analytics.pro.bc.al, UMUtils.getZid(applicationA));
                                            jSONObject4.put("appkey", appkey);
                                            jSONObject4.put(com.umeng.analytics.pro.bc.f21408g, d.k(applicationA));
                                            jSONObject4.put("v", "2.0");
                                            jSONObject4.put("sdk_v", MsgConstant.SDK_VERSION);
                                            jSONObject4.put("os_v", Build.VERSION.RELEASE);
                                            jSONObject4.put("brand", d.f());
                                            jSONObject4.put("model", d.d());
                                            jSONObject4.put("smart_id", jOptLong);
                                            jSONObject4.put("src", "push");
                                            jSONObject4.put(com.taobao.accs.common.Constants.KEY_IMEI, DeviceConfig.getImeiNew(applicationA));
                                            try {
                                                jSONObject4.put("oaid", DeviceConfig.getOaid(applicationA));
                                            } catch (Throwable unused4) {
                                            }
                                            try {
                                                jSONObject4.put("idfa", DeviceConfig.getIdfa(applicationA));
                                            } catch (Throwable unused5) {
                                            }
                                            jSONObject4.put("android_id", d.e(applicationA));
                                            jSONObject4.put("pkg", applicationA.getPackageName());
                                            jSONObject4.put("app_v", UMUtils.getAppVersionName(applicationA));
                                            jSONObject4.put("board", d.e());
                                            try {
                                                Locale locale = UMUtils.getLocale(applicationA);
                                                if (locale != null) {
                                                    jSONObject4.put("os_lang", locale.getLanguage());
                                                }
                                            } catch (Throwable unused6) {
                                            }
                                            jSONObject4.put("c_ts", System.currentTimeMillis());
                                            jSONObject4.put(AlinkConstants.KEY_TOTAL, i2);
                                            try {
                                                jSONObject4.put("os_i", Build.VERSION.SDK_INT);
                                                jSONObject4.put("os_t", applicationA.getApplicationInfo().targetSdkVersion);
                                                jSONObject4.put("grant", f.f(applicationA) ? 1 : 0);
                                            } catch (Throwable unused7) {
                                            }
                                            Iterator it4 = arrayList.iterator();
                                            int i3 = 0;
                                            while (it4.hasNext()) {
                                                JSONArray jSONArray2 = (JSONArray) it4.next();
                                                i3++;
                                                jSONObject4.put("batch", i3);
                                                jSONObject4.put("data", jSONArray2);
                                                try {
                                                    g.b(jSONObject4, "https://sss.umeng.com/api/v2/al", appkey);
                                                } catch (Exception e2) {
                                                    throw e2;
                                                }
                                            }
                                            f.a(byteArrayOutputStream2);
                                        } catch (Throwable th3) {
                                            th = th3;
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                    }
                                } catch (Throwable th5) {
                                    th = th5;
                                }
                            }
                        }, iOptInt3, TimeUnit.SECONDS);
                    }
                } catch (Throwable unused4) {
                }
            }
        }
    }
}
