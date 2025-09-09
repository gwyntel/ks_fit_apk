package com.cicada.player.utils.media;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.util.Range;
import androidx.media3.common.MimeTypes;
import com.cicada.player.utils.Logger;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class MediaCodecUtils {
    private static final String TAG = "MediaCodecUtils";
    private static List<MediaCodecInfo> allDecoders;

    public static synchronized List<MediaCodecInfo> getCodecInfos(String str, boolean z2, MediaFormat mediaFormat) {
        ArrayList arrayList;
        try {
            if (allDecoders == null) {
                allDecoders = getDeviceDecodecs();
            }
            arrayList = new ArrayList();
            for (MediaCodecInfo mediaCodecInfo : allDecoders) {
                String codecMimeType = getCodecMimeType(mediaCodecInfo, str);
                if (codecMimeType != null) {
                    MediaCodecInfo.CodecCapabilities capabilitiesForType = mediaCodecInfo.getCapabilitiesForType(codecMimeType);
                    if (isSecureSupport(z2, capabilitiesForType, codecMimeType) && isFormatSupport(mediaFormat, capabilitiesForType, codecMimeType)) {
                        arrayList.add(mediaCodecInfo);
                    }
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    private static String getCodecMimeType(MediaCodecInfo mediaCodecInfo, String str) {
        for (String str2 : mediaCodecInfo.getSupportedTypes()) {
            if (str2.equalsIgnoreCase(str)) {
                return str2;
            }
        }
        return null;
    }

    private static List<MediaCodecInfo> getDeviceDecodecs() {
        ArrayList arrayList = new ArrayList();
        for (MediaCodecInfo mediaCodecInfo : new MediaCodecList(1).getCodecInfos()) {
            if (!mediaCodecInfo.isEncoder()) {
                arrayList.add(mediaCodecInfo);
            }
        }
        return arrayList;
    }

    private static int getFormatInteger(MediaFormat mediaFormat, String str, int i2) {
        return mediaFormat.containsKey(str) ? mediaFormat.getInteger(str) : i2;
    }

    public static synchronized boolean isDolbyAudioSupport() {
        boolean z2;
        String str;
        try {
            Logger.i(TAG, "isDolbyAudioSupport begin");
            boolean z3 = false;
            try {
                if (allDecoders == null) {
                    allDecoders = getDeviceDecodecs();
                }
                z2 = false;
                for (MediaCodecInfo mediaCodecInfo : allDecoders) {
                    try {
                        for (String str2 : mediaCodecInfo.getSupportedTypes()) {
                            String str3 = TAG;
                            Logger.i(str3, "decoder mime:" + str2 + ", name:" + mediaCodecInfo.getName());
                            if (!str2.equals(MimeTypes.AUDIO_AC4) && str2.equals(MimeTypes.AUDIO_E_AC3)) {
                                if (mediaCodecInfo.getCapabilitiesForType(str2) != null) {
                                    MediaCodecInfo.AudioCapabilities audioCapabilities = mediaCodecInfo.getCapabilitiesForType(str2).getAudioCapabilities();
                                    int[] supportedSampleRates = audioCapabilities.getSupportedSampleRates();
                                    if (audioCapabilities == null || supportedSampleRates == null) {
                                        str = "decoder mime:audio/eac3 getAudioCapabilities is null";
                                    } else {
                                        String str4 = new String("");
                                        for (int i2 : supportedSampleRates) {
                                            str4 = (str4 + String.valueOf(i2)) + ", ";
                                        }
                                        Logger.i(TAG, "find dolby decodeer, cap:" + audioCapabilities.getMaxInputChannelCount() + "," + str4);
                                        z2 = true;
                                    }
                                } else {
                                    str = "decoder mime:audio/eac3 getCapabilitiesForType is null";
                                }
                                Logger.e(str3, str);
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        z3 = z2;
                        Logger.e(TAG, "found exception: " + e.getMessage());
                        z2 = z3;
                        return z2;
                    }
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th) {
            throw th;
        }
        return z2;
    }

    private static boolean isFormatSupport(MediaFormat mediaFormat, MediaCodecInfo.CodecCapabilities codecCapabilities, String str) {
        boolean zStartsWith = str.startsWith("video");
        boolean zStartsWith2 = str.startsWith("audio");
        boolean z2 = false;
        if (!zStartsWith) {
            if (!zStartsWith2) {
                return false;
            }
            MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
            if (audioCapabilities == null) {
                Logger.e(TAG, "audio format not support! audioCapabilities == null");
                return false;
            }
            int formatInteger = getFormatInteger(mediaFormat, "sample-rate", -1);
            int formatInteger2 = getFormatInteger(mediaFormat, "channel-count", -1);
            boolean z3 = formatInteger == -1 || audioCapabilities.isSampleRateSupported(formatInteger);
            int maxInputChannelCount = audioCapabilities.getMaxInputChannelCount();
            boolean z4 = z3 && (formatInteger2 == -1 || maxInputChannelCount >= formatInteger2);
            if (!z4) {
                Logger.e(TAG, "audio format not support! sampleRate=" + formatInteger + ", supportedSampleRates=" + Arrays.toString(audioCapabilities.getSupportedSampleRates()) + ", channelCount=" + formatInteger2 + ", maxInputChannelCount=" + maxInputChannelCount);
            }
            return z4;
        }
        int formatInteger3 = getFormatInteger(mediaFormat, ViewHierarchyConstants.DIMENSION_WIDTH_KEY, -1);
        int formatInteger4 = getFormatInteger(mediaFormat, ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, -1);
        if (formatInteger3 <= 0 || formatInteger4 <= 0) {
            return true;
        }
        int iMax = Math.max(formatInteger3, formatInteger4);
        int iMin = Math.min(formatInteger3, formatInteger4);
        MediaCodecInfo.VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            Logger.e(TAG, "video format not support! videoCapabilities == null");
        } else {
            Range<Integer> supportedWidths = videoCapabilities.getSupportedWidths();
            Range<Integer> supportedHeights = videoCapabilities.getSupportedHeights();
            boolean zContains = supportedWidths.contains((Range<Integer>) Integer.valueOf(iMax));
            boolean zContains2 = supportedHeights.contains((Range<Integer>) Integer.valueOf(iMin));
            if (zContains && zContains2) {
                z2 = true;
            }
            if (!z2) {
                Logger.e(TAG, "video format not support! width[" + iMax + "] not in widthRange [" + supportedWidths.getLower() + ", " + supportedWidths.getUpper() + "] or height[" + iMin + "] not in heightRange[" + supportedHeights.getLower() + ", " + supportedHeights.getUpper() + "]");
            }
            if (!z2) {
                Logger.w(TAG, "Still mark isFormatSupported = true, as some devices claim not support but can run actually");
                return true;
            }
        }
        return z2;
    }

    private static boolean isSecureSupport(boolean z2, MediaCodecInfo.CodecCapabilities codecCapabilities, String str) {
        boolean zIsFeatureSupported = codecCapabilities.isFeatureSupported("secure-playback");
        boolean zIsFeatureRequired = codecCapabilities.isFeatureRequired("secure-playback");
        if (z2 || !zIsFeatureRequired) {
            return !z2 || zIsFeatureSupported;
        }
        return false;
    }
}
