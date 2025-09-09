package com.http.helper;

/* loaded from: classes3.dex */
public interface HttpCallback<T, K> {
    void onFail(String str, T t2);

    void onSuccess(String str, K k2);
}
