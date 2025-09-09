package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class EnumCreatorDeserializer implements ObjectDeserializer {
    private final Method creator;
    private final Class paramType;

    public EnumCreatorDeserializer(Method method) {
        this.creator = method;
        this.paramType = method.getParameterTypes()[0];
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        try {
            return (T) this.creator.invoke(null, defaultJSONParser.parseObject((Class) this.paramType));
        } catch (IllegalAccessException e2) {
            throw new JSONException("parse enum error", e2);
        } catch (InvocationTargetException e3) {
            throw new JSONException("parse enum error", e3);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 0;
    }
}
