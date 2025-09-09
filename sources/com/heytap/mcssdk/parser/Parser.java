package com.heytap.mcssdk.parser;

import android.content.Context;
import android.content.Intent;
import com.heytap.msp.push.mode.BaseMode;

/* loaded from: classes3.dex */
public interface Parser<T> {
    BaseMode parse(Context context, int i2, Intent intent);
}
