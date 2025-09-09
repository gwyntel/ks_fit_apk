package xyz.luan.audioplayers.player;

import android.content.Context;
import android.media.AudioManager;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alipay.sdk.m.u.i;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;
import xyz.luan.audioplayers.AudioplayersPlugin;
import xyz.luan.audioplayers.EventHandler;
import xyz.luan.audioplayers.PlayerMode;
import xyz.luan.audioplayers.ReleaseMode;
import xyz.luan.audioplayers.source.Source;

@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u001a\u0018\u00002\u00020\u0001B'\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010O\u001a\u00020&H\u0002J\u0006\u0010P\u001a\u00020QJ\r\u0010R\u001a\u0004\u0018\u00010A¢\u0006\u0002\u0010SJ\r\u0010T\u001a\u0004\u0018\u00010A¢\u0006\u0002\u0010SJ\b\u0010U\u001a\u00020&H\u0002J$\u0010V\u001a\u00020Q2\b\u0010W\u001a\u0004\u0018\u00010X2\b\u0010Y\u001a\u0004\u0018\u00010X2\b\u0010Z\u001a\u0004\u0018\u00010\u0001J\u000e\u0010[\u001a\u00020Q2\u0006\u0010\\\u001a\u00020XJ\b\u0010]\u001a\u00020QH\u0002J\b\u0010^\u001a\u00020AH\u0002J\u000e\u0010_\u001a\u00020Q2\u0006\u0010`\u001a\u00020AJ\u0006\u0010a\u001a\u00020QJ\u0016\u0010b\u001a\u00020#2\u0006\u0010c\u001a\u00020A2\u0006\u0010d\u001a\u00020AJ\u0006\u0010e\u001a\u00020QJ\u0006\u0010f\u001a\u00020QJ\u0006\u0010g\u001a\u00020QJ\u0006\u0010h\u001a\u00020QJ\u0006\u0010i\u001a\u00020QJ\b\u0010j\u001a\u00020QH\u0002J\u000e\u0010k\u001a\u00020Q2\u0006\u0010l\u001a\u00020AJ\u0006\u0010m\u001a\u00020QJ\u000e\u0010n\u001a\u00020Q2\u0006\u0010o\u001a\u00020\u0007J\f\u0010p\u001a\u00020Q*\u00020&H\u0002J\u001c\u0010q\u001a\u00020Q*\u00020&2\u0006\u0010L\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002R\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\"\u001a\u00020#8F¢\u0006\u0006\u001a\u0004\b\"\u0010$R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010(\u001a\u00020'2\u0006\u0010\u0013\u001a\u00020'@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010$\"\u0004\b/\u00100R$\u00101\u001a\u00020#2\u0006\u0010\u0013\u001a\u00020#@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010$\"\u0004\b3\u00100R$\u00104\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0017\"\u0004\b6\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R$\u00108\u001a\u0002072\u0006\u0010\u0013\u001a\u000207@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u001a\u0010=\u001a\u00020#X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010$\"\u0004\b?\u00100R\u001a\u0010@\u001a\u00020AX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R(\u0010G\u001a\u0004\u0018\u00010F2\b\u0010\u0013\u001a\u0004\u0018\u00010F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR$\u0010L\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\u0017\"\u0004\bN\u0010\u0019¨\u0006r"}, d2 = {"Lxyz/luan/audioplayers/player/WrappedPlayer;", "", "ref", "Lxyz/luan/audioplayers/AudioplayersPlugin;", "eventHandler", "Lxyz/luan/audioplayers/EventHandler;", com.umeng.analytics.pro.f.X, "Lxyz/luan/audioplayers/AudioContextAndroid;", "soundPoolManager", "Lxyz/luan/audioplayers/player/SoundPoolManager;", "(Lxyz/luan/audioplayers/AudioplayersPlugin;Lxyz/luan/audioplayers/EventHandler;Lxyz/luan/audioplayers/AudioContextAndroid;Lxyz/luan/audioplayers/player/SoundPoolManager;)V", "applicationContext", "Landroid/content/Context;", "getApplicationContext", "()Landroid/content/Context;", "audioManager", "Landroid/media/AudioManager;", "getAudioManager", "()Landroid/media/AudioManager;", "value", "", "balance", "getBalance", "()F", "setBalance", "(F)V", "getContext", "()Lxyz/luan/audioplayers/AudioContextAndroid;", "setContext", "(Lxyz/luan/audioplayers/AudioContextAndroid;)V", "getEventHandler", "()Lxyz/luan/audioplayers/EventHandler;", "focusManager", "Lxyz/luan/audioplayers/player/FocusManager;", "isLooping", "", "()Z", "player", "Lxyz/luan/audioplayers/player/PlayerWrapper;", "Lxyz/luan/audioplayers/PlayerMode;", "playerMode", "getPlayerMode", "()Lxyz/luan/audioplayers/PlayerMode;", "setPlayerMode", "(Lxyz/luan/audioplayers/PlayerMode;)V", "playing", "getPlaying", "setPlaying", "(Z)V", "prepared", "getPrepared", "setPrepared", "rate", "getRate", "setRate", "Lxyz/luan/audioplayers/ReleaseMode;", "releaseMode", "getReleaseMode", "()Lxyz/luan/audioplayers/ReleaseMode;", "setReleaseMode", "(Lxyz/luan/audioplayers/ReleaseMode;)V", "released", "getReleased", "setReleased", "shouldSeekTo", "", "getShouldSeekTo", "()I", "setShouldSeekTo", "(I)V", "Lxyz/luan/audioplayers/source/Source;", "source", "getSource", "()Lxyz/luan/audioplayers/source/Source;", "setSource", "(Lxyz/luan/audioplayers/source/Source;)V", "volume", "getVolume", "setVolume", "createPlayer", "dispose", "", "getCurrentPosition", "()Ljava/lang/Integer;", "getDuration", "getOrCreatePlayer", "handleError", "errorCode", "", "errorMessage", "errorDetails", "handleLog", "message", "initPlayer", "maybeGetCurrentPosition", "onBuffering", "percent", "onCompletion", "onError", "what", PushConstants.EXTRA, "onPrepared", "onSeekComplete", "pause", "play", "release", "requestFocusAndStart", "seek", RequestParameters.POSITION, "stop", "updateAudioContext", "audioContext", "configAndPrepare", "setVolumeAndBalance", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nWrappedPlayer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WrappedPlayer.kt\nxyz/luan/audioplayers/player/WrappedPlayer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,396:1\n1#2:397\n*E\n"})
/* loaded from: classes5.dex */
public final class WrappedPlayer {
    private float balance;

    @NotNull
    private AudioContextAndroid context;

    @NotNull
    private final EventHandler eventHandler;

    @NotNull
    private final FocusManager focusManager;

    @Nullable
    private PlayerWrapper player;

    @NotNull
    private PlayerMode playerMode;
    private boolean playing;
    private boolean prepared;
    private float rate;

    @NotNull
    private final AudioplayersPlugin ref;

    @NotNull
    private ReleaseMode releaseMode;
    private boolean released;
    private int shouldSeekTo;

    @NotNull
    private final SoundPoolManager soundPoolManager;

    @Nullable
    private Source source;
    private float volume;

    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PlayerMode.values().length];
            try {
                iArr[PlayerMode.MEDIA_PLAYER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PlayerMode.LOW_LATENCY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public WrappedPlayer(@NotNull AudioplayersPlugin ref, @NotNull EventHandler eventHandler, @NotNull AudioContextAndroid context, @NotNull SoundPoolManager soundPoolManager) {
        Intrinsics.checkNotNullParameter(ref, "ref");
        Intrinsics.checkNotNullParameter(eventHandler, "eventHandler");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(soundPoolManager, "soundPoolManager");
        this.ref = ref;
        this.eventHandler = eventHandler;
        this.context = context;
        this.soundPoolManager = soundPoolManager;
        this.volume = 1.0f;
        this.rate = 1.0f;
        this.releaseMode = ReleaseMode.RELEASE;
        this.playerMode = PlayerMode.MEDIA_PLAYER;
        this.released = true;
        this.shouldSeekTo = -1;
        this.focusManager = FocusManager.INSTANCE.create(this, new Function0<Unit>() { // from class: xyz.luan.audioplayers.player.WrappedPlayer$focusManager$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                PlayerWrapper playerWrapper;
                if (!this.this$0.getPlaying() || (playerWrapper = this.this$0.player) == null) {
                    return;
                }
                playerWrapper.start();
            }
        }, new Function1<Boolean, Unit>() { // from class: xyz.luan.audioplayers.player.WrappedPlayer$focusManager$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z2) {
                if (!z2) {
                    this.this$0.pause();
                    return;
                }
                PlayerWrapper playerWrapper = this.this$0.player;
                if (playerWrapper != null) {
                    playerWrapper.pause();
                }
            }
        });
    }

    private final void configAndPrepare(PlayerWrapper playerWrapper) {
        setVolumeAndBalance(playerWrapper, this.volume, this.balance);
        playerWrapper.setLooping(isLooping());
        playerWrapper.prepare();
    }

    private final PlayerWrapper createPlayer() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.playerMode.ordinal()];
        if (i2 == 1) {
            return new MediaPlayerWrapper(this);
        }
        if (i2 == 2) {
            return new SoundPoolPlayer(this, this.soundPoolManager);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final PlayerWrapper getOrCreatePlayer() {
        PlayerWrapper playerWrapper = this.player;
        if (this.released || playerWrapper == null) {
            PlayerWrapper playerWrapperCreatePlayer = createPlayer();
            this.player = playerWrapperCreatePlayer;
            this.released = false;
            return playerWrapperCreatePlayer;
        }
        if (!this.prepared) {
            return playerWrapper;
        }
        playerWrapper.reset();
        setPrepared(false);
        return playerWrapper;
    }

    private final void initPlayer() {
        PlayerWrapper playerWrapperCreatePlayer = createPlayer();
        this.player = playerWrapperCreatePlayer;
        Source source = this.source;
        if (source != null) {
            playerWrapperCreatePlayer.setSource(source);
            configAndPrepare(playerWrapperCreatePlayer);
        }
    }

    private final int maybeGetCurrentPosition() {
        Object objM837constructorimpl;
        try {
            Result.Companion companion = Result.INSTANCE;
            PlayerWrapper playerWrapper = this.player;
            Integer currentPosition = playerWrapper != null ? playerWrapper.getCurrentPosition() : null;
            if (currentPosition != null && currentPosition.intValue() == 0) {
                currentPosition = null;
            }
            objM837constructorimpl = Result.m837constructorimpl(currentPosition);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM837constructorimpl = Result.m837constructorimpl(ResultKt.createFailure(th));
        }
        Integer num = (Integer) (Result.m843isFailureimpl(objM837constructorimpl) ? null : objM837constructorimpl);
        if (num != null) {
            return num.intValue();
        }
        return -1;
    }

    private final void requestFocusAndStart() {
        this.focusManager.maybeRequestAudioFocus();
    }

    private final void setVolumeAndBalance(PlayerWrapper playerWrapper, float f2, float f3) {
        playerWrapper.setVolume(Math.min(1.0f, 1.0f - f3) * f2, Math.min(1.0f, f3 + 1.0f) * f2);
    }

    public final void dispose() {
        release();
        this.eventHandler.dispose();
    }

    @NotNull
    public final Context getApplicationContext() {
        return this.ref.getApplicationContext();
    }

    @NotNull
    public final AudioManager getAudioManager() {
        return this.ref.getAudioManager();
    }

    public final float getBalance() {
        return this.balance;
    }

    @NotNull
    public final AudioContextAndroid getContext() {
        return this.context;
    }

    @Nullable
    public final Integer getCurrentPosition() {
        PlayerWrapper playerWrapper;
        if (!this.prepared || (playerWrapper = this.player) == null) {
            return null;
        }
        return playerWrapper.getCurrentPosition();
    }

    @Nullable
    public final Integer getDuration() {
        PlayerWrapper playerWrapper;
        if (!this.prepared || (playerWrapper = this.player) == null) {
            return null;
        }
        return playerWrapper.getDuration();
    }

    @NotNull
    public final EventHandler getEventHandler() {
        return this.eventHandler;
    }

    @NotNull
    public final PlayerMode getPlayerMode() {
        return this.playerMode;
    }

    public final boolean getPlaying() {
        return this.playing;
    }

    public final boolean getPrepared() {
        return this.prepared;
    }

    public final float getRate() {
        return this.rate;
    }

    @NotNull
    public final ReleaseMode getReleaseMode() {
        return this.releaseMode;
    }

    public final boolean getReleased() {
        return this.released;
    }

    public final int getShouldSeekTo() {
        return this.shouldSeekTo;
    }

    @Nullable
    public final Source getSource() {
        return this.source;
    }

    public final float getVolume() {
        return this.volume;
    }

    public final void handleError(@Nullable String errorCode, @Nullable String errorMessage, @Nullable Object errorDetails) {
        this.ref.handleError(this, errorCode, errorMessage, errorDetails);
    }

    public final void handleLog(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.ref.handleLog(this, message);
    }

    public final boolean isLooping() {
        return this.releaseMode == ReleaseMode.LOOP;
    }

    public final void onBuffering(int percent) {
    }

    public final void onCompletion() {
        if (this.releaseMode != ReleaseMode.LOOP) {
            stop();
        }
        this.ref.handleComplete(this);
    }

    public final boolean onError(int what, int extra) {
        String str;
        String str2;
        if (what == 100) {
            str = "MEDIA_ERROR_SERVER_DIED";
        } else {
            str = "MEDIA_ERROR_UNKNOWN {what:" + what + i.f9804d;
        }
        if (extra == Integer.MIN_VALUE) {
            str2 = "MEDIA_ERROR_SYSTEM";
        } else if (extra == -1010) {
            str2 = "MEDIA_ERROR_UNSUPPORTED";
        } else if (extra == -1007) {
            str2 = "MEDIA_ERROR_MALFORMED";
        } else if (extra == -1004) {
            str2 = "MEDIA_ERROR_IO";
        } else if (extra != -110) {
            str2 = "MEDIA_ERROR_UNKNOWN {extra:" + extra + i.f9804d;
        } else {
            str2 = "MEDIA_ERROR_TIMED_OUT";
        }
        if (this.prepared || !Intrinsics.areEqual(str2, "MEDIA_ERROR_SYSTEM")) {
            setPrepared(false);
            handleError("AndroidAudioError", str, str2);
        } else {
            handleError("AndroidAudioError", "Failed to set source. For troubleshooting, see: https://github.com/bluefireteam/audioplayers/blob/main/troubleshooting.md", str + ", " + str2);
        }
        return false;
    }

    public final void onPrepared() {
        PlayerWrapper playerWrapper;
        setPrepared(true);
        this.ref.handleDuration(this);
        if (this.playing) {
            requestFocusAndStart();
        }
        if (this.shouldSeekTo >= 0) {
            PlayerWrapper playerWrapper2 = this.player;
            if ((playerWrapper2 == null || !playerWrapper2.isLiveStream()) && (playerWrapper = this.player) != null) {
                playerWrapper.seekTo(this.shouldSeekTo);
            }
        }
    }

    public final void onSeekComplete() {
        this.ref.handleSeekComplete(this);
    }

    public final void pause() {
        PlayerWrapper playerWrapper;
        if (this.playing) {
            this.playing = false;
            if (!this.prepared || (playerWrapper = this.player) == null) {
                return;
            }
            playerWrapper.pause();
        }
    }

    public final void play() {
        if (this.playing || this.released) {
            return;
        }
        this.playing = true;
        if (this.player == null) {
            initPlayer();
        } else if (this.prepared) {
            requestFocusAndStart();
        }
    }

    public final void release() {
        PlayerWrapper playerWrapper;
        this.focusManager.handleStop();
        if (this.released) {
            return;
        }
        if (this.playing && (playerWrapper = this.player) != null) {
            playerWrapper.stop();
        }
        setSource(null);
        this.player = null;
    }

    public final void seek(int position) {
        PlayerWrapper playerWrapper;
        if (this.prepared && ((playerWrapper = this.player) == null || !playerWrapper.isLiveStream())) {
            PlayerWrapper playerWrapper2 = this.player;
            if (playerWrapper2 != null) {
                playerWrapper2.seekTo(position);
            }
            position = -1;
        }
        this.shouldSeekTo = position;
    }

    public final void setBalance(float f2) {
        PlayerWrapper playerWrapper;
        if (this.balance == f2) {
            return;
        }
        this.balance = f2;
        if (this.released || (playerWrapper = this.player) == null) {
            return;
        }
        setVolumeAndBalance(playerWrapper, this.volume, f2);
    }

    public final void setContext(@NotNull AudioContextAndroid audioContextAndroid) {
        Intrinsics.checkNotNullParameter(audioContextAndroid, "<set-?>");
        this.context = audioContextAndroid;
    }

    public final void setPlayerMode(@NotNull PlayerMode value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (this.playerMode != value) {
            this.playerMode = value;
            PlayerWrapper playerWrapper = this.player;
            if (playerWrapper != null) {
                this.shouldSeekTo = maybeGetCurrentPosition();
                setPrepared(false);
                playerWrapper.release();
            }
            initPlayer();
        }
    }

    public final void setPlaying(boolean z2) {
        this.playing = z2;
    }

    public final void setPrepared(boolean z2) {
        if (this.prepared != z2) {
            this.prepared = z2;
            this.ref.handlePrepared(this, z2);
        }
    }

    public final void setRate(float f2) {
        PlayerWrapper playerWrapper;
        if (this.rate == f2) {
            return;
        }
        this.rate = f2;
        if (!this.playing || (playerWrapper = this.player) == null) {
            return;
        }
        playerWrapper.setRate(f2);
    }

    public final void setReleaseMode(@NotNull ReleaseMode value) {
        PlayerWrapper playerWrapper;
        Intrinsics.checkNotNullParameter(value, "value");
        if (this.releaseMode != value) {
            this.releaseMode = value;
            if (this.released || (playerWrapper = this.player) == null) {
                return;
            }
            playerWrapper.setLooping(isLooping());
        }
    }

    public final void setReleased(boolean z2) {
        this.released = z2;
    }

    public final void setShouldSeekTo(int i2) {
        this.shouldSeekTo = i2;
    }

    public final void setSource(@Nullable Source source) {
        if (Intrinsics.areEqual(this.source, source)) {
            this.ref.handlePrepared(this, true);
            return;
        }
        if (source != null) {
            PlayerWrapper orCreatePlayer = getOrCreatePlayer();
            orCreatePlayer.setSource(source);
            configAndPrepare(orCreatePlayer);
        } else {
            this.released = true;
            setPrepared(false);
            this.playing = false;
            PlayerWrapper playerWrapper = this.player;
            if (playerWrapper != null) {
                playerWrapper.release();
            }
        }
        this.source = source;
    }

    public final void setVolume(float f2) {
        PlayerWrapper playerWrapper;
        if (this.volume == f2) {
            return;
        }
        this.volume = f2;
        if (this.released || (playerWrapper = this.player) == null) {
            return;
        }
        setVolumeAndBalance(playerWrapper, f2, this.balance);
    }

    public final void stop() {
        this.focusManager.handleStop();
        if (this.released) {
            return;
        }
        if (this.releaseMode == ReleaseMode.RELEASE) {
            release();
            return;
        }
        pause();
        if (this.prepared) {
            PlayerWrapper playerWrapper = this.player;
            if (playerWrapper == null || !playerWrapper.isLiveStream()) {
                seek(0);
                return;
            }
            PlayerWrapper playerWrapper2 = this.player;
            if (playerWrapper2 != null) {
                playerWrapper2.stop();
            }
            setPrepared(false);
            PlayerWrapper playerWrapper3 = this.player;
            if (playerWrapper3 != null) {
                playerWrapper3.prepare();
            }
        }
    }

    public final void updateAudioContext(@NotNull AudioContextAndroid audioContext) {
        Intrinsics.checkNotNullParameter(audioContext, "audioContext");
        if (Intrinsics.areEqual(this.context, audioContext)) {
            return;
        }
        if (this.context.getAudioFocus() != 0 && audioContext.getAudioFocus() == 0) {
            this.focusManager.handleStop();
        }
        this.context = AudioContextAndroid.copy$default(audioContext, false, false, 0, 0, 0, 0, 63, null);
        getAudioManager().setMode(this.context.getAudioMode());
        getAudioManager().setSpeakerphoneOn(this.context.isSpeakerphoneOn());
        PlayerWrapper playerWrapper = this.player;
        if (playerWrapper != null) {
            playerWrapper.stop();
            setPrepared(false);
            playerWrapper.updateContext(this.context);
            Source source = this.source;
            if (source != null) {
                playerWrapper.setSource(source);
                configAndPrepare(playerWrapper);
            }
        }
    }
}
