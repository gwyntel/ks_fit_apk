package com.kingsmith.xiaojin;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.FragmentActivity;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.f;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014¨\u0006\u000b"}, d2 = {"Lcom/kingsmith/xiaojin/ThirdJumpActivity;", "Landroidx/fragment/app/FragmentActivity;", "()V", "isOtherUIExisting", "", f.X, "Landroid/content/Context;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_externalRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class ThirdJumpActivity extends FragmentActivity {
    private final boolean isOtherUIExisting(Context context) throws SecurityException {
        try {
            String name = ThirdJumpActivity.class.getName();
            String packageName = getPackageName();
            Object systemService = context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
            List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) systemService).getRunningTasks(10);
            runningTasks.size();
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                ComponentName componentName = runningTaskInfo.baseActivity;
                Intrinsics.checkNotNull(componentName);
                String className = componentName.getClassName();
                Intrinsics.checkNotNullExpressionValue(className, "getClassName(...)");
                if (!Intrinsics.areEqual(className, name)) {
                    ComponentName componentName2 = runningTaskInfo.baseActivity;
                    Intrinsics.checkNotNull(componentName2);
                    if (Intrinsics.areEqual(componentName2.getPackageName(), packageName)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras;
        Set<String> setKeySet;
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri data = intent != null ? intent.getData() : null;
        String host = data != null ? data.getHost() : null;
        Intent intent2 = getIntent();
        String str = intent2 != null ? intent2.getPackage() : null;
        Intent intent3 = getIntent();
        String action = intent3 != null ? intent3.getAction() : null;
        Intent intent4 = getIntent();
        Log.e("ThirdJumpActivity", " onCreate action: " + action + " data: " + (intent4 != null ? intent4.getData() : null) + " packageName: " + str);
        if ((Intrinsics.areEqual(getIntent().getAction(), "android.intent.action.VIEW") && host != null && host.equals("xiaojin-eu")) || Intrinsics.areEqual(getIntent().getAction(), "android.nfc.action.NDEF_DISCOVERED")) {
            Intent intent5 = new Intent(this, (Class<?>) MainActivity.class);
            intent5.setAction(getIntent().getAction());
            intent5.addFlags(268435456);
            intent5.setData(getIntent().getData());
            if (getIntent().getExtras() != null && (extras = getIntent().getExtras()) != null && (setKeySet = extras.keySet()) != null && setKeySet.contains("android.nfc.extra.NDEF_MESSAGES")) {
                Log.e("ThirdJumpActivity", " NDEF_MESSAGES extras: " + getIntent().getExtras());
                Bundle extras2 = getIntent().getExtras();
                Intrinsics.checkNotNull(extras2);
                intent5.putExtras(extras2);
            }
            startActivity(intent5);
        }
        finish();
    }
}
