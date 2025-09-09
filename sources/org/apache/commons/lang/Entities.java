package org.apache.commons.lang;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.alink.business.devicecenter.base.AlinkConstants;
import com.aliyun.alink.linksdk.alcs.coap.resources.LinkFormat;
import com.aliyun.linksdk.alcs.AlcsConstant;
import com.facebook.appevents.UserDataStore;
import com.taobao.accs.common.Constants;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes5.dex */
class Entities {
    private static final String[][] APOS_ARRAY;
    private static final String[][] BASIC_ARRAY;
    public static final Entities HTML32;
    public static final Entities HTML40;
    public static final Entities XML;

    /* renamed from: a, reason: collision with root package name */
    static final String[][] f26613a;

    /* renamed from: b, reason: collision with root package name */
    static final String[][] f26614b;
    private final EntityMap map = new LookupEntityMap();

    static class BinaryEntityMap extends ArrayEntityMap {
        public BinaryEntityMap() {
        }

        private int binarySearch(int i2) {
            int i3 = this.f26616b - 1;
            int i4 = 0;
            while (i4 <= i3) {
                int i5 = (i4 + i3) >>> 1;
                int i6 = this.f26618d[i5];
                if (i6 < i2) {
                    i4 = i5 + 1;
                } else {
                    if (i6 <= i2) {
                        return i5;
                    }
                    i3 = i5 - 1;
                }
            }
            return -(i4 + 1);
        }

        @Override // org.apache.commons.lang.Entities.ArrayEntityMap, org.apache.commons.lang.Entities.EntityMap
        public void add(String str, int i2) {
            a(this.f26616b + 1);
            int iBinarySearch = binarySearch(i2);
            if (iBinarySearch > 0) {
                return;
            }
            int i3 = -(iBinarySearch + 1);
            int[] iArr = this.f26618d;
            int i4 = i3 + 1;
            System.arraycopy(iArr, i3, iArr, i4, this.f26616b - i3);
            this.f26618d[i3] = i2;
            String[] strArr = this.f26617c;
            System.arraycopy(strArr, i3, strArr, i4, this.f26616b - i3);
            this.f26617c[i3] = str;
            this.f26616b++;
        }

        @Override // org.apache.commons.lang.Entities.ArrayEntityMap, org.apache.commons.lang.Entities.EntityMap
        public String name(int i2) {
            int iBinarySearch = binarySearch(i2);
            if (iBinarySearch < 0) {
                return null;
            }
            return this.f26617c[iBinarySearch];
        }

        public BinaryEntityMap(int i2) {
            super(i2);
        }
    }

    interface EntityMap {
        void add(String str, int i2);

        String name(int i2);

        int value(String str);
    }

    static class HashEntityMap extends MapIntMap {
        public HashEntityMap() {
            super(new HashMap(), new HashMap());
        }
    }

    static class LookupEntityMap extends PrimitiveEntityMap {
        private static final int LOOKUP_TABLE_SIZE = 256;
        private String[] lookupTable;

        LookupEntityMap() {
        }

        private void createLookupTable() {
            this.lookupTable = new String[256];
            for (int i2 = 0; i2 < 256; i2++) {
                this.lookupTable[i2] = super.name(i2);
            }
        }

        private String[] lookupTable() {
            if (this.lookupTable == null) {
                createLookupTable();
            }
            return this.lookupTable;
        }

        @Override // org.apache.commons.lang.Entities.PrimitiveEntityMap, org.apache.commons.lang.Entities.EntityMap
        public String name(int i2) {
            return i2 < 256 ? lookupTable()[i2] : super.name(i2);
        }
    }

    static abstract class MapIntMap implements EntityMap {

        /* renamed from: a, reason: collision with root package name */
        protected final Map f26619a;

        /* renamed from: b, reason: collision with root package name */
        protected final Map f26620b;

        MapIntMap(Map map, Map map2) {
            this.f26619a = map;
            this.f26620b = map2;
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public void add(String str, int i2) {
            this.f26619a.put(str, new Integer(i2));
            this.f26620b.put(new Integer(i2), str);
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public String name(int i2) {
            return (String) this.f26620b.get(new Integer(i2));
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public int value(String str) {
            Object obj = this.f26619a.get(str);
            if (obj == null) {
                return -1;
            }
            return ((Integer) obj).intValue();
        }
    }

    static class PrimitiveEntityMap implements EntityMap {
        private final Map mapNameToValue = new HashMap();
        private final IntHashMap mapValueToName = new IntHashMap();

        PrimitiveEntityMap() {
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public void add(String str, int i2) {
            this.mapNameToValue.put(str, new Integer(i2));
            this.mapValueToName.put(i2, str);
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public String name(int i2) {
            return (String) this.mapValueToName.get(i2);
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public int value(String str) {
            Object obj = this.mapNameToValue.get(str);
            if (obj == null) {
                return -1;
            }
            return ((Integer) obj).intValue();
        }
    }

    static class TreeEntityMap extends MapIntMap {
        public TreeEntityMap() {
            super(new TreeMap(), new TreeMap());
        }
    }

    static {
        String[][] strArr = {new String[]{"quot", "34"}, new String[]{"amp", "38"}, new String[]{LinkFormat.LIFE_TIME, "60"}, new String[]{"gt", "62"}};
        BASIC_ARRAY = strArr;
        String[][] strArr2 = {new String[]{"apos", "39"}};
        APOS_ARRAY = strArr2;
        String[][] strArr3 = {new String[]{"nbsp", "160"}, new String[]{"iexcl", "161"}, new String[]{"cent", "162"}, new String[]{"pound", "163"}, new String[]{"curren", "164"}, new String[]{"yen", "165"}, new String[]{"brvbar", "166"}, new String[]{"sect", "167"}, new String[]{"uml", "168"}, new String[]{"copy", "169"}, new String[]{"ordf", "170"}, new String[]{"laquo", "171"}, new String[]{"not", "172"}, new String[]{"shy", "173"}, new String[]{"reg", "174"}, new String[]{"macr", "175"}, new String[]{"deg", "176"}, new String[]{"plusmn", "177"}, new String[]{"sup2", "178"}, new String[]{"sup3", "179"}, new String[]{"acute", "180"}, new String[]{"micro", "181"}, new String[]{"para", "182"}, new String[]{"middot", "183"}, new String[]{"cedil", "184"}, new String[]{"sup1", "185"}, new String[]{"ordm", "186"}, new String[]{"raquo", "187"}, new String[]{"frac14", "188"}, new String[]{"frac12", "189"}, new String[]{"frac34", "190"}, new String[]{"iquest", "191"}, new String[]{"Agrave", "192"}, new String[]{"Aacute", "193"}, new String[]{"Acirc", "194"}, new String[]{"Atilde", "195"}, new String[]{"Auml", "196"}, new String[]{"Aring", "197"}, new String[]{"AElig", "198"}, new String[]{"Ccedil", "199"}, new String[]{"Egrave", "200"}, new String[]{"Eacute", "201"}, new String[]{"Ecirc", "202"}, new String[]{"Euml", "203"}, new String[]{"Igrave", "204"}, new String[]{"Iacute", "205"}, new String[]{"Icirc", "206"}, new String[]{"Iuml", "207"}, new String[]{"ETH", "208"}, new String[]{"Ntilde", "209"}, new String[]{"Ograve", "210"}, new String[]{"Oacute", "211"}, new String[]{"Ocirc", "212"}, new String[]{"Otilde", "213"}, new String[]{"Ouml", "214"}, new String[]{Constants.KEY_TIMES, "215"}, new String[]{"Oslash", "216"}, new String[]{"Ugrave", "217"}, new String[]{"Uacute", "218"}, new String[]{"Ucirc", "219"}, new String[]{"Uuml", "220"}, new String[]{"Yacute", "221"}, new String[]{"THORN", "222"}, new String[]{"szlig", "223"}, new String[]{"agrave", "224"}, new String[]{"aacute", "225"}, new String[]{"acirc", "226"}, new String[]{"atilde", "227"}, new String[]{"auml", "228"}, new String[]{"aring", "229"}, new String[]{"aelig", "230"}, new String[]{"ccedil", "231"}, new String[]{"egrave", "232"}, new String[]{"eacute", "233"}, new String[]{"ecirc", "234"}, new String[]{"euml", "235"}, new String[]{"igrave", "236"}, new String[]{"iacute", "237"}, new String[]{"icirc", "238"}, new String[]{"iuml", "239"}, new String[]{"eth", "240"}, new String[]{"ntilde", "241"}, new String[]{"ograve", "242"}, new String[]{"oacute", "243"}, new String[]{"ocirc", "244"}, new String[]{"otilde", "245"}, new String[]{"ouml", "246"}, new String[]{"divide", "247"}, new String[]{"oslash", "248"}, new String[]{"ugrave", "249"}, new String[]{"uacute", "250"}, new String[]{"ucirc", "251"}, new String[]{"uuml", "252"}, new String[]{"yacute", "253"}, new String[]{"thorn", "254"}, new String[]{"yuml", "255"}};
        f26613a = strArr3;
        f26614b = new String[][]{new String[]{"fnof", "402"}, new String[]{"Alpha", "913"}, new String[]{"Beta", "914"}, new String[]{ExifInterface.TAG_GAMMA, "915"}, new String[]{"Delta", "916"}, new String[]{"Epsilon", "917"}, new String[]{"Zeta", "918"}, new String[]{"Eta", "919"}, new String[]{"Theta", "920"}, new String[]{"Iota", "921"}, new String[]{"Kappa", "922"}, new String[]{"Lambda", "923"}, new String[]{"Mu", "924"}, new String[]{"Nu", "925"}, new String[]{"Xi", "926"}, new String[]{"Omicron", "927"}, new String[]{"Pi", "928"}, new String[]{"Rho", "929"}, new String[]{"Sigma", "931"}, new String[]{"Tau", "932"}, new String[]{"Upsilon", "933"}, new String[]{"Phi", "934"}, new String[]{"Chi", "935"}, new String[]{"Psi", "936"}, new String[]{"Omega", "937"}, new String[]{"alpha", "945"}, new String[]{"beta", "946"}, new String[]{"gamma", "947"}, new String[]{"delta", "948"}, new String[]{"epsilon", "949"}, new String[]{"zeta", "950"}, new String[]{"eta", "951"}, new String[]{"theta", "952"}, new String[]{"iota", "953"}, new String[]{"kappa", "954"}, new String[]{"lambda", "955"}, new String[]{"mu", "956"}, new String[]{"nu", "957"}, new String[]{"xi", "958"}, new String[]{"omicron", "959"}, new String[]{AlinkConstants.KEY_PI, "960"}, new String[]{"rho", "961"}, new String[]{"sigmaf", "962"}, new String[]{"sigma", "963"}, new String[]{"tau", "964"}, new String[]{"upsilon", "965"}, new String[]{"phi", "966"}, new String[]{"chi", "967"}, new String[]{"psi", "968"}, new String[]{"omega", "969"}, new String[]{"thetasym", "977"}, new String[]{"upsih", "978"}, new String[]{"piv", "982"}, new String[]{"bull", "8226"}, new String[]{"hellip", "8230"}, new String[]{"prime", "8242"}, new String[]{"Prime", "8243"}, new String[]{"oline", "8254"}, new String[]{"frasl", "8260"}, new String[]{"weierp", "8472"}, new String[]{"image", "8465"}, new String[]{"real", "8476"}, new String[]{"trade", "8482"}, new String[]{"alefsym", "8501"}, new String[]{"larr", "8592"}, new String[]{"uarr", "8593"}, new String[]{"rarr", "8594"}, new String[]{"darr", "8595"}, new String[]{"harr", "8596"}, new String[]{"crarr", "8629"}, new String[]{"lArr", "8656"}, new String[]{"uArr", "8657"}, new String[]{"rArr", "8658"}, new String[]{"dArr", "8659"}, new String[]{"hArr", "8660"}, new String[]{"forall", "8704"}, new String[]{"part", "8706"}, new String[]{"exist", "8707"}, new String[]{"empty", "8709"}, new String[]{"nabla", "8711"}, new String[]{"isin", "8712"}, new String[]{"notin", "8713"}, new String[]{"ni", "8715"}, new String[]{"prod", "8719"}, new String[]{"sum", "8721"}, new String[]{"minus", "8722"}, new String[]{"lowast", "8727"}, new String[]{"radic", "8730"}, new String[]{"prop", "8733"}, new String[]{"infin", "8734"}, new String[]{"ang", "8736"}, new String[]{"and", "8743"}, new String[]{"or", "8744"}, new String[]{"cap", "8745"}, new String[]{"cup", "8746"}, new String[]{"int", "8747"}, new String[]{"there4", "8756"}, new String[]{"sim", "8764"}, new String[]{"cong", "8773"}, new String[]{"asymp", "8776"}, new String[]{"ne", "8800"}, new String[]{"equiv", "8801"}, new String[]{"le", "8804"}, new String[]{UserDataStore.GENDER, "8805"}, new String[]{AlcsConstant.EVENT_SUB, "8834"}, new String[]{"sup", "8835"}, new String[]{"sube", "8838"}, new String[]{"supe", "8839"}, new String[]{"oplus", "8853"}, new String[]{"otimes", "8855"}, new String[]{"perp", "8869"}, new String[]{"sdot", "8901"}, new String[]{"lceil", "8968"}, new String[]{"rceil", "8969"}, new String[]{"lfloor", "8970"}, new String[]{"rfloor", "8971"}, new String[]{"lang", "9001"}, new String[]{"rang", "9002"}, new String[]{"loz", "9674"}, new String[]{"spades", "9824"}, new String[]{"clubs", "9827"}, new String[]{"hearts", "9829"}, new String[]{"diams", "9830"}, new String[]{"OElig", "338"}, new String[]{"oelig", "339"}, new String[]{"Scaron", "352"}, new String[]{"scaron", "353"}, new String[]{"Yuml", "376"}, new String[]{"circ", "710"}, new String[]{"tilde", "732"}, new String[]{"ensp", "8194"}, new String[]{"emsp", "8195"}, new String[]{"thinsp", "8201"}, new String[]{"zwnj", "8204"}, new String[]{"zwj", "8205"}, new String[]{"lrm", "8206"}, new String[]{"rlm", "8207"}, new String[]{"ndash", "8211"}, new String[]{"mdash", "8212"}, new String[]{"lsquo", "8216"}, new String[]{"rsquo", "8217"}, new String[]{"sbquo", "8218"}, new String[]{"ldquo", "8220"}, new String[]{"rdquo", "8221"}, new String[]{"bdquo", "8222"}, new String[]{"dagger", "8224"}, new String[]{"Dagger", "8225"}, new String[]{"permil", "8240"}, new String[]{"lsaquo", "8249"}, new String[]{"rsaquo", "8250"}, new String[]{"euro", "8364"}};
        Entities entities = new Entities();
        entities.addEntities(strArr);
        entities.addEntities(strArr2);
        XML = entities;
        Entities entities2 = new Entities();
        entities2.addEntities(strArr);
        entities2.addEntities(strArr3);
        HTML32 = entities2;
        Entities entities3 = new Entities();
        a(entities3);
        HTML40 = entities3;
    }

    static void a(Entities entities) {
        entities.addEntities(BASIC_ARRAY);
        entities.addEntities(f26613a);
        entities.addEntities(f26614b);
    }

    private StringWriter createStringWriter(String str) {
        return new StringWriter((int) (str.length() + (str.length() * 0.1d)));
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void doUnescape(java.io.Writer r11, java.lang.String r12, int r13) throws java.io.IOException, java.lang.NumberFormatException {
        /*
            r10 = this;
            r0 = 0
            r11.write(r12, r0, r13)
            int r1 = r12.length()
        L8:
            if (r13 >= r1) goto L86
            char r2 = r12.charAt(r13)
            r3 = 38
            r4 = 1
            if (r2 != r3) goto L81
            int r5 = r13 + 1
            r6 = 59
            int r7 = r12.indexOf(r6, r5)
            r8 = -1
            if (r7 != r8) goto L22
            r11.write(r2)
            goto L84
        L22:
            int r9 = r12.indexOf(r3, r5)
            if (r9 == r8) goto L2e
            if (r9 >= r7) goto L2e
            r11.write(r2)
            goto L84
        L2e:
            java.lang.String r13 = r12.substring(r5, r7)
            int r2 = r13.length()
            if (r2 <= 0) goto L6f
            char r5 = r13.charAt(r0)
            r9 = 35
            if (r5 != r9) goto L6a
            if (r2 <= r4) goto L6f
            char r2 = r13.charAt(r4)
            r5 = 88
            if (r2 == r5) goto L59
            r5 = 120(0x78, float:1.68E-43)
            if (r2 == r5) goto L59
            java.lang.String r2 = r13.substring(r4)     // Catch: java.lang.NumberFormatException -> L6f
            r5 = 10
            int r2 = java.lang.Integer.parseInt(r2, r5)     // Catch: java.lang.NumberFormatException -> L6f
            goto L64
        L59:
            r2 = 2
            java.lang.String r2 = r13.substring(r2)     // Catch: java.lang.NumberFormatException -> L6f
            r5 = 16
            int r2 = java.lang.Integer.parseInt(r2, r5)     // Catch: java.lang.NumberFormatException -> L6f
        L64:
            r5 = 65535(0xffff, float:9.1834E-41)
            if (r2 <= r5) goto L70
            goto L6f
        L6a:
            int r2 = r10.entityValue(r13)
            goto L70
        L6f:
            r2 = r8
        L70:
            if (r2 != r8) goto L7c
            r11.write(r3)
            r11.write(r13)
            r11.write(r6)
            goto L7f
        L7c:
            r11.write(r2)
        L7f:
            r13 = r7
            goto L84
        L81:
            r11.write(r2)
        L84:
            int r13 = r13 + r4
            goto L8
        L86:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang.Entities.doUnescape(java.io.Writer, java.lang.String, int):void");
    }

    public void addEntities(String[][] strArr) {
        for (String[] strArr2 : strArr) {
            addEntity(strArr2[0], Integer.parseInt(strArr2[1]));
        }
    }

    public void addEntity(String str, int i2) {
        this.map.add(str, i2);
    }

    public String entityName(int i2) {
        return this.map.name(i2);
    }

    public int entityValue(String str) {
        return this.map.value(str);
    }

    public String escape(String str) {
        StringWriter stringWriterCreateStringWriter = createStringWriter(str);
        try {
            escape(stringWriterCreateStringWriter, str);
            return stringWriterCreateStringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    public String unescape(String str) throws NumberFormatException {
        int iIndexOf = str.indexOf(38);
        if (iIndexOf < 0) {
            return str;
        }
        StringWriter stringWriterCreateStringWriter = createStringWriter(str);
        try {
            doUnescape(stringWriterCreateStringWriter, str, iIndexOf);
            return stringWriterCreateStringWriter.toString();
        } catch (IOException e2) {
            throw new UnhandledException(e2);
        }
    }

    static class ArrayEntityMap implements EntityMap {

        /* renamed from: a, reason: collision with root package name */
        protected final int f26615a;

        /* renamed from: b, reason: collision with root package name */
        protected int f26616b;

        /* renamed from: c, reason: collision with root package name */
        protected String[] f26617c;

        /* renamed from: d, reason: collision with root package name */
        protected int[] f26618d;

        public ArrayEntityMap() {
            this.f26616b = 0;
            this.f26615a = 100;
            this.f26617c = new String[100];
            this.f26618d = new int[100];
        }

        protected void a(int i2) {
            if (i2 > this.f26617c.length) {
                int iMax = Math.max(i2, this.f26616b + this.f26615a);
                String[] strArr = new String[iMax];
                System.arraycopy(this.f26617c, 0, strArr, 0, this.f26616b);
                this.f26617c = strArr;
                int[] iArr = new int[iMax];
                System.arraycopy(this.f26618d, 0, iArr, 0, this.f26616b);
                this.f26618d = iArr;
            }
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public void add(String str, int i2) {
            a(this.f26616b + 1);
            String[] strArr = this.f26617c;
            int i3 = this.f26616b;
            strArr[i3] = str;
            this.f26618d[i3] = i2;
            this.f26616b = i3 + 1;
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public String name(int i2) {
            for (int i3 = 0; i3 < this.f26616b; i3++) {
                if (this.f26618d[i3] == i2) {
                    return this.f26617c[i3];
                }
            }
            return null;
        }

        @Override // org.apache.commons.lang.Entities.EntityMap
        public int value(String str) {
            for (int i2 = 0; i2 < this.f26616b; i2++) {
                if (this.f26617c[i2].equals(str)) {
                    return this.f26618d[i2];
                }
            }
            return -1;
        }

        public ArrayEntityMap(int i2) {
            this.f26616b = 0;
            this.f26615a = i2;
            this.f26617c = new String[i2];
            this.f26618d = new int[i2];
        }
    }

    public void escape(Writer writer, String str) throws IOException {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            String strEntityName = entityName(cCharAt);
            if (strEntityName != null) {
                writer.write(38);
                writer.write(strEntityName);
                writer.write(59);
            } else if (cCharAt > 127) {
                writer.write("&#");
                writer.write(Integer.toString(cCharAt, 10));
                writer.write(59);
            } else {
                writer.write(cCharAt);
            }
        }
    }

    public void unescape(Writer writer, String str) throws IOException, NumberFormatException {
        int iIndexOf = str.indexOf(38);
        if (iIndexOf < 0) {
            writer.write(str);
        } else {
            doUnescape(writer, str, iIndexOf);
        }
    }
}
