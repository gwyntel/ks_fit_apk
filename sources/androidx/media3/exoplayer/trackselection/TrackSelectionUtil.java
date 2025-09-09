package androidx.media3.exoplayer.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import androidx.media3.common.TrackGroup;
import androidx.media3.common.Tracks;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;
import androidx.media3.exoplayer.trackselection.MappingTrackSelector;
import androidx.media3.exoplayer.upstream.LoadErrorHandlingPolicy;
import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;

@UnstableApi
/* loaded from: classes2.dex */
public final class TrackSelectionUtil {

    public interface AdaptiveTrackSelectionFactory {
        ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition);
    }

    private TrackSelectionUtil() {
    }

    public static Tracks buildTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, TrackSelection[] trackSelectionArr) {
        List[] listArr = new List[trackSelectionArr.length];
        for (int i2 = 0; i2 < trackSelectionArr.length; i2++) {
            TrackSelection trackSelection = trackSelectionArr[i2];
            listArr[i2] = trackSelection != null ? ImmutableList.of(trackSelection) : ImmutableList.of();
        }
        return buildTracks(mappedTrackInfo, (List<? extends TrackSelection>[]) listArr);
    }

    public static LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (exoTrackSelection.isTrackExcluded(i3, jElapsedRealtime)) {
                i2++;
            }
        }
        return new LoadErrorHandlingPolicy.FallbackOptions(1, 0, length, i2);
    }

    public static ExoTrackSelection[] createTrackSelectionsForDefinitions(ExoTrackSelection.Definition[] definitionArr, AdaptiveTrackSelectionFactory adaptiveTrackSelectionFactory) {
        ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
        boolean z2 = false;
        for (int i2 = 0; i2 < definitionArr.length; i2++) {
            ExoTrackSelection.Definition definition = definitionArr[i2];
            if (definition != null) {
                int[] iArr = definition.tracks;
                if (iArr.length <= 1 || z2) {
                    exoTrackSelectionArr[i2] = new FixedTrackSelection(definition.group, iArr[0], definition.type);
                } else {
                    exoTrackSelectionArr[i2] = adaptiveTrackSelectionFactory.createAdaptiveTrackSelection(definition);
                    z2 = true;
                }
            }
        }
        return exoTrackSelectionArr;
    }

    @Deprecated
    public static DefaultTrackSelector.Parameters updateParametersWithOverride(DefaultTrackSelector.Parameters parameters, int i2, TrackGroupArray trackGroupArray, boolean z2, @Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        DefaultTrackSelector.Parameters.Builder rendererDisabled = parameters.buildUpon().clearSelectionOverrides(i2).setRendererDisabled(i2, z2);
        if (selectionOverride != null) {
            rendererDisabled.setSelectionOverride(i2, trackGroupArray, selectionOverride);
        }
        return rendererDisabled.build();
    }

    public static Tracks buildTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, List<? extends TrackSelection>[] listArr) {
        boolean z2;
        ImmutableList.Builder builder = new ImmutableList.Builder();
        for (int i2 = 0; i2 < mappedTrackInfo.getRendererCount(); i2++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i2);
            List<? extends TrackSelection> list = listArr[i2];
            for (int i3 = 0; i3 < trackGroups.length; i3++) {
                TrackGroup trackGroup = trackGroups.get(i3);
                boolean z3 = mappedTrackInfo.getAdaptiveSupport(i2, i3, false) != 0;
                int i4 = trackGroup.length;
                int[] iArr = new int[i4];
                boolean[] zArr = new boolean[i4];
                for (int i5 = 0; i5 < trackGroup.length; i5++) {
                    iArr[i5] = mappedTrackInfo.getTrackSupport(i2, i3, i5);
                    int i6 = 0;
                    while (true) {
                        if (i6 >= list.size()) {
                            z2 = false;
                            break;
                        }
                        TrackSelection trackSelection = list.get(i6);
                        if (trackSelection.getTrackGroup().equals(trackGroup) && trackSelection.indexOf(i5) != -1) {
                            z2 = true;
                            break;
                        }
                        i6++;
                    }
                    zArr[i5] = z2;
                }
                builder.add((ImmutableList.Builder) new Tracks.Group(trackGroup, z3, iArr, zArr));
            }
        }
        TrackGroupArray unmappedTrackGroups = mappedTrackInfo.getUnmappedTrackGroups();
        for (int i7 = 0; i7 < unmappedTrackGroups.length; i7++) {
            TrackGroup trackGroup2 = unmappedTrackGroups.get(i7);
            int[] iArr2 = new int[trackGroup2.length];
            Arrays.fill(iArr2, 0);
            builder.add((ImmutableList.Builder) new Tracks.Group(trackGroup2, false, iArr2, new boolean[trackGroup2.length]));
        }
        return new Tracks(builder.build());
    }
}
