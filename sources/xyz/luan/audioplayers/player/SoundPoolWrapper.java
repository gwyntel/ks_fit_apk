package xyz.luan.audioplayers.player;

import android.media.SoundPool;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import xyz.luan.audioplayers.source.UrlSource;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\u0012R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR#\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000f0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u0013"}, d2 = {"Lxyz/luan/audioplayers/player/SoundPoolWrapper;", "", "soundPool", "Landroid/media/SoundPool;", "(Landroid/media/SoundPool;)V", "soundIdToPlayer", "", "", "Lxyz/luan/audioplayers/player/SoundPoolPlayer;", "getSoundIdToPlayer", "()Ljava/util/Map;", "getSoundPool", "()Landroid/media/SoundPool;", "urlToPlayers", "Lxyz/luan/audioplayers/source/UrlSource;", "", "getUrlToPlayers", "dispose", "", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class SoundPoolWrapper {

    @NotNull
    private final Map<Integer, SoundPoolPlayer> soundIdToPlayer;

    @NotNull
    private final SoundPool soundPool;

    @NotNull
    private final Map<UrlSource, List<SoundPoolPlayer>> urlToPlayers;

    public SoundPoolWrapper(@NotNull SoundPool soundPool) {
        Intrinsics.checkNotNullParameter(soundPool, "soundPool");
        this.soundPool = soundPool;
        Map<Integer, SoundPoolPlayer> mapSynchronizedMap = Collections.synchronizedMap(new LinkedHashMap());
        Intrinsics.checkNotNullExpressionValue(mapSynchronizedMap, "synchronizedMap(...)");
        this.soundIdToPlayer = mapSynchronizedMap;
        Map<UrlSource, List<SoundPoolPlayer>> mapSynchronizedMap2 = Collections.synchronizedMap(new LinkedHashMap());
        Intrinsics.checkNotNullExpressionValue(mapSynchronizedMap2, "synchronizedMap(...)");
        this.urlToPlayers = mapSynchronizedMap2;
    }

    public final void dispose() {
        this.soundPool.release();
        this.soundIdToPlayer.clear();
        this.urlToPlayers.clear();
    }

    @NotNull
    public final Map<Integer, SoundPoolPlayer> getSoundIdToPlayer() {
        return this.soundIdToPlayer;
    }

    @NotNull
    public final SoundPool getSoundPool() {
        return this.soundPool;
    }

    @NotNull
    public final Map<UrlSource, List<SoundPoolPlayer>> getUrlToPlayers() {
        return this.urlToPlayers;
    }
}
