package com.google.android.gms.tasks;

import androidx.annotation.NonNull;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class TaskExecutors {

    @NonNull
    public static final Executor MAIN_THREAD = new zzu();

    /* renamed from: a, reason: collision with root package name */
    static final Executor f13338a = new zzt();

    private TaskExecutors() {
    }
}
