package androidx.media3.exoplayer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.audio.AudioRendererEventListener;
import androidx.media3.exoplayer.audio.AudioSink;
import androidx.media3.exoplayer.audio.DefaultAudioSink;
import androidx.media3.exoplayer.audio.MediaCodecAudioRenderer;
import androidx.media3.exoplayer.image.ImageDecoder;
import androidx.media3.exoplayer.image.ImageRenderer;
import androidx.media3.exoplayer.mediacodec.DefaultMediaCodecAdapterFactory;
import androidx.media3.exoplayer.mediacodec.MediaCodecAdapter;
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector;
import androidx.media3.exoplayer.metadata.MetadataOutput;
import androidx.media3.exoplayer.metadata.MetadataRenderer;
import androidx.media3.exoplayer.text.TextOutput;
import androidx.media3.exoplayer.text.TextRenderer;
import androidx.media3.exoplayer.video.MediaCodecVideoRenderer;
import androidx.media3.exoplayer.video.VideoRendererEventListener;
import androidx.media3.exoplayer.video.spherical.CameraMotionRenderer;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;

@UnstableApi
/* loaded from: classes.dex */
public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    public static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private final DefaultMediaCodecAdapterFactory codecAdapterFactory;
    private final Context context;
    private boolean enableAudioTrackPlaybackParams;
    private boolean enableDecoderFallback;
    private boolean enableFloatOutput;
    private int extensionRendererMode = 0;
    private long allowedVideoJoiningTimeMs = 5000;
    private MediaCodecSelector mediaCodecSelector = MediaCodecSelector.DEFAULT;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    public DefaultRenderersFactory(Context context) {
        this.context = context;
        this.codecAdapterFactory = new DefaultMediaCodecAdapterFactory(context);
    }

    protected void a(Context context, int i2, MediaCodecSelector mediaCodecSelector, boolean z2, AudioSink audioSink, Handler handler, AudioRendererEventListener audioRendererEventListener, ArrayList arrayList) {
        String str;
        int i3;
        int i4;
        int i5;
        arrayList.add(new MediaCodecAudioRenderer(context, i(), mediaCodecSelector, z2, handler, audioRendererEventListener, audioSink));
        if (i2 == 0) {
            return;
        }
        int size = arrayList.size();
        if (i2 == 2) {
            size--;
        }
        try {
            try {
                i3 = size + 1;
                try {
                    arrayList.add(size, (Renderer) Class.forName("androidx.media3.decoder.midi.MidiRenderer").getConstructor(Context.class).newInstance(context));
                    str = TAG;
                } catch (ClassNotFoundException unused) {
                    str = TAG;
                }
                try {
                    Log.i(str, "Loaded MidiRenderer.");
                } catch (ClassNotFoundException unused2) {
                    size = i3;
                    i3 = size;
                    try {
                        i4 = i3 + 1;
                        try {
                            try {
                                arrayList.add(i3, (Renderer) Class.forName("androidx.media3.decoder.opus.LibopusAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                                Log.i(str, "Loaded LibopusAudioRenderer.");
                            } catch (ClassNotFoundException unused3) {
                                i3 = i4;
                                i4 = i3;
                                i5 = i4 + 1;
                                arrayList.add(i4, (Renderer) Class.forName("androidx.media3.decoder.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                                Log.i(str, "Loaded LibflacAudioRenderer.");
                                arrayList.add(i5, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                                Log.i(str, "Loaded FfmpegAudioRenderer.");
                            }
                            i5 = i4 + 1;
                            arrayList.add(i4, (Renderer) Class.forName("androidx.media3.decoder.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                            Log.i(str, "Loaded LibflacAudioRenderer.");
                            arrayList.add(i5, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                            Log.i(str, "Loaded FfmpegAudioRenderer.");
                        } catch (Exception e2) {
                            throw new RuntimeException("Error instantiating FLAC extension", e2);
                        }
                    } catch (Exception e3) {
                        throw new RuntimeException("Error instantiating Opus extension", e3);
                    }
                }
            } catch (ClassNotFoundException unused4) {
                str = TAG;
            }
            try {
                i4 = i3 + 1;
                arrayList.add(i3, (Renderer) Class.forName("androidx.media3.decoder.opus.LibopusAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(str, "Loaded LibopusAudioRenderer.");
            } catch (ClassNotFoundException unused5) {
            }
            try {
                i5 = i4 + 1;
            } catch (ClassNotFoundException unused6) {
            }
            try {
                try {
                    arrayList.add(i4, (Renderer) Class.forName("androidx.media3.decoder.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                    Log.i(str, "Loaded LibflacAudioRenderer.");
                } catch (ClassNotFoundException unused7) {
                    i4 = i5;
                    i5 = i4;
                    arrayList.add(i5, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                    Log.i(str, "Loaded FfmpegAudioRenderer.");
                }
                arrayList.add(i5, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(str, "Loaded FfmpegAudioRenderer.");
            } catch (ClassNotFoundException unused8) {
            } catch (Exception e4) {
                throw new RuntimeException("Error instantiating FFmpeg extension", e4);
            }
        } catch (Exception e5) {
            throw new RuntimeException("Error instantiating MIDI extension", e5);
        }
    }

    protected AudioSink b(Context context, boolean z2, boolean z3) {
        return new DefaultAudioSink.Builder(context).setEnableFloatOutput(z2).setEnableAudioTrackPlaybackParams(z3).build();
    }

    protected void c(Context context, int i2, ArrayList arrayList) {
        arrayList.add(new CameraMotionRenderer());
    }

    @Override // androidx.media3.exoplayer.RenderersFactory
    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
        ArrayList arrayList = new ArrayList();
        h(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, handler, videoRendererEventListener, this.allowedVideoJoiningTimeMs, arrayList);
        AudioSink audioSinkB = b(this.context, this.enableFloatOutput, this.enableAudioTrackPlaybackParams);
        if (audioSinkB != null) {
            a(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, audioSinkB, handler, audioRendererEventListener, arrayList);
        }
        g(this.context, textOutput, handler.getLooper(), this.extensionRendererMode, arrayList);
        e(this.context, metadataOutput, handler.getLooper(), this.extensionRendererMode, arrayList);
        c(this.context, this.extensionRendererMode, arrayList);
        d(arrayList);
        f(this.context, handler, this.extensionRendererMode, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[0]);
    }

    protected void d(ArrayList arrayList) {
        arrayList.add(new ImageRenderer(ImageDecoder.Factory.DEFAULT, null));
    }

    protected void e(Context context, MetadataOutput metadataOutput, Looper looper, int i2, ArrayList arrayList) {
        arrayList.add(new MetadataRenderer(metadataOutput, looper));
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory experimentalSetMediaCodecAsyncCryptoFlagEnabled(boolean z2) {
        this.codecAdapterFactory.experimentalSetAsyncCryptoFlagEnabled(z2);
        return this;
    }

    protected void f(Context context, Handler handler, int i2, ArrayList arrayList) {
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory forceDisableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceDisableAsynchronous();
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory forceEnableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceEnableAsynchronous();
        return this;
    }

    protected void g(Context context, TextOutput textOutput, Looper looper, int i2, ArrayList arrayList) {
        arrayList.add(new TextRenderer(textOutput, looper));
    }

    protected void h(Context context, int i2, MediaCodecSelector mediaCodecSelector, boolean z2, Handler handler, VideoRendererEventListener videoRendererEventListener, long j2, ArrayList arrayList) {
        String str;
        int i3;
        arrayList.add(new MediaCodecVideoRenderer(context, i(), mediaCodecSelector, j2, z2, handler, videoRendererEventListener, 50));
        if (i2 == 0) {
            return;
        }
        int size = arrayList.size();
        if (i2 == 2) {
            size--;
        }
        try {
            try {
                i3 = size + 1;
                try {
                    arrayList.add(size, (Renderer) Class.forName("androidx.media3.decoder.vp9.LibvpxVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                    str = TAG;
                    try {
                        Log.i(str, "Loaded LibvpxVideoRenderer.");
                    } catch (ClassNotFoundException unused) {
                        size = i3;
                        i3 = size;
                        try {
                            int i4 = i3 + 1;
                            try {
                                arrayList.add(i3, (Renderer) Class.forName("androidx.media3.decoder.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                                Log.i(str, "Loaded Libgav1VideoRenderer.");
                            } catch (ClassNotFoundException unused2) {
                                i3 = i4;
                                i4 = i3;
                                arrayList.add(i4, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.ExperimentalFfmpegVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                                Log.i(str, "Loaded FfmpegVideoRenderer.");
                            }
                            arrayList.add(i4, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.ExperimentalFfmpegVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                            Log.i(str, "Loaded FfmpegVideoRenderer.");
                        } catch (Exception e2) {
                            throw new RuntimeException("Error instantiating AV1 extension", e2);
                        }
                    }
                } catch (ClassNotFoundException unused3) {
                    str = TAG;
                }
            } catch (Exception e3) {
                throw new RuntimeException("Error instantiating VP9 extension", e3);
            }
        } catch (ClassNotFoundException unused4) {
            str = TAG;
        }
        try {
            int i42 = i3 + 1;
            arrayList.add(i3, (Renderer) Class.forName("androidx.media3.decoder.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
            Log.i(str, "Loaded Libgav1VideoRenderer.");
        } catch (ClassNotFoundException unused5) {
        }
        try {
            arrayList.add(i42, (Renderer) Class.forName("androidx.media3.decoder.ffmpeg.ExperimentalFfmpegVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
            Log.i(str, "Loaded FfmpegVideoRenderer.");
        } catch (ClassNotFoundException unused6) {
        } catch (Exception e4) {
            throw new RuntimeException("Error instantiating FFmpeg extension", e4);
        }
    }

    protected MediaCodecAdapter.Factory i() {
        return this.codecAdapterFactory;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long j2) {
        this.allowedVideoJoiningTimeMs = j2;
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setEnableAudioFloatOutput(boolean z2) {
        this.enableFloatOutput = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean z2) {
        this.enableAudioTrackPlaybackParams = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setEnableDecoderFallback(boolean z2) {
        this.enableDecoderFallback = z2;
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setExtensionRendererMode(int i2) {
        this.extensionRendererMode = i2;
        return this;
    }

    @CanIgnoreReturnValue
    public final DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.mediaCodecSelector = mediaCodecSelector;
        return this;
    }
}
