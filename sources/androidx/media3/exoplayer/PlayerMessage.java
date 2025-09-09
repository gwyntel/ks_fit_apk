package androidx.media3.exoplayer;

import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.IllegalSeekPositionException;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.UnstableApi;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.TimeoutException;

@UnstableApi
/* loaded from: classes.dex */
public final class PlayerMessage {
    private final Clock clock;
    private boolean isCanceled;
    private boolean isDelivered;
    private boolean isProcessed;
    private boolean isSent;
    private Looper looper;
    private int mediaItemIndex;

    @Nullable
    private Object payload;
    private final Sender sender;
    private final Target target;
    private final Timeline timeline;
    private int type;
    private long positionMs = C.TIME_UNSET;
    private boolean deleteAfterDelivery = true;

    public interface Sender {
        void sendMessage(PlayerMessage playerMessage);
    }

    public interface Target {
        void handleMessage(int i2, @Nullable Object obj) throws ExoPlaybackException;
    }

    public PlayerMessage(Sender sender, Target target, Timeline timeline, int i2, Clock clock, Looper looper) {
        this.sender = sender;
        this.target = target;
        this.timeline = timeline;
        this.looper = looper;
        this.clock = clock;
        this.mediaItemIndex = i2;
    }

    public synchronized boolean blockUntilDelivered() throws InterruptedException {
        try {
            Assertions.checkState(this.isSent);
            Assertions.checkState(this.looper.getThread() != Thread.currentThread());
            while (!this.isProcessed) {
                wait();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.isDelivered;
    }

    @CanIgnoreReturnValue
    public synchronized PlayerMessage cancel() {
        Assertions.checkState(this.isSent);
        this.isCanceled = true;
        markAsProcessed(false);
        return this;
    }

    public boolean getDeleteAfterDelivery() {
        return this.deleteAfterDelivery;
    }

    public Looper getLooper() {
        return this.looper;
    }

    public int getMediaItemIndex() {
        return this.mediaItemIndex;
    }

    @Nullable
    public Object getPayload() {
        return this.payload;
    }

    public long getPositionMs() {
        return this.positionMs;
    }

    public Target getTarget() {
        return this.target;
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public int getType() {
        return this.type;
    }

    public synchronized boolean isCanceled() {
        return this.isCanceled;
    }

    public synchronized void markAsProcessed(boolean z2) {
        this.isDelivered = z2 | this.isDelivered;
        this.isProcessed = true;
        notifyAll();
    }

    @CanIgnoreReturnValue
    public PlayerMessage send() {
        Assertions.checkState(!this.isSent);
        if (this.positionMs == C.TIME_UNSET) {
            Assertions.checkArgument(this.deleteAfterDelivery);
        }
        this.isSent = true;
        this.sender.sendMessage(this);
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setDeleteAfterDelivery(boolean z2) {
        Assertions.checkState(!this.isSent);
        this.deleteAfterDelivery = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setLooper(Looper looper) {
        Assertions.checkState(!this.isSent);
        this.looper = looper;
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setPayload(@Nullable Object obj) {
        Assertions.checkState(!this.isSent);
        this.payload = obj;
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setPosition(long j2) {
        Assertions.checkState(!this.isSent);
        this.positionMs = j2;
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setType(int i2) {
        Assertions.checkState(!this.isSent);
        this.type = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public PlayerMessage setPosition(int i2, long j2) {
        Assertions.checkState(!this.isSent);
        Assertions.checkArgument(j2 != C.TIME_UNSET);
        if (i2 >= 0 && (this.timeline.isEmpty() || i2 < this.timeline.getWindowCount())) {
            this.mediaItemIndex = i2;
            this.positionMs = j2;
            return this;
        }
        throw new IllegalSeekPositionException(this.timeline, i2, j2);
    }

    public synchronized boolean blockUntilDelivered(long j2) throws InterruptedException, TimeoutException {
        boolean z2;
        try {
            Assertions.checkState(this.isSent);
            Assertions.checkState(this.looper.getThread() != Thread.currentThread());
            long jElapsedRealtime = this.clock.elapsedRealtime() + j2;
            while (true) {
                z2 = this.isProcessed;
                if (z2 || j2 <= 0) {
                    break;
                }
                this.clock.onThreadBlocked();
                wait(j2);
                j2 = jElapsedRealtime - this.clock.elapsedRealtime();
            }
            if (!z2) {
                throw new TimeoutException("Message delivery timed out.");
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.isDelivered;
    }
}
