package androidx.media3.extractor.metadata;

import androidx.annotation.Nullable;
import androidx.media3.common.Metadata;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import java.nio.ByteBuffer;

@UnstableApi
/* loaded from: classes2.dex */
public abstract class SimpleMetadataDecoder implements MetadataDecoder {
    protected abstract Metadata a(MetadataInputBuffer metadataInputBuffer, ByteBuffer byteBuffer);

    @Override // androidx.media3.extractor.metadata.MetadataDecoder
    @Nullable
    public final Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        ByteBuffer byteBuffer = (ByteBuffer) Assertions.checkNotNull(metadataInputBuffer.data);
        Assertions.checkArgument(byteBuffer.position() == 0 && byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0);
        return a(metadataInputBuffer, byteBuffer);
    }
}
