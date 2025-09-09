package com.heytap.mcssdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.heytap.mcssdk.PushService;
import com.heytap.mcssdk.constant.Constants;
import com.pushsdk.BuildConfig;

/* loaded from: classes3.dex */
public class SharedPreferenceManager {
    private static final String DECRYPT_TAG = "decryptTag";
    private static final String HAS_DEFAULT_CHANNEL_CREATED = "hasDefaultChannelCreated";
    private static final String SHARED_PREFS_NAME = "shared_msg_sdk";
    private static final String STATIC_INFOMATION_SDK_VERSION = "lastUpLoadInfoSDKVersionName";
    private static final String STATIC_INFOMATION_UNIQUE_ID = "lastUploadInfoUniqueID";
    private Context mContext;
    private SharedPreferences mSharedPrefs;
    private Object prefsLock;

    private static class SharedPreferenceManagerInstanceHolder {

        /* renamed from: a, reason: collision with root package name */
        static SharedPreferenceManager f15450a = new SharedPreferenceManager();

        private SharedPreferenceManagerInstanceHolder() {
        }
    }

    public static SharedPreferenceManager getInstance() {
        return SharedPreferenceManagerInstanceHolder.f15450a;
    }

    private SharedPreferences getSharedPrefs() {
        Context context;
        SharedPreferences sharedPreferences = this.mSharedPrefs;
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        synchronized (this.prefsLock) {
            try {
                SharedPreferences sharedPreferences2 = this.mSharedPrefs;
                if (sharedPreferences2 != null || (context = this.mContext) == null) {
                    return sharedPreferences2;
                }
                SharedPreferences sharedPreferences3 = context.getSharedPreferences(SHARED_PREFS_NAME, 0);
                this.mSharedPrefs = sharedPreferences3;
                return sharedPreferences3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private Context getStorageContext(Context context) {
        boolean zIsFBEVersion = ApkInfoUtil.isFBEVersion();
        LogUtil.d("fbeVersion is " + zIsFBEVersion);
        return (!zIsFBEVersion || Build.VERSION.SDK_INT < 24) ? context.getApplicationContext() : context.createDeviceProtectedStorageContext();
    }

    public String getDecryptTag() {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getString(DECRYPT_TAG, "DES") : "DES";
    }

    public int getIntData(String str) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            return sharedPrefs.getInt(str, 0);
        }
        return 0;
    }

    public String getLastUpdataUniqueID() {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getString(STATIC_INFOMATION_UNIQUE_ID, "") : "";
    }

    public String getLastUploadInfoSDKVersion() {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getString(STATIC_INFOMATION_SDK_VERSION, "") : "";
    }

    public long getLongData(String str) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getLong(str, Constants.UNKNOWN_LONG.longValue()) : Constants.UNKNOWN_LONG.longValue();
    }

    public boolean hasDefaultChannelCreated() {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            return sharedPrefs.getBoolean(HAS_DEFAULT_CHANNEL_CREATED, false);
        }
        return false;
    }

    public void saveDecryptTag(String str) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            sharedPrefs.edit().putString(DECRYPT_TAG, str).commit();
        }
    }

    public void saveLastUploadUniqueID(String str) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            sharedPrefs.edit().putString(STATIC_INFOMATION_UNIQUE_ID, str).commit();
        }
    }

    public void saveSDKVersionName() {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            sharedPrefs.edit().putString(STATIC_INFOMATION_SDK_VERSION, BuildConfig.VERSION_NAME).commit();
        }
    }

    public void setHasDefaultChannelCreated(boolean z2) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            sharedPrefs.edit().putBoolean(HAS_DEFAULT_CHANNEL_CREATED, z2).commit();
        }
    }

    public void setIntData(String str, int i2) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            SharedPreferences.Editor editorEdit = sharedPrefs.edit();
            editorEdit.putInt(str, i2);
            editorEdit.apply();
        }
    }

    public void setLongData(String str, long j2) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        if (sharedPrefs != null) {
            SharedPreferences.Editor editorEdit = sharedPrefs.edit();
            editorEdit.putLong(str, j2);
            editorEdit.apply();
        }
    }

    private SharedPreferenceManager() {
        this.prefsLock = new Object();
        Context context = PushService.getInstance().getContext();
        if (context != null) {
            this.mContext = getStorageContext(context);
        }
        Context context2 = this.mContext;
        if (context2 != null) {
            this.mSharedPrefs = context2.getSharedPreferences(SHARED_PREFS_NAME, 0);
        }
    }

    public int getIntData(String str, int i2) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getInt(str, i2) : i2;
    }

    public long getLongData(String str, long j2) {
        SharedPreferences sharedPrefs = getSharedPrefs();
        return sharedPrefs != null ? sharedPrefs.getLong(str, j2) : j2;
    }
}
