package com.aliyun.alink.linksdk.tmp.device.payload;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.ALog;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class ValueWrapper<T> {
    private static final String TAG = "[Tmp]ValueWrapper";
    private static Method isIntegralMethod;
    protected String type;
    protected T value;

    public static class ArrayValueWrapper extends ValueWrapper<List<ValueWrapper>> {
        public ArrayValueWrapper() {
            this.type = TmpConstant.TYPE_VALUE_ARRAY;
        }

        public void add(ValueWrapper valueWrapper) {
            if (this.value == null) {
                this.value = (T) new ArrayList();
            }
            ((List) this.value).add(valueWrapper);
        }

        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public List<ValueWrapper> getValue() {
            return (List) this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public void setValue(List<ValueWrapper> list) {
            this.value = list;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ArrayValueWrapper(List<ValueWrapper> list) {
            this();
            this.value = list;
        }
    }

    public static class DoubleValueWrapper extends ValueWrapper<Double> {
        public DoubleValueWrapper() {
            this.type = TmpConstant.TYPE_VALUE_DOUBLE;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public Double getValue() {
            return (Double) this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public void setValue(Double d2) {
            this.value = d2;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DoubleValueWrapper(Double d2) {
            this();
            this.value = d2;
        }
    }

    public static class IntValueWrapper extends ValueWrapper<Integer> {
        public IntValueWrapper() {
            this.type = "int";
        }

        public void setValue(int i2) {
            this.value = (T) Integer.valueOf(i2);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public Integer getValue() {
            return (Integer) this.value;
        }

        public IntValueWrapper(int i2) {
            this();
            this.value = (T) Integer.valueOf(i2);
        }
    }

    public static class StringValueWrapper extends ValueWrapper<String> {
        public StringValueWrapper() {
            this.type = "string";
        }

        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public String getValue() {
            return (String) this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public void setValue(String str) {
            this.value = str;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public StringValueWrapper(String str) {
            this();
            this.value = str;
        }
    }

    public static class StructValueWrapper extends ValueWrapper<Map<String, ValueWrapper>> {
        public StructValueWrapper() {
            this(new HashMap());
        }

        public ValueWrapper addValue(String str, ValueWrapper valueWrapper) {
            if (this.value == null) {
                this.value = (T) new HashMap();
            }
            return (ValueWrapper) ((Map) this.value).put(str, valueWrapper);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public StructValueWrapper(Map<String, ValueWrapper> map) {
            this.type = "struct";
            this.value = map;
        }

        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public Map<String, ValueWrapper> getValue() {
            return (Map) this.value;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper
        public void setValue(Map<String, ValueWrapper> map) {
            this.value = map;
        }
    }

    public static class ValueWrapperJsonDeSerializer implements JsonDeserializer<ValueWrapper> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.JsonDeserializer
        public ValueWrapper deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonPrimitive asJsonPrimitive;
            if (jsonElement == null) {
                return null;
            }
            if (jsonElement.isJsonObject()) {
                StructValueWrapper structValueWrapper = new StructValueWrapper();
                JsonObject asJsonObject = jsonElement.getAsJsonObject();
                for (String str : asJsonObject.keySet()) {
                    structValueWrapper.addValue(str, (ValueWrapper) jsonDeserializationContext.deserialize(asJsonObject.get(str), ValueWrapper.class));
                }
                return structValueWrapper;
            }
            if (jsonElement.isJsonArray()) {
                ArrayValueWrapper arrayValueWrapper = new ArrayValueWrapper();
                JsonArray asJsonArray = jsonElement.getAsJsonArray();
                for (int i2 = 0; i2 < asJsonArray.size(); i2++) {
                    arrayValueWrapper.add((ValueWrapper) jsonDeserializationContext.deserialize(asJsonArray.get(i2), ValueWrapper.class));
                }
                return arrayValueWrapper;
            }
            if (!jsonElement.isJsonPrimitive() || (asJsonPrimitive = jsonElement.getAsJsonPrimitive()) == null) {
                return null;
            }
            if (asJsonPrimitive.isBoolean()) {
                return new BooleanValueWrapper(Integer.valueOf(asJsonPrimitive.getAsInt()));
            }
            if (asJsonPrimitive.isString()) {
                return new StringValueWrapper(asJsonPrimitive.getAsString());
            }
            if (asJsonPrimitive.isNumber()) {
                return ValueWrapper.isInteger(asJsonPrimitive) ? new IntValueWrapper(asJsonPrimitive.getAsInt()) : new DoubleValueWrapper(Double.valueOf(asJsonPrimitive.getAsDouble()));
            }
            if (!asJsonPrimitive.isJsonObject()) {
                return null;
            }
            StructValueWrapper structValueWrapper2 = new StructValueWrapper();
            JsonObject asJsonObject2 = asJsonPrimitive.getAsJsonObject();
            for (String str2 : asJsonObject2.keySet()) {
                structValueWrapper2.addValue(str2, (ValueWrapper) jsonDeserializationContext.deserialize(asJsonObject2.get(str2), ValueWrapper.class));
            }
            return structValueWrapper2;
        }
    }

    public static class ValueWrapperJsonSerializer implements JsonSerializer<ValueWrapper> {
        @Override // com.google.gson.JsonSerializer
        public JsonElement serialize(ValueWrapper valueWrapper, Type type, JsonSerializationContext jsonSerializationContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (valueWrapper == null) {
                return null;
            }
            if ("int".equalsIgnoreCase(valueWrapper.getType()) || "enum".equalsIgnoreCase(valueWrapper.getType())) {
                return new JsonPrimitive((Integer) valueWrapper.getValue());
            }
            if ("string".equalsIgnoreCase(valueWrapper.getType()) || "date".equalsIgnoreCase(valueWrapper.getType())) {
                return new JsonPrimitive((String) valueWrapper.getValue());
            }
            if ("bool".equalsIgnoreCase(valueWrapper.getType())) {
                return new JsonPrimitive((Integer) valueWrapper.getValue());
            }
            if (TmpConstant.TYPE_VALUE_DOUBLE.equalsIgnoreCase(valueWrapper.getType()) || "float".equalsIgnoreCase(valueWrapper.getType())) {
                return new JsonPrimitive((Double) valueWrapper.getValue());
            }
            if (TmpConstant.TYPE_VALUE_ARRAY.equalsIgnoreCase(valueWrapper.getType())) {
                List list = (List) valueWrapper.getValue();
                JsonArray jsonArray = new JsonArray();
                if (list == null || list.isEmpty()) {
                    ALog.w(ValueWrapper.TAG, "TYPE_VALUE_ARRAY empty return []");
                    return jsonArray;
                }
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    jsonArray.add(jsonSerializationContext.serialize((ValueWrapper) it.next(), new TypeToken<ValueWrapper>() { // from class: com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper.ValueWrapperJsonSerializer.1
                    }.getType()));
                }
                return jsonArray;
            }
            if (!"struct".equalsIgnoreCase(valueWrapper.getType())) {
                return jsonSerializationContext.serialize(valueWrapper.getValue());
            }
            JsonObject jsonObject = new JsonObject();
            StructValueWrapper structValueWrapper = (StructValueWrapper) valueWrapper;
            if (valueWrapper.getValue() == null) {
                return jsonObject;
            }
            for (Map.Entry<String, ValueWrapper> entry : structValueWrapper.getValue().entrySet()) {
                jsonObject.add(entry.getKey(), jsonSerializationContext.serialize(entry.getValue(), new TypeToken<ValueWrapper>() { // from class: com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper.ValueWrapperJsonSerializer.2
                }.getType()));
            }
            return jsonObject;
        }
    }

    public ValueWrapper(T t2) {
        this.value = t2;
    }

    public static boolean isInteger(JsonPrimitive jsonPrimitive) {
        String asString = jsonPrimitive.getAsString();
        return (asString.contains(".") || asString.contains("e") || asString.contains(ExifInterface.LONGITUDE_EAST)) ? false : true;
    }

    public static boolean isIntegral(JsonPrimitive jsonPrimitive) throws NoSuchMethodException, SecurityException {
        try {
            if (isIntegralMethod == null) {
                Method declaredMethod = JsonPrimitive.class.getDeclaredMethod("isIntegral", JsonPrimitive.class);
                isIntegralMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            }
            return ((Boolean) isIntegralMethod.invoke(JsonPrimitive.class, jsonPrimitive)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public String getType() {
        return this.type;
    }

    public T getValue() {
        return this.value;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setValue(T t2) {
        this.value = t2;
    }

    public static class BooleanValueWrapper extends IntValueWrapper {
        public BooleanValueWrapper() {
            this.type = "bool";
        }

        /* JADX WARN: Multi-variable type inference failed */
        public BooleanValueWrapper(Integer num) {
            this();
            this.value = num;
        }
    }

    public static class DateValueWrapper extends StringValueWrapper {
        public DateValueWrapper() {
            this.type = "date";
        }

        public DateValueWrapper(String str) {
            this();
            setValue(str);
        }
    }

    public static class EnumValueWrapper extends IntValueWrapper {
        public EnumValueWrapper() {
            this.type = "enum";
        }

        public EnumValueWrapper(int i2) {
            this();
            setValue(i2);
        }
    }

    public ValueWrapper() {
    }
}
