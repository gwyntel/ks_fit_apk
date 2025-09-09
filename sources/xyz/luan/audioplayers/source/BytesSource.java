package xyz.luan.audioplayers.source;

import android.media.MediaPlayer;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.player.SoundPoolPlayer;

@RequiresApi(23)
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\n\u001a\u00020\u0006HÆ\u0003J\u0013\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u001b"}, d2 = {"Lxyz/luan/audioplayers/source/BytesSource;", "Lxyz/luan/audioplayers/source/Source;", "bytes", "", "([B)V", "dataSource", "Lxyz/luan/audioplayers/source/ByteDataSource;", "(Lxyz/luan/audioplayers/source/ByteDataSource;)V", "getDataSource", "()Lxyz/luan/audioplayers/source/ByteDataSource;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "setForMediaPlayer", "", "mediaPlayer", "Landroid/media/MediaPlayer;", "setForSoundPool", "soundPoolPlayer", "Lxyz/luan/audioplayers/player/SoundPoolPlayer;", "toString", "", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class BytesSource implements Source {

    @NotNull
    private final ByteDataSource dataSource;

    public BytesSource(@NotNull ByteDataSource dataSource) {
        Intrinsics.checkNotNullParameter(dataSource, "dataSource");
        this.dataSource = dataSource;
    }

    public static /* synthetic */ BytesSource copy$default(BytesSource bytesSource, ByteDataSource byteDataSource, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            byteDataSource = bytesSource.dataSource;
        }
        return bytesSource.copy(byteDataSource);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final ByteDataSource getDataSource() {
        return this.dataSource;
    }

    @NotNull
    public final BytesSource copy(@NotNull ByteDataSource dataSource) {
        Intrinsics.checkNotNullParameter(dataSource, "dataSource");
        return new BytesSource(dataSource);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof BytesSource) && Intrinsics.areEqual(this.dataSource, ((BytesSource) other).dataSource);
    }

    @NotNull
    public final ByteDataSource getDataSource() {
        return this.dataSource;
    }

    public int hashCode() {
        return this.dataSource.hashCode();
    }

    @Override // xyz.luan.audioplayers.source.Source
    public void setForMediaPlayer(@NotNull MediaPlayer mediaPlayer) throws IllegalStateException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(mediaPlayer, "mediaPlayer");
        mediaPlayer.setDataSource(this.dataSource);
    }

    @Override // xyz.luan.audioplayers.source.Source
    public void setForSoundPool(@NotNull SoundPoolPlayer soundPoolPlayer) {
        Intrinsics.checkNotNullParameter(soundPoolPlayer, "soundPoolPlayer");
        throw new IllegalStateException("Bytes sources are not supported on LOW_LATENCY mode yet.".toString());
    }

    @NotNull
    public String toString() {
        return "BytesSource(dataSource=" + this.dataSource + ")";
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BytesSource(@NotNull byte[] bytes) {
        this(new ByteDataSource(bytes));
        Intrinsics.checkNotNullParameter(bytes, "bytes");
    }
}
