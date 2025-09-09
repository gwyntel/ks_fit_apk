package xyz.bczl.flutter_scankit;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes5.dex */
public class ScanKitAPI {

    public static class FlutterError extends RuntimeException {
        public final String code;
        public final Object details;

        public FlutterError(@NonNull String str, @Nullable String str2, @Nullable Object obj) {
            super(str2);
            this.code = str;
            this.details = obj;
        }
    }

    public interface ScanKitApi {
        @NonNull
        Long createDefaultMode();

        @NonNull
        Map<String, Object> decode(@NonNull byte[] bArr, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map);

        @NonNull
        Map<String, Object> decodeBitmap(@NonNull byte[] bArr, @NonNull Map<String, Object> map);

        @NonNull
        Map<String, Object> decodeYUV(@NonNull byte[] bArr, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map);

        void disposeCustomizedMode(@NonNull Long l2);

        void disposeDefaultMode(@NonNull Long l2);

        @NonNull
        byte[] encode(@NonNull String str, @NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map);

        @NonNull
        Boolean getLightStatus(@NonNull Long l2);

        void pauseContinuouslyScan(@NonNull Long l2);

        void pickPhoto(@NonNull Long l2);

        void resumeContinuouslyScan(@NonNull Long l2);

        @NonNull
        Long startScan(@NonNull Long l2, @NonNull Long l3, @NonNull Map<String, Object> map);

        void switchLight(@NonNull Long l2);
    }

    protected static ArrayList a(Throwable th) {
        ArrayList arrayList = new ArrayList(3);
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
