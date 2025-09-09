package androidx.media3.exoplayer.rtsp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.Util;
import androidx.media3.container.NalUnitUtil;
import androidx.media3.extractor.AacUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/* loaded from: classes.dex */
final class RtspMediaTrack {
    private static final String AAC_CODECS_PREFIX = "mp4a.40.";
    private static final int DEFAULT_H263_HEIGHT = 288;
    private static final int DEFAULT_H263_WIDTH = 352;
    private static final int DEFAULT_MP4V_HEIGHT = 288;
    private static final int DEFAULT_MP4V_WIDTH = 352;
    private static final int DEFAULT_VP8_HEIGHT = 240;
    private static final int DEFAULT_VP8_WIDTH = 320;
    private static final int DEFAULT_VP9_HEIGHT = 240;
    private static final int DEFAULT_VP9_WIDTH = 320;
    private static final String GENERIC_CONTROL_ATTR = "*";
    private static final String H264_CODECS_PREFIX = "avc1.";
    private static final String MPEG4_CODECS_PREFIX = "mp4v.";
    private static final int OPUS_CLOCK_RATE = 48000;
    private static final String PARAMETER_AMR_INTERLEAVING = "interleaving";
    private static final String PARAMETER_AMR_OCTET_ALIGN = "octet-align";
    private static final String PARAMETER_H265_SPROP_MAX_DON_DIFF = "sprop-max-don-diff";
    private static final String PARAMETER_H265_SPROP_PPS = "sprop-pps";
    private static final String PARAMETER_H265_SPROP_SPS = "sprop-sps";
    private static final String PARAMETER_H265_SPROP_VPS = "sprop-vps";
    private static final String PARAMETER_MP4A_CONFIG = "config";
    private static final String PARAMETER_MP4A_C_PRESENT = "cpresent";
    private static final String PARAMETER_PROFILE_LEVEL_ID = "profile-level-id";
    private static final String PARAMETER_SPROP_PARAMS = "sprop-parameter-sets";
    public final RtpPayloadFormat payloadFormat;
    public final Uri uri;

    public RtspMediaTrack(RtspHeaders rtspHeaders, MediaDescription mediaDescription, Uri uri) {
        Assertions.checkArgument(mediaDescription.attributes.containsKey("control"), "missing attribute control");
        this.payloadFormat = a(mediaDescription);
        this.uri = extractTrackUri(rtspHeaders, uri, (String) Util.castNonNull(mediaDescription.attributes.get("control")));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    static androidx.media3.exoplayer.rtsp.RtpPayloadFormat a(androidx.media3.exoplayer.rtsp.MediaDescription r13) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 606
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.rtsp.RtspMediaTrack.a(androidx.media3.exoplayer.rtsp.MediaDescription):androidx.media3.exoplayer.rtsp.RtpPayloadFormat");
    }

    private static Uri extractTrackUri(RtspHeaders rtspHeaders, Uri uri, String str) {
        Uri uri2 = Uri.parse(str);
        if (uri2.isAbsolute()) {
            return uri2;
        }
        if (!TextUtils.isEmpty(rtspHeaders.get(RtspHeaders.CONTENT_BASE))) {
            uri = Uri.parse(rtspHeaders.get(RtspHeaders.CONTENT_BASE));
        } else if (!TextUtils.isEmpty(rtspHeaders.get("Content-Location"))) {
            uri = Uri.parse(rtspHeaders.get("Content-Location"));
        }
        return str.equals("*") ? uri : uri.buildUpon().appendEncodedPath(str).build();
    }

    private static byte[] getInitializationDataFromParameterSet(String str) {
        byte[] bArrDecode = Base64.decode(str, 0);
        int length = bArrDecode.length;
        byte[] bArr = NalUnitUtil.NAL_START_CODE;
        byte[] bArr2 = new byte[length + bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(bArrDecode, 0, bArr2, bArr.length, bArrDecode.length);
        return bArr2;
    }

    private static int inferChannelCount(int i2, String str) {
        return i2 != -1 ? i2 : str.equals(MimeTypes.AUDIO_AC3) ? 6 : 1;
    }

    private static AacUtil.Config parseAacStreamMuxConfig(String str) {
        ParsableBitArray parsableBitArray = new ParsableBitArray(Util.getBytesFromHexString(str));
        Assertions.checkArgument(parsableBitArray.readBits(1) == 0, "Only supports audio mux version 0.");
        Assertions.checkArgument(parsableBitArray.readBits(1) == 1, "Only supports allStreamsSameTimeFraming.");
        parsableBitArray.skipBits(6);
        Assertions.checkArgument(parsableBitArray.readBits(4) == 0, "Only supports one program.");
        Assertions.checkArgument(parsableBitArray.readBits(3) == 0, "Only supports one numLayer.");
        try {
            return AacUtil.parseAudioSpecificConfig(parsableBitArray, false);
        } catch (ParserException e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    private static void processAacFmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap, String str, int i2, int i3) {
        String str2 = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        if (str2 == null && str.equals(RtpPayloadFormat.RTP_MEDIA_MPEG4_LATM_AUDIO)) {
            str2 = "30";
        }
        Assertions.checkArgument((str2 == null || str2.isEmpty()) ? false : true, "missing profile-level-id param");
        builder.setCodecs(AAC_CODECS_PREFIX + str2);
        builder.setInitializationData(ImmutableList.of(AacUtil.buildAacLcAudioSpecificConfig(i3, i2)));
    }

    private static void processH264FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_SPROP_PARAMS), "missing sprop parameter");
        String[] strArrSplit = Util.split((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_SPROP_PARAMS)), ",");
        Assertions.checkArgument(strArrSplit.length == 2, "empty sprop value");
        ImmutableList immutableListOf = ImmutableList.of(getInitializationDataFromParameterSet(strArrSplit[0]), getInitializationDataFromParameterSet(strArrSplit[1]));
        builder.setInitializationData(immutableListOf);
        byte[] bArr = immutableListOf.get(0);
        NalUnitUtil.SpsData spsNalUnit = NalUnitUtil.parseSpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(spsNalUnit.pixelWidthHeightRatio);
        builder.setHeight(spsNalUnit.height);
        builder.setWidth(spsNalUnit.width);
        builder.setColorInfo(new ColorInfo.Builder().setColorSpace(spsNalUnit.colorSpace).setColorRange(spsNalUnit.colorRange).setColorTransfer(spsNalUnit.colorTransfer).setLumaBitdepth(spsNalUnit.bitDepthLumaMinus8 + 8).setChromaBitdepth(spsNalUnit.bitDepthChromaMinus8 + 8).build());
        String str = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        if (str == null) {
            builder.setCodecs(CodecSpecificDataUtil.buildAvcCodecString(spsNalUnit.profileIdc, spsNalUnit.constraintsFlagsAndReservedZero2Bits, spsNalUnit.levelIdc));
            return;
        }
        builder.setCodecs(H264_CODECS_PREFIX + str);
    }

    private static void processH265FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) throws NumberFormatException {
        if (immutableMap.containsKey(PARAMETER_H265_SPROP_MAX_DON_DIFF)) {
            int i2 = Integer.parseInt((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_MAX_DON_DIFF)));
            Assertions.checkArgument(i2 == 0, "non-zero sprop-max-don-diff " + i2 + " is not supported");
        }
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_VPS), "missing sprop-vps parameter");
        String str = (String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_VPS));
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_SPS), "missing sprop-sps parameter");
        String str2 = (String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_SPS));
        Assertions.checkArgument(immutableMap.containsKey(PARAMETER_H265_SPROP_PPS), "missing sprop-pps parameter");
        ImmutableList immutableListOf = ImmutableList.of(getInitializationDataFromParameterSet(str), getInitializationDataFromParameterSet(str2), getInitializationDataFromParameterSet((String) Assertions.checkNotNull(immutableMap.get(PARAMETER_H265_SPROP_PPS))));
        builder.setInitializationData(immutableListOf);
        byte[] bArr = immutableListOf.get(1);
        NalUnitUtil.H265SpsData h265SpsNalUnit = NalUnitUtil.parseH265SpsNalUnit(bArr, NalUnitUtil.NAL_START_CODE.length, bArr.length);
        builder.setPixelWidthHeightRatio(h265SpsNalUnit.pixelWidthHeightRatio);
        builder.setHeight(h265SpsNalUnit.height).setWidth(h265SpsNalUnit.width);
        builder.setColorInfo(new ColorInfo.Builder().setColorSpace(h265SpsNalUnit.colorSpace).setColorRange(h265SpsNalUnit.colorRange).setColorTransfer(h265SpsNalUnit.colorTransfer).setLumaBitdepth(h265SpsNalUnit.bitDepthLumaMinus8 + 8).setChromaBitdepth(h265SpsNalUnit.bitDepthChromaMinus8 + 8).build());
        builder.setCodecs(CodecSpecificDataUtil.buildHevcCodecString(h265SpsNalUnit.generalProfileSpace, h265SpsNalUnit.generalTierFlag, h265SpsNalUnit.generalProfileIdc, h265SpsNalUnit.generalProfileCompatibilityFlags, h265SpsNalUnit.constraintBytes, h265SpsNalUnit.generalLevelIdc));
    }

    private static void processMPEG4FmtpAttribute(Format.Builder builder, ImmutableMap<String, String> immutableMap) {
        String str = immutableMap.get("config");
        if (str != null) {
            byte[] bytesFromHexString = Util.getBytesFromHexString(str);
            builder.setInitializationData(ImmutableList.of(bytesFromHexString));
            Pair<Integer, Integer> videoResolutionFromMpeg4VideoConfig = CodecSpecificDataUtil.getVideoResolutionFromMpeg4VideoConfig(bytesFromHexString);
            builder.setWidth(((Integer) videoResolutionFromMpeg4VideoConfig.first).intValue()).setHeight(((Integer) videoResolutionFromMpeg4VideoConfig.second).intValue());
        } else {
            builder.setWidth(352).setHeight(288);
        }
        String str2 = immutableMap.get(PARAMETER_PROFILE_LEVEL_ID);
        StringBuilder sb = new StringBuilder();
        sb.append(MPEG4_CODECS_PREFIX);
        if (str2 == null) {
            str2 = "1";
        }
        sb.append(str2);
        builder.setCodecs(sb.toString());
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || RtspMediaTrack.class != obj.getClass()) {
            return false;
        }
        RtspMediaTrack rtspMediaTrack = (RtspMediaTrack) obj;
        return this.payloadFormat.equals(rtspMediaTrack.payloadFormat) && this.uri.equals(rtspMediaTrack.uri);
    }

    public int hashCode() {
        return ((217 + this.payloadFormat.hashCode()) * 31) + this.uri.hashCode();
    }
}
