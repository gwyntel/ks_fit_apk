package androidx.media3.exoplayer.source;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.AbstractConcatenatedTimeline;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.ShuffleOrder;
import androidx.media3.exoplayer.upstream.Allocator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UnstableApi
@Deprecated
/* loaded from: classes2.dex */
public final class ConcatenatingMediaSource extends CompositeMediaSource<MediaSourceHolder> {
    private static final int MSG_ADD = 1;
    private static final int MSG_MOVE = 3;
    private static final int MSG_ON_COMPLETION = 6;
    private static final int MSG_REMOVE = 2;
    private static final int MSG_SET_SHUFFLE_ORDER = 4;
    private static final int MSG_UPDATE_TIMELINE = 5;
    private static final MediaItem PLACEHOLDER_MEDIA_ITEM = new MediaItem.Builder().setUri(Uri.EMPTY).build();
    private final Set<MediaSourceHolder> enabledMediaSourceHolders;
    private final boolean isAtomic;
    private final IdentityHashMap<MediaPeriod, MediaSourceHolder> mediaSourceByMediaPeriod;
    private final Map<Object, MediaSourceHolder> mediaSourceByUid;
    private final List<MediaSourceHolder> mediaSourceHolders;

    @GuardedBy("this")
    private final List<MediaSourceHolder> mediaSourcesPublic;
    private Set<HandlerAndRunnable> nextTimelineUpdateOnCompletionActions;

    @GuardedBy("this")
    private final Set<HandlerAndRunnable> pendingOnCompletionActions;

    @Nullable
    @GuardedBy("this")
    private Handler playbackThreadHandler;
    private ShuffleOrder shuffleOrder;
    private boolean timelineUpdateScheduled;
    private final boolean useLazyPreparation;

    private static final class ConcatenatedTimeline extends AbstractConcatenatedTimeline {
        private final HashMap<Object, Integer> childIndexByUid;
        private final int[] firstPeriodInChildIndices;
        private final int[] firstWindowInChildIndices;
        private final int periodCount;
        private final Timeline[] timelines;
        private final Object[] uids;
        private final int windowCount;

        public ConcatenatedTimeline(Collection<MediaSourceHolder> collection, ShuffleOrder shuffleOrder, boolean z2) {
            super(z2, shuffleOrder);
            int size = collection.size();
            this.firstPeriodInChildIndices = new int[size];
            this.firstWindowInChildIndices = new int[size];
            this.timelines = new Timeline[size];
            this.uids = new Object[size];
            this.childIndexByUid = new HashMap<>();
            int windowCount = 0;
            int periodCount = 0;
            int i2 = 0;
            for (MediaSourceHolder mediaSourceHolder : collection) {
                this.timelines[i2] = mediaSourceHolder.mediaSource.getTimeline();
                this.firstWindowInChildIndices[i2] = windowCount;
                this.firstPeriodInChildIndices[i2] = periodCount;
                windowCount += this.timelines[i2].getWindowCount();
                periodCount += this.timelines[i2].getPeriodCount();
                Object[] objArr = this.uids;
                Object obj = mediaSourceHolder.uid;
                objArr[i2] = obj;
                this.childIndexByUid.put(obj, Integer.valueOf(i2));
                i2++;
            }
            this.windowCount = windowCount;
            this.periodCount = periodCount;
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int a(Object obj) {
            Integer num = this.childIndexByUid.get(obj);
            if (num == null) {
                return -1;
            }
            return num.intValue();
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int b(int i2) {
            return Util.binarySearchFloor(this.firstWindowInChildIndices, i2 + 1, false, false);
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected Object c(int i2) {
            return this.uids[i2];
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int d(int i2) {
            return this.firstPeriodInChildIndices[i2];
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int e(int i2) {
            return this.firstWindowInChildIndices[i2];
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected Timeline f(int i2) {
            return this.timelines[i2];
        }

        @Override // androidx.media3.exoplayer.AbstractConcatenatedTimeline
        protected int getChildIndexByPeriodIndex(int i2) {
            return Util.binarySearchFloor(this.firstPeriodInChildIndices, i2 + 1, false, false);
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return this.periodCount;
        }

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return this.windowCount;
        }
    }

    private static final class FakeMediaSource extends BaseMediaSource {
        private FakeMediaSource() {
        }

        @Override // androidx.media3.exoplayer.source.MediaSource
        public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.media3.exoplayer.source.MediaSource
        public MediaItem getMediaItem() {
            return ConcatenatingMediaSource.PLACEHOLDER_MEDIA_ITEM;
        }

        @Override // androidx.media3.exoplayer.source.BaseMediaSource
        protected void j(TransferListener transferListener) {
        }

        @Override // androidx.media3.exoplayer.source.MediaSource
        public void maybeThrowSourceInfoRefreshError() {
        }

        @Override // androidx.media3.exoplayer.source.MediaSource
        public void releasePeriod(MediaPeriod mediaPeriod) {
        }

        @Override // androidx.media3.exoplayer.source.BaseMediaSource
        protected void releaseSourceInternal() {
        }
    }

    private static final class HandlerAndRunnable {
        private final Handler handler;
        private final Runnable runnable;

        public HandlerAndRunnable(Handler handler, Runnable runnable) {
            this.handler = handler;
            this.runnable = runnable;
        }

        public void dispatch() {
            this.handler.post(this.runnable);
        }
    }

    static final class MediaSourceHolder {
        public int childIndex;
        public int firstWindowIndexInChild;
        public boolean isRemoved;
        public final MaskingMediaSource mediaSource;
        public final List<MediaSource.MediaPeriodId> activeMediaPeriodIds = new ArrayList();
        public final Object uid = new Object();

        public MediaSourceHolder(MediaSource mediaSource, boolean z2) {
            this.mediaSource = new MaskingMediaSource(mediaSource, z2);
        }

        public void reset(int i2, int i3) {
            this.childIndex = i2;
            this.firstWindowIndexInChild = i3;
            this.isRemoved = false;
            this.activeMediaPeriodIds.clear();
        }
    }

    private static final class MessageData<T> {
        public final T customData;
        public final int index;

        @Nullable
        public final HandlerAndRunnable onCompletionAction;

        public MessageData(int i2, T t2, @Nullable HandlerAndRunnable handlerAndRunnable) {
            this.index = i2;
            this.customData = t2;
            this.onCompletionAction = handlerAndRunnable;
        }
    }

    public ConcatenatingMediaSource(MediaSource... mediaSourceArr) {
        this(false, mediaSourceArr);
    }

    private void addMediaSourceInternal(int i2, MediaSourceHolder mediaSourceHolder) {
        if (i2 > 0) {
            MediaSourceHolder mediaSourceHolder2 = this.mediaSourceHolders.get(i2 - 1);
            mediaSourceHolder.reset(i2, mediaSourceHolder2.firstWindowIndexInChild + mediaSourceHolder2.mediaSource.getTimeline().getWindowCount());
        } else {
            mediaSourceHolder.reset(i2, 0);
        }
        correctOffsets(i2, 1, mediaSourceHolder.mediaSource.getTimeline().getWindowCount());
        this.mediaSourceHolders.add(i2, mediaSourceHolder);
        this.mediaSourceByUid.put(mediaSourceHolder.uid, mediaSourceHolder);
        t(mediaSourceHolder, mediaSourceHolder.mediaSource);
        if (h() && this.mediaSourceByMediaPeriod.isEmpty()) {
            this.enabledMediaSourceHolders.add(mediaSourceHolder);
        } else {
            n(mediaSourceHolder);
        }
    }

    private void addMediaSourcesInternal(int i2, Collection<MediaSourceHolder> collection) {
        Iterator<MediaSourceHolder> it = collection.iterator();
        while (it.hasNext()) {
            addMediaSourceInternal(i2, it.next());
            i2++;
        }
    }

    @GuardedBy("this")
    private void addPublicMediaSources(int i2, Collection<MediaSource> collection, @Nullable Handler handler, @Nullable Runnable runnable) {
        Assertions.checkArgument((handler == null) == (runnable == null));
        Handler handler2 = this.playbackThreadHandler;
        Iterator<MediaSource> it = collection.iterator();
        while (it.hasNext()) {
            Assertions.checkNotNull(it.next());
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<MediaSource> it2 = collection.iterator();
        while (it2.hasNext()) {
            arrayList.add(new MediaSourceHolder(it2.next(), this.useLazyPreparation));
        }
        this.mediaSourcesPublic.addAll(i2, arrayList);
        if (handler2 != null && !collection.isEmpty()) {
            handler2.obtainMessage(1, new MessageData(i2, arrayList, createOnCompletionAction(handler, runnable))).sendToTarget();
        } else {
            if (runnable == null || handler == null) {
                return;
            }
            handler.post(runnable);
        }
    }

    private void correctOffsets(int i2, int i3, int i4) {
        while (i2 < this.mediaSourceHolders.size()) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(i2);
            mediaSourceHolder.childIndex += i3;
            mediaSourceHolder.firstWindowIndexInChild += i4;
            i2++;
        }
    }

    @Nullable
    @GuardedBy("this")
    private HandlerAndRunnable createOnCompletionAction(@Nullable Handler handler, @Nullable Runnable runnable) {
        if (handler == null || runnable == null) {
            return null;
        }
        HandlerAndRunnable handlerAndRunnable = new HandlerAndRunnable(handler, runnable);
        this.pendingOnCompletionActions.add(handlerAndRunnable);
        return handlerAndRunnable;
    }

    private void disableUnusedMediaSources() {
        Iterator<MediaSourceHolder> it = this.enabledMediaSourceHolders.iterator();
        while (it.hasNext()) {
            MediaSourceHolder next = it.next();
            if (next.activeMediaPeriodIds.isEmpty()) {
                n(next);
                it.remove();
            }
        }
    }

    private synchronized void dispatchOnCompletionActions(Set<HandlerAndRunnable> set) {
        try {
            Iterator<HandlerAndRunnable> it = set.iterator();
            while (it.hasNext()) {
                it.next().dispatch();
            }
            this.pendingOnCompletionActions.removeAll(set);
        } catch (Throwable th) {
            throw th;
        }
    }

    private void enableMediaSource(MediaSourceHolder mediaSourceHolder) {
        this.enabledMediaSourceHolders.add(mediaSourceHolder);
        o(mediaSourceHolder);
    }

    private static Object getChildPeriodUid(Object obj) {
        return AbstractConcatenatedTimeline.getChildPeriodUidFromConcatenatedUid(obj);
    }

    private static Object getMediaSourceHolderUid(Object obj) {
        return AbstractConcatenatedTimeline.getChildTimelineUidFromConcatenatedUid(obj);
    }

    private static Object getPeriodUid(MediaSourceHolder mediaSourceHolder, Object obj) {
        return AbstractConcatenatedTimeline.getConcatenatedUid(mediaSourceHolder.uid, obj);
    }

    private Handler getPlaybackThreadHandlerOnPlaybackThread() {
        return (Handler) Assertions.checkNotNull(this.playbackThreadHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 1:
                MessageData messageData = (MessageData) Util.castNonNull(message.obj);
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(messageData.index, ((Collection) messageData.customData).size());
                addMediaSourcesInternal(messageData.index, (Collection) messageData.customData);
                scheduleTimelineUpdate(messageData.onCompletionAction);
                return true;
            case 2:
                MessageData messageData2 = (MessageData) Util.castNonNull(message.obj);
                int i2 = messageData2.index;
                int iIntValue = ((Integer) messageData2.customData).intValue();
                if (i2 == 0 && iIntValue == this.shuffleOrder.getLength()) {
                    this.shuffleOrder = this.shuffleOrder.cloneAndClear();
                } else {
                    this.shuffleOrder = this.shuffleOrder.cloneAndRemove(i2, iIntValue);
                }
                for (int i3 = iIntValue - 1; i3 >= i2; i3--) {
                    removeMediaSourceInternal(i3);
                }
                scheduleTimelineUpdate(messageData2.onCompletionAction);
                return true;
            case 3:
                MessageData messageData3 = (MessageData) Util.castNonNull(message.obj);
                ShuffleOrder shuffleOrder = this.shuffleOrder;
                int i4 = messageData3.index;
                ShuffleOrder shuffleOrderCloneAndRemove = shuffleOrder.cloneAndRemove(i4, i4 + 1);
                this.shuffleOrder = shuffleOrderCloneAndRemove;
                this.shuffleOrder = shuffleOrderCloneAndRemove.cloneAndInsert(((Integer) messageData3.customData).intValue(), 1);
                moveMediaSourceInternal(messageData3.index, ((Integer) messageData3.customData).intValue());
                scheduleTimelineUpdate(messageData3.onCompletionAction);
                return true;
            case 4:
                MessageData messageData4 = (MessageData) Util.castNonNull(message.obj);
                this.shuffleOrder = (ShuffleOrder) messageData4.customData;
                scheduleTimelineUpdate(messageData4.onCompletionAction);
                return true;
            case 5:
                updateTimelineAndScheduleOnCompletionActions();
                return true;
            case 6:
                dispatchOnCompletionActions((Set) Util.castNonNull(message.obj));
                return true;
            default:
                throw new IllegalStateException();
        }
    }

    private void maybeReleaseChildSource(MediaSourceHolder mediaSourceHolder) {
        if (mediaSourceHolder.isRemoved && mediaSourceHolder.activeMediaPeriodIds.isEmpty()) {
            this.enabledMediaSourceHolders.remove(mediaSourceHolder);
            u(mediaSourceHolder);
        }
    }

    private void moveMediaSourceInternal(int i2, int i3) {
        int iMin = Math.min(i2, i3);
        int iMax = Math.max(i2, i3);
        int windowCount = this.mediaSourceHolders.get(iMin).firstWindowIndexInChild;
        List<MediaSourceHolder> list = this.mediaSourceHolders;
        list.add(i3, list.remove(i2));
        while (iMin <= iMax) {
            MediaSourceHolder mediaSourceHolder = this.mediaSourceHolders.get(iMin);
            mediaSourceHolder.childIndex = iMin;
            mediaSourceHolder.firstWindowIndexInChild = windowCount;
            windowCount += mediaSourceHolder.mediaSource.getTimeline().getWindowCount();
            iMin++;
        }
    }

    @GuardedBy("this")
    private void movePublicMediaSource(int i2, int i3, @Nullable Handler handler, @Nullable Runnable runnable) {
        Assertions.checkArgument((handler == null) == (runnable == null));
        Handler handler2 = this.playbackThreadHandler;
        List<MediaSourceHolder> list = this.mediaSourcesPublic;
        list.add(i3, list.remove(i2));
        if (handler2 != null) {
            handler2.obtainMessage(3, new MessageData(i2, Integer.valueOf(i3), createOnCompletionAction(handler, runnable))).sendToTarget();
        } else {
            if (runnable == null || handler == null) {
                return;
            }
            handler.post(runnable);
        }
    }

    private void removeMediaSourceInternal(int i2) {
        MediaSourceHolder mediaSourceHolderRemove = this.mediaSourceHolders.remove(i2);
        this.mediaSourceByUid.remove(mediaSourceHolderRemove.uid);
        correctOffsets(i2, -1, -mediaSourceHolderRemove.mediaSource.getTimeline().getWindowCount());
        mediaSourceHolderRemove.isRemoved = true;
        maybeReleaseChildSource(mediaSourceHolderRemove);
    }

    @GuardedBy("this")
    private void removePublicMediaSources(int i2, int i3, @Nullable Handler handler, @Nullable Runnable runnable) {
        Assertions.checkArgument((handler == null) == (runnable == null));
        Handler handler2 = this.playbackThreadHandler;
        Util.removeRange(this.mediaSourcesPublic, i2, i3);
        if (handler2 != null) {
            handler2.obtainMessage(2, new MessageData(i2, Integer.valueOf(i3), createOnCompletionAction(handler, runnable))).sendToTarget();
        } else {
            if (runnable == null || handler == null) {
                return;
            }
            handler.post(runnable);
        }
    }

    private void scheduleTimelineUpdate() {
        scheduleTimelineUpdate(null);
    }

    @GuardedBy("this")
    private void setPublicShuffleOrder(ShuffleOrder shuffleOrder, @Nullable Handler handler, @Nullable Runnable runnable) {
        Assertions.checkArgument((handler == null) == (runnable == null));
        Handler handler2 = this.playbackThreadHandler;
        if (handler2 != null) {
            int size = getSize();
            if (shuffleOrder.getLength() != size) {
                shuffleOrder = shuffleOrder.cloneAndClear().cloneAndInsert(0, size);
            }
            handler2.obtainMessage(4, new MessageData(0, shuffleOrder, createOnCompletionAction(handler, runnable))).sendToTarget();
            return;
        }
        if (shuffleOrder.getLength() > 0) {
            shuffleOrder = shuffleOrder.cloneAndClear();
        }
        this.shuffleOrder = shuffleOrder;
        if (runnable == null || handler == null) {
            return;
        }
        handler.post(runnable);
    }

    private void updateMediaSourceInternal(MediaSourceHolder mediaSourceHolder, Timeline timeline) {
        if (mediaSourceHolder.childIndex + 1 < this.mediaSourceHolders.size()) {
            int windowCount = timeline.getWindowCount() - (this.mediaSourceHolders.get(mediaSourceHolder.childIndex + 1).firstWindowIndexInChild - mediaSourceHolder.firstWindowIndexInChild);
            if (windowCount != 0) {
                correctOffsets(mediaSourceHolder.childIndex + 1, 0, windowCount);
            }
        }
        scheduleTimelineUpdate();
    }

    private void updateTimelineAndScheduleOnCompletionActions() {
        this.timelineUpdateScheduled = false;
        Set<HandlerAndRunnable> set = this.nextTimelineUpdateOnCompletionActions;
        this.nextTimelineUpdateOnCompletionActions = new HashSet();
        k(new ConcatenatedTimeline(this.mediaSourceHolders, this.shuffleOrder, this.isAtomic));
        getPlaybackThreadHandlerOnPlaybackThread().obtainMessage(6, set).sendToTarget();
    }

    public synchronized void addMediaSource(MediaSource mediaSource) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection) {
        addPublicMediaSources(this.mediaSourcesPublic.size(), collection, null, null);
    }

    public synchronized void clear() {
        removeMediaSourceRange(0, getSize());
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaPeriod createPeriod(MediaSource.MediaPeriodId mediaPeriodId, Allocator allocator, long j2) {
        Object mediaSourceHolderUid = getMediaSourceHolderUid(mediaPeriodId.periodUid);
        MediaSource.MediaPeriodId mediaPeriodIdCopyWithPeriodUid = mediaPeriodId.copyWithPeriodUid(getChildPeriodUid(mediaPeriodId.periodUid));
        MediaSourceHolder mediaSourceHolder = this.mediaSourceByUid.get(mediaSourceHolderUid);
        if (mediaSourceHolder == null) {
            mediaSourceHolder = new MediaSourceHolder(new FakeMediaSource(), this.useLazyPreparation);
            mediaSourceHolder.isRemoved = true;
            t(mediaSourceHolder, mediaSourceHolder.mediaSource);
        }
        enableMediaSource(mediaSourceHolder);
        mediaSourceHolder.activeMediaPeriodIds.add(mediaPeriodIdCopyWithPeriodUid);
        MaskingMediaPeriod maskingMediaPeriodCreatePeriod = mediaSourceHolder.mediaSource.createPeriod(mediaPeriodIdCopyWithPeriodUid, allocator, j2);
        this.mediaSourceByMediaPeriod.put(maskingMediaPeriodCreatePeriod, mediaSourceHolder);
        disableUnusedMediaSources();
        return maskingMediaPeriodCreatePeriod;
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void e() {
        super.e();
        this.enabledMediaSourceHolders.clear();
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected void f() {
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public synchronized Timeline getInitialTimeline() {
        try {
        } catch (Throwable th) {
            throw th;
        }
        return new ConcatenatedTimeline(this.mediaSourcesPublic, this.shuffleOrder.getLength() != this.mediaSourcesPublic.size() ? this.shuffleOrder.cloneAndClear().cloneAndInsert(0, this.mediaSourcesPublic.size()) : this.shuffleOrder, this.isAtomic);
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public MediaItem getMediaItem() {
        return PLACEHOLDER_MEDIA_ITEM;
    }

    public synchronized MediaSource getMediaSource(int i2) {
        return this.mediaSourcesPublic.get(i2).mediaSource;
    }

    public synchronized int getSize() {
        return this.mediaSourcesPublic.size();
    }

    @Override // androidx.media3.exoplayer.source.BaseMediaSource, androidx.media3.exoplayer.source.MediaSource
    public boolean isSingleWindow() {
        return false;
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected synchronized void j(TransferListener transferListener) {
        try {
            super.j(transferListener);
            this.playbackThreadHandler = new Handler(new Handler.Callback() { // from class: androidx.media3.exoplayer.source.c
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    return this.f5436a.handleMessage(message);
                }
            });
            if (this.mediaSourcesPublic.isEmpty()) {
                updateTimelineAndScheduleOnCompletionActions();
            } else {
                this.shuffleOrder = this.shuffleOrder.cloneAndInsert(0, this.mediaSourcesPublic.size());
                addMediaSourcesInternal(0, this.mediaSourcesPublic);
                scheduleTimelineUpdate();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void moveMediaSource(int i2, int i3) {
        movePublicMediaSource(i2, i3, null, null);
    }

    @Override // androidx.media3.exoplayer.source.MediaSource
    public void releasePeriod(MediaPeriod mediaPeriod) {
        MediaSourceHolder mediaSourceHolder = (MediaSourceHolder) Assertions.checkNotNull(this.mediaSourceByMediaPeriod.remove(mediaPeriod));
        mediaSourceHolder.mediaSource.releasePeriod(mediaPeriod);
        mediaSourceHolder.activeMediaPeriodIds.remove(((MaskingMediaPeriod) mediaPeriod).id);
        if (!this.mediaSourceByMediaPeriod.isEmpty()) {
            disableUnusedMediaSources();
        }
        maybeReleaseChildSource(mediaSourceHolder);
    }

    @Override // androidx.media3.exoplayer.source.CompositeMediaSource, androidx.media3.exoplayer.source.BaseMediaSource
    protected synchronized void releaseSourceInternal() {
        try {
            super.releaseSourceInternal();
            this.mediaSourceHolders.clear();
            this.enabledMediaSourceHolders.clear();
            this.mediaSourceByUid.clear();
            this.shuffleOrder = this.shuffleOrder.cloneAndClear();
            Handler handler = this.playbackThreadHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                this.playbackThreadHandler = null;
            }
            this.timelineUpdateScheduled = false;
            this.nextTimelineUpdateOnCompletionActions.clear();
            dispatchOnCompletionActions(this.pendingOnCompletionActions);
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized MediaSource removeMediaSource(int i2) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i2);
        removePublicMediaSources(i2, i2 + 1, null, null);
        return mediaSource;
    }

    public synchronized void removeMediaSourceRange(int i2, int i3) {
        removePublicMediaSources(i2, i3, null, null);
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder) {
        setPublicShuffleOrder(shuffleOrder, null, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: x, reason: merged with bridge method [inline-methods] */
    public MediaSource.MediaPeriodId p(MediaSourceHolder mediaSourceHolder, MediaSource.MediaPeriodId mediaPeriodId) {
        for (int i2 = 0; i2 < mediaSourceHolder.activeMediaPeriodIds.size(); i2++) {
            if (mediaSourceHolder.activeMediaPeriodIds.get(i2).windowSequenceNumber == mediaPeriodId.windowSequenceNumber) {
                return mediaPeriodId.copyWithPeriodUid(getPeriodUid(mediaSourceHolder, mediaPeriodId.periodUid));
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: y, reason: merged with bridge method [inline-methods] */
    public int r(MediaSourceHolder mediaSourceHolder, int i2) {
        return i2 + mediaSourceHolder.firstWindowIndexInChild;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.media3.exoplayer.source.CompositeMediaSource
    /* renamed from: z, reason: merged with bridge method [inline-methods] */
    public void lambda$prepareChildSource$0(MediaSourceHolder mediaSourceHolder, MediaSource mediaSource, Timeline timeline) {
        updateMediaSourceInternal(mediaSourceHolder, timeline);
    }

    public ConcatenatingMediaSource(boolean z2, MediaSource... mediaSourceArr) {
        this(z2, new ShuffleOrder.DefaultShuffleOrder(0), mediaSourceArr);
    }

    private void scheduleTimelineUpdate(@Nullable HandlerAndRunnable handlerAndRunnable) {
        if (!this.timelineUpdateScheduled) {
            getPlaybackThreadHandlerOnPlaybackThread().obtainMessage(5).sendToTarget();
            this.timelineUpdateScheduled = true;
        }
        if (handlerAndRunnable != null) {
            this.nextTimelineUpdateOnCompletionActions.add(handlerAndRunnable);
        }
    }

    public ConcatenatingMediaSource(boolean z2, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        this(z2, false, shuffleOrder, mediaSourceArr);
    }

    public synchronized void addMediaSource(MediaSource mediaSource, Handler handler, Runnable runnable) {
        addMediaSource(this.mediaSourcesPublic.size(), mediaSource, handler, runnable);
    }

    public synchronized void clear(Handler handler, Runnable runnable) {
        removeMediaSourceRange(0, getSize(), handler, runnable);
    }

    public synchronized void moveMediaSource(int i2, int i3, Handler handler, Runnable runnable) {
        movePublicMediaSource(i2, i3, handler, runnable);
    }

    public synchronized void removeMediaSourceRange(int i2, int i3, Handler handler, Runnable runnable) {
        removePublicMediaSources(i2, i3, handler, runnable);
    }

    public synchronized void setShuffleOrder(ShuffleOrder shuffleOrder, Handler handler, Runnable runnable) {
        setPublicShuffleOrder(shuffleOrder, handler, runnable);
    }

    public ConcatenatingMediaSource(boolean z2, boolean z3, ShuffleOrder shuffleOrder, MediaSource... mediaSourceArr) {
        for (MediaSource mediaSource : mediaSourceArr) {
            Assertions.checkNotNull(mediaSource);
        }
        this.shuffleOrder = shuffleOrder.getLength() > 0 ? shuffleOrder.cloneAndClear() : shuffleOrder;
        this.mediaSourceByMediaPeriod = new IdentityHashMap<>();
        this.mediaSourceByUid = new HashMap();
        this.mediaSourcesPublic = new ArrayList();
        this.mediaSourceHolders = new ArrayList();
        this.nextTimelineUpdateOnCompletionActions = new HashSet();
        this.pendingOnCompletionActions = new HashSet();
        this.enabledMediaSourceHolders = new HashSet();
        this.isAtomic = z2;
        this.useLazyPreparation = z3;
        addMediaSources(Arrays.asList(mediaSourceArr));
    }

    public synchronized MediaSource removeMediaSource(int i2, Handler handler, Runnable runnable) {
        MediaSource mediaSource;
        mediaSource = getMediaSource(i2);
        removePublicMediaSources(i2, i2 + 1, handler, runnable);
        return mediaSource;
    }

    public synchronized void addMediaSource(int i2, MediaSource mediaSource) {
        addPublicMediaSources(i2, Collections.singletonList(mediaSource), null, null);
    }

    public synchronized void addMediaSources(Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        addPublicMediaSources(this.mediaSourcesPublic.size(), collection, handler, runnable);
    }

    public synchronized void addMediaSources(int i2, Collection<MediaSource> collection) {
        addPublicMediaSources(i2, collection, null, null);
    }

    public synchronized void addMediaSource(int i2, MediaSource mediaSource, Handler handler, Runnable runnable) {
        addPublicMediaSources(i2, Collections.singletonList(mediaSource), handler, runnable);
    }

    public synchronized void addMediaSources(int i2, Collection<MediaSource> collection, Handler handler, Runnable runnable) {
        addPublicMediaSources(i2, collection, handler, runnable);
    }
}
