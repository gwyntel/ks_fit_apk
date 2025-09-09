package kotlinx.coroutines.guava;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.f;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.JvmField;
import kotlinx.coroutines.AbstractCoroutine;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00028\u0000H\u0014¢\u0006\u0004\b\t\u0010\nJ\u001f\u0010\u000f\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0014¢\u0006\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u00118\u0006X\u0087\u0004¢\u0006\u0006\n\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/guava/ListenableFutureCoroutine;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/AbstractCoroutine;", "Lkotlin/coroutines/CoroutineContext;", f.X, "<init>", "(Lkotlin/coroutines/CoroutineContext;)V", "value", "", "onCompleted", "(Ljava/lang/Object;)V", "", "cause", "", "handled", "m", "(Ljava/lang/Throwable;Z)V", "Lkotlinx/coroutines/guava/JobListenableFuture;", "future", "Lkotlinx/coroutines/guava/JobListenableFuture;", "kotlinx-coroutines-guava"}, k = 1, mv = {1, 8, 0})
/* loaded from: classes5.dex */
final class ListenableFutureCoroutine<T> extends AbstractCoroutine<T> {

    @JvmField
    @NotNull
    public final JobListenableFuture<T> future;

    public ListenableFutureCoroutine(@NotNull CoroutineContext coroutineContext) {
        super(coroutineContext, true, true);
        this.future = new JobListenableFuture<>(this);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void m(Throwable cause, boolean handled) {
        this.future.completeExceptionallyOrCancel(cause);
    }

    @Override // kotlinx.coroutines.AbstractCoroutine
    protected void onCompleted(Object value) {
        this.future.complete(value);
    }
}
