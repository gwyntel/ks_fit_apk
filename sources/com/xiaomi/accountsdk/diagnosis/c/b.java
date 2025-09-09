package com.xiaomi.accountsdk.diagnosis.c;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes4.dex */
public class b extends AsyncTask<Void, Void, String> {

    /* renamed from: a, reason: collision with root package name */
    private final a f23311a;

    /* renamed from: b, reason: collision with root package name */
    private final int f23312b;

    /* renamed from: c, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private final Context f23313c;

    public interface a {
        void onReadLog(String str);
    }

    public b(Context context, a aVar, int i2) {
        this.f23313c = context.getApplicationContext();
        this.f23311a = aVar;
        this.f23312b = i2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String doInBackground(Void... voidArr) throws IOException {
        List listA = a(a());
        if (listA != null && listA.size() > this.f23312b) {
            listA = listA.subList(listA.size() - this.f23312b, listA.size() - 1);
        }
        return TextUtils.join("\n", listA);
    }

    private ArrayList<String> a(String[] strArr) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d -v time").getInputStream()));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (line.contains(strArr[i2])) {
                        arrayList.add(line);
                        break;
                    }
                    i2++;
                }
            }
        } catch (IOException unused) {
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(String str) {
        this.f23311a.onReadLog(str);
        super.onPostExecute(str);
    }

    private String[] a() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        String packageName = this.f23313c.getPackageName();
        ActivityManager activityManager = (ActivityManager) this.f23313c.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        HashSet hashSet = new HashSet();
        if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                String str = runningAppProcessInfo.processName;
                if (str != null && str.contains(packageName)) {
                    hashSet.add(String.valueOf(runningAppProcessInfo.pid));
                }
            }
        }
        hashSet.add(String.valueOf(Process.myPid()));
        return (String[]) hashSet.toArray(new String[0]);
    }
}
