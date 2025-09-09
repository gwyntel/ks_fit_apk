package androidx.media3.common;

import androidx.media3.common.util.GlUtil;
import androidx.media3.common.util.UnstableApi;

@UnstableApi
/* loaded from: classes.dex */
public final class GlTextureInfo {
    public static final GlTextureInfo UNSET = new GlTextureInfo(-1, -1, -1, -1, -1);
    public final int fboId;
    public final int height;
    public final int rboId;
    public final int texId;
    public final int width;

    public GlTextureInfo(int i2, int i3, int i4, int i5, int i6) {
        this.texId = i2;
        this.fboId = i3;
        this.rboId = i4;
        this.width = i5;
        this.height = i6;
    }

    public void release() throws GlUtil.GlException {
        int i2 = this.texId;
        if (i2 != -1) {
            GlUtil.deleteTexture(i2);
        }
        int i3 = this.fboId;
        if (i3 != -1) {
            GlUtil.deleteFbo(i3);
        }
        int i4 = this.rboId;
        if (i4 != -1) {
            GlUtil.deleteRbo(i4);
        }
    }
}
