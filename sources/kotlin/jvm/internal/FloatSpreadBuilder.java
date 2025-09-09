package kotlin.jvm.internal;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\u0003*\u00020\u0002H\u0014¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u0002¢\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lkotlin/jvm/internal/FloatSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "size", "<init>", "(I)V", "e", "([F)I", "", "value", "", TmpConstant.GROUP_OP_ADD, "(F)V", "toArray", "()[F", "values", "[F", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class FloatSpreadBuilder extends PrimitiveSpreadBuilder<float[]> {

    @NotNull
    private final float[] values;

    public FloatSpreadBuilder(int i2) {
        super(i2);
        this.values = new float[i2];
    }

    public final void add(float value) {
        float[] fArr = this.values;
        int position = getPosition();
        b(position + 1);
        fArr[position] = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public int getSize(float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<this>");
        return fArr.length;
    }

    @NotNull
    public final float[] toArray() {
        return (float[]) d(this.values, new float[c()]);
    }
}
