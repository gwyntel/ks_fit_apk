package androidx.activity.contextaware;

import android.content.Context;
import com.umeng.analytics.pro.f;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CancellableContinuation;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"androidx/activity/contextaware/ContextAwareKt$withContextAvailable$2$listener$1", "Landroidx/activity/contextaware/OnContextAvailableListener;", "onContextAvailable", "", f.X, "Landroid/content/Context;", "activity_release"}, k = 1, mv = {1, 8, 0}, xi = 176)
@SourceDebugExtension({"SMAP\nContextAware.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContextAware.kt\nandroidx/activity/contextaware/ContextAwareKt$withContextAvailable$2$listener$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1#2:95\n*E\n"})
/* loaded from: classes.dex */
public final class ContextAwareKt$withContextAvailable$2$listener$1 implements OnContextAvailableListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ CancellableContinuation f1892a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Function1 f1893b;

    public ContextAwareKt$withContextAvailable$2$listener$1(CancellableContinuation<R> cancellableContinuation, Function1<Context, R> function1) {
        this.f1892a = cancellableContinuation;
        this.f1893b = function1;
    }

    @Override // androidx.activity.contextaware.OnContextAvailableListener
    public void onContextAvailable(@NotNull Context context) {
        Object objM837constructorimpl;
        Intrinsics.checkNotNullParameter(context, "context");
        CancellableContinuation cancellableContinuation = this.f1892a;
        Function1 function1 = this.f1893b;
        try {
            Result.Companion companion = Result.INSTANCE;
            objM837constructorimpl = Result.m837constructorimpl(function1.invoke(context));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM837constructorimpl = Result.m837constructorimpl(ResultKt.createFailure(th));
        }
        cancellableContinuation.resumeWith(objM837constructorimpl);
    }
}
