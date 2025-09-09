package com.facebook.login;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes3.dex */
interface StartActivityDelegate {
    Activity getActivityContext();

    void startActivityForResult(Intent intent, int i2);
}
