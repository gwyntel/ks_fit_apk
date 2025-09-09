package com.aliyun.player.nativeclass;

import android.content.Context;
import com.aliyun.player.source.BitStreamSource;
import com.aliyun.player.source.UrlSource;
import com.aliyun.utils.f;
import com.cicada.player.utils.NativeUsed;

/* loaded from: classes3.dex */
public class JniUrlPlayer extends NativePlayerBase {
    private static final String TAG = "JniUrlPlayer";
    private BitStreamSource.ReadCallback mReadCallback;
    private BitStreamSource.SeekCallback mSeekCallback;

    static {
        f.f();
    }

    public JniUrlPlayer(Context context) {
        super(context);
        this.mReadCallback = null;
        this.mSeekCallback = null;
    }

    public static void loadClass() {
    }

    private native void nEnableDowngrade(UrlSource urlSource, PlayerConfig playerConfig);

    @NativeUsed
    private int nRead(byte[] bArr) {
        BitStreamSource.ReadCallback readCallback = this.mReadCallback;
        if (readCallback != null) {
            return readCallback.read(bArr);
        }
        return -22;
    }

    @NativeUsed
    private long nSeek(long j2, int i2) {
        BitStreamSource.SeekCallback seekCallback = this.mSeekCallback;
        if (seekCallback != null) {
            return seekCallback.seek(j2, i2);
        }
        return -22L;
    }

    private native void nSetDataSource(BitStreamSource bitStreamSource);

    private native void nSetDataSource(UrlSource urlSource);

    public void enableDowngrade(UrlSource urlSource, PlayerConfig playerConfig) {
        if (f.b()) {
            nEnableDowngrade(urlSource, playerConfig);
        }
    }

    public void setDataSource(BitStreamSource bitStreamSource) {
        if (f.b()) {
            this.mReadCallback = bitStreamSource.getReadCallback();
            this.mSeekCallback = bitStreamSource.getSeekCallback();
            nSetDataSource(bitStreamSource);
        }
    }

    public void setDataSource(UrlSource urlSource) {
        if (f.b()) {
            nSetDataSource(urlSource);
        }
    }
}
