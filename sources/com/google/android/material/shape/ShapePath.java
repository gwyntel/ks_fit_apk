package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.internal.Experimental;
import java.util.ArrayList;
import java.util.List;

@Experimental("The shapes API is currently experimental and subject to change")
/* loaded from: classes3.dex */
public class ShapePath {
    public float endX;
    public float endY;
    private final List<PathOperation> operations = new ArrayList();
    public float startX;
    public float startY;

    public static class PathArcOperation extends PathOperation {
        private static final RectF rectF = new RectF();
        public float bottom;
        public float left;
        public float right;
        public float startAngle;
        public float sweepAngle;

        /* renamed from: top, reason: collision with root package name */
        public float f13529top;

        public PathArcOperation(float f2, float f3, float f4, float f5) {
            this.left = f2;
            this.f13529top = f3;
            this.right = f4;
            this.bottom = f5;
        }

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.f13532a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            RectF rectF2 = rectF;
            rectF2.set(this.left, this.f13529top, this.right, this.bottom);
            path.arcTo(rectF2, this.startAngle, this.sweepAngle, false);
            path.transform(matrix);
        }
    }

    public static class PathLineOperation extends PathOperation {

        /* renamed from: x, reason: collision with root package name */
        private float f13530x;

        /* renamed from: y, reason: collision with root package name */
        private float f13531y;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.f13532a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.f13530x, this.f13531y);
            path.transform(matrix);
        }
    }

    public static abstract class PathOperation {

        /* renamed from: a, reason: collision with root package name */
        protected final Matrix f13532a = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    public static class PathQuadOperation extends PathOperation {
        public float controlX;
        public float controlY;
        public float endX;
        public float endY;

        @Override // com.google.android.material.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.f13532a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.quadTo(this.controlX, this.controlY, this.endX, this.endY);
            path.transform(matrix);
        }
    }

    public ShapePath() {
        reset(0.0f, 0.0f);
    }

    public void addArc(float f2, float f3, float f4, float f5, float f6, float f7) {
        PathArcOperation pathArcOperation = new PathArcOperation(f2, f3, f4, f5);
        pathArcOperation.startAngle = f6;
        pathArcOperation.sweepAngle = f7;
        this.operations.add(pathArcOperation);
        double d2 = f6 + f7;
        this.endX = ((f2 + f4) * 0.5f) + (((f4 - f2) / 2.0f) * ((float) Math.cos(Math.toRadians(d2))));
        this.endY = ((f3 + f5) * 0.5f) + (((f5 - f3) / 2.0f) * ((float) Math.sin(Math.toRadians(d2))));
    }

    public void applyToPath(Matrix matrix, Path path) {
        int size = this.operations.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.operations.get(i2).applyToPath(matrix, path);
        }
    }

    public void lineTo(float f2, float f3) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.f13530x = f2;
        pathLineOperation.f13531y = f3;
        this.operations.add(pathLineOperation);
        this.endX = f2;
        this.endY = f3;
    }

    public void quadToPoint(float f2, float f3, float f4, float f5) {
        PathQuadOperation pathQuadOperation = new PathQuadOperation();
        pathQuadOperation.controlX = f2;
        pathQuadOperation.controlY = f3;
        pathQuadOperation.endX = f4;
        pathQuadOperation.endY = f5;
        this.operations.add(pathQuadOperation);
        this.endX = f4;
        this.endY = f5;
    }

    public void reset(float f2, float f3) {
        this.startX = f2;
        this.startY = f3;
        this.endX = f2;
        this.endY = f3;
        this.operations.clear();
    }

    public ShapePath(float f2, float f3) {
        reset(f2, f3);
    }
}
