package xyz.luan.audioplayers.player;

import android.media.SoundPool;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;
import xyz.luan.audioplayers.source.Source;
import xyz.luan.audioplayers.source.UrlSource;

@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010$\u001a\u0004\u0018\u00010%H\u0016J\n\u0010&\u001a\u0004\u0018\u00010%H\u0016J\b\u0010'\u001a\u00020(H\u0016J\b\u0010)\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020*H\u0016J\b\u0010,\u001a\u00020*H\u0016J\b\u0010-\u001a\u00020*H\u0016J\u0010\u0010.\u001a\u00020*2\u0006\u0010/\u001a\u00020\u000fH\u0016J\u0010\u00100\u001a\u00020*2\u0006\u00101\u001a\u00020(H\u0016J\u0010\u00102\u001a\u00020*2\u0006\u00103\u001a\u000204H\u0016J\u0010\u00105\u001a\u00020*2\u0006\u00106\u001a\u000207H\u0016J\u0018\u00108\u001a\u00020*2\u0006\u00109\u001a\u0002042\u0006\u0010:\u001a\u000204H\u0016J\b\u0010;\u001a\u00020*H\u0016J\b\u0010<\u001a\u00020*H\u0016J\u0010\u0010=\u001a\u00020%2\u0006\u0010>\u001a\u00020?H\u0002J\u0010\u0010@\u001a\u00020*2\u0006\u0010A\u001a\u00020\bH\u0016J\f\u0010B\u001a\u00020\u000f*\u00020(H\u0002R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0082\u000e¢\u0006\b\n\u0000\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u0014\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0014R(\u0010\u001d\u001a\u0004\u0018\u00010\u001c2\b\u0010\u0007\u001a\u0004\u0018\u00010\u001c@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#¨\u0006C"}, d2 = {"Lxyz/luan/audioplayers/player/SoundPoolPlayer;", "Lxyz/luan/audioplayers/player/PlayerWrapper;", "wrappedPlayer", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "soundPoolManager", "Lxyz/luan/audioplayers/player/SoundPoolManager;", "(Lxyz/luan/audioplayers/player/WrappedPlayer;Lxyz/luan/audioplayers/player/SoundPoolManager;)V", "value", "Lxyz/luan/audioplayers/AudioContextAndroid;", "audioContext", "setAudioContext", "(Lxyz/luan/audioplayers/AudioContextAndroid;)V", "mainScope", "Lkotlinx/coroutines/CoroutineScope;", "soundId", "", "getSoundId", "()Ljava/lang/Integer;", "setSoundId", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "soundPool", "Landroid/media/SoundPool;", "getSoundPool", "()Landroid/media/SoundPool;", "soundPoolWrapper", "Lxyz/luan/audioplayers/player/SoundPoolWrapper;", "streamId", "Lxyz/luan/audioplayers/source/UrlSource;", "urlSource", "getUrlSource", "()Lxyz/luan/audioplayers/source/UrlSource;", "setUrlSource", "(Lxyz/luan/audioplayers/source/UrlSource;)V", "getWrappedPlayer", "()Lxyz/luan/audioplayers/player/WrappedPlayer;", "getCurrentPosition", "", "getDuration", "isLiveStream", "", "pause", "", "prepare", "release", "reset", "seekTo", RequestParameters.POSITION, "setLooping", "looping", "setRate", "rate", "", "setSource", "source", "Lxyz/luan/audioplayers/source/Source;", "setVolume", "leftVolume", "rightVolume", "start", "stop", "unsupportedOperation", "message", "", "updateContext", com.umeng.analytics.pro.f.X, "loopModeInteger", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSoundPoolPlayer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SoundPoolPlayer.kt\nxyz/luan/audioplayers/player/SoundPoolPlayer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,308:1\n1#2:309\n357#3,7:310\n*S KotlinDebug\n*F\n+ 1 SoundPoolPlayer.kt\nxyz/luan/audioplayers/player/SoundPoolPlayer\n*L\n101#1:310,7\n*E\n"})
/* loaded from: classes5.dex */
public final class SoundPoolPlayer implements PlayerWrapper {

    @NotNull
    private AudioContextAndroid audioContext;

    @NotNull
    private final CoroutineScope mainScope;

    @Nullable
    private Integer soundId;

    @NotNull
    private final SoundPoolManager soundPoolManager;

    @NotNull
    private SoundPoolWrapper soundPoolWrapper;

    @Nullable
    private Integer streamId;

    @Nullable
    private UrlSource urlSource;

    @NotNull
    private final WrappedPlayer wrappedPlayer;

    public SoundPoolPlayer(@NotNull WrappedPlayer wrappedPlayer, @NotNull SoundPoolManager soundPoolManager) {
        Intrinsics.checkNotNullParameter(wrappedPlayer, "wrappedPlayer");
        Intrinsics.checkNotNullParameter(soundPoolManager, "soundPoolManager");
        this.wrappedPlayer = wrappedPlayer;
        this.soundPoolManager = soundPoolManager;
        this.mainScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
        AudioContextAndroid context = wrappedPlayer.getContext();
        this.audioContext = context;
        soundPoolManager.createSoundPoolWrapper(32, context);
        SoundPoolWrapper soundPoolWrapper = soundPoolManager.getSoundPoolWrapper(this.audioContext);
        if (soundPoolWrapper != null) {
            this.soundPoolWrapper = soundPoolWrapper;
            return;
        }
        throw new IllegalStateException(("Could not create SoundPool " + this.audioContext).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SoundPool getSoundPool() {
        return this.soundPoolWrapper.getSoundPool();
    }

    private final int loopModeInteger(boolean z2) {
        return z2 ? -1 : 0;
    }

    private final void setAudioContext(AudioContextAndroid audioContextAndroid) {
        if (!Intrinsics.areEqual(this.audioContext.buildAttributes(), audioContextAndroid.buildAttributes())) {
            release();
            this.soundPoolManager.createSoundPoolWrapper(32, audioContextAndroid);
            SoundPoolWrapper soundPoolWrapper = this.soundPoolManager.getSoundPoolWrapper(audioContextAndroid);
            if (soundPoolWrapper == null) {
                throw new IllegalStateException(("Could not create SoundPool " + audioContextAndroid).toString());
            }
            this.soundPoolWrapper = soundPoolWrapper;
        }
        this.audioContext = audioContextAndroid;
    }

    private final Void unsupportedOperation(String message) {
        throw new UnsupportedOperationException("LOW_LATENCY mode does not support: " + message);
    }

    @Nullable
    /* renamed from: getCurrentPosition, reason: collision with other method in class */
    public Void m2396getCurrentPosition() {
        return null;
    }

    @Nullable
    /* renamed from: getDuration, reason: collision with other method in class */
    public Void m2397getDuration() {
        return null;
    }

    @Nullable
    public final Integer getSoundId() {
        return this.soundId;
    }

    @Nullable
    public final UrlSource getUrlSource() {
        return this.urlSource;
    }

    @NotNull
    public final WrappedPlayer getWrappedPlayer() {
        return this.wrappedPlayer;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public boolean isLiveStream() {
        return false;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void pause() {
        Integer num = this.streamId;
        if (num != null) {
            getSoundPool().pause(num.intValue());
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void prepare() {
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void release() {
        stop();
        Integer num = this.soundId;
        if (num != null) {
            int iIntValue = num.intValue();
            UrlSource urlSource = this.urlSource;
            if (urlSource == null) {
                return;
            }
            synchronized (this.soundPoolWrapper.getUrlToPlayers()) {
                try {
                    List<SoundPoolPlayer> list = this.soundPoolWrapper.getUrlToPlayers().get(urlSource);
                    if (list == null) {
                        return;
                    }
                    if (CollectionsKt.singleOrNull((List) list) == this) {
                        this.soundPoolWrapper.getUrlToPlayers().remove(urlSource);
                        getSoundPool().unload(iIntValue);
                        this.soundPoolWrapper.getSoundIdToPlayer().remove(num);
                        this.wrappedPlayer.handleLog("unloaded soundId " + iIntValue);
                    } else {
                        list.remove(this);
                    }
                    this.soundId = null;
                    setUrlSource(null);
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void reset() {
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void seekTo(int position) {
        if (position != 0) {
            unsupportedOperation("seek");
            throw new KotlinNothingValueException();
        }
        Integer num = this.streamId;
        if (num != null) {
            int iIntValue = num.intValue();
            stop();
            if (this.wrappedPlayer.getPlaying()) {
                getSoundPool().resume(iIntValue);
            }
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setLooping(boolean looping) {
        Integer num = this.streamId;
        if (num != null) {
            getSoundPool().setLoop(num.intValue(), loopModeInteger(looping));
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setRate(float rate) {
        Integer num = this.streamId;
        if (num != null) {
            getSoundPool().setRate(num.intValue(), rate);
        }
    }

    public final void setSoundId(@Nullable Integer num) {
        this.soundId = num;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setSource(@NotNull Source source) {
        Intrinsics.checkNotNullParameter(source, "source");
        source.setForSoundPool(this);
    }

    public final void setUrlSource(@Nullable UrlSource urlSource) {
        if (urlSource != null) {
            synchronized (this.soundPoolWrapper.getUrlToPlayers()) {
                try {
                    Map<UrlSource, List<SoundPoolPlayer>> urlToPlayers = this.soundPoolWrapper.getUrlToPlayers();
                    List<SoundPoolPlayer> arrayList = urlToPlayers.get(urlSource);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                        urlToPlayers.put(urlSource, arrayList);
                    }
                    List<SoundPoolPlayer> list = arrayList;
                    SoundPoolPlayer soundPoolPlayer = (SoundPoolPlayer) CollectionsKt.firstOrNull((List) list);
                    if (soundPoolPlayer != null) {
                        boolean prepared = soundPoolPlayer.wrappedPlayer.getPrepared();
                        this.wrappedPlayer.setPrepared(prepared);
                        Integer num = soundPoolPlayer.soundId;
                        this.soundId = num;
                        this.wrappedPlayer.handleLog("Reusing soundId " + num + " for " + urlSource + " is prepared=" + prepared + " " + this);
                    } else {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        this.wrappedPlayer.setPrepared(false);
                        this.wrappedPlayer.handleLog("Fetching actual URL for " + urlSource);
                        BuildersKt__Builders_commonKt.launch$default(this.mainScope, Dispatchers.getIO(), null, new SoundPoolPlayer$urlSource$1$1(urlSource, this, this, jCurrentTimeMillis, null), 2, null);
                    }
                    list.add(this);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        this.urlSource = urlSource;
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void setVolume(float leftVolume, float rightVolume) {
        Integer num = this.streamId;
        if (num != null) {
            getSoundPool().setVolume(num.intValue(), leftVolume, rightVolume);
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void start() {
        Integer num = this.streamId;
        Integer num2 = this.soundId;
        if (num != null) {
            getSoundPool().resume(num.intValue());
        } else if (num2 != null) {
            this.streamId = Integer.valueOf(getSoundPool().play(num2.intValue(), this.wrappedPlayer.getVolume(), this.wrappedPlayer.getVolume(), 0, loopModeInteger(this.wrappedPlayer.isLooping()), this.wrappedPlayer.getRate()));
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void stop() {
        Integer num = this.streamId;
        if (num != null) {
            getSoundPool().stop(num.intValue());
            this.streamId = null;
        }
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public void updateContext(@NotNull AudioContextAndroid context) {
        Intrinsics.checkNotNullParameter(context, "context");
        setAudioContext(context);
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public /* bridge */ /* synthetic */ Integer getCurrentPosition() {
        return (Integer) m2396getCurrentPosition();
    }

    @Override // xyz.luan.audioplayers.player.PlayerWrapper
    public /* bridge */ /* synthetic */ Integer getDuration() {
        return (Integer) m2397getDuration();
    }
}
