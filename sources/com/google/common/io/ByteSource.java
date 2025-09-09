package com.google.common.io;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.hash.Funnels;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@ElementTypesAreNonnullByDefault
@J2ktIncompatible
@GwtIncompatible
/* loaded from: classes3.dex */
public abstract class ByteSource {

    class AsCharSource extends CharSource {

        /* renamed from: a, reason: collision with root package name */
        final Charset f14658a;

        AsCharSource(Charset charset) {
            this.f14658a = (Charset) Preconditions.checkNotNull(charset);
        }

        @Override // com.google.common.io.CharSource
        public ByteSource asByteSource(Charset charset) {
            return charset.equals(this.f14658a) ? ByteSource.this : super.asByteSource(charset);
        }

        @Override // com.google.common.io.CharSource
        public Reader openStream() throws IOException {
            return new InputStreamReader(ByteSource.this.openStream(), this.f14658a);
        }

        @Override // com.google.common.io.CharSource
        public String read() throws IOException {
            return new String(ByteSource.this.read(), this.f14658a);
        }

        public String toString() {
            return ByteSource.this.toString() + ".asCharSource(" + this.f14658a + ")";
        }
    }

    private static class ByteArrayByteSource extends ByteSource {

        /* renamed from: a, reason: collision with root package name */
        final byte[] f14660a;

        /* renamed from: b, reason: collision with root package name */
        final int f14661b;

        /* renamed from: c, reason: collision with root package name */
        final int f14662c;

        ByteArrayByteSource(byte[] bArr) {
            this(bArr, 0, bArr.length);
        }

        @Override // com.google.common.io.ByteSource
        public long copyTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.f14660a, this.f14661b, this.f14662c);
            return this.f14662c;
        }

        @Override // com.google.common.io.ByteSource
        public HashCode hash(HashFunction hashFunction) throws IOException {
            return hashFunction.hashBytes(this.f14660a, this.f14661b, this.f14662c);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() {
            return this.f14662c == 0;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() {
            return openStream();
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() {
            return new ByteArrayInputStream(this.f14660a, this.f14661b, this.f14662c);
        }

        @Override // com.google.common.io.ByteSource
        public byte[] read() {
            byte[] bArr = this.f14660a;
            int i2 = this.f14661b;
            return Arrays.copyOfRange(bArr, i2, this.f14662c + i2);
        }

        @Override // com.google.common.io.ByteSource
        public long size() {
            return this.f14662c;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            return Optional.of(Long.valueOf(this.f14662c));
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j2, long j3) {
            Preconditions.checkArgument(j2 >= 0, "offset (%s) may not be negative", j2);
            Preconditions.checkArgument(j3 >= 0, "length (%s) may not be negative", j3);
            long jMin = Math.min(j2, this.f14662c);
            return new ByteArrayByteSource(this.f14660a, this.f14661b + ((int) jMin), (int) Math.min(j3, this.f14662c - jMin));
        }

        public String toString() {
            return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.f14660a, this.f14661b, this.f14662c), 30, "...") + ")";
        }

        ByteArrayByteSource(byte[] bArr, int i2, int i3) {
            this.f14660a = bArr;
            this.f14661b = i2;
            this.f14662c = i3;
        }

        @Override // com.google.common.io.ByteSource
        @ParametricNullness
        public <T> T read(ByteProcessor<T> byteProcessor) throws IOException {
            byteProcessor.processBytes(this.f14660a, this.f14661b, this.f14662c);
            return byteProcessor.getResult();
        }
    }

    private static final class ConcatenatedByteSource extends ByteSource {

        /* renamed from: a, reason: collision with root package name */
        final Iterable f14663a;

        ConcatenatedByteSource(Iterable iterable) {
            this.f14663a = (Iterable) Preconditions.checkNotNull(iterable);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            Iterator it = this.f14663a.iterator();
            while (it.hasNext()) {
                if (!((ByteSource) it.next()).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return new MultiInputStream(this.f14663a.iterator());
        }

        @Override // com.google.common.io.ByteSource
        public long size() throws IOException {
            Iterator it = this.f14663a.iterator();
            long size = 0;
            while (it.hasNext()) {
                size += ((ByteSource) it.next()).size();
                if (size < 0) {
                    return Long.MAX_VALUE;
                }
            }
            return size;
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Iterable iterable = this.f14663a;
            if (!(iterable instanceof Collection)) {
                return Optional.absent();
            }
            Iterator it = iterable.iterator();
            long jLongValue = 0;
            while (it.hasNext()) {
                Optional<Long> optionalSizeIfKnown = ((ByteSource) it.next()).sizeIfKnown();
                if (!optionalSizeIfKnown.isPresent()) {
                    return Optional.absent();
                }
                jLongValue += optionalSizeIfKnown.get().longValue();
                if (jLongValue < 0) {
                    return Optional.of(Long.MAX_VALUE);
                }
            }
            return Optional.of(Long.valueOf(jLongValue));
        }

        public String toString() {
            return "ByteSource.concat(" + this.f14663a + ")";
        }
    }

    private static final class EmptyByteSource extends ByteArrayByteSource {

        /* renamed from: d, reason: collision with root package name */
        static final EmptyByteSource f14664d = new EmptyByteSource();

        EmptyByteSource() {
            super(new byte[0]);
        }

        @Override // com.google.common.io.ByteSource
        public CharSource asCharSource(Charset charset) {
            Preconditions.checkNotNull(charset);
            return CharSource.empty();
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource, com.google.common.io.ByteSource
        public byte[] read() {
            return this.f14660a;
        }

        @Override // com.google.common.io.ByteSource.ByteArrayByteSource
        public String toString() {
            return "ByteSource.empty()";
        }
    }

    private final class SlicedByteSource extends ByteSource {

        /* renamed from: a, reason: collision with root package name */
        final long f14665a;

        /* renamed from: b, reason: collision with root package name */
        final long f14666b;

        SlicedByteSource(long j2, long j3) {
            Preconditions.checkArgument(j2 >= 0, "offset (%s) may not be negative", j2);
            Preconditions.checkArgument(j3 >= 0, "length (%s) may not be negative", j3);
            this.f14665a = j2;
            this.f14666b = j3;
        }

        private InputStream sliceStream(InputStream inputStream) throws Throwable {
            long j2 = this.f14665a;
            if (j2 > 0) {
                try {
                    if (ByteStreams.b(inputStream, j2) < this.f14665a) {
                        inputStream.close();
                        return new ByteArrayInputStream(new byte[0]);
                    }
                } finally {
                }
            }
            return ByteStreams.limit(inputStream, this.f14666b);
        }

        @Override // com.google.common.io.ByteSource
        public boolean isEmpty() throws IOException {
            return this.f14666b == 0 || super.isEmpty();
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openBufferedStream() throws IOException {
            return sliceStream(ByteSource.this.openBufferedStream());
        }

        @Override // com.google.common.io.ByteSource
        public InputStream openStream() throws IOException {
            return sliceStream(ByteSource.this.openStream());
        }

        @Override // com.google.common.io.ByteSource
        public Optional<Long> sizeIfKnown() {
            Optional<Long> optionalSizeIfKnown = ByteSource.this.sizeIfKnown();
            if (!optionalSizeIfKnown.isPresent()) {
                return Optional.absent();
            }
            long jLongValue = optionalSizeIfKnown.get().longValue();
            return Optional.of(Long.valueOf(Math.min(this.f14666b, jLongValue - Math.min(this.f14665a, jLongValue))));
        }

        @Override // com.google.common.io.ByteSource
        public ByteSource slice(long j2, long j3) {
            Preconditions.checkArgument(j2 >= 0, "offset (%s) may not be negative", j2);
            Preconditions.checkArgument(j3 >= 0, "length (%s) may not be negative", j3);
            long j4 = this.f14666b - j2;
            return j4 <= 0 ? ByteSource.empty() : ByteSource.this.slice(this.f14665a + j2, Math.min(j3, j4));
        }

        public String toString() {
            return ByteSource.this.toString() + ".slice(" + this.f14665a + ", " + this.f14666b + ")";
        }
    }

    protected ByteSource() {
    }

    public static ByteSource concat(Iterable<? extends ByteSource> iterable) {
        return new ConcatenatedByteSource(iterable);
    }

    private long countBySkipping(InputStream inputStream) throws IOException {
        long j2 = 0;
        while (true) {
            long jB = ByteStreams.b(inputStream, 2147483647L);
            if (jB <= 0) {
                return j2;
            }
            j2 += jB;
        }
    }

    public static ByteSource empty() {
        return EmptyByteSource.f14664d;
    }

    public static ByteSource wrap(byte[] bArr) {
        return new ByteArrayByteSource(bArr);
    }

    public CharSource asCharSource(Charset charset) {
        return new AsCharSource(charset);
    }

    public boolean contentEquals(ByteSource byteSource) throws Throwable {
        int i2;
        Preconditions.checkNotNull(byteSource);
        byte[] bArrA = ByteStreams.a();
        byte[] bArrA2 = ByteStreams.a();
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            InputStream inputStream2 = (InputStream) closerCreate.register(byteSource.openStream());
            do {
                i2 = ByteStreams.read(inputStream, bArrA, 0, bArrA.length);
                if (i2 == ByteStreams.read(inputStream2, bArrA2, 0, bArrA2.length) && Arrays.equals(bArrA, bArrA2)) {
                }
                return false;
            } while (i2 == bArrA.length);
            closerCreate.close();
            return true;
        } finally {
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(OutputStream outputStream) throws Throwable {
        Preconditions.checkNotNull(outputStream);
        try {
            return ByteStreams.copy((InputStream) Closer.create().register(openStream()), outputStream);
        } finally {
        }
    }

    public HashCode hash(HashFunction hashFunction) throws Throwable {
        Hasher hasherNewHasher = hashFunction.newHasher();
        copyTo(Funnels.asOutputStream(hasherNewHasher));
        return hasherNewHasher.hash();
    }

    public boolean isEmpty() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue() == 0;
        }
        Closer closerCreate = Closer.create();
        try {
            return ((InputStream) closerCreate.register(openStream())).read() == -1;
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } finally {
                closerCreate.close();
            }
        }
    }

    public InputStream openBufferedStream() throws IOException {
        InputStream inputStreamOpenStream = openStream();
        return inputStreamOpenStream instanceof BufferedInputStream ? (BufferedInputStream) inputStreamOpenStream : new BufferedInputStream(inputStreamOpenStream);
    }

    public abstract InputStream openStream() throws IOException;

    public byte[] read() throws Throwable {
        Closer closerCreate = Closer.create();
        try {
            InputStream inputStream = (InputStream) closerCreate.register(openStream());
            Optional<Long> optionalSizeIfKnown = sizeIfKnown();
            return optionalSizeIfKnown.isPresent() ? ByteStreams.c(inputStream, optionalSizeIfKnown.get().longValue()) : ByteStreams.toByteArray(inputStream);
        } catch (Throwable th) {
            try {
                throw closerCreate.rethrow(th);
            } finally {
                closerCreate.close();
            }
        }
    }

    public long size() throws Throwable {
        Optional<Long> optionalSizeIfKnown = sizeIfKnown();
        if (optionalSizeIfKnown.isPresent()) {
            return optionalSizeIfKnown.get().longValue();
        }
        Closer closerCreate = Closer.create();
        try {
            return countBySkipping((InputStream) closerCreate.register(openStream()));
        } catch (IOException unused) {
            closerCreate.close();
            try {
                return ByteStreams.exhaust((InputStream) Closer.create().register(openStream()));
            } finally {
            }
        } finally {
        }
    }

    public Optional<Long> sizeIfKnown() {
        return Optional.absent();
    }

    public ByteSource slice(long j2, long j3) {
        return new SlicedByteSource(j2, j3);
    }

    public static ByteSource concat(Iterator<? extends ByteSource> it) {
        return concat(ImmutableList.copyOf(it));
    }

    public static ByteSource concat(ByteSource... byteSourceArr) {
        return concat(ImmutableList.copyOf(byteSourceArr));
    }

    @CanIgnoreReturnValue
    public long copyTo(ByteSink byteSink) throws Throwable {
        Preconditions.checkNotNull(byteSink);
        Closer closerCreate = Closer.create();
        try {
            return ByteStreams.copy((InputStream) closerCreate.register(openStream()), (OutputStream) closerCreate.register(byteSink.openStream()));
        } finally {
        }
    }

    @CanIgnoreReturnValue
    @ParametricNullness
    public <T> T read(ByteProcessor<T> byteProcessor) throws Throwable {
        Preconditions.checkNotNull(byteProcessor);
        try {
            return (T) ByteStreams.readBytes((InputStream) Closer.create().register(openStream()), byteProcessor);
        } finally {
        }
    }
}
