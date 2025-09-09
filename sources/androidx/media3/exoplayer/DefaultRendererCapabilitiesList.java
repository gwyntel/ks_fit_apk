package androidx.media3.exoplayer;

import android.content.Context;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.VideoSize;
import androidx.media3.common.text.CueGroup;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.DefaultRendererCapabilitiesList;
import androidx.media3.exoplayer.RendererCapabilitiesList;
import androidx.media3.exoplayer.analytics.PlayerId;
import androidx.media3.exoplayer.audio.AudioRendererEventListener;
import androidx.media3.exoplayer.audio.AudioSink;
import androidx.media3.exoplayer.metadata.MetadataOutput;
import androidx.media3.exoplayer.text.TextOutput;
import androidx.media3.exoplayer.video.VideoRendererEventListener;
import java.util.Arrays;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public final class DefaultRendererCapabilitiesList implements RendererCapabilitiesList {
    private final Renderer[] renderers;

    public static final class Factory implements RendererCapabilitiesList.Factory {
        private final RenderersFactory renderersFactory;

        public Factory(Context context) {
            this.renderersFactory = new DefaultRenderersFactory(context);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$createRendererCapabilitiesList$0(CueGroup cueGroup) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$createRendererCapabilitiesList$1(Metadata metadata) {
        }

        @Override // androidx.media3.exoplayer.RendererCapabilitiesList.Factory
        public DefaultRendererCapabilitiesList createRendererCapabilitiesList() {
            return new DefaultRendererCapabilitiesList(this.renderersFactory.createRenderers(Util.createHandlerForCurrentOrMainLooper(), new VideoRendererEventListener() { // from class: androidx.media3.exoplayer.DefaultRendererCapabilitiesList.Factory.1
                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onDroppedFrames(int i2, long j2) {
                    androidx.media3.exoplayer.video.m.a(this, i2, j2);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onRenderedFirstFrame(Object obj, long j2) {
                    androidx.media3.exoplayer.video.m.b(this, obj, j2);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoCodecError(Exception exc) {
                    androidx.media3.exoplayer.video.m.c(this, exc);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoDecoderInitialized(String str, long j2, long j3) {
                    androidx.media3.exoplayer.video.m.d(this, str, j2, j3);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoDecoderReleased(String str) {
                    androidx.media3.exoplayer.video.m.e(this, str);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoDisabled(DecoderCounters decoderCounters) {
                    androidx.media3.exoplayer.video.m.f(this, decoderCounters);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoEnabled(DecoderCounters decoderCounters) {
                    androidx.media3.exoplayer.video.m.g(this, decoderCounters);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoFrameProcessingOffset(long j2, int i2) {
                    androidx.media3.exoplayer.video.m.h(this, j2, i2);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                    androidx.media3.exoplayer.video.m.i(this, format, decoderReuseEvaluation);
                }

                @Override // androidx.media3.exoplayer.video.VideoRendererEventListener
                public /* synthetic */ void onVideoSizeChanged(VideoSize videoSize) {
                    androidx.media3.exoplayer.video.m.j(this, videoSize);
                }
            }, new AudioRendererEventListener() { // from class: androidx.media3.exoplayer.DefaultRendererCapabilitiesList.Factory.2
                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioCodecError(Exception exc) {
                    androidx.media3.exoplayer.audio.h.a(this, exc);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioDecoderInitialized(String str, long j2, long j3) {
                    androidx.media3.exoplayer.audio.h.b(this, str, j2, j3);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioDecoderReleased(String str) {
                    androidx.media3.exoplayer.audio.h.c(this, str);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioDisabled(DecoderCounters decoderCounters) {
                    androidx.media3.exoplayer.audio.h.d(this, decoderCounters);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioEnabled(DecoderCounters decoderCounters) {
                    androidx.media3.exoplayer.audio.h.e(this, decoderCounters);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioInputFormatChanged(Format format, DecoderReuseEvaluation decoderReuseEvaluation) {
                    androidx.media3.exoplayer.audio.h.f(this, format, decoderReuseEvaluation);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioPositionAdvancing(long j2) {
                    androidx.media3.exoplayer.audio.h.g(this, j2);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioSinkError(Exception exc) {
                    androidx.media3.exoplayer.audio.h.h(this, exc);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioTrackInitialized(AudioSink.AudioTrackConfig audioTrackConfig) {
                    androidx.media3.exoplayer.audio.h.i(this, audioTrackConfig);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioTrackReleased(AudioSink.AudioTrackConfig audioTrackConfig) {
                    androidx.media3.exoplayer.audio.h.j(this, audioTrackConfig);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onAudioUnderrun(int i2, long j2, long j3) {
                    androidx.media3.exoplayer.audio.h.k(this, i2, j2, j3);
                }

                @Override // androidx.media3.exoplayer.audio.AudioRendererEventListener
                public /* synthetic */ void onSkipSilenceEnabledChanged(boolean z2) {
                    androidx.media3.exoplayer.audio.h.l(this, z2);
                }
            }, new TextOutput() { // from class: androidx.media3.exoplayer.k
                @Override // androidx.media3.exoplayer.text.TextOutput
                public final void onCues(CueGroup cueGroup) {
                    DefaultRendererCapabilitiesList.Factory.lambda$createRendererCapabilitiesList$0(cueGroup);
                }

                @Override // androidx.media3.exoplayer.text.TextOutput
                public /* synthetic */ void onCues(List list) {
                    androidx.media3.exoplayer.text.c.a(this, list);
                }
            }, new MetadataOutput() { // from class: androidx.media3.exoplayer.l
                @Override // androidx.media3.exoplayer.metadata.MetadataOutput
                public final void onMetadata(Metadata metadata) {
                    DefaultRendererCapabilitiesList.Factory.lambda$createRendererCapabilitiesList$1(metadata);
                }
            }));
        }

        public Factory(RenderersFactory renderersFactory) {
            this.renderersFactory = renderersFactory;
        }
    }

    @Override // androidx.media3.exoplayer.RendererCapabilitiesList
    public RendererCapabilities[] getRendererCapabilities() {
        RendererCapabilities[] rendererCapabilitiesArr = new RendererCapabilities[this.renderers.length];
        int i2 = 0;
        while (true) {
            Renderer[] rendererArr = this.renderers;
            if (i2 >= rendererArr.length) {
                return rendererCapabilitiesArr;
            }
            rendererCapabilitiesArr[i2] = rendererArr[i2].getCapabilities();
            i2++;
        }
    }

    @Override // androidx.media3.exoplayer.RendererCapabilitiesList
    public void release() {
        for (Renderer renderer : this.renderers) {
            renderer.release();
        }
    }

    @Override // androidx.media3.exoplayer.RendererCapabilitiesList
    public int size() {
        return this.renderers.length;
    }

    private DefaultRendererCapabilitiesList(Renderer[] rendererArr) {
        this.renderers = (Renderer[]) Arrays.copyOf(rendererArr, rendererArr.length);
        for (int i2 = 0; i2 < rendererArr.length; i2++) {
            this.renderers[i2].init(i2, PlayerId.UNSET, Clock.DEFAULT);
        }
    }
}
