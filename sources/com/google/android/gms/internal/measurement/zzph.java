package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;

/* loaded from: classes3.dex */
final class zzph extends zzpi {
    zzph() {
    }

    @Override // com.google.android.gms.internal.measurement.zzpi
    final int a(int i2, byte[] bArr, int i3, int i4) {
        while (i3 < i4 && bArr[i3] >= 0) {
            i3++;
        }
        if (i3 >= i4) {
            return 0;
        }
        while (i3 < i4) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            if (b2 < 0) {
                if (b2 < -32) {
                    if (i5 >= i4) {
                        return b2;
                    }
                    if (b2 >= -62) {
                        i3 += 2;
                        if (bArr[i5] > -65) {
                        }
                    }
                    return -1;
                }
                if (b2 >= -16) {
                    if (i5 >= i4 - 2) {
                        return zzpg.c(bArr, i5, i4);
                    }
                    int i6 = i3 + 2;
                    byte b3 = bArr[i5];
                    if (b3 <= -65 && (((b2 << Ascii.FS) + (b3 + 112)) >> 30) == 0) {
                        int i7 = i3 + 3;
                        if (bArr[i6] <= -65) {
                            i3 += 4;
                            if (bArr[i7] > -65) {
                            }
                        }
                    }
                    return -1;
                }
                if (i5 >= i4 - 1) {
                    return zzpg.c(bArr, i5, i4);
                }
                int i8 = i3 + 2;
                byte b4 = bArr[i5];
                if (b4 <= -65 && ((b2 != -32 || b4 >= -96) && (b2 != -19 || b4 < -96))) {
                    i3 += 3;
                    if (bArr[i8] > -65) {
                    }
                }
                return -1;
            }
            i3 = i5;
        }
        return 0;
    }

    @Override // com.google.android.gms.internal.measurement.zzpi
    final int b(CharSequence charSequence, byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        char cCharAt;
        int length = charSequence.length();
        int i6 = i3 + i2;
        int i7 = 0;
        while (i7 < length && (i5 = i7 + i2) < i6 && (cCharAt = charSequence.charAt(i7)) < 128) {
            bArr[i5] = (byte) cCharAt;
            i7++;
        }
        if (i7 == length) {
            return i2 + length;
        }
        int i8 = i2 + i7;
        while (i7 < length) {
            char cCharAt2 = charSequence.charAt(i7);
            if (cCharAt2 < 128 && i8 < i6) {
                bArr[i8] = (byte) cCharAt2;
                i8++;
            } else if (cCharAt2 < 2048 && i8 <= i6 - 2) {
                int i9 = i8 + 1;
                bArr[i8] = (byte) ((cCharAt2 >>> 6) | 960);
                i8 += 2;
                bArr[i9] = (byte) ((cCharAt2 & '?') | 128);
            } else {
                if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || i8 > i6 - 3) {
                    if (i8 > i6 - 4) {
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i4 = i7 + 1) == charSequence.length() || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i4)))) {
                            throw new zzpk(i7, length);
                        }
                        throw new ArrayIndexOutOfBoundsException("Failed writing " + cCharAt2 + " at index " + i8);
                    }
                    int i10 = i7 + 1;
                    if (i10 != charSequence.length()) {
                        char cCharAt3 = charSequence.charAt(i10);
                        if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                            int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                            bArr[i8] = (byte) ((codePoint >>> 18) | 240);
                            bArr[i8 + 1] = (byte) (((codePoint >>> 12) & 63) | 128);
                            int i11 = i8 + 3;
                            bArr[i8 + 2] = (byte) (((codePoint >>> 6) & 63) | 128);
                            i8 += 4;
                            bArr[i11] = (byte) ((codePoint & 63) | 128);
                            i7 = i10;
                        } else {
                            i7 = i10;
                        }
                    }
                    throw new zzpk(i7 - 1, length);
                }
                bArr[i8] = (byte) ((cCharAt2 >>> '\f') | 480);
                int i12 = i8 + 2;
                bArr[i8 + 1] = (byte) (((cCharAt2 >>> 6) & 63) | 128);
                i8 += 3;
                bArr[i12] = (byte) ((cCharAt2 & '?') | 128);
            }
            i7++;
        }
        return i8;
    }

    @Override // com.google.android.gms.internal.measurement.zzpi
    final String c(byte[] bArr, int i2, int i3) throws zzme {
        if ((i2 | i3 | ((bArr.length - i2) - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        int i4 = i2 + i3;
        char[] cArr = new char[i3];
        int i5 = 0;
        while (i2 < i4) {
            byte b2 = bArr[i2];
            if (b2 < 0) {
                break;
            }
            i2++;
            zzpf.d(b2, cArr, i5);
            i5++;
        }
        int i6 = i5;
        while (i2 < i4) {
            int i7 = i2 + 1;
            byte b3 = bArr[i2];
            if (b3 >= 0) {
                int i8 = i6 + 1;
                zzpf.d(b3, cArr, i6);
                while (i7 < i4) {
                    byte b4 = bArr[i7];
                    if (b4 < 0) {
                        break;
                    }
                    i7++;
                    zzpf.d(b4, cArr, i8);
                    i8++;
                }
                i6 = i8;
                i2 = i7;
            } else if (b3 < -32) {
                if (i7 >= i4) {
                    throw zzme.zzd();
                }
                i2 += 2;
                zzpf.c(b3, bArr[i7], cArr, i6);
                i6++;
            } else if (b3 < -16) {
                if (i7 >= i4 - 1) {
                    throw zzme.zzd();
                }
                int i9 = i2 + 2;
                i2 += 3;
                zzpf.b(b3, bArr[i7], bArr[i9], cArr, i6);
                i6++;
            } else {
                if (i7 >= i4 - 2) {
                    throw zzme.zzd();
                }
                byte b5 = bArr[i7];
                int i10 = i2 + 3;
                byte b6 = bArr[i2 + 2];
                i2 += 4;
                zzpf.a(b3, b5, b6, bArr[i10], cArr, i6);
                i6 += 2;
            }
        }
        return new String(cArr, 0, i6);
    }
}
