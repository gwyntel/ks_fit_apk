package androidx.webkit.internal;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;

@RequiresApi(33)
/* loaded from: classes2.dex */
public class ApiHelperForTiramisu {
    private ApiHelperForTiramisu() {
    }

    @DoNotInline
    static ServiceInfo a(PackageManager packageManager, ComponentName componentName, PackageManager.ComponentInfoFlags componentInfoFlags) throws PackageManager.NameNotFoundException {
        return packageManager.getServiceInfo(componentName, componentInfoFlags);
    }

    @DoNotInline
    static PackageManager.ComponentInfoFlags b(long j2) {
        return PackageManager.ComponentInfoFlags.of(j2);
    }
}
