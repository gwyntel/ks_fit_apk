package io.flutter.plugin.common;

import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/* loaded from: classes4.dex */
public final class JSONMessageCodec implements MessageCodec<Object> {
    public static final JSONMessageCodec INSTANCE = new JSONMessageCodec();

    private JSONMessageCodec() {
    }

    @Override // io.flutter.plugin.common.MessageCodec
    @Nullable
    public Object decodeMessage(@Nullable ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        try {
            JSONTokener jSONTokener = new JSONTokener(StringCodec.INSTANCE.decodeMessage(byteBuffer));
            Object objNextValue = jSONTokener.nextValue();
            if (jSONTokener.more()) {
                throw new IllegalArgumentException("Invalid JSON");
            }
            return objNextValue;
        } catch (JSONException e2) {
            throw new IllegalArgumentException("Invalid JSON", e2);
        }
    }

    @Override // io.flutter.plugin.common.MessageCodec
    @Nullable
    public ByteBuffer encodeMessage(@Nullable Object obj) throws JSONException {
        if (obj == null) {
            return null;
        }
        Object objWrap = JSONUtil.wrap(obj);
        return objWrap instanceof String ? StringCodec.INSTANCE.encodeMessage(JSONObject.quote((String) objWrap)) : StringCodec.INSTANCE.encodeMessage(objWrap.toString());
    }
}
