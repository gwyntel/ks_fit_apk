package com.facebook.internal.instrument.crashreport;

import android.os.Process;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.instrument.InstrumentData;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import java.io.IOException;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes3.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final int MAX_CRASH_REPORT_NUM = 5;
    private static final String TAG = "com.facebook.internal.instrument.crashreport.CrashHandler";

    @Nullable
    private static CrashHandler instance;
    private boolean mEndApplication = false;

    @Nullable
    private final Thread.UncaughtExceptionHandler mPreviousHandler;

    private CrashHandler(@Nullable Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.mPreviousHandler = uncaughtExceptionHandler;
    }

    public static synchronized void enable() {
        try {
            if (FacebookSdk.getAutoLogAppEventsEnabled()) {
                sendCrashReports();
            }
            if (instance != null) {
                Log.w(TAG, "Already enabled!");
                return;
            }
            CrashHandler crashHandler = new CrashHandler(Thread.getDefaultUncaughtExceptionHandler());
            instance = crashHandler;
            Thread.setDefaultUncaughtExceptionHandler(crashHandler);
        } catch (Throwable th) {
            throw th;
        }
    }

    private static void killProcess() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(10);
        } catch (Throwable unused) {
        }
    }

    private static void sendCrashReports() {
        File[] fileArrListCrashReportFiles = InstrumentUtility.listCrashReportFiles();
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListCrashReportFiles) {
            InstrumentData instrumentData = new InstrumentData(file);
            if (instrumentData.isValid()) {
                arrayList.add(instrumentData);
            }
        }
        Collections.sort(arrayList, new Comparator<InstrumentData>() { // from class: com.facebook.internal.instrument.crashreport.CrashHandler.1
            @Override // java.util.Comparator
            public int compare(InstrumentData instrumentData2, InstrumentData instrumentData3) {
                return instrumentData2.compareTo(instrumentData3);
            }
        });
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < arrayList.size() && i2 < 5; i2++) {
            jSONArray.put(arrayList.get(i2));
        }
        InstrumentUtility.sendReports("crash_reports", jSONArray, new GraphRequest.Callback() { // from class: com.facebook.internal.instrument.crashreport.CrashHandler.2
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                try {
                    if (graphResponse.getError() == null && graphResponse.getJSONObject().getBoolean("success")) {
                        for (int i3 = 0; arrayList.size() > i3; i3++) {
                            ((InstrumentData) arrayList.get(i3)).clear();
                        }
                    }
                } catch (JSONException unused) {
                }
            }
        });
    }

    public void endApplication() {
        this.mEndApplication = true;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) throws IOException {
        if (InstrumentUtility.isSDKRelatedException(th)) {
            new InstrumentData(th, InstrumentData.Type.CrashReport).save();
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.mPreviousHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
        if (this.mEndApplication) {
            killProcess();
        }
    }
}
