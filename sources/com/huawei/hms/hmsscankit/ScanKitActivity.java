package com.huawei.hms.hmsscankit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import com.huawei.hms.scankit.p.o4;
import com.huawei.hms.scankit.p.w7;

/* loaded from: classes4.dex */
public class ScanKitActivity extends Activity {
    private static final int REQUEST_SETTING_DETAILS = 1;
    private static final String TAG = "ScanKitActivity";
    static AlertDialog alertDialog;
    static a mDialog;
    private boolean hasCameraPermission;
    private OrientationEventListener mOrientationListener;
    private RemoteView remoteView;
    private int lastRotation = Integer.MAX_VALUE;
    private boolean errorReport = false;
    private boolean showGuide = false;

    private void cameraPermissionChange() {
        a aVar = mDialog;
        if (aVar != null) {
            aVar.dismiss();
        }
        AlertDialog alertDialog2 = alertDialog;
        if (alertDialog2 != null && alertDialog2.isShowing()) {
            alertDialog.dismiss();
        }
        if (isFinishing()) {
            return;
        }
        setResult(-1);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void gotoSetting() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        Uri uriFromParts = Uri.fromParts("package", getPackageName(), null);
        o4.a(TAG, "getPackageName ", getPackageName());
        intent.setData(uriFromParts);
        startActivityForResult(intent, 1);
    }

    private void setActivityUseNotchScreen(Activity activity) {
        if (activity != null) {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            if (Build.VERSION.SDK_INT >= 28) {
                attributes.layoutInDisplayCutoutMode = 1;
            }
            activity.getWindow().setAttributes(attributes);
        }
    }

    private void startOrientationChangeListener() {
        OrientationEventListener orientationEventListener = new OrientationEventListener(this) { // from class: com.huawei.hms.hmsscankit.ScanKitActivity.7
            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i2) {
                try {
                    int rotation = ScanKitActivity.this.getWindowManager().getDefaultDisplay().getRotation();
                    if (Math.abs(ScanKitActivity.this.lastRotation - rotation) == 2) {
                        ScanKitActivity.this.recreate();
                        Log.i(ScanKitActivity.TAG, "onOrientationChanged: currentRotation" + rotation);
                    }
                    ScanKitActivity.this.lastRotation = rotation;
                } catch (RuntimeException unused) {
                    Log.e(ScanKitActivity.TAG, "onOrientationChanged: RuntimeException");
                }
            }
        };
        this.mOrientationListener = orientationEventListener;
        orientationEventListener.enable();
    }

    @Override // android.app.Activity
    public void finish() {
        this.remoteView.onStop();
        w7.f17978c = true;
        Log.i(TAG, "ScankitActivity finish");
        super.finish();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        this.remoteView.onActivityResult(i2, i3, intent);
        if (i2 == 1) {
            cameraPermissionChange();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0135  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onCreate(android.os.Bundle r14) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.hms.hmsscankit.ScanKitActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.remoteView.onDestroy();
        w7.f17978c = true;
        Log.i(TAG, "ScankitActivity onDestroy");
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
        this.remoteView.onPause();
        Log.i(TAG, "ScankitActivity onPause");
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        this.remoteView.onRequestPermissionsResult(i2, strArr, iArr, this);
        super.onRequestPermissionsResult(i2, strArr, iArr);
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.remoteView.onResume();
        w7.f17978c = false;
        if (!this.hasCameraPermission && w7.a((Context) this)) {
            cameraPermissionChange();
        }
        Log.i(TAG, "ScankitActivity onResume");
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        this.remoteView.onStart();
        w7.f17978c = false;
        Log.i(TAG, "ScankitActivity onStart");
    }

    @Override // android.app.Activity
    protected void onStop() {
        super.onStop();
        if (!w7.f17978c) {
            this.remoteView.onStop();
            w7.f17978c = true;
        }
        Log.i(TAG, "ScankitActivity onStop");
    }
}
