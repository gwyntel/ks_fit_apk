package androidx.media3.exoplayer.mediacodec;

import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.mediacodec.MediaCodecUtil;
import java.util.List;

@UnstableApi
/* loaded from: classes.dex */
public interface MediaCodecSelector {
    public static final MediaCodecSelector DEFAULT = new MediaCodecSelector() { // from class: androidx.media3.exoplayer.mediacodec.p
        @Override // androidx.media3.exoplayer.mediacodec.MediaCodecSelector
        public final List getDecoderInfos(String str, boolean z2, boolean z3) {
            return MediaCodecUtil.getDecoderInfos(str, z2, z3);
        }
    };

    List<MediaCodecInfo> getDecoderInfos(String str, boolean z2, boolean z3) throws MediaCodecUtil.DecoderQueryException;
}
