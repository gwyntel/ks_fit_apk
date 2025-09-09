package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import java.util.Queue;

@GwtCompatible
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class ConsumingQueueIterator<T> extends AbstractIterator<T> {
    private final Queue<T> queue;

    ConsumingQueueIterator(Queue queue) {
        this.queue = (Queue) Preconditions.checkNotNull(queue);
    }

    @Override // com.google.common.collect.AbstractIterator
    protected Object computeNext() {
        return this.queue.isEmpty() ? a() : this.queue.remove();
    }
}
