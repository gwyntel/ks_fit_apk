package androidx.heifwriter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes.dex */
public class EglRectBlt {
    private static final FloatBuffer FULL_RECTANGLE_BUF;
    private static final float[] FULL_RECTANGLE_COORDS;
    private static final int SIZEOF_FLOAT = 4;
    private Texture2dProgram mProgram;
    private final FloatBuffer mTexCoordArray;
    private final float[] mTexCoords;
    private final int mTexHeight;
    private final int mTexWidth;

    static {
        float[] fArr = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        FULL_RECTANGLE_COORDS = fArr;
        FULL_RECTANGLE_BUF = createFloatBuffer(fArr);
    }

    public EglRectBlt(Texture2dProgram texture2dProgram, int i2, int i3) {
        float[] fArr = new float[8];
        this.mTexCoords = fArr;
        this.mTexCoordArray = createFloatBuffer(fArr);
        this.mProgram = texture2dProgram;
        this.mTexWidth = i2;
        this.mTexHeight = i3;
    }

    public static FloatBuffer createFloatBuffer(float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    void a(Rect rect) {
        float[] fArr = this.mTexCoords;
        int i2 = rect.left;
        int i3 = this.mTexWidth;
        fArr[0] = i2 / i3;
        int i4 = rect.bottom;
        int i5 = this.mTexHeight;
        fArr[1] = 1.0f - (i4 / i5);
        int i6 = rect.right;
        fArr[2] = i6 / i3;
        fArr[3] = 1.0f - (i4 / i5);
        fArr[4] = i2 / i3;
        int i7 = rect.top;
        fArr[5] = 1.0f - (i7 / i5);
        fArr[6] = i6 / i3;
        fArr[7] = 1.0f - (i7 / i5);
        this.mTexCoordArray.put(fArr);
        this.mTexCoordArray.position(0);
    }

    public void copyRect(int i2, float[] fArr, Rect rect) {
        a(rect);
        this.mProgram.draw(Texture2dProgram.IDENTITY_MATRIX, FULL_RECTANGLE_BUF, 0, 4, 2, 8, fArr, this.mTexCoordArray, i2, 8);
    }

    public int createTextureObject() {
        return this.mProgram.createTextureObject();
    }

    public void loadTexture(int i2, Bitmap bitmap) {
        this.mProgram.loadTexture(i2, bitmap);
    }

    public void release(boolean z2) {
        Texture2dProgram texture2dProgram = this.mProgram;
        if (texture2dProgram != null) {
            if (z2) {
                texture2dProgram.release();
            }
            this.mProgram = null;
        }
    }
}
