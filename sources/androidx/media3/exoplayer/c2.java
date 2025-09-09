package androidx.media3.exoplayer;

import androidx.media3.common.Timeline;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.ExoTrackSelection;

/* loaded from: classes.dex */
public abstract /* synthetic */ class c2 {
    public static long a(LoadControl loadControl) {
        throw new IllegalStateException("getBackBufferDurationUs not implemented");
    }

    public static long b(LoadControl loadControl, PlayerId playerId) {
        return loadControl.getBackBufferDurationUs();
    }

    public static void c(LoadControl loadControl) {
        throw new IllegalStateException("onPrepared not implemented");
    }

    public static void d(LoadControl loadControl, PlayerId playerId) {
        loadControl.onPrepared();
    }

    public static void e(LoadControl loadControl) {
        throw new IllegalStateException("onReleased not implemented");
    }

    public static void f(LoadControl loadControl, PlayerId playerId) {
        loadControl.onReleased();
    }

    public static void g(LoadControl loadControl) {
        throw new IllegalStateException("onStopped not implemented");
    }

    public static void h(LoadControl loadControl, PlayerId playerId) {
        loadControl.onStopped();
    }

    public static void i(LoadControl loadControl, Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        loadControl.onTracksSelected(rendererArr, trackGroupArray, exoTrackSelectionArr);
    }

    public static void j(LoadControl loadControl, PlayerId playerId, Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        loadControl.onTracksSelected(timeline, mediaPeriodId, rendererArr, trackGroupArray, exoTrackSelectionArr);
    }

    public static void k(LoadControl loadControl, Renderer[] rendererArr, TrackGroupArray trackGroupArray, ExoTrackSelection[] exoTrackSelectionArr) {
        throw new IllegalStateException("onTracksSelected not implemented");
    }

    public static boolean l(LoadControl loadControl) {
        throw new IllegalStateException("retainBackBufferFromKeyframe not implemented");
    }

    public static boolean m(LoadControl loadControl, PlayerId playerId) {
        return loadControl.retainBackBufferFromKeyframe();
    }

    public static boolean n(LoadControl loadControl, long j2, long j3, float f2) {
        throw new IllegalStateException("shouldContinueLoading not implemented");
    }

    public static boolean o(LoadControl loadControl, LoadControl.Parameters parameters) {
        return loadControl.shouldContinueLoading(parameters.playbackPositionUs, parameters.bufferedDurationUs, parameters.playbackSpeed);
    }

    public static boolean p(LoadControl loadControl, long j2, float f2, boolean z2, long j3) {
        throw new IllegalStateException("shouldStartPlayback not implemented");
    }

    public static boolean q(LoadControl loadControl, Timeline timeline, MediaSource.MediaPeriodId mediaPeriodId, long j2, float f2, boolean z2, long j3) {
        return loadControl.shouldStartPlayback(j2, f2, z2, j3);
    }

    public static boolean r(LoadControl loadControl, LoadControl.Parameters parameters) {
        return loadControl.shouldStartPlayback(parameters.timeline, parameters.mediaPeriodId, parameters.bufferedDurationUs, parameters.playbackSpeed, parameters.rebuffering, parameters.targetLiveOffsetUs);
    }
}
