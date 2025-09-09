package com.facebook;

/* loaded from: classes3.dex */
public interface FacebookCallback<RESULT> {
    void onCancel();

    void onError(FacebookException facebookException);

    void onSuccess(RESULT result);
}
