package io.flutter.plugins.sharedpreferences;

import io.flutter.plugin.common.StandardMessageCodec;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u001a\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0004H\u0014¨\u0006\u000e"}, d2 = {"Lio/flutter/plugins/sharedpreferences/SharedPreferencesAsyncApiCodec;", "Lio/flutter/plugin/common/StandardMessageCodec;", "()V", "readValueOfType", "", "type", "", "buffer", "Ljava/nio/ByteBuffer;", "writeValue", "", "stream", "Ljava/io/ByteArrayOutputStream;", "value", "shared_preferences_android_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes4.dex */
final class SharedPreferencesAsyncApiCodec extends StandardMessageCodec {

    @NotNull
    public static final SharedPreferencesAsyncApiCodec INSTANCE = new SharedPreferencesAsyncApiCodec();

    private SharedPreferencesAsyncApiCodec() {
    }

    @Override // io.flutter.plugin.common.StandardMessageCodec
    @Nullable
    protected Object readValueOfType(byte type, @NotNull ByteBuffer buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        if (type != Byte.MIN_VALUE) {
            return super.readValueOfType(type, buffer);
        }
        Object value = readValue(buffer);
        List<? extends Object> list = value instanceof List ? (List) value : null;
        if (list != null) {
            return SharedPreferencesPigeonOptions.INSTANCE.fromList(list);
        }
        return null;
    }

    @Override // io.flutter.plugin.common.StandardMessageCodec
    protected void writeValue(@NotNull ByteArrayOutputStream stream, @Nullable Object value) {
        Intrinsics.checkNotNullParameter(stream, "stream");
        if (!(value instanceof SharedPreferencesPigeonOptions)) {
            super.writeValue(stream, value);
        } else {
            stream.write(128);
            writeValue(stream, ((SharedPreferencesPigeonOptions) value).toList());
        }
    }
}
