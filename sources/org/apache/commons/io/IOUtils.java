package org.apache.commons.io;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.StringBuilderWriter;

/* loaded from: classes5.dex */
public class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final int EOF = -1;
    public static final String LINE_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static byte[] SKIP_BYTE_BUFFER;
    private static char[] SKIP_CHAR_BUFFER;

    static {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter(4);
        try {
            PrintWriter printWriter = new PrintWriter(stringBuilderWriter);
            try {
                printWriter.println();
                LINE_SEPARATOR = stringBuilderWriter.toString();
                printWriter.close();
                stringBuilderWriter.close();
            } finally {
            }
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    stringBuilderWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static BufferedReader buffer(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static void close(URLConnection uRLConnection) {
        if (uRLConnection instanceof HttpURLConnection) {
            ((HttpURLConnection) uRLConnection).disconnect();
        }
    }

    @Deprecated
    public static void closeQuietly(Reader reader) throws IOException {
        closeQuietly((Closeable) reader);
    }

    public static boolean contentEquals(InputStream inputStream, InputStream inputStream2) throws IOException {
        if (inputStream == inputStream2) {
            return true;
        }
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(inputStream2 instanceof BufferedInputStream)) {
            inputStream2 = new BufferedInputStream(inputStream2);
        }
        for (int i2 = inputStream.read(); -1 != i2; i2 = inputStream.read()) {
            if (i2 != inputStream2.read()) {
                return false;
            }
        }
        return inputStream2.read() == -1;
    }

    public static boolean contentEqualsIgnoreEOL(Reader reader, Reader reader2) throws IOException {
        if (reader == reader2) {
            return true;
        }
        BufferedReader bufferedReader = toBufferedReader(reader);
        BufferedReader bufferedReader2 = toBufferedReader(reader2);
        String line = bufferedReader.readLine();
        String line2 = bufferedReader2.readLine();
        while (line != null && line2 != null && line.equals(line2)) {
            line = bufferedReader.readLine();
            line2 = bufferedReader2.readLine();
        }
        return line == null ? line2 == null : line.equals(line2);
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        long jCopyLarge = copyLarge(inputStream, outputStream);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream) throws IOException {
        return copy(inputStream, outputStream, 4096);
    }

    public static LineIterator lineIterator(Reader reader) {
        return new LineIterator(reader);
    }

    public static int read(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i3);
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = reader.read(cArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static void readFully(Reader reader, char[] cArr, int i2, int i3) throws IOException {
        int i4 = read(reader, cArr, i2, i3);
        if (i4 == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + i4);
    }

    @Deprecated
    public static List<String> readLines(InputStream inputStream) throws IOException {
        return readLines(inputStream, Charset.defaultCharset());
    }

    public static byte[] resourceToByteArray(String str) throws IOException {
        return resourceToByteArray(str, null);
    }

    public static String resourceToString(String str, Charset charset) throws IOException {
        return resourceToString(str, charset, null);
    }

    public static URL resourceToURL(String str) throws IOException {
        return resourceToURL(str, null);
    }

    public static long skip(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
        }
        if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[2048];
        }
        long j3 = j2;
        while (j3 > 0) {
            long j4 = inputStream.read(SKIP_BYTE_BUFFER, 0, (int) Math.min(j3, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH));
            if (j4 < 0) {
                break;
            }
            j3 -= j4;
        }
        return j2 - j3;
    }

    public static void skipFully(InputStream inputStream, long j2) throws IOException {
        if (j2 < 0) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + j2);
        }
        long jSkip = skip(inputStream, j2);
        if (jSkip == j2) {
            return;
        }
        throw new EOFException("Bytes to skip: " + j2 + " actual: " + jSkip);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            copy(inputStream, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    @Deprecated
    public static char[] toCharArray(InputStream inputStream) throws IOException {
        return toCharArray(inputStream, Charset.defaultCharset());
    }

    @Deprecated
    public static InputStream toInputStream(CharSequence charSequence) {
        return toInputStream(charSequence, Charset.defaultCharset());
    }

    @Deprecated
    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, Charset.defaultCharset());
    }

    public static void write(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            outputStream.write(bArr);
        }
    }

    public static void writeChunked(byte[] bArr, OutputStream outputStream) throws IOException {
        if (bArr != null) {
            int length = bArr.length;
            int i2 = 0;
            while (length > 0) {
                int iMin = Math.min(length, 4096);
                outputStream.write(bArr, i2, iMin);
                length -= iMin;
                i2 += iMin;
            }
        }
    }

    @Deprecated
    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream) throws IOException {
        writeLines(collection, str, outputStream, Charset.defaultCharset());
    }

    public static BufferedReader buffer(Reader reader, int i2) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i2);
    }

    @Deprecated
    public static void closeQuietly(Writer writer) throws IOException {
        closeQuietly((Closeable) writer);
    }

    public static long copy(InputStream inputStream, OutputStream outputStream, int i2) throws IOException {
        return copyLarge(inputStream, outputStream, new byte[i2]);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = inputStream.read(bArr);
            if (-1 == i2) {
                return j2;
            }
            outputStream.write(bArr, 0, i2);
            j2 += i2;
        }
    }

    public static LineIterator lineIterator(InputStream inputStream, Charset charset) throws IOException {
        return new LineIterator(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static List<String> readLines(InputStream inputStream, Charset charset) throws IOException {
        return readLines(new InputStreamReader(inputStream, Charsets.toCharset(charset)));
    }

    public static byte[] resourceToByteArray(String str, ClassLoader classLoader) throws IOException {
        return toByteArray(resourceToURL(str, classLoader));
    }

    public static String resourceToString(String str, Charset charset, ClassLoader classLoader) throws IOException {
        return toString(resourceToURL(str, classLoader), charset);
    }

    public static URL resourceToURL(String str, ClassLoader classLoader) throws IOException {
        URL resource = classLoader == null ? IOUtils.class.getResource(str) : classLoader.getResource(str);
        if (resource != null) {
            return resource;
        }
        throw new IOException("Resource not found: " + str);
    }

    public static InputStream toBufferedInputStream(InputStream inputStream, int i2) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(inputStream, i2);
    }

    public static BufferedReader toBufferedReader(Reader reader, int i2) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i2);
    }

    public static char[] toCharArray(InputStream inputStream, Charset charset) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(inputStream, charArrayWriter, charset);
        return charArrayWriter.toCharArray();
    }

    public static InputStream toInputStream(CharSequence charSequence, Charset charset) {
        return toInputStream(charSequence.toString(), charset);
    }

    public static String toString(InputStream inputStream, Charset charset) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        try {
            copy(inputStream, stringBuilderWriter, charset);
            String string = stringBuilderWriter.toString();
            stringBuilderWriter.close();
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    stringBuilderWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    @Deprecated
    public static void write(byte[] bArr, Writer writer) throws IOException {
        write(bArr, writer, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, Charset charset) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = LINE_SEPARATOR;
        }
        Charset charset2 = Charsets.toCharset(charset);
        for (Object obj : collection) {
            if (obj != null) {
                outputStream.write(obj.toString().getBytes(charset2));
            }
            outputStream.write(str.getBytes(charset2));
        }
    }

    public static BufferedWriter buffer(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    @Deprecated
    public static void closeQuietly(InputStream inputStream) throws IOException {
        closeQuietly((Closeable) inputStream);
    }

    @Deprecated
    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        copy(inputStream, writer, Charset.defaultCharset());
    }

    public static LineIterator lineIterator(InputStream inputStream, String str) throws IOException {
        return lineIterator(inputStream, Charsets.toCharset(str));
    }

    public static int read(Reader reader, char[] cArr) throws IOException {
        return read(reader, cArr, 0, cArr.length);
    }

    public static void readFully(Reader reader, char[] cArr) throws IOException {
        readFully(reader, cArr, 0, cArr.length);
    }

    public static InputStream toInputStream(CharSequence charSequence, String str) throws IOException {
        return toInputStream(charSequence, Charsets.toCharset(str));
    }

    public static void write(byte[] bArr, Writer writer, Charset charset) throws IOException {
        if (bArr != null) {
            writer.write(new String(bArr, Charsets.toCharset(charset)));
        }
    }

    public static BufferedWriter buffer(Writer writer, int i2) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i2);
    }

    @Deprecated
    public static void closeQuietly(OutputStream outputStream) throws IOException {
        closeQuietly((Closeable) outputStream);
    }

    public static void copy(InputStream inputStream, Writer writer, Charset charset) throws IOException {
        copy(new InputStreamReader(inputStream, Charsets.toCharset(charset)), writer);
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j2, long j3) throws IOException {
        return copyLarge(inputStream, outputStream, j2, j3, new byte[4096]);
    }

    public static int read(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        if (i3 < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + i3);
        }
        int i4 = i3;
        while (i4 > 0) {
            int i5 = inputStream.read(bArr, (i3 - i4) + i2, i4);
            if (-1 == i5) {
                break;
            }
            i4 -= i5;
        }
        return i3 - i4;
    }

    public static void readFully(InputStream inputStream, byte[] bArr, int i2, int i3) throws IOException {
        int i4 = read(inputStream, bArr, i2, i3);
        if (i4 == i3) {
            return;
        }
        throw new EOFException("Length to read: " + i3 + " actual: " + i4);
    }

    public static List<String> readLines(InputStream inputStream, String str) throws IOException {
        return readLines(inputStream, Charsets.toCharset(str));
    }

    public static void skipFully(ReadableByteChannel readableByteChannel, long j2) throws IOException {
        if (j2 >= 0) {
            long jSkip = skip(readableByteChannel, j2);
            if (jSkip == j2) {
                return;
            }
            throw new EOFException("Bytes to skip: " + j2 + " actual: " + jSkip);
        }
        throw new IllegalArgumentException("Bytes to skip must not be negative: " + j2);
    }

    @Deprecated
    public static InputStream toInputStream(String str) {
        return toInputStream(str, Charset.defaultCharset());
    }

    public static void write(byte[] bArr, Writer writer, String str) throws IOException {
        write(bArr, writer, Charsets.toCharset(str));
    }

    public static void writeChunked(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            int length = cArr.length;
            int i2 = 0;
            while (length > 0) {
                int iMin = Math.min(length, 4096);
                writer.write(cArr, i2, iMin);
                length -= iMin;
                i2 += iMin;
            }
        }
    }

    public static BufferedOutputStream buffer(OutputStream outputStream) {
        outputStream.getClass();
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    @Deprecated
    public static void closeQuietly(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static long copyLarge(InputStream inputStream, OutputStream outputStream, long j2, long j3, byte[] bArr) throws IOException {
        long j4 = 0;
        if (j2 > 0) {
            skipFully(inputStream, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = bArr.length;
        int iMin = (j3 <= 0 || j3 >= ((long) length)) ? length : (int) j3;
        while (iMin > 0) {
            int i2 = inputStream.read(bArr, 0, iMin);
            if (-1 == i2) {
                break;
            }
            outputStream.write(bArr, 0, i2);
            j4 += i2;
            if (j3 > 0) {
                iMin = (int) Math.min(j3 - j4, length);
            }
        }
        return j4;
    }

    public static List<String> readLines(Reader reader) throws IOException {
        BufferedReader bufferedReader = toBufferedReader(reader);
        ArrayList arrayList = new ArrayList();
        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
            arrayList.add(line);
        }
        return arrayList;
    }

    public static long skip(ReadableByteChannel readableByteChannel, long j2) throws IOException {
        if (j2 >= 0) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) Math.min(j2, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH));
            long j3 = j2;
            while (j3 > 0) {
                byteBufferAllocate.position(0);
                byteBufferAllocate.limit((int) Math.min(j3, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH));
                int i2 = readableByteChannel.read(byteBufferAllocate);
                if (i2 == -1) {
                    break;
                }
                j3 -= i2;
            }
            return j2 - j3;
        }
        throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
    }

    public static char[] toCharArray(InputStream inputStream, String str) throws IOException {
        return toCharArray(inputStream, Charsets.toCharset(str));
    }

    public static InputStream toInputStream(String str, Charset charset) {
        return new ByteArrayInputStream(str.getBytes(Charsets.toCharset(charset)));
    }

    public static void write(char[] cArr, Writer writer) throws IOException {
        if (cArr != null) {
            writer.write(cArr);
        }
    }

    @Deprecated
    public static void closeQuietly(Closeable... closeableArr) throws IOException {
        if (closeableArr == null) {
            return;
        }
        for (Closeable closeable : closeableArr) {
            closeQuietly(closeable);
        }
    }

    public static void copy(InputStream inputStream, Writer writer, String str) throws IOException {
        copy(inputStream, writer, Charsets.toCharset(str));
    }

    public static int read(InputStream inputStream, byte[] bArr) throws IOException {
        return read(inputStream, bArr, 0, bArr.length);
    }

    public static void readFully(InputStream inputStream, byte[] bArr) throws IOException {
        readFully(inputStream, bArr, 0, bArr.length);
    }

    public static char[] toCharArray(Reader reader) throws IOException {
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        copy(reader, charArrayWriter);
        return charArrayWriter.toCharArray();
    }

    public static InputStream toInputStream(String str, String str2) throws IOException {
        return new ByteArrayInputStream(str.getBytes(Charsets.toCharset(str2)));
    }

    @Deprecated
    public static void write(char[] cArr, OutputStream outputStream) throws IOException {
        write(cArr, outputStream, Charset.defaultCharset());
    }

    public static BufferedOutputStream buffer(OutputStream outputStream, int i2) {
        outputStream.getClass();
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, i2);
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        long jCopyLarge = copyLarge(reader, writer);
        if (jCopyLarge > 2147483647L) {
            return -1;
        }
        return (int) jCopyLarge;
    }

    public static int read(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        while (byteBuffer.remaining() > 0 && -1 != readableByteChannel.read(byteBuffer)) {
        }
        return iRemaining - byteBuffer.remaining();
    }

    public static byte[] readFully(InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        readFully(inputStream, bArr, 0, i2);
        return bArr;
    }

    public static void skipFully(Reader reader, long j2) throws IOException {
        long jSkip = skip(reader, j2);
        if (jSkip == j2) {
            return;
        }
        throw new EOFException("Chars to skip: " + j2 + " actual: " + jSkip);
    }

    public static byte[] toByteArray(InputStream inputStream, long j2) throws IOException {
        if (j2 <= 2147483647L) {
            return toByteArray(inputStream, (int) j2);
        }
        throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j2);
    }

    public static void write(char[] cArr, OutputStream outputStream, Charset charset) throws IOException {
        if (cArr != null) {
            outputStream.write(new String(cArr).getBytes(Charsets.toCharset(charset)));
        }
    }

    public static void writeLines(Collection<?> collection, String str, OutputStream outputStream, String str2) throws IOException {
        writeLines(collection, str, outputStream, Charsets.toCharset(str2));
    }

    @Deprecated
    public static void closeQuietly(Socket socket) throws IOException {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
        }
    }

    @Deprecated
    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        copy(reader, outputStream, Charset.defaultCharset());
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        return toString(inputStream, Charsets.toCharset(str));
    }

    public static void write(char[] cArr, OutputStream outputStream, String str) throws IOException {
        write(cArr, outputStream, Charsets.toCharset(str));
    }

    public static void writeLines(Collection<?> collection, String str, Writer writer) throws IOException {
        if (collection == null) {
            return;
        }
        if (str == null) {
            str = LINE_SEPARATOR;
        }
        for (Object obj : collection) {
            if (obj != null) {
                writer.write(obj.toString());
            }
            writer.write(str);
        }
    }

    public static BufferedInputStream buffer(InputStream inputStream) {
        inputStream.getClass();
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    @Deprecated
    public static void closeQuietly(Selector selector) throws IOException {
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException unused) {
            }
        }
    }

    public static boolean contentEquals(Reader reader, Reader reader2) throws IOException {
        if (reader == reader2) {
            return true;
        }
        BufferedReader bufferedReader = toBufferedReader(reader);
        BufferedReader bufferedReader2 = toBufferedReader(reader2);
        for (int i2 = bufferedReader.read(); -1 != i2; i2 = bufferedReader.read()) {
            if (i2 != bufferedReader2.read()) {
                return false;
            }
        }
        return bufferedReader2.read() == -1;
    }

    public static void copy(Reader reader, OutputStream outputStream, Charset charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charsets.toCharset(charset));
        copy(reader, outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void readFully(ReadableByteChannel readableByteChannel, ByteBuffer byteBuffer) throws IOException {
        int iRemaining = byteBuffer.remaining();
        int i2 = read(readableByteChannel, byteBuffer);
        if (i2 == iRemaining) {
            return;
        }
        throw new EOFException("Length to read: " + iRemaining + " actual: " + i2);
    }

    public static byte[] toByteArray(InputStream inputStream, int i2) throws IOException {
        if (i2 < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + i2);
        }
        int i3 = 0;
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2];
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                break;
            }
            i3 += i4;
        }
        if (i3 == i2) {
            return bArr;
        }
        throw new IOException("Unexpected read size. current: " + i3 + ", expected: " + i2);
    }

    public static String toString(Reader reader) throws IOException {
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        try {
            copy(reader, stringBuilderWriter);
            String string = stringBuilderWriter.toString();
            stringBuilderWriter.close();
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    stringBuilderWriter.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    public static void write(CharSequence charSequence, Writer writer) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), writer);
        }
    }

    @Deprecated
    public static void closeQuietly(ServerSocket serverSocket) throws IOException {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException unused) {
            }
        }
    }

    public static long copyLarge(Reader reader, Writer writer) throws IOException {
        return copyLarge(reader, writer, new char[4096]);
    }

    public static long skip(Reader reader, long j2) throws IOException {
        if (j2 >= 0) {
            if (SKIP_CHAR_BUFFER == null) {
                SKIP_CHAR_BUFFER = new char[2048];
            }
            long j3 = j2;
            while (j3 > 0) {
                long j4 = reader.read(SKIP_CHAR_BUFFER, 0, (int) Math.min(j3, PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH));
                if (j4 < 0) {
                    break;
                }
                j3 -= j4;
            }
            return j2 - j3;
        }
        throw new IllegalArgumentException("Skip count must be non-negative, actual: " + j2);
    }

    @Deprecated
    public static void write(CharSequence charSequence, OutputStream outputStream) throws IOException {
        write(charSequence, outputStream, Charset.defaultCharset());
    }

    public static BufferedInputStream buffer(InputStream inputStream, int i2) {
        inputStream.getClass();
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream, i2);
    }

    public static long copyLarge(Reader reader, Writer writer, char[] cArr) throws IOException {
        long j2 = 0;
        while (true) {
            int i2 = reader.read(cArr);
            if (-1 == i2) {
                return j2;
            }
            writer.write(cArr, 0, i2);
            j2 += i2;
        }
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, Charset charset) throws IOException {
        if (charSequence != null) {
            write(charSequence.toString(), outputStream, charset);
        }
    }

    public static void copy(Reader reader, OutputStream outputStream, String str) throws IOException {
        copy(reader, outputStream, Charsets.toCharset(str));
    }

    public static void write(CharSequence charSequence, OutputStream outputStream, String str) throws IOException {
        write(charSequence, outputStream, Charsets.toCharset(str));
    }

    public static long copyLarge(Reader reader, Writer writer, long j2, long j3) throws IOException {
        return copyLarge(reader, writer, j2, j3, new char[4096]);
    }

    public static void write(String str, Writer writer) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    public static long copyLarge(Reader reader, Writer writer, long j2, long j3, char[] cArr) throws IOException {
        long j4 = 0;
        if (j2 > 0) {
            skipFully(reader, j2);
        }
        if (j3 == 0) {
            return 0L;
        }
        int length = cArr.length;
        if (j3 > 0 && j3 < cArr.length) {
            length = (int) j3;
        }
        while (length > 0) {
            int i2 = reader.read(cArr, 0, length);
            if (-1 == i2) {
                break;
            }
            writer.write(cArr, 0, i2);
            j4 += i2;
            if (j3 > 0) {
                length = (int) Math.min(j3 - j4, cArr.length);
            }
        }
        return j4;
    }

    @Deprecated
    public static byte[] toByteArray(Reader reader) throws IOException {
        return toByteArray(reader, Charset.defaultCharset());
    }

    @Deprecated
    public static void write(String str, OutputStream outputStream) throws IOException {
        write(str, outputStream, Charset.defaultCharset());
    }

    public static byte[] toByteArray(Reader reader, Charset charset) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            copy(reader, byteArrayOutputStream, charset);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th3) {
                    th.addSuppressed(th3);
                }
                throw th2;
            }
        }
    }

    @Deprecated
    public static String toString(URI uri) throws IOException {
        return toString(uri, Charset.defaultCharset());
    }

    public static void write(String str, OutputStream outputStream, Charset charset) throws IOException {
        if (str != null) {
            outputStream.write(str.getBytes(Charsets.toCharset(charset)));
        }
    }

    public static String toString(URI uri, Charset charset) throws IOException {
        return toString(uri.toURL(), Charsets.toCharset(charset));
    }

    public static void write(String str, OutputStream outputStream, String str2) throws IOException {
        write(str, outputStream, Charsets.toCharset(str2));
    }

    public static String toString(URI uri, String str) throws IOException {
        return toString(uri, Charsets.toCharset(str));
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, Writer writer) throws IOException {
        if (stringBuffer != null) {
            writer.write(stringBuffer.toString());
        }
    }

    @Deprecated
    public static String toString(URL url) throws IOException {
        return toString(url, Charset.defaultCharset());
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream) throws IOException {
        write(stringBuffer, outputStream, (String) null);
    }

    public static String toString(URL url, Charset charset) throws IOException {
        InputStream inputStreamOpenStream = url.openStream();
        try {
            String string = toString(inputStreamOpenStream, charset);
            if (inputStreamOpenStream != null) {
                inputStreamOpenStream.close();
            }
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (inputStreamOpenStream != null) {
                    try {
                        inputStreamOpenStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }

    @Deprecated
    public static void write(StringBuffer stringBuffer, OutputStream outputStream, String str) throws IOException {
        if (stringBuffer != null) {
            outputStream.write(stringBuffer.toString().getBytes(Charsets.toCharset(str)));
        }
    }

    public static byte[] toByteArray(Reader reader, String str) throws IOException {
        return toByteArray(reader, Charsets.toCharset(str));
    }

    @Deprecated
    public static byte[] toByteArray(String str) throws IOException {
        return str.getBytes(Charset.defaultCharset());
    }

    public static byte[] toByteArray(URI uri) throws IOException {
        return toByteArray(uri.toURL());
    }

    public static byte[] toByteArray(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        try {
            return toByteArray(uRLConnectionOpenConnection);
        } finally {
            close(uRLConnectionOpenConnection);
        }
    }

    public static String toString(URL url, String str) throws IOException {
        return toString(url, Charsets.toCharset(str));
    }

    @Deprecated
    public static String toString(byte[] bArr) throws IOException {
        return new String(bArr, Charset.defaultCharset());
    }

    public static String toString(byte[] bArr, String str) throws IOException {
        return new String(bArr, Charsets.toCharset(str));
    }

    public static byte[] toByteArray(URLConnection uRLConnection) throws IOException {
        InputStream inputStream = uRLConnection.getInputStream();
        try {
            byte[] byteArray = toByteArray(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
            return byteArray;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
        }
    }
}
