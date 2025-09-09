package org.apache.commons.io.input;

import com.alipay.sdk.m.u.i;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.ByteOrderMark;

/* loaded from: classes5.dex */
public class XmlStreamReader extends Reader {
    private static final int BUFFER_SIZE = 4096;
    private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
    private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
    private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
    private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
    private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_16 = "UTF-16";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String UTF_16LE = "UTF-16LE";
    private static final String UTF_32 = "UTF-32";
    private static final String UTF_8 = "UTF-8";
    private final String defaultEncoding;
    private final String encoding;
    private final Reader reader;
    private static final ByteOrderMark[] BOMS = {ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE};
    private static final String UTF_32BE = "UTF-32BE";
    private static final String UTF_32LE = "UTF-32LE";
    private static final String EBCDIC = "CP1047";
    private static final ByteOrderMark[] XML_GUESS_BYTES = {new ByteOrderMark("UTF-8", 60, 63, 120, 109), new ByteOrderMark("UTF-16BE", 0, 60, 0, 63), new ByteOrderMark("UTF-16LE", 60, 0, 63, 0), new ByteOrderMark(UTF_32BE, 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109), new ByteOrderMark(UTF_32LE, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0), new ByteOrderMark(EBCDIC, 76, 111, 167, 148)};
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
    public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);

    public XmlStreamReader(File file) throws IOException {
        this(new FileInputStream(file));
    }

    static String c(String str) {
        int iIndexOf;
        if (str == null || (iIndexOf = str.indexOf(i.f9802b)) <= -1) {
            return null;
        }
        Matcher matcher = CHARSET_PATTERN.matcher(str.substring(iIndexOf + 1));
        String strGroup = matcher.find() ? matcher.group(1) : null;
        if (strGroup != null) {
            return strGroup.toUpperCase(Locale.US);
        }
        return null;
    }

    static String d(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(i.f9802b);
        if (iIndexOf >= 0) {
            str = str.substring(0, iIndexOf);
        }
        return str.trim();
    }

    private String doHttpStream(BOMInputStream bOMInputStream, BOMInputStream bOMInputStream2, String str, boolean z2) throws IOException {
        String bOMCharsetName = bOMInputStream.getBOMCharsetName();
        String bOMCharsetName2 = bOMInputStream2.getBOMCharsetName();
        try {
            return a(str, bOMCharsetName, bOMCharsetName2, getXmlProlog(bOMInputStream2, bOMCharsetName2), z2);
        } catch (XmlStreamReaderException e2) {
            if (z2) {
                return doLenientDetection(str, e2);
            }
            throw e2;
        }
    }

    private String doLenientDetection(String str, XmlStreamReaderException xmlStreamReaderException) throws IOException {
        if (str != null && str.startsWith("text/html")) {
            try {
                return a("text/xml" + str.substring(9), xmlStreamReaderException.getBomEncoding(), xmlStreamReaderException.getXmlGuessEncoding(), xmlStreamReaderException.getXmlEncoding(), true);
            } catch (XmlStreamReaderException e2) {
                xmlStreamReaderException = e2;
            }
        }
        String xmlEncoding = xmlStreamReaderException.getXmlEncoding();
        if (xmlEncoding == null) {
            xmlEncoding = xmlStreamReaderException.getContentTypeEncoding();
        }
        if (xmlEncoding != null) {
            return xmlEncoding;
        }
        String str2 = this.defaultEncoding;
        return str2 == null ? "UTF-8" : str2;
    }

    private String doRawStream(BOMInputStream bOMInputStream, BOMInputStream bOMInputStream2, boolean z2) throws IOException {
        String bOMCharsetName = bOMInputStream.getBOMCharsetName();
        String bOMCharsetName2 = bOMInputStream2.getBOMCharsetName();
        try {
            return b(bOMCharsetName, bOMCharsetName2, getXmlProlog(bOMInputStream2, bOMCharsetName2));
        } catch (XmlStreamReaderException e2) {
            if (z2) {
                return doLenientDetection(null, e2);
            }
            throw e2;
        }
    }

    static boolean e(String str) {
        return str != null && (str.equals("application/xml") || str.equals("application/xml-dtd") || str.equals("application/xml-external-parsed-entity") || (str.startsWith("application/") && str.endsWith("+xml")));
    }

    static boolean f(String str) {
        return str != null && (str.equals("text/xml") || str.equals("text/xml-external-parsed-entity") || (str.startsWith("text/") && str.endsWith("+xml")));
    }

    private static String getXmlProlog(InputStream inputStream, String str) throws IOException {
        if (str != null) {
            byte[] bArr = new byte[4096];
            inputStream.mark(4096);
            int i2 = inputStream.read(bArr, 0, 4096);
            String str2 = "";
            int i3 = 4096;
            int i4 = 0;
            int iIndexOf = -1;
            while (i2 != -1 && iIndexOf == -1 && i4 < 4096) {
                i4 += i2;
                i3 -= i2;
                i2 = inputStream.read(bArr, i4, i3);
                str2 = new String(bArr, 0, i4, str);
                iIndexOf = str2.indexOf(62);
            }
            if (iIndexOf == -1) {
                if (i2 == -1) {
                    throw new IOException("Unexpected end of XML stream");
                }
                throw new IOException("XML prolog or ROOT element not found on first " + i4 + " bytes");
            }
            if (i4 > 0) {
                inputStream.reset();
                BufferedReader bufferedReader = new BufferedReader(new StringReader(str2.substring(0, iIndexOf + 1)));
                StringBuffer stringBuffer = new StringBuffer();
                for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                    stringBuffer.append(line);
                }
                Matcher matcher = ENCODING_PATTERN.matcher(stringBuffer);
                if (matcher.find()) {
                    String upperCase = matcher.group(1).toUpperCase();
                    return upperCase.substring(1, upperCase.length() - 1);
                }
            }
        }
        return null;
    }

    String a(String str, String str2, String str3, String str4, boolean z2) throws XmlStreamReaderException {
        if (z2 && str4 != null) {
            return str4;
        }
        String strD = d(str);
        String strC = c(str);
        boolean zE = e(strD);
        boolean zF = f(strD);
        if (!zE && !zF) {
            throw new XmlStreamReaderException(MessageFormat.format(HTTP_EX_3, strD, strC, str2, str3, str4), strD, strC, str2, str3, str4);
        }
        if (strC == null) {
            if (zE) {
                return b(str2, str3, str4);
            }
            String str5 = this.defaultEncoding;
            return str5 == null ? "US-ASCII" : str5;
        }
        if (strC.equals("UTF-16BE") || strC.equals("UTF-16LE")) {
            if (str2 == null) {
                return strC;
            }
            throw new XmlStreamReaderException(MessageFormat.format(HTTP_EX_1, strD, strC, str2, str3, str4), strD, strC, str2, str3, str4);
        }
        if (strC.equals("UTF-16")) {
            if (str2 == null || !str2.startsWith("UTF-16")) {
                throw new XmlStreamReaderException(MessageFormat.format(HTTP_EX_2, strD, strC, str2, str3, str4), strD, strC, str2, str3, str4);
            }
            return str2;
        }
        if (strC.equals(UTF_32BE) || strC.equals(UTF_32LE)) {
            if (str2 == null) {
                return strC;
            }
            throw new XmlStreamReaderException(MessageFormat.format(HTTP_EX_1, strD, strC, str2, str3, str4), strD, strC, str2, str3, str4);
        }
        if (!strC.equals(UTF_32)) {
            return strC;
        }
        if (str2 == null || !str2.startsWith(UTF_32)) {
            throw new XmlStreamReaderException(MessageFormat.format(HTTP_EX_2, strD, strC, str2, str3, str4), strD, strC, str2, str3, str4);
        }
        return str2;
    }

    String b(String str, String str2, String str3) throws XmlStreamReaderException {
        if (str == null) {
            if (str2 != null && str3 != null) {
                return (str3.equals("UTF-16") && (str2.equals("UTF-16BE") || str2.equals("UTF-16LE"))) ? str2 : str3;
            }
            String str4 = this.defaultEncoding;
            return str4 == null ? "UTF-8" : str4;
        }
        if (str.equals("UTF-8")) {
            if (str2 != null && !str2.equals("UTF-8")) {
                throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
            }
            if (str3 == null || str3.equals("UTF-8")) {
                return str;
            }
            throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
        }
        if (str.equals("UTF-16BE") || str.equals("UTF-16LE")) {
            if (str2 != null && !str2.equals(str)) {
                throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
            }
            if (str3 == null || str3.equals("UTF-16") || str3.equals(str)) {
                return str;
            }
            throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
        }
        if (!str.equals(UTF_32BE) && !str.equals(UTF_32LE)) {
            throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_2, str, str2, str3), str, str2, str3);
        }
        if (str2 != null && !str2.equals(str)) {
            throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
        }
        if (str3 == null || str3.equals(UTF_32) || str3.equals(str)) {
            return str;
        }
        throw new XmlStreamReaderException(MessageFormat.format(RAW_EX_1, str, str2, str3), str, str2, str3);
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.reader.close();
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i2, int i3) throws IOException {
        return this.reader.read(cArr, i2, i3);
    }

    public XmlStreamReader(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public XmlStreamReader(InputStream inputStream, boolean z2) throws IOException {
        this(inputStream, z2, (String) null);
    }

    public XmlStreamReader(InputStream inputStream, boolean z2, String str) throws IOException {
        this.defaultEncoding = str;
        BOMInputStream bOMInputStream = new BOMInputStream(new BufferedInputStream(inputStream, 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream(bOMInputStream, true, XML_GUESS_BYTES);
        String strDoRawStream = doRawStream(bOMInputStream, bOMInputStream2, z2);
        this.encoding = strDoRawStream;
        this.reader = new InputStreamReader(bOMInputStream2, strDoRawStream);
    }

    public XmlStreamReader(URL url) throws IOException {
        this(url.openConnection(), (String) null);
    }

    public XmlStreamReader(URLConnection uRLConnection, String str) throws IOException {
        this.defaultEncoding = str;
        String contentType = uRLConnection.getContentType();
        BOMInputStream bOMInputStream = new BOMInputStream(new BufferedInputStream(uRLConnection.getInputStream(), 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream(bOMInputStream, true, XML_GUESS_BYTES);
        if (!(uRLConnection instanceof HttpURLConnection) && contentType == null) {
            this.encoding = doRawStream(bOMInputStream, bOMInputStream2, true);
        } else {
            this.encoding = doHttpStream(bOMInputStream, bOMInputStream2, contentType, true);
        }
        this.reader = new InputStreamReader(bOMInputStream2, this.encoding);
    }

    public XmlStreamReader(InputStream inputStream, String str) throws IOException {
        this(inputStream, str, true);
    }

    public XmlStreamReader(InputStream inputStream, String str, boolean z2, String str2) throws IOException {
        this.defaultEncoding = str2;
        BOMInputStream bOMInputStream = new BOMInputStream(new BufferedInputStream(inputStream, 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream(bOMInputStream, true, XML_GUESS_BYTES);
        String strDoHttpStream = doHttpStream(bOMInputStream, bOMInputStream2, str, z2);
        this.encoding = strDoHttpStream;
        this.reader = new InputStreamReader(bOMInputStream2, strDoHttpStream);
    }

    public XmlStreamReader(InputStream inputStream, String str, boolean z2) throws IOException {
        this(inputStream, str, z2, null);
    }
}
