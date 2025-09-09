package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.profileinstaller.ProfileInstallReceiver;
import java.io.File;

/* loaded from: classes2.dex */
class BenchmarkOperation {

    @RequiresApi(api = 21)
    private static class Api21ContextHelper {
        private Api21ContextHelper() {
        }

        static File a(Context context) {
            return context.getCodeCacheDir();
        }
    }

    @RequiresApi(api = 24)
    private static class Api24ContextHelper {
        private Api24ContextHelper() {
        }

        static File a(Context context) {
            return context.createDeviceProtectedStorageContext().getCodeCacheDir();
        }
    }

    private BenchmarkOperation() {
    }

    static boolean a(File file) {
        if (!file.isDirectory()) {
            file.delete();
            return true;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            return false;
        }
        boolean z2 = true;
        for (File file2 : fileArrListFiles) {
            z2 = a(file2) && z2;
        }
        return z2;
    }

    static void b(Context context, ProfileInstallReceiver.ResultDiagnostics resultDiagnostics) {
        if (a(Build.VERSION.SDK_INT >= 24 ? Api24ContextHelper.a(context) : Api21ContextHelper.a(context))) {
            resultDiagnostics.onResultReceived(14, null);
        } else {
            resultDiagnostics.onResultReceived(15, null);
        }
    }
}
