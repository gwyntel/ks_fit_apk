package com.leeson.image_pickers.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public abstract class BaseActivity extends AppCompatActivity {
    private int REQUEST_CODE_PERMISSION = 1;

    private boolean checkPermissions(String[] strArr) {
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                return false;
            }
        }
        return true;
    }

    private List<String> getDeniedPermissions(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0 || ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    private boolean verifyPermissions(int[] iArr) {
        for (int i2 : iArr) {
            if (i2 != 0) {
                return false;
            }
        }
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 == this.REQUEST_CODE_PERMISSION) {
            if (verifyPermissions(iArr)) {
                permissionSuccess(this.REQUEST_CODE_PERMISSION);
                return;
            }
            for (String str : strArr) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                    permissonNecessity(this.REQUEST_CODE_PERMISSION);
                    return;
                }
            }
            permissionFail(this.REQUEST_CODE_PERMISSION);
        }
    }

    public void permissionFail(int i2) {
    }

    public void permissionSuccess(int i2) {
    }

    public void permissonNecessity(int i2) {
    }

    public void requestPermission(String[] strArr, int i2) {
        this.REQUEST_CODE_PERMISSION = i2;
        if (checkPermissions(strArr)) {
            permissionSuccess(this.REQUEST_CODE_PERMISSION);
        } else {
            List<String> deniedPermissions = getDeniedPermissions(strArr);
            ActivityCompat.requestPermissions(this, (String[]) deniedPermissions.toArray(new String[deniedPermissions.size()]), this.REQUEST_CODE_PERMISSION);
        }
    }

    public void showSettingDialog() {
        showTipsDialog();
    }

    public void showTipsDialog() {
        new AlertDialog.Builder(this).setTitle("提示信息").setCancelable(false).setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。").setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.leeson.image_pickers.activitys.BaseActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
                BaseActivity.this.finish();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.leeson.image_pickers.activitys.BaseActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
                BaseActivity.this.startAppSettings();
                BaseActivity.this.finish();
            }
        }).show();
    }

    public void startAppSettings() {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
