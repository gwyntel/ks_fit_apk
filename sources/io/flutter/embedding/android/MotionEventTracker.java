package io.flutter.embedding.android;

import android.util.LongSparseArray;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

/* loaded from: classes4.dex */
public final class MotionEventTracker {
    private static MotionEventTracker INSTANCE = null;
    private static final String TAG = "MotionEventTracker";
    private final LongSparseArray<MotionEvent> eventById = new LongSparseArray<>();
    private final PriorityQueue<Long> unusedEvents = new PriorityQueue<>();

    public static class MotionEventId {
        private static final AtomicLong ID_COUNTER = new AtomicLong(0);
        private final long id;

        private MotionEventId(long j2) {
            this.id = j2;
        }

        @NonNull
        public static MotionEventId createUnique() {
            return from(ID_COUNTER.incrementAndGet());
        }

        @NonNull
        public static MotionEventId from(long j2) {
            return new MotionEventId(j2);
        }

        public long getId() {
            return this.id;
        }
    }

    private MotionEventTracker() {
    }

    @NonNull
    public static MotionEventTracker getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MotionEventTracker();
        }
        return INSTANCE;
    }

    @Nullable
    public MotionEvent pop(@NonNull MotionEventId motionEventId) {
        while (!this.unusedEvents.isEmpty() && this.unusedEvents.peek().longValue() < motionEventId.id) {
            this.eventById.remove(this.unusedEvents.poll().longValue());
        }
        if (!this.unusedEvents.isEmpty() && this.unusedEvents.peek().longValue() == motionEventId.id) {
            this.unusedEvents.poll();
        }
        MotionEvent motionEvent = this.eventById.get(motionEventId.id);
        this.eventById.remove(motionEventId.id);
        return motionEvent;
    }

    @NonNull
    public MotionEventId track(@NonNull MotionEvent motionEvent) {
        MotionEventId motionEventIdCreateUnique = MotionEventId.createUnique();
        this.eventById.put(motionEventIdCreateUnique.id, MotionEvent.obtain(motionEvent));
        this.unusedEvents.add(Long.valueOf(motionEventIdCreateUnique.id));
        return motionEventIdCreateUnique;
    }
}
