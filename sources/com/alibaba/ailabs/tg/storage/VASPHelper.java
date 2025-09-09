package com.alibaba.ailabs.tg.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.alibaba.ailabs.tg.UtilsConfig;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes2.dex */
public class VASPHelper {
    private static final String VASP_NAME = "video_assistant_sp";
    private static VASPHelper mInstance;
    private SharedPreferences mSharedPreferences;

    private VASPHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext().getSharedPreferences(VASP_NAME, 0);
    }

    public static VASPHelper getInstance() {
        VASPHelper vASPHelper = mInstance;
        if (vASPHelper != null) {
            return vASPHelper;
        }
        synchronized (VASPHelper.class) {
            try {
                if (mInstance == null) {
                    mInstance = new VASPHelper(UtilsConfig.getInstance().getAppContext());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return mInstance;
    }

    public void clear() {
        this.mSharedPreferences.edit().clear().commit();
    }

    public String get(String str, String str2) {
        return this.mSharedPreferences.getString(str, str2);
    }

    public boolean isHas(String str) {
        return this.mSharedPreferences.contains(str);
    }

    public void put(String str, String str2) {
        this.mSharedPreferences.edit().putString(str, str2).commit();
    }

    public void remove(String str) {
        this.mSharedPreferences.edit().remove(str).commit();
    }

    public long get(String str, long j2) {
        return this.mSharedPreferences.getLong(str, j2);
    }

    public void put(String str, long j2) {
        this.mSharedPreferences.edit().putLong(str, j2).commit();
    }

    public int get(String str, int i2) {
        return this.mSharedPreferences.getInt(str, i2);
    }

    public void put(String str, int i2) {
        this.mSharedPreferences.edit().putInt(str, i2).commit();
    }

    public boolean get(String str, boolean z2) {
        return this.mSharedPreferences.getBoolean(str, z2);
    }

    public void put(String str, boolean z2) {
        this.mSharedPreferences.edit().putBoolean(str, z2).commit();
    }
}
