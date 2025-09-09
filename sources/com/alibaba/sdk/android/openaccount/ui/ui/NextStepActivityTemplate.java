package com.alibaba.sdk.android.openaccount.ui.ui;

import android.os.Bundle;
import android.widget.Button;
import com.alibaba.sdk.android.openaccount.annotation.ExtensionPoint;
import com.alibaba.sdk.android.openaccount.ui.widget.NextStepButtonWatcher;
import org.mozilla.javascript.ES6Iterator;

/* loaded from: classes2.dex */
public abstract class NextStepActivityTemplate extends ActivityTemplate {

    @ExtensionPoint
    protected Button next;
    private NextStepButtonWatcher nextStepTextWatcher = new NextStepButtonWatcher();

    @ExtensionPoint
    protected NextStepButtonWatcher getNextStepButtonWatcher() {
        return this.nextStepTextWatcher;
    }

    @Override // com.alibaba.sdk.android.openaccount.ui.ui.ActivityTemplate, com.alibaba.sdk.android.openaccount.ui.ui.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Button button = (Button) findViewById(ES6Iterator.NEXT_METHOD);
        this.next = button;
        if (button != null) {
            this.nextStepTextWatcher.setNextStepButton(button);
        }
    }
}
