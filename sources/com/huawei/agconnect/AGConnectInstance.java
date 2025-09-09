package com.huawei.agconnect;

import android.content.Context;
import android.util.Log;
import com.huawei.agconnect.core.a.b;

/* loaded from: classes3.dex */
public abstract class AGConnectInstance {
    public static AGConnectInstance buildInstance(AGConnectOptions aGConnectOptions) {
        return b.a(aGConnectOptions);
    }

    public static AGConnectInstance getInstance() {
        return b.a();
    }

    public static synchronized void initialize(Context context) {
        Log.i("AGConnectInstance", "AGConnectInstance#initialize");
        b.a(context);
    }

    public abstract Context getContext();

    public abstract String getIdentifier();

    public abstract AGConnectOptions getOptions();

    public abstract <T> T getService(Class<? super T> cls);

    public static AGConnectInstance getInstance(String str) {
        return b.a(str);
    }

    public static synchronized void initialize(Context context, AGConnectOptionsBuilder aGConnectOptionsBuilder) {
        Log.i("AGConnectInstance", "AGConnectInstance#initialize with options");
        b.a(context, aGConnectOptionsBuilder);
    }
}
