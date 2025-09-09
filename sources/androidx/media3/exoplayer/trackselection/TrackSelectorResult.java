package androidx.media3.exoplayer.trackselection;

import androidx.annotation.Nullable;
import androidx.media3.common.Tracks;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.RendererConfiguration;

@UnstableApi
/* loaded from: classes2.dex */
public final class TrackSelectorResult {

    /* renamed from: info, reason: collision with root package name */
    @Nullable
    public final Object f5508info;
    public final int length;
    public final RendererConfiguration[] rendererConfigurations;
    public final ExoTrackSelection[] selections;
    public final Tracks tracks;

    @Deprecated
    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, @Nullable Object obj) {
        this(rendererConfigurationArr, exoTrackSelectionArr, Tracks.EMPTY, obj);
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult) {
        if (trackSelectorResult == null || trackSelectorResult.selections.length != this.selections.length) {
            return false;
        }
        for (int i2 = 0; i2 < this.selections.length; i2++) {
            if (!isEquivalent(trackSelectorResult, i2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRendererEnabled(int i2) {
        return this.rendererConfigurations[i2] != null;
    }

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, Tracks tracks, @Nullable Object obj) {
        Assertions.checkArgument(rendererConfigurationArr.length == exoTrackSelectionArr.length);
        this.rendererConfigurations = rendererConfigurationArr;
        this.selections = (ExoTrackSelection[]) exoTrackSelectionArr.clone();
        this.tracks = tracks;
        this.f5508info = obj;
        this.length = rendererConfigurationArr.length;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult, int i2) {
        return trackSelectorResult != null && Util.areEqual(this.rendererConfigurations[i2], trackSelectorResult.rendererConfigurations[i2]) && Util.areEqual(this.selections[i2], trackSelectorResult.selections[i2]);
    }
}
