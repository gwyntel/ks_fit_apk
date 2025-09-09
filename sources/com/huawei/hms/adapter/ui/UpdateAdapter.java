package com.huawei.hms.adapter.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.activity.IBridgeActivityDelegate;
import com.huawei.hms.adapter.AvailableUtil;
import com.huawei.hms.adapter.internal.CommonCode;
import com.huawei.hms.adapter.sysobs.SystemManager;
import com.huawei.hms.availableupdate.c;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.update.kpms.KpmsConstant;
import com.huawei.hms.update.ui.UpdateBean;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.PackageManagerHelper;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class UpdateAdapter implements IBridgeActivityDelegate {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<Activity> f15803a;

    /* renamed from: b, reason: collision with root package name */
    private Context f15804b;

    /* renamed from: c, reason: collision with root package name */
    private int f15805c;

    /* renamed from: d, reason: collision with root package name */
    private UpdateBean f15806d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f15807e = false;

    private boolean a(Intent intent, Activity activity) {
        if (!intent.getBooleanExtra(CommonCode.MapKey.NEW_UPDATE, false)) {
            return false;
        }
        HMSLog.i("UpdateAdapter", "4.0 framework HMSCore upgrade process");
        String hMSPackageName = HMSPackageManager.getInstance(activity.getApplicationContext()).getHMSPackageName();
        ComponentName componentName = new ComponentName(hMSPackageName, "com.huawei.hms.fwksdk.stub.UpdateStubActivity");
        Intent intent2 = new Intent();
        intent2.putExtra(KpmsConstant.CALLER_PACKAGE_NAME, activity.getApplicationContext().getPackageName());
        intent2.putExtra(KpmsConstant.UPDATE_PACKAGE_NAME, hMSPackageName);
        intent2.setComponent(componentName);
        activity.startActivityForResult(intent2, 1001);
        return true;
    }

    private Activity b() {
        WeakReference<Activity> weakReference = this.f15803a;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private void c() {
        SystemManager.getInstance().notifyUpdateResult(8);
        a();
    }

    public static Object invokeMethod(String str, String str2, Object[] objArr) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i2 = 0; i2 < objArr.length; i2++) {
            Object obj = objArr[i2];
            if (obj instanceof Activity) {
                clsArr[i2] = Activity.class;
            } else if (obj instanceof Context) {
                clsArr[i2] = Context.class;
            } else if (obj instanceof UpdateBean) {
                clsArr[i2] = UpdateBean.class;
            } else if (obj instanceof Integer) {
                clsArr[i2] = Integer.TYPE;
            } else if (obj instanceof Boolean) {
                clsArr[i2] = Boolean.TYPE;
            } else {
                HMSLog.e("UpdateAdapter", "not set args[" + i2 + "] type");
            }
        }
        try {
            Class<?> cls = Class.forName(str);
            return cls.getMethod(str2, clsArr).invoke(cls.getDeclaredConstructor(null).newInstance(null), objArr);
        } catch (ClassCastException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e2) {
            HMSLog.e("UpdateAdapter", "invoke " + str + "." + str2 + " fail. " + e2.getMessage());
            return null;
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public int getRequestCode() {
        return 1001;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityCreate(Activity activity) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (activity == null) {
            HMSLog.i("UpdateAdapter", "activity == null");
            c();
            return;
        }
        this.f15804b = activity.getApplicationContext();
        this.f15803a = new WeakReference<>(activity);
        if (c.f15869b.a(b())) {
            Intent intent = activity.getIntent();
            if (intent == null) {
                c();
                return;
            }
            try {
                this.f15805c = intent.getIntExtra(CommonCode.MapKey.UPDATE_VERSION, 0);
            } catch (Exception e2) {
                HMSLog.e("UpdateAdapter", "get update_version:" + e2.getMessage());
            }
            if (this.f15805c == 0) {
                c();
                return;
            }
            if (intent.hasExtra("installHMS")) {
                this.f15807e = true;
            }
            if (!a(intent, activity) && AvailableUtil.isInstallerLibExist(this.f15804b)) {
                UpdateBean updateBean = (UpdateBean) invokeMethod("com.huawei.hms.adapter.ui.InstallerAdapter", "setUpdateBean", new Object[]{activity, Integer.valueOf(this.f15805c), Boolean.valueOf(this.f15807e)});
                this.f15806d = updateBean;
                invokeMethod("com.huawei.hms.adapter.ui.InstallerAdapter", "startUpdateHms", new Object[]{activity, updateBean, 1001});
                this.f15806d = null;
            }
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityDestroy() {
        HMSLog.i("UpdateAdapter", "onBridgeActivityDestroy");
        c.f15869b.b(b());
        this.f15803a = null;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public boolean onBridgeActivityResult(int i2, int i3, Intent intent) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (i2 != getRequestCode()) {
            this.f15806d = null;
            return false;
        }
        HMSLog.i("UpdateAdapter", "onBridgeActivityResult " + i3);
        if (AvailableUtil.isInstallerLibExist(this.f15804b) && i3 == 1214) {
            HMSLog.i("UpdateAdapter", "Enter update escape route");
            Activity activityB = b();
            if (activityB == null) {
                HMSLog.e("UpdateAdapter", "bridgeActivity is null, update escape failed ");
                this.f15806d = null;
                return true;
            }
            invokeMethod("com.huawei.hms.update.manager.UpdateManager", "startUpdate", new Object[]{activityB, 1001, this.f15806d});
            this.f15806d = null;
        }
        if (i3 == -1) {
            if (intent != null) {
                if (intent.getIntExtra(KpmsConstant.KIT_UPDATE_RESULT, 0) == 1) {
                    HMSLog.i("UpdateAdapter", "new framework update process,Error resolved successfully!");
                    SystemManager.getInstance().notifyUpdateResult(0);
                    this.f15806d = null;
                    a();
                    return true;
                }
                a(intent);
            }
        } else if (i3 == 0) {
            HMSLog.i("UpdateAdapter", "Activity.RESULT_CANCELED");
            this.f15806d = null;
            Activity activityB2 = b();
            if (activityB2 == null) {
                return true;
            }
            String hMSPackageName = HMSPackageManager.getInstance(activityB2.getApplicationContext()).getHMSPackageName();
            if (this.f15807e || a(activityB2, hMSPackageName, this.f15805c)) {
                HMSLog.i("UpdateAdapter", "Resolve error, process canceled by user clicking back button!");
                SystemManager.getInstance().notifyUpdateResult(13);
            } else {
                SystemManager.getInstance().notifyUpdateResult(0);
            }
        } else if (i3 == 1) {
            SystemManager.getInstance().notifyUpdateResult(28);
        }
        a();
        return true;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeConfigurationChanged() {
        HMSLog.i("UpdateAdapter", "onBridgeConfigurationChanged");
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onKeyUp(int i2, KeyEvent keyEvent) {
        HMSLog.i("UpdateAdapter", "On key up when resolve conn error");
    }

    private void a(Intent intent) {
        int intExtra = intent.getIntExtra(BridgeActivity.EXTRA_RESULT, -1);
        if (intExtra == 0) {
            HMSLog.i("UpdateAdapter", "Error resolved successfully!");
            SystemManager.getInstance().notifyUpdateResult(0);
        } else if (intExtra == 13) {
            HMSLog.i("UpdateAdapter", "Resolve error process canceled by user!");
            SystemManager.getInstance().notifyUpdateResult(13);
        } else if (intExtra == 8) {
            HMSLog.i("UpdateAdapter", "Internal error occurred, recommended retry.");
            SystemManager.getInstance().notifyUpdateResult(8);
        } else {
            HMSLog.i("UpdateAdapter", "Other error codes.");
            SystemManager.getInstance().notifyUpdateResult(intExtra);
        }
    }

    private void a() {
        Activity activityB = b();
        if (activityB == null || activityB.isFinishing()) {
            return;
        }
        activityB.finish();
    }

    private boolean a(Context context, String str, int i2) {
        if (context == null || TextUtils.isEmpty(str) || i2 == 0) {
            return false;
        }
        PackageManagerHelper packageManagerHelper = new PackageManagerHelper(context);
        return PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(packageManagerHelper.getPackageStates(str)) || packageManagerHelper.getPackageVersionCode(str) < i2;
    }
}
