package h;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.health.connect.client.PermissionController;

/* loaded from: classes.dex */
public abstract /* synthetic */ class b {
    static {
        PermissionController.Companion companion = PermissionController.INSTANCE;
    }

    public static ActivityResultContract a() {
        return PermissionController.INSTANCE.createRequestPermissionResultContract();
    }

    public static ActivityResultContract b(String str) {
        return PermissionController.INSTANCE.createRequestPermissionResultContract(str);
    }

    public static ActivityResultContract c() {
        return PermissionController.INSTANCE.createRequestPermissionResultContractLegacy();
    }

    public static ActivityResultContract d(String str) {
        return PermissionController.INSTANCE.createRequestPermissionResultContractLegacy(str);
    }
}
