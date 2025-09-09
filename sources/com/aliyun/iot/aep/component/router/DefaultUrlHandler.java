package com.aliyun.iot.aep.component.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/* loaded from: classes3.dex */
public class DefaultUrlHandler {
    public void onUrlHandle(Context context, String str, Bundle bundle, boolean z2, int i2) {
        onUrlHandle(context, str, bundle, z2, i2, null);
    }

    public void onUrlHandle(Context context, String str, Bundle bundle, boolean z2, int i2, IAsyncHandlerCallback iAsyncHandlerCallback) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(str));
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            if (z2 && (context instanceof Activity)) {
                ((Activity) context).startActivityForResult(intent, i2);
            } else {
                context.startActivity(intent);
            }
            if (iAsyncHandlerCallback != null) {
                iAsyncHandlerCallback.asyncHandle(true);
            }
        } catch (Exception unused) {
            if (iAsyncHandlerCallback != null) {
                iAsyncHandlerCallback.asyncHandle(false);
            }
        }
    }
}
