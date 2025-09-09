package io.flutter.plugins.pathprovider;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class Messages {

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    public interface PathProviderApi {
        @Nullable
        String getApplicationCachePath();

        @Nullable
        String getApplicationDocumentsPath();

        @Nullable
        String getApplicationSupportPath();

        @NonNull
        List<String> getExternalCachePaths();

        @Nullable
        String getExternalStoragePath();

        @NonNull
        List<String> getExternalStoragePaths(@NonNull StorageDirectory storageDirectory);

        @Nullable
        String getTemporaryPath();
    }

    public enum StorageDirectory {
        ROOT(0),
        MUSIC(1),
        PODCASTS(2),
        RINGTONES(3),
        ALARMS(4),
        NOTIFICATIONS(5),
        PICTURES(6),
        MOVIES(7),
        DOWNLOADS(8),
        DCIM(9),
        DOCUMENTS(10);

        final int index;

        StorageDirectory(int i2) {
            this.index = i2;
        }
    }

    @NonNull
    protected static ArrayList<Object> wrapError(@NonNull Throwable th) {
        ArrayList<Object> arrayList = new ArrayList<>(3);
        if (th instanceof FlutterError) {
            FlutterError flutterError = (FlutterError) th;
            arrayList.add(flutterError.code);
            arrayList.add(flutterError.getMessage());
            arrayList.add(flutterError.details);
        } else {
            arrayList.add(th.toString());
            arrayList.add(th.getClass().getSimpleName());
            arrayList.add("Cause: " + th.getCause() + ", Stacktrace: " + Log.getStackTraceString(th));
        }
        return arrayList;
    }
}
