package xyz.luan.audioplayers.player;

import android.media.AudioAttributes;
import android.media.SoundPool;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.luan.audioplayers.AudioContextAndroid;
import xyz.luan.audioplayers.AudioplayersPlugin;
import xyz.luan.audioplayers.source.UrlSource;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\fJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000f\u001a\u00020\u0010R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0006`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lxyz/luan/audioplayers/player/SoundPoolManager;", "", "ref", "Lxyz/luan/audioplayers/AudioplayersPlugin;", "(Lxyz/luan/audioplayers/AudioplayersPlugin;)V", "legacySoundPoolWrapper", "Lxyz/luan/audioplayers/player/SoundPoolWrapper;", "soundPoolWrappers", "Ljava/util/HashMap;", "Landroid/media/AudioAttributes;", "Lkotlin/collections/HashMap;", "createSoundPoolWrapper", "", "maxStreams", "", "audioContext", "Lxyz/luan/audioplayers/AudioContextAndroid;", "dispose", "getSoundPoolWrapper", "audioplayers_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class SoundPoolManager {

    @Nullable
    private SoundPoolWrapper legacySoundPoolWrapper;

    @NotNull
    private final AudioplayersPlugin ref;

    @NotNull
    private final HashMap<AudioAttributes, SoundPoolWrapper> soundPoolWrappers;

    public SoundPoolManager(@NotNull AudioplayersPlugin ref) {
        Intrinsics.checkNotNullParameter(ref, "ref");
        this.ref = ref;
        this.soundPoolWrappers = new HashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSoundPoolWrapper$lambda$1(SoundPoolManager this$0, SoundPoolWrapper soundPoolWrapper, SoundPool soundPool, int i2, int i3) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(soundPoolWrapper, "$soundPoolWrapper");
        this$0.ref.handleGlobalLog("Loaded " + i2);
        SoundPoolPlayer soundPoolPlayer = soundPoolWrapper.getSoundIdToPlayer().get(Integer.valueOf(i2));
        UrlSource urlSource = soundPoolPlayer != null ? soundPoolPlayer.getUrlSource() : null;
        if (urlSource != null) {
            TypeIntrinsics.asMutableMap(soundPoolWrapper.getSoundIdToPlayer()).remove(soundPoolPlayer.getSoundId());
            synchronized (soundPoolWrapper.getUrlToPlayers()) {
                try {
                    List<SoundPoolPlayer> listEmptyList = soundPoolWrapper.getUrlToPlayers().get(urlSource);
                    if (listEmptyList == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                    for (SoundPoolPlayer soundPoolPlayer2 : listEmptyList) {
                        soundPoolPlayer2.getWrappedPlayer().handleLog("Marking " + soundPoolPlayer2 + " as loaded");
                        soundPoolPlayer2.getWrappedPlayer().setPrepared(true);
                        if (soundPoolPlayer2.getWrappedPlayer().getPlaying()) {
                            soundPoolPlayer2.getWrappedPlayer().handleLog("Delayed start of " + soundPoolPlayer2);
                            soundPoolPlayer2.start();
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void createSoundPoolWrapper(int maxStreams, @NotNull AudioContextAndroid audioContext) {
        Intrinsics.checkNotNullParameter(audioContext, "audioContext");
        AudioAttributes audioAttributesBuildAttributes = audioContext.buildAttributes();
        if (this.soundPoolWrappers.containsKey(audioAttributesBuildAttributes)) {
            return;
        }
        SoundPool soundPoolBuild = new SoundPool.Builder().setAudioAttributes(audioAttributesBuildAttributes).setMaxStreams(maxStreams).build();
        this.ref.handleGlobalLog("Create SoundPool with " + audioAttributesBuildAttributes);
        Intrinsics.checkNotNull(soundPoolBuild);
        final SoundPoolWrapper soundPoolWrapper = new SoundPoolWrapper(soundPoolBuild);
        soundPoolWrapper.getSoundPool().setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: xyz.luan.audioplayers.player.h
            @Override // android.media.SoundPool.OnLoadCompleteListener
            public final void onLoadComplete(SoundPool soundPool, int i2, int i3) {
                SoundPoolManager.createSoundPoolWrapper$lambda$1(this.f26928a, soundPoolWrapper, soundPool, i2, i3);
            }
        });
        this.soundPoolWrappers.put(audioAttributesBuildAttributes, soundPoolWrapper);
    }

    public final void dispose() {
        Iterator<Map.Entry<AudioAttributes, SoundPoolWrapper>> it = this.soundPoolWrappers.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().dispose();
        }
        this.soundPoolWrappers.clear();
    }

    @Nullable
    public final SoundPoolWrapper getSoundPoolWrapper(@NotNull AudioContextAndroid audioContext) {
        Intrinsics.checkNotNullParameter(audioContext, "audioContext");
        return this.soundPoolWrappers.get(audioContext.buildAttributes());
    }
}
