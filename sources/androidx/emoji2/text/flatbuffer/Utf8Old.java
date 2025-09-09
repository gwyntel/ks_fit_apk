package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class Utf8Old extends Utf8 {
    private static final ThreadLocal<Cache> CACHE = ThreadLocal.withInitial(new Supplier() { // from class: androidx.emoji2.text.flatbuffer.b
        @Override // java.util.function.Supplier
        public final Object get() {
            return Utf8Old.lambda$static$0();
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    static class Cache {

        /* renamed from: a, reason: collision with root package name */
        final CharsetEncoder f4074a;

        /* renamed from: b, reason: collision with root package name */
        final CharsetDecoder f4075b;

        /* renamed from: c, reason: collision with root package name */
        CharSequence f4076c = null;

        /* renamed from: d, reason: collision with root package name */
        ByteBuffer f4077d = null;

        Cache() {
            Charset charset = StandardCharsets.UTF_8;
            this.f4074a = charset.newEncoder();
            this.f4075b = charset.newDecoder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Cache lambda$static$0() {
        return new Cache();
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public String decodeUtf8(ByteBuffer byteBuffer, int i2, int i3) {
        CharsetDecoder charsetDecoder = CACHE.get().f4075b;
        charsetDecoder.reset();
        ByteBuffer byteBufferDuplicate = byteBuffer.duplicate();
        byteBufferDuplicate.position(i2);
        byteBufferDuplicate.limit(i2 + i3);
        try {
            return charsetDecoder.decode(byteBufferDuplicate).toString();
        } catch (CharacterCodingException e2) {
            throw new IllegalArgumentException("Bad encoding", e2);
        }
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public void encodeUtf8(CharSequence charSequence, ByteBuffer byteBuffer) throws CharacterCodingException {
        Cache cache = CACHE.get();
        if (cache.f4076c != charSequence) {
            encodedLength(charSequence);
        }
        byteBuffer.put(cache.f4077d);
    }

    @Override // androidx.emoji2.text.flatbuffer.Utf8
    public int encodedLength(CharSequence charSequence) throws CharacterCodingException {
        Cache cache = CACHE.get();
        int length = (int) (charSequence.length() * cache.f4074a.maxBytesPerChar());
        ByteBuffer byteBuffer = cache.f4077d;
        if (byteBuffer == null || byteBuffer.capacity() < length) {
            cache.f4077d = ByteBuffer.allocate(Math.max(128, length));
        }
        cache.f4077d.clear();
        cache.f4076c = charSequence;
        CoderResult coderResultEncode = cache.f4074a.encode(charSequence instanceof CharBuffer ? (CharBuffer) charSequence : CharBuffer.wrap(charSequence), cache.f4077d, true);
        if (coderResultEncode.isError()) {
            try {
                coderResultEncode.throwException();
            } catch (CharacterCodingException e2) {
                throw new IllegalArgumentException("bad character encoding", e2);
            }
        }
        cache.f4077d.flip();
        return cache.f4077d.remaining();
    }
}
