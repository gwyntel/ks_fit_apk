package com.squareup.okhttp.internal.framed;

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

/* loaded from: classes4.dex */
final class Hpack {
    private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
    private static final int PREFIX_4_BITS = 15;
    private static final int PREFIX_5_BITS = 31;
    private static final int PREFIX_6_BITS = 63;
    private static final int PREFIX_7_BITS = 127;
    private static final Header[] STATIC_HEADER_TABLE;

    static final class Reader {

        /* renamed from: b, reason: collision with root package name */
        int f19972b;
        private int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        private final BufferedSource source;
        private final List<Header> headerList = new ArrayList();

        /* renamed from: a, reason: collision with root package name */
        Header[] f19971a = new Header[8];

        /* renamed from: c, reason: collision with root package name */
        int f19973c = 0;

        /* renamed from: d, reason: collision with root package name */
        int f19974d = 0;

        Reader(int i2, Source source) {
            this.f19972b = r0.length - 1;
            this.headerTableSizeSetting = i2;
            this.maxDynamicTableByteCount = i2;
            this.source = Okio.buffer(source);
        }

        private void adjustDynamicTableByteCount() {
            int i2 = this.maxDynamicTableByteCount;
            int i3 = this.f19974d;
            if (i2 < i3) {
                if (i2 == 0) {
                    clearDynamicTable();
                } else {
                    evictToRecoverBytes(i3 - i2);
                }
            }
        }

        private void clearDynamicTable() {
            this.headerList.clear();
            Arrays.fill(this.f19971a, (Object) null);
            this.f19972b = this.f19971a.length - 1;
            this.f19973c = 0;
            this.f19974d = 0;
        }

        private int dynamicTableIndex(int i2) {
            return this.f19972b + 1 + i2;
        }

        private int evictToRecoverBytes(int i2) {
            int i3;
            int i4 = 0;
            if (i2 > 0) {
                int length = this.f19971a.length;
                while (true) {
                    length--;
                    i3 = this.f19972b;
                    if (length < i3 || i2 <= 0) {
                        break;
                    }
                    int i5 = this.f19971a[length].f19970a;
                    i2 -= i5;
                    this.f19974d -= i5;
                    this.f19973c--;
                    i4++;
                }
                Header[] headerArr = this.f19971a;
                System.arraycopy(headerArr, i3 + 1, headerArr, i3 + 1 + i4, this.f19973c);
                this.f19972b += i4;
            }
            return i4;
        }

        private ByteString getName(int i2) {
            return isStaticHeader(i2) ? Hpack.STATIC_HEADER_TABLE[i2].name : this.f19971a[dynamicTableIndex(i2 - Hpack.STATIC_HEADER_TABLE.length)].name;
        }

        private void insertIntoDynamicTable(int i2, Header header) {
            this.headerList.add(header);
            int i3 = header.f19970a;
            if (i2 != -1) {
                i3 -= this.f19971a[dynamicTableIndex(i2)].f19970a;
            }
            int i4 = this.maxDynamicTableByteCount;
            if (i3 > i4) {
                clearDynamicTable();
                return;
            }
            int iEvictToRecoverBytes = evictToRecoverBytes((this.f19974d + i3) - i4);
            if (i2 == -1) {
                int i5 = this.f19973c + 1;
                Header[] headerArr = this.f19971a;
                if (i5 > headerArr.length) {
                    Header[] headerArr2 = new Header[headerArr.length * 2];
                    System.arraycopy(headerArr, 0, headerArr2, headerArr.length, headerArr.length);
                    this.f19972b = this.f19971a.length - 1;
                    this.f19971a = headerArr2;
                }
                int i6 = this.f19972b;
                this.f19972b = i6 - 1;
                this.f19971a[i6] = header;
                this.f19973c++;
            } else {
                this.f19971a[i2 + dynamicTableIndex(i2) + iEvictToRecoverBytes] = header;
            }
            this.f19974d += i3;
        }

        private boolean isStaticHeader(int i2) {
            return i2 >= 0 && i2 <= Hpack.STATIC_HEADER_TABLE.length - 1;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 255;
        }

        private void readIndexedHeader(int i2) throws IOException {
            if (isStaticHeader(i2)) {
                this.headerList.add(Hpack.STATIC_HEADER_TABLE[i2]);
                return;
            }
            int iDynamicTableIndex = dynamicTableIndex(i2 - Hpack.STATIC_HEADER_TABLE.length);
            if (iDynamicTableIndex >= 0) {
                Header[] headerArr = this.f19971a;
                if (iDynamicTableIndex <= headerArr.length - 1) {
                    this.headerList.add(headerArr[iDynamicTableIndex]);
                    return;
                }
            }
            throw new IOException("Header index too large " + (i2 + 1));
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int i2) throws IOException {
            insertIntoDynamicTable(-1, new Header(getName(i2), b()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(b()), b()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int i2) throws IOException {
            this.headerList.add(new Header(getName(i2), b()));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            this.headerList.add(new Header(Hpack.checkLowercase(b()), b()));
        }

        void a(int i2) {
            this.headerTableSizeSetting = i2;
            this.maxDynamicTableByteCount = i2;
            adjustDynamicTableByteCount();
        }

        ByteString b() throws IOException {
            int i2 = readByte();
            boolean z2 = (i2 & 128) == 128;
            int iD = d(i2, 127);
            return z2 ? ByteString.of(Huffman.get().a(this.source.readByteArray(iD))) : this.source.readByteString(iD);
        }

        void c() throws IOException {
            while (!this.source.exhausted()) {
                byte b2 = this.source.readByte();
                int i2 = b2 & 255;
                if (i2 == 128) {
                    throw new IOException("index == 0");
                }
                if ((b2 & 128) == 128) {
                    readIndexedHeader(d(i2, 127) - 1);
                } else if (i2 == 64) {
                    readLiteralHeaderWithIncrementalIndexingNewName();
                } else if ((b2 & 64) == 64) {
                    readLiteralHeaderWithIncrementalIndexingIndexedName(d(i2, 63) - 1);
                } else if ((b2 & 32) == 32) {
                    int iD = d(i2, 31);
                    this.maxDynamicTableByteCount = iD;
                    if (iD < 0 || iD > this.headerTableSizeSetting) {
                        throw new IOException("Invalid dynamic table size update " + this.maxDynamicTableByteCount);
                    }
                    adjustDynamicTableByteCount();
                } else if (i2 == 16 || i2 == 0) {
                    readLiteralHeaderWithoutIndexingNewName();
                } else {
                    readLiteralHeaderWithoutIndexingIndexedName(d(i2, 15) - 1);
                }
            }
        }

        int d(int i2, int i3) throws IOException {
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
    }

    static final class Writer {
        private final Buffer out;

        Writer(Buffer buffer) {
            this.out = buffer;
        }

        void a(ByteString byteString) {
            c(byteString.size(), 127, 0);
            this.out.write(byteString);
        }

        void b(List list) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                ByteString asciiLowercase = ((Header) list.get(i2)).name.toAsciiLowercase();
                Integer num = (Integer) Hpack.NAME_TO_FIRST_INDEX.get(asciiLowercase);
                if (num != null) {
                    c(num.intValue() + 1, 15, 0);
                    a(((Header) list.get(i2)).value);
                } else {
                    this.out.writeByte(0);
                    a(asciiLowercase);
                    a(((Header) list.get(i2)).value);
                }
            }
        }

        void c(int i2, int i3, int i4) {
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
        STATIC_HEADER_TABLE = new Header[]{header, header2, header3, header4, header5, header6, header7, new Header(byteString4, "200"), new Header(byteString4, "204"), new Header(byteString4, "206"), new Header(byteString4, "304"), new Header(byteString4, "400"), new Header(byteString4, "404"), new Header(byteString4, SyncBoneInvoker.ERROR_SUB_CODE_EXCEPTION), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header(HttpConstant.CLOUDAPI_HTTP_HEADER_ACCEPT, ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header(Common.AUTHORIZATION, ""), new Header(Common.CACHE_CONTROL, ""), new Header("content-disposition", ""), new Header(Common.CONTENT_ENCODING, ""), new Header("content-language", ""), new Header(Common.CONTENT_LENGTH, ""), new Header("content-location", ""), new Header(Common.CONTENT_RANGE, ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header(Common.LAST_MODIFIED, ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header(RequestParameters.SUBRESOURCE_REFERER, ""), new Header(d.f9880w, ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header(HttpConstant.CLOUDAPI_HTTP_HEADER_USER_AGENT, ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        NAME_TO_FIRST_INDEX = nameToFirstIndex();
    }

    private Hpack() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteString checkLowercase(ByteString byteString) throws IOException {
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
        LinkedHashMap linkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
        int i2 = 0;
        while (true) {
            Header[] headerArr = STATIC_HEADER_TABLE;
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
