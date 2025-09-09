package xyz.luan.audioplayers.player;

import android.media.AudioManager;
import android.os.Build;
import com.umeng.analytics.pro.bc;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import xyz.luan.audioplayers.AudioContextAndroid;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 )2\u00020\u0001:\u0001)B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u000f\u0010\u0005\u001a\u00020\u0004H$¢\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H$¢\u0006\u0004\b\b\u0010\u0003J\r\u0010\t\u001a\u00020\u0007¢\u0006\u0004\b\t\u0010\u0003J\u000f\u0010\n\u001a\u00020\u0007H$¢\u0006\u0004\b\n\u0010\u0003J\u000f\u0010\u000b\u001a\u00020\u0007H&¢\u0006\u0004\b\u000b\u0010\u0003J\u0017\u0010\u000e\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\fH\u0004¢\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0013\u001a\u00020\u00108&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u00148&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R/\u0010\u001e\u001a\u001d\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0019\u0012\b\b\u001a\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u00070\u00188&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001c\u0010$\u001a\u00020\u001f8&@&X¦\u000e¢\u0006\f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u0014\u0010(\u001a\u00020%8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'¨\u0006*"}, d2 = {"Lxyz/luan/audioplayers/player/FocusManager;", "", "<init>", "()V", "", bc.aL, "()Z", "", "e", "maybeRequestAudioFocus", "d", "handleStop", "", "result", "b", "(I)V", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "getPlayer", "()Lxyz/luan/audioplayers/player/WrappedPlayer;", "player", "Lkotlin/Function0;", "getOnGranted", "()Lkotlin/jvm/functions/Function0;", "onGranted", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "isTransient", "getOnLoss", "()Lkotlin/jvm/functions/Function1;", "onLoss", "Lxyz/luan/audioplayers/AudioContextAndroid;", "getContext", "()Lxyz/luan/audioplayers/AudioContextAndroid;", "setContext", "(Lxyz/luan/audioplayers/AudioContextAndroid;)V", com.umeng.analytics.pro.f.X, "Landroid/media/AudioManager;", "a", "()Landroid/media/AudioManager;", "audioManager", "Companion", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes5.dex */
public abstract class FocusManager {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J?\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\f¢\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\t0\u000b¨\u0006\u0010"}, d2 = {"Lxyz/luan/audioplayers/player/FocusManager$Companion;", "", "()V", "create", "Lxyz/luan/audioplayers/player/FocusManager;", "player", "Lxyz/luan/audioplayers/player/WrappedPlayer;", "onGranted", "Lkotlin/Function0;", "", "onLoss", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "isTransient", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final FocusManager create(@NotNull WrappedPlayer player, @NotNull Function0<Unit> onGranted, @NotNull Function1<? super Boolean, Unit> onLoss) {
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(onGranted, "onGranted");
            Intrinsics.checkNotNullParameter(onLoss, "onLoss");
            return Build.VERSION.SDK_INT >= 26 ? new ModernFocusManager(player, onGranted, onLoss) : new LegacyFocusManager(player, onGranted, onLoss);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    protected final AudioManager a() {
        return getPlayer().getAudioManager();
    }

    protected final void b(int result) {
        if (result == -2) {
            getOnLoss().invoke(Boolean.TRUE);
        } else if (result == -1) {
            getOnLoss().invoke(Boolean.FALSE);
        } else {
            if (result != 1) {
                return;
            }
            getOnGranted().invoke();
        }
    }

    protected abstract boolean c();

    protected abstract void d();

    protected abstract void e();

    @NotNull
    public abstract AudioContextAndroid getContext();

    @NotNull
    public abstract Function0<Unit> getOnGranted();

    @NotNull
    public abstract Function1<Boolean, Unit> getOnLoss();

    @NotNull
    public abstract WrappedPlayer getPlayer();

    public abstract void handleStop();

    public final void maybeRequestAudioFocus() {
        if (!Intrinsics.areEqual(getContext(), getPlayer().getContext())) {
            setContext(getPlayer().getContext());
            e();
        }
        if (c()) {
            d();
        } else {
            getOnGranted().invoke();
        }
    }

    public abstract void setContext(@NotNull AudioContextAndroid audioContextAndroid);
}
