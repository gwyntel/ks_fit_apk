package com.aliyun.alink.linksdk.tmp.device.payload;

import android.text.TextUtils;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Set;

/* loaded from: classes2.dex */
public class KeyValuePair {
    private String key;
    private ValueWrapper valueWrapper;

    public static class KeyValuePairJsonDeSerializer implements JsonDeserializer<KeyValuePair> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.google.gson.JsonDeserializer
        public KeyValuePair deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject asJsonObject;
            Set<String> setKeySet;
            if (jsonElement == null || !jsonElement.isJsonObject() || (setKeySet = (asJsonObject = jsonElement.getAsJsonObject()).keySet()) == null || asJsonObject.size() < 1) {
                return null;
            }
            for (String str : setKeySet) {
                JsonElement jsonElement2 = asJsonObject.get(str);
                if (jsonElement2 != null && !jsonElement2.isJsonNull() && jsonElement2.isJsonPrimitive()) {
                    JsonPrimitive jsonPrimitive = (JsonPrimitive) jsonElement2;
                    if (jsonPrimitive.isString()) {
                        String asString = jsonElement2.getAsString();
                        if (!TextUtils.isEmpty(asString)) {
                            return new KeyValuePair(str, asString);
                        }
                    } else {
                        if (jsonPrimitive.isNumber()) {
                            return new KeyValuePair(str, jsonElement2.getAsInt());
                        }
                        if (jsonPrimitive.isBoolean()) {
                            return new KeyValuePair(str, jsonElement2.getAsBoolean());
                        }
                    }
                }
            }
            return null;
        }
    }

    public static class KeyValuePairJsonSerializer implements JsonSerializer<KeyValuePair> {
        @Override // com.google.gson.JsonSerializer
        public JsonElement serialize(KeyValuePair keyValuePair, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = null;
            if (keyValuePair == null) {
                return null;
            }
            if (!TextUtils.isEmpty(keyValuePair.getKey()) && keyValuePair.getValueWrapper() != null) {
                jsonObject = new JsonObject();
                if ("int".equalsIgnoreCase(keyValuePair.getValueWrapper().getType())) {
                    jsonObject.addProperty(keyValuePair.getKey(), ((ValueWrapper.IntValueWrapper) keyValuePair.getValueWrapper()).getValue());
                } else if ("string".equalsIgnoreCase(keyValuePair.getValueWrapper().getType())) {
                    jsonObject.addProperty(keyValuePair.getKey(), ((ValueWrapper.StringValueWrapper) keyValuePair.getValueWrapper()).getValue());
                } else if ("bool".equalsIgnoreCase(keyValuePair.getValueWrapper().getType())) {
                    jsonObject.addProperty(keyValuePair.getKey(), ((ValueWrapper.BooleanValueWrapper) keyValuePair.getValueWrapper()).getValue());
                }
            }
            return jsonObject;
        }
    }

    public KeyValuePair(String str, ValueWrapper valueWrapper) {
        this.key = str;
        this.valueWrapper = valueWrapper;
    }

    public String getKey() {
        return this.key;
    }

    public ValueWrapper getValueWrapper() {
        return this.valueWrapper;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setValueWrapper(ValueWrapper valueWrapper) {
        this.valueWrapper = valueWrapper;
    }

    public KeyValuePair(String str, int i2) {
        this.key = str;
        this.valueWrapper = new ValueWrapper.IntValueWrapper(i2);
    }

    public KeyValuePair(String str, String str2) {
        this.key = str;
        this.valueWrapper = new ValueWrapper.StringValueWrapper(str2);
    }

    public KeyValuePair(String str, boolean z2) {
        this.key = str;
        this.valueWrapper = new ValueWrapper.BooleanValueWrapper(Integer.valueOf(z2 ? 1 : 0));
    }
}
