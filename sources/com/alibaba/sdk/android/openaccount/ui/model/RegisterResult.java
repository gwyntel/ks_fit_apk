package com.alibaba.sdk.android.openaccount.ui.model;

import com.alibaba.sdk.android.openaccount.model.LoginSuccessResult;
import java.io.Serializable;

/* loaded from: classes2.dex */
public class RegisterResult implements Serializable {
    private static final long serialVersionUID = 4749747351683353560L;
    public String clientVerifyData;
    public LoginSuccessResult loginSuccessResult;
    public String smsCheckTrustToken;
}
