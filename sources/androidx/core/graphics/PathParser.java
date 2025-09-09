package androidx.core.graphics;

import android.graphics.Path;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {

        /* renamed from: a, reason: collision with root package name */
        int f3561a;

        /* renamed from: b, reason: collision with root package name */
        boolean f3562b;

        ExtractFloatResult() {
        }
    }

    private PathParser() {
    }

    static float[] a(float[] fArr, int i2, int i3) {
        if (i2 > i3) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i2 < 0 || i2 > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i4 = i3 - i2;
        int iMin = Math.min(i4, length - i2);
        float[] fArr2 = new float[i4];
        System.arraycopy(fArr, i2, fArr2, 0, iMin);
        return fArr2;
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c2, float[] fArr) {
        arrayList.add(new PathDataNode(c2, fArr));
    }

    public static boolean canMorph(@Nullable PathDataNode[] pathDataNodeArr, @Nullable PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            if (pathDataNodeArr[i2].mType != pathDataNodeArr2[i2].mType || pathDataNodeArr[i2].mParams.length != pathDataNodeArr2[i2].mParams.length) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public static PathDataNode[] createNodesFromPathData(@NonNull String str) {
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 1;
        while (i3 < str.length()) {
            int iNextStart = nextStart(str, i3);
            String strTrim = str.substring(i2, iNextStart).trim();
            if (!strTrim.isEmpty()) {
                addNode(arrayList, strTrim.charAt(0), getFloats(strTrim));
            }
            i2 = iNextStart;
            i3 = iNextStart + 1;
        }
        if (i3 - i2 == 1 && i2 < str.length()) {
            addNode(arrayList, str.charAt(i2), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[0]);
    }

    @NonNull
    public static Path createPathFromPathData(@NonNull String str) {
        Path path = new Path();
        try {
            PathDataNode.nodesToPath(createNodesFromPathData(str), path);
            return path;
        } catch (RuntimeException e2) {
            throw new RuntimeException("Error in parsing " + str, e2);
        }
    }

    @NonNull
    public static PathDataNode[] deepCopyNodes(@NonNull PathDataNode[] pathDataNodeArr) {
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
            pathDataNodeArr2[i2] = new PathDataNode(pathDataNodeArr[i2]);
        }
        return pathDataNodeArr2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0039 A[LOOP:0: B:3:0x0007->B:24:0x0039, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static void extract(java.lang.String r8, int r9, androidx.core.graphics.PathParser.ExtractFloatResult r10) {
        /*
            r0 = 0
            r10.f3562b = r0
            r1 = r9
            r2 = r0
            r3 = r2
            r4 = r3
        L7:
            int r5 = r8.length()
            if (r1 >= r5) goto L3c
            char r5 = r8.charAt(r1)
            r6 = 32
            r7 = 1
            if (r5 == r6) goto L29
            r6 = 69
            if (r5 == r6) goto L35
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L35
            switch(r5) {
                case 44: goto L29;
                case 45: goto L2c;
                case 46: goto L22;
                default: goto L21;
            }
        L21:
            goto L33
        L22:
            if (r3 != 0) goto L27
            r2 = r0
            r3 = r7
            goto L36
        L27:
            r10.f3562b = r7
        L29:
            r2 = r0
            r4 = r7
            goto L36
        L2c:
            if (r1 == r9) goto L33
            if (r2 != 0) goto L33
            r10.f3562b = r7
            goto L29
        L33:
            r2 = r0
            goto L36
        L35:
            r2 = r7
        L36:
            if (r4 == 0) goto L39
            goto L3c
        L39:
            int r1 = r1 + 1
            goto L7
        L3c:
            r10.f3561a = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.PathParser.extract(java.lang.String, int, androidx.core.graphics.PathParser$ExtractFloatResult):void");
    }

    private static float[] getFloats(String str) {
        if (str.charAt(0) == 'z' || str.charAt(0) == 'Z') {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = str.length();
            int i2 = 1;
            int i3 = 0;
            while (i2 < length) {
                extract(str, i2, extractFloatResult);
                int i4 = extractFloatResult.f3561a;
                if (i2 < i4) {
                    fArr[i3] = Float.parseFloat(str.substring(i2, i4));
                    i3++;
                }
                i2 = extractFloatResult.f3562b ? i4 : i4 + 1;
            }
            return a(fArr, 0, i3);
        } catch (NumberFormatException e2) {
            throw new RuntimeException("error in parsing \"" + str + "\"", e2);
        }
    }

    public static void interpolatePathDataNodes(@NonNull PathDataNode[] pathDataNodeArr, float f2, @NonNull PathDataNode[] pathDataNodeArr2, @NonNull PathDataNode[] pathDataNodeArr3) {
        if (!interpolatePathDataNodes(pathDataNodeArr, pathDataNodeArr2, pathDataNodeArr3, f2)) {
            throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
        }
    }

    private static int nextStart(String str, int i2) {
        while (i2 < str.length()) {
            char cCharAt = str.charAt(i2);
            if (((cCharAt - 'A') * (cCharAt - 'Z') <= 0 || (cCharAt - 'a') * (cCharAt - 'z') <= 0) && cCharAt != 'e' && cCharAt != 'E') {
                return i2;
            }
            i2++;
        }
        return i2;
    }

    public static void nodesToPath(@NonNull PathDataNode[] pathDataNodeArr, @NonNull Path path) {
        float[] fArr = new float[6];
        char c2 = 'm';
        for (PathDataNode pathDataNode : pathDataNodeArr) {
            PathDataNode.addCommand(path, fArr, c2, pathDataNode.mType, pathDataNode.mParams);
            c2 = pathDataNode.mType;
        }
    }

    public static void updateNodes(@NonNull PathDataNode[] pathDataNodeArr, @NonNull PathDataNode[] pathDataNodeArr2) {
        for (int i2 = 0; i2 < pathDataNodeArr2.length; i2++) {
            pathDataNodeArr[i2].mType = pathDataNodeArr2[i2].mType;
            for (int i3 = 0; i3 < pathDataNodeArr2[i2].mParams.length; i3++) {
                pathDataNodeArr[i2].mParams[i3] = pathDataNodeArr2[i2].mParams[i3];
            }
        }
    }

    public static class PathDataNode {
        private final float[] mParams;
        private char mType;

        PathDataNode(char c2, float[] fArr) {
            this.mType = c2;
            this.mParams = fArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public static void addCommand(Path path, float[] fArr, char c2, char c3, float[] fArr2) {
            int i2;
            int i3;
            int i4;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            char c4 = c3;
            boolean z2 = false;
            float f10 = fArr[0];
            float f11 = fArr[1];
            float f12 = fArr[2];
            float f13 = fArr[3];
            float f14 = fArr[4];
            float f15 = fArr[5];
            switch (c4) {
                case 'A':
                case 'a':
                    i2 = 7;
                    i3 = i2;
                    break;
                case 'C':
                case 'c':
                    i2 = 6;
                    i3 = i2;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i3 = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                default:
                    i3 = 2;
                    break;
                case 'Q':
                case 'S':
                case 'q':
                case 's':
                    i3 = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f14, f15);
                    f10 = f14;
                    f12 = f10;
                    f11 = f15;
                    f13 = f11;
                    i3 = 2;
                    break;
            }
            float f16 = f10;
            float f17 = f11;
            float f18 = f14;
            float f19 = f15;
            int i5 = 0;
            char c5 = c2;
            while (i5 < fArr2.length) {
                if (c4 != 'A') {
                    if (c4 == 'C') {
                        i4 = i5;
                        int i6 = i4 + 2;
                        int i7 = i4 + 3;
                        int i8 = i4 + 4;
                        int i9 = i4 + 5;
                        path.cubicTo(fArr2[i4], fArr2[i4 + 1], fArr2[i6], fArr2[i7], fArr2[i8], fArr2[i9]);
                        f16 = fArr2[i8];
                        float f20 = fArr2[i9];
                        float f21 = fArr2[i6];
                        float f22 = fArr2[i7];
                        f17 = f20;
                        f13 = f22;
                        f12 = f21;
                    } else if (c4 == 'H') {
                        i4 = i5;
                        path.lineTo(fArr2[i4], f17);
                        f16 = fArr2[i4];
                    } else if (c4 == 'Q') {
                        i4 = i5;
                        int i10 = i4 + 1;
                        int i11 = i4 + 2;
                        int i12 = i4 + 3;
                        path.quadTo(fArr2[i4], fArr2[i10], fArr2[i11], fArr2[i12]);
                        float f23 = fArr2[i4];
                        float f24 = fArr2[i10];
                        f16 = fArr2[i11];
                        f17 = fArr2[i12];
                        f12 = f23;
                        f13 = f24;
                    } else if (c4 == 'V') {
                        i4 = i5;
                        path.lineTo(f16, fArr2[i4]);
                        f17 = fArr2[i4];
                    } else if (c4 != 'a') {
                        if (c4 != 'c') {
                            if (c4 == 'h') {
                                path.rLineTo(fArr2[i5], 0.0f);
                                f16 += fArr2[i5];
                            } else if (c4 != 'q') {
                                if (c4 == 'v') {
                                    path.rLineTo(0.0f, fArr2[i5]);
                                    f5 = fArr2[i5];
                                } else if (c4 == 'L') {
                                    int i13 = i5 + 1;
                                    path.lineTo(fArr2[i5], fArr2[i13]);
                                    f16 = fArr2[i5];
                                    f17 = fArr2[i13];
                                } else if (c4 == 'M') {
                                    f16 = fArr2[i5];
                                    f17 = fArr2[i5 + 1];
                                    if (i5 > 0) {
                                        path.lineTo(f16, f17);
                                    } else {
                                        path.moveTo(f16, f17);
                                        i4 = i5;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c4 == 'S') {
                                    if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    float f25 = f17;
                                    float f26 = f16;
                                    int i14 = i5 + 1;
                                    int i15 = i5 + 2;
                                    int i16 = i5 + 3;
                                    path.cubicTo(f26, f25, fArr2[i5], fArr2[i14], fArr2[i15], fArr2[i16]);
                                    f2 = fArr2[i5];
                                    f3 = fArr2[i14];
                                    f16 = fArr2[i15];
                                    f17 = fArr2[i16];
                                    f12 = f2;
                                    f13 = f3;
                                } else if (c4 == 'T') {
                                    if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                                        f16 = (f16 * 2.0f) - f12;
                                        f17 = (f17 * 2.0f) - f13;
                                    }
                                    int i17 = i5 + 1;
                                    path.quadTo(f16, f17, fArr2[i5], fArr2[i17]);
                                    i4 = i5;
                                    f13 = f17;
                                    f12 = f16;
                                    f16 = fArr2[i5];
                                    f17 = fArr2[i17];
                                } else if (c4 == 'l') {
                                    int i18 = i5 + 1;
                                    path.rLineTo(fArr2[i5], fArr2[i18]);
                                    f16 += fArr2[i5];
                                    f5 = fArr2[i18];
                                } else if (c4 == 'm') {
                                    float f27 = fArr2[i5];
                                    f16 += f27;
                                    float f28 = fArr2[i5 + 1];
                                    f17 += f28;
                                    if (i5 > 0) {
                                        path.rLineTo(f27, f28);
                                    } else {
                                        path.rMoveTo(f27, f28);
                                        i4 = i5;
                                        f19 = f17;
                                        f18 = f16;
                                    }
                                } else if (c4 == 's') {
                                    if (c5 == 'c' || c5 == 's' || c5 == 'C' || c5 == 'S') {
                                        float f29 = f16 - f12;
                                        f6 = f17 - f13;
                                        f7 = f29;
                                    } else {
                                        f7 = 0.0f;
                                        f6 = 0.0f;
                                    }
                                    int i19 = i5 + 1;
                                    int i20 = i5 + 2;
                                    int i21 = i5 + 3;
                                    path.rCubicTo(f7, f6, fArr2[i5], fArr2[i19], fArr2[i20], fArr2[i21]);
                                    f2 = fArr2[i5] + f16;
                                    f3 = fArr2[i19] + f17;
                                    f16 += fArr2[i20];
                                    f4 = fArr2[i21];
                                } else if (c4 == 't') {
                                    if (c5 == 'q' || c5 == 't' || c5 == 'Q' || c5 == 'T') {
                                        f8 = f16 - f12;
                                        f9 = f17 - f13;
                                    } else {
                                        f9 = 0.0f;
                                        f8 = 0.0f;
                                    }
                                    int i22 = i5 + 1;
                                    path.rQuadTo(f8, f9, fArr2[i5], fArr2[i22]);
                                    float f30 = f8 + f16;
                                    float f31 = f9 + f17;
                                    f16 += fArr2[i5];
                                    f17 += fArr2[i22];
                                    f13 = f31;
                                    f12 = f30;
                                }
                                f17 += f5;
                            } else {
                                int i23 = i5 + 1;
                                int i24 = i5 + 2;
                                int i25 = i5 + 3;
                                path.rQuadTo(fArr2[i5], fArr2[i23], fArr2[i24], fArr2[i25]);
                                f2 = fArr2[i5] + f16;
                                f3 = fArr2[i23] + f17;
                                f16 += fArr2[i24];
                                f4 = fArr2[i25];
                            }
                            i4 = i5;
                        } else {
                            int i26 = i5 + 2;
                            int i27 = i5 + 3;
                            int i28 = i5 + 4;
                            int i29 = i5 + 5;
                            path.rCubicTo(fArr2[i5], fArr2[i5 + 1], fArr2[i26], fArr2[i27], fArr2[i28], fArr2[i29]);
                            f2 = fArr2[i26] + f16;
                            f3 = fArr2[i27] + f17;
                            f16 += fArr2[i28];
                            f4 = fArr2[i29];
                        }
                        f17 += f4;
                        f12 = f2;
                        f13 = f3;
                        i4 = i5;
                    } else {
                        int i30 = i5 + 5;
                        int i31 = i5 + 6;
                        i4 = i5;
                        drawArc(path, f16, f17, fArr2[i30] + f16, fArr2[i31] + f17, fArr2[i5], fArr2[i5 + 1], fArr2[i5 + 2], fArr2[i5 + 3] != 0.0f, fArr2[i5 + 4] != 0.0f);
                        f16 += fArr2[i30];
                        f17 += fArr2[i31];
                    }
                    i5 = i4 + i3;
                    c5 = c3;
                    c4 = c5;
                    z2 = false;
                } else {
                    i4 = i5;
                    int i32 = i4 + 5;
                    int i33 = i4 + 6;
                    drawArc(path, f16, f17, fArr2[i32], fArr2[i33], fArr2[i4], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f, fArr2[i4 + 4] != 0.0f);
                    f16 = fArr2[i32];
                    f17 = fArr2[i33];
                }
                f13 = f17;
                f12 = f16;
                i5 = i4 + i3;
                c5 = c3;
                c4 = c5;
                z2 = false;
            }
            fArr[z2 ? 1 : 0] = f16;
            fArr[1] = f17;
            fArr[2] = f12;
            fArr[3] = f13;
            fArr[4] = f18;
            fArr[5] = f19;
        }

        private static void arcToBezier(Path path, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10) {
            double d11 = d4;
            int iCeil = (int) Math.ceil(Math.abs((d10 * 4.0d) / 3.141592653589793d));
            double dCos = Math.cos(d8);
            double dSin = Math.sin(d8);
            double dCos2 = Math.cos(d9);
            double dSin2 = Math.sin(d9);
            double d12 = -d11;
            double d13 = d12 * dCos;
            double d14 = d5 * dSin;
            double d15 = (d13 * dSin2) - (d14 * dCos2);
            double d16 = d12 * dSin;
            double d17 = d5 * dCos;
            double d18 = (dSin2 * d16) + (dCos2 * d17);
            double d19 = d10 / iCeil;
            double d20 = d18;
            double d21 = d15;
            int i2 = 0;
            double d22 = d6;
            double d23 = d7;
            double d24 = d9;
            while (i2 < iCeil) {
                double d25 = d24 + d19;
                double dSin3 = Math.sin(d25);
                double dCos3 = Math.cos(d25);
                double d26 = (d2 + ((d11 * dCos) * dCos3)) - (d14 * dSin3);
                double d27 = d3 + (d11 * dSin * dCos3) + (d17 * dSin3);
                double d28 = (d13 * dSin3) - (d14 * dCos3);
                double d29 = (dSin3 * d16) + (dCos3 * d17);
                double d30 = d25 - d24;
                double dTan = Math.tan(d30 / 2.0d);
                double dSin4 = (Math.sin(d30) * (Math.sqrt(((dTan * 3.0d) * dTan) + 4.0d) - 1.0d)) / 3.0d;
                double d31 = d22 + (d21 * dSin4);
                path.rLineTo(0.0f, 0.0f);
                path.cubicTo((float) d31, (float) (d23 + (d20 * dSin4)), (float) (d26 - (dSin4 * d28)), (float) (d27 - (dSin4 * d29)), (float) d26, (float) d27);
                i2++;
                d19 = d19;
                dSin = dSin;
                d22 = d26;
                d16 = d16;
                dCos = dCos;
                d24 = d25;
                d20 = d29;
                d21 = d28;
                iCeil = iCeil;
                d23 = d27;
                d11 = d4;
            }
        }

        private static void drawArc(Path path, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z2, boolean z3) {
            double d2;
            double d3;
            double radians = Math.toRadians(f8);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d4 = f2;
            double d5 = d4 * dCos;
            double d6 = f3;
            double d7 = f6;
            double d8 = (d5 + (d6 * dSin)) / d7;
            double d9 = ((-f2) * dSin) + (d6 * dCos);
            double d10 = f7;
            double d11 = d9 / d10;
            double d12 = f5;
            double d13 = ((f4 * dCos) + (d12 * dSin)) / d7;
            double d14 = (((-f4) * dSin) + (d12 * dCos)) / d10;
            double d15 = d8 - d13;
            double d16 = d11 - d14;
            double d17 = (d8 + d13) / 2.0d;
            double d18 = (d11 + d14) / 2.0d;
            double d19 = (d15 * d15) + (d16 * d16);
            if (d19 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d20 = (1.0d / d19) - 0.25d;
            if (d20 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d19);
                float fSqrt = (float) (Math.sqrt(d19) / 1.99999d);
                drawArc(path, f2, f3, f4, f5, f6 * fSqrt, f7 * fSqrt, f8, z2, z3);
                return;
            }
            double dSqrt = Math.sqrt(d20);
            double d21 = d15 * dSqrt;
            double d22 = dSqrt * d16;
            if (z2 == z3) {
                d2 = d17 - d22;
                d3 = d18 + d21;
            } else {
                d2 = d17 + d22;
                d3 = d18 - d21;
            }
            double dAtan2 = Math.atan2(d11 - d3, d8 - d2);
            double dAtan22 = Math.atan2(d14 - d3, d13 - d2) - dAtan2;
            if (z3 != (dAtan22 >= 0.0d)) {
                dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
            }
            double d23 = d2 * d7;
            double d24 = d3 * d10;
            arcToBezier(path, (d23 * dCos) - (d24 * dSin), (d23 * dSin) + (d24 * dCos), d7, d10, d4, d6, radians, dAtan2, dAtan22);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        @Deprecated
        public static void nodesToPath(@NonNull PathDataNode[] pathDataNodeArr, @NonNull Path path) {
            PathParser.nodesToPath(pathDataNodeArr, path);
        }

        @NonNull
        public float[] getParams() {
            return this.mParams;
        }

        public char getType() {
            return this.mType;
        }

        public void interpolatePathDataNode(@NonNull PathDataNode pathDataNode, @NonNull PathDataNode pathDataNode2, float f2) {
            this.mType = pathDataNode.mType;
            int i2 = 0;
            while (true) {
                float[] fArr = pathDataNode.mParams;
                if (i2 >= fArr.length) {
                    return;
                }
                this.mParams[i2] = (fArr[i2] * (1.0f - f2)) + (pathDataNode2.mParams[i2] * f2);
                i2++;
            }
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.mType = pathDataNode.mType;
            float[] fArr = pathDataNode.mParams;
            this.mParams = PathParser.a(fArr, 0, fArr.length);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Deprecated
    public static boolean interpolatePathDataNodes(@NonNull PathDataNode[] pathDataNodeArr, @NonNull PathDataNode[] pathDataNodeArr2, @NonNull PathDataNode[] pathDataNodeArr3, float f2) {
        if (pathDataNodeArr.length == pathDataNodeArr2.length && pathDataNodeArr2.length == pathDataNodeArr3.length) {
            if (!canMorph(pathDataNodeArr2, pathDataNodeArr3)) {
                return false;
            }
            for (int i2 = 0; i2 < pathDataNodeArr.length; i2++) {
                pathDataNodeArr[i2].interpolatePathDataNode(pathDataNodeArr2[i2], pathDataNodeArr3[i2], f2);
            }
            return true;
        }
        throw new IllegalArgumentException("The nodes to be interpolated and resulting nodes must have the same length");
    }
}
