package com.aliyun.iot.aep.component.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class BaseUrlHandler implements IUrlHandler {
    protected List<String> mUrls = new ArrayList();

    public List<String> getUrls() {
        return this.mUrls;
    }

    @Override // com.aliyun.iot.aep.component.router.IUrlHandler
    public void onUrlHandle(Context context, String str, Bundle bundle, boolean z2, int i2) {
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
    }
}
