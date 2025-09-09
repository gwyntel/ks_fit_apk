package androidx.media3.exoplayer.source;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.util.BundleCollectionUtil;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;

@UnstableApi
/* loaded from: classes2.dex */
public final class TrackGroupArray {
    public static final TrackGroupArray EMPTY = new TrackGroupArray(new TrackGroup[0]);
    private static final String FIELD_TRACK_GROUPS = Util.intToStringMaxRadix(0);
    private static final String TAG = "TrackGroupArray";
    private int hashCode;
    public final int length;
    private final ImmutableList<TrackGroup> trackGroups;

    public TrackGroupArray(TrackGroup... trackGroupArr) {
        this.trackGroups = ImmutableList.copyOf(trackGroupArr);
        this.length = trackGroupArr.length;
        verifyCorrectness();
    }

    public static TrackGroupArray fromBundle(Bundle bundle) {
        ArrayList parcelableArrayList = bundle.getParcelableArrayList(FIELD_TRACK_GROUPS);
        return parcelableArrayList == null ? new TrackGroupArray(new TrackGroup[0]) : new TrackGroupArray((TrackGroup[]) BundleCollectionUtil.fromBundleList(new Function() { // from class: androidx.media3.exoplayer.source.i0
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return TrackGroup.fromBundle((Bundle) obj);
            }
        }, parcelableArrayList).toArray(new TrackGroup[0]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Integer lambda$getTrackTypes$0(TrackGroup trackGroup) {
        return Integer.valueOf(trackGroup.type);
    }

    private void verifyCorrectness() {
        int i2 = 0;
        while (i2 < this.trackGroups.size()) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < this.trackGroups.size(); i4++) {
                if (this.trackGroups.get(i2).equals(this.trackGroups.get(i4))) {
                    Log.e(TAG, "", new IllegalArgumentException("Multiple identical TrackGroups added to one TrackGroupArray."));
                }
            }
            i2 = i3;
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackGroupArray.class != obj.getClass()) {
            return false;
        }
        TrackGroupArray trackGroupArray = (TrackGroupArray) obj;
        return this.length == trackGroupArray.length && this.trackGroups.equals(trackGroupArray.trackGroups);
    }

    public TrackGroup get(int i2) {
        return this.trackGroups.get(i2);
    }

    public ImmutableList<Integer> getTrackTypes() {
        return ImmutableList.copyOf((Collection) Lists.transform(this.trackGroups, new Function() { // from class: androidx.media3.exoplayer.source.g0
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return TrackGroupArray.lambda$getTrackTypes$0((TrackGroup) obj);
            }
        }));
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.trackGroups.hashCode();
        }
        return this.hashCode;
    }

    public int indexOf(TrackGroup trackGroup) {
        int iIndexOf = this.trackGroups.indexOf(trackGroup);
        if (iIndexOf >= 0) {
            return iIndexOf;
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FIELD_TRACK_GROUPS, BundleCollectionUtil.toBundleArrayList(this.trackGroups, new Function() { // from class: androidx.media3.exoplayer.source.h0
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return ((TrackGroup) obj).toBundle();
            }
        }));
        return bundle;
    }
}
