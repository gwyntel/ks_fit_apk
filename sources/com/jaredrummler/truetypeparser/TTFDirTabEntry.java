package com.jaredrummler.truetypeparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class TTFDirTabEntry {
    private long length;
    private long offset;
    private final byte[] tag;

    TTFDirTabEntry() {
        this.tag = new byte[4];
    }

    public long getLength() {
        return this.length;
    }

    public long getOffset() {
        return this.offset;
    }

    public byte[] getTag() {
        return this.tag;
    }

    public String getTagString() {
        try {
            return new String(this.tag, "ISO-8859-1");
        } catch (UnsupportedEncodingException unused) {
            return toString();
        }
    }

    public String read(FontFileReader fontFileReader) throws IOException {
        this.tag[0] = fontFileReader.readTTFByte();
        this.tag[1] = fontFileReader.readTTFByte();
        this.tag[2] = fontFileReader.readTTFByte();
        this.tag[3] = fontFileReader.readTTFByte();
        fontFileReader.skip(4L);
        this.offset = fontFileReader.readTTFULong();
        this.length = fontFileReader.readTTFULong();
        return new String(this.tag, "ISO-8859-1");
    }

    public TTFDirTabEntry(long j2, long j3) {
        this.tag = new byte[4];
        this.offset = j2;
        this.length = j3;
    }
}
