package com.aliyun.alink.linksdk.tmp.utils;

/* loaded from: classes2.dex */
public abstract class SingleInstance<T> {
    protected volatile T mInstance;

    public abstract T create();

    public final T getInstance() {
        if (this.mInstance == null) {
            synchronized (this) {
                try {
                    if (this.mInstance == null) {
                        this.mInstance = create();
                    }
                } finally {
                }
            }
        }
        return this.mInstance;
    }
}
