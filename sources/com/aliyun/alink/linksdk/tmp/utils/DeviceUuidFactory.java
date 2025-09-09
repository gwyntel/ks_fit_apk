package com.aliyun.alink.linksdk.tmp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import com.aliyun.alink.linksdk.tmp.TmpSdk;

/* loaded from: classes2.dex */
public class DeviceUuidFactory {
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static final String PREFS_FILE = "device_id.xml";
    protected static volatile String mDevicdId;

    public static String getDeviceUuidStr() {
        init(TmpSdk.getContext());
        return mDevicdId;
    }

    public static void init(Context context) {
        if (mDevicdId == null) {
            synchronized (DeviceUuidFactory.class) {
                try {
                    if (mDevicdId == null) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_FILE, 0);
                        String string = sharedPreferences.getString("device_id", null);
                        if (string != null) {
                            mDevicdId = string;
                        } else {
                            String string2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
                            if ("9774d56d682e549c".equals(string2)) {
                                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                                if (deviceId == null) {
                                    deviceId = new String(Base64.encode(TextHelper.getRandomString().getBytes(), 0));
                                }
                                mDevicdId = deviceId;
                            } else {
                                mDevicdId = string2;
                            }
                            sharedPreferences.edit().putString("device_id", mDevicdId.toString()).commit();
                        }
                    }
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                } finally {
                }
            }
        }
    }
}
