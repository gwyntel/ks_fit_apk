package com.heytap.mcssdk.processor;

import android.content.Context;
import com.heytap.msp.push.callback.IDataMessageCallBackService;
import com.heytap.msp.push.mode.BaseMode;

/* loaded from: classes3.dex */
public interface Processor {
    void process(Context context, BaseMode baseMode, IDataMessageCallBackService iDataMessageCallBackService);
}
