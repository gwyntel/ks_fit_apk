package androidx.media3.exoplayer.hls.playlist;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.UriUtil;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.hls.playlist.HlsMediaPlaylist;
import androidx.media3.exoplayer.hls.playlist.HlsMultivariantPlaylist;
import androidx.media3.exoplayer.upstream.ParsingLoadable;
import androidx.media3.extractor.mp4.PsshAtomUtil;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.common.collect.Iterables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.ES6Iterator;

@UnstableApi
/* loaded from: classes.dex */
public final class HlsPlaylistParser implements ParsingLoadable.Parser<HlsPlaylist> {
    private static final String ATTR_CLOSED_CAPTIONS_NONE = "CLOSED-CAPTIONS=NONE";
    private static final String BOOLEAN_FALSE = "NO";
    private static final String BOOLEAN_TRUE = "YES";
    private static final String KEYFORMAT_IDENTITY = "identity";
    private static final String KEYFORMAT_PLAYREADY = "com.microsoft.playready";
    private static final String KEYFORMAT_WIDEVINE_PSSH_BINARY = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed";
    private static final String KEYFORMAT_WIDEVINE_PSSH_JSON = "com.widevine";
    private static final String LOG_TAG = "HlsPlaylistParser";
    private static final String METHOD_AES_128 = "AES-128";
    private static final String METHOD_NONE = "NONE";
    private static final String METHOD_SAMPLE_AES = "SAMPLE-AES";
    private static final String METHOD_SAMPLE_AES_CENC = "SAMPLE-AES-CENC";
    private static final String METHOD_SAMPLE_AES_CTR = "SAMPLE-AES-CTR";
    private static final String PLAYLIST_HEADER = "#EXTM3U";
    private static final String TAG_BYTERANGE = "#EXT-X-BYTERANGE";
    private static final String TAG_DEFINE = "#EXT-X-DEFINE";
    private static final String TAG_DISCONTINUITY = "#EXT-X-DISCONTINUITY";
    private static final String TAG_DISCONTINUITY_SEQUENCE = "#EXT-X-DISCONTINUITY-SEQUENCE";
    private static final String TAG_ENDLIST = "#EXT-X-ENDLIST";
    private static final String TAG_GAP = "#EXT-X-GAP";
    private static final String TAG_IFRAME = "#EXT-X-I-FRAMES-ONLY";
    private static final String TAG_INDEPENDENT_SEGMENTS = "#EXT-X-INDEPENDENT-SEGMENTS";
    private static final String TAG_INIT_SEGMENT = "#EXT-X-MAP";
    private static final String TAG_I_FRAME_STREAM_INF = "#EXT-X-I-FRAME-STREAM-INF";
    private static final String TAG_KEY = "#EXT-X-KEY";
    private static final String TAG_MEDIA = "#EXT-X-MEDIA";
    private static final String TAG_MEDIA_DURATION = "#EXTINF";
    private static final String TAG_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
    private static final String TAG_PART = "#EXT-X-PART";
    private static final String TAG_PART_INF = "#EXT-X-PART-INF";
    private static final String TAG_PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
    private static final String TAG_PREFIX = "#EXT";
    private static final String TAG_PRELOAD_HINT = "#EXT-X-PRELOAD-HINT";
    private static final String TAG_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
    private static final String TAG_RENDITION_REPORT = "#EXT-X-RENDITION-REPORT";
    private static final String TAG_SERVER_CONTROL = "#EXT-X-SERVER-CONTROL";
    private static final String TAG_SESSION_KEY = "#EXT-X-SESSION-KEY";
    private static final String TAG_SKIP = "#EXT-X-SKIP";
    private static final String TAG_START = "#EXT-X-START";
    private static final String TAG_STREAM_INF = "#EXT-X-STREAM-INF";
    private static final String TAG_TARGET_DURATION = "#EXT-X-TARGETDURATION";
    private static final String TAG_VERSION = "#EXT-X-VERSION";
    private static final String TYPE_AUDIO = "AUDIO";
    private static final String TYPE_CLOSED_CAPTIONS = "CLOSED-CAPTIONS";
    private static final String TYPE_MAP = "MAP";
    private static final String TYPE_PART = "PART";
    private static final String TYPE_SUBTITLES = "SUBTITLES";
    private static final String TYPE_VIDEO = "VIDEO";
    private final HlsMultivariantPlaylist multivariantPlaylist;

    @Nullable
    private final HlsMediaPlaylist previousMediaPlaylist;
    private static final Pattern REGEX_AVERAGE_BANDWIDTH = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_VIDEO = Pattern.compile("VIDEO=\"(.+?)\"");
    private static final Pattern REGEX_AUDIO = Pattern.compile("AUDIO=\"(.+?)\"");
    private static final Pattern REGEX_SUBTITLES = Pattern.compile("SUBTITLES=\"(.+?)\"");
    private static final Pattern REGEX_CLOSED_CAPTIONS = Pattern.compile("CLOSED-CAPTIONS=\"(.+?)\"");
    private static final Pattern REGEX_BANDWIDTH = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_CHANNELS = Pattern.compile("CHANNELS=\"(.+?)\"");
    private static final Pattern REGEX_CODECS = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern REGEX_RESOLUTION = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern REGEX_FRAME_RATE = Pattern.compile("FRAME-RATE=([\\d\\.]+)\\b");
    private static final Pattern REGEX_TARGET_DURATION = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern REGEX_ATTR_DURATION = Pattern.compile("DURATION=([\\d\\.]+)\\b");
    private static final Pattern REGEX_PART_TARGET_DURATION = Pattern.compile("PART-TARGET=([\\d\\.]+)\\b");
    private static final Pattern REGEX_VERSION = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final Pattern REGEX_PLAYLIST_TYPE = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern REGEX_CAN_SKIP_UNTIL = Pattern.compile("CAN-SKIP-UNTIL=([\\d\\.]+)\\b");
    private static final Pattern REGEX_CAN_SKIP_DATE_RANGES = compileBooleanAttrPattern("CAN-SKIP-DATERANGES");
    private static final Pattern REGEX_SKIPPED_SEGMENTS = Pattern.compile("SKIPPED-SEGMENTS=(\\d+)\\b");
    private static final Pattern REGEX_HOLD_BACK = Pattern.compile("[:|,]HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern REGEX_PART_HOLD_BACK = Pattern.compile("PART-HOLD-BACK=([\\d\\.]+)\\b");
    private static final Pattern REGEX_CAN_BLOCK_RELOAD = compileBooleanAttrPattern("CAN-BLOCK-RELOAD");
    private static final Pattern REGEX_MEDIA_SEQUENCE = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern REGEX_MEDIA_TITLE = Pattern.compile("#EXTINF:[\\d\\.]+\\b,(.+)");
    private static final Pattern REGEX_LAST_MSN = Pattern.compile("LAST-MSN=(\\d+)\\b");
    private static final Pattern REGEX_LAST_PART = Pattern.compile("LAST-PART=(\\d+)\\b");
    private static final Pattern REGEX_TIME_OFFSET = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern REGEX_BYTERANGE = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern REGEX_ATTR_BYTERANGE = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern REGEX_BYTERANGE_START = Pattern.compile("BYTERANGE-START=(\\d+)\\b");
    private static final Pattern REGEX_BYTERANGE_LENGTH = Pattern.compile("BYTERANGE-LENGTH=(\\d+)\\b");
    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(NONE|AES-128|SAMPLE-AES|SAMPLE-AES-CENC|SAMPLE-AES-CTR)\\s*(?:,|$)");
    private static final Pattern REGEX_KEYFORMAT = Pattern.compile("KEYFORMAT=\"(.+?)\"");
    private static final Pattern REGEX_KEYFORMATVERSIONS = Pattern.compile("KEYFORMATVERSIONS=\"(.+?)\"");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern REGEX_TYPE = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern REGEX_PRELOAD_HINT_TYPE = Pattern.compile("TYPE=(PART|MAP)");
    private static final Pattern REGEX_LANGUAGE = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern REGEX_NAME = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern REGEX_GROUP_ID = Pattern.compile("GROUP-ID=\"(.+?)\"");
    private static final Pattern REGEX_CHARACTERISTICS = Pattern.compile("CHARACTERISTICS=\"(.+?)\"");
    private static final Pattern REGEX_INSTREAM_ID = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern REGEX_AUTOSELECT = compileBooleanAttrPattern("AUTOSELECT");
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern(MessengerShareContentUtility.PREVIEW_DEFAULT);
    private static final Pattern REGEX_FORCED = compileBooleanAttrPattern("FORCED");
    private static final Pattern REGEX_INDEPENDENT = compileBooleanAttrPattern("INDEPENDENT");
    private static final Pattern REGEX_GAP = compileBooleanAttrPattern("GAP");
    private static final Pattern REGEX_PRECISE = compileBooleanAttrPattern("PRECISE");
    private static final Pattern REGEX_VALUE = Pattern.compile("VALUE=\"(.+?)\"");
    private static final Pattern REGEX_IMPORT = Pattern.compile("IMPORT=\"(.+?)\"");
    private static final Pattern REGEX_VARIABLE_REFERENCE = Pattern.compile("\\{\\$([a-zA-Z0-9\\-_]+)\\}");

    public static final class DeltaUpdateException extends IOException {
    }

    private static class LineIterator {
        private final Queue<String> extraLines;

        @Nullable
        private String next;
        private final BufferedReader reader;

        public LineIterator(Queue<String> queue, BufferedReader bufferedReader) {
            this.extraLines = queue;
            this.reader = bufferedReader;
        }

        @EnsuresNonNullIf(expression = {ES6Iterator.NEXT_METHOD}, result = true)
        public boolean hasNext() throws IOException {
            String strTrim;
            if (this.next != null) {
                return true;
            }
            if (!this.extraLines.isEmpty()) {
                this.next = (String) Assertions.checkNotNull(this.extraLines.poll());
                return true;
            }
            do {
                String line = this.reader.readLine();
                this.next = line;
                if (line == null) {
                    return false;
                }
                strTrim = line.trim();
                this.next = strTrim;
            } while (strTrim.isEmpty());
            return true;
        }

        public String next() throws IOException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = this.next;
            this.next = null;
            return str;
        }
    }

    public HlsPlaylistParser() {
        this(HlsMultivariantPlaylist.EMPTY, null);
    }

    private static boolean checkPlaylistHeader(BufferedReader bufferedReader) throws IOException {
        int i2 = bufferedReader.read();
        if (i2 == 239) {
            if (bufferedReader.read() != 187 || bufferedReader.read() != 191) {
                return false;
            }
            i2 = bufferedReader.read();
        }
        int iSkipIgnorableWhitespace = skipIgnorableWhitespace(bufferedReader, true, i2);
        for (int i3 = 0; i3 < 7; i3++) {
            if (iSkipIgnorableWhitespace != PLAYLIST_HEADER.charAt(i3)) {
                return false;
            }
            iSkipIgnorableWhitespace = bufferedReader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(bufferedReader, false, iSkipIgnorableWhitespace));
    }

    private static Pattern compileBooleanAttrPattern(String str) {
        return Pattern.compile(str + "=(NO|" + BOOLEAN_TRUE + ")");
    }

    private static DrmInitData getPlaylistProtectionSchemes(@Nullable String str, DrmInitData.SchemeData[] schemeDataArr) {
        DrmInitData.SchemeData[] schemeDataArr2 = new DrmInitData.SchemeData[schemeDataArr.length];
        for (int i2 = 0; i2 < schemeDataArr.length; i2++) {
            schemeDataArr2[i2] = schemeDataArr[i2].copyWithData(null);
        }
        return new DrmInitData(str, schemeDataArr2);
    }

    @Nullable
    private static String getSegmentEncryptionIV(long j2, @Nullable String str, @Nullable String str2) {
        if (str == null) {
            return null;
        }
        return str2 != null ? str2 : Long.toHexString(j2);
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithAudioGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.audioGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithSubtitleGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.subtitleGroupId)) {
                return variant;
            }
        }
        return null;
    }

    @Nullable
    private static HlsMultivariantPlaylist.Variant getVariantWithVideoGroup(ArrayList<HlsMultivariantPlaylist.Variant> arrayList, String str) {
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            HlsMultivariantPlaylist.Variant variant = arrayList.get(i2);
            if (str.equals(variant.videoGroupId)) {
                return variant;
            }
        }
        return null;
    }

    private static double parseDoubleAttr(String str, Pattern pattern) throws ParserException {
        return Double.parseDouble(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    @Nullable
    private static DrmInitData.SchemeData parseDrmSchemeData(String str, String str2, Map<String, String> map) throws ParserException {
        String optionalStringAttr = parseOptionalStringAttr(str, REGEX_KEYFORMATVERSIONS, "1", map);
        if (KEYFORMAT_WIDEVINE_PSSH_BINARY.equals(str2)) {
            String stringAttr = parseStringAttr(str, REGEX_URI, map);
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, "video/mp4", Base64.decode(stringAttr.substring(stringAttr.indexOf(44)), 0));
        }
        if (KEYFORMAT_WIDEVINE_PSSH_JSON.equals(str2)) {
            return new DrmInitData.SchemeData(C.WIDEVINE_UUID, "hls", Util.getUtf8Bytes(str));
        }
        if (!KEYFORMAT_PLAYREADY.equals(str2) || !"1".equals(optionalStringAttr)) {
            return null;
        }
        String stringAttr2 = parseStringAttr(str, REGEX_URI, map);
        byte[] bArrDecode = Base64.decode(stringAttr2.substring(stringAttr2.indexOf(44)), 0);
        UUID uuid = C.PLAYREADY_UUID;
        return new DrmInitData.SchemeData(uuid, "video/mp4", PsshAtomUtil.buildPsshAtom(uuid, bArrDecode));
    }

    private static String parseEncryptionScheme(String str) {
        return (METHOD_SAMPLE_AES_CENC.equals(str) || METHOD_SAMPLE_AES_CTR.equals(str)) ? C.CENC_TYPE_cenc : C.CENC_TYPE_cbcs;
    }

    private static int parseIntAttr(String str, Pattern pattern) throws ParserException {
        return Integer.parseInt(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    private static long parseLongAttr(String str, Pattern pattern) throws ParserException {
        return Long.parseLong(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static HlsMediaPlaylist parseMediaPlaylist(HlsMultivariantPlaylist hlsMultivariantPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist, LineIterator lineIterator, String str) throws IOException, NumberFormatException {
        ArrayList arrayList;
        ArrayList arrayList2;
        String str2;
        long j2;
        boolean z2;
        int i2;
        HlsMediaPlaylist.Part part;
        int i3;
        String optionalStringAttr;
        long j3;
        long j4;
        long j5;
        long j6;
        boolean z3;
        Object drmInitData;
        HlsMultivariantPlaylist hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
        HlsMediaPlaylist hlsMediaPlaylist2 = hlsMediaPlaylist;
        boolean z4 = hlsMultivariantPlaylist2.hasIndependentSegments;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        HlsMediaPlaylist.ServerControl serverControl = new HlsMediaPlaylist.ServerControl(C.TIME_UNSET, false, C.TIME_UNSET, C.TIME_UNSET, false);
        TreeMap treeMap = new TreeMap();
        boolean z5 = false;
        String str3 = "";
        boolean z6 = z4;
        HlsMediaPlaylist.ServerControl serverControl2 = serverControl;
        int i4 = 0;
        boolean optionalBooleanAttribute = false;
        boolean z7 = false;
        int i5 = 0;
        boolean z8 = false;
        boolean z9 = false;
        int i6 = 0;
        boolean z10 = false;
        String optionalStringAttr2 = str3;
        String stringAttr = null;
        long doubleAttr = C.TIME_UNSET;
        long jMsToUs = 0;
        long j7 = 0;
        int intAttr = 1;
        long intAttr2 = C.TIME_UNSET;
        long doubleAttr2 = C.TIME_UNSET;
        DrmInitData playlistProtectionSchemes = null;
        long j8 = 0;
        Object obj = null;
        long j9 = 0;
        long j10 = -1;
        String str4 = null;
        String encryptionScheme = null;
        long j11 = 0;
        long longAttr = 0;
        HlsMediaPlaylist.Segment segment = null;
        long timeSecondsToUs = 0;
        long j12 = 0;
        ArrayList arrayList7 = arrayList4;
        HlsMediaPlaylist.Part part2 = null;
        while (lineIterator.hasNext()) {
            String next = lineIterator.next();
            if (next.startsWith(TAG_PREFIX)) {
                arrayList6.add(next);
            }
            if (next.startsWith(TAG_PLAYLIST_TYPE)) {
                String stringAttr2 = parseStringAttr(next, REGEX_PLAYLIST_TYPE, map);
                if ("VOD".equals(stringAttr2)) {
                    i4 = 1;
                } else if ("EVENT".equals(stringAttr2)) {
                    i4 = 2;
                }
            } else if (next.equals(TAG_IFRAME)) {
                z10 = true;
            } else if (next.startsWith(TAG_START)) {
                doubleAttr = (long) (parseDoubleAttr(next, REGEX_TIME_OFFSET) * 1000000.0d);
                optionalBooleanAttribute = parseOptionalBooleanAttribute(next, REGEX_PRECISE, z5);
            } else if (next.startsWith(TAG_SERVER_CONTROL)) {
                serverControl2 = parseServerControl(next);
            } else if (next.startsWith(TAG_PART_INF)) {
                doubleAttr2 = (long) (parseDoubleAttr(next, REGEX_PART_TARGET_DURATION) * 1000000.0d);
            } else if (next.startsWith(TAG_INIT_SEGMENT)) {
                String stringAttr3 = parseStringAttr(next, REGEX_URI, map);
                String optionalStringAttr3 = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE, map);
                if (optionalStringAttr3 != null) {
                    String[] strArrSplit = Util.split(optionalStringAttr3, "@");
                    j10 = Long.parseLong(strArrSplit[z5 ? 1 : 0]);
                    if (strArrSplit.length > 1) {
                        j8 = Long.parseLong(strArrSplit[1]);
                    }
                }
                if (j10 == -1) {
                    j8 = 0;
                }
                String str5 = str4;
                if (stringAttr != null && str5 == null) {
                    throw ParserException.createForMalformedManifest("The encryption IV attribute must be present when an initialization segment is encrypted with METHOD=AES-128.", null);
                }
                segment = new HlsMediaPlaylist.Segment(stringAttr3, j8, j10, stringAttr, str5);
                if (j10 != -1) {
                    j8 += j10;
                }
                str4 = str5;
                j10 = -1;
            } else {
                String str6 = str4;
                if (next.startsWith(TAG_TARGET_DURATION)) {
                    intAttr2 = 1000000 * parseIntAttr(next, REGEX_TARGET_DURATION);
                } else {
                    if (next.startsWith(TAG_MEDIA_SEQUENCE)) {
                        longAttr = parseLongAttr(next, REGEX_MEDIA_SEQUENCE);
                        str4 = str6;
                        j7 = longAttr;
                    } else if (next.startsWith(TAG_VERSION)) {
                        intAttr = parseIntAttr(next, REGEX_VERSION);
                    } else {
                        if (next.startsWith(TAG_DEFINE)) {
                            String optionalStringAttr4 = parseOptionalStringAttr(next, REGEX_IMPORT, map);
                            if (optionalStringAttr4 != null) {
                                String str7 = hlsMultivariantPlaylist2.variableDefinitions.get(optionalStringAttr4);
                                if (str7 != null) {
                                    map.put(optionalStringAttr4, str7);
                                }
                            } else {
                                map.put(parseStringAttr(next, REGEX_NAME, map), parseStringAttr(next, REGEX_VALUE, map));
                            }
                            arrayList = arrayList7;
                            arrayList2 = arrayList6;
                            str2 = encryptionScheme;
                            j2 = longAttr;
                            z2 = false;
                            i2 = i4;
                        } else if (next.startsWith(TAG_MEDIA_DURATION)) {
                            timeSecondsToUs = parseTimeSecondsToUs(next, REGEX_MEDIA_DURATION);
                            optionalStringAttr2 = parseOptionalStringAttr(next, REGEX_MEDIA_TITLE, str3, map);
                        } else {
                            String str8 = str3;
                            if (next.startsWith(TAG_SKIP)) {
                                int intAttr3 = parseIntAttr(next, REGEX_SKIPPED_SEGMENTS);
                                Assertions.checkState(hlsMediaPlaylist2 != null && arrayList3.isEmpty());
                                int i7 = (int) (j7 - ((HlsMediaPlaylist) Util.castNonNull(hlsMediaPlaylist)).mediaSequence);
                                int i8 = intAttr3 + i7;
                                if (i7 < 0 || i8 > hlsMediaPlaylist2.segments.size()) {
                                    throw new DeltaUpdateException();
                                }
                                str3 = str8;
                                String str9 = str6;
                                long j13 = j11;
                                while (i7 < i8) {
                                    HlsMediaPlaylist.Segment segmentCopyWith = hlsMediaPlaylist2.segments.get(i7);
                                    ArrayList arrayList8 = arrayList7;
                                    ArrayList arrayList9 = arrayList6;
                                    if (j7 != hlsMediaPlaylist2.mediaSequence) {
                                        segmentCopyWith = segmentCopyWith.copyWith(j13, (hlsMediaPlaylist2.discontinuitySequence - i5) + segmentCopyWith.relativeDiscontinuitySequence);
                                    }
                                    arrayList3.add(segmentCopyWith);
                                    j13 += segmentCopyWith.durationUs;
                                    long j14 = segmentCopyWith.byteRangeLength;
                                    if (j14 != -1) {
                                        i3 = i8;
                                        j8 = segmentCopyWith.byteRangeOffset + j14;
                                    } else {
                                        i3 = i8;
                                    }
                                    int i9 = segmentCopyWith.relativeDiscontinuitySequence;
                                    HlsMediaPlaylist.Segment segment2 = segmentCopyWith.initializationSegment;
                                    DrmInitData drmInitData2 = segmentCopyWith.drmInitData;
                                    String str10 = segmentCopyWith.fullSegmentEncryptionKeyUri;
                                    String str11 = segmentCopyWith.encryptionIV;
                                    if (str11 == null || !str11.equals(Long.toHexString(longAttr))) {
                                        str9 = segmentCopyWith.encryptionIV;
                                    }
                                    longAttr++;
                                    i7++;
                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                    obj = drmInitData2;
                                    stringAttr = str10;
                                    j9 = j13;
                                    i8 = i3;
                                    i6 = i9;
                                    segment = segment2;
                                    arrayList7 = arrayList8;
                                    arrayList6 = arrayList9;
                                }
                                hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                j11 = j13;
                                str4 = str9;
                            } else {
                                ArrayList arrayList10 = arrayList7;
                                arrayList2 = arrayList6;
                                str3 = str8;
                                if (next.startsWith(TAG_KEY)) {
                                    String stringAttr4 = parseStringAttr(next, REGEX_METHOD, map);
                                    String optionalStringAttr5 = parseOptionalStringAttr(next, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, map);
                                    if (METHOD_NONE.equals(stringAttr4)) {
                                        treeMap.clear();
                                        optionalStringAttr = null;
                                        stringAttr = null;
                                    } else {
                                        optionalStringAttr = parseOptionalStringAttr(next, REGEX_IV, map);
                                        if (KEYFORMAT_IDENTITY.equals(optionalStringAttr5)) {
                                            if (METHOD_AES_128.equals(stringAttr4)) {
                                                stringAttr = parseStringAttr(next, REGEX_URI, map);
                                            }
                                            hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                                            str4 = optionalStringAttr;
                                        } else {
                                            String str12 = encryptionScheme;
                                            encryptionScheme = str12 == null ? parseEncryptionScheme(stringAttr4) : str12;
                                            DrmInitData.SchemeData drmSchemeData = parseDrmSchemeData(next, optionalStringAttr5, map);
                                            if (drmSchemeData != null) {
                                                treeMap.put(optionalStringAttr5, drmSchemeData);
                                                stringAttr = null;
                                            }
                                        }
                                        stringAttr = null;
                                        hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                                        str4 = optionalStringAttr;
                                    }
                                    obj = stringAttr;
                                    hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                    str4 = optionalStringAttr;
                                } else {
                                    String str13 = encryptionScheme;
                                    if (next.startsWith(TAG_BYTERANGE)) {
                                        String[] strArrSplit2 = Util.split(parseStringAttr(next, REGEX_BYTERANGE, map), "@");
                                        j10 = Long.parseLong(strArrSplit2[0]);
                                        if (strArrSplit2.length > 1) {
                                            j8 = Long.parseLong(strArrSplit2[1]);
                                        }
                                    } else if (next.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                                        i5 = Integer.parseInt(next.substring(next.indexOf(58) + 1));
                                        hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                                        encryptionScheme = str13;
                                        str4 = str6;
                                        arrayList7 = arrayList10;
                                        arrayList6 = arrayList2;
                                        z5 = false;
                                        z7 = true;
                                    } else if (next.equals(TAG_DISCONTINUITY)) {
                                        i6++;
                                    } else {
                                        if (next.startsWith(TAG_PROGRAM_DATE_TIME)) {
                                            if (jMsToUs == 0) {
                                                jMsToUs = Util.msToUs(Util.parseXsDateTime(next.substring(next.indexOf(58) + 1))) - j11;
                                            } else {
                                                i2 = i4;
                                                str2 = str13;
                                            }
                                        } else if (next.equals(TAG_GAP)) {
                                            hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                                            encryptionScheme = str13;
                                            str4 = str6;
                                            arrayList7 = arrayList10;
                                            arrayList6 = arrayList2;
                                            z5 = false;
                                            z9 = true;
                                        } else if (next.equals(TAG_INDEPENDENT_SEGMENTS)) {
                                            hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                                            encryptionScheme = str13;
                                            str4 = str6;
                                            arrayList7 = arrayList10;
                                            arrayList6 = arrayList2;
                                            z5 = false;
                                            z6 = true;
                                        } else if (next.equals(TAG_ENDLIST)) {
                                            hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                            hlsMediaPlaylist2 = hlsMediaPlaylist;
                                            encryptionScheme = str13;
                                            str4 = str6;
                                            arrayList7 = arrayList10;
                                            arrayList6 = arrayList2;
                                            z5 = false;
                                            z8 = true;
                                        } else if (next.startsWith(TAG_RENDITION_REPORT)) {
                                            i2 = i4;
                                            str2 = str13;
                                            arrayList5.add(new HlsMediaPlaylist.RenditionReport(Uri.parse(UriUtil.resolve(str, parseStringAttr(next, REGEX_URI, map))), parseOptionalLongAttr(next, REGEX_LAST_MSN, -1L), parseOptionalIntAttr(next, REGEX_LAST_PART, -1)));
                                        } else {
                                            i2 = i4;
                                            str2 = str13;
                                            if (!next.startsWith(TAG_PRELOAD_HINT)) {
                                                j2 = longAttr;
                                                if (next.startsWith(TAG_PART)) {
                                                    String segmentEncryptionIV = getSegmentEncryptionIV(j2, stringAttr, str6);
                                                    String stringAttr5 = parseStringAttr(next, REGEX_URI, map);
                                                    long doubleAttr3 = (long) (parseDoubleAttr(next, REGEX_ATTR_DURATION) * 1000000.0d);
                                                    HlsMediaPlaylist.Part part3 = part2;
                                                    boolean optionalBooleanAttribute2 = parseOptionalBooleanAttribute(next, REGEX_INDEPENDENT, false) | (z6 && arrayList10.isEmpty());
                                                    boolean optionalBooleanAttribute3 = parseOptionalBooleanAttribute(next, REGEX_GAP, false);
                                                    String optionalStringAttr6 = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE, map);
                                                    if (optionalStringAttr6 != null) {
                                                        String[] strArrSplit3 = Util.split(optionalStringAttr6, "@");
                                                        j4 = Long.parseLong(strArrSplit3[0]);
                                                        if (strArrSplit3.length > 1) {
                                                            j12 = Long.parseLong(strArrSplit3[1]);
                                                        }
                                                        j3 = -1;
                                                    } else {
                                                        j3 = -1;
                                                        j4 = -1;
                                                    }
                                                    if (j4 == j3) {
                                                        j12 = 0;
                                                    }
                                                    if (obj == null && !treeMap.isEmpty()) {
                                                        DrmInitData.SchemeData[] schemeDataArr = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                        DrmInitData drmInitData3 = new DrmInitData(str2, schemeDataArr);
                                                        if (playlistProtectionSchemes == null) {
                                                            playlistProtectionSchemes = getPlaylistProtectionSchemes(str2, schemeDataArr);
                                                        }
                                                        obj = drmInitData3;
                                                    }
                                                    arrayList10.add(new HlsMediaPlaylist.Part(stringAttr5, segment, doubleAttr3, i6, j9, obj, stringAttr, segmentEncryptionIV, j12, j4, optionalBooleanAttribute3, optionalBooleanAttribute2, false));
                                                    j9 += doubleAttr3;
                                                    if (j4 != j3) {
                                                        j12 += j4;
                                                    }
                                                    hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                                    str4 = str6;
                                                    i4 = i2;
                                                    part2 = part3;
                                                    longAttr = j2;
                                                    encryptionScheme = str2;
                                                    arrayList7 = arrayList10;
                                                    arrayList6 = arrayList2;
                                                } else {
                                                    part = part2;
                                                    arrayList = arrayList10;
                                                    if (next.startsWith(MqttTopic.MULTI_LEVEL_WILDCARD)) {
                                                        z2 = false;
                                                        hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                                        str4 = str6;
                                                        i4 = i2;
                                                        part2 = part;
                                                        longAttr = j2;
                                                        encryptionScheme = str2;
                                                        arrayList7 = arrayList;
                                                        arrayList6 = arrayList2;
                                                        z5 = z2;
                                                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                                                    } else {
                                                        String segmentEncryptionIV2 = getSegmentEncryptionIV(j2, stringAttr, str6);
                                                        long j15 = j2 + 1;
                                                        String strReplaceVariableReferences = replaceVariableReferences(next, map);
                                                        HlsMediaPlaylist.Segment segment3 = (HlsMediaPlaylist.Segment) map2.get(strReplaceVariableReferences);
                                                        if (j10 == -1) {
                                                            j5 = 0;
                                                        } else {
                                                            if (z10 && segment == null && segment3 == null) {
                                                                segment3 = new HlsMediaPlaylist.Segment(strReplaceVariableReferences, 0L, j8, null, null);
                                                                map2.put(strReplaceVariableReferences, segment3);
                                                            }
                                                            j5 = j8;
                                                        }
                                                        if (obj != null || treeMap.isEmpty()) {
                                                            j6 = j15;
                                                            z3 = false;
                                                            drmInitData = obj;
                                                        } else {
                                                            j6 = j15;
                                                            z3 = false;
                                                            DrmInitData.SchemeData[] schemeDataArr2 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                            drmInitData = new DrmInitData(str2, schemeDataArr2);
                                                            if (playlistProtectionSchemes == null) {
                                                                playlistProtectionSchemes = getPlaylistProtectionSchemes(str2, schemeDataArr2);
                                                            }
                                                        }
                                                        arrayList3.add(new HlsMediaPlaylist.Segment(strReplaceVariableReferences, segment != null ? segment : segment3, optionalStringAttr2, timeSecondsToUs, i6, j11, drmInitData, stringAttr, segmentEncryptionIV2, j5, j10, z9, arrayList));
                                                        j9 = j11 + timeSecondsToUs;
                                                        arrayList7 = new ArrayList();
                                                        if (j10 != -1) {
                                                            j5 += j10;
                                                        }
                                                        j8 = j5;
                                                        hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                                        z9 = z3;
                                                        str4 = str6;
                                                        obj = drmInitData;
                                                        optionalStringAttr2 = str3;
                                                        j11 = j9;
                                                        i4 = i2;
                                                        part2 = part;
                                                        arrayList6 = arrayList2;
                                                        j10 = -1;
                                                        timeSecondsToUs = 0;
                                                        encryptionScheme = str2;
                                                        longAttr = j6;
                                                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                                                        z5 = z9;
                                                    }
                                                }
                                            } else if (part2 == null && TYPE_PART.equals(parseStringAttr(next, REGEX_PRELOAD_HINT_TYPE, map))) {
                                                String stringAttr6 = parseStringAttr(next, REGEX_URI, map);
                                                long optionalLongAttr = parseOptionalLongAttr(next, REGEX_BYTERANGE_START, -1L);
                                                long optionalLongAttr2 = parseOptionalLongAttr(next, REGEX_BYTERANGE_LENGTH, -1L);
                                                long j16 = longAttr;
                                                String segmentEncryptionIV3 = getSegmentEncryptionIV(j16, stringAttr, str6);
                                                if (obj == null && !treeMap.isEmpty()) {
                                                    DrmInitData.SchemeData[] schemeDataArr3 = (DrmInitData.SchemeData[]) treeMap.values().toArray(new DrmInitData.SchemeData[0]);
                                                    DrmInitData drmInitData4 = new DrmInitData(str2, schemeDataArr3);
                                                    if (playlistProtectionSchemes == null) {
                                                        playlistProtectionSchemes = getPlaylistProtectionSchemes(str2, schemeDataArr3);
                                                    }
                                                    obj = drmInitData4;
                                                }
                                                if (optionalLongAttr == -1 || optionalLongAttr2 != -1) {
                                                    part2 = new HlsMediaPlaylist.Part(stringAttr6, segment, 0L, i6, j9, obj, stringAttr, segmentEncryptionIV3, optionalLongAttr != -1 ? optionalLongAttr : 0L, optionalLongAttr2, false, false, true);
                                                }
                                                hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                                hlsMediaPlaylist2 = hlsMediaPlaylist;
                                                longAttr = j16;
                                                str4 = str6;
                                                arrayList7 = arrayList10;
                                                i4 = i2;
                                                arrayList6 = arrayList2;
                                                encryptionScheme = str2;
                                            }
                                        }
                                        arrayList = arrayList10;
                                        j2 = longAttr;
                                        z2 = false;
                                    }
                                    hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                                    hlsMediaPlaylist2 = hlsMediaPlaylist;
                                    encryptionScheme = str13;
                                    str4 = str6;
                                }
                                arrayList7 = arrayList10;
                                arrayList6 = arrayList2;
                            }
                        }
                        part = part2;
                        hlsMultivariantPlaylist2 = hlsMultivariantPlaylist;
                        str4 = str6;
                        i4 = i2;
                        part2 = part;
                        longAttr = j2;
                        encryptionScheme = str2;
                        arrayList7 = arrayList;
                        arrayList6 = arrayList2;
                        z5 = z2;
                        hlsMediaPlaylist2 = hlsMediaPlaylist;
                    }
                    z5 = false;
                }
                str4 = str6;
                z5 = false;
            }
        }
        int i10 = i4;
        HlsMediaPlaylist.Part part4 = part2;
        ArrayList arrayList11 = arrayList7;
        ArrayList arrayList12 = arrayList6;
        int i11 = z5 ? 1 : 0;
        HashMap map3 = new HashMap();
        for (int i12 = i11; i12 < arrayList5.size(); i12++) {
            HlsMediaPlaylist.RenditionReport renditionReport = (HlsMediaPlaylist.RenditionReport) arrayList5.get(i12);
            long size = renditionReport.lastMediaSequence;
            if (size == -1) {
                size = (j7 + arrayList3.size()) - (arrayList11.isEmpty() ? 1L : 0L);
            }
            int size2 = renditionReport.lastPartIndex;
            if (size2 == -1 && doubleAttr2 != C.TIME_UNSET) {
                size2 = (arrayList11.isEmpty() ? ((HlsMediaPlaylist.Segment) Iterables.getLast(arrayList3)).parts : arrayList11).size() - 1;
            }
            Uri uri = renditionReport.playlistUri;
            map3.put(uri, new HlsMediaPlaylist.RenditionReport(uri, size, size2));
        }
        if (part4 != null) {
            arrayList11.add(part4);
        }
        return new HlsMediaPlaylist(i10, str, arrayList12, doubleAttr, optionalBooleanAttribute, jMsToUs, z7, i5, j7, intAttr, intAttr2, doubleAttr2, z6, z8, jMsToUs != 0, playlistProtectionSchemes, arrayList3, arrayList11, serverControl2, map3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:94:0x0330. Please report as an issue. */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0303  */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r10v4, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static androidx.media3.exoplayer.hls.playlist.HlsMultivariantPlaylist parseMultivariantPlaylist(androidx.media3.exoplayer.hls.playlist.HlsPlaylistParser.LineIterator r37, java.lang.String r38) throws java.io.IOException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.hls.playlist.HlsPlaylistParser.parseMultivariantPlaylist(androidx.media3.exoplayer.hls.playlist.HlsPlaylistParser$LineIterator, java.lang.String):androidx.media3.exoplayer.hls.playlist.HlsMultivariantPlaylist");
    }

    private static boolean parseOptionalBooleanAttribute(String str, Pattern pattern, boolean z2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? BOOLEAN_TRUE.equals(matcher.group(1)) : z2;
    }

    private static double parseOptionalDoubleAttr(String str, Pattern pattern, double d2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Double.parseDouble((String) Assertions.checkNotNull(matcher.group(1))) : d2;
    }

    private static int parseOptionalIntAttr(String str, Pattern pattern, int i2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1))) : i2;
    }

    private static long parseOptionalLongAttr(String str, Pattern pattern, long j2) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find() ? Long.parseLong((String) Assertions.checkNotNull(matcher.group(1))) : j2;
    }

    @Nullable
    private static String parseOptionalStringAttr(String str, Pattern pattern, Map<String, String> map) {
        return parseOptionalStringAttr(str, pattern, null, map);
    }

    private static int parseRoleFlags(String str, Map<String, String> map) {
        String optionalStringAttr = parseOptionalStringAttr(str, REGEX_CHARACTERISTICS, map);
        if (TextUtils.isEmpty(optionalStringAttr)) {
            return 0;
        }
        String[] strArrSplit = Util.split(optionalStringAttr, ",");
        int i2 = Util.contains(strArrSplit, "public.accessibility.describes-video") ? 512 : 0;
        if (Util.contains(strArrSplit, "public.accessibility.transcribes-spoken-dialog")) {
            i2 |= 4096;
        }
        if (Util.contains(strArrSplit, "public.accessibility.describes-music-and-sound")) {
            i2 |= 1024;
        }
        return Util.contains(strArrSplit, "public.easy-to-read") ? i2 | 8192 : i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    private static int parseSelectionFlags(String str) {
        boolean optionalBooleanAttribute = parseOptionalBooleanAttribute(str, REGEX_DEFAULT, false);
        ?? r02 = optionalBooleanAttribute;
        if (parseOptionalBooleanAttribute(str, REGEX_FORCED, false)) {
            r02 = (optionalBooleanAttribute ? 1 : 0) | 2;
        }
        return parseOptionalBooleanAttribute(str, REGEX_AUTOSELECT, false) ? r02 | 4 : r02;
    }

    private static HlsMediaPlaylist.ServerControl parseServerControl(String str) {
        double optionalDoubleAttr = parseOptionalDoubleAttr(str, REGEX_CAN_SKIP_UNTIL, -9.223372036854776E18d);
        long j2 = C.TIME_UNSET;
        long j3 = optionalDoubleAttr == -9.223372036854776E18d ? -9223372036854775807L : (long) (optionalDoubleAttr * 1000000.0d);
        boolean optionalBooleanAttribute = parseOptionalBooleanAttribute(str, REGEX_CAN_SKIP_DATE_RANGES, false);
        double optionalDoubleAttr2 = parseOptionalDoubleAttr(str, REGEX_HOLD_BACK, -9.223372036854776E18d);
        long j4 = optionalDoubleAttr2 == -9.223372036854776E18d ? -9223372036854775807L : (long) (optionalDoubleAttr2 * 1000000.0d);
        double optionalDoubleAttr3 = parseOptionalDoubleAttr(str, REGEX_PART_HOLD_BACK, -9.223372036854776E18d);
        if (optionalDoubleAttr3 != -9.223372036854776E18d) {
            j2 = (long) (optionalDoubleAttr3 * 1000000.0d);
        }
        return new HlsMediaPlaylist.ServerControl(j3, optionalBooleanAttribute, j4, j2, parseOptionalBooleanAttribute(str, REGEX_CAN_BLOCK_RELOAD, false));
    }

    private static String parseStringAttr(String str, Pattern pattern, Map<String, String> map) throws ParserException {
        String optionalStringAttr = parseOptionalStringAttr(str, pattern, map);
        if (optionalStringAttr != null) {
            return optionalStringAttr;
        }
        throw ParserException.createForMalformedManifest("Couldn't match " + pattern.pattern() + " in " + str, null);
    }

    private static long parseTimeSecondsToUs(String str, Pattern pattern) throws ParserException {
        return new BigDecimal(parseStringAttr(str, pattern, Collections.emptyMap())).multiply(new BigDecimal(1000000L)).longValue();
    }

    private static String replaceVariableReferences(String str, Map<String, String> map) {
        Matcher matcher = REGEX_VARIABLE_REFERENCE.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String strGroup = matcher.group(1);
            if (map.containsKey(strGroup)) {
                matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(map.get(strGroup)));
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static int skipIgnorableWhitespace(BufferedReader bufferedReader, boolean z2, int i2) throws IOException {
        while (i2 != -1 && Character.isWhitespace(i2) && (z2 || !Util.isLinebreak(i2))) {
            i2 = bufferedReader.read();
        }
        return i2;
    }

    public HlsPlaylistParser(HlsMultivariantPlaylist hlsMultivariantPlaylist, @Nullable HlsMediaPlaylist hlsMediaPlaylist) {
        this.multivariantPlaylist = hlsMultivariantPlaylist;
        this.previousMediaPlaylist = hlsMediaPlaylist;
    }

    private static String parseOptionalStringAttr(String str, Pattern pattern, String str2, Map<String, String> map) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str2 = (String) Assertions.checkNotNull(matcher.group(1));
        }
        return (map.isEmpty() || str2 == null) ? str2 : replaceVariableReferences(str2, map);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.media3.exoplayer.upstream.ParsingLoadable.Parser
    public HlsPlaylist parse(Uri uri, InputStream inputStream) throws IOException {
        String strTrim;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayDeque arrayDeque = new ArrayDeque();
        try {
            if (!checkPlaylistHeader(bufferedReader)) {
                throw ParserException.createForMalformedManifest("Input does not start with the #EXTM3U header.", null);
            }
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    Util.closeQuietly(bufferedReader);
                    throw ParserException.createForMalformedManifest("Failed to parse the playlist, could not identify any tags.", null);
                }
                strTrim = line.trim();
                if (!strTrim.isEmpty()) {
                    if (!strTrim.startsWith(TAG_STREAM_INF)) {
                        if (strTrim.startsWith(TAG_TARGET_DURATION) || strTrim.startsWith(TAG_MEDIA_SEQUENCE) || strTrim.startsWith(TAG_MEDIA_DURATION) || strTrim.startsWith(TAG_KEY) || strTrim.startsWith(TAG_BYTERANGE) || strTrim.equals(TAG_DISCONTINUITY) || strTrim.equals(TAG_DISCONTINUITY_SEQUENCE) || strTrim.equals(TAG_ENDLIST)) {
                            break;
                        }
                        arrayDeque.add(strTrim);
                    } else {
                        arrayDeque.add(strTrim);
                        return parseMultivariantPlaylist(new LineIterator(arrayDeque, bufferedReader), uri.toString());
                    }
                }
            }
            arrayDeque.add(strTrim);
            return parseMediaPlaylist(this.multivariantPlaylist, this.previousMediaPlaylist, new LineIterator(arrayDeque, bufferedReader), uri.toString());
        } finally {
            Util.closeQuietly(bufferedReader);
        }
    }
}
