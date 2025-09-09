package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* loaded from: classes2.dex */
public class OptionalCodec implements ObjectSerializer, ObjectDeserializer {
    public static OptionalCodec instance = new OptionalCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        if (type == c1.a()) {
            Integer numCastToInt = TypeUtils.castToInt(defaultJSONParser.parseObject((Class) Integer.class));
            return numCastToInt == null ? (T) OptionalInt.empty() : (T) OptionalInt.of(numCastToInt.intValue());
        }
        if (type == x1.a()) {
            Long lCastToLong = TypeUtils.castToLong(defaultJSONParser.parseObject((Class) Long.class));
            return lCastToLong == null ? (T) OptionalLong.empty() : (T) OptionalLong.of(lCastToLong.longValue());
        }
        if (type == a2.a()) {
            Double dCastToDouble = TypeUtils.castToDouble(defaultJSONParser.parseObject((Class) Double.class));
            return dCastToDouble == null ? (T) OptionalDouble.empty() : (T) OptionalDouble.of(dCastToDouble.doubleValue());
        }
        Object object = defaultJSONParser.parseObject(TypeUtils.unwrapOptional(type));
        return object == null ? (T) Optional.empty() : (T) Optional.of(object);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i2) throws IOException {
        if (obj == null) {
            jSONSerializer.writeNull();
            return;
        }
        if (e1.a(obj)) {
            Optional optionalA = l1.a(obj);
            jSONSerializer.write(optionalA.isPresent() ? optionalA.get() : null);
            return;
        }
        if (p1.a(obj)) {
            OptionalDouble optionalDoubleA = q1.a(obj);
            if (optionalDoubleA.isPresent()) {
                jSONSerializer.write(Double.valueOf(optionalDoubleA.getAsDouble()));
                return;
            } else {
                jSONSerializer.writeNull();
                return;
            }
        }
        if (t1.a(obj)) {
            OptionalInt optionalIntA = u1.a(obj);
            if (optionalIntA.isPresent()) {
                jSONSerializer.out.writeInt(optionalIntA.getAsInt());
                return;
            } else {
                jSONSerializer.writeNull();
                return;
            }
        }
        if (!h1.a(obj)) {
            throw new JSONException("not support optional : " + obj.getClass());
        }
        OptionalLong optionalLongA = i1.a(obj);
        if (optionalLongA.isPresent()) {
            jSONSerializer.out.writeLong(optionalLongA.getAsLong());
        } else {
            jSONSerializer.writeNull();
        }
    }
}
