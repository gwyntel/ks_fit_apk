package androidx.media3.common;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.AdPlaybackState;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.InlineMe;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class Timeline {
    public static final Timeline EMPTY = new Timeline() { // from class: androidx.media3.common.Timeline.1
        @Override // androidx.media3.common.Timeline
        public int getIndexOfPeriod(Object obj) {
            return -1;
        }

        @Override // androidx.media3.common.Timeline
        public Period getPeriod(int i2, Period period, boolean z2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return 0;
        }

        @Override // androidx.media3.common.Timeline
        public Object getUidOfPeriod(int i2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // androidx.media3.common.Timeline
        public Window getWindow(int i2, Window window, long j2) {
            throw new IndexOutOfBoundsException();
        }

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return 0;
        }
    };
    private static final String FIELD_WINDOWS = Util.intToStringMaxRadix(0);
    private static final String FIELD_PERIODS = Util.intToStringMaxRadix(1);
    private static final String FIELD_SHUFFLED_WINDOW_INDICES = Util.intToStringMaxRadix(2);

    public static final class Period {
        private AdPlaybackState adPlaybackState = AdPlaybackState.NONE;

        @UnstableApi
        public long durationUs;

        @Nullable
        public Object id;
        public boolean isPlaceholder;

        @UnstableApi
        public long positionInWindowUs;

        @Nullable
        public Object uid;
        public int windowIndex;
        private static final String FIELD_WINDOW_INDEX = Util.intToStringMaxRadix(0);
        private static final String FIELD_DURATION_US = Util.intToStringMaxRadix(1);
        private static final String FIELD_POSITION_IN_WINDOW_US = Util.intToStringMaxRadix(2);
        private static final String FIELD_PLACEHOLDER = Util.intToStringMaxRadix(3);
        private static final String FIELD_AD_PLAYBACK_STATE = Util.intToStringMaxRadix(4);

        @UnstableApi
        public static Period fromBundle(Bundle bundle) {
            int i2 = bundle.getInt(FIELD_WINDOW_INDEX, 0);
            long j2 = bundle.getLong(FIELD_DURATION_US, C.TIME_UNSET);
            long j3 = bundle.getLong(FIELD_POSITION_IN_WINDOW_US, 0L);
            boolean z2 = bundle.getBoolean(FIELD_PLACEHOLDER, false);
            Bundle bundle2 = bundle.getBundle(FIELD_AD_PLAYBACK_STATE);
            AdPlaybackState adPlaybackStateFromBundle = bundle2 != null ? AdPlaybackState.fromBundle(bundle2) : AdPlaybackState.NONE;
            Period period = new Period();
            period.set(null, null, i2, j2, j3, adPlaybackStateFromBundle, z2);
            return period;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Period.class.equals(obj.getClass())) {
                return false;
            }
            Period period = (Period) obj;
            return Util.areEqual(this.id, period.id) && Util.areEqual(this.uid, period.uid) && this.windowIndex == period.windowIndex && this.durationUs == period.durationUs && this.positionInWindowUs == period.positionInWindowUs && this.isPlaceholder == period.isPlaceholder && Util.areEqual(this.adPlaybackState, period.adPlaybackState);
        }

        public int getAdCountInAdGroup(int i2) {
            return this.adPlaybackState.getAdGroup(i2).count;
        }

        public long getAdDurationUs(int i2, int i3) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.getAdGroup(i2);
            return adGroup.count != -1 ? adGroup.durationsUs[i3] : C.TIME_UNSET;
        }

        public int getAdGroupCount() {
            return this.adPlaybackState.adGroupCount;
        }

        public int getAdGroupIndexAfterPositionUs(long j2) {
            return this.adPlaybackState.getAdGroupIndexAfterPositionUs(j2, this.durationUs);
        }

        public int getAdGroupIndexForPositionUs(long j2) {
            return this.adPlaybackState.getAdGroupIndexForPositionUs(j2, this.durationUs);
        }

        public long getAdGroupTimeUs(int i2) {
            return this.adPlaybackState.getAdGroup(i2).timeUs;
        }

        public long getAdResumePositionUs() {
            return this.adPlaybackState.adResumePositionUs;
        }

        @UnstableApi
        public int getAdState(int i2, int i3) {
            AdPlaybackState.AdGroup adGroup = this.adPlaybackState.getAdGroup(i2);
            if (adGroup.count != -1) {
                return adGroup.states[i3];
            }
            return 0;
        }

        @Nullable
        public Object getAdsId() {
            return this.adPlaybackState.adsId;
        }

        @UnstableApi
        public long getContentResumeOffsetUs(int i2) {
            return this.adPlaybackState.getAdGroup(i2).contentResumeOffsetUs;
        }

        public long getDurationMs() {
            return Util.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public int getFirstAdIndexToPlay(int i2) {
            return this.adPlaybackState.getAdGroup(i2).getFirstAdIndexToPlay();
        }

        public int getNextAdIndexToPlay(int i2, int i3) {
            return this.adPlaybackState.getAdGroup(i2).getNextAdIndexToPlay(i3);
        }

        public long getPositionInWindowMs() {
            return Util.usToMs(this.positionInWindowUs);
        }

        public long getPositionInWindowUs() {
            return this.positionInWindowUs;
        }

        public int getRemovedAdGroupCount() {
            return this.adPlaybackState.removedAdGroupCount;
        }

        public boolean hasPlayedAdGroup(int i2) {
            return !this.adPlaybackState.getAdGroup(i2).hasUnplayedAds();
        }

        public int hashCode() {
            Object obj = this.id;
            int iHashCode = (217 + (obj == null ? 0 : obj.hashCode())) * 31;
            Object obj2 = this.uid;
            int iHashCode2 = (((iHashCode + (obj2 != null ? obj2.hashCode() : 0)) * 31) + this.windowIndex) * 31;
            long j2 = this.durationUs;
            int i2 = (iHashCode2 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.positionInWindowUs;
            return ((((i2 + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.isPlaceholder ? 1 : 0)) * 31) + this.adPlaybackState.hashCode();
        }

        @UnstableApi
        public boolean isLivePostrollPlaceholder(int i2) {
            return i2 == getAdGroupCount() - 1 && this.adPlaybackState.isLivePostrollPlaceholder(i2);
        }

        @UnstableApi
        public boolean isServerSideInsertedAdGroup(int i2) {
            return this.adPlaybackState.getAdGroup(i2).isServerSideInserted;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3) {
            return set(obj, obj2, i2, j2, j3, AdPlaybackState.NONE, false);
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            int i2 = this.windowIndex;
            if (i2 != 0) {
                bundle.putInt(FIELD_WINDOW_INDEX, i2);
            }
            long j2 = this.durationUs;
            if (j2 != C.TIME_UNSET) {
                bundle.putLong(FIELD_DURATION_US, j2);
            }
            long j3 = this.positionInWindowUs;
            if (j3 != 0) {
                bundle.putLong(FIELD_POSITION_IN_WINDOW_US, j3);
            }
            boolean z2 = this.isPlaceholder;
            if (z2) {
                bundle.putBoolean(FIELD_PLACEHOLDER, z2);
            }
            if (!this.adPlaybackState.equals(AdPlaybackState.NONE)) {
                bundle.putBundle(FIELD_AD_PLAYBACK_STATE, this.adPlaybackState.toBundle());
            }
            return bundle;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Period set(@Nullable Object obj, @Nullable Object obj2, int i2, long j2, long j3, AdPlaybackState adPlaybackState, boolean z2) {
            this.id = obj;
            this.uid = obj2;
            this.windowIndex = i2;
            this.durationUs = j2;
            this.positionInWindowUs = j3;
            this.adPlaybackState = adPlaybackState;
            this.isPlaceholder = z2;
            return this;
        }
    }

    @UnstableApi
    public static final class RemotableTimeline extends Timeline {
        private final ImmutableList<Period> periods;
        private final int[] shuffledWindowIndices;
        private final int[] windowIndicesInShuffled;
        private final ImmutableList<Window> windows;

        public RemotableTimeline(ImmutableList<Window> immutableList, ImmutableList<Period> immutableList2, int[] iArr) {
            Assertions.checkArgument(immutableList.size() == iArr.length);
            this.windows = immutableList;
            this.periods = immutableList2;
            this.shuffledWindowIndices = iArr;
            this.windowIndicesInShuffled = new int[iArr.length];
            for (int i2 = 0; i2 < iArr.length; i2++) {
                this.windowIndicesInShuffled[iArr[i2]] = i2;
            }
        }

        @Override // androidx.media3.common.Timeline
        public int getFirstWindowIndex(boolean z2) {
            if (isEmpty()) {
                return -1;
            }
            if (z2) {
                return this.shuffledWindowIndices[0];
            }
            return 0;
        }

        @Override // androidx.media3.common.Timeline
        public int getIndexOfPeriod(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.media3.common.Timeline
        public int getLastWindowIndex(boolean z2) {
            if (isEmpty()) {
                return -1;
            }
            return z2 ? this.shuffledWindowIndices[getWindowCount() - 1] : getWindowCount() - 1;
        }

        @Override // androidx.media3.common.Timeline
        public int getNextWindowIndex(int i2, int i3, boolean z2) {
            if (i3 == 1) {
                return i2;
            }
            if (i2 != getLastWindowIndex(z2)) {
                return z2 ? this.shuffledWindowIndices[this.windowIndicesInShuffled[i2] + 1] : i2 + 1;
            }
            if (i3 == 2) {
                return getFirstWindowIndex(z2);
            }
            return -1;
        }

        @Override // androidx.media3.common.Timeline
        public Period getPeriod(int i2, Period period, boolean z2) {
            Period period2 = this.periods.get(i2);
            period.set(period2.id, period2.uid, period2.windowIndex, period2.durationUs, period2.positionInWindowUs, period2.adPlaybackState, period2.isPlaceholder);
            return period;
        }

        @Override // androidx.media3.common.Timeline
        public int getPeriodCount() {
            return this.periods.size();
        }

        @Override // androidx.media3.common.Timeline
        public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
            if (i3 == 1) {
                return i2;
            }
            if (i2 != getFirstWindowIndex(z2)) {
                return z2 ? this.shuffledWindowIndices[this.windowIndicesInShuffled[i2] - 1] : i2 - 1;
            }
            if (i3 == 2) {
                return getLastWindowIndex(z2);
            }
            return -1;
        }

        @Override // androidx.media3.common.Timeline
        public Object getUidOfPeriod(int i2) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.media3.common.Timeline
        public Window getWindow(int i2, Window window, long j2) {
            Window window2 = this.windows.get(i2);
            window.set(window2.uid, window2.mediaItem, window2.manifest, window2.presentationStartTimeMs, window2.windowStartTimeMs, window2.elapsedRealtimeEpochOffsetMs, window2.isSeekable, window2.isDynamic, window2.liveConfiguration, window2.defaultPositionUs, window2.durationUs, window2.firstPeriodIndex, window2.lastPeriodIndex, window2.positionInFirstPeriodUs);
            window.isPlaceholder = window2.isPlaceholder;
            return window;
        }

        @Override // androidx.media3.common.Timeline
        public int getWindowCount() {
            return this.windows.size();
        }
    }

    public static final class Window {

        @UnstableApi
        public long defaultPositionUs;

        @UnstableApi
        public long durationUs;
        public long elapsedRealtimeEpochOffsetMs;
        public int firstPeriodIndex;
        public boolean isDynamic;
        public boolean isPlaceholder;
        public boolean isSeekable;
        public int lastPeriodIndex;

        @Nullable
        public MediaItem.LiveConfiguration liveConfiguration;

        @Nullable
        public Object manifest;

        @UnstableApi
        public long positionInFirstPeriodUs;
        public long presentationStartTimeMs;

        @Nullable
        @UnstableApi
        @Deprecated
        public Object tag;
        public long windowStartTimeMs;
        public static final Object SINGLE_WINDOW_UID = new Object();
        private static final Object FAKE_WINDOW_UID = new Object();
        private static final MediaItem PLACEHOLDER_MEDIA_ITEM = new MediaItem.Builder().setMediaId("androidx.media3.common.Timeline").setUri(Uri.EMPTY).build();
        private static final String FIELD_MEDIA_ITEM = Util.intToStringMaxRadix(1);
        private static final String FIELD_PRESENTATION_START_TIME_MS = Util.intToStringMaxRadix(2);
        private static final String FIELD_WINDOW_START_TIME_MS = Util.intToStringMaxRadix(3);
        private static final String FIELD_ELAPSED_REALTIME_EPOCH_OFFSET_MS = Util.intToStringMaxRadix(4);
        private static final String FIELD_IS_SEEKABLE = Util.intToStringMaxRadix(5);
        private static final String FIELD_IS_DYNAMIC = Util.intToStringMaxRadix(6);
        private static final String FIELD_LIVE_CONFIGURATION = Util.intToStringMaxRadix(7);
        private static final String FIELD_IS_PLACEHOLDER = Util.intToStringMaxRadix(8);
        private static final String FIELD_DEFAULT_POSITION_US = Util.intToStringMaxRadix(9);
        private static final String FIELD_DURATION_US = Util.intToStringMaxRadix(10);
        private static final String FIELD_FIRST_PERIOD_INDEX = Util.intToStringMaxRadix(11);
        private static final String FIELD_LAST_PERIOD_INDEX = Util.intToStringMaxRadix(12);
        private static final String FIELD_POSITION_IN_FIRST_PERIOD_US = Util.intToStringMaxRadix(13);
        public Object uid = SINGLE_WINDOW_UID;
        public MediaItem mediaItem = PLACEHOLDER_MEDIA_ITEM;

        @UnstableApi
        public static Window fromBundle(Bundle bundle) {
            Bundle bundle2 = bundle.getBundle(FIELD_MEDIA_ITEM);
            MediaItem mediaItemFromBundle = bundle2 != null ? MediaItem.fromBundle(bundle2) : MediaItem.EMPTY;
            long j2 = bundle.getLong(FIELD_PRESENTATION_START_TIME_MS, C.TIME_UNSET);
            long j3 = bundle.getLong(FIELD_WINDOW_START_TIME_MS, C.TIME_UNSET);
            long j4 = bundle.getLong(FIELD_ELAPSED_REALTIME_EPOCH_OFFSET_MS, C.TIME_UNSET);
            boolean z2 = bundle.getBoolean(FIELD_IS_SEEKABLE, false);
            boolean z3 = bundle.getBoolean(FIELD_IS_DYNAMIC, false);
            Bundle bundle3 = bundle.getBundle(FIELD_LIVE_CONFIGURATION);
            MediaItem.LiveConfiguration liveConfigurationFromBundle = bundle3 != null ? MediaItem.LiveConfiguration.fromBundle(bundle3) : null;
            boolean z4 = bundle.getBoolean(FIELD_IS_PLACEHOLDER, false);
            long j5 = bundle.getLong(FIELD_DEFAULT_POSITION_US, 0L);
            long j6 = bundle.getLong(FIELD_DURATION_US, C.TIME_UNSET);
            int i2 = bundle.getInt(FIELD_FIRST_PERIOD_INDEX, 0);
            int i3 = bundle.getInt(FIELD_LAST_PERIOD_INDEX, 0);
            long j7 = bundle.getLong(FIELD_POSITION_IN_FIRST_PERIOD_US, 0L);
            Window window = new Window();
            window.set(FAKE_WINDOW_UID, mediaItemFromBundle, null, j2, j3, j4, z2, z3, liveConfigurationFromBundle, j5, j6, i2, i3, j7);
            window.isPlaceholder = z4;
            return window;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !Window.class.equals(obj.getClass())) {
                return false;
            }
            Window window = (Window) obj;
            return Util.areEqual(this.uid, window.uid) && Util.areEqual(this.mediaItem, window.mediaItem) && Util.areEqual(this.manifest, window.manifest) && Util.areEqual(this.liveConfiguration, window.liveConfiguration) && this.presentationStartTimeMs == window.presentationStartTimeMs && this.windowStartTimeMs == window.windowStartTimeMs && this.elapsedRealtimeEpochOffsetMs == window.elapsedRealtimeEpochOffsetMs && this.isSeekable == window.isSeekable && this.isDynamic == window.isDynamic && this.isPlaceholder == window.isPlaceholder && this.defaultPositionUs == window.defaultPositionUs && this.durationUs == window.durationUs && this.firstPeriodIndex == window.firstPeriodIndex && this.lastPeriodIndex == window.lastPeriodIndex && this.positionInFirstPeriodUs == window.positionInFirstPeriodUs;
        }

        public long getCurrentUnixTimeMs() {
            return Util.getNowUnixTimeMs(this.elapsedRealtimeEpochOffsetMs);
        }

        public long getDefaultPositionMs() {
            return Util.usToMs(this.defaultPositionUs);
        }

        public long getDefaultPositionUs() {
            return this.defaultPositionUs;
        }

        public long getDurationMs() {
            return Util.usToMs(this.durationUs);
        }

        public long getDurationUs() {
            return this.durationUs;
        }

        public long getPositionInFirstPeriodMs() {
            return Util.usToMs(this.positionInFirstPeriodUs);
        }

        public long getPositionInFirstPeriodUs() {
            return this.positionInFirstPeriodUs;
        }

        public int hashCode() {
            int iHashCode = (((217 + this.uid.hashCode()) * 31) + this.mediaItem.hashCode()) * 31;
            Object obj = this.manifest;
            int iHashCode2 = (iHashCode + (obj == null ? 0 : obj.hashCode())) * 31;
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            int iHashCode3 = (iHashCode2 + (liveConfiguration != null ? liveConfiguration.hashCode() : 0)) * 31;
            long j2 = this.presentationStartTimeMs;
            int i2 = (iHashCode3 + ((int) (j2 ^ (j2 >>> 32)))) * 31;
            long j3 = this.windowStartTimeMs;
            int i3 = (i2 + ((int) (j3 ^ (j3 >>> 32)))) * 31;
            long j4 = this.elapsedRealtimeEpochOffsetMs;
            int i4 = (((((((i3 + ((int) (j4 ^ (j4 >>> 32)))) * 31) + (this.isSeekable ? 1 : 0)) * 31) + (this.isDynamic ? 1 : 0)) * 31) + (this.isPlaceholder ? 1 : 0)) * 31;
            long j5 = this.defaultPositionUs;
            int i5 = (i4 + ((int) (j5 ^ (j5 >>> 32)))) * 31;
            long j6 = this.durationUs;
            int i6 = (((((i5 + ((int) (j6 ^ (j6 >>> 32)))) * 31) + this.firstPeriodIndex) * 31) + this.lastPeriodIndex) * 31;
            long j7 = this.positionInFirstPeriodUs;
            return i6 + ((int) (j7 ^ (j7 >>> 32)));
        }

        public boolean isLive() {
            return this.liveConfiguration != null;
        }

        @CanIgnoreReturnValue
        @UnstableApi
        public Window set(Object obj, @Nullable MediaItem mediaItem, @Nullable Object obj2, long j2, long j3, long j4, boolean z2, boolean z3, @Nullable MediaItem.LiveConfiguration liveConfiguration, long j5, long j6, int i2, int i3, long j7) {
            MediaItem.LocalConfiguration localConfiguration;
            this.uid = obj;
            this.mediaItem = mediaItem != null ? mediaItem : PLACEHOLDER_MEDIA_ITEM;
            this.tag = (mediaItem == null || (localConfiguration = mediaItem.localConfiguration) == null) ? null : localConfiguration.tag;
            this.manifest = obj2;
            this.presentationStartTimeMs = j2;
            this.windowStartTimeMs = j3;
            this.elapsedRealtimeEpochOffsetMs = j4;
            this.isSeekable = z2;
            this.isDynamic = z3;
            this.liveConfiguration = liveConfiguration;
            this.defaultPositionUs = j5;
            this.durationUs = j6;
            this.firstPeriodIndex = i2;
            this.lastPeriodIndex = i3;
            this.positionInFirstPeriodUs = j7;
            this.isPlaceholder = false;
            return this;
        }

        @UnstableApi
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            if (!MediaItem.EMPTY.equals(this.mediaItem)) {
                bundle.putBundle(FIELD_MEDIA_ITEM, this.mediaItem.toBundle());
            }
            long j2 = this.presentationStartTimeMs;
            if (j2 != C.TIME_UNSET) {
                bundle.putLong(FIELD_PRESENTATION_START_TIME_MS, j2);
            }
            long j3 = this.windowStartTimeMs;
            if (j3 != C.TIME_UNSET) {
                bundle.putLong(FIELD_WINDOW_START_TIME_MS, j3);
            }
            long j4 = this.elapsedRealtimeEpochOffsetMs;
            if (j4 != C.TIME_UNSET) {
                bundle.putLong(FIELD_ELAPSED_REALTIME_EPOCH_OFFSET_MS, j4);
            }
            boolean z2 = this.isSeekable;
            if (z2) {
                bundle.putBoolean(FIELD_IS_SEEKABLE, z2);
            }
            boolean z3 = this.isDynamic;
            if (z3) {
                bundle.putBoolean(FIELD_IS_DYNAMIC, z3);
            }
            MediaItem.LiveConfiguration liveConfiguration = this.liveConfiguration;
            if (liveConfiguration != null) {
                bundle.putBundle(FIELD_LIVE_CONFIGURATION, liveConfiguration.toBundle());
            }
            boolean z4 = this.isPlaceholder;
            if (z4) {
                bundle.putBoolean(FIELD_IS_PLACEHOLDER, z4);
            }
            long j5 = this.defaultPositionUs;
            if (j5 != 0) {
                bundle.putLong(FIELD_DEFAULT_POSITION_US, j5);
            }
            long j6 = this.durationUs;
            if (j6 != C.TIME_UNSET) {
                bundle.putLong(FIELD_DURATION_US, j6);
            }
            int i2 = this.firstPeriodIndex;
            if (i2 != 0) {
                bundle.putInt(FIELD_FIRST_PERIOD_INDEX, i2);
            }
            int i3 = this.lastPeriodIndex;
            if (i3 != 0) {
                bundle.putInt(FIELD_LAST_PERIOD_INDEX, i3);
            }
            long j7 = this.positionInFirstPeriodUs;
            if (j7 != 0) {
                bundle.putLong(FIELD_POSITION_IN_FIRST_PERIOD_US, j7);
            }
            return bundle;
        }
    }

    protected Timeline() {
    }

    @UnstableApi
    public static Timeline fromBundle(Bundle bundle) {
        ImmutableList immutableListFromBundleListRetriever = fromBundleListRetriever(new Function() { // from class: androidx.media3.common.g2
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Timeline.Window.fromBundle((Bundle) obj);
            }
        }, bundle.getBinder(FIELD_WINDOWS));
        ImmutableList immutableListFromBundleListRetriever2 = fromBundleListRetriever(new Function() { // from class: androidx.media3.common.h2
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Timeline.Period.fromBundle((Bundle) obj);
            }
        }, bundle.getBinder(FIELD_PERIODS));
        int[] intArray = bundle.getIntArray(FIELD_SHUFFLED_WINDOW_INDICES);
        if (intArray == null) {
            intArray = generateUnshuffledIndices(immutableListFromBundleListRetriever.size());
        }
        return new RemotableTimeline(immutableListFromBundleListRetriever, immutableListFromBundleListRetriever2, intArray);
    }

    private static <T> ImmutableList<T> fromBundleListRetriever(Function<Bundle, T> function, @Nullable IBinder iBinder) {
        return iBinder == null ? ImmutableList.of() : BundleCollectionUtil.fromBundleList(function, BundleListRetriever.getList(iBinder));
    }

    private static int[] generateUnshuffledIndices(int i2) {
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[i3] = i3;
        }
        return iArr;
    }

    @UnstableApi
    public final Timeline copyWithSingleWindow(int i2) {
        if (getWindowCount() == 1) {
            return this;
        }
        Window window = getWindow(i2, new Window(), 0L);
        ImmutableList.Builder builder = ImmutableList.builder();
        int i3 = window.firstPeriodIndex;
        while (true) {
            int i4 = window.lastPeriodIndex;
            if (i3 > i4) {
                window.lastPeriodIndex = i4 - window.firstPeriodIndex;
                window.firstPeriodIndex = 0;
                return new RemotableTimeline(ImmutableList.of(window), builder.build(), new int[]{0});
            }
            Period period = getPeriod(i3, new Period(), true);
            period.windowIndex = 0;
            builder.add((ImmutableList.Builder) period);
            i3++;
        }
    }

    public boolean equals(@Nullable Object obj) {
        int lastWindowIndex;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Timeline)) {
            return false;
        }
        Timeline timeline = (Timeline) obj;
        if (timeline.getWindowCount() != getWindowCount() || timeline.getPeriodCount() != getPeriodCount()) {
            return false;
        }
        Window window = new Window();
        Period period = new Period();
        Window window2 = new Window();
        Period period2 = new Period();
        for (int i2 = 0; i2 < getWindowCount(); i2++) {
            if (!getWindow(i2, window).equals(timeline.getWindow(i2, window2))) {
                return false;
            }
        }
        for (int i3 = 0; i3 < getPeriodCount(); i3++) {
            if (!getPeriod(i3, period, true).equals(timeline.getPeriod(i3, period2, true))) {
                return false;
            }
        }
        int firstWindowIndex = getFirstWindowIndex(true);
        if (firstWindowIndex != timeline.getFirstWindowIndex(true) || (lastWindowIndex = getLastWindowIndex(true)) != timeline.getLastWindowIndex(true)) {
            return false;
        }
        while (firstWindowIndex != lastWindowIndex) {
            int nextWindowIndex = getNextWindowIndex(firstWindowIndex, 0, true);
            if (nextWindowIndex != timeline.getNextWindowIndex(firstWindowIndex, 0, true)) {
                return false;
            }
            firstWindowIndex = nextWindowIndex;
        }
        return true;
    }

    public int getFirstWindowIndex(boolean z2) {
        return isEmpty() ? -1 : 0;
    }

    public abstract int getIndexOfPeriod(Object obj);

    public int getLastWindowIndex(boolean z2) {
        if (isEmpty()) {
            return -1;
        }
        return getWindowCount() - 1;
    }

    public final int getNextPeriodIndex(int i2, Period period, Window window, int i3, boolean z2) {
        int i4 = getPeriod(i2, period).windowIndex;
        if (getWindow(i4, window).lastPeriodIndex != i2) {
            return i2 + 1;
        }
        int nextWindowIndex = getNextWindowIndex(i4, i3, z2);
        if (nextWindowIndex == -1) {
            return -1;
        }
        return getWindow(nextWindowIndex, window).firstPeriodIndex;
    }

    public int getNextWindowIndex(int i2, int i3, boolean z2) {
        if (i3 == 0) {
            if (i2 == getLastWindowIndex(z2)) {
                return -1;
            }
            return i2 + 1;
        }
        if (i3 == 1) {
            return i2;
        }
        if (i3 == 2) {
            return i2 == getLastWindowIndex(z2) ? getFirstWindowIndex(z2) : i2 + 1;
        }
        throw new IllegalStateException();
    }

    public final Period getPeriod(int i2, Period period) {
        return getPeriod(i2, period, false);
    }

    public abstract Period getPeriod(int i2, Period period, boolean z2);

    public Period getPeriodByUid(Object obj, Period period) {
        return getPeriod(getIndexOfPeriod(obj), period, true);
    }

    public abstract int getPeriodCount();

    @InlineMe(replacement = "this.getPeriodPositionUs(window, period, windowIndex, windowPositionUs)")
    @UnstableApi
    @Deprecated
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i2, long j2) {
        return getPeriodPositionUs(window, period, i2, j2);
    }

    public final Pair<Object, Long> getPeriodPositionUs(Window window, Period period, int i2, long j2) {
        return (Pair) Assertions.checkNotNull(getPeriodPositionUs(window, period, i2, j2, 0L));
    }

    public int getPreviousWindowIndex(int i2, int i3, boolean z2) {
        if (i3 == 0) {
            if (i2 == getFirstWindowIndex(z2)) {
                return -1;
            }
            return i2 - 1;
        }
        if (i3 == 1) {
            return i2;
        }
        if (i3 == 2) {
            return i2 == getFirstWindowIndex(z2) ? getLastWindowIndex(z2) : i2 - 1;
        }
        throw new IllegalStateException();
    }

    public abstract Object getUidOfPeriod(int i2);

    public final Window getWindow(int i2, Window window) {
        return getWindow(i2, window, 0L);
    }

    public abstract Window getWindow(int i2, Window window, long j2);

    public abstract int getWindowCount();

    public int hashCode() {
        Window window = new Window();
        Period period = new Period();
        int windowCount = 217 + getWindowCount();
        for (int i2 = 0; i2 < getWindowCount(); i2++) {
            windowCount = (windowCount * 31) + getWindow(i2, window).hashCode();
        }
        int periodCount = (windowCount * 31) + getPeriodCount();
        for (int i3 = 0; i3 < getPeriodCount(); i3++) {
            periodCount = (periodCount * 31) + getPeriod(i3, period, true).hashCode();
        }
        int firstWindowIndex = getFirstWindowIndex(true);
        while (firstWindowIndex != -1) {
            periodCount = (periodCount * 31) + firstWindowIndex;
            firstWindowIndex = getNextWindowIndex(firstWindowIndex, 0, true);
        }
        return periodCount;
    }

    public final boolean isEmpty() {
        return getWindowCount() == 0;
    }

    public final boolean isLastPeriod(int i2, Period period, Window window, int i3, boolean z2) {
        return getNextPeriodIndex(i2, period, window, i3, z2) == -1;
    }

    @UnstableApi
    public final Bundle toBundle() {
        ArrayList arrayList = new ArrayList();
        int windowCount = getWindowCount();
        Window window = new Window();
        for (int i2 = 0; i2 < windowCount; i2++) {
            arrayList.add(getWindow(i2, window, 0L).toBundle());
        }
        ArrayList arrayList2 = new ArrayList();
        int periodCount = getPeriodCount();
        Period period = new Period();
        for (int i3 = 0; i3 < periodCount; i3++) {
            arrayList2.add(getPeriod(i3, period, false).toBundle());
        }
        int[] iArr = new int[windowCount];
        if (windowCount > 0) {
            iArr[0] = getFirstWindowIndex(true);
        }
        for (int i4 = 1; i4 < windowCount; i4++) {
            iArr[i4] = getNextWindowIndex(iArr[i4 - 1], 0, true);
        }
        Bundle bundle = new Bundle();
        bundle.putBinder(FIELD_WINDOWS, new BundleListRetriever(arrayList));
        bundle.putBinder(FIELD_PERIODS, new BundleListRetriever(arrayList2));
        bundle.putIntArray(FIELD_SHUFFLED_WINDOW_INDICES, iArr);
        return bundle;
    }

    @Deprecated
    @Nullable
    @InlineMe(replacement = "this.getPeriodPositionUs(window, period, windowIndex, windowPositionUs, defaultPositionProjectionUs)")
    @UnstableApi
    public final Pair<Object, Long> getPeriodPosition(Window window, Period period, int i2, long j2, long j3) {
        return getPeriodPositionUs(window, period, i2, j2, j3);
    }

    @Nullable
    public final Pair<Object, Long> getPeriodPositionUs(Window window, Period period, int i2, long j2, long j3) {
        Assertions.checkIndex(i2, 0, getWindowCount());
        getWindow(i2, window, j3);
        if (j2 == C.TIME_UNSET) {
            j2 = window.getDefaultPositionUs();
            if (j2 == C.TIME_UNSET) {
                return null;
            }
        }
        int i3 = window.firstPeriodIndex;
        getPeriod(i3, period);
        while (i3 < window.lastPeriodIndex && period.positionInWindowUs != j2) {
            int i4 = i3 + 1;
            if (getPeriod(i4, period).positionInWindowUs > j2) {
                break;
            }
            i3 = i4;
        }
        getPeriod(i3, period, true);
        long jMin = j2 - period.positionInWindowUs;
        long j4 = period.durationUs;
        if (j4 != C.TIME_UNSET) {
            jMin = Math.min(jMin, j4 - 1);
        }
        return Pair.create(Assertions.checkNotNull(period.uid), Long.valueOf(Math.max(0L, jMin)));
    }
}
