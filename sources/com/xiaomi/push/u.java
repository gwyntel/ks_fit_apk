package com.xiaomi.push;

import android.content.Context;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public abstract class u implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private Context f24638a;

    /* renamed from: a, reason: collision with other field name */
    private File f1122a;

    /* renamed from: a, reason: collision with other field name */
    private Runnable f1123a;

    /* synthetic */ u(Context context, File file, v vVar) {
        this(context, file);
    }

    public static void a(Context context, File file, Runnable runnable) throws IOException {
        new v(context, file, runnable).run();
    }

    protected abstract void a(Context context);

    @Override // java.lang.Runnable
    public final void run() throws IOException {
        t tVarA = null;
        try {
            try {
                if (this.f1122a == null) {
                    this.f1122a = new File(this.f24638a.getFilesDir(), "default_locker");
                }
                tVarA = t.a(this.f24638a, this.f1122a);
                Runnable runnable = this.f1123a;
                if (runnable != null) {
                    runnable.run();
                }
                a(this.f24638a);
                if (tVarA == null) {
                    return;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (tVarA == null) {
                    return;
                }
            }
            tVarA.a();
        } catch (Throwable th) {
            if (tVarA != null) {
                tVarA.a();
            }
            throw th;
        }
    }

    private u(Context context, File file) {
        this.f24638a = context;
        this.f1122a = file;
    }
}
