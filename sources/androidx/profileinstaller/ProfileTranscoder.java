package androidx.profileinstaller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

@RequiresApi(19)
/* loaded from: classes2.dex */
class ProfileTranscoder {
    private static final int HOT = 1;
    private static final int INLINE_CACHE_MEGAMORPHIC_ENCODING = 7;
    private static final int INLINE_CACHE_MISSING_TYPES_ENCODING = 6;
    private static final int POST_STARTUP = 4;
    private static final int STARTUP = 2;

    /* renamed from: a, reason: collision with root package name */
    static final byte[] f5754a = {112, 114, 111, 0};

    /* renamed from: b, reason: collision with root package name */
    static final byte[] f5755b = {112, 114, 109, 0};

    private ProfileTranscoder() {
    }

    static byte[] a(InputStream inputStream, byte[] bArr) {
        if (Arrays.equals(bArr, Encoding.d(inputStream, bArr.length))) {
            return Encoding.d(inputStream, ProfileVersion.f5762b.length);
        }
        throw Encoding.c("Invalid magic");
    }

    static DexProfileData[] b(InputStream inputStream, byte[] bArr, byte[] bArr2, DexProfileData[] dexProfileDataArr) {
        if (Arrays.equals(bArr, ProfileVersion.f5766f)) {
            if (Arrays.equals(ProfileVersion.f5761a, bArr2)) {
                throw Encoding.c("Requires new Baseline Profile Metadata. Please rebuild the APK with Android Gradle Plugin 7.2 Canary 7 or higher");
            }
            return c(inputStream, bArr, dexProfileDataArr);
        }
        if (Arrays.equals(bArr, ProfileVersion.f5767g)) {
            return d(inputStream, bArr2, dexProfileDataArr);
        }
        throw Encoding.c("Unsupported meta version");
    }

    static DexProfileData[] c(InputStream inputStream, byte[] bArr, DexProfileData[] dexProfileDataArr) throws IOException {
        if (!Arrays.equals(bArr, ProfileVersion.f5766f)) {
            throw Encoding.c("Unsupported meta version");
        }
        int iJ = Encoding.j(inputStream);
        byte[] bArrE = Encoding.e(inputStream, (int) Encoding.i(inputStream), (int) Encoding.i(inputStream));
        if (inputStream.read() > 0) {
            throw Encoding.c("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrE);
        try {
            DexProfileData[] metadataForNBody = readMetadataForNBody(byteArrayInputStream, iJ, dexProfileDataArr);
            byteArrayInputStream.close();
            return metadataForNBody;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static int computeMethodFlags(@NonNull DexProfileData dexProfileData) {
        Iterator it = dexProfileData.f5751i.entrySet().iterator();
        int iIntValue = 0;
        while (it.hasNext()) {
            iIntValue |= ((Integer) ((Map.Entry) it.next()).getValue()).intValue();
        }
        return iIntValue;
    }

    @NonNull
    private static byte[] createCompressibleBody(@NonNull DexProfileData[] dexProfileDataArr, @NonNull byte[] bArr) throws IOException {
        int i2 = 0;
        int iK = 0;
        for (DexProfileData dexProfileData : dexProfileDataArr) {
            iK += Encoding.k(generateDexKey(dexProfileData.f5743a, dexProfileData.f5744b, bArr)) + 16 + (dexProfileData.f5747e * 2) + dexProfileData.f5748f + getMethodBitmapStorageSize(dexProfileData.f5749g);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(iK);
        if (Arrays.equals(bArr, ProfileVersion.f5763c)) {
            int length = dexProfileDataArr.length;
            while (i2 < length) {
                DexProfileData dexProfileData2 = dexProfileDataArr[i2];
                writeLineHeader(byteArrayOutputStream, dexProfileData2, generateDexKey(dexProfileData2.f5743a, dexProfileData2.f5744b, bArr));
                writeLineData(byteArrayOutputStream, dexProfileData2);
                i2++;
            }
        } else {
            for (DexProfileData dexProfileData3 : dexProfileDataArr) {
                writeLineHeader(byteArrayOutputStream, dexProfileData3, generateDexKey(dexProfileData3.f5743a, dexProfileData3.f5744b, bArr));
            }
            int length2 = dexProfileDataArr.length;
            while (i2 < length2) {
                writeLineData(byteArrayOutputStream, dexProfileDataArr[i2]);
                i2++;
            }
        }
        if (byteArrayOutputStream.size() == iK) {
            return byteArrayOutputStream.toByteArray();
        }
        throw Encoding.c("The bytes saved do not match expectation. actual=" + byteArrayOutputStream.size() + " expected=" + iK);
    }

    private static WritableFileSection createCompressibleClassSection(@NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        for (int i3 = 0; i3 < dexProfileDataArr.length; i3++) {
            try {
                DexProfileData dexProfileData = dexProfileDataArr[i3];
                Encoding.p(byteArrayOutputStream, i3);
                Encoding.p(byteArrayOutputStream, dexProfileData.f5747e);
                i2 = i2 + 4 + (dexProfileData.f5747e * 2);
                writeClasses(byteArrayOutputStream, dexProfileData);
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (i2 == byteArray.length) {
            WritableFileSection writableFileSection = new WritableFileSection(FileSectionType.CLASSES, i2, byteArray, true);
            byteArrayOutputStream.close();
            return writableFileSection;
        }
        throw Encoding.c("Expected size " + i2 + ", does not match actual size " + byteArray.length);
    }

    private static WritableFileSection createCompressibleMethodsSection(@NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        for (int i3 = 0; i3 < dexProfileDataArr.length; i3++) {
            try {
                DexProfileData dexProfileData = dexProfileDataArr[i3];
                int iComputeMethodFlags = computeMethodFlags(dexProfileData);
                byte[] bArrCreateMethodBitmapRegion = createMethodBitmapRegion(dexProfileData);
                byte[] bArrCreateMethodsWithInlineCaches = createMethodsWithInlineCaches(dexProfileData);
                Encoding.p(byteArrayOutputStream, i3);
                int length = bArrCreateMethodBitmapRegion.length + 2 + bArrCreateMethodsWithInlineCaches.length;
                Encoding.q(byteArrayOutputStream, length);
                Encoding.p(byteArrayOutputStream, iComputeMethodFlags);
                byteArrayOutputStream.write(bArrCreateMethodBitmapRegion);
                byteArrayOutputStream.write(bArrCreateMethodsWithInlineCaches);
                i2 = i2 + 6 + length;
            } catch (Throwable th) {
                try {
                    byteArrayOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        if (i2 == byteArray.length) {
            WritableFileSection writableFileSection = new WritableFileSection(FileSectionType.METHODS, i2, byteArray, true);
            byteArrayOutputStream.close();
            return writableFileSection;
        }
        throw Encoding.c("Expected size " + i2 + ", does not match actual size " + byteArray.length);
    }

    private static byte[] createMethodBitmapRegion(@NonNull DexProfileData dexProfileData) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeMethodBitmap(byteArrayOutputStream, dexProfileData);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static byte[] createMethodsWithInlineCaches(@NonNull DexProfileData dexProfileData) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeMethodsWithInlineCaches(byteArrayOutputStream, dexProfileData);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return byteArray;
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static DexProfileData[] d(InputStream inputStream, byte[] bArr, DexProfileData[] dexProfileDataArr) throws IOException {
        int iH = Encoding.h(inputStream);
        byte[] bArrE = Encoding.e(inputStream, (int) Encoding.i(inputStream), (int) Encoding.i(inputStream));
        if (inputStream.read() > 0) {
            throw Encoding.c("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrE);
        try {
            DexProfileData[] metadataV002Body = readMetadataV002Body(byteArrayInputStream, bArr, iH, dexProfileDataArr);
            byteArrayInputStream.close();
            return metadataV002Body;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    static DexProfileData[] e(InputStream inputStream, byte[] bArr, String str) throws IOException {
        if (!Arrays.equals(bArr, ProfileVersion.f5762b)) {
            throw Encoding.c("Unsupported version");
        }
        int iJ = Encoding.j(inputStream);
        byte[] bArrE = Encoding.e(inputStream, (int) Encoding.i(inputStream), (int) Encoding.i(inputStream));
        if (inputStream.read() > 0) {
            throw Encoding.c("Content found after the end of file");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArrE);
        try {
            DexProfileData[] uncompressedBody = readUncompressedBody(byteArrayInputStream, str, iJ);
            byteArrayInputStream.close();
            return uncompressedBody;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @NonNull
    private static String enforceSeparator(@NonNull String str, @NonNull String str2) {
        return "!".equals(str2) ? str.replace(":", "!") : ":".equals(str2) ? str.replace("!", ":") : str;
    }

    @NonNull
    private static String extractKey(@NonNull String str) {
        int iIndexOf = str.indexOf("!");
        if (iIndexOf < 0) {
            iIndexOf = str.indexOf(":");
        }
        return iIndexOf > 0 ? str.substring(iIndexOf + 1) : str;
    }

    static boolean f(OutputStream outputStream, byte[] bArr, DexProfileData[] dexProfileDataArr) throws IOException {
        if (Arrays.equals(bArr, ProfileVersion.f5761a)) {
            writeProfileForS(outputStream, dexProfileDataArr);
            return true;
        }
        if (Arrays.equals(bArr, ProfileVersion.f5762b)) {
            writeProfileForP(outputStream, dexProfileDataArr);
            return true;
        }
        if (Arrays.equals(bArr, ProfileVersion.f5764d)) {
            writeProfileForO(outputStream, dexProfileDataArr);
            return true;
        }
        if (Arrays.equals(bArr, ProfileVersion.f5763c)) {
            writeProfileForO_MR1(outputStream, dexProfileDataArr);
            return true;
        }
        if (!Arrays.equals(bArr, ProfileVersion.f5765e)) {
            return false;
        }
        writeProfileForN(outputStream, dexProfileDataArr);
        return true;
    }

    @Nullable
    private static DexProfileData findByDexName(@NonNull DexProfileData[] dexProfileDataArr, @NonNull String str) {
        if (dexProfileDataArr.length <= 0) {
            return null;
        }
        String strExtractKey = extractKey(str);
        for (int i2 = 0; i2 < dexProfileDataArr.length; i2++) {
            if (dexProfileDataArr[i2].f5744b.equals(strExtractKey)) {
                return dexProfileDataArr[i2];
            }
        }
        return null;
    }

    static void g(OutputStream outputStream, byte[] bArr) throws IOException {
        outputStream.write(f5754a);
        outputStream.write(bArr);
    }

    @NonNull
    private static String generateDexKey(@NonNull String str, @NonNull String str2, @NonNull byte[] bArr) {
        String strA = ProfileVersion.a(bArr);
        if (str.length() <= 0) {
            return enforceSeparator(str2, strA);
        }
        if (str2.equals("classes.dex")) {
            return str;
        }
        if (str2.contains("!") || str2.contains(":")) {
            return enforceSeparator(str2, strA);
        }
        if (str2.endsWith(".apk")) {
            return str2;
        }
        return str + ProfileVersion.a(bArr) + str2;
    }

    private static int getMethodBitmapStorageSize(int i2) {
        return roundUpToByte(i2 * 2) / 8;
    }

    private static int methodFlagBitmapIndex(int i2, int i3, int i4) {
        if (i2 == 1) {
            throw Encoding.c("HOT methods are not stored in the bitmap");
        }
        if (i2 == 2) {
            return i3;
        }
        if (i2 == 4) {
            return i3 + i4;
        }
        throw Encoding.c("Unexpected flag: " + i2);
    }

    private static int[] readClasses(@NonNull InputStream inputStream, int i2) throws IOException {
        int[] iArr = new int[i2];
        int iH = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            iH += Encoding.h(inputStream);
            iArr[i3] = iH;
        }
        return iArr;
    }

    private static int readFlagsFromBitmap(@NonNull BitSet bitSet, int i2, int i3) {
        int i4 = bitSet.get(methodFlagBitmapIndex(2, i2, i3)) ? 2 : 0;
        return bitSet.get(methodFlagBitmapIndex(4, i2, i3)) ? i4 | 4 : i4;
    }

    private static void readHotMethodRegion(@NonNull InputStream inputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        int iAvailable = inputStream.available() - dexProfileData.f5748f;
        int iH = 0;
        while (inputStream.available() > iAvailable) {
            iH += Encoding.h(inputStream);
            dexProfileData.f5751i.put(Integer.valueOf(iH), 1);
            for (int iH2 = Encoding.h(inputStream); iH2 > 0; iH2--) {
                skipInlineCache(inputStream);
            }
        }
        if (inputStream.available() != iAvailable) {
            throw Encoding.c("Read too much data during profile line parse");
        }
    }

    @NonNull
    private static DexProfileData[] readMetadataForNBody(@NonNull InputStream inputStream, int i2, DexProfileData[] dexProfileDataArr) throws IOException {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        if (i2 != dexProfileDataArr.length) {
            throw Encoding.c("Mismatched number of dex files found in metadata");
        }
        String[] strArr = new String[i2];
        int[] iArr = new int[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int iH = Encoding.h(inputStream);
            iArr[i3] = Encoding.h(inputStream);
            strArr[i3] = Encoding.f(inputStream, iH);
        }
        for (int i4 = 0; i4 < i2; i4++) {
            DexProfileData dexProfileData = dexProfileDataArr[i4];
            if (!dexProfileData.f5744b.equals(strArr[i4])) {
                throw Encoding.c("Order of dexfiles in metadata did not match baseline");
            }
            int i5 = iArr[i4];
            dexProfileData.f5747e = i5;
            dexProfileData.f5750h = readClasses(inputStream, i5);
        }
        return dexProfileDataArr;
    }

    @NonNull
    private static DexProfileData[] readMetadataV002Body(@NonNull InputStream inputStream, @NonNull byte[] bArr, int i2, DexProfileData[] dexProfileDataArr) throws IOException {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        if (i2 != dexProfileDataArr.length) {
            throw Encoding.c("Mismatched number of dex files found in metadata");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            Encoding.h(inputStream);
            String strF = Encoding.f(inputStream, Encoding.h(inputStream));
            long jI = Encoding.i(inputStream);
            int iH = Encoding.h(inputStream);
            DexProfileData dexProfileDataFindByDexName = findByDexName(dexProfileDataArr, strF);
            if (dexProfileDataFindByDexName == null) {
                throw Encoding.c("Missing profile key: " + strF);
            }
            dexProfileDataFindByDexName.f5746d = jI;
            int[] classes = readClasses(inputStream, iH);
            if (Arrays.equals(bArr, ProfileVersion.f5765e)) {
                dexProfileDataFindByDexName.f5747e = iH;
                dexProfileDataFindByDexName.f5750h = classes;
            }
        }
        return dexProfileDataArr;
    }

    private static void readMethodBitmap(@NonNull InputStream inputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        BitSet bitSetValueOf = BitSet.valueOf(Encoding.d(inputStream, Encoding.a(dexProfileData.f5749g * 2)));
        int i2 = 0;
        while (true) {
            int i3 = dexProfileData.f5749g;
            if (i2 >= i3) {
                return;
            }
            int flagsFromBitmap = readFlagsFromBitmap(bitSetValueOf, i2, i3);
            if (flagsFromBitmap != 0) {
                Integer num = (Integer) dexProfileData.f5751i.get(Integer.valueOf(i2));
                if (num == null) {
                    num = 0;
                }
                dexProfileData.f5751i.put(Integer.valueOf(i2), Integer.valueOf(flagsFromBitmap | num.intValue()));
            }
            i2++;
        }
    }

    @NonNull
    private static DexProfileData[] readUncompressedBody(@NonNull InputStream inputStream, @NonNull String str, int i2) throws IOException {
        if (inputStream.available() == 0) {
            return new DexProfileData[0];
        }
        DexProfileData[] dexProfileDataArr = new DexProfileData[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            int iH = Encoding.h(inputStream);
            int iH2 = Encoding.h(inputStream);
            dexProfileDataArr[i3] = new DexProfileData(str, Encoding.f(inputStream, iH), Encoding.i(inputStream), 0L, iH2, (int) Encoding.i(inputStream), (int) Encoding.i(inputStream), new int[iH2], new TreeMap());
        }
        for (int i4 = 0; i4 < i2; i4++) {
            DexProfileData dexProfileData = dexProfileDataArr[i4];
            readHotMethodRegion(inputStream, dexProfileData);
            dexProfileData.f5750h = readClasses(inputStream, dexProfileData.f5747e);
            readMethodBitmap(inputStream, dexProfileData);
        }
        return dexProfileDataArr;
    }

    private static int roundUpToByte(int i2) {
        return (i2 + 7) & (-8);
    }

    private static void setMethodBitmapBit(@NonNull byte[] bArr, int i2, int i3, @NonNull DexProfileData dexProfileData) {
        int iMethodFlagBitmapIndex = methodFlagBitmapIndex(i2, i3, dexProfileData.f5749g);
        int i4 = iMethodFlagBitmapIndex / 8;
        bArr[i4] = (byte) ((1 << (iMethodFlagBitmapIndex % 8)) | bArr[i4]);
    }

    private static void skipInlineCache(@NonNull InputStream inputStream) throws IOException {
        Encoding.h(inputStream);
        int iJ = Encoding.j(inputStream);
        if (iJ == 6 || iJ == 7) {
            return;
        }
        while (iJ > 0) {
            Encoding.j(inputStream);
            for (int iJ2 = Encoding.j(inputStream); iJ2 > 0; iJ2--) {
                Encoding.h(inputStream);
            }
            iJ--;
        }
    }

    private static void writeClasses(@NonNull OutputStream outputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        int[] iArr = dexProfileData.f5750h;
        int length = iArr.length;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = iArr[i2];
            Encoding.p(outputStream, i4 - i3);
            i2++;
            i3 = i4;
        }
    }

    private static WritableFileSection writeDexFileSection(@NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Encoding.p(byteArrayOutputStream, dexProfileDataArr.length);
            int i2 = 2;
            for (DexProfileData dexProfileData : dexProfileDataArr) {
                Encoding.q(byteArrayOutputStream, dexProfileData.f5745c);
                Encoding.q(byteArrayOutputStream, dexProfileData.f5746d);
                Encoding.q(byteArrayOutputStream, dexProfileData.f5749g);
                String strGenerateDexKey = generateDexKey(dexProfileData.f5743a, dexProfileData.f5744b, ProfileVersion.f5761a);
                int iK = Encoding.k(strGenerateDexKey);
                Encoding.p(byteArrayOutputStream, iK);
                i2 = i2 + 14 + iK;
                Encoding.n(byteArrayOutputStream, strGenerateDexKey);
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (i2 == byteArray.length) {
                WritableFileSection writableFileSection = new WritableFileSection(FileSectionType.DEX_FILES, i2, byteArray, false);
                byteArrayOutputStream.close();
                return writableFileSection;
            }
            throw Encoding.c("Expected size " + i2 + ", does not match actual size " + byteArray.length);
        } catch (Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static void writeLineData(@NonNull OutputStream outputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        writeMethodsWithInlineCaches(outputStream, dexProfileData);
        writeClasses(outputStream, dexProfileData);
        writeMethodBitmap(outputStream, dexProfileData);
    }

    private static void writeLineHeader(@NonNull OutputStream outputStream, @NonNull DexProfileData dexProfileData, @NonNull String str) throws IOException {
        Encoding.p(outputStream, Encoding.k(str));
        Encoding.p(outputStream, dexProfileData.f5747e);
        Encoding.q(outputStream, dexProfileData.f5748f);
        Encoding.q(outputStream, dexProfileData.f5745c);
        Encoding.q(outputStream, dexProfileData.f5749g);
        Encoding.n(outputStream, str);
    }

    private static void writeMethodBitmap(@NonNull OutputStream outputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        byte[] bArr = new byte[getMethodBitmapStorageSize(dexProfileData.f5749g)];
        for (Map.Entry entry : dexProfileData.f5751i.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            int iIntValue2 = ((Integer) entry.getValue()).intValue();
            if ((iIntValue2 & 2) != 0) {
                setMethodBitmapBit(bArr, 2, iIntValue, dexProfileData);
            }
            if ((iIntValue2 & 4) != 0) {
                setMethodBitmapBit(bArr, 4, iIntValue, dexProfileData);
            }
        }
        outputStream.write(bArr);
    }

    private static void writeMethodsWithInlineCaches(@NonNull OutputStream outputStream, @NonNull DexProfileData dexProfileData) throws IOException {
        int i2 = 0;
        for (Map.Entry entry : dexProfileData.f5751i.entrySet()) {
            int iIntValue = ((Integer) entry.getKey()).intValue();
            if ((((Integer) entry.getValue()).intValue() & 1) != 0) {
                Encoding.p(outputStream, iIntValue - i2);
                Encoding.p(outputStream, 0);
                i2 = iIntValue;
            }
        }
    }

    private static void writeProfileForN(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        Encoding.p(outputStream, dexProfileDataArr.length);
        for (DexProfileData dexProfileData : dexProfileDataArr) {
            String strGenerateDexKey = generateDexKey(dexProfileData.f5743a, dexProfileData.f5744b, ProfileVersion.f5765e);
            Encoding.p(outputStream, Encoding.k(strGenerateDexKey));
            Encoding.p(outputStream, dexProfileData.f5751i.size());
            Encoding.p(outputStream, dexProfileData.f5750h.length);
            Encoding.q(outputStream, dexProfileData.f5745c);
            Encoding.n(outputStream, strGenerateDexKey);
            Iterator it = dexProfileData.f5751i.keySet().iterator();
            while (it.hasNext()) {
                Encoding.p(outputStream, ((Integer) it.next()).intValue());
            }
            for (int i2 : dexProfileData.f5750h) {
                Encoding.p(outputStream, i2);
            }
        }
    }

    private static void writeProfileForO(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        Encoding.r(outputStream, dexProfileDataArr.length);
        for (DexProfileData dexProfileData : dexProfileDataArr) {
            int size = dexProfileData.f5751i.size() * 4;
            String strGenerateDexKey = generateDexKey(dexProfileData.f5743a, dexProfileData.f5744b, ProfileVersion.f5764d);
            Encoding.p(outputStream, Encoding.k(strGenerateDexKey));
            Encoding.p(outputStream, dexProfileData.f5750h.length);
            Encoding.q(outputStream, size);
            Encoding.q(outputStream, dexProfileData.f5745c);
            Encoding.n(outputStream, strGenerateDexKey);
            Iterator it = dexProfileData.f5751i.keySet().iterator();
            while (it.hasNext()) {
                Encoding.p(outputStream, ((Integer) it.next()).intValue());
                Encoding.p(outputStream, 0);
            }
            for (int i2 : dexProfileData.f5750h) {
                Encoding.p(outputStream, i2);
            }
        }
    }

    private static void writeProfileForO_MR1(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        byte[] bArrCreateCompressibleBody = createCompressibleBody(dexProfileDataArr, ProfileVersion.f5763c);
        Encoding.r(outputStream, dexProfileDataArr.length);
        Encoding.m(outputStream, bArrCreateCompressibleBody);
    }

    private static void writeProfileForP(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        byte[] bArrCreateCompressibleBody = createCompressibleBody(dexProfileDataArr, ProfileVersion.f5762b);
        Encoding.r(outputStream, dexProfileDataArr.length);
        Encoding.m(outputStream, bArrCreateCompressibleBody);
    }

    private static void writeProfileForS(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        writeProfileSections(outputStream, dexProfileDataArr);
    }

    private static void writeProfileSections(@NonNull OutputStream outputStream, @NonNull DexProfileData[] dexProfileDataArr) throws IOException {
        int length;
        ArrayList arrayList = new ArrayList(3);
        ArrayList arrayList2 = new ArrayList(3);
        arrayList.add(writeDexFileSection(dexProfileDataArr));
        arrayList.add(createCompressibleClassSection(dexProfileDataArr));
        arrayList.add(createCompressibleMethodsSection(dexProfileDataArr));
        long length2 = ProfileVersion.f5761a.length + f5754a.length + 4 + (arrayList.size() * 16);
        Encoding.q(outputStream, arrayList.size());
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            WritableFileSection writableFileSection = (WritableFileSection) arrayList.get(i2);
            Encoding.q(outputStream, writableFileSection.f5768a.getValue());
            Encoding.q(outputStream, length2);
            if (writableFileSection.f5771d) {
                byte[] bArr = writableFileSection.f5770c;
                long length3 = bArr.length;
                byte[] bArrB = Encoding.b(bArr);
                arrayList2.add(bArrB);
                Encoding.q(outputStream, bArrB.length);
                Encoding.q(outputStream, length3);
                length = bArrB.length;
            } else {
                arrayList2.add(writableFileSection.f5770c);
                Encoding.q(outputStream, writableFileSection.f5770c.length);
                Encoding.q(outputStream, 0L);
                length = writableFileSection.f5770c.length;
            }
            length2 += length;
        }
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            outputStream.write((byte[]) arrayList2.get(i3));
        }
    }
}
