package okhttp3.internal.http2;

import com.alibaba.cloudapi.sdk.constant.HttpConstant;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.alipay.sdk.m.x.d;
import com.aliyun.iot.aep.sdk.bridge.invoker.SyncBoneInvoker;
import com.xiaomi.infra.galaxy.fds.Common;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

/* loaded from: classes5.dex */
final class Hpack {
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;

    /* renamed from: a, reason: collision with root package name */
    static final Header[] f26325a;

    /* renamed from: b, reason: collision with root package name */
    static final Map f26326b;

    static final class Reader {

        /* renamed from: a, reason: collision with root package name */
        Header[] f26327a;

        /* renamed from: b, reason: collision with root package name */
        int f26328b;

        /* renamed from: c, reason: collision with root package name */
        int f26329c;

        /* renamed from: d, reason: collision with root package name */
        int f26330d;
        private final List<Header> headerList;
        private final int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        private final BufferedSource source;

        Reader(int i2, Source source) {
            this(i2, i2, source);
        }

        private void adjustDynamicTableByteCount() {
            int i2 = this.maxDynamicTableByteCount;
            int i3 = this.f26330d;
            if (i2 < i3) {
                if (i2 == 0) {
                    clearDynamicTable();
                } else {
                    evictToRecoverBytes(i3 - i2);
                }
            }
        }

        private void clearDynamicTable() {
            Arrays.fill(this.f26327a, (Object) null);
            this.f26328b = this.f26327a.length - 1;
            this.f26329c = 0;
            this.f26330d = 0;
        }

        private int dynamicTableIndex(int i2) {
            return this.f26328b + 1 + i2;
        }

        private int evictToRecoverBytes(int i2) {
            int i3;
            int i4 = 0;
            if (i2 > 0) {
                int length = this.f26327a.length;
                while (true) {
                    length--;
                    i3 = this.f26328b;
                    if (length < i3 || i2 <= 0) {
                        break;
                    }
                    int i5 = this.f26327a[length].f26324a;
                    i2 -= i5;
                    this.f26330d -= i5;
                    this.f26329c--;
                    i4++;
                }
                Header[] headerArr = this.f26327a;
                System.arraycopy(headerArr, i3 + 1, headerArr, i3 + 1 + i4, this.f26329c);
                this.f26328b += i4;
            }
            return i4;
        }

        private ByteString getName(int i2) throws IOException {
            if (isStaticHeader(i2)) {
                return Hpack.f26325a[i2].name;
            }
            int iDynamicTableIndex = dynamicTableIndex(i2 - Hpack.f26325a.length);
            if (iDynamicTableIndex >= 0) {
                Header[] headerArr = this.f26327a;
                if (iDynamicTableIndex < headerArr.length) {
                    return headerArr[iDynamicTableIndex].name;
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }

        private void insertIntoDynamicTable(int i2, Header header) {
            this.headerList.add(header);
            int i3 = header.f26324a;
            if (i2 != -1) {
                i3 -= this.f26327a[dynamicTableIndex(i2)].f26324a;
            }
            int i4 = this.maxDynamicTableByteCount;
            if (i3 > i4) {
                clearDynamicTable();
                return;
            }
            int iEvictToRecoverBytes = evictToRecoverBytes((this.f26330d + i3) - i4);
            if (i2 == -1) {
                int i5 = this.f26329c + 1;
                Header[] headerArr = this.f26327a;
                if (i5 > headerArr.length) {
                    Header[] headerArr2 = new Header[headerArr.length * 2];
                    System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                    this.f26328b = this.f26327a.length - 1;
                    this.f26327a = headerArr2;
                }
                int i6 = this.f26328b;
                this.f26328b = i6 - 1;
                this.f26327a[i6] = header;
                this.f26329c++;
            } else {
                this.f26327a[i2 + dynamicTableIndex(i2) + iEvictToRecoverBytes] = header;
            }
            this.f26330d += i3;
        }

        private boolean isStaticHeader(int i2) {
            return i2 >= 0 && i2 <= Hpack.f26325a.length - 1;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int i2) throws IOException {
            if (isStaticHeader(i2)) {
                this.headerList.add(Hpack.f26325a[i2]);
                return;
            }
            int iDynamicTableIndex = dynamicTableIndex(i2 - Hpack.f26325a.length);
            if (iDynamicTableIndex >= 0) {
                Header[] headerArr = this.f26327a;
                if (iDynamicTableIndex < headerArr.length) {
                    this.headerList.add(headerArr[iDynamicTableIndex]);
                    return;
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i2) throws IOException {
            insertIntoDynamicTable(-1, new Header(getName(i2), a()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoDynamicTable(-1, new Header(Hpack.a(a()), a()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i2) throws IOException {
            this.headerList.add(new Header(getName(i2), a()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.headerList.add(new Header(Hpack.a(a()), a()));
        }

        ByteString a() throws IOException {
            int i2 = readByte();
            boolean z2 = (i2 & 128) == 128;
            int iC = c(i2, 127);
            return z2 ? ByteString.of(Huffman.get().a(this.source.readByteArray(iC))) : this.source.readByteString(iC);
        }

        void b() throws IOException {
            while (!this.source.exhausted()) {
                byte b2 = this.source.readByte();
                int i2 = b2 & 255;
                if (i2 == 128) {
                    throw new IOException("index == 0");
                }
                if ((b2 & 128) == 128) {
                    readIndexedHeader(c(i2, 127) - 1);
                } else if (i2 == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((b2 & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(c(i2, 63) - 1);
                } else if ((b2 & 32) == 32) {
                    int iC = c(i2, 31);
                    this.maxDynamicTableByteCount = iC;
                    if (iC < 0 || iC > this.headerTableSizeSetting) {
                        throw new IOException("Invalid dynamic table size update " + this.maxDynamicTableByteCount);
                    }
                    adjustDynamicTableByteCount();
                } else if (i2 == 16 || i2 == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(c(i2, 15) - 1);
                }
            }
        }

        int c(int i2, int i3) throws IOException {
            int i4 = i2 & i3;
            if (i4 < i3) {
                return i4;
            }
            int i5 = 0;
            while (true) {
                int i6 = readByte();
                if ((i6 & 128) == 0) {
                    return i3 + (i6 << i5);
                }
                i3 += (i6 & 127) << i5;
                i5 += 7;
            }
        }

        public List<Header> getAndResetHeaderList() {
            ArrayList arrayList = new ArrayList(this.headerList);
            this.headerList.clear();
            return arrayList;
        }

        Reader(int i2, int i3, Source source) {
            this.headerList = new ArrayList();
            this.f26327a = new Header[8];
            this.f26328b = r0.length - 1;
            this.f26329c = 0;
            this.f26330d = 0;
            this.headerTableSizeSetting = i2;
            this.maxDynamicTableByteCount = i3;
            this.source = Okio.buffer(source);
        }
    }

    static final class Writer {
        private static final int SETTINGS_HEADER_TABLE_SIZE = 4096;
        private static final int SETTINGS_HEADER_TABLE_SIZE_LIMIT = 16384;

        /* renamed from: a, reason: collision with root package name */
        int f26331a;

        /* renamed from: b, reason: collision with root package name */
        int f26332b;

        /* renamed from: c, reason: collision with root package name */
        Header[] f26333c;

        /* renamed from: d, reason: collision with root package name */
        int f26334d;

        /* renamed from: e, reason: collision with root package name */
        int f26335e;
        private boolean emitDynamicTableSizeUpdate;

        /* renamed from: f, reason: collision with root package name */
        int f26336f;
        private final Buffer out;
        private int smallestHeaderTableSizeSetting;
        private final boolean useCompression;

        Writer(Buffer buffer) {
            this(4096, true, buffer);
        }

        private void adjustDynamicTableByteCount() {
            int i2 = this.f26332b;
            int i3 = this.f26336f;
            if (i2 < i3) {
                if (i2 == 0) {
                    clearDynamicTable();
                } else {
                    evictToRecoverBytes(i3 - i2);
                }
            }
        }

        private void clearDynamicTable() {
            Arrays.fill(this.f26333c, (Object) null);
            this.f26334d = this.f26333c.length - 1;
            this.f26335e = 0;
            this.f26336f = 0;
        }

        private int evictToRecoverBytes(int i2) {
            int i3;
            int i4 = 0;
            if (i2 > 0) {
                int length = this.f26333c.length;
                while (true) {
                    length--;
                    i3 = this.f26334d;
                    if (length < i3 || i2 <= 0) {
                        break;
                    }
                    int i5 = this.f26333c[length].f26324a;
                    i2 -= i5;
                    this.f26336f -= i5;
                    this.f26335e--;
                    i4++;
                }
                Header[] headerArr = this.f26333c;
                System.arraycopy(headerArr, i3 + 1, headerArr, i3 + 1 + i4, this.f26335e);
                Header[] headerArr2 = this.f26333c;
                int i6 = this.f26334d;
                Arrays.fill(headerArr2, i6 + 1, i6 + 1 + i4, (Object) null);
                this.f26334d += i4;
            }
            return i4;
        }

        private void insertIntoDynamicTable(Header header) {
            int i2 = header.f26324a;
            int i3 = this.f26332b;
            if (i2 > i3) {
                clearDynamicTable();
                return;
            }
            evictToRecoverBytes((this.f26336f + i2) - i3);
            int i4 = this.f26335e + 1;
            Header[] headerArr = this.f26333c;
            if (i4 > headerArr.length) {
                Header[] headerArr2 = new Header[headerArr.length * 2];
                System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                this.f26334d = this.f26333c.length - 1;
                this.f26333c = headerArr2;
            }
            int i5 = this.f26334d;
            this.f26334d = i5 - 1;
            this.f26333c[i5] = header;
            this.f26335e++;
            this.f26336f += i2;
        }

        void a(int i2) {
            this.f26331a = i2;
            int iMin = Math.min(i2, 16384);
            int i3 = this.f26332b;
            if (i3 == iMin) {
                return;
            }
            if (iMin < i3) {
                this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, iMin);
            }
            this.emitDynamicTableSizeUpdate = true;
            this.f26332b = iMin;
            adjustDynamicTableByteCount();
        }

        void b(ByteString byteString) throws IOException {
            if (!this.useCompression || Huffman.get().c(byteString) >= byteString.size()) {
                d(byteString.size(), 127, 0);
                this.out.write(byteString);
                return;
            }
            Buffer buffer = new Buffer();
            Huffman.get().b(byteString, buffer);
            ByteString byteString2 = buffer.readByteString();
            d(byteString2.size(), 127, 128);
            this.out.write(byteString2);
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0069  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        void c(java.util.List r14) throws java.io.IOException {
            /*
                Method dump skipped, instructions count: 236
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Hpack.Writer.c(java.util.List):void");
        }

        void d(int i2, int i3, int i4) {
            if (i2 < i3) {
                this.out.writeByte(i2 | i4);
                return;
            }
            this.out.writeByte(i4 | i3);
            int i5 = i2 - i3;
            while (i5 >= 128) {
                this.out.writeByte(128 | (i5 & 127));
                i5 >>>= 7;
            }
            this.out.writeByte(i5);
        }

        Writer(int i2, boolean z2, Buffer buffer) {
            this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
            this.f26333c = new Header[8];
            this.f26334d = r0.length - 1;
            this.f26335e = 0;
            this.f26336f = 0;
            this.f26331a = i2;
            this.f26332b = i2;
            this.useCompression = z2;
            this.out = buffer;
        }
    }

    static {
        Header header = new Header(Header.TARGET_AUTHORITY, "");
        ByteString byteString = Header.TARGET_METHOD;
        Header header2 = new Header(byteString, "GET");
        Header header3 = new Header(byteString, "POST");
        ByteString byteString2 = Header.TARGET_PATH;
        Header header4 = new Header(byteString2, "/");
        Header header5 = new Header(byteString2, "/index.html");
        ByteString byteString3 = Header.TARGET_SCHEME;
        Header header6 = new Header(byteString3, "http");
        Header header7 = new Header(byteString3, "https");
        ByteString byteString4 = Header.RESPONSE_STATUS;
        f26325a = new Header[]{header, header2, header3, header4, header5, header6, header7, new Header(byteString4, "200"), new Header(byteString4, "204"), new Header(byteString4, "206"), new Header(byteString4, "304"), new Header(byteString4, "400"), new Header(byteString4, "404"), new Header(byteString4, SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT, ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header(Common.AUTHORIZATION, ""), new Header(Common.CACHE_CONTROL, ""), new Header("content-disposition", ""), new Header(Common.CONTENT_ENCODING, ""), new Header("content-language", ""), new Header(Common.CONTENT_LENGTH, ""), new Header("content-location", ""), new Header(Common.CONTENT_RANGE, ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header(Common.LAST_MODIFIED, ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header(RequestParameters.SUBRESOURCE_REFERER, ""), new Header(d.f9880w, ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header(HttpConstant.CLOUDAPI_HTTP_HEADER_USER_AGENT, ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        f26326b = nameToFirstIndex();
    }

    private Hpack() {
    }

    static ByteString a(ByteString byteString) throws IOException {
        int size = byteString.size();
        for (int i2 = 0; i2 < size; i2++) {
            byte b2 = byteString.getByte(i2);
            if (b2 >= 65 && b2 <= 90) {
                throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
            }
        }
        return byteString;
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap linkedHashMap = new LinkedHashMap(f26325a.length);
        int i2 = 0;
        while (true) {
            Header[] headerArr = f26325a;
            if (i2 >= headerArr.length) {
                return Collections.unmodifiableMap(linkedHashMap);
            }
            if (!linkedHashMap.containsKey(headerArr[i2].name)) {
                linkedHashMap.put(headerArr[i2].name, Integer.valueOf(i2));
            }
            i2++;
        }
    }
}
