package xyz.luan.audioplayers.player;

import android.media.AudioFocusRequest;
import android.media.AudioManager;
import androidx.annotation.RequiresApi;
import androidx.media3.exoplayer.i;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;

@RequiresApi(26)
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0003\u0018\u00002\u00020\u0001B@\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012!\u0010\f\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\bH\u0014¢\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\u0005H\u0014¢\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\u0005H\u0016¢\u0006\u0004\b\u0013\u0010\u0012J\u000f\u0010\u0014\u001a\u00020\u0005H\u0014¢\u0006\u0004\b\u0014\u0010\u0012R\u001a\u0010\u0003\u001a\u00020\u00028\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\u0003\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R \u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\u0006\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR5\u0010\f\u001a\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u00050\u00078\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\f\u0010\u001b\u001a\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e¢\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0018\u0010&\u001a\u0004\u0018\u00010%8\u0002@\u0002X\u0082\u000e¢\u0006\u0006\n\u0004\b&\u0010'¨\u0006("}, d2 = {"Lxyz/luan/audioplayers/player/ModernFocusManager;", "Lxyz/luan/audioplayers/player/FocusManager;", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "player", "Lkotlin/Function0;", "", "onGranted", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "isTransient", "onLoss", "<init>", "(Lxyz/luan/audioplayers/player/WrappedPlayer;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function1;)V", bc.aL, "()Z", "e", "()V", "handleStop", "d", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "getPlayer", "()Lxyz/luan/audioplayers/player/WrappedPlayer;", "Lkotlin/jvm/functions/Function0;", "getOnGranted", "()Lkotlin/jvm/functions/Function0;", "Lkotlin/jvm/functions/Function1;", "getOnLoss", "()Lkotlin/jvm/functions/Function1;", "Lxyz/luan/audioplayers/AudioContextAndroid;", com.umeng.analytics.pro.f.X, "Lxyz/luan/audioplayers/AudioContextAndroid;", "getContext", "()Lxyz/luan/audioplayers/AudioContextAndroid;", "setContext", "(Lxyz/luan/audioplayers/AudioContextAndroid;)V", "Landroid/media/AudioFocusRequest;", "audioFocusRequest", "Landroid/media/AudioFocusRequest;", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0})
@SourceDebugExtension({"SMAP\nFocusManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FocusManager.kt\nxyz/luan/audioplayers/player/ModernFocusManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,156:1\n1#2:157\n*E\n"})
/* loaded from: classes5.dex */
final class ModernFocusManager extends FocusManager {

    @Nullable
    private AudioFocusRequest audioFocusRequest;

    @NotNull
    private AudioContextAndroid context;

    @NotNull
    private final Function0<Unit> onGranted;

    @NotNull
    private final Function1<Boolean, Unit> onLoss;

    @NotNull
    private final WrappedPlayer player;

    /* JADX WARN: Multi-variable type inference failed */
    public ModernFocusManager(@NotNull WrappedPlayer player, @NotNull Function0<Unit> onGranted, @NotNull Function1<? super Boolean, Unit> onLoss) {
        Intrinsics.checkNotNullParameter(player, "player");
        Intrinsics.checkNotNullParameter(onGranted, "onGranted");
        Intrinsics.checkNotNullParameter(onLoss, "onLoss");
        this.player = player;
        this.onGranted = onGranted;
        this.onLoss = onLoss;
        this.context = getPlayer().getContext();
        e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateAudioFocusRequest$lambda$0(ModernFocusManager this$0, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.b(i2);
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    protected boolean c() {
        return this.audioFocusRequest != null;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    protected void d() {
        AudioManager audioManagerA = a();
        AudioFocusRequest audioFocusRequest = this.audioFocusRequest;
        Intrinsics.checkNotNull(audioFocusRequest);
        b(audioManagerA.requestAudioFocus(audioFocusRequest));
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    protected void e() {
        AudioFocusRequest audioFocusRequestBuild;
        if (getContext().getAudioFocus() == 0) {
            audioFocusRequestBuild = null;
        } else {
            i.a();
            audioFocusRequestBuild = androidx.media3.exoplayer.g.a(getContext().getAudioFocus()).setAudioAttributes(getContext().buildAttributes()).setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() { // from class: xyz.luan.audioplayers.player.g
                @Override // android.media.AudioManager.OnAudioFocusChangeListener
                public final void onAudioFocusChange(int i2) {
                    ModernFocusManager.updateAudioFocusRequest$lambda$0(this.f26927a, i2);
                }
            }).build();
        }
        this.audioFocusRequest = audioFocusRequestBuild;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    @NotNull
    public AudioContextAndroid getContext() {
        return this.context;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    @NotNull
    public Function0<Unit> getOnGranted() {
        return this.onGranted;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    @NotNull
    public Function1<Boolean, Unit> getOnLoss() {
        return this.onLoss;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    @NotNull
    public WrappedPlayer getPlayer() {
        return this.player;
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    public void handleStop() {
        AudioFocusRequest audioFocusRequest;
        if (!c() || (audioFocusRequest = this.audioFocusRequest) == null) {
            return;
        }
        a().abandonAudioFocusRequest(audioFocusRequest);
    }

    @Override // xyz.luan.audioplayers.player.FocusManager
    public void setContext(@NotNull AudioContextAndroid audioContextAndroid) {
        Intrinsics.checkNotNullParameter(audioContextAndroid, "<set-?>");
        this.context = audioContextAndroid;
    }
}
