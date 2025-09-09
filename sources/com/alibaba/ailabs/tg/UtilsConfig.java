package com.alibaba.ailabs.tg;

import android.annotation.SuppressLint;
import android.content.Context;

/* loaded from: classes2.dex */
public class UtilsConfig {
    private Context mContext;

    @SuppressLint({"StaticFieldLeak"})
    private static class SingletonHolder {
        private static final UtilsConfig INSTANCE = new UtilsConfig();

        private SingletonHolder() {
        }
    }

    public static UtilsConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Context getAppContext() {
        return this.mContext;
    }

    public void setAppContext(Context context) {
        this.mContext = context;
    }

    private UtilsConfig() {
    }
}
