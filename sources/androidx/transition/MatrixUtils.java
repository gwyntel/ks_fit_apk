package androidx.transition;

import android.graphics.Matrix;
import android.graphics.RectF;

/* loaded from: classes2.dex */
class MatrixUtils {

    /* renamed from: a, reason: collision with root package name */
    static final Matrix f6302a = new Matrix() { // from class: androidx.transition.MatrixUtils.1
        void a() {
            throw new IllegalStateException("Matrix can not be modified");
        }

        @Override // android.graphics.Matrix
        public boolean postConcat(Matrix matrix) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postRotate(float f2, float f3, float f4) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postScale(float f2, float f3, float f4, float f5) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postSkew(float f2, float f3, float f4, float f5) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postTranslate(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preConcat(Matrix matrix) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preRotate(float f2, float f3, float f4) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preScale(float f2, float f3, float f4, float f5) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preSkew(float f2, float f3, float f4, float f5) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preTranslate(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public void reset() {
            a();
        }

        @Override // android.graphics.Matrix
        public void set(Matrix matrix) {
            a();
        }

        @Override // android.graphics.Matrix
        public boolean setConcat(Matrix matrix, Matrix matrix2) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean setPolyToPoly(float[] fArr, int i2, float[] fArr2, int i3, int i4) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean setRectToRect(RectF rectF, RectF rectF2, Matrix.ScaleToFit scaleToFit) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public void setRotate(float f2, float f3, float f4) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setScale(float f2, float f3, float f4, float f5) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setSinCos(float f2, float f3, float f4, float f5) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setSkew(float f2, float f3, float f4, float f5) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setTranslate(float f2, float f3) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setValues(float[] fArr) {
            a();
        }

        @Override // android.graphics.Matrix
        public boolean postRotate(float f2) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postScale(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean postSkew(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preRotate(float f2) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preScale(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public boolean preSkew(float f2, float f3) {
            a();
            return false;
        }

        @Override // android.graphics.Matrix
        public void setRotate(float f2) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setScale(float f2, float f3) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setSinCos(float f2, float f3) {
            a();
        }

        @Override // android.graphics.Matrix
        public void setSkew(float f2, float f3) {
            a();
        }
    };

    private MatrixUtils() {
    }
}
