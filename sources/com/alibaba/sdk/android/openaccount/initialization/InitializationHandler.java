package com.alibaba.sdk.android.openaccount.initialization;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public interface InitializationHandler<T> {
    public static final int REQUEST_REQUIREMENT_MUST = 1;
    public static final int REQUEST_REQUIREMENT_NEVER = 2;
    public static final int REQUEST_REQUIREMENT_OPTIMISTIK = 3;

    Object createRequestParameters();

    String getRequestParameterKey();

    int getRequestRequirement();

    int getRequestServiceType();

    String getResponseValueKey();

    void handleResponseError(int i2, String str);

    T handleResponseValue(JSONObject jSONObject);
}
