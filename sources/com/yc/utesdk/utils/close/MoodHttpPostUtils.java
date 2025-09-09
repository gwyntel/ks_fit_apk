package com.yc.utesdk.utils.close;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.umeng.analytics.pro.f;
import com.yc.utesdk.bean.ActivationRequest;
import com.yc.utesdk.bean.ActivationResult;
import com.yc.utesdk.bean.BaseResult;
import com.yc.utesdk.bean.MoodPressureFatigueInfo;
import com.yc.utesdk.ble.open.UteBleClient;
import com.yc.utesdk.listener.NetBaseListener;
import com.yc.utesdk.log.LogUtils;
import com.yc.utesdk.utils.open.CalendarUtils;
import com.yc.utesdk.utils.open.GBUtils;
import com.yc.utesdk.utils.open.SPUtil;
import com.yc.utesdk.watchface.open.HttpRequestor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MoodHttpPostUtils {
    private static MoodHttpPostUtils instance;

    /* renamed from: a, reason: collision with root package name */
    MoodServerListener f24941a;
    private final String AICARING_ALL_MOODPRESS_URL = "https://api.aicaring.com/alg/exterior/health/allrslts";
    private final String AICARING_ACTIVE_ALGORITHM_URL = "https://api.aicaring.com/alg/exterior/sinklib/activation";
    private final String AICARING_ACTIVE_ELBPALGORITHM_URL = "https://api.aicaring.com/api/v0/sinklib/activation";

    /* renamed from: b, reason: collision with root package name */
    MoodPressureFatigueInfo f24942b = new MoodPressureFatigueInfo();

    /* renamed from: c, reason: collision with root package name */
    int f24943c = 0;
    private final String EL_APP_KEY = "19e0d8a6485655628224c5162b19498b";
    private final String deviceFirm = "UTE";
    private final String deviceModel = "RH316";
    private final String sinklibVersion = "v0.1.0.020623";

    /* renamed from: d, reason: collision with root package name */
    NetBaseListener f24944d = null;

    public class utenfor extends AsyncTask {
        public utenfor() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            return HttpRequestor.getInstance().doPost("https://api.aicaring.com/api/v0/sinklib/activation", strArr[0]);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            super.onPostExecute((utenfor) str);
            LogUtils.d("activeMoodAlgorithm 执行完成");
            if (str != null) {
                try {
                    ActivationResult activationResult = (ActivationResult) new Gson().fromJson(str, ActivationResult.class);
                    LogUtils.d("恒爱血压 激活返回 = " + activationResult.toString());
                    if (activationResult.isStatus()) {
                        NetBaseListener netBaseListener = MoodHttpPostUtils.this.f24944d;
                        if (netBaseListener != null) {
                            netBaseListener.success(activationResult);
                            return;
                        }
                    } else {
                        NetBaseListener netBaseListener2 = MoodHttpPostUtils.this.f24944d;
                        if (netBaseListener2 != null) {
                            netBaseListener2.failed(activationResult);
                            return;
                        }
                    }
                } catch (Exception e2) {
                    LogUtils.d("恒爱血压 激活  e = " + e2);
                }
                NetBaseListener netBaseListener3 = MoodHttpPostUtils.this.f24944d;
                if (netBaseListener3 != null) {
                    netBaseListener3.failed(new BaseResult(false));
                }
            }
        }
    }

    public class utenif extends AsyncTask {
        public utenif() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            return HttpRequestor.getInstance().doPost("https://api.aicaring.com/alg/exterior/sinklib/activation", strArr[0]);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) throws JSONException {
            super.onPostExecute((utenif) str);
            try {
                if (TextUtils.isEmpty(str)) {
                    MoodHttpPostUtils.this.f24942b.setTestResultStatus(2);
                } else {
                    JSONObject jSONObject = new JSONObject(str);
                    String string = jSONObject.getString("data");
                    if (jSONObject.getBoolean("status")) {
                        SPUtil.getInstance().setMoodActivationCodeSp(GBUtils.StringToAsciiString(string));
                        MoodHttpPostUtils.this.f24942b.setTestResultStatus(0);
                    } else {
                        MoodHttpPostUtils.this.f24942b.setTestResultStatus(4);
                        LogUtils.d("activeMoodAlgorithm 4 失败");
                    }
                }
                MoodHttpPostUtils moodHttpPostUtils = MoodHttpPostUtils.this;
                moodHttpPostUtils.f24941a.onMoodServerStatus(moodHttpPostUtils.f24942b);
            } catch (Exception e2) {
                e2.printStackTrace();
                LogUtils.d("执行了 activeMoodAlgorithm 8 Exception =" + e2);
                MoodHttpPostUtils.this.f24942b.setTestResultStatus(6);
                MoodHttpPostUtils moodHttpPostUtils2 = MoodHttpPostUtils.this;
                moodHttpPostUtils2.f24941a.onMoodServerStatus(moodHttpPostUtils2.f24942b);
            }
            LogUtils.d("activeMoodAlgorithm 执行完成");
        }
    }

    public class utenint extends AsyncTask {
        public utenint() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... strArr) {
            return HttpRequestor.getInstance().doPost("https://api.aicaring.com/alg/exterior/health/allrslts", strArr[0]);
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) throws JSONException {
            super.onPostExecute((utenint) str);
            LogUtils.d("执行了 getMoodPressureData result = " + str);
            try {
                if (TextUtils.isEmpty(str)) {
                    MoodHttpPostUtils.this.f24942b.setTestResultStatus(2);
                } else {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject.getBoolean("status")) {
                        MoodHttpPostUtils.this.f24942b.setTestResultStatus(0);
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        int i2 = jSONObject2.has("emo_status") ? jSONObject2.getInt("emo_status") : -1;
                        String string = jSONObject2.has("emo_desc") ? jSONObject2.getString("emo_desc") : "";
                        float f2 = jSONObject2.has("pressure") ? jSONObject2.getLong("pressure") : -1.0f;
                        String string2 = jSONObject2.has("prs_desc") ? jSONObject2.getString("prs_desc") : "";
                        float f3 = jSONObject2.has("fatigue") ? jSONObject2.getLong("fatigue") : -1.0f;
                        String string3 = jSONObject2.has("ftg_desc") ? jSONObject2.getString("ftg_desc") : "";
                        LogUtils.d("执行了 getMoodPressureData 解析数据1 pressureValue =" + f2 + ",fatigueValue =" + f3 + ",moodValue =" + i2);
                        if (SPUtil.getInstance().getMoodInterfaceSwitch() == 1) {
                            if (i2 >= 0) {
                                MoodHttpPostUtils.this.f24942b.setMoodValue(i2);
                                MoodHttpPostUtils.this.f24942b.setMoodDes(string);
                                SPUtil.getInstance().setMoodValueSp(i2);
                            } else {
                                MoodHttpPostUtils.this.f24942b.setTestResultStatus(3);
                            }
                        }
                        if (SPUtil.getInstance().getPressureInterfaceSwitch() == 1) {
                            if (f2 > 0.0f) {
                                int i3 = (int) f2;
                                MoodHttpPostUtils.this.f24942b.setPressureValue(i3);
                                MoodHttpPostUtils.this.f24942b.setPressureDes(string2);
                                SPUtil.getInstance().setPressureValueSp(i3);
                            } else {
                                MoodHttpPostUtils.this.f24942b.setTestResultStatus(3);
                            }
                        }
                        if (SPUtil.getInstance().getFatigueInterfaceSwitch() == 1) {
                            if (f3 > 0.0f) {
                                int i4 = (int) f3;
                                MoodHttpPostUtils.this.f24942b.setFatigueValue(i4);
                                MoodHttpPostUtils.this.f24942b.setFatigueDes(string3);
                                SPUtil.getInstance().setFatigueValueSp(i4);
                            } else {
                                MoodHttpPostUtils.this.f24942b.setTestResultStatus(3);
                            }
                        }
                    } else {
                        MoodHttpPostUtils.this.f24942b.setTestResultStatus(4);
                        LogUtils.d("getMoodPressureData 4 失败");
                    }
                }
                MoodHttpPostUtils moodHttpPostUtils = MoodHttpPostUtils.this;
                moodHttpPostUtils.f24941a.onMoodServerStatus(moodHttpPostUtils.f24942b);
            } catch (Exception e2) {
                e2.printStackTrace();
                LogUtils.d("执行了 getMoodPressureData 8 Exception =" + e2);
                MoodHttpPostUtils.this.f24942b.setTestResultStatus(6);
                MoodHttpPostUtils moodHttpPostUtils2 = MoodHttpPostUtils.this;
                moodHttpPostUtils2.f24941a.onMoodServerStatus(moodHttpPostUtils2.f24942b);
            }
            LogUtils.d("getMoodPressureData 执行完成");
        }
    }

    public static MoodHttpPostUtils getInstance() {
        if (instance == null) {
            instance = new MoodHttpPostUtils();
        }
        return instance;
    }

    public void activationElbpIo(String str, NetBaseListener<ActivationResult> netBaseListener) {
        this.f24944d = netBaseListener;
        ActivationRequest activationRequest = new ActivationRequest();
        activationRequest.setApp_key("19e0d8a6485655628224c5162b19498b");
        activationRequest.setName(UteBleClient.getUteBleClient().getDeviceName());
        activationRequest.setFirm("UTE");
        activationRequest.setMac(SPUtil.getInstance().getLastConnectDeviceAddress());
        activationRequest.setModel("RH316");
        ActivationRequest.SensorBean sensorBean = new ActivationRequest.SensorBean();
        sensorBean.setModel(SPUtil.getInstance().getMoodSensorType());
        sensorBean.setFs(100);
        activationRequest.setSensor(sensorBean);
        activationRequest.setSinklib_version("v0.1.0.020623");
        activationRequest.setRequest_code(str);
        String string = activationRequest.toString();
        LogUtils.d("恒爱血压 激活请求 = " + string);
        new utenfor().execute(string);
    }

    public void activeMoodAlgorithm(String str, MoodServerListener moodServerListener) {
        LogUtils.d("执行了 activeMoodAlgorithm 1");
        this.f24941a = moodServerListener;
        this.f24942b = new MoodPressureFatigueInfo();
        if (!utendo()) {
            this.f24942b.setTestResultStatus(5);
            this.f24941a.onMoodServerStatus(this.f24942b);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AppMeasurementSdk.ConditionalUserProperty.ACTIVE, str);
            new utenif().execute(jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
            LogUtils.d("执行了 activeMoodAlgorithm2 JSONException =" + e2);
            this.f24942b.setTestResultStatus(1);
            this.f24941a.onMoodServerStatus(this.f24942b);
        }
    }

    public void getMoodPressureData(String str, String str2, String str3, MoodServerListener moodServerListener) {
        LogUtils.d("执行了 getMoodPressureData 1");
        this.f24941a = moodServerListener;
        this.f24942b = new MoodPressureFatigueInfo();
        if (!utendo()) {
            this.f24942b.setTestResultStatus(5);
            this.f24941a.onMoodServerStatus(this.f24942b);
            return;
        }
        List listUtendo = utendo(str3);
        JSONObject jSONObject = new JSONObject();
        try {
            String moodOpenId = SPUtil.getInstance().getMoodOpenId();
            String lastConnectDeviceAddress = SPUtil.getInstance().getLastConnectDeviceAddress();
            jSONObject.put("open_id", moodOpenId);
            jSONObject.put("feature_data", new JSONArray((Collection) listUtendo));
            jSONObject.put(f.f21694p, CalendarUtils.strMinuteToDateSecTime(str));
            jSONObject.put(f.f21695q, CalendarUtils.strMinuteToDateSecTime(str2));
            jSONObject.put("device_code", lastConnectDeviceAddress);
            jSONObject.put("sensor_type", SPUtil.getInstance().getMoodSensorType());
            new utenint().execute(jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
            LogUtils.d("执行了 getMoodPressureData 2 JSONException =" + e2);
            this.f24942b.setTestResultStatus(1);
            this.f24941a.onMoodServerStatus(this.f24942b);
        }
    }

    public final List utendo(String str) {
        byte[] bArrHexStringToBytes = GBUtils.getInstance().hexStringToBytes(str);
        this.f24943c = 0;
        int length = bArrHexStringToBytes.length / 32;
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2++) {
            ArrayList arrayList2 = new ArrayList();
            for (int i3 = 0; i3 < 8; i3++) {
                int iUtendo = utendo(bArrHexStringToBytes, (i2 * 32) + (i3 * 4));
                if (iUtendo < 0) {
                    this.f24943c++;
                }
                arrayList2.add(Float.valueOf(Float.intBitsToFloat(iUtendo)));
            }
            arrayList.add(arrayList2);
        }
        LogUtils.i("test allList.size() =" + arrayList.size() + ",负数个数negativeNumbersCount =" + this.f24943c);
        return arrayList;
    }

    public final int utendo(byte[] bArr, int i2) {
        return (bArr[i2] & 255) | ((bArr[i2 + 3] << Ascii.CAN) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[i2 + 2] << 16) & 16711680) | ((bArr[i2 + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
    }

    public final boolean utendo() {
        NetworkInfo[] allNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) UteBleClient.getContext().getSystemService("connectivity");
        if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
