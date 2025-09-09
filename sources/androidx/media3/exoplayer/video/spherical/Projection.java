package androidx.media3.exoplayer.video.spherical;

import androidx.media3.common.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* loaded from: classes2.dex */
final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshArr) {
            this.subMeshes = subMeshArr;
        }

        public SubMesh getSubMesh(int i2) {
            return this.subMeshes[i2];
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int i2, float[] fArr, float[] fArr2, int i3) {
            this.textureId = i2;
            Assertions.checkArgument(((long) fArr.length) * 2 == ((long) fArr2.length) * 3);
            this.vertices = fArr;
            this.textureCoords = fArr2;
            this.mode = i3;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public Projection(Mesh mesh, int i2) {
        this(mesh, mesh, i2);
    }

    public static Projection createEquirectangular(int i2) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, i2);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i2) {
        this.leftMesh = mesh;
        this.rightMesh = mesh2;
        this.stereoMode = i2;
        this.singleMesh = mesh == mesh2;
    }

    public static Projection createEquirectangular(float f2, int i2, int i3, float f3, float f4, int i4) {
        float f5;
        float f6;
        int i5;
        int i6;
        float[] fArr;
        int i7 = i2;
        int i8 = 1;
        Assertions.checkArgument(f2 > 0.0f);
        Assertions.checkArgument(i7 >= 1);
        Assertions.checkArgument(i3 >= 1);
        Assertions.checkArgument(f3 > 0.0f && f3 <= 180.0f);
        Assertions.checkArgument(f4 > 0.0f && f4 <= 360.0f);
        float radians = (float) Math.toRadians(f3);
        float radians2 = (float) Math.toRadians(f4);
        float f7 = radians / i7;
        float f8 = radians2 / i3;
        int i9 = i3 + 1;
        int i10 = ((i9 * 2) + 2) * i7;
        float[] fArr2 = new float[i10 * 3];
        float[] fArr3 = new float[i10 * 2];
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        while (i11 < i7) {
            float f9 = radians / 2.0f;
            float f10 = (i11 * f7) - f9;
            int i14 = i11 + 1;
            float f11 = (i14 * f7) - f9;
            int i15 = 0;
            while (i15 < i9) {
                int i16 = i14;
                float f12 = f10;
                int i17 = i13;
                int i18 = 0;
                int i19 = 2;
                while (i18 < i19) {
                    if (i18 == 0) {
                        f5 = f12;
                        f6 = f11;
                    } else {
                        f5 = f11;
                        f6 = f5;
                    }
                    float f13 = i15 * f8;
                    float f14 = f8;
                    float f15 = radians;
                    double d2 = f2;
                    int i20 = i15;
                    float f16 = f7;
                    double d3 = (f13 + 3.1415927f) - (radians2 / 2.0f);
                    double d4 = f5;
                    int i21 = i9;
                    float[] fArr4 = fArr3;
                    fArr2[i12] = -((float) (Math.sin(d3) * d2 * Math.cos(d4)));
                    float f17 = radians2;
                    int i22 = i18;
                    fArr2[i12 + 1] = (float) (d2 * Math.sin(d4));
                    int i23 = i12 + 3;
                    fArr2[i12 + 2] = (float) (d2 * Math.cos(d3) * Math.cos(d4));
                    fArr4[i17] = f13 / f17;
                    int i24 = i17 + 2;
                    fArr4[i17 + 1] = ((i11 + i22) * f16) / f15;
                    if (i20 == 0 && i22 == 0) {
                        i6 = i22;
                        i5 = i20;
                    } else {
                        i5 = i20;
                        i6 = i22;
                        if (i5 != i3 || i6 != 1) {
                            fArr = fArr4;
                            i19 = 2;
                            i12 = i23;
                            i17 = i24;
                        }
                        i18 = i6 + 1;
                        fArr3 = fArr;
                        radians2 = f17;
                        f11 = f6;
                        f8 = f14;
                        f7 = f16;
                        i9 = i21;
                        i15 = i5;
                        radians = f15;
                    }
                    System.arraycopy(fArr2, i12, fArr2, i23, 3);
                    i12 += 6;
                    fArr = fArr4;
                    i19 = 2;
                    System.arraycopy(fArr, i17, fArr, i24, 2);
                    i17 += 4;
                    i18 = i6 + 1;
                    fArr3 = fArr;
                    radians2 = f17;
                    f11 = f6;
                    f8 = f14;
                    f7 = f16;
                    i9 = i21;
                    i15 = i5;
                    radians = f15;
                }
                i15++;
                i14 = i16;
                f10 = f12;
                i13 = i17;
                radians2 = radians2;
                f8 = f8;
                radians = radians;
                i9 = i9;
            }
            i7 = i2;
            i11 = i14;
            i8 = 1;
        }
        int i25 = i8;
        SubMesh[] subMeshArr = new SubMesh[i25];
        subMeshArr[0] = new SubMesh(0, fArr2, fArr3, i25);
        return new Projection(new Mesh(subMeshArr), i4);
    }
}
