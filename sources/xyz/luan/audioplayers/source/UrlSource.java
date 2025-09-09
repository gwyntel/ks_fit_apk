package xyz.luan.audioplayers.source;

import android.media.MediaPlayer;
import com.huawei.hms.push.constant.RemoteMessageConst;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import m.c;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.player.SoundPoolPlayer;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u000fH\u0002J\u0013\u0010\u0010\u001a\u00020\u00052\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\u0006\u0010\u0013\u001a\u00020\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\t\u0010\u001f\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006 "}, d2 = {"Lxyz/luan/audioplayers/source/UrlSource;", "Lxyz/luan/audioplayers/source/Source;", "url", "", "isLocal", "", "(Ljava/lang/String;Z)V", "()Z", "getUrl", "()Ljava/lang/String;", "component1", "component2", "copy", "downloadUrl", "", "Ljava/net/URL;", "equals", "other", "", "getAudioPathForSoundPool", "hashCode", "", "loadTempFileFromNetwork", "Ljava/io/File;", "setForMediaPlayer", "", "mediaPlayer", "Landroid/media/MediaPlayer;", "setForSoundPool", "soundPoolPlayer", "Lxyz/luan/audioplayers/player/SoundPoolPlayer;", "toString", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nUrlSource.kt\nKotlin\n*S Kotlin\n*F\n+ 1 UrlSource.kt\nxyz/luan/audioplayers/source/UrlSource\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,54:1\n1#2:55\n*E\n"})
/* loaded from: classes5.dex */
public final /* data */ class UrlSource implements Source {
    private final boolean isLocal;

    @NotNull
    private final String url;

    public UrlSource(@NotNull String url, boolean z2) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.isLocal = z2;
    }

    public static /* synthetic */ UrlSource copy$default(UrlSource urlSource, String str, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = urlSource.url;
        }
        if ((i2 & 2) != 0) {
            z2 = urlSource.isLocal;
        }
        return urlSource.copy(str, z2);
    }

    private final byte[] downloadUrl(URL url) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStreamOpenStream = url.openStream();
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                Integer numValueOf = Integer.valueOf(inputStreamOpenStream.read(bArr));
                if (numValueOf.intValue() <= 0) {
                    numValueOf = null;
                }
                if (numValueOf == null) {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(inputStreamOpenStream, null);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                    return byteArray;
                }
                byteArrayOutputStream.write(bArr, 0, numValueOf.intValue());
            }
        } finally {
        }
    }

    private final File loadTempFileFromNetwork() throws IOException {
        URL url = URI.create(this.url).toURL();
        Intrinsics.checkNotNullExpressionValue(url, "toURL(...)");
        byte[] bArrDownloadUrl = downloadUrl(url);
        File fileCreateTempFile = File.createTempFile(RemoteMessageConst.Notification.SOUND, "");
        FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
        try {
            fileOutputStream.write(bArrDownloadUrl);
            fileCreateTempFile.deleteOnExit();
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, null);
            Intrinsics.checkNotNull(fileCreateTempFile);
            return fileCreateTempFile;
        } finally {
        }
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getUrl() {
        return this.url;
    }

    /* renamed from: component2, reason: from getter */
    public final boolean getIsLocal() {
        return this.isLocal;
    }

    @NotNull
    public final UrlSource copy(@NotNull String url, boolean isLocal) {
        Intrinsics.checkNotNullParameter(url, "url");
        return new UrlSource(url, isLocal);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UrlSource)) {
            return false;
        }
        UrlSource urlSource = (UrlSource) other;
        return Intrinsics.areEqual(this.url, urlSource.url) && this.isLocal == urlSource.isLocal;
    }

    @NotNull
    public final String getAudioPathForSoundPool() {
        if (this.isLocal) {
            return StringsKt.removePrefix(this.url, (CharSequence) "file://");
        }
        String absolutePath = loadTempFileFromNetwork().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }

    @NotNull
    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        return (this.url.hashCode() * 31) + c.a(this.isLocal);
    }

    public final boolean isLocal() {
        return this.isLocal;
    }

    @Override // xyz.luan.audioplayers.source.Source
    public void setForMediaPlayer(@NotNull MediaPlayer mediaPlayer) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(mediaPlayer, "mediaPlayer");
        mediaPlayer.setDataSource(this.url);
    }

    @Override // xyz.luan.audioplayers.source.Source
    public void setForSoundPool(@NotNull SoundPoolPlayer soundPoolPlayer) {
        Intrinsics.checkNotNullParameter(soundPoolPlayer, "soundPoolPlayer");
        soundPoolPlayer.release();
        soundPoolPlayer.setUrlSource(this);
    }

    @NotNull
    public String toString() {
        return "UrlSource(url=" + this.url + ", isLocal=" + this.isLocal + ")";
    }
}
