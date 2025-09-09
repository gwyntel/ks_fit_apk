package com.huawei.hms.support.api.push;

import android.app.Activity;
import android.os.Bundle;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import com.huawei.android.hms.push.R;
import com.huawei.hms.push.r;

/* loaded from: classes4.dex */
public class TransActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.hwpush_trans_activity);
        getWindow().addFlags(AccessibilityEventCompat.TYPE_VIEW_TARGETED_BY_SCROLL);
        r.a(this, getIntent());
        finish();
    }
}
