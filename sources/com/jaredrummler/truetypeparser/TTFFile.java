package com.jaredrummler.truetypeparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public class TTFFile {
    private Map<TTFTableName, TTFDirTabEntry> dirTabs;
    private FontFileReader fontFile;
    private int weightClass;
    private final Set<String> familyNames = new HashSet();
    private String postScriptName = "";
    private String fullName = "";
    private String notice = "";
    private String subFamilyName = "";

    TTFFile() {
    }

    public static TTFFile open(File file) throws IOException {
        return open(new FileInputStream(file));
    }

    private void readDirTabs() throws IOException {
        this.fontFile.readTTFLong();
        int tTFUShort = this.fontFile.readTTFUShort();
        this.fontFile.skip(6L);
        this.dirTabs = new HashMap();
        TTFDirTabEntry[] tTFDirTabEntryArr = new TTFDirTabEntry[tTFUShort];
        for (int i2 = 0; i2 < tTFUShort; i2++) {
            TTFDirTabEntry tTFDirTabEntry = new TTFDirTabEntry();
            tTFDirTabEntryArr[i2] = tTFDirTabEntry;
            this.dirTabs.put(TTFTableName.getValue(tTFDirTabEntry.read(this.fontFile)), tTFDirTabEntryArr[i2]);
        }
        this.dirTabs.put(TTFTableName.TABLE_DIRECTORY, new TTFDirTabEntry(0L, this.fontFile.getCurrentPos()));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readName() throws java.io.IOException {
        /*
            r15 = this;
            com.jaredrummler.truetypeparser.FontFileReader r0 = r15.fontFile
            com.jaredrummler.truetypeparser.TTFTableName r1 = com.jaredrummler.truetypeparser.TTFTableName.NAME
            r2 = 2
            r15.seekTab(r0, r1, r2)
            com.jaredrummler.truetypeparser.FontFileReader r0 = r15.fontFile
            int r0 = r0.getCurrentPos()
            com.jaredrummler.truetypeparser.FontFileReader r1 = r15.fontFile
            int r1 = r1.readTTFUShort()
            com.jaredrummler.truetypeparser.FontFileReader r2 = r15.fontFile
            int r2 = r2.readTTFUShort()
            int r2 = r2 + r0
            r3 = 2
            int r2 = r2 - r3
            r4 = 4
            int r0 = r0 + r4
        L20:
            int r5 = r1 + (-1)
            if (r1 <= 0) goto Lb8
            com.jaredrummler.truetypeparser.FontFileReader r1 = r15.fontFile
            long r6 = (long) r0
            r1.seekSet(r6)
            com.jaredrummler.truetypeparser.FontFileReader r1 = r15.fontFile
            int r1 = r1.readTTFUShort()
            com.jaredrummler.truetypeparser.FontFileReader r6 = r15.fontFile
            int r6 = r6.readTTFUShort()
            com.jaredrummler.truetypeparser.FontFileReader r7 = r15.fontFile
            int r7 = r7.readTTFUShort()
            com.jaredrummler.truetypeparser.FontFileReader r8 = r15.fontFile
            int r8 = r8.readTTFUShort()
            com.jaredrummler.truetypeparser.FontFileReader r9 = r15.fontFile
            int r9 = r9.readTTFUShort()
            r10 = 3
            r11 = 1
            if (r1 == r11) goto L4e
            if (r1 != r10) goto Lb3
        L4e:
            if (r6 == 0) goto L52
            if (r6 != r11) goto Lb3
        L52:
            com.jaredrummler.truetypeparser.FontFileReader r12 = r15.fontFile
            int r13 = r12.readTTFUShort()
            int r13 = r13 + r2
            long r13 = (long) r13
            r12.seekSet(r13)
            if (r1 != r10) goto L66
            com.jaredrummler.truetypeparser.FontFileReader r12 = r15.fontFile
            java.lang.String r6 = r12.readTTFString(r9, r6)
            goto L6c
        L66:
            com.jaredrummler.truetypeparser.FontFileReader r6 = r15.fontFile
            java.lang.String r6 = r6.readTTFString(r9)
        L6c:
            if (r8 == 0) goto La9
            if (r8 == r11) goto La3
            if (r8 == r3) goto L98
            if (r8 == r4) goto L87
            r1 = 6
            if (r8 == r1) goto L7c
            r1 = 16
            if (r8 == r1) goto La3
            goto Lb3
        L7c:
            java.lang.String r1 = r15.postScriptName
            int r1 = r1.length()
            if (r1 != 0) goto Lb3
            r15.postScriptName = r6
            goto Lb3
        L87:
            java.lang.String r8 = r15.fullName
            int r8 = r8.length()
            if (r8 == 0) goto L95
            if (r1 != r10) goto Lb3
            r1 = 1033(0x409, float:1.448E-42)
            if (r7 != r1) goto Lb3
        L95:
            r15.fullName = r6
            goto Lb3
        L98:
            java.lang.String r1 = r15.subFamilyName
            int r1 = r1.length()
            if (r1 != 0) goto Lb3
            r15.subFamilyName = r6
            goto Lb3
        La3:
            java.util.Set<java.lang.String> r1 = r15.familyNames
            r1.add(r6)
            goto Lb3
        La9:
            java.lang.String r1 = r15.notice
            int r1 = r1.length()
            if (r1 != 0) goto Lb3
            r15.notice = r6
        Lb3:
            int r0 = r0 + 12
            r1 = r5
            goto L20
        Lb8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jaredrummler.truetypeparser.TTFFile.readName():void");
    }

    private boolean seekTab(FontFileReader fontFileReader, TTFTableName tTFTableName, long j2) throws IOException {
        TTFDirTabEntry tTFDirTabEntry = this.dirTabs.get(tTFTableName);
        if (tTFDirTabEntry == null) {
            return false;
        }
        fontFileReader.seekSet(tTFDirTabEntry.getOffset() + j2);
        return true;
    }

    void a(FontFileReader fontFileReader) throws IOException {
        this.fontFile = fontFileReader;
        readDirTabs();
        Map<TTFTableName, TTFDirTabEntry> map = this.dirTabs;
        TTFTableName tTFTableName = TTFTableName.OS2;
        if (map.get(tTFTableName) != null) {
            seekTab(this.fontFile, tTFTableName, 0L);
            this.fontFile.readTTFUShort();
            this.fontFile.skip(2L);
            this.weightClass = this.fontFile.readTTFUShort();
        }
        readName();
    }

    public Set<String> getFamilyNames() {
        return this.familyNames;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getNotice() {
        return this.notice;
    }

    public String getPostScriptName() {
        return this.postScriptName;
    }

    public String getSubFamilyName() {
        return this.subFamilyName;
    }

    public int getWeightClass() {
        return this.weightClass;
    }

    public static TTFFile open(InputStream inputStream) throws IOException {
        TTFFile tTFFile = new TTFFile();
        tTFFile.a(new FontFileReader(inputStream));
        return tTFFile;
    }
}
