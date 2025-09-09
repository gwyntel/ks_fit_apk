package com.huawei.hms.activity;

import android.app.Activity;
import android.os.Bundle;
import com.huawei.hms.utils.ResourceLoaderUtil;

/* loaded from: classes.dex */
public class EnableServiceActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(ResourceLoaderUtil.getLayoutId("activity_endisable_service"));
    }
}
