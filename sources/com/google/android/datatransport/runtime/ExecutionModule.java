package com.google.android.datatransport.runtime;

import com.google.android.datatransport.runtime.dagger.Module;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Module
/* loaded from: classes3.dex */
abstract class ExecutionModule {
    static Executor a() {
        return new SafeLoggingExecutor(Executors.newSingleThreadExecutor());
    }
}
