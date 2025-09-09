package androidx.media3.exoplayer.audio;

import android.media.AudioDeviceInfo;
import androidx.media3.common.Format;
import androidx.media3.common.util.Clock;
import androidx.media3.exoplayer.analytics.PlayerId;

/* loaded from: classes.dex */
public abstract /* synthetic */ class u {
    public static AudioOffloadSupport a(AudioSink audioSink, Format format) {
        return AudioOffloadSupport.DEFAULT_UNSUPPORTED;
    }

    public static void b(AudioSink audioSink) {
    }

    public static void c(AudioSink audioSink, Clock clock) {
    }

    public static void e(AudioSink audioSink, int i2) {
    }

    public static void f(AudioSink audioSink, long j2) {
    }

    public static void g(AudioSink audioSink, PlayerId playerId) {
    }

    public static void h(AudioSink audioSink, AudioDeviceInfo audioDeviceInfo) {
    }

    public static void d(AudioSink audioSink, int i2, int i3) {
    }
}
