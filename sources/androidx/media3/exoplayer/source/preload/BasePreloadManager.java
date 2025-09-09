package androidx.media3.exoplayer.source.preload;

import android.os.Handler;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.preload.TargetPreloadStatusControl;
import com.kingsmith.miot.KsProperty;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class BasePreloadManager<T> {

    /* renamed from: a, reason: collision with root package name */
    protected final Comparator f5456a;
    private final MediaSource.Factory mediaSourceFactory;
    private final TargetPreloadStatusControl<T> targetPreloadStatusControl;

    @Nullable
    @GuardedBy(KsProperty.Lock)
    private TargetPreloadStatusControl.PreloadStatus targetPreloadStatusOfCurrentPreloadingSource;
    private final Object lock = new Object();
    private final Map<MediaItem, BasePreloadManager<T>.MediaSourceHolder> mediaItemMediaSourceHolderMap = new HashMap();
    private final Handler startPreloadingHandler = Util.createHandlerForCurrentOrMainLooper();

    @GuardedBy(KsProperty.Lock)
    private final PriorityQueue<BasePreloadManager<T>.MediaSourceHolder> sourceHolderPriorityQueue = new PriorityQueue<>();

    protected static abstract class BuilderBase<T> {

        /* renamed from: a, reason: collision with root package name */
        protected final Comparator f5457a;

        /* renamed from: b, reason: collision with root package name */
        protected final TargetPreloadStatusControl f5458b;

        /* renamed from: c, reason: collision with root package name */
        protected final MediaSource.Factory f5459c;

        public BuilderBase(Comparator<T> comparator, TargetPreloadStatusControl<T> targetPreloadStatusControl, MediaSource.Factory factory) {
            this.f5457a = comparator;
            this.f5458b = targetPreloadStatusControl;
            this.f5459c = factory;
        }

        public abstract BasePreloadManager<T> build();
    }

    private final class MediaSourceHolder implements Comparable<BasePreloadManager<T>.MediaSourceHolder> {
        public final MediaSource mediaSource;
        public final T rankingData;
        public final long startPositionUs;

        public MediaSourceHolder(BasePreloadManager basePreloadManager, MediaSource mediaSource, T t2) {
            this(mediaSource, t2, C.TIME_UNSET);
        }

        public MediaSourceHolder(MediaSource mediaSource, T t2, long j2) {
            this.mediaSource = mediaSource;
            this.rankingData = t2;
            this.startPositionUs = j2;
        }

        @Override // java.lang.Comparable
        public int compareTo(BasePreloadManager<T>.MediaSourceHolder mediaSourceHolder) {
            return BasePreloadManager.this.f5456a.compare(this.rankingData, mediaSourceHolder.rankingData);
        }
    }

    protected BasePreloadManager(Comparator comparator, TargetPreloadStatusControl targetPreloadStatusControl, MediaSource.Factory factory) {
        this.f5456a = comparator;
        this.targetPreloadStatusControl = targetPreloadStatusControl;
        this.mediaSourceFactory = factory;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPreloadCompleted$0(MediaSource mediaSource) {
        synchronized (this.lock) {
            try {
                if (!this.sourceHolderPriorityQueue.isEmpty() && ((MediaSourceHolder) Assertions.checkNotNull(this.sourceHolderPriorityQueue.peek())).mediaSource == mediaSource) {
                    do {
                        this.sourceHolderPriorityQueue.poll();
                        if (this.sourceHolderPriorityQueue.isEmpty()) {
                            break;
                        }
                    } while (!maybeStartPreloadNextSource());
                }
            } finally {
            }
        }
    }

    @GuardedBy(KsProperty.Lock)
    private boolean maybeStartPreloadNextSource() {
        if (!h()) {
            return false;
        }
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.sourceHolderPriorityQueue.peek());
        TargetPreloadStatusControl.PreloadStatus targetPreloadStatus = this.targetPreloadStatusControl.getTargetPreloadStatus(mediaSourceHolder.rankingData);
        this.targetPreloadStatusOfCurrentPreloadingSource = targetPreloadStatus;
        if (targetPreloadStatus != null) {
            e(mediaSourceHolder.mediaSource, mediaSourceHolder.startPositionUs);
            return true;
        }
        b(mediaSourceHolder.mediaSource);
        return false;
    }

    public final void add(MediaItem mediaItem, T t2) {
        add(this.mediaSourceFactory.createMediaSource(mediaItem), (MediaSource) t2);
    }

    protected abstract void b(MediaSource mediaSource);

    protected final TargetPreloadStatusControl.PreloadStatus c(MediaSource mediaSource) {
        synchronized (this.lock) {
            try {
                if (!this.sourceHolderPriorityQueue.isEmpty() && ((MediaSourceHolder) Assertions.checkNotNull(this.sourceHolderPriorityQueue.peek())).mediaSource == mediaSource) {
                    return this.targetPreloadStatusOfCurrentPreloadingSource;
                }
                return null;
            } finally {
            }
        }
    }

    protected MediaSource createMediaSourceForPreloading(MediaSource mediaSource) {
        return mediaSource;
    }

    protected final void d(final MediaSource mediaSource) {
        this.startPreloadingHandler.post(new Runnable() { // from class: androidx.media3.exoplayer.source.preload.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f5464a.lambda$onPreloadCompleted$0(mediaSource);
            }
        });
    }

    protected abstract void e(MediaSource mediaSource, long j2);

    protected void f() {
    }

    protected abstract void g(MediaSource mediaSource);

    @Nullable
    public final MediaSource getMediaSource(MediaItem mediaItem) {
        if (this.mediaItemMediaSourceHolderMap.containsKey(mediaItem)) {
            return this.mediaItemMediaSourceHolderMap.get(mediaItem).mediaSource;
        }
        return null;
    }

    public final int getSourceCount() {
        return this.mediaItemMediaSourceHolderMap.size();
    }

    protected boolean h() {
        return true;
    }

    public final void invalidate() {
        synchronized (this.lock) {
            try {
                this.sourceHolderPriorityQueue.clear();
                this.sourceHolderPriorityQueue.addAll(this.mediaItemMediaSourceHolderMap.values());
                while (!this.sourceHolderPriorityQueue.isEmpty() && !maybeStartPreloadNextSource()) {
                    this.sourceHolderPriorityQueue.poll();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void release() {
        reset();
        f();
    }

    public final boolean remove(MediaItem mediaItem) {
        if (!this.mediaItemMediaSourceHolderMap.containsKey(mediaItem)) {
            return false;
        }
        MediaSource mediaSource = this.mediaItemMediaSourceHolderMap.get(mediaItem).mediaSource;
        this.mediaItemMediaSourceHolderMap.remove(mediaItem);
        g(mediaSource);
        return true;
    }

    public final void reset() {
        Iterator<BasePreloadManager<T>.MediaSourceHolder> it = this.mediaItemMediaSourceHolderMap.values().iterator();
        while (it.hasNext()) {
            g(it.next().mediaSource);
        }
        this.mediaItemMediaSourceHolderMap.clear();
        synchronized (this.lock) {
            this.sourceHolderPriorityQueue.clear();
            this.targetPreloadStatusOfCurrentPreloadingSource = null;
        }
    }

    public final void add(MediaSource mediaSource, T t2) {
        MediaSource mediaSourceCreateMediaSourceForPreloading = createMediaSourceForPreloading(mediaSource);
        this.mediaItemMediaSourceHolderMap.put(mediaSourceCreateMediaSourceForPreloading.getMediaItem(), new MediaSourceHolder(this, mediaSourceCreateMediaSourceForPreloading, t2));
    }

    public final boolean remove(MediaSource mediaSource) {
        MediaItem mediaItem = mediaSource.getMediaItem();
        if (!this.mediaItemMediaSourceHolderMap.containsKey(mediaItem) || mediaSource != this.mediaItemMediaSourceHolderMap.get(mediaItem).mediaSource) {
            return false;
        }
        this.mediaItemMediaSourceHolderMap.remove(mediaItem);
        g(mediaSource);
        return true;
    }
}
