package xyz.luan.audioplayers;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import androidx.annotation.RequiresApi;
import java.util.Objects;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0007\b\u0017¢\u0006\u0002\u0010\u0002B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\b\u0010\u0013\u001a\u00020\u0014H\u0007J\t\u0010\u0015\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÆ\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u001c\u001a\u00020\u00042\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001e\u001a\u00020\u0007H\u0003J\b\u0010\u001f\u001a\u00020\u0007H\u0016J\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#J\t\u0010$\u001a\u00020%HÖ\u0001R\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\r¨\u0006&"}, d2 = {"Lxyz/luan/audioplayers/AudioContextAndroid;", "", "()V", "isSpeakerphoneOn", "", "stayAwake", "contentType", "", "usageType", "audioFocus", "audioMode", "(ZZIIII)V", "getAudioFocus", "()I", "getAudioMode", "getContentType", "()Z", "getStayAwake", "getUsageType", "buildAttributes", "Landroid/media/AudioAttributes;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "getStreamType", "hashCode", "setAttributesOnPlayer", "", "player", "Landroid/media/MediaPlayer;", "toString", "", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class AudioContextAndroid {
    private final int audioFocus;
    private final int audioMode;
    private final int contentType;
    private final boolean isSpeakerphoneOn;
    private final boolean stayAwake;
    private final int usageType;

    public AudioContextAndroid(boolean z2, boolean z3, int i2, int i3, int i4, int i5) {
        this.isSpeakerphoneOn = z2;
        this.stayAwake = z3;
        this.contentType = i2;
        this.usageType = i3;
        this.audioFocus = i4;
        this.audioMode = i5;
    }

    public static /* synthetic */ AudioContextAndroid copy$default(AudioContextAndroid audioContextAndroid, boolean z2, boolean z3, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            z2 = audioContextAndroid.isSpeakerphoneOn;
        }
        if ((i6 & 2) != 0) {
            z3 = audioContextAndroid.stayAwake;
        }
        boolean z4 = z3;
        if ((i6 & 4) != 0) {
            i2 = audioContextAndroid.contentType;
        }
        int i7 = i2;
        if ((i6 & 8) != 0) {
            i3 = audioContextAndroid.usageType;
        }
        int i8 = i3;
        if ((i6 & 16) != 0) {
            i4 = audioContextAndroid.audioFocus;
        }
        int i9 = i4;
        if ((i6 & 32) != 0) {
            i5 = audioContextAndroid.audioMode;
        }
        return audioContextAndroid.copy(z2, z4, i7, i8, i9, i5);
    }

    @Deprecated(message = "This is used for Android older than LOLLIPOP", replaceWith = @ReplaceWith(expression = "buildAttributes", imports = {}))
    private final int getStreamType() {
        int i2 = this.usageType;
        if (i2 != 2) {
            return i2 != 6 ? 3 : 2;
        }
        return 0;
    }

    @RequiresApi(21)
    @NotNull
    public final AudioAttributes buildAttributes() {
        AudioAttributes audioAttributesBuild = new AudioAttributes.Builder().setUsage(this.usageType).setContentType(this.contentType).build();
        Intrinsics.checkNotNullExpressionValue(audioAttributesBuild, "build(...)");
        return audioAttributesBuild;
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getIsSpeakerphoneOn() {
        return this.isSpeakerphoneOn;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getStayAwake() {
        return this.stayAwake;
    }

    /* renamed from: component3, reason: from getter */
    public final int getContentType() {
        return this.contentType;
    }

    /* renamed from: component4, reason: from getter */
    public final int getUsageType() {
        return this.usageType;
    }

    /* renamed from: component5, reason: from getter */
    public final int getAudioFocus() {
        return this.audioFocus;
    }

    /* renamed from: component6, reason: from getter */
    public final int getAudioMode() {
        return this.audioMode;
    }

    @NotNull
    public final AudioContextAndroid copy(boolean isSpeakerphoneOn, boolean stayAwake, int contentType, int usageType, int audioFocus, int audioMode) {
        return new AudioContextAndroid(isSpeakerphoneOn, stayAwake, contentType, usageType, audioFocus, audioMode);
    }

    public boolean equals(@Nullable Object other) {
        if (other instanceof AudioContextAndroid) {
            AudioContextAndroid audioContextAndroid = (AudioContextAndroid) other;
            if (this.isSpeakerphoneOn == audioContextAndroid.isSpeakerphoneOn && this.stayAwake == audioContextAndroid.stayAwake && this.contentType == audioContextAndroid.contentType && this.usageType == audioContextAndroid.usageType && this.audioFocus == audioContextAndroid.audioFocus && this.audioMode == audioContextAndroid.audioMode) {
                return true;
            }
        }
        return false;
    }

    public final int getAudioFocus() {
        return this.audioFocus;
    }

    public final int getAudioMode() {
        return this.audioMode;
    }

    public final int getContentType() {
        return this.contentType;
    }

    public final boolean getStayAwake() {
        return this.stayAwake;
    }

    public final int getUsageType() {
        return this.usageType;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.isSpeakerphoneOn), Boolean.valueOf(this.stayAwake), Integer.valueOf(this.contentType), Integer.valueOf(this.usageType), Integer.valueOf(this.audioFocus), Integer.valueOf(this.audioMode));
    }

    public final boolean isSpeakerphoneOn() {
        return this.isSpeakerphoneOn;
    }

    public final void setAttributesOnPlayer(@NotNull MediaPlayer player) throws IllegalArgumentException {
        Intrinsics.checkNotNullParameter(player, "player");
        player.setAudioAttributes(buildAttributes());
    }

    @NotNull
    public String toString() {
        return "AudioContextAndroid(isSpeakerphoneOn=" + this.isSpeakerphoneOn + ", stayAwake=" + this.stayAwake + ", contentType=" + this.contentType + ", usageType=" + this.usageType + ", audioFocus=" + this.audioFocus + ", audioMode=" + this.audioMode + ")";
    }

    @SuppressLint({"InlinedApi"})
    public AudioContextAndroid() {
        this(false, false, 2, 1, 1, 0);
    }
}
