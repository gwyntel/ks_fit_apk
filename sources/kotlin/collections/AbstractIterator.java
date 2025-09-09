package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import com.umeng.analytics.pro.bc;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mozilla.javascript.ES6Iterator;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0005H\u0096\u0002¢\u0006\u0004\b\b\u0010\u0007J\u0010\u0010\t\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\f\u001a\u00020\u000bH$¢\u0006\u0004\b\f\u0010\u0004J\u0017\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\u000bH\u0004¢\u0006\u0004\b\u0010\u0010\u0004R\u0016\u0010\u0012\u001a\u00020\u00118\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u0018\u0010\u0014\u001a\u0004\u0018\u00018\u00008\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b\u0014\u0010\u0015¨\u0006\u0016"}, d2 = {"Lkotlin/collections/AbstractIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", "<init>", "()V", "", "tryToComputeNext", "()Z", "hasNext", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", "", "a", "value", bc.aL, "(Ljava/lang/Object;)V", "b", "Lkotlin/collections/State;", "state", "Lkotlin/collections/State;", "nextValue", "Ljava/lang/Object;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public abstract class AbstractIterator<T> implements Iterator<T>, KMappedMarker {

    @Nullable
    private T nextValue;

    @NotNull
    private State state = State.NotReady;

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.Done.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.Ready.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private final boolean tryToComputeNext() {
        this.state = State.Failed;
        a();
        return this.state == State.Ready;
    }

    protected abstract void a();

    protected final void b() {
        this.state = State.Done;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final void c(Object value) {
        this.nextValue = value;
        this.state = State.Ready;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        State state = this.state;
        if (state == State.Failed) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        int i2 = WhenMappings.$EnumSwitchMapping$0[state.ordinal()];
        if (i2 == 1) {
            return false;
        }
        if (i2 != 2) {
            return tryToComputeNext();
        }
        return true;
    }

    @Override // java.util.Iterator
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.state = State.NotReady;
        return this.nextValue;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
