package com.facebook.internal.instrument.threadcheck;

import android.os.Looper;
import android.util.Log;
import androidx.annotation.RestrictTo;
import com.facebook.internal.instrument.InstrumentData;
import java.io.IOException;
import java.util.Locale;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class ThreadCheckHandler {
    private static final String TAG = "com.facebook.internal.instrument.threadcheck.ThreadCheckHandler";

    private ThreadCheckHandler() {
    }

    private static void log(String str, Class<?> cls, String str2, String str3) throws IOException {
        String str4 = String.format(Locale.US, "%s annotation violation detected in %s.%s%s. Current looper is %s and main looper is %s.", str, cls.getName(), str2, str3, Looper.myLooper(), Looper.getMainLooper());
        Exception exc = new Exception();
        Log.e(TAG, str4, exc);
        new InstrumentData(exc, InstrumentData.Type.ThreadCheck).save();
    }

    public static void uiThreadViolationDetected(Class<?> cls, String str, String str2) throws IOException {
        log("@UiThread", cls, str, str2);
    }

    public static void workerThreadViolationDetected(Class<?> cls, String str, String str2) throws IOException {
        log("@WorkerThread", cls, str, str2);
    }
}
