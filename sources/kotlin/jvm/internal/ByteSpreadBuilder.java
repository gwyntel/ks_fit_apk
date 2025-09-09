package kotlin.jvm.internal;

import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\u0007\u001a\u00020\u0003*\u00020\u0002H\u0014¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t¢\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u0002¢\u0006\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00028\u0002X\u0082\u0004¢\u0006\u0006\n\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lkotlin/jvm/internal/ByteSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "", "size", "<init>", "(I)V", "e", "([B)I", "", "value", "", TmpConstant.GROUP_OP_ADD, "(B)V", "toArray", "()[B", "values", "[B", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class ByteSpreadBuilder extends PrimitiveSpreadBuilder<byte[]> {

    @NotNull
    private final byte[] values;

    public ByteSpreadBuilder(int i2) {
        super(i2);
        this.values = new byte[i2];
    }

    public final void add(byte value) {
        byte[] bArr = this.values;
        int position = getPosition();
        b(position + 1);
        bArr[position] = value;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // kotlin.jvm.internal.PrimitiveSpreadBuilder
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public int getSize(byte[] bArr) {
        Intrinsics.checkNotNullParameter(bArr, "<this>");
        return bArr.length;
    }

    @NotNull
    public final byte[] toArray() {
        return (byte[]) d(this.values, new byte[c()]);
    }
}
