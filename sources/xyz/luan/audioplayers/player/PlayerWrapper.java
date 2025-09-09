package xyz.luan.audioplayers.player;

import com.alibaba.sdk.android.oss.common.RequestParameters;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;
import xyz.luan.audioplayers.source.Source;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0002\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004J\u000f\u0010\u0005\u001a\u0004\u0018\u00010\u0003H&¢\u0006\u0002\u0010\u0004J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&J\b\u0010\n\u001a\u00020\tH&J\b\u0010\u000b\u001a\u00020\tH&J\b\u0010\f\u001a\u00020\tH&J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0007H&J\u0010\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0018\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0013H&J\b\u0010\u001a\u001a\u00020\tH&J\b\u0010\u001b\u001a\u00020\tH&J\u0010\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001eH&¨\u0006\u001f"}, d2 = {"Lxyz/luan/audioplayers/player/PlayerWrapper;", "", "getCurrentPosition", "", "()Ljava/lang/Integer;", "getDuration", "isLiveStream", "", "pause", "", "prepare", "release", "reset", "seekTo", RequestParameters.POSITION, "setLooping", "looping", "setRate", "rate", "", "setSource", "source", "Lxyz/luan/audioplayers/source/Source;", "setVolume", "leftVolume", "rightVolume", "start", "stop", "updateContext", com.umeng.analytics.pro.f.X, "Lxyz/luan/audioplayers/AudioContextAndroid;", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public interface PlayerWrapper {
    @Nullable
    Integer getCurrentPosition();

    @Nullable
    Integer getDuration();

    boolean isLiveStream();

    void pause();

    void prepare();

    void release();

    void reset();

    void seekTo(int position);

    void setLooping(boolean looping);

    void setRate(float rate);

    void setSource(@NotNull Source source);

    void setVolume(float leftVolume, float rightVolume);

    void start();

    void stop();

    void updateContext(@NotNull AudioContextAndroid context);
}
