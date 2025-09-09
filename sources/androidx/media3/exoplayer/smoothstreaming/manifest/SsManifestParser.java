package androidx.media3.exoplayer.smoothstreaming.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.media3.common.C;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.exoplayer.smoothstreaming.manifest.SsManifest;
import androidx.media3.exoplayer.upstream.ParsingLoadable;
import androidx.media3.extractor.mp4.PsshAtomUtil;
import androidx.media3.extractor.mp4.TrackEncryptionBox;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

@UnstableApi
/* loaded from: classes.dex */
public class SsManifestParser implements ParsingLoadable.Parser<SsManifest> {
    private final XmlPullParserFactory xmlParserFactory;

    private static abstract class ElementParser {
        private final String baseUri;
        private final List<Pair<String, Object>> normalizedAttributes = new LinkedList();

        @Nullable
        private final ElementParser parent;
        private final String tag;

        public ElementParser(@Nullable ElementParser elementParser, String str, String str2) {
            this.parent = elementParser;
            this.baseUri = str;
            this.tag = str2;
        }

        private ElementParser newChildParser(ElementParser elementParser, String str, String str2) {
            if (QualityLevelParser.TAG.equals(str)) {
                return new QualityLevelParser(elementParser, str2);
            }
            if (ProtectionParser.TAG.equals(str)) {
                return new ProtectionParser(elementParser, str2);
            }
            if (StreamIndexParser.TAG.equals(str)) {
                return new StreamIndexParser(elementParser, str2);
            }
            return null;
        }

        protected final Object a(String str) {
            for (int i2 = 0; i2 < this.normalizedAttributes.size(); i2++) {
                Pair<String, Object> pair = this.normalizedAttributes.get(i2);
                if (((String) pair.first).equals(str)) {
                    return pair.second;
                }
            }
            ElementParser elementParser = this.parent;
            if (elementParser == null) {
                return null;
            }
            return elementParser.a(str);
        }

        protected void addChild(Object obj) {
        }

        protected final boolean b(XmlPullParser xmlPullParser, String str, boolean z2) {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            return attributeValue != null ? Boolean.parseBoolean(attributeValue) : z2;
        }

        protected abstract Object build();

        protected final int c(XmlPullParser xmlPullParser, String str, int i2) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                return i2;
            }
            try {
                return Integer.parseInt(attributeValue);
            } catch (NumberFormatException e2) {
                throw ParserException.createForMalformedManifest(null, e2);
            }
        }

        protected final long d(XmlPullParser xmlPullParser, String str, long j2) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                return j2;
            }
            try {
                return Long.parseLong(attributeValue);
            } catch (NumberFormatException e2) {
                throw ParserException.createForMalformedManifest(null, e2);
            }
        }

        protected final int e(XmlPullParser xmlPullParser, String str) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                throw new MissingFieldException(str);
            }
            try {
                return Integer.parseInt(attributeValue);
            } catch (NumberFormatException e2) {
                throw ParserException.createForMalformedManifest(null, e2);
            }
        }

        protected final long f(XmlPullParser xmlPullParser, String str) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue == null) {
                throw new MissingFieldException(str);
            }
            try {
                return Long.parseLong(attributeValue);
            } catch (NumberFormatException e2) {
                throw ParserException.createForMalformedManifest(null, e2);
            }
        }

        protected final String g(XmlPullParser xmlPullParser, String str) throws MissingFieldException {
            String attributeValue = xmlPullParser.getAttributeValue(null, str);
            if (attributeValue != null) {
                return attributeValue;
            }
            throw new MissingFieldException(str);
        }

        protected final void h(String str, Object obj) {
            this.normalizedAttributes.add(Pair.create(str, obj));
        }

        protected boolean handleChildInline(String str) {
            return false;
        }

        public final Object parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            boolean z2 = false;
            int i2 = 0;
            while (true) {
                int eventType = xmlPullParser.getEventType();
                if (eventType == 1) {
                    return null;
                }
                if (eventType == 2) {
                    String name = xmlPullParser.getName();
                    if (this.tag.equals(name)) {
                        parseStartTag(xmlPullParser);
                        z2 = true;
                    } else if (z2) {
                        if (i2 > 0) {
                            i2++;
                        } else if (handleChildInline(name)) {
                            parseStartTag(xmlPullParser);
                        } else {
                            ElementParser elementParserNewChildParser = newChildParser(this, name, this.baseUri);
                            if (elementParserNewChildParser == null) {
                                i2 = 1;
                            } else {
                                addChild(elementParserNewChildParser.parse(xmlPullParser));
                            }
                        }
                    }
                } else if (eventType != 3) {
                    if (eventType == 4 && z2 && i2 == 0) {
                        parseText(xmlPullParser);
                    }
                } else if (!z2) {
                    continue;
                } else if (i2 > 0) {
                    i2--;
                } else {
                    String name2 = xmlPullParser.getName();
                    parseEndTag(xmlPullParser);
                    if (!handleChildInline(name2)) {
                        return build();
                    }
                }
                xmlPullParser.next();
            }
        }

        protected void parseEndTag(XmlPullParser xmlPullParser) {
        }

        protected void parseStartTag(XmlPullParser xmlPullParser) {
        }

        protected void parseText(XmlPullParser xmlPullParser) {
        }
    }

    public static class MissingFieldException extends ParserException {
        public MissingFieldException(String str) {
            super("Missing required field: " + str, null, true, 4);
        }
    }

    private static class ProtectionParser extends ElementParser {
        private static final int INITIALIZATION_VECTOR_SIZE = 8;
        public static final String KEY_SYSTEM_ID = "SystemID";
        public static final String TAG = "Protection";
        public static final String TAG_PROTECTION_HEADER = "ProtectionHeader";
        private boolean inProtectionHeader;
        private byte[] initData;
        private UUID uuid;

        public ProtectionParser(ElementParser elementParser, String str) {
            super(elementParser, str, TAG);
        }

        private static TrackEncryptionBox[] buildTrackEncryptionBoxes(byte[] bArr) {
            return new TrackEncryptionBox[]{new TrackEncryptionBox(true, null, 8, getProtectionElementKeyId(bArr), 0, 0, null)};
        }

        private static byte[] getProtectionElementKeyId(byte[] bArr) {
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 < bArr.length; i2 += 2) {
                sb.append((char) bArr[i2]);
            }
            String string = sb.toString();
            byte[] bArrDecode = Base64.decode(string.substring(string.indexOf("<KID>") + 5, string.indexOf("</KID>")), 0);
            swap(bArrDecode, 0, 3);
            swap(bArrDecode, 1, 2);
            swap(bArrDecode, 4, 5);
            swap(bArrDecode, 6, 7);
            return bArrDecode;
        }

        private static String stripCurlyBraces(String str) {
            return (str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') ? str.substring(1, str.length() - 1) : str;
        }

        private static void swap(byte[] bArr, int i2, int i3) {
            byte b2 = bArr[i2];
            bArr[i2] = bArr[i3];
            bArr[i3] = b2;
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            UUID uuid = this.uuid;
            return new SsManifest.ProtectionElement(uuid, PsshAtomUtil.buildPsshAtom(uuid, this.initData), buildTrackEncryptionBoxes(this.initData));
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public boolean handleChildInline(String str) {
            return TAG_PROTECTION_HEADER.equals(str);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseEndTag(XmlPullParser xmlPullParser) {
            if (TAG_PROTECTION_HEADER.equals(xmlPullParser.getName())) {
                this.inProtectionHeader = false;
            }
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser xmlPullParser) {
            if (TAG_PROTECTION_HEADER.equals(xmlPullParser.getName())) {
                this.inProtectionHeader = true;
                this.uuid = UUID.fromString(stripCurlyBraces(xmlPullParser.getAttributeValue(null, KEY_SYSTEM_ID)));
            }
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseText(XmlPullParser xmlPullParser) {
            if (this.inProtectionHeader) {
                this.initData = Base64.decode(xmlPullParser.getText(), 0);
            }
        }
    }

    private static class QualityLevelParser extends ElementParser {
        private static final String KEY_BITRATE = "Bitrate";
        private static final String KEY_CHANNELS = "Channels";
        private static final String KEY_CODEC_PRIVATE_DATA = "CodecPrivateData";
        private static final String KEY_FOUR_CC = "FourCC";
        private static final String KEY_INDEX = "Index";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SAMPLING_RATE = "SamplingRate";
        private static final String KEY_SUB_TYPE = "Subtype";
        private static final String KEY_TYPE = "Type";
        public static final String TAG = "QualityLevel";
        private Format format;

        public QualityLevelParser(ElementParser elementParser, String str) {
            super(elementParser, str, TAG);
        }

        private static List<byte[]> buildCodecSpecificData(String str) {
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(str)) {
                byte[] bytesFromHexString = Util.getBytesFromHexString(str);
                byte[][] bArrSplitNalUnits = CodecSpecificDataUtil.splitNalUnits(bytesFromHexString);
                if (bArrSplitNalUnits == null) {
                    arrayList.add(bytesFromHexString);
                } else {
                    Collections.addAll(arrayList, bArrSplitNalUnits);
                }
            }
            return arrayList;
        }

        @Nullable
        private static String fourCCToMimeType(String str) {
            if (str.equalsIgnoreCase(RtpPayloadFormat.RTP_MEDIA_H264) || str.equalsIgnoreCase("X264") || str.equalsIgnoreCase("AVC1") || str.equalsIgnoreCase("DAVC")) {
                return MimeTypes.VIDEO_H264;
            }
            if (str.equalsIgnoreCase("AAC") || str.equalsIgnoreCase("AACL") || str.equalsIgnoreCase("AACH") || str.equalsIgnoreCase("AACP")) {
                return MimeTypes.AUDIO_AAC;
            }
            if (str.equalsIgnoreCase("TTML") || str.equalsIgnoreCase("DFXP")) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (str.equalsIgnoreCase("ac-3") || str.equalsIgnoreCase("dac3")) {
                return MimeTypes.AUDIO_AC3;
            }
            if (str.equalsIgnoreCase("ec-3") || str.equalsIgnoreCase("dec3")) {
                return MimeTypes.AUDIO_E_AC3;
            }
            if (str.equalsIgnoreCase("dtsc")) {
                return MimeTypes.AUDIO_DTS;
            }
            if (str.equalsIgnoreCase("dtsh") || str.equalsIgnoreCase("dtsl")) {
                return MimeTypes.AUDIO_DTS_HD;
            }
            if (str.equalsIgnoreCase("dtse")) {
                return MimeTypes.AUDIO_DTS_EXPRESS;
            }
            if (str.equalsIgnoreCase("opus")) {
                return MimeTypes.AUDIO_OPUS;
            }
            return null;
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            return this.format;
        }

        /* JADX WARN: Removed duplicated region for block: B:27:0x00b0  */
        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void parseStartTag(org.xmlpull.v1.XmlPullParser r9) throws androidx.media3.common.ParserException {
            /*
                r8 = this;
                androidx.media3.common.Format$Builder r0 = new androidx.media3.common.Format$Builder
                r0.<init>()
                java.lang.String r1 = "FourCC"
                java.lang.String r1 = r8.g(r9, r1)
                java.lang.String r1 = fourCCToMimeType(r1)
                java.lang.String r2 = "Type"
                java.lang.Object r2 = r8.a(r2)
                java.lang.Integer r2 = (java.lang.Integer) r2
                int r2 = r2.intValue()
                r3 = 2
                java.lang.String r4 = "CodecPrivateData"
                r5 = 0
                if (r2 != r3) goto L48
                java.lang.String r2 = r9.getAttributeValue(r5, r4)
                java.util.List r2 = buildCodecSpecificData(r2)
                java.lang.String r3 = "video/mp4"
                androidx.media3.common.Format$Builder r3 = r0.setContainerMimeType(r3)
                java.lang.String r4 = "MaxWidth"
                int r4 = r8.e(r9, r4)
                androidx.media3.common.Format$Builder r3 = r3.setWidth(r4)
                java.lang.String r4 = "MaxHeight"
                int r4 = r8.e(r9, r4)
                androidx.media3.common.Format$Builder r3 = r3.setHeight(r4)
                r3.setInitializationData(r2)
                goto Lbc
            L48:
                r3 = 1
                if (r2 != r3) goto L8a
                java.lang.String r2 = "audio/mp4a-latm"
                if (r1 != 0) goto L50
                r1 = r2
            L50:
                java.lang.String r3 = "Channels"
                int r3 = r8.e(r9, r3)
                java.lang.String r6 = "SamplingRate"
                int r6 = r8.e(r9, r6)
                java.lang.String r4 = r9.getAttributeValue(r5, r4)
                java.util.List r4 = buildCodecSpecificData(r4)
                boolean r7 = r4.isEmpty()
                if (r7 == 0) goto L78
                boolean r2 = r2.equals(r1)
                if (r2 == 0) goto L78
                byte[] r2 = androidx.media3.extractor.AacUtil.buildAacLcAudioSpecificConfig(r6, r3)
                java.util.List r4 = java.util.Collections.singletonList(r2)
            L78:
                java.lang.String r2 = "audio/mp4"
                androidx.media3.common.Format$Builder r2 = r0.setContainerMimeType(r2)
                androidx.media3.common.Format$Builder r2 = r2.setChannelCount(r3)
                androidx.media3.common.Format$Builder r2 = r2.setSampleRate(r6)
                r2.setInitializationData(r4)
                goto Lbc
            L8a:
                r3 = 3
                java.lang.String r4 = "application/mp4"
                if (r2 != r3) goto Lb9
                java.lang.String r2 = "Subtype"
                java.lang.Object r2 = r8.a(r2)
                java.lang.String r2 = (java.lang.String) r2
                if (r2 == 0) goto Lb0
                java.lang.String r3 = "CAPT"
                boolean r3 = r2.equals(r3)
                if (r3 != 0) goto Lad
                java.lang.String r3 = "DESC"
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto Laa
                goto Lb0
            Laa:
                r2 = 1024(0x400, float:1.435E-42)
                goto Lb1
            Lad:
                r2 = 64
                goto Lb1
            Lb0:
                r2 = 0
            Lb1:
                androidx.media3.common.Format$Builder r3 = r0.setContainerMimeType(r4)
                r3.setRoleFlags(r2)
                goto Lbc
            Lb9:
                r0.setContainerMimeType(r4)
            Lbc:
                java.lang.String r2 = "Index"
                java.lang.String r2 = r9.getAttributeValue(r5, r2)
                androidx.media3.common.Format$Builder r0 = r0.setId(r2)
                java.lang.String r2 = "Name"
                java.lang.Object r2 = r8.a(r2)
                java.lang.String r2 = (java.lang.String) r2
                androidx.media3.common.Format$Builder r0 = r0.setLabel(r2)
                androidx.media3.common.Format$Builder r0 = r0.setSampleMimeType(r1)
                java.lang.String r1 = "Bitrate"
                int r9 = r8.e(r9, r1)
                androidx.media3.common.Format$Builder r9 = r0.setAverageBitrate(r9)
                java.lang.String r0 = "Language"
                java.lang.Object r0 = r8.a(r0)
                java.lang.String r0 = (java.lang.String) r0
                androidx.media3.common.Format$Builder r9 = r9.setLanguage(r0)
                androidx.media3.common.Format r9 = r9.build()
                r8.format = r9
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.QualityLevelParser.parseStartTag(org.xmlpull.v1.XmlPullParser):void");
        }
    }

    private static class SmoothStreamingMediaParser extends ElementParser {
        private static final String KEY_DURATION = "Duration";
        private static final String KEY_DVR_WINDOW_LENGTH = "DVRWindowLength";
        private static final String KEY_IS_LIVE = "IsLive";
        private static final String KEY_LOOKAHEAD_COUNT = "LookaheadCount";
        private static final String KEY_MAJOR_VERSION = "MajorVersion";
        private static final String KEY_MINOR_VERSION = "MinorVersion";
        private static final String KEY_TIME_SCALE = "TimeScale";
        public static final String TAG = "SmoothStreamingMedia";
        private long duration;
        private long dvrWindowLength;
        private boolean isLive;
        private int lookAheadCount;
        private int majorVersion;
        private int minorVersion;

        @Nullable
        private SsManifest.ProtectionElement protectionElement;
        private final List<SsManifest.StreamElement> streamElements;
        private long timescale;

        public SmoothStreamingMediaParser(ElementParser elementParser, String str) {
            super(elementParser, str, TAG);
            this.lookAheadCount = -1;
            this.protectionElement = null;
            this.streamElements = new LinkedList();
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void addChild(Object obj) {
            if (obj instanceof SsManifest.StreamElement) {
                this.streamElements.add((SsManifest.StreamElement) obj);
            } else if (obj instanceof SsManifest.ProtectionElement) {
                Assertions.checkState(this.protectionElement == null);
                this.protectionElement = (SsManifest.ProtectionElement) obj;
            }
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            int size = this.streamElements.size();
            SsManifest.StreamElement[] streamElementArr = new SsManifest.StreamElement[size];
            this.streamElements.toArray(streamElementArr);
            if (this.protectionElement != null) {
                SsManifest.ProtectionElement protectionElement = this.protectionElement;
                DrmInitData drmInitData = new DrmInitData(new DrmInitData.SchemeData(protectionElement.uuid, "video/mp4", protectionElement.data));
                for (int i2 = 0; i2 < size; i2++) {
                    SsManifest.StreamElement streamElement = streamElementArr[i2];
                    int i3 = streamElement.type;
                    if (i3 == 2 || i3 == 1) {
                        Format[] formatArr = streamElement.formats;
                        for (int i4 = 0; i4 < formatArr.length; i4++) {
                            formatArr[i4] = formatArr[i4].buildUpon().setDrmInitData(drmInitData).build();
                        }
                    }
                }
            }
            return new SsManifest(this.majorVersion, this.minorVersion, this.timescale, this.duration, this.dvrWindowLength, this.lookAheadCount, this.isLive, this.protectionElement, streamElementArr);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser xmlPullParser) throws ParserException {
            this.majorVersion = e(xmlPullParser, KEY_MAJOR_VERSION);
            this.minorVersion = e(xmlPullParser, KEY_MINOR_VERSION);
            this.timescale = d(xmlPullParser, KEY_TIME_SCALE, 10000000L);
            this.duration = f(xmlPullParser, KEY_DURATION);
            this.dvrWindowLength = d(xmlPullParser, KEY_DVR_WINDOW_LENGTH, 0L);
            this.lookAheadCount = c(xmlPullParser, KEY_LOOKAHEAD_COUNT, -1);
            this.isLive = b(xmlPullParser, KEY_IS_LIVE, false);
            h(KEY_TIME_SCALE, Long.valueOf(this.timescale));
        }
    }

    private static class StreamIndexParser extends ElementParser {
        private static final String KEY_DISPLAY_HEIGHT = "DisplayHeight";
        private static final String KEY_DISPLAY_WIDTH = "DisplayWidth";
        private static final String KEY_FRAGMENT_DURATION = "d";
        private static final String KEY_FRAGMENT_REPEAT_COUNT = "r";
        private static final String KEY_FRAGMENT_START_TIME = "t";
        private static final String KEY_LANGUAGE = "Language";
        private static final String KEY_MAX_HEIGHT = "MaxHeight";
        private static final String KEY_MAX_WIDTH = "MaxWidth";
        private static final String KEY_NAME = "Name";
        private static final String KEY_SUB_TYPE = "Subtype";
        private static final String KEY_TIME_SCALE = "TimeScale";
        private static final String KEY_TYPE = "Type";
        private static final String KEY_TYPE_AUDIO = "audio";
        private static final String KEY_TYPE_TEXT = "text";
        private static final String KEY_TYPE_VIDEO = "video";
        private static final String KEY_URL = "Url";
        public static final String TAG = "StreamIndex";
        private static final String TAG_STREAM_FRAGMENT = "c";
        private final String baseUri;
        private int displayHeight;
        private int displayWidth;
        private final List<Format> formats;
        private String language;
        private long lastChunkDuration;
        private int maxHeight;
        private int maxWidth;
        private String name;
        private ArrayList<Long> startTimes;
        private String subType;
        private long timescale;
        private int type;
        private String url;

        public StreamIndexParser(ElementParser elementParser, String str) {
            super(elementParser, str, TAG);
            this.baseUri = str;
            this.formats = new LinkedList();
        }

        private void parseStreamElementStartTag(XmlPullParser xmlPullParser) throws ParserException {
            int type = parseType(xmlPullParser);
            this.type = type;
            h(KEY_TYPE, Integer.valueOf(type));
            if (this.type == 3) {
                this.subType = g(xmlPullParser, KEY_SUB_TYPE);
            } else {
                this.subType = xmlPullParser.getAttributeValue(null, KEY_SUB_TYPE);
            }
            h(KEY_SUB_TYPE, this.subType);
            String attributeValue = xmlPullParser.getAttributeValue(null, KEY_NAME);
            this.name = attributeValue;
            h(KEY_NAME, attributeValue);
            this.url = g(xmlPullParser, KEY_URL);
            this.maxWidth = c(xmlPullParser, KEY_MAX_WIDTH, -1);
            this.maxHeight = c(xmlPullParser, KEY_MAX_HEIGHT, -1);
            this.displayWidth = c(xmlPullParser, KEY_DISPLAY_WIDTH, -1);
            this.displayHeight = c(xmlPullParser, KEY_DISPLAY_HEIGHT, -1);
            String attributeValue2 = xmlPullParser.getAttributeValue(null, KEY_LANGUAGE);
            this.language = attributeValue2;
            h(KEY_LANGUAGE, attributeValue2);
            long jC = c(xmlPullParser, KEY_TIME_SCALE, -1);
            this.timescale = jC;
            if (jC == -1) {
                this.timescale = ((Long) a(KEY_TIME_SCALE)).longValue();
            }
            this.startTimes = new ArrayList<>();
        }

        private void parseStreamFragmentStartTag(XmlPullParser xmlPullParser) throws ParserException {
            int size = this.startTimes.size();
            long jD = d(xmlPullParser, "t", C.TIME_UNSET);
            int i2 = 1;
            if (jD == C.TIME_UNSET) {
                if (size == 0) {
                    jD = 0;
                } else {
                    if (this.lastChunkDuration == -1) {
                        throw ParserException.createForMalformedManifest("Unable to infer start time", null);
                    }
                    jD = this.lastChunkDuration + this.startTimes.get(size - 1).longValue();
                }
            }
            this.startTimes.add(Long.valueOf(jD));
            this.lastChunkDuration = d(xmlPullParser, "d", C.TIME_UNSET);
            long jD2 = d(xmlPullParser, "r", 1L);
            if (jD2 > 1 && this.lastChunkDuration == C.TIME_UNSET) {
                throw ParserException.createForMalformedManifest("Repeated chunk with unspecified duration", null);
            }
            while (true) {
                long j2 = i2;
                if (j2 >= jD2) {
                    return;
                }
                this.startTimes.add(Long.valueOf((this.lastChunkDuration * j2) + jD));
                i2++;
            }
        }

        private int parseType(XmlPullParser xmlPullParser) throws ParserException {
            String attributeValue = xmlPullParser.getAttributeValue(null, KEY_TYPE);
            if (attributeValue == null) {
                throw new MissingFieldException(KEY_TYPE);
            }
            if ("audio".equalsIgnoreCase(attributeValue)) {
                return 1;
            }
            if ("video".equalsIgnoreCase(attributeValue)) {
                return 2;
            }
            if ("text".equalsIgnoreCase(attributeValue)) {
                return 3;
            }
            throw ParserException.createForMalformedManifest("Invalid key value[" + attributeValue + "]", null);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void addChild(Object obj) {
            if (obj instanceof Format) {
                this.formats.add((Format) obj);
            }
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public Object build() {
            Format[] formatArr = new Format[this.formats.size()];
            this.formats.toArray(formatArr);
            return new SsManifest.StreamElement(this.baseUri, this.url, this.type, this.subType, this.timescale, this.name, this.maxWidth, this.maxHeight, this.displayWidth, this.displayHeight, this.language, formatArr, this.startTimes, this.lastChunkDuration);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public boolean handleChildInline(String str) {
            return "c".equals(str);
        }

        @Override // androidx.media3.exoplayer.smoothstreaming.manifest.SsManifestParser.ElementParser
        public void parseStartTag(XmlPullParser xmlPullParser) throws ParserException {
            if ("c".equals(xmlPullParser.getName())) {
                parseStreamFragmentStartTag(xmlPullParser);
            } else {
                parseStreamElementStartTag(xmlPullParser);
            }
        }
    }

    public SsManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.media3.exoplayer.upstream.ParsingLoadable.Parser
    public SsManifest parse(Uri uri, InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            return (SsManifest) new SmoothStreamingMediaParser(null, uri.toString()).parse(xmlPullParserNewPullParser);
        } catch (XmlPullParserException e2) {
            throw ParserException.createForMalformedManifest(null, e2);
        }
    }
}
