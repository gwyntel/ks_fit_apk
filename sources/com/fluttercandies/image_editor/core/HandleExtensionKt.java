package com.fluttercandies.image_editor.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import com.facebook.appevents.internal.ViewHierarchyConstants;
import com.fluttercandies.image_editor.option.draw.ArcToPathPart;
import com.fluttercandies.image_editor.option.draw.BezierPathPart;
import com.fluttercandies.image_editor.option.draw.DrawOption;
import com.fluttercandies.image_editor.option.draw.DrawPart;
import com.fluttercandies.image_editor.option.draw.LineDrawPart;
import com.fluttercandies.image_editor.option.draw.LineToPathPart;
import com.fluttercandies.image_editor.option.draw.MovePathPart;
import com.fluttercandies.image_editor.option.draw.OvalDrawPart;
import com.fluttercandies.image_editor.option.draw.PathDrawPart;
import com.fluttercandies.image_editor.option.draw.PathPart;
import com.fluttercandies.image_editor.option.draw.PointsDrawPart;
import com.fluttercandies.image_editor.option.draw.RectDrawPart;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001a\u0016\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0007\u001a\u0016\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\t\u001a\u0016\u0010\n\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u000b\u001a\u0016\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\r\u001a\u001a\u0010\u000e\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011\u001a\u0012\u0010\u0013\u001a\u00020\u000f*\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015¨\u0006\u0016"}, d2 = {"drawLine", "", "canvas", "Landroid/graphics/Canvas;", "drawPart", "Lcom/fluttercandies/image_editor/option/draw/LineDrawPart;", "drawOval", "Lcom/fluttercandies/image_editor/option/draw/OvalDrawPart;", "drawPath", "Lcom/fluttercandies/image_editor/option/draw/PathDrawPart;", "drawPoints", "Lcom/fluttercandies/image_editor/option/draw/PointsDrawPart;", "drawRect", "Lcom/fluttercandies/image_editor/option/draw/RectDrawPart;", "createNewBitmap", "Landroid/graphics/Bitmap;", ViewHierarchyConstants.DIMENSION_WIDTH_KEY, "", ViewHierarchyConstants.DIMENSION_HEIGHT_KEY, "draw", "option", "Lcom/fluttercandies/image_editor/option/draw/DrawOption;", "image_editor_common_release"}, k = 2, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final class HandleExtensionKt {
    @NotNull
    public static final Bitmap createNewBitmap(@NotNull Bitmap bitmap, int i2, int i3) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Bitmap.Config config = bitmap.getConfig();
        Intrinsics.checkNotNullExpressionValue(config, "getConfig(...)");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, config);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        return bitmapCreateBitmap;
    }

    @NotNull
    public static final Bitmap draw(@NotNull Bitmap bitmap, @NotNull DrawOption option) {
        Intrinsics.checkNotNullParameter(bitmap, "<this>");
        Intrinsics.checkNotNullParameter(option, "option");
        Bitmap bitmapCreateNewBitmap = createNewBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
        Canvas canvas = new Canvas(bitmapCreateNewBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        for (DrawPart drawPart : option.getDrawPart()) {
            if (drawPart instanceof LineDrawPart) {
                drawLine(canvas, (LineDrawPart) drawPart);
            } else if (drawPart instanceof RectDrawPart) {
                drawRect(canvas, (RectDrawPart) drawPart);
            } else if (drawPart instanceof OvalDrawPart) {
                drawOval(canvas, (OvalDrawPart) drawPart);
            } else if (drawPart instanceof PointsDrawPart) {
                drawPoints(canvas, (PointsDrawPart) drawPart);
            } else if (drawPart instanceof PathDrawPart) {
                drawPath(canvas, (PathDrawPart) drawPart);
            }
        }
        return bitmapCreateNewBitmap;
    }

    private static final void drawLine(Canvas canvas, LineDrawPart lineDrawPart) {
        canvas.drawLine(lineDrawPart.getStart().x, lineDrawPart.getStart().y, lineDrawPart.getEnd().x, lineDrawPart.getEnd().y, lineDrawPart.getPaint());
    }

    public static final void drawOval(@NotNull Canvas canvas, @NotNull OvalDrawPart drawPart) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(drawPart, "drawPart");
        canvas.drawOval(new RectF(drawPart.getRect()), drawPart.getPaint());
    }

    public static final void drawPath(@NotNull Canvas canvas, @NotNull PathDrawPart drawPart) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(drawPart, "drawPart");
        Path path = new Path();
        boolean autoClose = drawPart.getAutoClose();
        for (PathPart pathPart : drawPart.getPaths()) {
            if (pathPart instanceof MovePathPart) {
                MovePathPart movePathPart = (MovePathPart) pathPart;
                path.moveTo(movePathPart.getOffset().x, movePathPart.getOffset().y);
            } else if (pathPart instanceof LineToPathPart) {
                LineToPathPart lineToPathPart = (LineToPathPart) pathPart;
                path.lineTo(lineToPathPart.getOffset().x, lineToPathPart.getOffset().y);
            } else if (pathPart instanceof ArcToPathPart) {
                ArcToPathPart arcToPathPart = (ArcToPathPart) pathPart;
                path.arcTo(new RectF(arcToPathPart.getRect()), arcToPathPart.getStart().floatValue(), arcToPathPart.getSweep().floatValue(), arcToPathPart.getUseCenter());
            } else if (pathPart instanceof BezierPathPart) {
                BezierPathPart bezierPathPart = (BezierPathPart) pathPart;
                if (bezierPathPart.getKind() == 2) {
                    path.quadTo(bezierPathPart.getControl1().x, bezierPathPart.getControl1().y, bezierPathPart.getTarget().x, bezierPathPart.getTarget().y);
                } else if (bezierPathPart.getKind() == 3) {
                    float f2 = bezierPathPart.getControl1().x;
                    float f3 = bezierPathPart.getControl1().y;
                    Intrinsics.checkNotNull(bezierPathPart.getControl2());
                    path.cubicTo(f2, f3, r4.x, bezierPathPart.getControl2().y, bezierPathPart.getTarget().x, bezierPathPart.getTarget().y);
                }
            }
        }
        if (autoClose) {
            path.close();
        }
        canvas.drawPath(path, drawPart.getPaint());
    }

    public static final void drawPoints(@NotNull Canvas canvas, @NotNull PointsDrawPart drawPart) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(drawPart, "drawPart");
        List<Point> offsets = drawPart.getOffsets();
        Paint paint = drawPart.getPaint();
        for (Point point : offsets) {
            canvas.drawPoint(point.x, point.y, paint);
        }
    }

    public static final void drawRect(@NotNull Canvas canvas, @NotNull RectDrawPart drawPart) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(drawPart, "drawPart");
        canvas.drawRect(drawPart.getRect(), drawPart.getPaint());
    }
}
