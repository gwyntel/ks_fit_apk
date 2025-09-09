package com.aliyun.dns;

import android.text.TextUtils;
import com.aliyun.utils.f;

/* loaded from: classes2.dex */
public class DomainProcessor {
    private static final String TAG;
    private static DomainProcessor sInstance;

    static {
        f.f();
        TAG = DomainProcessor.class.getSimpleName();
        sInstance = null;
    }

    private DomainProcessor() {
    }

    public static DomainProcessor getInstance() {
        if (sInstance == null) {
            synchronized (DomainProcessor.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DomainProcessor();
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public static void loadClass() {
    }

    private static native void nAddEnhancedHttpDnsDomain(String str);

    private static native void nAddPreResolveDomain(String str);

    @Deprecated
    public void addEnhancedHttpDnsDomain(String str) {
        if (f.b() && !TextUtils.isEmpty(str)) {
            nAddEnhancedHttpDnsDomain(str);
        }
    }

    public void addPreResolveDomain(String str) {
        if (f.b() && !TextUtils.isEmpty(str)) {
            nAddPreResolveDomain(str);
        }
    }
}
