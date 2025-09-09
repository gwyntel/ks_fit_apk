package androidx.media3.exoplayer.audio;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.audio.AudioOffloadSupport;
import androidx.media3.exoplayer.audio.DefaultAudioSink;

@UnstableApi
/* loaded from: classes.dex */
public final class DefaultAudioOffloadSupportProvider implements DefaultAudioSink.AudioOffloadSupportProvider {
    private static final String OFFLOAD_VARIABLE_RATE_SUPPORTED_KEY = "offloadVariableRateSupported";

    @Nullable
    private final Context context;
    private Boolean isOffloadVariableRateSupported;

    @RequiresApi(29)
    private static final class Api29 {
        private Api29() {
        }

        @DoNotInline
        public static AudioOffloadSupport getOffloadedPlaybackSupport(AudioFormat audioFormat, AudioAttributes audioAttributes, boolean z2) {
            return !AudioManager.isOffloadedPlaybackSupported(audioFormat, audioAttributes) ? AudioOffloadSupport.DEFAULT_UNSUPPORTED : new AudioOffloadSupport.Builder().setIsFormatSupported(true).setIsSpeedChangeSupported(z2).build();
        }
    }

    @RequiresApi(31)
    private static final class Api31 {
        private Api31() {
        }

        @DoNotInline
        public static AudioOffloadSupport getOffloadedPlaybackSupport(AudioFormat audioFormat, AudioAttributes audioAttributes, boolean z2) {
            int playbackOffloadSupport = AudioManager.getPlaybackOffloadSupport(audioFormat, audioAttributes);
            if (playbackOffloadSupport == 0) {
                return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
            }
            return new AudioOffloadSupport.Builder().setIsFormatSupported(true).setIsGaplessSupported(Util.SDK_INT > 32 && playbackOffloadSupport == 2).setIsSpeedChangeSupported(z2).build();
        }
    }

    public DefaultAudioOffloadSupportProvider() {
        this(null);
    }

    private boolean isOffloadVariableRateSupported(@Nullable Context context) {
        AudioManager audioManager;
        Boolean bool = this.isOffloadVariableRateSupported;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (context == null || (audioManager = (AudioManager) context.getSystemService("audio")) == null) {
            this.isOffloadVariableRateSupported = Boolean.FALSE;
        } else {
            String parameters = audioManager.getParameters(OFFLOAD_VARIABLE_RATE_SUPPORTED_KEY);
            this.isOffloadVariableRateSupported = Boolean.valueOf(parameters != null && parameters.equals("offloadVariableRateSupported=1"));
        }
        return this.isOffloadVariableRateSupported.booleanValue();
    }

    @Override // androidx.media3.exoplayer.audio.DefaultAudioSink.AudioOffloadSupportProvider
    public AudioOffloadSupport getAudioOffloadSupport(Format format, androidx.media3.common.AudioAttributes audioAttributes) {
        Assertions.checkNotNull(format);
        Assertions.checkNotNull(audioAttributes);
        int i2 = Util.SDK_INT;
        if (i2 < 29 || format.sampleRate == -1) {
            return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
        }
        boolean zIsOffloadVariableRateSupported = isOffloadVariableRateSupported(this.context);
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        if (encoding == 0 || i2 < Util.getApiLevelThatAudioFormatIntroducedAudioEncoding(encoding)) {
            return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
        }
        int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(format.channelCount);
        if (audioTrackChannelConfig == 0) {
            return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
        }
        try {
            AudioFormat audioFormat = Util.getAudioFormat(format.sampleRate, audioTrackChannelConfig, encoding);
            return i2 >= 31 ? Api31.getOffloadedPlaybackSupport(audioFormat, audioAttributes.getAudioAttributesV21().audioAttributes, zIsOffloadVariableRateSupported) : Api29.getOffloadedPlaybackSupport(audioFormat, audioAttributes.getAudioAttributesV21().audioAttributes, zIsOffloadVariableRateSupported);
        } catch (IllegalArgumentException unused) {
            return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
        }
    }

    public DefaultAudioOffloadSupportProvider(@Nullable Context context) {
        this.context = context;
    }
}
