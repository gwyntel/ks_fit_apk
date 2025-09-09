package com.huawei.secure.android.common.ssl.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes4.dex */
public class c extends AsyncTask<Context, Integer, Boolean> {

    /* renamed from: a, reason: collision with root package name */
    private static final String f18484a = "c";

    /* renamed from: b, reason: collision with root package name */
    private static final long f18485b = 432000000;

    /* renamed from: c, reason: collision with root package name */
    private static final String f18486c = "lastCheckTime";

    /* renamed from: d, reason: collision with root package name */
    private static volatile boolean f18487d = false;

    @SuppressLint({"NewApi"})
    public static void a() {
        if (b()) {
            e.c(f18484a, "checkUpgradeBks, execute check task");
            new c().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ContextUtil.getInstance());
        }
    }

    private static boolean b() {
        if (f18487d) {
            return false;
        }
        Context contextUtil = ContextUtil.getInstance();
        if (contextUtil == null) {
            e.e(f18484a, "checkUpgradeBks, context is null");
            return false;
        }
        f18487d = true;
        long jA = g.a(f18486c, 0L, contextUtil);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - jA > f18485b) {
            g.b(f18486c, jCurrentTimeMillis, contextUtil);
            return true;
        }
        e.c(f18484a, "checkUpgradeBks, ignore");
        return false;
    }

    @Override // android.os.AsyncTask
    protected void onPreExecute() {
        e.a(f18484a, "onPreExecute");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean doInBackground(Context... contextArr) throws IOException {
        InputStream bksFromTss;
        long jCurrentTimeMillis = System.currentTimeMillis();
        try {
            bksFromTss = BksUtil.getBksFromTss(contextArr[0]);
        } catch (Exception e2) {
            e.b(f18484a, "doInBackground: exception : " + e2.getMessage());
            bksFromTss = null;
        }
        e.a(f18484a, "doInBackground: get bks from hms tss cost : " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms");
        if (bksFromTss != null) {
            d.a(bksFromTss);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Boolean bool) {
        if (bool.booleanValue()) {
            e.c(f18484a, "onPostExecute: upate done");
        } else {
            e.b(f18484a, "onPostExecute: upate failed");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onProgressUpdate(Integer... numArr) {
        e.c(f18484a, "onProgressUpdate");
    }
}
