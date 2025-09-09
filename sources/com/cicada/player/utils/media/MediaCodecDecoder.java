package com.cicada.player.utils.media;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.os.Build;
import android.text.TextUtils;
import android.view.Surface;
import androidx.media3.common.C;
import androidx.media3.decoder.c;
import com.cicada.player.utils.Logger;
import com.cicada.player.utils.NativeUsed;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NativeUsed
/* loaded from: classes3.dex */
public class MediaCodecDecoder {
    private static int CODEC_CATEGORY_AUDIO = 1;
    private static int CODEC_CATEGORY_VIDEO = 0;
    private static final int ERROR = -1;
    private static final String TAG = "MediaCodecDecoder";
    private static final int TRY_AGAIN = -11;
    private String mDecoderName;
    private String mMime;
    private static final Object queLock = new Object();
    static List<String> blackCodecPrefix = new ArrayList();
    static List<String> blackCodecSuffix = new ArrayList();
    private Map<String, byte[]> mCodecSpecificDataMap = new LinkedHashMap();
    private int mCodecCateGory = CODEC_CATEGORY_VIDEO;
    private MediaCodec mMediaCodec = null;
    private MediaCrypto mediaCrypto = null;
    private boolean forceInsecureDecoder = false;
    private ByteBuffer[] mInputBuffers = null;
    private ByteBuffer[] mOutputBuffers = null;
    private MediaCodec.BufferInfo mBufferInfo = null;
    private boolean started = false;

    private MediaCodec.CryptoInfo createCryptoInfo(EncryptionInfo encryptionInfo) {
        int i2;
        MediaCodec.CryptoInfo cryptoInfo = new MediaCodec.CryptoInfo();
        cryptoInfo.key = encryptionInfo.key_id;
        cryptoInfo.iv = encryptionInfo.iv;
        List<SubsampleEncryptionInfo> list = encryptionInfo.subsamples;
        if (list != null) {
            int size = list.size();
            cryptoInfo.numSubSamples = size;
            cryptoInfo.numBytesOfClearData = new int[size];
            cryptoInfo.numBytesOfEncryptedData = new int[size];
            for (int i3 = 0; i3 < encryptionInfo.subsamples.size(); i3++) {
                SubsampleEncryptionInfo subsampleEncryptionInfo = encryptionInfo.subsamples.get(i3);
                cryptoInfo.numBytesOfClearData[i3] = subsampleEncryptionInfo.bytes_of_clear_data;
                cryptoInfo.numBytesOfEncryptedData[i3] = subsampleEncryptionInfo.bytes_of_protected_data;
            }
        }
        if (!C.CENC_TYPE_cenc.equals(encryptionInfo.scheme) && !C.CENC_TYPE_cens.equals(encryptionInfo.scheme)) {
            i2 = (C.CENC_TYPE_cbc1.equals(encryptionInfo.scheme) || C.CENC_TYPE_cbcs.equals(encryptionInfo.scheme)) ? 2 : 1;
            if (Build.VERSION.SDK_INT >= 24 && (C.CENC_TYPE_cens.equals(encryptionInfo.scheme) || C.CENC_TYPE_cbcs.equals(encryptionInfo.scheme))) {
                cryptoInfo.setPattern(c.a(encryptionInfo.crypt_byte_block, encryptionInfo.skip_byte_block));
            }
            return cryptoInfo;
        }
        cryptoInfo.mode = i2;
        if (Build.VERSION.SDK_INT >= 24) {
            cryptoInfo.setPattern(c.a(encryptionInfo.crypt_byte_block, encryptionInfo.skip_byte_block));
        }
        return cryptoInfo;
    }

    private OutputBufferInfo fillDecodeBufferInfo(int i2) {
        OutputBufferInfo outputBufferInfo = new OutputBufferInfo();
        outputBufferInfo.type = 0;
        outputBufferInfo.index = i2;
        MediaCodec.BufferInfo bufferInfo = this.mBufferInfo;
        outputBufferInfo.pts = bufferInfo.presentationTimeUs;
        outputBufferInfo.eos = (bufferInfo.flags & 4) != 0;
        outputBufferInfo.bufferSize = bufferInfo.size;
        outputBufferInfo.bufferOffset = bufferInfo.offset;
        return outputBufferInfo;
    }

    private OutputBufferInfo fillFormatOutputBufferInfo() {
        try {
            MediaFormat outputFormat = this.mMediaCodec.getOutputFormat();
            OutputBufferInfo outputBufferInfo = new OutputBufferInfo();
            outputBufferInfo.type = 1;
            outputBufferInfo.eos = false;
            if (this.mCodecCateGory == CODEC_CATEGORY_VIDEO) {
                outputBufferInfo.videoCropBottom = getFormatInteger(outputFormat, "crop-bottom");
                outputBufferInfo.videoCropLeft = getFormatInteger(outputFormat, "crop-left");
                outputBufferInfo.videoCropRight = getFormatInteger(outputFormat, "crop-right");
                outputBufferInfo.videoCropTop = getFormatInteger(outputFormat, "crop-top");
                outputBufferInfo.videoHeight = getFormatInteger(outputFormat, ViewHierarchyConstants.DIMENSION_WIDTH_KEY);
                outputBufferInfo.videoWidth = getFormatInteger(outputFormat, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY);
                outputBufferInfo.videoPixelFormat = getFormatInteger(outputFormat, "color-format");
                outputBufferInfo.videoSliceHeight = getFormatInteger(outputFormat, "slice-height");
                outputBufferInfo.videoStride = getFormatInteger(outputFormat, "stride");
            } else {
                outputBufferInfo.audioChannelCount = getFormatInteger(outputFormat, "channel-count");
                outputBufferInfo.audioChannelMask = getFormatInteger(outputFormat, "channel-mask");
                outputBufferInfo.audioSampleRate = getFormatInteger(outputFormat, "sample-rate");
                outputBufferInfo.audioFormat = getFormatInteger(outputFormat, "pcm-encoding");
            }
            return outputBufferInfo;
        } catch (Exception unused) {
            return null;
        }
    }

    private String findDecoderName(MediaFormat mediaFormat) {
        MediaCrypto mediaCrypto = this.mediaCrypto;
        boolean z2 = false;
        if (mediaCrypto != null && !this.forceInsecureDecoder && mediaCrypto.requiresSecureDecoderComponent(this.mMime)) {
            z2 = true;
        }
        String decoderName = getDecoderName(mediaFormat, z2);
        Logger.i(TAG, "findDecoderName : " + decoderName + " , secure = " + z2);
        return decoderName;
    }

    private static int getFormatInteger(MediaFormat mediaFormat, String str) {
        if (mediaFormat.containsKey(str)) {
            return mediaFormat.getInteger(str);
        }
        return -1;
    }

    private ByteBuffer getInputBuffer(int i2) {
        try {
            return this.mMediaCodec.getInputBuffer(i2);
        } catch (Exception e2) {
            Logger.e(TAG, "getInputBuffer fail " + e2.getMessage());
            return null;
        }
    }

    private String getNotBlackCodecName(List<MediaCodecInfo> list) {
        if (!list.isEmpty()) {
            for (MediaCodecInfo mediaCodecInfo : list) {
                if (!isBlackCodec(mediaCodecInfo)) {
                    return mediaCodecInfo.getName();
                }
            }
        }
        return null;
    }

    private static boolean isBlackCodec(MediaCodecInfo mediaCodecInfo) {
        if (blackCodecPrefix.isEmpty()) {
            blackCodecPrefix.add("OMX.PV.");
            blackCodecPrefix.add("OMX.google.");
            blackCodecPrefix.add("OMX.ARICENT.");
            blackCodecPrefix.add("OMX.SEC.WMV.Decoder");
            blackCodecPrefix.add("OMX.SEC.MP3.Decoder");
            blackCodecPrefix.add("OMX.MTK.VIDEO.DECODER.VC1");
            blackCodecPrefix.add("OMX.SEC.vp8.dec");
        }
        if (blackCodecSuffix.isEmpty()) {
            blackCodecSuffix.add(".sw.dec");
            blackCodecSuffix.add(".hevcswvdec");
        }
        String name = mediaCodecInfo.getName();
        Iterator<String> it = blackCodecPrefix.iterator();
        while (it.hasNext()) {
            if (name.startsWith(it.next())) {
                return true;
            }
        }
        Iterator<String> it2 = blackCodecSuffix.iterator();
        while (it2.hasNext()) {
            if (name.endsWith(it2.next())) {
                return true;
            }
        }
        return false;
    }

    private int queueInputBufferInner(int i2, byte[] bArr, long j2, boolean z2, boolean z3, Object obj) throws MediaCodec.CryptoException {
        ByteBuffer inputBuffer;
        if (this.mMediaCodec == null || (inputBuffer = getInputBuffer(i2)) == null) {
            return -1;
        }
        inputBuffer.clear();
        if (bArr != null) {
            inputBuffer.put(bArr, 0, bArr.length);
            inputBuffer.flip();
        }
        int i3 = z2 ? 2 : 0;
        if (bArr == null) {
            i3 |= 4;
        }
        int i4 = i3;
        try {
            if (z3 && bArr != null) {
                MediaCodec.CryptoInfo cryptoInfoCreateCryptoInfo = createCryptoInfo((EncryptionInfo) obj);
                synchronized (queLock) {
                    this.mMediaCodec.queueSecureInputBuffer(i2, 0, cryptoInfoCreateCryptoInfo, j2, i4);
                }
            } else if ((i4 & 4) == 4) {
                this.mMediaCodec.queueInputBuffer(i2, 0, 0, 0L, i4);
            } else {
                this.mMediaCodec.queueInputBuffer(i2, 0, inputBuffer.limit(), j2, i4);
            }
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, "queueInputBufferInner  fail " + e2.getLocalizedMessage());
            return -1;
        }
    }

    @NativeUsed
    public int configureAudio(String str, int i2, int i3, int i4) {
        String str2 = TAG;
        Logger.i(str2, "--> configureAudio start " + str + i2 + " , " + i3);
        this.mCodecCateGory = CODEC_CATEGORY_AUDIO;
        this.mMime = str;
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat(str, i2, i3);
        mediaFormatCreateAudioFormat.setInteger("is-adts", i4);
        String strFindDecoderName = findDecoderName(mediaFormatCreateAudioFormat);
        if (TextUtils.isEmpty(strFindDecoderName)) {
            Logger.e(str2, "not found codec : " + str);
            return -12;
        }
        try {
            this.mMediaCodec = MediaCodec.createByCodecName(strFindDecoderName);
        } catch (IOException unused) {
        }
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            Logger.e(TAG, "createByCodecName fail : " + strFindDecoderName);
            return -13;
        }
        try {
            mediaCodec.configure(mediaFormatCreateAudioFormat, (Surface) null, this.mediaCrypto, 0);
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, "configure fail : " + e2.getMessage());
            return -14;
        }
    }

    @NativeUsed
    public int configureVideo(String str, int i2, int i3, int i4, boolean z2, Object obj) {
        MediaCrypto mediaCrypto;
        Surface surface;
        Logger.i(TAG, "--> configureVideo start " + str + ", " + i2 + ", " + i3 + ", " + i4 + ", " + z2 + ", " + obj);
        this.mCodecCateGory = CODEC_CATEGORY_VIDEO;
        this.mMime = str;
        int i5 = z2 ? 2 : 1;
        while (true) {
            int i6 = i5 - 1;
            if (i5 <= 0) {
                return 0;
            }
            MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat(str, i2, i3);
            if (i4 != 0) {
                mediaFormatCreateVideoFormat.setInteger("rotation-degrees", i4);
            }
            String strFindDecoderName = findDecoderName(mediaFormatCreateVideoFormat);
            this.mDecoderName = strFindDecoderName;
            if (TextUtils.isEmpty(strFindDecoderName)) {
                Logger.e(TAG, "not found video codec : " + str);
                return -12;
            }
            if (z2) {
                if (strFindDecoderName.toLowerCase().startsWith("omx.hisi")) {
                    if (Build.VERSION.SDK_INT >= 29) {
                        mediaFormatCreateVideoFormat.setInteger("vendor.hisi-ext-low-latency-video-dec.video-scene-for-low-latency-req", 1);
                        mediaFormatCreateVideoFormat.setInteger("vendor.hisi-ext-low-latency-video-dec.video-scene-for-low-latency-rdy", -1);
                    }
                } else if (Build.VERSION.SDK_INT >= 30) {
                    mediaFormatCreateVideoFormat.setInteger("low-latency", 1);
                }
            }
            try {
                this.mMediaCodec = MediaCodec.createByCodecName(strFindDecoderName);
            } catch (IOException e2) {
                Logger.e(TAG, "MediaCodec.createByCodecName fail:" + e2.getMessage());
            }
            MediaCodec mediaCodec = this.mMediaCodec;
            if (mediaCodec == null) {
                Logger.e(TAG, "createByCodecName fail : " + strFindDecoderName);
                return -13;
            }
            try {
                if (obj instanceof Surface) {
                    surface = (Surface) obj;
                    mediaCrypto = this.mediaCrypto;
                } else {
                    mediaCrypto = this.mediaCrypto;
                    surface = null;
                }
                mediaCodec.configure(mediaFormatCreateVideoFormat, surface, mediaCrypto, 0);
                return 0;
            } catch (Exception e3) {
                String str2 = TAG;
                Logger.e(str2, "configure fail : " + e3.getMessage());
                if (!z2) {
                    return -14;
                }
                Logger.e(str2, "try configure again with lowLatency=false");
                i5 = i6;
                z2 = false;
            }
        }
    }

    @NativeUsed
    public int dequeueInputBufferIndex(long j2) throws MediaCodec.CryptoException {
        ByteBuffer inputBuffer;
        if (this.mMediaCodec == null) {
            return -1;
        }
        try {
            if (!this.mCodecSpecificDataMap.isEmpty()) {
                for (String str : this.mCodecSpecificDataMap.keySet()) {
                    int iDequeueInputBuffer = this.mMediaCodec.dequeueInputBuffer(j2);
                    if (iDequeueInputBuffer < 0 || (inputBuffer = getInputBuffer(iDequeueInputBuffer)) == null) {
                        return -11;
                    }
                    byte[] bArr = this.mCodecSpecificDataMap.get(str);
                    if (bArr != null) {
                        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length);
                        byteBufferAllocate.put(bArr);
                        byteBufferAllocate.flip();
                        inputBuffer.put(byteBufferAllocate);
                        this.mMediaCodec.queueInputBuffer(iDequeueInputBuffer, 0, byteBufferAllocate.limit(), 0L, 2);
                    }
                }
                this.mCodecSpecificDataMap.clear();
            }
            int iDequeueInputBuffer2 = this.mMediaCodec.dequeueInputBuffer(j2);
            if (iDequeueInputBuffer2 >= 0) {
                return iDequeueInputBuffer2;
            }
            return -11;
        } catch (Exception e2) {
            Logger.e(TAG, "dequeueInputBufferIndex fail " + e2.getMessage());
            return -1;
        }
    }

    @NativeUsed
    public int dequeueOutputBufferIndex(long j2) {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            return -1;
        }
        try {
            int iDequeueOutputBuffer = mediaCodec.dequeueOutputBuffer(this.mBufferInfo, j2);
            if (iDequeueOutputBuffer >= 0) {
                return iDequeueOutputBuffer;
            }
            if (iDequeueOutputBuffer == -1) {
                return -11;
            }
            if (iDequeueOutputBuffer == -2 || iDequeueOutputBuffer == -3) {
                return iDequeueOutputBuffer;
            }
            return -1;
        } catch (Exception e2) {
            Logger.e(TAG, "dequeueOutputBufferIndex fail " + e2.getMessage());
            return -1;
        }
    }

    @NativeUsed
    public int flush() {
        String str = TAG;
        Logger.i(str, "--> flush start");
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            Logger.e(str, "mMediaCodec  null ");
            return -1;
        }
        try {
            mediaCodec.flush();
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, "flush  fail " + e2.getMessage());
            return 0;
        }
    }

    @NativeUsed
    public Object getDecoderName() {
        return this.mDecoderName;
    }

    @NativeUsed
    public Object getOutBuffer(int i2) {
        if (i2 < 0) {
            return null;
        }
        try {
            return this.mMediaCodec.getOutputBuffer(i2);
        } catch (Exception e2) {
            Logger.e(TAG, "getOutBuffer fail " + e2.getMessage());
            return null;
        }
    }

    @NativeUsed
    public Object getOutputBufferInfo(int i2) {
        if (i2 == -2) {
            return fillFormatOutputBufferInfo();
        }
        if (i2 >= 0) {
            return fillDecodeBufferInfo(i2);
        }
        return null;
    }

    @NativeUsed
    public int queueInputBuffer(int i2, byte[] bArr, long j2, boolean z2) {
        return queueInputBufferInner(i2, bArr, j2, z2, false, null);
    }

    @NativeUsed
    public int queueSecureInputBuffer(int i2, byte[] bArr, Object obj, long j2, boolean z2) {
        return queueInputBufferInner(i2, bArr, j2, z2, true, obj);
    }

    @NativeUsed
    public int release() {
        Logger.i(TAG, "--> release ");
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            return -1;
        }
        mediaCodec.release();
        this.mMediaCodec = null;
        MediaCrypto mediaCrypto = this.mediaCrypto;
        if (mediaCrypto == null) {
            return 0;
        }
        mediaCrypto.release();
        return 0;
    }

    @NativeUsed
    public int releaseOutputBuffer(int i2, boolean z2) {
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            return -1;
        }
        try {
            mediaCodec.releaseOutputBuffer(i2, z2);
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, "releaseOutputBuffer fail index=" + i2 + " render=" + z2 + " " + e2.getMessage());
            return -1;
        }
    }

    @NativeUsed
    public void setCodecSpecificData(Object obj) {
        Logger.d(TAG, "--> setCodecSpecificData datas " + obj);
        this.mCodecSpecificDataMap.clear();
        if (obj == null) {
            return;
        }
        this.mCodecSpecificDataMap.putAll((LinkedHashMap) obj);
    }

    @NativeUsed
    public boolean setDrmInfo(String str, byte[] bArr) {
        Logger.d(TAG, "--> setDrmInfo uuid " + str);
        try {
            this.mediaCrypto = new MediaCrypto(UUID.fromString(str), bArr);
            return true;
        } catch (Exception e2) {
            Logger.e(TAG, "createMediaCrypto failed: " + e2.getMessage());
            return false;
        }
    }

    @NativeUsed
    public void setForceInsecureDecoder(boolean z2) {
        Logger.d(TAG, "--> setForceInsecureDecoder  " + z2);
        this.forceInsecureDecoder = z2;
    }

    @NativeUsed
    public int start() {
        String str = TAG;
        Logger.i(str, "--> start ");
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            Logger.e(str, "mMediaCodec  null ");
            return -1;
        }
        try {
            mediaCodec.start();
            this.started = true;
            this.mBufferInfo = new MediaCodec.BufferInfo();
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, this.mMediaCodec.getName() + " start fail : " + e2.getMessage());
            return -1;
        }
    }

    public int stop() {
        Logger.i(TAG, "--> stop start");
        MediaCodec mediaCodec = this.mMediaCodec;
        if (mediaCodec == null) {
            return -1;
        }
        if (!this.started) {
            return 0;
        }
        try {
            mediaCodec.stop();
            return 0;
        } catch (Exception e2) {
            Logger.e(TAG, "stop fail " + e2.getMessage());
            return -1;
        }
    }

    private String getDecoderName(MediaFormat mediaFormat, boolean z2) {
        List<MediaCodecInfo> codecInfos = MediaCodecUtils.getCodecInfos(this.mMime, z2, mediaFormat);
        String notBlackCodecName = getNotBlackCodecName(codecInfos);
        return (this.mediaCrypto == null || !TextUtils.isEmpty(notBlackCodecName) || codecInfos.isEmpty()) ? notBlackCodecName : codecInfos.get(0).getName();
    }
}
