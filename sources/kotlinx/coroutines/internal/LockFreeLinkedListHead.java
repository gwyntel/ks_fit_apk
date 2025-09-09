package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.alipay.sdk.m.l.c;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J4\u0010\t\u001a\u00020\u0007\"\u000e\b\u0000\u0010\u0005\u0018\u0001*\u00060\u0001j\u0002`\u00042\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\b¢\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000f\u001a\u00020\u0007H\u0000¢\u0006\u0004\b\u000e\u0010\u0003R\u0011\u0010\u0011\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0012¨\u0006\u0014"}, d2 = {"Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "<init>", "()V", "Lkotlinx/coroutines/internal/Node;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function1;", "", "block", "forEach", "(Lkotlin/jvm/functions/Function1;)V", "", "remove", "()Ljava/lang/Void;", "validate$kotlinx_coroutines_core", c.f9460j, "", "isEmpty", "()Z", "isRemoved", "kotlinx-coroutines-core"}, k = 1, mv = {1, 8, 0})
/* loaded from: classes5.dex */
public class LockFreeLinkedListHead extends LockFreeLinkedListNode {
    public final /* synthetic */ <T extends LockFreeLinkedListNode> void forEach(Function1<? super T, Unit> block) {
        Object next = getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
        for (LockFreeLinkedListNode nextNode = (LockFreeLinkedListNode) next; !Intrinsics.areEqual(nextNode, this); nextNode = nextNode.getNextNode()) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (nextNode instanceof LockFreeLinkedListNode) {
                block.invoke(nextNode);
            }
        }
    }

    public final boolean isEmpty() {
        return getNext() == this;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    public boolean isRemoved() {
        return false;
    }

    @Override // kotlinx.coroutines.internal.LockFreeLinkedListNode
    /* renamed from: remove, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo2379remove() {
        return ((Boolean) remove()).booleanValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.internal.LockFreeLinkedListNode] */
    public final void validate$kotlinx_coroutines_core() {
        Object next = getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
        LockFreeLinkedListHead lockFreeLinkedListHead = this;
        LockFreeLinkedListHead lockFreeLinkedListHead2 = (LockFreeLinkedListNode) next;
        while (!Intrinsics.areEqual(lockFreeLinkedListHead2, this)) {
            LockFreeLinkedListNode nextNode = lockFreeLinkedListHead2.getNextNode();
            lockFreeLinkedListHead2.validateNode$kotlinx_coroutines_core(lockFreeLinkedListHead, nextNode);
            lockFreeLinkedListHead = lockFreeLinkedListHead2;
            lockFreeLinkedListHead2 = nextNode;
        }
        Object next2 = getNext();
        Intrinsics.checkNotNull(next2, "null cannot be cast to non-null type kotlinx.coroutines.internal.LockFreeLinkedListNode{ kotlinx.coroutines.internal.LockFreeLinkedListKt.Node }");
        validateNode$kotlinx_coroutines_core(lockFreeLinkedListHead, (LockFreeLinkedListNode) next2);
    }

    @NotNull
    public final Void remove() {
        throw new IllegalStateException("head cannot be removed".toString());
    }
}
