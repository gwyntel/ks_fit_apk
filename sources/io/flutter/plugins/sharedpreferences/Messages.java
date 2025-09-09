package io.flutter.plugins.sharedpreferences;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public interface SharedPreferencesApi {
        @NonNull
        Boolean clear(@NonNull String str, @Nullable List<String> list);

        @NonNull
        Map<String, Object> getAll(@NonNull String str, @Nullable List<String> list);

        @NonNull
        Boolean remove(@NonNull String str);

        @NonNull
        Boolean setBool(@NonNull String str, @NonNull Boolean bool);

        @NonNull
        Boolean setDouble(@NonNull String str, @NonNull Double d2);

        @NonNull
        Boolean setInt(@NonNull String str, @NonNull Long l2);

        @NonNull
        Boolean setString(@NonNull String str, @NonNull String str2);

        @NonNull
        Boolean setStringList(@NonNull String str, @NonNull List<String> list);
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
