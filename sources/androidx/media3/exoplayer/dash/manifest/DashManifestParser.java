package androidx.media3.exoplayer.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;
import androidx.media3.common.C;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.Label;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.common.util.UriUtil;
import androidx.media3.common.util.Util;
import androidx.media3.common.util.XmlPullParserUtil;
import androidx.media3.exoplayer.dash.manifest.SegmentBase;
import androidx.media3.exoplayer.upstream.ParsingLoadable;
import androidx.media3.extractor.metadata.emsg.EventMessage;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kingsmith.miot.KsProperty;
import com.xiaomi.mipush.sdk.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

@UnstableApi
/* loaded from: classes.dex */
public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final int[] MPEG_CHANNEL_CONFIGURATION_MAPPING = {-1, 1, 2, 3, 4, 5, 6, 8, 2, 3, 4, 7, 8, 24, 8, 12, 10, 12, 14, 12, 14};

    protected static final class RepresentationInfo {
        public final ImmutableList<BaseUrl> baseUrls;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;

        @Nullable
        public final String drmSchemeType;
        public final List<Descriptor> essentialProperties;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;
        public final List<Descriptor> supplementalProperties;

        public RepresentationInfo(Format format, List<BaseUrl> list, SegmentBase segmentBase, @Nullable String str, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2, List<Descriptor> list2, List<Descriptor> list3, long j2) {
            this.format = format;
            this.baseUrls = ImmutableList.copyOf((Collection) list);
            this.segmentBase = segmentBase;
            this.drmSchemeType = str;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.essentialProperties = list2;
            this.supplementalProperties = list3;
            this.revisionId = j2;
        }
    }

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e2);
        }
    }

    protected static int A(XmlPullParser xmlPullParser) {
        int iBitCount;
        String attributeValue = xmlPullParser.getAttributeValue(null, "value");
        if (attributeValue == null || (iBitCount = Integer.bitCount(Integer.parseInt(attributeValue, 16))) == 0) {
            return -1;
        }
        return iBitCount;
    }

    protected static long B(XmlPullParser xmlPullParser, String str, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Util.parseXsDuration(attributeValue);
    }

    protected static String C(List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            String str = descriptor.schemeIdUri;
            if ("tag:dolby.com,2018:dash:EC3_ExtensionType:2018".equals(str) && "JOC".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(str) && MimeTypes.CODEC_E_AC3_JOC.equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float G(XmlPullParser xmlPullParser, String str, float f2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? f2 : Float.parseFloat(attributeValue);
    }

    protected static float H(XmlPullParser xmlPullParser, float f2) throws NumberFormatException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f2;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f2;
        }
        int i2 = Integer.parseInt(matcher.group(1));
        return !TextUtils.isEmpty(matcher.group(2)) ? i2 / Integer.parseInt(r2) : i2;
    }

    protected static int J(XmlPullParser xmlPullParser, String str, int i2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i2 : Integer.parseInt(attributeValue);
    }

    protected static long L(List list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/last-segment-number", descriptor.schemeIdUri)) {
                return Long.parseLong(descriptor.value);
            }
        }
        return -1L;
    }

    protected static long M(XmlPullParser xmlPullParser, String str, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Long.parseLong(attributeValue);
    }

    protected static int O(XmlPullParser xmlPullParser) {
        int iJ = J(xmlPullParser, "value", -1);
        if (iJ < 0) {
            return -1;
        }
        int[] iArr = MPEG_CHANNEL_CONFIGURATION_MAPPING;
        if (iJ < iArr.length) {
            return iArr[iJ];
        }
        return -1;
    }

    private long addSegmentTimelineElementsToList(List<SegmentBase.SegmentTimelineElement> list, long j2, long j3, int i2, long j4) {
        int iCeilDivide = i2 >= 0 ? i2 + 1 : (int) Util.ceilDivide(j4 - j2, j3);
        for (int i3 = 0; i3 < iCeilDivide; i3++) {
            list.add(k(j2, j3));
            j2 += j3;
        }
        return j2;
    }

    private static int checkContentTypeConsistency(int i2, int i3) {
        if (i2 == -1) {
            return i3;
        }
        if (i3 == -1) {
            return i2;
        }
        Assertions.checkState(i2 == i3);
        return i2;
    }

    @Nullable
    private static String checkLanguageConsistency(@Nullable String str, @Nullable String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static void fillInClearKeyInformation(ArrayList<DrmInitData.SchemeData> arrayList) {
        String str;
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                str = null;
                break;
            }
            DrmInitData.SchemeData schemeData = arrayList.get(i2);
            if (C.CLEARKEY_UUID.equals(schemeData.uuid) && (str = schemeData.licenseServerUrl) != null) {
                arrayList.remove(i2);
                break;
            }
            i2++;
        }
        if (str == null) {
            return;
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            DrmInitData.SchemeData schemeData2 = arrayList.get(i3);
            if (C.COMMON_PSSH_UUID.equals(schemeData2.uuid) && schemeData2.licenseServerUrl == null) {
                arrayList.set(i3, new DrmInitData.SchemeData(C.CLEARKEY_UUID, str, schemeData2.mimeType, schemeData2.data));
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = arrayList.get(size);
            if (!schemeData.hasData()) {
                int i2 = 0;
                while (true) {
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    if (arrayList.get(i2).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    }
                    i2++;
                }
            }
        }
    }

    protected static String g0(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    private static long getFinalAvailabilityTimeOffset(long j2, long j3) {
        if (j3 != C.TIME_UNSET) {
            j2 = j3;
        }
        return j2 == Long.MAX_VALUE ? C.TIME_UNSET : j2;
    }

    @Nullable
    private static String getSampleMimeType(@Nullable String str, @Nullable String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (MimeTypes.isText(str) || MimeTypes.isImage(str)) {
            return str;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(str)) {
            return null;
        }
        String mediaMimeType = MimeTypes.getMediaMimeType(str2);
        return MimeTypes.TEXT_VTT.equals(mediaMimeType) ? MimeTypes.APPLICATION_MP4VTT : mediaMimeType;
    }

    protected static String h0(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String text = "";
        do {
            xmlPullParser.next();
            if (xmlPullParser.getEventType() == 4) {
                text = xmlPullParser.getText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return text;
    }

    private boolean isDvbProfileDeclared(String[] strArr) {
        for (String str : strArr) {
            if (str.startsWith("urn:dvb:dash:profile:dvb-dash:")) {
                return true;
            }
        }
        return false;
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i2 = 1;
            while (i2 != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i2++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i2--;
                }
            }
        }
    }

    protected static int s(List list) {
        String str;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && (str = descriptor.value) != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(str);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.w(TAG, "Unable to parse CEA-608 channel number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static int t(List list) {
        String str;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && (str = descriptor.value) != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(str);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.w(TAG, "Unable to parse CEA-708 service block number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static long w(XmlPullParser xmlPullParser, String str, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j2 : Util.parseXsDateTime(attributeValue);
    }

    protected static Descriptor x(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String strG0 = g0(xmlPullParser, "schemeIdUri", "");
        String strG02 = g0(xmlPullParser, "value", null);
        String strG03 = g0(xmlPullParser, "id", null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(strG0, strG02, strG03);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static int y(org.xmlpull.v1.XmlPullParser r4) {
        /*
            r0 = 2
            r1 = 1
            r2 = 0
            java.lang.String r3 = "value"
            java.lang.String r4 = r4.getAttributeValue(r2, r3)
            r2 = -1
            if (r4 != 0) goto Ld
            return r2
        Ld:
            java.lang.String r4 = com.google.common.base.Ascii.toLowerCase(r4)
            r4.hashCode()
            int r3 = r4.hashCode()
            switch(r3) {
                case 1596796: goto L49;
                case 2937391: goto L3e;
                case 3094034: goto L33;
                case 3094035: goto L28;
                case 3133436: goto L1d;
                default: goto L1b;
            }
        L1b:
            r4 = r2
            goto L53
        L1d:
            java.lang.String r3 = "fa01"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L26
            goto L1b
        L26:
            r4 = 4
            goto L53
        L28:
            java.lang.String r3 = "f801"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L31
            goto L1b
        L31:
            r4 = 3
            goto L53
        L33:
            java.lang.String r3 = "f800"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L3c
            goto L1b
        L3c:
            r4 = r0
            goto L53
        L3e:
            java.lang.String r3 = "a000"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L47
            goto L1b
        L47:
            r4 = r1
            goto L53
        L49:
            java.lang.String r3 = "4000"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L52
            goto L1b
        L52:
            r4 = 0
        L53:
            switch(r4) {
                case 0: goto L5f;
                case 1: goto L5e;
                case 2: goto L5c;
                case 3: goto L5a;
                case 4: goto L57;
                default: goto L56;
            }
        L56:
            return r2
        L57:
            r4 = 8
            return r4
        L5a:
            r4 = 6
            return r4
        L5c:
            r4 = 5
            return r4
        L5e:
            return r0
        L5f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.y(org.xmlpull.v1.XmlPullParser):int");
    }

    protected static int z(XmlPullParser xmlPullParser) {
        int iJ = J(xmlPullParser, "value", -1);
        if (iJ <= 0 || iJ >= 33) {
            return -1;
        }
        return iJ;
    }

    protected Pair D(XmlPullParser xmlPullParser, String str, String str2, long j2, long j3, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        long jM = M(xmlPullParser, "id", 0L);
        long jM2 = M(xmlPullParser, "duration", C.TIME_UNSET);
        long jM3 = M(xmlPullParser, "presentationTime", 0L);
        long jScaleLargeTimestamp = Util.scaleLargeTimestamp(jM2, 1000L, j2);
        long jScaleLargeTimestamp2 = Util.scaleLargeTimestamp(jM3 - j3, 1000000L, j2);
        String strG0 = g0(xmlPullParser, "messageData", null);
        byte[] bArrE = E(xmlPullParser, byteArrayOutputStream);
        Long lValueOf = Long.valueOf(jScaleLargeTimestamp2);
        if (strG0 != null) {
            bArrE = Util.getUtf8Bytes(strG0);
        }
        return Pair.create(lValueOf, b(str, str2, jM, jScaleLargeTimestamp, bArrE));
    }

    protected byte[] E(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IllegalStateException, IOException, IllegalArgumentException {
        byteArrayOutputStream.reset();
        XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
        xmlSerializerNewSerializer.setOutput(byteArrayOutputStream, Charsets.UTF_8.name());
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            switch (xmlPullParser.getEventType()) {
                case 0:
                    xmlSerializerNewSerializer.startDocument(null, Boolean.FALSE);
                    break;
                case 1:
                    xmlSerializerNewSerializer.endDocument();
                    break;
                case 2:
                    xmlSerializerNewSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
                        xmlSerializerNewSerializer.attribute(xmlPullParser.getAttributeNamespace(i2), xmlPullParser.getAttributeName(i2), xmlPullParser.getAttributeValue(i2));
                    }
                    break;
                case 3:
                    xmlSerializerNewSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    xmlSerializerNewSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    xmlSerializerNewSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    xmlSerializerNewSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    xmlSerializerNewSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    xmlSerializerNewSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    xmlSerializerNewSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    xmlSerializerNewSerializer.docdecl(xmlPullParser.getText());
                    break;
            }
            xmlPullParser.nextToken();
        }
        xmlSerializerNewSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    protected EventStream F(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        long j2;
        ArrayList arrayList;
        String strG0 = g0(xmlPullParser, "schemeIdUri", "");
        String strG02 = g0(xmlPullParser, "value", "");
        long jM = M(xmlPullParser, "timescale", 1L);
        long jM2 = M(xmlPullParser, "presentationTimeOffset", 0L);
        ArrayList arrayList2 = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(512);
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                byteArrayOutputStream = byteArrayOutputStream2;
                long j3 = jM2;
                j2 = jM2;
                arrayList = arrayList2;
                arrayList.add(D(xmlPullParser, strG0, strG02, jM, j3, byteArrayOutputStream));
            } else {
                byteArrayOutputStream = byteArrayOutputStream2;
                j2 = jM2;
                arrayList = arrayList2;
                maybeSkipTag(xmlPullParser);
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream")) {
                break;
            }
            arrayList2 = arrayList;
            byteArrayOutputStream2 = byteArrayOutputStream;
            jM2 = j2;
        }
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Pair pair = (Pair) arrayList.get(i2);
            jArr[i2] = ((Long) pair.first).longValue();
            eventMessageArr[i2] = (EventMessage) pair.second;
        }
        return c(strG0, strG02, jM, jArr, eventMessageArr);
    }

    protected RangedUri I(XmlPullParser xmlPullParser) {
        return S(xmlPullParser, "sourceURL", "range");
    }

    protected Label K(XmlPullParser xmlPullParser) {
        return new Label(xmlPullParser.getAttributeValue(null, "lang"), h0(xmlPullParser, "Label"));
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x01dc A[LOOP:0: B:24:0x00a0->B:80:0x01dc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0197 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected androidx.media3.exoplayer.dash.manifest.DashManifest N(org.xmlpull.v1.XmlPullParser r47, android.net.Uri r48) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.N(org.xmlpull.v1.XmlPullParser, android.net.Uri):androidx.media3.exoplayer.dash.manifest.DashManifest");
    }

    protected Pair P(XmlPullParser xmlPullParser, List list, long j2, long j3, long j4, long j5, boolean z2) throws XmlPullParserException, IOException, NumberFormatException {
        long j6;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        Object obj;
        long j7;
        SegmentBase segmentBaseA0;
        DashManifestParser dashManifestParser = this;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        Object obj2 = null;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "id");
        long jB = B(xmlPullParser2, "start", j2);
        long j8 = C.TIME_UNSET;
        long j9 = j4 != C.TIME_UNSET ? j4 + jB : -9223372036854775807L;
        long jB2 = B(xmlPullParser2, "duration", C.TIME_UNSET);
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        long jQ = j3;
        boolean z3 = false;
        long j10 = -9223372036854775807L;
        SegmentBase segmentBaseY = null;
        Descriptor descriptorX = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (!z3) {
                    jQ = dashManifestParser.q(xmlPullParser2, jQ);
                    z3 = true;
                }
                arrayList6.addAll(dashManifestParser.r(xmlPullParser2, list, z2));
                arrayList3 = arrayList5;
                arrayList = arrayList6;
                j7 = j8;
                obj = obj2;
                arrayList2 = arrayList4;
            } else {
                if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AdaptationSet")) {
                    j6 = jQ;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    arrayList2.add(n(xmlPullParser, !arrayList6.isEmpty() ? arrayList6 : list, segmentBaseY, jB2, jQ, j10, j9, j5, z2));
                    xmlPullParser2 = xmlPullParser;
                    arrayList3 = arrayList5;
                } else {
                    j6 = jQ;
                    ArrayList arrayList7 = arrayList5;
                    arrayList = arrayList6;
                    arrayList2 = arrayList4;
                    xmlPullParser2 = xmlPullParser;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser2, "EventStream")) {
                        arrayList7.add(F(xmlPullParser));
                        arrayList3 = arrayList7;
                    } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentBase")) {
                        arrayList3 = arrayList7;
                        segmentBaseY = Y(xmlPullParser2, null);
                        obj = null;
                        jQ = j6;
                        j7 = C.TIME_UNSET;
                    } else {
                        arrayList3 = arrayList7;
                        if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentList")) {
                            long jQ2 = q(xmlPullParser2, C.TIME_UNSET);
                            obj = null;
                            segmentBaseA0 = Z(xmlPullParser, null, j9, jB2, j6, jQ2, j5);
                            j10 = jQ2;
                            jQ = j6;
                            j7 = C.TIME_UNSET;
                        } else {
                            obj = null;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTemplate")) {
                                long jQ3 = q(xmlPullParser2, C.TIME_UNSET);
                                j7 = -9223372036854775807L;
                                segmentBaseA0 = a0(xmlPullParser, null, ImmutableList.of(), j9, jB2, j6, jQ3, j5);
                                j10 = jQ3;
                                jQ = j6;
                            } else {
                                j7 = C.TIME_UNSET;
                                if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AssetIdentifier")) {
                                    descriptorX = x(xmlPullParser2, "AssetIdentifier");
                                } else {
                                    maybeSkipTag(xmlPullParser);
                                }
                                jQ = j6;
                            }
                        }
                        segmentBaseY = segmentBaseA0;
                    }
                }
                obj = null;
                j7 = C.TIME_UNSET;
                jQ = j6;
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser2, "Period")) {
                return Pair.create(f(attributeValue, jB, arrayList2, arrayList3, descriptorX), Long.valueOf(jB2));
            }
            arrayList4 = arrayList2;
            arrayList6 = arrayList;
            obj2 = obj;
            arrayList5 = arrayList3;
            j8 = j7;
            dashManifestParser = this;
        }
    }

    protected String[] Q(XmlPullParser xmlPullParser, String str, String[] strArr) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? strArr : attributeValue.split(",");
    }

    protected ProgramInformation R(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String strNextText = null;
        String strG0 = g0(xmlPullParser, "moreInformationURL", null);
        String strG02 = g0(xmlPullParser, "lang", null);
        String strNextText2 = null;
        String strNextText3 = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Title")) {
                strNextText = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "Source")) {
                strNextText2 = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.TAG_COPYRIGHT)) {
                strNextText3 = xmlPullParser.nextText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
            String str = strNextText3;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ProgramInformation")) {
                return new ProgramInformation(strNextText, strNextText2, str, strG0, strG02);
            }
            strNextText3 = str;
        }
    }

    protected RangedUri S(XmlPullParser xmlPullParser, String str, String str2) throws NumberFormatException {
        long j2;
        long j3;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] strArrSplit = attributeValue2.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            j2 = Long.parseLong(strArrSplit[0]);
            if (strArrSplit.length == 2) {
                j3 = (Long.parseLong(strArrSplit[1]) - j2) + 1;
            }
            return g(attributeValue, j2, j3);
        }
        j2 = 0;
        j3 = -1;
        return g(attributeValue, j2, j3);
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01ee A[LOOP:0: B:3:0x006a->B:57:0x01ee, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0198 A[EDGE_INSN: B:58:0x0198->B:47:0x0198 BREAK  A[LOOP:0: B:3:0x006a->B:57:0x01ee], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected androidx.media3.exoplayer.dash.manifest.DashManifestParser.RepresentationInfo T(org.xmlpull.v1.XmlPullParser r36, java.util.List r37, java.lang.String r38, java.lang.String r39, int r40, int r41, float r42, int r43, int r44, java.lang.String r45, java.util.List r46, java.util.List r47, java.util.List r48, java.util.List r49, androidx.media3.exoplayer.dash.manifest.SegmentBase r50, long r51, long r53, long r55, long r57, long r59, boolean r61) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 509
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.T(org.xmlpull.v1.XmlPullParser, java.util.List, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, java.util.List, java.util.List, java.util.List, java.util.List, androidx.media3.exoplayer.dash.manifest.SegmentBase, long, long, long, long, long, boolean):androidx.media3.exoplayer.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    protected int U(List list) {
        int iJ0;
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            Descriptor descriptor = (Descriptor) list.get(i3);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                iJ0 = V(descriptor.value);
            } else if (Ascii.equalsIgnoreCase("urn:tva:metadata:cs:AudioPurposeCS:2007", descriptor.schemeIdUri)) {
                iJ0 = j0(descriptor.value);
            }
            i2 |= iJ0;
        }
        return i2;
    }

    protected int V(String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
        }
        return 0;
    }

    protected int W(List list) {
        int i2 = 0;
        for (int i3 = 0; i3 < list.size(); i3++) {
            if (Ascii.equalsIgnoreCase("http://dashif.org/guidelines/trickmode", ((Descriptor) list.get(i3)).schemeIdUri)) {
                i2 = 16384;
            }
        }
        return i2;
    }

    protected int X(List list) {
        int iV = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                iV |= V(descriptor.value);
            }
        }
        return iV;
    }

    protected SegmentBase.SingleSegmentBase Y(XmlPullParser xmlPullParser, SegmentBase.SingleSegmentBase singleSegmentBase) throws XmlPullParserException, NumberFormatException, IOException {
        long j2;
        long j3;
        long jM = M(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.f5174b : 1L);
        long jM2 = M(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.f5175c : 0L);
        long j4 = singleSegmentBase != null ? singleSegmentBase.f5186d : 0L;
        long j5 = singleSegmentBase != null ? singleSegmentBase.f5187e : 0L;
        String attributeValue = xmlPullParser.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] strArrSplit = attributeValue.split(Constants.ACCEPT_TIME_SEPARATOR_SERVER);
            j3 = Long.parseLong(strArrSplit[0]);
            j2 = (Long.parseLong(strArrSplit[1]) - j3) + 1;
        } else {
            j2 = j5;
            j3 = j4;
        }
        RangedUri rangedUriI = singleSegmentBase != null ? singleSegmentBase.f5173a : null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUriI = I(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase"));
        return l(rangedUriI, jM, jM2, j3, j2);
    }

    protected SegmentBase.SegmentList Z(XmlPullParser xmlPullParser, SegmentBase.SegmentList segmentList, long j2, long j3, long j4, long j5, long j6) throws XmlPullParserException, IOException {
        long jM = M(xmlPullParser, "timescale", segmentList != null ? segmentList.f5174b : 1L);
        long jM2 = M(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.f5175c : 0L);
        long jM3 = M(xmlPullParser, "duration", segmentList != null ? segmentList.f5177e : C.TIME_UNSET);
        long jM4 = M(xmlPullParser, "startNumber", segmentList != null ? segmentList.f5176d : 1L);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j4, j5);
        List listB0 = null;
        List arrayList = null;
        RangedUri rangedUriI = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUriI = I(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                listB0 = b0(xmlPullParser, jM, j3);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(c0(xmlPullParser));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList != null) {
            if (rangedUriI == null) {
                rangedUriI = segmentList.f5173a;
            }
            if (listB0 == null) {
                listB0 = segmentList.f5178f;
            }
            if (arrayList == null) {
                arrayList = segmentList.f5180h;
            }
        }
        return i(rangedUriI, jM, jM2, jM4, jM3, listB0, finalAvailabilityTimeOffset, arrayList, j6, j2);
    }

    protected AdaptationSet a(long j2, int i2, List list, List list2, List list3, List list4) {
        return new AdaptationSet(j2, i2, list, list2, list3, list4);
    }

    protected SegmentBase.SegmentTemplate a0(XmlPullParser xmlPullParser, SegmentBase.SegmentTemplate segmentTemplate, List list, long j2, long j3, long j4, long j5, long j6) throws XmlPullParserException, IOException {
        long jM = M(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.f5174b : 1L);
        long jM2 = M(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.f5175c : 0L);
        long jM3 = M(xmlPullParser, "duration", segmentTemplate != null ? segmentTemplate.f5177e : C.TIME_UNSET);
        long jM4 = M(xmlPullParser, "startNumber", segmentTemplate != null ? segmentTemplate.f5176d : 1L);
        long jL = L(list);
        long finalAvailabilityTimeOffset = getFinalAvailabilityTimeOffset(j4, j5);
        List listB0 = null;
        UrlTemplate urlTemplateK0 = k0(xmlPullParser, "media", segmentTemplate != null ? segmentTemplate.f5182i : null);
        UrlTemplate urlTemplateK02 = k0(xmlPullParser, "initialization", segmentTemplate != null ? segmentTemplate.f5181h : null);
        RangedUri rangedUriI = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUriI = I(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                listB0 = b0(xmlPullParser, jM, j3);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate"));
        if (segmentTemplate != null) {
            if (rangedUriI == null) {
                rangedUriI = segmentTemplate.f5173a;
            }
            if (listB0 == null) {
                listB0 = segmentTemplate.f5178f;
            }
        }
        return j(rangedUriI, jM, jM2, jM4, jL, jM3, listB0, finalAvailabilityTimeOffset, urlTemplateK02, urlTemplateK0, j6, j2);
    }

    protected EventMessage b(String str, String str2, long j2, long j3, byte[] bArr) {
        return new EventMessage(str, str2, j3, j2, bArr);
    }

    protected List b0(XmlPullParser xmlPullParser, long j2, long j3) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long jAddSegmentTimelineElementsToList = 0;
        long jM = -9223372036854775807L;
        boolean z2 = false;
        int iJ = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.LATITUDE_SOUTH)) {
                long jM2 = M(xmlPullParser, "t", C.TIME_UNSET);
                if (z2) {
                    jAddSegmentTimelineElementsToList = addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, jM, iJ, jM2);
                }
                if (jM2 == C.TIME_UNSET) {
                    jM2 = jAddSegmentTimelineElementsToList;
                }
                jM = M(xmlPullParser, "d", C.TIME_UNSET);
                iJ = J(xmlPullParser, "r", 0);
                z2 = true;
                jAddSegmentTimelineElementsToList = jM2;
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        if (z2) {
            addSegmentTimelineElementsToList(arrayList, jAddSegmentTimelineElementsToList, jM, iJ, Util.scaleLargeTimestamp(j3, j2, 1000L));
        }
        return arrayList;
    }

    protected EventStream c(String str, String str2, long j2, long[] jArr, EventMessage[] eventMessageArr) {
        return new EventStream(str, str2, j2, jArr, eventMessageArr);
    }

    protected RangedUri c0(XmlPullParser xmlPullParser) {
        return S(xmlPullParser, "media", "mediaRange");
    }

    protected Format d(String str, String str2, int i2, int i3, float f2, int i4, int i5, int i6, String str3, List list, List list2, String str4, List list3, List list4) {
        String str5 = str4;
        String sampleMimeType = getSampleMimeType(str2, str5);
        if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType)) {
            sampleMimeType = C(list4);
            if (MimeTypes.AUDIO_E_AC3_JOC.equals(sampleMimeType)) {
                str5 = MimeTypes.CODEC_E_AC3_JOC;
            }
        }
        int iE0 = e0(list);
        int iX = X(list) | U(list2) | W(list3) | W(list4);
        Pair pairI0 = i0(list3);
        Format.Builder language = new Format.Builder().setId(str).setContainerMimeType(str2).setSampleMimeType(sampleMimeType).setCodecs(str5).setPeakBitrate(i6).setSelectionFlags(iE0).setRoleFlags(iX).setLanguage(str3);
        int iT = -1;
        Format.Builder tileCountVertical = language.setTileCountHorizontal(pairI0 != null ? ((Integer) pairI0.first).intValue() : -1).setTileCountVertical(pairI0 != null ? ((Integer) pairI0.second).intValue() : -1);
        if (MimeTypes.isVideo(sampleMimeType)) {
            tileCountVertical.setWidth(i2).setHeight(i3).setFrameRate(f2);
        } else if (MimeTypes.isAudio(sampleMimeType)) {
            tileCountVertical.setChannelCount(i4).setSampleRate(i5);
        } else if (MimeTypes.isText(sampleMimeType)) {
            if (MimeTypes.APPLICATION_CEA608.equals(sampleMimeType)) {
                iT = s(list2);
            } else if (MimeTypes.APPLICATION_CEA708.equals(sampleMimeType)) {
                iT = t(list2);
            }
            tileCountVertical.setAccessibilityChannel(iT);
        } else if (MimeTypes.isImage(sampleMimeType)) {
            tileCountVertical.setWidth(i2).setHeight(i3);
        }
        return tileCountVertical.build();
    }

    protected int d0(String str) {
        if (str == null) {
            return 0;
        }
        return (str.equals("forced_subtitle") || str.equals("forced-subtitle")) ? 2 : 0;
    }

    protected DashManifest e(long j2, long j3, long j4, boolean z2, long j5, long j6, long j7, long j8, ProgramInformation programInformation, UtcTimingElement utcTimingElement, ServiceDescriptionElement serviceDescriptionElement, Uri uri, List list) {
        return new DashManifest(j2, j3, j4, z2, j5, j6, j7, j8, programInformation, utcTimingElement, serviceDescriptionElement, uri, list);
    }

    protected int e0(List list) {
        int iD0 = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if (Ascii.equalsIgnoreCase("urn:mpeg:dash:role:2011", descriptor.schemeIdUri)) {
                iD0 |= d0(descriptor.value);
            }
        }
        return iD0;
    }

    protected Period f(String str, long j2, List list, List list2, Descriptor descriptor) {
        return new Period(str, j2, list, list2, descriptor);
    }

    protected ServiceDescriptionElement f0(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        long jM = -9223372036854775807L;
        long jM2 = -9223372036854775807L;
        long jM3 = -9223372036854775807L;
        float fG = -3.4028235E38f;
        float fG2 = -3.4028235E38f;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Latency")) {
                jM = M(xmlPullParser, "target", C.TIME_UNSET);
                jM2 = M(xmlPullParser, "min", C.TIME_UNSET);
                jM3 = M(xmlPullParser, KsProperty.Max, C.TIME_UNSET);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "PlaybackRate")) {
                fG = G(xmlPullParser, "min", -3.4028235E38f);
                fG2 = G(xmlPullParser, KsProperty.Max, -3.4028235E38f);
            }
            long j2 = jM;
            long j3 = jM2;
            long j4 = jM3;
            float f2 = fG;
            float f3 = fG2;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ServiceDescription")) {
                return new ServiceDescriptionElement(j2, j3, j4, f2, f3);
            }
            jM = j2;
            jM2 = j3;
            jM3 = j4;
            fG = f2;
            fG2 = f3;
        }
    }

    protected RangedUri g(String str, long j2, long j3) {
        return new RangedUri(str, j2, j3);
    }

    protected Representation h(RepresentationInfo representationInfo, String str, List list, String str2, ArrayList arrayList, ArrayList arrayList2) {
        Format.Builder builderBuildUpon = representationInfo.format.buildUpon();
        if (str == null || !list.isEmpty()) {
            builderBuildUpon.setLabels(list);
        } else {
            builderBuildUpon.setLabel(str);
        }
        String str3 = representationInfo.drmSchemeType;
        if (str3 == null) {
            str3 = str2;
        }
        ArrayList<DrmInitData.SchemeData> arrayList3 = representationInfo.drmSchemeDatas;
        arrayList3.addAll(arrayList);
        if (!arrayList3.isEmpty()) {
            fillInClearKeyInformation(arrayList3);
            filterRedundantIncompleteSchemeDatas(arrayList3);
            builderBuildUpon.setDrmInitData(new DrmInitData(str3, arrayList3));
        }
        ArrayList<Descriptor> arrayList4 = representationInfo.inbandEventStreams;
        arrayList4.addAll(arrayList2);
        return Representation.newInstance(representationInfo.revisionId, builderBuildUpon.build(), representationInfo.baseUrls, representationInfo.segmentBase, arrayList4, representationInfo.essentialProperties, representationInfo.supplementalProperties, null);
    }

    protected SegmentBase.SegmentList i(RangedUri rangedUri, long j2, long j3, long j4, long j5, List list, long j6, List list2, long j7, long j8) {
        return new SegmentBase.SegmentList(rangedUri, j2, j3, j4, j5, list, j6, list2, Util.msToUs(j7), Util.msToUs(j8));
    }

    protected Pair i0(List list) {
        String str;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if ((Ascii.equalsIgnoreCase("http://dashif.org/thumbnail_tile", descriptor.schemeIdUri) || Ascii.equalsIgnoreCase("http://dashif.org/guidelines/thumbnail_tile", descriptor.schemeIdUri)) && (str = descriptor.value) != null) {
                String[] strArrSplit = Util.split(str, "x");
                if (strArrSplit.length != 2) {
                    continue;
                } else {
                    try {
                        return Pair.create(Integer.valueOf(Integer.parseInt(strArrSplit[0])), Integer.valueOf(Integer.parseInt(strArrSplit[1])));
                    } catch (NumberFormatException unused) {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    protected SegmentBase.SegmentTemplate j(RangedUri rangedUri, long j2, long j3, long j4, long j5, long j6, List list, long j7, UrlTemplate urlTemplate, UrlTemplate urlTemplate2, long j8, long j9) {
        return new SegmentBase.SegmentTemplate(rangedUri, j2, j3, j4, j5, j6, list, j7, urlTemplate, urlTemplate2, Util.msToUs(j8), Util.msToUs(j9));
    }

    protected int j0(String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
        }
        return 0;
    }

    protected SegmentBase.SegmentTimelineElement k(long j2, long j3) {
        return new SegmentBase.SegmentTimelineElement(j2, j3);
    }

    protected UrlTemplate k0(XmlPullParser xmlPullParser, String str, UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    protected SegmentBase.SingleSegmentBase l(RangedUri rangedUri, long j2, long j3, long j4, long j5) {
        return new SegmentBase.SingleSegmentBase(rangedUri, j2, j3, j4, j5);
    }

    protected UtcTimingElement l0(XmlPullParser xmlPullParser) {
        return m(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, "value"));
    }

    protected UtcTimingElement m(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0352 A[LOOP:0: B:3:0x007f->B:74:0x0352, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0310 A[EDGE_INSN: B:75:0x0310->B:68:0x0310 BREAK  A[LOOP:0: B:3:0x007f->B:74:0x0352], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected androidx.media3.exoplayer.dash.manifest.AdaptationSet n(org.xmlpull.v1.XmlPullParser r57, java.util.List r58, androidx.media3.exoplayer.dash.manifest.SegmentBase r59, long r60, long r62, long r64, long r66, long r68, boolean r70) throws org.xmlpull.v1.XmlPullParserException, java.lang.NumberFormatException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 877
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.n(org.xmlpull.v1.XmlPullParser, java.util.List, androidx.media3.exoplayer.dash.manifest.SegmentBase, long, long, long, long, long, boolean):androidx.media3.exoplayer.dash.manifest.AdaptationSet");
    }

    protected void o(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        maybeSkipTag(xmlPullParser);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected int p(org.xmlpull.v1.XmlPullParser r4) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r3 = this;
            java.lang.String r0 = "schemeIdUri"
            r1 = 0
            java.lang.String r0 = g0(r4, r0, r1)
            r0.hashCode()
            r1 = -1
            int r2 = r0.hashCode()
            switch(r2) {
                case -2128649360: goto L56;
                case -1352850286: goto L4b;
                case -1138141449: goto L40;
                case -986633423: goto L35;
                case -79006963: goto L2a;
                case 312179081: goto L1f;
                case 2036691300: goto L14;
                default: goto L12;
            }
        L12:
            r0 = r1
            goto L60
        L14:
            java.lang.String r2 = "urn:dolby:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L1d
            goto L12
        L1d:
            r0 = 6
            goto L60
        L1f:
            java.lang.String r2 = "tag:dts.com,2018:uhd:audio_channel_configuration"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L28
            goto L12
        L28:
            r0 = 5
            goto L60
        L2a:
            java.lang.String r2 = "tag:dts.com,2014:dash:audio_channel_configuration:2012"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L33
            goto L12
        L33:
            r0 = 4
            goto L60
        L35:
            java.lang.String r2 = "urn:mpeg:mpegB:cicp:ChannelConfiguration"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L3e
            goto L12
        L3e:
            r0 = 3
            goto L60
        L40:
            java.lang.String r2 = "tag:dolby.com,2014:dash:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L49
            goto L12
        L49:
            r0 = 2
            goto L60
        L4b:
            java.lang.String r2 = "urn:mpeg:dash:23003:3:audio_channel_configuration:2011"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L54
            goto L12
        L54:
            r0 = 1
            goto L60
        L56:
            java.lang.String r2 = "urn:dts:dash:audio_channel_configuration:2012"
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L5f
            goto L12
        L5f:
            r0 = 0
        L60:
            switch(r0) {
                case 0: goto L7a;
                case 1: goto L73;
                case 2: goto L6e;
                case 3: goto L69;
                case 4: goto L7a;
                case 5: goto L64;
                case 6: goto L6e;
                default: goto L63;
            }
        L63:
            goto L7e
        L64:
            int r1 = A(r4)
            goto L7e
        L69:
            int r1 = O(r4)
            goto L7e
        L6e:
            int r1 = y(r4)
            goto L7e
        L73:
            java.lang.String r0 = "value"
            int r1 = J(r4, r0, r1)
            goto L7e
        L7a:
            int r1 = z(r4)
        L7e:
            r4.next()
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = androidx.media3.common.util.XmlPullParserUtil.isEndTag(r4, r0)
            if (r0 == 0) goto L7e
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.p(org.xmlpull.v1.XmlPullParser):int");
    }

    protected long q(XmlPullParser xmlPullParser, long j2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "availabilityTimeOffset");
        if (attributeValue == null) {
            return j2;
        }
        if ("INF".equals(attributeValue)) {
            return Long.MAX_VALUE;
        }
        return (long) (Float.parseFloat(attributeValue) * 1000000.0f);
    }

    protected List r(XmlPullParser xmlPullParser, List list, boolean z2) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "dvb:priority");
        int i2 = attributeValue != null ? Integer.parseInt(attributeValue) : z2 ? 1 : Integer.MIN_VALUE;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "dvb:weight");
        int i3 = attributeValue2 != null ? Integer.parseInt(attributeValue2) : 1;
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "serviceLocation");
        String strH0 = h0(xmlPullParser, "BaseURL");
        if (UriUtil.isAbsolute(strH0)) {
            if (attributeValue3 == null) {
                attributeValue3 = strH0;
            }
            return Lists.newArrayList(new BaseUrl(strH0, attributeValue3, i2, i3));
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < list.size(); i4++) {
            BaseUrl baseUrl = (BaseUrl) list.get(i4);
            String strResolve = UriUtil.resolve(baseUrl.url, strH0);
            String str = attributeValue3 == null ? strResolve : attributeValue3;
            if (z2) {
                i2 = baseUrl.priority;
                i3 = baseUrl.weight;
                str = baseUrl.serviceLocation;
            }
            arrayList.add(new BaseUrl(strResolve, str, i2, i3));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0119  */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v25 */
    /* JADX WARN: Type inference failed for: r5v26 */
    /* JADX WARN: Type inference failed for: r5v27 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected android.util.Pair u(org.xmlpull.v1.XmlPullParser r12) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.dash.manifest.DashManifestParser.u(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    protected int v(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if ("audio".equals(attributeValue)) {
            return 1;
        }
        if ("video".equals(attributeValue)) {
            return 2;
        }
        if ("text".equals(attributeValue)) {
            return 3;
        }
        return "image".equals(attributeValue) ? 4 : -1;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // androidx.media3.exoplayer.upstream.ParsingLoadable.Parser
    public DashManifest parse(Uri uri, InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParserNewPullParser = this.xmlParserFactory.newPullParser();
            xmlPullParserNewPullParser.setInput(inputStream, null);
            if (xmlPullParserNewPullParser.next() == 2 && "MPD".equals(xmlPullParserNewPullParser.getName())) {
                return N(xmlPullParserNewPullParser, uri);
            }
            throw ParserException.createForMalformedManifest("inputStream does not contain a valid media presentation description", null);
        } catch (XmlPullParserException e2) {
            throw ParserException.createForMalformedManifest(null, e2);
        }
    }
}
