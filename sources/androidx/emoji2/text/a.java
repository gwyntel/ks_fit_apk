package androidx.emoji2.text;

import android.os.Handler;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final /* synthetic */ class a implements Executor {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ Handler f4033a;

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.f4033a.post(runnable);
    }
}
