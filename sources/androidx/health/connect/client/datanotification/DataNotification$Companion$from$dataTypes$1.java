package androidx.health.connect.client.datanotification;

import androidx.health.platform.client.proto.DataProto;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

@Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes.dex */
/* synthetic */ class DataNotification$Companion$from$dataTypes$1 extends FunctionReferenceImpl implements Function1<byte[], DataProto.DataType> {
    public static final DataNotification$Companion$from$dataTypes$1 INSTANCE = new DataNotification$Companion$from$dataTypes$1();

    DataNotification$Companion$from$dataTypes$1() {
        super(1, DataProto.DataType.class, "parseFrom", "parseFrom([B)Landroidx/health/platform/client/proto/DataProto$DataType;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    public final DataProto.DataType invoke(byte[] bArr) {
        return DataProto.DataType.parseFrom(bArr);
    }
}
