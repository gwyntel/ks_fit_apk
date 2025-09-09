package kotlin.sequences;

import androidx.exifinterface.media.ExifInterface;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B)\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0014¢\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0005\u0010\rR \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00068\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0007\u0010\u000eR$\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00028\u00010\u000fj\b\u0012\u0004\u0012\u00028\u0001`\u00108\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lkotlin/sequences/DistinctIterator;", ExifInterface.GPS_DIRECTION_TRUE, "K", "Lkotlin/collections/AbstractIterator;", "", "source", "Lkotlin/Function1;", "keySelector", "<init>", "(Ljava/util/Iterator;Lkotlin/jvm/functions/Function1;)V", "", "a", "()V", "Ljava/util/Iterator;", "Lkotlin/jvm/functions/Function1;", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "observed", "Ljava/util/HashSet;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes5.dex */
final class DistinctIterator<T, K> extends AbstractIterator<T> {

    @NotNull
    private final Function1<T, K> keySelector;

    @NotNull
    private final HashSet<K> observed;

    @NotNull
    private final Iterator<T> source;

    /* JADX WARN: Multi-variable type inference failed */
    public DistinctIterator(@NotNull Iterator<? extends T> source, @NotNull Function1<? super T, ? extends K> keySelector) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(keySelector, "keySelector");
        this.source = source;
        this.keySelector = keySelector;
        this.observed = new HashSet<>();
    }

    @Override // kotlin.collections.AbstractIterator
    protected void a() {
        while (this.source.hasNext()) {
            T next = this.source.next();
            if (this.observed.add(this.keySelector.invoke(next))) {
                c(next);
                return;
            }
        }
        b();
    }
}
